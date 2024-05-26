package com.nscorp.obis.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.PoolType;
import com.nscorp.obis.dto.PoolTypeDTO;
import com.nscorp.obis.dto.mapper.PoolTypeMapper;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.SizeExceedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.PoolTypeService;

@RestController
@RequestMapping(value = ControllerConstants.RSRV_TP_SELEC)
@Validated
public class PoolTypeController {
	
	@Autowired
    PoolTypeService reservationService;
	
	@GetMapping
	public ResponseEntity<APIResponse<List<PoolTypeDTO>>> getAllReserveType(){

        try{
            List<PoolTypeDTO> reserveTypeDtoList = Collections.emptyList();
            List<PoolType> reserveTypeList = reservationService.getReserveType();
            if (reserveTypeList != null && !reserveTypeList.isEmpty()) {
            	reserveTypeDtoList = reserveTypeList.stream().map(PoolTypeMapper.INSTANCE::poolTypeToPoolTypeDTO)
                        .collect(Collectors.toList());
            }
            APIResponse<List<PoolTypeDTO>> responseObj = new APIResponse<>(Arrays.asList(CommonConstants.RETRIVED_SUCCESS_MESSAGE),
            		reserveTypeDtoList, ResponseStatusCode.SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(responseObj);
        } catch (NoRecordsFoundException e) {
            APIResponse<List<PoolTypeDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
        } catch (Exception e) {
            APIResponse<List<PoolTypeDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }
	}
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<APIResponse<PoolTypeDTO>> insertReservationType(@Valid @RequestBody PoolTypeDTO poolTypeDto, @RequestHeader Map<String, String> headers){
        try {
        	PoolType poolTp = PoolTypeMapper.INSTANCE.poolTypeDtoToPoolType(poolTypeDto);
        	PoolType addPoolTp = reservationService.insertReservationType(poolTp, headers);
        	PoolTypeDTO addPoolTpDTO = PoolTypeMapper.INSTANCE.poolTypeToPoolTypeDTO(addPoolTp);

            APIResponse<PoolTypeDTO> responseObj = new APIResponse<>(Arrays.asList(CommonConstants.ADD_SUCCESS_MESSAGE),
            		addPoolTpDTO, ResponseStatusCode.SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(responseObj);
        }catch (NoRecordsFoundException e){
            APIResponse<PoolTypeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
        } catch (SizeExceedException e){
            APIResponse<PoolTypeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.LENGTH_REQUIRED).body(responseObj);
        } catch (RecordAlreadyExistsException e){
            APIResponse<PoolTypeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(responseObj);
        } catch (RecordNotAddedException e){
            APIResponse<PoolTypeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseObj);
        } catch (NullPointerException e){
            APIResponse<PoolTypeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
        }catch (InvalidDataException e){
            APIResponse<PoolTypeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
        } catch (Exception e){
            APIResponse<PoolTypeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }
	}

    @PutMapping
    public ResponseEntity<APIResponse<PoolTypeDTO>> updateReservationType(@Valid @RequestBody PoolTypeDTO poolTypeDto, @RequestHeader Map<String,String> headers) {

        try {
            PoolType poolType = PoolTypeMapper.INSTANCE.poolTypeDtoToPoolType(poolTypeDto);
            PoolType updatedPoolType = reservationService.updateReservationType(poolType, headers);
            PoolTypeDTO updatedPoolTypeDto = PoolTypeMapper.INSTANCE.poolTypeToPoolTypeDTO(updatedPoolType);
            APIResponse<PoolTypeDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully updated data!"),updatedPoolTypeDto, ResponseStatusCode.SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(responseObj);
        } catch (NoRecordsFoundException e){
            APIResponse<PoolTypeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
        } catch (RecordNotAddedException e){
            APIResponse<PoolTypeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseObj);
        } catch (NullPointerException e){
            APIResponse<PoolTypeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
        } catch (SizeExceedException e){
            APIResponse<PoolTypeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.LENGTH_REQUIRED).body(responseObj);
        } catch (InvalidDataException e){
            APIResponse<PoolTypeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
        } catch (Exception e){
            APIResponse<PoolTypeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }
    }

    @DeleteMapping
    public ResponseEntity<List<APIResponse<PoolTypeDTO>>> deleteReservationType(@RequestBody List<PoolTypeDTO> poolTypeDtoList) {
        List<APIResponse<PoolTypeDTO>> responseDTOList;
        AtomicInteger errorCount = new AtomicInteger();
        if (!CollectionUtils.isEmpty(poolTypeDtoList)) {
            responseDTOList = poolTypeDtoList.stream().map(poolTypeDto -> {
                APIResponse<PoolTypeDTO> singleDtoDelResponse;
                try {
                    PoolType poolType = reservationService.deleteReservationType(PoolTypeMapper.INSTANCE.poolTypeDtoToPoolType(poolTypeDto));
                    singleDtoDelResponse = new APIResponse<>(Arrays.asList("Successfully deleted data!"),
                            PoolTypeMapper.INSTANCE.poolTypeToPoolTypeDTO(poolType),
                            ResponseStatusCode.SUCCESS);
                } catch (NoRecordsFoundException e) {
                    errorCount.incrementAndGet();
                    singleDtoDelResponse = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
                } catch (Exception e) {
                    errorCount.incrementAndGet();
                    singleDtoDelResponse = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
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
