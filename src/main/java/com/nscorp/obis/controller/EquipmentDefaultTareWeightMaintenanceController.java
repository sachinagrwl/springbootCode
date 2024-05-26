package com.nscorp.obis.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.EquipmentDefaultTareWeightMaintenance;
import com.nscorp.obis.dto.EquipmentDefaultTareWeightMaintenanceDTO;
import com.nscorp.obis.dto.mapper.EquipmentDefaultTareWeightMaintenanceMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.SizeExceedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.EquipmentDefaultTareWeightMaintenanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
@Tag(name = "Equipment Default TAre Weight Maintenance API ", description = "API to perform CRUD operations on EQ_TARE_WGT Table")
public class EquipmentDefaultTareWeightMaintenanceController {
	@Autowired
	EquipmentDefaultTareWeightMaintenanceService tareWeightService;
	
	@GetMapping(value= ControllerConstants.EQUIPMENT_TARE_WEIGHTS)
	@Operation(description = "Fetch all the values")
	public ResponseEntity<APIResponse<List<EquipmentDefaultTareWeightMaintenanceDTO>>> getAllEquipmentTareWeights(){
		try {
			List<EquipmentDefaultTareWeightMaintenanceDTO> tareWeightDtoList = Collections.emptyList();
			List<EquipmentDefaultTareWeightMaintenance> tareWeightList = tareWeightService.getAllTareWeights();
			if (tareWeightList != null && !tareWeightList.isEmpty()) {
				tareWeightDtoList = tareWeightList.stream()
						.map(EquipmentDefaultTareWeightMaintenanceMapper.INSTANCE::equipmentDefaultTareWeightMaintenanceToEquipmentDefaultTareWeightMaintenanceDTO)
						.collect(Collectors.toList());
			}
			APIResponse<List<EquipmentDefaultTareWeightMaintenanceDTO>> responseObj = new APIResponse<>(Arrays.asList("Successfully retrieved data!"),tareWeightDtoList, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e) {
			APIResponse<List<EquipmentDefaultTareWeightMaintenanceDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (Exception e){
			APIResponse<List<EquipmentDefaultTareWeightMaintenanceDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}
	
	/* This Method Is Used To Add Tare Weights */
	@JsonIgnoreProperties(ignoreUnknown = true)
	@PostMapping(value= ControllerConstants.EQUIPMENT_TARE_WEIGHTS)
	public ResponseEntity<APIResponse<EquipmentDefaultTareWeightMaintenanceDTO>> addTareWeights(@Valid @NotNull @RequestBody EquipmentDefaultTareWeightMaintenanceDTO tareWeightDto, @RequestHeader Map<String,String> headers) {
		try {
			EquipmentDefaultTareWeightMaintenance tareWeight = EquipmentDefaultTareWeightMaintenanceMapper.INSTANCE.equipmentDefaultTareWeightMaintenanceDTOToEquipmentDefaultTareWeightMaintenance(tareWeightDto);
			EquipmentDefaultTareWeightMaintenance tareWeightAdded = tareWeightService.addTareWeight(tareWeight, headers);
			EquipmentDefaultTareWeightMaintenanceDTO addedTareWeight = EquipmentDefaultTareWeightMaintenanceMapper.INSTANCE.equipmentDefaultTareWeightMaintenanceToEquipmentDefaultTareWeightMaintenanceDTO(tareWeightAdded);
			APIResponse<EquipmentDefaultTareWeightMaintenanceDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully added data!"),addedTareWeight, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e){
			APIResponse<EquipmentDefaultTareWeightMaintenanceDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (SizeExceedException e){
			APIResponse<EquipmentDefaultTareWeightMaintenanceDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.LENGTH_REQUIRED).body(responseObj);
		} catch (RecordAlreadyExistsException e){
			APIResponse<EquipmentDefaultTareWeightMaintenanceDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(responseObj);
		} catch (RecordNotAddedException e){
			APIResponse<EquipmentDefaultTareWeightMaintenanceDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseObj);
		} catch (NullPointerException e){
			APIResponse<EquipmentDefaultTareWeightMaintenanceDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
		} catch (Exception e){
			APIResponse<EquipmentDefaultTareWeightMaintenanceDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}
	
	/* This Method Is Used To Update Tare Weights */
	@PutMapping(value= ControllerConstants.EQUIPMENT_TARE_WEIGHTS)
	public ResponseEntity<APIResponse<EquipmentDefaultTareWeightMaintenanceDTO>> updateWeight(@Valid @NotNull @RequestBody EquipmentDefaultTareWeightMaintenanceDTO tareWeightDto, @RequestHeader Map<String,String> headers) {
		try {
			EquipmentDefaultTareWeightMaintenance tareWeight = EquipmentDefaultTareWeightMaintenanceMapper.INSTANCE.equipmentDefaultTareWeightMaintenanceDTOToEquipmentDefaultTareWeightMaintenance(tareWeightDto);
			EquipmentDefaultTareWeightMaintenance tareWeightAdded = tareWeightService.updateWeight(tareWeight, headers);
			EquipmentDefaultTareWeightMaintenanceDTO updateTareWeight = EquipmentDefaultTareWeightMaintenanceMapper.INSTANCE.equipmentDefaultTareWeightMaintenanceToEquipmentDefaultTareWeightMaintenanceDTO(tareWeightAdded);
			APIResponse<EquipmentDefaultTareWeightMaintenanceDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully updated data!"),updateTareWeight, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e){
			APIResponse<EquipmentDefaultTareWeightMaintenanceDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (NullPointerException e){
			APIResponse<EquipmentDefaultTareWeightMaintenanceDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
		} catch (SizeExceedException e){
			APIResponse<EquipmentDefaultTareWeightMaintenanceDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.LENGTH_REQUIRED).body(responseObj);
		} catch (Exception e){
			APIResponse<EquipmentDefaultTareWeightMaintenanceDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}
	
	/* This Method Is Used To Delete Tare Weights */
	@DeleteMapping(value= ControllerConstants.EQUIPMENT_TARE_WEIGHTS)
	public ResponseEntity<List<APIResponse<EquipmentDefaultTareWeightMaintenanceDTO>>> deleteWeight(@Valid @NotNull @RequestBody List<EquipmentDefaultTareWeightMaintenanceDTO> tareObjList){
		List<APIResponse<EquipmentDefaultTareWeightMaintenanceDTO>> response;
		AtomicInteger errorCount = new AtomicInteger();
		if (tareObjList != null && !tareObjList.isEmpty()) {
				response = tareObjList.stream().map(tareObjDto -> {
					APIResponse<EquipmentDefaultTareWeightMaintenanceDTO> singleDtoDelResponse;
					try {
						tareWeightService.deleteWeight(EquipmentDefaultTareWeightMaintenanceMapper.INSTANCE.equipmentDefaultTareWeightMaintenanceDTOToEquipmentDefaultTareWeightMaintenance(tareObjDto));
						singleDtoDelResponse = new APIResponse<>(Arrays.asList("Successfully deleted data!"),tareObjDto, ResponseStatusCode.SUCCESS);
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
}
