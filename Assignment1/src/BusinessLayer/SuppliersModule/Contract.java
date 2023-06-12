package BusinessLayer.SuppliersModule;

import java.util.*;
public class Contract
{
    Map<String,SupplierProduct> products; // barcoode, supplier product
    Map<java.lang.Integer,List<Discount>> quantities_bill; // catalogNumber, discount list


    public Contract()
    {
        {
            this.products = new HashMap<String,SupplierProduct>();
            this.quantities_bill = new HashMap<java.lang.Integer,List<Discount>>();

        }
    }

    public Map<String, SupplierProduct> getProducts() {
        return products;
    }

    public void setProducts(Map<String, SupplierProduct> products) {
        this.products = products;
    }

    public Map<Integer, List<Discount>> getQuantities_bill() {
        return quantities_bill;
    }

    public void setQuantities_bill(Map<Integer, List<Discount>> quantities_bill) {
        this.quantities_bill = quantities_bill;
    }


    public void addTo_contract(String supermarket_id, SupplierProduct supplierProduct)
    {
        this.products.put(supermarket_id,supplierProduct);
    }
    public void addTo_QuantitiesBill(String supermarket_id,int catalog_number, Discount discount)
    {
        if (this.products.containsKey(supermarket_id))
        {
            if (this.quantities_bill.containsKey(catalog_number))
                this.quantities_bill.get(catalog_number).add(discount);
            else
            {
                List<Discount> discounts = new ArrayList<Discount>();
                this.quantities_bill.put(catalog_number,discounts);
                this.quantities_bill.get(catalog_number).add(discount);
            }

        }
    }
    public void removeFrom_contract_andQuantityBill(String supermarket_id)
    {
        if (this.products.containsKey(supermarket_id)) {
            int catalog_number = this.products.get(supermarket_id).getCatalog_number();
            this.quantities_bill.remove(catalog_number);
            this.products.remove(supermarket_id);
        }
    }
    public void removeFrom_quantitiesBill(Discount discount)
    {
        int catalog_number = discount.getCatalog_number();
        List<Discount> product_discounts = this.quantities_bill.get(catalog_number);
        product_discounts.remove(discount);
    }


}
