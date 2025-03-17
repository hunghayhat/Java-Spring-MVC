package vn.hung.laptopshop.controller.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import vn.hung.laptopshop.domain.Order;
import vn.hung.laptopshop.domain.OrderDetail;
import vn.hung.laptopshop.service.ProductService;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OrderController {
    private final ProductService productService;

    public OrderController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/admin/order")
    public String getDashboard(Model model) {
        List<Order> orders = this.productService.findAllOrders();
        model.addAttribute("orders", orders);
        return "admin/order/show";

    }

    @GetMapping("/admin/order/{id}")
    public String getOrderDetail(Model model, @PathVariable long id) {
        Order currentOrder = this.productService.findOrderById(id);
        List<OrderDetail> orderDetails = this.productService.findDetailsByOrder(currentOrder);
        model.addAttribute("orderDetails", orderDetails);
        return "admin/order/detail";
    }

    @GetMapping("/admin/order/update/{id}")
    public String getUpdateOrderDetail(Model model, @PathVariable long id) {
        Order currentOrder = this.productService.findOrderById(id);
        model.addAttribute("currentOrder", currentOrder);
        return "admin/order/update";
    }

    @PostMapping("/admin/order/update")
    public String postUpdateOrderStatus(Model model, @ModelAttribute("currentOrder") Order order) {
        Order currentOrder = this.productService.findOrderById(order.getId());
        currentOrder.setStatus(order.getStatus());
        this.productService.handleUpdateOrder(currentOrder);
        return "redirect:/admin/order";
    }

    @GetMapping("/admin/order/delete/{id}")
    public String getDeleteOrder(Model model, @PathVariable long id) {
        Order currentOrder = this.productService.findOrderById(id);
        model.addAttribute("currentOrder", currentOrder);
        return "admin/order/delete";
    }

    @PostMapping("/admin/order/delete")
    public String postDeleteOrder(Model model, @ModelAttribute("currentOrder") Order order) {
        Order currentOrder = this.productService.findOrderById(order.getId());
        this.productService.handleDeleteOrder(currentOrder);
        return "redirect:/admin/order";
    }
}
