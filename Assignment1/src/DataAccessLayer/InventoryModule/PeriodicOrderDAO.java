package DataAccessLayer.InventoryModule;

import BusinessLayer.InventoryModule.Discount;
import BusinessLayer.InventoryModule.PeriodicOrder;
import DataAccessLayer.SuppliersModule.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PeriodicOrderDAO extends AbstractDAO {
    Map<Integer, PeriodicOrder> periodicOrders;
    private static PeriodicOrderDAO instance = null;

    public PeriodicOrderDAO() {
        this.periodicOrders = new HashMap<>();
        this.con= Database.getDataBaseInstance().getConnection();
    }

    public static PeriodicOrderDAO getInstance() {
        if (instance == null)
            instance = new PeriodicOrderDAO();
        return instance;
    }

    @Override
    public void update(Object obj) {
        PeriodicOrder pOrder = (PeriodicOrder) obj;
        pOrder.getIdAmountMap().forEach((id, amount) -> {
            PreparedStatement stmt = null;
            try {
                stmt = con.prepareStatement("UPDATE PeriodicOrder SET AmountToOrder = ? WHERE OrderID = ? AND Barcode = ?");
                stmt.setInt(1, amount);
                stmt.setInt(2, pOrder.getOrderID());
                stmt.setInt(3, id);
                stmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println("An error occurred");
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        });
        periodicOrders.put(pOrder.getOrderID(), pOrder);
    }

    @Override
    public void add(Object obj) {

    }

    @Override
    public void remove(int id) {

    }

    @Override
    public ArrayList<Object> getAll() {
        ArrayList<Object> allOrders = new ArrayList<>();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT OrderID FROM PeriodicOrder");
            while (rs.next()) {
                if (periodicOrders.containsKey(rs.getInt("OrderID"))) {
                    allOrders.add(periodicOrders.get(rs.getInt("OrderID")));
                    continue;
                }
                PreparedStatement stmt2 = con.prepareStatement("SELECT Barcode, AmountToOrder FROM PeriodicOrder WHERE OrderID = ?");
                stmt2.setInt(1, rs.getInt("OrderID"));
                ResultSet rs2 = stmt2.executeQuery();
                Map<Integer, Integer> idAmount = new HashMap<>();
                while (rs2.next()) {
                    idAmount.put(rs2.getInt("Barcode"), rs2.getInt("AmountToOrder"));
                }
                PeriodicOrder pOrder = new PeriodicOrder(rs.getInt("OrderID"), idAmount);
                periodicOrders.put(pOrder.getOrderID(), pOrder);
                allOrders.add(pOrder);
            }

        } catch (SQLException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return allOrders;
    }

    @Override
    public Object getById(int id) {
        try {
            PreparedStatement stmt1 = con.prepareStatement("SELECT Day FROM PeriodicOrder WHERE OrderID = ?");
            stmt1.setInt(1, id);
            ResultSet rs1 = stmt1.executeQuery();
            if (rs1.next()) {
                String orderDay = rs1.getString("Day").toUpperCase();
                DayOfWeek orderday = DayOfWeek.valueOf(orderDay);
                LocalDate date = LocalDate.now();
                DayOfWeek today = date.getDayOfWeek();
                int result = today.compareTo(orderday);
                if (result >= 0) {

                    return null;
                }
            }
            if (periodicOrders.containsKey(id)) {

                return periodicOrders.get(id);
            }
            PreparedStatement stmt = con.prepareStatement("SELECT Barcode, AmountToOrder FROM PeriodicOrder WHERE OrderID = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            Map<Integer, Integer> idAmount = new HashMap<>();
            while (rs.next()) {
                idAmount.put(rs.getInt("Barcode"), rs.getInt("AmountToOrder"));
            }
            if(idAmount.isEmpty()){
                return null;
            }
            PeriodicOrder pOrder = new PeriodicOrder(id, idAmount);
            periodicOrders.put(pOrder.getOrderID(), pOrder);
            return pOrder;
        }
        catch(SQLException e){
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void removeAll(){
    }
}
