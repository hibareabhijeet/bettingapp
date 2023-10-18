package com.assignment.dice.bettingapp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CheckHTTPResp {
  @LocalServerPort private int port;

  @Autowired private TestRestTemplate testRestTemplate;

  @Test
  public void shouldPassIfStringMatches() {
    assertEquals(
        "Hello world",
        testRestTemplate.getForObject("http://localhost:" + port + "/user/Abhi", String.class));
  }
}
