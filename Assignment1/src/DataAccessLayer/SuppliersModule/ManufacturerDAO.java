package DataAccessLayer.SuppliersModule;
import BusinessLayer.SuppliersModule.*;
import java.sql.*;
import java.lang.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.*;

public class ManufacturerDAO{
    private Map<String,Manufacturer> identityMap;
    PreparedStatement stmt;
    private Connection conn;
    private static ManufacturerDAO instance;

    private ManufacturerDAO() throws SQLException
    {
        this.conn=Database.getDataBaseInstance().getConnection();
        this.identityMap=new HashMap<>();
    }

    public static ManufacturerDAO getManufacturerInstance()
    {
        if (instance==null)
        {
            try
            {
                instance = new ManufacturerDAO();
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

    public void add(Object obj)
    {
        Manufacturer manufacturer=(Manufacturer) obj;
        stmt = null;
        try {
            stmt = conn.prepareStatement("INSERT INTO Manufacturers (ManufacturerName) VALUES (?)");
            stmt.setString(1,manufacturer.getManufacturer_name());
            stmt.executeUpdate();
            String key=manufacturer.getManufacturer_name();
            identityMap.put(key,manufacturer);
        }
        catch (SQLException e)
        {
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public void update(Object obj)
    {
        Manufacturer manufacturer=(Manufacturer) obj;
        stmt = null;
        try
        {
            stmt = conn.prepareStatement("UPDATE Manufacturers SET ManufacturerName = ? WHERE ManufacturerName = ?");
            stmt.setInt(1, Integer.parseInt(manufacturer.getManufacturer_name()));
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public void delete(String manufacturerName)
    {

        try
        {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM Manufacturers WHERE ManufacturerName = ?");
            stmt.setString(1, manufacturerName);
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public List<SuperProduct> getProductsByManufacturer(String manufacturerName)
    {
        List<SuperProduct> products = new ArrayList<>();

        // Retrieve all the SuperProducts that match the given manufacturerName
        String query = "SELECT * FROM BasicItem WHERE ManufacturerName = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, manufacturerName);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int barcode = rs.getInt("Barcode");
                String SuperProductName = rs.getString("BasicItemName");
                SuperProduct product = new SuperProduct(new Manufacturer(manufacturerName), SuperProductName, String.valueOf(barcode));
                product.setSupermarket_id(String.valueOf(barcode));
                products.add(product);
            }
        }
        catch (SQLException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        return products;
    }

    public List<Manufacturer> getManufacturerListBySupplierID(int supplierID) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        List<Manufacturer> manufacturers =null;
        boolean isEmpty=true;
        try
        {
            PreparedStatement isEmptystmt=conn.prepareStatement("SELECT * FROM Suppliers_Manufacturers");
            ResultSet rsIsEmpty=isEmptystmt.executeQuery();
            if (rsIsEmpty.next())
                isEmpty=false;
        }
        catch(SQLException e)
        {
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        if (!isEmpty) {
            String sql = "SELECT * FROM Manufacturers WHERE ManufacturerName IN (SELECT ManufacturerName FROM Suppliers_Manufacturers WHERE SupplierID = ?)";
            try {
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setInt(1, supplierID);
                rs = statement.executeQuery();
                manufacturers = new ArrayList<>();
                while (rs.next()) {
                    String manufacturerName = rs.getString("ManufacturerName");
                    Manufacturer manufacturer = new Manufacturer(manufacturerName);
                    manufacturers.add(manufacturer);
                }
            } catch (SQLException e) {
                System.out.println("An error occurred");
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
        return manufacturers;
    }

    public boolean doesManufacturerExist(String manufacturer_name)
    {
        try
        {
            stmt=conn.prepareStatement("SELECT * FROM Manufacturers WHERE ManufacturerName=?");
            stmt.setString(1,manufacturer_name);
            ResultSet rs= stmt.executeQuery();
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

    public void removeAllManufacturers()
    {
        try
        {
            Statement stmt = conn.createStatement();
            String query = "DELETE FROM Manufacturers;";
            stmt.executeUpdate(query);
        }
        catch (SQLException e)
        {
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
    public Manufacturer getManufacturerByManufacturerName(String name)
    {
        try
        {
            PreparedStatement stmt =conn.prepareStatement("SELECT * FROM Manufacturers WHERE ManufacturerName=?");
            stmt.setString(1,name);
            ResultSet rsManu=stmt.executeQuery();
            if (rsManu.next())
            {
                String manuname=rsManu.getString("ManufacturerName");
                return new Manufacturer(manuname);

            }
        }
        catch (SQLException e)
        {
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        return null;
    }

}
