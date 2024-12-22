package DataAccessLayer.SuppliersModule;
import BusinessLayer.BusinessFacade;
import BusinessLayer.SuppliersModule.*;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.sql.*;
import java.lang.*;
import java.util.HashMap;
import java.util.Map;
import java.util.*;
public class SupplierProductDAO
{
    private Map<String,SupplierProduct> identityMap;
    //PreparedStatement stmt;
    int int_barcode, int_supplier_id;
    private Connection conn;
    private static SupplierProductDAO instance;
    private Map<String,Integer> suppliers_catalogNumber_counters;
    private SupplierProductDAO() throws SQLException
    {
        this.conn=Database.getDataBaseInstance().getConnection();
        this.identityMap=new HashMap<>();
        this.suppliers_catalogNumber_counters=new HashMap<>();
    }

    public static SupplierProductDAO getSupplierProductInstance()
    {
        if (instance==null)
        {
            try
            {
                instance = new SupplierProductDAO();
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

    public void add(SupplierProduct supplierProduct) {
        //stmt = null;
        try
        {
            int catalogNumber=1;
            String supplierID=supplierProduct.getSupplierID();
            PreparedStatement stmt1 = conn.prepareStatement("INSERT INTO SupplierProduct (Barcode, SupplierID,CatalogNumber,Amount,UnitPrice) VALUES (?,?,?,?,?)");
            stmt1.setInt(1, Integer.parseInt(supplierProduct.getSupermarket_id()));
            stmt1.setInt(2, Integer.parseInt(supplierProduct.getSupplierID()));
            if (suppliers_catalogNumber_counters.containsKey(supplierProduct.getSupplierID()))
            {
                catalogNumber=suppliers_catalogNumber_counters.get(supplierProduct.getSupplierID())+1;
                suppliers_catalogNumber_counters.put(supplierID,catalogNumber);
                //supplierProduct.setCatalog_number(catalogNumber);
            }
            else
            {
                suppliers_catalogNumber_counters.put(supplierProduct.getSupplierID(),1);
                //supplierProduct.setCatalog_number(1);
            }
            stmt1.setInt(3, catalogNumber);
            stmt1.setInt(4, supplierProduct.getAmount());
            stmt1.setDouble(5, supplierProduct.getUnit_price());
            stmt1.executeUpdate();
            String key = supplierProduct.getSupermarket_id() + "," + supplierProduct.getSupplierID();
            if (suppliers_catalogNumber_counters.containsKey(supplierProduct.getSupplierID()))
                supplierProduct.setCatalog_number(catalogNumber);

            else
                supplierProduct.setCatalog_number(1);
            identityMap.put(key, supplierProduct);

        }
        catch (SQLException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        String manufactorerName="";
        try
        {

            PreparedStatement stmt8 = conn.prepareStatement("SELECT ManufacturerName FROM BasicItem WHERE Barcode=?");
            stmt8.setInt(1,Integer.parseInt(supplierProduct.getSupermarket_id()));
            ResultSet rs=stmt8.executeQuery();
            manufactorerName=rs.getString("ManufacturerName");

        }
        catch (SQLException e)
        {
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        try
        {

            PreparedStatement stmt2 = conn.prepareStatement("SELECT * FROM Suppliers_Manufacturers WHERE SupplierID=? AND ManufacturerName=?");
            stmt2.setInt(1,Integer.parseInt(supplierProduct.getSupplierID()));
            stmt2.setString(2,manufactorerName);
            ResultSet rs = stmt2.executeQuery();
            if (rs.next())
            {
                int counter = rs.getInt("Counter");
                PreparedStatement preparedStatement = conn.prepareStatement("Update Suppliers_Manufacturers SET Counter=? WHERE SupplierID=? AND ManufacturerName=?");
                preparedStatement.setInt(1, counter + 1);
                preparedStatement.setInt(2, Integer.parseInt(supplierProduct.getSupplierID()));
                preparedStatement.setString(3, manufactorerName);
                preparedStatement.executeUpdate();
            }
            else
            {
            PreparedStatement stmtInsert = conn.prepareStatement("INSERT INTO Suppliers_Manufacturers (SupplierID, ManufacturerName, Counter) VALUES (?,?,?)");
            stmtInsert.setInt(1, Integer.parseInt(supplierProduct.getSupplierID()));
            stmtInsert.setString(2,manufactorerName);
            stmtInsert.setInt(3, 1);
            stmtInsert.executeUpdate();

            }


        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

   }



    public void update(Object obj)
    {
        SupplierProduct supplierProduct=(SupplierProduct)obj;
//stmt = null;
        try
        {
            PreparedStatement stmt3 = conn.prepareStatement("UPDATE SupplierProduct SET CatalogNumber=?,Amount=?,UnitPrice=? WHERE Barcode = ? AND SupplierID=?");
            stmt3.setInt(1, supplierProduct.getCatalog_number());
            stmt3.setInt(2, supplierProduct.getAmount());
            stmt3.setDouble(3,supplierProduct.getUnit_price());
            stmt3.setInt(4, Integer.parseInt(supplierProduct.getSupermarket_id()));
            stmt3.setInt(5, Integer.parseInt(supplierProduct.getSupplierID()));
            stmt3.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public void delete(String barcode_string,String supplier_id)
    {
        int barcode = Integer.parseInt((barcode_string));
        int supplier_id_int =Integer.parseInt(supplier_id);
        try
        {
            PreparedStatement stmt4 = conn.prepareStatement("DELETE FROM SupplierProduct WHERE Barcode = ? AND SupplierID=?");
            stmt4.setInt(1, barcode);
            stmt4.setInt(2,supplier_id_int);
            stmt4.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        String manufacturerName="";
        try
        {
            PreparedStatement manufacturerStmt=conn.prepareStatement("SELECT ManufacturerName FROM BasicItem WHERE Barcode=?");
            manufacturerStmt.setInt(1,barcode);
            ResultSet rsManufacturer=manufacturerStmt.executeQuery();
            if (rsManufacturer.next())
                manufacturerName=rsManufacturer.getString("ManufacturerName");
        }
        catch (SQLException e)
        {
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        try {
            PreparedStatement stmtSupplierManufacturer = conn.prepareStatement("SELECT * FROM Suppliers_Manufacturers WHERE SupplierID=? AND ManufacturerName =?");
            stmtSupplierManufacturer.setInt(1, supplier_id_int);
            stmtSupplierManufacturer.setString(2, manufacturerName);
            ResultSet rs = stmtSupplierManufacturer.executeQuery();
            if (rs.next()) {
                int counter = rs.getInt("Counter");
                if (counter > 1) {

                    PreparedStatement stmtUpdateCounter = conn.prepareStatement("UPDATE Suppliers_Manufacturers SET Counter WHERE SupplierID=? AND ManufacturerName =?  ");
                    stmtUpdateCounter.setInt(1, counter - 1);
                    stmtUpdateCounter.setInt(2, supplier_id_int);
                    stmtUpdateCounter.setString(3, manufacturerName);
                    stmtUpdateCounter.executeUpdate();
                }
                else if (counter == 1)
                {
                    PreparedStatement stmtDeleteCounter = conn.prepareStatement("DELETE FROM Suppliers_Manufacturers WHERE SupplierID=? AND ManufacturerName =?");
                    stmtDeleteCounter.setInt(1, supplier_id_int);
                    stmtDeleteCounter.setString(2, manufacturerName);
                    stmtDeleteCounter.executeUpdate();
                }

            }
        }

        catch (SQLException e)
        {
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }


    }

    public SupplierProduct getSupplierProduct(String barcode, String supplier_id)
    {

        int_barcode = Integer.parseInt(barcode);
        int_supplier_id = Integer.parseInt(supplier_id);
        PreparedStatement stmt5 = null;
        try {
            stmt5 = conn.prepareStatement("SELECT * FROM SupplierProduct WHERE Barcode=? AND SupplierID=?");
            stmt5.setInt(1, int_barcode);
            stmt5.setInt(2, int_supplier_id);
            ResultSet rs = stmt5.executeQuery();
            if (rs.next())
            {
                AddSupplierProductToIdentityMap(rs,barcode,supplier_id);
                if (identityMap.containsKey(barcode+","+supplier_id))
                    return identityMap.get(barcode+","+supplier_id);
            }
        }
        catch (SQLException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<SupplierProduct> getSupplierProductsBySupplierID(String supplier_id)
    {
        List<SupplierProduct> suppliersProductsList=new ArrayList<>();
        int int_supplier_id = Integer.parseInt(supplier_id);
        PreparedStatement stmt6 = null;
        try {
            stmt6 = conn.prepareStatement("SELECT * FROM SupplierProduct WHERE SupplierID=?");
            stmt6.setInt(1, int_supplier_id);
            ResultSet rs = stmt6.executeQuery();

            while(rs.next())
            {
                int_barcode=rs.getInt("Barcode");
                suppliersProductsList.add(getSupplierProduct(String.valueOf(int_barcode),supplier_id));
            }
            }
        catch (SQLException e)
        {
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return suppliersProductsList;
    }

    public List<SupplierProduct> getSupplierProductsByBarcode(String Barcode)
    {
        List<SupplierProduct> suppliersProductsList=null;
        int_barcode = Integer.parseInt(Barcode);
        PreparedStatement stmt7 = null;
        try {
            stmt7 = conn.prepareStatement("SELECT * FROM SupplierProduct WHERE Barcode=?");
            stmt7.setInt(1, int_barcode);
            ResultSet rs = stmt7.executeQuery();
            if (rs.next())
            {
                SupplierDAO supplierDAO=SupplierDAO.getSupplierInstance();
                suppliersProductsList = new ArrayList<>();
                while(rs.next())
                {
                    int_supplier_id=rs.getInt("Barcode");
                    suppliersProductsList.add(getSupplierProduct(Barcode,String.valueOf(int_supplier_id)));
                }
                return suppliersProductsList;
            }

        }
        catch (SQLException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return suppliersProductsList;
    }

    public void AddSupplierProductToIdentityMap(ResultSet rs,String barcode, String supplier_id)
    {
        Card card=null;
        Contact contact=null;
        Contract contract=null;
        SupplierProduct supplierProduct=null;
        try
        {
            // try - supplierProduct
            supplierProduct = new SupplierProduct(barcode,rs.getInt("CatalogNumber"),rs.getInt("Amount"),rs.getDouble("UnitPrice"),supplier_id);
//            supplierProduct.setCatalog_number();


        }
        catch(SQLException e)
        {
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        this.identityMap.put(barcode+","+supplier_id,supplierProduct);
    }
    public void deleteSupplierProductsBySupplierID(String  supplierID)
    {
        int supplier_id =Integer.parseInt((String)supplierID);
        try
        {
            PreparedStatement stmt8 = conn.prepareStatement("DELETE FROM SupplierProduct WHERE SupplierID=?");
            stmt8.setInt(1, supplier_id);
            stmt8.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        for (Map.Entry<String, SupplierProduct> entry : identityMap.entrySet())
        {
            String supplierid_only = entry.getKey().split(",")[1].trim();
            if (supplierid_only.equals(supplierID))
                identityMap.remove(entry.getKey());
        }
        try
        {
            PreparedStatement stmtDeleteFromSuppliers_Manufacturers=conn.prepareStatement("DELETE FROM Suppliers_Manufacturers WHERE SupplierID=?");
            stmtDeleteFromSuppliers_Manufacturers.setInt(1,supplier_id);
            stmtDeleteFromSuppliers_Manufacturers.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public void deleteSupplierProductsByBarcode(String barcode)
    {
        int barcode_int =Integer.parseInt(barcode);
        try
        {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM SupplierProduct WHERE Barcode=?");
            stmt.setInt(1, barcode_int);
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        for (Map.Entry<String, SupplierProduct> entry : identityMap.entrySet())
        {
            String barcode_only = entry.getKey().split(",")[0].trim();
            if (barcode_only.equals(barcode))
                identityMap.remove(entry.getKey());
        }
        String manufacturerName="";
        try
        {
            PreparedStatement manufacturerStmt=conn.prepareStatement("SELECT ManufacturerName FROM BasicItem WHERE Barcode=?");
            manufacturerStmt.setInt(1,barcode_int);
            ResultSet rsManufacturer=manufacturerStmt.executeQuery();
            if (rsManufacturer.next())
                manufacturerName=rsManufacturer.getString("ManufacturerName");
        }
        catch (SQLException e)
        {
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        try
        {
            PreparedStatement stmtSupplierManufacturer = conn.prepareStatement("SELECT * FROM Suppliers_Manufacturers WHERE ManufacturerName =?");
            stmtSupplierManufacturer.setString(1,manufacturerName);
            ResultSet rs= stmtSupplierManufacturer.executeQuery();
            if (rs.next())
            {
                int counter=rs.getInt("Counter");
                if (counter>1)
                {
                    try
                    {
                        PreparedStatement stmtUpdateCounter=conn.prepareStatement("UPDATE Suppliers_Manufacturers SET Counter WHERE SupplierID=? AND ManufacturerName =?  ");
                        stmtUpdateCounter.setInt(1,counter-1);
                        stmtUpdateCounter.setInt(2,rs.getInt("SupplierID"));
                        stmtUpdateCounter.setString(3,manufacturerName);
                    }
                    catch (SQLException e)
                    {
                        System.out.println("An error occurred");
                        e.printStackTrace();
                        System.out.println(e.getMessage());
                    }
                }
                else if (counter==1)
                {
                    try
                    {
                        PreparedStatement stmtUpdateCounter=conn.prepareStatement("DELETE FROM Suppliers_Manufacturers WHERE SupplierID=? AND ManufacturerName =?");
                        stmtUpdateCounter.setInt(1,rs.getInt("SupplierID"));
                        stmtUpdateCounter.setString(2, manufacturerName);
                        stmtUpdateCounter.executeUpdate();
                    }
                    catch (SQLException e)
                    {
                        System.out.println("An error occurred");
                        e.printStackTrace();
                        System.out.println(e.getMessage());
                    }

                }
            }

        }
        catch (SQLException e)
        {
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public void removeAllSupplierProduct()
    {
        try
        {
            Statement stmt2 = conn.createStatement();
            String query2 = "DELETE FROM Suppliers_Manufacturers";
            stmt2.executeUpdate(query2);
            Statement stmt1 = conn.createStatement();
            String query1 = "DELETE FROM SupplierProduct";
            stmt1.executeUpdate(query1);
        }
        catch (SQLException e)
        {
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}

