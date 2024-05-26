package com.nscorp.obis.controller;

import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.TerminalTrain;
import com.nscorp.obis.dto.TerminalTrainDTO;
import com.nscorp.obis.dto.mapper.TerminalTrainMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.SizeExceedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.TerminalTrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@CrossOrigin
@RequestMapping("/")
public class TerminalTrainController {
	
	@Autowired
	TerminalTrainService terminalTrainService;
	
		
	//private static final Logger logger = LoggerFactory.getLogger(TerminalTrainController.class);
	
	@GetMapping(value= ControllerConstants.GET_TERMINAL_TRAINS)
	public ResponseEntity<APIResponse<List<TerminalTrainDTO>>> getAllTerminalTrains(){
		
		try {
			List<TerminalTrainDTO> terminalTrainDtoList = Collections.emptyList();
			List<TerminalTrain> terminalTrainList = terminalTrainService.getAllTerminalTrains();
			if (terminalTrainList != null && ! terminalTrainList.isEmpty()) {
				terminalTrainDtoList = terminalTrainList.stream()
						.map(TerminalTrainMapper.INSTANCE::terminalTrainToTerminalTrainDTO)
						.collect(Collectors.toList());
			}
			
			APIResponse<List<TerminalTrainDTO>> responseObj = new APIResponse<>(Arrays.asList("Successfully retrieved data!"),terminalTrainDtoList, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e){
			APIResponse<List<TerminalTrainDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (Exception e){
			APIResponse<List<TerminalTrainDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
			
	}
	
	
	/* This Method Is Used To Update Train Desc */
	
	@PutMapping(value= ControllerConstants.UPDATE_TERM_TRAIN)
	public ResponseEntity<APIResponse<TerminalTrainDTO>> updateTrainDesc(@Valid @NotNull @RequestBody TerminalTrainDTO trainDescDto, @RequestHeader Map<String,String> headers) {
		try {
			TerminalTrain trainDesc = TerminalTrainMapper.INSTANCE.terminalTrainDTOToTerminalTrain(trainDescDto);
			TerminalTrain trainDescAdded = terminalTrainService.updateTrainDesc(trainDesc, headers);
			TerminalTrainDTO updateTrainDesc = TerminalTrainMapper.INSTANCE.terminalTrainToTerminalTrainDTO(trainDescAdded);
			APIResponse<TerminalTrainDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully updated data!"),updateTrainDesc, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e){
			APIResponse<TerminalTrainDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (NullPointerException e){
			APIResponse<TerminalTrainDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
		} catch (SizeExceedException e){
			APIResponse<TerminalTrainDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.LENGTH_REQUIRED).body(responseObj);
		} catch (Exception e){
			APIResponse<TerminalTrainDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}
	
	
	@DeleteMapping(value= ControllerConstants.DELETE_TERMINAL_TRAINS) 
	  public ResponseEntity<APIResponse<TerminalTrainDTO>> deleteTrain(@RequestBody
	  TerminalTrainDTO terminalTrainDTO){
		APIResponse<TerminalTrainDTO> singleDtoDelResponse;
		AtomicInteger errorCount = new AtomicInteger();
					try {
						TerminalTrain terminalTrain = TerminalTrainMapper.INSTANCE.terminalTrainDTOToTerminalTrain(terminalTrainDTO);
						terminalTrainService.deleteTrain(terminalTrain);
						singleDtoDelResponse = new APIResponse<>(Arrays.asList("Successfully deleted data!"),terminalTrainDTO, ResponseStatusCode.SUCCESS);
					} catch (Exception e) {
						errorCount.incrementAndGet();
						singleDtoDelResponse = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
					}

		if (errorCount.get() == 0) { // No errors and atleast 1 success
			return ResponseEntity.status(HttpStatus.OK).body(singleDtoDelResponse);
		}
		 else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(singleDtoDelResponse);
		}
		
	}
	
	
	
	
}
		
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
		
	

