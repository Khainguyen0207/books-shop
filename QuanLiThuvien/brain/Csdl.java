package QuanLiThuvien.brain;


import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Csdl {
    static File fileLogs = new File("TimeUpdateData.txt");
    public static void updateTime(String nameUpdate) {
        try {
            FileWriter fileWriter = new FileWriter(fileLogs , true);
            fileWriter.append(TimeUpdateData.timeUpdateData(2) +"-->"+ nameUpdate +"\n");
            fileWriter.close();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }


}
