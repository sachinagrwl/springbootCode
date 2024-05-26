package com.nscorp.obis.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.nscorp.obis.domain.DamageReason;
import com.nscorp.obis.dto.mapper.DamageReasonMapper;
import com.nscorp.obis.exception.InvalidDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.dto.DamageReasonDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.DamageReasonService;

import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin
@RequestMapping("/")
@Slf4j
public class DamageReasonController {

	@Autowired
	DamageReasonService damageReasonService;

	@PostMapping(value = ControllerConstants.DAMAGE_REASON)
	ResponseEntity<APIResponse<DamageReasonDTO>> addDamageReason(@Valid @RequestBody DamageReasonDTO damageReasonDTO,
			@RequestHeader Map<String, String> headers) {
		try {
			log.info("addDamageReason : Method Starts");
			APIResponse<DamageReasonDTO> responseObj = new APIResponse<>(
					Arrays.asList(CommonConstants.ADD_SUCCESS_MESSAGE),
					damageReasonService.addDamageReason(damageReasonDTO, headers),
					ResponseStatusCode.SUCCESS.getStatusCode(), ResponseStatusCode.SUCCESS.toString());
			log.info("addDamageReason : Method Ends");
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);

		} catch (NoRecordsFoundException | RecordAlreadyExistsException e) {
			APIResponse<DamageReasonDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			HttpStatus status = HttpStatus.NOT_FOUND;
			if (e.getClass() == RecordAlreadyExistsException.class) {
				status = HttpStatus.BAD_REQUEST;
			}
			return ResponseEntity.status(status).body(responseObj);
		} catch (Exception e) {
			APIResponse<DamageReasonDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}

	}

	@PutMapping(value = ControllerConstants.DAMAGE_REASON)
	ResponseEntity<APIResponse<DamageReasonDTO>> updateDamageReason(@Valid @RequestBody DamageReasonDTO damageReasonDTO,
			@RequestHeader Map<String, String> headers) {
		try {
			log.info("addDamageReason : Method Starts");
			APIResponse<DamageReasonDTO> responseObj = new APIResponse<>(
					Arrays.asList(CommonConstants.UPDATE_SUCCESS_MESSAGE),
					damageReasonService.updateDamageReason(damageReasonDTO, headers),
					ResponseStatusCode.SUCCESS.getStatusCode(), ResponseStatusCode.SUCCESS.toString());
			log.info("addDamageReason : Method Ends");
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);

		} catch (NoRecordsFoundException | RecordAlreadyExistsException e) {
			APIResponse<DamageReasonDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			HttpStatus status = HttpStatus.NOT_FOUND;
			if (e.getClass() == RecordAlreadyExistsException.class) {
				status = HttpStatus.BAD_REQUEST;
			}
			log.info("addDamageReason : error " + e.getMessage());
			return ResponseEntity.status(status).body(responseObj);
		} catch (Exception e) {
			APIResponse<DamageReasonDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			log.info("addDamageReason : error " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}

	}

	@GetMapping(value = ControllerConstants.DAMAGE_REASON)
	public ResponseEntity<APIResponse<List<DamageReason>>> getDamageReason(
			@RequestParam Integer catCd ) {
		try {
			List<DamageReason> damageReasonDTOS = damageReasonService.getDamageReason(catCd);
			APIResponse<List<DamageReason>> responseObj;
			List<String> message = Arrays.asList("Successfully retrieved data!");
			responseObj = new APIResponse<>(message, damageReasonDTOS, ResponseStatusCode.SUCCESS.getStatusCode(),
					ResponseStatusCode.SUCCESS.toString());
			return ResponseEntity.status(HttpStatus.OK).body(responseObj); //200/

		} catch (NoRecordsFoundException e) {
			APIResponse<List<DamageReason>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj); //404/
		}
		catch (Exception e) {
			APIResponse<List<DamageReason>> responseObj1 = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj1); //500/
		}
	}
	@DeleteMapping(value = ControllerConstants.DAMAGE_REASON)
	public ResponseEntity<List<APIResponse<DamageReasonDTO>>> deleteDamageReason(
			@Valid @RequestBody List<DamageReasonDTO> damageReasonDTOList) {
		log.info("deleteDamageReason : Method Starts");
		List<APIResponse<DamageReasonDTO>> response;
		AtomicInteger errorCount = new AtomicInteger();

		if (damageReasonDTOList != null && !damageReasonDTOList.isEmpty()) {
			response = damageReasonDTOList.stream().map(tableObjDto -> {
				APIResponse<DamageReasonDTO> singleDtoDelResponse;
				try {
					DamageReason deletedData = damageReasonService.
							deleteDamageReasons(
									DamageReasonMapper.INSTANCE.damageReasonDTOToDamageReason(tableObjDto));
					DamageReasonDTO deletedDataDTO = DamageReasonMapper.INSTANCE.damageReasonToDamageReasonDTO(deletedData);

					singleDtoDelResponse = new APIResponse<>(Arrays.asList("Successfully deleted data!"), deletedDataDTO,
							ResponseStatusCode.SUCCESS);
				} catch (InvalidDataException e) {
					errorCount.incrementAndGet();
					singleDtoDelResponse = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
				} catch (NoRecordsFoundException e) {
					errorCount.incrementAndGet();
					singleDtoDelResponse = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
				} catch (Exception e) {
					errorCount.incrementAndGet();
					singleDtoDelResponse = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
				}
				log.info("deleteDamageReason : Method Ends");
				return singleDtoDelResponse;
			}).collect(Collectors.toList());
		} else {
			response = Collections.emptyList();
		}
		if (errorCount.get() == 0 && response.size() > 0) { // No errors and at least 1 success
			return ResponseEntity.status(HttpStatus.OK).body(response); //200/
		} else if (response.size() > errorCount.get()) { // Partial success
			return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(response); //206
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);//500/
		}
		/*
		    when(beneficialOwnerService.deleteBeneficialCustomers(Mockito.any())).thenThrow(RecordAlreadyExistsException.class);
		ResponseEntity<List<APIResponse<BeneficialOwnerDTO>>> deleteData = beneficialOwnerController.deleteBeneficialCustomer(beneficialOwnerDtoList);
		assertEquals(deleteData.getStatusCodeValue(), 400);
		 */
	}

}

