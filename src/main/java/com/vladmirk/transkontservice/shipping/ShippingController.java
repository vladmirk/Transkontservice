package com.vladmirk.transkontservice.shipping;

import com.vladmirk.transkontservice.party.Expeditor;
import com.vladmirk.transkontservice.party.PartyService;
import com.vladmirk.transkontservice.party.Suggestions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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

    sr.setAppointmentLoadDate(from.getAppointmentLoadDate());
    sr = shippingReleaseService.save(sr);
    return sr;
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


  @GetMapping("/suggest/expeditors")
  @ResponseBody
  public Suggestions getExpeditors(@RequestParam(name = "query", defaultValue = "", required = false) String expeditor) {
    Suggestions suggestions = new Suggestions();
    if (expeditor.length() > 1) {
      for (Expeditor e : partyService.findExpeditors(expeditor)) {
        suggestions.add(e.getCode(), e.getName());
      }
    } else {
      suggestions.add("default", "default");
    }

    return suggestions;
  }
}
