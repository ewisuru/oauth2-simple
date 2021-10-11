package com.example.demo.service;

import com.example.demo.Session;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class SessionService {

  private static Map<String, Session> activeSessionMap = new HashMap<>();

  public String createCode(String userName) {
    String code = UUID.randomUUID().toString();
    activeSessionMap.put(
        code, Session.builder().code(code).userName(userName).issuedTime(new Date()).build());
    return code;
  }

  public boolean isValidCode(String code, String state) {
    Session session = activeSessionMap.get(code);
    if (session != null) {
      long codeAge = getTimeDiff(session.getIssuedTime(), new Date(), TimeUnit.MINUTES);
      return codeAge <= Session.CODE_VALID_DURATION_MINS;
    }
    return false;
  }

  public void discardSession(String code) {
    activeSessionMap.remove(code);
  }

  private long getTimeDiff(Date date1, Date date2, TimeUnit timeUnit) {
    long diffInMillies = date2.getTime() - date1.getTime();
    return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
  }
}
