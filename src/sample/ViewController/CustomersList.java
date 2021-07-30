package sample.ViewController;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CustomersList {
    Stage stage;
    Parent scene;

    public void backToMainController(ActionEvent event) throws IOException {
        stage = (Stage) ((javafx.scene.control.Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("MainController.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
}
