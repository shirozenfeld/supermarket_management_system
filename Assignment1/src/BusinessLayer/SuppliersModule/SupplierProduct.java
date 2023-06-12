package BusinessLayer.SuppliersModule;

public class SupplierProduct
{
    int catalog_number;
    int amount;
    double unit_price;

    String supplier_id;

    String supermarket_id;
    public SupplierProduct(String supermarket_id, int amount, double unit_price, String supplier_id)
    {
        this.supermarket_id = supermarket_id;
        this.amount = amount;
        this.unit_price = unit_price;
        this.supplier_id=supplier_id;
    }

    public SupplierProduct(String supermarket_id, int catalog_number, int amount, double unit_price, String supplier_id)
    {
        this.supermarket_id = supermarket_id;
        this.catalog_number = catalog_number;
        this.amount = amount;
        this.unit_price = unit_price;
        this.supplier_id=supplier_id;
    }
    public int getCatalog_number() {
        return catalog_number;
    }

    public void setCatalog_number(int catalog_number) {
        this.catalog_number = catalog_number;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(double unit_price) {
        this.unit_price = unit_price;
    }

    public String getSupplierID() {
        return this.supplier_id;
    }

    public String getSupermarket_id() {
        return supermarket_id;
    }

    public void setSupermarket_id(String supermarket_id) {
        this.supermarket_id = supermarket_id;
    }

    public java.lang.String toString() {
        return "BusinessLayer.SupplierProduct{" +
                "catalog_number=" + catalog_number +
                ", amount=" + amount +
                ", unit_price=" + unit_price +
                '}';
    }
    public void setSupplierID(String supplierid){this.supplier_id=supplierid;}
}
