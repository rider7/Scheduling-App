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
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.ResourceBundle;

public class AppointmentsList implements Initializable {
    /**
     * Class used to manipulate data for appointment records
     */
    //Set language resource bundle
    //Locale.setDefault(new Locale("fr"));
    Locale locale = new Locale("en");
    //System.out.println(Locale.getDefault());
    ResourceBundle bundle = ResourceBundle.getBundle("sample.Utilities.ResourceBundles.text", locale);
    //Message box instance
    JFrame frame;


    //FXML attributes
    //TableView field
    @FXML
    public TableView<Appointments> myAppointmentsList;
    //Table Columns
    @FXML
    private TableColumn<Appointments, Integer> appointmentID;
    @FXML
    private TableColumn<Appointments, String> title;
    @FXML
    private TableColumn<Appointments, String> description;
    @FXML
    private TableColumn<Appointments, String> myLocation;
    @FXML
    private TableColumn<Appointments, String> type;
    @FXML
    private TableColumn<Appointments, String> start;
    @FXML
    private TableColumn<Appointments, String> end;
    @FXML
    private TableColumn<Appointments, Integer> customerID;
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
    @FXML
    private Button backToReality;
    @FXML
    private Button goToAppointmentButton;
    @FXML
    private Button onActionDeleteAppointmentButton;
    @FXML Button toReports;
    @FXML
    private  RadioButton myAppointmentSort1;
    @FXML
    private  RadioButton myAppointmentSort2;
    @FXML
    public ToggleGroup myToggleGroup;
    public String toggleFilter;

    public static String myNewAppt;


    @FXML
    public void onActionDeleteAppointment(ActionEvent event) throws SQLException{
        /**
         * Method used to connect to the database and delete appointments by appointment_id
         */
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
        preparedStatement.setInt(1,Appointment_ID);

        preparedStatement.execute(); //Execute prepared statement
        //Delete the appointment from tableview as well
        Appointments.deleteAppointment(deleteSelectedAppointment);
        JOptionPane.showMessageDialog(frame,"You have deleted the appointment with the following details " +"\n" + "Appointment ID: " + Appointment_ID + "\n" + "Appointment Type: " + type);
        //System.out.println("Appointment Deleted!" + " Appointment ID: " + Appointment_ID + "Appointment Type: " + type);
    }

    @FXML
    private void goToAppointmentsController(ActionEvent event) throws IOException {
        /**
         * Method used to go to appointments controller
         */
        Parent root = FXMLLoader.load(getClass().
                getResource(
                        "AppointmentsController.fxml"),bundle);
        Stage stage = (Stage) goToAppointmentButton.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void backToMainController(ActionEvent event) throws IOException {
        /**
         * Method used to back to the main controller
         */
        Parent root = FXMLLoader.load(getClass().
                getResource(
                        "MainController.fxml"),bundle);
        Stage stage = (Stage) backToReality.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize (URL url, ResourceBundle resourceBundle){
        /**
         * Method used to initialize the scene with appointment lists
         */

        try {
            myConnection();
        }
        catch(Exception e){
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

    public static ObservableList<Appointments> getAllAppointments(){
        /**
         * Method used to return ObservableList of appointments
         */

        return Appointments.myAppointments;
    }

    public static void myConnection() throws SQLException {
        /**
         * Method used to connect to the database and to select appointments
         */
        //Establish connection before launch and assign it to the Connection reference variable named conn
        Connection conn = DBConnection.startConnection();
        //Pass conn object to statement
        Query.setStatement(conn); //Create statement object
        Statement statement =  Query.getTestStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE); //Get Statement reference

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
                //Create new instance of Appointments called newAppointment with the local variables that have been assigned the values found in the ResultSet from the SQL database
                Appointments newAppointments = new Appointments(appointmentID,customerID,userID,contactID,title,description,location,type,start,end,createDate,createdBy,updateDate,updatedBy);
                //Call addAppointments method with newAppointment instance passed to add to the observableList
                Appointments.addAppointments(newAppointments);
                Appointments.updateAppointments(newAppointments);
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }


    public static String getMyNewAppointments(){
        /**
         * Method used to get new appointments
         */
        System.out.println("In getMyNewApptMethod: " + myNewAppt);
        return myNewAppt;
    }

    public void getToggleFilter(){
        toggleFilter = "month";
        //System.out.println(myToggleGroup.getSelectedToggle());
        System.out.println(toggleFilter);
    }
    public void getToggleFilter2(){
        //System.out.println(myToggleGroup.getSelectedToggle());
        toggleFilter = "week";
        System.out.println(toggleFilter);
    }

    @FXML
    private void goToAppointmentUpdate(ActionEvent event) throws IOException {
        /**
         * Method used to go to the appointment list page with updated appointment
         */
        //Create instance of Appointments that is selected from tableview myAppointmentList
        Appointments updateSelectedAppointment = myAppointmentsList.getSelectionModel().getSelectedItem();
        //Call method to pass updatedSelectedAppointment object to AppointmentsController for use in populating data fields
        AppointmentsController.getAppointmentData(updateSelectedAppointment);

        Parent root = FXMLLoader.load(getClass().
                getResource(
                        "AppointmentsController.fxml"),bundle);
        Stage stage = (Stage) goToAppointmentButton.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void goToReports(ActionEvent event) throws IOException {
        /**
         * Method used to navigate to the report interface screen
         */
        Parent root = FXMLLoader.load(getClass().
                getResource(
                        "ReportsController.fxml"),bundle);
        Stage stage = (Stage) toReports.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
