package DataAccessLayer.InventoryModule;

import BusinessLayer.InventoryModule.*;
import DataAccessLayer.SuppliersModule.Database;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

//ItemID, DamageType, ExpiryDate, Location, AfterDiscountPrice, BranchID
public class ItemDAO extends AbstractDAO {
    Map<Integer, Item> items;
    private static ItemDAO instance = null;

    public ItemDAO(){
        this.items = new HashMap<>();
        this.con= Database.getDataBaseInstance().getConnection();
    }

    public static ItemDAO getInstance(){
        if(instance == null)
            instance = new ItemDAO();
        return instance;
    }


    @Override
    public void update(Object obj){
        try{

            Item item = (Item)obj;
            PreparedStatement stmt = con.prepareStatement("UPDATE Item SET DamageType = ?, ExpiryDate = ?, Location = ?, AfterDiscountPrice = ? WHERE ItemID = ?");
            stmt.setString(1, item.getDamageType());
            stmt.setDate(2, Date.valueOf(item.getExpireDate()));
            stmt.setString(3, item.getLocationInBranch().name());
            stmt.setDouble(4, item.getAfterDiscountPrice());
            stmt.setInt(5, item.getID());
            stmt.executeUpdate();
            items.put(item.getID(), item);

        }
        catch (SQLException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void add(Object obj){
        try {

            Item item = (Item) obj;
            if (items.containsKey(item.getCatalogNum())) {

                return;
            }
            PreparedStatement stmt = con.prepareStatement("INSERT INTO Item (ItemID, DamageType, ExpiryDate, Location, AfterDiscountPrice, BranchID, Barcode)\n" +
                    "VALUES (?,?,?,?,?,?,?)");
            stmt.setInt(1, item.getID());
            stmt.setString(2, item.getDamageType());
            if(item.getExpireDate()!=null)
                stmt.setDate(3, Date.valueOf(item.getExpireDate()));
            else
                stmt.setDate(3, null);
            stmt.setString(4, item.getLocationInBranch().name());
            stmt.setDouble(5, item.getAfterDiscountPrice());
            stmt.setInt(6, item.getID() % 10);
            stmt.setInt(7, item.getID() / 10000);
            stmt.executeUpdate();
            items.put(item.getID(), item);

        }
        catch (SQLException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }


    @Override
    public void remove(int id){
        try {

            PreparedStatement stmt = con.prepareStatement("DELETE FROM Item WHERE ItemID = ?");
            stmt.setInt(1, id);
            stmt.executeUpdate();
            items.remove(id);

        }
        catch (SQLException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    @Override
    public ArrayList<Object> getAll(){
        ArrayList<Object> allItems = new ArrayList<>();
        try {

            PreparedStatement stmt = con.prepareStatement("SELECT * FROM Item");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                if (items.containsKey(rs.getInt("ItemID"))) {
                    allItems.add(items.get(rs.getInt("ItemID")));
                    continue;
                }
                BasicItem bi = (BasicItem) BasicItemDAO.getInstance().getById(rs.getInt("ItemID") / 10000);
                InventoryItem invi = (InventoryItem) InventoryItemDAO.getInstance().getById(rs.getInt("ItemID") / 10000);
                ProductIntegrity damaged;
                if (!Objects.equals(rs.getString("DamageType"), "null"))
                    damaged = ProductIntegrity.Damaged;
                else
                    damaged = ProductIntegrity.Null;
                Location location = null;
                if (Objects.equals(rs.getString("Location"), "store")) {
                    location = Location.store;
                } else if (Objects.equals(rs.getString("Location"), "warehouse")) {
                    location = Location.warehouse;
                }
                Item item = new Item(bi.getName(), bi.getCatalogNum(), bi.getManufacturer(), bi.getCostPrice(), bi.getItemCategories(), invi.getSellPrice(), invi.getCurrDiscount().getDiscountRate(), rs.getInt("ItemID"), rs.getDate("ExpiryDate").toLocalDate(), damaged, rs.getString("DamageType"), location, bi.getIDcounter());
                items.put(item.getID(), item);
                allItems.add(item);

            }
        }
        catch (SQLException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return allItems;
    }

    @Override
    public Object getById(int id){
        try {

            if (items.containsKey(id)) {

                return items.get(id);
            }
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM Item WHERE ItemID = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                BasicItem bi = (BasicItem) BasicItemDAO.getInstance().getById(rs.getInt("ItemID") / 10000);
                InventoryItem invi = (InventoryItem) InventoryItemDAO.getInstance().getById(rs.getInt("ItemID") / 10000);
                ProductIntegrity damaged;
                if (!Objects.equals(rs.getString("DamageType"), "null"))
                    damaged = ProductIntegrity.Damaged;
                else
                    damaged = ProductIntegrity.Null;
                Location location = null;
                if (Objects.equals(rs.getString("Location"), "store")) {
                    location = Location.store;
                } else if (Objects.equals(rs.getString("Location"), "warehouse")) {
                    location = Location.warehouse;
                }
                double rate;
                if(invi.getCurrDiscount() != null)
                    rate = invi.getCurrDiscount().getDiscountRate();
                else
                    rate = 0;
                LocalDate expire;
                if(rs.getDate("ExpiryDate")!=null)
                   expire =  rs.getDate("ExpiryDate").toLocalDate();
                else
                    expire = null;
                Item item = new Item(bi.getName(), bi.getCatalogNum(), bi.getManufacturer(), bi.getCostPrice(), bi.getItemCategories(), invi.getSellPrice(), rate, rs.getInt("ItemID"), expire, damaged, rs.getString("DamageType"), location, bi.getIDcounter());
                items.put(item.getID(), item);

                return item;
            }
        }
        catch (SQLException e) {
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
            String query = "DELETE FROM Item;";
            stmt.executeUpdate(query);
        }
        catch (SQLException e){
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
