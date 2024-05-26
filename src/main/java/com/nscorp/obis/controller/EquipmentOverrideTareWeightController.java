package com.nscorp.obis.controller;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.EquipmentOverrideTareWeight;
import com.nscorp.obis.dto.EquipmentOverrideTareWeightDTO;
import com.nscorp.obis.dto.mapper.EquipmentOverrideTareWeightMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.SizeExceedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.EquipmentOverrideTareWeightService;



@RestController
@RequestMapping(value = ControllerConstants.EQ_OVERRIDE_TARE_WGT)
@Validated
public class EquipmentOverrideTareWeightController {

    @Autowired
    EquipmentOverrideTareWeightService eqOverrideService;

    @GetMapping
    public ResponseEntity<APIResponse<List<EquipmentOverrideTareWeightDTO>>> getAllOverrideWeights(@RequestParam(required = false, name = "eq-init") String equipmentInit,
                                                                                                   @RequestParam(required = false, name = "eq-nr-low") BigDecimal equipmentNumberLow,
                                                                                                   @RequestParam(required = false, name = "eq-nr-high") BigDecimal equipmentNumberHigh,
                                                                                                   @RequestParam(required = false, name = "eq-type") String equipmentType,
                                                                                                   @RequestParam(required = false, name = "override-tare-weight") Integer overrideTareWeight) {
        try {
            List<EquipmentOverrideTareWeightDTO> eqOverrideDtoList = Collections.emptyList();
            List<EquipmentOverrideTareWeight> eqOverrideList = eqOverrideService.getAllTareWeights(equipmentInit, equipmentNumberLow, equipmentNumberHigh, equipmentType, overrideTareWeight);
            if (eqOverrideList != null && !eqOverrideList.isEmpty()) {
                eqOverrideDtoList = eqOverrideList.stream()
                        .map(EquipmentOverrideTareWeightMapper.INSTANCE::EquipmentOverrideTareWeightToEquipmentOverrideTareWeightDTO)
                        .collect(Collectors.toList());
            }
            APIResponse<List<EquipmentOverrideTareWeightDTO>> listAPIResponse = new APIResponse<>(Arrays.asList("Successfully retrieved data!"), eqOverrideDtoList, ResponseStatusCode.SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(listAPIResponse);
        } catch (NoRecordsFoundException e) {
            APIResponse<List<EquipmentOverrideTareWeightDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
        } catch (Exception e) {
            APIResponse<List<EquipmentOverrideTareWeightDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }
    }

    @DeleteMapping
    public ResponseEntity<List<APIResponse<EquipmentOverrideTareWeightDTO>>> deleteOverrideWeights(@RequestBody List<EquipmentOverrideTareWeightDTO> overrideTareWeightDTOList) {
        List<APIResponse<EquipmentOverrideTareWeightDTO>> responseDTOList;
        AtomicInteger errorCount = new AtomicInteger();
        if (!CollectionUtils.isEmpty(overrideTareWeightDTOList)) {
            responseDTOList = overrideTareWeightDTOList.stream().map(overrideTareWeightDTO -> {
                APIResponse<EquipmentOverrideTareWeightDTO> singleDtoDelResponse;
                try {
                    EquipmentOverrideTareWeight eqOverrideTareWeight = eqOverrideService.deleteOverrideWeights(EquipmentOverrideTareWeightMapper.INSTANCE.EquipmentOverrideTareWeightDTOToEquipmentOverrideTareWeight(overrideTareWeightDTO));
                    singleDtoDelResponse = new APIResponse<>(Arrays.asList("Successfully deleted data!"),
                            EquipmentOverrideTareWeightMapper.INSTANCE.EquipmentOverrideTareWeightToEquipmentOverrideTareWeightDTO(eqOverrideTareWeight),
                            ResponseStatusCode.SUCCESS);
                } catch (NoRecordsFoundException e) {
                    errorCount.incrementAndGet();
                    singleDtoDelResponse = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
                } catch (Exception e) {
                    errorCount.incrementAndGet();
                    singleDtoDelResponse = new APIResponse<>(Arrays.asList(e.getMessage() + " For Override Id : " + overrideTareWeightDTO.getOverrideId()), ResponseStatusCode.FAILURE);
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

	
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<APIResponse<EquipmentOverrideTareWeightDTO>> addEqOverride(@Valid  @NotNull @RequestBody EquipmentOverrideTareWeightDTO eqTareDto, @RequestHeader Map<String, String> headers) {
		
		try {
			EquipmentOverrideTareWeight eqTareWgt = EquipmentOverrideTareWeightMapper.INSTANCE.EquipmentOverrideTareWeightDTOToEquipmentOverrideTareWeight(eqTareDto);
			EquipmentOverrideTareWeight eqTareWgtAdded = eqOverrideService.addOverrideTareWeight(eqTareWgt, headers);
			EquipmentOverrideTareWeightDTO addedEqTareWgt = EquipmentOverrideTareWeightMapper.INSTANCE.EquipmentOverrideTareWeightToEquipmentOverrideTareWeightDTO(eqTareWgtAdded);
			APIResponse<EquipmentOverrideTareWeightDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully added data!"),addedEqTareWgt, ResponseStatusCode.SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e){
            APIResponse<EquipmentOverrideTareWeightDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
        } catch (SizeExceedException e){
            APIResponse<EquipmentOverrideTareWeightDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.LENGTH_REQUIRED).body(responseObj);
        } catch (RecordAlreadyExistsException e){
            APIResponse<EquipmentOverrideTareWeightDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(responseObj);
        } catch (RecordNotAddedException e){
            APIResponse<EquipmentOverrideTareWeightDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseObj);
        } catch (NullPointerException e){
            APIResponse<EquipmentOverrideTareWeightDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
        } catch (Exception e){
            APIResponse<EquipmentOverrideTareWeightDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }
	}
	
	@PutMapping
	 public ResponseEntity<APIResponse<EquipmentOverrideTareWeightDTO>> updateEquipmentOverrideTareWeight(@Valid @NotNull @RequestBody EquipmentOverrideTareWeightDTO equipmentOverrideTareWeightDTO, @RequestHeader Map<String,String> headers) {
	        try {
	            EquipmentOverrideTareWeight eqOverrideTareWeightUpdated = eqOverrideService.updateEquipmentOverrideTareWeight(
	            		EquipmentOverrideTareWeightMapper.INSTANCE.EquipmentOverrideTareWeightDTOToEquipmentOverrideTareWeight(equipmentOverrideTareWeightDTO), headers);
	            APIResponse<EquipmentOverrideTareWeightDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully updated data!"),
	            		EquipmentOverrideTareWeightMapper.INSTANCE.EquipmentOverrideTareWeightToEquipmentOverrideTareWeightDTO(eqOverrideTareWeightUpdated), ResponseStatusCode.SUCCESS);
	            return ResponseEntity.status(HttpStatus.OK).body(responseObj);
	        }catch (NoRecordsFoundException e){
	            APIResponse<EquipmentOverrideTareWeightDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
	        }catch (RecordNotAddedException e) {
	        	APIResponse<EquipmentOverrideTareWeightDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
	            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(responseObj);
			}catch (Exception e){
	            APIResponse<EquipmentOverrideTareWeightDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
	        }
	 }
}
