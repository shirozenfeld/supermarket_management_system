package DataAccessLayer.SuppliersModule;

import DataAccessLayer.Connect;

import java.sql.*;
public class Database
{
    private static final String DB_URL = "jdbc:sqlite::resource:identifier.sqlite";
    //private static final String DB_URL = "jdbc:sqlite:identifier.sqlite";
    private static Database instance;
    private Connection conn;
    private Database()
    {
        try
        {
            this.conn = DriverManager.getConnection(DB_URL);
        }
        catch(SQLException e)
        {
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
    public static Database getDataBaseInstance() {
        if (instance == null)
                instance = new Database();
        return instance;
    }

    public Connection getConnection() {
        return this.conn;
    }
    public void closeConn()
    {
        try
        {
            this.conn.close();
        }
        catch(SQLException e)
        {
            System.out.println("An error occurred");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}