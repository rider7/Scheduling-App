package sample.Utilities;

import javax.sql.*;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    /**
     * Class used to create the JDBC URL and concatenate with the JDBC Driver
     */

    //JDBC URL parts
    private static final String protocol = "jdbc";
    private static final String vendorName= ":mysql:";
    private static final String ipAddress ="//wgudb.ucertify.com/WJ062s3";

    //JDBC URL concatenated
    private static final String jdbcURL = protocol + vendorName + ipAddress;

    //Driver interface reference
    private static final String mySQLJDBCDriver = "com.mysql.cj.jdbc.Driver";

    private static Connection conn = null;

    private static final String userName = "U062s3"; //Username

    private static final String password = "53688675267"; //Password

    //Method to start the connection
    public static Connection startConnection(){
        /**
         * Method used to start the connection
         */
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
    public static void closeConnection(){
        /**
         * Method used to close the connection
         */
        try{
        conn.close();
        System.out.println("Connection closed!");

        } catch(SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
