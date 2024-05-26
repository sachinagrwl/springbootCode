package com.nscorp.obis.controller;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.CustomerLocalInfo;
import com.nscorp.obis.dto.CustomerLocalInfoDTO;
import com.nscorp.obis.dto.mapper.CustomerLocalInfoMapper;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.CustomerLocalInfoService;

import lombok.extern.slf4j.Slf4j;

@Validated
@RestController
@RequestMapping("/")
@Slf4j
public class CustomerLocalInfoController {

	@Autowired
	CustomerLocalInfoService customerLocalInfoService;

	@Autowired
	CustomerLocalInfoMapper customerLocalInfoMapper;

	@GetMapping(value = ControllerConstants.CUSTOMER_LOCALINFO)
	public ResponseEntity<APIResponse<CustomerLocalInfoDTO>> getCustomerLocalInfo(
			@RequestParam(name = "customer-id") Long customerId, @RequestParam(name = "terminal-id") Long terminalId)
			throws SQLException {
		try {
			CustomerLocalInfoDTO customerLocalInfoDTO = new CustomerLocalInfoDTO();
			CustomerLocalInfo customerLocalInfo = customerLocalInfoService.fetchCustomerLocalInfo(customerId,
					terminalId);
			if (customerLocalInfo != null) {
				customerLocalInfoDTO = CustomerLocalInfoMapper.INSTANCE
						.customerLocalInfoToCustomerLocalInfoDTO(customerLocalInfo);
			}
			APIResponse<CustomerLocalInfoDTO> responseObj = new APIResponse<CustomerLocalInfoDTO>(
					Arrays.asList("Successfully retrieve data!"), customerLocalInfoDTO,
					ResponseStatusCode.SUCCESS.getStatusCode(), ResponseStatusCode.SUCCESS.toString());
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);

		} catch (NoRecordsFoundException e) {
			APIResponse<CustomerLocalInfoDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (Exception e) {
			APIResponse<CustomerLocalInfoDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}
	
	
	@PostMapping(value = ControllerConstants.CUSTOMER_LOCALINFO)
	ResponseEntity<APIResponse<CustomerLocalInfoDTO>> addCustomerLocalInfo(
			@Valid @RequestBody CustomerLocalInfoDTO customerLocalInfoDTO,@RequestHeader Map<String,String> headers) {
		log.info("CustomerLocalInfoController : addCustomerLocalInfo : Method Starts");
		try {
			APIResponse<CustomerLocalInfoDTO> apiResponse = new APIResponse<>(
					Arrays.asList("Successfully data added!"),
					customerLocalInfoService.addCustomerLocalInfo(customerLocalInfoDTO,headers), ResponseStatusCode.SUCCESS);
			log.info("CustomerLocalInfoController : addCustomerLocalInfo : Method Ends");
			return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
		} catch (NoRecordsFoundException | InvalidDataException | RecordAlreadyExistsException e) {
			APIResponse<CustomerLocalInfoDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			log.error("CustomerLocalInfoController : addCustomerLocalInfo : "+e.getMessage());
			HttpStatus httpStatus=HttpStatus.NOT_FOUND;
			if(e.getClass()==InvalidDataException.class || e.getClass()==RecordAlreadyExistsException.class)
				httpStatus=HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(httpStatus).body(responseObj);
		} catch (Exception e) {
			APIResponse<CustomerLocalInfoDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			log.error("CustomerLocalInfoController : addCustomerLocalInfo : "+e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}
	
	

	@DeleteMapping(value = ControllerConstants.CUSTOMER_LOCALINFO)
	ResponseEntity<APIResponse<CustomerLocalInfoDTO>> deleteCustomerLocalInfo(
			@Valid @RequestBody CustomerLocalInfoDTO customerLocalInfoDTO) {
		log.info("CustomerLocalInfoController : deleteCustomerLocalInfo : Method Starts");
		try {
			APIResponse<CustomerLocalInfoDTO> apiResponse = new APIResponse<>(
					Arrays.asList("Successfully deleted data!"),
					customerLocalInfoService.deleteCustomerLocalInfo(customerLocalInfoDTO), ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
		} catch (NoRecordsFoundException e) {
			APIResponse<CustomerLocalInfoDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (Exception e) {
			APIResponse<CustomerLocalInfoDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}
	
	
	

	@PutMapping(value = ControllerConstants.CUSTOMER_LOCALINFO)
	ResponseEntity<APIResponse<CustomerLocalInfoDTO>> updateCustomerLocalInfo(
			@Valid @RequestBody CustomerLocalInfoDTO customerLocalInfoDTO, @RequestHeader Map<String, String> headers)
			throws SQLException {
		try {
			CustomerLocalInfo customerLocalInfo = customerLocalInfoService.updateCustomerLocalInfo(customerLocalInfoDTO,
					headers);
			CustomerLocalInfoDTO localInfoDTO = CustomerLocalInfoMapper.INSTANCE
					.customerLocalInfoToCustomerLocalInfoDTO(customerLocalInfo);
			APIResponse<CustomerLocalInfoDTO> responseObject = new APIResponse<>(
					Arrays.asList("Successfully Data Updated!"), localInfoDTO, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObject);
		} catch (NoRecordsFoundException e) {
			APIResponse<CustomerLocalInfoDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (Exception e) {
			APIResponse<CustomerLocalInfoDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

}
