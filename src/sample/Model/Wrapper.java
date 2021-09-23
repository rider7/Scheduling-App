package sample.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

/**Class used to extent appointments and allow use of contact name in wrapper object*/
public class Wrapper extends Appointments{
    /**Appointment object used to keep track of appointment*/
    private Appointments appointments;
    /**Contacts object used to keep track of contacts*/
    private Contacts contacts;
    /**String used to hold the Test Class*/
    String contact_name ="Test Class";
    /**SimpleStringProperty contactName needed for the wrapper class*/
    private SimpleStringProperty contactName;

    //ObservableList used to display appt data on tableview
    /**The ObservableList used for the wrapper class*/
    public static ObservableList<Wrapper> myWrapper = FXCollections.observableArrayList(
            new Wrapper("Test",35, 25, 34,
                    23453, "234323432", "location", "steve",
                    null, null, null, null, null, null, null)
    );
    /**The wrapper constructor for the wrapper class*/
    public Wrapper(String Contact_Name, int appointment_ID, int customer_ID, int contact_ID, int user_ID, String title, String description, String location, String type, ZonedDateTime start, ZonedDateTime end, ZonedDateTime create_Date, String created_By, ZonedDateTime last_Update, String last_Updated_By) {
        super(appointment_ID, customer_ID, contact_ID, user_ID, title, description, location, type, start, end, create_Date, created_By, last_Update, last_Updated_By);
        contact_name = Contact_Name;
        Appointment_ID = appointment_ID;
        Customer_ID = customer_ID;
        Contact_ID = contact_ID;
        User_ID = user_ID;
        Title = title;
        Description = description;
        Location = location;
        Type = type;
        Start = start;
        End = end;
        Create_Date = create_Date;
        Created_By = created_By;
        Last_Update = last_Update;
        Last_Updated_By = last_Updated_By;
    }
    /**Method used to add wrapper objects to the observableList*/
    public static void addWrappers(Wrapper newWrappers) {
        myWrapper.add(newWrappers);
    }
    /**Method used to update wrapper objects to the observableList*/
    public static void updateWrappers(Wrapper selectedWrappers) {
        myWrapper.set(0,selectedWrappers);
    }
    /**Method used to get appointments*/
    public Appointments getAppointments() {
        return appointments;
    }
    /**Method used to set appointments*/
    public void setAppointments(Appointments appointments) {
        this.appointments = appointments;
    }
    /**Method used to get contacts*/
    public Contacts getContacts() {
        return contacts;
    }
    /**Method used to set contacts*/
    public void setContacts(Contacts contacts) {
        this.contacts = contacts;
    }
    /**Method used to get contact name*/
    public String getContact_Name() {
        return contact_name;
    }
    /**Method used to set contact name*/
    public void setContact_Name(String contact_name) {
        this.contact_name = contact_name;
    }
    /**Get the observableList for the wrapper*/
    public static ObservableList<Wrapper> getMyWrapper() {
        return myWrapper;
    }
    /**Set the observableList for the wrapper object*/
    public static void setMyWrapper(ObservableList<Wrapper> myWrapper) {
        Wrapper.myWrapper = myWrapper;
    }
    /**StringProperty for contact name property to return*/
    public StringProperty contactNameProperty(){
        return contactName;
    }
};

