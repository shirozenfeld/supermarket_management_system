package DataAccessLayer.SuppliersModule;
import BusinessLayer.SuppliersModule.*;

import java.sql.*;
import java.lang.*;
import java.util.*;
import BusinessLayer.*;

import javax.print.attribute.standard.JobKOctets;
import javax.sql.DataSource;
import javax.xml.crypto.Data;
import javax.xml.transform.Result;
import java.util.HashMap;


public class SuperProductDAO
{
    private Connection conn;
    private HashMap<String, SuperProduct> identityMap;
    int int_barcode;
    private static SuperProductDAO instance;
    PreparedStatement stmt;
    private SuperProductDAO() throws SQLException
    {
        this.conn=Database.getDataBaseInstance().getConnection();
        this.identityMap=new HashMap<>();
    }

    public static SuperProductDAO getSuperProductInstance()
    {
        if (instance==null)
        {
            try
            {
                instance = new SuperProductDAO();
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
        public void update(SuperProduct superProduct)
        {
        PreparedStatement stmt = null;
        try
        {
            stmt = conn.prepareStatement("UPDATE BasicItem SET Barcode = ?, BasicItemName = ?, ManufacturerName = ? WHERE Barcode = ? ");
            stmt.setInt(1, Integer.parseInt(superProduct.getSupermarket_id()));
            stmt.setString(2, superProduct.getProduct_name());
            stmt.setString(3, superProduct.getManufacturer().getManufacturer_name());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public void delete(String supermarket_id)
    {
        //delete from basic item
        int barcode = Integer.parseInt(supermarket_id);
        try
        {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM BasicItem WHERE Barcode = ?");
            stmt.setInt(1, barcode);
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        //delete from SupplierProduct
        SupplierProductDAO supplierProductDAO=SupplierProductDAO.getSupplierProductInstance();
        supplierProductDAO.deleteSupplierProductsByBarcode(supermarket_id);
        identityMap.remove(barcode);
    }


    public SuperProduct getSuperProductByBarcode(String barcode)
    {
        if(identityMap.containsKey(barcode)) {
            return identityMap.get(barcode);
        }
        int_barcode = Integer.parseInt(barcode);
        Manufacturer manufacturer;
        SuperProduct superProduct = null;
        PreparedStatement stmt = null;
        try
        {
            stmt = conn.prepareStatement("SELECT * FROM BasicItem WHERE Barcode=?");
            stmt.setInt(1, int_barcode);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                stmt = conn.prepareStatement("SELECT manufacturerName FROM BasicItem WHERE Barcode=?");
                stmt.setInt(1, int_barcode);
                ResultSet res = stmt.executeQuery();
                manufacturer = new Manufacturer(res.getString(1));
                stmt = conn.prepareStatement("SELECT BasicItemName FROM BasicItem WHERE Barcode=?");
                stmt.setInt(1, int_barcode);
                ResultSet result = stmt.executeQuery();

                superProduct = new SuperProduct(manufacturer, res.getString(1),barcode);
                superProduct.setProduct_name(rs.getString("BasicItemName"));
                List<Supplier> suppliersList=getSupplierListBySuperProduct(barcode);
                superProduct.setSuppliers(suppliersList);
                //TODO
            }
        }
        catch (SQLException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return superProduct;
    }

    public List<Supplier> getSupplierListBySuperProduct(String barcode)
    {
        List<Supplier> supplierList = new ArrayList<>();
        stmt = null;
        try {
            stmt = conn.prepareStatement("SELECT SupplierID FROM SupplierProduct WHERE barcode = ?");
            stmt.setInt(1, Integer.parseInt(barcode));
            ResultSet rs = stmt.executeQuery();
            SupplierDAO supplierDAO = SupplierDAO.getSupplierInstance();
            while (rs.next())
            {
                String supplierId = String.valueOf(rs.getInt("SupplierID"));
                Supplier supplier = supplierDAO.getSupplierBySupplierID(supplierId);
                if (supplier != null)
                {
                    supplierList.add(supplier);
                }
            }
        }
        catch (SQLException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return supplierList;
    }

    public Map<String,SuperProduct> getAllSuperProducts()
    {
        Map<String, SuperProduct> superProductsMap=new HashMap<>();
        try {
            stmt = conn.prepareStatement("SELECT Barcode FROM BasicItem");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String barcode= String.valueOf(rs.getInt("Barcode"));
                SuperProduct superProduct = getSuperProductByBarcode(barcode);
                superProductsMap.put(barcode,superProduct);
            }
        }
        catch (SQLException e)
        {
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return superProductsMap;
    }


}