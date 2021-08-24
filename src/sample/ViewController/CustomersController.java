package sample.ViewController;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import sample.Model.Countries;
import sample.Model.Customers;
import sample.Model.Divisions;
import sample.Model.Users;
import sample.Utilities.DBConnection;
import sample.Utilities.Query;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javafx.application.Application;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Utilities.DBConnection;
import sample.Utilities.Query;

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public class CustomersController {
    /**
     * Class used to manipulate data for Customer records
     */
    Locale locale = new Locale("en");
    //System.out.println(Locale.getDefault());
    ResourceBundle bundle = ResourceBundle.getBundle("sample.Utilities.ResourceBundles.text", locale);

    //FXML Buttons and Labels
    @FXML
    TextField customerID;
    @FXML
    TextField customerName;
    @FXML
    TextField address;
    @FXML
    TextField phoneNumber;
    @FXML
    TextField postalCode;
    @FXML
    Button saveCustomerButton;
    @FXML
    Button backToReality;
    @FXML
    Button updateCustomerButton;
    @FXML
    ComboBox cBoxCountries;
    @FXML
    ComboBox cBoxDivisions;

//Attributes
    public static int customerIDString;
    public static String customersNameString;
    public static String addressString;
    public static String phoneString;
    public static String postalCodeString;
    public static int divisionIDString;
    public static String countryString;

    //Enums
    public enum comboBoxCountries{
        US("U.S"), UK("UK"), Canada("Canada");
    public final String country;
        comboBoxCountries(String s) {
            this.country = s;
        }
    };

    public enum comboBoxDivision1{
        Alabama("Alabama"),Arizona("Arizona"),Arkansas("Arkansas"),California("California"),Colorado("Colorado"),Connecticut("Connecticut"),Delaware("Delaware"),
        DistrictofColumbia("District of Columbia"),Florida("Florida"),Georgia("Georgia"),Idaho("Idaho"),Illinois("Illinois"),Indiana("Indiana"),Iowa("Iowa"),Kansas("Kansas"),
        Kentucky("Kentucky"),Louisiana("Louisiana"),Maine("Maine"),Maryland("Maryland"),Massachusetts("Massachusetts"),Michigan("Michigan"),Minnesota("Minnesota"),
        Mississippi("Mississippi"),Missouri("Missouri"),Montana("Montana"),Nebraska("Nebraska"),Nevada("Nevada"),NewHampshire("New Hampshire"),NewJersey("New Jersey"),
        NewMexico("New Mexico"),NewYork("New York"),NorthCarolina("North Carolina"),NorthDakota("North Dakota"),Ohio("Ohio"),Oklahoma("Oklahoma"),Oregon("Oregon"),
        Pennsylvania("Pennsylvania"),RhodeIsland("Rhode Island"),SouthCarolina("South Carolina"),SouthDakota("South Dakota"),Tennessee("Tennessee"),Texas("Texas"),
        Utah("Utah"),Vermont("Vermont"),Virginia("Virginia"),Washington("Washington"),WestVirginia("West Virginia"),Wisconsin("Wisconsin"),Wyoming("Wyoming"),Hawaii("Hawaii"),Alaska("Alaska");

        public final String division;
        comboBoxDivision1(String s) {
            this.division = s;
        }
    };

    public enum comboBoxDivision2{
        England("England"),Wales("Wales"),Scotland("Scotland"),NorthernIreland("Northern Ireland");

        public final String division;
        comboBoxDivision2(String s) {
            this.division = s;
        }
    };

    public enum comboBoxDivision3{
        NorthwestTerritories("Northwest Territories"),Alberta("Alberta"),BritishColumbia("British Columbia"),Manitoba("Manitoba"),NewBrunswick("New Brunswick"),NovaScotia("Nova Scotia"),PrinceEdwardIsland("Prince Edward Island"),
        Ontario("Ontario"),Québec("Québec"),Saskatchewan("Saskatchewan"),Nunavut("Nunavut"),Yukon("Yukon"),NewfoundlandandLabrador("Newfoundland and Labrador");

        public final String division;
        comboBoxDivision3(String s) {
            this.division = s;
        }
    };

    @FXML
    public void initialize() {
        /**
         * Method used to initialize the scene and set the combobox data
         */
        if (Integer.valueOf(customerIDString) > 0) {
            customerID.setText(Integer.toString(customerIDString));
            customerName.setText(customersNameString);
            address.setText(addressString);
            phoneNumber.setText(phoneString);
            postalCode.setText(postalCodeString);
            cBoxCountries.getItems().clear();
            cBoxCountries.getItems().addAll(comboBoxCountries.values());
            cBoxCountries.setValue(countryString);
            cBoxDivisions.getItems().clear();
            if(cBoxCountries.getValue().toString().equals("US")||cBoxCountries.getValue().toString().equals("U.S")) {
                //System.out.println("In the if statement");
                cBoxDivisions.getItems().clear();
                cBoxDivisions.getItems().addAll(comboBoxDivision1.values());
            }else if(cBoxCountries.getValue().toString().equals("UK")) {
                //System.out.println("In the if statement");
                cBoxDivisions.getItems().clear();
                cBoxDivisions.getItems().addAll(comboBoxDivision2.values());
            } else if(cBoxCountries.getValue().toString().equals("Canada")) {
                //System.out.println("In the if statement");
                cBoxDivisions.getItems().clear();
                cBoxDivisions.getItems().addAll(comboBoxDivision3.values());
            }
            //cBoxDivisions.getItems().addAll(comboBoxDivision1.values());
            cBoxDivisions.setValue(divisionIDString);

       } else { //Populate comboboxes with country options then once a country is selected populate specific divisions
            cBoxCountries.getItems().clear();
            cBoxCountries.getItems().addAll(comboBoxCountries.values());
            cBoxDivisions.getItems().clear();
            cBoxDivisions.getItems().addAll(comboBoxDivision1.values());
        }
        ArrayList<String> numbers = new ArrayList<String>();
        numbers.add(customersNameString);
        numbers.add(addressString);
        numbers.add(phoneString);
        numbers.add(postalCodeString);
        numbers.add(countryString);
        numbers.forEach((n) -> {System.out.println(n);});


    }

    public void comboBoxCountryAction(){
        /**
         * Method used to set the comboBox data
         */
        //System.out.println("out of the if statement");
        //System.out.println(cBoxCountries.getValue());
        //System.out.println(cBoxCountries.getValue().toString().equals("US"));
        if(cBoxCountries.getValue().toString().equals("US")) {
            //System.out.println("In the if statement");
            cBoxDivisions.getItems().clear();
            cBoxDivisions.getItems().addAll(comboBoxDivision1.values());
        }else if(cBoxCountries.getValue().toString().equals("UK")) {
            //System.out.println("In the if statement");
            cBoxDivisions.getItems().clear();
            cBoxDivisions.getItems().addAll(comboBoxDivision2.values());
        } else if(cBoxCountries.getValue().toString().equals("Canada")) {
            //System.out.println("In the if statement");
            cBoxDivisions.getItems().clear();
            cBoxDivisions.getItems().addAll(comboBoxDivision3.values());
        }
    };



    @FXML
    public void onActionInsertCustomer(ActionEvent event) throws SQLException, IOException {
        /**
         * Method used to connect to the database and insert a new customer record
         */
        //System.out.println("Save Customer Button Works!");

        String newCustomerID = customerID.getText();
        String newCustomerName = customerName.getText();
        String newAddress = address.getText();
        String newPhoneNumber = phoneNumber.getText();
        String newPostalCode= postalCode.getText();
        String newDivision= cBoxDivisions.getValue().toString();
        getDivision(newDivision);
        int myNewDivision = divisionIDString;
        String newUser = UsersController.getMyNewUser();
        System.out.println(newCustomerName + newAddress + newPhoneNumber + newPostalCode + newUser + newDivision);

        //Establish connection before launch and assign it to the Connection reference variable named conn
        Connection conn = DBConnection.startConnection();

        //Insert Statement
        String insertStatement = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID, Created_By, Last_Updated_By) VALUES(?,?,?,?,?,?,?)"; //Question marks are placeholders to be mapped with key values in one-based index

        //Create prepared statement object for insertStatement
        Query.setPreparedStatement(conn, insertStatement);

        //Get the prepared statement reference
        PreparedStatement preparedStatement = Query.getPreparedStatement();

        //Key-value mapping to set the prepared statement
        //preparedStatement.setInt(1,Integer.valueOf(newCustomerID));
        preparedStatement.setString(1,newCustomerName);
        preparedStatement.setString(2,newAddress);
        preparedStatement.setString(3,newPhoneNumber);
        preparedStatement.setString(4,newPostalCode);
        preparedStatement.setInt(5,myNewDivision);
        preparedStatement.setString(6,newUser);
        preparedStatement.setString(7,newUser);

        preparedStatement.execute(); //Execute prepared statement

        //Check rows affected
        if (preparedStatement.getUpdateCount() > 0)
            System.out.println("Rows affected: " + preparedStatement.getUpdateCount());
        else System.out.println("No change!");
    }

    @FXML
    public void onActionUpdateCustomer(ActionEvent event) throws SQLException, IOException {
        /**
         * Method used to connect to the database and update the customer record
         */
        //System.out.println("Save Customer Button Works!");

        String newCustomerID = customerID.getText();
        String newCustomerName = customerName.getText();
        String newAddress = address.getText();
        String newPhoneNumber = phoneNumber.getText();
        String newPostalCode= postalCode.getText();
        String newDivision= cBoxDivisions.getValue().toString();
        getDivision(newDivision);
        int myNewDivision = divisionIDString;
        String newUser = UsersController.getMyNewUser();
        System.out.println(newCustomerName + newAddress + newPhoneNumber + newPostalCode + newUser + newDivision);

        //Establish connection before launch and assign it to the Connection reference variable named conn
        Connection conn = DBConnection.startConnection();

        //Update Statement
        String updateStatement = "UPDATE customers SET Customer_Name=?, Address=?, Phone=?, Postal_Code=?, Division_ID=?, Created_By=?, Last_Updated_By=? WHERE Customer_ID=?"; //Question marks are placeholders to be mapped with key values in one-based index

        //Create prepared statement object for insertStatement
        Query.setPreparedStatement(conn, updateStatement);

        //Get the prepared statement reference
        PreparedStatement preparedStatement = Query.getPreparedStatement();

        //Key-value mapping to set the prepared statement
        //preparedStatement.setInt(1,Integer.valueOf(newCustomerID));
        preparedStatement.setString(1,newCustomerName);
        preparedStatement.setString(2,newAddress);
        preparedStatement.setString(3,newPhoneNumber);
        preparedStatement.setString(4,newPostalCode);
        preparedStatement.setInt(5,myNewDivision);
        preparedStatement.setString(6,newUser);
        preparedStatement.setString(7,newUser);
        preparedStatement.setString(8,newCustomerID);

        preparedStatement.execute(); //Execute prepared statement

        //Check rows affected
        if (preparedStatement.getUpdateCount() > 0)
            System.out.println("Rows affected: " + preparedStatement.getUpdateCount());
        else System.out.println("No change!");

        Parent root = FXMLLoader.load(getClass().
                getResource(
                        "CustomersList.fxml"),bundle);
        Stage stage = (Stage) updateCustomerButton.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void getDivision(String division_ID) throws SQLException {
        /**
         * Method used to connect to the database and query with select where the division is the parameter
         */
        //Establish connection before launch and assign it to the Connection reference variable named conn
        Connection conn = DBConnection.startConnection();
        //Select all records from customers table
        String selectDivisionStatement = "SELECT Division_ID FROM first_level_divisions WHERE Division = ?"; //SQL statement
        //Create prepared statement object for selecting appointments
        Query.setPreparedStatement(conn, selectDivisionStatement);
        //Prepared statement reference
        PreparedStatement preparedDivisionStatement = Query.getPreparedStatement();
        //Key-value mapping to set the prepared statement based on customer id
        preparedDivisionStatement.setString(1, division_ID);

        try {
            //System.out.println("Try statement");
            preparedDivisionStatement.execute(); //Execute statement (returns true)
            ResultSet myResultSet = preparedDivisionStatement.getResultSet(); //Get the result sets and assigns to reference variable myResultSet
            myResultSet.next();
            divisionIDString = myResultSet.getInt("Division_ID");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void getCustomerData(Customers customer){
        /**
         * Method accepts a customer object and assigns the individual items to local string variables to be used in other methods
         */
        customerIDString = customer.getCustomer_ID();
        customersNameString = customer.getCustomer_Name();
        addressString = customer.getAddress();
        phoneString = customer.getPhone();
        postalCodeString = customer.getPostal_Code();
        divisionIDString = customer.getDivision_ID();
        //System.out.println(customerIDString);
    };

    public static int getDivisionData(Customers customer){
        /**
         * Method used assign the divisionID value and return it as a string
         */
        divisionIDString = customer.getDivision_ID();
        //System.out.println(divisionIDString);
        return divisionIDString;
    };

    public static void getCountryData(String country){
        /**
         * Method used get the country data and assign the country value
         */
        countryString = country;
    };

    @FXML
    private void backToMainController(ActionEvent event) throws IOException {
        /**
         * Method used to navigate back to the main controller
         */
        Parent root = FXMLLoader.load(getClass().
                getResource(
                        "MainController.fxml"),bundle);
        Stage stage = (Stage) backToReality.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
