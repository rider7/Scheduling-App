package sample.Utilities;

import java.io.*;
import java.io.PrintWriter;

public class TextFileOutput {


    public static void myFileOutput(String username, String attempt, String ldt) throws IOException {
        //Filename and user variable
        String filename = "loginactivity.txt", user;

        //Create FileWriter object
        FileWriter myFileWriter = new FileWriter(filename, true);


        //Create and Open file
        PrintWriter outputFile = new PrintWriter(myFileWriter);


            user=username;
            outputFile.println("Username: " + user + " - Attempt: " + attempt + " - Date and Time Stamp: " + ldt);
            System.out.println("Username: " + user + " - Attempt: " + attempt + " - Date and Time Stamp: " + ldt);

        //Close file
        outputFile.close();
        System.out.print("File is written!");


    }
}
