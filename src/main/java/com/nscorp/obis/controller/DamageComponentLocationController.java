package com.nscorp.obis.controller;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.common.NullOrNotBlank;
import com.nscorp.obis.domain.DamageCompLoc;
import com.nscorp.obis.dto.DamageCompLocDTO;
import com.nscorp.obis.dto.mapper.DamageCompLocMapper;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.DamageComponentLocationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.sql.SQLException;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
@CrossOrigin
@Validated
@Slf4j
public class DamageComponentLocationController {

    @Autowired
    DamageComponentLocationService damageComponentLocationService;


    @GetMapping(value = ControllerConstants.DAMAGE_COMP_LOC)
    public ResponseEntity<APIResponse<List<DamageCompLocDTO>>> getDamageComponentLocation(
    		@Min(value = 1000, message = "Job Code should be 4 digits")
    	    @Max(value = 9999, message = "Job Code should be 4 digits")
    	    @RequestParam(required = false) Integer jobCode,
    	    @NullOrNotBlank(max= 1, message="Damage Area Code size should not be more than 1 character")
    	    @RequestParam(required = false) String areaCode) {

        try {
            List<DamageCompLocDTO> damageComponentDTOList = Collections.emptyList();
            List<DamageCompLoc> damageComponentList = damageComponentLocationService.getDamageComponentLocation(jobCode, areaCode);
            if (damageComponentList != null && !damageComponentList.isEmpty()) {
                damageComponentDTOList = damageComponentList.stream().map(DamageCompLocMapper.INSTANCE::damageCompLocToDamageCompLocDTO)
                        .collect(Collectors.toList());
            }

            APIResponse<List<DamageCompLocDTO>> responseObj = new APIResponse<>(Arrays.asList("Successfully retrieved data!"),
                    damageComponentDTOList, ResponseStatusCode.SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(responseObj);

        } catch (NoRecordsFoundException e) {
            APIResponse<List<DamageCompLocDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
        } catch (Exception e) {
            APIResponse<List<DamageCompLocDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }

    }

    @DeleteMapping(value = ControllerConstants.DAMAGE_COMP_LOC)
    public ResponseEntity<List<APIResponse<DamageCompLocDTO>>> deleteDamageComponent(@NotNull @RequestBody List<DamageCompLocDTO> damageComponentDTOList) {
        List<APIResponse<DamageCompLocDTO>> response;
        AtomicInteger errorCount = new AtomicInteger();
        if (damageComponentDTOList != null && !damageComponentDTOList.isEmpty()) {
            response = damageComponentDTOList.stream().map(damageComponentDTOObj -> {
                        APIResponse<DamageCompLocDTO> singleDtoDelResponse;
                        try {
                            damageComponentLocationService.deleteDamageComponent(damageComponentDTOObj);
                            singleDtoDelResponse = new APIResponse<>(Arrays.asList("Successfully deleted data!"), damageComponentDTOObj, ResponseStatusCode.SUCCESS);
                        } catch (Exception e) {
                            errorCount.incrementAndGet();
                            singleDtoDelResponse = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
                        }
                        return singleDtoDelResponse;
                    })
                    .collect(Collectors.toList());
        } else {
            response = Collections.emptyList();
        }

        if (errorCount.get() == 0 && response.size() > 0) { // No errors and atleast 1 success
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else if (response.size() > errorCount.get()) { // Partial success
            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping(value = ControllerConstants.DAMAGE_COMP_LOC)
    public ResponseEntity<APIResponse<DamageCompLocDTO>> addDamageCompLocation(
            @Valid @RequestBody DamageCompLocDTO damageCompLocDTO, @RequestHeader Map<String, String> headers)
            throws SQLException {
        log.info("DamageComponentLocationController: addDamageCompLocation : Method Starts");
        try {
            APIResponse<DamageCompLocDTO> responseObj = new APIResponse<>(Arrays.asList(CommonConstants.ADD_SUCCESS_MESSAGE),
                    damageComponentLocationService.addDamageCompLocation(damageCompLocDTO, headers),
                    ResponseStatusCode.SUCCESS.getStatusCode(), ResponseStatusCode.SUCCESS.toString());
            log.info("DamageComponentLocationController: addDamageCompLocation : Method Ends");
            return ResponseEntity.status(HttpStatus.OK).body(responseObj);

        } catch (NoRecordsFoundException|RecordAlreadyExistsException e) {
            APIResponse<DamageCompLocDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
            HttpStatus status = null;
            if(e.getClass()==NoRecordsFoundException.class)
                status=HttpStatus.NOT_FOUND;
            if(e.getClass()==RecordAlreadyExistsException.class)
                status=HttpStatus.BAD_REQUEST;
            log.info("DamageComponentLocationController: addDamageCompLocation : Method Ends");
            return ResponseEntity.status(status).body(responseObj);
        } catch (Exception e) {
            APIResponse<DamageCompLocDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
            log.info("DamageComponentLocationController: addDamageCompLocation : Method Ends");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }
    }

    @PutMapping(value = ControllerConstants.DAMAGE_COMP_LOC)
    public ResponseEntity<APIResponse<DamageCompLocDTO>> updateDamageCompLocation(
            @Valid @RequestBody DamageCompLocDTO damageCompLocDTO, @RequestHeader Map<String, String> headers)
            throws SQLException {
        log.info("DamageComponentLocationController: updateDamageCompLocation : Method Starts");
        try {
            APIResponse<DamageCompLocDTO> responseObj = new APIResponse<>(Arrays.asList(CommonConstants.UPDATE_SUCCESS_MESSAGE),
                    damageComponentLocationService.updateDamageCompLocation(damageCompLocDTO, headers),
                    ResponseStatusCode.SUCCESS.getStatusCode(), ResponseStatusCode.SUCCESS.toString());
            log.info("DamageComponentLocationController: updateDamageCompLocation : Method Ends");
            return ResponseEntity.status(HttpStatus.OK).body(responseObj);

        } catch (NoRecordsFoundException e) {
            APIResponse<DamageCompLocDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
            HttpStatus status = HttpStatus.NOT_FOUND;
            log.info("DamageComponentLocationController: updateDamageCompLocation : Method Ends");
            return ResponseEntity.status(status).body(responseObj);
        } catch (Exception e) {
            APIResponse<DamageCompLocDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
            log.info("DamageComponentLocationController: updateDamageCompLocation : Method Ends");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }
    }


}
