package QuanLiThuvien.UserAndPass;

import java.io.*;
import java.util.Scanner;

import QuanLiThuvien.Forms.Login.Form_Login;

public class CsdlAccount extends Account {
    static File file = new File("account.txt");
    public static int numberUser = 0;
    public static void updateAccount() {
        try {
            Account account = new Account();
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                String accountData[] = data.split("/");
                Username username = new Username();
                username.setUser(accountData[0]);
                username.setMail(accountData[2]);
                account.addUser(username);
                Password password = new Password();
                password.setPassword(accountData[1]);
                username.addPass(password);
                Infomation infomation = new Infomation();
                String info[] = accountData[3].split("-");
                infomation.setInfoUser(info);
                password.addInfoUser(infomation);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
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
            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextLine()) {
                    String data = scanner.nextLine();
                    String account[] = data.split("/");
                    if (account[0].equals(user)) {
                        if (account[1].equals(pass)) {
                            if (account[3].equals("1")) {
                                Form_Login.function("admin");
                                System.out.println("Log in as admin");
                                return true;
                                
                            } else if (account[3].equals("0")) {
                                Form_Login.function("user");
                                System.out.println("Log in as user");
                                return true;
                            } else {
                                Form_Login.function("error");
                                System.out.println("Account is ban");
                                return true;
                            }
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
        return false;
    }
}
