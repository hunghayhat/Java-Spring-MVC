package vn.hung.laptopshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.hung.laptopshop.domain.User;
import vn.hung.laptopshop.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    public List<User> getAllUsersByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    public User handleSaveUser(User user) {
        User hung = this.userRepository.save(user);
        return hung;
    }

    public User findById(long id) {
        return this.userRepository.findById(id);
    }

    public void deleteById (long id) {
        this.userRepository.deleteById(id);
    }
}
