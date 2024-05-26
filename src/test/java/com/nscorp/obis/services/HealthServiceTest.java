package com.nscorp.obis.services;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.domain.health.Application;
import com.nscorp.obis.domain.health.Component;
import com.nscorp.obis.domain.health.Components;
import com.nscorp.obis.domain.health.Information;
import com.nscorp.obis.dto.health.ApplicationDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class HealthServiceTest {

    @InjectMocks
    HealthServiceImpl healthService;

    @Mock
    private Environment env;

    @Mock
    private DataSource datasource;

    @Mock
    private Connection connection;

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

//        connection = DriverManager.getConnection(
//                "jdbc:oracle:thin:@localhost:1521:xe","system","password");
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
    void testGetDBComponentValue() {
        when(env.getProperty(CommonConstants.DATASOURCE_URL)).thenReturn("URL");
        Application application = healthService.getApplicationHealth();
        assertNotNull(application);
    }

    @Test
    void testGetDBComponentValueException(){
        when(env.getProperty(CommonConstants.DATASOURCE_URL)).thenThrow(new RuntimeException());
        Application application = healthService.getApplicationHealth();
        assertNotNull(application);
    }

}
