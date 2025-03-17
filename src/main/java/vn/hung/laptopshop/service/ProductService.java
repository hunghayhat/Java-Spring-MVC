package vn.hung.laptopshop.service;

import java.util.List;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;
import vn.hung.laptopshop.controller.admin.DashboardController;
import vn.hung.laptopshop.controller.client.HomePageController;
import jakarta.servlet.http.HttpSession;
import vn.hung.laptopshop.domain.Cart;
import vn.hung.laptopshop.domain.CartDetail;
import vn.hung.laptopshop.domain.Order;
import vn.hung.laptopshop.domain.OrderDetail;
import vn.hung.laptopshop.domain.Product;
import vn.hung.laptopshop.domain.User;
import vn.hung.laptopshop.repository.CartDetailRepository;
import vn.hung.laptopshop.repository.CartRepository;
import vn.hung.laptopshop.repository.OrderDetailRepository;
import vn.hung.laptopshop.repository.OrderRepository;
import vn.hung.laptopshop.repository.ProductRepository;

@Service
public class ProductService {

    private final DashboardController dashboardController;

    private final DaoAuthenticationProvider daoAuthenticationProvider;

    private final CustomUserDetailsService customUserDetailsService;
    private final AuthenticationSuccessHandler customSuccessHandler;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final UserService userService;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    public ProductService(ProductRepository productRepository, CartRepository cartRepository,
            CartDetailRepository cartDetailRepository, UserService userService,
            AuthenticationSuccessHandler customSuccessHandler, OrderDetailRepository orderDetailRepository,
            OrderRepository orderRepository, CustomUserDetailsService customUserDetailsService,
            DaoAuthenticationProvider daoAuthenticationProvider, DashboardController dashboardController) {
        this.productRepository = productRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.cartRepository = cartRepository;
        this.userService = userService;
        this.customSuccessHandler = customSuccessHandler;
        this.orderDetailRepository = orderDetailRepository;
        this.orderRepository = orderRepository;
        this.customUserDetailsService = customUserDetailsService;
        this.daoAuthenticationProvider = daoAuthenticationProvider;
        this.dashboardController = dashboardController;
    }

    public List<Product> getAllProducts() {
        return this.productRepository.findAll();
    }

    public Product handleSaveProduct(Product product) {

        return this.productRepository.save(product);
    }

    public Product findProductById(long id) {
        return this.productRepository.findById(id);
    }

    public void deleteById(long id) {
        this.productRepository.deleteById(id);
    }

    public Cart findCartByUser(User user) {
        return this.cartRepository.findCartByUser(user);
    }

    public void handleAddProductToCart(String email, long productId, HttpSession session) {
        User user = this.userService.getUserByEmail(email);
        if (user != null) {
            Cart cart = this.cartRepository.findCartByUser(user);

            if (cart == null) {
                Cart otherCart = new Cart();
                otherCart.setUser(user);
                otherCart.setSum(0);

                cart = this.cartRepository.save(otherCart);
            }
            Product p = this.productRepository.findById(productId);
            if (p == null) {
                throw new IllegalArgumentException("Product with ID " + productId + " not found");
            }

            CartDetail oldDetail = this.cartDetailRepository.findByCartAndProduct(cart, p);
            if (oldDetail == null) {
                CartDetail cartDetail = new CartDetail();
                cartDetail.setCart(cart);
                cartDetail.setProduct(p);
                cartDetail.setPrice(p.getPrice());
                cartDetail.setQuantity(1);
                this.cartDetailRepository.save(cartDetail);

                int s = cart.getSum() + 1;
                cart.setSum(s);
                this.cartRepository.save(cart);
                session.setAttribute("sum", s);
            } else {
                oldDetail.setQuantity(oldDetail.getQuantity() + 1);
                this.cartDetailRepository.save(oldDetail);
            }

        }
    }

    public List<CartDetail> getAllCartItems() {
        return this.cartDetailRepository.findAll();
    }

    public void handleDeleteCartDetail(long id, HttpSession session) {
        CartDetail selectedItem = this.cartDetailRepository.findById(id);
        Cart selectedCart = selectedItem.getCart();
        this.cartDetailRepository.deleteById(id);
        int sum = selectedCart.getSum();
        if (sum == 1) {
            this.cartRepository.deleteById(selectedCart.getId());
            session.setAttribute("sum", 0);
        } else {
            session.setAttribute("sum", sum - 1);
            selectedCart.setSum(sum - 1);
        }
    }

    public void updateCartBeforeCheckout(List<CartDetail> cartDetails) {
        for (CartDetail cartDetail : cartDetails) {
            CartDetail selectedItem = this.cartDetailRepository.findById(cartDetail.getId());
            selectedItem.setQuantity(cartDetail.getQuantity());
            this.cartDetailRepository.save(selectedItem);
        }
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
