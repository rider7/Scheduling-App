package sample.ViewController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Main;
import sample.Utilities.DBConnection;
import sample.Utilities.Query;

import java.io.IOException;
import java.sql.*;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import sample.Utilities.DBConnection;
import sample.Utilities.Query;

public class UsersController implements Initializable {
    Stage stage;
    Parent scene;

    @FXML
    private TextField user;

    @FXML
    private TextField password;

    @FXML
    public void onActionVerifyUser(javafx.event.ActionEvent event) throws SQLException {
        ResultSet myResultSet = myConnection();
        boolean userMatched = false;
        //Forward scroll ResultSet
        while (myResultSet.next()) { //next() method returns true so while it equals true the loop will be active, looping through all records
            if(myResultSet.getString("User_Name").equals(user.getText()) && myResultSet.getString("Password").equals(password.getText())){ //Use user result set here!!
                System.out.println("USER MATCH YEEEEEEEE!");
                userMatched = true;
            }
        }
        if (!userMatched){
            System.out.println("Sorry username or password is incorrect. Are you sure you created an account?");
        }

        System.out.println(user.getText());
        System.out.println(password.getText());
        System.out.println("Login button clicked!");

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {


    }

    public ResultSet myConnection() throws SQLException {
        //Establish connection before launch and assign it to the Connection reference variable named conn
        Connection conn = DBConnection.startConnection();
        //Move all the sql as an event handler into the fxml controller
        //Pass conn object to statement
        Query.setStatement(conn); //Create statement object
        Statement statement = Query.getStatement(); //Get Statement reference

        //Select all records from countries table
        String selectStatement = "SELECT * FROM users"; //SQL statement
        statement.execute(selectStatement); //Execute statement (returns true)
        ResultSet myResultSet = statement.getResultSet(); //Get the result sets and assigns to reference variable myResultSet

        return myResultSet;
    }

    public void backToMainController(ActionEvent event) throws IOException {
        stage = (Stage) ((javafx.scene.control.Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("MainController.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
}
