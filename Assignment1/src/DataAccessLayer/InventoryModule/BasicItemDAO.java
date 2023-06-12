package DataAccessLayer.InventoryModule;
import BusinessLayer.InventoryModule.BasicItem;
import BusinessLayer.InventoryModule.Category;
import BusinessLayer.InventoryModule.PeriodicOrder;
import DataAccessLayer.SuppliersModule.Database;

import java.sql.*;
import java.util.*;

public class BasicItemDAO extends AbstractDAO {
    Map<Integer, BasicItem> basics;
    private static BasicItemDAO instance = null;
    public BasicItemDAO(){
        this.basics = new HashMap<>();
        this.con= Database.getDataBaseInstance().getConnection();
    }
    public static BasicItemDAO getInstance(){
        if(instance == null)
            instance = new BasicItemDAO();
        return instance;
    }
    public void removeAll(){
        try{
            Statement stmt = con.createStatement();
            String query = "DELETE FROM BasicItem;";
            stmt.executeUpdate(query);
            Statement stmt1 = con.createStatement();
            String query1 = "DELETE FROM BasicCategories;";
            stmt1.executeUpdate(query1);
        }
        catch (SQLException e){
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
    @Override
    public void update(Object obj){
        try {
            BasicItem bi = (BasicItem) obj;
            PreparedStatement stmt = con.prepareStatement("UPDATE BasicItem SET CostPrice = ?, Counter = ? WHERE Barcode = ?");
            stmt.setDouble(1, bi.getCostPrice());
            stmt.setDouble(2, bi.getIDcounter());
            stmt.setDouble(3, bi.getCatalogNum());
            stmt.executeUpdate();
            basics.put(bi.getCatalogNum(), bi);
        }
        catch (SQLException e){
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void add(Object obj){
        try {
            BasicItem bi = (BasicItem) obj;
            if (basics.containsKey(bi.getCatalogNum())) {
                return;
            }
            PreparedStatement stmt = con.prepareStatement("INSERT INTO BasicItem (Barcode, BasicItemName, ManufacturerName, CostPrice, Counter) " +
                    "VALUES (?,?,?,?,?)");

            stmt.setInt(1, bi.getCatalogNum());
            stmt.setString(2, bi.getName());
            stmt.setString(3, bi.getManufacturer());
            stmt.setDouble(4, bi.getCostPrice());
            stmt.setInt(5, bi.getIDcounter());
            stmt.executeUpdate();
            basics.put(bi.getCatalogNum(), bi);
            for (int i = 0; i < bi.getItemCategories().size(); i++) {
                PreparedStatement stmt1 = con.prepareStatement("INSERT INTO BasicCategories (Barcode, CategoryID) " +
                        "VALUES (?,?)");
                stmt1.setInt(1, bi.getCatalogNum());
                stmt1.setInt(2, bi.getItemCategories().get(i).getcID());
                stmt1.executeUpdate();
            }
        }
        catch (SQLException e){
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void remove(int id){
        try {
            PreparedStatement stmt = con.prepareStatement("DELETE FROM BasicItem WHERE Barcode = ?");
            stmt.setInt(1, id);
            stmt.executeUpdate();
            basics.remove(id);
        }
        catch (SQLException e){
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    @Override
    public ArrayList<Object> getAll(){
        ArrayList<Object> allBasic = new ArrayList<>();
        try {
            Statement stmt1 = con.createStatement();
            ResultSet rs1 = stmt1.executeQuery("SELECT * FROM BasicItem");
            while (rs1.next()) {
                if (basics.containsKey(rs1.getInt("Barcode"))) {
                    allBasic.add(basics.get(rs1.getInt("Barcode")));
                    continue;
                }
                PreparedStatement stmt2 = con.prepareStatement("SELECT CategoryID FROM BasicCategories WHERE Barcode = ?");
                stmt2.setInt(1, rs1.getInt("Barcode"));
                ResultSet rs2 = stmt2.executeQuery();
                LinkedList<Category> cats = new LinkedList<Category>();
                while (rs2.next()) {
                    Category cat = (Category) CategoryDAO.getInstance().getById(rs2.getInt("CategoryID"));
                    cats.add(cat);
                }
                BasicItem bi = new BasicItem(rs1.getString("BasicItemName"), rs1.getInt("Barcode"), rs1.getString("ManufacturerName"), rs1.getDouble("CostPrice"), cats, rs1.getInt("Counter"));
                allBasic.add(bi);
                basics.put(bi.getCatalogNum(), bi);
            }
        }
        catch (SQLException e){
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return allBasic;
    }

    @Override
    public Object getById(int id){
        try {
            if (basics.containsKey(id)) {
                return basics.get(id);
            }
            PreparedStatement stmt1 = con.prepareStatement("SELECT * FROM BasicItem WHERE Barcode = ?");
            stmt1.setInt(1, id);
            ResultSet rs1 = stmt1.executeQuery();
            PreparedStatement stmt2 = con.prepareStatement("SELECT CategoryID FROM BasicCategories WHERE Barcode = ?");
            stmt2.setInt(1, id);
            ResultSet rs2 = stmt2.executeQuery();
            LinkedList<Category> cats = new LinkedList<Category>();
            while (rs2.next()) {
                Category cat = (Category) CategoryDAO.getInstance().getById(rs2.getInt("CategoryID"));
                cats.add(cat);
            }
            if (rs1.next()) {
                BasicItem bi = new BasicItem(rs1.getString("BasicItemName"), rs1.getInt("Barcode"), rs1.getString("ManufacturerName"), rs1.getDouble("CostPrice"), cats, rs1.getInt("Counter"));
                basics.put(bi.getCatalogNum(), bi);
                return bi;
            }
        }
        catch (SQLException e){
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return null;
    }
}
