package com.nscorp.obis.services;

import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.repository.CityStateRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class CityStateServiceImplTest {

    @InjectMocks
    CityStateServiceImpl cityStateService;

    @Mock
    CityStateRepository cityStateRepository;

    String state;

    List<String> stateList;

    String city;

    List<String> cityList;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        state = "AL";
        stateList = new ArrayList<>();
        stateList.add(state);

        city = "Test";
        cityList = new ArrayList<>();
        cityList.add(city);

    }

    @AfterEach
    void tearDown() throws Exception {
        state = null;
        stateList = null;
        city = null;
        cityList = null;
    }

    @Test
    void testGetAllState() {
        when(cityStateRepository.findAllDistinct()).thenReturn(stateList);
        List<String> stateList = cityStateService.getStates();
        assertNotNull(stateList);
    }

    @Test
    void testGetAllCity() {
        when(cityStateRepository.findCityByState(Mockito.anyString())).thenReturn(cityList);
        List<String> cityList = cityStateService.getCityByState(state);
        assertNotNull(cityList);
    }

    @Test
    void testGetAllStateException() {
        stateList = new ArrayList<>();
        when(cityStateRepository.findAllDistinct()).thenReturn(stateList);
        NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
                () -> when(cityStateService.getStates()));
        Assertions.assertEquals("No records found", exception.getMessage());
    }

    @Test
    void testGetAllCityException() {
        cityList = new ArrayList<>();
        when(cityStateRepository.findCityByState(Mockito.anyString())).thenReturn(cityList);
        NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
                () -> when(cityStateService.getCityByState(state)));
        Assertions.assertEquals("No records found", exception.getMessage());
    }
}
