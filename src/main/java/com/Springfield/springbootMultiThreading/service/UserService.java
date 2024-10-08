package com.Springfield.springbootMultiThreading.service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.Springfield.springbootMultiThreading.dao.UserDao;
import com.Springfield.springbootMultiThreading.model.User;
import com.github.javafaker.Faker;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class UserService {

  @Autowired
  private UserDao userDao;

  @Autowired
  private Faker faker;

  @Autowired
  private Executor taskExecutor;

  public List<CompletableFuture<User>> saveUsers () {
    List<User> userList = getUserList ();
    var startTime = System.nanoTime ();
    List<CompletableFuture<User>> futureList = saveDataAsync (userList);
    log.info ("Thread:: {}", Thread.currentThread ().getName ());
    var endTime = System.nanoTime ();
    log.info ("Time taken:: {}", (endTime - startTime));
    return futureList;
  }

  @Async
  private List<User> getUserList () {
    var genderList = List.of ("Male", "Female", "Others");
    return IntStream.rangeClosed (1, 10).mapToObj (i -> {
      var genderPicker = new Random ().nextInt (0, 3);
      var saveUser = new User ();
      saveUser.setName (faker.funnyName ().name ());
      saveUser.setEmail (faker.internet ().emailAddress ());
      saveUser.setGender (genderList.get (genderPicker));
      return saveUser;
    }).collect (Collectors.toList ());
  }

  public List<CompletableFuture<User>> saveDataAsync (List<User> userList) {
    List<CompletableFuture<User>> futureList = new ArrayList<> ();
    for (User user : userList) {
      User savedUser = userDao.save (user);
      CompletableFuture<User> future = CompletableFuture.completedFuture (savedUser);
      futureList.add (future);
    }
    return futureList;
  }

  public User saveUser (User user) {
    log.info ("saveUser:: %s".formatted (LocalDateTime.now ()));
    return userDao.save (user);
  }

  public Page<User> getPaginatedUsers (String page, String size) {
    int pageNum = Integer.parseInt (page.substring (5));//page=1
    int pageSize = 10;
    Pageable pageable = PageRequest.of (pageNum, pageSize, Sort.by ("name").ascending ());
    log.info ("getPaginatedUsers:: %s".formatted (pageable));
    return userDao.findAll (pageable);
  }
}
