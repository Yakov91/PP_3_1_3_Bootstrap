package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.services.UserServiceImpl;

import java.security.Principal;

@Controller
@RequestMapping ("/user")
public class UserController {

    private final UserServiceImpl userService;
    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public String getUserById(Model model, Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("roles", user.getRoles());
        return "user/userPage";
    }

}
