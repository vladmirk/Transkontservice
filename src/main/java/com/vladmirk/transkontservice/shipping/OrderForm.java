package com.vladmirk.transkontservice.shipping;

import javax.validation.Valid;
import java.io.Serializable;

import static org.springframework.util.StringUtils.isEmpty;

public class OrderForm implements Serializable {
  @Valid
  private ShippingRelease sR;
  //  @NotNull
  private String someString;

  private String driverInfoStr;
  private String transportStr;

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

  public String getDriverInfoStr() {
    if (!isEmpty(driverInfoStr))
      return driverInfoStr;
    if (getSR() != null && getSR().getCarrierInfo() != null && getSR().getCarrierInfo().getDriverInfo() != null)
      return getSR().getCarrierInfo().getDriverInfo().toString();
    return "";
  }

  public void setDriverInfoStr(String driverInfoStr) {
    this.driverInfoStr = driverInfoStr;
  }

  public String getTransportStr() {
    if (!isEmpty(transportStr))
      return transportStr;
    if (getSR() != null && getSR().getCarrierInfo() != null && getSR().getCarrierInfo().getTransport() != null)
      return getSR().getCarrierInfo().getTransport().toString();
    return "";
  }

  public void setTransportStr(String transportStr) {
    this.transportStr = transportStr;
  }
}
