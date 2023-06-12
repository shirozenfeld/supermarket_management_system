package Test;
import BusinessLayer.BusinessFacade;
import BusinessLayer.InventoryModule.Location;
import BusinessLayer.InventoryModule.ProductIntegrity;
import DataAccessLayer.SuppliersModule.Database;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import BusinessLayer.InventoryModule.*;
import DataAccessLayer.InventoryModule.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;


class InventoryManagmentTest {
    @BeforeAll
    public static void setUp() {
//        BusinessFacade facade = BusinessFacade.getInstance();
        InventoryManagment imanage = InventoryManagment.getInstance();
        imanage.initializeSuper();
//        facade.initializeSuper();
    }
    //1
    @Test
    void checkLocation()
    {
        InventoryManagment imanage = InventoryManagment.getInstance();
//        imanage.initializeSuper();
        imanage.branchInSuper(1);
        imanage.editLocation(110011, "W");
        assertEquals(Location.warehouse, ((Item)(ItemDAO.getInstance().getById(110011))).getLocationInBranch(), "Test Passed");
    }
    //2
    @Test
    void checkPIntegrity()
    {
        InventoryManagment imanage = InventoryManagment.getInstance();
//        imanage.initializeSuper();
        imanage.branchInSuper(1);
        imanage.editPIntegrity(110011, "D", "broken", 0);
        assertEquals(ProductIntegrity.Damaged, ((Item)ItemDAO.getInstance().getById(110011)).getDamage(), "Test Passed");
    }
    //3
    @Test
    void checkIfAddedSuccessfullyBasicExist()
    {
        InventoryManagment imanage = InventoryManagment.getInstance();
//        imanage.initializeSuper();
        imanage.branchInSuper(1);
        LocalDate expired = LocalDate.parse("2023-05-25");
        imanage.addItemIfExist(1, 11, expired, "null", "S");
        assertEquals(110021, ((Item)ItemDAO.getInstance().getById(110021)).getID(), "Test Passed");
        assertEquals(Location.store, ((Item)ItemDAO.getInstance().getById(110021)).getLocationInBranch(), "Test Passed");
        assertEquals(expired, ((Item)ItemDAO.getInstance().getById(110021)).getExpireDate(), "Test Passed");
        assertEquals(ProductIntegrity.Null, ((Item)ItemDAO.getInstance().getById(110021)).getDamage(), "Test Passed");
        assertEquals("null", ((Item)ItemDAO.getInstance().getById(110021)).getDamageType(), "Test Passed");
    }
    //4
    @Test
    void checkDiscount()
    {
        InventoryManagment imanage = InventoryManagment.getInstance();
//        imanage.initializeSuper();
        imanage.branchInSuper(1);
        LocalDate start = LocalDate.parse("2023-05-10");
        LocalDate end = LocalDate.parse("2023-05-30");
        imanage.editDiscount(110011, start, end, 50);
        assertEquals(start, ((InventoryItem)InventoryItemDAO.getInstance().getById(11)).getCurrDiscount().getStartDate(), "Test Passed");
        assertEquals(end, ((InventoryItem)InventoryItemDAO.getInstance().getById(11)).getCurrDiscount().getEndDate(), "Test Passed");
        assertEquals(50, ((InventoryItem)InventoryItemDAO.getInstance().getById(11)).getCurrDiscount().getDiscountRate(), "Test Passed");
    }
    //5
    @Test
    void checkIfAddedSuccessfullyBasicNotExist()
    {
        InventoryManagment imanage = InventoryManagment.getInstance();
//        imanage.initializeSuper();
        imanage.branchInSuper(1);
        Map<Integer, String> cats = new HashMap<>();
        cats.put(11, "Dairy");
        cats.put(16, "Cheese");
        imanage.addItemIfBNotExist(22, "Cheese", "Tnuva", 12, cats);
        imanage.addItemIfInNotExist(22,0, 20);
        LocalDate expired = LocalDate.parse("2023-05-25");
        imanage.addItemIfExist(1, 22, expired, "null", "S");

        assertEquals(22, ((BasicItem)BasicItemDAO.getInstance().getById(22)).getCatalogNum(), "Test Passed");
        assertEquals(12, ((BasicItem)BasicItemDAO.getInstance().getById(22)).getCostPrice(), "Test Passed");
        assertEquals("Tnuva", ((BasicItem)BasicItemDAO.getInstance().getById(22)).getManufacturer(), "Test Passed");
        assertEquals(161, ((BasicItem)BasicItemDAO.getInstance().getById(22)).getItemCategories().get(0).getcID(), "Test Passed");
        assertEquals("Cheese", ((BasicItem)BasicItemDAO.getInstance().getById(22)).getName(), "Test Passed");

        assertEquals(20, ((InventoryItem)InventoryItemDAO.getInstance().getById(22)).getSellPrice(), "Test Passed");
        assertEquals(1, ((InventoryItem)InventoryItemDAO.getInstance().getById(22)).getTotalAmount(), "Test Passed");
        assertEquals(0, ((InventoryItem)InventoryItemDAO.getInstance().getById(22)).getMinimumAmount(), "Test Passed");

        assertEquals(220011, ((Item)ItemDAO.getInstance().getById(220011)).getID(), "Test Passed");
        assertEquals(Location.store, ((Item)ItemDAO.getInstance().getById(220011)).getLocationInBranch(), "Test Passed");
        assertEquals(expired, ((Item)ItemDAO.getInstance().getById(220011)).getExpireDate(), "Test Passed");
        assertEquals(ProductIntegrity.Null, ((Item)ItemDAO.getInstance().getById(220011)).getDamage(), "Test Passed");
        assertEquals("null", ((Item)ItemDAO.getInstance().getById(220011)).getDamageType(), "Test Passed");
    }

    //6
    @Test
    void checkIfDeleted()
    {
        InventoryManagment imanage = InventoryManagment.getInstance();
//        imanage.initializeSuper();
        imanage.branchInSuper(1);
        imanage.deleteItem(110011);
        assertNull(ItemDAO.getInstance().getById(110011), "Test Passed");
    }
    //7
    @Test
    void checkEditPeriodicExist()
    {
        BusinessFacade facade = BusinessFacade.getInstance();
        facade.initializeSuppliersModule();
        InventoryManagment imanage = InventoryManagment.getInstance();
        imanage.branchInSuper(1);
        Map<Integer,Integer> amounts = new HashMap<>();
        amounts.put(11, 10);

        imanage.editPeriodicOrder(1, amounts);
        assertEquals(10, ((PeriodicOrder)PeriodicOrderDAO.getInstance().getById(1)).getIdAmountMap().get(11), "Test Passed");
    }

}
