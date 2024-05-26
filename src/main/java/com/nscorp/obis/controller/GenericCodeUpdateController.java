package com.nscorp.obis.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.GenericCodeUpdate;
import com.nscorp.obis.dto.GenericCodeUpdateDTO;
import com.nscorp.obis.dto.mapper.GenericCodeUpdateMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.SizeExceedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.GenericCodeUpdateService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


@RestController
@RequestMapping(ControllerConstants.URI + "/genericcodelist")
@Validated
public class GenericCodeUpdateController {
	@Autowired
	GenericCodeUpdateService codeListService;

	@GetMapping(value= "/{tableName}", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(description = "Fetch all the values")
	public ResponseEntity<APIResponse<List<GenericCodeUpdateDTO>>> getByGenericTable(@PathVariable("tableName") String tableName){
		try {
			List<GenericCodeUpdateDTO> genericDTOList = Collections.emptyList();
			List<GenericCodeUpdate> genericList = codeListService.getByTableName(tableName);
			if (genericList != null && !genericList.isEmpty()) {
				genericDTOList = genericList.stream()
						.map(GenericCodeUpdateMapper.INSTANCE::GenericCodeUpdateToGenericCodeUpdateDTO)
						.collect(Collectors.toList());
			}
			APIResponse<List<GenericCodeUpdateDTO>> responseObj = new APIResponse<>(Arrays.asList("Successfully retrieved data!"),genericDTOList, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e){
			APIResponse<List<GenericCodeUpdateDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (Exception e){
			APIResponse<List<GenericCodeUpdateDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	@PostMapping
	public ResponseEntity<APIResponse<GenericCodeUpdateDTO>> insert(@Valid @NotNull @RequestBody GenericCodeUpdateDTO genericcodeupdatedto, @RequestHeader Map<String,String> headers) {
		try {
			GenericCodeUpdate codeData = GenericCodeUpdateMapper.INSTANCE.GenericCodeUpdateDTOToGenericCodeUpdate(genericcodeupdatedto);
			GenericCodeUpdate codeDataAdded = codeListService.insertCode(codeData, headers);
			GenericCodeUpdateDTO addedDTO = GenericCodeUpdateMapper.INSTANCE.GenericCodeUpdateToGenericCodeUpdateDTO(codeDataAdded);
			APIResponse<GenericCodeUpdateDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully added data!"),addedDTO, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e){
			APIResponse<GenericCodeUpdateDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (SizeExceedException e){
			APIResponse<GenericCodeUpdateDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.LENGTH_REQUIRED).body(responseObj);
		} catch (RecordAlreadyExistsException e){
			APIResponse<GenericCodeUpdateDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(responseObj);
		} catch (RecordNotAddedException e){
			APIResponse<GenericCodeUpdateDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseObj);
		} catch (NullPointerException e){
			APIResponse<GenericCodeUpdateDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
		} catch (Exception e){
			APIResponse<GenericCodeUpdateDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

	@PutMapping
	public ResponseEntity<APIResponse<GenericCodeUpdateDTO>> update(@Valid @NotNull @RequestBody GenericCodeUpdateDTO genericcodeupdatedto, @RequestHeader Map<String, String> headers) {
		try {
			GenericCodeUpdate codeData = GenericCodeUpdateMapper.INSTANCE.GenericCodeUpdateDTOToGenericCodeUpdate(genericcodeupdatedto);
			GenericCodeUpdate codeDataUpdated = codeListService.updateCode(codeData, headers);
			GenericCodeUpdateDTO updatedDTO = GenericCodeUpdateMapper.INSTANCE.GenericCodeUpdateToGenericCodeUpdateDTO(codeDataUpdated);
			APIResponse<GenericCodeUpdateDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully updated data!"),updatedDTO, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e){
			APIResponse<GenericCodeUpdateDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (SizeExceedException e){
			APIResponse<GenericCodeUpdateDTO>responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.LENGTH_REQUIRED).body(responseObj);
		} catch (NullPointerException e){
			APIResponse<GenericCodeUpdateDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
		} catch (Exception e){
			APIResponse<GenericCodeUpdateDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

	@DeleteMapping
	public ResponseEntity<List<APIResponse<GenericCodeUpdateDTO>>> delete(@RequestBody List<GenericCodeUpdateDTO> genericcodeupdate){
		List<APIResponse<GenericCodeUpdateDTO>> response;
		AtomicInteger errorCount = new AtomicInteger();
		if (genericcodeupdate != null && !genericcodeupdate.isEmpty()) {
				response = genericcodeupdate.stream().map( genericCodeDto -> {
					APIResponse<GenericCodeUpdateDTO> singleDtoDelResponse;
					try {
						codeListService.deleteCode(GenericCodeUpdateMapper.INSTANCE.GenericCodeUpdateDTOToGenericCodeUpdate(genericCodeDto));
						singleDtoDelResponse = new APIResponse<>(Arrays.asList("Successfully deleted data!"),genericCodeDto, ResponseStatusCode.SUCCESS);
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