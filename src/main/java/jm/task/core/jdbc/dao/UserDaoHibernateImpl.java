package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import java.util.List;

public class UserDaoHibernateImpl<T> implements UserDao<T> {
    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory2().openSession()) {
            session.beginTransaction();
            session.createNativeQuery("CREATE TABLE IF NOT EXISTS users (" +
                    "id BIGINT NOT NULL AUTO_INCREMENT , " +
                    "name VARCHAR(100) NULL,\n" +
                    "last_name VARCHAR(100) NULL,\n" +
                    "age TINYINT NULL,\n" +
                    "PRIMARY KEY (id));")
                    .executeUpdate();
            System.out.println("table created");
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory2().openSession()) {
            session.beginTransaction();
            session.createNativeQuery("DROP TABLE IF EXISTS users").executeUpdate();
            System.out.println("table removed");
        }
    }

    @Override
    public void saveUser(T t) {
        try (Session session = Util.getSessionFactory2().openSession()) {
            session.beginTransaction();
            session.save(t);
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory2().openSession()) {
            session.beginTransaction();
            session.createQuery("DELETE FROM User WHERE id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
        }
    }

    @Override
    public List<T> getAllUsers() {
        final List<T> users;
        try (Session session = Util.getSessionFactory2().openSession()) {
            session.beginTransaction();
            users = session.createQuery("FROM User").list();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory2().openSession()) {
            session.beginTransaction();
            session.createQuery("DELETE FROM User")
                    .executeUpdate();
        }
    }
}
