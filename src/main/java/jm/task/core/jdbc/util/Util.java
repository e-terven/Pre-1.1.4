package jm.task.core.jdbc.util;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/new_schema";
    private static final String USER = "root";
    private static final String PASSWORD = "mysql33!";

    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName(DRIVER); // наличие JDBC драйвера для работы с БД
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println(con.isClosed());
        } catch (SQLException e ) {
            System.out.println("Error: Unable to Connect to Database.");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return con;
    }
}
