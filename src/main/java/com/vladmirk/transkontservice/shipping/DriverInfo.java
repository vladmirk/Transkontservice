package com.vladmirk.transkontservice.shipping;

import com.vladmirk.transkontservice.party.Common;
import org.springframework.util.StringUtils;

import javax.persistence.Entity;

@Entity
public class DriverInfo extends Common {
  private String name;
  private String secondName;
  private String surname;
  private String passport;

  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getSecondName() {
    return secondName;
  }
  public void setSecondName(String secondName) {
    this.secondName = secondName;
  }
  public String getSurname() {
    return surname;
  }
  public void setSurname(String surname) {
    this.surname = surname;
  }
  public String getPassport() {
    return passport;
  }
  public void setPassport(String passport) {
    this.passport = passport;
  }
  @Override
  public String toString() {
    String passport = StringUtils.isEmpty(getPassport()) ? "" : " (" + getPassport() + ")";
    return getSurname() + " " + getName() + " " + getSecondName() + passport;
  }
}