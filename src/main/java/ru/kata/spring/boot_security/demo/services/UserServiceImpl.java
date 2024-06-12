package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.UserDAO;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;

import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;
    private final RoleService roleService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getAllUsers() {
        return userDAO.findAll();
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDAO.save(settingRoles(user));
    }

    private User settingRoles(User user) {
        List<Role> roles = user.getRoles();
        Collection<Role> roleList = roleService.getRoles();
        List<Role> list = new ArrayList<>();
        for (Role role : roleList) {
            for (Role userRole : roles) {
                if (role.getRoleName().equals(userRole.getRoleName())) {
                    list.add(role);
                }
            }
        }
        //Преобразование списка в множество перед установкой ролей пользователя
        Set<Role> roleSet = new HashSet<>(list);
        user.setRoles(roleSet); //если пердать list выдаёт ошибку типизации
        return user;
    }

    @Override
    public User getUserById(long id) {
        return userDAO.findById(id);
    }

    @Override
    @Transactional
    public void updateUser(long id, User updateUser) {
        User user = userDAO.findById(id);
        user.setUsername(updateUser.getUsername());
        user.setLastname(updateUser.getLastname());
        user.setAge(updateUser.getAge());
        user.setEmail(updateUser.getEmail());
        user.setRoles((Set<Role>) updateUser.getRoles());
        if (!user.getPassword().equals(updateUser.getPassword())) {
            user.setPassword(passwordEncoder.encode(updateUser.getPassword()));
        }
        userDAO.save(settingRoles(user));
    }

    @Override
    @Transactional //(rollbackFor = Exception.class)
    public void deleteUser(long id) {
        userDAO.deleteById(id);
    }

    @Override
    public User getUserByUsername(String username) {
        return userDAO.findByUsername(username);
    }
}

