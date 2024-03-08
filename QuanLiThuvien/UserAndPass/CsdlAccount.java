package QuanLiThuvien.UserAndPass;


import QuanLiThuvien.Forms.Login.Form_Login;
import QuanLiThuvien.Forms.User.Form_User;
import QuanLiThuvien.Models.UserModel;

import java.util.Map;

public class CsdlAccount extends Account {
    public static int numberUser = 0;

    public static boolean checkAccount(String user, String pass) {
        if (UserModel.checkAccount(user, pass)) {
            int ID = UserModel.getIDByUser("users", user);
            for (Map<String, String> userInfomation : UserModel.getDataByID("users", ID)) {
                //PanelUser.setInfoUser(userInfomation);
                int role = Integer.parseInt(userInfomation.get("role"));
                if (role == 0) {
                    Form_User.checkLogin = true;
                    Form_Login.function("user", ID);
                } else {
                    Form_Login.function("admin", ID);
                }

            }
            return true;
        }
        return false;
    }
}
