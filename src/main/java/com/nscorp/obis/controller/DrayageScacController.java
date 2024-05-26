package com.nscorp.obis.controller;

import java.util.Arrays;
import java.util.Map;

import javax.validation.Valid;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
import com.nscorp.obis.domain.DrayageScac;
import com.nscorp.obis.dto.DrayageScacDTO;
import com.nscorp.obis.dto.mapper.DrayageScacMapper;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.PaginatedResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.DrayageScacService;

import lombok.extern.slf4j.Slf4j;

@RequestMapping("/")
@Validated
@RestController
@CrossOrigin
@Slf4j
public class DrayageScacController {

	@Autowired
	DrayageScacService drayageScacService;

	@Autowired
	DrayageScacMapper drayageScacMapper;

	@GetMapping(value = ControllerConstants.DRAYAGE_SCAC)
	ResponseEntity<APIResponse<PaginatedResponse<DrayageScacDTO>>> getDrayageScacList(
			@Size(min=1,max=4) @RequestParam(name = "drayage-id", required = false) String drayId,
			@Size(min=1,max=99) @RequestParam(name = "carrierName", required = false) String carrierName,
			@Size(min=1,max=40) @RequestParam(name = "city", required = false) String carrierCity,
			@Size(min=1,max=2) @RequestParam(name = "state", required = false) String state,
			@RequestParam(name = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
			@RequestParam(name = "pageSize", required = false, defaultValue = "15") Integer pageSize,
			@RequestParam(name = "sort", required = false, defaultValue = "drayId, asc") String[] sort) {
		try {
			log.info("DrayageScacController : getDrayageScacList : Method Starts");
			PaginatedResponse<DrayageScacDTO> response = drayageScacService.getDrayageScac(drayId, carrierName,
					carrierCity, state, pageSize, pageNumber, sort);
			APIResponse<PaginatedResponse<DrayageScacDTO>> apiResponse = new APIResponse<>(
					Arrays.asList("Successfully retrieve data!"), response, ResponseStatusCode.SUCCESS.getStatusCode(),
					ResponseStatusCode.SUCCESS.toString());
			log.info("DrayageScacController : getDrayageScacList : Method Ends");
			return ResponseEntity.status(HttpStatus.OK).body(apiResponse);

		} catch (NoRecordsFoundException e) {
			APIResponse<PaginatedResponse<DrayageScacDTO>> responseObj = new APIResponse<>(
					Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION.getStatusCode(),
					ResponseStatusCode.INFORMATION.toString());
			log.error("DrayageScacController : getDrayageScacList : " + e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (Exception e) {
			APIResponse<PaginatedResponse<DrayageScacDTO>> responseObj = new APIResponse<>(
					Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION.getStatusCode(),
					ResponseStatusCode.INFORMATION.toString());
			log.error("DrayageScacController : getDrayageScacList : " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

	@PostMapping(value = ControllerConstants.DRAYAGE_SCAC)
	public ResponseEntity<APIResponse<DrayageScacDTO>> addDrayageScac(
		@Valid @RequestBody DrayageScacDTO DrayageScacDTO,@RequestHeader Map<String,String> headers) {

		try {
			DrayageScac drayageScac = drayageScacMapper.INSTANCE.drayageScacDtoToDrayageScac(DrayageScacDTO);
			DrayageScac added = drayageScacService.addDrayageScac(drayageScac,headers);
			DrayageScacDTO addedDTO = drayageScacMapper.INSTANCE.drayageScacToDrayageDto(added);

			APIResponse<DrayageScacDTO> response = new APIResponse<>(Arrays.asList("Sucessfully added data !"),
					addedDTO, ResponseStatusCode.SUCCESS.getStatusCode(), ResponseStatusCode.SUCCESS.toString());
			return ResponseEntity.status(HttpStatus.OK).body(response);

		} catch (RecordAlreadyExistsException e) {
			APIResponse<DrayageScacDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (NoRecordsFoundException e) {
			APIResponse<DrayageScacDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		}catch (InvalidDataException e) {
			APIResponse<DrayageScacDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
		}catch (RecordNotAddedException e) {
			APIResponse<DrayageScacDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (Exception e) {
			APIResponse<DrayageScacDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}

	}
	
	@PutMapping(value = ControllerConstants.DRAYAGE_SCAC)
	public ResponseEntity<APIResponse<DrayageScacDTO>> updateDrayageScac(
			@Valid @RequestBody DrayageScacDTO DrayageScacDTO,@RequestHeader Map<String,String> headers) {

		try {
			DrayageScacDTO updated = drayageScacService.updateDrayageScac(DrayageScacDTO,headers);

			APIResponse<DrayageScacDTO> response = new APIResponse<>(Arrays.asList("Sucessfully updated data !"),
					updated, ResponseStatusCode.SUCCESS.getStatusCode(), ResponseStatusCode.SUCCESS.toString());
			return ResponseEntity.status(HttpStatus.OK).body(response);

		} catch (NoRecordsFoundException e) {
			APIResponse<DrayageScacDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (InvalidDataException e) {
			APIResponse<DrayageScacDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (Exception e) {
			APIResponse<DrayageScacDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}

	}


	@DeleteMapping(value = ControllerConstants.DRAYAGE_SCAC)
	ResponseEntity<APIResponse<DrayageScacDTO>> deleteDrayageScac(
			@Valid @RequestBody DrayageScacDTO drayageScacDTO) {
		try {
			log.info("DrayageScacController : deleteDrayageScac : Method Starts");
			DrayageScacDTO response = drayageScacService.deleteDrayageScac(drayageScacDTO);
			APIResponse<DrayageScacDTO> apiResponse = new APIResponse<>(Arrays.asList("Successfully deleted data!"),
					response, ResponseStatusCode.SUCCESS.getStatusCode(), ResponseStatusCode.SUCCESS.toString());
			log.info("DrayageScacController : deleteDrayageScac : Method Starts");
			return ResponseEntity.status(HttpStatus.OK).body(apiResponse);

		} catch (NoRecordsFoundException e) {
			APIResponse<DrayageScacDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			log.error("DrayageScacController : deleteDrayageScac : " + e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (Exception e) {
			APIResponse<DrayageScacDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			log.error("DrayageScacController : deleteDrayageScac : " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

}
