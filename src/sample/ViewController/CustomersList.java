package sample.ViewController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import sample.Model.Customers;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
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


//    private ObservableList<Customers> myCustomers = FXCollections.observableArrayList(
//            new Customers(4,35, "Steve Jobs", "fake street",
//                    "23453", "234323432", "2021-07-22 03:26:56", "2021-07-22 03:26:09",
//                    "2021-07-22 03:26:10", "gary")
//    );

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        System.out.println("initialize method");
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

        //Sets the tableview myCustomerList with the items from myCustomers ObservableList
        myCustomerList.setItems(Customers.getAllCustomers());
        //customerID.setCellValueFactory(cellData -> cellData.getValue().nameProperty());


//        myCustomers.add(new Customers(4,35, "Steve Jobs", "fake street",
//                "23453", "234323432", "2021-07-22 03:26:56", "2021-07-22 03:26:09",
//                "2021-07-22 03:26:10", "gary"));
//
//        myCustomerList.setItems(myCustomers);
    }
    public void backToMainController(ActionEvent event) throws IOException {
        stage = (Stage) ((javafx.scene.control.Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("MainController.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
}

//    //ListView reference
//    @FXML
//    private ListView<String> myListView;
//
//
//    //Create observable List
//    @FXML
//    ObservableList<String> customerList = FXCollections.observableArrayList("Apples","Bananas","carrots");
//
//    myListView.setAll();
