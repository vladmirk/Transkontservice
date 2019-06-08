package com.vladmirk.transkontservice.party;

import com.vladmirk.transkontservice.shipping.DriverInfo;
import com.vladmirk.transkontservice.shipping.ShippingReleaseService;
import com.vladmirk.transkontservice.shipping.Transport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SuggestController {
  private ShippingReleaseService shippingReleaseService;
  private PartyService partyService;
  private static final String ORDERS = "orders";

  @Autowired
  public SuggestController(ShippingReleaseService shippingReleaseService, PartyService partyService) {
    this.shippingReleaseService = shippingReleaseService;
    this.partyService = partyService;
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

  @GetMapping("/suggest/party/{type:.+}")
  @ResponseBody
  public Suggestions getPartyNames(@PathVariable PartyType type,
      @RequestParam(name = "query", defaultValue = "", required = false) String partyName) {
    Suggestions suggestions = new Suggestions();
    if (partyName.length() > 1) {
      for (SimpleParty p : partyService.findPartyName(type, partyName)) {
        suggestions.add(String.valueOf(p.getId()), p.getName());
      }
    } else {
      suggestions.add("default", "default");
    }

    return suggestions;
  }


  @GetMapping("/suggest/unload")
  @ResponseBody
  public Suggestions getUnloads(@RequestParam(name = "query", defaultValue = "", required = false) String unload) {
    Suggestions suggestions = new Suggestions();
    if (unload.length() > 1) {
      for (SimpleParty p : partyService.findPartyName(PartyType.UNLOAD, unload)) {
        suggestions.add(String.valueOf(p.getId()), p.getName());
      }
    } else {
      suggestions.add("default", "default");
    }
    return suggestions;
  }

  @GetMapping("/suggest/unloadCity")
  @ResponseBody
  public Suggestions getUnloadCity(@RequestParam(name = "query", defaultValue = "", required = false) String unloadCity) {
    Suggestions suggestions = new Suggestions();
    for (SimpleParty p : partyService.findPartyName(PartyType.CITY, unloadCity)) {
      suggestions.add(String.valueOf(p.getId()), p.getName());
    }
    return suggestions;
  }

  @GetMapping("/suggest/destination")
  @ResponseBody
  public Suggestions getDestination(@RequestParam(name = "query", defaultValue = "", required = false) String dest) {
    Suggestions suggestions = new Suggestions();
    if (dest.length() > 1) {
      for (SimpleParty p : partyService.findPartyName(PartyType.DESTINATION, dest)) {
        suggestions.add(String.valueOf(p.getId()), p.getName());
      }
    } else {
      suggestions.add("default", "default");
    }
    return suggestions;
  }

  @GetMapping("/suggest/driverInfo")
  @ResponseBody
  public Suggestions getDriverInfo(@RequestParam(name = "query", defaultValue = "", required = false) String dInfo) {
    Suggestions suggestions = new Suggestions();
    if (dInfo.length() > 1) {
      for (DriverInfo p : partyService.findDriverInfo(dInfo)) {
        suggestions.add(String.valueOf(p.getId()), p.toString());
      }
    } else {
      suggestions.add("default", "default");
    }
    return suggestions;
  }

  @GetMapping("/suggest/transport")
  @ResponseBody
  public Suggestions getTransport(@RequestParam(name = "query", defaultValue = "", required = false) String transport) {
    Suggestions suggestions = new Suggestions();
    if (transport.length() > 1) {
      for (Transport t : partyService.findTransport(transport)) {
        suggestions.add(String.valueOf(t.getId()), t.toString());
      }
    } else {
      suggestions.add("-1", "default");
    }
    return suggestions;
  }
}
