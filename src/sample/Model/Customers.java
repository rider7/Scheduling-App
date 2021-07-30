package sample.Model;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Date;

public class Customers {
    //Attributes
    int Customer_ID; //PK
    int Division_ID; //FK
    String Customer_Name;
    String Address;
    String Postal_Code;
    String Phone;
    String Create_Date;
    String Created_By;
    String Last_Update;
    String Last_Updated_By;

    public static ObservableList<Customers> myCustomers = FXCollections.observableArrayList(
            new Customers(4,35, "Steve Jobs", "fake street",
                    "23453", "234323432", "2021-07-22 03:26:56", "2021-07-22 03:26:09",
                    "2021-07-22 03:26:10", "gary")
    );

    public Customers(int customer_ID, int division_ID, String customer_Name, String address, String postal_Code, String phone, String create_Date, String created_By, String last_Update, String last_Updated_By) {
        Customer_ID = customer_ID;
        Division_ID = division_ID;
        Customer_Name = customer_Name;
        Address = address;
        Postal_Code = postal_Code;
        Phone = phone;
        Create_Date = create_Date;
        Created_By = created_By;
        Last_Update = last_Update;
        Last_Updated_By = last_Updated_By;
    }

    public int getCustomer_ID() {
        return Customer_ID;
    }

    public void setCustomer_ID(int customer_ID) {
        Customer_ID = customer_ID;
    }

    public int getDivision_ID() {
        return Division_ID;
    }

    public void setDivision_ID(int division_ID) {
        Division_ID = division_ID;
    }

    public String getCustomer_Name() {
        return Customer_Name;
    }

    public void setCustomer_Name(String customer_Name) {
        Customer_Name = customer_Name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPostal_Code() {
        return Postal_Code;
    }

    public void setPostal_Code(String postal_Code) {
        Postal_Code = postal_Code;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getCreate_Date() {
        return Create_Date;
    }

    public void setCreate_Date(String create_Date) {
        Create_Date = create_Date;
    }

    public String getCreated_By() {
        return Created_By;
    }

    public void setCreated_By(String created_By) {
        Created_By = created_By;
    }

    public String getLast_Update() {
        return Last_Update;
    }

    public void setLast_Update(String last_Update) {
        Last_Update = last_Update;
    }

    public String getLast_Updated_By() {
        return Last_Updated_By;
    }

    public void setLast_Updated_By(String last_Updated_By) {
        Last_Updated_By = last_Updated_By;
    }

    public static ObservableList<Customers> getAllCustomers(){
        return myCustomers;
    }
}
