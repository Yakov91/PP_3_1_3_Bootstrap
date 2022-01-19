package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;


import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           @Lazy BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public BCryptPasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    @Transactional
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    @Override
    @Transactional
    public boolean addUser(User user) {
        User userAdd = userRepository.findByName(user.getName()); //Сделать лучше по Email?
        if (userAdd != null) {
            return false;
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return true;
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        if (!user.getPassword().equals(
                getUser(user.getId()).getPassword())) ;
        {//Сравнивает пароль пользователя, переданного в метод, с паролем пользователя
            user.setPassword(passwordEncoder.encode(user.getPassword())); //Устанавливает закодированный пароль обратно в объект пользователя
        }
        userRepository.save(user);
    }

    @Override
    @Transactional
    public boolean deleteUser(int userid) {
        if (userRepository.findById(userid).isPresent()) {
            userRepository.deleteById(userid);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public User getUser(int userid) {
        Optional<User> userFromUser = userRepository.findById(userid);
        return userFromUser.orElse(new User());
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        String password = user.getPassword();
        password = passwordEncoder.encode(password);
        user.setPassword(password);
        userRepository.save(user);
    }
}
