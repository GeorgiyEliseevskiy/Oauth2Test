package com.example.oauth2auth.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class ClientTokenSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int accessTokenTTL;

    private String type;

    @OneToOne
    private Client client;
}
