package vn.hoidanit.laptopshop.controller;


import org.springframework.web.bind.annotation.RestController;

import vn.hoidanit.laptopshop.service.UserService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


// @RestController
// public class UserController {

//     //DI: Dependency Injection
//     private UserService userService;

//     public UserController(UserService userService) {
//         this.userService = userService;
//     }


//     @GetMapping("")
    
//     public String getHomePage() {

//         return this.userService.handleHello();
//     }
// }

@Controller
public class UserController {
    @RequestMapping("/")
    public String getHomePage() {
        return "eric.html";
    }
}
