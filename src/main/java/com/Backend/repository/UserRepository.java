package com.Backend.repository;

import com.Backend.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username); // Finds user by username

    User findByEmail(String email);  // Finds user by email

}