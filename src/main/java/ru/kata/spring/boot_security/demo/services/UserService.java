package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kata.spring.boot_security.demo.entities.User;

import java.util.List;

public interface UserService {
    void save(User user);
    void update(Long id, User updateUser);
    void delete(Long id);
    List<User> findAll();
    User findOne(Long id);
    User findByUsername(String username);
    void setPasswordEncoder(PasswordEncoder passwordEncoder);

}
