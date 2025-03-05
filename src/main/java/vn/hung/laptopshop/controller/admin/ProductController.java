package vn.hung.laptopshop.controller.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import vn.hung.laptopshop.domain.Product;
import vn.hung.laptopshop.service.ProductService;
import vn.hung.laptopshop.service.UploadService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;

@Controller
public class ProductController {
    private final UploadService uploadService;
    private final ProductService productService;

    public ProductController(UploadService uploadService, ProductService productService) {
        this.uploadService = uploadService;
        this.productService = productService;
    }

    @GetMapping("/admin/product")
    public String getProductDashboard(Model model) {
        List<Product> products = this.productService.getAllProducts();
        model.addAttribute("newProducts", products);
        return "admin/product/show";
    }

    @GetMapping("/admin/product/create")
    public String createProduct(Model model) {
        model.addAttribute("newProduct", new Product());
        return "admin/product/create";
    }

    @PostMapping("/admin/product/create")
    public String postCreateProduct(Model model,
            @ModelAttribute("newProduct") @Valid Product laptop,
            BindingResult newProductBindingResult,
            @RequestParam("userFile") MultipartFile file) {
        List<FieldError> errors = newProductBindingResult.getFieldErrors();
        for (FieldError error : errors) {
            System.out.println(">>>>>>>>>>>" + error.getField() + " - " + error.getDefaultMessage());
        }

        if (newProductBindingResult.hasErrors())
            return "/admin/product/create";

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
        return "admin/product/detail";
    }

    @GetMapping("/admin/product/update/{id}")
    public String getUpdateProduct(Model model, @PathVariable long id) {
        Product currentProduct = this.productService.findProductById(id);
        model.addAttribute("currentProduct", currentProduct);
        return "admin/product/update";
    }

    @PostMapping("/admin/product/update")
    public String postUpdateProduct(Model model,
            @ModelAttribute("currentProduct") Product laptop,
            @RequestParam("userFile") MultipartFile file) {
        Product currentProduct = this.productService.findProductById(laptop.getId());

        model.addAttribute("currentProduct", currentProduct);
        if (currentProduct != null) {
            currentProduct.setName(laptop.getName());
            currentProduct.setPrice(laptop.getPrice());
            currentProduct.setDetailDesc(laptop.getDetailDesc());
            currentProduct.setShortDesc(laptop.getShortDesc());
            currentProduct.setQuantity(laptop.getQuantity());
            currentProduct.setFactory(laptop.getFactory());
            currentProduct.setTarget(laptop.getTarget());
            if (file != null && !file.isEmpty()) {
                String imagePath = this.uploadService.handleSaveUploadFile(file, "product");
                currentProduct.setImage(imagePath);
            }

            this.productService.handleSaveProduct(currentProduct);
        }
        return "redirect:/admin/product";
    }

    @GetMapping("admin/product/delete/{id}")
    public String getDeleteProductPage(Model model, @PathVariable long id) {
        Product currentProduct = this.productService.findProductById(id);
        model.addAttribute("currentProduct", currentProduct);
        return "admin/product/delete";
    }

    @PostMapping("/admin/product/delete")
    public String postDelete(Model model,
            @ModelAttribute("currentProduct") Product laptop) {
        this.productService.deleteById(laptop.getId());
        return "redirect:/admin/product";
    }

}
