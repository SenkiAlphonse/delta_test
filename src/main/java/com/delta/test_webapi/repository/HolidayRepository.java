package com.delta.test_webapi.repository;

import com.delta.test_webapi.model.Holiday;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;

public class HolidayRepository {

  ArrayList<Holiday> holidayList;
  String repoFileName;
  private static final Type HOLIDAY_LIST_TYPE = new TypeToken<ArrayList<Holiday>>(){}.getType();

  private HolidayRepository() {
    this.repoFileName = System.getenv("HOLIDAYS_REPOSITORY");
    try {
      readHolidaysFromFile();
    } catch (FileNotFoundException ex){
      //logger - no file found
      ex.printStackTrace();
    }
    if (holidayList == null) {
      //logger -- empty file
      holidayList = new ArrayList<>();
    }
  }

  // singleton pattern:
  private static HolidayRepository instance;

  public static HolidayRepository getInstance() {
    if (HolidayRepository.instance == null) {
      HolidayRepository.instance = new HolidayRepository();
    }
    return HolidayRepository.instance;
  }

  void readHolidaysFromFile() throws FileNotFoundException {
      Gson gson = new Gson();
      JsonReader jsonReader = new JsonReader(new FileReader(repoFileName));
      holidayList = gson.fromJson(jsonReader, HOLIDAY_LIST_TYPE);
  }

  void writeHolidaysToFile() {
    Gson gson = new Gson();
    try {
      FileWriter fileWriter = new FileWriter(repoFileName);
      gson.toJson(holidayList, fileWriter);
      fileWriter.flush();
      fileWriter.close();

    } catch (IOException e) {
      e.printStackTrace();
      //logger
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
    holidayList.remove(holiday);
      writeHolidaysToFile();
  }

  public ArrayList<Holiday> getHolidayList() {
    return holidayList;
  }

  public void setHolidayList(ArrayList<Holiday> holidayList) {
    this.holidayList = holidayList;
  }

  public String getRepoFileName() {
    return repoFileName;
  }

  public void setRepoFileName(String repoFileName) {
    this.repoFileName = repoFileName;
  }
}
