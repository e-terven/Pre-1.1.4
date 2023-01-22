package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Main {
    private static final UserService userService = new UserServiceImpl();
    public static void main(String[] args) throws SQLException {
        // реализуйте алгоритм здесь

        userService.createUsersTable();

        userService.saveUser("Katia", "Savenko", (byte) 1);
        userService.saveUser("Ter", "Ven", (byte) 2);
        userService.saveUser("Kafa", "Vols", (byte) 3);
        userService.saveUser("Katja", "Shiro", (byte) 4);

        List<User> list = userService.getAllUsers();
        list.forEach(System.out::println);

        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}

