package jm.task.core.jdbc.service;

import java.util.List;

public interface UserService<T> {
    void createUsersTable();

    void dropUsersTable();

    void saveUser(String name, String lastName, byte age);

    void removeUserById(long id);

    List<T> getAllUsers();

    void cleanUsersTable();
}
