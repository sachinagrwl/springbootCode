package com.nscorp.obis.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.nscorp.obis.exception.InvalidDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.CustomerCharge;
import com.nscorp.obis.dto.CustomerChargeDTO;
import com.nscorp.obis.dto.mapper.CustomerChargeMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.CustomerChargeService;

import lombok.extern.slf4j.Slf4j;

@RequestMapping("/")
@RestController
@CrossOrigin
@Slf4j
public class CustomerChargeController {

	@Autowired
	CustomerChargeService customerChargeService;

	@GetMapping(value = ControllerConstants.STORAGECHARGE)
	public ResponseEntity<APIResponse<List<CustomerChargeDTO>>> searchCustomerCharge(

			@NotEmpty(message = "equip init should not be NULL.")
			@Size( max=4, message="equip init less than 4 Char")
			@RequestParam(value = "equip-init", required = true) String equipInit,

			@NotNull(message = "equip nbr should not be NULL.")
			@Digits(integer = 6, fraction = 0, message = "equip nbr should have 6 digits")
			@RequestParam(value = "equip-nbr", required = true) Integer equipNbr) {

		try {
			log.info("CustomerChargeController : getStorageCharge - Method Starts");
			List<CustomerChargeDTO> storageChrg = customerChargeService.getStorageCharge(equipInit,equipNbr);
			APIResponse <List<CustomerChargeDTO>> responseObj;
			List<String> message = Arrays.asList("Successfully retrieved data!");
			responseObj = new APIResponse<>(message, storageChrg, ResponseStatusCode.SUCCESS.getStatusCode(),
					ResponseStatusCode.SUCCESS.toString());
			log.info("CustomerChargeController : getStorageCharge - Method Ends");
			return (ResponseEntity<APIResponse <List<CustomerChargeDTO>>>) ResponseEntity.status(HttpStatus.OK).body(responseObj);

		} catch (NoRecordsFoundException e) {
			APIResponse<List<CustomerChargeDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (Exception e) {
			APIResponse<List<CustomerChargeDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}
	
	 @PutMapping(value= ControllerConstants.STORAGECHARGE)
	    public ResponseEntity<APIResponse<CustomerChargeDTO>> updateCustomerCharge(@Valid @RequestBody CustomerChargeDTO customerChargeDTO, @RequestHeader Map<String, String> headers){
	        try {
	            CustomerCharge customerCharge = customerChargeService.updateCustomerCharge(customerChargeDTO,headers);
	            CustomerChargeDTO toCustomerChargeDTO = CustomerChargeMapper.INSTANCE.CustomerChargeToCustomerChargeDTO(customerCharge);
	            APIResponse<CustomerChargeDTO> responseObject = new APIResponse<>(
	                    Arrays.asList("Successfully Data Updated!"), toCustomerChargeDTO, ResponseStatusCode.SUCCESS);
	            return ResponseEntity.status(HttpStatus.OK).body(responseObject);
	        } catch (NoRecordsFoundException e) {
	            APIResponse<CustomerChargeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
	                    ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
	        }
			catch (InvalidDataException e) {
				APIResponse<CustomerChargeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
						ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
			}catch (Exception e) {
	            APIResponse<CustomerChargeDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
	                    ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
	        }
	    }

}
