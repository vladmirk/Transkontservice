package com.vladmirk.transkontservice.shipping;

import com.vladmirk.transkontservice.party.Common;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@Entity
public class CarrierInfo extends Common {
  @ManyToOne
  private DriverInfo driverInfo;
  @ManyToOne
  private Transport transport;
  private BigDecimal cost;


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
  public BigDecimal getCost() {
    return cost;
  }
  public void setCost(BigDecimal cost) {
    this.cost = cost;
  }
}
