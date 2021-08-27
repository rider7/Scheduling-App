package sample.Utilities;

import java.sql.*;

/**
 * Class used to query the database
 */
public class Query {
    /**Statement object used for the query*/
    private static Statement statement; //Statement reference
    /**Prepared Statement object used for the query*/
    private static PreparedStatement preparedStatement; //PreparedStatement reference

    //Create Statement Object
    /**
     * Method used for the statement object for the connection
     */
    public static void setStatement(Connection conn) throws SQLException {
        statement = conn.createStatement(); //Statement object created
    }

    //Return Statement Object
    /**
     * Method used to return the statement object
     */
    public static Statement getStatement(){
        return statement;
    }


    //Return Statement Object
    /**
     * Method used to return the statement object
     */
    public static Statement getTestStatement(int a, int b){
        return statement;
    }

    //Create Statement Object
    /**
     * Method used assign the prepared statement
     */
    public static void setPreparedStatement(Connection conn, String sqlStatement) throws SQLException {
        preparedStatement = conn.prepareStatement(sqlStatement); //Statement object created
    }

    //Return Statement Object
    /**
     * Method used to return the prepared statement
     */
    public static PreparedStatement getPreparedStatement(){
        return preparedStatement;
    }

}


