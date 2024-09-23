package com.Springfield.springbootMultiThreading.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Springfield.springbootMultiThreading.model.User;
import com.Springfield.springbootMultiThreading.service.UserService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@CrossOrigin (origins = "http://localhost:4200")
@RequestMapping ("/api/v1")
@RestController
public class UserController {

  @Autowired
  private UserService userService;

  @PostMapping ("/user")
  public ResponseEntity<User> saveUser (@RequestBody User user) {
    return ResponseEntity.ok (userService.saveUser (user));
  }

  @GetMapping ("/users/{page}/{size}")
  public ResponseEntity<Page<User>> fetchUsers (
      @PathVariable ("page") String page,
      @PathVariable ("size") String size
                                               ) {
    log.info ("fetchUsers: {},{}", page, size);
    return ResponseEntity.ok (userService.getPaginatedUsers (page, size));
  }
}
