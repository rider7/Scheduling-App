package sample.Model;

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
    Date Start;
    Date End;
    Date Create_Date;
    Date Created_By;
    Date Last_Update;
    Date Last_Updated_By;
}
