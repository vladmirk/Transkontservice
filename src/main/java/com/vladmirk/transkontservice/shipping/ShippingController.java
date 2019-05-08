package com.vladmirk.transkontservice.shipping;

import com.vladmirk.transkontservice.party.Expeditor;
import com.vladmirk.transkontservice.party.PartyService;
import com.vladmirk.transkontservice.party.Suggestions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

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
  public ModelAndView postOrder(@PathVariable Long id, @Valid OrderForm orderForm, ModelAndView model) {
    ShippingRelease existing = shippingReleaseService.findById(orderForm.getSR().getId()).get();
    existing.setAppointmentLoadDate(orderForm.getSR().getAppointmentLoadDate());
    existing = shippingReleaseService.save(existing);
    return editOrder(existing.getId(), model);
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
