package com.nscorp.obis.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.AARType;
import com.nscorp.obis.dto.AARTypeDTO;
import com.nscorp.obis.dto.mapper.AARTypeMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.AARTypeService;

@RestController
@RequestMapping("/")
public class AARTypeController {

	@Autowired
	AARTypeService aarTypeService;

	private static final Logger logger = LoggerFactory.getLogger(AARTypeController.class);

	
	@GetMapping(value = ControllerConstants.AAR_TYPE_LIST)
	public ResponseEntity<APIResponse<List<AARTypeDTO>>> getAllAARTypes(@RequestParam(required = false) String type) {

		try {
			logger.info("Type: {}", type);
			List<AARTypeDTO> aarTypeDtoList = Collections.emptyList();
			List<AARType> aarTypeList = aarTypeService.getAllAARTypes(type);
			if (aarTypeList != null && !aarTypeList.isEmpty()) {
				aarTypeDtoList = aarTypeList.stream().map(AARTypeMapper.INSTANCE::aarTypeToAARTypeDTO)
						.collect(Collectors.toList());
			}
			APIResponse<List<AARTypeDTO>> responseObj = new APIResponse<>(Arrays.asList("Successfully retrieved data!"),
					aarTypeDtoList, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e) {
			APIResponse<List<AARTypeDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (Exception e) {
			APIResponse<List<AARTypeDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}

	}
	 

	@PutMapping(value = ControllerConstants.AAR_TYPE)
	public ResponseEntity<APIResponse<AARTypeDTO>> updateAarType(@Valid @RequestBody AARTypeDTO aarTypeObjDto,
			@RequestHeader Map<String, String> headers) {
		try {
			AARType aarType = AARTypeMapper.INSTANCE.aarTypeDTOToAARType(aarTypeObjDto);
			AARType addAarType = aarTypeService.updateAARType(aarType, headers);
			AARTypeDTO addedAarTypeDto = AARTypeMapper.INSTANCE.aarTypeToAARTypeDTO(addAarType);
			APIResponse<AARTypeDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully updated data!"),
					addedAarTypeDto, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e) {
			APIResponse<AARTypeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (RecordAlreadyExistsException e) {
			APIResponse<AARTypeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(responseObj);
		} catch (Exception e) {
			APIResponse<AARTypeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

	@PostMapping(value = ControllerConstants.AAR_TYPE)
	public ResponseEntity<APIResponse<AARTypeDTO>> addAarType(@Valid @RequestBody AARTypeDTO aarTypeObjDto,
			@RequestHeader Map<String, String> headers) {
		try {
			AARType aarType = AARTypeMapper.INSTANCE.aarTypeDTOToAARType(aarTypeObjDto);
			AARType addAarType = aarTypeService.insertAARType(aarType, headers);
			AARTypeDTO addedAarTypeDto = AARTypeMapper.INSTANCE.aarTypeToAARTypeDTO(addAarType);
			APIResponse<AARTypeDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully added data!"),
					addedAarTypeDto, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e) {
			APIResponse<AARTypeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (RecordAlreadyExistsException e) {
			APIResponse<AARTypeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(responseObj);
		} catch (Exception e) {
			APIResponse<AARTypeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

	@GetMapping(value = ControllerConstants.AAR_TYPE)
	public ResponseEntity<APIResponse<List<AARTypeDTO>>> getAllAARTypesList(@RequestParam(required =false) List<String> type,
																			@RequestParam(required =false) List<String> description, @RequestParam(required =false) List<Integer> capacity) {

		try {
			logger.info("Type: {} and desc: {} and cap: {}", type, description, capacity);
			List<AARTypeDTO> aarTypeDtoList = Collections.emptyList();
			List<AARType> aarTypeList = aarTypeService.getAllAARTypesList(type, description, capacity);
			if (aarTypeList != null && !aarTypeList.isEmpty()) {
				aarTypeDtoList = aarTypeList.stream().map(AARTypeMapper.INSTANCE::aarTypeToAARTypeDTO)
						.collect(Collectors.toList());
			}
			APIResponse<List<AARTypeDTO>> responseObj = new APIResponse<>(Arrays.asList("Successfully retrieved data!"),
					aarTypeDtoList, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e) {
			APIResponse<List<AARTypeDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (Exception e) {
			APIResponse<List<AARTypeDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}



	@DeleteMapping(value= ControllerConstants.AAR_TYPE) 
	  public ResponseEntity<List<APIResponse<AARTypeDTO>>> deleteAARTypeDTO(@Valid @RequestBody List<AARTypeDTO> aarTypeDTO){
		List<APIResponse<AARTypeDTO>> response;
		AtomicInteger errorCount = new AtomicInteger();
		
		if (aarTypeDTO != null && !aarTypeDTO.isEmpty()) {
			response = aarTypeDTO.stream().map(tableObjDto -> {
				APIResponse<AARTypeDTO> singleDtoDelResponse;
				try {
					aarTypeService.deleteAARType(AARTypeMapper.INSTANCE.aarTypeDTOToAARType(tableObjDto));
					singleDtoDelResponse = new APIResponse<>(Arrays.asList("Successfully deleted data!"),tableObjDto, ResponseStatusCode.SUCCESS);
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
