package com.example.oauth2auth.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

@Entity
@Table(name = "authentication_methods")
@Setter
@Getter
public class AuthenticationMethods {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column (name = "authentication_methods")
    private String authenticationMethods;

    @ManyToOne
    private Client client;

    public static AuthenticationMethods from(ClientAuthenticationMethod authenticationMethod,
                                             Client client) {
        AuthenticationMethods a = new AuthenticationMethods();
        a.setAuthenticationMethods(authenticationMethod.getValue());
        a.setClient(client);
        return a;
    }


}
