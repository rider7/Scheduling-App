package sample;

import javafx.application.Application;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Utilities.DBConnection;
import sample.Utilities.Query;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Main extends Application {

    //Starting or primary fxml view when the application first runs
    @Override
    public void start(Stage primaryStage) throws Exception{
        //Set language
        //Locale.setDefault(new Locale("fr"));
        Locale locale = new Locale("en");
        //System.out.println(Locale.getDefault());
        ResourceBundle bundle = ResourceBundle.getBundle("sample.Utilities.ResourceBundles.text", locale);
        Parent root = FXMLLoader.load(getClass().getResource("ViewController/MainController.fxml"), bundle);
        primaryStage.setTitle("It Works");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    //Main entry into the application
    public static void main(String[] args) throws SQLException, IOException {


        //Establish connection before launch and assign it to the Connection reference variable named conn
        Connection conn = DBConnection.startConnection();

        //Select Statement
        String selectStatement = "SELECT * FROM countries";
        //Insert Statement
        String insertStatement = "INSERT INTO countries (Country, Create_Date, Created_By, Last_Updated_By) VALUES(?,?,?,?)"; //Question marks are placeholders to be mapped with key values in one-based index

        //Update statement
        String updateStatement = "UPDATE countries SET Country = ? WHERE Created_By = ?";

        //Delete statement
        String deleteStatement = "DELETE FROM countries WHERE country = ?";

        //Create prepared statement object for selectStatement
        //Query.setPreparedStatement(conn, selectStatement);

        //Create prepared statement object for insertStatement
        //Query.setPreparedStatement(conn, insertStatement);

        //Create prepared statement object for updateStatement
        //Query.setPreparedStatement(conn, updateStatement);

        //Create prepared statement object for deleteStatement
        Query.setPreparedStatement(conn, deleteStatement);

        //Prepared statement reference
        PreparedStatement preparedStatement = Query.getPreparedStatement();

        String Country = "Test";
        String Create_Date ="2020-03-28 00:00:00";
        String Created_By ="me";
        String Last_Updated_By ="me again";

        //Get keyboard input
//        Scanner keyboard = new Scanner(System.in);
//        System.out.println("Enter your country name: ");
//        countryName = keyboard.nextLine();

        //Key-value mapping to set the prepared statement
        preparedStatement.setString(1,Country);
        //preparedStatement.setString(2,Create_Date);
        //preparedStatement.setString(3,Created_By);
        //preparedStatement.setString(4,Last_Updated_By);

       preparedStatement.execute(); //Execute prepared statement

        //Check rows affected
        if(preparedStatement.getUpdateCount()>0) System.out.println("Rows affected: " + preparedStatement.getUpdateCount());
        else System.out.println("No change!");

        launch(args);
        DBConnection.closeConnection();

        //Filename and user variable
        String filename="loginactivity.txt";

        //Create and Open file
        FileWriter outputFile=new FileWriter(filename, true);
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
