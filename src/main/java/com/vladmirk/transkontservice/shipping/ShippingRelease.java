package com.vladmirk.transkontservice.shipping;

import com.vladmirk.transkontservice.party.Common;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class ShippingRelease extends Common {
  private String load, unload, unloadCity, destination;
  private Date appointmentLoadDate;
  private BigDecimal calulatedCost;
  @ManyToOne
  private ShippingOrder order;

  public ShippingRelease() {
  }

  public String getLoad() {
    return load;
  }
  public void setLoad(String load) {
    this.load = load;
  }
  public String getUnload() {
    return unload;
  }
  public void setUnload(String unload) {
    this.unload = unload;
  }
  public String getUnloadCity() {
    return unloadCity;
  }
  public void setUnloadCity(String unloadCity) {
    this.unloadCity = unloadCity;
  }
  public String getDestination() {
    return destination;
  }
  public void setDestination(String destination) {
    this.destination = destination;
  }
  public Date getAppointmentLoadDate() {
    return appointmentLoadDate;
  }
  public void setAppointmentLoadDate(Date appointmentLoadDate) {
    this.appointmentLoadDate = appointmentLoadDate;
  }
  public BigDecimal getCalulatedCost() {
    return calulatedCost;
  }
  public void setCalulatedCost(BigDecimal calulatedCost) {
    this.calulatedCost = calulatedCost;
  }
  public ShippingOrder getOrder() {
    return order;
  }
  public void setOrder(ShippingOrder order) {
    this.order = order;
  }
}
