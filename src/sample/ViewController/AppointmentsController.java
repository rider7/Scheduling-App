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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.*;
import java.util.Date;

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
    public static ZonedDateTime startString;
    /**LocalDateTime used to hold the end value*/
    public static ZonedDateTime endString;
    /**String used to hold the contact value*/
    public static String contactString;
    /**ArrayList used to hold CustomerIDs*/
    public static ArrayList<Integer> arl = new ArrayList<Integer>();
    /**ArrayList used to hold UserIDs*/
    public static ArrayList<Integer> userArrayList = new ArrayList<Integer>();
    /**ArrayList used to hold ContactIDs*/
    public static ArrayList<String> contactArrayList = new ArrayList<String>();

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
//    @FXML
//    private TextField customerIDField;
    /**TextField used to hold the user ID form*/
//    @FXML
//    private TextField userIDField;
    /**Button used to navigate back to the main page, update page and create appt page*/
    @FXML
    private Button backToReality, updateAppt, createNewAppt;
    /**ComboBox used to display the contacts*/
    @FXML
    private ComboBox contactComboBox, userComboBox, customerComboBox,contactIDComboBox;
    /**Label used to hold the appointment data*/
    @FXML
    Label apptLabel, format1, format2;

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
        One("1"), Two("2"), Three("3");
        public final String contacts2;
        comboBoxContactID(String s) {
            this.contacts2 = s;
        }


    };
    /**Enum used to hold the user ID names*/
    public enum comboBoxUserID{
        One("1"), Two("2");
        public final String users;
        comboBoxUserID(String s) {
            this.users = s;
        }
//        public static int getEnum(int myEnum){
//        comboBoxUserID users[] = comboBoxUserID.values();
//        return users[myEnum];
//    }
    };
    /**Enum used to hold the customer ID names*/
    public enum comboBoxCustomerID{
        One("1"), Two("2");
        public final String customers;
        comboBoxCustomerID(String s) {
            this.customers = s;
        }
    };


    /**
     * Method used to initialize the scene with the appointments controller fxml
     * Lambda Expression used to loop through and print the items in the ArrayList for the Appointments records
     */
    @FXML
    public void initialize() throws SQLException {
        myCustomers();

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
            System.out.println("Initialize:" + " userID " +userID + " contactID " + contactID + " customerID " + customerID);
            //customerIDField.setText(String.valueOf(customerID));
            //userIDField.setText(String.valueOf(userID));
            Collections.sort(arl);
            Collections.sort(userArrayList);
            Collections.sort(contactArrayList);
            userComboBox.getItems().addAll(userArrayList);
//            userComboBox.getItems().addAll(comboBoxUserID.getEnum(0));
            userComboBox.setValue(userID);
            contactComboBox.getItems().addAll(contactArrayList);
            contactComboBox.setValue(contactString);
            customerComboBox.getItems().addAll(arl);
            customerComboBox.setValue(customerID);
            apptLabel.setText("Update Existing Appointment");
            format1.setText("Format Example: 2020-05-29 22:05");
            format2.setText("Format Example: 2020-05-29 22:05");
            createNewAppt.setVisible(false);
            createNewAppt.setDisable(true);
//            if(contactComboBox.getValue().equals("Anika Costa")){
//                contactIDField.setText("1");
//            }else if(contactID==2){
//                contactComboBox.setValue("Daniel Garcia");
//            }else if(contactID==3){
//                contactComboBox.setValue("Li Lee");
//            }
//            if(contactID==1){
//                contactComboBox.setValue("Anika Costa");
//            }else if(contactID==2){
//                contactComboBox.setValue("Daniel Garcia");
//            }else if(contactID==3){
//                contactComboBox.setValue("Li Lee");
//            }
        }else{
            Collections.sort(arl);
            Collections.sort(userArrayList);
            Collections.sort(contactArrayList);
            userComboBox.getItems().addAll(userArrayList);
            contactComboBox.getItems().addAll(contactArrayList);
            customerComboBox.getItems().addAll(arl);
            apptLabel.setText("Create New Appointment");
            format1.setText("Format Example: 2020-05-29 22:05");
            format2.setText("Format Example: 2020-05-29 22:05");
            updateAppt.setVisible(false);
            updateAppt.setDisable(true);
        }
        contactID();
        /**Lambda Expression used to loop through and print the items in the ArrayList for the Appointments records*/
        //Lambda Expression used to loop through and print the items in the ArrayList for the Appointments records
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
    public void onActionInsertAppointment(ActionEvent event) throws SQLException, IOException, ParseException {
        System.out.println("Save Appointment Button Works!");

        String newAppointmentID = appointment_id.getText();
        String newTitleString = title.getText();
        String newDescriptionString = description.getText();
        String newLocationString = myLocation.getText();
        String newTypeString = type.getText();
        String newStartString = start.getText() + ":00";
        String newEndString = end.getText() + ":00";
        //String newContactString = contact.getText();
        String newUser = UsersController.getMyNewUser();
        //String newContactIDString = contactIDField.getText();
        String newAppt = AppointmentsList.getMyNewAppointments();
        //String newCustomerIDString = String.valueOf(customerID);
        String newContactIDString = String.valueOf(contactID);
        String newCustomerIDString = customerComboBox.getValue().toString();
        String newUserIDString = userComboBox.getValue().toString();

        //Format String to Timestamp
        String inDate = "01/10/2020 06:43:21";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = df.parse(newStartString);
        long time = date.getTime();

        Timestamp ts = new Timestamp(time);
        //Code to format UTC time to local timezone for user interface
        Timestamp tsStart = ts;
        ZoneId newzid = ZoneId.systemDefault();

        ZonedDateTime newzdtStart = tsStart.toLocalDateTime().atZone(ZoneId.of(String.valueOf(newzid)));

        ZonedDateTime utcStart = newzdtStart.withZoneSameInstant(ZoneId.of("UTC"));
        Timestamp timestampStart = Timestamp.valueOf(utcStart.toLocalDateTime());

        DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date2 = df2.parse(newEndString);
        long time2 = date2.getTime();

        Timestamp ts2 = new Timestamp(time2);
        //Code to format UTC time to local timezone for user interface
        Timestamp tsEnd = ts2;
        //ZoneId newzid = ZoneId.systemDefault();

        ZonedDateTime newzdtEnd = tsEnd.toLocalDateTime().atZone(ZoneId.of(String.valueOf(newzid)));

        ZonedDateTime utcEnd = newzdtEnd.withZoneSameInstant(ZoneId.of("UTC"));
        Timestamp timestampEnd = Timestamp.valueOf(utcEnd.toLocalDateTime());

        if(newCustomerIDString.equals("One")){
            newCustomerIDString="1";
        } else if(newCustomerIDString.equals("Two")){
            newCustomerIDString="2";
        }else{
            newCustomerIDString="3";
        }
        if(newUserIDString.equals("One")){
            newUserIDString="1";
        } else if(newUserIDString.equals("Two")){
            newUserIDString="2";
        }

        //Schedule conflict check
        Boolean myBool = apptConflictCheck(newCustomerIDString, timestampStart, timestampEnd);

        if(myBool) {
            //Establish connection before launch and assign it to the Connection reference variable named conn
            Connection conn = DBConnection.startConnection();

            //Insert Statement
            String insertStatement = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Created_By, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES(?,?,?,?,?,?,?,?,?,?,?)"; //Question marks are placeholders to be mapped with key values in one-based index

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
            preparedStatement.setTimestamp(5, timestampStart);
            preparedStatement.setTimestamp(6, timestampEnd);
            preparedStatement.setString(7, newUser);
            preparedStatement.setString(8, newUser);
            preparedStatement.setString(9, newCustomerIDString);
            preparedStatement.setString(10, newUserIDString);
            preparedStatement.setString(11, newContactIDString);

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
        Appointments.myAppointments.removeAll(Appointments.myAppointments);
        AppointmentsList.myConnection();
    }
    /**
     * Method used to connect to the database and update the appointment record
     */
    @FXML
    public void onActionUpdateAppointment(ActionEvent event) throws SQLException, IOException, ParseException {
        System.out.println("Update Appointment Button Works!");


        String newAppointmentID = appointment_id.getText();
        String newTitleString = title.getText();
        String newDescriptionString = description.getText();
        String newLocationString = myLocation.getText();
        String newTypeString = type.getText();
        String newStartString = start.getText() + ":00";
        String newEndString = end.getText() + ":00";
        String newContactIDString = String.valueOf(contactID);
        String newCustomerIDString = customerComboBox.getValue().toString();
        String newUserIDString = userComboBox.getValue().toString();

        //Format Stirng to Timestamp
        String inDate = "01/10/2020 06:43:21";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = df.parse(newStartString);
        long time = date.getTime();

        Timestamp ts = new Timestamp(time);
        //Code to format UTC time to local timezone for user interface
        Timestamp tsStart = ts;
        ZoneId newzid = ZoneId.systemDefault();

        ZonedDateTime newzdtStart = tsStart.toLocalDateTime().atZone(ZoneId.of(String.valueOf(newzid)));

        ZonedDateTime utcStart = newzdtStart.withZoneSameInstant(ZoneId.of("UTC"));
        Timestamp timestampStart = Timestamp.valueOf(utcStart.toLocalDateTime());

        DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date2 = df2.parse(newEndString);
        long time2 = date2.getTime();

        Timestamp ts2 = new Timestamp(time2);
        //Code to format UTC time to local timezone for user interface
        Timestamp tsEnd = ts2;
        //ZoneId newzid = ZoneId.systemDefault();

        ZonedDateTime newzdtEnd = tsEnd.toLocalDateTime().atZone(ZoneId.of(String.valueOf(newzid)));

        ZonedDateTime utcEnd = newzdtEnd.withZoneSameInstant(ZoneId.of("UTC"));
        Timestamp timestampEnd = Timestamp.valueOf(utcEnd.toLocalDateTime());

//        if(newCustomerIDString.equals("One") || newCustomerIDString.equals("1")){
//            newCustomerIDString="1";
//        } else if(newCustomerIDString.equals("Two")){
//            newCustomerIDString="2";
//        }else{
//            newCustomerIDString="3";
//        }
//        if(newUserIDString.equals("One")){
//            newUserIDString="1";
//        } else if(newUserIDString.equals("Two")){
//            newUserIDString="2";
//        }

        //String newContactString=contact.getText();
        //String newUser = UsersController.getMyNewUser();
        //String newAppt = AppointmentsList.getMyNewAppointments();
        //System.out.println(newAppointmentID + newTitleString + newDescriptionString + newDescriptionString + newLocationString + newTypeString + newStartString + newEndString + newAppt);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.US);

        LocalDateTime localDate = LocalDateTime.parse(newStartString, formatter);
        LocalDateTime localDate2 = LocalDateTime.parse(newEndString, formatter);
        String myStringStart = (DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(localDate));
        String myStringEnd = (DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(localDate2));

        if(convertTimeZone(newStartString,newEndString)){
            //System.out.println("OUTSIDE OF BUSINESS HOURS TRY AGAIN");
            JOptionPane.showMessageDialog(frame,"Sorry, you are trying to schedule an appointment outside of normal business hours defined as 8:00 a.m. to 10:00 p.m. EST. Please try again.");

    } else {

            //Schedule conflict check
            Boolean myBool = apptConflictCheck(newCustomerIDString, timestampStart, timestampEnd);
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
                preparedStatement.setTimestamp(5, timestampStart);
                preparedStatement.setTimestamp(6, timestampEnd);
                preparedStatement.setString(7, newCustomerIDString);
                preparedStatement.setString(8, newUserIDString);
                preparedStatement.setString(9, newContactIDString);
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
        Appointments.myAppointments.removeAll(Appointments.myAppointments);
        AppointmentsList.myConnection();
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
        if(contactID == 1){
            contactString = "Anika Costa";
        }else if(contactID == 2){
            contactString = "Daniel Garcia";
        }else {
            contactString = "Li Lee";
        }
        //System.out.println("Get Method:" + " userID " +userID + " contactID " + contactID + " customerID " + customerID);
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
        //System.out.println(start);
        //System.out.println(end);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTimeStart = LocalDateTime.parse(start, formatter);
        LocalDateTime dateTimeEnd = LocalDateTime.parse(end, formatter);
        //System.out.println("The start: " + dateTimeStart);
        //System.out.println("The end: " + dateTimeEnd);


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

        //System.out.println("End hour: " + endHour);
//        System.out.println(currentETime);
        //System.out.println(currentISTime);
        //return startHour;
        return myBoolean;
    }
    /**
     * Method used to check if there is a scheduling conflict
     */
    public boolean apptConflictCheck(String customerID, Timestamp start, Timestamp end) throws SQLException {
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

            //Timestamp myNewApptStart = Timestamp.valueOf(start);
            //Timestamp myNewApptEnd = Timestamp.valueOf(end);
            Timestamp myOldApptStart = Timestamp.valueOf(String.valueOf(myResultSet.getTimestamp("Start")));
            Timestamp myOldApptEnd = Timestamp.valueOf(String.valueOf(myResultSet.getTimestamp("End")));;
            //Code to format UTC time to local timezone for user interface
            ZoneId newzid = ZoneId.systemDefault();
            ZonedDateTime newzdtStart = myOldApptStart.toLocalDateTime().atZone(ZoneId.of(String.valueOf(newzid)));
            ZonedDateTime utcStart = newzdtStart.withZoneSameInstant(ZoneId.of("UTC"));
            Timestamp timestampStart = Timestamp.valueOf(utcStart.toLocalDateTime());
            ZonedDateTime newzdtEnd = myOldApptEnd.toLocalDateTime().atZone(ZoneId.of(String.valueOf(newzid)));
            ZonedDateTime utcEnd = newzdtEnd.withZoneSameInstant(ZoneId.of("UTC"));
            Timestamp timestampEnd = Timestamp.valueOf(utcEnd.toLocalDateTime());

            //if statement to compare if start time conflicts
            if(start.after(timestampStart) && end.before(timestampEnd)) {
                //message
                JOptionPane.showMessageDialog(frames,"Time conflicts with prior appointment! Please change and resubmit!");
                isConflict = false;
                break;
            }
            //if statement to compare if end time conflicts
            if(end.after(timestampStart) && start.before(timestampEnd)) {
                //message
                JOptionPane.showMessageDialog(frames,"Time conflicts with prior appointment! Please change and resubmit!");
                isConflict = false;
                break;
            }
        }

    System.out.println("Conflict Schedule Checked");
    return isConflict;
    }

    /**
     * Method used to get contact ID
     */
    public void contactID(){
        try{
        String contactIDTest = contactComboBox.getValue().toString();
        if(contactIDTest.equals("Anika Costa")){
            contactID=1;
            contactIDField.setText("1");
        }else if(contactIDTest.equals("Daniel Garcia")){
            contactID=2;
            contactIDField.setText("2");
        }else{
            contactID=3;
            contactIDField.setText("3");
        }}
        catch(Exception e){System.out.println("Something is wrong");}
        //System.out.println(contactIDTest);
    };

    /**
     * Method to get customer IDs
     */
    public static void myCustomers() throws SQLException {
        //Establish connection before launch and assign it to the Connection reference variable named conn
        Connection conn = DBConnection.startConnection();
        //Pass conn object to statement
        Query.setStatement(conn); //Create statement object
        Statement statement =  Query.getTestStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE); //Get Statement reference

        //Select all records from customers table
        String selectStatement = "SELECT Customer_ID FROM customers"; //SQL statement

        try {
            statement.execute(selectStatement); //Execute statement (returns true)
            ResultSet myResultSet = statement.getResultSet(); //Get the result sets and assigns to reference variable myResultSet
            System.out.println("Got customer ID table!");

            //Forward scroll ResultSet
            while (myResultSet.next()) { //next() method returns true so while it equals true the loop will be active, looping through all records ***also closes the resultSet
                //Code to format UTC time to local timezone for user interface


                int customerID = myResultSet.getInt("Customer_ID"); //Local variable countryID is assigned the value of getInt() method on myResultSet with the column name as a parameter.
                arl.add(customerID);
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        //Select all records from customers table
        String selectStatement2 = "SELECT User_ID FROM users"; //SQL statement

        try {
            statement.execute(selectStatement2); //Execute statement (returns true)
            ResultSet myResultSet = statement.getResultSet(); //Get the result sets and assigns to reference variable myResultSet
            System.out.println("Got user ID table!");

            //Forward scroll ResultSet
            while (myResultSet.next()) { //next() method returns true so while it equals true the loop will be active, looping through all records ***also closes the resultSet
                //Code to format UTC time to local timezone for user interface


                int userID = myResultSet.getInt("User_ID"); //Local variable countryID is assigned the value of getInt() method on myResultSet with the column name as a parameter.
                userArrayList.add(userID);
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        //Select all records from customers table
        String selectStatement3 = "SELECT Contact_Name FROM contacts"; //SQL statement

        try {
            statement.execute(selectStatement3); //Execute statement (returns true)
            ResultSet myResultSet = statement.getResultSet(); //Get the result sets and assigns to reference variable myResultSet
            System.out.println("Got contact ID table!");

            //Forward scroll ResultSet
            while (myResultSet.next()) { //next() method returns true so while it equals true the loop will be active, looping through all records ***also closes the resultSet
                //Code to format UTC time to local timezone for user interface


               String contactName = myResultSet.getString("Contact_Name"); //Local variable countryID is assigned the value of getInt() method on myResultSet with the column name as a parameter.
                contactArrayList.add(contactName);
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
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
