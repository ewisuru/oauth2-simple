package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TokenRequestDto {
  @NotNull String code;
  @NotNull String clientId;
  @NotNull String clientSecret;
  @NotNull String state;
}
