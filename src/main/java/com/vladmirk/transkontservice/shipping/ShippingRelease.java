package com.vladmirk.transkontservice.shipping;

import com.vladmirk.transkontservice.party.Common;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class ShippingRelease extends Common {
  private String load;
  private String unload, unloadCity, destination;
  @DateTimeFormat(pattern = "dd.MM.yyyy")
  @Temporal(TemporalType.DATE)
  private Date appointmentLoadDate;
  private BigDecimal calculatedCost;
  @ManyToOne
  @Valid
  private ShippingOrder order;
  @Enumerated(EnumType.STRING)
  private ShippingReleaseStatus status;

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
  public BigDecimal getCalculatedCost() {
    return calculatedCost;
  }
  public void setCalculatedCost(BigDecimal calculatedCost) {
    this.calculatedCost = calculatedCost;
  }
  public ShippingOrder getOrder() {
    return order;
  }
  public void setOrder(ShippingOrder order) {
    this.order = order;
  }
  public ShippingReleaseStatus getStatus() {
    return status;
  }
  public void setStatus(ShippingReleaseStatus status) {
    this.status = status;
  }
}
