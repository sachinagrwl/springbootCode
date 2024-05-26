package com.nscorp.obis.controller;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.DamageLocation;
import com.nscorp.obis.dto.BeneficialOwnerDTO;
import com.nscorp.obis.dto.CustomerNotifyProfileDTO;
import com.nscorp.obis.dto.DamageCategoryDTO;
import com.nscorp.obis.dto.DamageLocationDTO;
import com.nscorp.obis.dto.mapper.CustomerNotifyProfileMapper;
import com.nscorp.obis.dto.mapper.DamageCategoryMapper;
import com.nscorp.obis.dto.mapper.DamageLocationMapper;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.DamageLocationService;

@RestController
@CrossOrigin
@RequestMapping("/")
public class DamageLocationController {
    
    @Autowired
    DamageLocationService damageLocationService;

    @Autowired
    DamageLocationMapper damageLocationMapper;


    @GetMapping(value = ControllerConstants.DAMAGE_LOCATION)
	public ResponseEntity<APIResponse<List<DamageLocationDTO>>> getDamageLocation()
	{
		try {
			List<DamageLocationDTO> damageLocationDto = Collections.emptyList();
			List<DamageLocation> damageLocation = damageLocationService.getAllDamageLocation();
			if (damageLocation != null && !damageLocation.isEmpty()) {
				damageLocationDto = damageLocation.stream()
						.map(DamageLocationMapper.INSTANCE::damageLocationToDamageLocationDTO)
						.collect(Collectors.toList());
			}
			APIResponse<List<DamageLocationDTO>> responseObj = new APIResponse<>(
					Arrays.asList("Successfully retrieved data!"), damageLocationDto, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e) {
			APIResponse<List<DamageLocationDTO>> responseObj = new APIResponse<>(Arrays.asList("No Records Found"),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (Exception e) {
			APIResponse<List<DamageLocationDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

    @DeleteMapping(value = ControllerConstants.DAMAGE_LOCATION)
	public ResponseEntity<List<APIResponse<DamageLocationDTO>>> deleteDamageLocation(
			@Valid @NotNull @RequestBody List<DamageLocationDTO> dtoListToBeDeleted) {
		List<APIResponse<DamageLocationDTO>> response;
		AtomicInteger errorCount = new AtomicInteger();
		if (dtoListToBeDeleted != null && !dtoListToBeDeleted.isEmpty()) {
			response = dtoListToBeDeleted.stream().map(dto -> {
				APIResponse<DamageLocationDTO> singleDtoDelResponse;
				try {
					damageLocationService.deleteDamageLocation(DamageLocationMapper.INSTANCE.damageLocationDTOToDamageLocation(dto));
					singleDtoDelResponse = new APIResponse<>(Arrays.asList("Successfully deleted data!"), dto,
							ResponseStatusCode.SUCCESS);
				} catch (Exception e) {
					errorCount.incrementAndGet();
					singleDtoDelResponse = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
				}
				return singleDtoDelResponse;
			}).collect(Collectors.toList());
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

    @PostMapping(value = ControllerConstants.DAMAGE_LOCATION)
	public ResponseEntity<APIResponse<DamageLocationDTO>> addDamageLocation(
			@Valid @RequestBody DamageLocationDTO damageLocationDTO, @RequestHeader Map<String, String> headers)
			throws SQLException {

		try {
			APIResponse<DamageLocationDTO> responseObj = new APIResponse<>(Arrays.asList(CommonConstants.ADD_SUCCESS_MESSAGE),
                    damageLocationService.addDamageLocation(damageLocationDTO, headers),
					ResponseStatusCode.SUCCESS.getStatusCode(), ResponseStatusCode.SUCCESS.toString());
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);

		} catch (NoRecordsFoundException | RecordAlreadyExistsException e) {
			APIResponse<DamageLocationDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			HttpStatus status=HttpStatus.NOT_FOUND;
			if(e.getClass()==RecordAlreadyExistsException.class || e.getClass()==InvalidDataException.class) {
				status=HttpStatus.BAD_REQUEST;
			}
			return ResponseEntity.status(status).body(responseObj);
		} catch (Exception e) {
			APIResponse<DamageLocationDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

	@PutMapping(value = ControllerConstants.DAMAGE_LOCATION)
	ResponseEntity<APIResponse<DamageLocationDTO>> updateDamageLocation(
			@Valid @RequestBody DamageLocationDTO damageLocationDTO, @RequestHeader Map<String, String> headers) {

		try {
			APIResponse<DamageLocationDTO> responseObj = new APIResponse<>(
					Arrays.asList("Successfully updated data!"), damageLocationService.updateDamageLocation(damageLocationDTO, headers),
					ResponseStatusCode.SUCCESS.getStatusCode(), ResponseStatusCode.SUCCESS.toString());
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);

		} catch (NoRecordsFoundException | InvalidDataException e) {
			APIResponse<DamageLocationDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			HttpStatus status = HttpStatus.NOT_FOUND;
			if (e.getClass() == InvalidDataException.class)
				status = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status).body(responseObj);
		} catch (Exception e) {
			APIResponse<DamageLocationDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

    
    
}
