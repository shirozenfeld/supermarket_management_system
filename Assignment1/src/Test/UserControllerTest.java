package Test;

import BusinessLayer.BusinessFacade;
import BusinessLayer.SuppliersModule.*;
import DataAccessLayer.Connect;
import DataAccessLayer.SuppliersModule.*;
import PresentationLayer.SuppliersModule.UserController;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;



class UserControllerTest {
    @BeforeAll
    public static void setUp() {
        BusinessFacade facade = BusinessFacade.getInstance();
        facade.initialize();
    }

    //1
    @Test
    void checkManufacturersAddition() {
        ManufacturerController manufacturerController = ManufacturerController.getInstance();
        Manufacturer manufacturer = new Manufacturer("Muiler");
        manufacturerController.AddManufacturer("Muiler");
        assertEquals("Muiler", manufacturerController.getManufacturerByName("Muiler").getManufacturer_name(), "Test Passed");
    }

    //2
    @Test
    void checkSuppliersAddition() {
        //Connection conn=Database.getDataBaseInstance().getConnection();
        SuppliersController suppliersController = SuppliersController.getInstance();
        //adding through DAO
        suppliersController.addNotVisitingSupplier("Roni", "052356507", "roni@google.com", "Shoham", "Herzel", 12, "1235", 123, Card.Bill.shotef, Day.valueOf("Thursday"), "Davidovitz", null);
        // adding to Memory
        Contact supplier_contact = new Contact("Roni", "052356507", "roni@google.com", "Shoham", "Herzel", 12);
        Card supplier_card = new Card("1235", 123, supplier_contact, Card.Bill.shotef, Day.valueOf("Thursday"));
        Contract supplier_contract = new Contract();                        // find out what's the supplier's way of visiting
        Supplier supplier = new NotVisiting("Davidovitz", null, supplier_card, supplier_contract);

        assertEquals("Roni", suppliersController.getSupplierByID("3").getCard().getContact_details().getPoc_name(), "Test Passed");
        assertEquals("1235", suppliersController.getSupplierByID("3").getCard().getPhc_number(), "Test Passed");
        assertEquals("Davidovitz", suppliersController.getSupplierByID("3").getSupplierName(), "Test Passed");

    }

    //3
    @Test
    void checkSupplierProductsAddition() {
        Connection conn = Database.getDataBaseInstance().getConnection();
        SuppliersController suppliersController = SuppliersController.getInstance();
        suppliersController.add_supplierProduct("11", 4, 6.25, "3");
        SupplierProduct supplierProduct = new SupplierProduct("11", 4, 6.25, "3");
        assertEquals(6.25, suppliersController.getSupplierProduct("11", "3").getUnit_price(), "Test Passed");
        assertEquals(4, suppliersController.getSupplierProduct("11", "3").getAmount(), "Test Passed");


    }

    //4
    @Test
    void checkDiscountAddition() {
        Connection conn = Database.getDataBaseInstance().getConnection();
        QuantityDiscount quantityDiscount = new QuantityDiscount(4, 1, 20);
        SuppliersController suppliersController = SuppliersController.getInstance();
        suppliersController.add_discount_to_quantitiesBill("11", 1, 4, 'q', 20, "1");
        assertEquals(4, suppliersController.getSupplierByID("1").getContract().getQuantities_bill().get(1).get(0).getAmount(), "Test Passed");
        assertEquals(1, suppliersController.getSupplierByID("1").getContract().getQuantities_bill().get(1).get(0).getCatalog_number(), "Test Passed");
    }

    //5
    @Test
    void checkSupplierProductEdition() {
        Connection conn = Database.getDataBaseInstance().getConnection();
        SuppliersController suppliersController = SuppliersController.getInstance();
        suppliersController.update_supplierProduct("11", 5, 7, "1");
        SupplierProduct supplierProduct = new SupplierProduct("11", 5, 7, "1");
        assertEquals(7, suppliersController.getSupplierProduct("11", "1").getUnit_price(), "Test Passed");
        assertEquals(5, suppliersController.getSupplierProduct("11", "1").getAmount(), "Test Passed");
    }


    //6
    //TODO check if work!!!!!
    @Test
    void createShortageOrdersReport() {
        OrdersController ordersController = OrdersController.getInstance();
        DeficienciesReport dailyReport=ordersController.getDailyShortageReport();
        System.out.println(dailyReport.getReport_id());
        assertEquals("1", dailyReport.getReport_id(), "Test Passed");
    }

    //7
    //TODO check if work!!!!!
    @Test
    void handleShortageOrder()
    {
        OrdersController ordersController = OrdersController.getInstance();
        DeficienciesReport  dailyReport=ordersController.getDailyShortageReport();
        ordersController.deficiencies_handler(dailyReport);
        assertEquals(true, ordersController.doesOrderExist("2"), "Test Passed");

    }
    //8

    @Test
    void doesShortageOrderExist()
    {
        OrdersController ordersController = OrdersController.getInstance();
        assertEquals(true,ordersController.doesOrderExist("2") ,"Test Passed");
    }




    @AfterAll
    public static void clean()
    {
        Database.getDataBaseInstance().closeConn();
    }

}