package com.nscorp.obis.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.CustomerPerDiemRateSelection;
import com.nscorp.obis.dto.CustomerPerDiemRateSelectionDTO;
import com.nscorp.obis.dto.mapper.CustomerPerDiemRateSelectionMapper;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.CustomerPerDiemRateSelectionService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

@Validated
@RestController
@RequestMapping("/")
@Slf4j
public class CustomerPerDiemRateSelectionController {

	@Autowired
	CustomerPerDiemRateSelectionService customerPerDiemRateSelectionService;

	@GetMapping(value = ControllerConstants.PER_DIEM_RATES)
	@Operation(description = "Fetch all the values")
	public ResponseEntity<APIResponse<List<CustomerPerDiemRateSelectionDTO>>> getCustomerPerDiemRate(

			@NotNull(message = "Customer_Primsix be provided as request parameter.") 
			@Size(min = 6, message = "Customer_Primsix should have atleast 6 digits")
			@Size(max = 6, message = "Customer_Primsix should not have more than 6 digits")
			@RequestParam("custPrimSix") String custPrimSix)
			throws SQLException {
		try {
			List<CustomerPerDiemRateSelectionDTO> customerPerDiemRateSelectionDTO = new ArrayList<>();

			List<CustomerPerDiemRateSelection> customerPerDiemRateSelection = customerPerDiemRateSelectionService
					.fetchCustomerPerDiemRate(custPrimSix);
			if (customerPerDiemRateSelection != null && !customerPerDiemRateSelection.isEmpty()) {
				customerPerDiemRateSelectionDTO = customerPerDiemRateSelection.stream().map(
						CustomerPerDiemRateSelectionMapper.INSTANCE::customerPerDiemRateSelectionToCustomerPerDiemRateSelectionDTO)
						.collect(Collectors.toList());
			}

			APIResponse<List<CustomerPerDiemRateSelectionDTO>> responseObj = new APIResponse<>(
					Arrays.asList("Successfully retrieved data!"), customerPerDiemRateSelectionDTO,
					ResponseStatusCode.SUCCESS.getStatusCode(), ResponseStatusCode.SUCCESS.toString());
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);

		} catch (NoRecordsFoundException e) {
			APIResponse<List<CustomerPerDiemRateSelectionDTO>> responseObj = new APIResponse<>(
					Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION.getStatusCode(),
					ResponseStatusCode.INFORMATION.toString());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (Exception e) {
			APIResponse<List<CustomerPerDiemRateSelectionDTO>> responseObj = new APIResponse<>(
					Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION.getStatusCode(),
					ResponseStatusCode.INFORMATION.toString());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

	@PutMapping(value = ControllerConstants.PER_DIEM_RATES)
	ResponseEntity<APIResponse<CustomerPerDiemRateSelectionDTO>> updatePerDiemRate(
			@Valid @RequestBody CustomerPerDiemRateSelectionDTO perDiemRateDTO, @RequestHeader Map<String, String> headers)
			throws SQLException {
		try {
			log.info("updatePerDiemRate : Method Starts");
			APIResponse<CustomerPerDiemRateSelectionDTO> responseObj = new APIResponse<>(
					Arrays.asList("Successfully updated data!"),
					customerPerDiemRateSelectionService.updateCustomerPerDiemRate(perDiemRateDTO, headers),
					ResponseStatusCode.SUCCESS.getStatusCode(), ResponseStatusCode.SUCCESS.toString());
			log.info("updatePerDiemRate : Method Ends");
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);

		} catch (NoRecordsFoundException | InvalidDataException e) {
			APIResponse<CustomerPerDiemRateSelectionDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			HttpStatus status = HttpStatus.NOT_FOUND;
			if (e.getClass() == InvalidDataException.class)
				status = HttpStatus.BAD_REQUEST;
			log.error("updatePerDiemRate : Error " + e.getMessage());
			return ResponseEntity.status(status).body(responseObj);
		} catch (Exception e) {
			APIResponse<CustomerPerDiemRateSelectionDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			log.error("updatePerDiemRate : Error " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

	@PostMapping(value = ControllerConstants.PER_DIEM_RATES)
	ResponseEntity<APIResponse<CustomerPerDiemRateSelectionDTO>> addPerDiemRate(
			@Valid @RequestBody CustomerPerDiemRateSelectionDTO perDiemRateDTO, @RequestHeader Map<String, String> headers)
			throws SQLException {
		try {
			log.info("addPerDiemRate : Method Starts");
			APIResponse<CustomerPerDiemRateSelectionDTO> responseObj = new APIResponse<>(
					Arrays.asList("Successfully added data!"),
					customerPerDiemRateSelectionService.addCustomerPerDiemRate(perDiemRateDTO, headers),
					ResponseStatusCode.SUCCESS.getStatusCode(), ResponseStatusCode.SUCCESS.toString());
			log.info("addPerDiemRate : Method Ends");
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);

		} catch (NoRecordsFoundException | InvalidDataException e) {
			APIResponse<CustomerPerDiemRateSelectionDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			HttpStatus status = HttpStatus.NOT_FOUND;
			if (e.getClass() == InvalidDataException.class)
				status = HttpStatus.BAD_REQUEST;
			log.error("addPerDiemRate : Error " + e.getMessage());
			return ResponseEntity.status(status).body(responseObj);
		} catch (Exception e) {
			APIResponse<CustomerPerDiemRateSelectionDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			log.error("addPerDiemRate : Error " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}
}
