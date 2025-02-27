package vn.hung.laptopshop.controller.admin;

import java.lang.reflect.Field;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import vn.hung.laptopshop.domain.User;
import vn.hung.laptopshop.service.UploadService;
import vn.hung.laptopshop.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    private final UserService userService;
    private final UploadService uploadService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, UploadService uploadService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.uploadService = uploadService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/admin/user")
    public String getUserPage(Model model) {
        List<User> users = this.userService.getAllUsers();
        model.addAttribute("users1", users);
        return "admin/user/show";
    }

    @GetMapping("/admin/user/{id}")
    public String getUserDetailPage(Model model, @PathVariable long id) {
        User user = this.userService.findById(id);
        model.addAttribute("id", id);
        model.addAttribute("user", user);
        return "admin/user/detail";
    }

    @GetMapping("/admin/user/create")
    public String createUser(Model model) {
        model.addAttribute("newUser", new User());
        return "admin/user/create";
    }

    @GetMapping("/admin/user/update/{id}")
    public String getUpdateUser(Model model, @PathVariable long id) {

        User currentUser = this.userService.findById(id);
        model.addAttribute("newUser", currentUser);
        return "admin/user/update";
    }

    @PostMapping("/admin/user/create")
    public String createUserPage(Model model,
            @ModelAttribute("newUser") @Valid User hung,
            BindingResult newUserBindingResult,
            @RequestParam("userFile") MultipartFile file) {

        List<FieldError> errors = newUserBindingResult.getFieldErrors();
        for (FieldError error : errors) {
            System.out.println(error.getField() + " - " + error.getDefaultMessage());
        }

        if (newUserBindingResult.hasErrors()) {
            return "admin/user/create";
        }
        String avatar = this.uploadService.handleSaveUploadFile(file, "avatar");
        String hashPassword = this.passwordEncoder.encode(hung.getPassword());

        hung.setAvatar(avatar);
        hung.setPassword(hashPassword);
        hung.setRole(this.userService.getRoleByName(hung.getRole().getName()));
        this.userService.handleSaveUser(hung);
        return "redirect:/admin/user";
    }

    @PostMapping("/admin/user/update")
    public String postUpdateUser(Model model,
            @ModelAttribute("newUser") User hung,
            @RequestParam("userFile") MultipartFile file) {
        User currentUser = this.userService.findById(hung.getId());
        model.addAttribute("newUser", currentUser);
        System.out.println(currentUser);
        if (currentUser != null) {
            currentUser.setAddress(hung.getAddress());
            currentUser.setFullName(hung.getFullName());
            currentUser.setPhone(hung.getPhone());
            currentUser.setRole(this.userService.getRoleByName(hung.getRole().getName()));
            if (file != null && !file.isEmpty()) {
                String avatarPath = this.uploadService.handleSaveUploadFile(file, "avatar");
                currentUser.setAvatar(avatarPath);
            }
            this.userService.handleSaveUser(currentUser);
        }
        return "redirect:/admin/user";
    }

    @GetMapping("/admin/user/delete/{id}")
    public String getDeleteUser(Model model, @PathVariable long id) {
        User currentUser = this.userService.findById(id);
        model.addAttribute("newUser", currentUser);
        return "/admin/user/delete";
    }

    @PostMapping("/admin/user/delete")
    public String postDeleteUser(Model model, @ModelAttribute("newUser") User hung) {
        this.userService.deleteById(hung.getId());
        return "redirect:/admin/user";
    }

}
