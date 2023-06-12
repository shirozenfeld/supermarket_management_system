package BusinessLayer.InventoryModule;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class BasicItem {
    private String name;
    private int catalogNum;
    private String manufacturer;
    private double costPrice;
    private LinkedList<Category> itemCategories;
    private int IDcounter;

    public BasicItem(String name, int catalogNum, String manufacturer, double costPrice,LinkedList<Category> cats, int counter){
        this.name = name;
        this.catalogNum = catalogNum;
        this.manufacturer = manufacturer;
        this.costPrice = costPrice;
        this.itemCategories = cats;
        this.IDcounter = counter;
    }
    public void setCostPrice(double costPrice) {
        this.costPrice = costPrice;
    }

    public int getIDcounter(){
        return IDcounter;
    }

    public int updateIDcounter(){
        IDcounter++;
        return IDcounter;
    }


    public String getName() {
        return name;
    }

    public LinkedList<Category> getItemCategories() {
        return itemCategories;
    }

    public double getCostPrice(){
        return costPrice;
    }

    public int getCatalogNum() {
        return catalogNum;
    }

    public String getManufacturer() {
        return manufacturer;
    }

}
