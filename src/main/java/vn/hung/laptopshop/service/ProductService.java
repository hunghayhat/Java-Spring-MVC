package vn.hung.laptopshop.service;

import java.util.List;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpSession;
import vn.hung.laptopshop.domain.Cart;
import vn.hung.laptopshop.domain.CartDetail;
import vn.hung.laptopshop.domain.Product;
import vn.hung.laptopshop.domain.User;
import vn.hung.laptopshop.repository.CartDetailRepository;
import vn.hung.laptopshop.repository.CartRepository;
import vn.hung.laptopshop.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final UserService userService;

    public ProductService(ProductRepository productRepository, CartRepository cartRepository,
            CartDetailRepository cartDetailRepository, UserService userService,
            AuthenticationSuccessHandler customSuccessHandler, CustomUserDetailsService customUserDetailsService,
            DaoAuthenticationProvider daoAuthenticationProvider) {
        this.productRepository = productRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.cartRepository = cartRepository;
        this.userService = userService;

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

    public long productCount() {
        return this.productRepository.count();
    }

}
