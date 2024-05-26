package com.nscorp.obis.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.nscorp.obis.domain.EquipmentCar;
import com.nscorp.obis.dto.EquipmentCarDTO;
import com.nscorp.obis.dto.mapper.EquipmentCarMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.EquipmentOwnerPrefix;
import com.nscorp.obis.dto.EquipmentOwnerPrefixDTO;
import com.nscorp.obis.dto.mapper.EquipmentOwnerPrefixMapper;
import com.nscorp.obis.exception.InvalidOwneshipException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.EquipmentOwnerPrefixService;

@Validated
@RestController
@CrossOrigin
@RequestMapping("/")
public class EquipmentOwnerPrefixController {

	@Autowired(required = true)
	EquipmentOwnerPrefixService equipmentOwnerService;

	@GetMapping(value = ControllerConstants.EQ_OWNR_PREFIX)
	public ResponseEntity<APIResponse<List<EquipmentOwnerPrefixDTO>>> getAllTables() {

		try {
			List<EquipmentOwnerPrefixDTO> equiDtoList = Collections.emptyList();
			List<EquipmentOwnerPrefix> tablesList = equipmentOwnerService.getAllTables();
			if (tablesList != null && !tablesList.isEmpty()) {
				equiDtoList = tablesList.stream()
						.map(EquipmentOwnerPrefixMapper.INSTANCE::equipmentOwnerPrefixToequipmentOwnerPrefixDTO)
						.collect(Collectors.toList());
			}
			APIResponse<List<EquipmentOwnerPrefixDTO>> responseObj = new APIResponse<>(
					Arrays.asList("Successfully retrieved data!"), equiDtoList, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e) {
			APIResponse<List<EquipmentOwnerPrefixDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (Exception e) {
			APIResponse<List<EquipmentOwnerPrefixDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}

	}

	@DeleteMapping(value = ControllerConstants.EQ_OWNR_PREFIX)
	public ResponseEntity<List<APIResponse<EquipmentOwnerPrefixDTO>>> deleteEquipmentOwnerPrefix(
			@RequestBody List<EquipmentOwnerPrefixDTO> dtoListToBeDeleted) {
		List<APIResponse<EquipmentOwnerPrefixDTO>> response;
		AtomicInteger errorCount = new AtomicInteger();
		if (dtoListToBeDeleted != null && !dtoListToBeDeleted.isEmpty()) {
			response = dtoListToBeDeleted.stream().map(dto -> {
				APIResponse<EquipmentOwnerPrefixDTO> singleDtoDelResponse;
				try {
					equipmentOwnerService.deleteEquipmentOwnerPrefixTable(
							EquipmentOwnerPrefixMapper.INSTANCE.equipmentOwnerPrefixDTOToequipmentOwnerPrefix(dto));
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
		if (errorCount.get() == 0 && response.size() > 0) { // No errors and at least 1 success
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} else if (response.size() > errorCount.get()) { // Partial success
			return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(response);
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}
	
	
	@PostMapping(value= ControllerConstants.EQ_OWNR_PREFIX)
	public  ResponseEntity<List<APIResponse<EquipmentOwnerPrefixDTO>>> insertEquipmentOwnerPrefix(
			 @RequestBody List<@Valid EquipmentOwnerPrefixDTO> dtoListToBeAdded,@RequestHeader Map<String, String> headers){

		List<APIResponse<EquipmentOwnerPrefixDTO>> response;
		AtomicInteger errorCount = new AtomicInteger();
		String ownershipArray[] = { "F", "L", "S","P" };
		List<String> ownershipList = Arrays.asList(ownershipArray);

		if (dtoListToBeAdded != null && !dtoListToBeAdded.isEmpty()) {
			response = dtoListToBeAdded.stream().map(dto -> {
				APIResponse<EquipmentOwnerPrefixDTO> singleDtoDelResponse;
				try {
					if (dto.getOwnership() == null) {
						throw new Exception("Ownership field is Required");
					} else if (!ownershipList.contains(dto.getOwnership())) {
						throw new InvalidOwneshipException("Invalid ownership provide valid");
					}
				EquipmentOwnerPrefix equipmentOwnerPrefix = EquipmentOwnerPrefixMapper.INSTANCE.equipmentOwnerPrefixDTOToequipmentOwnerPrefix(dto);
				EquipmentOwnerPrefix equipmentOwnerPrefixAdded = equipmentOwnerService.addEquipmentOwnerPrefix(equipmentOwnerPrefix,headers);
				EquipmentOwnerPrefixDTO updateEquipmentOwnerPrefix = EquipmentOwnerPrefixMapper.INSTANCE.equipmentOwnerPrefixToequipmentOwnerPrefixDTO(equipmentOwnerPrefixAdded);
					singleDtoDelResponse = new APIResponse<>(Arrays.asList("Successfully added data!"), updateEquipmentOwnerPrefix,
							ResponseStatusCode.SUCCESS);
				}  catch (RecordAlreadyExistsException e){
					errorCount.incrementAndGet();
					singleDtoDelResponse = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
				} catch (RecordNotAddedException e){
					errorCount.incrementAndGet();
					singleDtoDelResponse = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);

				} catch (InvalidOwneshipException e){
					errorCount.incrementAndGet();
					singleDtoDelResponse = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
				} catch (Exception e) {
					errorCount.incrementAndGet();
					singleDtoDelResponse = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
				}
				return singleDtoDelResponse;
			}).collect(Collectors.toList());
		} else {
			response = Collections.emptyList();
		}
		if (errorCount.get() == 0 && response.size() > 0) { // No errors and at least 1 success
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} else if (response.size() > errorCount.get()) { // Partial success
			return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(response);
		} else {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(response);
		}


	}

	@PutMapping(value= ControllerConstants.EQ_OWNR_PREFIX, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<APIResponse<EquipmentOwnerPrefixDTO>> updateEquipmentCar(
			@Valid @NotNull @RequestBody EquipmentOwnerPrefixDTO equipmentOwnerPrefixDTO, @RequestHeader Map<String, String> headers) {
		try {
			EquipmentOwnerPrefix equipmentOwnerPrefix = EquipmentOwnerPrefixMapper.INSTANCE
					.equipmentOwnerPrefixDTOToequipmentOwnerPrefix(equipmentOwnerPrefixDTO);
			EquipmentOwnerPrefix updatedEquipmentOwnerPrefix = equipmentOwnerService.updateEquipmentOwnerPrefix(equipmentOwnerPrefix, headers);
			EquipmentOwnerPrefixDTO updatedEquipmentOwnerPrefixDto = EquipmentOwnerPrefixMapper.INSTANCE
					.equipmentOwnerPrefixToequipmentOwnerPrefixDTO(updatedEquipmentOwnerPrefix);
			APIResponse<EquipmentOwnerPrefixDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully updated data!"),
					updatedEquipmentOwnerPrefixDto, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e) {
			APIResponse<EquipmentOwnerPrefixDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (RecordNotAddedException e) {
			APIResponse<EquipmentOwnerPrefixDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseObj);
		} catch (Exception e) {
			APIResponse<EquipmentOwnerPrefixDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

}
