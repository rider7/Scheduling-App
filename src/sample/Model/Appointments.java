package sample.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;
import java.util.Date;
/**Class to handle the appointments object*/
public class Appointments {
    //Attributes
    /**Integer to hold the appointment ID*/
    int Appointment_ID; //PK
    /**Integer to hold the customer ID*/
    int Customer_ID; //FK
    /**Integer to hold the contact ID*/
    int Contact_ID; //FK
    /**Integer to hold the user ID*/
    int User_ID; //FK
    /**String used to hold the title*/
    String Title;
    /**String used to hold the description*/
    String Description;
    /**String used to hold the location*/
    String Location;
    /**String used to hold the type*/
    String Type;
    /**LocalDateTime used to hold the start data*/
    LocalDateTime Start;
    /**LocalDateTime used to hold the end data*/
    LocalDateTime End;
    /**LocalDateTime used to hold the create date data*/
    LocalDateTime Create_Date;
    /**LocalDateTime used to hold the start data*/
    String Created_By;
    /**LocalDateTime used to hold the last update data*/
    LocalDateTime Last_Update;
    /**String used to hold the last updated by data*/
    String Last_Updated_By;

    //ObservableList used to display appt data on tableview
    /**Observable List to hold the appointments collection of the array list*/
    public static ObservableList<Appointments> myAppointments = FXCollections.observableArrayList();
    /**Constructor for the appointments object*/
    public Appointments(int appointment_ID, int customer_ID, int contact_ID, int user_ID, String title, String description,
                        String location, String type, LocalDateTime start, LocalDateTime end, LocalDateTime create_Date, String created_By, LocalDateTime last_Update, String last_Updated_By) {
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

    public static void addAppointments(Appointments newAppointments) {
        System.out.println("Add Method:" + newAppointments.Appointment_ID);
        myAppointments.add(newAppointments);
    }

    public static void updateAppointments(Appointments selectedAppointments) {
        System.out.println("Update Method: " + selectedAppointments.Appointment_ID);
        myAppointments.set(0,selectedAppointments);
    }


    public static boolean deleteAppointment(Appointments selectedAppointment){
        myAppointments.remove(selectedAppointment);
        return true;
    };


    public int getAppointment_ID() {
        return Appointment_ID;
    }

    public void setAppointment_ID(int appointment_ID) {
        Appointment_ID = appointment_ID;
    }

    public int getCustomer_ID() {
        return Customer_ID;
    }

    public void setCustomer_ID(int customer_ID) {
        Customer_ID = customer_ID;
    }

    public int getContact_ID() {
        return Contact_ID;
    }

    public void setContact_ID(int contact_ID) {
        Contact_ID = contact_ID;
    }

    public int getUser_ID() {
        return User_ID;
    }

    public void setUser_ID(int user_ID) {
        User_ID = user_ID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public LocalDateTime getStart() {
        return Start;
    }

    public void setStart(LocalDateTime start) {
        Start = start;
    }

    public LocalDateTime getEnd() {
        return End;
    }

    public void setEnd(LocalDateTime end) {
        End = end;
    }

    public LocalDateTime getCreate_Date() {
        return Create_Date;
    }

    public void setCreate_Date(LocalDateTime create_Date) {
        Create_Date = create_Date;
    }

    public String getCreated_By() {
        return Created_By;
    }

    public void setCreated_By(String created_By) {
        Created_By = created_By;
    }

    public LocalDateTime getLast_Update() {
        return Last_Update;
    }

    public void setLast_Update(LocalDateTime last_Update) {
        Last_Update = last_Update;
    }

    public String getLast_Updated_By() {
        return Last_Updated_By;
    }

    public void setLast_Updated_By(String last_Updated_By) {
        Last_Updated_By = last_Updated_By;
    }

//    public static void updateAppointment(Appointments selectedAppointments){
//        myAppointments.set(0,selectedAppointments); //BUG: index of 0 used each time customer updated. possible issue.
//    }

}
