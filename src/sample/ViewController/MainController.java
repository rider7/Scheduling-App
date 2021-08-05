package sample.ViewController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;


public class MainController {
    //Set language
    //Locale.setDefault(new Locale("fr"));
    Locale locale = new Locale("en");
    //System.out.println(Locale.getDefault());
    ResourceBundle bundle = ResourceBundle.getBundle("sample.Utilities.ResourceBundles.text", locale);
    Stage stage;
    Parent scene;
    @FXML
    public Button editApptScene;


    @FXML
    public void goToUserLogin(ActionEvent event) throws IOException {

        stage = (Stage) ((javafx.scene.control.Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("UsersController.fxml"), bundle);
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    public void goToCustomerList(ActionEvent event) throws IOException {

        stage = (Stage) ((javafx.scene.control.Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("CustomersList.fxml"), bundle);
        stage.setScene(new Scene(scene));
        stage.show();
    }
    @FXML
    public void goToApptList(ActionEvent event) throws IOException {
        stage = (Stage) ((javafx.scene.control.Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("AppointmentsList.fxml"), bundle);
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void goToEditCust(ActionEvent event) {
    }

    public void goToEditAppt(ActionEvent event) {
    }

    public void goToReports(ActionEvent event) {
    }
}
