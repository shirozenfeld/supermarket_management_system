package BusinessLayer.InventoryModule;

import java.time.LocalDate;

public class Discount {
    private int discountID;
    private LocalDate startDate;
    private LocalDate endDate;
    private double discountRate;
    private static int discountCounter = 1;

    public Discount(LocalDate startDate, LocalDate endDate, double discountRate) {
        discountID = discountCounter++;
        this.startDate = startDate;
        this.endDate = endDate;
        this.discountRate = discountRate;
    }

    public int getDiscountID() {
        return discountID;
    }

    public double getDiscountRate(){
        return this.discountRate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}
