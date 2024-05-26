package com.nscorp.obis.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.CodeTableSelection;
import com.nscorp.obis.dto.CodeTableSelectionDTO;
import com.nscorp.obis.dto.mapper.CodeTableSelectionMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.SizeExceedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.CodeTableSelectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@RestController

//@CrossOrigin(origins="http://localhost:4200")
@RequestMapping("/")
public class CodeTableSelectionController {
	@Autowired
	CodeTableSelectionService genericService;
		
//	private static final Logger logger = LoggerFactory.getLogger(CodeTableSelectionController.class);
	
	@GetMapping(value= ControllerConstants.GENERIC_TABLE_LIST)
	public ResponseEntity<APIResponse<List<CodeTableSelectionDTO>>> getAllGenericTables(){
		
		try {
			List<CodeTableSelectionDTO> tablesDtoList = Collections.emptyList();
			List<CodeTableSelection> tablesList = genericService.getAllTables();
			if (tablesList != null && !tablesList.isEmpty()) {
				tablesDtoList = tablesList.stream()
						.map(CodeTableSelectionMapper.INSTANCE::codeTableSelectionToCodeTableSelectionDTO)
						.collect(Collectors.toList());
			}
			APIResponse<List<CodeTableSelectionDTO>> responseObj = new APIResponse<>(Arrays.asList("Successfully retrieved data!"),tablesDtoList, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e){
			APIResponse<List<CodeTableSelectionDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (Exception e){
			APIResponse<List<CodeTableSelectionDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	
	}
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	@PostMapping(value= ControllerConstants.GENERIC_TABLE_LIST)
	public ResponseEntity<APIResponse<CodeTableSelectionDTO>> addTable(@Valid @RequestBody CodeTableSelectionDTO codeObjDto, @RequestHeader Map<String,String> headers){
		try {
			CodeTableSelection table = CodeTableSelectionMapper.INSTANCE.codeTableSelectionDTOToCodeTableSelection(codeObjDto);
			
			CodeTableSelection addTableInfo = genericService.insertTable(table, headers);
			CodeTableSelectionDTO addedTableDto = CodeTableSelectionMapper.INSTANCE.codeTableSelectionToCodeTableSelectionDTO(addTableInfo);
			APIResponse<CodeTableSelectionDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully added data!"),addedTableDto, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e){
			APIResponse<CodeTableSelectionDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (SizeExceedException e){
			APIResponse<CodeTableSelectionDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.LENGTH_REQUIRED).body(responseObj);
		} catch (RecordAlreadyExistsException e){
			APIResponse<CodeTableSelectionDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(responseObj);
		} catch (RecordNotAddedException e){
			APIResponse<CodeTableSelectionDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseObj);
		} catch (NullPointerException e){
			APIResponse<CodeTableSelectionDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
		} catch (Exception e){
			APIResponse<CodeTableSelectionDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}
	
	
	@PutMapping(value = ControllerConstants.GENERIC_TABLE_LIST)
	public ResponseEntity<APIResponse<CodeTableSelectionDTO>> updateTable(@Valid @RequestBody CodeTableSelectionDTO tableObjDto, @RequestHeader Map<String,String> headers) {

		try {
			CodeTableSelection tableObj = CodeTableSelectionMapper.INSTANCE.codeTableSelectionDTOToCodeTableSelection(tableObjDto);
			CodeTableSelection updatedTables = genericService.updateCodeTableSelection(tableObj, headers);
			CodeTableSelectionDTO updatedTablesDto = CodeTableSelectionMapper.INSTANCE.codeTableSelectionToCodeTableSelectionDTO(updatedTables);
			APIResponse<CodeTableSelectionDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully updated data!"),updatedTablesDto, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e){
			APIResponse<CodeTableSelectionDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (NullPointerException e){
			APIResponse<CodeTableSelectionDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
		} catch (SizeExceedException e){
			APIResponse<CodeTableSelectionDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.LENGTH_REQUIRED).body(responseObj);
		} catch (Exception e){
			APIResponse<CodeTableSelectionDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}


	@DeleteMapping(value = ControllerConstants.GENERIC_TABLE_LIST)
	public ResponseEntity<List<APIResponse<CodeTableSelectionDTO>>> deleteGenericTable(@Valid @RequestBody List<CodeTableSelectionDTO> tableObjDtoList){

		List<APIResponse<CodeTableSelectionDTO>> response;
		AtomicInteger errorCount = new AtomicInteger();
		if (tableObjDtoList != null && !tableObjDtoList.isEmpty()) {
				response = tableObjDtoList.stream().map( tableObjDto -> {
					APIResponse<CodeTableSelectionDTO> singleDtoDelResponse;
					try {
						genericService.deleteTable(CodeTableSelectionMapper.INSTANCE.codeTableSelectionDTOToCodeTableSelection(tableObjDto));
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
