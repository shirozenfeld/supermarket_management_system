package DataAccessLayer.InventoryModule;

import BusinessLayer.InventoryModule.Category;
import BusinessLayer.InventoryModule.InventoryItem;
import DataAccessLayer.SuppliersModule.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryDAO extends AbstractDAO{
    Map<Integer, Category> categories;
    private static CategoryDAO instance = null;
    public static CategoryDAO getInstance(){
        if(instance == null)
            instance = new CategoryDAO();
        return instance;
    }

    public CategoryDAO(){
        this.categories = new HashMap<>();
        this.con= Database.getDataBaseInstance().getConnection();
    }
    @Override
    public void update(Object obj){

    }

    @Override
    public void add(Object obj){
        try {
            Category cat = (Category) obj;
            if (categories.containsKey(cat.getcID())) {
                return;
            }
            PreparedStatement stmt = con.prepareStatement("INSERT INTO Categories (CategoryID, Name, FatherID) " +
                    "VALUES (?,?,?)");
            stmt.setInt(1, cat.getcID());
            stmt.setString(2, cat.getcName());
            if (cat.getMainCat() == null)
                stmt.setInt(3, 0);
            else
                stmt.setInt(3, cat.getMainCat().getcID());
            stmt.executeUpdate();
            PreparedStatement stmt1 = con.prepareStatement("INSERT INTO BranchCategories (BranchID, CategoryID) " +
                    "VALUES (?,?)");
            stmt1.setInt(1, cat.getcID() % 10);
            stmt1.setInt(2, cat.getcID());
            stmt1.executeUpdate();
            categories.put(cat.getcID(), cat);
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
            PreparedStatement stmt = con.prepareStatement("DELETE FROM Category WHERE CategoryID = ?");
            stmt.setInt(1, id);
            stmt.executeUpdate();
            categories.remove(id);
        }
        catch (SQLException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    @Override
    public ArrayList<Object> getAll(){
        ArrayList<Object> allCategories = new ArrayList<>();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Categories");
            while (rs.next()) {
                if (categories.containsKey(rs.getInt("CategoryID"))) {
                    allCategories.add(categories.get(rs.getInt("CategoryID")));
                    continue;
                }
                Category main = (Category) getById(rs.getInt("FatherID"));
                Category cat = new Category(rs.getInt("CategoryID"), rs.getString("Name"), main);
                List<InventoryItem> inviL = new ArrayList<>();
                PreparedStatement stmt4 = con.prepareStatement("SELECT * FROM BasicCategories WHERE CategoryID = ?");
                stmt4.setInt(1, rs.getInt("CategoryID"));
                ResultSet rs4 = stmt4.executeQuery();
                while (rs4.next()) {
                    InventoryItem invi = (InventoryItem) InventoryItemDAO.getInstance().getById(rs4.getInt("Barcode"));
                    inviL.add(invi);
                }
                cat.setcItems(inviL);
                allCategories.add(cat);
                categories.put(cat.getcID(), cat);
            }
        }
        catch (SQLException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return allCategories;
    }

    @Override
    public Object getById(int id){
        try {
            if (id == 0) {
                return null;
            }
            if (categories.containsKey(id)) {
                return categories.get(id);
            }
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM Categories WHERE CategoryID = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Category main = (Category) getById(rs.getInt("FatherID"));
                Category cat = new Category(rs.getInt("CategoryID"), rs.getString("Name"), main);
                List<InventoryItem> inviL = new ArrayList<>();
                PreparedStatement stmt4 = con.prepareStatement("SELECT * FROM BasicCategories WHERE CategoryID = ?");
                stmt4.setInt(1, rs.getInt("CategoryID"));
                ResultSet rs4 = stmt4.executeQuery();
                while (rs4.next()) {
                    InventoryItem invi = (InventoryItem) InventoryItemDAO.getInstance().getById(rs4.getInt("Barcode"));
                    inviL.add(invi);
                }
                cat.setcItems(inviL);
                categories.put(cat.getcID(), cat);
                return cat;
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
            String query = "DELETE FROM Categories;";
            stmt.executeUpdate(query);
            Statement stmt1 = con.createStatement();
            String query1 = "DELETE FROM BranchCategories;";
            stmt1.executeUpdate(query1);
        }
        catch (SQLException e){
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
