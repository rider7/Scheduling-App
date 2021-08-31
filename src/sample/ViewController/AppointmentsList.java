package sample.ViewController;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.Model.Appointments;
import sample.Model.Customers;
import sample.Utilities.DBConnection;
import sample.Utilities.Query;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
/**
 * Class used to manipulate data for appointment records
 */
public class AppointmentsList implements Initializable {
    //Set language resource bundle
    //Locale.setDefault(new Locale("fr"));
    Locale locale = new Locale("en");
    //System.out.println(Locale.getDefault());
    ResourceBundle bundle = ResourceBundle.getBundle("sample.Utilities.ResourceBundles.text", locale);
    //Message box instance
    JFrame frame;


    //FXML attributes
    //TableView field
    /**
     * TablewView used to display the appointments list
     */
    @FXML
    public TableView<Appointments> myAppointmentsList;
    //Table Columns
    /**
     * Table Column used to display the appointment ID
     */
    @FXML
    private TableColumn<Appointments, Integer> appointmentID;
    /**
     * Table Column used to display the title
     */
    @FXML
    private TableColumn<Appointments, String> title;
    /**
     * Table Column used to display the description
     */
    @FXML
    private TableColumn<Appointments, String> description;
    /**
     * Table Column used to display the location
     */
    @FXML
    private TableColumn<Appointments, String> myLocation;
    /**
     * Table Column used to display the type
     */
    @FXML
    private TableColumn<Appointments, String> type;
    /**
     * Table Column used to display the start
     */
    @FXML
    private TableColumn<Appointments, String> start;
    /**
     * Table Column used to display the end
     */
    @FXML
    private TableColumn<Appointments, String> end;
    /**
     * Table Column used to display the customer ID
     */
    @FXML
    private TableColumn<Appointments, Integer> customerID;
    /**
     * Table Column used to display the contact ID
     */
    @FXML
    private TableColumn<Appointments, Integer> contactID;
//    @FXML
//    private TableColumn<Appointments, String> createDate;
//    @FXML
//    private TableColumn<Appointments, String> createdBy;
//    @FXML
//    private TableColumn<Appointments, String> lastUpdate;
//    @FXML
//    private TableColumn<Appointments, String> lastUpdatedBy;
//    @FXML
//    private TableColumn<Appointments, Integer> userID;
//    @FXML
//    private TableColumn<Appointments, String> contactName;

    //FXML Buttons
    /**
     * Button used to navigate back to the main page
     */
    @FXML
    private Button backToReality;
    /**
     * Button used to go to the appointment page
     */
    @FXML
    private Button goToAppointmentButton;
    /**
     * Button used to delete appointments by selection
     */
    @FXML
    private Button onActionDeleteAppointmentButton;
    /**
     * Button used to navigate to the reports page
     */
    @FXML
    Button toReports;
    /**
     * RadioButton used to sort the appointments by month
     */
    @FXML
    private RadioButton myAppointmentSort1;
    /**
     * RadioButton used to sort the appointments by week
     */
    @FXML
    private RadioButton myAppointmentSort2;
    /**
     * ToggleGroup used for the week/month display options
     */
    @FXML
    public ToggleGroup myToggleGroup;
    /**
     * String used to hold data for toggle filter
     */
    public String toggleFilter;
    /**
     * String used to keep track of new appointment
     */
    public static String myNewAppt;
    /**
     * String used to keep track of current week
     */
    public String currentWeek;
    /**
     * String used to keep track of current month
     */
    public String currentMonth;

    /**
     * Method used to connect to the database and delete appointments by appointment_id
     */
    @FXML
    public void onActionDeleteAppointment(ActionEvent event) throws SQLException {
        //Establish connection before launch and assign it to the Connection reference variable named conn
        Connection conn = DBConnection.startConnection();

        //Delete statement
        String deleteStatement = "DELETE FROM appointments WHERE appointment_id = ?";

        //Create prepared statement object for deleteStatement
        Query.setPreparedStatement(conn, deleteStatement);
        //Prepared statement reference
        PreparedStatement preparedStatement = Query.getPreparedStatement();
        //Create instance of Appointments that is selected from tableview myAppointmentList
        Appointments deleteSelectedAppointment = myAppointmentsList.getSelectionModel().getSelectedItem();
        //assign the appointment_ID of the deleteSelectedAppointment instance to the local variable Appointment_ID to be used in the sql delete statement above
        int Appointment_ID = deleteSelectedAppointment.getAppointment_ID();
        String type = deleteSelectedAppointment.getType();
        System.out.println(Appointment_ID);
        //Key-value mapping to set the prepared statement
        preparedStatement.setInt(1, Appointment_ID);

        preparedStatement.execute(); //Execute prepared statement
        //Delete the appointment from tableview as well
        Appointments.deleteAppointment(deleteSelectedAppointment);
        JOptionPane.showMessageDialog(frame, "You have deleted the appointment with the following details " + "\n" + "Appointment ID: " + Appointment_ID + "\n" + "Appointment Type: " + type);
        //System.out.println("Appointment Deleted!" + " Appointment ID: " + Appointment_ID + "Appointment Type: " + type);
    }

    /**
     * Method used to go to appointments controller
     */
    @FXML
    private void goToAppointmentsController(ActionEvent event) throws IOException {
        AppointmentsController.appointmentID = 0;
        Parent root = FXMLLoader.load(getClass().
                getResource(
                        "AppointmentsController.fxml"), bundle);
        Stage stage = (Stage) goToAppointmentButton.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Method used to back to the main controller
     */
    @FXML
    private void backToMainController(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().
                getResource(
                        "MainController.fxml"), bundle);
        Stage stage = (Stage) backToReality.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Method used to initialize the scene with appointment lists
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Appointments.myAppointments.removeAll(Appointments.myAppointments);

        try {
            myConnection();
            Appointments.myAppointments.remove(Appointments.myAppointments.size()-1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        //Sets the column for Appointments
        appointmentID.setCellValueFactory(new PropertyValueFactory<Appointments, Integer>("Appointment_ID"));
        title.setCellValueFactory(new PropertyValueFactory<Appointments, String>("Title"));
        description.setCellValueFactory(new PropertyValueFactory<Appointments, String>("Description"));
        myLocation.setCellValueFactory(new PropertyValueFactory<Appointments, String>("Location"));
        type.setCellValueFactory(new PropertyValueFactory<Appointments, String>("Type"));
        start.setSortType(TableColumn.SortType.ASCENDING);

        start.setCellValueFactory(new PropertyValueFactory<Appointments, String>("Start"));
        end.setCellValueFactory(new PropertyValueFactory<Appointments, String>("End"));
        customerID.setCellValueFactory(new PropertyValueFactory<Appointments, Integer>("Customer_ID"));
        contactID.setCellValueFactory(new PropertyValueFactory<Appointments, Integer>("Contact_ID"));
//        createDate.setCellValueFactory(new PropertyValueFactory<Appointments, String>("Create_Date"));
//        createdBy.setCellValueFactory(new PropertyValueFactory<Appointments, String>("Created_By"));
//        lastUpdate.setCellValueFactory(new PropertyValueFactory<Appointments, String>("Last_Update"));
//        lastUpdatedBy.setCellValueFactory(new PropertyValueFactory<Appointments, String>("Last_Updated_By"));
//        userID.setCellValueFactory(new PropertyValueFactory<Appointments, Integer>("User_ID"));
        //contactName.setCellValueFactory(new PropertyValueFactory<Appointments, String>("Postal_Code"));

        //Sets the items on the myCustomerList table from the Observable list for appointments via the getAllAppointments() method call
        myAppointmentsList.setItems(getAllAppointments());
        myAppointmentsList.getSortOrder().add(start);
        myAppointmentsList.sort();
    }

    /**
     * Method used to return ObservableList of appointments
     */
    public static ObservableList<Appointments> getAllAppointments() {
        System.out.println("Appointment List Size: " + Appointments.myAppointments.size());
        return Appointments.myAppointments;
    }

    /**
     * Method used to connect to the database and to select appointments
     */
    public static void myConnection() throws SQLException {
        Appointments.myAppointments.removeAll(Appointments.myAppointments);
        //Establish connection before launch and assign it to the Connection reference variable named conn
        Connection conn = DBConnection.startConnection();
        //Pass conn object to statement
        Query.setStatement(conn); //Create statement object
        Statement statement = Query.getTestStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE); //Get Statement reference

        //Select all records from countries table
        String selectStatement = "SELECT * FROM appointments"; //SQL statement

        try {
            statement.execute(selectStatement); //Execute statement (returns true)
            ResultSet myResultSet = statement.getResultSet(); //Get the result sets and assigns to reference variable myResultSet
            System.out.println("Got appointments table!");

            //Forward scroll ResultSet
            while (myResultSet.next()) { //next() method returns true so while it equals true the loop will be active, looping through all records ***also closes the resultSet
                int appointmentID = myResultSet.getInt("Appointment_ID"); //Local variable appointmentID is assigned the value of getInt() method on myResultSet with the column name as a parameter.
                System.out.println("My appointment ID for myConnectin: " + appointmentID);
                int customerID = myResultSet.getInt("Customer_ID");
                int userID = myResultSet.getInt("User_ID");
                int contactID = myResultSet.getInt("Contact_ID");
                String title = myResultSet.getString("Title");
                String description = myResultSet.getString("Description");
                String location = myResultSet.getString("Location");
                String type = myResultSet.getString("Type");
                LocalDateTime start = myResultSet.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = myResultSet.getTimestamp("End").toLocalDateTime();
                LocalDateTime createDate = myResultSet.getTimestamp("Create_Date").toLocalDateTime(); //Need toLocalDate() method to convert Date to LocalDate
                String createdBy = myResultSet.getString("Created_By");
                LocalDateTime updateDate = myResultSet.getTimestamp("Last_Update").toLocalDateTime(); //Need toLocalDateTime() method to convert. Using timestamp type
                //LocalTime updateTime = myResultSet.getTime("Last_Update").toLocalTime();
                String updatedBy = myResultSet.getString("Last_Updated_By");
                //Create new instance of Appointments called newAppointment with the local variables that have been assigned the values found in the ResultSet from the SQL database
                Appointments newAppointments = new Appointments(appointmentID, customerID, userID, contactID, title, description, location, type, start, end, createDate, createdBy, updateDate, updatedBy);
                //Call addAppointments method with newAppointment instance passed to add to the observableList
                Appointments.addAppointments(newAppointments);
                //Appointments.updateAppointments(newAppointments);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        //Appointments.myAppointments.removeAll();
    }

    /**
     * Method used to get new appointments
     */
    public static String getMyNewAppointments() {
        System.out.println("In getMyNewApptMethod: " + myNewAppt);
        return myNewAppt;
    }

    /**
     * Method used to get the toggle filter
     */
    public void getToggleFilter() throws SQLException {
        toggleFilter = "Month Filter";
        //System.out.println(myToggleGroup.getSelectedToggle());
        System.out.println(toggleFilter);
        apptMonthWeekFilter();
    }

    /**
     * Method used to see which option was toggled
     */
    public void getToggleFilter2() throws SQLException {
        //System.out.println(myToggleGroup.getSelectedToggle());
        toggleFilter = "Week Filter";
        System.out.println(toggleFilter);
        apptWeekFilter();
    }

    /**
     * Method used to see which option was toggled
     */
    public void getToggleFilter3() throws SQLException {
        //System.out.println(myToggleGroup.getSelectedToggle());
        toggleFilter = "All Appointments Filter";
        System.out.println(toggleFilter);
        myConnection();
    }

    /**
     * Method used to go to the appointment list page with updated appointment
     */
    @FXML
    private void goToAppointmentUpdate(ActionEvent event) throws IOException {
        //Create instance of Appointments that is selected from tableview myAppointmentList
        Appointments updateSelectedAppointment = myAppointmentsList.getSelectionModel().getSelectedItem();
        //Call method to pass updatedSelectedAppointment object to AppointmentsController for use in populating data fields
        AppointmentsController.getAppointmentData(updateSelectedAppointment);

        Parent root = FXMLLoader.load(getClass().
                getResource(
                        "AppointmentsController.fxml"), bundle);
        Stage stage = (Stage) goToAppointmentButton.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Method to find the appointment start date month and week
     */
    public void apptMonthWeekFilter() throws SQLException {
        Appointments.myAppointments.removeAll(Appointments.myAppointments);
        //Get local date time
        LocalDateTime myLocalDateTime = LocalDateTime.now();
        //Custom format pattern
        DateTimeFormatter myFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
        DateTimeFormatter myFormatterFull = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        DateTimeFormatter myMonthFull = DateTimeFormatter.ofPattern("MM");
        DateTimeFormatter myFormatterHour = DateTimeFormatter.ofPattern("HH");
        DateTimeFormatter myFormatterMin = DateTimeFormatter.ofPattern("mm");
        //Format to a string
        String currentDT = myLocalDateTime.format(myFormatter);
        String currentFullDT = myLocalDateTime.format(myFormatterFull);
        String currentMonthDT = myLocalDateTime.format(myMonthFull);
        String currentFullHour = myLocalDateTime.format(myFormatterHour);
        String currentFullMin = myLocalDateTime.format(myFormatterMin);
        //Appt dates and time strings
        String apptDT = myLocalDateTime.format(myFormatter);
        String apptFullDT = myLocalDateTime.format(myFormatterFull);
        String apptFullHour = myLocalDateTime.format(myFormatterHour);
        String apptFullMin = myLocalDateTime.format(myFormatterMin);

        //Establish connection before launch and assign it to the Connection reference variable named conn
        Connection conn = DBConnection.startConnection();
        //Pass conn object to statement
        Query.setStatement(conn); //Create statement object
        Statement statement = Query.getTestStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE); //Get Statement reference

        //Select all records from countries table
        String selectStatement = "SELECT * FROM appointments"; //SQL statement

        try {
            statement.execute(selectStatement); //Execute statement (returns true)
            ResultSet myResultSet = statement.getResultSet(); //Get the result sets and assigns to reference variable myResultSet
            System.out.println("Got appointments table!");

            //Forward scroll ResultSet
            while (myResultSet.next()) { //next() method returns true so while it equals true the loop will be active, looping through all records ***also closes the resultSet
                int appointmentID = myResultSet.getInt("Appointment_ID"); //Local variable appointmentID is assigned the value of getInt() method on myResultSet with the column name as a parameter.
                int customerID = myResultSet.getInt("Customer_ID");
                int userID = myResultSet.getInt("User_ID");
                int contactID = myResultSet.getInt("Contact_ID");
                String title = myResultSet.getString("Title");
                String description = myResultSet.getString("Description");
                String location = myResultSet.getString("Location");
                String type = myResultSet.getString("Type");
                LocalDateTime start = myResultSet.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = myResultSet.getTimestamp("End").toLocalDateTime();
                LocalDateTime createDate = myResultSet.getTimestamp("Create_Date").toLocalDateTime(); //Need toLocalDate() method to convert Date to LocalDate
                String createdBy = myResultSet.getString("Created_By");
                LocalDateTime updateDate = myResultSet.getTimestamp("Last_Update").toLocalDateTime(); //Need toLocalDateTime() method to convert. Using timestamp type
                //LocalTime updateTime = myResultSet.getTime("Last_Update").toLocalTime();
                String updatedBy = myResultSet.getString("Last_Updated_By");
                //String apptDT = myLocalDateTime.format(myFormatter);
                String apptMonth = start.format(myMonthFull);
//                String apptFullHour = myLocalDateTime.format(myFormatterHour);
//                String apptFullMin = myLocalDateTime.format(myFormatterMin);
                //If the resultset meets the the requirements of the filter variable then add it to the observableList
                System.out.println("Current Month: " + currentMonthDT + " Appt Date: " + apptMonth);

                if(currentMonthDT.equals(apptMonth)) {
                    System.out.println("Month True Filter");
                    //Create new instance of Appointments called newAppointment with the local variables that have been assigned the values found in the ResultSet from the SQL database
                    Appointments newAppointments = new Appointments(appointmentID, customerID, userID, contactID, title, description, location, type, start, end, createDate, createdBy, updateDate, updatedBy);
                    //Call addAppointments method with newAppointment instance passed to add to the observableList
                    Appointments.addAppointments(newAppointments);
                    //Appointments.updateAppointments(newAppointments);

                }
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Method to find the appointment start date month and week
     */
    public void apptWeekFilter() throws SQLException {

        Appointments.myAppointments.removeAll(Appointments.myAppointments);
        //Get local date time
        LocalDateTime myLocalDateTime = LocalDateTime.now();
        //Custom format pattern
//        DateTimeFormatter myFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
//        DateTimeFormatter myFormatterFull = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        DateTimeFormatter myFormatterYear = DateTimeFormatter.ofPattern("yyyy");
        DateTimeFormatter myMonthFull = DateTimeFormatter.ofPattern("MM");
        DateTimeFormatter myFormatterDay = DateTimeFormatter.ofPattern("dd");
//        DateTimeFormatter myFormatterHour = DateTimeFormatter.ofPattern("HH");
//        DateTimeFormatter myFormatterMin = DateTimeFormatter.ofPattern("mm");
        //Format to a string
//        String currentDT = myLocalDateTime.format(myFormatter);
//        String currentFullDT = myLocalDateTime.format(myFormatterFull);
        String currentMonthDT = myLocalDateTime.format(myMonthFull);
        String currentFullDay = myLocalDateTime.format(myFormatterDay);
        String currentFullYear = myLocalDateTime.format(myFormatterYear);
        //Appt dates and time strings
//        String apptDT = myLocalDateTime.format(myFormatter);
//        String apptFullDT = myLocalDateTime.format(myFormatterFull);
//        String apptFullHour = myLocalDateTime.format(myFormatterHour);
//        String apptFullMin = myLocalDateTime.format(myFormatterMin);

        LocalDate date = LocalDate.of(Integer.parseInt(currentFullYear), Integer.parseInt(currentMonthDT), Integer.parseInt(currentFullDay));
        int currentWeekOfYear = date.get(ChronoField.ALIGNED_WEEK_OF_YEAR) - 1;
        System.out.println(currentWeekOfYear);

        //Establish connection before launch and assign it to the Connection reference variable named conn
        Connection conn = DBConnection.startConnection();
        //Pass conn object to statement
        Query.setStatement(conn); //Create statement object
        Statement statement = Query.getTestStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE); //Get Statement reference

        //Select all records from countries table
        String selectStatement = "SELECT * FROM appointments"; //SQL statement

        try {
            statement.execute(selectStatement); //Execute statement (returns true)
            ResultSet myResultSet = statement.getResultSet(); //Get the result sets and assigns to reference variable myResultSet
            System.out.println("Got appointments table!");

            //Forward scroll ResultSet
            while (myResultSet.next()) { //next() method returns true so while it equals true the loop will be active, looping through all records ***also closes the resultSet
                int appointmentID = myResultSet.getInt("Appointment_ID"); //Local variable appointmentID is assigned the value of getInt() method on myResultSet with the column name as a parameter.
                int customerID = myResultSet.getInt("Customer_ID");
                int userID = myResultSet.getInt("User_ID");
                int contactID = myResultSet.getInt("Contact_ID");
                String title = myResultSet.getString("Title");
                String description = myResultSet.getString("Description");
                String location = myResultSet.getString("Location");
                String type = myResultSet.getString("Type");
                LocalDateTime start = myResultSet.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = myResultSet.getTimestamp("End").toLocalDateTime();
                LocalDateTime createDate = myResultSet.getTimestamp("Create_Date").toLocalDateTime(); //Need toLocalDate() method to convert Date to LocalDate
                String createdBy = myResultSet.getString("Created_By");
                LocalDateTime updateDate = myResultSet.getTimestamp("Last_Update").toLocalDateTime(); //Need toLocalDateTime() method to convert. Using timestamp type
                //LocalTime updateTime = myResultSet.getTime("Last_Update").toLocalTime();
                String updatedBy = myResultSet.getString("Last_Updated_By");
                //String apptDT = myLocalDateTime.format(myFormatter);
                String apptMonthDT = start.format(myMonthFull);
                String apptFullDay = start.format(myFormatterDay);
                String apptFullYear = start.format(myFormatterYear);
                LocalDate date2 = LocalDate.of(Integer.parseInt(apptFullYear), Integer.parseInt(apptMonthDT), Integer.parseInt(apptFullDay));
                int apptWeekOfYear = date2.get(ChronoField.ALIGNED_WEEK_OF_YEAR);
                System.out.println(apptWeekOfYear);
//                String apptFullHour = myLocalDateTime.format(myFormatterHour);
//                String apptFullMin = myLocalDateTime.format(myFormatterMin);
                //If the resultset meets the the requirements of the filter variable then add it to the observableList
                System.out.println("Current week: " + currentWeekOfYear + " Appt Date: " + apptWeekOfYear);

                if(currentWeekOfYear == apptWeekOfYear) {
                    System.out.println("Month True Filter");
                    //Create new instance of Appointments called newAppointment with the local variables that have been assigned the values found in the ResultSet from the SQL database
                    Appointments newAppointments = new Appointments(appointmentID, customerID, userID, contactID, title, description, location, type, start, end, createDate, createdBy, updateDate, updatedBy);
                    //Call addAppointments method with newAppointment instance passed to add to the observableList
                    Appointments.addAppointments(newAppointments);
                    //Appointments.updateAppointments(newAppointments);
                }
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

        /**
         * Method used to navigate to the report interface screen
         */
        @FXML
        private void goToReports (ActionEvent event) throws IOException {

            Parent root = FXMLLoader.load(getClass().
                    getResource(
                            "ReportsController.fxml"), bundle);
            Stage stage = (Stage) toReports.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

