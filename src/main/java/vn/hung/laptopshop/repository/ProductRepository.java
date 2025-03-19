package vn.hung.laptopshop.repository;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.hung.laptopshop.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @SuppressWarnings({ "null", "unchecked" })
    Product save(Product product);

    @SuppressWarnings("null")
    List<Product> findAll();

    Product findById(long id);

    void deleteById(long id);

    @SuppressWarnings("null")
    Page<Product> findAll(Pageable page);
}
