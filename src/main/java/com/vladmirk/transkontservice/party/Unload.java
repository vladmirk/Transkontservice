package com.vladmirk.transkontservice.party;

import javax.persistence.Entity;

@Entity
public class Unload extends SimpleParty implements Party {

  public Unload() {
    super();
  }

  public Unload(String name) {
    super(name);
  }


}
