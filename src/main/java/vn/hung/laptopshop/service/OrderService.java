package vn.hung.laptopshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import vn.hung.laptopshop.domain.Cart;
import vn.hung.laptopshop.domain.CartDetail;
import vn.hung.laptopshop.domain.Order;
import vn.hung.laptopshop.domain.OrderDetail;

import vn.hung.laptopshop.domain.User;
import vn.hung.laptopshop.repository.CartDetailRepository;
import vn.hung.laptopshop.repository.CartRepository;
import vn.hung.laptopshop.repository.OrderDetailRepository;
import vn.hung.laptopshop.repository.OrderRepository;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;

    public OrderService(OrderDetailRepository orderDetailRepository,
            OrderRepository orderRepository, CartRepository cartRepository, CartDetailRepository cartDetailRepository) {
        this.orderDetailRepository = orderDetailRepository;
        this.orderRepository = orderRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.cartRepository = cartRepository;

    }

    public void handlePlaceOrder(User user, HttpSession session, String receiverName, String receiverAddress,
            String receiverPhone) {

        Cart cart = this.cartRepository.findCartByUser(user);
        List<CartDetail> cartDetails = cart.getCartDetails();
        if (cart != null) {
            Order order = new Order();
            order.setUser(user);
            order.setReceiverName(receiverName);
            order.setReceiverAddress(receiverAddress);
            order.setReceiverPhone(receiverPhone);
            double totalPrice = 0;
            for (CartDetail cartDetail : cartDetails) {
                totalPrice += cartDetail.getPrice() * cartDetail.getQuantity();
            }
            order.setTotalPrice(totalPrice);
            order.setStatus("PENDING");
            order = this.orderRepository.save(order);

            if (cartDetails != null) {
                for (CartDetail cartDetail : cartDetails) {
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setOrder(order);
                    orderDetail.setProduct(cartDetail.getProduct());
                    orderDetail.setPrice(cartDetail.getPrice());
                    orderDetail.setQuantity(cartDetail.getQuantity());
                    this.orderDetailRepository.save(orderDetail);
                }
                for (CartDetail cartDetail : cartDetails) {
                    this.cartDetailRepository.deleteById(cartDetail.getId());
                }
                this.cartRepository.deleteById(cart.getId());
                session.setAttribute("sum", 0);
            }
        }
    }

    public List<Order> findAllOrders() {
        return this.orderRepository.findAll();
    }

    public Order findOrderById(long id) {
        return this.orderRepository.findById(id);
    }

    public List<OrderDetail> findDetailsByOrder(Order order) {
        return this.orderDetailRepository.findDetailsByOrder(order);
    }

    public void handleUpdateOrder(Order order) {
        this.orderRepository.save(order);
    }

    public void handleDeleteOrder(Order order) {
        this.orderRepository.deleteById(order.getId());
    }

}
