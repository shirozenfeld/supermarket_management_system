package DataAccessLayer.InventoryModule;

import BusinessLayer.InventoryModule.BasicItem;
import BusinessLayer.InventoryModule.Category;
import BusinessLayer.InventoryModule.Discount;
import BusinessLayer.InventoryModule.InventoryItem;
import DataAccessLayer.SuppliersModule.Database;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryItemDAO extends AbstractDAO {
    Map<Integer, InventoryItem> InventoryItems;
    private static InventoryItemDAO instance = null;
    public static InventoryItemDAO getInstance(){
        if(instance == null)
            instance = new InventoryItemDAO();
        return instance;
    }
    public InventoryItemDAO(){
        this.InventoryItems = new HashMap<>();
        this.con= Database.getDataBaseInstance().getConnection();
    }
    public void update(Object obj){
        try {
            InventoryItem invi = (InventoryItem) obj;
            double newS = invi.getSellPrice();
            PreparedStatement stmt1 = con.prepareStatement("SELECT SellPrice FROM InventoryItem WHERE Barcode = ?");
            stmt1.setInt(1, invi.getCatalogNum());
            ResultSet rs = stmt1.executeQuery();
            double oldS = rs.getDouble("SellPrice");
            PreparedStatement stmt = con.prepareStatement("UPDATE InventoryItem SET SellPrice = ?, MinimumAmount = ?, CountedInStore = ?, CountedInWarehouse = ? WHERE Barcode = ?");
            stmt.setDouble(1, invi.getSellPrice());
            stmt.setInt(2, invi.getMinimumAmount());
            stmt.setInt(3, invi.getCountedInStore());
            stmt.setInt(4, invi.getCountedInWareHouse());
            stmt.setInt(5, invi.getCatalogNum());
            stmt.executeUpdate();
            if (newS != oldS) {
                PreparedStatement stmt2 = con.prepareStatement("INSERT INTO SellHistory (Barcode, SellPrice, UpdateDate) \n" +
                        "VALUES (?,?,?)\n");
                stmt2.setInt(1, invi.getCatalogNum());
                stmt2.setDouble(2, invi.getSellPrice());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Timestamp now = new Timestamp(System.currentTimeMillis());
                String nowS = sdf.format(now);
                stmt2.setString(3, nowS);
                stmt2.executeUpdate();
            }
            PreparedStatement stmt3 = con.prepareStatement("SELECT * FROM InventoryDiscounts WHERE Barcode = ? \n");
            stmt3.setDouble(1, invi.getCatalogNum());
            ResultSet rs3 = stmt3.executeQuery();
            int dis = rs3.getInt("DiscountID");
            if(invi.getCurrDiscount()!=null && invi.getCurrDiscount().getDiscountID() != dis) {
                PreparedStatement stmt2 = con.prepareStatement("INSERT INTO InventoryDiscounts (DiscountID, Barcode) \n" +
                        "VALUES (?,?)\n");
                stmt2.setInt(1, invi.getCurrDiscount().getDiscountID());
                stmt2.setDouble(2, invi.getCatalogNum());
                stmt2.executeUpdate();
            }
            InventoryItems.put(invi.getCatalogNum(), invi);
        }
        catch (SQLException e){
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public void add(Object obj){
        try {
            InventoryItem invi = (InventoryItem) obj;
            if (InventoryItems.containsKey(invi.getCatalogNum())) {
                return;
            }
            PreparedStatement stmt = con.prepareStatement("INSERT INTO InventoryItem (Barcode, SellPrice, MinimumAmount, CountedInStore, CountedInWarehouse, BranchID)\n" +
                    "VALUES (?,?,?,?,?,?)");
            stmt.setInt(1, invi.getCatalogNum());
            stmt.setDouble(2, invi.getSellPrice());
            stmt.setInt(3, invi.getMinimumAmount());
            stmt.setInt(4, invi.getCountedInStore());
            stmt.setInt(5, invi.getCountedInWareHouse());
            stmt.setInt(6, invi.getBranchID());
            stmt.executeUpdate();

            if (invi.getCurrDiscount() != null) {
                PreparedStatement stmt2 = con.prepareStatement("INSERT INTO InventoryDiscounts (Barcode, DiscountID)\n " +
                        "VALUES (?,?)");
                stmt2.setInt(1, invi.getCatalogNum());
                stmt2.setDouble(2, invi.getCurrDiscount().getDiscountID());
                stmt2.executeUpdate();
            }
            InventoryItems.put(invi.getCatalogNum(), invi);

            PreparedStatement stmt3 = con.prepareStatement("INSERT INTO SellHistory (Barcode, SellPrice, UpdateDate) \n" +
                    "VALUES (?,?,?)\n");
            stmt3.setInt(1, invi.getCatalogNum());
            stmt3.setDouble(2, invi.getSellPrice());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Timestamp now = new Timestamp(System.currentTimeMillis());
            String nowS = sdf.format(now);
            stmt3.setString(3, nowS);
            stmt3.executeUpdate();
            InventoryItems.put(invi.getCatalogNum(), invi);
        }
        catch (SQLException e){
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public void remove(int id){
        try {
            InventoryItem invi = InventoryItems.get(id);
            PreparedStatement stmt = con.prepareStatement("DELETE FROM InventoryItem WHERE Barcode = ?");
            stmt.setInt(1, id);
            stmt.executeUpdate();

            PreparedStatement stmt2 = con.prepareStatement("DELETE FROM InventoryDiscounts WHERE Barcode = ? AND DiscountID = ?");
            stmt2.setInt(1, id);
            stmt2.setInt(2, invi.getCurrDiscount().getDiscountID());
            stmt2.executeUpdate();
            InventoryItems.remove(id);
        }
        catch (SQLException e){
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<Object> getAll(){
        ArrayList<Object> allInvis = new ArrayList<>();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM InventoryItem");
            while (rs.next()) {
                if (InventoryItems.containsKey(rs.getInt("Barcode"))) {
                    allInvis.add(InventoryItems.get(rs.getInt("Barcode")));
                    continue;
                }
                InventoryItem invi = new InventoryItem(rs.getInt("Barcode"),rs.getInt("MinimumAmount"), rs.getDouble("SellPrice"), rs.getInt("BranchID"));
                invi.setCountedInStore(rs.getInt("CountedInStore"));
                invi.setCountedInWareHouse(rs.getInt("CountedInWarehouse"));
                invi.setCountedAmount(rs.getInt("CountedInStore") + rs.getInt("CountedInWarehouse"));
                PreparedStatement stmt2 = con.prepareStatement("SELECT Discount.DiscountID as Discount_ID\n" +
                        "FROM Discount\n" +
                        "INNER JOIN InventoryDiscounts\n" +
                        "ON Discount.DiscountID = InventoryDiscounts.DiscountID\n" +
                        "WHERE StartDate <= DATE('now') AND DATE('now') <= EndDate AND Barcode = ?\n" +
                        "GROUP BY Discount.DiscountID\n" +
                        "HAVING Rate = MAX(Rate)\n" +
                        "ORDER BY Rate DESC\n" +
                        "LIMIT 1;");
                stmt2.setInt(1, rs.getInt("Barcode"));
                ResultSet rs2 = stmt2.executeQuery();
                Discount discount = (Discount) DiscountDAO.getInstance().getById(rs2.getInt("Discount_ID"));
                invi.setCurrDiscount(discount);
                PreparedStatement stmt3 = con.prepareStatement("SELECT * FROM SellHistory WHERE Barcode = ?");
                stmt3.setInt(1, rs.getInt("Barcode"));
                ResultSet rs3 = stmt3.executeQuery();
                Map<String, Double> sellH = new HashMap<String, Double>();
                while (rs3.next()) {
                    sellH.put(rs3.getDate("UpdateDate").toString(), rs3.getDouble("SellPrice"));
                }
                invi.setSellPriceHistory(sellH);
                List<Discount> disH = new ArrayList<>();
                PreparedStatement stmt4 = con.prepareStatement("SELECT * FROM InventoryDiscount WHERE Barcode = ?");
                stmt4.setInt(1, rs.getInt("Barcode"));
                ResultSet rs4 = stmt4.executeQuery();
                while (rs4.next()) {
                    discount = (Discount) DiscountDAO.getInstance().getById(rs4.getInt("Discount_ID"));
                    disH.add(discount);
                }
                invi.setDiscountHistory(disH);
                PreparedStatement stmt5 = con.prepareStatement("SELECT Count(*) as howmany\n" +
                        "FROM Item\n" +
                        "WHERE Barcode = ? AND Location = store;");
                stmt5.setInt(1, invi.getCatalogNum());
                ResultSet rs5 = stmt5.executeQuery();
                int storeCounter = rs5.getInt("howmany");
                invi.setAmountInStore(storeCounter);
                PreparedStatement stmt6 = con.prepareStatement("SELECT Count(*) as howmany\n" +
                        "FROM Item\n" +
                        "WHERE Barcode = ? AND Location = warehouse;");
                stmt6.setInt(1, invi.getCatalogNum());
                ResultSet rs6 = stmt6.executeQuery();
                int whCounter = rs6.getInt("howmany");
                invi.setAmountInWareHouse(whCounter);
                invi.setTotalAmount(whCounter + storeCounter);
                InventoryItems.put(invi.getCatalogNum(), invi);
                allInvis.add(invi);
            }
        }
        catch (SQLException e){
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return allInvis;
    }
    public Object getById(int id){
        try {
            if (InventoryItems.containsKey(id)) {
                return InventoryItems.get(id);
            }
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM InventoryItem WHERE Barcode = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                InventoryItem invi = new InventoryItem(rs.getInt("Barcode"), rs.getInt("MinimumAmount"), rs.getDouble("SellPrice"), rs.getInt("BranchID"));
                invi.setCountedInStore(rs.getInt("CountedInStore"));
                invi.setCountedInWareHouse(rs.getInt("CountedInWarehouse"));
                invi.setCountedAmount(rs.getInt("CountedInStore") + rs.getInt("CountedInWarehouse"));
                PreparedStatement stmt2 = con.prepareStatement("SELECT Discount.DiscountID as Discount_ID\n" +
                        "FROM Discount\n" +
                        "INNER JOIN InventoryDiscounts\n" +
                        "ON Discount.DiscountID = InventoryDiscounts.DiscountID\n" +
                        "WHERE StartDate <= DATE('now') AND DATE('now') <= EndDate AND Barcode = ?\n" +
                        "GROUP BY Discount.DiscountID\n" +
                        "HAVING Rate = MAX(Rate)\n" +
                        "ORDER BY Rate DESC\n" +
                        "LIMIT 1;");
                stmt2.setInt(1, id);
                ResultSet rs2 = stmt2.executeQuery();
                Discount discount = (Discount) DiscountDAO.getInstance().getById(rs2.getInt("Discount_ID"));
                invi.setCurrDiscount(discount);
                PreparedStatement stmt3 = con.prepareStatement("SELECT * FROM SellHistory WHERE Barcode = ?");
                stmt3.setInt(1, id);
                ResultSet rs3 = stmt3.executeQuery();
                Map<String, Double> sellH = new HashMap<String, Double>();
                while (rs3.next()) {
                    sellH.put(rs3.getString("UpdateDate"), rs3.getDouble("SellPrice"));
                }
                invi.setSellPriceHistory(sellH);
                List<Discount> disH = new ArrayList<>();
                PreparedStatement stmt4 = con.prepareStatement("SELECT * FROM InventoryDiscounts WHERE Barcode = ?");
                stmt4.setInt(1, id);
                ResultSet rs4 = stmt4.executeQuery();
                while (rs4.next()) {
                    discount = (Discount) DiscountDAO.getInstance().getById(rs4.getInt("DiscountID"));
                    disH.add(discount);
                }
                invi.setDiscountHistory(disH);
                PreparedStatement stmt5 = con.prepareStatement("SELECT Count(*) as howmany\n" +
                        "FROM Item\n" +
                        "WHERE Barcode = ? AND Location = 'store';");
                stmt5.setInt(1, invi.getCatalogNum());
                ResultSet rs5 = stmt5.executeQuery();
                int storeCounter = rs5.getInt("howmany");
                invi.setAmountInStore(storeCounter);
                PreparedStatement stmt6 = con.prepareStatement("SELECT Count(*) as howmany\n" +
                        "FROM Item\n" +
                        "WHERE Barcode = ? AND Location = 'warehouse';");
                stmt6.setInt(1,  invi.getCatalogNum());
                ResultSet rs6 = stmt6.executeQuery();
                int whCounter = rs6.getInt("howmany");
                invi.setAmountInWareHouse(whCounter);
                invi.setTotalAmount(whCounter + storeCounter);
                InventoryItems.put(invi.getCatalogNum(), invi);
                return invi;
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
            String query = "DELETE FROM InventoryItem;";
            stmt.executeUpdate(query);
            Statement stmt1 = con.createStatement();
            String query1 = "DELETE FROM SellHistory;";
            stmt.executeUpdate(query1);
            Statement stmt2 = con.createStatement();
            String query2 = "DELETE FROM InventoryDiscounts;";
            stmt.executeUpdate(query2);
        }
        catch (SQLException e){
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

}

