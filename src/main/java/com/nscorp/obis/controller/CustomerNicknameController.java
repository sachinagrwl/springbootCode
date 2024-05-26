package com.nscorp.obis.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.CustomerNickname;
import com.nscorp.obis.dto.CustomerNicknameDTO;
import com.nscorp.obis.dto.CustomerNicknameListDTO;
import com.nscorp.obis.dto.mapper.CustomerNicknameMapper;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.CustomerNicknameService;

@Validated
@RestController
@RequestMapping("/")
public class CustomerNicknameController {

	@Autowired
	CustomerNicknameService customerNicknameService;

	CustomerNicknameMapper customerNicknameMapper;

	@GetMapping(value = ControllerConstants.CUSTOMER_NICKNAME)
	public ResponseEntity<APIResponse<List<CustomerNicknameDTO>>> getCustomerNickname(
			@RequestParam(name = "customerId", required = false) Long customerId,
			@RequestParam(name = "terminalId", required = false) Long terminalId,
			@RequestParam(name = "customerNickname", required = false) String customerNickname) throws SQLException {
		try {
			List<CustomerNicknameDTO> customerNicknameDTOs = new ArrayList<CustomerNicknameDTO>();
			List<CustomerNickname> customerNicknames = customerNicknameService.fetchCustomerNickname(customerId,
					terminalId, customerNickname);
			if (customerNicknames != null && !customerNicknames.isEmpty()) {
				customerNicknameDTOs = customerNicknames.stream()
						.map(CustomerNicknameMapper.INSTANCE::customerNicknameToCustomerNicknameDTO)
						.collect(Collectors.toList());
			}
			APIResponse<List<CustomerNicknameDTO>> responseObj = new APIResponse<>(
					Arrays.asList("Successfully retrieved data!"), customerNicknameDTOs,
					ResponseStatusCode.SUCCESS.getStatusCode(), ResponseStatusCode.SUCCESS.toString());
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e) {
			APIResponse<List<CustomerNicknameDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (Exception e) {
			APIResponse<List<CustomerNicknameDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	@PostMapping(value = ControllerConstants.CUSTOMER_NICKNAME)
	public ResponseEntity<List<APIResponse<CustomerNicknameDTO>>> addCustomerNickname(
		@Valid @NotNull @RequestBody CustomerNicknameListDTO customerNicknameListDto,
		@RequestHeader Map<String, String> headers ) {
		
			List<APIResponse<CustomerNicknameDTO>> response;
			AtomicInteger errorCount = new AtomicInteger();
			CustomerNicknameDTO customerNicknameDto ;
			customerNicknameDto = new CustomerNicknameDTO();
			
			customerNicknameDto.setCustomerId(customerNicknameListDto.getCustomerId());
			customerNicknameDto.setTerminalId(customerNicknameListDto.getTerminalId());
			// if(customerNicknameListDto.getCustomerNickname())
			if (customerNicknameListDto.getCustomerNickname() != null && !customerNicknameListDto.getCustomerNickname().isEmpty()) {

				response = customerNicknameListDto.getCustomerNickname().stream().map(custNickname -> {
					
					APIResponse<CustomerNicknameDTO> responseObj;
					try{
						customerNicknameDto.setCustomerNickname(custNickname);
						CustomerNickname customerNickname = CustomerNicknameMapper.INSTANCE
							.customerNicknameDTOToCustomerNickname(customerNicknameDto);
						
						CustomerNickname customerNicknameAdded = customerNicknameService
							.addCustomerNickname(customerNickname,headers);

						CustomerNicknameDTO addedCustomerNicknameDto = CustomerNicknameMapper.INSTANCE
								.customerNicknameToCustomerNicknameDTO(customerNicknameAdded);
						
						responseObj = new APIResponse<>(Arrays.asList("Successfully added data!"), addedCustomerNicknameDto,ResponseStatusCode.SUCCESS);
					
					}catch (Exception e) {
						errorCount.incrementAndGet();
						responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
					}
					return responseObj;
					// return ResponseEntity.status(HttpStatus.OK).body(responseObj);

				}).collect(Collectors.toList());

			} 
			else {
				throw new InvalidDataException("Nickname cant be empty. Enter Valid Nickname");
				// response = Collections.emptyList();
			}

			if (errorCount.get() == 0 && response.size() > 0) { // No errors and atleast 1 success
				return ResponseEntity.status(HttpStatus.OK).body(response);
			} else if (response.size() > errorCount.get()) { // Partial success
				return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(response);
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

	}

	@DeleteMapping(value = ControllerConstants.CUSTOMER_NICKNAME)
	public ResponseEntity<List<APIResponse<CustomerNicknameDTO>>> deleteCustomerNickname(
			@Valid @NotNull @RequestBody CustomerNicknameListDTO customerNicknameListDto) {
				
			List<APIResponse<CustomerNicknameDTO>> response;
			AtomicInteger errorCount = new AtomicInteger();
			CustomerNicknameDTO customerNicknameDto ;
			customerNicknameDto = new CustomerNicknameDTO();
			customerNicknameDto.setCustomerId(customerNicknameListDto.getCustomerId());
			customerNicknameDto.setTerminalId(customerNicknameListDto.getTerminalId());

			if (customerNicknameListDto.getCustomerNickname() != null && !customerNicknameListDto.getCustomerNickname().isEmpty()) {

				response = customerNicknameListDto.getCustomerNickname().stream().map(custNickname -> {
					
					APIResponse<CustomerNicknameDTO> responseObj;
					try{
						customerNicknameDto.setCustomerNickname(custNickname);
						CustomerNickname customerNickname = CustomerNicknameMapper.INSTANCE
							.customerNicknameDTOToCustomerNickname(customerNicknameDto);
						
						CustomerNickname customerNicknamedeleted = customerNicknameService.deleteCustomerNickname(customerNickname);
						 
						CustomerNicknameDTO deletedCustomerNicknameDto = CustomerNicknameMapper.INSTANCE
								.customerNicknameToCustomerNicknameDTO(customerNicknamedeleted);

						responseObj = new APIResponse<>(Arrays.asList("Successfully deleted data!"), deletedCustomerNicknameDto, ResponseStatusCode.SUCCESS);
					}catch (Exception e) {
						errorCount.incrementAndGet();
						responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
					}
					return responseObj;

				}).collect(Collectors.toList());

			} 
			else {
				throw new InvalidDataException("Nickname cant be empty. Enter Valid Nickname");
				// response = Collections.emptyList();
			}

			if (errorCount.get() == 0 && response.size() > 0) { // No errors and atleast 1 success
				return ResponseEntity.status(HttpStatus.OK).body(response);
			} else if (response.size() > errorCount.get()) { // Partial success
				return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(response);
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}
	
	}

	
}
