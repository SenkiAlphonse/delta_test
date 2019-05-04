package com.delta.test_webapi.service;

import com.delta.test_webapi.dto.DayCountDto;
import com.delta.test_webapi.dto.HolidayCountDto;
import com.delta.test_webapi.dto.IsHolidayResponseDto;
import com.delta.test_webapi.dto.WorkdayCountDto;
import com.delta.test_webapi.model.*;

import java.time.LocalDate;
import java.util.ArrayList;

public interface DateService {

  LocalDate getMinDate();

  LocalDate getMaxDate();

  ArrayList<Holiday> getHolidayList();

  boolean isHoliday(String date);

  LocalDate getLocalDateFromParam(String date);

  IsHolidayResponseDto setHolidayResponseDto(String date);

  void addHoliday(String date);

  void removeHoliday(String date);

  DayCountDto countDays(String startDate, String endDate);

  HolidayCountDto countHolidays(String startDate, String endDate);

  WorkdayCountDto countWorkdays(String startDate, String endDate);

  boolean valiDate(String date);
}
