package vn.hung.laptopshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.hung.laptopshop.domain.Cart;
import vn.hung.laptopshop.domain.CartDetail;
import vn.hung.laptopshop.domain.Product;
import vn.hung.laptopshop.domain.User;
import vn.hung.laptopshop.repository.CartDetailRepository;
import vn.hung.laptopshop.repository.CartRepository;
import vn.hung.laptopshop.repository.ProductRepository;

@Service
public class ProductService {
    private ProductRepository productRepository;
    private CartRepository cartRepository;
    private CartDetailRepository cartDetailRepository;
    private UserService userService;

    public ProductService(ProductRepository productRepository, CartRepository cartRepository,
            CartDetailRepository cartDetailRepository, UserService userService) {
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

    public void handleAddProductToCart(String email, long productId) {
        User user = this.userService.getUserByEmail(email);
        if (user != null) {
            Cart cart = this.cartRepository.findCartByUser(user);

            if (cart == null) {
                Cart otherCart = new Cart();
                otherCart.setUser(user);
                otherCart.setSum(1);
                cart = this.cartRepository.save(otherCart);
            }
            Product p = this.productRepository.findById(productId);

            CartDetail cartDetail = new CartDetail();
            cartDetail.setCart(cart);
            cartDetail.setProduct(p);
            cartDetail.setPrice(p.getPrice());
            cartDetail.setQuantity(1);
            this.cartDetailRepository.save(cartDetail);
        }
    }
}
