package sample.Utilities;

import java.sql.*;

//Class to allow us to execute queries in our controllers
public class Query {
    private static Statement statement; //Statement reference
    private static PreparedStatement preparedStatement; //PreparedStatement reference

    //Create Statement Object
    public static void setStatement(Connection conn) throws SQLException {
        statement = conn.createStatement(); //Statement object created
    }

    //Return Statement Object
    public static Statement getStatement(){
        return statement;
    }


    //Return Statement Object
    public static Statement getTestStatement(int a, int b){
        return statement;
    }

    //Create Statement Object
    public static void setPreparedStatement(Connection conn, String sqlStatement) throws SQLException {

        preparedStatement = conn.prepareStatement(sqlStatement); //Statement object created
    }

    //Return Statement Object
    public static PreparedStatement getPreparedStatement(){
        return preparedStatement;
    }

}


