package com.vladmirk.transkontservice.party;

import javax.persistence.MappedSuperclass;
import java.util.Objects;

@MappedSuperclass
public class SimpleParty extends Common implements Party {
  private String name;

  public SimpleParty() {
  }

  public SimpleParty(String name) {
    this.name = name;
  }

  @Override
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    SimpleParty that = (SimpleParty) o;
    return Objects.equals(getName(), that.getName());
  }
  @Override
  public int hashCode() {

    return Objects.hash(getName());
  }

  @Override
  public String toString() {
    return getName();
  }
}
