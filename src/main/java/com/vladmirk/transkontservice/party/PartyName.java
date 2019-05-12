package com.vladmirk.transkontservice.party;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class PartyName extends SimpleParty implements Party {

  @Enumerated(EnumType.STRING)
  private PartyType type;

  public PartyName() {
    super();
  }

  public PartyName(PartyType type, String name) {
    super(name);
    setType(type);
  }

  public PartyType getType() {
    return type;
  }

  public void setType(PartyType type) {
    this.type = type;
  }
}
