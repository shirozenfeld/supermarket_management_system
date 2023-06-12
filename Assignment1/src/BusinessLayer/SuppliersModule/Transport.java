package BusinessLayer.SuppliersModule;

import java.util.List;
public class Transport
{
    String supplier_id;
    List<Order> orders;
    public  Transport(List<Order> orders)
    {
        if (orders!=null)
            this.orders=orders;
    }

    public String getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(String supplier_id) {
        this.supplier_id = supplier_id;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
