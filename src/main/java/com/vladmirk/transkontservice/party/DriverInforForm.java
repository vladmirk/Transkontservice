package com.vladmirk.transkontservice.party;

import com.vladmirk.transkontservice.shipping.DriverInfo;

import javax.validation.Valid;
import java.io.Serializable;

public class DriverInforForm implements Serializable {

  @Valid
  private DriverInfo driverInfo;

  public DriverInforForm() {
  }

  public DriverInforForm(DriverInfo driverInfo) {
    this.driverInfo = driverInfo;
  }

  public DriverInfo getDriverInfo() {
    return driverInfo;
  }
  public void setDriverInfo(DriverInfo driverInfo) {
    this.driverInfo = driverInfo;
  }
}
