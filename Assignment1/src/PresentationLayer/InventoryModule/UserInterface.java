package PresentationLayer.InventoryModule;

import BusinessLayer.InventoryModule.InventoryManagment;
import java.time.LocalDate;
import java.util.*;
import BusinessLayer.InventoryModule.ReportMaker;
import BusinessLayer.SuppliersModule.ManufacturerController;
import BusinessLayer.SuppliersModule.OrdersController;
import BusinessLayer.SuppliersModule.SuppliersController;
import DataAccessLayer.SuppliersModule.Database;
import PresentationLayer.SuppliersModule.UserController;

import static java.lang.Integer.parseInt;
public class UserInterface {
    static UserInterface instance=null;
    private UserInterface()
    {
        //add controller SingleTone
    }

    public static UserInterface getInstance()
    {
        if (instance == null) {
            instance = new UserInterface();
        }
        return instance;
    }
    public static int askInt(String ques){
        Scanner scan = new Scanner(System.in);
        int ans;
        while (true){
            System.out.println(ques);
            if (scan.hasNextInt()){
                ans = scan.nextInt();
                if(ans < 0){
                    System.out.println("Enter only positive numbers");
                    continue;
                }
                break;
            }
            else {
                System.out.println("Must contain only Integers!");
                scan.nextLine();
            }
        }
        return ans;
    }
    public static double askDouble(String ques){
        Database.getDataBaseInstance().closeConn();
        Scanner scan = new Scanner(System.in);
        double ans;
        while (true){
            System.out.println(ques);
            if (scan.hasNextDouble()){
                ans = scan.nextDouble();
                if(ans < 0){
                    System.out.println("Enter only positive numbers");
                    continue;
                }
                break;
            }
            else {
                System.out.println("Must contain only Integers and floating point numbers!");
                scan.nextLine();
            }
        }
        return ans;
    }


    public void mainInventory() {
//        Database.getDataBaseInstance().
        Scanner scan = new Scanner(System.in);
        System.out.println("Welcome To Super-Li!");
        InventoryManagment imanage = InventoryManagment.getInstance();
        //imanage.restoreAll();
        //imanage.initializeSuper();
        System.out.println("Please enter branch ID");
        int branchID = scan.nextInt();
        boolean exist = imanage.branchInSuper(branchID);
        while (!exist){
            System.out.println("Branch doesn't exist, please try again");
            branchID = scan.nextInt();
            exist = imanage.branchInSuper(branchID);
        }
        ReportMaker rMaker = new ReportMaker(imanage.getBranch());
        String choice = "";
        while (choice != "8") {
            System.out.println("What would you like to do?");
            System.out.println("1. Add item");
            System.out.println("2. Delete item");
            System.out.println("3. Edit item");
            System.out.println("4. Generate a report");
            System.out.println("5. Show sell price history or discount history");
            System.out.println("6. Manually set counted amount");
            System.out.println("7. Add discount by category");
            System.out.println("8. Update periodic order");
            System.out.println("9. Exit");
            choice = scan.next();
            switch (choice) {
                case "1":
                    int itemCN = askInt("What is the item Catalog Number?");
                    if(!imanage.basicExist(itemCN)) {
                        System.out.println("Please enter Basic item's name");
                        String iname = scan.next();
                        System.out.println("Please enter item's manufacturer");
                        String imanu = scan.next();
                        int icprice = askInt("Please enter item's cost price");
                        System.out.println("Add categories' IDs by order, from main to secondary");
                        boolean addMore = true;
                        Map<Integer, String> catList = new HashMap<Integer, String>();
                        while (addMore) {
                            int catNum = askInt("Please enter category's ID");
                            System.out.println("What is the Category's name?");
                            String c = scan.next();
                            catList.put(catNum, c);
                            System.out.println("Do you want to add another category? for yes press Y, else N");
                            String ans = scan.next();
                            c = scan.nextLine();
                            if (Objects.equals(ans, "Y")) {
                                addMore = true;
                            }
                            else{
                                addMore = false;
                            }
                        }
                        imanage.addItemIfBNotExist(itemCN, iname, imanu, icprice, catList);
                    }
                    if(!imanage.inviExist(itemCN)) {
                        int ima = askInt("Please enter item's minimum amount");
                        int isprice = askInt("Please enter item's sell price");
                        imanage.addItemIfInNotExist(itemCN, ima, isprice);
                    }
                    System.out.println("Please enter item's expiry date (YYYY-MM-DD), for item with no expiry date type null");
                    String date = scan.next();
                    LocalDate expired;
                    if (Objects.equals(date, "null"))
                    {
                        expired = null;
                    }
                    else
                    {
                        expired = LocalDate.parse(date);
                    }
                    System.out.println("What is the item's damage? if item has no damage type null");
                    String damageT = scan.next();
                    System.out.println("Where is the item stored? for store press S, for warehouse press W");
                    String loc = scan.next();
                    int howMany = askInt("How many items with those characteristics and same location in branch do you want to add?");
                    ArrayList<Integer> returned = imanage.addItemIfExist(howMany, itemCN, expired, damageT, loc);
                    System.out.println("Items successfully added!");
                    System.out.println("Catalog number: " + itemCN);
                    if(howMany > 1)
                        System.out.println("IDs: " + returned.get(0)+ "-" + returned.get(1));
                    else
                        System.out.println("ID: " + returned.get(0));
                    break;
                case "2":
                    ArrayList<Integer> isInStore = new ArrayList<>();
                    int itemID = askInt("What is the item ID?");
                    isInStore = imanage.deleteItem(itemID);
                    while (isInStore.isEmpty()) {
                        itemID = askInt("Item wasn't found, try again");
                        isInStore = imanage.deleteItem(itemID);
                    }
                    System.out.println("Item successfully deleted!");
                    if (isInStore.size() > 1) {
                        System.out.println("NOTICE! item with catalog number " + itemID + " has reached it's minimum amount");
                        System.out.println("Minimum amount: " + isInStore.get(0));
                        System.out.println("Current amount: " + isInStore.get(1));
                    }
                    break;
                case "3":
                    int itemId = askInt("What is the item ID?");
                    boolean flag = imanage.itemExist(itemId);
                    while (!flag){
                        itemId = askInt("What is the item ID?");
                        flag = imanage.itemExist(itemId);
                    }
                    int catalogN = itemId/10000;
                    String ch= "";
                    while (!Objects.equals(ch, "7")) {
                        System.out.println("What would you like to edit?");
                        System.out.println("1. Cost price");
                        System.out.println("2. Sell price");
                        System.out.println("3. Product integrity");
                        System.out.println("4. Location in branch");
                        System.out.println("5. Minimum amount");
                        System.out.println("6. Set discount");
                        System.out.println("7. Exit");
                        ch = scan.next();
                        switch (ch) {
                            case "1":
                                double newCP = askDouble("What is the new cost price?");
                                imanage.editCost(itemId, newCP);
                                System.out.println("Cost price changed successfully!");
                                break;
                            case "2":
                                double newSP = askDouble("What is the new sell price?");
                                imanage.editSellPrice(itemId, newSP);
                                System.out.println("Sell price changed successfully!");
                                break;
                            case "3":
                                System.out.println("What is the item's product integrity? press D for damaged or E for expired");
                                String type = scan.next();
                                System.out.println("What is the damage type?");
                                String dType = scan.next();
                                double newD = askDouble("What is the discount rate? (for example - for 70% press 70)");
                                imanage.editPIntegrity(itemId, type, dType, newD);
                                System.out.println("Product integrity changed successfully!");
                                break;
                            case "4":
                                System.out.println("Where do you want to Locate the item? for store press S for warehouse press W");
                                String location = scan.next();
                                imanage.editLocation(itemId, location);
                                if (Objects.equals(location, "S")){
                                    System.out.println("Item Location has changed to Store!");
                                }
                                else{
                                    System.out.println("Item Location has changed to WareHouse!");
                                }
                                break;
                            case "5":
                                int minimum = UserInterface.askInt("Please enter the demand per day");
                                int supply = UserInterface.askInt("Please enter the supply time");
                                int newMin = minimum * supply;
                                imanage.editMinAmount(itemId, newMin);
                                System.out.println("Minimum amount has changed successfully!");
                                break;
                            case "6":
                                System.out.println("Please enter start date (YYYY-MM-DD)");
                                String starts = scan.next();
                                LocalDate startd = LocalDate.parse(starts);
                                System.out.println("Please enter end date (YYYY-MM-DD)");
                                String ends = scan.next();
                                LocalDate endd = LocalDate.parse(ends);
                                double discountR = askDouble("Please enter discount rate (for example - for 70% press 70)");
                                imanage.editDiscount(itemId, startd, endd, discountR);
                                System.out.println("Discount successfully added!");
                                break;
                            case "7": break;
                            default:
                                System.out.println("This option doesn't exist, please try again");
                        }
                    }
                    break;
                case "4":
                    System.out.println("Which report would you like to generate?");
                    System.out.println("1. Inventory Report");
                    System.out.println("2. Shortage report");
                    System.out.println("3. Damaged and Expired Report");
                    ch = scan.next();
                    System.out.println("Do you want to filter the report by categories? for yes press Y, else N");
                    String ans;
                    ans = scan.next();
                    ArrayList<Integer> cats = new ArrayList<Integer>();
                    if (Objects.equals(ans, "N")) {
                        Formatter fmt = rMaker.generateReport(parseInt(ch));
                        if (fmt == null)
                            System.out.println("This option doesn't exist");
                        else
                            System.out.println(fmt);
                        break;
                    } else if (Objects.equals(ans, "Y")) {
                        boolean more = true;
                        while (more) {
                            int cID = UserInterface.askInt("Please enter Category's ID");
                            cats.add(cID);
                            System.out.println("Do you want to add another category? for yes press Y, else N");
                            String ansM = scan.next();
                            if (Objects.equals(ansM, "Y")) {
                                more = true;
                            }
                            else {
                                more = false;
                            }
                        }
                        Formatter fmt = rMaker.generateReportByCategory(parseInt(ch), cats);
                        if (fmt == null)
                            System.out.println("This option doesn't exist");
                        else if(fmt.toString().equals(""))
                            System.out.println("One or more categories don't exist");
                        else
                            System.out.println(fmt);
                    } else {
                        System.out.println("Not a valid answer");
                    }
                    break;
                case "5":
                    int cn = askInt("Please enter Catalog Number");
                    System.out.println("Which history would you like to watch?");
                    System.out.println("1. Sell History");
                    System.out.println("2. Discount Rate History");
                    ch = scan.next();
                    Formatter fmt = imanage.getPriceHistory(cn, parseInt(ch));
                    if (fmt == null)
                        System.out.println("Item doesn't exist");
                    else if(fmt.toString().equals(""))
                        System.out.println("This option doesn't exist");
                    else
                        System.out.println(fmt);
                    break;
                case "6":
                    boolean more = true;
                    while (more) {
                        cn = askInt("Please enter Catalog Number");
                        int cs = askInt("Please enter counted amount in store");
                        int cw = askInt("Please enter counted amount in ware house");
                        if (imanage.setCountedAmount(cn,cs,cw))
                            System.out.println("Counted amount successfully updated");
                        else {
                            System.out.println("Item wasn't fount, please try again");
                            continue;
                        }
                        System.out.println("Do you want to set amount for another item? for yes press Y, else N");
                        String ansM = scan.next();
                        if (Objects.equals(ansM, "Y")) {
                            more = true;
                        }
                        else {
                            more = false;
                        }
                    }
                    break;
                case "7":
                    int cID = UserInterface.askInt("Please enter Category ID");
                    double discountRate = UserInterface.askDouble("Please enter discount rate");
                    System.out.println("Please enter start date (YYYY-MM-DD)");
                    String startDate = scan.next();
                    LocalDate start = LocalDate.parse(startDate);
                    System.out.println("Please enter end date (YYYY-MM-DD)");
                    String endDate = scan.next();
                    LocalDate end = LocalDate.parse(endDate);
                    if (!imanage.addDiscountByCategory(cID,discountRate,start,end))
                        System.out.println("Category doesn't exist in this branch");
                    else
                        System.out.println("Discount successfully added!");
                    break;
                case "8":
                    int orderid = askInt("Please enter order's ID");
                    if(!imanage.validPOrderUpdate(orderid))
                        System.out.println("This order can not be changed");
                    else {
                        System.out.println(imanage.printPeriodic(orderid));
                        Map<Integer, Integer> idAmountMap = new HashMap<Integer, Integer>();
                        while (true) {
                            int itemid = askInt("Please enter item's ID");
                            int amount = askInt("What is the new amount?");
                            idAmountMap.put(itemid, amount);
                            System.out.println("Would you like to update another amount? for yes press Y, else N");
                            if (Objects.equals(scan.next(), "N"))
                                break;
                        }
                        imanage.editPeriodicOrder(orderid, idAmountMap);
                    }
                    break;
                case "9":
                    Database.getDataBaseInstance().closeConn();
                    return;
                default:
                    System.out.println("This option doesn't exist, please try again");
            }
        }
    }
}