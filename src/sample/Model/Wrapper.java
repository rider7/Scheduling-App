package sample.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;

public class Wrapper extends Appointments{

    private Appointments appointments;
    private Contacts contacts;
    String contact_name ="Test Class";
    private SimpleStringProperty contactName;

    //ObservableList used to display appt data on tableview
    public static ObservableList<Wrapper> myWrapper = FXCollections.observableArrayList(
            new Wrapper("Test",35, 25, 34,
                    23453, "234323432", "location", "steve",
                    null, null, null, null, null, null, null)
    );

    public Wrapper(String Contact_Name, int appointment_ID, int customer_ID, int contact_ID, int user_ID, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, LocalDateTime create_Date, String created_By, LocalDateTime last_Update, String last_Updated_By) {
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
    public static void addWrappers(Wrapper newWrappers) {
        myWrapper.add(newWrappers);
    }

    public static void updateWrappers(Wrapper selectedWrappers) {
        myWrapper.set(0,selectedWrappers);
    }

    public Appointments getAppointments() {
        return appointments;
    }

    public void setAppointments(Appointments appointments) {
        this.appointments = appointments;
    }

    public Contacts getContacts() {
        return contacts;
    }

    public void setContacts(Contacts contacts) {
        this.contacts = contacts;
    }

    public String getContact_Name() {
        return contact_name;
    }

    public void setContact_Name(String contact_name) {
        this.contact_name = contact_name;
    }

    public static ObservableList<Wrapper> getMyWrapper() {
        return myWrapper;
    }

    public static void setMyWrapper(ObservableList<Wrapper> myWrapper) {
        Wrapper.myWrapper = myWrapper;
    }
    public StringProperty contactNameProperty(){
        return contactName;
    }
};

