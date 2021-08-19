package sample.ViewController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Model.Appointments;
import sample.Model.Customers;
import sample.Utilities.DBConnection;
import sample.Utilities.Query;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.ResourceBundle;

public class AppointmentsController {
    //Set language resource bundle
    //Locale.setDefault(new Locale("fr"));
    Locale locale = new Locale("en");
    //System.out.println(Locale.getDefault());
    ResourceBundle bundle = ResourceBundle.getBundle("sample.Utilities.ResourceBundles.text", locale);

    //Attributes
    public static int appointmentID;
    public static int contactID;
    public static String titleString;
    public static String descriptionString;
    public static String locationString;
    public static String typeString;
    public static LocalDateTime startString;
    public static LocalDateTime endString;
    public static String contactString;

    @FXML
    private TextField appointment_id;
    @FXML
    private TextField title;
    @FXML
    private TextField description;
    @FXML
    private TextField myLocation;
    @FXML
    private TextField type;
    @FXML
    private TextField contact;
    @FXML
    private TextField start;
    @FXML
    private TextField end;
    @FXML
    private TextField contactIDField;
    @FXML
    private Button backToReality, updateAppt, createNewAppt;
    @FXML
    private ComboBox contactComboBox;

    //Enums
    public enum comboBoxContactName{
        AnikaCosta("Anika Costa"), DanielGarcia("Daniel Garcia"), LiLee("Li Lee");
        //Contact_ID 1, 2, 3;
            public final String contacts;
        comboBoxContactName(String s) {
            this.contacts = s;
        }
    };
    public enum comboBoxContactID{
        AnikaCosta("Anika Costa"), DanielGarcia("Daniel Garcia"), LiLee("Li Lee");
        public final String contacts;
        comboBoxContactID(String s) {
            this.contacts = s;
        }
    };

    @FXML
    public void initialize() {
        if (appointmentID > 0) {
            appointment_id.setText(String.valueOf(appointmentID));
            title.setText(titleString);
            description.setText(descriptionString);
            myLocation.setText(locationString);
            type.setText(typeString);
            start.setText(startString.toString());
            end.setText(endString.toString());
            contactIDField.setText(String.valueOf(contactID));
            contactComboBox.getItems().addAll(comboBoxContactName.values());
            if(contactID==1){
                contactComboBox.setValue("Anika Costa");
            }else if(contactID==2){
                contactComboBox.setValue("Daniel Garcia");
            }else if(contactID==3){
                contactComboBox.setValue("Li Lee");
            }
        }
    }

    @FXML
    public void onActionInsertAppointment(ActionEvent event) throws SQLException, IOException {
        System.out.println("Save Appointment Button Works!");

        String newAppointmentID = appointment_id.getText();
        String newTitleString = title.getText();
        String newDescriptionString = description.getText();
        String newLocationString = myLocation.getText();
        String newTypeString= type.getText();
        String newStartString= start.getText();
        String newEndString= end.getText();
        String newContactString=contact.getText();
        String newUser = UsersController.getMyNewUser();
        String newAppt = AppointmentsList.getMyNewAppointments();
        System.out.println(newAppointmentID + newTitleString + newDescriptionString + newDescriptionString + newLocationString + newTypeString + newStartString + newEndString + newAppt);

        //Establish connection before launch and assign it to the Connection reference variable named conn
        Connection conn = DBConnection.startConnection();

        //Insert Statement
        String insertStatement = "INSERT INTO appointments (Appointment_ID, Title, Description, Location, Type, Created_By, Last_Updated_By) VALUES(?,?,?,?,?,?,?)"; //Question marks are placeholders to be mapped with key values in one-based index

        //Create prepared statement object for insertStatement
        Query.setPreparedStatement(conn, insertStatement);

        //Get the prepared statement reference
        PreparedStatement preparedStatement = Query.getPreparedStatement();

        //Key-value mapping to set the prepared statement
        preparedStatement.setString(1,newAppointmentID);
        preparedStatement.setString(2,newTitleString);
        preparedStatement.setString(3,newDescriptionString);
        preparedStatement.setString(4,newLocationString);
        preparedStatement.setString(5,newTypeString);
        preparedStatement.setString(6,newUser);
        preparedStatement.setString(7,newUser);

        preparedStatement.execute(); //Execute prepared statement

        //Check rows affected
        if (preparedStatement.getUpdateCount() > 0)
            System.out.println("Rows affected: " + preparedStatement.getUpdateCount());
        else System.out.println("No change!");
    }

    public static void getAppointmentData(Appointments appointment){
        appointmentID = appointment.getAppointment_ID();
        titleString = appointment.getTitle();
        descriptionString = appointment.getDescription();
        locationString = appointment.getLocation();
        typeString = appointment.getType();
        startString = appointment.getStart();
        endString = appointment.getEnd();
        contactID = appointment.getContact_ID();

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
