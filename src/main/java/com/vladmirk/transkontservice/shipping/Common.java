package com.vladmirk.transkontservice.shipping;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Common {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }
}
