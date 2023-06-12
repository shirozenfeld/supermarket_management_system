package BusinessLayer.SuppliersModule;

import java.util.*;
public class Manufacturer
{

    String manufacturer_name;
    List<SuperProduct> products;

    public Manufacturer(String manufacturer_name)
    {
        {
            this.manufacturer_name = manufacturer_name;
            this.products = new ArrayList<SuperProduct>();
        }
    }
    public String getManufacturer_name() {
        return manufacturer_name;
    }

    public void setManufacturer_name(String manufacturer_name) {
        this.manufacturer_name = manufacturer_name;
    }

    public List<SuperProduct> getProducts() {
        return products;
    }

    public void addProduct(SuperProduct product)
    {
        this.products.add(product);
    }

    public void setProducts(List<SuperProduct> products) {
        this.products = products;
    }
}
