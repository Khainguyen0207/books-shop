package QuanLiThuvien.UserAndPass;

import java.util.ArrayList;
import java.util.HashMap;

public class Account {

    public static ArrayList<HashMap<String, Object>> arrayList = new ArrayList<>(); 
    public HashMap<String, String> tttks = new HashMap<>();
    
    public void addAccount(TTTK tt, Infomation infomation) {
        HashMap<String, Object> account = new HashMap<>();
        account.put("user", tt.username);
        account.put("pass", tt.password);
        account.put("email", tt.email);
        account.put("role", tt.role);
        account.put("name", infomation.name);
        account.put("birthday", infomation.birthday);
        account.put("address", infomation.address);
        arrayList.add(account);
    }

    
}
