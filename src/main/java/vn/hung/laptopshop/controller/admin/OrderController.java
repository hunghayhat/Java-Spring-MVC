package vn.hung.laptopshop.controller.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import vn.hung.laptopshop.domain.Order;
import vn.hung.laptopshop.domain.OrderDetail;
import vn.hung.laptopshop.domain.User;
import vn.hung.laptopshop.service.OrderService;

@Controller
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/place-order")
    public String postOrderPage(HttpServletRequest request,
            @RequestParam("receiverName") String receiverName,
            @RequestParam("receiverAddress") String receiverAddress,
            @RequestParam("receiverPhone") String receiverPhone) {
        User currentUser = new User();
        HttpSession session = request.getSession(false);
        long id = (long) session.getAttribute("id");
        currentUser.setId(id);
        this.orderService.handlePlaceOrder(currentUser, session, receiverName, receiverAddress, receiverPhone);
        return "redirect:order-finish";
    }

    @GetMapping("/admin/order")
    public String getDashboard(Model model) {
        List<Order> orders = this.orderService.findAllOrders();
        model.addAttribute("orders", orders);
        return "admin/order/show";

    }

    @GetMapping("/admin/order/{id}")
    public String getOrderDetail(Model model, @PathVariable long id) {
        Order currentOrder = this.orderService.findOrderById(id);
        List<OrderDetail> orderDetails = this.orderService.findDetailsByOrder(currentOrder);
        model.addAttribute("orderDetails", orderDetails);
        return "admin/order/detail";
    }

    @GetMapping("/admin/order/update/{id}")
    public String getUpdateOrderDetail(Model model, @PathVariable long id) {
        Order currentOrder = this.orderService.findOrderById(id);
        model.addAttribute("currentOrder", currentOrder);
        return "admin/order/update";
    }

    @PostMapping("/admin/order/update")
    public String postUpdateOrderStatus(Model model, @ModelAttribute("currentOrder") Order order) {
        Order currentOrder = this.orderService.findOrderById(order.getId());
        currentOrder.setStatus(order.getStatus());
        this.orderService.handleUpdateOrder(currentOrder);
        return "redirect:/admin/order";
    }

    @GetMapping("/admin/order/delete/{id}")
    public String getDeleteOrder(Model model, @PathVariable long id) {
        Order currentOrder = this.orderService.findOrderById(id);
        model.addAttribute("currentOrder", currentOrder);
        return "admin/order/delete";
    }

    @PostMapping("/admin/order/delete")
    public String postDeleteOrder(Model model, @ModelAttribute("currentOrder") Order order) {
        Order currentOrder = this.orderService.findOrderById(order.getId());
        this.orderService.handleDeleteOrder(currentOrder);
        return "redirect:/admin/order";
    }
}
