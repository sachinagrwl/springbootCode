package com.nscorp.obis.controller;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.common.NullOrNotBlank;
import com.nscorp.obis.dto.DamageComponentReasonDTO;
import com.nscorp.obis.dto.DamageComponentSizeDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.DamageComponentSizeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import java.sql.SQLException;
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
public class DamageComponentSizeController {

    @Autowired
    DamageComponentSizeService damageComponentSizeService;

    @GetMapping(value = ControllerConstants.DAMAGE_COMPONENT_SIZE)
	public ResponseEntity<APIResponse<List<DamageComponentSizeDTO>>> getAllDamageComponentSizes(
			@Digits(integer = 5, fraction = 0, message = "job code shouldn't have more than 5 digits") @RequestParam(name = "job-code", required = false) Integer jobCode,
			@Digits(integer = 5, fraction = 0, message = "why made code shouldn't have more than 5 digits") @RequestParam(name = "why-made", required = false) Integer aarWhyMadeCode,
			@RequestParam(name = "component-size-code", required = false) Long componentSizeCode) {

		try {
			APIResponse<List<DamageComponentSizeDTO>> responseObj = new APIResponse<>(
					Arrays.asList("Successfully retrieved data!"),
					damageComponentSizeService.fetchDamageComponentSize(jobCode, aarWhyMadeCode, componentSizeCode), ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);

		} catch (NoRecordsFoundException e) {
			APIResponse<List<DamageComponentSizeDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (Exception e) {
			APIResponse<List<DamageComponentSizeDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}

	}

    @DeleteMapping(value = ControllerConstants.DAMAGE_COMPONENT_SIZE)
	public ResponseEntity<List<APIResponse<DamageComponentSizeDTO>>> deleteDamageComponentSizes(
			@Valid @NotNull @RequestBody List<DamageComponentSizeDTO> damageComponentSizeDTOList) {

		List<APIResponse<DamageComponentSizeDTO>> response;
		AtomicInteger errorCount = new AtomicInteger();
		if (damageComponentSizeDTOList != null && !damageComponentSizeDTOList.isEmpty()) {
			response = damageComponentSizeDTOList.stream().map(damageComponentSizeDTOObj -> {
				APIResponse<DamageComponentSizeDTO> singleDtoDelResponse;
				try {
					damageComponentSizeDTOObj=damageComponentSizeService.deleteDamageComponentSize(damageComponentSizeDTOObj);
					singleDtoDelResponse = new APIResponse<>(Arrays.asList("Successfully deleted data!"),damageComponentSizeDTOObj, ResponseStatusCode.SUCCESS);
				} catch (Exception e) {
					if (e.getCause() instanceof javax.persistence.OptimisticLockException){
						errorCount.incrementAndGet();
						singleDtoDelResponse = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE_OPTIMISTICLOCK);
					} else {
						errorCount.incrementAndGet();
						singleDtoDelResponse = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
					}
				}
				return singleDtoDelResponse;
			}).collect(Collectors.toList());
		} else {
			response = Collections.emptyList();
		}
		if (errorCount.get() == 0 && response.size() > 0) { 
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} else if (response.size() > errorCount.get()) { 
			return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(response);
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

    @PostMapping(value = ControllerConstants.DAMAGE_COMPONENT_SIZE)
    public ResponseEntity<APIResponse<DamageComponentSizeDTO>> addDamageComponentSize(@Valid @RequestBody DamageComponentSizeDTO damageComponentSizeObj,
                @RequestHeader Map<String, String> headers) {
        try {
            log.info("DamageComponentSizeController: addDamageComponentSize : Method Starts");
            DamageComponentSizeDTO addDamageComponentSizeDTO = damageComponentSizeService.insertDamageComponentSize(damageComponentSizeObj, headers);
            APIResponse<DamageComponentSizeDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully added data!"),
                    addDamageComponentSizeDTO, ResponseStatusCode.SUCCESS);
            log.info("DamageComponentSizeController: addDamageComponentSize : Method Ends");
            return ResponseEntity.status(HttpStatus.OK).body(responseObj);
        } catch (NoRecordsFoundException e) {
            APIResponse<DamageComponentSizeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.INFORMATION);
            log.info("DamageComponentSizeController: addDamageComponentSize : Method Ends");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
        } catch (RecordAlreadyExistsException e) {
            APIResponse<DamageComponentSizeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.INFORMATION);
            log.info("DamageComponentSizeController: addDamageComponentSize : Method Ends");
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(responseObj);
        } catch (Exception e) {
            APIResponse<DamageComponentSizeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.FAILURE);
            log.info("DamageComponentSizeController: addDamageComponentSize : Method Ends");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }
    }

    @PutMapping(value = ControllerConstants.DAMAGE_COMPONENT_SIZE)
    public ResponseEntity<APIResponse<DamageComponentSizeDTO>> updateDamageComponentSize(
            @Valid @RequestBody DamageComponentSizeDTO damageComponentSizeDTO, @RequestHeader Map<String, String> headers)
            throws SQLException {
        log.info("DamageComponentSizeController: updateDamageComponentSize : Method Starts");
        try {
            APIResponse<DamageComponentSizeDTO> responseObj = new APIResponse<>(Arrays.asList(CommonConstants.UPDATE_SUCCESS_MESSAGE),
                    damageComponentSizeService.updateDamageCompSize(damageComponentSizeDTO, headers),
                    ResponseStatusCode.SUCCESS.getStatusCode(), ResponseStatusCode.SUCCESS.toString());
            log.info("DamageComponentSizeController: updateDamageComponentSize : Method Ends");
            return ResponseEntity.status(HttpStatus.OK).body(responseObj);

        } catch (NoRecordsFoundException e) {
            APIResponse<DamageComponentSizeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
            HttpStatus status = HttpStatus.NOT_FOUND;
            log.info("DamageComponentSizeController: updateDamageComponentSize : Method Ends");
            return ResponseEntity.status(status).body(responseObj);
        } catch (Exception e) {
            APIResponse<DamageComponentSizeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
            log.info("DamageComponentSizeController: updateDamageComponentSize : Method Ends");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }
    }
}
