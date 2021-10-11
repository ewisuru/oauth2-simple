package com.example.demo.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TokenResponseDto {
  private String access_token;
  private String refresh_token;
}
