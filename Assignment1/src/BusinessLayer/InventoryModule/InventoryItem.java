package BusinessLayer.InventoryModule;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryItem {
    private int BranchID;
    private int catalogNum;
    private int minimumAmount;
    private int amountInStore;
    private int countedInStore;
    private int countedInWareHouse;
    private int amountInWareHouse;
    private int countedAmount;
    private int totalAmount;
    private double sellPrice;
    private Map<String, Double> sellPriceHistory;
    private Discount currDiscount;
    private List<Discount> discountHistory;

    public InventoryItem(int catalogNum, int minimumAmount, double sellPrice, int BranchID) {
        this.catalogNum = catalogNum;
        this.minimumAmount = minimumAmount;
        this.amountInStore = 0;
        this.amountInWareHouse = 0;
        this.countedInStore = 0;
        this.countedInWareHouse = 0;
        this.countedAmount = 0;
        this.totalAmount = 0;
        this.sellPrice = sellPrice;
        this.sellPriceHistory = new HashMap<>();
        this.discountHistory = new ArrayList<Discount>();
        this.currDiscount= null;
        this.BranchID = BranchID;
    }

    public int getBranchID() {
        return BranchID;
    }

    public int getAmountInStore() {
        return amountInStore;
    }

    public int getCatalogNum() {
        return catalogNum;
    }

    public int getAmountInWareHouse() {
        return amountInWareHouse;
    }
    public double getSellPrice() {
        return sellPrice;
    }
    public void setCurrDiscount(Discount currDiscount) {
        this.currDiscount = currDiscount;
    }
    public Discount getCurrDiscount() {
        return currDiscount;
    }
    public void addToSellPriceHistory(double newP){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatDateTime = now.format(formatter);
        this.sellPriceHistory.put(formatDateTime, newP);
    }
    public void addToStore() {
        this.amountInStore++;
        this.totalAmount++;
    }

    public void addToWH() {
        this.amountInWareHouse++;
        this.totalAmount++;
    }
    public int getCountedAmount(){return countedAmount;}
    public void setCountedAmount(int countedAmount){this.countedAmount = countedAmount;}
    public void setMinimumAmount(int minimumAmount) {
        this.minimumAmount = minimumAmount;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public void removeFromStore() {
        this.amountInStore--;
        this.totalAmount--;
    }

    public void removeFromWH() {
        this.amountInWareHouse--;
        this.totalAmount--;
    }

    public int getMinimumAmount() {
        return minimumAmount;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setCountedInStore(int countedInStore) {
        this.countedInStore = countedInStore;
    }

    public void setCountedInWareHouse(int countedInWareHouse) {
        this.countedInWareHouse = countedInWareHouse;
    }

    public int getCountedInStore() {
        return countedInStore;
    }

    public int getCountedInWareHouse() {
        return countedInWareHouse;
    }

    public void addToDiscountHistory(Discount dis){
        this.discountHistory.add(dis);
    }

    public Map<String, Double> getSellPriceHistory() {
        return sellPriceHistory;
    }

    public List<Discount> getDiscountHistory() {
        return discountHistory;
    }

    public void setSellPriceHistory(Map<String, Double> sellPriceHistory) {
        this.sellPriceHistory = sellPriceHistory;
    }

    public void setDiscountHistory(List<Discount> discountHistory) {
        this.discountHistory = discountHistory;
    }

    public void setAmountInStore(int amountInStore) {
        this.amountInStore = amountInStore;
    }

    public void setAmountInWareHouse(int amountInWareHouse) {
        this.amountInWareHouse = amountInWareHouse;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }
}