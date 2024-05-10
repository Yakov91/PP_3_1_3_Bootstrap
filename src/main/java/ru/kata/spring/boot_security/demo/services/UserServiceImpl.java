package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.UserDAO;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;

import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserDAO userDao;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDAO userDao, RoleService roleService,
                           PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.save(settingRoles(user));
    }

    @Override
    public User getUserById(Long id) {
        return userDao.findById(id);
    }

    @Override
    public User getUserByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteUser(Long id) {
      userDao.deleteById(id);

    }

    @Override
    public void updateUser(Long id, User updateUser) {
        User user = userDao.findById(id);
        user.setUsername(updateUser.getUsername());
        user.setPassword(updateUser.getPassword());
        user.setRoles(updateUser.getRoles());
        if (!user.getPassword().equals(updateUser.getPassword())) {
            user.setPassword(passwordEncoder.encode(updateUser.getPassword()));
        }
        userDao.save(settingRoles(user));
    }

    private User settingRoles(User user) {
        List<Role> roles = user.getRoles();
        Collection<Role> roleList = roleService.getRoles();
        List<Role> list= new ArrayList<Role>();
        for(Role role : roleList) {
            for(Role userRole : roles) {
                if(role.getRoleName().equals(userRole.getRoleName())) {
                    list.add(role);
                }
            }
        }
        user.setRoles(list);
        return user;
    }

}
