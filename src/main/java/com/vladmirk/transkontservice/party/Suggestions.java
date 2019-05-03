package com.vladmirk.transkontservice.party;

import java.util.ArrayList;
import java.util.List;

public class Suggestions {
  private List<Pairs> suggestions = new ArrayList<>();

  public Suggestions() {
  }

  public void add(String value, String data) {
    suggestions.add(new Pairs(value, data));
  }

  public List<Pairs> getSuggestions() {
    return suggestions;
  }
  public void setSuggestions(List<Pairs> suggestions) {
    this.suggestions = suggestions;
  }
  private class Pairs {
    private String value, data;

    public Pairs(String data, String value) {
      this.value = value;
      this.data = data;
    }
    public String getValue() {
      return value;
    }
    public void setValue(String value) {
      this.value = value;
    }
    public String getData() {
      return data;
    }
    public void setData(String data) {
      this.data = data;
    }
  }
}
