package com.vladmirk.transkontservice.party;

import com.vladmirk.transkontservice.shipping.Transport;

import javax.validation.Valid;
import java.io.Serializable;

public class TransportForm implements Serializable {

  @Valid
  private Transport transport;

  public TransportForm() {
  }

  public TransportForm(Transport transport) {
    this.transport = transport;
  }

  public Transport getTransport() {
    return transport;
  }
  public void setTransport(Transport transport) {
    this.transport = transport;
  }
}
