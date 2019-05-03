package com.delta.test_webapi.controller.restcontroller;


import com.delta.test_webapi.model.IsHolidayResponseDto;
import com.delta.test_webapi.service.DateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class DateRESTController {

  private DateService dateService;
  private static Logger logger = LoggerFactory.getLogger(DateRESTController.class);


  @Autowired
  public DateRESTController (DateService dateService){
    this.dateService = dateService;
  }

  @GetMapping("/api/holidays")
  public ResponseEntity isHoliday(@RequestParam(value = "date") String date){
    logger.debug("/api/holiday GET isHoliday() called with " + date);
    try{
      IsHolidayResponseDto isThisDateHoliday = dateService.setHolidayResponseDto(date);
      logger.debug("/api/holidays GET isHoliday() response: " + isThisDateHoliday.isHoliday);
      return new ResponseEntity<>(isThisDateHoliday, HttpStatus.OK);
    }
    catch(Exception e){
      logger.debug("/api/holidays GET isHoliday response: " + e.getMessage());
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  @PutMapping("/api/holidays")
  public ResponseEntity setHoliday(@RequestParam(value = "date") String date,
                                   @RequestParam(value = "setholiday") boolean setHoliday){
    logger.debug("/api/holidays PUT setHoliday called with date = " + date + ", setholiday = " + setHoliday);

    boolean isHoliday = dateService.isHoliday(date);

    if(setHoliday && !isHoliday) {
      dateService.addHoliday(date);
      logger.debug("/api/holidays PUT response OK; New holiday added: " + date);
      return new ResponseEntity<>("New holiday added: " + date, HttpStatus.OK);
    }
    else if(!setHoliday && isHoliday) {
      dateService.removeHoliday(date);
      logger.debug("/api/holidays PUT response OK; Holiday removed: " + date);
      return new ResponseEntity<>("Holiday removed: " + date, HttpStatus.OK);
    }
    else if(setHoliday) {
      logger.debug("/api/holidays PUT response OK; No change required, already holiday: " + date);
      return new ResponseEntity<>("No change required, already holiday: " + date, HttpStatus.OK);
    }
    else {
      logger.debug("/api/holidays PUT response OK; No change required, not a holiday: " + date);
      return new ResponseEntity<>("No change required, not a holiday: " + date, HttpStatus.OK);
    }
  }
}
