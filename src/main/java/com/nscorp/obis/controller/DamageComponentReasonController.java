package com.nscorp.obis.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.nscorp.obis.common.NullOrNotBlank;
import com.nscorp.obis.domain.DamageComponent;
import com.nscorp.obis.dto.DamageComponentDTO;
import com.nscorp.obis.dto.DamageComponentReasonDTO;
import com.nscorp.obis.dto.mapper.DamageComponentMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.DamageComponentReasonService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/")
@CrossOrigin
@Validated
@Slf4j
public class DamageComponentReasonController {

	@Autowired
	private DamageComponentReasonService service;

	@GetMapping(value = ControllerConstants.DAMAGE_COMPONENT_REASON)
	public ResponseEntity<APIResponse<List<DamageComponentReasonDTO>>> getAllDamageComponentReasons(
			@Digits(integer = 5, fraction = 0, message = "job code shouldn't have more than 5 digits") @RequestParam(name = "job-code", required = false) Integer jobCode,
			@Digits(integer = 5, fraction = 0, message = "why made code shouldn't have more than 5 digits") @RequestParam(name = "why-made", required = false) Integer aarWhyMadeCode,
			@Pattern(regexp = "^[A-Z]{1}") @NullOrNotBlank(min = 1, max = 1, message = "orderCode length should be equal to 1") @RequestParam(name = "order-code", required = false) String orderCode,
			@Pattern(regexp = "^[YN]{1}") @NullOrNotBlank(min = 1, max = 1, message = "sizeRequired length should be equal to 1") @RequestParam(name = "size-req", required = false) String sizeRequired) {

		try {
			log.info("getAllDamageComponentReasons : Method Starts");
			APIResponse<List<DamageComponentReasonDTO>> responseObj = new APIResponse<>(
					Arrays.asList("Successfully retrieved data!"),
					service.getDamageComponentReasons(jobCode, aarWhyMadeCode, orderCode, sizeRequired),
					ResponseStatusCode.SUCCESS);
			log.info("getAllDamageComponentReasons : Method Ends");
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);

		} catch (NoRecordsFoundException e) {
			APIResponse<List<DamageComponentReasonDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			log.error("getAllDamageComponentReasons : Error" + e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (Exception e) {
			APIResponse<List<DamageComponentReasonDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			log.error("getAllDamageComponentReasons : Error" + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}

	}

	@DeleteMapping(value = ControllerConstants.DAMAGE_COMPONENT_REASON)
	public ResponseEntity<List<APIResponse<DamageComponentReasonDTO>>> deleteDamageComponentReason(
			@NotNull @RequestBody List<DamageComponentReasonDTO> damageComponentReasonDTOList) {
		log.info("deleteDamageComponentReason : Method Starts");
		List<APIResponse<DamageComponentReasonDTO>> response;
		AtomicInteger errorCount = new AtomicInteger();
		if (damageComponentReasonDTOList != null && !damageComponentReasonDTOList.isEmpty()) {
			response = damageComponentReasonDTOList.stream().map(damageComponentReasonDTOObj -> {
				APIResponse<DamageComponentReasonDTO> singleDtoDelResponse;
				try {
					damageComponentReasonDTOObj = service.deleteDamageComponentReason(damageComponentReasonDTOObj);
					singleDtoDelResponse = new APIResponse<>(Arrays.asList("Successfully deleted data!"),
							damageComponentReasonDTOObj, ResponseStatusCode.SUCCESS);
				} catch (Exception e) {
					if (e.getCause() instanceof javax.persistence.OptimisticLockException) {
						errorCount.incrementAndGet();
						singleDtoDelResponse = new APIResponse<>(Arrays.asList(e.getMessage()),
								ResponseStatusCode.FAILURE_OPTIMISTICLOCK);
					} else {
						errorCount.incrementAndGet();
						singleDtoDelResponse = new APIResponse<>(Arrays.asList(e.getMessage()),
								ResponseStatusCode.FAILURE);
					}
				}
				return singleDtoDelResponse;
			}).collect(Collectors.toList());
		} else {
			response = Collections.emptyList();
		}
		log.info("deleteDamageComponentReason : Method Ends");
		if (errorCount.get() == 0 && response.size() > 0) {
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} else if (response.size() > errorCount.get()) {
			return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(response);
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@PostMapping(value = ControllerConstants.DAMAGE_COMPONENT_REASON)
	public ResponseEntity<APIResponse<DamageComponentReasonDTO>> createDamageComponentReasons(
			@Valid @RequestBody DamageComponentReasonDTO damageComponentReasonDTO,
			@RequestHeader Map<String, String> headers) {

		try {
			log.info("createDamageComponentReasons : Method Starts");
			APIResponse<DamageComponentReasonDTO> responseObj = new APIResponse<>(
					Arrays.asList("Successfully added data!"),
					service.createDamageComponentReason(damageComponentReasonDTO, headers), ResponseStatusCode.SUCCESS);
			log.info("createDamageComponentReasons : Method Ends");
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);

		} catch (NoRecordsFoundException | RecordAlreadyExistsException e) {
			APIResponse<DamageComponentReasonDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			HttpStatus status = HttpStatus.ALREADY_REPORTED;
			if (e.getClass() == NoRecordsFoundException.class) {
				status = HttpStatus.NOT_FOUND;
			}
			log.error("createDamageComponentReasons : Error" + e.getMessage());
			return ResponseEntity.status(status).body(responseObj);
		} catch (Exception e) {
			APIResponse<DamageComponentReasonDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			log.error("createDamageComponentReasons : Error" + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}

	}

	@PutMapping(value = ControllerConstants.DAMAGE_COMPONENT_REASON)
	public ResponseEntity<APIResponse<DamageComponentReasonDTO>> updateDamageComponentReasons(
			@Valid @RequestBody DamageComponentReasonDTO damageComponentReasonDTO,
			@RequestHeader Map<String, String> headers) {

		try {
			log.info("updateDamageComponentReasons : Method Starts");
			APIResponse<DamageComponentReasonDTO> responseObj = new APIResponse<>(
					Arrays.asList("Successfully updated data!"),
					service.updateDamageComponentReason(damageComponentReasonDTO, headers), ResponseStatusCode.SUCCESS);
			log.info("updateDamageComponentReasons : Method Ends");
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);

		} catch (NoRecordsFoundException | RecordAlreadyExistsException e) {
			APIResponse<DamageComponentReasonDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			HttpStatus status = HttpStatus.ALREADY_REPORTED;
			if (e.getClass() == NoRecordsFoundException.class) {
				status = HttpStatus.NOT_FOUND;
			}
			log.error("updateDamageComponentReasons : Error" + e.getMessage());
			return ResponseEntity.status(status).body(responseObj);
		} catch (Exception e) {
			APIResponse<DamageComponentReasonDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			log.error("updateDamageComponentReasons : Error" + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}

	}
}
