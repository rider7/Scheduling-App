package sample.Model;

import java.util.Date;
/**Class to handle the contacts object*/
public class Contacts {
    //Attributes
    int Contact_ID; //PK
    String Contact_name;
    String Email;
/**Constructor for the contact*/
    public Contacts(int contact_ID, String contact_name, String email) {
        Contact_ID = contact_ID;
        Contact_name = contact_name;
        Email = email;
    }

    public int getContact_ID() {
        return Contact_ID;
    }

    public void setContact_ID(int contact_ID) {
        Contact_ID = contact_ID;
    }

    public String getContact_name() {
        return Contact_name;
    }

    public void setContact_name(String contact_name) {
        Contact_name = contact_name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
