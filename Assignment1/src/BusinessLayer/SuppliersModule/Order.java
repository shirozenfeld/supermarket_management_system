package BusinessLayer.SuppliersModule;

import java.util.*;
import java.time.LocalDate;
public class Order
{
    static int counter=0;
    //Supplier supplier;
    String supplierID;
    Map<java.lang.Integer,java.lang.Integer> products_list;
    Double first_price;
    Double finale_price;
    int total_amount;
    String order_id;
    LocalDate order_date;
    String type; // periodic/shortage
    String reportId;

    public Order(String type,String supplierID)
    {
        counter++;
        this.order_id=Integer.toString(counter);
        this.first_price=0.0;
        this.finale_price=0.0;
        this.products_list=new HashMap<java.lang.Integer,java.lang.Integer>();
        order_date=LocalDate.now();
        this.type=type;
        this.supplierID=supplierID;
    }
    public Order(String type,String supplierID,String reportID)
    {
        counter++;
        this.order_id=Integer.toString(counter);
        this.first_price=0.0;
        this.finale_price=0.0;
        this.products_list=new HashMap<java.lang.Integer,java.lang.Integer>();
        order_date=LocalDate.now();
        this.type=type;
        this.supplierID=supplierID;
        this.reportId=reportID;
    }

    public Order(String type,String supplierID, String order_id,String reportId)
    {
        this.order_id = order_id;
        this.first_price=0.0;
        this.finale_price=0.0;
        this.products_list=new HashMap<java.lang.Integer,java.lang.Integer>();
        order_date=LocalDate.now();
        this.type=type;
        this.supplierID=supplierID;
        this.reportId=reportId;
    }

    public String getSupplierID() {
        return this.supplierID;
    }
    public void setSupplier(String supplierID) {
        this.supplierID =supplierID;
    }

    public Map<java.lang.Integer,java.lang.Integer> getProducts() {
        return this.products_list;
    }

    public void setProducts(Map<java.lang.Integer,java.lang.Integer> products) {
        this.products_list = products;
    }
    public Double getFirst_price() {
        return first_price;
    }

    public void setFirst_price(Double first_price) {
        this.first_price = first_price;
    }

    public Double getFinale_price() {
        return finale_price;
    }

    public void setFinale_price(Double finale_price) {
        this.finale_price = finale_price;
    }
    public void addToShortageOrder(int catalog_number, int amount, double first_price, double finale_price)
    {
        this.products_list.put(catalog_number,amount);
        this.total_amount+=amount;
        this.first_price+=first_price;
        this.finale_price+=finale_price;
    }
    public void addToPeriodicOrder(int catalog_number, int amount)
    {
        this.products_list.put(catalog_number,amount);
        this.total_amount+=amount;
    }
    public int getTotal_amount() {
        return this.total_amount;
    }
    public String getOrder_id() {return this.order_id;}
    public LocalDate getOrder_date() {return this.order_date;}


    @Override
    public String toString() {
        String value =
                "supplier id= " + supplierID +
                "\norder_id= '" + order_id + '\'' +
                "\nfirst price= " + first_price +
                "\nfinale price (after discounts)= " + finale_price +
                "\norder's total amount= " + total_amount +
                "\n--- order's products list: --- ";
        for (Map.Entry<Integer, Integer> entry : this.products_list.entrySet())
        {
            value+="\ncatalog number: " + entry.getKey() + ",\tordered amount: " + entry.getValue();
        }
        return value;
    }
    public String getType()
    {
        return this.type;
    }
    public String getReportId()
    {
        return this.reportId;
    }
}
