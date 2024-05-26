package com.nscorp.obis.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.persistence.RollbackException;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.domain.EquipmentRestrict;
import com.nscorp.obis.dto.EquipmentRestrictDTO;
import com.nscorp.obis.dto.mapper.EquipmentRestrictMapper;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.JDBCException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.common.NullOrNotBlank;
import com.nscorp.obis.domain.Pool;
import com.nscorp.obis.dto.PoolDTO;
import com.nscorp.obis.dto.mapper.PoolMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.SizeExceedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.PoolService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/")
@Validated
@Slf4j
public class PoolController {

	@Autowired
	PoolService poolService;

	@GetMapping(value= ControllerConstants.POOL)
	@Operation(description = "Fetch all the values")
	public ResponseEntity<APIResponse<List<PoolDTO>>> getPools(
			@Digits(integer = 15, fraction = 0, message = "Pool Id should not have more than 15 digits")
			@Min(value = 0, message = "Pool Id value must be greater than or equal to 0")
			@RequestParam(name="pool-ID", required=false) Long poolId,
			@NullOrNotBlank(max=10, message = "Pool Name should not be empty and not have more than 10 characters.")
			@RequestParam(name="pool-name", required=false) String poolName,
			@RequestParam(name="fetchAll", required=false) String fetchAll){
		try {
			log.info("getPools : Method Starts");
			if(poolName==null)poolName="";
			List<PoolDTO> poolDtoList = Collections.emptyList();

			List<Pool> poolList = poolService.getPools(poolId,poolName);
			if(fetchAll != null){
				fetchAll = fetchAll.toUpperCase();
			} else{
				fetchAll = "no";
			}
			if(poolList != null && !poolList.isEmpty()) {
				if(fetchAll.equals("YES")) {
					poolDtoList = poolList.stream()
							.map(PoolMapper.INSTANCE::fetchAllPoolToPoolDto)
							.collect(Collectors.toList());
				} else{
					poolDtoList = poolList.stream()
							.map(PoolMapper.INSTANCE::poolToPoolDto)
							.collect(Collectors.toList());
				}
			}
			log.info("getPools : Method Ends");
			APIResponse<List<PoolDTO>> responseObj = new APIResponse<>(Arrays.asList(CommonConstants.RETRIVED_SUCCESS_MESSAGE),poolDtoList, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e){
			APIResponse<List<PoolDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (Exception e){
			APIResponse<List<PoolDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

	@PutMapping(value= ControllerConstants.POOL_TERMINAL)
	public ResponseEntity<APIResponse<PoolDTO>> updatePoolTerminal(@Valid @NotNull @RequestBody PoolDTO poolDto, @RequestHeader Map<String,String> headers) {
		try {
			log.info("updatePoolTerminal : Method Starts");
			Pool pool = PoolMapper.INSTANCE.poolDtoToPool(poolDto);
			Pool poolUpdated = poolService.updatePoolTerminal(pool, headers);
			PoolDTO updatedPool = PoolMapper.INSTANCE.poolToPoolDto(poolUpdated);
			log.info("updatePoolTerminal : Method Ends");
			APIResponse<PoolDTO> responseObj = new APIResponse<>(Arrays.asList(CommonConstants.UPDATE_SUCCESS_MESSAGE),updatedPool, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e){
			APIResponse<PoolDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (NullPointerException e){
			APIResponse<PoolDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
		} catch (SizeExceedException e){
			APIResponse<PoolDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.LENGTH_REQUIRED).body(responseObj);
		} catch (Exception e){
			e.printStackTrace();
			APIResponse<PoolDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	@PostMapping(value= ControllerConstants.POOL)
	public ResponseEntity<APIResponse<PoolDTO>> addPool(@Valid @RequestBody PoolDTO poolObjDto, @RequestHeader Map<String,String> headers){
		try {
			log.info("addPool : Method Starts");
			Pool pool = PoolMapper.INSTANCE.poolDtoToPool(poolObjDto);
			Pool addedPool = poolService.addPool(pool, headers);
			PoolDTO addedPoolDto = PoolMapper.INSTANCE.poolToPoolDto(addedPool);
			log.info("addPool : Method Ends");
			APIResponse<PoolDTO> responseObj = new APIResponse<>(Arrays.asList(CommonConstants.ADD_SUCCESS_MESSAGE),addedPoolDto, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e){
			APIResponse<PoolDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (SizeExceedException e){
			APIResponse<PoolDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.LENGTH_REQUIRED).body(responseObj);
		} catch (RecordAlreadyExistsException e){
			APIResponse<PoolDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(responseObj);
		} catch (RecordNotAddedException e){
			APIResponse<PoolDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseObj);
		} catch (NullPointerException e){
			APIResponse<PoolDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
		} catch (Exception e){
			Throwable t = e.getCause();
			while ((t != null) && !(t instanceof ConstraintViolationException)) {
				t = t.getCause();
			}
			if (t instanceof ConstraintViolationException) {
				APIResponse<PoolDTO> responseObj = new APIResponse<>(Arrays.asList("Pool Name & Pool Description should be unique from other records!"), ResponseStatusCode.FAILURE);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
			}
			APIResponse<PoolDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

	@PutMapping(value = ControllerConstants.POOL)
	public ResponseEntity<APIResponse<PoolDTO>> updatePool(@Valid @RequestBody PoolDTO poolObjDto, @RequestHeader Map<String,String> headers) {

		try {
			log.info("updatePool : Method Starts");
			Pool pool = PoolMapper.INSTANCE.poolDtoToPool(poolObjDto);
			Pool updatedPool = poolService.updatePool(pool, headers);
			PoolDTO updatedPoolDto = PoolMapper.INSTANCE.poolToPoolDto(updatedPool);
			log.info("updatePool : Method Ends");
			APIResponse<PoolDTO> responseObj = new APIResponse<>(Arrays.asList(CommonConstants.UPDATE_SUCCESS_MESSAGE), updatedPoolDto, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e){
			APIResponse<PoolDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (NullPointerException e){
			APIResponse<PoolDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
		} catch (SizeExceedException e){
			APIResponse<PoolDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.LENGTH_REQUIRED).body(responseObj);
		} catch (RecordNotAddedException e){
			APIResponse<PoolDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseObj);
		} catch (Exception e){
			Throwable t = e.getCause();
			while ((t != null) && !(t instanceof ConstraintViolationException)) {
				t = t.getCause();
			}
			if (t instanceof ConstraintViolationException) {
				APIResponse<PoolDTO> responseObj = new APIResponse<>(Arrays.asList("Pool Name & Pool Description should be unique from other records!"), ResponseStatusCode.FAILURE);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
			}
			APIResponse<PoolDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

	@DeleteMapping(value = ControllerConstants.POOL)
	public ResponseEntity<List<APIResponse<PoolDTO>>> deletePool(@RequestBody List<PoolDTO> poolObjDtoList) {
		log.info("deletePool : Method Starts");
		List<APIResponse<PoolDTO>> responseDTOList;
		AtomicInteger errorCount = new AtomicInteger();
		if (!CollectionUtils.isEmpty(poolObjDtoList)) {
			responseDTOList = poolObjDtoList.stream().map(poolObjDto -> {
				APIResponse<PoolDTO> singleDtoDelResponse;
				try {
					Pool pool = poolService.deletePool(PoolMapper.INSTANCE.poolDtoToPool(poolObjDto));
					singleDtoDelResponse = new APIResponse<>(Arrays.asList(CommonConstants.DELETE_SUCCESS_MESSAGE),
							PoolMapper.INSTANCE.poolToPoolDto(pool),
							ResponseStatusCode.SUCCESS);
				} catch (NoRecordsFoundException e) {
					errorCount.incrementAndGet();
					singleDtoDelResponse = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
				} catch (Exception e) {
					errorCount.incrementAndGet();
					singleDtoDelResponse = new APIResponse<>(Arrays.asList(e.getMessage() + " For Pool Id : " + poolObjDto.getPoolId()), ResponseStatusCode.FAILURE);
				}
				return singleDtoDelResponse;
			}).collect(Collectors.toList());
		} else {
			responseDTOList = Collections.emptyList();
		}
		log.info("deletePool : Method Ends");

		if (errorCount.get() == 0 && responseDTOList.size() > 0) // No errors and at least 1 success
			return ResponseEntity.status(HttpStatus.OK).body(responseDTOList);
		else if (responseDTOList.size() > errorCount.get()) // Partial success
			return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(responseDTOList);
		else
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTOList);
	}
}