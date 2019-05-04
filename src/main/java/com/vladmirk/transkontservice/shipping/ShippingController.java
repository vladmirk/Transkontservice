package com.vladmirk.transkontservice.shipping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ShippingController {
  private ShippingReleaseService shippingReleaseService;
  private static final String ORDERS = "orders";

  @Autowired
  public ShippingController(ShippingReleaseService shippingReleaseService) {
    this.shippingReleaseService = shippingReleaseService;
  }

  @GetMapping(ORDERS)
  public ModelAndView orders(ModelAndView model) {
    model.addObject("shippingReleases", shippingReleaseService.findShippingReleases());
    model.setViewName(ORDERS);
    return model;
  }


  @GetMapping(ORDERS + "/{id}")
  public ModelAndView editOrder(@PathVariable Long id, ModelAndView model) {
    model.addObject("sr", shippingReleaseService.findById(id).get());
    model.setViewName("editOrder");
    return model;
  }
}
