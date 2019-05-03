package com.vladmirk.transkontservice.party;

import javax.persistence.Entity;

@Entity
public class Expeditor extends Common {

  private String code;
  private String name;

  public Expeditor() {
  }

  public Expeditor(String code, String name) {
    this.code = code;
    this.name = name;
  }

  public String getCode() {
    return code;
  }
  public void setCode(String code) {
    this.code = code;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
}
