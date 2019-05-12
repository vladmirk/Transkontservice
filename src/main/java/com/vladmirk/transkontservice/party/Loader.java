package com.vladmirk.transkontservice.party;

import javax.persistence.Entity;

@Entity
public class Loader extends SimpleParty implements Party {

  public Loader() {
  }

  public Loader(String name) {
    setName(name);
  }

  @Override
  public String toString() {
    return getName();
  }


}
