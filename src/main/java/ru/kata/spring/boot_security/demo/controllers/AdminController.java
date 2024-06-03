package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }
    @GetMapping("/users")
    public String getUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "admin/users";
    }

    @GetMapping("users/{id}")
    public String getUserById(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.findOne(id));
        return "admin/userPage";
    }

    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.getRoles());
        return "admin/new";
    }

    @PostMapping
    public String createUser(@ModelAttribute("user")
                         @Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            return "admin/new";
        }
        userService.save(user);
        return "redirect:admin/users";
    }

    @GetMapping("user/{id}/edit")
    public String getUpdateEventPage(Model model, @PathVariable("id") long id) {
        model.addAttribute("user", userService.findOne(id));
        model.addAttribute("roles", roleService.getRoles());
        return "/admin/edit";
    }

    @DeleteMapping("users/{id}")
    public String deleteUser( @PathVariable("id") long id) {
        userService.delete(id);
        return "redirect:/admin/users";
    }

    @PatchMapping("users/{id}")
    public String updateEvent(@ModelAttribute("user") @Valid User user,
                              @PathVariable("id") long id,
                              BindingResult result) {
        if (result.hasErrors()) {
            return "/admin/edit";
        }
        userService.update(id, user);
        return "redirect:/admin/users";
    }
}