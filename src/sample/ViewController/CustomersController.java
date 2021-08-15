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
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public class CustomersController {
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

    //String comboBoxCountries [] = {"US","France","Japan","Canada","UT"};

    public enum comboBoxCountries{
        US("U.S"), UK("UK"), Canada("Canada");
    public final String country;
        comboBoxCountries(String s) {
            this.country = s;
        }
    };

    ObservableList<String> countryComboBox;

    @FXML
    public void initialize() {
        if (Integer.valueOf(customerIDString) > 0) {
            customerID.setText(Integer.toString(customerIDString));
            customerName.setText(customersNameString);
            address.setText(addressString);
            phoneNumber.setText(phoneString);
            postalCode.setText(postalCodeString);
            cBoxDivisions.setValue(divisionIDString);
            cBoxCountries.getItems().clear();
            cBoxCountries.getItems().addAll(comboBoxCountries.values());
            cBoxCountries.setValue(countryString);

       } else { //Populate comboboxes with country options then once a country is selected populate specific divisions
            cBoxCountries.getItems().clear();
            cBoxCountries.getItems().addAll(comboBoxCountries.values());
//            cBoxDivisions.getItems().clear();
//            cBoxDivisions.getItems().addAll(comboBoxDivisions.values());
        }

    }

    @FXML
    public void onActionInsertCustomer(ActionEvent event) throws SQLException, IOException {
        System.out.println("Save Customer Button Works!");

        String newCustomerID = customerID.getText();
        String newCustomerName = customerName.getText();
        String newAddress = address.getText();
        String newPhoneNumber = phoneNumber.getText();
        String newPostalCode= postalCode.getText();
        String newUser = UsersController.getMyNewUser();
        System.out.println(newCustomerName + newAddress + newPhoneNumber + newPostalCode + newUser);

        //Establish connection before launch and assign it to the Connection reference variable named conn
        Connection conn = DBConnection.startConnection();

        //Insert Statement
        String insertStatement = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID, Created_By, Last_Updated_By) VALUES(?,?,?,?,?,?,?)"; //Question marks are placeholders to be mapped with key values in one-based index

        //Create prepared statement object for insertStatement
        Query.setPreparedStatement(conn, insertStatement);

        //Get the prepared statement reference
        PreparedStatement preparedStatement = Query.getPreparedStatement();

//        String Customer_Name = newCustomerName;
//        String Address = newAddress;
//        String Postal_Code = newPhoneNumber;
//        String Phone = "me again";


        //Key-value mapping to set the prepared statement
        //preparedStatement.setInt(1,Integer.valueOf(newCustomerID));
        preparedStatement.setString(1,newCustomerName);
        preparedStatement.setString(2,newAddress);
        preparedStatement.setString(3,newPhoneNumber);
        preparedStatement.setString(4,newPostalCode);
        preparedStatement.setInt(5,5);
        preparedStatement.setString(6,newUser);
        preparedStatement.setString(7,newUser);

        preparedStatement.execute(); //Execute prepared statement

        //Check rows affected
        if (preparedStatement.getUpdateCount() > 0)
            System.out.println("Rows affected: " + preparedStatement.getUpdateCount());
        else System.out.println("No change!");
    }

    public static void getCustomerData(Customers customer){
        customerIDString = customer.getCustomer_ID();
        customersNameString = customer.getCustomer_Name();
        addressString = customer.getAddress();
        phoneString = customer.getPhone();
        postalCodeString = customer.getPostal_Code();
        divisionIDString = customer.getDivision_ID();
        //System.out.println(customerIDString);
    };

    public static int getDivisionData(Customers customer){
        divisionIDString = customer.getDivision_ID();
        //System.out.println(divisionIDString);
        return divisionIDString;
    };

    public static void getCountryData(String country){
        countryString = country;
    };

    @FXML
    private void backToMainController(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().
                getResource(
                        "MainController.fxml"),bundle);
        Stage stage = (Stage) backToReality.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
