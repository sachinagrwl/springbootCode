package com.nscorp.obis.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.nscorp.obis.domain.DamageComponent;
import com.nscorp.obis.dto.DamageComponentDTO;
import com.nscorp.obis.dto.mapper.DamageComponentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.AARLocationCode;
import com.nscorp.obis.dto.AARLocationCodeDTO;
import com.nscorp.obis.dto.mapper.AARLocationCodeMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.AARLocationCodeService;

@RestController
@RequestMapping("/")
public class AARLocationCodeController {

	@Autowired
	AARLocationCodeService aarLocationCodeService;

	@GetMapping(value = ControllerConstants.ARR_LOC_CODE)
	public ResponseEntity<APIResponse<List<AARLocationCodeDTO>>> getAARLocationCodes(@RequestParam(required = false) String locCode) {

		try {
			List<AARLocationCodeDTO> aarLocationCodeDTOList = Collections.emptyList();
			if (locCode != null) {
				AARLocationCode aarLocEntity = aarLocationCodeService.getAARLocationCodesByLocCode(locCode);
				aarLocationCodeDTOList = Arrays.asList(AARLocationCodeMapper.INSTANCE.aarLocationCodeToAARLocationCodeDTO(aarLocEntity));
			} else {
				List<AARLocationCode> AARLocationCodeList = aarLocationCodeService.getAllAARLocationCodes();
				if (AARLocationCodeList != null && !AARLocationCodeList.isEmpty()) {
					aarLocationCodeDTOList = AARLocationCodeList.stream().map(AARLocationCodeMapper.INSTANCE::aarLocationCodeToAARLocationCodeDTO)
							.collect(Collectors.toList());
				}
			}

			APIResponse<List<AARLocationCodeDTO>> responseObj = new APIResponse<>(Arrays.asList("Successfully retrieved data!"),
					aarLocationCodeDTOList, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);

		} catch (NoRecordsFoundException e) {
			APIResponse<List<AARLocationCodeDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (Exception e) {
			APIResponse<List<AARLocationCodeDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}

	}

	@PutMapping(value = ControllerConstants.ARR_LOC_CODE)
	public ResponseEntity<APIResponse<AARLocationCodeDTO>> updateAARLocationCode(
			@Valid @RequestBody AARLocationCodeDTO aarLocationCodeDTOObj, @RequestHeader Map<String, String> headers) {
		try {
			AARLocationCode aarLocationCode = AARLocationCodeMapper.INSTANCE
					.aarLocationCodeDTOToAARLocationCode(aarLocationCodeDTOObj);
			AARLocationCode updateaarLocationCode = aarLocationCodeService.updateAARLocationCode(aarLocationCode,
					headers);
			AARLocationCodeDTO updatedaarLocationCode = AARLocationCodeMapper.INSTANCE
					.aarLocationCodeToAARLocationCodeDTO(updateaarLocationCode);
			APIResponse<AARLocationCodeDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully updated data!"),
					updatedaarLocationCode, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e) {
			APIResponse<AARLocationCodeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (RecordAlreadyExistsException e) {
			APIResponse<AARLocationCodeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(responseObj);
		} catch (Exception e) {
			APIResponse<AARLocationCodeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

	@PostMapping(value = ControllerConstants.ARR_LOC_CODE)
	public ResponseEntity<APIResponse<AARLocationCodeDTO>> addAARLocationCode(
			@Valid @RequestBody AARLocationCodeDTO aarLocationCodeDTOObj, @RequestHeader Map<String, String> headers) {
		try {
			AARLocationCode aarLocationCode = AARLocationCodeMapper.INSTANCE
					.aarLocationCodeDTOToAARLocationCode(aarLocationCodeDTOObj);
			AARLocationCode updateaarLocationCode = aarLocationCodeService.addAARLocationCode(aarLocationCode, headers);
			AARLocationCodeDTO updatedaarLocationCode = AARLocationCodeMapper.INSTANCE
					.aarLocationCodeToAARLocationCodeDTO(updateaarLocationCode);
			APIResponse<AARLocationCodeDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully added data!"),
					updatedaarLocationCode, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e) {
			APIResponse<AARLocationCodeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (RecordAlreadyExistsException e) {
			APIResponse<AARLocationCodeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(responseObj);
		} catch (Exception e) {
			APIResponse<AARLocationCodeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}


	@DeleteMapping(value = ControllerConstants.ARR_LOC_CODE)
	public ResponseEntity<List<APIResponse<AARLocationCodeDTO>>> deleteByAARLocCode(@NotNull @RequestBody List<AARLocationCodeDTO> aarLocationCodeDTOList) {
		List<APIResponse<AARLocationCodeDTO>> response;
		AtomicInteger errorCount = new AtomicInteger();
		if (aarLocationCodeDTOList != null && !aarLocationCodeDTOList.isEmpty()) {
			response = aarLocationCodeDTOList.stream().map(aarLocCodeDTOObj -> {
						APIResponse<AARLocationCodeDTO> singleDtoDelResponse;
						try {
							aarLocationCodeService.deleteAARLocationCode(AARLocationCodeMapper.INSTANCE.aarLocationCodeDTOToAARLocationCode(aarLocCodeDTOObj));
							singleDtoDelResponse = new APIResponse<>(Arrays.asList("Successfully deleted data!"), aarLocCodeDTOObj, ResponseStatusCode.SUCCESS);
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
