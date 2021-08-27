package sample.Model;

import java.util.Date;
/**Class used to handle Users objects*/
public class Users {
    //Attributes
    int User_ID; //PK
    String User_Name; //Unique
    String Password;
    Date Create_Date;
    Date Create_By;
    Date Last_Update;
    Date Last_Updated_By;

    public int getUser_ID() {
        return User_ID;
    }

    public void setUser_ID(int user_ID) {
        User_ID = user_ID;
    }

    public String getUser_Name() {
        return User_Name;
    }

    public void setUser_Name(String user_Name) {
        User_Name = user_Name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public Date getCreate_Date() {
        return Create_Date;
    }

    public void setCreate_Date(Date create_Date) {
        Create_Date = create_Date;
    }

    public Date getCreate_By() {
        return Create_By;
    }

    public void setCreate_By(Date create_By) {
        Create_By = create_By;
    }

    public Date getLast_Update() {
        return Last_Update;
    }

    public void setLast_Update(Date last_Update) {
        Last_Update = last_Update;
    }

    public Date getLast_Updated_By() {
        return Last_Updated_By;
    }

    public void setLast_Updated_By(Date last_Updated_By) {
        Last_Updated_By = last_Updated_By;
    }
}
