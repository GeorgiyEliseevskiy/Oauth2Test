package com.example.oauth2auth.repository;

import com.example.oauth2auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {


    Optional<User> findUserByUsername(String username);
}
