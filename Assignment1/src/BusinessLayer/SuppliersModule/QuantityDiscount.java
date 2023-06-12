package BusinessLayer.SuppliersModule;

public class QuantityDiscount extends Discount
{
    int gift_amount;

    public QuantityDiscount(int amount, int catalog_number, int gift_amount) {
        super(amount, catalog_number);
        this.gift_amount = gift_amount;
    }

    @Override
    public int getAmount() {
        return super.getAmount();
    }

    @Override
    public void setAmount(int amount) {
        super.setAmount(amount);
    }

    @Override
    public int getCatalog_number() {
        return super.getCatalog_number();
    }

    @Override
    public void setCatalog_number(int catalog_number) {
        super.setCatalog_number(catalog_number);
    }

    public QuantityDiscount(int amount, int catalog_number) {
        super(amount, catalog_number);
    }

    public int getGift_amount() {
        return gift_amount;
    }

    public void setGift_amount(int gift_amount) {
        this.gift_amount = gift_amount;
    }

    @Override
    public double Discount_Calculator(double unit_price, int needed_amount)
    {
        if (this.amount>=needed_amount)
            return this.gift_amount*unit_price;
        else
            return 0;
    }

}
