package com.backend.java.trabalho.Controller;

import com.backend.java.trabalho.controller.CityInfoController;
import com.backend.java.trabalho.service.CityInfoService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


@RunWith(SpringJUnit4ClassRunner.class)
public class CityInfoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CityInfoController cityInfoController;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(cityInfoController)
                .build();
    }

    @Test
    @DisplayName("Should load CSV file, persist the cities information and return 200 http status(OK).")
    public void readCSVFileAndPersistCities() throws Exception {
        mockMvc.perform(get("/v1/readCSV"))
                .andExpect(status().isOk());
    }

//    @Test
//    @DisplayName("Should return the capitals list and return 200 http status(OK).")
//    public void getCapitals() throws Exception {
//        mockMvc.perform(get("/v1/capitals"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.*", hasSize(27)));
//    }
}
