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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.PoolCarStorageExempt;
import com.nscorp.obis.dto.PoolCarStorageExemptDTO;
import com.nscorp.obis.dto.mapper.PoolCarStorageExemptMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.PoolCarStorageExemptService;

@RestController
@RequestMapping("/")
public class PoolCarStorageExemptController {

	@Autowired
	PoolCarStorageExemptService poolStorageExemptService;

	@GetMapping(value = ControllerConstants.POOL_STORAGE_EXEMPT)
	public ResponseEntity<APIResponse<List<PoolCarStorageExemptDTO>>> getAllPoolCarStorageExempts() {
		try {
			List<PoolCarStorageExemptDTO> carExemptsDTOList = Collections.emptyList();
			List<PoolCarStorageExempt> carExemptsList = poolStorageExemptService.getAllPoolCarStorageExempts();
			if (!CollectionUtils.isEmpty(carExemptsList)) {
				carExemptsDTOList = carExemptsList.stream()
						.map(PoolCarStorageExemptMapper.INSTANCE::PoolCarStorageExemptToPoolCarStorageExemptDTO)
						.collect(Collectors.toList());
			}
			APIResponse<List<PoolCarStorageExemptDTO>> responseObj = new APIResponse<>(
					Arrays.asList(CommonConstants.RETRIVED_SUCCESS_MESSAGE), carExemptsDTOList,
					ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e) {
			APIResponse<List<PoolCarStorageExemptDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (Exception e) {
			APIResponse<List<PoolCarStorageExemptDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}

	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	@PostMapping(value = ControllerConstants.POOL_STORAGE_EXEMPT)
	public ResponseEntity<List<APIResponse<PoolCarStorageExemptDTO>>> addPoolCarStorageExempt(
			@Valid @RequestBody List<PoolCarStorageExemptDTO> carStorageExemptDTOList,
			@RequestHeader Map<String, String> headers) {
		List<APIResponse<PoolCarStorageExemptDTO>> responseDTOList;
		AtomicInteger errorCount = new AtomicInteger();
		if (!CollectionUtils.isEmpty(carStorageExemptDTOList)) {
			responseDTOList = carStorageExemptDTOList.stream().map(carStorageExemptDTO -> {
				APIResponse<PoolCarStorageExemptDTO> singleDtoAddResponse;
				try {

					PoolCarStorageExempt carStorageExempt = poolStorageExemptService
							.addPoolCarStorageExempt(PoolCarStorageExemptMapper.INSTANCE
									.PoolCarStorageExemptDTOToPoolCarStorageExempt(carStorageExemptDTO), headers);
					singleDtoAddResponse = new APIResponse<>(
							Arrays.asList(CommonConstants.ADD_SUCCESS_MESSAGE), PoolCarStorageExemptMapper.INSTANCE
									.PoolCarStorageExemptToPoolCarStorageExemptDTO(carStorageExempt),
							ResponseStatusCode.SUCCESS);
				} catch (RecordNotAddedException e) {
					errorCount.incrementAndGet();
					singleDtoAddResponse = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
				} catch (RecordAlreadyExistsException e) {
					errorCount.incrementAndGet();
					singleDtoAddResponse = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
				} catch (Exception e) {
					errorCount.incrementAndGet();
					singleDtoAddResponse = new APIResponse<>(
							Arrays.asList(e.getMessage() + " For Pool Id : " + carStorageExemptDTO.getPoolId()),
							ResponseStatusCode.FAILURE);
				}
				return singleDtoAddResponse;
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

	@JsonIgnoreProperties(ignoreUnknown = true)
	@DeleteMapping(value = ControllerConstants.POOL_STORAGE_EXEMPT)
	public ResponseEntity<List<APIResponse<PoolCarStorageExemptDTO>>> deletePoolCarStorageExempt(
			@RequestBody List<PoolCarStorageExemptDTO> carStorageExemptDTOList) {
		List<APIResponse<PoolCarStorageExemptDTO>> responseDTOList;
		AtomicInteger errorCount = new AtomicInteger();
		if (!CollectionUtils.isEmpty(carStorageExemptDTOList)) {
			responseDTOList = carStorageExemptDTOList.stream().map(carStorageExemptDTO -> {
				APIResponse<PoolCarStorageExemptDTO> singleDtoDelResponse;
				try {
					PoolCarStorageExempt carStorageExempt = poolStorageExemptService
							.deletePoolCarStorageExempt(PoolCarStorageExemptMapper.INSTANCE
									.PoolCarStorageExemptDTOToPoolCarStorageExempt(carStorageExemptDTO));
					singleDtoDelResponse = new APIResponse<>(
							Arrays.asList(CommonConstants.DELETE_SUCCESS_MESSAGE), PoolCarStorageExemptMapper.INSTANCE
									.PoolCarStorageExemptToPoolCarStorageExemptDTO(carStorageExempt),
							ResponseStatusCode.SUCCESS);
				} catch (NoRecordsFoundException e) {
					errorCount.incrementAndGet();
					singleDtoDelResponse = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
				} catch (Exception e) {
					errorCount.incrementAndGet();
					singleDtoDelResponse = new APIResponse<>(
							Arrays.asList(e.getMessage() + " For Pool Id : " + carStorageExemptDTO.getPoolId()),
							ResponseStatusCode.FAILURE);
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
