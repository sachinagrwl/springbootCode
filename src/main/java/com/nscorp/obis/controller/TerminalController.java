package com.nscorp.obis.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.Terminal;
import com.nscorp.obis.dto.TerminalDTO;
import com.nscorp.obis.dto.mapper.TerminalMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.TerminalService;

@Validated
@RestController
@CrossOrigin
public class TerminalController {

	@Autowired
	TerminalService terminalService;
	
	@PutMapping(value = ControllerConstants.TERMINAL)
	public ResponseEntity<APIResponse<TerminalDTO>> updateTerminal(@Valid @NotNull @RequestBody TerminalDTO terminalDTO, @RequestHeader Map<String,String> headers) {
		try {
			Terminal terminal = TerminalMapper.INSTANCE.terminalDTOToTerminal(terminalDTO);
			Terminal terminalAdded = terminalService.updateTerminal(terminal, headers);
			TerminalDTO updateTerminal = TerminalMapper.INSTANCE.terminalToTerminalDTO(terminalAdded);
			APIResponse<TerminalDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully updated data!"),updateTerminal, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
            
        } catch (NullPointerException e){
			APIResponse<TerminalDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
		} catch (RecordAlreadyExistsException e){
			APIResponse<TerminalDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(responseObj);
		} catch (RecordNotAddedException e){
			APIResponse<TerminalDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseObj);
		
		} catch (NoRecordsFoundException e){
            APIResponse<TerminalDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
        }catch (Exception e){
			APIResponse<TerminalDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
}
		
	@JsonIgnoreProperties(ignoreUnknown = true)
	@PostMapping(value = ControllerConstants.TERMINAL)
	public ResponseEntity<APIResponse<TerminalDTO>> addTerminal(@Valid @NotNull @RequestBody TerminalDTO terminalDto, @RequestHeader Map<String,String> headers){
		try {
			
			Terminal terminal = TerminalMapper.INSTANCE.terminalDTOToTerminal(terminalDto);
			
			Terminal addedTerminal = terminalService.insertTerminal(terminal, headers);
			TerminalDTO addedTerminalDTO = TerminalMapper.INSTANCE.terminalToTerminalDTO(addedTerminal);
			APIResponse<TerminalDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully Added Data!"),addedTerminalDTO, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NullPointerException e){
			APIResponse<TerminalDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
		} catch (RecordAlreadyExistsException e){
			APIResponse<TerminalDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(responseObj);
		} catch (RecordNotAddedException e){
			APIResponse<TerminalDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseObj);
		
		} catch (NoRecordsFoundException e){
            APIResponse<TerminalDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
        }catch (Exception e){
			APIResponse<TerminalDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}

}
	
	@GetMapping(value= ControllerConstants.GET_TERMINAL)
	public ResponseEntity<APIResponse<List<TerminalDTO>>> getTerminal(@RequestParam("term-id") List<Long> termid)
	{
		
		try {
			List<TerminalDTO> terminalDtoList = Collections.emptyList();
			List<Terminal> terminalList = termid.stream().map(al->terminalService.getTerminal(al)).collect(Collectors.toList());
			
			if (terminalList != null && ! terminalList.isEmpty()) {
				terminalDtoList = terminalList.stream().map(TerminalMapper.INSTANCE::terminalToTerminalDTO)
						.collect(Collectors.toList());
			}
			
			APIResponse<List<TerminalDTO>> responseObj = new APIResponse<>(Arrays.asList("Successfully retrieved data!"),terminalDtoList, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e){
			APIResponse<List<TerminalDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (Exception e){
			APIResponse<List<TerminalDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
			
	}
	
	
	
}
