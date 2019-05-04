package com.delta.test_webapi.dto;

public class DayCountDto {
  public int holidayCount;
  public int workdayCount;

  public DayCountDto() {
  }

  public DayCountDto(int holidayCount, int workdayCount) {
    this.holidayCount = holidayCount;
    this.workdayCount = workdayCount;
  }

  public int getHolidayCount() {
    return holidayCount;
  }

  public void setHolidayCount(int holidayCount) {
    this.holidayCount = holidayCount;
  }

  public int getWorkdayCount() {
    return workdayCount;
  }

  public void setWorkdayCount(int workdayCount) {
    this.workdayCount = workdayCount;
  }
}
