package com.nscorp.obis.services;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.domain.health.Application;
import com.nscorp.obis.domain.health.Component;
import com.nscorp.obis.domain.health.Components;
import com.nscorp.obis.domain.health.Information;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class HealthServiceImpl implements HealthService {

    @Autowired
    Environment env;
    @Autowired
    DataSource datasource;
    @Override
    public Application getApplicationHealth() {
        Application application = new Application();
        Components components = new Components();
        List<Component> componentList = new ArrayList<>();
        log.debug("HealthMonitorServiceImpl : getApplicationHealth : method starts ");
        application.setName(CommonConstants.APP_NAME);
        componentList.add(getDBComponentValue());
        components.setComponent(componentList);
        application.setComponents(components);
        log.debug("HealthMonitorServiceImpl : getApplicationHealth : Health details retrieved successfully ");
        log.debug("HealthMonitorServiceImpl : getApplicationHealth : method ends ");
        return application;
    }

    private Component getDBComponentValue() {
        Component component = new Component();
        Information information = new Information();
//        String success = Constants.SUCCESS;
//        String failure = Constants.FAILURE;
        Connection connection = null;
        try {
            log.info("HealthMonitorServiceImpl : getDBComponentValue : method starts");
            String dbUrl = env.getProperty(CommonConstants.DATASOURCE_URL);
            information.setUrl(dbUrl);
            component.setName("DATABASE");
            connection = datasource.getConnection();
            connection.getCatalog();
            information.setMessage("SUCCESS");
            component.setInformation(information);
            component.setStatus(0);
        } catch (Exception e) {
            log.error("HealthMonitorServiceImpl : getDBComponentValue : exception occured");
            information.setMessage("FAILURE");
            component.setInformation(information);
            component.setStatus(1);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                log.error("HealthMonitorServiceImpl : getDBComponentValue : connection close exception occured");
            }
        }
        log.info("HealthMonitorServiceImpl : getDBComponentValue : method ends");
        return component;
    }

}
