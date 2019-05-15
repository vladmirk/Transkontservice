package com.vladmirk.transkontservice;

import com.vladmirk.transkontservice.party.Expeditor;
import com.vladmirk.transkontservice.party.PartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {


  private PartyService partyService;
  @Autowired
  public HomeController(PartyService partyService) {

    this.partyService = partyService;
  }

  @GetMapping("/login")
  public String login() {
    return "login";
  }

  @GetMapping("/logout")
  public String logout() {
    return "login";
  }

  @GetMapping("/")
  public String index() {
    return "index";
  }


  @PostMapping("/testForm")
  public String testForm(@RequestParam("suggestTest") String suggest, ModelAndView model) {

    return "index";
  }


  // Test
  @GetMapping("/testModal")
  public String testModal() {
    return "modalForm";
  }

  @PostMapping("/testModal")
  @ResponseBody
  public Expeditor postModal(@RequestParam("someModal") String s) {
    Expeditor expeditor = partyService.findExpeditorByCode("00-00006612");
    return expeditor;
  }

}
