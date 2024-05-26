package com.nscorp.obis.controller;

import java.util.Arrays;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.StorageOverrideBillToParty;
import com.nscorp.obis.dto.StorageOverrideBillToPartyDTO;
import com.nscorp.obis.dto.mapper.StorageOverrideBillToPartyMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.StorageOverrideBillToPartyService;

@RestController
@RequestMapping(value = ControllerConstants.STORAGE_OVERRIDE_BILL_TO_PARTY)
@Validated
public class StorageOverrideBillToPartyController {

	@Autowired
	StorageOverrideBillToPartyService overrideBillToPartyService;

	@PutMapping
	public ResponseEntity<APIResponse<StorageOverrideBillToPartyDTO>> updateOverrideBillToParty(
			@Valid @NotNull @RequestBody StorageOverrideBillToPartyDTO storageOverrideBillToPartyDTO,
			@RequestHeader Map<String, String> headers) {
		try {
			StorageOverrideBillToParty overridedBill = overrideBillToPartyService.updateOverrideBillToParty(
					StorageOverrideBillToPartyMapper.INSTANCE.StorageOverrideBillToPartyDTOToStorageOverrideBillToParty(
							storageOverrideBillToPartyDTO),
					headers);
			APIResponse<StorageOverrideBillToPartyDTO> responseObj = new APIResponse<>(
					Arrays.asList(CommonConstants.UPDATE_SUCCESS_MESSAGE), StorageOverrideBillToPartyMapper.INSTANCE
							.StorageOverrideBillToPartyToStorageOverrideBillToPartyDTO(overridedBill),
					ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e) {
			APIResponse<StorageOverrideBillToPartyDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (NullPointerException e) {
			APIResponse<StorageOverrideBillToPartyDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
		} catch (Exception e) {
			APIResponse<StorageOverrideBillToPartyDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}
}
