package sample;

import javafx.application.Application;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Utilities.DBConnection;
import sample.Utilities.Query;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Main extends Application {

    //Starting or primary fxml view when the application first runs
    @Override
    public void start(Stage primaryStage) throws Exception {
        //Set language
        //Locale.setDefault(new Locale("fr"));
        Locale locale = new Locale("en");
        //System.out.println(Locale.getDefault());
        ResourceBundle bundle = ResourceBundle.getBundle("sample.Utilities.ResourceBundles.text", locale);
        Parent root = FXMLLoader.load(getClass().getResource("ViewController/MainController.fxml"), bundle);
        primaryStage.setTitle("It Works");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    //Main entry into the application
    public static void main(String[] args) throws SQLException, IOException {
            launch(args);

            //Filename and user variable
            String filename = "login_activity.txt";

            //Create and Open file
            FileWriter outputFile = new FileWriter(filename, true);
        }
    }
