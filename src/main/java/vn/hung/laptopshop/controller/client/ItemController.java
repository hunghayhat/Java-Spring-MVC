package vn.hung.laptopshop.controller.client;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import vn.hung.laptopshop.domain.Cart;
import vn.hung.laptopshop.domain.CartDetail;
import vn.hung.laptopshop.domain.Product;
import vn.hung.laptopshop.domain.User;
import vn.hung.laptopshop.service.ProductService;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ItemController {
    private final ProductService productService;

    public ItemController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product/{id}")
    public String getProductDetail(Model model, @PathVariable long id) {
        Product currentProduct = productService.findProductById(id);
        model.addAttribute("currentProduct", currentProduct);
        return "client/product/detail";
    }

    @PostMapping("/add-product-to-cart/{id}")
    public String postAddProductToCart(@PathVariable long id, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        long productId = id;
        String email = (String) session.getAttribute("email");

        this.productService.handleAddProductToCart(email, productId, session, 1);

        return "redirect:/";
    }

    @PostMapping("/add-product-from-detail-view")
    public String handleAddProductFromViewDetail(@RequestParam("id") long id, @RequestParam("quantity") long quantity,
            HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String email = (String) session.getAttribute("email");
        this.productService.handleAddProductToCart(email, id, session, quantity);
        return "redirect:/product/" + id;
    }

    @GetMapping("/cart")
    public String getCart(Model model, HttpServletRequest request) {
        User currentUser = new User();
        HttpSession session = request.getSession(false);
        long id = (long) session.getAttribute("id");
        currentUser.setId(id);

        List<CartDetail> cartDetails = this.productService.getAllCartItems();
        Cart cart = this.productService.findCartByUser(currentUser);
        model.addAttribute("cartDetails", cartDetails);
        model.addAttribute("cart", cart);
        return "client/cart/show";
    }

    @PostMapping("cart/delete/{id}")
    public String postDeleteProductInCart(Model model, @PathVariable long id, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        this.productService.handleDeleteCartDetail(id, session);
        return "redirect:/cart";
    }

    @PostMapping("proceed-checkout")
    public String proceedCheckout(@ModelAttribute("cart") Cart cart) {
        List<CartDetail> cartDetails = cart == null ? new ArrayList<CartDetail>() : cart.getCartDetails();
        this.productService.updateCartBeforeCheckout(cartDetails);
        return "redirect:checkout";
    }

    @GetMapping("checkout")
    public String getCheckoutPage(Model model, HttpServletRequest request) {
        User currentUser = new User();
        HttpSession session = request.getSession(false);
        long id = (long) session.getAttribute("id");
        currentUser.setId(id);

        List<CartDetail> cartDetails = this.productService.getAllCartItems();
        Cart cart = this.productService.findCartByUser(currentUser);
        model.addAttribute("cartDetails", cartDetails);
        model.addAttribute("cart", cart);
        return "client/cart/checkout";
    }

    @GetMapping("/order-finish")
    public String finishOrderPage() {
        return "client/cart/finish";
    }

}
