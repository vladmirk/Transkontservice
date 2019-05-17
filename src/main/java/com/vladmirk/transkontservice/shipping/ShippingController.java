package com.vladmirk.transkontservice.shipping;

import com.vladmirk.transkontservice.party.Expeditor;
import com.vladmirk.transkontservice.party.PartyName;
import com.vladmirk.transkontservice.party.PartyService;
import com.vladmirk.transkontservice.party.PartyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

import static org.springframework.util.StringUtils.isEmpty;

@Controller
public class ShippingController {
  private ShippingReleaseService shippingReleaseService;
  private PartyService partyService;
  private static final String ORDERS = "orders";

  @Autowired
  public ShippingController(ShippingReleaseService shippingReleaseService, PartyService partyService) {
    this.shippingReleaseService = shippingReleaseService;
    this.partyService = partyService;
  }

  @GetMapping(ORDERS)
  public ModelAndView orders(ModelAndView model) {
    model.addObject("shippingReleases", shippingReleaseService.findShippingReleases());
    model.setViewName(ORDERS);
    return model;
  }


  @GetMapping(ORDERS + "/{id}")
  public ModelAndView editOrder(@PathVariable Long id, ModelAndView model) {
    model.addObject("orderForm", new OrderForm(shippingReleaseService.findById(id).get()));
    model.setViewName("editOrder");

    return model;
  }

  @PostMapping(ORDERS + "/{id}")
  public String postOrder(@PathVariable Long id, @Valid OrderForm orderForm, BindingResult bindingResult, ModelAndView model) {
    if (bindingResult.hasErrors()) {
      return "editOrder";
    }
    ShippingRelease existing = updateSR(orderForm);

    return "redirect:/" + ORDERS;
  }

  private ShippingRelease updateSR(OrderForm of) {
    assert of.getSR() != null;
    ShippingRelease sr = shippingReleaseService.findById(of.getSR().getId()).get();
    assert sr != null;

    ShippingRelease from = of.getSR();
    ShippingOrder o = updateOrder(from.getOrder());
    sr.setOrder(o);
    sr.setLoad(updatePartyName(PartyType.LOAD, of.getSR().getLoad()));
    sr.setUnload(updatePartyName(PartyType.UNLOAD, of.getSR().getUnload()));
    sr.setUnloadCity(updatePartyName(PartyType.CITY, of.getSR().getUnloadCity()));
    sr.setDestination(updatePartyName(PartyType.DESTINATION, of.getSR().getDestination()));
    sr.setAppointmentLoadDate(from.getAppointmentLoadDate());
    sr.setCalculatedCost(from.getCalculatedCost());
    sr.setArrivalDate(from.getArrivalDate());
    sr.setLoadDate(from.getLoadDate());
    sr.setComment1(from.getComment1());
    sr.setComment2(from.getComment2());

    CarrierInfo infor = updateCarrierInfo(of);
    sr.setCarrierInfo(infor);

    sr = shippingReleaseService.save(sr);
    return sr;
  }
  private CarrierInfo updateCarrierInfo(OrderForm of) {
    assert of.getSR() != null;

    CarrierInfo carrierInfo = getCarrierInfo(of);
    // Driver Infor
    if (!isEmpty(of.getDriverInfoStr()) && of.getSR().getCarrierInfo().getDriverInfo() != null && of.getSR().getCarrierInfo().getDriverInfo()
        .getId() != null) {
      Optional<DriverInfo> driver = partyService.findDriverInforById(of.getSR().getCarrierInfo().getDriverInfo().getId());
      carrierInfo.setDriverInfo(driver.isPresent() ? driver.get() : null);
    } else
      carrierInfo.setDriverInfo(null);
    // Transport
    if (!isEmpty(of.getTransportStr()) && of.getSR().getCarrierInfo().getTransport() != null && of.getSR().getCarrierInfo().getTransport()
        .getId() != null) {
      Optional<Transport> transportById = partyService.findTransportById(of.getSR().getCarrierInfo().getTransport().getId());
      carrierInfo.setTransport(transportById.isPresent() ? transportById.get() : null);
    } else
      carrierInfo.setTransport(null);

    carrierInfo.setCost(of.getSR().getCarrierInfo().getCost());
    return shippingReleaseService.saveCarrierInfo(carrierInfo);
  }

  private CarrierInfo getCarrierInfo(OrderForm of) {
    if (of.getSR().getCarrierInfo() == null || of.getSR().getCarrierInfo().getId() == null)
      return shippingReleaseService.createNewCarrierInfo();
    else {
      Optional<CarrierInfo> carrierInfoById = shippingReleaseService.findCarrierInfoById(of.getSR().getCarrierInfo().getId());
      if (carrierInfoById.isPresent())
        return carrierInfoById.get();
      else
        throw new RuntimeException("Cannot find Carrier by id " + of.getSR().getCarrierInfo().getId());
    }
  }

  private PartyName updatePartyName(PartyType type, PartyName partyName) {
    return partyService.savePartyNameOrCreateNew(type, partyName);
  }


  private ShippingOrder updateOrder(ShippingOrder from) {
    Optional<ShippingOrder> optionalOrder = shippingReleaseService.findOrderById(from.getId());
    if (!optionalOrder.isPresent() || optionalOrder.get() == null)
      return from;
    ShippingOrder o = optionalOrder.get();
    if (from.getOrderNumber() != null)
      o.setOrderNumber(from.getOrderNumber());
    o.setOrderDate(from.getOrderDate());

    if (isEmpty(from.getExpeditor().getCode()) && isEmpty(from.getExpeditor().getName()))
      o.setExpeditor(null);
    else {
      Expeditor expeditor = partyService.findExpeditor(from.getExpeditor().getCode(), from.getExpeditor().getName());
      if (expeditor != null)
        o.setExpeditor(expeditor);
    }

    return shippingReleaseService.saveOrder(o);
  }


}
