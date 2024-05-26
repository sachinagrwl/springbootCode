package com.nscorp.obis.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.TerminalNotifyProfile;
import com.nscorp.obis.dto.TerminalNotifyProfileDTO;
import com.nscorp.obis.dto.mapper.TerminalNotifyProfileMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.SizeExceedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.TerminalNotifyProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@RestController
@Validated
@RequestMapping("/")
public class TerminalNotifyProfileController {
	@Autowired
	TerminalNotifyProfileService service;
	
//	private static final Logger logger = LoggerFactory.getLogger(TerminalNotifyProfileController.class);
	
	@GetMapping(value= ControllerConstants.TERMINAL_NOTIFY_PROFILES+"/{termId}")
	public ResponseEntity<APIResponse<List<TerminalNotifyProfileDTO>>> getTerminalProfilesByTermId(@NotNull(message="Terminal Id should be provided.") 
	@Min(value = 1, message = "Terminal Id value must be greater than 0")
    @Digits(integer=15, fraction=0, message="Terminal Id should not have more than 15 digits. ") @PathVariable("termId") Long terminalId) throws SQLException{
		try{
			List<TerminalNotifyProfileDTO> notifyTerminalsDTO = Collections.emptyList();
		List<TerminalNotifyProfile> notifyTerminals= service.fetchTerminalProfilesByTermId(terminalId);
		if (notifyTerminals != null && !notifyTerminals.isEmpty()) {
			notifyTerminalsDTO = notifyTerminals.stream()
					.map(TerminalNotifyProfileMapper.INSTANCE::terminalNotifyProfileToTerminalNotifyProfileDTO)
					.collect(Collectors.toList());
		}
		APIResponse<List<TerminalNotifyProfileDTO>> responseObj = new APIResponse<>(Arrays.asList("Successfully retrieved data!"),notifyTerminalsDTO, ResponseStatusCode.SUCCESS);
		return ResponseEntity.status(HttpStatus.OK).body(responseObj);
	} catch (NoRecordsFoundException e){
		APIResponse<List<TerminalNotifyProfileDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
	} catch (Exception e){
		APIResponse<List<TerminalNotifyProfileDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
	}
}
	
	/*This Method Is Used To Add Values */
	@JsonIgnoreProperties(ignoreUnknown = true)
	@PostMapping(value= ControllerConstants.TERMINAL_NOTIFY_PROFILES)
	public ResponseEntity<APIResponse<TerminalNotifyProfileDTO>> addTerminalNotifyProfile(@Valid @NotNull @RequestBody TerminalNotifyProfileDTO terminalnotifyprofiledto,
			@Digits(integer=15, fraction=0, message="Terminal Id should not have more than 15 digits. ")
	        @Min(value = 0, message = "Terminal Id value must be greater than or equal to 0")
	        @RequestParam(value="terminalId", required=false) Long terminalId,
			@RequestHeader Map<String,String> headers) {
		try {
			TerminalNotifyProfile terminalnotifyprofile = TerminalNotifyProfileMapper.INSTANCE.terminalNotifyProfileDTOToTerminalNotifyProfile(terminalnotifyprofiledto);
			TerminalNotifyProfile terminalnotifyprofileAdded = service.insertTerminalNotifyProfile(terminalnotifyprofile,headers,terminalId);
			TerminalNotifyProfileDTO addedNotifyTerminalDto = TerminalNotifyProfileMapper.INSTANCE.terminalNotifyProfileToTerminalNotifyProfileDTO(terminalnotifyprofileAdded);
			APIResponse<TerminalNotifyProfileDTO> responseObj  = new APIResponse<>(Arrays.asList("Successfully added data!"), addedNotifyTerminalDto, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		}catch (NoRecordsFoundException e) {
			APIResponse<TerminalNotifyProfileDTO> responseObj  = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		}catch (SizeExceedException e) {
			APIResponse<TerminalNotifyProfileDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.LENGTH_REQUIRED).body(responseObj);
		}catch (RecordAlreadyExistsException e) {
			APIResponse<TerminalNotifyProfileDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(responseObj);
		}catch (RecordNotAddedException e) {
			APIResponse<TerminalNotifyProfileDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseObj);
		}catch (Exception e){
			APIResponse<TerminalNotifyProfileDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

	/* This Method Is Used To Update Values */
	@PutMapping(value = ControllerConstants.UPDATE_TERMINAL_NOTIFY_PROFILES+"/{prfId}")
	public ResponseEntity<APIResponse<TerminalNotifyProfileDTO>> updateTerminalNotifyProfileByProfileId(@Valid @RequestBody TerminalNotifyProfileDTO terminalNotifyProfileDTO , @PathVariable("prfId") Long prfId, @RequestHeader Map<String,String> headers) {
		try {
			TerminalNotifyProfile terminalNotifyProfile = service.updateTerminalProfileByProfileId(terminalNotifyProfileDTO, headers);
		    TerminalNotifyProfileDTO terminalNotifyProfileDto = TerminalNotifyProfileMapper.INSTANCE.terminalNotifyProfileToTerminalNotifyProfileDTO(terminalNotifyProfile);
			APIResponse<TerminalNotifyProfileDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully updated data!"),terminalNotifyProfileDto,
					ResponseStatusCode.SUCCESS.getStatusCode(), ResponseStatusCode.SUCCESS.toString());
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e){
			APIResponse<TerminalNotifyProfileDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION.
					getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (NullPointerException e){
			APIResponse<TerminalNotifyProfileDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION.
        		  getStatusCode(), ResponseStatusCode.INFORMATION.toString());
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
      } catch (SizeExceedException e){
    	  APIResponse<TerminalNotifyProfileDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE.getStatusCode(),
        		  ResponseStatusCode.FAILURE.toString());
          return ResponseEntity.status(HttpStatus.LENGTH_REQUIRED).body(responseObj);
		} catch (Exception e){
			APIResponse<TerminalNotifyProfileDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION.
					getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}
	
	@DeleteMapping(value = ControllerConstants.TERMINAL_NOTIFY_PROFILES)
	public ResponseEntity<List<APIResponse<TerminalNotifyProfileDTO>>> deleteTerminalNotifyProfileByNotifyProfileId (@Valid @NotNull 
			@RequestBody List<TerminalNotifyProfileDTO> terminalNotifyProfiles) {
		
		List<APIResponse<TerminalNotifyProfileDTO>> response;
		AtomicInteger errorCount = new AtomicInteger();
		if (terminalNotifyProfiles != null && !terminalNotifyProfiles.isEmpty()) {
			response = terminalNotifyProfiles.stream().map( terminalNotifyProfileDto -> {
				APIResponse<TerminalNotifyProfileDTO> singleDtoDelResponse;
			try {
				service.deleteTerminalProfile(TerminalNotifyProfileMapper.INSTANCE.terminalNotifyProfileDTOToTerminalNotifyProfile(terminalNotifyProfileDto));
				singleDtoDelResponse = new APIResponse<>(Arrays.asList("Successfully deleted data!"),terminalNotifyProfileDto, ResponseStatusCode.SUCCESS);
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
