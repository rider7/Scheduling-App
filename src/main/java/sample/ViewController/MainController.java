package sample.ViewController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import sample.Utilities.TextFile;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Class for the main controller which is used mainly for testing purposes
 */
public class MainController{
    //Set language
    //Locale.setDefault(new Locale("fr"));
    Locale locale = Locale.getDefault();

    //System.out.println(Locale.getDefault());
    ResourceBundle bundle = ResourceBundle.getBundle("sample.Utilities.ResourceBundles.text", locale);
    Stage stage;
    Parent scene;
    /**Button used to kick off the edit appointment method*/
    @FXML
    public Button editApptScene;

    /**
     * Method used to go to the user login controller page
     */
    @FXML
    public void goToUserLogin(ActionEvent event) throws IOException {
        stage = (Stage) ((javafx.scene.control.Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("UsersController.fxml"), bundle);
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /**
     * Method used to go to the customer list controller page
     */
    @FXML
    public void goToCustomerList(ActionEvent event) throws IOException {
        stage = (Stage) ((javafx.scene.control.Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("CustomersList.fxml"), bundle);
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /**
     * Method used to go to the appt list controller page
     */
    @FXML
    public void goToApptList(ActionEvent event) throws IOException {
        stage = (Stage) ((javafx.scene.control.Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("AppointmentsList.fxml"), bundle);
        stage.setScene(new Scene(scene));
        stage.show();
    }
}
