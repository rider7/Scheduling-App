package sample.Model;

import java.util.Date;
/**Class to handle the countries object*/
public class Countries {
    //Attributes
    int Country_ID; //PK
    String Country;
    Date Create_Date;
    Date Created_By;
    Date Last_Update;
    Date Last_Update_By;

    public int getCountry_ID() {
        return Country_ID;
    }

    public void setCountry_ID(int country_ID) {
        Country_ID = country_ID;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public Date getCreate_Date() {
        return Create_Date;
    }

    public void setCreate_Date(Date create_Date) {
        Create_Date = create_Date;
    }

    public Date getCreated_By() {
        return Created_By;
    }

    public void setCreated_By(Date created_By) {
        Created_By = created_By;
    }

    public Date getLast_Update() {
        return Last_Update;
    }

    public void setLast_Update(Date last_Update) {
        Last_Update = last_Update;
    }

    public Date getLast_Update_By() {
        return Last_Update_By;
    }

    public void setLast_Update_By(Date last_Update_By) {
        Last_Update_By = last_Update_By;
    }
}
