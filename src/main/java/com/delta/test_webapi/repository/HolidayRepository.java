package com.delta.test_webapi.repository;

import com.delta.test_webapi.model.Holiday;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class HolidayRepository {

  private static Logger logger = LoggerFactory.getLogger(HolidayRepository.class);

  private List<Holiday> holidayList;
  private String repoFileName;
  private static final Type HOLIDAY_LIST_TYPE = new TypeToken<ArrayList<Holiday>>(){}.getType();

  public HolidayRepository() {
    this.repoFileName = System.getenv("HOLIDAYS_REPOSITORY");
    try {
      readHolidaysFromFile();
    } catch (FileNotFoundException e){
      logger.error("Repository file could not be found: " + e.getMessage());
      e.printStackTrace();
      holidayList = new ArrayList<>();
    } catch (IOException e) {
      logger.error("Repository file error: " + e.getMessage());
      e.printStackTrace();
    }
    if (holidayList == null) {
      logger.error("Repository file is empty");
      holidayList = new ArrayList<>();
    }
  }

  private void readHolidaysFromFile() throws IOException {
      Gson gson = new Gson();
      JsonReader jsonReader = new JsonReader(new FileReader(repoFileName));
      holidayList = gson.fromJson(jsonReader, HOLIDAY_LIST_TYPE);
      jsonReader.close();
  }

  private void writeHolidaysToFile() {
    Gson gson = new Gson();
    try {
      FileWriter fileWriter = new FileWriter(repoFileName);
      gson.toJson(holidayList, fileWriter);
      fileWriter.flush();
      fileWriter.close();

    } catch (IOException e) {
      logger.error("Error occurred while trying to write Repository file");
      e.printStackTrace();
    }
  }

  void dummyDataToFile(){
    this.holidayList = new ArrayList<>();
    this.holidayList.add(new Holiday(LocalDate.now()));
    writeHolidaysToFile();
  }

  public boolean isHoliday(LocalDate date){
    return holidayList.stream().anyMatch(h -> h.getDate().equals(date));
  }

  public void addHoliday(Holiday holiday) {
    holidayList.add(holiday);
    writeHolidaysToFile();
  }

  public void removeHoliday(Holiday holiday) {
    holidayList.removeIf(h -> h.getDate().equals(holiday.getDate()));
    writeHolidaysToFile();
  }

  public List<Holiday> getHolidayList() {
    return holidayList;
  }

  public void setHolidayList(List<Holiday> holidayList) {
    this.holidayList = holidayList;
  }

  public String getRepoFileName() {
    return repoFileName;
  }

  public void setRepoFileName(String repoFileName) {
    this.repoFileName = repoFileName;
  }
}
