package com.delta.test_webapi.service;

import com.delta.test_webapi.model.Holiday;
import com.delta.test_webapi.model.IsHolidayResponseDto;
import com.delta.test_webapi.repository.HolidayRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Service
public class DateServiceImpl implements DateService {

  private static Logger logger = LoggerFactory.getLogger(DateService.class);

  HolidayRepository holidayRepository = HolidayRepository.getInstance();

  @Override
  public ArrayList<Holiday> getHolidayList() {
    return holidayRepository.getHolidayList();
  }

  @Override
  public boolean isHoliday(String date) {
    return holidayRepository.isHoliday(getLocalDateFromParam(date));
  }

  @Override
  public LocalDate getLocalDateFromParam(String date) {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    return LocalDate.parse(date, dateTimeFormatter);
  }

  @Override
  public IsHolidayResponseDto setHolidayResponseDto(String date){
    IsHolidayResponseDto isHolidayResponseDto = new IsHolidayResponseDto();
    isHolidayResponseDto.date = getLocalDateFromParam(date);
    isHolidayResponseDto.isHoliday = isHoliday(date);
    return isHolidayResponseDto;
  }

  @Override
  public void addHoliday(String date) {
    Holiday newHoliday = new Holiday(getLocalDateFromParam(date));
    holidayRepository.addHoliday(newHoliday);
  }

  @Override
  public void removeHoliday(String date) {
    Holiday removeHoliday = new Holiday(getLocalDateFromParam(date));
    holidayRepository.removeHoliday(removeHoliday);
  }
}
