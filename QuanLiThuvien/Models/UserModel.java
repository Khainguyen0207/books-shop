package QuanLiThuvien.Models;

import java.util.*;

public class UserModel extends Model {

    public UserModel(String table) {
        setNameTable(table);
        setFillable(setFillTable(table));
    }

    public List<String> setFillTable(String table) {
        List<String> fillTable;
        fillTable = switch (table) {
            case "users" -> Arrays.asList("id", "username", "password", "email", "phone", "role", "name", "sex", "create_day", "ip", "birthday");
            case "products" -> Arrays.asList("id", "category_id", "name", "quantity", "price", "type_product", "image", "description");
            case "categorys" -> Arrays.asList("id", "name_category", "image_category");
            default -> null;
        };
        return fillTable;
    }

    public static List<Map<String, String>> getData(String table) {
        UserModel userModel = new UserModel(table);
        return userModel.getData();
    }

    public static List<Map<String, String>> getDataByID(String table, int ID) {
        UserModel userModel = new UserModel(table);
        return userModel.getDataByID(ID);
    }

    public static int getIDByUser(String table, String user) {
        UserModel userModel = new UserModel(table);
        return Integer.parseInt(userModel.getDataByUser(user, "id"));
    }

    public static Boolean checkAccount(String user, String pass) {
        UserModel userModel = new UserModel("users");
        return userModel.checkDataExist("username", user) && userModel.getDataByUser(user, "password").equals(pass);
    }

    public static void updateData(String table, Map<String, String> dataUpdate, int ID) {
        UserModel userModel = new UserModel(table);
        userModel.updateByID(dataUpdate, ID);
    }

    public static Boolean createData(Map<String, String> data, String table) {
        UserModel userModel = new UserModel(table);
        return userModel.create(data);
    }

    public static boolean checkDataAccount(String key, String value) {
        UserModel userModel = new UserModel("users");
        return userModel.checkDataExist(key, value);
    }

    public static void updateInfo(String newPassword, String email) {
        UserModel userModel = new UserModel("users");
        userModel.updateInfomation("password", newPassword, email);
    }

    public static List<Map<String, String>> setDataForTable(String table) {
        String query = "SELECT `categorys`.* FROM categorys WHERE 1 ORDER BY `id` ASC";
        UserModel userModel = new UserModel(table);
        return userModel.getDataLinkColumn(query);
    }
    public static List<Map<String, String>> setDataForTableBooks(String table, int id) {
        UserModel userModel = new UserModel(table);
        String query = "SELECT `products`.* FROM products WHERE products.category_id = %d;".formatted(id);
        return userModel.getDataLinkColumn(query);
    }

    public static Boolean delete(int ID) {
        UserModel userModel = new UserModel("products");
        return userModel.deleteDataByID(ID);
    }

    public static int getCountProduct(int ID) {
        UserModel userModel = new UserModel("products");
        return userModel.getCountData(ID);
    }

    public static void main(String[] args) {

    }
}
