package com.vladmirk.transkontservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {


  @Autowired
  public HomeController() {

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

}
