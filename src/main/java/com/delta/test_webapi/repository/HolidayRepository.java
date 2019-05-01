package com.delta.test_webapi.repository;

import com.delta.test_webapi.model.Holiday;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class HolidayRepository {

  ArrayList<Holiday> holidayList;
  String repoFileName;
  private static final Type jsonType = new TypeToken<ArrayList<Holiday>>(){}.getType();

  private HolidayRepository(){
    this.holidayList = new ArrayList();
    this.repoFileName = System.getenv("HOLIDAYS_REPOSITORY");
    readHolidaysFromFile();
  }

  // singleton pattern:
  private static HolidayRepository instance;

  public static HolidayRepository getInstance() {
    if (HolidayRepository.instance == null) {
      HolidayRepository.instance = new HolidayRepository();
    }
    return HolidayRepository.instance;
  }

  public void readHolidaysFromFile(){
    try {
      Gson gson = new Gson();
      JsonReader jsonReader = new JsonReader(new FileReader(repoFileName));
      //logger
      holidayList = gson.fromJson(jsonReader, jsonType);
      //logger
    }
    catch (FileNotFoundException ex) {
      //logger
      //exception
    }
  }
}
