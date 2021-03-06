package sample.ViewController;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.Model.Appointments;
import sample.Model.Customers;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Utilities.DBConnection;
import sample.Utilities.Query;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.ResourceBundle;
/**
 * Class used for the customer list functionality
 */
public class CustomersList implements Initializable {

    Stage stage;
    Parent root;

    //Set language resource bundle
    //Locale.setDefault(new Locale("fr"));
    Locale locale = Locale.getDefault();

    //System.out.println(Locale.getDefault());
    ResourceBundle bundle = ResourceBundle.getBundle("sample.Utilities.ResourceBundles.text", locale);

    //TableView field
    /**TableView used to display the Customers List*/
    @FXML
    public TableView<Customers> myCustomerList;
    //Table Columns
    /**Table Column used to display the customer ID*/
    @FXML
    private TableColumn<Customers, Integer> customerID;
    /**Table Column used to display the division ID*/
    @FXML
    private TableColumn<Customers, Integer> divisionID;
    /**Table Column used to display the customer name*/
    @FXML
    private TableColumn<Customers, String> customerName;
    /**Table Column used to display the address*/
    @FXML
    private TableColumn<Customers, String> address;
    /**Table Column used to display the postal code*/
    @FXML
    private TableColumn<Customers, String> postalCode;
    /**Table Column used to display the phone data*/
    @FXML
    private TableColumn<Customers, String> phone;
    /**Table Column used to display the create date data*/
    @FXML
    private TableColumn<Customers, String> createDate;
    /**Table Column used to display the created by data*/
    @FXML
    private TableColumn<Customers, String> createdBy;
    /**Table Column used to display the last updated by data*/
    @FXML
    private TableColumn<Customers, String> lastUpdate;
    /**Table Column used to display the last udpate data*/
    @FXML
    private TableColumn<Customers, String> lastUpdatedBy;

    //FXML Buttons
    /**Button to navigate back to the main page*/
    @FXML
    private Button backToReality;
    /**Button used to navigate back to the customer page*/
    @FXML
    private Button goToCustomerButton;
    /**Button used to delete the selected customers record*/
    @FXML
    private Button deleteCustomersButton;
    /**Button used to navigate to the reports page*/
    @FXML
    private Button toReports;
    /**Button used to navigate to the appointment list*/
    @FXML
    private Button toApptList;

    /**
     * Method used to initialize the scene
     */
    @Override
    public void initialize (URL url, ResourceBundle resourceBundle){
//        System.out.println(myCustomerList.getItems());
//        myCustomerList.getItems().clear();
        Customers.myCustomers.removeAll(Customers.myCustomers);
        try {
            myConnection(); //Bug getting last customer record twice
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

        //Sets the column for customers
        customerID.setCellValueFactory(new PropertyValueFactory<Customers, Integer>("Customer_ID"));
        divisionID.setCellValueFactory(new PropertyValueFactory<Customers, Integer>("Division_ID"));
        customerName.setCellValueFactory(new PropertyValueFactory<Customers, String>("Customer_Name"));
        address.setCellValueFactory(new PropertyValueFactory<Customers, String>("Address"));
        postalCode.setCellValueFactory(new PropertyValueFactory<Customers, String>("Postal_Code"));
        phone.setCellValueFactory(new PropertyValueFactory<Customers, String>("Phone"));
        createDate.setCellValueFactory(new PropertyValueFactory<Customers, String>("Create_Date"));
        createdBy.setCellValueFactory(new PropertyValueFactory<Customers, String>("Created_By"));
        lastUpdate.setCellValueFactory(new PropertyValueFactory<Customers, String>("Last_Update"));
        lastUpdatedBy.setCellValueFactory(new PropertyValueFactory<Customers, String>("Last_Updated_By"));

        //Sets the items on the myCustomerList table from the Observable list for customers via the getAllCustomers() method call
        myCustomerList.setItems(getAllCustomers());
    }
    /**
     * Method used to connect to the database and query with a select statement
     */
    public static void myConnection() throws SQLException {
        //Establish connection before launch and assign it to the Connection reference variable named conn
        Connection conn = DBConnection.startConnection();
        //Pass conn object to statement
        Query.setStatement(conn); //Create statement object
        Statement statement =  Query.getTestStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE); //Get Statement reference

        //Select all records from customers table
        String selectStatement = "SELECT * FROM customers"; //SQL statement

        try {
            statement.execute(selectStatement); //Execute statement (returns true)
            ResultSet myResultSet = statement.getResultSet(); //Get the result sets and assigns to reference variable myResultSet
            System.out.println("Got customer table!");

            //Forward scroll ResultSet
            while (myResultSet.next()) { //next() method returns true so while it equals true the loop will be active, looping through all records ***also closes the resultSet
                //Code to format UTC time to local timezone for user interface
                Timestamp tsStart = myResultSet.getTimestamp("Create_Date");
                Timestamp tsStart2 = myResultSet.getTimestamp("Last_Update");
                ZoneId newzid = ZoneId.systemDefault();

                ZonedDateTime newzdtStart = tsStart.toLocalDateTime().atZone(ZoneId.of("UTC"));
                ZonedDateTime newzdtStart2 = tsStart2.toLocalDateTime().atZone(ZoneId.of("UTC"));
                ZonedDateTime newLocalStart = newzdtStart.withZoneSameInstant(newzid);

                System.out.println("From db in UTC: " + newzdtStart);
                System.out.println("From db in local time: " + newLocalStart);

                int customerID = myResultSet.getInt("Customer_ID"); //Local variable countryID is assigned the value of getInt() method on myResultSet with the column name as a parameter.
                int divisionID = myResultSet.getInt("Division_ID");
                String customerName = myResultSet.getString("Customer_Name");
                String address = myResultSet.getString("Address");
                String postalCode = myResultSet.getString("Postal_Code");
                String phone = myResultSet.getString("Phone");
                //LocalDateTime createDate = myResultSet.getTimestamp("Create_Date").toLocalDateTime(); //Need toLocalDate() method to convert Date to LocalDate
                ZonedDateTime createDate = newzdtStart.withZoneSameInstant(newzid);
                String createdBy = myResultSet.getString("Created_By");
                //LocalDateTime updateDate = myResultSet.getTimestamp("Last_Update").toLocalDateTime(); //Need toLocalDateTime() method to convert. Using timestamp type
                ZonedDateTime updateDate = newzdtStart2.withZoneSameInstant(newzid);
                //LocalTime updateTime = myResultSet.getTime("Last_Update").toLocalTime();
                String updatedBy = myResultSet.getString("Last_Updated_By");
                //Create new instance of Customers called newCustomer with the local variables that have been assigned the values found in the ResultSet from the SQL database
                Customers newCustomer = new Customers(customerID,divisionID,customerName,address,postalCode,phone,createDate,createdBy,updateDate,updatedBy);
                //Call addCustomer method with newCustomer instance passed to add to the observableList
                Customers.addCustomer(newCustomer);
                //Customers.updateCustomer(newCustomer);
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
     }
    /**
     * Method used to return the observableList
     */
    public static ObservableList<Customers> getAllCustomers(){
        return Customers.myCustomers;
    }

    /**
     * Method used to go to the customer Update controller page
     */
    @FXML
    private void goToCustomerUpdate(ActionEvent event) throws IOException, SQLException {
        //Create instance of Customers that is selected from tableview myCustomerList
        Customers updateSelectedCustomer = myCustomerList.getSelectionModel().getSelectedItem();
        //Call method to pass updatedSelectedCustomer object to CustomersController for use in populating data fields
        CustomersController.getCustomerData(updateSelectedCustomer);
        //Call method to pass country id
        getCountry();
        //CustomersController.getCountryData(updateSelectedCountries);

        Parent root = FXMLLoader.load(getClass().
                getResource(
                        "CustomersController.fxml"),bundle);
        Stage stage = (Stage) goToCustomerButton.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        //CustomersController.changeCustomerLabel("Update");
    }
    /**Method used to navigate to the customer controller*/
    @FXML
    public void goToCustomerController(ActionEvent event) throws IOException {
        //CustomersController.changeCustomerLabel("Create New Customer");
        CustomersController.customerIDString =0;
        /**
         * Method used to go to the customer controller page
         */
        Parent root = FXMLLoader.load(getClass().
                getResource(
                        "CustomersController.fxml"),bundle);
        Stage stage = (Stage) goToCustomerButton.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        //CustomersController.changeCustomerLabel("New");
    }
    /**Method used to navigate back to the main controller*/
    @FXML
    private void backToMainController(ActionEvent event) throws IOException {
        /**
         * Method used to go back to the main controller page
         */
        Parent root = FXMLLoader.load(getClass().
                getResource(
                        "MainController.fxml"),bundle);
        Stage stage = (Stage) backToReality.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    /**
     * Method used to go to the appointment list controller page
     */
    @FXML
    private void toAppointmentsList(ActionEvent event) throws IOException {


        Parent root = FXMLLoader.load(getClass().
                getResource(
                        "AppointmentsList.fxml"),bundle);
        Stage stage = (Stage) toApptList.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    /**Method used to delete customer records on selection*/
    @FXML
    public void onActionDeleteCustomer(ActionEvent event) throws SQLException{
        /**
         * Method used to connect to the database and query with select where the customer_id is the parameter
         */
    //Establish connection before launch and assign it to the Connection reference variable named conn
    Connection conn = DBConnection.startConnection();

    //SELECT statements
        String selectApptStatement = "SELECT * FROM appointments WHERE customer_id=?"; //SQL statement
    //Delete statements
    String deleteStatement = "DELETE FROM customers WHERE customer_id = ?";
    String deleteApptStatement = "DELETE FROM appointments WHERE customer_id = ?";

    //Create instance of Customers that is selected from tableview myCustomerList
        Customers deleteSelectedCustomer = myCustomerList.getSelectionModel().getSelectedItem();
        //assign the customer_id of the deleteSelectedCustomer instance to the local variable Customer_Id to be used in the sql delete statement above
        int Customer_ID = deleteSelectedCustomer.getCustomer_ID();
    //Create prepared statement object for selecting appointments
    Query.setPreparedStatement(conn, selectApptStatement);
    //Prepared statement reference
    PreparedStatement preparedApptStatement = Query.getPreparedStatement();
        //Key-value mapping to set the prepared statement based on customer id
        preparedApptStatement.setInt(1,Customer_ID);
        //Execute prepared statement
        preparedApptStatement.execute();
        ResultSet apptResultSet =preparedApptStatement.getResultSet();
        System.out.println(apptResultSet);
        while(apptResultSet.next()){ //Loop through the select appointment by id results and delete each one
            int customerID = apptResultSet.getInt("Customer_ID");
            if(Customer_ID ==customerID){
                //Create prepared statement object for deleteStatement
                Query.setPreparedStatement(conn, deleteApptStatement);
                //Prepared statement reference
                PreparedStatement preparedDeleteStatement = Query.getPreparedStatement();
                //Key-value mapping to set the prepared statement
                preparedDeleteStatement.setInt(1,customerID);
                preparedDeleteStatement.execute(); //Execute prepared statement
               System.out.println("In Appt delete while loop " + customerID + "********" + Customer_ID);
            }else{
                return;
            }
        }

   //Create prepared statement object for deleteStatement
    Query.setPreparedStatement(conn, deleteStatement);
   //Prepared statement reference
    PreparedStatement preparedStatement = Query.getPreparedStatement();

    //Key-value mapping to set the prepared statement
        preparedStatement.setInt(1,Customer_ID);

    preparedStatement.execute(); //Execute prepared statement
        //Delete the customer from tableview as well
        Customers.deleteCustomer(deleteSelectedCustomer);
        System.out.println("Customer Deleted!");
    }

    /**Method used to get the country data*/
    public void getCountry() throws SQLException {
        /**
         * Method used to connect to the database and query with select where the division_id is the parameter
         */
        //Establish connection before launch and assign it to the Connection reference variable named conn
        Connection conn = DBConnection.startConnection();
        //Select all records from customers table
        String selectCountryStatement = "SELECT COUNTRY_ID FROM first_level_divisions WHERE Division_ID = ?"; //SQL statement
        //Create prepared statement object for selecting appointments
        Query.setPreparedStatement(conn, selectCountryStatement);
        //Prepared statement reference
        PreparedStatement preparedDivisionStatement = Query.getPreparedStatement();
        //Create instance of Customers that is selected from tableview myCustomerList
        Customers updateSelectedCustomer = myCustomerList.getSelectionModel().getSelectedItem();
        //Call method to pass updatedSelectedCustomer object to CustomersController for use in populating data fields
        int myDivisionID = CustomersController.getDivisionData(updateSelectedCustomer);
        System.out.println("Division ID = " + myDivisionID);
        //Key-value mapping to set the prepared statement based on customer id
        preparedDivisionStatement.setInt(1, myDivisionID);

        try {
            //System.out.println("Try statement");
            preparedDivisionStatement.execute(); //Execute statement (returns true)
            ResultSet myResultSet = preparedDivisionStatement.getResultSet(); //Get the result sets and assigns to reference variable myResultSet
            myResultSet.next();
            int countryID = myResultSet.getInt("COUNTRY_ID");
            //System.out.println("My Country ID: " + countryID);
            if(countryID == 1  ){
                CustomersController.getCountryData("U.S");
            } else if (countryID == 2) {
                CustomersController.getCountryData("UK");
                //System.out.println("UK");
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    /**Method used to navigate to the report page*/
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


