package BusinessLayer.InventoryModule;

import java.time.LocalDate;
import java.util.LinkedList;

public class Item extends BasicItem {
    private int ID;
    private int catalogNum;
    private double afterDiscountPrice;
    private LocalDate expireDate;
    private ProductIntegrity Damage;
    private String damageType;
    private Location locationInBranch;

    public Item(String name, int catalogNum, String manufacturer, double costPrice, LinkedList<Category> catList, double sellPrice, double discountRate, int ID, LocalDate expireDate, ProductIntegrity damage, String damageType, Location locationInBranch, int counter) {
        super(name, catalogNum, manufacturer, costPrice, catList, counter);
        this.ID = ID;
        this.afterDiscountPrice = sellPrice * (100-discountRate) / 100;
        this.expireDate = expireDate;
        this.Damage = damage;
        this.damageType = damageType;
        this.locationInBranch = locationInBranch;
    }

    public void setAfterDiscountPrice(double sellP, double discountRate) {
        this.afterDiscountPrice = sellP * (100-discountRate) / 100;
    }

    public void setExpireDate(LocalDate expireDate) {
        this.expireDate = expireDate;
    }

    public int getID() {
        return ID;
    }

    public void setDamage(ProductIntegrity damage) {
        Damage = damage;
    }

    public void setDamageType(String damageType) {
        this.damageType = damageType;
    }

    public String getDamageType() {
        return damageType;
    }

    public Location getLocationInBranch() {
        return locationInBranch;
    }

    public LocalDate getExpireDate() {
        return expireDate;
    }

    public void setLocationInBranch(Location locationIn) {
        this.locationInBranch = locationIn;
    }

    public ProductIntegrity getDamage() {
        return Damage;
    }

    public double getAfterDiscountPrice() {
        return afterDiscountPrice;
    }
}
