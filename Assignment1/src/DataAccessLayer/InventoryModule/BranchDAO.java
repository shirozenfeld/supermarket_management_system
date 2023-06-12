package DataAccessLayer.InventoryModule;

import BusinessLayer.InventoryModule.*;
import DataAccessLayer.SuppliersModule.Database;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class BranchDAO extends AbstractDAO{
    Map<Integer, Branch> branches;
    private static BranchDAO instance = null;
    public BranchDAO(){
        this.branches = new HashMap<>();
        this.con= Database.getDataBaseInstance().getConnection();
    }

    public static BranchDAO getInstance(){
        if(instance == null)
            instance = new BranchDAO();
        return instance;
    }

    public void update(Object obj){

    }
    public void addToShortage(int barcode, int amountToOrder, String manuName){
        try {
            PreparedStatement stmt1 = con.prepareStatement("SELECT Status FROM ShortageReport WHERE Barcode = ?;");
            stmt1.setInt(1, barcode);
            ResultSet rs1 = stmt1.executeQuery();
            if(rs1.next()){
                String st = rs1.getString("Status");
                if(Objects.equals(st, "in_process")){
                    return;
                }
            }
            PreparedStatement stmt = con.prepareStatement("INSERT INTO ShortageReport (Barcode, Date, AmountToOrder, Status)\n" +
                    "VALUES (?,?,?,?)");
            stmt.setInt(1, barcode);
            stmt.setDate(2, Date.valueOf(LocalDate.now()));
            stmt.setInt(3, amountToOrder);
            stmt.setString(4, "in_process");
            stmt.executeUpdate();
        }
        catch (SQLException e){
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

    }
    public void add(Object obj){
        try {
            Branch branch = (Branch) obj;
            if (branches.containsKey(branch.getBranchID())) {
                return;
            }
            PreparedStatement stmt = con.prepareStatement("INSERT INTO Branch (BranchID, Name)\n" +
                    "VALUES (?,?)");
            stmt.setInt(1, branch.getBranchID());
            stmt.setString(2, branch.getBranchName());
            stmt.executeUpdate();
            branches.put(branch.getBranchID(), branch);
        }
        catch (SQLException e){
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
    public void remove(int id){

    }
    public ArrayList<Object> getAll(){
        ArrayList<Object> allBranches = new ArrayList<>();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Branch");
            while (rs.next()) {
                if (branches.containsKey(rs.getInt("BranchID"))) {
                    allBranches.add(branches.get(rs.getInt("BranchID")));
                    continue;
                }
                Branch branch = new Branch(rs.getInt("BranchID"), rs.getString("Name"));
                PreparedStatement stmt2 = con.prepareStatement("SELECT CategoryID FROM BranchCategories WHERE BranchID = ?");
                stmt2.setInt(1, rs.getInt("BranchID"));
                ResultSet rs2 = stmt2.executeQuery();
                Map<Integer, Category> cats = new HashMap<>();
                while (rs2.next()) {
                    Category cat = (Category) CategoryDAO.getInstance().getById(rs2.getInt("CategoryID"));
                    cats.put(cat.getcID(), cat);
                }
                branch.setCategories(cats);

                Map<Integer, InventoryItem> invis = new HashMap<>();
                PreparedStatement stmt3 = con.prepareStatement("SELECT * FROM InventoryItem WHERE BranchID = ?");
                stmt3.setInt(1, rs.getInt("BranchID"));
                ResultSet rs3 = stmt3.executeQuery();
                while (rs3.next()) {
                    InventoryItem invi = (InventoryItem) InventoryItemDAO.getInstance().getById(rs3.getInt("Barcode"));
                    invis.put(invi.getCatalogNum(), invi);
                }
                branch.setInventoryItems(invis);

                Map<Integer, Item> itemsInStore = new HashMap<>();
                PreparedStatement stmt4 = con.prepareStatement("SELECT * FROM Item WHERE BranchID = ? AND Location = 'store'");
                stmt4.setInt(1, rs.getInt("BranchID"));
                ResultSet rs4 = stmt4.executeQuery();
                while (rs4.next()) {
                    Item item = (Item) ItemDAO.getInstance().getById(rs4.getInt("ItemID"));
                    itemsInStore.put(item.getID(), item);
                }
                branch.setStoreInventory(itemsInStore);

                Map<Integer, Item> itemsInWH = new HashMap<>();
                PreparedStatement stmt5 = con.prepareStatement("SELECT * FROM Item WHERE BranchID = ? AND Location = 'warehouse'");
                stmt5.setInt(1, rs.getInt("BranchID"));
                ResultSet rs5 = stmt5.executeQuery();
                while (rs5.next()) {
                    Item item = (Item) ItemDAO.getInstance().getById(rs5.getInt("ItemID"));
                    itemsInWH.put(item.getID(), item);
                }
                branch.setWarehouseInventory(itemsInWH);

                Map<Integer, Item> damagedItems = new HashMap<>();
                PreparedStatement stmt6 = con.prepareStatement("SELECT * FROM Item WHERE BranchID = ? AND DamageType <> 'null'");
                stmt6.setInt(1, rs.getInt("BranchID"));
                ResultSet rs6 = stmt6.executeQuery();
                while (rs6.next()) {
                    Item item = (Item) ItemDAO.getInstance().getById(rs6.getInt("ItemID"));
                    damagedItems.put(item.getID(), item);
                }
                branch.setDamagedItems(damagedItems);

                Map<Integer, InventoryItem> shoratgeItems = new HashMap<>();
                PreparedStatement stmt7 = con.prepareStatement("SELECT *\n" +
                        "FROM InventoryItem\n" +
                        "WHERE MinimumAmount >= (SELECT Count(*) as howmany\n" +
                        "FROM Item\n" +
                        "WHERE BranchID = ? AND InventoryItem.Barcode = Item.Barcode);");
                stmt7.setInt(1, rs.getInt("BranchID"));
                ResultSet rs7 = stmt7.executeQuery();
                while (rs7.next()) {
                    InventoryItem invi = (InventoryItem) InventoryItemDAO.getInstance().getById(rs7.getInt("Barcode"));
                    invis.put(invi.getCatalogNum(), invi);
                    shoratgeItems.put(invi.getCatalogNum(), invi);
                }
                branch.setShortageItems(shoratgeItems);

                branches.put(branch.getBranchID(), branch);
                allBranches.add(branch);
            }
        }
        catch (SQLException e){
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return allBranches;
    }
    public Object getById(int id){
        try {
            if (branches.containsKey(id)) {
                return branches.get(id);
            }
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM Branch WHERE BranchID = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Branch branch = new Branch(id, rs.getString("Name"));
                PreparedStatement stmt2 = con.prepareStatement("SELECT CategoryID FROM BranchCategories WHERE BranchID = ?");
                stmt2.setInt(1, id);
                ResultSet rs2 = stmt2.executeQuery();
                Map<Integer, Category> cats = new HashMap<>();
                while (rs2.next()) {
                    Category cat = (Category) CategoryDAO.getInstance().getById(rs2.getInt("CategoryID"));
                    cats.put(cat.getcID(), cat);
                }
                branch.setCategories(cats);

                Map<Integer, InventoryItem> invis = new HashMap<>();
                PreparedStatement stmt3 = con.prepareStatement("SELECT * FROM InventoryItem WHERE BranchID = ?");
                stmt3.setInt(1, id);
                ResultSet rs3 = stmt3.executeQuery();
                while (rs3.next()) {
                    InventoryItem invi = (InventoryItem) InventoryItemDAO.getInstance().getById(rs3.getInt("Barcode"));
                    invis.put(invi.getCatalogNum(), invi);
                }
                branch.setInventoryItems(invis);

                Map<Integer, Item> itemsInStore = new HashMap<>();
                PreparedStatement stmt4 = con.prepareStatement("SELECT * FROM Item WHERE BranchID = ? AND Location = 'store'");
                stmt4.setInt(1, id);
                ResultSet rs4 = stmt4.executeQuery();
                while (rs4.next()) {
                    Item item = (Item) ItemDAO.getInstance().getById(rs4.getInt("ItemID"));
                    itemsInStore.put(item.getID(), item);
                }
                branch.setStoreInventory(itemsInStore);

                Map<Integer, Item> itemsInWH = new HashMap<>();
                PreparedStatement stmt5 = con.prepareStatement("SELECT * FROM Item WHERE BranchID = ? AND Location = 'warehouse'");
                stmt5.setInt(1, id);
                ResultSet rs5 = stmt5.executeQuery();
                while (rs5.next()) {
                    Item item = (Item) ItemDAO.getInstance().getById(rs5.getInt("ItemID"));
                    itemsInWH.put(item.getID(), item);
                }
                branch.setWarehouseInventory(itemsInWH);

                Map<Integer, Item> damagedItems = new HashMap<>();
                PreparedStatement stmt6 = con.prepareStatement("SELECT * FROM Item WHERE BranchID = ? AND DamageType <> 'null'");
                stmt6.setInt(1, id);
                ResultSet rs6 = stmt6.executeQuery();
                while (rs6.next()) {
                    Item item = (Item) ItemDAO.getInstance().getById(rs6.getInt("ItemID"));
                    damagedItems.put(item.getID(), item);
                }
                branch.setDamagedItems(damagedItems);

                Map<Integer, InventoryItem> shoratgeItems = new HashMap<>();
                PreparedStatement stmt7 = con.prepareStatement("SELECT *\n" +
                        "FROM InventoryItem\n" +
                        "WHERE MinimumAmount >= (SELECT Count(*) as howmany\n" +
                        "FROM Item\n" +
                        "WHERE BranchID = ? AND InventoryItem.Barcode = Item.Barcode);");
                stmt7.setInt(1, id);
                ResultSet rs7 = stmt7.executeQuery();
                while (rs7.next()) {
                    InventoryItem invi = (InventoryItem) InventoryItemDAO.getInstance().getById(rs7.getInt("Barcode"));
                    invis.put(invi.getCatalogNum(), invi);
                    shoratgeItems.put(invi.getCatalogNum(), invi);
                }
                branch.setShortageItems(shoratgeItems);

                branches.put(branch.getBranchID(), branch);
                return branch;
            }
        }
        catch (SQLException e){
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void removeAll(){
        try{
            Statement stmt = con.createStatement();
            String query = "DELETE FROM Branch;";
            stmt.executeUpdate(query);
            Statement stmt1 = con.createStatement();
            String query1 = "DELETE FROM ShortageReport;";
            stmt1.executeUpdate(query1);
        }
        catch (SQLException e){
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

}
