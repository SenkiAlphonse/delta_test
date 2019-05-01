package com.delta.test_webapi.controller.restcontroller;


import com.delta.test_webapi.model.IsHolidayResponseDto;
import com.delta.test_webapi.service.DateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
public class DateRESTController {

  private DateService dateService;

  @Autowired
  public DateRESTController (DateService dateService){
    this.dateService = dateService;
  }

  @GetMapping("/api/holidays")
  public ResponseEntity isHoliday(@RequestParam(value = "date") String date){
    //logger
    try{
      LocalDate requestedDate = dateService.getLocalDateFromParam(date);

      IsHolidayResponseDto isHolidayResponseDto = new IsHolidayResponseDto();
      isHolidayResponseDto.date = requestedDate;
      isHolidayResponseDto.isHoliday = dateService.isHoliday(requestedDate);
      //logger
      return new ResponseEntity<>(isHolidayResponseDto, HttpStatus.OK);
    }
    catch(Exception ex){
      //logger
      //throw exception from repo if can't read file.
      //other exceptions handle differently.
      return new ResponseEntity<>(ex.getLocalizedMessage(), HttpStatus.I_AM_A_TEAPOT);
    }
  }

  @PutMapping("/api/holidays")
  public ResponseEntity setDayOff(@RequestParam(value = "date") String date,
                                  @RequestParam(value = "holiday", required = false) boolean isHoliday){
    return null;
  }
}
