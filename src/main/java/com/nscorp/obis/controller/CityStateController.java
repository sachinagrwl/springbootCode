package com.nscorp.obis.controller;

import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.common.NullOrNotBlank;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.CityStateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

@RequestMapping("/")
@Validated
@RestController
@CrossOrigin
@Slf4j
public class CityStateController {

    @Autowired
    CityStateService cityStateService;


    @GetMapping(value = ControllerConstants.STATE)
    ResponseEntity<APIResponse<List<String>>> getCityState() {
        try {
            log.info("getState : Method Starts");
            List<String> results = cityStateService.getStates();
            log.info("getState : Method Ends");
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new APIResponse<>(Arrays.asList("Successfully retrieve data!"),
                            results,
                            ResponseStatusCode.SUCCESS.getStatusCode(), ResponseStatusCode.SUCCESS.toString()));
        } catch (NoRecordsFoundException e) {
            APIResponse<List<String>> response = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
            log.error("getState : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            APIResponse<List<String>> response = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
            log.error("getState : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    @GetMapping(value = ControllerConstants.STATE_CITY)
    ResponseEntity<APIResponse<List<String>>> getCityByState( @NotNull(message = "parameter is missing") @RequestParam(name = "state", required = true) String state) {
        try {

            log.info("getCityByState : Method Starts");
            List<String> results = cityStateService.getCityByState(state);
            log.info("getCityByState : Method Ends");
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new APIResponse<>(Arrays.asList("Successfully retrieve data!"),
                            results,
                            ResponseStatusCode.SUCCESS.getStatusCode(), ResponseStatusCode.SUCCESS.toString()));
        } catch (NoRecordsFoundException e) {
            APIResponse<List<String>> response = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
            log.error("getCityByState : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            APIResponse<List<String>> response = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
            log.error("getCityByState : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
