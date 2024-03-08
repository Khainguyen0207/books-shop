package QuanLiThuvien.Models;

import java.util.*;
import java.util.Map.Entry;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Model {
    
    DatabaseConnection database = new DatabaseConnection();
    String table;
    List<String> fillable = new ArrayList<>();

    public void setNameTable(String table) {
        this.table = table;
    }

    public void setFillable(List<String> fillable) {
        this.fillable = fillable;
    }

    public String getName() {
        return table;
    }
    
    public List<String> getFillable() {
        return fillable;
    }

    public List<Map<String, String>> getData() {
        String query = "SELECT * FROM `"+ getName() +"` WHERE 1 ORDER BY `id` ASC";
        List<Map<String, String>> list = new ArrayList<>();
        try {
            ResultSet data = database.getData(database.connection, query);
            while (data.next()) {
                Map<String, String> getData = new HashMap<>();
                for (String string : getFillable()) {
                    getData.put(string, data.getString(string));
                }
                list.add(getData);
            }
        } catch (SQLException e) {
            System.out.println("Error Connect SQL: " + e.getMessage());
        }
        return list;
    }

    public List<Map<String, String>> getDataLinkColumn(String query) { // lấy data từ cột liên kết
        List<Map<String, String>> list = new ArrayList<>();
        try {
            ResultSet data = database.getData(database.connection, query);
            while (data.next()) {
                Map<String, String> getData = new HashMap<>();
                for (String string : getFillable()) {

                    getData.put(string, data.getString(string));
                }
                list.add(getData);
            }
        } catch (SQLException e) {
            System.out.println("Error Connect SQL: " + e.getMessage());
        }
        return list;
    }

    //SELECT `products`.* FROM products WHERE products.id = %d;".formatted(ID)
    public List<Map<String, String>> getDataByID(int ID) { // lấy data từ cột liên kết
        String query = "SELECT `"+ getName()+"`.* FROM "+ getName()+" WHERE "+ getName()+ ".id = " + ID +";";
        List<Map<String, String>> list = new ArrayList<>();
        try {
            ResultSet data = database.getData(database.connection, query);
            while (data.next()) {
                Map<String, String> getData = new HashMap<>();
                for (String string : getFillable()) {
                    getData.put(string, data.getString(string));
                }
                list.add(getData);
            }
        } catch (SQLException e) {
            System.out.println("Error Connect SQL: " + e.getMessage());
        }
        return list;
    }
    public int getCountData(int ID) {
        String query = "SELECT COUNT(products.id) AS 'Count' FROM products WHERE `products`.`category_id` = " + ID + ";";
        try {
            ResultSet resultSet = database.getData(database.connection, query);
            while (resultSet.next()) {
                int count = resultSet.getInt("Count");
                return count;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }
    public String getDataByUser(String user, String getdata) {
        try {
            //SELECT `id`, `username`, `password`, `email`, `role`, `infomation` FROM `users` WHERE `username`='anhdz'
            String datatype = "SELECT * FROM `" +
                    getName() +"` WHERE `username`='" + user +"'" ;
            ResultSet data = database.getData(database.connection, datatype);
            while (data.next()) {
                getdata = data.getString(getdata);
            }
        } catch (Exception e) {
            System.out.println("infomation: " + e.getMessage());
        }
        return getdata;
    }

    protected Boolean checkDataExist(String key, String value) {  //Kiểm tra dữ liệu có trong bảng hay không
        try {
            //SELECT `username`,`password` FROM `users` WHERE `username`="anhdz", `password`="anhdz"
            String datatype = "SELECT `" + getName() + "`.* " + "FROM `" + getName() + "` WHERE `" + key + "`='" + value +"';";
            ResultSet resultSet = database.getData(database.connection, datatype);
            if (resultSet.next()) {
                return true;
            }
        } catch (Exception e) {
            System.out.println("Khong co du lieu vua tim kiếm" + e.getMessage());
        }
        return false;
    }

    public Boolean create(Map<String,String> data) {
        /*INSERT INTO `products`(`id`, `name`, `quantity`, `price`, `name_category`, `type_product`, `image`, `description`)
                 VALUES ('[value-1]','[value-2]','[value-3]','[value-4]','[value-5]','[value-6]','[value-7]','[value-8]')*/
        try {
            String query = "";
            String keys = "";
            String values = "";
            // Thiết lập câu lệnh truy vấn để tạo 1 dữ liệu
            for (String key : data.keySet()) {
                //System.out.println(key);
                keys += "`"+ key +"`,";
            }
            keys = keys.substring(0, keys.length() - 1);

            for (String value : data.values()) {
                //System.out.println(value);
                values += "'"+ value +"',";
            }
            values = values.substring(0, values.length() - 1);

            query = "INSERT INTO `"+ getName() + "`(" + keys + ")" + " VALUES " + "("+ values+ ")";
            System.out.println(query);// Câu lệnh sẽ có dạng
            //Kết nối và truy vấn dữ liệu theo câu lệnh truy vấn query
            Statement statement = database.connection.createStatement();
            statement.executeUpdate(query);
        } catch (Exception e) { // Trả về nếu bị trùng dữ liệu
            System.out.println("Đã tồn tại dữ liệu");
            return false;
        }
        return true;
    }

    protected void updateInfomation(String key, String value, String email) {
        //UPDATE `users` SET `username`='as' WHERE `username`='anhdz';
        try {
            String query = "UPDATE `users` SET `" + key + "`='" + value + "' WHERE `email`='" + email + "'" ;
            //Gọi sql và thực hiện truy vấn cập nhật email
            Statement statement = database.connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }        
    }

    /* Liên kết bảng
     * SELECT `products`.*, COUNT(products.category_id) AS 'quantity_book'
     * FROM products
     * JOIN `categorys`
     * ON products.category_id = categorys.id
     * GROUP BY categorys.id;
    * */

    public void updateByID(Map<String,String> data, int ID) {
        //UPDATE `users` SET `id`='[value-1]',`username`='[value-2]',`password`='[value-3]',`email`='[value-4]',`infomation`='[value-5]' WHERE `id`='[value-1]'
        String query = "UPDATE `"+ getName() + "` SET ";
        String username = " WHERE " + "`id`=" + ID +";";
        try {
            for (Entry<String, String> data1 : data.entrySet()) {
                query += "`" + data1.getKey() +"`='" + data1.getValue() +"',";
            }
            username = username.substring(0, username.length()-1);
            query = query.substring(0, query.length()-1);
            query += username;
            System.out.println(query);

            Statement statement = database.connection.createStatement();
            statement.executeUpdate(query);
        } catch (Exception e) {
            System.out.println(query);
            if (query.equals("UPDATE `"+ getName() + "` SET ")) {
                System.out.println("Pass thay đổi trống");
            } else {
                System.out.println("Đã tồn tại dữ liệu");
            }
        }
    }

    public Boolean deleteDataByID(int ID) {
        //"DELETE FROM products WHERE `products`.`id` = 37"
        String query = "DELETE FROM " + getName() + " WHERE `" + getName() + "`.`id` = " + ID;
        try {
            System.out.println("Truy vấn Delete: " + query);
            Statement statement = database.connection.createStatement();
            statement.executeUpdate(query);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
