package com.nscorp.obis.controller;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.domain.health.Application;
import com.nscorp.obis.domain.health.Component;
import com.nscorp.obis.domain.health.Components;
import com.nscorp.obis.domain.health.Information;
import com.nscorp.obis.dto.CodeTableSelectionDTO;
import com.nscorp.obis.dto.health.ApplicationDTO;
import com.nscorp.obis.dto.mapper.health.ApplicationMapper;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.HealthService;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class HealthControllerTest {

    @Mock
    HealthService healthService;

    @Mock
    ApplicationMapper applicationMapper;

    @InjectMocks
    HealthController healthController;

    Application application;

    ApplicationDTO applicationDto;

    Components components;
    Component component;
    List<Component> componentList;
    Information information;

    @SuppressWarnings("deprecation")
    @BeforeEach
    void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        information = new Information();
        information.setMessage("Test");
        information.setUrl("Test");

        component = new Component();
        component.setName("DATABASE");
        component.setInformation(information);
        component.setStatus(100);
        componentList = new ArrayList<Component>();
        componentList.add(component);

        components = new Components();
        components.setComponent(componentList);

        application = new Application();
        application.setComponents(components);
        application.setName(CommonConstants.APP_NAME);

        applicationDto = new ApplicationDTO();
        applicationDto.setComponents(components);
        applicationDto.setName(CommonConstants.APP_NAME);
    }

    @AfterEach
    void tearDown() throws Exception {
        information = null;
        component = null;
        application = null;
        applicationDto = null;
        componentList = null;
    }

    @Test
    void testGetApplicationHealth() {
        when(healthService.getApplicationHealth()).thenReturn(application);
        when(applicationMapper.ApplicationToApplicationDTO(Mockito.any())).thenReturn(applicationDto);
        ResponseEntity<ApplicationDTO> appHealth = healthController.getApplicationHealth();
        assertNotNull(appHealth.getBody());
    }

//    @Test
//    void testGetApplicationHealthException() {
//        when(healthService.getApplicationHealth()).thenThrow(new RuntimeException());
//        ResponseEntity<APIResponse<ApplicationDTO>> appHealth = healthController.getApplicationHealth();
//        assertEquals(appHealth.getStatusCodeValue(), 500);
//    }
}
