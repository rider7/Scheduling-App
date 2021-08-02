package sample.Utilities;

import java.io.*;
import java.io.PrintWriter;

public class TextFileOutput {


    public static void myFileOutput(String username) throws IOException {
        //Filename and user variable
        String filename = "loginactivity.txt", user;

        //Create FileWriter object
        FileWriter myFileWriter = new FileWriter(filename, true);


        //Create and Open file
        PrintWriter outputFile = new PrintWriter(myFileWriter);
            user=username;
            outputFile.println(user);
            System.out.println("FileOutput: "+ user);

        //Close file
        outputFile.close();
        System.out.print("File is written!");


    }
}
