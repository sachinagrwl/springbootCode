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
import com.nscorp.obis.domain.PoolEquipmentController;
import com.nscorp.obis.dto.PoolEquipmentControllerDTO;
import com.nscorp.obis.dto.mapper.PoolEquipmentControllerMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.SizeExceedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.PoolEquipmentControllerService;

@RestController
@RequestMapping(value = ControllerConstants.POOL_EQUIPMENT_CONTROLLER)
@Validated
public class EquipmentPoolController {
	
	@Autowired
	PoolEquipmentControllerService poolEquipmentService;
	
	@GetMapping
	public ResponseEntity<APIResponse<List<PoolEquipmentControllerDTO>>> getAllPoolController(){

        try{
            List<PoolEquipmentControllerDTO> poolControllerDtoList = Collections.emptyList();
            List<PoolEquipmentController> poolControllerList = poolEquipmentService.getAllPool();
            if (poolControllerList != null && !poolControllerList.isEmpty()) {
            	poolControllerDtoList = poolControllerList.stream().map(PoolEquipmentControllerMapper.INSTANCE::poolEquipmentControllerToPoolEquipmentControllerDTO)
                        .collect(Collectors.toList());
            }
            APIResponse<List<PoolEquipmentControllerDTO>> responseObj = new APIResponse<>(Arrays.asList("Successfully retrieved data!"),
            		poolControllerDtoList, ResponseStatusCode.SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(responseObj);

        } catch (NoRecordsFoundException e) {
            APIResponse<List<PoolEquipmentControllerDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
        } catch (Exception e) {
            APIResponse<List<PoolEquipmentControllerDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }
	}
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<APIResponse<PoolEquipmentControllerDTO>> insertPoolController(@Valid @RequestBody PoolEquipmentControllerDTO poolEquipmentControllerDto, @RequestHeader Map<String, String> headers){
        try {
        	PoolEquipmentController poolCtrl = PoolEquipmentControllerMapper.INSTANCE.poolEquipmentControllerDTOToPoolEquipmentController(poolEquipmentControllerDto);
        	PoolEquipmentController addPoolCtrl = poolEquipmentService.insertPoolCtrl(poolCtrl,headers);
            PoolEquipmentControllerDTO addPoolCtrlDTO = PoolEquipmentControllerMapper.INSTANCE.poolEquipmentControllerToPoolEquipmentControllerDTO(addPoolCtrl);

            APIResponse<PoolEquipmentControllerDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully added data!"),
            		addPoolCtrlDTO, ResponseStatusCode.SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(responseObj);
        }catch (NoRecordsFoundException e){
            APIResponse<PoolEquipmentControllerDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
        } catch (SizeExceedException e){
            APIResponse<PoolEquipmentControllerDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.LENGTH_REQUIRED).body(responseObj);
        } catch (RecordAlreadyExistsException e){
            APIResponse<PoolEquipmentControllerDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(responseObj);
        } catch (RecordNotAddedException e){
            APIResponse<PoolEquipmentControllerDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseObj);
        } catch (NullPointerException e){
            APIResponse<PoolEquipmentControllerDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
        } catch (Exception e){
            APIResponse<PoolEquipmentControllerDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }
	}
	
	@PutMapping
	public ResponseEntity<APIResponse<PoolEquipmentControllerDTO>> updatePoolController(@Valid @RequestBody PoolEquipmentControllerDTO poolEquipmentControllerDto, @RequestHeader Map<String, String> headers){
        try {
        	PoolEquipmentController poolCtrl = PoolEquipmentControllerMapper.INSTANCE.poolEquipmentControllerDTOToPoolEquipmentController(poolEquipmentControllerDto);
        	PoolEquipmentController updatePoolCtrl = poolEquipmentService.updatePoolCtrl(poolCtrl,headers);
            PoolEquipmentControllerDTO updatePoolCtrlDTO = PoolEquipmentControllerMapper.INSTANCE.poolEquipmentControllerToPoolEquipmentControllerDTO(updatePoolCtrl);

            APIResponse<PoolEquipmentControllerDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully updated data!"),
            		updatePoolCtrlDTO, ResponseStatusCode.SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(responseObj);
        } catch (NoRecordsFoundException e){
            APIResponse<PoolEquipmentControllerDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
        } catch (RecordAlreadyExistsException e){
            APIResponse<PoolEquipmentControllerDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(responseObj);
        } catch (RecordNotAddedException e){
            APIResponse<PoolEquipmentControllerDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseObj);
        } catch (NullPointerException e){
            APIResponse<PoolEquipmentControllerDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
        } catch (Exception e){
        	e.printStackTrace();
            APIResponse<PoolEquipmentControllerDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }
	}
	
	@DeleteMapping
	public ResponseEntity<List<APIResponse<PoolEquipmentControllerDTO>>> deletePoolController(@Valid @NotNull @RequestBody List<PoolEquipmentControllerDTO> poolCtrlDtoList){
        List<APIResponse<PoolEquipmentControllerDTO>> response;
        AtomicInteger errorCount = new AtomicInteger();
        if (poolCtrlDtoList != null && !poolCtrlDtoList.isEmpty()) {
            response = poolCtrlDtoList.stream().map(poolCtrlObjDto -> {
                        APIResponse<PoolEquipmentControllerDTO> singleDtoDelResponse;
                        try {
                        	poolEquipmentService.deletePoolCtrl(PoolEquipmentControllerMapper.INSTANCE.poolEquipmentControllerDTOToPoolEquipmentController(poolCtrlObjDto));
                            singleDtoDelResponse = new APIResponse<>(Arrays.asList("Successfully deleted data!"),poolCtrlObjDto, ResponseStatusCode.SUCCESS);
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


