package com.example.demo.data;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public final class RegisteredClient {
  private String clientId;
  private String clientSecret;
  private String redirectUri;
  private String scope;

}
