package DataAccessLayer.SuppliersModule;
import BusinessLayer.SuppliersModule.*;
import java.sql.*;
import java.util.HashMap;
import java.util.*;

public class SupplierDAO
{
    private static SupplierDAO instance;
    private Map<String,Supplier> identityMap;
    PreparedStatement stmt;
    private Connection conn;
    private int int_supplier_id;
    private int daysID_counter=0;

    private SupplierDAO() throws SQLException
    {
        this.conn=Database.getDataBaseInstance().getConnection();
        this.identityMap = new HashMap<>();
        this.daysID_counter=0;
    }

    public static SupplierDAO getSupplierInstance()
    {
        if (instance==null)
        {
            try {
                instance = new SupplierDAO();
            }
            catch(SQLException e)
            {
                System.out.println("An error occurred");
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
        return instance;
    }
    public void add(Supplier supplier)
    {
        int daysID=-1,delayDays=-1;
        List<Day> visitingDays=null;
        Contact contact = supplier.getCard().getContact_details();
        PreparedStatement stmtSupplier = null, stmtContact=null;
        int supplier_id=Integer.parseInt(supplier.getSupplier_id());
        try
        {

            //handle contact
            stmtContact=conn.prepareStatement("INSERT INTO Contact_Details (SupplierID,Street,City,PhoneNumber,Poc,BuildingNumber,Email) VALUES (?,?,?,?,?,?,?)");
            stmtContact.setInt(1,supplier_id);
            stmtContact.setString(2,contact.getStreet());
            stmtContact.setString(3,contact.getCity());
            stmtContact.setString(4,contact.getPhone_number());
            stmtContact.setString(5,contact.getPoc_name());
            stmtContact.setInt(6,contact.getBuilding_number());
            stmtContact.setString(7,contact.getEmail());
            stmtContact.executeUpdate();
            //handle Days
            if (supplier instanceof PersistentVisiting)
            {
                    visitingDays = ((PersistentVisiting) supplier).getDays();
                    //handle contact
                    PreparedStatement stmtDays = conn.prepareStatement("INSERT INTO Days (DaysID) VALUES (?)");
                    this.daysID_counter++;
                    daysID=this.daysID_counter;
                    stmtDays.setInt(1,daysID);
                    updateVisitingDays(this.daysID_counter,visitingDays);
                    stmtDays.executeUpdate();
            }
            stmtSupplier = conn.prepareStatement("INSERT INTO Suppliers (SupplierName,VisitingDays,BankAccountNumber,PHCNumber,PaymentCondition,PeriodicDay,SupplierType,DelayDays) VALUES (?,?,?,?,?,?,?,?)");
            //stmtSupplier.setInt(1, supplier_id);
            stmtSupplier.setString(1, supplier.getSupplierName());
            stmtSupplier.setInt(2, daysID);
            stmtSupplier.setInt(3, supplier.getCard().getBank_account_number());
            stmtSupplier.setInt(4, Integer.parseInt(supplier.getCard().getPhc_number()));
            stmtSupplier.setString(5, String.valueOf(supplier.getCard().getPayment_condition()));
            stmtSupplier.setString(6, String.valueOf(supplier.getCard().getPeriodic_orders_supplying_day()));
            if (supplier instanceof OrderlyVisiting) {
                stmtSupplier.setString(7, "OrderlyVisiting");
                stmtSupplier.setInt(8, ((OrderlyVisiting) supplier).getDelay_days());

            } else if (supplier instanceof NotVisiting) {
                stmtSupplier.setString(7, "NotVisiting");
                stmtSupplier.setInt(8, delayDays);
            } else if (supplier instanceof PersistentVisiting) {
                stmtSupplier.setString(7, "PersistentVisiting");
                stmtSupplier.setInt(8, delayDays);
            }
            stmtSupplier.executeUpdate();


        }
        catch (SQLException e)
        {
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        //identityMap.put(supplier.getSupplier_id(),supplier);
    }

    public void update(Supplier supplier)
        {
            stmt = null;
            try
            {
                stmt = conn.prepareStatement("UPDATE Suppliers SET SupplierName = ?, VisitingDay=?, BankAccount=?,PHCNumber=?,PaymentCondition=?,PeriodicDay=?,SupplierType=?,delayDays=? WHERE SuppplierID =?");
                stmt.setString(2, supplier.getSupplierName());
                if (!(supplier instanceof OrderlyVisiting || supplier instanceof NotVisiting)) {
                    List<Day> days=((PersistentVisiting)supplier).getDays();

                }
                stmt.setInt(4, supplier.getCard().getBank_account_number());
                stmt.setString(5,supplier.getCard().getPhc_number());
                stmt.setString(6,String.valueOf(supplier.getCard().getPayment_condition()));
                stmt.setString(7,String.valueOf(supplier.getCard().getPeriodic_orders_supplying_day()));
                //no need to update number 8
                stmt.executeUpdate();

            }
            catch (SQLException e)
            {
                System.out.println("An error occurred");
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
            //identityMap.put(supplier.getSupplier_id(),supplier);
        }

    public void delete(String supplierID)
    {
        //deleting a supplier = deleting him from Suppliers, his supplierProduct objects and his contact
        int supplier_id =Integer.parseInt((String)supplierID);
        try
        {

            PreparedStatement stmt = conn.prepareStatement("DELETE FROM Suppliers WHERE SupplierID=?");
            stmt.setInt(1, supplier_id);
            stmt.executeUpdate();

        }
        catch (SQLException e)
        {
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        deleteSupplierProducts(supplierID);
        deleteSuppliersContact(supplierID);
        deleteSuppliersOrders(supplierID);
        //identityMap.remove(supplierID);
    }

    public void updateVisitingDays(int daysID,List<Day> days)
    {
        String DayNameNumber;
        //handle contact
        for(int i=0;i<days.size();i++)
        {
            int day=days.get(i).ordinal()+1;
            DayNameNumber="DayName"+day;
        try
        {

            stmt = conn.prepareStatement("UPDATE Days SET " + DayNameNumber + " WHERE DaysID = ?");
            stmt.setInt(1, daysID);
            stmt.setString(day,"V");
            stmt.executeUpdate();

        }
        catch (SQLException e)
        {
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        }
    }

    public Supplier getSupplierBySupplierID(String supplier_id)
    {
        /*
        if (identityMap.containsKey(supplier_id))
        {
            Supplier supplier= identityMap.get(supplier_id);
            supplier.setContract();
            supplier.setPeriodicOrder_list();
            supplier.setOrders_list();
            supplier.setManufacturers();
            supplier.setCard();
            return supplier;
        }
        */
        Supplier supplier=null;
        int_supplier_id=Integer.parseInt(supplier_id);
        Card card=null;
        Contact contact=null;
        Contract contract=null;
        ContractDAO contractDAO=ContractDAO.getContractInstance();
        ManufacturerDAO manufacturerDAO=ManufacturerDAO.getManufacturerInstance();
        int visitingDays;
        List<Manufacturer> manufacturerList=null;
            // try - supplier
            try {
                PreparedStatement supplierStmt = conn.prepareStatement("SELECT * FROM Suppliers WHERE supplierID=?");
                supplierStmt.setInt(1, int_supplier_id);
                ResultSet supplierRS = supplierStmt.executeQuery();
                if (supplierRS.next())
                {
                    String supplierName = supplierRS.getString("SupplierName");
                    visitingDays = supplierRS.getInt("VisitingDays");
                    int bankAccount = supplierRS.getInt("BankAccountNumber");
                    String PHCNumber = supplierRS.getString("PHCNumber");
                    String paymentCondition = supplierRS.getString("PaymentCondition");
                    String PeriodicDay = supplierRS.getString("PeriodicDay");
                    String SupplierType = supplierRS.getString("SupplierType");
                    //try - contact
                    PreparedStatement contactStmt = conn.prepareStatement("SELECT * FROM Contact_Details WHERE SupplierID =?");
                    contactStmt.setInt(1, int_supplier_id);
                    ResultSet contactRS = contactStmt.executeQuery();
                    //if (contactRS.next())
                    //{
                        String Street = contactRS.getString("Street");
                        String City = contactRS.getString("City");
                        String PhoneNumber = contactRS.getString("PhoneNumber");
                        String Poc = contactRS.getString("Poc");
                        int BuildingNumber = contactRS.getInt("BuildingNumber");
                        String Email = contactRS.getString("Email");
                        contact = new Contact(Poc, PhoneNumber, Email, City, Street, BuildingNumber);
                        card = new Card(PHCNumber, bankAccount, contact, Card.Bill.valueOf(paymentCondition), Enum.valueOf(Day.class, PeriodicDay));
                    //}
                    OrderDAO orderDAO = OrderDAO.getOrderInstance();

                    Map<String, Order> shortage_orders = orderDAO.getShortageOrdersBySupplier(supplier_id);
                    Collection<Order> ordersCollection = shortage_orders.values();
                    List<Order> shortageOrdersList = new ArrayList<>(ordersCollection);
                    Map<String, Order> periodic_orders = orderDAO.getPeriodicOrdersBySupplier(supplier_id);
                    contract = contractDAO.getSupplierContractBySupplierID(supplier_id);
                    manufacturerList = manufacturerDAO.getManufacturerListBySupplierID(Integer.parseInt(supplier_id));
                    int current_catalogNumber=getMaxCatalogNumber(supplier_id);
                    if (SupplierType.equals("NotVisiting"))
                        supplier = new NotVisiting(supplierName, null, card, contract, supplier_id,current_catalogNumber);
                    else if (SupplierType.equals("OrderlyVisiting"))
                        supplier = new OrderlyVisiting(0, supplierName, null, card, contract, supplier_id,current_catalogNumber);
                    else if (SupplierType.equals("PersistentVisiting")) {

                        if (visitingDays >= 0) {
                            PreparedStatement visitingDaysStmt = conn.prepareStatement("SELECT * FROM Days WHERE DaysID=?");
                            visitingDaysStmt.setInt(1, visitingDays);
                            ResultSet rs = visitingDaysStmt.executeQuery();
                            List<Day> days = new ArrayList<>();
                            for (int i = 0; i < 7; i++) {
                                String dayName = "DayName" + i + 1;
                                if (rs.getString(dayName).equals("V")) {
                                    days.add(Day.values()[i]);
                                }
                            }
                            supplier = new PersistentVisiting(supplierName, null, card, contract, days, supplier_id,current_catalogNumber);
                        }

                    }
                supplier.setOrders_list(shortageOrdersList);
                supplier.setPeriodicOrder_list(periodic_orders);
                supplier.setManufacturers(manufacturerList);
                supplier.setName(supplierName);
                }

            }
            catch(SQLException e) {
                System.out.println("An error occurred");
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
            //identityMap.put(supplier_id,supplier);
            return supplier;

    }

    public void deleteSuppliersContact(String  supplierID)
    {
        int supplier_id =Integer.parseInt((String)supplierID);
        try
        {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM Contact_Details WHERE SupplierID=?");
            stmt.setInt(1, supplier_id);
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public void deleteSuppliersOrders(String  supplierID)
    {
        int supplier_id =Integer.parseInt((String)supplierID);
        try
        {
            PreparedStatement stmt1 = conn.prepareStatement("DELETE FROM ShortageOrders WHERE SupplierID=?");
            stmt1.setInt(1, supplier_id);
            stmt1.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        try
        {

            PreparedStatement stmt2 = conn.prepareStatement("DELETE FROM PeriodicOrder WHERE SupplierID=?");
            stmt2.setInt(1, supplier_id);
            stmt2.executeUpdate();

        }
        catch (SQLException e)
        {
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

    }

    public void deleteSupplierProducts(String supplierID)
    {
       SupplierProductDAO supplierProductDAO = SupplierProductDAO.getSupplierProductInstance();
       supplierProductDAO.getSupplierProductsBySupplierID(supplierID);
    }

    public Map<String,Supplier> getAllSuppliers() {
        Map<String, Supplier> suppliersList = new HashMap<>();
        try {
            PreparedStatement stmt69 = conn.prepareStatement("SELECT SupplierID FROM Suppliers");
            ResultSet rs = stmt69.executeQuery();
            while (rs.next()) {
                String supplierID = String.valueOf(rs.getInt("SupplierID"));
                Supplier supplier = getSupplierBySupplierID(supplierID);
                suppliersList.put(supplierID, supplier);
            }
        } catch (SQLException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return suppliersList;
    }

    public int getMaxID() {

        int supplierID=0;
        try {
            PreparedStatement stmt44 = conn.prepareStatement("SELECT MAX(SupplierID) FROM Suppliers");
            ResultSet rs = stmt44.executeQuery();
            while (rs.next()) {
                supplierID = rs.getInt(1) ;
            }

        } catch (SQLException e)
        {
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return supplierID;
    }

    public int getMaxCatalogNumber(String supplierID) {


        int supplierIDint= Integer.parseInt(supplierID);
        int max_catalogNumber=0;
        try {
            PreparedStatement stmt44 = conn.prepareStatement("SELECT MAX(CatalogNumber) FROM SupplierProduct WHERE SupplierID=? ");
            stmt44.setInt(1,supplierIDint);
            ResultSet rs = stmt44.executeQuery();
            while (rs.next()) {
                max_catalogNumber = rs.getInt(1) ;
            }

        } catch (SQLException e)
        {
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());

        }
        return max_catalogNumber;
    }

        public void removeAllSupplier()
        {
        try
        {
            Statement stmt2 = conn.createStatement();
            String query2 = "DELETE FROM Days";
            stmt2.executeUpdate(query2);
            Statement stmt1 = conn.createStatement();
            String query1 = "DELETE FROM Contact_Details";
            stmt1.executeUpdate(query1);
            Statement stmt99 = conn.createStatement();
            String query = "DELETE FROM Suppliers;";
            stmt99.executeUpdate(query);
        }
        catch (SQLException e)
        {
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        }

    }
