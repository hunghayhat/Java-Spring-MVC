package vn.hung.laptopshop.controller.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import vn.hung.laptopshop.domain.Product;
import vn.hung.laptopshop.service.ProductService;
import vn.hung.laptopshop.service.UploadService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;



@Controller
public class ProductController {
    private final UploadService uploadService;
    private final ProductService productService;

    public ProductController(UploadService uploadService, ProductService productService){
        this.uploadService = uploadService;
        this.productService = productService;
    }

    
    @GetMapping("/admin/product")
    public String getProductDashboard(Model model) {
        List<Product> products = this.productService.getAllProducts();
        model.addAttribute("newProducts", products);
        return "/admin/product/show";
    }

    @GetMapping("/admin/product/create")
    public String createProduct(Model model) {
        model.addAttribute("newProduct", new Product());
        return "admin/product/create";
    }
    
    @PostMapping("/admin/product/create")
    public String postCreateProduct(Model model,
    @ModelAttribute("newProduct") Product laptop,
    @RequestParam("userFile") MultipartFile file) {
        String productImage = this.uploadService.handleSaveUploadFile(file, "product");
        laptop.setImage(productImage);
        this.productService.handleSaveProduct(laptop);
        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/{id}")
    public String getProductDetailPage(Model model, @PathVariable long id) {
        model.addAttribute("id", id);
        Product product = this.productService.findProductById(id);
        model.addAttribute("product", product);
        return "/admin/product/detail";
    }
    
    
    
}
