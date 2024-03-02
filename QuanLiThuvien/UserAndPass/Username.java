package QuanLiThuvien.UserAndPass;

import java.util.ArrayList;

public class Username {
    public String username;
    public String email;

    public ArrayList<Password> passwords = new ArrayList<>();

    public void setUser(String name) {
        this.username = name;

    }

    public void setMail(String email) {
        this.email = email;
    }

    public void addPass(Password password) {
        this.passwords.add(password);

    }
}
