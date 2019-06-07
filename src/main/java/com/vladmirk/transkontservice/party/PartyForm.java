package com.vladmirk.transkontservice.party;

import javax.validation.Valid;
import java.io.Serializable;

public class PartyForm implements Serializable {

  @Valid
  private PartyName partyName;


  public PartyForm() {
  }

  public PartyForm(PartyName partyName) {
    this.partyName = partyName;
  }

  public PartyName getPartyName() {
    return partyName;
  }
  public void setPartyName(PartyName partyName) {
    this.partyName = partyName;
  }
}
