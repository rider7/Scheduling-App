package sample.ViewController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import sample.Model.Customers;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Utilities.DBConnection;
import sample.Utilities.Query;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class CustomersList implements Initializable {
    Stage stage;
    Parent scene;

    //TableView field
    @FXML
    public TableView<Customers> myCustomerList;
    //Table Columns
    @FXML
    private TableColumn<Customers, Integer> customerID;
    @FXML
    private TableColumn<Customers, Integer> divisionID;
    @FXML
    private TableColumn<Customers, String> customerName;
    @FXML
    private TableColumn<Customers, String> address;
    @FXML
    private TableColumn<Customers, String> postalCode;
    @FXML
    private TableColumn<Customers, String> phone;
    @FXML
    private TableColumn<Customers, String> createDate;
    @FXML
    private TableColumn<Customers, String> createdBy;
    @FXML
    private TableColumn<Customers, String> lastUpdate;
    @FXML
    private TableColumn<Customers, String> lastUpdatedBy;

    public CustomersList() throws SQLException {
        //customerListSet();
    }

    @Override
    public void initialize (URL url, ResourceBundle resourceBundle){

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

    public static ResultSet myConnection() throws SQLException {
        //Establish connection before launch and assign it to the Connection reference variable named conn
        Connection conn = DBConnection.startConnection();
        //Pass conn object to statement
        Query.setStatement(conn); //Create statement object
        Statement statement = Query.getStatement(); //Get Statement reference

        //Select all records from countries table
        String selectStatement = "SELECT * FROM customers"; //SQL statement
        statement.execute(selectStatement); //Execute statement (returns true)
        ResultSet myResultSet = statement.getResultSet(); //Get the result sets and assigns to reference variable myResultSet
        System.out.println("Got customer table!");
        System.out.println(statement.execute(selectStatement));
        return myResultSet;
    }


    //Forward scroll ResultSet
//        while(myResultSet.next()){ //next() method returns true so while it equals true the loop will be active, looping through all records
//            int countryID = myResultSet.getInt("Country_ID"); //Local variable countryID is assigned the value of getInt() method on myResultSet with the column name as a parameter.
//            String countryName = myResultSet.getString("Country");
//            LocalDate createDate = myResultSet.getDate("Create_Date").toLocalDate(); //Need toLocalDate() method to convert Date to LocalDate
//            LocalTime createTime = myResultSet.getTime("Create_Date").toLocalTime(); //Need toLocalTime to convert to Local Time
//            String createdBy = myResultSet.getString("Created_By");
//            LocalDateTime updateDate = myResultSet.getTimestamp("Last_Update").toLocalDateTime(); //Need toLocalDateTime() method to convert. Using timestamp type
//            //LocalTime updateTime = myResultSet.getTime("Last_Update").toLocalTime();
//            String updatedBy = myResultSet.getString("Last_Updated_By");
//
//            System.out.println("Country ID: " + countryID);

    public static ObservableList<Customers> myCustomers = FXCollections.observableArrayList(
            new Customers(4,35, "Steve Jobs", "fake street",
                    "23453", "234323432", "2021-07-22 03:26:56", "2021-07-22 03:26:09",
                    "2021-07-22 03:26:10", "gary")
    );
    public static ObservableList<Customers> getAllCustomers(){

        return myCustomers;
    }

    public void backToMainController(ActionEvent event) throws IOException {
        stage = (Stage) ((javafx.scene.control.Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("MainController.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
}


