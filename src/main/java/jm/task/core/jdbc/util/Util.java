package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Util {
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static final String URL = "jdbc:mysql://localhost:3306/jmpp";

    private Util() {
    }

    static {
        loadDriver();
    }

    private static void loadDriver() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() throws SQLException{
//        Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
//        connection.setAutoCommit(false);
//        return connection;
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
