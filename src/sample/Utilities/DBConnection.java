package sample.Utilities;

import javax.sql.*;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * Class used to create the JDBC URL and concatenate with the JDBC Driver
 */
public class DBConnection {

    //JDBC URL parts
    /**String to hold the protocol data*/
    private static final String protocol = "jdbc";
    /**String to hold the vendor name data*/
    private static final String vendorName= ":mysql:";
    /**String to hold the ip address data*/
    private static final String ipAddress ="//wgudb.ucertify.com/WJ062s3";

    //JDBC URL concatenated
    /**String to hold the URL for the JDBC data*/
    private static final String jdbcURL = protocol + vendorName + ipAddress;

    //Driver interface reference
    /**String to hold the JDBC Driver data*/
    private static final String mySQLJDBCDriver = "com.mysql.cj.jdbc.Driver";
    /**Data to hold the connection object*/
    private static Connection conn = null;
    /**String to hold the username credential*/
    private static final String userName = "U062s3"; //Username
    /**String to hold the password credential*/
    private static final String password = "53688675267"; //Password

    //Method to start the connection
    /**
     * Method used to start the connection
     */
    public static Connection startConnection(){
        System.out.println("Trying to connect!");
        try {
            Class.forName(mySQLJDBCDriver);
            conn = (Connection)DriverManager.getConnection(jdbcURL, userName, password);
            System.out.println("Connection is Successful!!!!!!");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    //Method to close the connection
    /**
     * Method used to close the connection
     */
    public static void closeConnection(){
        try{
        conn.close();
        System.out.println("Connection closed!");

        } catch(SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
