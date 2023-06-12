package DataAccessLayer.SuppliersModule;
import BusinessLayer.SuppliersModule.*;

import java.sql.*;
import java.util.HashMap;
import java.util.*;

public class DiscountDAO
{
    private static DiscountDAO instance;
    private Map<String,Discount> identityMap;
    PreparedStatement stmt;
    private Connection conn;


    private DiscountDAO() throws SQLException
    {
        this.conn=Database.getDataBaseInstance().getConnection();
        this.identityMap = new HashMap<>();
    }

    public static DiscountDAO getDiscountInstance()
    {
        if (instance==null)
        {
            try {
                instance = new DiscountDAO();
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

    public void add(Discount discount, String barcode,String supplierID) {
        stmt = null;
        try {
            stmt = conn.prepareStatement("INSERT INTO SupplierDiscount (DiscountID,Barcode,SupplierID, percents, giftAmount, CatalogNumber, amount,DiscountType) VALUES (?,?,?,?,?,?,?,?)");
            stmt.setInt(1, Integer.parseInt((discount.getDiscount_id())));
            stmt.setInt(2, Integer.parseInt(barcode));
            stmt.setInt(3, Integer.parseInt(supplierID));
            if (discount instanceof QuantityDiscount) {
                stmt.setInt(4, 0);
                stmt.setInt(5, ((QuantityDiscount) discount).getGift_amount());
                stmt.setString(8, "Quantity");
            }
            if (discount instanceof MoneyDiscount) {
                stmt.setInt(4, ((MoneyDiscount) discount).getPercents());
                stmt.setInt(5, 0);
                stmt.setString(8, "Money");
            }
            stmt.setInt(6, discount.getCatalog_number());
            stmt.setInt(7, discount.getAmount());
            stmt.executeUpdate();
            String key = discount.getDiscount_id();
            identityMap.put(key, discount);

        } catch (SQLException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }


    public void update(Object obj)
    {
        Discount discount=(Discount) obj;
        stmt = null;
        try
        {
            stmt = conn.prepareStatement("UPDATE SupplierDiscount SET percents=?, giftAmount=?, CatalogNumber=?, amount=? WHERE DiscountID=? AND barcode=? AND supplierID=?");
            if(discount instanceof QuantityDiscount)
            {
                stmt.setInt(1,0);
                stmt.setInt(2, ((QuantityDiscount)discount).getGift_amount());
            }
            if(discount instanceof MoneyDiscount)
            {
                stmt.setInt(1,((MoneyDiscount)discount).getPercents());
                stmt.setInt(2, 0);
            }
            stmt.setInt(3, discount.getCatalog_number());
            stmt.setInt(4, discount.getAmount());
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }


    public void RemoveDiscountByDiscountID(String discountID) {
        try {
            Discount discount = identityMap.get(discountID);
            if (discount != null)
            {
                identityMap.remove(discountID);
            }
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM SupplierDiscount WHERE DiscountID=?");
            stmt.setInt(1, Integer.parseInt(discountID));
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }


    public Map<Integer, List<Discount>> getDiscountsMapBySupplierID(String supplierID)
    {
        Map<Integer, List<Discount>> discountMap = new HashMap<>();
        try
        {
            stmt = conn.prepareStatement("SELECT * FROM SupplierDiscount WHERE SupplierID=?");
            stmt.setInt(1, Integer.parseInt(supplierID));
            ResultSet rs = stmt.executeQuery();
            while (rs.next())
            {
                int catalog_number = rs.getInt("CatalogNumber");
                int amount = rs.getInt("amount");
                String discountType = rs.getString("DiscountType");
                int percents = rs.getInt("percents");
                int giftAmount = rs.getInt("giftAmount");
                Discount discount = null;
                if ("Quantity".equals(discountType)) {
                    discount = new QuantityDiscount(amount, catalog_number, giftAmount);
                }
                else if ("Money".equals(discountType)) {
                    discount = new MoneyDiscount(amount, catalog_number, percents);
                }

                if(discount!= null)
                {
                    discount.setDiscount_id(String.valueOf(rs.getInt("DiscountID")));
                    discount.setAmount(amount);
                    discount.setCatalog_number(catalog_number);

                    if (discountMap.containsKey(catalog_number))
                        discountMap.get(catalog_number).add(discount);
                    else
                    {
                        List<Discount> discounts=new ArrayList<>();
                        discounts.add(discount);
                        discountMap.put(catalog_number,discounts);
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
        return discountMap;
    }
    public void removeAllDiscounts()
    {
        try
        {
            Statement stmt = conn.createStatement();
            String query = "DELETE FROM SupplierDiscount;";
            stmt.executeUpdate(query);
        }
        catch (SQLException e)
        {
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
