package BusinessLayer.SuppliersModule;

public abstract class Discount
{
    int amount;
    int catalog_number;
    static int counter=0;
    String discount_id;
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getCatalog_number() {
        return catalog_number;
    }

    public void setCatalog_number(int catalog_number) {
        this.catalog_number = catalog_number;
    }


    public Discount(int amount, int catalog_number) {
        if (amount>0 && catalog_number>=0) {
            this.amount = amount;
            this.catalog_number = catalog_number;
            this.counter++;
            this.discount_id=Integer.toString(counter);

        }
    }

    public Discount(int amount, int catalog_number, String discount_id) {
        if (amount>0 && catalog_number>=0) {
            this.amount = amount;
            this.catalog_number = catalog_number;
            this.discount_id = discount_id;

        }
    }
    public abstract double Discount_Calculator(double unit_price, int needed_amount);

    public String getDiscount_id() {
        return discount_id;
    }

    public void setDiscount_id(String discountId)
    {
        this.discount_id= discountId;
    }
}
