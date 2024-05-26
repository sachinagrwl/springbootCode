package com.nscorp.obis.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
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
import com.nscorp.obis.domain.CustomerTerm;
import com.nscorp.obis.domain.CustomerTerminal;
import com.nscorp.obis.dto.CustomerTermDTO;
import com.nscorp.obis.dto.CustomerTerminalDTO;
import com.nscorp.obis.dto.CustomerTerminalListDTO;
import com.nscorp.obis.dto.mapper.CustomerTermMapper;
import com.nscorp.obis.dto.mapper.CustomerTerminalMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.CustomerTermService;

@RestController
@Validated
@RequestMapping("/")
public class CustomerTermController {

	@Autowired
	CustomerTermService customerTermService;

	@GetMapping(value = ControllerConstants.CUSTOMER_TERM)
	public ResponseEntity<APIResponse<List<CustomerTermDTO>>> getCustomerTerm(
			@Valid @RequestParam(name = "terminalId", required = false) Long terminalId,
			@RequestParam(name = "customerName", required = false) String customerName) throws SQLException {
		try {
			List<CustomerTerm> customerTerms = customerTermService.fetchCustomerNotifyProfiles(terminalId,
					customerName);
			List<CustomerTermDTO> customerTermDTOs = customerTerms.stream()
					.map(CustomerTermMapper.INSTANCE::CustomerTermToCustomerTermDTO).collect(Collectors.toList());
			APIResponse<List<CustomerTermDTO>> responseObj = new APIResponse<>(
					Arrays.asList("Successfully retrieve data!"), customerTermDTOs,
					ResponseStatusCode.SUCCESS.getStatusCode(), ResponseStatusCode.SUCCESS.toString());
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e) {
			APIResponse<List<CustomerTermDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (Exception e) {
			APIResponse<List<CustomerTermDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

	// ims02656
	@GetMapping(value = ControllerConstants.CUSTOMER_TERMINAL)
	public ResponseEntity<APIResponse<List<CustomerTerminalDTO>>> getCustomerTerminals(
			@Digits(integer = CommonConstants.CUST_ID_MAX_SIZE, fraction = 0, message = "Customer id length cannot be more than 15") 
			@Min(value = 1, message = "Customer id must be greater than 0") 
			@NotNull(message="Required parameter Customer id can not be null.")
			@RequestParam(name = "customerId", required = true) Long customerId)
			throws SQLException {
		try {
			List<CustomerTerminal> customerTerms = customerTermService.fetchCustomerTerminal(customerId);
			List<CustomerTerminalDTO> customerTermDTOs = customerTerms.stream()
					.map(CustomerTerminalMapper.INSTANCE::CustomerTerminalToCustomerTerminalDTO)
					.collect(Collectors.toList());
			APIResponse<List<CustomerTerminalDTO>> responseObj = new APIResponse<>(
					Arrays.asList("Successfully retrieve data!"), customerTermDTOs,
					ResponseStatusCode.SUCCESS.getStatusCode(), ResponseStatusCode.SUCCESS.toString());
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e) {
			APIResponse<List<CustomerTerminalDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (Exception e) {
			APIResponse<List<CustomerTerminalDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

	@PutMapping(value = ControllerConstants.CUSTOMER_TERMINAL)
	public ResponseEntity<List<APIResponse<CustomerTerminalDTO>>> updateCustomerTerminals(
			@Valid @RequestBody CustomerTerminalListDTO customerTerminalListDTO,
			@RequestHeader Map<String, String> headers) throws SQLException {

		List<APIResponse<CustomerTerminalDTO>> response;
		AtomicInteger errorCount = new AtomicInteger();
		CustomerTerminalDTO customerTerminalDto;
		customerTerminalDto = new CustomerTerminalDTO();
		customerTerminalDto.setCustomerId(customerTerminalListDTO.getCustomerId());

		Long customerId = customerTerminalListDTO.getCustomerId();
		customerId = customerTermService.deleteByCustomerId(customerId);

		if (customerTerminalListDTO.getTerminalId() != null && !customerTerminalListDTO.getTerminalId().isEmpty()) {

			response = customerTerminalListDTO.getTerminalId().stream().map(termId -> {

				APIResponse<CustomerTerminalDTO> responseObj;
				try {
					customerTerminalDto.setTerminalId(termId);
					CustomerTerminal customerTerminal = CustomerTerminalMapper.INSTANCE
							.CustomerTerminalDTOToCustomerTerminal(customerTerminalDto);

					CustomerTerminal customerTerminalAdded = customerTermService
							.updateCustomerTerminals(customerTerminal, headers);

					CustomerTerminalDTO addedCustomerTerminalDto = CustomerTerminalMapper.INSTANCE
							.CustomerTerminalToCustomerTerminalDTO(customerTerminalAdded);

					responseObj = new APIResponse<>(Arrays.asList("Successfully updated data!"),
							addedCustomerTerminalDto, ResponseStatusCode.SUCCESS);

				} catch (Exception e) {
					errorCount.incrementAndGet();
					responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
				}
				return responseObj;

			}).collect(Collectors.toList());

		} else {
			APIResponse<CustomerTerminalDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully updated data!"),ResponseStatusCode.SUCCESS);
			response = new ArrayList<>();
			response.add(responseObj);
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
