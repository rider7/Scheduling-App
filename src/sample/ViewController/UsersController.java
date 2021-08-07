package sample.ViewController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Model.Users;
import sample.Utilities.DBConnection;
import sample.Utilities.Query;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

import static sample.Utilities.TextFileOutput.myFileOutput;

public class UsersController {
    //Set language resource bundle
    //Locale.setDefault(new Locale("fr"));
    Locale locale = new Locale("en");
    //System.out.println(Locale.getDefault());
    ResourceBundle bundle = ResourceBundle.getBundle("sample.Utilities.ResourceBundles.text", locale);
    Stage stage;
    Parent scene;
    //Object instance created for dialog box
    JFrame frame;
    public static String myNewUser;

    boolean userMatched = false;
    String attempt = "Fail";

    @FXML
    private TextField user;

    @FXML
    private TextField password;

    @FXML
    private Button backToReality;
    @FXML
    public void onActionVerifyUser(javafx.event.ActionEvent event) throws SQLException, IOException {
        ResultSet myResultSet = myConnection();
        String username = user.getText();
        myNewUser=user.getText();
        System.out.println("In Action: " + myNewUser);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String formatLocalDateTime = dtf.format(now);

        //Forward scroll ResultSet
        while (myResultSet.next()) { //next() method returns true so while it equals true the loop will be active, looping through all records
            if(myResultSet.getString("User_Name").equals(user.getText()) && myResultSet.getString("Password").equals(password.getText())){ //Use user result set here!!
                userMatched = true;
                attempt = "Success";
                JOptionPane.showMessageDialog(frame,"Successful Login!");
                System.out.println("Successful Login!");


            }
        }
        if (!userMatched){
            JOptionPane.showMessageDialog(frame,"Sorry, your User ID and Password do not match. Please try again.");

            System.out.println("Sorry username or password is incorrect. Are you sure you created an account?");
        }
        if(userMatched) { //If the user/password is matched in the db then user can navigate the application
            myResultSet.close();
            DBConnection.closeConnection();
            stage = (Stage) ((javafx.scene.control.Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("CustomersList.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }

        myFileOutput(username,attempt,formatLocalDateTime);

        System.out.println(user.getText());
        System.out.println(password.getText());
        System.out.println("Login button clicked!");
        myResultSet.close();
        DBConnection.closeConnection();

    }

    @FXML
    public Label myTimeZone;

    String myTimeZoneString = getTimeZone();

    @FXML private void initialize(){
        myTimeZone.setText("Time Zone: " + myTimeZoneString);
    }

    public static String getTimeZone(){

        //Get Calendar instance
        Calendar now = Calendar.getInstance();

        //Get current timezone using get timezone method of calendar class
        TimeZone timezone = now.getTimeZone();

        //System.out.print(timezone.getDisplayName());
        return timezone.getDisplayName();
    }



    public ResultSet myConnection() throws SQLException {
        //Establish connection before launch and assign it to the Connection reference variable named conn
        Connection conn = DBConnection.startConnection();
        //Pass conn object to statement
        Query.setStatement(conn); //Create statement object
        Statement statement = Query.getStatement(); //Get Statement reference

        //Select all records from countries table
        String selectStatement = "SELECT * FROM users"; //SQL statement
        statement.execute(selectStatement); //Execute statement (returns true)
        ResultSet myResultSet = statement.getResultSet(); //Get the result sets and assigns to reference variable myResultSet

        return myResultSet;
    }

    public ResultSet myPreparedStatement() throws SQLException{
        //Establish connection before launch and assign it to the Connection reference variable named conn
        Connection conn = DBConnection.startConnection();
        //Pass conn object to statement
        Query.setStatement(conn); //Create statement object
        Statement statement = Query.getPreparedStatement(); //Get Statement reference

        //Select all records from countries table
        String selectStatement = "SELECT * FROM users"; //SQL statement
        statement.execute(selectStatement); //Execute statement (returns true)
        ResultSet myResultSet = statement.getResultSet(); //Get the result sets and assigns to reference variable myResultSet
        return myResultSet;
    }

    public void backToMainController(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().
                getResource(
                        "MainController.fxml"),bundle);
        Stage stage = (Stage) backToReality.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static String getMyNewUser(){
        System.out.println("In getMyNewUserMethod: " + myNewUser);
        return myNewUser;
    }
}
