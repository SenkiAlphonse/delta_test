package com.delta.test_webapi.service;

import com.delta.test_webapi.dto.DayCountDto;
import com.delta.test_webapi.dto.HolidayCountDto;
import com.delta.test_webapi.dto.IsHolidayResponseDto;
import com.delta.test_webapi.dto.WorkdayCountDto;
import com.delta.test_webapi.model.*;
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

  private LocalDate minDate;
  private LocalDate maxDate;

  private HolidayRepository holidayRepository;

  public DateServiceImpl(){
    this.holidayRepository = HolidayRepository.getInstance();
    this.minDate = getLocalDateFromParam(System.getenv("EARLIEST_DATE"));
    this.maxDate = (LocalDate.of(LocalDate.now().getYear()+5, 12, 31));
  }

  @Override
  public LocalDate getMinDate() {
    return minDate;
  }

  @Override
  public LocalDate getMaxDate() {
    return maxDate;
  }

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

  @Override
  public DayCountDto countDays(String startDate, String endDate) {
    LocalDate start = getLocalDateFromParam(startDate);
    LocalDate end = getLocalDateFromParam(endDate);

    int holidayCount = 0;
    int workdayCount = 0;

    for (LocalDate d = start; !d.isAfter(end); d = d.plusDays(1)) {
      if (holidayRepository.isHoliday(d)){
        holidayCount++;
      }
      else {
        workdayCount++;
      }
    }
    return new DayCountDto(holidayCount, workdayCount);
  }

  @Override
  public HolidayCountDto countHolidays(String startDate, String endDate) {
    LocalDate start = getLocalDateFromParam(startDate);
    LocalDate end = getLocalDateFromParam(endDate);

    int holidayCount = 0;

    for (LocalDate d = start; !d.isAfter(end); d = d.plusDays(1)) {
      if (holidayRepository.isHoliday(d)) {
        holidayCount++;
      }
    }
    return new HolidayCountDto(holidayCount);
  }

  @Override
  public WorkdayCountDto countWorkdays(String startDate, String endDate) {
    LocalDate start = getLocalDateFromParam(startDate);
    LocalDate end = getLocalDateFromParam(endDate);

    int workdayCount = 0;

    for (LocalDate d = start; !d.isAfter(end); d = d.plusDays(1)) {
      if (!holidayRepository.isHoliday(d)) {
        workdayCount++;
      }
    }
    return new WorkdayCountDto(workdayCount);
  }

  @Override
  public boolean valiDate(String date) {
    LocalDate valiDate = getLocalDateFromParam(date);
    return !valiDate.isBefore(minDate) &&
        !valiDate.isAfter(maxDate);
  }
}


