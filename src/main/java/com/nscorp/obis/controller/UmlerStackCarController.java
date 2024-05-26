package com.nscorp.obis.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.UmlerStackCar;
import com.nscorp.obis.dto.UmlerStackCarDTO;
import com.nscorp.obis.dto.mapper.UmlerStackCarMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.SizeExceedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.UmlerStackCarService;

@RestController
@RequestMapping(value = ControllerConstants.STACK_LOAD_CAPABILITIES)
public class UmlerStackCarController {

	@Autowired
	UmlerStackCarService umlerStackCarService;

	@GetMapping
	public ResponseEntity<APIResponse<List<UmlerStackCarDTO>>> getUmlerStackCars(
			@RequestParam(required = false, name = "aarType") String aarType,
			@RequestParam(required = false, name = "carInit") String carInit) {
		try {
			List<UmlerStackCarDTO> umlerStackCarDTOList = Collections.emptyList();
			List<UmlerStackCar> umlerStackCarList = umlerStackCarService.getUmlerStackCars(aarType, carInit);
			if (!CollectionUtils.isEmpty(umlerStackCarList)) {
				umlerStackCarDTOList = umlerStackCarList.stream()
						.map(UmlerStackCarMapper.INSTANCE::umlerStackCarToUmlerStackCarDTO)
						.collect(Collectors.toList());
			}
			APIResponse<List<UmlerStackCarDTO>> listAPIResponse = new APIResponse<>(
					Arrays.asList(CommonConstants.RETRIVED_SUCCESS_MESSAGE), umlerStackCarDTOList,
					ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(listAPIResponse);
		} catch (NoRecordsFoundException e) {
			APIResponse<List<UmlerStackCarDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (Exception e) {
			APIResponse<List<UmlerStackCarDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	@PostMapping
	public ResponseEntity<APIResponse<UmlerStackCarDTO>> addStackCar(@Valid @RequestBody UmlerStackCarDTO stackCarDto, @RequestHeader Map<String,String> headers){
		try {
			UmlerStackCar umlerStackCar = UmlerStackCarMapper.INSTANCE.umlerStackCarDTOToUmlerStackCar(stackCarDto);
			UmlerStackCar addUmlerStackCar = umlerStackCarService.insertStackCar(umlerStackCar, headers);
			UmlerStackCarDTO addUmlerStackCarDto = UmlerStackCarMapper.INSTANCE.umlerStackCarToUmlerStackCarDTO(addUmlerStackCar);
			
			APIResponse<UmlerStackCarDTO> responseObj = new APIResponse<>(Arrays.asList(CommonConstants.ADD_SUCCESS_MESSAGE),addUmlerStackCarDto, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e){
			APIResponse<UmlerStackCarDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (SizeExceedException e){
			APIResponse<UmlerStackCarDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.LENGTH_REQUIRED).body(responseObj);
		} catch (RecordAlreadyExistsException e){
			APIResponse<UmlerStackCarDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(responseObj);
		} catch (RecordNotAddedException e){
			APIResponse<UmlerStackCarDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseObj);
		} catch (NullPointerException e){
			APIResponse<UmlerStackCarDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
		} catch (Exception e){
			APIResponse<UmlerStackCarDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

	@PutMapping
	public ResponseEntity<APIResponse<UmlerStackCarDTO>> updateStackCar(@Valid @RequestBody UmlerStackCarDTO umlerStackCarDTO, @RequestHeader Map<String,String> headers) {
		try {
			UmlerStackCar stackCar = UmlerStackCarMapper.INSTANCE.umlerStackCarDTOToUmlerStackCar(umlerStackCarDTO);
			UmlerStackCar updatedStackCar = umlerStackCarService.updateStackCar(stackCar, headers);
			UmlerStackCarDTO updatedStackCarDto = UmlerStackCarMapper.INSTANCE.umlerStackCarToUmlerStackCarDTO(updatedStackCar);
			APIResponse<UmlerStackCarDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully updated data!"),updatedStackCarDto, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e){
			APIResponse<UmlerStackCarDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (RecordNotAddedException e){
			APIResponse<UmlerStackCarDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseObj);
		} catch (NullPointerException e){
			APIResponse<UmlerStackCarDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
		} catch (SizeExceedException e){
			APIResponse<UmlerStackCarDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.LENGTH_REQUIRED).body(responseObj);
		} catch (Exception e){
			APIResponse<UmlerStackCarDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

	@DeleteMapping
	public ResponseEntity<List<APIResponse<UmlerStackCarDTO>>> deleteUmlerStackCar(
			@RequestBody List<UmlerStackCarDTO> umlerStackCarDTOList) {
		List<APIResponse<UmlerStackCarDTO>> responseDTOList;
		AtomicInteger errorCount = new AtomicInteger();
		if (!CollectionUtils.isEmpty(umlerStackCarDTOList)) {
			responseDTOList = umlerStackCarDTOList.stream().map(umlerStackCarDTO -> {
				APIResponse<UmlerStackCarDTO> singleDtoDelResponse;
				try {
					UmlerStackCar umlerStackCar = umlerStackCarService.deleteUmlerStackCar(
							UmlerStackCarMapper.INSTANCE.umlerStackCarDTOToUmlerStackCar(umlerStackCarDTO));
					singleDtoDelResponse = new APIResponse<>(Arrays.asList(CommonConstants.DELETE_SUCCESS_MESSAGE),
							UmlerStackCarMapper.INSTANCE.umlerStackCarToUmlerStackCarDTO(umlerStackCar),
							ResponseStatusCode.SUCCESS);
				} catch (NoRecordsFoundException e) {
					errorCount.incrementAndGet();
					singleDtoDelResponse = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
				} catch (Exception e) {
					errorCount.incrementAndGet();
					singleDtoDelResponse = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
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
