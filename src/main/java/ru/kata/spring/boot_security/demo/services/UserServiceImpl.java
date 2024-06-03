package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.entities.User;

import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private PasswordEncoder passwordEncoder;


    @Autowired
    public UserServiceImpl(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

//    @Override
//    @Transactional
//    public void save(User user) {
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        repository.save(user);
//    }
    @Override
    @Transactional
    public void save(User user) {
        User userForAdd = repository.findByUsername(user.getUsername());
        if (userForAdd != null) {  //если Имя = НикНейм
            System.out.println("Введите другое имя");
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            repository.save(user);
        }
    }

    @Override
    @Transactional
    public void update(long id, User updateUser) {
        updateUser.setId(id);
        updateUser.setPassword(passwordEncoder.encode(updateUser.getPassword()));
        repository.save(updateUser);
    }

    @Override
    @Transactional//(rollbackFor = Exception.class)
    public void delete(long id) {
        repository.deleteById(id);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public User findOne(long id) {
        Optional<User> user = repository.findById(id);
        return user.orElse(null);
    }

    @Override
    public User findByUsername(String username) {
        if(repository.findByUsername(username).getAuthorities().isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        return repository.findByUsername(username);
    }
}

