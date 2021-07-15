package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;
import java.sql.SQLException;

/***
 *  Создание таблицы User(ов)
 *
 *  Добавление 4 User(ов) в таблицу с данными на свой выбор. После каждого добавления
 *  должен быть вывод в консоль ( User с именем – name добавлен в базу данных )
 *
 *  Получение всех User из базы и вывод в консоль ( должен быть переопределен
 *  toString в классе User)
 *
 *  Очистка таблицы User(ов)
 *
 *  Удаление таблицы
 */

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();

        userService.createUsersTable();

//        userService.saveUser("Jack", "Sparrow", (byte)250);
//        userService.saveUser("Bob", "Huston", (byte)19);
//        userService.saveUser("Smith", "Prank", (byte)60);
//        userService.saveUser("Andrew", "Collins", (byte)35);
//
//        System.out.println(userService.getAllUsers());
//        userService.cleanUsersTable();
//
//        userService.dropUsersTable();
    }
}
