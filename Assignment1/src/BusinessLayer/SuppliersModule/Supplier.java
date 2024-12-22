package BusinessLayer.SuppliersModule;

import java.util.*;
import java.util.Map;

public abstract class Supplier
{
    static int supplier_id_counter=0;
    int periodic_order_id__counter;
    String supplier_id;
    String name;
    List<String> domains;
    List<Manufacturer> manufacturers;
    List<Order> orders_list;
    Contract contract;
    Card card;
    Map<String,Order> periodicOrder_list;
    int shortage_order_id__counter;

    int supplier_catalog_numbers_counter;
    public Supplier(String name, List<String> domains, Card card,Contract contract)
    {
        //new from Menu
        this.supplier_id_counter++;
        this.name=name;
        this.domains=domains;
        this.card=card;
        this.supplier_id=Integer.toString(this.supplier_id_counter);
        this.manufacturers=new ArrayList<Manufacturer>();
        this.contract=contract;
        this.supplier_catalog_numbers_counter=0;
        this.periodicOrder_list=new HashMap<>();
        this.periodic_order_id__counter=0;
        this.shortage_order_id__counter=0;
    }
    public Supplier(String supplierID, String name, List<String> domains, Card card,Contract contract,int supplier_catalog_numbers_counter)
    {
        //existing from DB
        this.supplier_id=supplierID;
        this.name=name;
        this.domains=domains;
        this.card=card;
        this.manufacturers=new ArrayList<Manufacturer>();
        this.contract=contract;
        this.supplier_catalog_numbers_counter= supplier_catalog_numbers_counter;
        this.periodicOrder_list=new HashMap<>();

    }

    public String getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(String supplier_id) {
        this.supplier_id = supplier_id;
    }

    public List<String> getDomains() {
        return domains;
    }

    public void setDomains(List<String> domains) {
        this.domains = domains;
    }

    public List<Manufacturer> getManufacturers() {
        return manufacturers;
    }

    public void setManufacturers(List<Manufacturer> manufacturers) {
        this.manufacturers = manufacturers;
    }

    public void addManufacturer(Manufacturer manufacturers) {
        this.manufacturers.add(manufacturers);
    }

    public List<Order> getOrders_list() {
        return orders_list;
    }

    public Contract getContract() {
        return contract;
    }

    public Card getCard() {
        return card;
    }

    public void setOrders_list(List<Order> orders_list) {
        this.orders_list = orders_list;
    }

    public String getSupplierName()
    {
        return this.name;
    }

    public int get_a_new_supplierProduct_catalog_number()
    {
        this.supplier_catalog_numbers_counter++;
        return this.supplier_catalog_numbers_counter;
    }

    public Map<String, Order> getPeriodicOrder_list()
    {
        return periodicOrder_list;
    }


    public Order get_a_new_periodic_order_number(){
        this.periodic_order_id__counter++;
        Order new_order = new Order("Periodic",this.getSupplier_id());
        periodicOrder_list.put(Integer.toString(this.periodic_order_id__counter),new_order);
        return new_order;
    }


    public void AddProductPeriodicOrder(int catalog_number, int amount, String order_number)
    {
        if(!periodicOrder_list.get(order_number).getProducts().equals(catalog_number))
        {
            periodicOrder_list.get(order_number).getProducts().put(catalog_number,amount);
        }
    }

    public static void setSupplier_id_counter(int supplier_id_counter) {
        Supplier.supplier_id_counter = supplier_id_counter;
    }

    public void setPeriodic_order_id__counter(int periodic_order_id__counter) {
        this.periodic_order_id__counter = periodic_order_id__counter;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public void setPeriodicOrder_list(Map<String, Order> periodicOrder_list) {
        this.periodicOrder_list = periodicOrder_list;
    }

    public void setSupplier_catalog_numbers_counter(int supplier_catalog_numbers_counter) {
        this.supplier_catalog_numbers_counter = supplier_catalog_numbers_counter;
    }

    public abstract int get_minimial_supplying_time();

}
