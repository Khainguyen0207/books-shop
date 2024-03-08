package QuanLiThuvien.UserAndPass;

import java.io.*;
import java.util.Scanner;

import QuanLiThuvien.Forms.Login.Form_Login;
import QuanLiThuvien.Models.UserModel;

public class CsdlAccount extends Account {
    static File file = new File("account.txt");
    public static int numberUser = 0;
    public static void updateAccount() {
       
    }

    public static void writeFile(String account) {
        try {
            FileWriter fileWriter = new FileWriter(file, true);
            fileWriter.append(account +"\n");
            fileWriter.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        
    }

    public static boolean checkExisUsername(String username) {
        try {
            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextLine()) {
                    String i = scanner.nextLine();
                    String data[] = i.split("/");
                    if (data[0].equals(username)) {
                        return true;
                    }
                }
                scanner.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
        return false;
    }

    public static boolean checkAccount(String user, String pass) {
        try {
            int role = UserModel.checkAccount(user, pass);
            if (role == 1) {
                Form_Login.function("admin");
                System.out.println("Log in as admin");
                return true;
                
            } else if (role == 0) {
                Form_Login.function("user");
                System.out.println("Log in as user");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    
}
