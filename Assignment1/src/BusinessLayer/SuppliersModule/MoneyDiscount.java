package BusinessLayer.SuppliersModule;

public class MoneyDiscount extends Discount
{
    int percents;

    public MoneyDiscount(int amount, int catalog_number,int percents) {
        super(amount, catalog_number);
        this.percents = percents;
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

    public int getPercents() {
        return percents;
    }

    public void setPercents(int percents) {
        this.percents = percents;
    }

    @Override
    public double Discount_Calculator(double finale_price, int needed_amount)
    {
        if (this.amount>=needed_amount)
            return (double)finale_price*((double)this.percents/100.0);
        else
            return 0;
    }
}
