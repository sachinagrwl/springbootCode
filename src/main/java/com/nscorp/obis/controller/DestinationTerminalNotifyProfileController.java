package com.nscorp.obis.controller;

import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.DestinationTerminalNotifyProfile;
import com.nscorp.obis.dto.DestinationTerminalNotifyProfileDTO;
import com.nscorp.obis.dto.mapper.DestinationTerminalNotifyProfileMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.SizeExceedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.DestinationTerminalNotifyProfileService;
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
public class DestinationTerminalNotifyProfileController {
	@Autowired
	DestinationTerminalNotifyProfileService service;
	
	//private static final Logger logger = LoggerFactory.getLogger(DestinationTerminalNotifyProfileController.class);
	
	@GetMapping(value= ControllerConstants.DESTINATION_TERMINAL_NOTIFY_PROFILE)
	public ResponseEntity<APIResponse<List<DestinationTerminalNotifyProfileDTO>>> getDestinationTerminalProfilesByTermId(@NotNull(message="Terminal Id should be provided.") 
	@Min(value = 1, message = "Terminal Id value must be greater than 0")
    @Digits(integer=15, fraction=0, message="Terminal Id should not have more than 15 digits. ") @RequestParam("terminalId") Long terminalId) throws SQLException{
		try {
			List<DestinationTerminalNotifyProfileDTO> notifyTerminalsDTO = Collections.emptyList();
			List<DestinationTerminalNotifyProfile> notifyTerminals = service.fetchDestinationTerminalProfilesByTermId(terminalId);
			if (notifyTerminals != null && !notifyTerminals.isEmpty()) {
				notifyTerminalsDTO = notifyTerminals.stream()
						.map(DestinationTerminalNotifyProfileMapper.INSTANCE::destinationTerminalNotifyProfileToDestinationTerminalNotifyProfileDTO)
						.collect(Collectors.toList());
			}
			APIResponse<List<DestinationTerminalNotifyProfileDTO>> responseObj = new APIResponse<>(Arrays.asList("Successfully retrieved data!"),notifyTerminalsDTO, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e){
			APIResponse<List<DestinationTerminalNotifyProfileDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (Exception e){
			APIResponse<List<DestinationTerminalNotifyProfileDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}
	
	/*This Method Is Used to Add Values*/
	@PostMapping(value = ControllerConstants.DESTINATION_TERMINAL_NOTIFY_PROFILE)
	public ResponseEntity<APIResponse<DestinationTerminalNotifyProfileDTO>> addDestinationTerminalNotifyProfile(@Valid @RequestBody DestinationTerminalNotifyProfileDTO destinationTerminalNotifyProfileDTO,
	@NotNull(message="Terminal Id should be proviced.") 
	@Min(value = 1, message = "Terminal Id value must be greater than 0") 
	@Digits(integer=15, fraction=0, message="Terminal Id should not have more than 15 digits.") @RequestParam("terminalId") Long terminalId, 
	@RequestHeader Map<String,String> headers){
		try {
			DestinationTerminalNotifyProfile destinationTerminalNotifyProfile = DestinationTerminalNotifyProfileMapper.INSTANCE.destinationTerminalNotifyProfileDTOToDestinationTerminalNotifyProfile(destinationTerminalNotifyProfileDTO);
			DestinationTerminalNotifyProfile destinationTerminalNotifyProfileAdded = service.insertDestinationTerminalNotifyProfile(destinationTerminalNotifyProfile,headers,terminalId);
			DestinationTerminalNotifyProfileDTO addedDestinationTerminalNotifyProfileDto = DestinationTerminalNotifyProfileMapper.INSTANCE.destinationTerminalNotifyProfileToDestinationTerminalNotifyProfileDTO(destinationTerminalNotifyProfileAdded);
			APIResponse<DestinationTerminalNotifyProfileDTO> responseObj  = new APIResponse<>(Arrays.asList("Successfully added data!"), addedDestinationTerminalNotifyProfileDto, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		}catch (NoRecordsFoundException e) {
			APIResponse<DestinationTerminalNotifyProfileDTO> responseObj  = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		}catch (SizeExceedException e) {
			APIResponse<DestinationTerminalNotifyProfileDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.LENGTH_REQUIRED).body(responseObj);
		}catch (RecordAlreadyExistsException e) {
			APIResponse<DestinationTerminalNotifyProfileDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(responseObj);
		}catch (RecordNotAddedException e) {
			APIResponse<DestinationTerminalNotifyProfileDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseObj);
		}catch (Exception e){
			APIResponse<DestinationTerminalNotifyProfileDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}
	
	/* This Method Is Used To Update Values */
	@PutMapping(value = ControllerConstants.DESTINATION_TERMINAL_NOTIFY_PROFILE)
	public ResponseEntity<APIResponse<DestinationTerminalNotifyProfileDTO>> updateDestinationTerminalNotifyProfile(@Valid @RequestBody DestinationTerminalNotifyProfileDTO 
			destinationTerminalNotifyProfileDTO, @RequestHeader Map<String,String> headers) {
		try {
			DestinationTerminalNotifyProfile destinationTerminalNotifyProfile = service.updateDestinationTerminalProfile
					(destinationTerminalNotifyProfileDTO, headers);
			DestinationTerminalNotifyProfileDTO DestinationTerminalNotifyProfileDTO = DestinationTerminalNotifyProfileMapper.
					INSTANCE.destinationTerminalNotifyProfileToDestinationTerminalNotifyProfileDTO(destinationTerminalNotifyProfile);
			APIResponse<DestinationTerminalNotifyProfileDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully updated data!"),DestinationTerminalNotifyProfileDTO,
					ResponseStatusCode.SUCCESS.getStatusCode(), ResponseStatusCode.SUCCESS.toString());
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e){
			APIResponse<DestinationTerminalNotifyProfileDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION.
					getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (NullPointerException e){
			APIResponse<DestinationTerminalNotifyProfileDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION.
        		  getStatusCode(), ResponseStatusCode.INFORMATION.toString());
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
      } catch (SizeExceedException e){
    	  APIResponse<DestinationTerminalNotifyProfileDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE.getStatusCode(),
        		  ResponseStatusCode.FAILURE.toString());
          return ResponseEntity.status(HttpStatus.LENGTH_REQUIRED).body(responseObj);
		} catch (Exception e){
			APIResponse<DestinationTerminalNotifyProfileDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION.
					getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}

	}

	/*This Method Is Used to Delete Values*/
	@DeleteMapping(value = ControllerConstants.DESTINATION_TERMINAL_NOTIFY_PROFILE)
	public ResponseEntity<List<APIResponse<DestinationTerminalNotifyProfileDTO>>> deleteDestinationTerminalNotifyProfileByNotifyProfileId (@Valid @NotNull 
			@RequestBody List<DestinationTerminalNotifyProfileDTO> destinationTerminalNotifyProfiles) {
		
		List<APIResponse<DestinationTerminalNotifyProfileDTO>> response;
		AtomicInteger errorCount = new AtomicInteger();
		if (destinationTerminalNotifyProfiles != null && !destinationTerminalNotifyProfiles.isEmpty()) {
			response = destinationTerminalNotifyProfiles.stream().map( destTermNotifyProfile -> {
				APIResponse<DestinationTerminalNotifyProfileDTO> singleDtoDelResponse;
			try {
				service.deleteDestinationTerminalProfile(DestinationTerminalNotifyProfileMapper.INSTANCE.destinationTerminalNotifyProfileDTOToDestinationTerminalNotifyProfile(destTermNotifyProfile));
				singleDtoDelResponse = new APIResponse<>(Arrays.asList("Successfully deleted data!"),destTermNotifyProfile, ResponseStatusCode.SUCCESS);
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
