package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД


      private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/mydbtest";

    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    static Connection connection;


//     public Util() {
//          try {
//               connection = DriverManager.getConnection(HOST, USERNAME, PASSWORD);
//          } catch (SQLException e) {
//               e.printStackTrace();
//          }
//     }


    public static Connection getConnection() {
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("YES");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("NO");
        }
        return connection;
    }
}
