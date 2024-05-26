package com.nscorp.obis.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.PositionalWeightLimitMaintenance;
import com.nscorp.obis.dto.PositionalWeightLimitMaintenanceDTO;
import com.nscorp.obis.dto.mapper.PositionalWeightLimitMaintenanceMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.SizeExceedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.PositionalWeightLimitMaintenanceService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
@Validated
public class PositionalWeightLimitMaintenanceController {
	
	@Autowired
	PositionalWeightLimitMaintenanceService loadService;
	@GetMapping(value= ControllerConstants.POSITIONAL_WEIGHT_LIMITS)
	@Operation(description = "Fetch all the values")
	public ResponseEntity<APIResponse<List<PositionalWeightLimitMaintenanceDTO>>> getAllPositionalLoadLimits(){
		try {
			List<PositionalWeightLimitMaintenanceDTO> loadDtoList = Collections.emptyList();
	
			List<PositionalWeightLimitMaintenance> loadList = loadService.getAllLoadLimits();
			if(loadList != null && !loadList.isEmpty()) {
				loadDtoList = loadList.stream()
						.map(PositionalWeightLimitMaintenanceMapper.INSTANCE::positionalWeightLimitMaintenanceToPositionalWeightLimitMaintenanceDTO)
						.collect(Collectors.toList());
			}
			APIResponse<List<PositionalWeightLimitMaintenanceDTO>> responseObj = new APIResponse<>(Arrays.asList("Successfully retrieved data!"),loadDtoList, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e){
			APIResponse<List<PositionalWeightLimitMaintenanceDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (Exception e){
			APIResponse<List<PositionalWeightLimitMaintenanceDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}	
	}
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	@PostMapping(value = ControllerConstants.POSITIONAL_WEIGHT_LIMITS, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<APIResponse<PositionalWeightLimitMaintenanceDTO>> addLoadLimits(@Valid @NotNull @RequestBody PositionalWeightLimitMaintenanceDTO loadObjDto, @RequestHeader Map<String,String> headers){
		try {
			PositionalWeightLimitMaintenance weight = PositionalWeightLimitMaintenanceMapper.INSTANCE.positionalWeightLimitMaintenanceDTOToPositionalWeightLimitMaintenance(loadObjDto);
			PositionalWeightLimitMaintenance addedWeight = loadService.insertLoad(weight, headers);
			PositionalWeightLimitMaintenanceDTO addedWeightDTO = PositionalWeightLimitMaintenanceMapper.INSTANCE.positionalWeightLimitMaintenanceToPositionalWeightLimitMaintenanceDTO(addedWeight);
			APIResponse<PositionalWeightLimitMaintenanceDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully added data!"),addedWeightDTO, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e){
			APIResponse<PositionalWeightLimitMaintenanceDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (SizeExceedException e){
			APIResponse<PositionalWeightLimitMaintenanceDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.LENGTH_REQUIRED).body(responseObj);
		} catch (RecordAlreadyExistsException e){
			APIResponse<PositionalWeightLimitMaintenanceDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(responseObj);
		} catch (RecordNotAddedException e){
			APIResponse<PositionalWeightLimitMaintenanceDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseObj);
		} catch (NullPointerException e){
			APIResponse<PositionalWeightLimitMaintenanceDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
		} catch (Exception e){
			APIResponse<PositionalWeightLimitMaintenanceDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}
	
	
	/* This Method Is Used To Update Values */
	@PutMapping(value = ControllerConstants.POSITIONAL_WEIGHT_LIMITS)
	public ResponseEntity<APIResponse<PositionalWeightLimitMaintenanceDTO>> updatePositionalWeightLimitMaintenance(@Valid @NotNull @RequestBody PositionalWeightLimitMaintenanceDTO loadObjDto, @RequestHeader Map<String, String> headers) {
	try {
			PositionalWeightLimitMaintenance weight = PositionalWeightLimitMaintenanceMapper.INSTANCE.positionalWeightLimitMaintenanceDTOToPositionalWeightLimitMaintenance(loadObjDto);
			PositionalWeightLimitMaintenance updatedWeight = loadService.updatePositionalWeightLimitMaintenance(weight, headers);
			PositionalWeightLimitMaintenanceDTO updatedWeightDTO = PositionalWeightLimitMaintenanceMapper.INSTANCE.positionalWeightLimitMaintenanceToPositionalWeightLimitMaintenanceDTO(updatedWeight);
			APIResponse<PositionalWeightLimitMaintenanceDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully updated data!"),updatedWeightDTO, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
	} catch (SizeExceedException e){
		APIResponse<PositionalWeightLimitMaintenanceDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
		return ResponseEntity.status(HttpStatus.LENGTH_REQUIRED).body(responseObj);
	} catch (NullPointerException e){
		APIResponse<PositionalWeightLimitMaintenanceDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
	} catch (NoRecordsFoundException e){
		APIResponse<PositionalWeightLimitMaintenanceDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
	} catch (Exception e){
		APIResponse<PositionalWeightLimitMaintenanceDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
	}
}
	
	/* This Method Is Used To Delete Values */
	@DeleteMapping(value = ControllerConstants.POSITIONAL_WEIGHT_LIMITS)
	public ResponseEntity<List<APIResponse<PositionalWeightLimitMaintenanceDTO>>> deletePositionalWeightLimitMaintenance(@Valid @RequestBody List<PositionalWeightLimitMaintenanceDTO> loadObj){
		
		List<APIResponse<PositionalWeightLimitMaintenanceDTO>> response;
		AtomicInteger errorCount = new AtomicInteger();
		if (loadObj != null && !loadObj.isEmpty()) {
			response = loadObj.stream().map(loadObjDto -> {
				APIResponse<PositionalWeightLimitMaintenanceDTO> singleDtoDelResponse;
				try {
					loadService.deletePositionalWeightLimitMaintenance(PositionalWeightLimitMaintenanceMapper.INSTANCE.positionalWeightLimitMaintenanceDTOToPositionalWeightLimitMaintenance(loadObjDto));
					singleDtoDelResponse = new APIResponse<>(Arrays.asList("Successfully deleted data!"),loadObjDto, ResponseStatusCode.SUCCESS);
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
		}}
}
		


			
	

