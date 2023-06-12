package BusinessLayer.InventoryModule;

import java.util.HashMap;
import java.util.Map;

public class Branch {
    private int branchID;
    private String branchName;
    private Map<Integer, Category> categories;
    private Map<Integer, InventoryItem> inventoryItems;
    private Map<Integer, Item> storeInventory;
    private Map<Integer, Item> warehouseInventory;
    private Map<Integer, Item> damagedItems;
    private Map<Integer, InventoryItem> shortageItems;

    public Branch(int branchID, String branchName) {
        this.branchID = branchID;
        this.branchName = branchName;
        this.categories = new HashMap<>();
        this.inventoryItems = new HashMap<>();
        this.storeInventory = new HashMap<>();
        this.warehouseInventory = new HashMap<>();
        this.damagedItems = new HashMap<>();
        this.shortageItems = new HashMap<>();
    }
    public void setCategories(Map<Integer, Category> categories) {
        this.categories = categories;
    }

    public void setInventoryItems(Map<Integer, InventoryItem> inventoryItems) {
        this.inventoryItems = inventoryItems;
    }

    public void setStoreInventory(Map<Integer, Item> storeInventory) {
        this.storeInventory = storeInventory;
    }

    public void setWarehouseInventory(Map<Integer, Item> warehouseInventory) {
        this.warehouseInventory = warehouseInventory;
    }

    public void setDamagedItems(Map<Integer, Item> damagedItems) {
        this.damagedItems = damagedItems;
    }

    public void setShortageItems(Map<Integer, InventoryItem> shortageItems) {
        this.shortageItems = shortageItems;
    }
    public int getBranchID() {
        return branchID;
    }

    public String getBranchName() {
        return branchName;
    }

    public Category categoryIsInStore(int categoryID){
        return categories.get(categoryID);
    }
    public void addCategory(int catalogNum, Category add){
        categories.put(catalogNum, add);
    }
    public Item itemIsInStore(int itemID){ return storeInventory.get(itemID); }
    public void addToStoreInventory(int itemID, Item item){
        this.storeInventory.put(itemID, item);
    }
    public void removeStoreInventory(Item item){ this.storeInventory.remove(item.getID()); }
    public Item itemIsInWareHouse(int itemID){
        return warehouseInventory.get(itemID);
    }
    public void addToWarehouseInventory(int itemID, Item item){
        this.warehouseInventory.put(itemID, item);
    }
    public void removeWarehouseInventory(Item item){ this.warehouseInventory.remove(item.getID()); }
    public InventoryItem findInventoryInStore(int catalogNumber){ return inventoryItems.get(catalogNumber); }
    public Map<Integer, InventoryItem> getInventoryItems() {
        return inventoryItems;
    }
    public void addInventoryItem(int catalogNum, InventoryItem add){ inventoryItems.put(catalogNum, add); }
    public Map<Integer, Item> getDamagedItems() {
        return damagedItems;
    }
    public void addToDamagedItems(int itemID, Item item){ this.damagedItems.put(itemID, item); }
    public void removeDamagedItems(Item item){ this.damagedItems.remove(item.getID()); }
    public InventoryItem isInShortage(int catalogNumber){
        return shortageItems.get(catalogNumber);
    }
    public Map<Integer, InventoryItem> getShortageItems() {
        return shortageItems;
    }
    public void addToShortageItems(int catalogNum, InventoryItem item){ this.shortageItems.put(catalogNum, item); }
    public void removeShortageItems(InventoryItem item){ this.shortageItems.remove(item.getCatalogNum()); }
    public Map<Integer, Item> getStoreInventory() { return storeInventory; }
    public Map<Integer, Item> getWarehouseInventory() { return warehouseInventory; }
}
