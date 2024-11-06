package com.example.demo.user.service.port;

public interface CustomMailSender {

  void send(String email, String title, String content);
}
