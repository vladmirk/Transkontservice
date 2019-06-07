package com.vladmirk.transkontservice.party;

import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

class PartyNameBindingException extends Exception {
  private PartyForm partyForm;
  private BindingResult bindingResult;
  private ModelAndView modelAndView;

  public PartyNameBindingException(PartyForm partyForm, BindingResult bindingResult, ModelAndView modelAndView) {
    this.partyForm = partyForm;
    this.bindingResult = bindingResult;
    this.modelAndView = modelAndView;
  }

  public BindingResult getBindingResult() {
    return bindingResult;
  }

  public ModelAndView getModelAndView() {
    return modelAndView;
  }

  public PartyForm getPartyForm() {
    return partyForm;
  }
}