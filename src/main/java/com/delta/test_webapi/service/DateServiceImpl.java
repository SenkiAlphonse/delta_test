package com.delta.test_webapi.service;

import com.delta.test_webapi.model.Holiday;
import com.delta.test_webapi.repository.HolidayRepository;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Null;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Service
public class DateServiceImpl implements DateService {



  @Override
  public ArrayList<Holiday> getHolidayList() {
    return HolidayRepository.getInstance().getHolidayList();
  }

  @Override
  public boolean isHoliday(LocalDate date) {
    return HolidayRepository.getInstance().isHoliday(date);
  }

  @Override
  public LocalDate getLocalDateFromParam(String date) {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    return LocalDate.parse(date, dateTimeFormatter);
  }
}
