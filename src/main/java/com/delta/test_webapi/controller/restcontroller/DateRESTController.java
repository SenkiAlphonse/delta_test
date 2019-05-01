package com.delta.test_webapi.controller.restcontroller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class DateRESTController {

  @GetMapping("/api/holidays")
  public ResponseEntity isDayOff(@RequestParam(value = "date") String date){
    return null;
  }

  @PutMapping("/api/holidays")
  public ResponseEntity setDayOff(@RequestParam(value = "date") String date){
    return null;
  }
}
