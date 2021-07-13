package jm.task.core.jdbc;

import java.sql.Connection;
import jm.task.core.jdbc.util.Util;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        try (Connection connection = Util.open()) {
            System.out.println(connection.getTransactionIsolation());
        }
    }
}
