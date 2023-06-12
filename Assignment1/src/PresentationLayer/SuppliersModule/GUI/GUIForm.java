package PresentationLayer.SuppliersModule.GUI;
import java.awt.Point;
public class GUIForm
{
    public static SuppliersMenu suppliersMenu= new SuppliersMenu();
    public static AddSupplier addSupplier= new AddSupplier();
    public static RemoveSupplier removeSupplier = new RemoveSupplier();
    public static UpdateCard updateCard = new UpdateCard();
    public static UpdateContract updateContract = new UpdateContract();
    public static UpdateVisitingDay updateVisitingDay= new UpdateVisitingDay();
    public static WatchVisitingDay watchVisitingDay = new WatchVisitingDay();
    public static AddManufacturer addManufacturer = new AddManufacturer();
    public static HandleDeficiencies handleDeficiencies = new HandleDeficiencies();
    public static WatchSuppliersContracts watchSuppliersContracts = new WatchSuppliersContracts();
    public static AddPeriodicOrder addPeriodicOrder = new AddPeriodicOrder();



//    public static void UpdateDisplay()
//    {
//
//        if(displaylist.isVisible())
//        {
//            Point O= displaylist.getLocation();
//            displaylist.dispose();
//            displaylist = new DisplayList();
//            displaylist.setVisible(true);
//            displaylist.setLocation(O);;
//        }
//
//        else {
//            displaylist = new DisplayList();
//        }
//
//    }


}
