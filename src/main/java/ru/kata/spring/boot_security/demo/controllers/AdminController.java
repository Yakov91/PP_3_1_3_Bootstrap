package ru.kata.spring.boot_security.demo.controllers;

import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.services.UserServiceImpl;


import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserServiceImpl userService;
    private final RoleServiceImpl roleService;


    @Autowired
    public AdminController(UserServiceImpl userService, RoleServiceImpl roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }
    @GetMapping("/users")
    public String getUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin/users";
    }

    @GetMapping("user/{id}")
    public String getUserById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "admin/userPage";
    }

    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.getRoles());
        return "admin/new";
    }

    @PostMapping
    public String createUser(@Valid @ModelAttribute("user") User user,
                             BindingResult result) {
        if (result.hasErrors()) {
            return "admin/new";
        }
        userService.saveUser(user);
        return "redirect:/admin/users";
    }

    @GetMapping("user/{id}/edit")
    public String getUpdateEventPage(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("roles", roleService.getRoles());
        return "redirect:/admin/edit";
    }

    @DeleteMapping("/users/{id}")
    public String deleteUser( @PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }

    @PatchMapping("user/{id}")
    public String updateEvent(@Valid @ModelAttribute("user") User user,
                              @PathVariable("id") Long id,
                              BindingResult result) {
        if (result.hasErrors()) {
            return "/admin/edit";
        }
        userService.updateUser(id, user);
        return "redirect:/admin/users";
    }

}

//    @GetMapping
//    public String showUser(Model model, Principal principal) {
//        User user = userService.findByUsername(principal.getName());
//        model.addAttribute("user", user);
//        return "admin";
//    }
//
//    @GetMapping("/new")
//    public String saveUser(@ModelAttribute("user") User user,
//                           @RequestParam("authorities") List<String> values) {
//        Set<Role> roleSet = roleService.getSetOfRoles(values);
//        user.setRoles(roleSet);
//        userService.createUser(user);
//        return "redirect:/admin/users";
//    }
//
//    @PostMapping("/edit") //Почему User вроде создаётся в браузере но в базе на отображается
//    public String editUser(@ModelAttribute("user") User user,
//                           @RequestParam("authorities") List<String> values)  {
//        Set<Role> roleSet = roleService.getSetOfRoles(values);
//        user.setRoles(roleSet);
//        userService.updateUser(user);
//
//        return "redirect:/admin/users";
//    }
//    @DeleteMapping("/delete")
//    public String deleteUser(@RequestParam(value = "id", required = true) long id) {
//        User user = userService.deleteUser(id);
//        return "redirect:/admin/users";
//    }
//    @GetMapping("/edit")
//    public String editUserForm(@RequestParam(value = "id",
//                                required = true) long id, Model model) {
//        User user = userService.getUser(id);
//        if (null == user) {
//            return "redirect:/admin/users";
//        }
//        model.addAttribute("user", user);
//        List<Role> roles = roleService.getRolesList();
//        model.addAttribute("allRoles", roles);
//        return "edit";
//    }
