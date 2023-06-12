package BusinessLayer.SuppliersModule;

public class Product
{
    static int counter=0;
    String supermarket_id;
    //for creating a super product
    public Product()
    {
            this.counter++;
            this.supermarket_id = Integer.toString(this.counter);
    }
    //for creating a supplier product
    public Product(String supermarket_id)
    {
        this.supermarket_id = supermarket_id;
    }

    public String getSupermarket_id() {
        return supermarket_id;
    }

    public void setSupermarket_id(String supermarket_id) {
        this.supermarket_id = supermarket_id;
    }
}
