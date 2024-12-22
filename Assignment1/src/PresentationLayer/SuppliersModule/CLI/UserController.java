package PresentationLayer.SuppliersModule.CLI;

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
            System.out.println("2. Update Supplier's Contract");
            System.out.println("3. Add Manufacturer");
            System.out.println("4. Handle Deficiencies Report");
            System.out.println("5. Watch Suppliers' Contracts Report");
            System.out.println("6. Add a periodic order");
            System.out.println("7. Watch History Orders");
            System.out.println("8. Exit");
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
                    System.out.println("--- Update Supplier's Contract ---");
                    System.out.println("Insert supplier id");
                    supplier_id = scanner.next();
                    if (suppliersController.doesSupplierExistByID(supplier_id)) {
                        Supplier supplier_ = suppliersController.AllSuppliersMap().get(supplier_id);
                        System.out.println("what would you want to update?\n 1.add supplier product\n 2.add discount\n 3.update exsiting supplier product");
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

                            case 3:
                                System.out.println("--- Update Supplier Product ---");
                                System.out.print("Enter supermarket ID: ");
                                supermarket_id = scanner.next();
                                if (!manufacturerController.getAllSuperProducts().containsKey(supermarket_id)) {
                                    System.out.println("super_products " + supermarket_id + " does not exist.");
                                    break;
                                }
                                supplier = suppliersController.AllSuppliersMap().get(supplier_id);
                                System.out.println("what would you want to do?\n 1.update the amount\n 2. update the unit price\n 3. both");
                                choice = scanner.nextInt();
                                if (choice == 1) {
                                    System.out.print("Enter new amount: ");
                                    double new_amount = Integer.parseInt(scanner.next());
                                    suppliersController.update_supplierProduct(supermarket_id, new_amount, 'a', supplier_id);
                                }
                                if (choice == 2) {
                                    System.out.print("Enter new unit price: ");
                                    double new_unitPrice = Double.parseDouble(scanner.next());
                                    suppliersController.update_supplierProduct(supermarket_id, new_unitPrice, 'u', supplier_id);
                                }
                                if (choice == 3) {
                                    System.out.print("Enter new amount: ");
                                    int new_amount = Integer.parseInt(scanner.next());
                                    System.out.print("Enter new unit price: ");
                                    double new_unitPrice = Double.parseDouble(scanner.next());
                                    suppliersController.update_supplierProduct(supermarket_id, amount, new_unitPrice, supplier_id);
                                }
                                System.out.println("Supplier product updated successfully");
                                break;

                        }
                    }
                    //System.out.println("update had occurred successfully");
                    break;
                case 3:
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

                case 4:
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

                case 5:
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

                case 6:
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
                    ordersController.addPeriodicOrder("Periodic",supplier_id,order_products);

                case 7:
                    System.out.println("--- Watch History Orders ---");
                    System.out.println("insert supplier id");
                    supplier_id = scanner.next();
                    i = 1;
                    deficienciesReport = ordersController.getDailyShortageReport();
                    ordersController.deficiencies_handler(deficienciesReport);
                    for (Map.Entry<String, Order> entry : deficienciesReport.getOrders().entrySet()) {

                        order = entry.getValue();
                        if(Objects.equals(order.getSupplierID(), supplier_id)){
                            System.out.println(order);
                            System.out.println();
                            i++;
                        }
                    }
                    break;


                case 8:
                    System.out.println("EXIT");
                    Database.getDataBaseInstance().closeConn();
                    break;

                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 11.");
                    break;
                    }
                }
        while (choice != 8);
                scanner.close();
    }


}
