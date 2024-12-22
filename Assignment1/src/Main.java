import BusinessLayer.BusinessFacade;
import BusinessLayer.InventoryModule.InventoryManagment;
import PresentationLayer.InventoryModule.UserInterface;
import PresentationLayer.SuppliersModule.CLI.UserController;

import java.util.*;
// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main
{

    public static void main(String[] args)
    {
        //TODO: DELETE
        /*
        Scanner scan = new Scanner(System.in);
        BusinessFacade businessFacade = BusinessFacade.getInstance();
        businessFacade.initialize();
        //businessFacade.initializeSuppliersModule();
        //InventoryManagment.getInstance().restoreAll();
        //show menu
        //PresentationLayer.SuppliersModule.GUI.GUIForm.suppliersMenu.setVisible(true);
        //PresentationLayer.InventoryModule.GUI.GUIForm.stockkeeper.setVisible(true);
        //PresentationLayer.StoreManagerMenu storeManagerMenu=new PresentationLayer.StoreManagerMenu();
        //storeManagerMenu.setVisible(true);
        //UserController userController = UserController.getInstance();
        //UserInterface userInterface = UserInterface.getInstance();

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

         */

        String screen=args[0];
        String user=args[1];

        UserController userController = UserController.getInstance();
        UserInterface userInterface = UserInterface.getInstance();
        Scanner scan = new Scanner(System.in);
        BusinessFacade businessFacade = BusinessFacade.getInstance();
        businessFacade.initialize();
        //businessFacade.initializeSuppliersModule();
        //InventoryManagment.getInstance().restoreAll();
        if(screen.equals("GUI")&&user.equals("Purchaser"))
        {
            PresentationLayer.SuppliersModule.GUI.SuppliersMenu suppliersMenu= new PresentationLayer.SuppliersModule.GUI.SuppliersMenu();
            suppliersMenu.setVisible(true);

        }
        else if (screen.equals("CLI") && user.equals("Purchaser"))
        {
            System.out.println("============ Welcome To Super-Li! ============");
            userController.PurchasorScreen();

        }
        else if (screen.equals("GUI") && user.equals("StockKeeper"))
        {

            PresentationLayer.InventoryModule.GUI.Stockkeeper stockkeeper= new PresentationLayer.InventoryModule.GUI.Stockkeeper();
            stockkeeper.setVisible(true);

        }
        else if (screen.equals("CLI") && user.equals("StockKeeper"))
        {
            System.out.println("============ Welcome To Super-Li! ============");
            userInterface.mainInventory();
        }

        else if (screen.equals("GUI") && user.equals("StoreManager"))
        {

            PresentationLayer.StoreManagerMenu storeManagerMenu=new PresentationLayer.StoreManagerMenu();
            storeManagerMenu.setVisible(true);
        }
        else if (screen.equals("CLI") && user.equals("StoreManager"))
        {
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
}
