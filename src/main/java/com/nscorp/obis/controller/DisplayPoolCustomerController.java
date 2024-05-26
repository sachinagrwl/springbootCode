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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.Pool;
import com.nscorp.obis.dto.PoolDTO;
import com.nscorp.obis.dto.mapper.PoolMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.DisplayPoolCustomerService;

@RestController
@RequestMapping("/")
public class DisplayPoolCustomerController {

	@Autowired
	DisplayPoolCustomerService displayPoolCustomerService;

	@JsonIgnoreProperties(ignoreUnknown = true)
	@PostMapping(value = ControllerConstants.DISPLAY_POOL_CUSTOMER)
	public ResponseEntity<APIResponse<PoolDTO>> addPoolCustomer(@Valid @RequestBody PoolDTO poolDTO,
			@RequestHeader Map<String, String> headers) {
		try {
			Pool associatedPool = displayPoolCustomerService.addPoolCustomer(PoolMapper.INSTANCE.poolDtoToPool(poolDTO),
					headers);
			APIResponse<PoolDTO> responseObj = new APIResponse<>(Arrays.asList(CommonConstants.ADD_SUCCESS_MESSAGE),
					PoolMapper.INSTANCE.poolToPoolDto(associatedPool), ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e) {
			APIResponse<PoolDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (RecordNotAddedException e) {
			APIResponse<PoolDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseObj);
		} catch (NullPointerException e) {
			APIResponse<PoolDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
		} catch (Exception e) {
			APIResponse<PoolDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}
	
	@DeleteMapping(value = ControllerConstants.DISPLAY_POOL_CUSTOMER)
	public ResponseEntity<List<APIResponse<PoolDTO>>> deletePool(@Valid @RequestBody List<PoolDTO> poolObj){
		
		List<APIResponse<PoolDTO>> response;
		AtomicInteger errorCount = new AtomicInteger();
		if (poolObj != null && !poolObj.isEmpty()) {
			response = poolObj.stream().map(poolObjDto -> {
				APIResponse<PoolDTO> singleDtoDelResponse;
				try {
					Pool pool = displayPoolCustomerService.deletePool(PoolMapper.INSTANCE.poolDtoToPool(poolObjDto));
					singleDtoDelResponse = new APIResponse<>(Arrays.asList("Successfully deleted data!"),PoolMapper.INSTANCE.poolToPoolDto(pool), ResponseStatusCode.SUCCESS);
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
