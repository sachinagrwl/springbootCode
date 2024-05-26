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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.GuaranteeCustCrossRef;
import com.nscorp.obis.dto.GuaranteeCustCrossRefDTO;
import com.nscorp.obis.dto.mapper.GuaranteeCustCrossRefMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.GuaranteeCustCrossRefService;

@Validated
@RestController
@RequestMapping(ControllerConstants.GUARANTEE_CUSTOMER_CROSS_REF)
public class GuaranteeCustCrossRefController {

	@Autowired
	GuaranteeCustCrossRefService guaranteeCustCrossRefService;

	@GetMapping
	public ResponseEntity<APIResponse<List<GuaranteeCustCrossRefDTO>>> getAllGuaranteeCustCrossRef(
			@RequestParam(required = false, name = "customerName") String customerName,
			@RequestParam(required = false, name = "customerNumber") String customerNumber,
			@RequestParam(required = false, name = "terminalName") String terminalName) {

		try {

			List<GuaranteeCustCrossRefDTO> guaranteeCustCrossRefDTOList = Collections.emptyList();
			List<GuaranteeCustCrossRef> guaranteeCustCrossRefList = guaranteeCustCrossRefService
					.getAllGuaranteeCustCrossRef(customerName, customerNumber, terminalName);
			if (!CollectionUtils.isEmpty(guaranteeCustCrossRefList)) {
				guaranteeCustCrossRefDTOList = guaranteeCustCrossRefList.stream()
						.map(GuaranteeCustCrossRefMapper.INSTANCE::GuaranteeCustCrossRefToGuaranteeCustCrossRefDTO)
						.collect(Collectors.toList());
			}
			APIResponse<List<GuaranteeCustCrossRefDTO>> listAPIResponse = new APIResponse<>(
					Arrays.asList(CommonConstants.RETRIVED_SUCCESS_MESSAGE), guaranteeCustCrossRefDTOList,
					ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(listAPIResponse);
		} catch (NoRecordsFoundException e) {
			APIResponse<List<GuaranteeCustCrossRefDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (Exception e) {
			APIResponse<List<GuaranteeCustCrossRefDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}

	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	@PostMapping
	public ResponseEntity<APIResponse<GuaranteeCustCrossRefDTO>> addGuaranteeCustCrossRef(
			@Valid @RequestBody GuaranteeCustCrossRefDTO guaranteeCustCrossRefDTO,
			@RequestHeader Map<String, String> headers) {
		try {
			GuaranteeCustCrossRef addedGuarCust = guaranteeCustCrossRefService
					.addGuaranteeCustCrossRef(GuaranteeCustCrossRefMapper.INSTANCE
							.GuaranteeCustCrossRefDTOToGuaranteeCustCrossRef(guaranteeCustCrossRefDTO), headers);
			APIResponse<GuaranteeCustCrossRefDTO> responseObj = new APIResponse<>(
					Arrays.asList(CommonConstants.ADD_SUCCESS_MESSAGE),
					GuaranteeCustCrossRefMapper.INSTANCE.GuaranteeCustCrossRefToGuaranteeCustCrossRefDTO(addedGuarCust),
					ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (RecordNotAddedException e) {
			APIResponse<GuaranteeCustCrossRefDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseObj);
		} catch (RecordAlreadyExistsException e) {
			APIResponse<GuaranteeCustCrossRefDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseObj);
		} catch (NullPointerException e) {
			APIResponse<GuaranteeCustCrossRefDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
		} catch (Exception e) {
			APIResponse<GuaranteeCustCrossRefDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	@PutMapping
	public ResponseEntity<APIResponse<GuaranteeCustCrossRefDTO>> updateGuaranteeCustCrossRef(
			@Valid @RequestBody GuaranteeCustCrossRefDTO guaranteeCustCrossRefDTO,
			@RequestHeader Map<String, String> headers) {
		try {
			GuaranteeCustCrossRef addedGuarCust = guaranteeCustCrossRefService
					.updateGuaranteeCustCrossRef(GuaranteeCustCrossRefMapper.INSTANCE
							.GuaranteeCustCrossRefDTOToGuaranteeCustCrossRef(guaranteeCustCrossRefDTO), headers);
			APIResponse<GuaranteeCustCrossRefDTO> responseObj = new APIResponse<>(
					Arrays.asList(CommonConstants.UPDATE_SUCCESS_MESSAGE),
					GuaranteeCustCrossRefMapper.INSTANCE.GuaranteeCustCrossRefToGuaranteeCustCrossRefDTO(addedGuarCust),
					ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (RecordNotAddedException e) {
			APIResponse<GuaranteeCustCrossRefDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseObj);
		} catch (RecordAlreadyExistsException e) {
			APIResponse<GuaranteeCustCrossRefDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseObj);
		} catch (NoRecordsFoundException e) {
			APIResponse<GuaranteeCustCrossRefDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (NullPointerException e) {
			APIResponse<GuaranteeCustCrossRefDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
		} catch (Exception e) {
			APIResponse<GuaranteeCustCrossRefDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	@DeleteMapping
	public ResponseEntity<List<APIResponse<GuaranteeCustCrossRefDTO>>> deleteGuaranteeCustCrossRef(
			@RequestBody List<GuaranteeCustCrossRefDTO> guaranteeCustCrossRefDTOList,
			@RequestHeader Map<String, String> headers) {
		List<APIResponse<GuaranteeCustCrossRefDTO>> responseDTOList;
		AtomicInteger errorCount = new AtomicInteger();
		if (!CollectionUtils.isEmpty(guaranteeCustCrossRefDTOList)) {
			responseDTOList = guaranteeCustCrossRefDTOList.stream().map(guaranteeCustCrossRefDTO -> {
				APIResponse<GuaranteeCustCrossRefDTO> singleDtoDelResponse;
				try {
					GuaranteeCustCrossRef guaranteeCustCrossRef = guaranteeCustCrossRefService
							.deleteGuaranteeCustCrossRef(
									GuaranteeCustCrossRefMapper.INSTANCE
											.GuaranteeCustCrossRefDTOToGuaranteeCustCrossRef(guaranteeCustCrossRefDTO),
									headers);
					singleDtoDelResponse = new APIResponse<>(
							Arrays.asList(CommonConstants.DELETE_SUCCESS_MESSAGE), GuaranteeCustCrossRefMapper.INSTANCE
									.GuaranteeCustCrossRefToGuaranteeCustCrossRefDTO(guaranteeCustCrossRef),
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
