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
import org.springframework.validation.annotation.Validated;
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
import com.nscorp.obis.domain.EquipmentEmbargo;
import com.nscorp.obis.dto.EquipmentEmbargoDTO;
import com.nscorp.obis.dto.mapper.EquipmentEmbargoMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.SizeExceedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.EquipmentEmbargoService;

@RestController
@RequestMapping(value = ControllerConstants.EQ_EMBARGO)
@Validated
public class EquipmentEmbargoController {
	
	@Autowired
	EquipmentEmbargoService eqEmbargoService;
	
	@GetMapping
	public ResponseEntity<APIResponse<List<EquipmentEmbargoDTO>>> getAllEqEmbargo() {
	try {
			List<EquipmentEmbargoDTO> eqEmbargoDtoList = Collections.emptyList();
			List<EquipmentEmbargo> eqEmbargoList = eqEmbargoService.getAllEmbargo();
			if (eqEmbargoList != null && !eqEmbargoList.isEmpty()) {
				eqEmbargoDtoList = eqEmbargoList.stream()
                        .map(EquipmentEmbargoMapper.INSTANCE::EquipmentEmbargoToEquipmentEmbargoDTO)
                        .collect(Collectors.toList());
            }
			 APIResponse<List<EquipmentEmbargoDTO>> listAPIResponse = new APIResponse<>(Arrays.asList("Successfully retrieved data!"), eqEmbargoDtoList, ResponseStatusCode.SUCCESS);
	            return ResponseEntity.status(HttpStatus.OK).body(listAPIResponse);
	        } catch (NoRecordsFoundException e) {
	            APIResponse<List<EquipmentEmbargoDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
	        } catch (Exception e) {
	            APIResponse<List<EquipmentEmbargoDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
	        }
		}

	
	@JsonIgnoreProperties(ignoreUnknown = true)
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<APIResponse<EquipmentEmbargoDTO>> addEqEmbargo(@Valid  @NotNull @RequestBody EquipmentEmbargoDTO eqEmabrgoDto, @RequestHeader Map<String, String> headers) {
		
		try {
			EquipmentEmbargo eqEmbargo = EquipmentEmbargoMapper.INSTANCE.EquipmentEmbargoDTOToEquipmentEmbargo(eqEmabrgoDto);
			EquipmentEmbargo eqEmbargoAdded = eqEmbargoService.addEquipEmbargo(eqEmbargo,headers);
			EquipmentEmbargoDTO addedEqEmbargo = EquipmentEmbargoMapper.INSTANCE.EquipmentEmbargoToEquipmentEmbargoDTO(eqEmbargoAdded);
			APIResponse<EquipmentEmbargoDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully added data!"), addedEqEmbargo, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e){
            APIResponse<EquipmentEmbargoDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
        } catch (SizeExceedException e){
            APIResponse<EquipmentEmbargoDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.LENGTH_REQUIRED).body(responseObj);
        } catch (RecordAlreadyExistsException e){
            APIResponse<EquipmentEmbargoDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(responseObj);
        } catch (RecordNotAddedException e){
            APIResponse<EquipmentEmbargoDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseObj);
        } catch (NullPointerException e){
            APIResponse<EquipmentEmbargoDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
        } catch (Exception e){
            APIResponse<EquipmentEmbargoDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }
	}
	
	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	 public ResponseEntity<APIResponse<EquipmentEmbargoDTO>> updateEquipmentEmbargo(@Valid @NotNull @RequestBody EquipmentEmbargoDTO eqEmabrgoDto, @RequestHeader Map<String,String> headers) {
	        try {
	        	EquipmentEmbargo equipmentEmbargo = eqEmbargoService.updateEquipmentEmbargo(
	        			EquipmentEmbargoMapper.INSTANCE.EquipmentEmbargoDTOToEquipmentEmbargo(eqEmabrgoDto), headers);
	            APIResponse<EquipmentEmbargoDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully updated data!"),
	            		EquipmentEmbargoMapper.INSTANCE.EquipmentEmbargoToEquipmentEmbargoDTO(equipmentEmbargo), ResponseStatusCode.SUCCESS);
	            return ResponseEntity.status(HttpStatus.OK).body(responseObj);
	        }catch (NoRecordsFoundException e){
	            APIResponse<EquipmentEmbargoDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
	        }catch (RecordNotAddedException e) {
	        	APIResponse<EquipmentEmbargoDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
	            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(responseObj);
			}catch (Exception e){
	            APIResponse<EquipmentEmbargoDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
	        }
	 }
	
	  @DeleteMapping
	    public ResponseEntity<List<APIResponse<EquipmentEmbargoDTO>>> deleteEquipmentEmbargo(@RequestBody List<EquipmentEmbargoDTO> equipmentEmbargoDTOList) {
	        List<APIResponse<EquipmentEmbargoDTO>> responseDTOList;
	        AtomicInteger errorCount = new AtomicInteger();
	        if (!CollectionUtils.isEmpty(equipmentEmbargoDTOList)) {
	            responseDTOList = equipmentEmbargoDTOList.stream().map(equipmentEmbargoDTO -> {
	                APIResponse<EquipmentEmbargoDTO> singleDtoDelResponse;
	                try {
	                	EquipmentEmbargo eqOverrideTareWeight = eqEmbargoService.deleteEquipmentEmbargo(EquipmentEmbargoMapper.INSTANCE.EquipmentEmbargoDTOToEquipmentEmbargo(equipmentEmbargoDTO));
	                    singleDtoDelResponse = new APIResponse<>(Arrays.asList("Successfully deleted data!"),
	                    		EquipmentEmbargoMapper.INSTANCE.EquipmentEmbargoToEquipmentEmbargoDTO(eqOverrideTareWeight),
	                            ResponseStatusCode.SUCCESS);
	                } catch (NoRecordsFoundException e) {
	                    errorCount.incrementAndGet();
	                    singleDtoDelResponse = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
	                } catch (Exception e) {
	                    errorCount.incrementAndGet();
	                    singleDtoDelResponse = new APIResponse<>(Arrays.asList(e.getMessage() + " For Embargo Id : " + equipmentEmbargoDTO.getEmbargoId()), ResponseStatusCode.FAILURE);
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
