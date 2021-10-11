package com.example.demo;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Session {

  public static long CODE_VALID_DURATION_MINS = 1;

  private String code;
  private String userName;
  private Date issuedTime;
}
