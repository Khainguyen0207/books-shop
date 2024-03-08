package QuanLiThuvien;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    public Connection connection;
    public DatabaseConnection() {
        try {
            final String DATABASE_URL = "jdbc:mysql://localhost:3306/book_shop";
            final String USERNAME = "root";
            final String PASSWORD = "";
            this.connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                System.out.println(e.getMessage());
            }
            if (connection != null) {
                System.out.println("Kết nối database thành công!");
                
            } else {
                System.out.println("Kết nối database thất bại!");
            }

            ResultSet data = getData(connection, "SELECT * FROM book_shop");
            
            while (data.next()) {
                try {
                    int id = data.getInt("id");
                    String name = data.getString("name");
                    System.out.println("ID: " + id + ", Name: " + name);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

            data.close();
            connection.close();
        } catch (Exception e) {}
    }

    public ResultSet getData(Connection connection, String sql) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet data = statement.executeQuery(sql);
        return data;
    }


}
