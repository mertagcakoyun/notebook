package com.notebook.User.repository;

import com.notebook.User.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for performing CRUD operations on User entities.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
