package BusinessLayer;

import BusinessLayer.InventoryModule.*;
import BusinessLayer.SuppliersModule.*;

import java.util.HashMap;
import java.util.Map;

public class BusinessFacade
{
    ManufacturerController manufacturerController;
    OrdersController ordersController;
    SuppliersController suppliersController;
    InventoryManagment imanage;

    private static BusinessFacade instance=null;

    private BusinessFacade()
    {
        this.manufacturerController = ManufacturerController.getInstance();
        this.ordersController=OrdersController.getInstance();
        this.suppliersController=SuppliersController.getInstance();
        this.imanage= InventoryManagment.getInstance();

    }

    public static BusinessFacade getInstance()
    {
        if (instance == null) {
            instance = new BusinessFacade();
        }
        return instance;
    }
    public void initialize()
    {
        imanage.initializeSuper();
        initializeSuppliersModule();

    }
    public void restore(){
        imanage.restoreAll();
    }


    public void initializeSuppliersModule()
    {

        this.ordersController.removeAllOrders();
        this.suppliersController.removeAllSupplierDiscounts();
        this.suppliersController.removeAllSupplierProducts();
        this.manufacturerController.removeAllManufacturers();
        this.suppliersController.removeAllSuppliers();
        this.manufacturerController.AddManufacturer("Tnuva");
        this.manufacturerController.AddManufacturer("Tavor");
        this.manufacturerController.AddManufacturer("Carmel");
        //BasicItem b1 = new BasicItem("Liter milk 3% Tnuva", 11, "Tnuva", 4, cats1, 0);
        //BasicItem b2 = new BasicItem("Carmel white wine", 25, "Carmel", 14, cats2, 0);
        //BasicItem b3 = new BasicItem("Tavor white wine", 26, "Tavor", 14, cats3, 0);

        this.suppliersController.addNotVisitingSupplier("Shir","0523180907","shir@google.com","Ramla","Bialik",12,"1235",123, Card.Bill.shotef,Day.valueOf("Friday"),"Uniliver",null);
        this.suppliersController.addNotVisitingSupplier("Ofek", "0542476453", "ofek@gmail.com", "Tel Aviv","Barkan", 117, "1456",622, Card.Bill.shotef, Day.valueOf("Friday"), "Shastovitz", null);

        this.suppliersController.add_supplierProduct("11",4,6.25,"1"); // barcode: 1
        this.suppliersController.add_supplierProduct("11",3,4,"2"); // barcode: 1
        this.suppliersController.add_supplierProduct("26",5,8,"1"); // barcode: this.suppliersController.add_supplierProduct("26",2,6,"2");// barcode: 1
        this.suppliersController.add_supplierProduct("25",3,4,"2");// barcode: 2
        this.suppliersController.add_discount_to_quantitiesBill("11",1,4,'q',20,"1");//       this.suppliersController.add_discount_to_quantitiesBill("26",2,2,'q',20,"1");

        Map<Integer,Integer> map=new HashMap<>();
        map.put(1,1);
        Order order = new Order("Periodic","1");
        order.setProducts(map);
        this.ordersController.addPeriodicOrder(order);

    }

}
