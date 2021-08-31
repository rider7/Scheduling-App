package sample.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Model.Appointments;
import sample.Model.Contacts;

import java.time.LocalDateTime;
/**Class used to extent appointments and allow use of contact name in wrapper object*/
public class Reports {

    public String month;
    public String type;
    public String count;
    /**
     * The ObservableList used for the reports class
     */
    public static ObservableList<Reports> myReports = FXCollections.observableArrayList();

    public static ObservableList<Reports> getMyReports() {
        return myReports;
    }

    public static void setMyReports(ObservableList<Reports> myReports) {
        Reports.myReports = myReports;
    }
//    public static void reportModel() {
//            myReports = FXCollections.observableArrayList();
//            myReports.add(new Reports("Month", "Type", "Count"));
//    };

    public Reports(String Month, String Type, String Count) {
        this.month = Month;
        this.type = Type;
        this.count = Count;
    }
    public static void addReports(Reports newReports) {
        myReports.add(newReports);
        System.out.println("Add Reports: " + newReports.getType());
    }

    public static void updateReports(Reports updateReports) {
        myReports.set(0,updateReports);
        System.out.println("Update Reports: " + updateReports.getType());
    }
    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
