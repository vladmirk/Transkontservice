package com.vladmirk.transkontservice.shipping;

public enum ShippingReleaseStatus {
  DRAFT("Драфт");


  private String desc;
  ShippingReleaseStatus(String desc) {
    this.desc = desc;
  }


  public String getDesc() {
    return desc;
  }

  @Override
  public String toString() {
    return getDesc();
  }
}
