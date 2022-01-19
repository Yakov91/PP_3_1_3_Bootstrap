package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;


import java.security.Principal;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;


    @Autowired
    public AdminController(UserService userService,
                           RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

@GetMapping
public String getAllUsers(Model model, Principal principal) {
    User user = userService.findByName(principal.getName());
    model.addAttribute("user", user);
    List<User> listOfUsers = userService.getAllUsers();
    model.addAttribute("listOfUsers", listOfUsers);
    return "admin";
}

    @GetMapping("/new")//по другому URL
    public String createUserForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        Collection<Role> roles = roleService.getRoles();
        model.addAttribute("role", roles);
        return "/userCreater";
    }

    @PostMapping() //Почему User вроде создаётся но в базе на отображается
    public String addUser(@ModelAttribute("user") User user) {
        userService.addUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/{id}/update") //обновление по конкретному id
    public String getEditUserFrom(Model model,
                                  @PathVariable("id") Integer id) { //связывает значение переменной {id} из URI с параметром метода.
        model.addAttribute("user", userService.getUser(id));
        return "/userUpdate";
    }

    @PatchMapping("/{id}")
    public String saveUpdateUser(@ModelAttribute("user") User user,
                                 @PathVariable("id") Integer id) {
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") Integer id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
