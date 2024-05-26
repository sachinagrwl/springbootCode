package com.nscorp.obis.controller;

import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.common.NullOrNotBlank;
import com.nscorp.obis.domain.CustomerIndex;
import com.nscorp.obis.dto.CustomerIndexDTO;
import com.nscorp.obis.dto.mapper.CustomerIndexMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.CustomerIndexService;

import lombok.extern.slf4j.Slf4j;

@RequestMapping("/")
@Validated
@RestController
@CrossOrigin
@Slf4j
public class CustomerIndexController {

	@Autowired
	CustomerIndexService customerIndexService;

	@GetMapping(value = ControllerConstants.CUSTOMER_INDEX)
	ResponseEntity<APIResponse<List<CustomerIndexDTO>>> getCustomers(
			@NullOrNotBlank(min = CommonConstants.CUST_NM_MIN_SIZE, max = 35, message = "Customer Name length should be between 1 and 35 and can't be empty") @RequestParam(name = "customerName", required = false) String customerName,
			@NullOrNotBlank(min = CommonConstants.CUST_NR_MIN_SIZE, max = CommonConstants.CUST_NR_MAX_SIZE, message = "Customer Number length should be between 1 and 10 and can't be empty") @RequestParam(name = "customerNumber", required = false) String customerNumber,
			@RequestParam(name = "city", required = false) String city,
			@RequestParam(name = "state", required = false) String state,
			@RequestParam(name = "corp-customer", required = false) Long corporateId,
			@RequestParam(name = "uniqueGroup", required = false) String uniqueGroup,
			@RequestParam(name = "latest", required = false) String latest,
			@RequestParam(name = "sort", required = false, defaultValue = "customerName, asc") String[] sort,
			@Pattern(regexp = "^[YN]{1}", message = "fetchExpired should be either Y or N !") @Nullable @RequestParam(name = "fetchExpired",defaultValue = "N" ,required = false) String fetchExpired) {
		try {
			log.info("getCustomers : Method Starts");
			if (customerName == null && customerNumber == null && corporateId==null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new APIResponse<>(Arrays.asList("Parameters are missing!"),
								ResponseStatusCode.INFORMATION.getStatusCode(),
								ResponseStatusCode.INFORMATION.toString()));
			}
			if (uniqueGroup == "y" && latest == "y") {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new APIResponse<>(Arrays.asList("UniqueGroup and Latest can not be queried together!"),
								ResponseStatusCode.INFORMATION.getStatusCode(),
								ResponseStatusCode.INFORMATION.toString()));
			}
			log.info("getCustomers : Method Ends");
			return ResponseEntity.status(HttpStatus.OK)
					.body(new APIResponse<>(Arrays.asList("Successfully retrieve data!"),
							customerIndexService.getCustomers(customerName, customerNumber, city, state, uniqueGroup,
									corporateId, latest, sort, fetchExpired),
							ResponseStatusCode.SUCCESS.getStatusCode(), ResponseStatusCode.SUCCESS.toString()));
		} catch (NoRecordsFoundException e) {
			APIResponse<List<CustomerIndexDTO>> response = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			log.error("getCustomers : " + e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (Exception e) {
			APIResponse<List<CustomerIndexDTO>> response = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			log.error("getCustomers : " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}
	
	@GetMapping(value = ControllerConstants.CUST_INDEX)
	ResponseEntity<APIResponse<CustomerIndexDTO>> getCustomerIndex(
			@RequestParam(required = true, name = "ntfy-queue-id") Long notifyQueueId) {
		
		try {
			CustomerIndex customerIndex = customerIndexService.getCustIndex(notifyQueueId);
			APIResponse<CustomerIndexDTO> responseObj = new APIResponse<>(Arrays.asList(CommonConstants.RETRIVED_SUCCESS_MESSAGE),
					CustomerIndexMapper.INSTANCE.customerIndexTCustomerIndexDTO(customerIndex), ResponseStatusCode.SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e) {
            APIResponse<CustomerIndexDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
        } catch (Exception e) {
            APIResponse<CustomerIndexDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
                    ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }
	}
}
