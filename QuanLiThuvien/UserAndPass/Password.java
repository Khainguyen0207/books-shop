package QuanLiThuvien.UserAndPass;

import java.util.ArrayList;

public class Password {
    public String password;
    public ArrayList<Infomation> infoUsers = new ArrayList<>();
    public void setPassword(String password) {
        this.password = password;
    }

    public void addInfoUser(Infomation infomation) {
        infoUsers.add(infomation);
    }
        
}
