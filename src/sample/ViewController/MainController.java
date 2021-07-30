package sample.ViewController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;


public class MainController {

    Stage stage;
    Parent scene;

    @FXML
    public void goToUserLogin(ActionEvent event) throws IOException {

        stage = (Stage) ((javafx.scene.control.Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("UsersController.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void goToCustomerList(ActionEvent event) throws IOException {

        stage = (Stage) ((javafx.scene.control.Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("CustomersList.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void goToApptList(ActionEvent event) {
    }

    public void goToEditCust(ActionEvent event) {
    }

    public void goToEditAppt(ActionEvent event) {
    }

    public void goToReports(ActionEvent event) {
    }
}
