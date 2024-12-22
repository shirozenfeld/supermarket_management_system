package DataAccessLayer.InventoryModule;

import BusinessLayer.InventoryModule.*;
import DataAccessLayer.SuppliersModule.Database;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class SuperLiDAO {
    protected Connection con;
    Map<Integer, String> workers;
    private static SuperLiDAO instance = null;
    public SuperLiDAO(){
        this.con= Database.getDataBaseInstance().getConnection();
        this.workers = new HashMap<>();
    }
    public static SuperLiDAO getInstance(){
        if(instance == null)
            instance = new SuperLiDAO();
        return instance;
    }
    public void update(int id, String password) {
        try{
            PreparedStatement stmt = con.prepareStatement("UPDATE Worker SET Password = ? WHERE WorkerID = ?");
            stmt.setString(1, password);
            stmt.setInt(2, id);
            stmt.executeUpdate();
            workers.put(id, password);
        }
        catch (SQLException e){
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

    }
    public void add(int id, String password){
        try {
            if (workers.containsKey(id)) {
                return;
            }
            PreparedStatement stmt = con.prepareStatement("INSERT INTO Worker (WorkerID, Password) " +
                    "VALUES (?,?)");
            stmt.setInt(1, id);
            stmt.setString(2, password);
            stmt.executeUpdate();
            workers.put(id, password);
        }
        catch (SQLException e){
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public void remove(int id){
        try {
            PreparedStatement stmt = con.prepareStatement("DELETE FROM Worker WHERE WorkerID = ?");
            stmt.setInt(1, id);
            stmt.executeUpdate();
            workers.remove(id);
        }
        catch (SQLException e){
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public Map<Integer, BasicItem> getAll(){
//        Map<Integer, String> allWorkers = new HashMap<>();
        Map<Integer, BasicItem> allbi = new HashMap<>();
        try {
//            Statement stmt = con.createStatement();
//            ResultSet rs = stmt.executeQuery("SELECT * FROM Worker");
//            while (rs.next()) {
//                if (workers.containsKey(rs.getInt("WorkerID"))) {
//                    allWorkers.put(rs.getInt("WorkerID"), workers.get(rs.getInt("WorkerID")));
//                    continue;
//                }
//                workers.put(rs.getInt("WorkerID"), rs.getString("Password"));
//                allWorkers.put(rs.getInt("WorkerID"), rs.getString("Password"));
//            }
            Statement stmt1 = con.createStatement();
            ResultSet rs1 = stmt1.executeQuery("SELECT * FROM BasicItem");
            while (rs1.next()) {
                PreparedStatement stmt2 = con.prepareStatement("SELECT CategoryID FROM BasicCategories WHERE Barcode = ?");
                stmt2.setInt(1, rs1.getInt("Barcode"));
                ResultSet rs2 = stmt2.executeQuery();
                LinkedList<Category> cats = new LinkedList<Category>();
                while (rs2.next()) {
                    Category cat = (Category) CategoryDAO.getInstance().getById(rs2.getInt("CategoryID"));
                    cats.add(cat);
                }
                BasicItem bi = new BasicItem(rs1.getString("BasicItemName"), rs1.getInt("Barcode"), rs1.getString("ManufacturerName"), rs1.getDouble("CostPrice"), cats, rs1.getInt("Counter"));
                allbi.put(bi.getCatalogNum(), bi);
            }
        }
        catch (SQLException e){
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return allbi;
    }

    public String getById(int id){
        try {
            if (workers.containsKey(id)) {
                return workers.get(id);
            }
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM Worker WHERE WorkerID = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                workers.put(id, rs.getString("Password"));
                return rs.getString("Password");
            }
        }
        catch (SQLException e){
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return "Not Exist";
    }
}
