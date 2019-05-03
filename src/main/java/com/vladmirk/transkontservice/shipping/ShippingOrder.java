package com.vladmirk.transkontservice.shipping;

import com.vladmirk.transkontservice.party.Common;
import com.vladmirk.transkontservice.party.Expeditor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
public class ShippingOrder extends Common {

  private String orderNumber;
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
