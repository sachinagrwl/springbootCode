package com.nscorp.obis.controller;

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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.DamageArea;
import com.nscorp.obis.dto.DamageAreaDTO;
import com.nscorp.obis.dto.mapper.DamageAreaMapper;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.DamageAreaService;

@RestController
@RequestMapping("/")
public class DamageAreaController {

	@Autowired
	DamageAreaService damageAreaService;

	@GetMapping(value = ControllerConstants.DAMAGE_AREA)
	public ResponseEntity<APIResponse<List<DamageAreaDTO>>> getAllDamageArea() {

		try {
			List<DamageAreaDTO> damageAreaDtoList = Collections.emptyList();
			List<DamageArea> damageAreaList = damageAreaService.getAllDamageArea();
			if (damageAreaList != null && !damageAreaList.isEmpty()) {
				damageAreaDtoList = damageAreaList.stream().map(DamageAreaMapper.INSTANCE::DamageAreaToDamageAreaDTO)
						.collect(Collectors.toList());
			}

			APIResponse<List<DamageAreaDTO>> responseObj = new APIResponse<>(
					Arrays.asList("Successfully retrieved data!"), damageAreaDtoList, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e) {
			APIResponse<List<DamageAreaDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (Exception e) {
			APIResponse<List<DamageAreaDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}

	}

	@PostMapping(value = ControllerConstants.DAMAGE_AREA)
	public ResponseEntity<APIResponse<DamageAreaDTO>> addDamageArea(
			@Valid @NotNull @RequestBody DamageAreaDTO damageAreaDTO, @RequestHeader Map<String, String> headers) {
		try {
			DamageArea damageAreaAdded = damageAreaService
					.addDamageArea(DamageAreaMapper.INSTANCE.DamageAreaDTOToDamageArea(damageAreaDTO), headers);
			DamageAreaDTO addDamageAreaDTO = DamageAreaMapper.INSTANCE.DamageAreaToDamageAreaDTO(damageAreaAdded);
			APIResponse<DamageAreaDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully added data!"),
					addDamageAreaDTO, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (RecordAlreadyExistsException e) {
			APIResponse<DamageAreaDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(responseObj);
		}catch (InvalidDataException e){
			APIResponse<DamageAreaDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
		} catch (Exception e) {
			APIResponse<DamageAreaDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

	@DeleteMapping(value = ControllerConstants.DAMAGE_AREA)
	public ResponseEntity<List<APIResponse<DamageAreaDTO>>> deleteDamageArea(
			@RequestBody List<DamageAreaDTO> dtoListToBeDeleted) {
		List<APIResponse<DamageAreaDTO>> response;
		AtomicInteger errorCount = new AtomicInteger();
		if (dtoListToBeDeleted != null && !dtoListToBeDeleted.isEmpty()) {
			response = dtoListToBeDeleted.stream().map(dto -> {
				APIResponse<DamageAreaDTO> singleDtoDelResponse;
				try {
					damageAreaService.deleteDamageArea(DamageAreaMapper.INSTANCE.DamageAreaDTOToDamageArea(dto));
					singleDtoDelResponse = new APIResponse<>(Arrays.asList("Successfully deleted data!"), dto,
							ResponseStatusCode.SUCCESS);
				} catch (NoRecordsFoundException e1) {
					singleDtoDelResponse = new APIResponse<>(Arrays.asList(e1.getMessage()), ResponseStatusCode.INFORMATION);
				}catch (Exception e) {
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
	
	@PutMapping(value = ControllerConstants.DAMAGE_AREA)
	public ResponseEntity<APIResponse<DamageAreaDTO>> updateDamageArea(
			@Valid @RequestBody DamageAreaDTO damageAreaDTO, @RequestHeader Map<String, String> headers) {
		try{
			DamageArea damageArea = DamageAreaMapper.INSTANCE.DamageAreaDTOToDamageArea(damageAreaDTO);
			DamageArea updateDamageArea = damageAreaService.updateDamageArea(damageArea, headers);
			DamageAreaDTO damageAreaDto = DamageAreaMapper.INSTANCE.DamageAreaToDamageAreaDTO(updateDamageArea);
			APIResponse<DamageAreaDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully updated data!"),
					damageAreaDto, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		}
		catch (NoRecordsFoundException e) {
			APIResponse<DamageAreaDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (RecordAlreadyExistsException e) {
			APIResponse<DamageAreaDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(responseObj);
		} catch (Exception e) {
			APIResponse<DamageAreaDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

}
