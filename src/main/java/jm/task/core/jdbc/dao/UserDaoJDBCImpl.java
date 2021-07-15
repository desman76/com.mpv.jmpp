package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao<User> {
    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        String createTableSql = "CREATE TABLE IF NOT EXISTS users (" +
                "id BIGINT NOT NULL AUTO_INCREMENT , " +
                "name VARCHAR(100) NULL,\n" +
                "last_name VARCHAR(100) NULL,\n" +
                "age TINYINT NULL,\n" +
                "PRIMARY KEY (id));";
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(createTableSql);
        } catch (SQLException e) {
            System.out.println("Fail to create table: " + e);
        }
    }

    public void dropUsersTable() {
        String dropTableSql = "DROP TABLE IF EXISTS users";
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(dropTableSql);
        } catch (SQLException e) {
            System.out.println("Fail to drop table: " + e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String insertUserSql = "INSERT INTO users(name, last_name, age)" +
                "VALUES (?, ?, ?)";
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertUserSql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.execute();
            try {
                connection.commit();
                System.out.printf("User с именем – \"%s %s\" добавлен в базу данных%n", name, lastName);
            } catch (SQLException e) {
                System.out.println("Ошибка при коммите нового пользователя " + e);
                try {
                    connection.rollback();
                } catch (SQLException exception) {
                    System.out.println("Ошибка при попытке роллбэка добавления нового пользователя " + exception);;
                }
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при попытке добавления нового пользователя: " + e);
        }
    }

    public void removeUserById(long id) {
        String deleteSql = "DELETE FROM users WHERE id = ?";
        try (Connection connection = Util.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(deleteSql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
            try {
                connection.commit();
            } catch (SQLException e) {
                System.out.println("Ошибка при коммите удаления пользователя " + e);
                try {
                    connection.rollback();
                } catch (SQLException exception) {
                    System.out.println("Ошибка при роллбэк удаления пользователя");
                }
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении пользователя: " + e);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String getAllSql = "SELECT * FROM users";
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(getAllSql);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при получении списка пользователей: " + e);
        }
        return users;
    }

    public void cleanUsersTable() {
        String cleanTableSql = "TRUNCATE TABLE users";
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(cleanTableSql);
        } catch (SQLException e) {
            System.out.println("Ошибка при очистке таблицы \"Users\": " + e);
        }
    }
}
