package QuanLiThuvien.Models;

import java.util.Arrays;
import java.util.Map;

import javax.swing.JOptionPane;

public class UserModel extends Model {
    public UserModel() {
        table = "users";
        fillable = Arrays.asList("username", "password", "email", "role", "infomation");
    }

    public static int checkAccount(String user, String pass) {
        UserModel userModel = new UserModel();
        for (Map<String,String> string : userModel.getData()) {
            if (string.get("username").toString().equals(user) && string.get("password").equals(pass)) {
                //System.out.println(string.get("role"));
                return Integer.valueOf(string.get("role"));
            }
        }

        return 3;
    }

    public static void main(String[] args) {
        UserModel userModel = new UserModel();
        System.out.println(userModel.getFillable().toString());
    }

    public static Boolean checkData(String key, String value) {
        UserModel userModel = new UserModel();
        return userModel.checkDataExis(key, value);
    }

    public static void updateInfo(String key, String value, String email) {
        UserModel userModel = new UserModel();
        if (key.equals("username")) {
            JOptionPane.showMessageDialog(null, "Không thể thay đổi tài khoản");
        } else {
            userModel.updateInfomation(key, value, email);
        }
        
    }


}
