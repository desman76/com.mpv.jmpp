package jm.task.core.jdbc.util;

import com.mysql.cj.jdbc.MysqlDataSource;
import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public final class Util {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static final String URL = "jdbc:mysql://localhost:3306/jmpp";
    private static final String DIALECT = "org.hibernate.dialect.MySQL8Dialect";
    private static final String HBM2DDL = "update";
    private static final String SHOW_SQL = "true";
    private static final String FORMAT_SQL = "true";
    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;

    private Util() {
    }

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

//        MysqlDataSource dataSource = new MysqlDataSource();
//        dataSource.setURL(URL);
//        dataSource.setUser(USERNAME);
//        dataSource.setPassword(PASSWORD);
//        Connection connection = dataSource.getConnection();

        Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        connection.setAutoCommit(false);
        return connection;
    }

    public static SessionFactory getSessionFactory2() {
        if (sessionFactory == null) {
            Properties properties = new Properties();
            properties.setProperty(Environment.URL, URL);
            properties.setProperty(Environment.USER, USERNAME);
            properties.setProperty(Environment.PASS, PASSWORD);
            properties.setProperty(Environment.DRIVER, DRIVER);
            properties.setProperty(Environment.DIALECT, DIALECT);
            properties.setProperty(Environment.HBM2DDL_AUTO, HBM2DDL);
            properties.setProperty(Environment.SHOW_SQL, SHOW_SQL);
            properties.setProperty(Environment.FORMAT_SQL, FORMAT_SQL);

            try {
                sessionFactory = new Configuration()
                        .addProperties(properties)
                        .addAnnotatedClass(User.class)
                        .buildSessionFactory();

            } catch (HibernateException e) {
                throw new RuntimeException("Ошибка получения сессии: " + e);
            }
        }
        return sessionFactory;
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();

                Map<String, String> settings = new HashMap<>();
                settings.put(Environment.DRIVER, DRIVER);
                settings.put(Environment.URL, URL);
                settings.put(Environment.USER, USERNAME);
                settings.put(Environment.PASS, PASSWORD);
                settings.put(Environment.DIALECT, DIALECT);
                settings.put(Environment.SHOW_SQL, SHOW_SQL);
                settings.put(Environment.HBM2DDL_AUTO, HBM2DDL);
                registryBuilder.applySettings(settings);

                registry = registryBuilder.build();

                MetadataSources sources = new MetadataSources(registry);
                sources.addAnnotatedClass(User.class);
                Metadata metadata = sources.getMetadataBuilder().build();

                sessionFactory = metadata.getSessionFactoryBuilder().build();

            } catch (Exception e) {
                if (registry != null) {
                    StandardServiceRegistryBuilder.destroy(registry);
                }
                throw new RuntimeException("Ошибка получения сессии: " + e);
            }
        }
        return sessionFactory;
    }
}
