package com.example.oauth2auth.repository;

import com.example.oauth2auth.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Integer> {

    Optional<Client> findByClientId(String clientId);
}
