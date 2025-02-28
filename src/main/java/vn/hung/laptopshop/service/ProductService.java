package vn.hung.laptopshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.hung.laptopshop.domain.Product;
import vn.hung.laptopshop.repository.ProductRepository;

@Service
public class ProductService {
    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return this.productRepository.findAll();
    }

    public Product handleSaveProduct(Product product) {
          
        return this.productRepository.save(product);
    }

    public Product findProductById (long id) {
        return this.productRepository.findById(id);
    }

    public void deleteById (long id) {
        this.productRepository.deleteById(id);
    }
}
