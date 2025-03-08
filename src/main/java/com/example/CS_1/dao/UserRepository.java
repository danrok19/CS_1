package com.example.CS_1.dao;

import com.example.CS_1.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    <Optional> User findByUsername(String username);
}
