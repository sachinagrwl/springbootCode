package com.nscorp.obis.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nscorp.obis.domain.CodeTableSelection;
import com.nscorp.obis.dto.CodeTableSelectionDTO;
import com.nscorp.obis.dto.mapper.CodeTableSelectionMapper;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.SizeExceedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.UmlerLowProfileCar;
import com.nscorp.obis.dto.UmlerLowProfileCarDTO;
import com.nscorp.obis.dto.mapper.UmlerLowProfileCarMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.UmlerLowProfileCarService;

import javax.validation.Valid;

@RestController
@RequestMapping(value = ControllerConstants.LOW_PROFILE_LOAD_CAPABILITIES)
public class UmlerLowProfileCarController {
	
	@Autowired
	UmlerLowProfileCarService umlerLowProfileCarService;

	@GetMapping
	public ResponseEntity<APIResponse<List<UmlerLowProfileCarDTO>>> getUmlerLowProfileCars(
			@RequestParam(required = false, name = "aarType") String aarType,
			@RequestParam(required = false, name = "carInit") String carInit) {
		try {
			List<UmlerLowProfileCarDTO> umlerLowProfileCarDTOList = Collections.emptyList();
			List<UmlerLowProfileCar> umlerLowProfileCarList = umlerLowProfileCarService.getUmlerLowProfileCars(aarType,
					carInit);
			if (!CollectionUtils.isEmpty(umlerLowProfileCarList)) {
				umlerLowProfileCarDTOList = umlerLowProfileCarList.stream()
						.map(UmlerLowProfileCarMapper.INSTANCE::UmlerLowProfileCarToUmlerLowProfileCarDTO)
						.collect(Collectors.toList());
			}
			APIResponse<List<UmlerLowProfileCarDTO>> listAPIResponse = new APIResponse<>(
					Arrays.asList(CommonConstants.RETRIVED_SUCCESS_MESSAGE), umlerLowProfileCarDTOList,
					ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(listAPIResponse);
		} catch (NoRecordsFoundException e) {
			APIResponse<List<UmlerLowProfileCarDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (Exception e) {
			APIResponse<List<UmlerLowProfileCarDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	@PostMapping
	public ResponseEntity<APIResponse<UmlerLowProfileCarDTO>> addLowProfileCar(@Valid @RequestBody UmlerLowProfileCarDTO umlerLowProfileCarDTO, @RequestHeader Map<String,String> headers){
		try {
			UmlerLowProfileCar lowProfileCar = UmlerLowProfileCarMapper.INSTANCE.UmlerLowProfileCarDTOToUmlerLowProfileCar(umlerLowProfileCarDTO);

			UmlerLowProfileCar addedLowProfileCar = umlerLowProfileCarService.addLowProfileCar(lowProfileCar, headers);
			UmlerLowProfileCarDTO addedLowProfileCarDto = UmlerLowProfileCarMapper.INSTANCE.UmlerLowProfileCarToUmlerLowProfileCarDTO(addedLowProfileCar);
			APIResponse<UmlerLowProfileCarDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully added data!"),addedLowProfileCarDto, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e){
			APIResponse<UmlerLowProfileCarDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (SizeExceedException e){
			APIResponse<UmlerLowProfileCarDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.LENGTH_REQUIRED).body(responseObj);
		} catch (RecordAlreadyExistsException e){
			APIResponse<UmlerLowProfileCarDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(responseObj);
		} catch (RecordNotAddedException e){
			APIResponse<UmlerLowProfileCarDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseObj);
		} catch (NullPointerException e){
			APIResponse<UmlerLowProfileCarDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
		} catch (Exception e){
			APIResponse<UmlerLowProfileCarDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

	@PutMapping
	public ResponseEntity<APIResponse<UmlerLowProfileCarDTO>> updateLowProfileCar(@Valid @RequestBody UmlerLowProfileCarDTO umlerLowProfileCarDTO, @RequestHeader Map<String,String> headers) {
		try {
			UmlerLowProfileCar lowProfileCar = UmlerLowProfileCarMapper.INSTANCE.UmlerLowProfileCarDTOToUmlerLowProfileCar(umlerLowProfileCarDTO);
			UmlerLowProfileCar updatedLowProfileCar = umlerLowProfileCarService.updateUmlerLowProfileCars(lowProfileCar, headers);
			UmlerLowProfileCarDTO updatedLowProfileCarDto = UmlerLowProfileCarMapper.INSTANCE.UmlerLowProfileCarToUmlerLowProfileCarDTO(updatedLowProfileCar);
			APIResponse<UmlerLowProfileCarDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully updated data!"),updatedLowProfileCarDto, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e){
			APIResponse<UmlerLowProfileCarDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (RecordNotAddedException e){
			APIResponse<UmlerLowProfileCarDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseObj);
		} catch (NullPointerException e){
			APIResponse<UmlerLowProfileCarDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
		} catch (SizeExceedException e){
			APIResponse<UmlerLowProfileCarDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.LENGTH_REQUIRED).body(responseObj);
		} catch (Exception e){
			APIResponse<UmlerLowProfileCarDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}
	
	@DeleteMapping
	public ResponseEntity<List<APIResponse<UmlerLowProfileCarDTO>>> deleteProfileCar(
			@RequestBody List<UmlerLowProfileCarDTO> umlerProfileCarDtoList) {
		List<APIResponse<UmlerLowProfileCarDTO>> responseDTOList;
		AtomicInteger errorCount = new AtomicInteger();
		if (!CollectionUtils.isEmpty(umlerProfileCarDtoList)) {
			responseDTOList = umlerProfileCarDtoList.stream().map(umlerProfileCarDTO -> {
				APIResponse<UmlerLowProfileCarDTO> singleDtoDelResponse;
				try {
					UmlerLowProfileCar umlerProfileCar = umlerLowProfileCarService
							.deleteProfileLoad(UmlerLowProfileCarMapper.INSTANCE
									.UmlerLowProfileCarDTOToUmlerLowProfileCar(umlerProfileCarDTO));
					singleDtoDelResponse = new APIResponse<>(
							Arrays.asList(CommonConstants.DELETE_SUCCESS_MESSAGE), UmlerLowProfileCarMapper.INSTANCE
							.UmlerLowProfileCarToUmlerLowProfileCarDTO(umlerProfileCar),
							ResponseStatusCode.SUCCESS);
				} catch (NoRecordsFoundException e) {
					errorCount.incrementAndGet();
					singleDtoDelResponse = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
				} catch (Exception e) {
					errorCount.incrementAndGet();
					singleDtoDelResponse = new APIResponse<>(
							Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
				}
				return singleDtoDelResponse;
			}).collect(Collectors.toList());
		} else {
			responseDTOList = Collections.emptyList();
		}

		if (errorCount.get() == 0 && responseDTOList.size() > 0) // No errors and at least 1 success
			return ResponseEntity.status(HttpStatus.OK).body(responseDTOList);
		else if (responseDTOList.size() > errorCount.get()) // Partial success
			return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(responseDTOList);
		else
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTOList);
	}

}
