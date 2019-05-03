package com.delta.test_webapi.controller.restcontroller;

import com.delta.test_webapi.model.DayCountDto;
import com.delta.test_webapi.model.HolidayCountDto;
import com.delta.test_webapi.model.WorkdayCountDto;
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
    logger.debug("/api/holidays/count GET countHolidays() called with: " + startDate + " as startDate, and " + endDate + " as endDate");
    try {
      valiDates(startDate, endDate);

      HolidayCountDto holidayCountDto = dateService.countHolidays(startDate, endDate);
      logger.debug("/api/holidays/count GET countHolidays() response: " + holidayCountDto.holidayCount);
      return new ResponseEntity<>(holidayCountDto, HttpStatus.OK);
    }
    catch(IllegalArgumentException e){
      logger.debug("/api/holidays GET isHoliday response: " + e.getMessage());
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    catch (Exception e) {
      logger.debug("/api/holidays GET isHoliday response: " + e.getMessage());
      return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/api/workdays/count")
  public ResponseEntity countWorkdays(@RequestParam(value = "startdate") String startDate,
                                      @RequestParam(value = "enddate") String endDate) {
    logger.debug("/api/workdays/count GET countWorkdays() called with: " + startDate + " as startDate, and " + endDate + " as endDate");

    try {
      valiDates(startDate, endDate);

      WorkdayCountDto workdayCountDto = dateService.countWorkdays(startDate, endDate);
      logger.debug("/api/workdays/count GET countWorkdays() response: " + workdayCountDto.workdayCount);
      return new ResponseEntity<>(workdayCountDto, HttpStatus.OK);
    }
    catch(IllegalArgumentException e){
      logger.debug("/api/holidays GET isHoliday response: " + e.getMessage());
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    catch (Exception e) {
      logger.debug("/api/holidays GET isHoliday response: " + e.getMessage());
      return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }


  @GetMapping("/api/days/count")
  public ResponseEntity countHolidaysAndWorkdays(@RequestParam(value = "startdate") String startDate,
                                                 @RequestParam(value = "enddate") String endDate) {
    logger.debug("/api/days/count GET countDays() called with: " + startDate + " as startDate, and " + endDate + " as endDate");
    try {
      valiDates(startDate, endDate);
      DayCountDto dayCountDto = dateService.countDays(startDate, endDate);
      logger.debug("/api/days/count GET countWorkdays() response: holidayCount = " + dayCountDto.holidayCount + ", workdayCount = " + dayCountDto.workdayCount);
      return new ResponseEntity<>(dayCountDto, HttpStatus.OK);
    }
      catch(IllegalArgumentException e){
      logger.debug("/api/holidays GET isHoliday response: " + e.getMessage());
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
      catch (Exception e) {
      logger.debug("/api/holidays GET isHoliday response: " + e.getMessage());
      return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  private void valiDates(@RequestParam("startdate") String startDate, @RequestParam("enddate") String endDate) {
    if (!dateService.valiDate(startDate) ||
        !dateService.valiDate(endDate)) {
      throw new IllegalArgumentException(startDate + " or " + endDate + " is out of scope");
    }
  }
}
