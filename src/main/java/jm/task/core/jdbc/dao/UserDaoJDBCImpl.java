package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        String createTableSql = "CREATE TABLE IF NOT EXISTS users (" +
                "idUsers BIGINT NOT NULL AUTO_INCREMENT , " +
                "name VARCHAR(100) NULL,\n" +
                "last_name VARCHAR(100) NULL,\n" +
                "age TINYINT NULL,\n" +
                "PRIMARY KEY (idUsers));";
        try (Connection connection = Util.open();
             PreparedStatement preparedStatement = connection.prepareStatement(createTableSql)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("Fail to create table: " + e);
        }
    }

    public void dropUsersTable() {
        String dropTableSql = "DROP TABLE IF EXISTS users";
        try (Connection connection = Util.open();
             PreparedStatement preparedStatement = connection.prepareStatement(dropTableSql)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("Fail to drop table: " + e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String insertUserSql = "INSERT INTO users(name, last_name, age)" +
                "VALUES (?, ?, ?)";
        try (Connection connection = Util.open();
             PreparedStatement preparedStatement = connection.prepareStatement(insertUserSql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.execute();
            System.out.printf("User с именем – \"%s %s\" добавлен в базу данных%n", name, lastName);
        } catch (SQLException e) {
            System.out.println("Fail to save new user: " + e);
        }
    }

    public void removeUserById(long id) {
        String deleteSql = "DELETE FROM users WHERE idUsers = ?";
        try (Connection connection = Util.open();
        final PreparedStatement preparedStatement = connection.prepareStatement(deleteSql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("Fail to delete new user: " + e);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String getAllSql = "SELECT * FROM users";
        try (Connection connection = Util.open();
             PreparedStatement preparedStatement = connection.prepareStatement(getAllSql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("idUsers"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Fail to get all users: " + e);
        }
        return users;
    }

    public void cleanUsersTable() {
        String cleanTableSql = "TRUNCATE TABLE users";
        try (Connection connection = Util.open();
             PreparedStatement preparedStatement = connection.prepareStatement(cleanTableSql)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("Fail to clean table \"Users\": " + e);
        }
    }
}
