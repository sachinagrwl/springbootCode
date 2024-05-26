package com.nscorp.obis.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.TerminalFunction;
import com.nscorp.obis.dto.TerminalFunctionDTO;
import com.nscorp.obis.dto.mapper.TerminalFunctionMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.TerminalFunctionService;

@RestController
@CrossOrigin
@RequestMapping("/")
public class TerminalFunctionController {
	
	@Autowired
	TerminalFunctionService terminalFunctionService;


	@GetMapping(value = ControllerConstants.TERMINAL_FUNCTION)
	public ResponseEntity<APIResponse<List<TerminalFunctionDTO>>> getTerminalFunction(
        @RequestParam(name = "term-id", required = false) Long terminalId,
        @RequestParam(name = "function-name", required = false) String functionName){
		
		try {
			List<TerminalFunctionDTO> termFunctionDtoList = Collections.emptyList();

			List<TerminalFunction> termFunction = terminalFunctionService.getTerminalFunctionList(terminalId, functionName);
			if (termFunction != null && !termFunction.isEmpty()) {
				termFunctionDtoList = termFunction.stream().map(TerminalFunctionMapper.INSTANCE::terminalFunctionToTerminalFunctionDTO)
						.collect(Collectors.toList());
			}
			APIResponse<List<TerminalFunctionDTO>> responseObj = new APIResponse<>(
					Arrays.asList("Successfully retrieved data!"), termFunctionDtoList,
					ResponseStatusCode.SUCCESS.getStatusCode(), ResponseStatusCode.SUCCESS.toString());
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
			
		} catch (NoRecordsFoundException e) {
			APIResponse<List<TerminalFunctionDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (Exception e) {
			APIResponse<List<TerminalFunctionDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}
	

	
	@PutMapping(value = ControllerConstants.TERMINAL_FUNCTION)
	public ResponseEntity<APIResponse<TerminalFunctionDTO>> updateTerminalFunction(@Valid @RequestBody TerminalFunctionDTO terminalFunctionDtoObj,
			@RequestHeader Map<String, String> headers) {
		try {
			TerminalFunction terminalFunction = TerminalFunctionMapper.INSTANCE.terminalFunctionDTOToTerminalFunction(terminalFunctionDtoObj);
			TerminalFunction updateTerminalFunction = terminalFunctionService.updateTerminalFunction(terminalFunction, headers);
			TerminalFunctionDTO updatedTerminalFunctionDto = TerminalFunctionMapper.INSTANCE.terminalFunctionToTerminalFunctionDTO(updateTerminalFunction);
			APIResponse<TerminalFunctionDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully updated data!"),
					updatedTerminalFunctionDto, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e) {
			APIResponse<TerminalFunctionDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (RecordAlreadyExistsException e) {
			APIResponse<TerminalFunctionDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(responseObj);
		} catch (Exception e) {
			APIResponse<TerminalFunctionDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}
}
