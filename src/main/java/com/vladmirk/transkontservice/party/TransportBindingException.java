package com.vladmirk.transkontservice.party;

import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

class TransportBindingException extends Exception {
  private TransportForm transportForm;
  private BindingResult bindingResult;
  private ModelAndView modelAndView;

  public TransportBindingException(TransportForm transportForm, BindingResult bindingResult, ModelAndView modelAndView) {
    this.transportForm = transportForm;
    this.bindingResult = bindingResult;
    this.modelAndView = modelAndView;
  }

  public BindingResult getBindingResult() {
    return bindingResult;
  }

  public ModelAndView getModelAndView() {
    return modelAndView;
  }

  public TransportForm getTransportForm() {
    return transportForm;
  }
}