package BusinessLayer.SuppliersModule;

public class Card
{
    String phc_number;
    int bank_account_number;
    Contact contact_details;
    Bill payment_condition;
    Day periodic_orders_supplying_day;
    public Card(String phc_number, int bank_account_number, Contact contact_details, Bill payment_condition,Day periodic_orders_supplying_day)
    {
        if (phc_number!=null && bank_account_number>0 && contact_details!=null && payment_condition!=null )
        {
            this.phc_number = phc_number;
            this.bank_account_number = bank_account_number;
            this.contact_details = contact_details;
            this.payment_condition = payment_condition;
            this.periodic_orders_supplying_day=periodic_orders_supplying_day;
        }
    }

    public String getPhc_number() {
        return phc_number;
    }

    public int getBank_account_number() {
        return bank_account_number;
    }

    public Contact getContact_details() {
        return contact_details;
    }

    public Bill getPayment_condition() {
        return payment_condition;
    }

    public void setPhc_number(String phc_number) {
        this.phc_number = phc_number;
    }

    public void setBank_account_number(int bank_account_number) {
        this.bank_account_number = bank_account_number;
    }

    public void setPayment_condition(Bill payment_condition) {
        this.payment_condition = payment_condition;
    }

    public enum Bill{shotef,shotef30,shotef60}
    public Day getPeriodic_orders_supplying_day() {
        return periodic_orders_supplying_day;
    }


}
