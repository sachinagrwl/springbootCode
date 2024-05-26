package com.nscorp.obis.controller;

import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.CityStateServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CityStateControllerTest {

    @Mock
    CityStateServiceImpl cityStateService;

    @InjectMocks
    CityStateController cityStateController;

    String state;

    String city;

    List<String> stateList;

    List<String> cityList;


    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        state = "AL";
        city = "test";
        stateList = new ArrayList<>();
        cityList = new ArrayList<>();
        cityList.add(city);
        stateList.add(state);
    }

    @AfterEach
    void tearDown() throws Exception {
        state = null;
        stateList = null;
        city = null;
        cityList = null;
    }

    @Test
    void getAllState() {
        when(cityStateService.getStates()).thenReturn(stateList);
        ResponseEntity<APIResponse<List<String>>> getStateList = cityStateController.getCityState();
        assertEquals(getStateList.getStatusCodeValue(), 200);
    }

    @Test
    void getAllCity() {
        when(cityStateService.getCityByState(Mockito.anyString())).thenReturn(cityList);
        ResponseEntity<APIResponse<List<String>>> getCityList = cityStateController.getCityByState(state);
        assertEquals(getCityList.getStatusCodeValue(), 200);
    }

    @Test
    void getAllCityException() {
        when(cityStateService.getCityByState(Mockito.anyString())).thenThrow(new RuntimeException());
        ResponseEntity<APIResponse<List<String>>> getCityList = cityStateController.getCityByState(state);
        assertEquals(getCityList.getStatusCodeValue(), 500);
    }

    @Test
    void getAllStateException() {
        when(cityStateService.getStates()).thenThrow(new RuntimeException());
        ResponseEntity<APIResponse<List<String>>> getStateList = cityStateController.getCityState();
        assertEquals(getStateList.getStatusCodeValue(), 500);
    }

    @Test
    void getAllStateNoRecordsFoundException() {
        when(cityStateService.getStates()).thenThrow(new NoRecordsFoundException());
        ResponseEntity<APIResponse<List<String>>> getStateList = cityStateController.getCityState();
        assertEquals(getStateList.getStatusCodeValue(), 404);
    }

    @Test
    void getAllCityNoRecordsFoundException() {
        when(cityStateService.getCityByState(Mockito.anyString())).thenThrow(new NoRecordsFoundException());
        ResponseEntity<APIResponse<List<String>>> getCityList = cityStateController.getCityByState(state);
        assertEquals(getCityList.getStatusCodeValue(), 404);
    }
}
