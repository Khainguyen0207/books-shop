package QuanLiThuvien.UserAndPass;

import java.util.ArrayList;

public class Account {
    public static ArrayList<Username> Usernames = new ArrayList<>();
    public ArrayList<Username> Emails = new ArrayList<>();

    public void addUser(Username username) {
        Usernames.add(username);

    }

}
