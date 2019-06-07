package com.vladmirk.transkontservice.party;

import com.vladmirk.transkontservice.shipping.DriverInfo;
import com.vladmirk.transkontservice.shipping.Transport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Arrays;

@Controller
@RequestMapping(value = "/party")
public class PartyController {

  private PartyService partyService;
  @Autowired
  public PartyController(PartyService partyService) {
    this.partyService = partyService;
  }


  @GetMapping("/newParty/{type:.+}")
  public ModelAndView newParty(@PathVariable String type, ModelAndView model) {
    addDefaultPropertiesToPartyFormModel(model, new PartyForm(new PartyName(PartyType.valueOf(type))));
    return model;
  }

  @PostMapping("/newParty")
  @ResponseBody
  public PartyName newParty(@Valid PartyForm partyForm, BindingResult bindingResult, ModelAndView model) throws PartyNameBindingException {
    if (bindingResult.hasErrors())
      throw new PartyNameBindingException(partyForm, bindingResult, model);

    PartyName partyName = updatePartyName(partyForm.getPartyName());
    return partyName;
  }


  @GetMapping("/newDriverInfo")
  public ModelAndView newDriverInfo(ModelAndView model) {
    addDefaultPropertiesToDriverFormModel(model, new DriverInforForm());
    return model;
  }

  private void addDefaultPropertiesToDriverFormModel(ModelAndView model, DriverInforForm driverInforForm) {
    model.addObject("driverInfoForm", driverInforForm);
    model.setViewName("fragments/modalFormItems :: driverInfoForm");
    addModals(model);
  }

  private void addDefaultPropertiesToPartyFormModel(ModelAndView model, PartyForm partyForm) {
    model.addObject("partyForm", partyForm);
    model.setViewName("fragments/modalFormItems :: partyNameForm");
    addModals(model);
  }

  private void addModals(ModelAndView model) {
    model.addObject("modals", Arrays.asList("appModal-1", "appModal-2"));
  }

  private void addDefaultPropertiesToTransportFormModel(ModelAndView model, TransportForm transportForm) {
    model.addObject("transportForm", transportForm);
    model.setViewName("fragments/modalFormItems :: transportForm");
    addModals(model);
  }

  @PostMapping("/newDriverInfo")
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

  private PartyName updatePartyName(PartyName partyName) {
    PartyName update = partyName.getId() == null ? new PartyName() : partyService.findPartyName(partyName.getId()).get();
    update.setType(partyName.getType());
    update.setName(partyName.getName());
    return partyService.savePartyName(update);
  }


  @GetMapping("/newTransport")
  public ModelAndView newTransport(ModelAndView model) {
    addDefaultPropertiesToTransportFormModel(model, new TransportForm());
    return model;
  }

  @PostMapping("/newTransport")
  @ResponseBody
  public Transport newTransport(@Valid TransportForm transportForm, BindingResult bindingResult, ModelAndView model) throws
      TransportBindingException {
    if (bindingResult.hasErrors())
      throw new TransportBindingException(transportForm, bindingResult, model);

//    DriverInfo driverInfo = updateDiverInfo(transportForm.getTransport());
//    return driverInfo;
    // TODO:
    return transportForm.getTransport();
  }
}

