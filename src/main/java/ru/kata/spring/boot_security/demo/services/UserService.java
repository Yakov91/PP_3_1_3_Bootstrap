package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.entities.User;

import java.util.List;

public interface UserService {

    User findByName(String name);

    List<User> getAllUsers();
    boolean addUser(User user);
    void updateUser(User user);
    boolean deleteUser(int userid);
    User getUser(int userid);
    void saveUser(User user);


}
