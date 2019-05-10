package com.vladmirk.transkontservice.shipping;

import com.vladmirk.transkontservice.party.Common;
import com.vladmirk.transkontservice.party.Expeditor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class ShippingOrder extends Common {

  private String orderNumber;
  @DateTimeFormat(pattern = "dd.MM.yyyy")
  @Temporal(TemporalType.DATE)
  @NotNull
  private Date orderDate;
  @ManyToOne
  private Expeditor expeditor;

  public String getOrderNumber() {
    return orderNumber;
  }
  public void setOrderNumber(String orderNumber) {
    this.orderNumber = orderNumber;
  }
  public Date getOrderDate() {
    return orderDate;
  }
  public void setOrderDate(Date orderDate) {
    this.orderDate = orderDate;
  }

  public Expeditor getExpeditor() {
    return expeditor;
  }
  public void setExpeditor(Expeditor expeditor) {
    this.expeditor = expeditor;
  }
}
