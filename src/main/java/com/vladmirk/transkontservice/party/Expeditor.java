package com.vladmirk.transkontservice.party;

import javax.persistence.Entity;

@Entity
public class Expeditor extends SimpleParty implements Party {

  private String code;

  public Expeditor() {
  }

  public Expeditor(String code, String name) {
    setCode(code);
    setName(name);
  }

  public String getCode() {
    return code;
  }
  public void setCode(String code) {
    this.code = code;
  }

}
