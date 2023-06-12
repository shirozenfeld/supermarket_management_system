package PresentationLayer.SuppliersModule.GUI;

import BusinessLayer.SuppliersModule.*;
import java.time.LocalDate;
import java.util.*;
import java.time.temporal.ChronoUnit;
import java.util.Timer;
import java.util.TimerTask;
import BusinessLayer.SuppliersModule.Day;
import DataAccessLayer.SuppliersModule.*;

public class UserController
{
    OrdersController ordersController;
    SuppliersController suppliersController;
    ManufacturerController manufacturerController;
    static UserController instance=null;
    private UserController()
    {
        this.ordersController= OrdersController.getInstance();
        this.suppliersController=SuppliersController.getInstance();
        this.manufacturerController=ManufacturerController.getInstance();
    }

    public static UserController getInstance()
    {
        if (instance == null) {
            instance = new UserController();
        }
        return instance;
    }
    public void PurchasorScreen()
    {
        //Map<String,Supplier> super_suppliers=suppliersController.AllSuppliers();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                ordersController.automatic_periodic_orders();
                //TODO: with every addition of a new supplier, retrieve the list again
            }
        }, 0, 24 * 60 * 60 * 1000); // execute the task every 24 hours, starting immediately
        //TODO: DELETE UNESSECARY
        //the 5 maps above store the data of the program
        Scanner scanner = new Scanner(System.in); //used for getting input from the user
        // the following objects are being reused along the program and are used temporarily each time for calculations
        // the following objects are being reused along the program and are used temporarily each time for calculations
        Contact supplier_contact;
        Card supplier_card;
        Contract supplier_contract;
        Order order;
        DeficienciesReport deficienciesReport;

        Supplier supplier = null;
        String name, phc_number, email, street, city, supplier_id, supermarket_id = null, Date, order_id, name_Super_Product;
        int choice = 0, expected_amount, building_number, delay_days, int_day, amount = 0, counter, catalogNumber = 0;
        List<String> domains;
        String phone_numbers = "", poc_names = "";
        Card.Bill payment_condition;
        boolean bool;
        Manufacturer manufacturer;
        List<Day> days;
        Day day;
        do {
            System.out.println("User Menu");
            System.out.println("1. Add Supplier");
            System.out.println("2. Remove Supplier");
            System.out.println("3. Update Supplier's Card");
            System.out.println("4. Update Supplier's Contract");
            System.out.println("5. Update Supplier's visiting day");
            System.out.println("6. Watch Supplier's next visiting day");
            System.out.println("7. Add Manufacturer");
            System.out.println("8. Handle Deficiencies Report");
            System.out.println("9. Watch Suppliers' Contracts Report");
            System.out.println("10. Add a periodic order");
            System.out.println("11. Exit");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();

            switch (choice)
            {
                case 1:
                    boolean does_exist = false;
                    //receive supplier name and check if exists
                    System.out.println("--- Add Supplier ---");
                    System.out.println("Insert supplier name");
                    name = scanner.next();
                        if (suppliersController.doesSupplierExistByName(name))
                        {
                            System.out.println("Supplier named " + name + " already exists.");
                            does_exist = true;
                            break;
                        }
                    if (does_exist)
                        break;
                    //receive supplier domains
                    domains = new ArrayList<String>();
                    System.out.println("in how many domains does the supplier work?");
                    expected_amount = scanner.nextInt();
                    for (int i = 1; i < expected_amount + 1; i++)
                    {
                        System.out.println("insert domain " + i + "");
                        domains.add(scanner.next());
                    }
                    //receive supplier contact details
                    System.out.println("insert POC name");
                    poc_names = scanner.next();
                    System.out.println("insert phone number");
                    phone_numbers = scanner.next();
                    System.out.println("insert email address");
                    email = scanner.next();
                    System.out.println("insert city (physical address)");
                    city = scanner.next();
                    System.out.println("insert street name (physical address)");
                    street = scanner.next();
                    System.out.println("insert building number (physical address)");
                    building_number = scanner.nextInt();
                    //receive supplier card details
                    System.out.println("insert phc number");
                    phc_number = scanner.next();
                    System.out.println("insert bank account number");
                    int bank_account_number = scanner.nextInt();
                    System.out.println("insert payment conditions:\n 1.shotef \n 2.shotef30 \n 3.shotef60");
                    payment_condition = Card.Bill.values()[scanner.nextInt() - 1];
                    System.out.println("What is the supplier's day of delivering periodic orders?");
                    day = Day.values()[scanner.nextInt() - 1];
                    System.out.println("What is supplier's way of visiting?\n 1.Orderly visiting \n 2.Persistenly visiting \n 3.Not visiting ");
                    choice = scanner.nextInt();
                    if (choice == 1)
                    {
                        System.out.println("insert the maximum delay days of arrival after an order was made");
                        delay_days = scanner.nextInt();
                        suppliersController.addOrderlyVisitingSupplier(poc_names, phone_numbers, email, city, street, building_number,phc_number, bank_account_number, payment_condition, day,delay_days, name, domains);
                    }
                    if (choice == 2)
                    {
                        System.out.println("how many visiting days does the supplier have?");
                        days = new ArrayList<Day>();
                        expected_amount = scanner.nextInt();
                        for (int i = 1; i < expected_amount + 1; i++)
                        {
                            System.out.println("insert day number " + i + " (ordinal)");
                            day = Day.values()[scanner.nextInt() - 1];
                            days.add(day);
                        }
                        suppliersController.addPersistentVisitingSupplier(poc_names, phone_numbers, email, city, street, building_number,phc_number, bank_account_number, payment_condition, day, name, domains, days);
                    }
                    if (choice == 3) {
                        suppliersController.addNotVisitingSupplier(poc_names, phone_numbers, email, city, street, building_number,phc_number, bank_account_number, payment_condition, day, name, domains);
                    }
                    //add supplier to the list
                    System.out.println("Successfuly done!");
                    break;


                case 2:
                    System.out.println("--- Remove Supplier ---");
                    System.out.println("Insert supplier id");
                    supplier_id = scanner.next();
                    if (SuppliersController.getInstance().doesSupplierExistByID(supplier_id)) //as long as the supplier exists
                    {
                        //remove all supplier products (including removal of suppliers from super-products' suppliers list)

                        for (Map.Entry<String, SupplierProduct> entry : suppliersController.supplierProductsMap(supplier_id).entrySet())
                            suppliersController.remove_supplierProduct(entry.getValue().getSupermarket_id(), supplier_id);
                        suppliersController.AllSuppliersMap().remove(supplier_id);
                        System.out.println("Supplier has been successfully removed");
                    } else //print an error message to the user
                        System.out.println("Supplier does not exist");
                    break;


                case 3:
                    System.out.println("--- Update Supplier's Card ---");
                    System.out.println("Insert supplier id");
                    supplier_id = scanner.next();
                    if (!(suppliersController.doesSupplierExistByID(supplier_id)))
                    {
                        supplier=suppliersController.getSupplierByID(supplier_id);
                        System.out.println("what would you want to update?\n 1.phc number\n 2.bank account number\n 3.contact details\n 4.payment condition");
                        choice = scanner.nextInt();
                        switch (choice) {
                            case 1:
                                System.out.println("insert a new phc number");
                                phc_number = scanner.next();
                                supplier.getCard().setPhc_number(phc_number);
                            case 2:
                                System.out.println("insert a new bank account number");
                                bank_account_number = scanner.nextInt();
                                supplier.getCard().setBank_account_number(bank_account_number);
                            case 3:
                                System.out.println("what would you want to change?\n 1.add a poc\n 2.remove a poc\n 3.add a phone number\n 4.remove a phone number\n 5.change email\n 6.change city \n 7.change street\n 8.change building number ");
                                choice = scanner.nextInt();
                                switch (choice) {
                                    case 1:
                                        System.out.println("insert a poc name");
                                        poc_names=scanner.next();
                                        supplier.getCard().getContact_details().setPoc_name(poc_names);
                                        break;
                                    case 2:
                                        System.out.println("insert a phone number");
                                        phone_numbers=scanner.next();
                                        supplier.getCard().getContact_details().setPhone_numbers(phone_numbers);
                                        break;
                                    case 3:
                                        System.out.println("insert a new email address");
                                        email = scanner.next();
                                        supplier.getCard().getContact_details().setEmail(email);
                                        break;
                                    case 4:
                                        System.out.println("insert a new city");
                                        city = scanner.next();
                                        supplier.getCard().getContact_details().setCity(city);
                                        break;
                                    case 5:
                                        System.out.println("insert a new street");
                                        street = scanner.next();
                                        supplier.getCard().getContact_details().setStreet(street);
                                        break;
                                    case 6:
                                        System.out.println("insert a new building number");
                                        building_number = scanner.nextInt();
                                        supplier.getCard().getContact_details().setBuilding_number(building_number);
                                        break;
                                }
                                break;
                            case 4:
                                System.out.println("insert payment conditions:\n 1.shotef \n 2.shotef30 \n 3.shotef60");
                                payment_condition = Card.Bill.values()[scanner.nextInt() - 1];
                                supplier.getCard().setPayment_condition(payment_condition);
                        }
                        suppliersController.UpdateSupplier(supplier);
                        System.out.println("Update done successfuly");
                    }
                    else
                        System.out.println("Supplier does not exist");
                    break;


                case 4:
                    System.out.println("--- Update Supplier's Contract ---");
                    System.out.println("Insert supplier id");
                    supplier_id = scanner.next();
                    if (suppliersController.doesSupplierExistByID(supplier_id)) {
                        Supplier supplier_ = suppliersController.AllSuppliersMap().get(supplier_id);
                        System.out.println("what would you want to update?\n 1.add supplier product\n 2.update supplier product\n 3.add discount\n 4.update discount");
                        choice = scanner.nextInt();
                        switch (choice) {
                            case 1:
                                System.out.println("--- Add Supplier Product ---");
                                // Get the supplier ID from the user
                                System.out.print("Enter supermarket id: ");
                                supermarket_id = scanner.next();
                                if (!manufacturerController.getAllSuperProducts().containsKey(supermarket_id)) {
                                    System.out.println("super_products " + supermarket_id + " does not exist.");
                                    break;
                                }
                                // Get the supplier product details from the user
                                System.out.print("Enter amount: ");
                                amount = Integer.parseInt(scanner.next());
                                System.out.print("Enter unit price: ");
                                double unitPrice = Double.parseDouble(scanner.next());
                                suppliersController.add_supplierProduct(supermarket_id, amount, unitPrice, supplier_id);
                                break;

                            case 2:
                                System.out.println("--- Update Supplier Product ---");
                                System.out.print("Enter supplier ID: ");
                                String supplierID = scanner.next();
                                if (!suppliersController.doesSupplierExistByID(supplier_id)) {
                                    System.out.println("Supplier with ID " + supplierID + " does not exist.");
                                    break;
                                }
                                if (!manufacturerController.getAllSuperProducts().containsKey(supermarket_id)) {
                                    System.out.println("super_products " + supermarket_id + " does not exist.");
                                    break;
                                }
                                supplier = suppliersController.AllSuppliersMap().get(supplierID);
                                System.out.println("what would you want to do?\n 1.update the amount\n 2. update the unit price\n 3. both");
                                choice = scanner.nextInt();
                                if (choice == 1) {
                                    System.out.print("Enter new amount: ");
                                    double new_amount = Integer.parseInt(scanner.next());
                                    suppliersController.update_supplierProduct(supermarket_id, new_amount, 'a', supplierID);
                                }
                                if (choice == 2) {
                                    System.out.print("Enter new unit price: ");
                                    double new_unitPrice = Double.parseDouble(scanner.next());
                                    suppliersController.update_supplierProduct(supermarket_id, new_unitPrice, 'u', supplierID);
                                }
                                if (choice == 3) {
                                    System.out.print("Enter new amount: ");
                                    int new_amount = Integer.parseInt(scanner.next());
                                    System.out.print("Enter new unit price: ");
                                    double new_unitPrice = Double.parseDouble(scanner.next());
                                    suppliersController.update_supplierProduct(supermarket_id, amount, new_unitPrice, supplierID);
                                }
                                System.out.println("Supplier product updated successfully");
                                break;
                            case 3:
                                System.out.println("--- Add discount to quantities bill ---");
                                System.out.println("Enter the supplier ID:");
                                supplier_id = scanner.next();
                                if (!suppliersController.AllSuppliersMap().containsKey(supplier_id)) {
                                    System.out.println("supplier named" + supplier_id + " does not exist.");
                                    break;
                                }
                                supplier = suppliersController.AllSuppliersMap().get(supplier_id);
                                System.out.println("Enter the supermarket ID:");
                                supermarket_id = scanner.next();
                                if (!manufacturerController.getAllSuperProducts().containsKey(supermarket_id)) {
                                    System.out.println("super_products " + supermarket_id + " does not exist.");
                                    break;
                                }
                                catalogNumber = supplier.getContract().getProducts().get(supermarket_id).getCatalog_number();
                                System.out.println("Enter the discount amount:");
                                amount = scanner.nextInt();
                                System.out.println("Enter the discount type (q for quantity or m for money):");
                                char discountType = scanner.next().charAt(0);
                                System.out.println("Enter the discount gift or percentage:");
                                int giftOrPercentage = scanner.nextInt();
                                suppliersController.add_discount_to_quantitiesBill(supermarket_id, catalogNumber, amount, discountType, giftOrPercentage, supplier_id);
                                break;

                            case 4:
                                System.out.println("Update discount");
                                System.out.println("Enter the supplier ID:");
                                supplier_id = scanner.next();
                                if (!suppliersController.AllSuppliersMap().containsKey(supplier_id)) {
                                    System.out.println("Supplier with ID " + supplier_id + " does not exist.");
                                    break;
                                }
                                supplier = suppliersController.AllSuppliersMap().get(supplier_id);
                                System.out.println("Enter the Discount id:");
                                String discountID = scanner.next();
                                List<Discount> discounts = supplier.getContract().getQuantities_bill().get(catalogNumber);
                                Discount discount = null;
                                does_exist = false;
                                for (int i = 0; i < discounts.size(); i++) {
                                    if (discounts.get(i).getDiscount_id().equals(discountID)) {
                                        discount = discounts.get(i);
                                        does_exist = true;
                                    }
                                }
                                if (does_exist) {
                                    System.out.println("discount with ID " + discountID + " does not exist for supplier with id " + supplier_id);
                                    break;
                                }
                                System.out.println("Enter the catalog number:");
                                catalogNumber = scanner.nextInt();
                                if (!supplier.getContract().getQuantities_bill().containsKey(catalogNumber)) {
                                    System.out.println("supplier product " + catalogNumber + " does not exist.");
                                    break;
                                }
                                System.out.println("what would you want to update?\n 1.Remove discount\n 2.update discount\n");
                                switch (choice) {
                                    case 1:
                                        suppliersController.remove_discount_from_quantitiesBill(discount, supplier.getSupplier_id());

                                    case 2:
                                        System.out.println("What would you want to do?\n 1. Update amount\n 2.update percents\n 3.update gift\n");
                                        switch (choice) {
                                            case 1:
                                                System.out.println("Insert the new amount:");
                                                amount = scanner.nextInt();
                                                suppliersController.update_discount_from_quantitiesBill(amount, true, discount);

                                            case 2:
                                                System.out.println("insert the new discount's percentage");
                                                amount = scanner.nextInt();
                                                suppliersController.update_discount_from_quantitiesBill(amount, false, discount);

                                            case 3:
                                                System.out.println("insert the new discount's gift amount");
                                                amount = scanner.nextInt();
                                                suppliersController.update_discount_from_quantitiesBill(amount, false, discount);
                                        }
                                }

                        }
                    }
                    //System.out.println("update had occurred successfully");
                    break;



                case 5:
                    System.out.println("--- Update Supplier's visiting day ---");
                    //relevant only for PersistentVisiting and OrderlyVisiting suppliers
                    System.out.println("Insert supplier id");
                    supplier_id = scanner.next();
                    if (suppliersController.doesSupplierExistByID(supplier_id)) {
                        supplier = suppliersController.getSupplierByID(supplier_id);
                        if (supplier instanceof OrderlyVisiting) {
                            System.out.println("insert the maximum delay days of arrival after an order was made");
                            ((OrderlyVisiting)supplier).setDelay_days(scanner.nextInt());
                            suppliersController.UpdateSupplier(supplier);
                        }
                        if (supplier instanceof PersistentVisiting) {
                            System.out.println("what would you want to do?\n 1.remove a day\n 2. add a day");
                            choice = scanner.nextInt();
                            if (choice == 2) {
                                System.out.println("insert day");
                                day = Day.values()[scanner.nextInt() - 1];
                                ((PersistentVisiting)supplier).addDay(day);
                            }
                            else
                            {
                                if (((PersistentVisiting)supplier).getDays().size() == 1)
                                    System.out.println("can't do that, there's only one day of visiting. first insert alternative and then remove the wished day");
                                else {
                                    System.out.println("insert day");
                                    day = Day.values()[scanner.nextInt() - 1];
                                    if (((PersistentVisiting)supplier).getDays().contains(day)) {
                                        ((PersistentVisiting)supplier).removeDay(day);
                                        System.out.println("update had occurred successfully");
                                    } else
                                        System.out.println("supplier never visits on this day");
                                }
                            }

                        }
                    }
                    else
                        suppliersController.UpdateSupplier(supplier);
                        System.out.println("Supplier does not exist");
                    break;

                case 6:
                    System.out.println("--- Watch Supplier's next visiting day ---");
                    //relevant only for PersistentVisiting and OrderlyVisiting suppliers
                    System.out.println("Insert supplier id");
                    supplier_id = scanner.next();
                    if (suppliersController.doesSupplierExistByID(supplier_id)) {
                        Date currentDate = new Date();
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(currentDate);
                        int today = calendar.get(Calendar.DAY_OF_WEEK);
                        supplier=suppliersController.getSupplierByID(supplier_id);
                        if (supplier instanceof OrderlyVisiting) {
                            if (((OrderlyVisiting)supplier).getLatest_order() != null)
                            {
                                LocalDate order_date = ((OrderlyVisiting)supplier).getLatest_order().getOrder_date();
                                LocalDate now = LocalDate.now();
                                if (ChronoUnit.DAYS.between(order_date, now) > ((OrderlyVisiting)supplier).getDelay_days())
                                    System.out.println("no order to visit upon");
                                else
                                    System.out.println("supplier will visit within " + (((OrderlyVisiting)supplier).getDelay_days() - (int) ChronoUnit.DAYS.between(order_date, now)) + " days");
                            }
                            else
                                System.out.println("no order to visit upon");
                        } else {
                            days = ((PersistentVisiting)supplier).getDays();
                            Arrays.sort(days.toArray());
                            for (int i = 0; i < days.size(); i++) {
                                int_day = (days.get(i)).ordinal() + 1;
                                if (today < int_day) {
                                    System.out.println("Supplier will visit in " + (int_day - today) + " days");
                                    break;
                                }
                                if (today == int_day)
                                    System.out.println("Supplier will visit today");
                            }
                            if (days.get(days.size() - 1).ordinal() + 1 < today)
                                System.out.println("Supplier will visit in" + (7 - today + days.get(0).ordinal() + 1) + " days");
                        }
                    } else
                        System.out.println("Supplier does not exist");
                    break;

                case 7:
                    System.out.println("--- Add Manufacturer ---");
                    System.out.println("insert manufacturer name");
                    name = scanner.next();
                    if (manufacturerController.doesManufacturerExist(name))
                    {
                        //check if it already exists
                        System.out.println("manufacturer named " + name + " already exists");
                        break;
                    }
                    else
                    {
                        manufacturerController.AddManufacturer(name);
                        System.out.println("manufacturer was added successfully");
                    }
                    break;

                case 8:
                    System.out.println("--- Handle Deficiencies Report ---");
                    deficienciesReport = ordersController.getDailyShortageReport();
                    ordersController.deficiencies_handler(deficienciesReport);
                    System.out.println("*** Deficiencies Report *** ");
                    System.out.println("Deficiencies Report's status: " + deficienciesReport.getReport_status());
                    //show deficiencies report products and amounts
                    for (Map.Entry<SuperProduct, java.lang.Integer> entry : deficienciesReport.getProducts().entrySet())
                    {
                        //TODO: CHECK RETRIEVAL OF SUPERPRODUCT
                        SuperProduct superProduct = entry.getKey();
                        amount = entry.getValue();
                        System.out.println(superProduct + "\t| amount: " + amount);
                    }
                    System.out.println("\n*** Orders Under Report *** ");
                    // has already been handled - show ordres details
                    int i = 1;
                    for (Map.Entry<String, Order> entry : deficienciesReport.getOrders().entrySet()) {
                        if (i == 1)
                            System.out.println("Order " + i + ": ");
                        else
                            System.out.println("\nOrder " + i + ": ");
                        order = entry.getValue();
                        System.out.println(order);
                        System.out.println();
                        i++;
                    }
                    break;

                case 9:
                    System.out.println("--- Watch Suppliers' Contracts Report ---");
                     i = 1;
                    for (Map.Entry<String, Supplier> entry : suppliersController.AllSuppliersMap().entrySet()) {
                        supplier = entry.getValue();
                        System.out.println("Supplier number " + i + ": " + supplier.getSupplierName());
                        Map<String, SupplierProduct> supplier_products = suppliersController.supplierProductsMap(supplier.getSupplier_id());
                        for (Map.Entry<String, SupplierProduct> entry1 : supplier_products.entrySet()) {
                            SupplierProduct supplierProduct = entry1.getValue();
                            SuperProduct superProduct1 = manufacturerController.getAllSuperProducts().get(entry1.getKey());
                            System.out.println(superProduct1 + " | " + supplierProduct);
                        }
                        i++;

                    }
                    break;

                case 10:
                    System.out.println("--- Add a periodic order ---");
                    System.out.println("insert supplier id");
                    supplier_id = scanner.next();
                    counter = 1;
                    HashMap<Integer,Integer> order_products=new HashMap<>();
                    while (true)
                    {
                        System.out.println("Choose an option:\n 1. continue to adding the next product.\n 2. stop");
                        choice = scanner.nextInt();
                        if (choice == 1)
                        {
                            System.out.println("Product number " + counter + ":");
                            System.out.println("insert product's catalog number (supplier product's id");
                            catalogNumber = scanner.nextInt();
                            System.out.println("insert amount :");
                            amount = scanner.nextInt();
                            order_products.put(catalogNumber,amount);
                        }
                        else
                        {
                            break;
                        }
                    }
                    Order order1=new Order("Periodic",supplier_id);
                    order1.setProducts(order_products);
                    ordersController.addPeriodicOrder(order1);
                case 11:
                    System.out.println("EXIT");
                    Database.getDataBaseInstance().closeConn();
                    break;

                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 11.");
                    break;
                    }
                }
        while (choice != 12);
                scanner.close();
    }


}
