package com.nscorp.obis.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.EquipmentAAR600Cont;
import com.nscorp.obis.dto.EquipmentAAR600ContDTO;
import com.nscorp.obis.dto.mapper.EquipmentAAR600ContMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.SizeExceedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.EquipmentAAR600ContService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(ControllerConstants.EQUIPMENT_AAR_600_CONT)
public class EquipmentAAR600ContController {


    @Autowired
    EquipmentAAR600ContService equipmentAAR600ContService;

    @GetMapping
    public ResponseEntity<APIResponse<List<EquipmentAAR600ContDTO>>> getAllAarCont() {
        try {
            List<EquipmentAAR600ContDTO> equipmentAAR600ContDTOList = Collections.emptyList();
            List<EquipmentAAR600Cont> equipmentAAR600Conts = equipmentAAR600ContService.getAllCont();
            if (equipmentAAR600Conts != null && !equipmentAAR600Conts.isEmpty()) {
                equipmentAAR600ContDTOList = equipmentAAR600Conts.stream()
                        .map(EquipmentAAR600ContMapper.INSTANCE::EquipmentAAR600ContToEquipmentAAR600ContDTO)
                        .collect(Collectors.toList());
            }
            APIResponse<List<EquipmentAAR600ContDTO>> listAPIResponse = new APIResponse<>(Arrays.asList("Successfully retrieved data!"), equipmentAAR600ContDTOList, ResponseStatusCode.SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(listAPIResponse);
        } catch (NoRecordsFoundException e) {
            APIResponse<List<EquipmentAAR600ContDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
        } catch (Exception e) {
            APIResponse<List<EquipmentAAR600ContDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }
    }
    
    @JsonIgnoreProperties(ignoreUnknown = true)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponse<EquipmentAAR600ContDTO>> addEqCont(@Valid @NotNull @NotBlank @RequestBody EquipmentAAR600ContDTO eqContObj, @RequestHeader Map<String, String> headers) {
    	
    	try {
    		EquipmentAAR600Cont eqCont = EquipmentAAR600ContMapper.INSTANCE.EquipmentAAR600ContDTOToEquipmentAAR600Cont(eqContObj);
    		EquipmentAAR600Cont addEqCont = equipmentAAR600ContService.addEqCont(eqCont, headers);
    		EquipmentAAR600ContDTO eqContDto = EquipmentAAR600ContMapper.INSTANCE.EquipmentAAR600ContToEquipmentAAR600ContDTO(addEqCont);
    		APIResponse<EquipmentAAR600ContDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully added data!"), eqContDto, ResponseStatusCode.SUCCESS);
    		return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e){
			APIResponse<EquipmentAAR600ContDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (SizeExceedException e){
			APIResponse<EquipmentAAR600ContDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.LENGTH_REQUIRED).body(responseObj);
		} catch (RecordAlreadyExistsException e){
			APIResponse<EquipmentAAR600ContDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(responseObj);
		} catch (RecordNotAddedException e){
			APIResponse<EquipmentAAR600ContDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseObj);
		} catch (NullPointerException e){
			APIResponse<EquipmentAAR600ContDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
		} catch (Exception e){
			APIResponse<EquipmentAAR600ContDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
    }

	@DeleteMapping
	public ResponseEntity<List<APIResponse<EquipmentAAR600ContDTO>>> deleteEqCont(@Valid @RequestBody List<EquipmentAAR600ContDTO> eqContObjDtoList){

		List<APIResponse<EquipmentAAR600ContDTO>> response;
		AtomicInteger errorCount = new AtomicInteger();
		if (eqContObjDtoList != null && !eqContObjDtoList.isEmpty()) {
			response = eqContObjDtoList.stream().map( eqContObjDto -> {
						APIResponse<EquipmentAAR600ContDTO> singleDtoDelResponse;
						try {
							equipmentAAR600ContService.deleteEqCont(EquipmentAAR600ContMapper.INSTANCE.EquipmentAAR600ContDTOToEquipmentAAR600Cont(eqContObjDto));
							singleDtoDelResponse = new APIResponse<>(Arrays.asList("Successfully deleted data!"),eqContObjDto, ResponseStatusCode.SUCCESS);
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
