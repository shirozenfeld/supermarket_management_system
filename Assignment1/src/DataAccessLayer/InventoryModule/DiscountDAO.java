package DataAccessLayer.InventoryModule;

import BusinessLayer.InventoryModule.Category;
import BusinessLayer.InventoryModule.Discount;
import DataAccessLayer.SuppliersModule.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DiscountDAO extends AbstractDAO{
    Map<Integer, Discount> Discounts;
    private static DiscountDAO instance = null;
    public DiscountDAO(){
        this.Discounts = new HashMap<>();
        this.con= Database.getDataBaseInstance().getConnection();
    }

    public static DiscountDAO getInstance(){
        if(instance == null)
            instance = new DiscountDAO();
        return instance;
    }

    public void update(Object obj){

    }
    public void add(Object obj){
        try {
            Discount discount = (Discount) obj;
            if (Discounts.containsKey(discount.getDiscountID())) {
                return;
            }
            PreparedStatement stmt = con.prepareStatement("INSERT INTO Discount (DiscountID, StartDate, EndDate, Rate) " +
                    "VALUES (?,?,?,?)");
            stmt.setInt(1, discount.getDiscountID());
            stmt.setString(2, discount.getStartDate().toString());
            stmt.setString(3, discount.getEndDate().toString());
            stmt.setDouble(4, discount.getDiscountRate());
            stmt.executeUpdate();
            Discounts.put(discount.getDiscountID(), discount);
        }
        catch (SQLException e){
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public void remove(int id){
        try {
            PreparedStatement stmt = con.prepareStatement("DELETE FROM Discount WHERE DiscountID = ?");
            stmt.setInt(1, id);
            stmt.executeUpdate();
            Discounts.remove(id);
        }
        catch (SQLException e){
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<Object> getAll(){
        ArrayList<Object> allDiscounts = new ArrayList<>();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Discount");
            while (rs.next()) {
                if (Discounts.containsKey(rs.getInt("DiscountID"))) {
                    allDiscounts.add(Discounts.get(rs.getInt("DiscountID")));
                    continue;
                }
                Discount discount = new Discount(LocalDate.parse(rs.getString("StartDate")), LocalDate.parse(rs.getString("EndDate")), rs.getDouble("Rate"));
                Discounts.put(discount.getDiscountID(), discount);
                allDiscounts.add(discount);
            }
        }
        catch (SQLException e){
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return allDiscounts;
    }
    public Object getById(int id){
        try {
            if (Discounts.containsKey(id)) {
                return Discounts.get(id);
            }
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM Discount WHERE DiscountID = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Discount discount = new Discount(LocalDate.parse(rs.getString("StartDate")), LocalDate.parse(rs.getString("EndDate")), rs.getDouble("Rate"));
                Discounts.put(discount.getDiscountID(), discount);
                return discount;
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
            String query = "DELETE FROM Discount;";
            stmt.executeUpdate(query);
        }
        catch (SQLException e){
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
