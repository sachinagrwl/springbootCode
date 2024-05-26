package com.nscorp.obis.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.CorporateCustomer;
import com.nscorp.obis.dto.CorporateCustomerDTO;
import com.nscorp.obis.dto.mapper.CorporateCustomerMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.CorporateCustomerService;

@RestController
@CrossOrigin
@RequestMapping("/")
public class CorporateCustomerController {
	@Autowired
	CorporateCustomerService corporateCustomerService;
	
	@Autowired
	CorporateCustomerMapper corporateCustomerMapper;
	
	@GetMapping(value= ControllerConstants.GET_CORPORATE_CUSTOMERS)
	public ResponseEntity<APIResponse<List<CorporateCustomerDTO>>> getCorporateCustomers(){
		
		try {
			List<CorporateCustomerDTO> corporateCustomerDtoList = Collections.emptyList();
			List<CorporateCustomer> corporateCustomerList = corporateCustomerService.getAllCorporateCustomers();
			if (corporateCustomerList != null && !corporateCustomerList.isEmpty()) {
				corporateCustomerDtoList = corporateCustomerList.stream()
						.map(CorporateCustomerMapper.INSTANCE::corporateCustomerToCorporateCustomerDTO)
						.collect(Collectors.toList());
			}
			APIResponse<List<CorporateCustomerDTO>> responseObj = new APIResponse<>(Arrays.asList("Successfully retrieved data!"),corporateCustomerDtoList, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e){
			APIResponse<List<CorporateCustomerDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		}
		catch (Exception e){
			APIResponse<List<CorporateCustomerDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}
	
	
}
