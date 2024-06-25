package com.notebook.User.service;

import com.notebook.User.entity.User;
import com.notebook.User.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(String username, String password) {
        if (userRepository.findByUsername(username) != null) {
            throw new IllegalArgumentException("Username already exists");
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        return userRepository.save(user);
    }

    public User login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        throw new IllegalArgumentException("Invalid username or password");
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public User update(Long id, String username, String password) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setUsername(username);
            user.setPassword(password);
            return userRepository.save(user);
        }
        return null;
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
