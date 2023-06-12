package BusinessLayer.InventoryModule;
import DataAccessLayer.InventoryModule.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class InventoryManagment {
    private BasicItemDAO biDAO;
    private BranchDAO branchDAO;
    private CategoryDAO categoryDAO;
    private DiscountDAO discountDAO;
    private InventoryItemDAO inviDAO;
    private ItemDAO iDAO;
    private PeriodicOrderDAO poDAO;
    private SuperLiDAO slDAO;
    private SuperLi superli;
    private Branch branch;
    static InventoryManagment instance=null;
    private InventoryManagment() {
        biDAO = BasicItemDAO.getInstance();
        branchDAO = BranchDAO.getInstance();
        categoryDAO = CategoryDAO.getInstance();
        discountDAO = DiscountDAO.getInstance();
        inviDAO = InventoryItemDAO.getInstance();
        iDAO = ItemDAO.getInstance();
        poDAO = PeriodicOrderDAO.getInstance();
        slDAO = SuperLiDAO.getInstance();
    }

    public static InventoryManagment getInstance()
    {
        if (instance == null) {
            instance = new InventoryManagment();
        }
        return instance;
    }
    public void restoreAll(){
        SuperLi cstore = new SuperLi();
        cstore.setBasicItems(slDAO.getAll());
        ArrayList<Object> branches = branchDAO.getAll();
        for (int i = 0; i<branches.size(); i++){
            Branch branch = (Branch)branches.get(i);
            cstore.addBranch(branch.getBranchID(), branch);
        }
        this.superli = cstore;
    }
    public void initializeSuper() {
        biDAO.removeAll();
        branchDAO.removeAll();
        categoryDAO.removeAll();
        discountDAO.removeAll();
        inviDAO.removeAll();
        iDAO.removeAll();
        SuperLi cstore = new SuperLi();
        Branch branch1 = new Branch(1, "Super-Li Beer-Sheva");
        cstore.addBranch(1, branch1);
        branchDAO.add(branch1);
        Branch branch2 = new Branch(2, "Super-Li Tel-Aviv");
        cstore.addBranch(2, branch2);
        branchDAO.add(branch2);
        Category c11 = new Category(111, "Dairy", null);
        Category c12 = new Category(121, "Milk", c11);
        Category c13 = new Category(131, "Liter Milk", c12);
        categoryDAO.add(c11);
        categoryDAO.add(c12);
        categoryDAO.add(c13);
        branch1.addCategory(111, c11);
        branch1.addCategory(121, c12);
        branch1.addCategory(131, c13);
        LinkedList<Category> cats1 = new LinkedList<Category>();
        cats1.add(c11);
        cats1.add(c12);
        cats1.add(c13);
        BasicItem b1 = new BasicItem("Liter milk 3% Tnuva", 11, "Tnuva", 4, cats1, 0);
        biDAO.add(b1);
        Category c21 = new Category(211, "Beverages", null);
        Category c22 = new Category(221, "Alcohol", c21);
        Category c23 = new Category(231, "Wine", c22);
        Category c24 = new Category(241, "Wine 750 ml", c23);
        categoryDAO.add(c21);
        categoryDAO.add(c22);
        categoryDAO.add(c23);
        categoryDAO.add(c24);
        branch1.addCategory(21, c21);
        branch1.addCategory(22, c22);
        branch1.addCategory(23, c23);
        branch1.addCategory(24, c24);
        Category c25 = new Category(251, "Liter Wine", c23);
        categoryDAO.add(c25);
        branch1.addCategory(25, c25);
        LinkedList<Category> cats2 = new LinkedList<Category>();
        cats2.add(c21);
        cats2.add(c22);
        cats2.add(c23);
        cats2.add(c24);
        LinkedList<Category> cats3 = new LinkedList<Category>();
        cats3.add(c21);
        cats3.add(c22);
        cats3.add(c23);
        cats3.add(c25);
        BasicItem b2 = new BasicItem("Carmel white wine", 25, "Carmel", 14, cats2, 0);
        biDAO.add(b2);
        BasicItem b3 = new BasicItem("Tavor white wine", 26, "Tavor", 14, cats3, 0);
        biDAO.add(b3);
        cstore.addBasicItem(11, b1);
        cstore.addBasicItem(25, b2);
        cstore.addBasicItem(26, b3);
        InventoryItem i1 = new InventoryItem(11, 2, 6.2, 1);
        inviDAO.add(i1);
        i1.addToSellPriceHistory(i1.getSellPrice());
        c11.addToList(i1);
        c12.addToList(i1);
        c13.addToList(i1);
        InventoryItem i2 = new InventoryItem(25, 0, 29.9, 1);
        inviDAO.add(i2);
        i2.addToSellPriceHistory(i2.getSellPrice());
        InventoryItem i3 = new InventoryItem(26, 1, 24.9, 1);
        inviDAO.add(i3);
        i3.addToSellPriceHistory(i3.getSellPrice());
        c21.addToList(i2);
        c22.addToList(i2);
        c23.addToList(i2);
        c24.addToList(i2);
        c21.addToList(i3);
        c22.addToList(i3);
        c23.addToList(i3);
        c25.addToList(i3);
        branch1.addInventoryItem(11, i1);
        branch1.addInventoryItem(25, i2);
        branch1.addInventoryItem(26, i3);
        Item it1 = new Item(b1.getName(), b1.getCatalogNum(), b1.getManufacturer(), b1.getCostPrice(), b1.getItemCategories(),
                i1.getSellPrice(), 0, (b1.getCatalogNum() * 1000 + b1.updateIDcounter()) * 10 + branch1.getBranchID(), LocalDate.of(2023, 05, 13), ProductIntegrity.Null,
                "null", Location.store, b1.getIDcounter());
        iDAO.add(it1);
        biDAO.update(b1);
        branch1.addToStoreInventory(it1.getID(), it1);
        i1.addToStore();
        branch1.addToShortageItems(11, i1);
        branchDAO.addToShortage(11, (int) (i1.getMinimumAmount() * 1.5), b1.getManufacturer());
        Item it2 = new Item(b2.getName(), b2.getCatalogNum(), b2.getManufacturer(), b2.getCostPrice(), b2.getItemCategories(),
                i2.getSellPrice(), 0, (b2.getCatalogNum() * 1000 + b2.updateIDcounter()) * 10 + branch1.getBranchID(), LocalDate.of(2028, 12, 28), ProductIntegrity.Null,
                "null", Location.store, b2.getIDcounter());
        iDAO.add(it2);
        biDAO.update(b2);
        branch1.addToStoreInventory(it2.getID(), it2);
        i2.addToStore();
        Item it3 = new Item(b3.getName(), b3.getCatalogNum(), b3.getManufacturer(), b3.getCostPrice(), b3.getItemCategories(),
                i3.getSellPrice(), 0, (b3.getCatalogNum() * 1000 + b3.updateIDcounter()) * 10 + branch1.getBranchID(), LocalDate.of(2026, 06, 8), ProductIntegrity.Null,
                "null", Location.warehouse, b3.getIDcounter());
        iDAO.add(it3);
        biDAO.update(b3);
        branch1.addToWarehouseInventory(it3.getID(), it3);
        i3.addToWH();
        branch1.addToShortageItems(26, i3);
        branchDAO.addToShortage(26, (int) (i3.getMinimumAmount() * 1.5), b3.getManufacturer());
        this.superli = cstore;
    }
    public Formatter printPeriodic(int orderID){
        PeriodicOrder pOrder = (PeriodicOrder)poDAO.getById(orderID);
        superli.addPeriodicOrder(orderID, pOrder);
        Formatter fmt = new Formatter();
        fmt.format("%15s %25s %15s\n", "Catalog Number","Description", "Amount");
        pOrder.getIdAmountMap().forEach((itemCN, amount) -> {
            BasicItem bi = superli.basicIsInStore(itemCN);
            fmt.format("%15s %25s %15s \n", itemCN, bi.getName(),amount);
        });
        return fmt;
    }
    public boolean validPOrderUpdate(int orderID){
        if(PeriodicOrderDAO.getInstance().getById(orderID) == null)
            return false;
        return true;
    }
    public void editPeriodicOrder(int orderID, Map<Integer, Integer> idAmountMap){
        PeriodicOrder pOrder = (PeriodicOrder)poDAO.getById(orderID);
        if(pOrder!= null) {
            idAmountMap.forEach((itemCN, amount) -> {
                pOrder.updateAmount(itemCN, amount);
            });
            superli.addPeriodicOrder(orderID, pOrder);
            poDAO.update(pOrder);
        }
    }
    public boolean branchInSuper(int bID){
        Branch branch = (Branch) branchDAO.getById(bID);
        if (branch != null){
            superli.addBranch(bID, branch);
            this.branch = branch;
            return true;
        }
        return false;
    }
    public Branch getBranch(){
        return this.branch;
    }
    public boolean basicExist(int itemCN){
        BasicItem bi = (BasicItem) biDAO.getById(itemCN);
        if(bi != null) {
            superli.addBasicItem(itemCN, bi);
            return true;
        }
        return false;
    }
    public boolean itemExist(int itemID){
        Item item = (Item) iDAO.getById(itemID);
        if(item != null){
            if(item.getLocationInBranch() == Location.store)
                branch.addToStoreInventory(itemID, item);
            else
                branch.getWarehouseInventory();
            return true;
        }
        return false;
    }
    public boolean inviExist(int itemCN){
        InventoryItem invi = (InventoryItem) inviDAO.getById(itemCN);
        if(invi != null) {
            branch.addInventoryItem(itemCN, invi);
            return true;
        }
        return false;
    }
    public void addItemIfInNotExist(int itemCN, int ima, double isprice){
        BasicItem bi = (BasicItem) biDAO.getById(itemCN);
        InventoryItem invi = new InventoryItem(itemCN, ima, isprice, branch.getBranchID());
        invi.addToSellPriceHistory(isprice);
        inviDAO.add(invi);
        for (int i = 0; i < bi.getItemCategories().size(); i++) {
            bi.getItemCategories().get(i).addToList(invi);
        }
        branch.addInventoryItem(itemCN, invi);
    }
    public ArrayList<Integer> addItemIfExist(int howMany, int itemCN, LocalDate expired, String damageT, String loc){
        ArrayList<Integer> toReturn = new ArrayList<>();
        BasicItem bi = (BasicItem) biDAO.getById(itemCN);
        superli.addBasicItem(itemCN, bi);
        InventoryItem invi = (InventoryItem) inviDAO.getById(itemCN);
        Location location = null;
        if (Objects.equals(loc, "S")) {
            location = Location.store;
        }
        else if (Objects.equals(loc, "W")) {
            location = Location.warehouse;
        }
        double discount;
        if (invi.getCurrDiscount() == null) {
            discount = 100;
        } else {
            discount = invi.getCurrDiscount().getDiscountRate();
        }
        int firstID = 0;
        for (int i = 0; i<howMany;i++) {
            ProductIntegrity damaged;
            if(!Objects.equals(damageT, "null"))
                damaged = ProductIntegrity.Damaged;
            else
                damaged = ProductIntegrity.Null;
            Item newI = new Item(bi.getName(), itemCN, bi.getManufacturer(), bi.getCostPrice(), bi.getItemCategories(),
                    invi.getSellPrice(), discount, ((bi.getCatalogNum() * 1000) + bi.updateIDcounter())*10 + branch.getBranchID() , expired, damaged, damageT, location, bi.getIDcounter());
            iDAO.add(newI);
            biDAO.update(bi);
            if (Objects.equals(loc, "S")) {
                invi.addToStore();
                branch.addToStoreInventory(newI.getID(), newI);
            } else if (Objects.equals(loc, "W")) {
                invi.addToWH();
                branch.addToWarehouseInventory(newI.getID(), newI);
            }

            if (!Objects.equals(damageT, "null")) {
                branch.addToDamagedItems(newI.getID(), newI);
            }
            if (branch.isInShortage(itemCN) != null) {
                if (invi.getTotalAmount() > invi.getMinimumAmount()) {
                    branch.removeShortageItems(invi);
                }
            }
            else {
                if (invi.getTotalAmount() <= invi.getMinimumAmount()){
                    branch.addToShortageItems(itemCN, invi);
                    branchDAO.addToShortage(itemCN, (int)(invi.getMinimumAmount()*1.5), bi.getManufacturer());
                }

            }
            if(i == 0){
                toReturn.add(newI.getID());
            }
            if(i == howMany-1) {
                toReturn.add(newI.getID());
            }
        }
        return toReturn;
    }
    public void addItemIfBNotExist(int itemCN, String iname, String imanu, int icprice, Map<Integer, String> catsID){
        LinkedList<Category> catList = new LinkedList<>();
        final Category[] main = {null};
        catsID.forEach((catNum, c) ->{
            Category cat = (Category) categoryDAO.getById(catNum*10+branch.getBranchID());
            if (cat == null) {
                cat = new Category(catNum*10+branch.getBranchID(), c, main[0]);
                categoryDAO.add(cat);
            }
            branch.addCategory(catNum*10+branch.getBranchID(), cat);
            catList.add(cat);
            main[0] = cat;
        });
        BasicItem bi = new BasicItem(iname, itemCN, imanu, icprice, catList, 0);
        biDAO.add(bi);
        superli.addBasicItem(itemCN, bi);
    }
    public ArrayList<Integer> deleteItem(int itemID) {
        InventoryItem invi = null;
        Item myItem = null;
        myItem = branch.itemIsInStore(itemID);
        ArrayList<Integer> toReturn = new ArrayList<>();
        invi = branch.findInventoryInStore(itemID / 10000);
        //check if in store
        if (myItem == null) {
            myItem = branch.itemIsInWareHouse(itemID);
        }
        //check if in warehouse, if not return null
        if (myItem == null) {
            return toReturn;
        }
        branch.removeDamagedItems(myItem);
        int ID = myItem.getID();
        if (branch.itemIsInStore(ID) != null) {
            branch.removeStoreInventory(myItem);
            invi.removeFromStore();
        } else {
            branch.removeWarehouseInventory(myItem);
            invi.removeFromWH();
        }
        iDAO.remove(itemID);
        int minimumAmount = invi.getMinimumAmount();
        int totalAmount = invi.getTotalAmount();
        if (minimumAmount >= totalAmount) {
            if (branch.isInShortage(ID) == null) {
                branch.addToShortageItems(ID, invi);
                branchDAO.addToShortage(itemID/10000, (int)(invi.getMinimumAmount()*1.5), myItem.getManufacturer());
            }
            toReturn.add(minimumAmount);
            toReturn.add(totalAmount);
        }
        else{
            toReturn.add(-1);
        }
        return toReturn;
    }
    public void editCost(int itemID, double newCost){
        BasicItem myBItem = superli.basicIsInStore(itemID/10000);
        myBItem.setCostPrice(newCost);
        biDAO.update(myBItem);
    }
    public void editSellPrice(int itemID, double newPrice){
        InventoryItem myInItem = branch.findInventoryInStore(itemID/10000);
        myInItem.setSellPrice(newPrice);
        myInItem.addToSellPriceHistory(newPrice);
        inviDAO.update(myInItem);
    }
    public void editPIntegrity(int itemID, String type, String dType, double newD){
        Item myItem = branch.itemIsInStore(itemID);
        if(myItem == null)
            myItem = branch.itemIsInWareHouse(itemID);
        InventoryItem myInItem = branch.findInventoryInStore(itemID/10000);
        if(Objects.equals(type, "D"))
            myItem.setDamage(ProductIntegrity.Damaged);
        else
            myItem.setDamage(ProductIntegrity.Expired);
        myItem.setDamageType(dType);
        branch.addToDamagedItems(itemID, myItem);
        myItem.setAfterDiscountPrice(myInItem.getSellPrice(), newD);
        iDAO.update(myItem);
    }
    public void editLocation(int itemID, String location){
        Item myItem = branch.itemIsInStore(itemID);
        if(myItem == null)
            myItem = branch.itemIsInWareHouse(itemID);
        InventoryItem myInItem = branch.findInventoryInStore(itemID/10000);
        if (Objects.equals(location, "S")) {
            if (myItem.getLocationInBranch() == Location.warehouse) {
                myInItem.addToStore();
                myInItem.removeFromWH();
                branch.removeWarehouseInventory(myItem);
                branch.addToStoreInventory(itemID, myItem);
            }
            myItem.setLocationInBranch(Location.store);
        }
        else {
            if(myItem.getLocationInBranch() == Location.store) {
                myInItem.addToWH();
                myInItem.removeFromStore();
                branch.removeStoreInventory(myItem);
                branch.addToWarehouseInventory(itemID, myItem);
            }
            myItem.setLocationInBranch(Location.warehouse);
        }
        iDAO.update(myItem);
    }
    public void editMinAmount(int itemID, int newMin){
        InventoryItem myInItem = branch.findInventoryInStore(itemID/10000);
        BasicItem mybItem = superli.basicIsInStore(itemID/10000);
        myInItem.setMinimumAmount(newMin);
        inviDAO.update(myInItem);
        if (branch.isInShortage(itemID/10000) != null) {
            if (myInItem.getTotalAmount() > myInItem.getMinimumAmount()) {
                branch.removeShortageItems(myInItem);
            }
        }
        else{
            if (myInItem.getTotalAmount() <= myInItem.getMinimumAmount()) {
                branch.addToShortageItems(myInItem.getCatalogNum(), myInItem);
                branchDAO.addToShortage(itemID/10000, (int)(myInItem.getMinimumAmount()*1.5), mybItem.getManufacturer());
            }
        }
    }
    public void editDiscount(int itemID, LocalDate startd, LocalDate endd, double discountR) {
        InventoryItem myInItem = branch.findInventoryInStore(itemID / 10000);
        Discount newD = new Discount(startd, endd, discountR);
        discountDAO.add(newD);
        if (myInItem.getCurrDiscount() == null || myInItem.getCurrDiscount().getDiscountRate() < discountR) {
            if (startd.compareTo(LocalDate.now()) <= 0 && endd.compareTo(LocalDate.now()) >= 0) {
                myInItem.setCurrDiscount(newD);
                myInItem.addToDiscountHistory(newD);
                inviDAO.update(myInItem);
                int finalItemCN = itemID / 10000;
                InventoryItem finalMyInItem = myInItem;
                branch.getStoreInventory().forEach((id, item) -> {
                    int icn = id / 10000;
                    if (icn == finalItemCN) {
                        item.setAfterDiscountPrice(finalMyInItem.getSellPrice(), discountR);
                        iDAO.update(item);
                    }
                });
                branch.getWarehouseInventory().forEach((id, item) -> {
                    int icn = id / 10000;
                    if (icn == finalItemCN) {
                        item.setAfterDiscountPrice(finalMyInItem.getSellPrice(), discountR);
                        iDAO.update(item);
                    }
                });
            }
        }
    }
    public Formatter getPriceHistory(int cn, int choice){
        InventoryItem invi = (InventoryItem) inviDAO.getById(cn);
        if (invi == null){
            return null;
        }
        Formatter fmt = new Formatter();
        switch (choice) {
            case 1:
                fmt.format("%25s %15s \n", "Date", "Sell Price");
                invi.getSellPriceHistory().forEach((date, price) -> {
                    fmt.format("%25s %15s\n", date, price);});
                return fmt;
            case 2:
                fmt.format("%15s %15s %15s\n", "Start Date", "End Date", "Discount Rate");
                for(int i=0; i<invi.getDiscountHistory().size();i++){
                    Discount now = invi.getDiscountHistory().get(i);
                    fmt.format("%15s %15s %15s\n", now.getStartDate(), now.getEndDate(), now.getDiscountRate());
                }
                return fmt;
            default:
                return fmt.format("");
        }
    }
    public boolean setCountedAmount(int cn, int cs, int cw){
        InventoryItem invi = branch.findInventoryInStore(cn);
        if (invi == null) {
            return false;
        }
        invi.setCountedInStore(cs);
        invi.setCountedInWareHouse(cw);
        invi.setCountedAmount(cs+cw);
        inviDAO.update(invi);
        return true;
    }
    public boolean addDiscountByCategory(int cID, double discountRate, LocalDate start, LocalDate end){
        Category cat = branch.categoryIsInStore(cID);
        if(cat == null)
            return false;
        Discount discount = new Discount(start, end, discountRate);
        discountDAO.add(discount);
        for(int i=0; i<cat.getcItems().size(); i++){
            InventoryItem invi = cat.getcItems().get(i);
            if(invi.getCurrDiscount() == null || invi.getCurrDiscount().getDiscountRate() < discountRate) {
                if (start.compareTo(LocalDate.now()) <= 0 && end.compareTo(LocalDate.now()) >= 0) {
                    invi.setCurrDiscount(discount);
                    invi.addToDiscountHistory(discount);
                    inviDAO.update(invi);
                    int itemCN = invi.getCatalogNum();
                    branch.getStoreInventory().forEach((id, item) -> {
                        int icn = id / 10000;
                        if (icn == itemCN) {
                            item.setAfterDiscountPrice(invi.getSellPrice(), discountRate);
                            iDAO.update(item);
                        }
                    });
                    branch.getWarehouseInventory().forEach((id, item) -> {
                        int icn = id / 10000;
                        if (icn == itemCN) {
                            item.setAfterDiscountPrice(invi.getSellPrice(), discountRate);
                            iDAO.update(item);
                        }
                    });
                }
            }
        }
        return true;
    }
}