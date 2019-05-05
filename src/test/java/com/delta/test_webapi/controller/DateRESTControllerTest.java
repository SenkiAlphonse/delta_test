package com.delta.test_webapi.controller;

import com.delta.test_webapi.TestWebapiApplication;
import com.delta.test_webapi.controller.restcontroller.DateRESTController;
import com.delta.test_webapi.model.Holiday;
import com.delta.test_webapi.repository.HolidayRepository;
import com.delta.test_webapi.service.DateService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
    DateService.class,
    TestWebapiApplication.class,
    DateRESTController.class},
    webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
public class DateRESTControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  HolidayRepository holidayRepository;

  private LocalDate workdayDate;
  private LocalDate holidayDate;
  private Holiday holiday;
  private String holidayDateString;
  private String workdayDateString;

  @Before
  public void setup() {
    workdayDate = LocalDate.of(2019, 10, 12);
    holidayDate = LocalDate.of(2022, 9, 13);
    holiday = new Holiday(holidayDate);
    holidayDateString = "2022-09-13";
    workdayDateString = "2019-10-12";
  }

  @Test
  public void checkDateAndGetAHoliday() throws Exception {
    when(holidayRepository.isHoliday(holidayDate)).thenReturn(true);

    mockMvc.perform(get("/api/holidays")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .param("date", holidayDateString)
    )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.date", is(holidayDateString)))
        .andExpect(jsonPath("$.isHoliday", is(true)));
  }

  @Test
  public void checkDateAndGetAWorkday() throws Exception {
    when(holidayRepository.isHoliday(workdayDate)).thenReturn(false);

    mockMvc.perform(get("/api/holidays")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .param("date", workdayDateString)
    )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.date", is(workdayDateString)))
        .andExpect(jsonPath("$.isHoliday", is(false)));
  }

}
