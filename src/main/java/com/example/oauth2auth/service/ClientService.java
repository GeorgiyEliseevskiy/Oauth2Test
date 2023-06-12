package com.example.oauth2auth.service;

import com.example.oauth2auth.entity.*;
import com.example.oauth2auth.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ClientService implements RegisteredClientRepository {
    private final ClientRepository clientRepository;

    @Override
    @Transactional
    public void save(RegisteredClient registeredClient) {
        Client c = new Client();
        c.setClientId(registeredClient.getClientId());
        c.setSecret(registeredClient.getClientSecret());
        c.setAuthenticationMethods(
                registeredClient.getClientAuthenticationMethods()
                        .stream().map(a -> AuthenticationMethods.from(a, c))
                        .collect(Collectors.toList())
        );
        c.setGrantTypes(
                registeredClient.getAuthorizationGrantTypes().stream()
                        .map(g -> GrantType.from(g, c))
                        .collect(Collectors.toList())
        );
        c.setRedirectUrls(
                registeredClient.getRedirectUris().stream()
                        .map(m -> RedirectUrl.from(m, c))
                        .collect(Collectors.toList())
        );

        c.setScopes(
                registeredClient.getScopes().stream()
                        .map(s-> Scope.from(s, c))
                        .collect(Collectors.toList())
        );
        clientRepository.save(c);
    }

    @Override
    public RegisteredClient findById(String id) {
        var client = clientRepository.findById(Integer.parseInt(id));
        return client.map(Client::from)
                .orElseThrow(
                        () -> new RuntimeException("pizda")
                );

    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        var client = clientRepository.findByClientId(clientId);
        return client.map(Client::from)
                .orElseThrow(
                        () -> new RuntimeException("pizda")
                );

    }
}
