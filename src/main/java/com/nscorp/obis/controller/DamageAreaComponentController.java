package com.nscorp.obis.controller;

import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.common.NullOrNotBlank;
import com.nscorp.obis.dto.CustomerPerDiemRateSelectionDTO;
import com.nscorp.obis.dto.DamageAreaComponentDTO;
import com.nscorp.obis.dto.DamageCompLocDTO;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.DamageAreaComponentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping("/")
@Slf4j
public class DamageAreaComponentController {
    @Autowired
    DamageAreaComponentService damageAreaComponentService;


    @GetMapping(value = ControllerConstants.DAMAGE_AREA_COMPONENT)
    public ResponseEntity<APIResponse<List<DamageAreaComponentDTO>>> getAllDamageAreaComponents(@Valid
            @Min(value = 1000, message = "Job Code should be 4 digits")
            @Max(value = 9999, message = "Job Code should be 4 digits")
            @RequestParam(required = false) Integer jobCode,
            @NullOrNotBlank(max= 1, message="Damage Area Code size should not be more than 1 character")
            @RequestParam(required = false) String areaCode) {

        try {
            APIResponse<List<DamageAreaComponentDTO>> responseObj = new APIResponse<>(
                    Arrays.asList("Successfully retrieved data!"),
                    damageAreaComponentService.fetchDamageComponentSize(jobCode, areaCode), ResponseStatusCode.SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(responseObj);

        } catch (NoRecordsFoundException e) {
            APIResponse<List<DamageAreaComponentDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
        } catch (Exception e) {
            APIResponse<List<DamageAreaComponentDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }

    }

    @DeleteMapping(value = ControllerConstants.DAMAGE_AREA_COMPONENT)
    public ResponseEntity<List<APIResponse<DamageAreaComponentDTO>>> deleteDamageAreaComponent(@NotNull @RequestBody List<DamageAreaComponentDTO> damageAreaComponentDTOList) {
        List<APIResponse<DamageAreaComponentDTO>> response;
        AtomicInteger errorCount = new AtomicInteger();
        if (damageAreaComponentDTOList != null && !damageAreaComponentDTOList.isEmpty()) {
            response = damageAreaComponentDTOList.stream().map(damageComponentDTOObj -> {
                        APIResponse<DamageAreaComponentDTO> singleDtoDelResponse;
                        try {
                            damageAreaComponentService.deleteDamageAreaComponent(damageComponentDTOObj);
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

    @PostMapping(value = ControllerConstants.DAMAGE_AREA_COMPONENT)
	ResponseEntity<APIResponse<DamageAreaComponentDTO>> addDamageAreaComponent(
			@Valid @RequestBody DamageAreaComponentDTO damageAreaComponentDTO, @RequestHeader Map<String, String> headers)
			throws SQLException {
		try {
			APIResponse<DamageAreaComponentDTO> responseObj = new APIResponse<>(
					Arrays.asList("Successfully added data!"),
					damageAreaComponentService.addDamageAreaComponent(damageAreaComponentDTO, headers),
					ResponseStatusCode.SUCCESS.getStatusCode(), ResponseStatusCode.SUCCESS.toString());
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);

		} catch (NoRecordsFoundException | InvalidDataException e) {
			APIResponse<DamageAreaComponentDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			HttpStatus status = HttpStatus.NOT_FOUND;
			if (e.getClass() == InvalidDataException.class)
				status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status).body(responseObj);
		} catch (Exception e) {
			APIResponse<DamageAreaComponentDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

    @PutMapping(value = ControllerConstants.DAMAGE_AREA_COMPONENT)
	ResponseEntity<APIResponse<DamageAreaComponentDTO>> updateDamageAreaComponent(
			@Valid @RequestBody DamageAreaComponentDTO damageAreaComponentDTO, @RequestHeader Map<String, String> headers)
			throws SQLException {
		try {
			APIResponse<DamageAreaComponentDTO> responseObj = new APIResponse<>(
					Arrays.asList("Successfully updated data!"),
					damageAreaComponentService.updateDamageAreaComponent(damageAreaComponentDTO, headers),
					ResponseStatusCode.SUCCESS.getStatusCode(), ResponseStatusCode.SUCCESS.toString());
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);

		} catch (NoRecordsFoundException | InvalidDataException e) {
			APIResponse<DamageAreaComponentDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			HttpStatus status = HttpStatus.NOT_FOUND;
			if (e.getClass() == InvalidDataException.class)
				status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status).body(responseObj);
		} catch (Exception e) {
			APIResponse<DamageAreaComponentDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}
}
