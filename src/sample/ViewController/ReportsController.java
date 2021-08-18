package sample.ViewController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.Main;
import sample.Model.Customers;
import javafx.scene.control.*;
import sample.Utilities.DBConnection;
import sample.Utilities.Query;

import java.awt.event.*;


import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class ReportsController implements Initializable {
    Locale locale = new Locale("en");
    //System.out.println(Locale.getDefault());
    ResourceBundle bundle = ResourceBundle.getBundle("sample.Utilities.ResourceBundles.text", locale);

    //Attributes
    int reportNumber;
    Parent scene;
    Stage stage;

    @FXML
    Button backToReality;
    @FXML
    Button reportOne;
    @FXML
    Button reportTwo;
    @FXML
    Button reportThree;
    @FXML
    VBox reportVBox;
    @FXML
    VBox report1VBox;
    @FXML
    TextField planningSessionsTotal;
    @FXML
    TextField deBriefingTotal;

    //TablewView Fields
    @FXML TableView<Customers> myTableView1;
    @FXML TableView<Customers> myTableView2;
    @FXML TableView<Customers> myTableView3;
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

    @Override
    public void initialize (URL url, ResourceBundle resourceBundle){
        // remove tableviews except for tableview1
        reportVBox.getChildren().remove(myTableView1);
        reportVBox.getChildren().remove(myTableView2);
        reportVBox.getChildren().remove(myTableView3);

//        try {
//           // myConnection();
//        }
//        catch(Exception e){
//            System.out.println(e.getMessage());
//        }

        //Sets the column for customers
//        customerID.setCellValueFactory(new PropertyValueFactory<Customers, Integer>("Customer_ID"));
//        divisionID.setCellValueFactory(new PropertyValueFactory<Customers, Integer>("Division_ID"));
//        customerName.setCellValueFactory(new PropertyValueFactory<Customers, String>("Customer_Name"));
//        address.setCellValueFactory(new PropertyValueFactory<Customers, String>("Address"));
//        postalCode.setCellValueFactory(new PropertyValueFactory<Customers, String>("Postal_Code"));
//        phone.setCellValueFactory(new PropertyValueFactory<Customers, String>("Phone"));
//        createDate.setCellValueFactory(new PropertyValueFactory<Customers, String>("Create_Date"));
//        createdBy.setCellValueFactory(new PropertyValueFactory<Customers, String>("Created_By"));
//        lastUpdate.setCellValueFactory(new PropertyValueFactory<Customers, String>("Last_Update"));
//        lastUpdatedBy.setCellValueFactory(new PropertyValueFactory<Customers, String>("Last_Updated_By"));

        //Sets the items on the myCustomerList table from the Observable list for customers via the getAllCustomers() method call
        //myCustomerList.setItems(getAllCustomers());
    }

    public void reportOneAction(ActionEvent event) throws SQLException {
        String planningString = "Planning Session";
        reportNumber =1;
        System.out.println("Report Number: " + reportNumber);
        //Establish connection before launch and assign it to the Connection reference variable named conn
        Connection conn = DBConnection.startConnection();

        //Select Statement
        String selectTypeStatement = "SELECT * FROM  appointments WHERE Type = ?"; //Question marks are placeholders to be mapped with key values in one-based index

        //Create prepared statement object for insertStatement
        Query.setPreparedStatement(conn, selectTypeStatement);

        //Get the prepared statement reference
        PreparedStatement preparedStatement = Query.getPreparedStatement();
        //Map value to prepared statement
        preparedStatement.setString(1,planningString);
        preparedStatement.execute();
        //Create ResultSet object and assign the preparedStatement results
        ResultSet myResultSet = preparedStatement.getResultSet();
        int count=0;
        while (myResultSet.next()) { //Loop through each row adding to the count
            count++;
            System.out.println(count);
        }
        planningSessionsTotal.setText(String.valueOf(count));

        String deBriefingString = "De-Briefing";
        //Select Statement
        String selectType2Statement = "SELECT * FROM  appointments WHERE Type = ?"; //Question marks are placeholders to be mapped with key values in one-based index

        //Create prepared statement object for insertStatement
        Query.setPreparedStatement(conn, selectType2Statement);

        //Get the prepared statement reference
        PreparedStatement preparedStatement2 = Query.getPreparedStatement();
        //Map value to prepared statement
        preparedStatement2.setString(1,deBriefingString);
        preparedStatement2.execute();
        //Create ResultSet object and assign the preparedStatement results
        ResultSet myResultSet2 = preparedStatement2.getResultSet();
        int count2=0;
        while (myResultSet2.next()) { //Loop through each row adding to the count
            count2++;
            System.out.println(count2);
        }
        deBriefingTotal.setText(String.valueOf(count2));

    };

    public void reportTwoAction(){
        reportNumber =2;
        System.out.println(reportNumber);
        //add tableview
        reportVBox.getChildren().add(1,myTableView2);
        //tableview visibility
        myTableView2.setVisible(true);
        // remove tableviews
        reportVBox.getChildren().remove(myTableView1);
        reportVBox.getChildren().remove(myTableView3);
    };

    public void reportThreeAction(){
        reportNumber =3;
        System.out.println(reportNumber);
        //add tableview
        reportVBox.getChildren().add(1,myTableView3);
        //tableview visibility
        myTableView3.setVisible(true);
        // remove tableviews
        reportVBox.getChildren().remove(myTableView2);
        reportVBox.getChildren().remove(myTableView1);
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
