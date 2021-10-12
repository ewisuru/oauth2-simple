package com.example.demo.controller;

import com.example.demo.data.RegisteredClient;
import com.example.demo.dto.LoginRequestDto;
import com.example.demo.dto.TokenRequestDto;
import com.example.demo.dto.TokenResponseDto;
import com.example.demo.service.RegisteredClientService;
import com.example.demo.service.SessionService;
import com.example.demo.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class AuthController {

  private RegisteredClientService registeredClientService;
  private UserService userService;
  private SessionService sessionService;

  @GetMapping("/authorize")
  public RedirectView authorize(
      @RequestParam("client_id") @NotNull String clientId,
      @RequestParam("redirect_uri") @NotNull String redirectUri,
      @RequestParam("scope") @NotNull String scope,
      @RequestParam("response_type") @NotNull String responseType,
      @RequestParam("state") @NotNull String state) {
    boolean isValid = registeredClientService.validateAuthClient(clientId, scope, redirectUri);
    if (isValid) {
      return new RedirectView("http://localhost:8080/login?client_id=" + clientId);
    }
    return new RedirectView("http://localhost:8000/error?reason=invalid_client");
  }

  @PostMapping("/login")
  public RedirectView login(@Validated @RequestBody LoginRequestDto loginRequestDto) {
    boolean isValidUser =
        userService.isValidUser(loginRequestDto.getUserName(), loginRequestDto.getPassword());
    if (isValidUser) {
      String code = sessionService.createCode(loginRequestDto.getUserName());
      Optional<RegisteredClient> client =
          registeredClientService.getClientInfo(loginRequestDto.getClientId());
      if (client.isPresent()) {
        return new RedirectView(
            client.get().getRedirectUri()
                + "?code="
                + code
                + "&state="
                + loginRequestDto.getState());
      }
    }
    return new RedirectView("\"http://localhost:8000/error?reason=invalid_login_credentials");
  }

  @PostMapping("/token")
  public ResponseEntity token(@RequestBody @Validated TokenRequestDto tokenRequestDto) {
    boolean isValidClient =
        registeredClientService.isValidClient(
            tokenRequestDto.getClientId(), tokenRequestDto.getClientSecret());
    boolean isValidCode =
        sessionService.isValidCode(tokenRequestDto.getCode(), tokenRequestDto.getState());
    if (isValidClient && isValidCode) {
      sessionService.discardSession(tokenRequestDto.getCode());
      return new ResponseEntity(
          TokenResponseDto.builder()
              .access_token(UUID.randomUUID().toString())
              .refresh_token(UUID.randomUUID().toString())
              .build(),
          HttpStatus.OK);
    }
    return new ResponseEntity("Invalid token request", HttpStatus.UNAUTHORIZED);
  }
}
