package vn.hung.laptopshop.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.hung.laptopshop.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @SuppressWarnings({ "null", "unchecked" })
    User save(User hung);

    List<User> findByEmail(String email);

    @SuppressWarnings("null")
    List<User> findAll();

    User findById(long id);

    void deleteById(long id);

    boolean existsByEmail(String email);

    User findUserByEmail(String email);

    @SuppressWarnings("null")
    Page<User> findAll(Pageable pageable);

}
