package com.example.demo.service;

import com.example.demo.data.User;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserService {
  private static List<User> users =
      Arrays.asList(
          User.builder().userName("user1").password("pass1").build(),
          User.builder().userName("user2").password("pass2").build(),
          User.builder().userName("user3").password("pass3").build());

  public boolean isValidUser(String userName, String password) {
    return users.stream()
        .anyMatch(
            user -> user.getUserName().equals(userName) && user.getPassword().equals(password));
  }
}
