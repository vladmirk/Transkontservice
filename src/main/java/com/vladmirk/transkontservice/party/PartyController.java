package com.vladmirk.transkontservice.party;

import com.vladmirk.transkontservice.shipping.DriverInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class PartyController {

  private PartyService partyService;
  @Autowired
  public PartyController(PartyService partyService) {
    this.partyService = partyService;
  }

  @GetMapping("/party/newDriverInfo")
  public ModelAndView newDriverInfo(ModelAndView model) {
    addDefaultPropertiesToDriverFormModel(model, new DriverInforForm());
    return model;
  }

  private void addDefaultPropertiesToDriverFormModel(ModelAndView model, DriverInforForm driverInforForm) {
    model.addObject("driverInfoForm", driverInforForm);
    model.setViewName("fragments/driverInfoForm :: driverInfoForm");
  }

  @PostMapping("/party/newDriverInfo")
  @ResponseBody
  public DriverInfo newDriverInfo(@Valid DriverInforForm driverInfoForm, BindingResult bindingResult, ModelAndView model) throws
      DriverInforBindingException {
    if (bindingResult.hasErrors())
      throw new DriverInforBindingException(driverInfoForm, bindingResult, model);

    DriverInfo driverInfo = updateDiverInfo(driverInfoForm.getDriverInfo());
    return driverInfo;
  }

  @ExceptionHandler(DriverInforBindingException.class)
  public ModelAndView handleBindResultException(DriverInforBindingException e) {
    ModelAndView m = e.getModelAndView();
    addDefaultPropertiesToDriverFormModel(m, e.getDriverInforForm());
    m.addObject("org.springframework.validation.BindingResult.driverInfoForm", e.getBindingResult());
    m.setStatus(HttpStatus.EXPECTATION_FAILED);
    return m;
  }

  private DriverInfo updateDiverInfo(DriverInfo driverInfo) {
    DriverInfo update = driverInfo.getId() == null ? new DriverInfo() : partyService.findDriverInforById(driverInfo.getId()).get();
    update.setName(driverInfo.getName());
    update.setSecondName(driverInfo.getSecondName());
    update.setSurname(driverInfo.getSurname());
    update.setPassport(driverInfo.getPassport());
    return partyService.saveDriverInfo(update);
  }
}

