package com.nscorp.obis.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.QueryParameterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.EquipmentCustomerLesseeRange;
import com.nscorp.obis.domain.SortFilter;
import com.nscorp.obis.dto.EquipmentCustomerLesseeRangeDTO;
import com.nscorp.obis.dto.mapper.EquipmentCustomerLesseeRangeMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.SizeExceedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.PaginationWrapper;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.EquipmentCustomerLesseeRangeService;

@RestController
@RequestMapping(value = ControllerConstants.EQUIP_LESSEE_RANGE)
public class EquipmentCustomerLesseeRangeController {

	@Autowired
	EquipmentCustomerLesseeRangeService equipmentLesseeService;

	@GetMapping
	public ResponseEntity<APIResponse<PaginationWrapper>> getEquipLesseeRange(
			@RequestParam(required = false, name = "eqInit") String equipmentInit,
			@RequestParam(required = false, name = "corpLongName") String corporateLongName,
			@RequestParam int pageNumber, // Page number
			@RequestParam int pageSize,
			@RequestParam(required = false, defaultValue = "equipmentInit:asc,equipmentLowNumber:asc") String[] sort) {
		try {
			if (StringUtils.isBlank(equipmentInit) && StringUtils.isBlank(corporateLongName)) {
				throw new NullPointerException("Either 'eqInit' or 'corpLongName' should be present");
			}

			Pageable pageable = PageRequest.of(pageNumber == 0 ? pageNumber : pageNumber - 1, pageSize,
					Sort.by(SortFilter.sortOrder(sort)));
			Page<EquipmentCustomerLesseeRange> equipLessee = equipmentLesseeService.getEquipLessee(equipmentInit,
					corporateLongName, pageable);

			List<EquipmentCustomerLesseeRangeDTO> equipLesseeDtoList = Collections.emptyList();
			List<EquipmentCustomerLesseeRange> equipLesseeList = equipLessee.getContent();

			if (!CollectionUtils.isEmpty(equipLesseeList)) {
				equipLesseeDtoList = equipLesseeList.stream().map(
						EquipmentCustomerLesseeRangeMapper.INSTANCE::EquipmentCustomerLesseeRangeToEquipmentCustomerLesseeRangeDTO)
						.collect(Collectors.toList());
			}

			PaginationWrapper paginationWrapper = new PaginationWrapper(equipLesseeDtoList, equipLessee.getNumber() + 1,
					equipLessee.getTotalPages(), equipLessee.getTotalElements());
			APIResponse<PaginationWrapper> listAPIResponse = new APIResponse<>(
					Arrays.asList(CommonConstants.RETRIVED_SUCCESS_MESSAGE), paginationWrapper,
					ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(listAPIResponse);
		} catch (NullPointerException e) {
			APIResponse<PaginationWrapper> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
		} catch (DataAccessException | QueryParameterException exception) {
			APIResponse<PaginationWrapper> responseObj = new APIResponse<>(
					Arrays.asList("Request Parameter is incorrect!"), ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
		} catch (SizeExceedException e) {
			APIResponse<PaginationWrapper> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.LENGTH_REQUIRED).body(responseObj);
		} catch (NoRecordsFoundException e) {
			APIResponse<PaginationWrapper> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (Exception e) {
			APIResponse<PaginationWrapper> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

	@DeleteMapping
	public ResponseEntity<List<APIResponse<EquipmentCustomerLesseeRangeDTO>>> deleteEquipLesseeRange(
			@RequestBody List<EquipmentCustomerLesseeRangeDTO> equipmentCustomerLesseeRangeDTOList) {
		List<APIResponse<EquipmentCustomerLesseeRangeDTO>> responseDTOList;
		AtomicInteger errorCount = new AtomicInteger();
		if (!CollectionUtils.isEmpty(equipmentCustomerLesseeRangeDTOList)) {
			responseDTOList = equipmentCustomerLesseeRangeDTOList.stream().map(equipCustLesseeDTO -> {
				APIResponse<EquipmentCustomerLesseeRangeDTO> singleDtoDelResponse;
				try {
					EquipmentCustomerLesseeRange equipmentCustomerLessee = equipmentLesseeService
							.deleteEquipLesseeRange(EquipmentCustomerLesseeRangeMapper.INSTANCE
									.EquipmentCustomerLesseeRangeDTOToEquipmentCustomerLesseeRange(equipCustLesseeDTO));
					singleDtoDelResponse = new APIResponse<>(Arrays.asList(CommonConstants.DELETE_SUCCESS_MESSAGE),
							EquipmentCustomerLesseeRangeMapper.INSTANCE
									.EquipmentCustomerLesseeRangeToEquipmentCustomerLesseeRangeDTO(
											equipmentCustomerLessee),
							ResponseStatusCode.SUCCESS);
				} catch (NoRecordsFoundException e) {
					errorCount.incrementAndGet();
					singleDtoDelResponse = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
				} catch (Exception e) {
					errorCount.incrementAndGet();
					singleDtoDelResponse = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
				}
				return singleDtoDelResponse;
			}).collect(Collectors.toList());
		} else {
			responseDTOList = Collections.emptyList();
		}

		if (errorCount.get() == 0 && responseDTOList.size() > 0) // No errors and at least 1 success
			return ResponseEntity.status(HttpStatus.OK).body(responseDTOList);
		else if (responseDTOList.size() > errorCount.get()) // Partial success
			return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(responseDTOList);
		else
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTOList);
	}

	@GetMapping(value = ControllerConstants.EQUIP_INIT)
	public ResponseEntity<APIResponse<List<String>>> getAllEquipmentInit() {
		try {
			List<String> eqInitList = equipmentLesseeService.getAllEquipmentInit();
			APIResponse<List<String>> listAPIResponse = new APIResponse<>(
					Arrays.asList(CommonConstants.RETRIVED_SUCCESS_MESSAGE), eqInitList, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(listAPIResponse);
		} catch (Exception e) {
			APIResponse<List<String>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}

	@GetMapping(value = ControllerConstants.CORP_LONG_NAME)
	public ResponseEntity<APIResponse<List<String>>> getAllCorporateLongName() {
		try {
			List<String> corpLongNames = equipmentLesseeService.getAllCorporateLongName();
			APIResponse<List<String>> listAPIResponse = new APIResponse<>(
					Arrays.asList(CommonConstants.RETRIVED_SUCCESS_MESSAGE), corpLongNames, ResponseStatusCode.SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(listAPIResponse);
		} catch (Exception e) {
			APIResponse<List<String>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.FAILURE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}
}
