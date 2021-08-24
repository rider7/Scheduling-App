package sample.Utilities;

import java.sql.*;

//Class to allow us to execute queries in our controllers
public class Query {
    /**
     * Class used to query the database
     */
    private static Statement statement; //Statement reference
    private static PreparedStatement preparedStatement; //PreparedStatement reference

    //Create Statement Object
    public static void setStatement(Connection conn) throws SQLException {
        /**
         * Method used for the statement object for the connection
         */
        statement = conn.createStatement(); //Statement object created
    }

    //Return Statement Object
    public static Statement getStatement(){
        /**
         * Method used to return the statement object
         */
        return statement;
    }


    //Return Statement Object
    public static Statement getTestStatement(int a, int b){
        /**
         * Method used to return the statement object
         */
        return statement;
    }

    //Create Statement Object
    public static void setPreparedStatement(Connection conn, String sqlStatement) throws SQLException {
        /**
         * Method used assign the prepared statement
         */
        preparedStatement = conn.prepareStatement(sqlStatement); //Statement object created
    }

    //Return Statement Object
    public static PreparedStatement getPreparedStatement(){
        /**
         * Method used to return the prepared statement
         */
        return preparedStatement;
    }

}


