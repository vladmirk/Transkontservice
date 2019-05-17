package com.vladmirk.transkontservice.party;

import com.vladmirk.transkontservice.shipping.DriverInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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
    model.addObject("driverInfoForm", new DriverInforForm());
    model.setViewName("fragments/driverInfoForm :: driverInfoForm");

    return model;
  }

  @PostMapping("/party/newDriverInfo")
  @ResponseBody
  public DriverInfo newDriverInfo(@Valid DriverInforForm driverInfoForm, BindingResult bindingResult) {
//    if (bindingResult.hasErrors())
//      return bindingResult;
    return updateDiverInfo(driverInfoForm.getDriverInfo());
  }

  private DriverInfo updateDiverInfo(DriverInfo driverInfo) {
    DriverInfo update = driverInfo.getId() == null ? new DriverInfo() : partyService.findDriverInforById(driverInfo.getId()).get();
    update.setName(driverInfo.getName());
    update.setSecondName(driverInfo.getSecondName());
    update.setSurname(driverInfo.getSurname());
    update.setPassport(driverInfo.getPassport());
    ResponseEntity<DriverInfo> responseEntity = new ResponseEntity<DriverInfo>(driverInfo, HttpStatus.CREATED);
    return partyService.saveDriverInfo(driverInfo);
  }
}
