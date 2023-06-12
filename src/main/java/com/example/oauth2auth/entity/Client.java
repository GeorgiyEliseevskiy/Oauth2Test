package com.example.oauth2auth.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

@Entity
@Table(name = "clients")
@Getter
@Setter
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "client_id")
    private String clientId;

    private String secret;

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private List<AuthenticationMethods> authenticationMethods;

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private List<GrantType> grantTypes;

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private List<RedirectUrl> redirectUrls;

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private List<Scope> scopes;

    @OneToOne(mappedBy = "client")
    private ClientTokenSetting clientTokenSetting;

    public static RegisteredClient from(Client client) {
        return RegisteredClient.withId(String.valueOf(client.getId()))
                .clientId(client.getClientId())
                .clientSecret(client.getSecret())
                .clientAuthenticationMethod(
                        new ClientAuthenticationMethod(
                        client.getAuthenticationMethods().get(0).getAuthenticationMethods()))
                .authorizationGrantTypes(
                        authorizationGrantTypes(client.getGrantTypes()))
                .scopes(scopes(client.getScopes()))
                .redirectUris(redirectUris(client.getRedirectUrls()))
                .tokenSettings(TokenSettings.builder()
                        .accessTokenTimeToLive(Duration.ofHours(client.clientTokenSetting.getAccessTokenTTL()))
                        .accessTokenFormat(new OAuth2TokenFormat(client.clientTokenSetting.getType()))
                        .build())
                .build();
    }

    private static Consumer<Set<AuthorizationGrantType>> authorizationGrantTypes(List<GrantType> grantTypes) {
        return s-> {
            for(GrantType g: grantTypes) {
                s.add(new AuthorizationGrantType(g.getGrantType()));
            }
        };
    }

    private static Consumer<Set<ClientAuthenticationMethod>> clientAuthenticationMethods(
            List<AuthenticationMethods> authenticationMethods
    ) {
        return m-> {
            for(AuthenticationMethods a: authenticationMethods) {
                m.add(new ClientAuthenticationMethod(a.getAuthenticationMethods()));
            }
        };
    }

    private static Consumer<Set<String>> scopes(List<Scope> scopes) {
        return s -> {
            for(Scope x : scopes) {
                s.add(x.getScope());
            }
        };
    }

    private static Consumer<Set<String>> redirectUris(List<RedirectUrl> uris) {
        return r -> {
            for(RedirectUrl u : uris) {
                r.add(u.getUrl());
            }
        };
    }

}
