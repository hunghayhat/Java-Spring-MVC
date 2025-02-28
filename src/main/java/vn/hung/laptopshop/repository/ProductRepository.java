package vn.hung.laptopshop.repository;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.hung.laptopshop.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product save(Product product);

    List<Product> findAll();

    Product findById(long id);

    void deleteById(long id);
}
