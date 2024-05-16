//package ru.kata.spring.boot_security.demo.configs;
//
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//import ru.kata.spring.boot_security.demo.entities.Role;
//import ru.kata.spring.boot_security.demo.entities.User;
//import ru.kata.spring.boot_security.demo.services.RoleService;
//import ru.kata.spring.boot_security.demo.services.UserService;
//import ru.kata.spring.boot_security.demo.services.UserServiceImpl;
//
//import javax.annotation.PostConstruct;
//import java.util.*;
//
//@Component
//public class DataInitializer {
//    private final UserService userService;
//
//    public DataInitializer(UserService userService) {
//        this.userService = userService;
//    }
//
//    @PostConstruct
//    public void init() {
//
//        Set<Role> adminRoles = new HashSet<>();
//        Role userRole = new Role("ROLE_USER");
//        Role adminRole = new Role("ROLE_ADMIN");
//
//        User admin = new User("Ivan", "100", adminRoles);
//
//        adminRoles.add(userRole);
//        adminRoles.add(adminRole);
//        admin.setRoles(adminRoles);
//
//        userService.save(admin);
//    }
//}
