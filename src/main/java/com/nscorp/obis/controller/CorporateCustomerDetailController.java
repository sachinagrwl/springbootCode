package com.nscorp.obis.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.CorporateCustomerDetail;
import com.nscorp.obis.dto.CorporateCustomerDetailDTO;
import com.nscorp.obis.dto.mapper.CorporateCustomerDetailMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.CorporateCustomerDetailService;

@RestController
@CrossOrigin
@RequestMapping("/")
public class CorporateCustomerDetailController {

	@Autowired
	CorporateCustomerDetailService corporateCustomerDetailService;

	@Autowired
	CorporateCustomerDetailMapper corporateCustomerDetailMapper;

	@GetMapping(value = ControllerConstants.CORP_CUST_DTL)
	public ResponseEntity<APIResponse<List<CorporateCustomerDetail>>> getCorporateCustomers(
			@RequestParam(name = "corpCustId") Long corpCustId,
			@RequestParam(name = "corpPrimNbr", required = false) String corpCust6) {
		try {
			List<CorporateCustomerDetailDTO> corporateCustomerDtoList = Collections.emptyList();
			List<CorporateCustomerDetail> corporateCustomerList = corporateCustomerDetailService
					.getCorporateCustomerDetails(corpCustId, corpCust6);
			if (corporateCustomerList != null && !corporateCustomerList.isEmpty()) {
				corporateCustomerDtoList = corporateCustomerList.stream().map(
						CorporateCustomerDetailMapper.INSTANCE::corporateCustomerDetailToCorporateCustomerDetailDTO)
						.collect(Collectors.toList());
			}
			APIResponse<List<CorporateCustomerDetail>> responseObj = new APIResponse<>(
					Arrays.asList("Successfully retrieved data!"), corporateCustomerList, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);
		} catch (NoRecordsFoundException e) {
			APIResponse<List<CorporateCustomerDetail>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (Exception e) {
			APIResponse<List<CorporateCustomerDetail>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

	@DeleteMapping(value = ControllerConstants.CORP_CUST_DTL)
	public ResponseEntity<List<APIResponse<CorporateCustomerDetailDTO>>> deletecorpCustDtl(
			@RequestBody List<CorporateCustomerDetailDTO> corpCustDtlDtoList) {
		List<APIResponse<CorporateCustomerDetailDTO>> response;
		AtomicInteger errorCount = new AtomicInteger();

		if (corpCustDtlDtoList != null && !corpCustDtlDtoList.isEmpty()) {
			response = corpCustDtlDtoList.stream().map(tableObjDto -> {
				APIResponse<CorporateCustomerDetailDTO> singleDtoDelResponse;
				try {
					corporateCustomerDetailService.deleteCorpCustDetail(
							CorporateCustomerDetailMapper.INSTANCE.corporateCustomerDetailDTOToCorporateCustomerDetail(tableObjDto));
					singleDtoDelResponse = new APIResponse<>(Arrays.asList("Successfully deleted data!"), tableObjDto,
							ResponseStatusCode.SUCCESS);
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

	@PostMapping(value = ControllerConstants.CORP_CUST_DTL)
	public ResponseEntity<APIResponse<CorporateCustomerDetailDTO>> addPrimary6(
			@Valid @RequestBody CorporateCustomerDetailDTO corporateCustomerDetailDto,
			@RequestHeader Map<String, String> headers) {
		try {
			CorporateCustomerDetail CustomerDtl = CorporateCustomerDetailMapper.INSTANCE
					.corporateCustomerDetailDTOToCorporateCustomerDetail(corporateCustomerDetailDto);
			CorporateCustomerDetail CustomerDtlAdded = corporateCustomerDetailService.addPrimary6(CustomerDtl, headers);
			CorporateCustomerDetailDTO addPrimary6 = CorporateCustomerDetailMapper.INSTANCE
					.corporateCustomerDetailToCorporateCustomerDetailDTO(CustomerDtlAdded);
			APIResponse<CorporateCustomerDetailDTO> responseObj = new APIResponse<>(
					Arrays.asList("Successfully added data!"), addPrimary6, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);

		} catch (NoRecordsFoundException e) {
			APIResponse<CorporateCustomerDetailDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (RecordAlreadyExistsException e) {
			APIResponse<CorporateCustomerDetailDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(responseObj);
		} catch (Exception e) {
			APIResponse<CorporateCustomerDetailDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

}
