package com.Springfield.springbootMultiThreading;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

import com.Springfield.springbootMultiThreading.service.UserService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@EnableAsync
@SpringBootApplication
public class Application {

  @Autowired
  private UserService userService;


  public static void main (String[] args) {
    SpringApplication.run (Application.class, args);
  }

  @Bean
  CommandLineRunner initDatabase () {
    return args -> userService.saveUsers ();
  }

}

