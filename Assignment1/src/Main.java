import BusinessLayer.BusinessFacade;
import BusinessLayer.InventoryModule.InventoryManagment;
import PresentationLayer.InventoryModule.UserInterface;
import PresentationLayer.SuppliersModule.GUI.GUIForm;
import PresentationLayer.SuppliersModule.GUI.UserController;

import java.util.*;
// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main
{

    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);
        BusinessFacade businessFacade = BusinessFacade.getInstance();
        //businessFacade.initialize();
        //businessFacade.initializeSuppliersModule();
        InventoryManagment.getInstance().restoreAll();
        //show menu
        GUIForm.suppliersMenu.setVisible(true);
        UserController userController = UserController.getInstance();
        UserInterface userInterface = UserInterface.getInstance();
        System.out.println("============ Welcome To Super-Li! ============");
        String choice = "";
        while (choice != "3") {
            System.out.println("What would you like to do today?");
            System.out.println("1. Inventory Issues");
            System.out.println("2. Supplier Issues");
            System.out.println("3. Exit");
            choice = scan.next();
            switch (choice) {
                case "1":
                    userInterface.mainInventory();
                case "2":
                    userController.PurchasorScreen();
                case "3":
                    System.out.println("Bye! See You Soon!");
                    System.out.println("Have a nice day!");
                    return;
                default:
                    System.out.println("This option doesn't exist, please try again");
            }
        }



    }
}
