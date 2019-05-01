package com.delta.test_webapi.service;

import com.delta.test_webapi.model.Holiday;
import com.delta.test_webapi.model.IsHolidayResponseDto;

import java.time.LocalDate;
import java.util.ArrayList;

public interface DateService {
  ArrayList<Holiday> getHolidayList();
  boolean isHoliday(String date);
  LocalDate getLocalDateFromParam(String date);
  IsHolidayResponseDto setHolidayResponseDto(String date);
  void addHoliday(String date);
  void removeHoliday(String date);

}
