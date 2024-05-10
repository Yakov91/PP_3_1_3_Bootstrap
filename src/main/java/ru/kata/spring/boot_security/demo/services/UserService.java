package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.entities.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    void saveUser(User user);

    User getUserById(Long id);

    User getUserByUsername(String username);

    void deleteUser(Long id);

    void updateUser(Long id, User updateUser);

}
