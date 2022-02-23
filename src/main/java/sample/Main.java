package sample;

import javafx.application.Application;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.Utilities.DBConnection;
import sample.Utilities.Query;
import sample.Utilities.TextFile;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;
/**
 * This is the main class
 */
public class Main extends Application{
    Locale locale;
    Scene reportScene;
    Stage primaryStage;
    //Starting or primary fxml view when the application first runs
    /**
     * Starts the application at the UsersControllers.txt stage and sets the language
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        //Set language
        locale = Locale.getDefault();

        //Locale.setDefault(new Locale("fr"));
        //Locale locale = new Locale("fr");
        //System.out.println(Locale.getDefault());
        ResourceBundle bundle = ResourceBundle.getBundle("sample.Utilities.ResourceBundles.text", locale);
        Parent root = FXMLLoader.load(getClass().getResource("ViewController/UsersController.fxml"), bundle);
        primaryStage.setTitle("Scheduling-Application");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }
    /**
     * The entry point into the application which includes the login_activity.txt file
     */
    //Main entry into the application
    public static void main(String[] args) throws SQLException, IOException {

        //Filename and user variable
        String filename = "login_activity.txt";

        //Create and Open file
        FileWriter outputFile = new FileWriter(filename, true);

        //Lambda expression test
        TextFile myFile = (int x)->System.out.println(2*x);
        myFile.myMessage(3);

        launch(args);
        }
    }
