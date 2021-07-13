package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Util {
    private static final String USERNAME_KEY = PropertiesUtil.get("db.username");
    private static final String PASSWORD_KEY = PropertiesUtil.get("db.password");
    private static final String URL_KEY = PropertiesUtil.get("db.url");

    private Util() {
    }

    static {
        loadDriver();
    }

    private static void loadDriver() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection open() {
        try {
            return DriverManager.getConnection(URL_KEY,USERNAME_KEY, PASSWORD_KEY);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
