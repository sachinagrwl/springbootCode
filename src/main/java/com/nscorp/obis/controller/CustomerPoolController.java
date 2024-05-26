package com.nscorp.obis.controller;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.CustomerPool;
import com.nscorp.obis.dto.CustomerPoolDTO;
import com.nscorp.obis.dto.PoolListDTO;
import com.nscorp.obis.dto.mapper.CustomerPoolMapper;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.CustomerPoolService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Validated
@RequestMapping("/")
public class CustomerPoolController {

	@Autowired
	CustomerPoolService customerPoolService;

	@GetMapping(value = ControllerConstants.CUSTOMER_POOL)
	public ResponseEntity<APIResponse<List<CustomerPoolDTO>>> getCustomerPools(
			@Digits(integer = CommonConstants.CUST_ID_MAX_SIZE, fraction = 0, message = "Customer id length cannot be more than 15") @Min(value = 1, message = "Customer id must be greater than 0") @NotNull(message = "Required parameter Customer id can not be null.") @RequestParam(name = "customerId", required = true) Long customerId)
			throws SQLException {
		try {
			List<CustomerPool> customerPools = customerPoolService.fetchCustomerPool(customerId);
			List<CustomerPoolDTO> customerPoolDTOs = customerPools.stream()
					.map(CustomerPoolMapper.INSTANCE::CustomerPoolToCustomerPoolDTO).collect(Collectors.toList());
			APIResponse<List<CustomerPoolDTO>> responseObj = new APIResponse<>(
					Arrays.asList("Successfully retrieve data!"), customerPoolDTOs,
					ResponseStatusCode.SUCCESS.getStatusCode(), ResponseStatusCode.SUCCESS.toString());
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e) {
			APIResponse<List<CustomerPoolDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (Exception e) {
			APIResponse<List<CustomerPoolDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

	@PutMapping(value = ControllerConstants.CUSTOMER_POOL)
	public ResponseEntity<APIResponse<List<CustomerPoolDTO>>> updateCustomerPools(
			@Valid @RequestBody PoolListDTO poolListDTO, @RequestHeader Map<String, String> headers)
			throws SQLException {
		log.info("CustomerPoolController: updateCustomerPools : Method Starts");
		try {
			List<CustomerPoolDTO> customerPoolDTOs = customerPoolService.updateCustomerPools(poolListDTO, headers);
			APIResponse<List<CustomerPoolDTO>> responseObj = new APIResponse<>(
					Arrays.asList("Successfully updated data!"), customerPoolDTOs,
					ResponseStatusCode.SUCCESS.getStatusCode(), ResponseStatusCode.SUCCESS.toString());
			log.info("CustomerPoolController: updateCustomerPools : Method Ends");
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException | InvalidDataException e) {
			APIResponse<List<CustomerPoolDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			HttpStatus httpStatus = HttpStatus.NOT_FOUND;
			if (e.getClass() == InvalidDataException.class) {
				httpStatus = HttpStatus.BAD_REQUEST;
			}
			log.error("CustomerPoolController: updateCustomerPools : " + e.getMessage());
			return ResponseEntity.status(httpStatus).body(responseObj);
		} catch (Exception e) {
			APIResponse<List<CustomerPoolDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			log.error("CustomerPoolController: updateCustomerPools : " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}
}
