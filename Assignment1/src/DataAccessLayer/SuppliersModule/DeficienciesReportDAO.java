package DataAccessLayer.SuppliersModule;

import BusinessLayer.SuppliersModule.*;
import DataAccessLayer.SuppliersModule.Database;

import javax.xml.transform.Result;
import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class DeficienciesReportDAO
{

    private Connection conn;
    private static DeficienciesReportDAO instance;
    private Map<String, DeficienciesReport> identityMap; //string =
    //PreparedStatement stmt;
    private static int report_id_counter;
    private DeficienciesReportDAO() throws SQLException
    {
        this.conn=Database.getDataBaseInstance().getConnection();
        this.identityMap = new HashMap<>();
        this.report_id_counter=0;
    }


    public static DeficienciesReportDAO getDeficienciesReportInstance() {
        if (instance == null) {
            try {
                instance = new DeficienciesReportDAO();
            } catch (SQLException e)
            {
                System.out.println("An error occurred");
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
        return instance;
    }

    public void deleteByFailure()
    {
        try
        {
            PreparedStatement stmt1 = conn.prepareStatement("DELETE FROM ShortageReport WHERE Status =?");
            stmt1.setString(1, "in_process");
            stmt1.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }


    // addition is under Shay and Shahar's responsibility
    public DeficienciesReport getDailyDeficienciesReport()
    {
        SuperProductDAO superProductDAO = SuperProductDAO.getSuperProductInstance();
        DeficienciesReport deficienciesReport = new DeficienciesReport();
        Map<String, Integer> products = new HashMap<>(); // super product & amount
        Map<String, Order> orders=new HashMap<>();
        try
        {
            PreparedStatement stmt2 = conn.prepareStatement("SELECT * FROM ShortageReport WHERE Date=?");
            //stmt2.setString(1,"in_process");
            stmt2.setDate(1, Date.valueOf(LocalDate.now()));
            ResultSet rs = stmt2.executeQuery();
            while (rs.next()) {
                String barcode = String.valueOf(rs.getInt("Barcode"));
                int amountToOrder = rs.getInt("AmountToOrder");
                products.put(barcode,amountToOrder);
            }
            Map<SuperProduct,Integer> productsObjects=new HashMap<SuperProduct,Integer>();
            for (Map.Entry<String, Integer> entry : products.entrySet())
            {
                SuperProduct superProduct=superProductDAO.getSuperProductByBarcode(entry.getKey());
                int amount = entry.getValue();
                productsObjects.put(superProduct,amount);
            }
            deficienciesReport.setReport_id(String.valueOf(report_id_counter));
            deficienciesReport.setReport_status(DeficienciesReport.Status.valueOf("in_process"));
            deficienciesReport.setProductsList(products);
            deficienciesReport.setProducts(productsObjects);
            deficienciesReport.setOrders(orders);
        }
        catch (SQLException e)
        {
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        report_id_counter++;
        return deficienciesReport;
    }

    public void updateDailyReportStatus()
    {
        try
        {
            PreparedStatement stmt590 = conn.prepareStatement("UPDATE ShortageReport SET Status =? WHERE Status =?");
            stmt590.setString(1,"completed");
            stmt590.setString(2,"in_process");
            stmt590.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    //TODO
    public DeficienciesReport getReportByDate(LocalDate date)
    {
        DeficienciesReport report = new DeficienciesReport();
        Map<SuperProduct, Integer> products = new HashMap<>(); // super product & amount
        Map<String, Order> orders=null;
        ResultSet rs;
        PreparedStatement stmt3;
        try
        {
            stmt3 = conn.prepareStatement("SELECT * FROM ShortageReport WHERE Date=?");
            stmt3.setDate(1, Date.valueOf(date));
            rs = stmt3.executeQuery();
            while (rs.next())
            {
                String barcode=String.valueOf(rs.getInt("Barcode"));
                int amountToOrder=rs.getInt("AmountToOrder");
                String status=rs.getString("Status");
                SuperProductDAO superProductDAO = SuperProductDAO.getSuperProductInstance();
                SuperProduct superProduct = superProductDAO.getSuperProductByBarcode(barcode);
                products.put(superProduct,amountToOrder);
            }
            report.setReport_id(String.valueOf(report_id_counter));
            report.setReport_status(DeficienciesReport.Status.valueOf("in_process"));
            report.setProducts(products);

        }
        catch (SQLException e)
        {
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        try {
            stmt3 = conn.prepareStatement("SELECT * FROM ShortageOrders WHERE Date=?");
            stmt3.setDate(1, Date.valueOf(date));
            rs = stmt3.executeQuery();
            OrderDAO orderDAO = OrderDAO.getOrderInstance();
            if (rs.next()) 
            {
                String reportID = String.valueOf(rs.getInt("ReportID"));
                report.setOrders(orderDAO.getOrdersByReportID(reportID));
            }


        }
        catch (SQLException e)
        {
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return report;
    }


}