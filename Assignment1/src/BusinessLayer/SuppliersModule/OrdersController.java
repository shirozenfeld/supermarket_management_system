package BusinessLayer.SuppliersModule;

import BusinessLayer.InventoryModule.PeriodicOrder;
import DataAccessLayer.SuppliersModule.*;

import java.time.LocalDate;
import java.util.*;


public class OrdersController
{

    DataAccessLayer.SuppliersModule.SupplierDAO supplierDAO ;
    DataAccessLayer.SuppliersModule.ContractDAO contractDAO;
    DataAccessLayer.SuppliersModule.DiscountDAO discountDAO;
    DataAccessLayer.SuppliersModule.ManufacturerDAO manufacturerDAO;
    DataAccessLayer.SuppliersModule.SupplierProductDAO supplierProductDAO;
    DataAccessLayer.SuppliersModule.SuperProductDAO superProductDAO;
    DataAccessLayer.SuppliersModule.OrderDAO orderDAO;
    DataAccessLayer.SuppliersModule.DeficienciesReportDAO deficienciesReportDAO;

    private static OrdersController instance=null;

    private OrdersController()
    {

        this.supplierDAO = DataAccessLayer.SuppliersModule.SupplierDAO.getSupplierInstance();
        this.contractDAO=DataAccessLayer.SuppliersModule.ContractDAO.getContractInstance();
        this.discountDAO=DataAccessLayer.SuppliersModule.DiscountDAO.getDiscountInstance();
        this.supplierProductDAO=DataAccessLayer.SuppliersModule.SupplierProductDAO.getSupplierProductInstance();
        this.orderDAO=DataAccessLayer.SuppliersModule.OrderDAO.getOrderInstance();
        this.deficienciesReportDAO=DataAccessLayer.SuppliersModule.DeficienciesReportDAO.getDeficienciesReportInstance();
        this.manufacturerDAO=DataAccessLayer.SuppliersModule.ManufacturerDAO.getManufacturerInstance();
        this.superProductDAO=DataAccessLayer.SuppliersModule.SuperProductDAO.getSuperProductInstance();
        this.periodicOrders_Queue = new HashMap<>();
    }

    public static OrdersController getInstance()
    {
        if (instance == null)
        {
            instance = new OrdersController();

        }
        return instance;
    }
    Map<String,Order> periodicOrders_Queue;

    public boolean deficiencies_handler(DeficienciesReport deficienciesReport)
    {
        /**
         * Receives a BusinessLayer.DeficienciesReport makes supplier orders out of it, in attempt to find the cheapest supply combination for each product.
         * @deficienciesReport - BusinessLayer.DeficienciesReport, contains missing super-products and the missing amount.
         * @open_orders - stores all orders that were created in the scope of the current deficiencies report.
         * @open_deficienciesReports - stores all deficiencies reports that were created in the program. Needed here in order to delete the current handled report in case it fails.
         * returns a boolean value that indicates whether the process succeeded or not.
         */
        //DeficienciesReport deficienciesReport = this.deficienciesReportDAO.getDailyDeficienciesReport();
        SuperProductDAO superProductDAO = SuperProductDAO.getSuperProductInstance();
        Map<SuperProduct,Integer> productsOfDeficiencies=new HashMap<>();
        for(Map.Entry<String, Integer> entry : deficienciesReport.getProductsList().entrySet())
        {
            String barcode= entry.getKey();
            int amount = entry.getValue();
            SuperProduct superProduct = superProductDAO.getSuperProductByBarcode(barcode);
            productsOfDeficiencies.put(superProduct,amount);
        }
        deficienciesReport.setProducts(productsOfDeficiencies);
        Map<SupplierProduct, Integer> cheapest_combination = null;
        Order order;
        List<Double> finite_finale_prices = null;
        double first_price, finale_price; //used for storing the total price of an order before and after discounts.
        Supplier supplier = null;
        Map<Supplier, Double> calculated_supplier_equal = null, calculated_supplier_greater_than=null; //will store the cheapest supplier in the equal/greater domain and its finale unit-price for the product
        SupplierProduct supplier_product = null;
        for (Map.Entry<SuperProduct, java.lang.Integer> entry : deficienciesReport.getProducts().entrySet())
        {
            //go over each missing super product,
            List<Supplier> less_than = new ArrayList<Supplier>(), equal = new ArrayList<Supplier>(), greater_than = new ArrayList<Supplier>();
            // the lists are used to store suppliers according to the amount they supply of the product, regarding to the needed amount in the report
            SuperProduct product = entry.getKey();
            int needed_amount = entry.getValue();
            for (int i = 0; i < product.getSuppliers().size(); i++)
            {
                //for each missing super product, go over its relevant suppliers and divide them into the lists mentioned above
                supplier = product.getSuppliers().get(i);
                Contract contract = supplier.getContract();
                supplier_product = contract.getProducts().get(product.getSupermarket_id());
                if (supplier_product!=null) {
                    int contract_amount = supplier_product.getAmount();
                    if (contract_amount == needed_amount)
                        equal.add(supplier);
                    else if (contract_amount > needed_amount)
                        greater_than.add(supplier);
                    else
                        less_than.add(supplier);
                }
            }
            if (equal.size() ==0  && greater_than.size()==0 && less_than.size()==0)
            {

                //if there is super product that has no suppliers to supply it, cancel the deficiencies report and the orders that were created within it as well
                this.orderDAO.removeOrdersByReportID(deficienciesReport.getReport_id());
                //this.deficienciesReportDAO.deleteByFailure();
                return false;
            }
            else
            {
                //else, there might be a supplier that supplies the accurate amount/ more than it/ a combination of suppliers that supply less than the needed amount
                if (equal.size()!=0 || greater_than.size()!=0)
                {
                    if (equal.size()!=0 )
                    {
                        //the priority is to go to the suppliers who supply the accurate amount first
                        calculated_supplier_equal = calculator_cheapest_supplier(equal, product, needed_amount);
                        if (deficienciesReport.getOrders().size()!=0) //to avoid null pointer exception
                            order = deficienciesReport.getOrders().get(calculated_supplier_equal.entrySet().iterator().next().getKey().getSupplier_id());
                        else
                            order=null;
                    }
                    if(greater_than.size()!=0)
                    {
                        calculated_supplier_greater_than = calculator_cheapest_supplier(greater_than, product, needed_amount);
                    }
                }
                if(less_than.size()!=0) // less than
                {
                    cheapest_combination=new HashMap<SupplierProduct, Integer>();
                    //find the smallest and the cheapest combination of suppliers, as long as they add up to the needed amount, using subset-sum algorithm
                    List<SupplierProduct> supplierProducts = new ArrayList<SupplierProduct>();
                    for (int i = 0; i < product.getSuppliers().size(); i++)
                        supplierProducts.add(product.getSuppliers().get(i).getContract().getProducts().get(product.getSupermarket_id()));
                    List<Map<SupplierProduct, Integer>> suppliers_subsets = findSubsets(supplierProducts, needed_amount, supplierProducts.size()); //stores all possible subsets
                    int minimal_suppliers_combinations_size = 0;
                    List<Map<SupplierProduct, Integer>> minimal_suppliers_combinations = new ArrayList<Map<SupplierProduct, Integer>>(); // will store the minimal group (in size terms)
                    if(suppliers_subsets.size()==0) //means there is no supplier to supply the product. then cancel the report and the orders within it
                    {
                        orderDAO.removeOrdersByReportID(deficienciesReport.getReport_id());
                        deficienciesReportDAO.deleteByFailure();
                        return false;

                    }
                    for (int i = 0; i < suppliers_subsets.size(); i++)
                    {
                        //find the minimal combination (minimal in terms of size) among the subset groups of suppliers
                        if (i == 0 || minimal_suppliers_combinations_size == suppliers_subsets.get(i).size())
                        {
                            minimal_suppliers_combinations_size = suppliers_subsets.get(i).size();
                            minimal_suppliers_combinations.add(suppliers_subsets.get(i));
                        }
                        if (minimal_suppliers_combinations_size > suppliers_subsets.get(i).size())
                        {
                            minimal_suppliers_combinations.clear();
                            minimal_suppliers_combinations_size = suppliers_subsets.get(i).size();
                            minimal_suppliers_combinations.add(suppliers_subsets.get(i));
                        }
                    }
                    finite_finale_prices = new ArrayList<Double>();
                    List<Double> current_finale_prices = new ArrayList<Double>();
                    double cheapest_combination_price = Double.POSITIVE_INFINITY;
                    finale_price = 0;
                    for (int i = 0; i < minimal_suppliers_combinations.size(); i++)
                    {
                        //find the cheapest combination (minimal in terms of price) among the minimal subset groups of suppliers
                        for (Map.Entry<SupplierProduct, Integer> entry1 : minimal_suppliers_combinations.get(i).entrySet())
                        {
                            SupplierProduct key = entry1.getKey();
                            Integer value = entry1.getValue();
                            SupplierDAO supplierDAO=SupplierDAO.getSupplierInstance();
                            Supplier keySupplier=supplierDAO.getSupplierBySupplierID(key.getSupplierID());
                            if (keySupplier.getContract().getQuantities_bill().containsKey(key.getCatalog_number()))
                            {
                                double current_price_after_discount = best_discount(key, value);
                                finale_price += current_price_after_discount;
                                current_finale_prices.add(current_price_after_discount);
                            }
                            else
                            {
                                finale_price+=value*key.getUnit_price();
                                current_finale_prices.add(value*key.getUnit_price());
                            }
                        }
                        if (i == 0 || finale_price < cheapest_combination_price)
                        {
                            cheapest_combination = minimal_suppliers_combinations.get(i);
                            cheapest_combination_price = finale_price;
                            finite_finale_prices.clear();
                            finite_finale_prices.addAll(current_finale_prices);
                        }
                        finale_price=0;
                        current_finale_prices.clear();
                    }
                }
            }
            find_soonest_and_create_order(cheapest_combination,calculated_supplier_equal,calculated_supplier_greater_than,deficienciesReport,product,finite_finale_prices,needed_amount);
        }

        //deficienciesReport.update_reportStatus(); // report status goes from "in process" to "completed". if it fails, the whole object is deleted.
        //deficienciesReportDAO.deleteByFailure();
        //deficienciesReportDAO.updateDailyReportStatus();
        finale_price_orders(deficienciesReport); // goes through the open orders in the scope of a  deficienciesReport and applies the most lucrative BusinessLayer.Supplier-BusinessLayer.Discount for each order (a given discount per order, not per a specific product

        for (Map.Entry<String, Order> entry : deficienciesReport.getOrders().entrySet())
        {
            this.orderDAO.addShortageOrder(entry.getValue());
        }
        return true;
    }

    public double best_discount(SupplierProduct supplier_product, int needed_amount)
    {
        /**
         * Receives a supplier-product and a needed amount of it, goes over the discounts of the supplier-product and chooses the most lucrative one
         * Returns the finale price regarding the deduction of the best discount
         */
        SupplierDAO supplierDAO=SupplierDAO.getSupplierInstance();
        Supplier supplier = supplierDAO.getSupplierBySupplierID(supplier_product.getSupplierID());
        Contract contract = supplier.getContract();
        double first_price = needed_amount * supplier_product.getUnit_price(), finale_price = 0, minimal_supplier_price = Double.POSITIVE_INFINITY;
        List<Discount> product_discounts = contract.getQuantities_bill().get(supplier_product.getCatalog_number());
        for (int j = 0; j < product_discounts.size(); j++) {
            double discount = product_discounts.get(j).Discount_Calculator(first_price, needed_amount);
            finale_price = first_price - discount;
            if (j == 0 || minimal_supplier_price > finale_price) {
                minimal_supplier_price = finale_price;
            }
        }
        return minimal_supplier_price;
    }

    public Map<Supplier, Double> calculator_cheapest_supplier(List<Supplier> suppliers, SuperProduct product, int needed_amount) {
        /**
         * calculate the cheapest supplier in a given list of suppliers, regarding a super-product they all supply and a needed amount
         * returns a map of the cheapest supplier and its finale price for the needed amount
         * this function handles only with suppliers that supply an equal/greater amount the needed amount
         */
        Supplier cheapest_supplier = null;
        double minimal_supplier_price = 0, minimal_price_among_suppliers = 0, first_price = 0;
        for (int i = 0; i < suppliers.size(); i++)
        {
            //go through each supplier's contract price + quantities bill and find its cheapest finale price
            Supplier supplier = suppliers.get(i);
            SupplierProduct supplier_product = supplier.getContract().getProducts().get(product.getSupermarket_id());
            double contract_price = supplier_product.getUnit_price();
            //getting the discounts of the product
            if(supplier.getContract().getQuantities_bill().containsKey(supplier_product.getCatalog_number()))
            {
                //compare the result to the minimal price found up until now, and update if necessary
                minimal_supplier_price = best_discount(supplier_product, needed_amount);
                if (i == 0 || minimal_price_among_suppliers > minimal_supplier_price)
                {
                    minimal_price_among_suppliers = minimal_supplier_price;
                    cheapest_supplier = supplier;
                }
            }
            else
            {
                minimal_supplier_price = needed_amount*contract_price;
                if (i == 0 || minimal_price_among_suppliers > minimal_supplier_price)
                {
                    minimal_price_among_suppliers = minimal_supplier_price;
                    cheapest_supplier = supplier;
                }
            }

        }
        Map<Supplier, Double> returned_value = new HashMap<Supplier, Double>();
        returned_value.put(cheapest_supplier, minimal_price_among_suppliers);
        return returned_value;
    }



    List<Map<SupplierProduct, Integer>> findSubsets(List<SupplierProduct> supplier_products, int target,
                                                           int minSize)
    {
        // a wrapper function - recieves a list of supplier-products, a target and a minimum size found, and returns the minimal subset of supplier-products (suppliers,in terms of size)
        // the subset sum algorithm happens recursively
        List<SupplierProduct> all_amounts_list=new ArrayList<SupplierProduct>();
        for(int i=0;i<minSize;i++)
        {
            for(int j=supplier_products.get(i).getAmount();j>0;j--)
            {
                all_amounts_list.add(new SupplierProduct(supplier_products.get(i).getSupermarket_id(),supplier_products.get(i).getCatalog_number(),j,supplier_products.get(i).getUnit_price(),supplier_products.get(i).getSupplierID()));
            }
        }
        List<Map<SupplierProduct, Integer>> subsets = new ArrayList<>();
        findSubsetsHelper(all_amounts_list, target, minSize, new HashMap<>(), 0, subsets,new ArrayList<Supplier>());
        return subsets;
    }

    private void findSubsetsHelper(List<SupplierProduct> supplier_products, int target, int minSize, Map<
            SupplierProduct, Integer> subset, int index, List<Map<SupplierProduct, Integer>> subsets, List<Supplier> suppliers)
    {
        //
        // a helper function, applies subset-sum algorithm on a group of supplier-products, in order to find the minimal set of supplier-products
        //
        if (target == 0 && subset.size() <= minSize) {
            subsets.add(new HashMap<>(subset));
            return;
        }
        if (index == supplier_products.size()) {
            return;
        }
        SupplierProduct currentSupplier = supplier_products.get(index);
        SupplierDAO supplierDAO=SupplierDAO.getSupplierInstance();
        Supplier supplier=supplierDAO.getSupplierBySupplierID(currentSupplier.getSupplierID());
        if (currentSupplier.getAmount() <= target && !suppliers.contains(supplier))
        {
            subset.put(currentSupplier, currentSupplier.getAmount());

            suppliers.add(supplier);
            findSubsetsHelper(supplier_products, target - currentSupplier.getAmount(), minSize, subset, index + 1, subsets,suppliers);
            subset.remove(currentSupplier);
            suppliers.remove(supplier);
        }
        findSubsetsHelper(supplier_products, target, minSize, subset, index + 1, subsets,suppliers);

    }


    public void finale_price_orders(DeficienciesReport deficienciesReport)
    {
        /**
         * goes through the open orders in the scope of a  deficienciesReport and applies the most lucrative BusinessLayer.Supplier-BusinessLayer.Discount for each order (a given discount per order, not per a specific product)
         */
        for (Map.Entry<String, Order> entry2 : deficienciesReport.getOrders().entrySet()) {
            String key = entry2.getKey();
            Order value = entry2.getValue();
            String supplierID=value.getSupplierID();
            Supplier supplier = supplierDAO.getSupplierBySupplierID(supplierID);
            Contract contract = supplier.getContract();
            double finale_price = 0, minimal_supplier_price = Double.POSITIVE_INFINITY;
            List<Discount> supplier_discounts = contract.getQuantities_bill().get(-1);
            if (supplier_discounts != null) //there might not be supplier discounts
            {
                for (int i = 0; i < supplier_discounts.size(); i++) {
                    MoneyDiscount moneyDiscount = (MoneyDiscount) supplier_discounts.get(i);
                    int total_amount = value.getTotal_amount();
                    finale_price = value.getFinale_price() - moneyDiscount.Discount_Calculator(value.getFirst_price(), total_amount);
                    if (minimal_supplier_price > finale_price) {
                        minimal_supplier_price = finale_price;
                    }
                }
                value.setFinale_price(finale_price);
            }
        }
    }



    public void automatic_periodic_orders()
    {

        Map<String,Supplier> super_suppliers = supplierDAO.getAllSuppliers();
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        int today = calendar.get(Calendar.DAY_OF_WEEK);
        for(Map.Entry<String,Supplier> entry : super_suppliers.entrySet())
        {
            if (entry.getValue().getCard().getPeriodic_orders_supplying_day().ordinal()==today)
            {
                for (int i=0;i<entry.getValue().getPeriodicOrder_list().size();i++)
                {
                    String order_id=entry.getValue().getPeriodicOrder_list().get(i).getOrder_id();
                    Order periodic_order=entry.getValue().getPeriodicOrder_list().get(i);
                    periodicOrders_Queue.put(order_id,periodic_order);
                }
            }
        }
    }

    public void find_soonest_and_create_order(Map<SupplierProduct, Integer> cheapest_less_than, Map<Supplier, Double> cheapest_equal,Map<Supplier, Double> cheapest_greater_than,DeficienciesReport deficienciesReport,SuperProduct product, List<Double> finite_final_prices, int needed_amount)
    {
        int counter=0,distance=0,min_distance=100;
        Supplier supplier=null,min_supplier=null;
        Double first_price=0.0,finale_price=0.0;
        if(cheapest_equal!=null)
        {
            //starting from the equal
            min_supplier = cheapest_equal.entrySet().iterator().next().getKey();
            min_distance = min_supplier.get_minimial_supplying_time();
            finale_price = cheapest_equal.entrySet().iterator().next().getValue();

            //continuing to greater
            supplier=cheapest_greater_than.entrySet().iterator().next().getKey();
            distance=supplier.get_minimial_supplying_time();
            if (distance<=min_distance)
            {
                min_supplier=supplier;
                min_distance=distance;
                finale_price=cheapest_greater_than.entrySet().iterator().next().getValue();
            }
        }
        else if (cheapest_greater_than!=null) //cheapest_equal = null so continuing to the greater than
        {
            //starting from the equal
            min_supplier = cheapest_greater_than.entrySet().iterator().next().getKey();
            min_distance = min_supplier.get_minimial_supplying_time();
            finale_price=cheapest_greater_than.entrySet().iterator().next().getValue();
        }
        SupplierDAO supplierDAO=SupplierDAO.getSupplierInstance();
        //continuing to less_than
        if (cheapest_less_than!=null)
        {
            for (Map.Entry<SupplierProduct, Integer> entry : cheapest_less_than.entrySet())
            {
                supplier = supplierDAO.getSupplierBySupplierID(entry.getKey().getSupplierID());
                distance = supplier.get_minimial_supplying_time();
                if (distance <= min_distance)
                {
                    counter = 0;
                    for (Map.Entry<SupplierProduct, Integer> entry1 : cheapest_less_than.entrySet()) {
                        min_supplier = supplierDAO.getSupplierBySupplierID(entry1.getKey().getSupplierID());
                        finale_price = finite_final_prices.get(counter);
                        open_an_order(min_supplier, finale_price, deficienciesReport, product, needed_amount);
                        counter++;
                    }
                    return;
                }
            }
        }
        // if the soonest option is an equal/ greater than
        open_an_order(min_supplier,finale_price, deficienciesReport, product, needed_amount);
    }

    public void open_an_order(Supplier supplier,double finale_price,DeficienciesReport deficienciesReport, SuperProduct product, int needed_amount)
    {
        Order order=null;
        String orderID="";
        double first_price = (supplier.getContract().getProducts().get(product.getSupermarket_id()).getUnit_price()) * needed_amount;
        if (deficienciesReport.getOrders().size()!=0)
        {

            for (Map.Entry<String, Order> entry : deficienciesReport.getOrders().entrySet())
            {
                String curr_order_supplier_id= entry.getValue().getSupplierID();
                if(curr_order_supplier_id.equals(supplier.getSupplier_id()))
                {
                    orderID=entry.getKey();
                    order=deficienciesReport.getOrders().get(orderID);
                }
            }
            if(order!=null)
                order.addToShortageOrder(supplier.getContract().getProducts().get(product.getSupermarket_id()).getCatalog_number(), needed_amount, first_price, finale_price);
            else
            {
                //if there is no open order of the chosen supplier in the scope of the deficiencies report, then open a new one
                order = new Order("Shortage",supplier.getSupplier_id(),deficienciesReport.getReport_id());
                order.addToShortageOrder(supplier.getContract().getProducts().get(product.getSupermarket_id()).getCatalog_number(), needed_amount, first_price, finale_price);
                if (supplier instanceof OrderlyVisiting)
                    ((OrderlyVisiting) supplier).setLatest_order(order);
            }
        }
        else {

            //if there is no open order of the chosen supplier in the scope of the deficiencies report, then open a new one
            order = new Order("Shortage",supplier.getSupplier_id(),deficienciesReport.getReport_id());
            order.addToShortageOrder(supplier.getContract().getProducts().get(product.getSupermarket_id()).getCatalog_number(), needed_amount, first_price, finale_price);
            if (supplier instanceof OrderlyVisiting)
                ((OrderlyVisiting) supplier).setLatest_order(order);
        }
        deficienciesReport.getOrders().put(order.getOrder_id(), order);

    }

//    public boolean doesReportExist(LocalDate date)
//    {
//        return orderDAO.doesReportExistByDate(LocalDate.parse(String.valueOf(date)));
//    }

    public boolean doesOrderExist(String orderID)
    {
        return orderDAO.doesOrderExist(orderID);
    }

    public DeficienciesReport getDeficienciesReport(LocalDate Date){
        return deficienciesReportDAO.getReportByDate(Date);
    }


    public DeficienciesReport getDailyShortageReport()
    {
        return deficienciesReportDAO.getDailyDeficienciesReport();
    }
    public void removeAllOrders()
    {
        orderDAO.removeAllOrders();
    }

    public void addPeriodicOrder(Order order)
    {
        orderDAO.addPeriodicOrder(order);
    }

}

