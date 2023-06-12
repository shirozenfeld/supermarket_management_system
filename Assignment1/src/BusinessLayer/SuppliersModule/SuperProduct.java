package BusinessLayer.SuppliersModule;

import java.util.ArrayList;
import java.util.List;


public class SuperProduct {
    List<Supplier> suppliers;
    Manufacturer manufacturer;
    String product_name;
    String supermarket_id;


    //for creating a super product

    public SuperProduct(Manufacturer manufacturer, String product_name, String supermarket_id) {
        this.supermarket_id = supermarket_id;
        this.suppliers = new ArrayList<Supplier>();
        this.manufacturer = manufacturer;
        this.product_name=product_name;
    }


    public void setSupermarket_id(String supermarket_id) {
        this.supermarket_id = supermarket_id;
    }

    public String getSupermarket_id() {
        return supermarket_id;
    }

    public List<Supplier> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(List<Supplier> suppliers) {
        this.suppliers = suppliers;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getProduct_name() {
        return this.product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    @Override
    public String toString()
    {
        return "product name='" + product_name+ '\''+", manufacturer= " + manufacturer.getManufacturer_name();

    }
}

