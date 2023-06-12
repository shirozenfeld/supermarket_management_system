package BusinessLayer.InventoryModule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Category {
    private int cID;
    private String cName;
    //private Map<Integer, Category> subs;
    private List<InventoryItem> cItems;
    private Category mainCat;

    public Category(int cID, String cName, Category main) {
        this.cID = cID;
        this.cName = cName;
        this.cItems = new ArrayList<InventoryItem>();
        this.mainCat = main;
        //this.subs = new HashMap<>();
    }

    public Category getMainCat() {
        return mainCat;
    }

    /*public void addToSubs(int id, Category cat){
            subs.put(id, cat);
        }*/
    public List<InventoryItem> getcItems() {
        return cItems;
    }

    public void addToList(InventoryItem item){
        cItems.add(item);
    }

    public String getcName() {
        return cName;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return cID == category.cID;
    }

    public int getcID() {
        return cID;
    }

    public void setcItems(List<InventoryItem> cItems) {
        this.cItems = cItems;
    }
}