package com.nscorp.obis.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.CustomerInfo;
import com.nscorp.obis.dto.CustomerInfoDTO;
import com.nscorp.obis.dto.mapper.CustomerInfoMapper;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.PaginatedResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.CustomerInfoService;

import lombok.extern.slf4j.Slf4j;

@RequestMapping("/")
@Validated
@RestController
@CrossOrigin
@Slf4j
public class CustomerInfoController {

	@Autowired
	private CustomerInfoService customerInfoService;


	@GetMapping(value = ControllerConstants.CUSTOMER)
	public ResponseEntity<APIResponse<PaginatedResponse<CustomerInfoDTO>>> fetchCustomers(
			@Digits(integer = CommonConstants.CUST_ID_MAX_SIZE, fraction = 0, message = "Customer id cannot be more than 15") @RequestParam(name = "customerId", required = false) Long customerId,
			@Size(min = 1, max = 35) @RequestParam(name = "customerName", required = false) String customerName,
			@Size(min = 1, max = 10, message = "Customer Number length should be between 1 and 10 and can't be empty") @RequestParam(name = "customerNumber", required = false) String customerNumber,
			@RequestParam(name = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
			@RequestParam(name = "pageSize", required = false, defaultValue = "20") Integer pageSize,
			@RequestParam(name = "sort", required = false, defaultValue = "customerName, asc") String[] sort,
			@RequestParam(name = "filter", required = false) String[] filter,
			@Pattern(regexp = "^[YN]{1}", message = "fetchExpired should be either Y or N !") @RequestParam(name = "fetchExpired", required = false, defaultValue = "N") String fetchExpired) {
		try {
			log.info("CustomerInfoController : fetchCustomers - Method Starts");
			if (customerId == null && customerName == null && customerNumber == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new APIResponse<>(Arrays.asList("Parameters are missing!"),
								ResponseStatusCode.INFORMATION.getStatusCode(),
								ResponseStatusCode.INFORMATION.toString()));
			}
			PaginatedResponse<CustomerInfoDTO> customers = customerInfoService.fetchCustomers(customerId, customerName,
					customerNumber, pageSize, pageNumber, sort , filter, fetchExpired);
			APIResponse<PaginatedResponse<CustomerInfoDTO>> responseObj;
			List<String> message = Arrays.asList("Successfully retrieved data!");
			responseObj = new APIResponse<>(message, customers, ResponseStatusCode.SUCCESS.getStatusCode(),
					ResponseStatusCode.SUCCESS.toString());
			if (customerNumber != null && customers.getTotalCountOfRecords() > 1) {
				message = Arrays.asList("The latest record for the customer number is retrieved");
				responseObj = new APIResponse<>(message,
						PaginatedResponse.of(customers.getContent(),
								new PageImpl<>(customers.getContent(), PageRequest.of(0, 1), 1)),
						ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			}
			log.info("CustomerInfoController : fetchCustomers - Method Ends");
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e) {
			APIResponse<PaginatedResponse<CustomerInfoDTO>> responseObj = new APIResponse<>(
					Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION.getStatusCode(),
					ResponseStatusCode.INFORMATION.toString());
			log.error("CustomerInfoController : fetchCustomers - Error :" + e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (Exception e) {
			APIResponse<PaginatedResponse<CustomerInfoDTO>> responseObj = new APIResponse<>(
					Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION.getStatusCode(),
					ResponseStatusCode.INFORMATION.toString());
			log.error("CustomerInfoController : fetchCustomers - Error :" + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

	@PostMapping(value = ControllerConstants.CUSTOMER)
	public ResponseEntity<APIResponse<CustomerInfoDTO>> addCustomerInfo(
			@Valid @RequestBody CustomerInfoDTO customerInfoDTO, @RequestHeader Map<String, String> headers) {

		try {
			CustomerInfo customerInfo = CustomerInfoMapper.INSTANCE.customerInfoDTOToCustomerInfo(customerInfoDTO);
			CustomerInfo added = customerInfoService.addCustomer(customerInfo, headers);
			CustomerInfoDTO addedDTO = CustomerInfoMapper.INSTANCE.customerInfoToCustomerInfoDTO(added);

			APIResponse<CustomerInfoDTO> response = new APIResponse<>(Arrays.asList("Sucessfully added data!"),
					addedDTO, ResponseStatusCode.SUCCESS.getStatusCode(), ResponseStatusCode.SUCCESS.toString());
			return ResponseEntity.status(HttpStatus.OK).body(response);

		} catch (NoRecordsFoundException | InvalidDataException e) {
			APIResponse<CustomerInfoDTO> response = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			HttpStatus httpStatus = HttpStatus.NOT_FOUND;
			if (e.getClass() == InvalidDataException.class)
				httpStatus = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(httpStatus).body(response);
		} catch (Exception e) {
			APIResponse<CustomerInfoDTO> response = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}

	}

	@PutMapping(value = ControllerConstants.CUSTOMER)
	public ResponseEntity<APIResponse<CustomerInfoDTO>> updateCustomerInfo(
			@Valid @RequestBody CustomerInfoDTO customerInfoDTO, @RequestHeader Map<String, String> headers) {
		try {
			CustomerInfoDTO infoDTO = customerInfoService.updateCustomer(customerInfoDTO, headers);
			APIResponse<CustomerInfoDTO> response = new APIResponse<>(Arrays.asList("Sucessfully updated data!"),
					infoDTO, ResponseStatusCode.SUCCESS.getStatusCode(), ResponseStatusCode.SUCCESS.toString());
			return ResponseEntity.status(HttpStatus.OK).body(response);

		} catch (NoRecordsFoundException | InvalidDataException e) {
			APIResponse<CustomerInfoDTO> response = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			HttpStatus httpStatus = HttpStatus.NOT_FOUND;
			if (e.getClass() == InvalidDataException.class)
				httpStatus = HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(httpStatus).body(response);
		} catch (Exception e) {
			APIResponse<CustomerInfoDTO> response = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

}
