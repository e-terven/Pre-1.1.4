package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserDaoJDBCImpl extends Util implements UserDao {
    private final Util util;

    public UserDaoJDBCImpl() {
        this.util = new Util();
    }

    Connection con = getConnection();

    public void createUsersTable() {
        try (Statement statement = con.createStatement()) {
            statement.executeUpdate("CREATE TABLE users (id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, " +
                    "name VARCHAR(25), lastName VARCHAR(25), age INT);");
            System.out.println("Table Users created successfully");

        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Statement statement = con.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS users;");
            System.out.println("Table has been deleted");
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO users (name, lastName, age) VALUES (?, ?, ?);")) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("Added User: " + " " + name  + " " + lastName  + " " + age);
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement = con.prepareStatement("DELETE FROM users WHERE id = ?")) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try (PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM users;")) {
            ResultSet res = preparedStatement.executeQuery();
            while (res.next()) {
                User user = new User();
                user.setId(res.getLong(1));
                user.setName(res.getString(2));
                user.setLastName(res.getString(3));
                user.setAge(res.getByte(4));
                list.add(user);
            }
            System.out.println('\n' + "Users list:");
        } catch (SQLException e) {
            e.getStackTrace();
        }
        return list;
    }

    public void cleanUsersTable() {
        try (Statement statement = con.createStatement()) {
            statement.executeUpdate("DELETE FROM users;");
            System.out.println('\n' + "Table is empty");
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }
}

