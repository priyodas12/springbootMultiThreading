package com.Springfield.springbootMultiThreading.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Data
@Table (name = "users_info")
public class User {

  @Id
  @GeneratedValue (strategy = GenerationType.SEQUENCE)
  private Long id;
  private String name;
  private String email;
  private String gender;

}
