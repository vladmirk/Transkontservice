package com.vladmirk.transkontservice.shipping;

import java.io.Serializable;

public class OrderForm implements Serializable {
  private ShippingRelease sR;

  public OrderForm(ShippingRelease sr) {
    this.sR = sr;
  }

  public ShippingRelease getSR() {
    return sR;
  }

  public void setSR(ShippingRelease sr) {
    this.sR = sr;
  }
}
