package com.nscorp.obis.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.Customer;
import com.nscorp.obis.domain.CustomerNotifyProfile;
import com.nscorp.obis.domain.DeliveryDetail;
import com.nscorp.obis.dto.CustomerDTO;
import com.nscorp.obis.dto.CustomerNotifyProfileDTO;
import com.nscorp.obis.dto.DeliveryDetailDTO;
import com.nscorp.obis.dto.mapper.CustomerNotifyProfileMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.SizeExceedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.CustomerNotifyProfileService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping("/")
public class CustomerNotifyProfileController {
	@Autowired
	CustomerNotifyProfileService service;
//	private static final Logger logger = LoggerFactory.getLogger(CustomerNotifyProfileController.class);

	@GetMapping(value = ControllerConstants.CUSTOMER_NOTIFY_PROFILE)
	@Operation(description = "Fetch all the values")
	public ResponseEntity<APIResponse<List<CustomerNotifyProfileDTO>>> getCustomerNotifyProfiles(
			@NotNull(message = "customerId should be provided as request parameter.") @Digits(integer = 15, fraction = 0, message = "Customer Id should not have more than 15 digits") @Min(value = 0, message = "Customer Id value must be greater than or equal to 0") @RequestParam("customerId") Long customerId)
			throws SQLException {
		try {
			List<CustomerNotifyProfileDTO> customerNotifyProfilesDto = new ArrayList<>();

			List<CustomerNotifyProfile> customerNotifyProfiles = service.fetchCustomerNotifyProfiles(customerId);
			if (customerNotifyProfiles != null && !customerNotifyProfiles.isEmpty()) {
				customerNotifyProfilesDto = customerNotifyProfiles.stream()
						.map(CustomerNotifyProfileMapper.INSTANCE::customerNotifyProfileToCustomerNotifyProfileDto)
						.collect(Collectors.toList());
			}

			APIResponse<List<CustomerNotifyProfileDTO>> responseObj = new APIResponse<>(
					Arrays.asList("Successfully retrieved data!"), customerNotifyProfilesDto,
					ResponseStatusCode.SUCCESS.getStatusCode(), ResponseStatusCode.SUCCESS.toString());
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);

		} catch (NoRecordsFoundException e) {
			APIResponse<List<CustomerNotifyProfileDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (Exception e) {
			APIResponse<List<CustomerNotifyProfileDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

	/* This method is used to add values */
	@JsonIgnoreProperties(ignoreUnknown = true)
	@PostMapping(value = ControllerConstants.CUSTOMER_NOTIFY_PROFILE)
	public ResponseEntity<APIResponse<CustomerNotifyProfileDTO>> addCustomerNotifyProfile(
			@Valid @NotNull @RequestBody CustomerNotifyProfileDTO customerNotifyprofiledto,
			@Min(value = 1, message = "Terminal Id value must be greater than 0") @Digits(integer = 15, fraction = 0, message = "notify Terminal Id should not be have more than 15 digits. ") @RequestParam(value = "notifyTerminalId", required = false) Long notifyTerminalId,
			@Min(value = 1, message = "Customer Id value must be greater than 0") @Digits(integer = 15, fraction = 0, message = "customer Id should not be have more than 15 digits. ") @RequestParam(value = "customerId", required = true) Long customerId,

			@RequestHeader Map<String, String> headers) {
		try {
			CustomerNotifyProfile customerNotifyProfile = CustomerNotifyProfileMapper.INSTANCE
					.customerNotifyProfileDtoToCustomerNotifyProfile(customerNotifyprofiledto);
			CustomerNotifyProfile customernotifyprofileAdded = service
					.insertCustomerNotifyProfile(customerNotifyProfile, headers, notifyTerminalId, customerId);
			CustomerNotifyProfileDTO addedCustomerNotifyTerminalDto = CustomerNotifyProfileMapper.INSTANCE
					.customerNotifyProfileToCustomerNotifyProfileDto(customernotifyprofileAdded);
			APIResponse<CustomerNotifyProfileDTO> responseObj = new APIResponse<>(
					Arrays.asList("Successfully added data!"), addedCustomerNotifyTerminalDto,
					ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e) {
			APIResponse<CustomerNotifyProfileDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (SizeExceedException e) {
			APIResponse<CustomerNotifyProfileDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.LENGTH_REQUIRED).body(responseObj);
		} catch (RecordAlreadyExistsException e) {
			APIResponse<CustomerNotifyProfileDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(responseObj);
		} catch (RecordNotAddedException e) {
			APIResponse<CustomerNotifyProfileDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseObj);
		} catch (Exception e) {
			APIResponse<CustomerNotifyProfileDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}

	}

	/* This method is used to update values */
	@PutMapping(value = ControllerConstants.CUSTOMER_NOTIFY_PROFILE)
	public ResponseEntity<APIResponse<CustomerNotifyProfileDTO>> updateCustomerNotifyProfile(
			@Valid @RequestBody CustomerNotifyProfileDTO customerNotifyProfileDTO,
			@RequestHeader Map<String, String> headers) throws SQLException {
		try {
			CustomerNotifyProfile customerNotifyProfile = service.updateCustomerNotifyProfile(customerNotifyProfileDTO,
					headers);
			CustomerNotifyProfileDTO notifyProfileDTO = CustomerNotifyProfileMapper.INSTANCE
					.customerNotifyProfileToCustomerNotifyProfileDto(customerNotifyProfile);

			Customer customer = customerNotifyProfile.getCustomer();
			CustomerDTO customerDTO = notifyProfileDTO.getCustomer();
			if (customer != null) {
				DeliveryDetailDTO deliveryDetailDTO = customerDTO.getDeliveryDetail();
				DeliveryDetail deliveryDetail = customer.getDeliveryDetail();
				String t = "T";
				if (deliveryDetail != null) {
					if (deliveryDetail.getSendDETG() != null) {
						if (deliveryDetail.getSendDETG().equals(t)) {
							deliveryDetailDTO.setSendDETG(true);
						}
					} else {
						deliveryDetailDTO.setSendDETG(null);
					}
					if (deliveryDetail.getSendSETG() != null) {
						if (deliveryDetail.getSendSETG().equals(t)) {
							deliveryDetailDTO.setSendSETG(true);
						}
					} else {
						deliveryDetailDTO.setSendSETG(null);
					}
				}
			}
			APIResponse<CustomerNotifyProfileDTO> responseObj = new APIResponse<>(
					Arrays.asList("Successfully updated data!"), notifyProfileDTO,
					ResponseStatusCode.SUCCESS.getStatusCode(), ResponseStatusCode.SUCCESS.toString());
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e) {
			APIResponse<CustomerNotifyProfileDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (NullPointerException e) {
			APIResponse<CustomerNotifyProfileDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
		} catch (SizeExceedException e) {
			APIResponse<CustomerNotifyProfileDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE.getStatusCode(), ResponseStatusCode.FAILURE.toString());
			return ResponseEntity.status(HttpStatus.LENGTH_REQUIRED).body(responseObj);
		} catch (Exception e) {
			APIResponse<CustomerNotifyProfileDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

	/* This Method Is Used to Delete Values */
	@DeleteMapping(value = ControllerConstants.CUSTOMER_NOTIFY_PROFILE)
	public ResponseEntity<List<APIResponse<CustomerNotifyProfileDTO>>> deleteCustomerNotifyProfile(
			@Valid @NotNull @RequestBody List<CustomerNotifyProfileDTO> customerNotifyProfiles) {
		List<APIResponse<CustomerNotifyProfileDTO>> response;
		AtomicInteger errorCount = new AtomicInteger();
		if (customerNotifyProfiles != null && !customerNotifyProfiles.isEmpty()) {
			response = customerNotifyProfiles.stream().map(customerNotifyProfileDto -> {
				APIResponse<CustomerNotifyProfileDTO> singleDtoDelResponse;
				try {
					service.deleteCustomerNotifyProfiles(CustomerNotifyProfileMapper.INSTANCE
							.customerNotifyProfileDtoToCustomerNotifyProfile(customerNotifyProfileDto));
					singleDtoDelResponse = new APIResponse<>(Arrays.asList("Successfully deleted data!"),
							customerNotifyProfileDto, ResponseStatusCode.SUCCESS);
				} catch (Exception e) {
					errorCount.incrementAndGet();
					singleDtoDelResponse = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
				}
				return singleDtoDelResponse;
			}).collect(Collectors.toList());
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