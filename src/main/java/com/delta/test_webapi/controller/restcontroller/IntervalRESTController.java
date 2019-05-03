package com.delta.test_webapi.controller.restcontroller;

import com.delta.test_webapi.service.DateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IntervalRESTController {

  private DateService dateService;
  private static Logger logger = LoggerFactory.getLogger(IntervalRESTController.class);

  @Autowired
  public IntervalRESTController(DateService dateService){
    this.dateService = dateService;
  }

  @GetMapping("/api/holidays/count")
  public ResponseEntity countHolidays(@RequestParam(value = "startdate") String startDate,
                                      @RequestParam(value = "enddate") String endDate) {
    return new ResponseEntity<>(dateService.countHolidays(startDate, endDate), HttpStatus.OK);
  }

  @GetMapping("/api/workdays/count")
  public ResponseEntity countWorkdays(@RequestParam(value = "startdate") String startDate,
                                      @RequestParam(value = "enddate") String endDate) {
    return new ResponseEntity<>(dateService.countWorkdays(startDate, endDate), HttpStatus.OK);
  }

  @GetMapping("/api/days/count")
  public ResponseEntity countHolidaysAndWorkdays(@RequestParam(value = "startdate") String startDate,
                                                 @RequestParam(value = "enddate") String endDate) {
    return new ResponseEntity<>(dateService.countDays(startDate, endDate), HttpStatus.OK);
  }

}
