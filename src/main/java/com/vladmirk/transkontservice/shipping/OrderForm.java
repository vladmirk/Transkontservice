package com.vladmirk.transkontservice.shipping;

import javax.validation.Valid;
import java.io.Serializable;

public class OrderForm implements Serializable {
  @Valid
  private ShippingRelease sR;
  //  @NotNull
  private String someString;

  public OrderForm(ShippingRelease sr) {
    this.sR = sr;
  }

  public ShippingRelease getSR() {
    return sR;
  }
  public void setSR(ShippingRelease sr) {
    this.sR = sr;
  }

  public String getSomeString() {
    return someString;
  }
  public void setSomeString(String someString) {
    this.someString = someString;
  }
}
