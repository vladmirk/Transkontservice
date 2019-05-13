package com.vladmirk.transkontservice.shipping;

import com.vladmirk.transkontservice.party.Common;
import com.vladmirk.transkontservice.party.PartyName;
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
  @ManyToOne
  private PartyName load;
  @ManyToOne
  private PartyName unload;

  @ManyToOne
  private PartyName unloadCity;
  @ManyToOne
  private PartyName destination;

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

  public PartyName getLoad() {
    return load;
  }
  public void setLoad(PartyName load) {
    this.load = load;
  }
  public PartyName getUnload() {
    return unload;
  }
  public void setUnload(PartyName unload) {
    this.unload = unload;
  }
  public PartyName getUnloadCity() {
    return unloadCity;
  }
  public void setUnloadCity(PartyName unloadCity) {
    this.unloadCity = unloadCity;
  }
  public PartyName getDestination() {
    return destination;
  }
  public void setDestination(PartyName destination) {
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
