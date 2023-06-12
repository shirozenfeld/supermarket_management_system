package BusinessLayer.SuppliersModule;

import java.util.*;

public class NotVisiting  extends Supplier
{
    public NotVisiting(String name, List<String> domains, Card card,Contract contract)
    {
        //new from menu
        super(name, domains, card,contract);
        this.name = name;
        this.domains = domains;
        this.card = card;
        this.contract = contract;
    }

    public NotVisiting(String name, List<String> domains, Card card,Contract contract,String supplier_id, int catalog_number)
    {
        super(supplier_id,name, domains, card, contract, catalog_number);
        this.name = name;
        this.domains = domains;
        this.card = card;
        this.contract = contract;
        this.supplier_id = supplier_id;
    }


    public int get_minimial_supplying_time()
    {
        return 3;
    }

}
