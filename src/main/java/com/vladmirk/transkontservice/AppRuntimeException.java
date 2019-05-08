package com.vladmirk.transkontservice;

public class AppRuntimeException extends RuntimeException {
  private Exceptions code;
  public AppRuntimeException(Exceptions code) {
    super(code.name());
  }
}
