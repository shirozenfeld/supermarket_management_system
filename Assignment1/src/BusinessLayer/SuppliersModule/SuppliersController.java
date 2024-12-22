package BusinessLayer.SuppliersModule;

import DataAccessLayer.SuppliersModule.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
public class SuppliersController {

    DataAccessLayer.SuppliersModule.SupplierDAO supplierDAO ;
    DataAccessLayer.SuppliersModule.ContractDAO contractDAO;
    DataAccessLayer.SuppliersModule.DiscountDAO discountDAO;
    DataAccessLayer.SuppliersModule.ManufacturerDAO manufacturerDAO;
    DataAccessLayer.SuppliersModule.SupplierProductDAO supplierProductDAO;
    DataAccessLayer.SuppliersModule.SuperProductDAO superProductDAO;
    DataAccessLayer.SuppliersModule.OrderDAO orderDAO;
    ManufacturerController manufacturerController;
    private static SuppliersController instance=null;

    private SuppliersController()
    {
        this.supplierDAO = DataAccessLayer.SuppliersModule.SupplierDAO.getSupplierInstance();
        this.contractDAO=DataAccessLayer.SuppliersModule.ContractDAO.getContractInstance();
        this.discountDAO=DataAccessLayer.SuppliersModule.DiscountDAO.getDiscountInstance();
        this.supplierProductDAO=DataAccessLayer.SuppliersModule.SupplierProductDAO.getSupplierProductInstance();
        this.orderDAO=DataAccessLayer.SuppliersModule.OrderDAO.getOrderInstance();
        this.manufacturerDAO=DataAccessLayer.SuppliersModule.ManufacturerDAO.getManufacturerInstance();
        this.superProductDAO=DataAccessLayer.SuppliersModule.SuperProductDAO.getSuperProductInstance();
        Supplier.setSupplier_id_counter(supplierDAO.getMaxID());
        this.manufacturerController=ManufacturerController.getInstance();
    }

    public static SuppliersController getInstance()
    {
        if (instance == null) {
            instance = new SuppliersController();
        }
        return instance;
    }
    public void add_supplierProduct(String supermarket_id, int amount, double unit_price, String supplierID)
    {
        /**
         * recieves a supplier, a super product id, the stock of super products, an amount and a unit price, and creates a new supplier product.
         * the supplier product describes the fact that the super product is supplied by the current supplier, and other details about the supply method.
         */
        //SuperProduct superProduct = super_products.get(supermarket_id);
        // notifying the super product of its new supplier
        //superProduct.getSuppliers().add(supplier);
        // creating the new supplier product
        SupplierProduct new_supplier_product;
        Supplier supplier = supplierDAO.getSupplierBySupplierID(supplierID);
        if (supplier.getContract().getProducts().isEmpty())
                new_supplier_product = new SupplierProduct(supermarket_id, amount, unit_price, supplierID);
        else {
            int catalogNumber = supplier.get_a_new_supplierProduct_catalog_number();
            new_supplier_product = new SupplierProduct(supermarket_id,catalogNumber, amount, unit_price, supplierID);

        }

        // adding the new supplier product to the contract
        //supplier.getContract().addTo_contract(supermarket_id, new_supplier_product);
        // adding the manufacturer to the supplier's list
        //supplier.addManufacturer(superProduct.getManufacturer());
        // adding the new object to the DataBase

        this.supplierProductDAO.add(new_supplier_product);
        }

    public void update_supplierProduct(String supermarket_id, int amount, double unit_price, String supplierID)
    {
        /**
         * Receives a supermarket id, a supplier, an amount and a unit price and update the last 2 fields of the existing supplier product
         */
        SupplierProduct supplier_product = supplierDAO.getSupplierBySupplierID(supplierID).getContract().getProducts().get(supermarket_id);
        supplier_product.setAmount(amount);
        supplier_product.setUnit_price(unit_price);
        this.supplierProductDAO.update(supplier_product);
    }

    public void update_supplierProduct(String supermarket_id, double amount_or_unit_price,char a_or_u, String supplierID)
    {
        /**
         * Receives a supermarket id, a supplier, an amount and a unit price and update one of the last 2 fields of the existing supplier product
         */
        Supplier supplier = supplierDAO.getSupplierBySupplierID(supplierID);
        SupplierProduct supplier_product = supplier.getContract().getProducts().get(supermarket_id);
        if (a_or_u=='a') // 'a' for amount, 'u' for unite_price
        {
            supplier_product.setAmount((int)amount_or_unit_price);
        }
        else
        {
            supplier_product.setUnit_price(amount_or_unit_price);
        }
        this.supplierProductDAO.update(supplier_product);
    }

    public void remove_supplierProduct(String supermarket_id,String supplier_id)
    {
        /**
         * Receives a supplier product and the stock of super products, and updates the supplying termination of the product
         */
        //SuperProduct superProduct = super_products.get(supplierProduct.getSupermarket_id());
        // removing supplier from super product's list of suppliers
        //superProduct.getSuppliers().remove(supplierProduct.getSupplier());
        // removing supplier product from the suppliers contract and from the supplier's quantity bill, if it exists in there
        //supplierProduct.getSupplier().getContract().removeFrom_contract_andQuantityBill(supplierProduct.getSupermarket_id());
        this.supplierProductDAO.delete(supermarket_id,supplier_id);
    }

    public void add_discount_to_quantitiesBill(String supermarket_id,int catalog_number, int amount, char type_q_or_m, int gift_or_percents,String supplierID)
    {
        /**
         * reveices a super product id, a catalog number, a parameter of the discount and the currnet supplier, and adds a new discount to the supplier's quantity bill
         */
        Supplier supplier = supplierDAO.getSupplierBySupplierID(supplierID);
        Discount discount = null;
        if (type_q_or_m=='q')
            discount = new QuantityDiscount(amount, catalog_number, gift_or_percents);
        else if (type_q_or_m=='m')
            discount = new MoneyDiscount(amount, catalog_number, gift_or_percents);
        Contract contract= supplier.getContract();
        contract.addTo_QuantitiesBill(supermarket_id,catalog_number,discount);
        this.contractDAO.addDiscountToQuantitiesBill(contract,discount,supplierID,supermarket_id);
    }

    public void remove_discount_from_quantitiesBill(Discount discount, String supplierID)
    {
        //reveices a discount and the relevant supplier that owns it, and removes the discount from the supplier's quantity bill
        Supplier supplier = supplierDAO.getSupplierBySupplierID(supplierID);
        supplier.getContract().removeFrom_quantitiesBill(discount);
        if(supplier.getContract().getQuantities_bill().get(discount.getCatalog_number()).size()==0){
            supplier.getContract().getQuantities_bill().remove(discount.getCatalog_number());
        }
        this.contractDAO.deleteDiscountFromQuantitiesBill(discount.getDiscount_id());
    }

    public void update_discount_from_quantitiesBill(int amount_giftAmount_or_percents,boolean regular_amount, Discount discount)
    {
        /**
         * receives a discount, a parameter to update and the wished change in it, and updates the relevant discount field accordingly
         */
        if (regular_amount) //the update is to the base-amount that qualifies for the discount
        {
            discount.setAmount(amount_giftAmount_or_percents);
        }
        else
        {
            if (discount instanceof QuantityDiscount) //the update is to the gift amount/ percentage of the discount
                ((QuantityDiscount) discount).setGift_amount(amount_giftAmount_or_percents);
            if (discount instanceof MoneyDiscount)
                ((MoneyDiscount) discount).setPercents((amount_giftAmount_or_percents));
        }
        this.discountDAO.update(discount);
    }

    public void CreatePeriodicOrder(String supplier_id, int catalog_number, int amount, Map<String,Supplier> super_suppliers, Map<String,SuperProduct> super_products)
    {
        if (super_suppliers.containsKey(supplier_id) && super_products.containsKey(catalog_number) && amount >0 )
        {
            Order order = super_suppliers.get(supplier_id).get_a_new_periodic_order_number();
            String order_id=order.getOrder_id();
            super_suppliers.get(supplier_id).AddProductPeriodicOrder(catalog_number,amount,order_id);
            this.orderDAO.addPeriodicOrder(order);
        }

    }

    public Map<String, Supplier> AllSuppliersMap()
    {
        return supplierDAO.getAllSuppliers();
    }

    public boolean doesSupplierExistByName(String name)
    {
        Map<String, Supplier> suppliers=AllSuppliersMap();
        for(Map.Entry<String,Supplier> entry : suppliers.entrySet())
        {
            if (entry.getValue().getSupplierName().equals(name))
                return true;
        }
        return false;
    }

    public boolean doesSupplierExistByID(String id)
    {
        Map<String, Supplier> suppliers=AllSuppliersMap();
        if (suppliers.containsKey(id))
            return true;
        return false;
    }

    public void addNotVisitingSupplier(String poc_names,String phone_numbers,String email,String  city, String street,int building_number, String phc_number, int bank_account_number, Card.Bill payment_condition,Day day,String name,List<String> domains)
    {
        Contact supplier_contact = new Contact(poc_names, phone_numbers, email, city, street, building_number);
        Card supplier_card = new Card(phc_number, bank_account_number, supplier_contact, payment_condition, day);
        Contract supplier_contract = new Contract();                        // find out what's the supplier's way of visiting
        Supplier supplier = new NotVisiting(name, domains, supplier_card, supplier_contract);
        supplierDAO.add(supplier);
    }
    public void addOrderlyVisitingSupplier(String poc_names,String phone_numbers,String email,String  city, String street,int building_number, String phc_number, int bank_account_number, Card.Bill payment_condition, Day day, int delay_days,String  name,List<String> domains)
    {
        Contact supplier_contact = new Contact(poc_names, phone_numbers, email, city, street, building_number);
        Card supplier_card = new Card(phc_number, bank_account_number, supplier_contact, payment_condition, day);
        Contract supplier_contract = new Contract(); // find out what's the supplier's way of visiting
        Supplier supplier=new OrderlyVisiting(delay_days, name, domains, supplier_card, supplier_contract);
        supplierDAO.add(supplier);
    }
    public void addPersistentVisitingSupplier(String poc_names,String phone_numbers,String email,String  city, String street,int building_number, String phc_number, int bank_account_number, Card.Bill payment_condition, Day day,String name,List<String>domains,List<Day> days)
    {
        Contact supplier_contact = new Contact(poc_names, phone_numbers, email, city, street, building_number);
        Card supplier_card = new Card(phc_number, bank_account_number, supplier_contact, payment_condition, day);
        Contract supplier_contract = new Contract();     // find out what's the supplier's way of visiting
        Supplier supplier=new PersistentVisiting(name, domains, supplier_card, supplier_contract, days);
        supplierDAO.add(supplier);
    }

    public Map<String, SupplierProduct> supplierProductsMap(String supplierID)
    {
        Map<String, SupplierProduct> supplierProductsMap = contractDAO.getSupplierContractBySupplierID(supplierID).getProducts();
        return supplierProductsMap;
    }

    public Supplier getSupplierByID(String supplierID)
    {
        return supplierDAO.getSupplierBySupplierID(supplierID);
    }

    public void UpdateSupplier(Supplier supplier)
    {

        supplierDAO.update(supplier);
    }

    public void removeSupplierProducts(String supplierID){
        supplierDAO.deleteSupplierProducts(supplierID);
    }

    public void removeAllSuppliers()
    {
        supplierDAO.removeAllSupplier();
    }
    public void removeAllSupplierProducts()
    {
        supplierProductDAO.removeAllSupplierProduct();
    }
    public void removeAllSupplierDiscounts()
    {
        discountDAO.removeAllDiscounts();
    }

    public SupplierProduct getSupplierProduct(String barcode, String supplierID){
        return supplierProductDAO.getSupplierProduct(barcode, supplierID);
    }


    public Map<String,List<String[]>> getSuppliersContracts()
    {
        Map<String,List<String[]>> contracts=new HashMap<>();
        Map<String,Supplier> suppliers= supplierDAO.getAllSuppliers();
        for(Map.Entry<String, Supplier> entry : suppliers.entrySet())
        {

            for(Map.Entry<String, SupplierProduct> entry1 : entry.getValue().getContract().getProducts().entrySet())
            {
                String [] values=new String[6];
                values[0]= entry.getKey();
                values[1]=manufacturerController.getAllSuperProducts().get(entry1.getKey()).getProduct_name();
                values[2]=manufacturerController.getAllSuperProducts().get(entry1.getKey()).getManufacturer().getManufacturer_name();
                values[3]=String.valueOf(entry1.getValue().getCatalog_number());
                values[4]=String.valueOf(entry1.getValue().getAmount());
                values[5]=String.valueOf(entry1.getValue().getUnit_price());
                if(contracts.containsKey(entry.getKey()))
                {
                    contracts.get(entry.getKey()).add(values);
                }
                else
                {
                    List<String[]> list=new ArrayList<>();
                    list.add(values);
                    contracts.put(entry.getKey(),list);
                }
            }
        }
        return contracts;
    }


    public void removeSupplier(String supplierID){
        supplierDAO.delete(supplierID);
    }

}
