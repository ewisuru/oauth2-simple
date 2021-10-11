package com.example.demo.data;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class User {

  private String userName;
  private String password;

}
