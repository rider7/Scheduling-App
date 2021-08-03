package sample.ViewController;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import sample.Utilities.DBConnection;
import sample.Utilities.Query;

import java.sql.Connection;
import java.sql.PreparedStatement;
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

public class CustomersController {

    @FXML
    Button saveCustomerButton;
    @FXML
    public void onActionInsertCustomer(ActionEvent event) throws SQLException, IOException {
        System.out.println("Save Customer Button Works!");

//        //Establish connection before launch and assign it to the Connection reference variable named conn
//        Connection conn = DBConnection.startConnection();
//
//        //Select Statement
//        String selectStatement = "SELECT * FROM countries";
//        //Insert Statement
//        String insertStatement = "INSERT INTO countries (Country, Create_Date, Created_By, Last_Updated_By) VALUES(?,?,?,?)"; //Question marks are placeholders to be mapped with key values in one-based index
//
//        //Update statement
//        String updateStatement = "UPDATE countries SET Country = ? WHERE Created_By = ?";
//
//        //Delete statement
//        String deleteStatement = "DELETE FROM countries WHERE country = ?";
//
//        //Create prepared statement object for selectStatement
//        //Query.setPreparedStatement(conn, selectStatement);
//
//        //Create prepared statement object for insertStatement
//        //Query.setPreparedStatement(conn, insertStatement);
//
//        //Create prepared statement object for updateStatement
//        //Query.setPreparedStatement(conn, updateStatement);
//
//        //Create prepared statement object for deleteStatement
//        Query.setPreparedStatement(conn, deleteStatement);
//
//        //Prepared statement reference
//        PreparedStatement preparedStatement = Query.getPreparedStatement();
//
//        String Country = "Test";
//        String Create_Date = "2020-03-28 00:00:00";
//        String Created_By = "me";
//        String Last_Updated_By = "me again";
//
//        //Get keyboard input
////        Scanner keyboard = new Scanner(System.in);
////        System.out.println("Enter your country name: ");
////        countryName = keyboard.nextLine();
//
//        //Key-value mapping to set the prepared statement
//        preparedStatement.setString(1, Country);
//        //preparedStatement.setString(2,Create_Date);
//        //preparedStatement.setString(3,Created_By);
//        //preparedStatement.setString(4,Last_Updated_By);
//
//        preparedStatement.execute(); //Execute prepared statement
//
//        //Check rows affected
//        if (preparedStatement.getUpdateCount() > 0)
//            System.out.println("Rows affected: " + preparedStatement.getUpdateCount());
//        else System.out.println("No change!");
    }
}
