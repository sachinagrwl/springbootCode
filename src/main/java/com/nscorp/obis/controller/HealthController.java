package com.nscorp.obis.controller;

import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.health.Application;
import com.nscorp.obis.dto.health.ApplicationDTO;
import com.nscorp.obis.dto.mapper.health.ApplicationMapper;
import com.nscorp.obis.services.HealthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@Validated
@RestController
@RequestMapping("/")
public class HealthController {

    @Autowired
    HealthService healthService;

    @PostMapping(value= ControllerConstants.HEALTH, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<ApplicationDTO> getApplicationHealth(){
        Application getAppHealth = healthService.getApplicationHealth();
        ApplicationDTO getAppHealthDto = ApplicationMapper.INSTANCE.ApplicationToApplicationDTO(getAppHealth);
//      APIResponse<ApplicationDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully retrieved data!"), getAppHealthDto, ResponseStatusCode.SUCCESS);
        return ResponseEntity.status(HttpStatus.OK).body(getAppHealthDto);
    }
}
