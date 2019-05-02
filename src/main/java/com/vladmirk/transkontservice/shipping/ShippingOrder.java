package com.vladmirk.transkontservice.shipping;

import lombok.Data;

import javax.persistence.Entity;
import java.util.Date;

@Entity
public @Data
class ShippingOrder extends Common {

  private String orderNumber;
  private Date orderDate;
  private String expeditorCode;
}
