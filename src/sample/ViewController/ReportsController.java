package sample.ViewController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import java.awt.event.*;


import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class ReportsController{
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



    public void reportOneAction(ActionEvent event){
        //AnchorPane root = new AnchorPane();

        reportNumber =1;
        System.out.println(reportNumber);
        //Create Button
        Button myReportOne = new Button("Report One");
        //Listener event

        TableView tableView = new TableView();
        TableColumn<Customers, String> customerColumn = new TableColumn <>("Customer Name");
        customerColumn.setCellValueFactory(new PropertyValueFactory<>("customer_Name"));

        tableView.getColumns().add(customerColumn);
        tableView.getItems().add(new Customers("ReportTest"));

        VBox vbox = new VBox(tableView,myReportOne);
        //vbox.getChildren().add(tableView);
        //vbox.getChildren().add(reportOne);
        //vbox.getChildren().add(reportTwo);
        //vbox.getChildren().add(reportThree);
        //root.getChildren().add(vbox);
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(vbox));
        stage.show();
        //createNewTable();
    };

    public void reportTwoAction(){
        reportNumber =2;
        System.out.println(reportNumber);
    };

    public void reportThreeAction(){
        reportNumber =3;
        System.out.println(reportNumber);
    };

    public void createNewTable() {
//        TableView tableView = new TableView();
//        TableColumn<Customers, String> customerColumn = new TableColumn <>("Customer Name");
//        customerColumn.setCellValueFactory(new PropertyValueFactory<>("customer_Name"));
//
//        tableView.getColumns().add(customerColumn);
//        tableView.getItems().add(new Customers("ReportTest"));
//
//        stage = (Stage) ((javafx.scene.control.Button)event.getSource()).getScene().getWindow();
//        stage.setScene(new Scene(reportVBox));
//        stage.show();


    };

//    @FXML
//    private void backToReportsController(ActionEvent event) throws IOException {
//        Parent root = FXMLLoader.load(getClass().
//                getResource(
//                        "ReportsController.fxml"),bundle);
//        Stage stage = (Stage) myReportOne.getScene().getWindow();
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
//    }

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
