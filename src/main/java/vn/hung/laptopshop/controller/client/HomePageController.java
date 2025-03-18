package vn.hung.laptopshop.controller.client;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import vn.hung.laptopshop.domain.Order;
import vn.hung.laptopshop.domain.Product;
import vn.hung.laptopshop.domain.User;
import vn.hung.laptopshop.domain.dto.RegisterDTO;
import vn.hung.laptopshop.repository.CartDetailRepository;
import vn.hung.laptopshop.service.OrderService;
import vn.hung.laptopshop.service.ProductService;
import vn.hung.laptopshop.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class HomePageController {

    private final CartDetailRepository cartDetailRepository;
    private final UserService userService;
    private final ProductService productService;
    private final PasswordEncoder passwordEncoder;
    private final OrderService orderService;

    public HomePageController(ProductService productService, UserService userService, PasswordEncoder passwordEncoder,
            CartDetailRepository cartDetailRepository, OrderService orderService) {
        this.productService = productService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.cartDetailRepository = cartDetailRepository;
        this.orderService = orderService;
    }

    @GetMapping("/")
    public String getHomePage(Model model) {
        List<Product> products = this.productService.getAllProducts();
        model.addAttribute("products", products);
        return "client/homepage/show";
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("registerUser", new RegisterDTO());
        return "client/auth/register";
    }

    @PostMapping("/register")
    public String handleRegister(@ModelAttribute("registerUser") @Valid RegisterDTO registerDTO,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "client/auth/register";
        }
        User user = this.userService.registerUserDTOtoUser(registerDTO);
        String hashPassword = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(hashPassword);
        user.setRole(this.userService.getRoleByName("USER"));
        this.userService.handleSaveUser(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        return "client/auth/login";
    }

    @PostMapping("/login")
    public String postLoginPage(Model model) {

        return "";
    }

    @GetMapping("/access-deny")
    public String getDenyPage(Model model) {
        return "client/auth/deny";
    }

    @GetMapping("/order-history")
    public String getOrderHistoryPage(Model model, HttpServletRequest request) {
        User currentUser = new User();
        HttpSession session = request.getSession(false);
        long id = (long) session.getAttribute("id");
        currentUser.setId(id);

        List<Order> orders = this.orderService.findAllOrders();
        model.addAttribute("orders", orders);
        return "client/order/order-history";
    }

}
