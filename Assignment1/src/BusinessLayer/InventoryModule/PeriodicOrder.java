package BusinessLayer.InventoryModule;

import java.util.Formatter;
import java.util.Map;

public class PeriodicOrder {
    private int orderID;
    private Map<Integer, Integer> idAmountMap;

    public Map<Integer, Integer> getIdAmountMap() {
        return idAmountMap;
    }

    public PeriodicOrder(int orderID, Map<Integer, Integer> idAmountMap) {
        this.orderID = orderID;
        this.idAmountMap = idAmountMap;
    }

    public int getOrderID() {
        return orderID;
    }

    public boolean updateAmount(int itemID, int newAmount){
        if (!idAmountMap.containsKey(itemID))
            return false;
        idAmountMap.put(itemID, newAmount);
        return true;
    }

}
