package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class MainController {

    @GetMapping("/authenticated")
    public String pageForAuthenticatedUsers(Principal principal) {
        return "secured part of web service: " + principal.getName();
    }

//    @GetMapping("/hello")
//    public String hello() {
//        return "Hello World!";
//    }
//    @GetMapping("/admin")
//    public String admin() {
//        return "Hello admin!";
//    }
//    @GetMapping("/user")
//    public String user() {
//        return "Hello user!";
//    }
}
