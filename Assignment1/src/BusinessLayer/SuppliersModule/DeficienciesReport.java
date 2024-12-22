package BusinessLayer.SuppliersModule;

import java.util.HashMap;
import java.util.Map;

public class DeficienciesReport
{
    Map<String, Order> orders;
    String report_id;
    static int report_counter;
    Map<SuperProduct,java.lang.Integer> products;
    Status report_status;

    Map<String, Integer> productsList = new HashMap<>();

    public DeficienciesReport()
    {
            report_counter++;
            this.products=new HashMap<SuperProduct,java.lang.Integer>();
            this.report_id = Integer.toString(report_counter);
            this.orders=new HashMap<String, Order>();
            this.report_status=Status.in_process;

    }

    public Map<SuperProduct,java.lang.Integer> getProducts() {
        return products;
    }
    public void addProductToReport(SuperProduct superProduct,int amount)
    {
        this.products.put(superProduct,amount);
    }
    public void removeProductFromReport(SuperProduct superProduct) {this.products.remove(superProduct);}
    public Map<String, Order> getOrders(){
       return orders;
    }

    public String getReport_id(){return this.report_id;}

    public void update_reportStatus()
    {
        this.report_status=Status.completed;
    }

    public Status getReport_status(){return this.report_status;}

    public enum Status{in_process,completed}

    public void setOrders(Map<String, Order> orders) {
        this.orders = orders;
    }

    public void setReport_id(String report_id) {
        this.report_id = report_id;
    }

    public void setProducts(Map<SuperProduct, Integer> products) {
        this.products = products;
    }

    public void setReport_status(Status report_status) {
        this.report_status = report_status;
    }

    public Map<String, Integer> getProductsList() {
        return productsList;
    }

    public void setProductsList(Map<String, Integer> productsList) {
        this.productsList = productsList;
    }
}
