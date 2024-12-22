package DataAccessLayer.SuppliersModule;
import BusinessLayer.SuppliersModule.*;
import org.junit.platform.commons.function.Try;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class OrderDAO {
    private static OrderDAO instance;
    private Map<String, Order> identityMap;
    //PreparedStatement stmt;
    private Connection conn;
    private int int_order_id;
    private int daysID_counter = 0;

    SupplierDAO supplierDAO;

    private OrderDAO()
    {
        this.conn=Database.getDataBaseInstance().getConnection();
        this.identityMap = new HashMap<>();
        this.supplierDAO = SupplierDAO.getSupplierInstance();
    }

    public static OrderDAO getOrderInstance() {
        if (instance == null) {
            instance = new OrderDAO();
        }
        return instance;
    }

    public void add(Object obj) {
        if (((Order) obj).getType().equals("Shortage"))
            addShortageOrder((Order) obj);
        if (((Order) obj).getType().equals("Periodic"))
            addPeriodicOrder((Order) obj);
    }

    public void addShortageOrder(Order order) {
        String supplierID = order.getSupplierID();
        Supplier supplier = supplierDAO.getSupplierBySupplierID(supplierID);
        String order_id = order.getOrder_id();
        Map<Integer, SupplierProduct> catalognumber_supplierproduct = new HashMap<Integer, SupplierProduct>();
        for (Map.Entry<String, SupplierProduct> entry1 : supplier.getContract().getProducts().entrySet()) {
            SupplierProduct supplierProduct = entry1.getValue();
            int catalogNumber = supplierProduct.getCatalog_number();
            catalognumber_supplierproduct.put(catalogNumber, supplierProduct);
        }
        //PreparedStatement stmt1 = null;
        //insert a new line for each product in the order
        try {
            for (Map.Entry<Integer, Integer> entry : order.getProducts().entrySet()) {
                String barcode = catalognumber_supplierproduct.get(entry.getKey()).getSupermarket_id();
                int amount = entry.getValue();
                //find barcode and supplierProduct
                LocalDate date = order.getOrder_date();
                double initial_price = order.getFirst_price();
                double finale_price = order.getFinale_price();
                String reportid = order.getReportId();

                PreparedStatement stmt1 = conn.prepareStatement("INSERT INTO ShortageOrders (Barcode, Date,AmountToOrder,OrderID,SupplierID,InitialPrice,FinalPrice,ReportID,Type) VALUES (?,?,?,?,?,?,?,?,?)");
                stmt1.setInt(1, Integer.parseInt(barcode));
                stmt1.setDate(2, Date.valueOf(date));
                stmt1.setInt(3, amount);
                stmt1.setInt(4, Integer.parseInt(order_id));
                stmt1.setInt(5, Integer.parseInt(supplierID));
                stmt1.setDouble(6, initial_price);
                stmt1.setDouble(7, finale_price);
                stmt1.setInt(8, Integer.parseInt(reportid));
                stmt1.setString(9, "Shortage");
                stmt1.executeUpdate();
            }
        }
        catch(SQLException e){
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
    }
        identityMap.put(order_id, order);
    }

    public void addPeriodicOrder(Order order) {
        String supplierID = order.getSupplierID();
        Supplier supplier = supplierDAO.getSupplierBySupplierID(supplierID);
        String order_id = order.getOrder_id();
        String day = String.valueOf(supplier.getCard().getPeriodic_orders_supplying_day());
        Map<Integer, SupplierProduct> catalognumber_supplierproduct = new HashMap<Integer, SupplierProduct>();
        for (Map.Entry<String, SupplierProduct> entry1 : supplier.getContract().getProducts().entrySet()) {
            SupplierProduct supplierProduct = entry1.getValue();
            int catalogNumber = supplierProduct.getCatalog_number();
            catalognumber_supplierproduct.put(catalogNumber, supplierProduct);
        }
        PreparedStatement stmt2 = null;
        //insert a new line for each product in the order
        for (Map.Entry<Integer, Integer> entry : order.getProducts().entrySet())
        {
            String barcode = catalognumber_supplierproduct.get(entry.getKey()).getSupermarket_id();
            int amount = entry.getValue();
            try {
                stmt2 = conn.prepareStatement("INSERT INTO PeriodicOrder (Barcode, OrderID ,Day,AmountToOrder,SupplierID,Type) VALUES (?,?,?,?,?,?)");
                stmt2.setInt(1, Integer.parseInt(barcode));
                stmt2.setInt(2, Integer.parseInt(order_id));
                stmt2.setString(3, String.valueOf(day));
                stmt2.setInt(4, amount);
                stmt2.setInt(5, Integer.parseInt(supplier.getSupplier_id()));
                stmt2.setString(6, "Periodic");
                stmt2.executeUpdate();
            }
            catch (SQLException e)
            {
                System.out.println("An error occurred");
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
        //identityMap.put(order_id, order);
    }

    public void update(Object obj) {
        Order order = (Order) obj;
        PreparedStatement stmt4 = null;
        String orderID = order.getOrder_id();
        String supplierID = order.getSupplierID();
        Supplier supplier = supplierDAO.getSupplierBySupplierID(supplierID);
        Map<Integer, SupplierProduct> catalognumber_supplierproduct = new HashMap<>();
        for (Map.Entry<String, SupplierProduct> entry1 : supplier.getContract().getProducts().entrySet()) {
            SupplierProduct supplierProduct = entry1.getValue();
            int catalogNumber = supplierProduct.getCatalog_number();
            catalognumber_supplierproduct.put(catalogNumber, supplierProduct);
        }

        try {
            // Update Shortage Orders
            if (order.getType().equals("Shortage")) {
                stmt4 = conn.prepareStatement("UPDATE ShortageOrders SET Barcode=?, Date=?, AmountToOrder=?, OrderID=?, SupplierID=?, InitialPrice=?, FinalPrice=?, ReportID=?, Type=? WHERE OrderID=?");
                for (Map.Entry<Integer, Integer> entry : order.getProducts().entrySet()) {
                    int catalogNumber = entry.getKey();
                    int barcode = Integer.parseInt(catalognumber_supplierproduct.get(catalogNumber).getSupermarket_id());
                    int amount = entry.getValue();

                    stmt4.setInt(1, barcode);
                    stmt4.setDate(2, java.sql.Date.valueOf(order.getOrder_date()));
                    stmt4.setInt(3, amount);
                    stmt4.setInt(4, Integer.parseInt(order.getOrder_id()));
                    stmt4.setInt(5, Integer.parseInt(order.getSupplierID()));
                    stmt4.setDouble(6, order.getFirst_price());
                    stmt4.setDouble(7, order.getFinale_price());
                    stmt4.setInt(8, Integer.parseInt(order.getReportId()));
                    stmt4.setString(9, "Shortage");
                    stmt4.setInt(10, Integer.parseInt(order.getOrder_id()));
                    stmt4.executeUpdate();
                }
            }

            // Update Periodic Order
            if (order.getType().equals("Periodic")) {
                stmt4 = conn.prepareStatement("UPDATE PeriodicOrder SET Barcode=?, OrderID=?, Day=?, AmountToOrder=?, SupplierID=?, Type=? WHERE OrderID=?");
                for (Map.Entry<Integer, Integer> entry : order.getProducts().entrySet()) {
                    int catalogNumber = entry.getKey();
                    int barcode = Integer.parseInt(catalognumber_supplierproduct.get(catalogNumber).getSupermarket_id());
                    int amount = entry.getValue();

                    stmt4.setInt(1, barcode);
                    stmt4.setInt(2, Integer.parseInt(order.getOrder_id()));
                    stmt4.setString(3, String.valueOf(supplier.getCard().getPeriodic_orders_supplying_day()));
                    stmt4.setInt(4, amount);
                    stmt4.setInt(5, Integer.parseInt(supplier.getSupplier_id()));
                    stmt4.setString(6, "Periodic");
                    stmt4.setInt(7, Integer.parseInt(order.getOrder_id()));
                    stmt4.executeUpdate();
                }
            }
        } catch (SQLException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        identityMap.put(orderID, order);
    }

    public void delete(String OrderID) {
        int_order_id = Integer.parseInt(OrderID);
        String type="";
        PreparedStatement stmt6;
        try {
            stmt6 = conn.prepareStatement("SELECT * FROM PeriodicOrder WHERE OrderID=?");
            stmt6.setInt(1, int_order_id);
            ResultSet rs = stmt6.executeQuery();
            if (rs.next())
                type = "PeriodicOrder";

        }
        catch(SQLException e)
        {
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
            try
            {
                stmt6 = conn.prepareStatement("SELECT * FROM ShortageOrders WHERE OrderID=?");
                stmt6.setInt(1, int_order_id);
                ResultSet rs = stmt6.executeQuery();
                if (rs.next())
                    type = "ShortageOrders";
            }
            catch (SQLException e) {
                System.out.println("An error occurred");
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
            if (type!="") {
                try {
                    stmt6 = conn.prepareStatement("DELETE FROM " + type + " WHERE OrderID=?");
                    stmt6.setInt(1, int_order_id);
                    stmt6.executeUpdate();
                    if (identityMap.containsKey(OrderID))
                        identityMap.remove(OrderID);
                }
                catch (SQLException e)
                {
                    System.out.println("An error occurred");
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }
            }
        }

    public Map<String,Order> getShortageOrdersBySupplier(String supplier_id)
    {
        Map<String, Order> orders = new HashMap<>();
        int catalogNumber=0;
        PreparedStatement stmt7 = null;
        try
        {
            stmt7 = conn.prepareStatement("SELECT * FROM ShortageOrders WHERE SupplierID=?");
            stmt7.setInt(1, Integer.parseInt(supplier_id));
            ResultSet rs = stmt7.executeQuery();
            while (rs.next())
            {
                if (rs.getString("Type").equals("Shortage")) {
                    String order_id = String.valueOf(rs.getInt("OrderID"));
                    int amount = rs.getInt("AmountToOrder");
                    double initial_price = rs.getDouble("InitialPrice");
                    double finalPrice = rs.getDouble("FinalPrice");
                    String report_id = String.valueOf(rs.getInt("ReportID"));
                    String barcode=String.valueOf(rs.getInt("Barcode"));
                    String type = rs.getString("Type");
                    PreparedStatement stmtCatalogNumber = conn.prepareStatement("SELECT * FROM SupplierProduct WHERE Barcode = ? AND SupplierID=?");
                    stmtCatalogNumber.setInt(1,Integer.parseInt(barcode));
                    stmtCatalogNumber.setInt(2,Integer.parseInt(supplier_id));
                    ResultSet rsCatalogNumber=stmtCatalogNumber.executeQuery();
                    if(rsCatalogNumber.next())
                        catalogNumber=rsCatalogNumber.getInt("CatalogNumber");
                    if (orders.containsKey(order_id))
                        orders.get(order_id).addToShortageOrder(catalogNumber, amount, initial_price, finalPrice);
                    else
                    {
                        Order order = new Order(rs.getString("Type"),String.valueOf(rs.getInt("SupplierID")), String.valueOf(rs.getInt("ReportID")));
                        order.setFinale_price(rs.getDouble("FinalPrice"));
                        order.setFirst_price(rs.getDouble("InitialPrice"));
                        if (orders.get(order_id)!=null) {
                            orders.get(order_id).addToShortageOrder(catalogNumber, amount, initial_price, finalPrice);
                            orders.put(order_id, order);
                        }
                        identityMap.put(order_id,order);
                    }
                }
            }
        }
        catch (SQLException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return orders;
    }

    public Map<String,Order> getPeriodicOrdersBySupplier(String supplier_id)
    {
        int catalogNumber=-1;
        Map<String, Order> orders = new HashMap<>();
        PreparedStatement stmt8 = null;
        try
        {
            stmt8 = conn.prepareStatement("SELECT * FROM PeriodicOrder WHERE SupplierID=?");
            stmt8.setInt(1, Integer.parseInt(supplier_id));
            ResultSet rs = stmt8.executeQuery();
            if (rs.next()) {
                while (rs.next())
                {
                    if (rs.getString("Type").equals("Periodic"))
                    {
                        String order_id = String.valueOf(rs.getInt("OrderID"));
                        int amount = rs.getInt("AmountToOrder");
                        String type = rs.getString("Type");
                        String barcode=String.valueOf(rs.getInt("Barcode"));
                        try
                        {
                            PreparedStatement stmtCatalogNumber = conn.prepareStatement("SELECT * FROM SupplierProduct WHERE Barcode = ? AND SupplierID=?");
                            stmtCatalogNumber.setInt(1,Integer.parseInt(barcode));
                            stmtCatalogNumber.setInt(2,Integer.parseInt(supplier_id));
                            ResultSet rsCatalogNumber=stmtCatalogNumber.executeQuery();
                            if(rsCatalogNumber.next())
                                catalogNumber=rsCatalogNumber.getInt("CatalogNumber");

                        }
                        catch(SQLException e)
                        {
                            System.out.println("An error occurred");
                            e.printStackTrace();
                            System.out.println(e.getMessage());
                        }
                        if (orders.containsKey(order_id))
                            orders.get(order_id).addToPeriodicOrder(catalogNumber,amount);
                        else {
                            Order order = new Order(rs.getString("Type"),String.valueOf(rs.getInt("SupplierID")),"-1");
                            orders.put(order_id,order);
                            orders.get(order_id).addToPeriodicOrder(catalogNumber, amount);
                            //identityMap.put(order_id,order);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return (orders);
    }

    public Map<String,Order> getOrdersByReportID(String reportID)
    {
        Map<String,Order> orders=new HashMap<>();
        int catalogNumber=0;
        PreparedStatement stmt9 = null;
        try
        {

            stmt9 = conn.prepareStatement("SELECT * FROM ShortageOrder WHERE ReportID=?");
            stmt9.setInt(1, Integer.parseInt(reportID));
            ResultSet rs = stmt9.executeQuery();
            while (rs.next()) {
                    String order_id = String.valueOf(rs.getInt("OrderID"));
                    int amount = rs.getInt("AmountToOrder");
                    double initial_price = rs.getDouble("InitialPrice");
                    double finalPrice = rs.getDouble("FinalPrice");
                    String supplier_id = String.valueOf(rs.getInt("SupplierID"));
                    String barcode=String.valueOf(rs.getInt("Barcode"));
                    String type = rs.getString("Type");
                    PreparedStatement stmtCatalogNumber = conn.prepareStatement("SELECT * FROM SupplierProduct WHERE Barcode = ? AND SupplierID=?");
                    stmtCatalogNumber.setInt(1,Integer.parseInt(barcode));
                    stmtCatalogNumber.setInt(2,Integer.parseInt(supplier_id));
                    ResultSet rsCatalogNumber=stmtCatalogNumber.executeQuery();
                    if(rsCatalogNumber.next())
                        catalogNumber=rsCatalogNumber.getInt("CatalogNumber ");

                    if (orders.containsKey(order_id))
                        orders.get(order_id).addToShortageOrder(catalogNumber, amount, initial_price, finalPrice);
                    else
                    {
                        Order order = new Order(rs.getString("Type"),String.valueOf(rs.getInt("SupplierID")),String.valueOf(rs.getInt("ReportID")));
                        orders.get(order_id).addToShortageOrder(catalogNumber, amount, initial_price, finalPrice);
                        orders.put(order_id, order);
                        order.setFinale_price(rs.getDouble("FinalPrice"));
                        order.setFirst_price(rs.getDouble("InitialPrice"));
                        identityMap.put(order_id,order);
                    }
                }
        }
        catch (SQLException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return orders;
    }

//    public boolean doesReportExistByDate(LocalDate date)
//    {
//        try
//        {
//            PreparedStatement stmt10 = conn.prepareStatement("SELECT * FROM ShortageOrders WHERE Date=?");
//            stmt10.setDate(1,java.sql.Date.valueOf(date));
//            ResultSet rs= stmt10.executeQuery();
//            if (rs.next())
//                return true;
//        }
//        catch(SQLException e)
//        {
//            System.out.println("An error occurred");
//            e.printStackTrace();
//            System.out.println(e.getMessage());
//        }
//        return false;
//    }

    public boolean doesOrderExist(String orderID)
    {
        try
        {
            PreparedStatement stmt2323 = conn.prepareStatement("SELECT * FROM ShortageOrders WHERE OrderID=?");
            stmt2323.setInt(1,Integer.parseInt(orderID));
            ResultSet rs= stmt2323.executeQuery();
            if (rs.next())
                return true;
        }
        catch(SQLException e)
        {
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return false;
    }

    public void removeAllOrders()
    {
        try
        {
            Statement stmt3434 = conn.createStatement();
            String query1443 = "DELETE FROM ShortageOrders";
            stmt3434.executeUpdate(query1443);
            Statement stmt1 = conn.createStatement();
            String query1 = "DELETE FROM PeriodicOrder";
            stmt1.executeUpdate(query1);
        }
        catch (SQLException e)
        {
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public void removeOrdersByReportID(String reportID)
    {
        int reportintID=Integer.parseInt(reportID);
        try
        {
            PreparedStatement stmt11 = conn.prepareStatement("DELETE FROM ShortageOrders WHERE ReportID=?");
            stmt11.setInt(1, reportintID);
            stmt11.executeQuery();
        }
        catch (SQLException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
    public void removePeriodicOrderBySupplierID(String orderid)
    {
        int int_orderid=Integer.parseInt(orderid);
        try
        {
            PreparedStatement stmt11 = conn.prepareStatement("DELETE FROM PeriodicOrder WHERE OrderID=?");
            stmt11.setInt(1, int_orderid);
            stmt11.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
    public String getPeriodicOrdersBySupplierID(String supplierID)
    {
        try
        {
            PreparedStatement stmt23123 = conn.prepareStatement("SELECT * FROM PeriodicOrder WHERE SupplierID=?");
            stmt23123.setInt(1,Integer.parseInt(supplierID));
            ResultSet rs= stmt23123.executeQuery();
            if (rs.next())
                return String.valueOf(rs.getInt("OrderID"));
        }
        catch(SQLException e)
        {
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return null;
    }

    public int getMaxPeriodicOrderNumber()
    {

        int max_PeriodicOrderID=0;
        try {
            PreparedStatement stmt44 = conn.prepareStatement("SELECT MAX(OrderID) FROM PeriodicOrder");
            ResultSet rs = stmt44.executeQuery();
            while (rs.next()) {
                max_PeriodicOrderID = rs.getInt(1) ;
            }

        } catch (SQLException e)
        {
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());

        }
        return max_PeriodicOrderID;
    }

    public int getMaxShortageOrderNumber()
    {

        int max_ShortageOrderID=0;
        try {
            PreparedStatement stmt44 = conn.prepareStatement("SELECT MAX(OrderID) FROM ShortageOrders");
            ResultSet rs = stmt44.executeQuery();
            while (rs.next()) {
                max_ShortageOrderID = rs.getInt(1) ;
            }

        } catch (SQLException e)
        {
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());

        }
        return max_ShortageOrderID;
    }

}