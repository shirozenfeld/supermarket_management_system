package BusinessLayer.SuppliersModule;

import java.util.*;

public class OrderlyVisiting extends Supplier
{
    int delay_days;
    Order latest_order;
    public OrderlyVisiting(int delay_days,String name, List<String> domains, Card card,Contract contract)
    {
        //new from menu
        super(name,  domains, card, contract);
        this.delay_days=delay_days;
    }
    public OrderlyVisiting(int delay_days,String name, List<String> domains, Card card,Contract contract,String SupplierID,int catalogNumber)
    {
        super(SupplierID, name, domains, card, contract,catalogNumber);
        this.delay_days=delay_days;
    }

    public int getDelay_days() {
        return delay_days;
    }

    public void setDelay_days(int delay_days) {
        this.delay_days = delay_days;
    }

    public Order getLatest_order() {
        return latest_order;
    }

    public void setLatest_order(Order latest_order) {
        this.latest_order = latest_order;
    }

    public int get_minimial_supplying_time()
    {
        return this.delay_days;
    }
}