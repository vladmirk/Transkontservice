package com.vladmirk.transkontservice.party;

import javax.persistence.Entity;

@Entity
public class Load extends SimpleParty implements Party {

  public Load() {
    super();
  }

  public Load(String name) {
    super(name);
  }


}
