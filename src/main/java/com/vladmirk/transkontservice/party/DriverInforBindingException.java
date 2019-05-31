package com.vladmirk.transkontservice.party;

import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

class DriverInforBindingException extends Exception {
  private DriverInforForm driverInforForm;
  private BindingResult bindingResult;
  private ModelAndView modelAndView;

  public DriverInforBindingException(DriverInforForm driverInforForm, BindingResult bindingResult, ModelAndView modelAndView) {
    this.driverInforForm = driverInforForm;
    this.bindingResult = bindingResult;
    this.modelAndView = modelAndView;
  }

  public BindingResult getBindingResult() {
    return bindingResult;
  }

  public ModelAndView getModelAndView() {
    return modelAndView;
  }

  public DriverInforForm getDriverInforForm() {
    return driverInforForm;
  }
}