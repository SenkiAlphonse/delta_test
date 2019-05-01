package com.delta.test_webapi.service;

import com.delta.test_webapi.model.Holiday;

import java.time.LocalDate;
import java.util.ArrayList;

public interface DateService {
  ArrayList<Holiday> getHolidayList();
  boolean isHoliday(LocalDate date);
  LocalDate getLocalDateFromParam(String date);
}
