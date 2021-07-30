package sample;

import javafx.application.Application;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Utilities.DBConnection;
import sample.Utilities.Query;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Main extends Application {

    //Starting or primary fxml view when the application first runs
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("ViewController/MainController.fxml"));
        primaryStage.setTitle("It Works");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    //Main entry into the application
    public static void main(String[] args) throws SQLException {

        //Establish connection before launch and assign it to the Connection reference variable named conn
        Connection conn = DBConnection.startConnection();
        //Move all the sql as an event handler into the fxml controller
        //Pass conn object to statement
        Query.setStatement(conn); //Create statement object
        Statement statement = Query.getStatement(); //Get Statement reference

        //Select all records from countries table
        String selectStatement = "SELECT * FROM countries"; //SQL statement
        statement.execute(selectStatement); //Execute statement (returns true)
        ResultSet myResultSet = statement.getResultSet(); //Get the result sets and assigns to reference variable myResultSet

        //Forward scroll ResultSet
//        while(myResultSet.next()){ //next() method returns true so while it equals true the loop will be active, looping through all records
//            int countryID = myResultSet.getInt("Country_ID"); //Local variable countryID is assigned the value of getInt() method on myResultSet with the column name as a parameter.
//            String countryName = myResultSet.getString("Country");
//            LocalDate createDate = myResultSet.getDate("Create_Date").toLocalDate(); //Need toLocalDate() method to convert Date to LocalDate
//            LocalTime createTime = myResultSet.getTime("Create_Date").toLocalTime(); //Need toLocalTime to convert to Local Time
//            String createdBy = myResultSet.getString("Created_By");
//            LocalDateTime updateDate = myResultSet.getTimestamp("Last_Update").toLocalDateTime(); //Need toLocalDateTime() method to convert. Using timestamp type
//            //LocalTime updateTime = myResultSet.getTime("Last_Update").toLocalTime();
//            String updatedBy = myResultSet.getString("Last_Updated_By");
//
//            System.out.println("Country ID: " + countryID);
//            System.out.println("Country Name: " + countryName);
//            System.out.println("createDate: " + createDate + createTime);
//            System.out.println("createdBy: " + createdBy);
//            System.out.println("updateDate: " + updateDate);
//
//        }

        launch(args);
        DBConnection.closeConnection();
    }

}

//Raw SQL Insert Statement
//String insertStatement ="INSERT INTO countries(Country, Create_Date, Created_By, Last_Update, Last_Updated_By)" //table name countries and columns in parenthesis
//+ "VALUES('US', '2021-02-22 00:00:00', 'admin','2021-02-22 00:00:00', 'admin')"; //Values hardcoded to insert into the table
        /*
        //Variable Insert Statement
        String Country = "Canada";
        String Create_Date = "2021-02-22 00:00:00";
        String Created_By = "admin";
        String Last_Update = "2021-02-22 00:00:00";
        String Last_Updated_By = "admin";

        String insertStatement = "INSERT INTO countries(Country, Create_Date, Created_By, Last_Update, Last_Updated_By)"
                + "VALUES(" +
                "'" + Country + "'," +
                "'" + Create_Date + "'," +
                "'" + Created_By + "'," +
                "'" + Last_Update + "'," +
                "'" + Last_Updated_By + "'" +
                ")";
                */
//Update Statement
//String updateStatement = "UPDATE countries SET Country = 'Japan' WHERE Country = 'Canada'"; //Updating countries table at country column

//Delete Statement ....... has foreign key constraint issues...
//String deleteStatement = "DELETE FROM countries WHERE Country = 'Japan'"; //Delete from countries table where country column record equals 'japan'

//Select Statement is ResultSet

//Execute SQL Statements

//statement.execute(deleteStatement);
//statement.execute(updateStatement); //Returns false since update was executed
//statement.execute(insertStatement); //Returns false because we are using insert statement

//Confirms rows affected
//        if(statement.getUpdateCount() > 0){
//            System.out.println(statement.getUpdateCount() + " rows affected!");
//        } else{
//            System.out.println("No Change");
//        };
