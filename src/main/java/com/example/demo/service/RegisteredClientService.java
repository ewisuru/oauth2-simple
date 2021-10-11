package com.example.demo.service;

import com.example.demo.data.RegisteredClient;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class RegisteredClientService {

  private static List<RegisteredClient> registeredClients =
      Collections.singletonList(
          RegisteredClient.builder()
              .clientId("client1")
              .clientSecret("1q2w3e4r")
              .scope("custom_scope")
              .redirectUri("http://localhost:8000/redirect")
              .build());

  public boolean validateAuthClient(String clientId, String scope, String redirectUri) {

    return registeredClients.stream()
        .anyMatch(
            client ->
                client.getClientId().equals(clientId)
                    && client.getScope().equals(scope)
                    && client.getRedirectUri().equals(redirectUri));
  }

  public Optional<RegisteredClient> getClientInfo(String clientId) {
    return registeredClients.stream()
        .filter(client -> client.getClientId().equals(clientId))
        .findFirst();
  }

  public boolean isValidClient(String clientId, String clientSecret) {
    return registeredClients.stream()
        .anyMatch(
            client ->
                client.getClientId().equals(clientId)
                    && client.getClientSecret().equals(clientSecret));
  }
}
