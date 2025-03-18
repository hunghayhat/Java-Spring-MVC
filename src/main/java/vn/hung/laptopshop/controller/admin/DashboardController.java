package vn.hung.laptopshop.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import vn.hung.laptopshop.service.OrderService;
import vn.hung.laptopshop.service.ProductService;
import vn.hung.laptopshop.service.UserService;

@Controller
public class DashboardController {
    private final UserService userService;
    private final ProductService productService;
    private final OrderService orderService;

    DashboardController(UserService userService, ProductService productService, OrderService orderService) {
        this.userService = userService;
        this.productService = productService;
        this.orderService = orderService;
    }

    @GetMapping("/admin")
    public String getDashboard(Model model) {
        model.addAttribute("userCount", this.userService.userCount());
        model.addAttribute("productCount", this.productService.productCount());
        model.addAttribute("orderCount", this.orderService.orderCount());
        return "admin/dashboard/show";
    }

}
