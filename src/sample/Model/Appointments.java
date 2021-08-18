package sample.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;
import java.util.Date;

public class Appointments {
    //Attributes
    int Appointment_ID; //PK
    int Customer_ID; //FK
    int Contact_ID; //FK
    int User_ID; //FK
    String Title;
    String Description;
    String Location;
    String Type;
    LocalDateTime Start;
    LocalDateTime End;
    LocalDateTime Create_Date;
    String Created_By;
    LocalDateTime Last_Update;
    String Last_Updated_By;

    //ObservableList used to display appt data on tableview
    public static ObservableList<Appointments> myAppointments = FXCollections.observableArrayList(
            new Appointments(3,35, 25, 34,
                    "23453", "234323432", "location", "steve",
                    null, null, null, "admin", null, "admin")
    );

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
        myAppointments.add(newAppointments);
    }

    public static void updateAppointments(Appointments selectedAppointments) {
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

    public static void updateAppointment(Appointments selectedAppointments){
        myAppointments.set(0,selectedAppointments); //BUG: index of 0 used each time customer updated. possible issue.
    }


}
