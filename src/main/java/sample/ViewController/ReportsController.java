package sample.ViewController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.Main;
import sample.Model.*;
import javafx.scene.control.*;
import sample.Model.Wrapper;
import sample.Model.Reports;
import sample.Utilities.DBConnection;
import sample.Utilities.Query;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Locale;
import java.util.ResourceBundle;
/**
 * This class is used to populate all the report information
 */
public class ReportsController implements Initializable {

    Locale locale = new Locale("en");
    //System.out.println(Locale.getDefault());
    ResourceBundle bundle = ResourceBundle.getBundle("sample.Utilities.ResourceBundles.text", locale);

    //Attributes
    int reportNumber;
    Parent scene;
    Stage stage;
    /** Button used to navigate back to the page*/
    @FXML
    Button backToReality;
    /** Button used to navigate to the report one page*/
    @FXML
    Button reportOne;
    /** Button used to navigate to the report two page*/
    @FXML
    Button reportTwo;
    /** Button used to navigate to the report three page*/
    @FXML
    Button reportThree;
    /**VBOX for the report container*/
    @FXML
    VBox reportVBox;
    /**VBOX for the report 1 container*/
    @FXML
    VBox report1VBox;
    /**VBOX for the report 2 container*/
    @FXML
    VBox report3VBox;
    /**VBOX for the report 3 container*/
    @FXML
    HBox report1HBox, report2HBox;
    /**TextField for the planning sessions total*/
    @FXML
    TextField planningSessionsTotal;
    /**TextField for the debriefing total*/
    @FXML
    TextField deBriefingTotal;

    //Report 3 textfields
    /**Textfield to keep track of the us customers*/
    @FXML
    TextField usCustomers;
    /**Textfield to keep track of the uk customers*/
    @FXML
    TextField ukCustomers;
    /**Textfield to keep track of the canada customers*/
    @FXML
    TextField canadaCustomers;
    //Month textfields
    /**Textfield to keep track of the months*/
    @FXML
    TextField janTextField, febTextField, marchTextField, aprilTextField, mayTextField, juneTextField, julyTextField,
    augustTextField, septTextField, octTextField, novTextField, decTextField;

    //TablewView Fields
    /**Table View to display the wrapper objects*/
    @FXML TableView<Wrapper> myWrapper;
    //@FXML TableView<Customers> myTableView3;
    //Table Columns
    /**Table Column to display the wrapper contact*/
    @FXML
    public TableColumn<Wrapper, String> contact;
    /**Table Column to display the wrapper appointment ID*/
    @FXML
    public TableColumn<Wrapper, Integer> apptID;
    /**Table Column to display the wrapper title*/
    @FXML
    public TableColumn<Wrapper, String> title;
    /**Table Column to display the wrapper description*/
    @FXML
    public TableColumn<Wrapper, String> description;
    /**Table Column to display the wrapper type*/
    @FXML
    public TableColumn<Wrapper, String> type;
    /**Table Column to display the wrapper start*/
    @FXML
    public TableColumn<Wrapper, String> start;
    /**Table Column to display the wrapper end*/
    @FXML
    public TableColumn<Wrapper, String> end;
    /**Table Column to display the wrapper customer ID*/
    @FXML
    public TableColumn<Wrapper, Integer> customerID;


    //Table Columns
    //@FXML
   // private TableColumn<Customers, Integer> customerID;
    /**Table Column to hold the division ID data*/
    @FXML
    private TableColumn<Customers, Integer> divisionID;
    /**Table Column to hold the customer name data*/
    @FXML
    private TableColumn<Customers, String> customerName;
    /**Table Column to hold the address data*/
    @FXML
    private TableColumn<Customers, String> address;
    /**Table Column to hold the postal code data*/
    @FXML
    private TableColumn<Customers, String> postalCode;
    /**Table Column to hold the phone data*/
    @FXML
    private TableColumn<Customers, String> phone;
    /**Table Column to hold the create date data*/
    @FXML
    private TableColumn<Customers, String> createDate;
    /**Table Column to hold the created by data*/
    @FXML
    private TableColumn<Customers, String> createdBy;
    /**Table Column to hold the last update data*/
    @FXML
    private TableColumn<Customers, String> lastUpdate;
    /**Table Column to hold the last updated by data*/
    @FXML
    private TableColumn<Customers, String> lastUpdatedBy;
    Frame frame;

    //TablewView Fields
    /**Table View to display the Reports objects*/
    @FXML
    public TableView<Reports> reportTableView;
    /**Table Column to display the wrapper contact*/
    @FXML
    public TableColumn<Reports, String> monthTableColumn;
    /**Table Column to display the wrapper appointment ID*/
    @FXML
    public TableColumn<Reports, String> typeTableColumn;
    /**Table Column to display the wrapper title*/
    @FXML
    public TableColumn<Reports, String> countTableColumn;

    /**
     * Method used to intialize the scene when the controller is used
     */
    @Override
    public void initialize (URL url, ResourceBundle resourceBundle){

        // remove tableviews except for tableview1
        usCustomers.setText(String.valueOf(1));
        ukCustomers.setText(String.valueOf(1));
        canadaCustomers.setText(String.valueOf(1));
        reportVBox.getChildren().remove(report1HBox);
        reportVBox.getChildren().remove(report2HBox);
        reportVBox.getChildren().remove(report3VBox);

        //set report number to 1 when initializing so system does not need to get the report 1 vbox if it is already set in the scene
        reportNumber =1;

        //Sets the column for Appointments
        contact.setCellValueFactory(new PropertyValueFactory<Wrapper, String>("Contact_Name"));
        apptID.setCellValueFactory(new PropertyValueFactory<Wrapper, Integer>("Appointment_ID"));
        title.setCellValueFactory(new PropertyValueFactory<Wrapper, String>("Title"));
        description.setCellValueFactory(new PropertyValueFactory<Wrapper, String>("Description"));
        type.setCellValueFactory(new PropertyValueFactory<Wrapper, String>("Type"));
        start.setCellValueFactory(new PropertyValueFactory<Wrapper, String>("Start"));
        end.setCellValueFactory(new PropertyValueFactory<Wrapper, String>("End"));
        customerID.setCellValueFactory(new PropertyValueFactory<Wrapper, Integer>("Customer_ID"));
        myWrapper.setItems(getAllWrappers());
        System.out.println("Get All Wrappers: " + getAllWrappers());
        JOptionPane.showMessageDialog(frame,"Choose a report by clicking a button below.");
    }
    /**
     * Method used to wrap the observableList and create a super()
     */
    public static ObservableList<Wrapper>getAllWrappers(){
        return Wrapper.myWrapper;
    }

    public static ObservableList<Reports>getAllReports(){
        return Reports.myReports;
    }

    /**
     * Method used to query the database and evaluate the information for the first report
     */
    @FXML
    public void reportOne(ActionEvent event) throws SQLException{
        //add report 1 VBox if it is not set already in the scene

            reportVBox.getChildren().remove(report2HBox);
            reportVBox.getChildren().remove(report3VBox);
            reportVBox.getChildren().add(report1HBox);

        // remove tableviews
        //myWrapper.setVisible(false);
        //report1HBox.setVisible(true);
        String planningString = "Planning Session";
        reportNumber =1;
        //Establish connection before launch and assign it to the Connection reference variable named conn
        Connection conn = DBConnection.startConnection();
        //Pass conn object to statement
        Query.setStatement(conn); //Create statement object
        Statement statement = Query.getTestStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE); //Get Statement reference

        //Select all records from countries table
        String selectStatement = "SELECT DISTINCT Type, EXTRACT(MONTH From Start) FROM  appointments ORDER BY EXTRACT(MONTH From Start)"; //SQL statement

        try {
            statement.execute(selectStatement); //Execute statement (returns true)
            ResultSet myResultSet = statement.getResultSet(); //Get the result sets and assigns to reference variable myResultSet
            System.out.println("Got appointments table!");

            //Forward scroll ResultSet
            while (myResultSet.next()) { //next() method returns true so while it equals true the loop will be active, looping through all records ***also closes the resultSet
                //String location = myResultSet.getString("Location");
                String myType = myResultSet.getString("Type");
                System.out.println(myType);
                //LocalDateTime start = myResultSet.getTimestamp("EXTRACT(MONTH From Start)").toLocalDateTime();
                Long start = myResultSet.getLong("EXTRACT(MONTH From Start)");
                String startMonth = Month.of(start.intValue()).name();;

                //Select all records from countries table
                String selectStatement2 = "SELECT DISTINCT count(*) FROM appointments WHERE Type = ? AND (EXTRACT(MONTH From Start)= ?)"; //SQL statement
                Query.setPreparedStatement(conn,selectStatement2); //Create statement object
                PreparedStatement statement2 = Query.getPreparedStatement(); //Get Statement reference
                //Key-value mapping to set the prepared statement based on customer id
                statement2.setString(1, myType);
                statement2.setLong(2, start);
                statement2.execute(); //Execute statement (returns true)
                ResultSet myResultSet2 = statement2.getResultSet(); //Get the result sets and assigns to reference variable myResultSet
                myResultSet2.next();
                Long myCount = myResultSet2.getLong("count(*)");
                //Create new instance of Appointments called newAppointment with the local variables that have been assigned the values found in the ResultSet from the SQL database
                Reports newReports = new Reports(startMonth, myType, myCount);
                System.out.println("New Reports: " + newReports.getMonth());
                //Call addAppointments method with newAppointment instance passed to add to the observableList
                Reports.addReports(newReports);
                //Reports.updateReports(newReports);
                //System.out.println("My Reports: " + Reports.myReports.get(0).getMonth());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        //Set columns for reportOne
        monthTableColumn.setCellValueFactory(new PropertyValueFactory<>("Month"));
        typeTableColumn.setCellValueFactory(new PropertyValueFactory<>("Type"));
        countTableColumn.setCellValueFactory(new PropertyValueFactory<>("Count"));
        reportTableView.setItems(getAllReports());
        System.out.println("Get All Reports: " + getAllReports());
    }
    /**
     * Method used to query the database and evaluate the information for the first report
     */
    public void reportOneAction(ActionEvent event) throws SQLException {
        //add report 1 VBox if it is not set already in the scene
        if(reportNumber!=1) {
            reportVBox.getChildren().add(1, report1VBox);
            reportVBox.getChildren().remove(report2HBox);
            reportVBox.getChildren().remove(report3VBox);
        }
        // remove tableviews
        reportVBox.getChildren().remove(report2HBox);
        myWrapper.setVisible(false);
        report1VBox.setVisible(true);
        String planningString = "Planning Session";
        reportNumber =1;
        //System.out.println("Report Number: " + reportNumber);
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
            //System.out.println(count2);
        }
        deBriefingTotal.setText(String.valueOf(count2));

        //call method monthInteger which finds and totals the month for each appointment
        monthInteger(conn);


    };
    /**
     * Method used to query the database and evaluate the information for the first report
     */
    public void monthInteger(Connection conn) throws SQLException {

        int janCount =0;
        int febCount =0;
        int marCount =0;
        int aprilCount =0;
        int mayCount =0;
        int juneCount =0;
        int julyCount =0;
        int augustCount =0;
        int septCount =0;
        int octCount =0;
        int novCount =0;
        int decCount =0;
        String selectTypeStatement = "SELECT * FROM  appointments"; //Question marks are placeholders to be mapped with key values in one-based index

        //Create prepared statement object for insertStatement
        Query.setPreparedStatement(conn, selectTypeStatement);

        //Get the prepared statement reference
        PreparedStatement preparedStatement = Query.getPreparedStatement();
        //Map value to prepared statement
        preparedStatement.execute();
        //Create ResultSet object and assign the preparedStatement results
        ResultSet myResultSet = preparedStatement.getResultSet();

        while (myResultSet.next()) { //Loop through each row adding to the count
            Timestamp ts = myResultSet.getTimestamp("Start");
            Date myDate = new Date(ts.getTime());
            Calendar cal = Calendar.getInstance();
            cal.setTime(myDate);
            int month = cal.get(Calendar.MONTH);
            //System.out.println("month number " + month);
            switch (month){
                case 0:
                    janCount++;
                    break;
                case 1:
                    febCount++;
                    break;
                case 2:
                    marCount++;
                    break;
                case 3:
                    aprilCount++;
                    break;
                case 4:
                    mayCount++;
                    break;
                case 5:
                    juneCount++;
                    break;
                case 6:
                    julyCount++;
                    break;
                case 7:
                    augustCount++;
                    break;
                case 8:
                    septCount++;
                    break;
                case 9:
                    octCount++;
                    break;
                case 10:
                    novCount++;
                    break;
                case 11:
                    decCount++;
                    break;
            }
            //System.out.println(mayCount);
        }
        janTextField.setText(String.valueOf(janCount));
        febTextField.setText(String.valueOf(febCount));
        marchTextField.setText(String.valueOf(marCount));
        aprilTextField.setText(String.valueOf(aprilCount));
        mayTextField.setText(String.valueOf(mayCount));
        juneTextField.setText(String.valueOf(juneCount));
        julyTextField.setText(String.valueOf(julyCount));
        augustTextField.setText(String.valueOf(augustCount));
        septTextField.setText(String.valueOf(septCount));
        octTextField.setText(String.valueOf(octCount));
        novTextField.setText(String.valueOf(novCount));
        decTextField.setText(String.valueOf(decCount));
    };

    /**
     *Method used for the second report
     * @throws SQLException
     */
    public void reportTwoAction() throws SQLException {


            reportVBox.getChildren().remove(report1HBox);
            reportVBox.getChildren().remove(report3VBox);
            reportVBox.getChildren().add(report2HBox);

        reportNumber=2;
        System.out.println(reportNumber);
        //add tableview
        //reportVBox.getChildren().add(1,myWrapper);
        //tableview visibility
        //myWrapper.setVisible(true);
        //report1HBox.setVisible(false);
        //report3VBox.setVisible(false);

        //Establish connection before launch and assign it to the Connection reference variable named conn
        Connection conn = DBConnection.startConnection();

        //Select Appt Statement
        String selectApptStatement = "SELECT * FROM  appointments";
        //Select Contact Statement
        String selectContactStatement = "SELECT * FROM  contacts WHERE Contact_ID = ?";
        //Select join statement
        String selectJoinStatement = "SELECT appointments.Appointment_ID, appointments.Title, appointments.Description, appointments.Type, appointments.Customer_ID, appointments.User_ID, appointments.Contact_ID, appointments.Start, appointments.End, contacts.Contact_Name FROM appointments INNER JOIN contacts ON appointments.Contact_ID=contacts.Contact_ID";

        //Create prepared statement object for insertStatement
        Query.setPreparedStatement(conn, selectJoinStatement);

        //Get the prepared statement reference
        PreparedStatement preparedStatement = Query.getPreparedStatement();
        try {
            preparedStatement.execute(selectJoinStatement);
            //Create ResultSet object and assign the preparedStatement results
            ResultSet myResultSet = preparedStatement.getResultSet();
            System.out.println("Appt table for Report 2");
            //Forward scroll ResultSet
            while (myResultSet.next()) { //next() method returns true so while it equals true the loop will be active, looping through all records ***also closes the resultSet
                //Code to format UTC time to local timezone for user interface
                Timestamp tsStart = myResultSet.getTimestamp("Create_Date");
                Timestamp tsStart2 = myResultSet.getTimestamp("Last_Update");
                Timestamp tsStart3 = myResultSet.getTimestamp("Start");
                Timestamp tsStart4 = myResultSet.getTimestamp("End");
                ZoneId newzid = ZoneId.systemDefault();

                ZonedDateTime newzdtStart = tsStart.toLocalDateTime().atZone(ZoneId.of("UTC"));
                ZonedDateTime newzdtStart2 = tsStart2.toLocalDateTime().atZone(ZoneId.of("UTC"));
                ZonedDateTime newzdtStart3 = tsStart3.toLocalDateTime().atZone(ZoneId.of("UTC"));
                ZonedDateTime newzdtStart4 = tsStart4.toLocalDateTime().atZone(ZoneId.of("UTC"));
                ZonedDateTime newLocalStart = newzdtStart.withZoneSameInstant(newzid);
                String contact_name = myResultSet.getString("Contact_Name");
                int appt_ID = myResultSet.getInt("Appointment_ID");
                //System.out.println(appt_ID);
                String title = myResultSet.getString("Title");
                String description = myResultSet.getString("Description");
                String type = myResultSet.getString("Type");
                int customer_id = myResultSet.getInt("Customer_ID");
                int user_id = myResultSet.getInt("User_ID");
                int contact_id = myResultSet.getInt("Contact_ID");
                //LocalDateTime start = myResultSet.getTimestamp("Start").toLocalDateTime(); //Need toLocalDate() method to convert Date to LocalDate
                ZonedDateTime start = newzdtStart3.withZoneSameInstant(newzid);
                //LocalDateTime end = myResultSet.getTimestamp("End").toLocalDateTime(); //Need toLocalDate() method to convert Date to LocalDate
                ZonedDateTime end = newzdtStart4.withZoneSameInstant(newzid);
                //Create new instance of Appointments called newCustomer with the local variables that have been assigned the values found in the ResultSet from the SQL database
                Wrapper newWrapper = new Wrapper(contact_name,appt_ID, customer_id, user_id, contact_id, title, description, null, type, start, end, null,null,null, null);
                //Call addCustomer method with newCustomer instance passed to add to the observableList
                //Appointments.addAppointments(newAppointment);
                Wrapper.addWrappers(newWrapper);
                //Appointments.updateAppointments(newAppointment);
                Wrapper.updateWrappers(newWrapper);
                System.out.println(appt_ID);
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    };
    /**
     * Method used to query the database and evaluate the information for the third report
     */
    public void reportThreeAction() throws SQLException {

            reportVBox.getChildren().remove(report2HBox);
            reportVBox.getChildren().remove(report1HBox);
            reportVBox.getChildren().add(report3VBox);


        int usCount = 0;
        int ukCount = 0;
        int canadaCount = 0;
        reportNumber=3;
        System.out.println(reportNumber);

        //tableview visibility
        //report3VBox.setVisible(true);
        //report1HBox.setVisible(false);
        //myWrapper.setVisible(false);

        //Establish connection before launch and assign it to the Connection reference variable named conn
        Connection conn = DBConnection.startConnection();

        //Select join statement
        String selectJoinStatement = "SELECT customers.Division_ID, customers.Customer_ID, first_level_divisions.COUNTRY_ID FROM customers INNER JOIN first_level_divisions ON customers.Division_ID=first_level_divisions.Division_ID";

        //Create prepared statement object for insertStatement
        Query.setPreparedStatement(conn, selectJoinStatement);

        //Get the prepared statement reference
        PreparedStatement preparedStatement = Query.getPreparedStatement();
        try {
            preparedStatement.execute(selectJoinStatement);
            //Create ResultSet object and assign the preparedStatement results
            ResultSet myResultSet = preparedStatement.getResultSet();
            System.out.println("Customer table for Report 3");
            //Forward scroll ResultSet
            while (myResultSet.next()) { //next() method returns true so while it equals true the loop will be active, looping through all records ***also closes the resultSet
                int division_ID = myResultSet.getInt("Division_ID");
                int country_ID = myResultSet.getInt("COUNTRY_ID");
                int customer_id = myResultSet.getInt("Customer_ID");
                if(country_ID==1){
                    usCount++;
                } else if(country_ID ==2){
                    ukCount++;
                }else if(country_ID ==3){
                    canadaCount++;
                }
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        usCustomers.setText(String.valueOf(usCount));
        ukCustomers.setText(String.valueOf(ukCount));
        canadaCustomers.setText(String.valueOf(canadaCount));
    }
    /**
     * Method used navigate back to the main controller
     */
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