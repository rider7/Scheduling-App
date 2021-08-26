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

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

import static sample.Utilities.TextFileOutput.myFileOutput;

public class UsersController {
    //Set language resource bundle
    Locale locale = Locale.getDefault();
    //Locale.setDefault(new Locale("fr"));
    //Locale locale = new Locale("es");

    //System.out.println(Locale.getDefault());
    ResourceBundle bundle = ResourceBundle.getBundle("sample.Utilities.ResourceBundles.text", locale);
    Stage stage;
    Parent scene;
    //Object instance created for dialog box
    JFrame frame;
    public static String myNewUser;
    private final String user_one = "test";
    private final String user_two = "admin";

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
        /**
         * Method used to verify if the user credentials are in the system
         */
        // declaring object of Locale
        Locale locale;
        //Locale locale2;

        // calling the getDefault method
        locale = Locale.getDefault();
        //locale.setDefault(new Locale("fr"));
        //locale2 = Locale.getDefault();
        String localeString = locale.toString();
        System.out.println(localeString);

        ResultSet myResultSet = myConnection();
        String username = user.getText();
        myNewUser=user.getText();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String formatLocalDateTime = dtf.format(now);

        //Forward scroll ResultSet
        while (myResultSet.next()) { //next() method returns true so while it equals true the loop will be active, looping through all records
            if(myResultSet.getString("User_Name").equals(user.getText()) && myResultSet.getString("Password").equals(password.getText())){ //Use user result set here!!
                userMatched = true;
                attempt = "Success";
                System.out.println("User beginning");
                myPreparedStatement(myNewUser);
                System.out.println("User end");
                if(localeString.equals("fr")){ //Logic used if user is a match to display a dialog box either in English or French based on the default locale
                    //System.out.println("FRENCHIE");
                    JOptionPane.showMessageDialog(frame, "Connexion réussie!");}
                else{
                    //System.out.println("ENGLISH");
                    JOptionPane.showMessageDialog(frame,"Successful Login!");}

                // printing the locale
                //System.out.println(locale);

            }
        }
        if (!userMatched){ //Logic used if user is not a match to display a dialog box either in English or French based on the default locale
            if(localeString.equals("fr")){
                //System.out.println("FRENCHIE");
                JOptionPane.showMessageDialog(frame, "Désolé, votre identifiant et votre mot de passe ne correspondent pas. Veuillez réessayer.");}
            else{
                //System.out.println("ENGLISH");
                JOptionPane.showMessageDialog(frame,"Sorry, your User ID and Password do not match. Please try again.");}

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
        //System.out.println("Login button clicked!");
        myResultSet.close();
        DBConnection.closeConnection();

    }

    @FXML
    public Label myTimeZone;
    @FXML
    public Label textTimeZone;

    String myTimeZoneString = getTimeZone();

    @FXML private void initialize(){
        /**
         * Initialize method that starts when this controller is used
         */
        //Set language resource bundle
        // declaring object of Locale
        Locale locale;
        Locale locale2;

        // calling the getDefault method
        locale = Locale.getDefault();
        //locale.setDefault(new Locale("fr"));
        locale2 = Locale.getDefault();
        String localeString = locale2.toString();

        myTimeZone.setText(myTimeZoneString);
    }

    public static String getTimeZone(){
        /**
         * Method used to get the current timezone and display the name
         */
        //Get Calendar instance
        Calendar now = Calendar.getInstance();

        //Get current timezone using get timezone method of calendar class
        TimeZone timezone = now.getTimeZone();

        //System.out.print(timezone.getDisplayName());
        return timezone.getDisplayName();
    }



    public ResultSet myConnection() throws SQLException {
        /**
         * Method used to create a connection with the database and select the users
         */
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

    public void myPreparedStatement(String user) throws SQLException{
        /**
         * Method used to connect to the database and select the appointment records by user_id
         */
        Boolean scheduleConflict = false;
        //System.out.println("My prep statement method user: " + user);
        //Establish connection before launch and assign it to the Connection reference variable named conn
        Connection conn = DBConnection.startConnection();
        //Select all records from countries table
        String selectStatement = "SELECT * FROM appointments WHERE User_ID = ?"; //SQL statement
        //String selectStatement = "SELECT * FROM appointments"; //SQL statement
        //Pass conn object to statement
        Query.setPreparedStatement(conn, selectStatement); //Create statement object
        PreparedStatement statement = Query.getPreparedStatement(); //Get Statement reference
        if(user.equals(user_one)){
            System.out.println("User 1");
        //Key-value mapping to set the prepared statement based on customer id
        statement.setInt(1, 1);}
        else{
            System.out.println("User 2");
            statement.setInt(1, 2);
        }

        statement.execute(); //Execute statement (returns true)
        ResultSet myResultSet = statement.getResultSet(); //Get the result sets and assigns to reference variable myResultSet
        //System.out.println(myResultSet.getTimestamp("Start"));
        //Get local date time
        LocalDateTime myLocalDateTime = LocalDateTime.now();
        //Custom format pattern
        DateTimeFormatter myFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
        DateTimeFormatter myFormatterFull = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        DateTimeFormatter myFormatterHour = DateTimeFormatter.ofPattern("HH");
        DateTimeFormatter myFormatterMin = DateTimeFormatter.ofPattern("mm");
        //Format to a string
        String formDT = myLocalDateTime.format(myFormatter);
        String formFullDT = myLocalDateTime.format(myFormatterFull);
        String formFullHour = myLocalDateTime.format(myFormatterHour);
        String formFullMin = myLocalDateTime.format(myFormatterMin);
        //System.out.println("Local " + formDT);
        String myApptTime = null;
        int myApptId = 0;
        //return myResultSet;
        while(myResultSet.next()){
            Timestamp time =myResultSet.getTimestamp("Start");
            int apptID = myResultSet.getInt("Appointment_ID");
            String formatMonth = new SimpleDateFormat("MM/dd/yyyy HH:mm").format(time);
            String formatFull = new SimpleDateFormat("MM/dd/yyyy").format(time);
            System.out.println("Appt time: " + formatMonth);
            String formatHour = new SimpleDateFormat("HH").format(time);
            String formatMin = new SimpleDateFormat("mm").format(time);
            //System.out.println(formatHour);
            //Build formatter
            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
            //Parse String to LocalDateTime
            //LocalDateTime dateTimeLocal = LocalDateTime.parse(formDT, formatter);
            LocalDateTime dateTimeAppt = time.toLocalDateTime();
            //int compareDate = myLocalDateTime.compareTo(dateTimeAppt);
            int compareDate = dateTimeAppt.compareTo(myLocalDateTime);
            //System.out.println(compareDate);
            if(formatFull.equals(formFullDT)){
                //JOptionPane.showMessageDialog(frame,"Upcoming appointments date!");
                //System.out.println("Same day");
                if(formFullHour.equals(formatHour)){
                    //JOptionPane.showMessageDialog(frame,"Upcoming appointments hour!");
                    //System.out.println("Same hour");
                    if(Math.abs((Integer.parseInt(formFullMin)) - (Integer.parseInt(formatMin))) <= 15){
                        //System.out.println("15 minute appt");
                        //JOptionPane.showMessageDialog(frame,"Upcoming appointments minutes!");
                        scheduleConflict = true;
                        myApptTime = formatMonth;
                        myApptId = apptID;
                    }
                }
            }
        }
        if(!scheduleConflict) {
            JOptionPane.showMessageDialog(frame, "No appointments upcoming appointments within the next 15 minutes. ");
        } else {
            JOptionPane.showMessageDialog(frame, "Upcoming appointment! " + "\n" + "Appointment ID: " + myApptId + "\n" + "Appointment Date and Time: " + myApptTime);
        }
    }

    public void backToMainController(ActionEvent event) throws IOException {
        /**
         * Method used to go back to the main controller
         */
        Parent root = FXMLLoader.load(getClass().
                getResource(
                        "MainController.fxml"),bundle);
        Stage stage = (Stage) backToReality.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static String getMyNewUser(){
        /**
         * Method used to get the new user information
         */
        System.out.println("In getMyNewUserMethod: " + myNewUser);
        return myNewUser;
    }
}
