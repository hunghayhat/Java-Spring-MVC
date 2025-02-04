package vn.hoidanit.laptopshop.controller;


import org.springframework.web.bind.annotation.RestController;

import vn.hoidanit.laptopshop.service.UserService;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class UserController {

    //DI: Dependency Injection
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("")
    
    public String getHomePage() {

        return this.userService.handleHello();
    }
}
