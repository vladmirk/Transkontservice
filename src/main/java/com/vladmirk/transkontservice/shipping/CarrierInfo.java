package com.vladmirk.transkontservice.shipping;

import com.vladmirk.transkontservice.party.Common;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class CarrierInfo extends Common {
  @ManyToOne
  private DriverInfo driverInfo;
  @ManyToOne
  private Transport transport;

  public DriverInfo getDriverInfo() {
    return driverInfo;
  }
  public void setDriverInfo(DriverInfo driverInfo) {
    this.driverInfo = driverInfo;
  }
  public Transport getTransport() {
    return transport;
  }
  public void setTransport(Transport transport) {
    this.transport = transport;
  }
}
