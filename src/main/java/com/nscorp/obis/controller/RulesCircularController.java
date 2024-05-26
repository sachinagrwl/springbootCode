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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.RulesCircular;
import com.nscorp.obis.dto.RulesCircularDTO;
import com.nscorp.obis.dto.mapper.RulesCircularMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.SizeExceedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.RulesCircularService;

@RestController
@RequestMapping(ControllerConstants.RULES_CIRCULAR)
public class RulesCircularController {

    @Autowired
    RulesCircularService rulesCircularService;

    @GetMapping
    public ResponseEntity<APIResponse<List<RulesCircularDTO>>> getAllRules(){
        try {
            List<RulesCircularDTO> rulesCircularDTOList = Collections.emptyList();
            List<RulesCircular> rulesCircularList = rulesCircularService.getAllRulesCircular();
            if(rulesCircularList != null && !rulesCircularList.isEmpty()){
                rulesCircularDTOList = rulesCircularList.stream()
                        .map(RulesCircularMapper.INSTANCE::RulesCircularToRulesCircularDTO)
                        .collect(Collectors.toList());
            }
            APIResponse<List<RulesCircularDTO>> listAPIResponse = new APIResponse<>(Arrays.asList("Successfully retrieved data!"), rulesCircularDTOList, ResponseStatusCode.SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(listAPIResponse);
        } catch (NoRecordsFoundException e) {
            APIResponse<List<RulesCircularDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
        } catch (Exception e) {
            APIResponse<List<RulesCircularDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponse<RulesCircularDTO>> addRulesCircular(@Valid @NotNull @RequestBody RulesCircularDTO rulesCircularDTO, @RequestHeader Map<String,String> headers) {
        try {
            RulesCircular rulesCircular = RulesCircularMapper.INSTANCE.RulesCircularDTOToRulesCircular(rulesCircularDTO);
            RulesCircular rulesCircularAdded = rulesCircularService.addRulesCircular(rulesCircular, headers);
            RulesCircularDTO addedRulesCircular = RulesCircularMapper.INSTANCE.RulesCircularToRulesCircularDTO(rulesCircularAdded);
            APIResponse<RulesCircularDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully added data!"),addedRulesCircular, ResponseStatusCode.SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(responseObj);
        } catch (NoRecordsFoundException e){
            APIResponse<RulesCircularDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
        } catch (SizeExceedException e){
            APIResponse<RulesCircularDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.LENGTH_REQUIRED).body(responseObj);
        } catch (RecordAlreadyExistsException e){
            APIResponse<RulesCircularDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(responseObj);
        } catch (RecordNotAddedException e){
            APIResponse<RulesCircularDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseObj);
        } catch (NullPointerException e){
            APIResponse<RulesCircularDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
        } catch (Exception e){
            APIResponse<RulesCircularDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }
    }

    @PutMapping
    public ResponseEntity<APIResponse<RulesCircularDTO>> updateRulesCircular(@Valid @NotNull @RequestBody RulesCircularDTO rulesCircularDTO, @RequestHeader Map<String,String> headers) {
        try {
            RulesCircular rulesCircular = RulesCircularMapper.INSTANCE.RulesCircularDTOToRulesCircular(rulesCircularDTO);
            RulesCircular rulesCircularUpdated = rulesCircularService.updateRulesCircular(rulesCircular, headers);
            RulesCircularDTO updateRulesCircular = RulesCircularMapper.INSTANCE.RulesCircularToRulesCircularDTO(rulesCircularUpdated);
            APIResponse<RulesCircularDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully updated data!"),updateRulesCircular, ResponseStatusCode.SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(responseObj);
        } catch (NoRecordsFoundException e){
            APIResponse<RulesCircularDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
        } catch (NullPointerException e){
            APIResponse<RulesCircularDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
        } catch (SizeExceedException e){
            APIResponse<RulesCircularDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.LENGTH_REQUIRED).body(responseObj);
        } catch (Exception e){
            APIResponse<RulesCircularDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }
    }
    
    @DeleteMapping
	public ResponseEntity<List<APIResponse<RulesCircularDTO>>> deleteRulesCircular(@Valid @RequestBody List<RulesCircularDTO> dtoListToBeDeleted) {
    	List<APIResponse<RulesCircularDTO>> responseDTOList;
    	AtomicInteger errorCount = new AtomicInteger();
    	if(!CollectionUtils.isEmpty(dtoListToBeDeleted)) {
    		responseDTOList = dtoListToBeDeleted.stream().map(dto -> {
				APIResponse<RulesCircularDTO> singleDtoDelResponse;
				try {
						RulesCircular deleteRulesCircular = rulesCircularService.deleteRulesCircular(RulesCircularMapper.INSTANCE.RulesCircularDTOToRulesCircular(dto));
						singleDtoDelResponse = new APIResponse<>(Arrays.asList("Successfully deleted data!"), 
								RulesCircularMapper.INSTANCE.RulesCircularToRulesCircularDTO(deleteRulesCircular),
								ResponseStatusCode.SUCCESS);
					} catch(NoRecordsFoundException e) {
						errorCount.incrementAndGet();
						singleDtoDelResponse = new APIResponse<>(Arrays.asList(e.getMessage()),ResponseStatusCode.FAILURE);
					} catch (Exception e) {
						errorCount.incrementAndGet();
						singleDtoDelResponse = new APIResponse<>(
								Arrays.asList(e.getMessage()
												+ " For Equipment Type: "+ dto.getEquipmentType()
												+ " and Equipment Length: "+ dto.getEquipmentLength()
											),ResponseStatusCode.FAILURE);
					}
				return singleDtoDelResponse;
			}).collect(Collectors.toList());
    	} else {
    		responseDTOList = Collections.emptyList();
    	}
    	
    	if (errorCount.get() == 0 && responseDTOList.size() > 0) // No errors and at least 1 success
			return ResponseEntity.status(HttpStatus.OK).body(responseDTOList);
		else if (responseDTOList.size() > errorCount.get()) // Partial success
			return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(responseDTOList);
		else
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTOList);
    }
}
