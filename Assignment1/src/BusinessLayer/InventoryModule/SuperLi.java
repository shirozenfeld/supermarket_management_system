package BusinessLayer.InventoryModule;

import DataAccessLayer.InventoryModule.*;

import java.util.HashMap;
import java.util.Map;

public class SuperLi {
    private Map<Integer, Branch> branchList;
    private Map<Integer, BasicItem> BasicItems;
    private Map<Integer, PeriodicOrder> periodicOrders;

    public SuperLi() {
        this.branchList = new HashMap<>();
        this.BasicItems = new HashMap<>();
        this.periodicOrders = new HashMap<>();
    }

    public void addBranch(int branchID, Branch branch){
        branchList.put(branchID, branch);
    }
    public Branch branchInSuper(int branchID){
        return branchList.get(branchID);
    }
    public PeriodicOrder periodicOrderInSuper(int orderID){
        return periodicOrders.get(orderID);
    }

    public BasicItem basicIsInStore(int catalogNumber){
        return BasicItems.get(catalogNumber);
    }

    public void addBasicItem(int catalogNum, BasicItem add){
        BasicItems.put(catalogNum, add);
    }
    public void addPeriodicOrder(int orderID, PeriodicOrder add){
        periodicOrders.put(orderID, add);
    }

    public void setBasicItems(Map<Integer, BasicItem> basicItems) {
        BasicItems = basicItems;
    }
}
