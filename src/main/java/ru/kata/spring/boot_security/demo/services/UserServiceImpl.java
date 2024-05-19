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
    private EntityManager entityManager;


    @Autowired
    public UserServiceImpl(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    @Transactional
    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repository.save(user);
    }
    @Override
    public void update(Long id, User updateUser) {
       updateUser.setId(id);
       updateUser.setPassword(passwordEncoder.encode(updateUser.getPassword()));
       repository.save(updateUser);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(Long id) {
        repository.deleteById(id);

    }
    @Override
    public List<User> findAll() {
        return repository.findAll();
    }
    @Override
    public User findOne(Long id) {
        Optional<User> user = repository.findById(id);
        return user.orElse(null);
    }

    @Override
    public User findByUsername(String username) {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.name = :name", User.class);
        query.setParameter("name", username);
        if (username == null) {
            throw new UsernameNotFoundException("User not found");
        }
        User user = query.getSingleResult();
        user.getRoles().size();
        return user;
//        if(repository.findByUsername(username).getAuthorities().isEmpty()) {
//            throw new UsernameNotFoundException("User not found");
//        }
//        return repository.findByUsername(username);
    }

    @Override
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
}
