package sample.Model;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.ViewController.CustomersList;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
/**Class used to handle the Customer objects*/
public class Customers {
    //Attributes
    /**Integer to hold the Customer ID*/
    int Customer_ID; //PK
    /**Integer to hold the Division ID*/
    int Division_ID; //FK
    /**String to hold the Customer Name*/
    String Customer_Name;
    /**String to hold the Address*/
    String Address;
    /**String to hold the Postal Code*/
    String Postal_Code;
    /**String to hold the Phone*/
    String Phone;
    /**LocalDateTime to hold the Create Date*/
    ZonedDateTime Create_Date;
    /**String to hold the Created By data*/
    String Created_By;
    /**LocalDateTime to hold the Last Update*/
    ZonedDateTime Last_Update;
    /**String to hold the last updated by data*/
    String Last_Updated_By;

//    //Moved this ObservableList to the CustomersList controller
//    public static ObservableList<Customers> myCustomers = FXCollections.observableArrayList(
//            new Customers(3,35, "Steve Jobs", "fake street",
//                    "23453", "234323432", null, "steve",
//                    null, "gary")
//    );

    //Moved this ObservableList to the CustomersList controller
    /**Observable List to hold the array of the customer objects*/
    public static ObservableList<Customers> myCustomers = FXCollections.observableArrayList();

    /**Method used to set customer name*/
    public Customers(String customer_Name) {
        this.Customer_Name = customer_Name;
    }
    /**Method used for the Customer class constructor*/
    public Customers(int customer_ID, int division_ID, String customer_Name, String address, String postal_Code, String phone, ZonedDateTime create_Date, String created_By, ZonedDateTime last_Update, String last_Updated_By) {
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
    /**Method used to get the Customer ID*/
    public int getCustomer_ID() {
        return Customer_ID;
    }
    /**Method used to set the Customer ID*/
    public void setCustomer_ID(int customer_ID) {
        Customer_ID = customer_ID;
    }
    /**Method used to get the Division ID*/
    public int getDivision_ID() {
        return Division_ID;
    }
    /**Method used to set the Division ID*/
    public void setDivision_ID(int division_ID) {
        Division_ID = division_ID;
    }
    /**Method used to get the Customer Name*/
    public String getCustomer_Name() {
        return Customer_Name;
    }
    /**Method used to set the Customer Name*/
    public void setCustomer_Name(String customer_Name) {
        Customer_Name = customer_Name;
    }
    /**String used to get the address*/
    public String getAddress() {
        return Address;
    }
    /**String used to set the address*/
    public void setAddress(String address) {
        Address = address;
    }
    /**String used to get the postal code*/
    public String getPostal_Code() {
        return Postal_Code;
    }
    /**String used to set the postal code*/
    public void setPostal_Code(String postal_Code) {
        Postal_Code = postal_Code;
    }
    /**String used to get Phone*/
    public String getPhone() {
        return Phone;
    }
    /**String used to set Phone*/
    public void setPhone(String phone) {
        Phone = phone;
    }
    /**LocalDateTime used to get create date*/
    public ZonedDateTime getCreate_Date() {
        return Create_Date;
    }
    /**LocalDateTime used to set create date*/
    public void setCreate_Date(ZonedDateTime create_Date) {
        Create_Date = create_Date;
    }
    /**LocalDateTime used to get create by*/
    public String getCreated_By() {
        return Created_By;
    }
    /**LocalDateTime used to set create by*/
    public void setCreated_By(String created_By) {
        Created_By = created_By;
    }
    /**LocalDateTime used to get the last update*/
    public ZonedDateTime getLast_Update() {
        return Last_Update;
    }
    /**LocalDateTime used to set the last update*/
    public void setLast_Update(ZonedDateTime last_Update) {
        Last_Update = last_Update;
    }
    /**LocalDateTime used to get the last update by*/
    public String getLast_Updated_By() {
        return Last_Updated_By;
    }
    /**String used to set the last update by*/
    public void setLast_Updated_By(String last_Updated_By) {
        Last_Updated_By = last_Updated_By;
    }
    /**Method used to add a customer*/
    public static void addCustomer(Customers newCustomer){
        myCustomers.add(newCustomer); }
    /**Method used to delete a customer recored*/
    public static boolean deleteCustomer(Customers selectedCustomer){
        myCustomers.remove(selectedCustomer);
        return true;
    };
    /**Method used to update a customer record*/
    public static void updateCustomer(Customers selectedCustomers){
        myCustomers.set(0,selectedCustomers); //BUG: index of 0 used each time customer updated. possible issue.
        }
}
