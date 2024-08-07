package QuanLiThuvien.Forms.Login;

import java.util.HashMap;

import QuanLiThuvien.Models.UserModel;
import QuanLiThuvien.UserAndPass.CsdlAccount;
import QuanLiThuvien.brain.SendMail;




public class ErrorList extends Thread {
    public static HashMap<String, String> errors = new HashMap<>();

    public static Boolean checkErrorLogin(String user, String pass) {
        //Error username
        if (user.length() < 1) {
            errors.put("user", "Tài khoản không được bỏ trống");
            return true;
        }

        //Error password
        if (pass.length() < 1) {
            errors.put("pass", "Mật khẩu không được để trống");
            return true;
        }

        //UserPass Login
        if (!CsdlAccount.checkAccount(user, pass)) {
            errors.put("login", "Tài khoản hoặc mật khẩu không đúng");
            return true;
        }

        return false;
    }

    public static Boolean checkErrorRegister(String user, String pass, String email, String name) {
        //Error username
        if (user.length() < 3) {
            errors.put("user", "Tài khoản trên 3 kí tự!");
            return true;
        }

        if (name.length() < 3) {
            errors.put("user", "Vui lòng nhập tên hợp lệ");
            return true;
        }

        //Error password
        if (pass.length() < 1) {
            errors.put("pass", "Mật khẩu không được để trống");
            return true;
        }

        if (email.length() < 1) {
            errors.put("pass", "Mail không được để trống");
            return true;
        }

        if (!email.contains("@") || !email.contains(".")) {
            errors.put("pass", "Định dạng mail không đúng");
            return true;
        }

        if (UserModel.checkDataAccount("username", user) || UserModel.checkDataAccount("email", email)) {
            errors.put("register", "Tài khoản hoặc email đã tồn tại");
            return true;
        }
        return false;
    }

    public static Boolean checkErrorAddBook(String namebook, String money, String countBook, String content, String path) {
        if (namebook.isEmpty() || money.isEmpty() || countBook.isEmpty() || content.isEmpty()) {
            errors.put("addBook", "Vui lòng điền đầy đủ thông tin");
            return true;
        }

        if (path.equals("Chưa có ảnh") || path.isEmpty()) {
            errors.put("addBook", "Vui lòng chọn ảnh");
            return true;
        }

        String data[] = {namebook, money, content , content};
        try {
            Integer.valueOf(money);
            Integer.valueOf(countBook);
        } catch (Exception e) {
            errors.put("addBook", "Giá trị tiền và số lượng không hợp lệ - Nhập số nguyên");
            return true;
        }

        return false;
    }

    public static Boolean checkMailForget(String mailto) {

        if (mailto.length() < 1) {
            errors.put("mail", "Mail xác thực không được để trống");
            return true;
        } 

        if (!mailto.contains("@") || !mailto.contains(".")) {
            errors.put("mail", "Định dạng mail không đúng");
            return true;
        }

        if (!UserModel.checkDataAccount("email", mailto)) {
            errors.put("mail", "Mail chưa được đăng kí");
            return true;
        }

        return false;
    }

    public static Boolean checkPasswordOld(String password) {
        if (password.isEmpty()) {
            errors.put("empty", "Vui lòng nhập mật khẩu");
            return true;
        }

        if (password.length() < 5) {
            errors.put("empty", "Mật khẩu quá ngắn phải nhiều hơn 4 kí tự");
            return true;
        }
        return false;
    }

    public static Boolean checkCodeForget(String code) {
        try {
            Integer.valueOf(code);
        } catch (Exception e) {
            errors.put("code", "Mã không hợp lệ");
            return true;
        }

        if (Integer.valueOf(code) != SendMail.code) {
            errors.put("code", "Mã không trùng khớp");
            return true;
        }
        return false;

    }

}
