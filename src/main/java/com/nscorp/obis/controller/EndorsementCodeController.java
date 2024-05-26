package com.nscorp.obis.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
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
import com.nscorp.obis.domain.EndorsementCode;
import com.nscorp.obis.dto.EndorsementCodeDTO;
import com.nscorp.obis.dto.mapper.EndorsementCodeMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.SizeExceedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.EndorsementCodeService;

@Validated
@RestController
@CrossOrigin@RequestMapping("/")
public class EndorsementCodeController {
	
	@Autowired(required = true)
	EndorsementCodeService endorsementCodeService;

	@GetMapping(value = ControllerConstants.ENDORSEMENT_CODE)
	public ResponseEntity<APIResponse<List<EndorsementCodeDTO>>> getAllTables(
			@RequestParam(name = "endorsement-cd",required = false) String endorsementCd,
			@RequestParam(name = "endorse-desc",required = false) String endorseCdDesc) {

		try {
			List<EndorsementCodeDTO> endorsementDtoList = Collections.emptyList();
			List<EndorsementCode> endorsementList = endorsementCodeService.getAllTables(endorsementCd,endorseCdDesc);
			if (endorsementList != null && !endorsementList.isEmpty()) {
				endorsementDtoList = endorsementList.stream().map(EndorsementCodeMapper.INSTANCE::endorsementCodeToEndorsementCodeDTO)
						.collect(Collectors.toList());
			}
			APIResponse<List<EndorsementCodeDTO>> responseObj = new APIResponse<>(
					Arrays.asList("Successfully retrieved data!"), endorsementDtoList, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e) {
			APIResponse<List<EndorsementCodeDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (Exception e) {
			APIResponse<List<EndorsementCodeDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}

	}
	
	@PutMapping(value = ControllerConstants.ENDORSEMENT_CODE)
	public ResponseEntity<APIResponse<EndorsementCodeDTO>> updateEndorsementCode(@Valid @NotNull @RequestBody EndorsementCodeDTO endorsementCodeDTO, @RequestHeader Map<String,String> headers) {
		try {
			EndorsementCode endorsementCode = EndorsementCodeMapper.INSTANCE.endorsementCodeDTOToEndorsementCode(endorsementCodeDTO);
			EndorsementCode endorsementCodeAdded = endorsementCodeService.updateEndorsementCode(endorsementCode, headers);
			EndorsementCodeDTO updateendorsementCodeDTO = EndorsementCodeMapper.INSTANCE.endorsementCodeToEndorsementCodeDTO(endorsementCodeAdded);
			APIResponse<EndorsementCodeDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully updated data!"),updateendorsementCodeDTO, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e){
			APIResponse<EndorsementCodeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (NullPointerException e){
			APIResponse<EndorsementCodeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
		}catch (RecordAlreadyExistsException e){
			APIResponse<EndorsementCodeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(responseObj);
		} catch (SizeExceedException e){
			APIResponse<EndorsementCodeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.LENGTH_REQUIRED).body(responseObj);
		} catch (Exception e){
			APIResponse<EndorsementCodeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}
	
	@PostMapping(value = ControllerConstants.ENDORSEMENT_CODE)
	public ResponseEntity<APIResponse<EndorsementCodeDTO>> addEndorsementCode(@Valid @NotNull @RequestBody EndorsementCodeDTO endorsementCodeDTO, @RequestHeader Map<String,String> headers) {
		try {
			EndorsementCode endorsementCode = EndorsementCodeMapper.INSTANCE.endorsementCodeDTOToEndorsementCode(endorsementCodeDTO);
			EndorsementCode endorsementCodeAdded = endorsementCodeService.addEndorsementCode(endorsementCode, headers);
			EndorsementCodeDTO addendorsementCodeDTO = EndorsementCodeMapper.INSTANCE.endorsementCodeToEndorsementCodeDTO(endorsementCodeAdded);
			APIResponse<EndorsementCodeDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully added data!"),addendorsementCodeDTO, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e){
			APIResponse<EndorsementCodeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (RecordAlreadyExistsException e){
			APIResponse<EndorsementCodeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(responseObj);
		} catch (NullPointerException e){
			APIResponse<EndorsementCodeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
		}catch (SizeExceedException e){
			APIResponse<EndorsementCodeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.LENGTH_REQUIRED).body(responseObj);
		} catch (Exception e){
			APIResponse<EndorsementCodeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	
	}
	
	@DeleteMapping(value = ControllerConstants.ENDORSEMENT_CODE)
	public ResponseEntity<List<APIResponse<EndorsementCodeDTO>>> deleteEndorsementCode(@Valid @RequestBody List<EndorsementCodeDTO> endorsementCodeDTO) {
		List<APIResponse<EndorsementCodeDTO>> response;
		AtomicInteger errorCount = new AtomicInteger();
		
		if (endorsementCodeDTO != null && !endorsementCodeDTO.isEmpty()) {
			response = endorsementCodeDTO.stream().map(tableObjDto -> {
				APIResponse<EndorsementCodeDTO> singleDtoDelResponse;
				try {
					endorsementCodeService.deleteEndorsementCode(EndorsementCodeMapper.INSTANCE.endorsementCodeDTOToEndorsementCode(tableObjDto));
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
