package QuanLiThuvien.Models;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import QuanLiThuvien.DatabaseConnection;

public class Model {
    
    DatabaseConnection database = new DatabaseConnection();
    String table;
    List<String> fillable = new ArrayList<>();

    public void setNameTable(String table) {
        this.table = table;
    }

    public void setFillable(ArrayList<String> fillable) {
        this.fillable = fillable;
    }

    public String getName() {
        return table;
    }
    
    public List<String> getFillable() {
        return fillable;
    }

    public List<Map<String,String>> getData() {
        try {
            String datatype = "SELECT * FROM " + this.getName() ;
            ResultSet data = database.getData(database.connection, datatype);
            List<Map<String,String>> items = new ArrayList<>();
            
            while (data.next()) {
                try {
                    Map<String,String> item = new HashMap<>();
                    for (String fill : this.fillable) {
                        item.put(fill, data.getString(fill));
                    }
                    items.add(item);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            return items;
        } catch (Exception e) {}
        return null; 
    }

    protected Boolean checkDataExis(String key, String value) {
        try {
            //SELECT `key` FROM `users` WHERE `key`="value"
            String datatype = "SELECT `" + key + "` FROM `" + this.getName() +"` WHERE `" + key +"`='" + value +"'";
            ResultSet data = database.getData(database.connection, datatype);
            System.out.println(datatype);
            while (data.next()) {
                if (data.getString(key).equals(value)) {
                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false; 
        }
        return false; 
    }

    public void create(Map<String,String> data) {
        try {
            String query = "";
            String keys = "";
            String values = "";

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

            query = "INSERT INTO `users`(" + keys + ")" + " VALUES " + "("+ values+ ")";
            Statement statement = database.connection.createStatement();
            System.out.println(query);
            statement.executeUpdate(query); 
        } catch (Exception e) {
            System.out.println("Đã tồn tại dữ liệu");
        }
    }

    protected void updateInfomation(String key, String value, String email) {
        //UPDATE `users` SET `username`='as' WHERE `username`='anhdz';
        try {
            String query = "UPDATE `users` SET `" + key + "`='" + value + "' WHERE `email`='" + email + "'" ;
            Statement statement = database.connection.createStatement();
            System.out.println(query);
            statement.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }        
    }

    public void update(Map<String,String> data) {
        //UPDATE `users` SET `id`='[value-1]',`username`='[value-2]',`password`='[value-3]',`email`='[value-4]',`infomation`='[value-5]' 
        String query = "UPDATE `users` SET ";
        String username = " WHERE ";
        try {   
            for (Entry<String, String> data1 : data.entrySet()) {
                if (data1.getKey().equals("password")) {
                    query += "`" + data1.getKey() +"`='" + data1.getValue() +"',";
                } else {
                    username += "`" + data1.getKey() +"`='" + data1.getValue() +"',";
                }
            }
            username = username.substring(0, username.length()-1);
            query = query.substring(0, query.length()-1);
            query += username;

            Statement statement = database.connection.createStatement();
            System.out.println(query);
            statement.executeUpdate(query);
        } catch (Exception e) {
            System.out.println(query);
            if (query.equals("UPDATE `users` SET")) {
                System.out.println("Pass thay đổi trống");
            } else {
                System.out.println("Đã tồn tại dữ liệu");
            }
        }
    }

}
