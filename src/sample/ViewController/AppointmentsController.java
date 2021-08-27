package sample.ViewController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Model.Appointments;
import sample.Model.Customers;
import sample.Utilities.DBConnection;
import sample.Utilities.Query;

import javax.swing.*;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.*;
/**
 * Class used for data manipulation of appointment records
 */
public class AppointmentsController {
    //Set language resource bundle
    //Locale.setDefault(new Locale("fr"));
    Locale locale = new Locale("en");
    //System.out.println(Locale.getDefault());
    ResourceBundle bundle = ResourceBundle.getBundle("sample.Utilities.ResourceBundles.text", locale);

    //Attributes
    /**Integer used to hold the appointment ID value*/
    public static int appointmentID;
    /**Integer used to hold the contact ID value*/
    public static int contactID;
    /**Integer used to hold the customer ID value*/
    public static int customerID;
    /**Integer used to hold the user ID value*/
    public static int userID;
    /**String used to hold the title string value*/
    public static String titleString;
    /**String used to hold the description string value*/
    public static String descriptionString;
    /**String used to hold the location string value*/
    public static String locationString;
    /**String used to hold the type string value*/
    public static String typeString;
    /**LocalDateTime used to hold the start value*/
    public static LocalDateTime startString;
    /**LocalDateTime used to hold the end value*/
    public static LocalDateTime endString;
    /**String used to hold the contact value*/
    public static String contactString;

    /**TextField used to hold the appointment id form*/
    @FXML
    private TextField appointment_id;
    /**TextField used to hold the title form*/
    @FXML
    private TextField title;
    /**TextField used to hold the description form*/
    @FXML
    private TextField description;
    /**TextField used to hold the location form*/
    @FXML
    private TextField myLocation;
    /**TextField used to hold the type form*/
    @FXML
    private TextField type;
    /**TextField used to hold the contact form*/
    @FXML
    private TextField contact;
    /**TextField used to hold the start form*/
    @FXML
    private TextField start;
    /**TextField used to hold the end form*/
    @FXML
    private TextField end;
    /**TextField used to hold the contact ID form*/
    @FXML
    private TextField contactIDField;
    /**TextField used to hold the customer ID form*/
    @FXML
    private TextField customerIDField;
    /**TextField used to hold the user ID form*/
    @FXML
    private TextField userIDField;
    /**Button used to navigate back to the main page, update page and create appt page*/
    @FXML
    private Button backToReality, updateAppt, createNewAppt;
    /**ComboBox used to display the contacts*/
    @FXML
    private ComboBox contactComboBox;
    /**Label used to hold the appointment data*/
    @FXML
    Label apptLabel;

    JFrame frame;
    JFrame frames;
    //Enums
    /**Enum used to hold the contact names*/
    public enum comboBoxContactName{
        AnikaCosta("Anika Costa"), DanielGarcia("Daniel Garcia"), LiLee("Li Lee");
        //Contact_ID 1, 2, 3;
            public final String contacts;
        comboBoxContactName(String s) {
            this.contacts = s;
        }
    };
    /**Enum used to hold the contact ID names*/
    public enum comboBoxContactID{
        AnikaCosta("Anika Costa"), DanielGarcia("Daniel Garcia"), LiLee("Li Lee");
        public final String contacts;
        comboBoxContactID(String s) {
            this.contacts = s;
        }
    };


    /**
     * Method used to initialize the scene with the appointments controller fxml
     */
    @FXML
    public void initialize() {
        //convertTimeZone();
        if (appointmentID > 0) {
            appointment_id.setText(String.valueOf(appointmentID));
            title.setText(titleString);
            description.setText(descriptionString);
            myLocation.setText(locationString);
            type.setText(typeString);
            start.setText(startString.toString());
            end.setText(endString.toString());
            contactIDField.setText(String.valueOf(contactID));
            customerIDField.setText(String.valueOf(customerID));
            userIDField.setText(String.valueOf(userID));
            contactComboBox.getItems().addAll(comboBoxContactName.values());
            apptLabel.setText("Update Existing Appointment");
            createNewAppt.setVisible(false);
            createNewAppt.setDisable(true);
            if(contactID==1){
                contactComboBox.setValue("Anika Costa");
            }else if(contactID==2){
                contactComboBox.setValue("Daniel Garcia");
            }else if(contactID==3){
                contactComboBox.setValue("Li Lee");
            }
        }else{
            contactComboBox.getItems().clear();
            contactComboBox.getItems().addAll(comboBoxContactName.values());
            apptLabel.setText("Create New Appointment");
            updateAppt.setVisible(false);
            updateAppt.setDisable(true);
        }

        ArrayList<String> numbers = new ArrayList<String>();
        numbers.add(titleString);
        numbers.add(descriptionString);
        numbers.add(locationString);
        numbers.add(typeString);
        numbers.forEach((n) -> {System.out.println(n);});

    }
    /**
     * Method used to connect to the database and insert appointment record data
     */
    @FXML
    public void onActionInsertAppointment(ActionEvent event) throws SQLException, IOException {
        System.out.println("Save Appointment Button Works!");

        String newAppointmentID = appointment_id.getText();
        String newTitleString = title.getText();
        String newDescriptionString = description.getText();
        String newLocationString = myLocation.getText();
        String newTypeString= type.getText();
        String newStartString= start.getText();
        String newEndString= end.getText();
        //String newContactString=contact.getText();
        String newUser = UsersController.getMyNewUser();
        String newContactIDString= contactIDField.getText();
        String newAppt = AppointmentsList.getMyNewAppointments();
        String newCustomerIDString = customerIDField.getText();

        //Schedule conflict check
        Boolean myBool = apptConflictCheck(newCustomerIDString, newStartString, newEndString);

        if(myBool) {
            //Establish connection before launch and assign it to the Connection reference variable named conn
            Connection conn = DBConnection.startConnection();

            //Insert Statement
            String insertStatement = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Created_By, Last_Updated_By, Customer_ID) VALUES(?,?,?,?,?,?,?,?,?)"; //Question marks are placeholders to be mapped with key values in one-based index

            //Create prepared statement object for insertStatement
            Query.setPreparedStatement(conn, insertStatement);

            //Get the prepared statement reference
            PreparedStatement preparedStatement = Query.getPreparedStatement();

            //Key-value mapping to set the prepared statement
            //preparedStatement.setString(1,newAppointmentID);
            preparedStatement.setString(1, newTitleString);
            preparedStatement.setString(2, newDescriptionString);
            preparedStatement.setString(3, newLocationString);
            preparedStatement.setString(4, newTypeString);
            preparedStatement.setString(5, newStartString);
            preparedStatement.setString(6, newEndString);
            preparedStatement.setString(7, newUser);
            preparedStatement.setString(8, newUser);
            preparedStatement.setString(9, newCustomerIDString);

            preparedStatement.execute(); //Execute prepared statement

            //Check rows affected
            if (preparedStatement.getUpdateCount() > 0)
                System.out.println("Rows affected: " + preparedStatement.getUpdateCount());
            else System.out.println("No change!");
            Parent root = FXMLLoader.load(getClass().
                    getResource(
                            "AppointmentsList.fxml"), bundle);
            Stage stage = (Stage) updateAppt.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
    /**
     * Method used to connect to the database and update the appointment record
     */
    @FXML
    public void onActionUpdateAppointment(ActionEvent event) throws SQLException, IOException {
        System.out.println("Update Appointment Button Works!");

        String newAppointmentID = appointment_id.getText();
        String newTitleString = title.getText();
        String newDescriptionString = description.getText();
        String newLocationString = myLocation.getText();
        String newTypeString= type.getText();
        String newStartString= start.getText();
        String newEndString= end.getText();
        String newContactIDString= contactIDField.getText();
        String newCustomerIDString= customerIDField.getText();
        String newUserIDString= userIDField.getText();
        //String newContactString=contact.getText();
        //String newUser = UsersController.getMyNewUser();
        //String newAppt = AppointmentsList.getMyNewAppointments();
        //System.out.println(newAppointmentID + newTitleString + newDescriptionString + newDescriptionString + newLocationString + newTypeString + newStartString + newEndString + newAppt);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm", Locale.US);

        LocalDateTime localDate = LocalDateTime.parse(newStartString, formatter);
        LocalDateTime localDate2 = LocalDateTime.parse(newEndString, formatter);
        String myStringStart = (DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(localDate)) + ":00";
        String myStringEnd = (DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(localDate2)) + ":00";

        if(convertTimeZone(newStartString,newEndString)){
            System.out.println("OUTSIDE OF BUSINESS HOURS TRY AGAIN");
            JOptionPane.showMessageDialog(frame,"Sorry, you are trying to schedule an appointment outside of normal business hours defined as 8:00 a.m. to 10:00 p.m. EST. Please try again.");

    } else {
            //Schedule conflict check
            Boolean myBool = apptConflictCheck(newCustomerIDString, myStringStart, myStringEnd);
            if (myBool) {
                //Establish connection before launch and assign it to the Connection reference variable named conn
                Connection conn = DBConnection.startConnection();

                //Update Statement
                String updateStatement = "UPDATE appointments SET Title=?, Description=?, Location=?, Type=?, Start=?, End=?, Customer_ID=?, User_ID=?, Contact_ID=? WHERE Appointment_ID=?"; //Question marks are placeholders to be mapped with key values in one-based index

                //Create prepared statement object for insertStatement
                Query.setPreparedStatement(conn, updateStatement);

                //Get the prepared statement reference
                PreparedStatement preparedStatement = Query.getPreparedStatement();

                //Key-value mapping to set the prepared statement
                preparedStatement.setString(1, newTitleString);
                preparedStatement.setString(2, newDescriptionString);
                preparedStatement.setString(3, newLocationString);
                preparedStatement.setString(4, newTypeString);
                preparedStatement.setString(5, myStringStart);
                preparedStatement.setString(6, myStringEnd);
                preparedStatement.setString(7, newContactIDString);
                preparedStatement.setString(8, newCustomerIDString);
                preparedStatement.setString(9, newUserIDString);
                preparedStatement.setString(10, newAppointmentID);

                preparedStatement.execute(); //Execute prepared statement

                //Check rows affected
                if (preparedStatement.getUpdateCount() > 0)
                    System.out.println("Rows affected: " + preparedStatement.getUpdateCount());
                else System.out.println("No change!");

                Parent root = FXMLLoader.load(getClass().
                        getResource(
                                "AppointmentsList.fxml"), bundle);
                Stage stage = (Stage) updateAppt.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        }
    }
    /**
     * Method used to get the appointment object data
     */
    public static void getAppointmentData(Appointments appointment){
        appointmentID = appointment.getAppointment_ID();
        titleString = appointment.getTitle();
        descriptionString = appointment.getDescription();
        locationString = appointment.getLocation();
        typeString = appointment.getType();
        startString = appointment.getStart();
        endString = appointment.getEnd();
        contactID = appointment.getContact_ID();
        customerID = appointment.getCustomer_ID();
        userID = appointment.getUser_ID();
    };
    /**
     * Method used to get the timezone
     */
    public static String getTimeZone(){
        //Get Calendar instance
        Calendar now = Calendar.getInstance();

        //Get current timezone using get timezone method of calendar class
        TimeZone timezone = now.getTimeZone();

        //System.out.print(timezone.getDisplayName());
        return timezone.getDisplayName();
    }
    /**
     * Method used to convert the time zone
     */
    public static boolean convertTimeZone(String start, String end) {
        Boolean myBoolean;
        //Convert string to LocalDateTime format
        //String myStart = start;
        //String myEnd = end;
        //2007-12-03T10:15:30
        System.out.println(start);
        System.out.println(end);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime dateTimeStart = LocalDateTime.parse(start, formatter);
        LocalDateTime dateTimeEnd = LocalDateTime.parse(end, formatter);
        System.out.println("The start: " + dateTimeStart);
        System.out.println("The end: " + dateTimeEnd);


        final String DATE_FORMAT = "dd-M-yyyy hh:mm:ss a z";
        final DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern(DATE_FORMAT);

        ZoneId fromTimeZone = ZoneId.systemDefault();    //Source timezone
        ZoneId toTimeZone = ZoneId.of("America/New_York");  //Target timezone

        LocalDateTime today = LocalDateTime.now();          //Current time

        //Zoned date time at start source timezone
        ZonedDateTime startTime = dateTimeStart.atZone(fromTimeZone);
        //Zoned date time at source timezone
        ZonedDateTime endTime = dateTimeEnd.atZone(fromTimeZone);

        //Zoned date time at target timezone
        ZonedDateTime startETime = startTime.withZoneSameInstant(toTimeZone);
        //Zoned date time at target timezone
        ZonedDateTime endETime = endTime.withZoneSameInstant(toTimeZone);
        int startHour = startETime.get(ChronoField.HOUR_OF_DAY);
        int endHour = endETime.get(ChronoField.HOUR_OF_DAY);
        if (startHour < 8 || endHour > 22 || endHour == 0) {
            myBoolean = true;
            System.out.println("Outside of Business Hours");
        } else{
            myBoolean = false;
        }

        System.out.println("End hour: " + endHour);
//        System.out.println(currentETime);
        //System.out.println(currentISTime);
        //return startHour;
        return myBoolean;
    }
    /**
     * Method used to check if there is a scheduling conflict
     */
    public boolean apptConflictCheck(String customerID, String start, String end) throws SQLException {
        boolean isConflict = true;
        //connect to database and select all appts based on user_id passed through method
        //Establish connection before launch and assign it to the Connection reference variable named conn
        Connection conn = DBConnection.startConnection();
        //Select all records from countries table
        String selectStatement = "SELECT * FROM appointments WHERE Customer_ID = ?"; //SQL statement
        //String selectStatement = "SELECT * FROM appointments"; //SQL statement
        //Pass conn object to statement
        Query.setPreparedStatement(conn, selectStatement); //Create statement object
        PreparedStatement statement = Query.getPreparedStatement(); //Get Statement reference
        //Key-value mapping to set the prepared statement based on userid passed through this method
        statement.setString(1, customerID);
        statement.execute(); //Execute statement (returns true)
        ResultSet myResultSet = statement.getResultSet(); //Get the result sets and assigns to reference variable myResultSet
        while(myResultSet.next()) {
            Timestamp myNewApptStart = Timestamp.valueOf(start);
            Timestamp myNewApptEnd = Timestamp.valueOf(end);
            Timestamp myOldApptStart = Timestamp.valueOf(String.valueOf(myResultSet.getTimestamp("Start")));
            Timestamp myOldApptEnd = Timestamp.valueOf(String.valueOf(myResultSet.getTimestamp("End")));;
            //if statement to compare if start time conflicts
            if(myNewApptStart.after(myOldApptStart) && myNewApptStart.before(myOldApptEnd)) {
                //message
                JOptionPane.showMessageDialog(frames,"Start time conflicts with prior appointment! Please change and resubmit!");
                isConflict = false;
                break;
            }
            //if statement to compare if end time conflicts
            if(myNewApptEnd.after(myOldApptStart) && myNewApptEnd.before(myOldApptEnd)) {
                //message
                JOptionPane.showMessageDialog(frames,"End time conflicts with prior appointment! Please change and resubmit!");
                isConflict = false;
                break;
            }
        }

    System.out.println("Conflict Schedule Checked");
    return isConflict;
    }

    /**
     * Method used to navigate back to the main controller
     */
    @FXML
    private void backToMainController(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().
                getResource(
                        "AppointmentsList.fxml"),bundle);
        Stage stage = (Stage) backToReality.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
