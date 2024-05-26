package com.nscorp.obis.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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
import com.nscorp.obis.domain.InterChangeParty;
import com.nscorp.obis.dto.InterChangePartyDTO;
import com.nscorp.obis.dto.mapper.InterChangePartyMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.SizeExceedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.InterChangePartyService;


@Validated
@RestController
@CrossOrigin
@RequestMapping(ControllerConstants.ICHG_PARTY)
public class InterChangePartyController {
	
	@Autowired(required = true)
	InterChangePartyService interChangePartyService;

	@GetMapping
	public ResponseEntity<APIResponse<List<InterChangePartyDTO>>> getAllTables(@RequestParam(required = false, name = "ichg-party-nm") String ichgCode) {

		try {
			List<InterChangePartyDTO> interDtoList = Collections.emptyList();
			List<InterChangeParty> tablesList = interChangePartyService.getAllTables(ichgCode);
			if (tablesList != null && !tablesList.isEmpty()) {
				interDtoList = tablesList.stream()
						.map(InterChangePartyMapper.INSTANCE::interChangePartyTointerChangePartyDTO)
						.collect(Collectors.toList());
			}
			APIResponse<List<InterChangePartyDTO>> responseObj = new APIResponse<>(
					Arrays.asList("Successfully retrieved data!"), interDtoList, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e) {
			APIResponse<List<InterChangePartyDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (Exception e) {
			APIResponse<List<InterChangePartyDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}

	}
	
	@PutMapping
	public ResponseEntity<APIResponse<InterChangePartyDTO>> updateInterChangeParty(@Valid @NotNull @RequestBody InterChangePartyDTO interChangePartyDTO, @RequestHeader Map<String,String> headers) {
		try {
			InterChangeParty interChangeParty = InterChangePartyMapper.INSTANCE.interChangePartyDTOTointerChangeParty(interChangePartyDTO);
			InterChangeParty interChangePartyAdded = interChangePartyService.updateInterChangeParty(interChangeParty, headers);
			InterChangePartyDTO updateInterChangePartyDTO = InterChangePartyMapper.INSTANCE.interChangePartyTointerChangePartyDTO(interChangePartyAdded);
			APIResponse<InterChangePartyDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully updated data!"),updateInterChangePartyDTO, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e){
			APIResponse<InterChangePartyDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (NullPointerException e){
			APIResponse<InterChangePartyDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
		} catch (SizeExceedException e){
			APIResponse<InterChangePartyDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.LENGTH_REQUIRED).body(responseObj);
		} catch (Exception e){
			APIResponse<InterChangePartyDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

	@DeleteMapping
	public ResponseEntity<List<APIResponse<InterChangePartyDTO>>> deleteInterchangePartyDto(@RequestBody List<InterChangePartyDTO> interchangePartyDTO) {
		List<APIResponse<InterChangePartyDTO>> response;
		AtomicInteger errorCount = new AtomicInteger();
		
		if (interchangePartyDTO != null && !interchangePartyDTO.isEmpty()) {
			response = interchangePartyDTO.stream().map(tableObjDto -> {
				APIResponse<InterChangePartyDTO> singleDtoDelResponse;
				try {
					interChangePartyService.deleteInterChangeParty(InterChangePartyMapper.INSTANCE.interChangePartyDTOTointerChangeParty(tableObjDto));
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
	
	
	@PostMapping
	public ResponseEntity<APIResponse<InterChangePartyDTO>> insertInterChangeParty(@Valid @RequestBody InterChangePartyDTO interChangePartyObjDto,
			@RequestHeader Map<String, String> headers) {
		try {
			InterChangeParty interChangeParty = InterChangePartyMapper.INSTANCE.interChangePartyDTOTointerChangeParty(interChangePartyObjDto);
			InterChangeParty addInterChangeParty = interChangePartyService.insertInterChangeParty(interChangeParty, headers);
			InterChangePartyDTO addedInterChangePartyDto = InterChangePartyMapper.INSTANCE.interChangePartyTointerChangePartyDTO(addInterChangeParty);
			APIResponse<InterChangePartyDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully added data!"),
					addedInterChangePartyDto, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e) {
			APIResponse<InterChangePartyDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (Exception e) {
			APIResponse<InterChangePartyDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}

	}
}

	