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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import org.springframework.web.bind.annotation.*;
import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.HazRestrPermit;
import com.nscorp.obis.dto.HazRestrPermitDTO;
import com.nscorp.obis.dto.mapper.HazRestrPermitMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.HazRestrPermitService;

@RestController
@CrossOrigin
@RequestMapping("/")
public class HazRestrPermitController {
	
	@Autowired
	HazRestrPermitService hazRestrPermitService;

	@Autowired
	HazRestrPermitMapper hazRestrPermitMapper;
	
	@GetMapping(value = ControllerConstants.HAZ_RESTR_PERMIT)
	public ResponseEntity<APIResponse<List<HazRestrPermitDTO>>> getHazRestrPermit(){
		try {
			List<HazRestrPermitDTO> hazRestrPermitDto = Collections.emptyList();
			List<HazRestrPermit> hazRestrPermit = hazRestrPermitService.getHazRestrPermit();
			if (hazRestrPermit != null && !hazRestrPermit.isEmpty()) {
				hazRestrPermitDto = hazRestrPermit.stream()
						.map(HazRestrPermitMapper.INSTANCE::hazRestrPermitToHazRestrPermitDTO)
						.collect(Collectors.toList());
			}
			APIResponse<List<HazRestrPermitDTO>> responseObj = new APIResponse<>(Arrays.asList("Successfully retrieved data!"), hazRestrPermitDto, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		}catch (NoRecordsFoundException e ){
			APIResponse<List<HazRestrPermitDTO>> responseObj = new APIResponse<>(Arrays.asList("No Records Found"), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		}catch (Exception e){
			APIResponse<List<HazRestrPermitDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

	
	@DeleteMapping(value = ControllerConstants.HAZ_RESTR_PERMIT)
	public ResponseEntity<List<APIResponse<HazRestrPermitDTO>>> deleteHazRestrPermit(
			@RequestBody List<HazRestrPermitDTO> dtoListToBeDeleted) {
    	List<APIResponse<HazRestrPermitDTO>> response;
		AtomicInteger errorCount = new AtomicInteger();
		if (dtoListToBeDeleted != null && !dtoListToBeDeleted.isEmpty()) {
			response = dtoListToBeDeleted.stream().map(dto -> {
				APIResponse<HazRestrPermitDTO> singleDtoDelResponse;
				try {
					
					HazRestrPermit hazRestrPermit =  hazRestrPermitService.deleteHazRestrPermit(
							HazRestrPermitMapper.INSTANCE.hazRestrPermitDtoToHazRestrPermit(dto));
					singleDtoDelResponse = new APIResponse<>(Arrays.asList("Successfully deleted data!"), HazRestrPermitMapper.INSTANCE.hazRestrPermitToHazRestrPermitDTO(hazRestrPermit),
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
		if (errorCount.get() == 0 && response.size() > 0) { 
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} else if (response.size() > errorCount.get()) { 
			return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(response);
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}
	
	@PostMapping(value = ControllerConstants.HAZ_RESTR_PERMIT)
	public ResponseEntity<APIResponse<HazRestrPermitDTO>> addHazRestrPermit(@Valid @RequestBody HazRestrPermitDTO hazRestrPermitDTO,
																		   @RequestHeader Map<String, String> headers) {
		try {
			HazRestrPermit hazRestrPermit = HazRestrPermitMapper.INSTANCE.hazRestrPermitDtoToHazRestrPermit(hazRestrPermitDTO);
			HazRestrPermit addHaz = hazRestrPermitService.addHazRestrPermit(hazRestrPermit, headers);
			HazRestrPermitDTO hazDTO = HazRestrPermitMapper.INSTANCE.hazRestrPermitToHazRestrPermitDTO(addHaz);
			APIResponse<HazRestrPermitDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully added data!"),
					hazDTO, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e) {
			APIResponse<HazRestrPermitDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (RecordAlreadyExistsException e) {
			APIResponse<HazRestrPermitDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(responseObj);
		} catch (Exception e) {
			APIResponse<HazRestrPermitDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

	@PutMapping(value = ControllerConstants.HAZ_RESTR_PERMIT)
	public ResponseEntity<APIResponse<HazRestrPermitDTO>> updateHazRestrPermit(
			@Valid @RequestBody HazRestrPermitDTO hazRestrPermitDTO, @RequestHeader Map<String, String> headers) {
		try{
			HazRestrPermit hazRestrictionReq = HazRestrPermitMapper.INSTANCE.hazRestrPermitDtoToHazRestrPermit(hazRestrPermitDTO);
			HazRestrPermit addHazRestrictionRes = hazRestrPermitService.updateHazRestrPermit(hazRestrictionReq, headers);
			HazRestrPermitDTO hazRestrictionDtoRes = HazRestrPermitMapper.INSTANCE.hazRestrPermitToHazRestrPermitDTO(addHazRestrictionRes);
			APIResponse<HazRestrPermitDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully updated data!"),
					hazRestrictionDtoRes, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		}
		catch (NoRecordsFoundException e) {
			APIResponse<HazRestrPermitDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (RecordAlreadyExistsException e) {
			APIResponse<HazRestrPermitDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(responseObj);
		} catch (Exception e) {
			APIResponse<HazRestrPermitDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}
}
