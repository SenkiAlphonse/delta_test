package com.delta.test_webapi.controller;

import com.delta.test_webapi.service.DateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

  private DateService dateService;

  @Autowired
  public IndexController(DateService dateService){
    this.dateService = dateService;
  }

  @GetMapping("/")
  public String showIndexPage(Model model) {
    model.addAttribute("mindate", dateService.getMinDate());
    model.addAttribute("maxdate", dateService.getMaxDate());
    return "index";
  }
}
