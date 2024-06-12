package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.entities.User;

import java.util.List;

public interface UserDAO {
    List<User> findAll();
    void save(User user);
    User findByUsername(String username);
    User findById(Long id);
    void deleteById(Long id);
}
