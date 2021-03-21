package com.example.zj.androidstudy.model;

import java.io.Serializable;

/**
 * Created on 3/21/21.
 */

public class AuthorInfo implements Serializable {
  public String name;
  public int age;

  public AuthorInfo(String name, int age) {
    this.name = name;
    this.age = age;
  }
}
