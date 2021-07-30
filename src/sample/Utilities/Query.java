package sample.Utilities;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

//Class to allow us to execute queries in our controllers
public class Query {
    private static Statement statement; //Statement reference

    //Create Statement Object
    public static void setStatement(Connection conn) throws SQLException {
        statement = conn.createStatement(); //Statement object created
    }

    //Return Statement Object
    public static Statement getStatement(){
        return statement;
    }
}
