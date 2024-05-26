package com.nscorp.obis.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.QueryParameterException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;

import com.nscorp.obis.domain.CorporateCustomer;
import com.nscorp.obis.domain.EquipmentCustomerLesseeRange;
import com.nscorp.obis.domain.SortFilter;
import com.nscorp.obis.dto.CorporateCustomerDTO;
import com.nscorp.obis.dto.EquipmentCustomerLesseeRangeDTO;
import com.nscorp.obis.dto.mapper.EquipmentCustomerLesseeRangeMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.SizeExceedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.PaginationWrapper;
import com.nscorp.obis.services.EquipmentCustomerLesseeRangeService;

class EquipmentCustomerLesseeRangeControllerTest {

	@Mock
	EquipmentCustomerLesseeRangeService equipmentLesseeRangeService;

	@Mock
	EquipmentCustomerLesseeRangeMapper equipmentLesseeMapper;

	@InjectMocks
	EquipmentCustomerLesseeRangeController equipmentLesseController;

	EquipmentCustomerLesseeRange equipmentLesseeRange;
	EquipmentCustomerLesseeRangeDTO equipmentLesseeRangeDto;
	List<EquipmentCustomerLesseeRange> equipmentLesseeRangeList;
	Page<EquipmentCustomerLesseeRange> equipmentLesseeRangePageList;
	List<EquipmentCustomerLesseeRangeDTO> equipmentLesseeRangeDtoList;
	CorporateCustomer corpCustomer;
	CorporateCustomerDTO corpCustomerDTO;
	List<CorporateCustomer> corpCustomerList;
	PaginationWrapper paginationWrapper;
	Pageable pageable;
	Map<String, String> header;

	String equipmentInit = "TEST";
	String corporateLongName = "TEST";
	BigDecimal equipmentLowNumber;
	int pageNumber;
	int pageSize;
	String[] sort;

	List<String> responseList;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		equipmentLesseeRange = new EquipmentCustomerLesseeRange();
		equipmentLesseeRangeDto = new EquipmentCustomerLesseeRangeDTO();
		corpCustomer = new CorporateCustomer();
		corpCustomerList = new ArrayList<>();
		equipmentLesseeRangeList = new ArrayList<>();
		equipmentLesseeRangeDtoList = new ArrayList<>();

		equipmentLesseeRange.setEquipmentCustomerRangeId(100L);
		equipmentLesseeRange.setEquipmentHighNumber(BigDecimal.valueOf(35));
		equipmentLesseeRange.setEquipmentLowNumber(BigDecimal.valueOf(30));
		equipmentLesseeRange.setEquipmentInit(null);
		equipmentLesseeRange.setEquipmentLesseeId((long) 1.2839942823086E13);
		equipmentLesseeRange.setEquipmentOwnerId(100L);
		equipmentLesseeRange.setEquipmentOwnerType("TEST");
		equipmentLesseeRange.setEquipmentType("C");
		equipmentLesseeRange.setCorporateCustomer(corpCustomer);

		equipmentLesseeRangeDto.setEquipmentCustomerRangeId(100L);
		equipmentLesseeRangeDto.setEquipmentHighNumber(BigDecimal.valueOf(35));
		equipmentLesseeRangeDto.setEquipmentLowNumber(BigDecimal.valueOf(30));
		equipmentLesseeRangeDto.setEquipmentInit("TEST");
		equipmentLesseeRangeDto.setEquipmentLesseeId((long) 1.2839942823086E13);
		equipmentLesseeRangeDto.setEquipmentOwnerId(100L);
		equipmentLesseeRangeDto.setEquipmentOwnerType("TEST");
		equipmentLesseeRangeDto.setEquipmentType("C");
		equipmentLesseeRangeDto.setCorporateCustomer(corpCustomerDTO);

		corpCustomer.setCorporateCustomerId((long) 1.985733);
		corpCustomer.setCorporateLongName(null);
		corpCustomer.setCorporateShortName("KLNC");
		corpCustomer.setCustomerId((long) 1.2839942823086E13);
		corpCustomer.setIcghCd("TEST");
		corpCustomer.setPrimaryLob("I");
		corpCustomer.setSecondaryLob("OCN CARR");
		corpCustomer.setScac("TEST");
		corpCustomer.setTerminalFeedEnabled("TEST");
		corpCustomer.setAccountManager("TEST");
		corpCustomerList.add(corpCustomer);

		equipmentLesseeRangeList.add(equipmentLesseeRange);
		equipmentLesseeRangeDtoList.add(equipmentLesseeRangeDto);
		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");

		pageNumber = 1;
		pageSize = 10;
		sort = new String[] { "equipmentLowNumber,asc" };

		equipmentLesseeRangePageList = new PageImpl<>(equipmentLesseeRangeList, PageRequest.of(0, 10), 100L);
		pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.by(SortFilter.sortOrder(sort)));
		paginationWrapper = new PaginationWrapper(equipmentLesseeRangeList,
				equipmentLesseeRangePageList.getNumber() + 1, equipmentLesseeRangePageList.getTotalPages(),
				equipmentLesseeRangePageList.getTotalElements());

		responseList = new ArrayList<>();
		responseList.add("TEST");

	}

	@AfterEach
	void tearDown() throws Exception {
		equipmentLesseeRangeDtoList = null;
		equipmentLesseeRangeDto = null;
		equipmentLesseeRangeList = null;
		equipmentLesseeRange = null;
	}

	@Test
	void testGetEquipLesseeRange() {
		when(equipmentLesseeRangeService.getEquipLessee(equipmentInit, corporateLongName, pageable))
				.thenReturn(equipmentLesseeRangePageList);
		ResponseEntity<APIResponse<PaginationWrapper>> allEqLesseeRange = equipmentLesseController
				.getEquipLesseeRange(equipmentInit, corporateLongName, pageNumber, pageSize, sort);
		assertEquals(allEqLesseeRange.getStatusCodeValue(), 200);
	}

	@Test
	void testEquipLesseeRangeQueryParameterException() {
		when(equipmentLesseeRangeService.getEquipLessee(Mockito.any(), Mockito.any(), Mockito.any()))
				.thenThrow(new QueryParameterException("Incorrect Param"));
		ResponseEntity<APIResponse<PaginationWrapper>> allEqLesseeRange = equipmentLesseController
				.getEquipLesseeRange(equipmentInit, corporateLongName, pageNumber, pageSize, sort);
		assertEquals(allEqLesseeRange.getStatusCodeValue(), 400);
	}

	@Test
	void testEquipLesseeRangeException() {
		when(equipmentLesseeRangeService.getEquipLessee(Mockito.any(), Mockito.any(), Mockito.any()))
				.thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<PaginationWrapper>> allEqLesseeRange = equipmentLesseController
				.getEquipLesseeRange(equipmentInit, corporateLongName, pageNumber, pageSize, sort);
		assertEquals(allEqLesseeRange.getStatusCodeValue(), 500);

		when(equipmentLesseeRangeService.deleteEquipLesseeRange(Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<List<APIResponse<EquipmentCustomerLesseeRangeDTO>>> deleteResponse = equipmentLesseController
				.deleteEquipLesseeRange(equipmentLesseeRangeDtoList);
		assertEquals(deleteResponse.getStatusCodeValue(), 500);

		when(equipmentLesseeRangeService.getAllCorporateLongName()).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<List<String>>> response = equipmentLesseController.getAllCorporateLongName();
		assertEquals(response.getStatusCodeValue(), 500);

		when(equipmentLesseeRangeService.getAllEquipmentInit()).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<List<String>>> response1 = equipmentLesseController.getAllEquipmentInit();
		assertEquals(response1.getStatusCodeValue(), 500);
	}

	@Test
	void testEquipLesseeRangeNoRecordsFoundException() {
		when(equipmentLesseeRangeService.getEquipLessee(Mockito.any(), Mockito.any(), Mockito.any()))
				.thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<PaginationWrapper>> allEqLesseeRange = equipmentLesseController
				.getEquipLesseeRange(equipmentInit, corporateLongName, pageNumber, pageSize, sort);
		assertEquals(allEqLesseeRange.getStatusCodeValue(), 404);

		when(equipmentLesseeRangeService.deleteEquipLesseeRange(Mockito.any()))
				.thenThrow(new NoRecordsFoundException());
		ResponseEntity<List<APIResponse<EquipmentCustomerLesseeRangeDTO>>> deleteResponse = equipmentLesseController
				.deleteEquipLesseeRange(equipmentLesseeRangeDtoList);
		assertEquals(deleteResponse.getStatusCodeValue(), 500);
	}

	@Test
	void testEquipLesseeRangeSizeExceedException() {
		when(equipmentLesseeRangeService.getEquipLessee(Mockito.any(), Mockito.any(), Mockito.any()))
				.thenThrow(new SizeExceedException());
		ResponseEntity<APIResponse<PaginationWrapper>> allEqLesseeRange = equipmentLesseController
				.getEquipLesseeRange(equipmentInit, corporateLongName, pageNumber, pageSize, sort);
		assertEquals(allEqLesseeRange.getStatusCodeValue(), 411);
	}

	@Test
	void testEquipLesseeRangeNullPointerException() {
		when(equipmentLesseeRangeService.getEquipLessee(Mockito.any(), Mockito.any(), Mockito.any()))
				.thenThrow(new NullPointerException("Either 'eqInit' or 'corpLongName' should be present"));
		ResponseEntity<APIResponse<PaginationWrapper>> allEqLesseeRange = equipmentLesseController
				.getEquipLesseeRange(null, null, pageNumber, pageSize, sort);
		assertEquals(allEqLesseeRange.getStatusCodeValue(), 400);
	}

	@Test
	void testDeleteUmlerStackCar() {
		when(equipmentLesseeRangeService.deleteEquipLesseeRange(Mockito.any())).thenReturn(equipmentLesseeRange);
		ResponseEntity<List<APIResponse<EquipmentCustomerLesseeRangeDTO>>> deleteResponse = equipmentLesseController
				.deleteEquipLesseeRange(equipmentLesseeRangeDtoList);
		assertEquals(deleteResponse.getStatusCodeValue(), 200);
	}

	@SuppressWarnings("unchecked")
	@Test
	void testDeleteEmptyList() {
		when(equipmentLesseeRangeService.deleteEquipLesseeRange(Mockito.any())).thenReturn(equipmentLesseeRange);
		ResponseEntity<List<APIResponse<EquipmentCustomerLesseeRangeDTO>>> deleteResponse = equipmentLesseController
				.deleteEquipLesseeRange(Collections.EMPTY_LIST);
		assertEquals(deleteResponse.getStatusCodeValue(), 500);
	}

	@Test
	void testGetAllEquipmentInit() {
		when(equipmentLesseeRangeService.getAllEquipmentInit()).thenReturn(responseList);
		ResponseEntity<APIResponse<List<String>>> response = equipmentLesseController.getAllEquipmentInit();
		assertEquals(response.getStatusCodeValue(), 200);
	}

	@Test
	void testGetCorpLongName() {
		when(equipmentLesseeRangeService.getAllCorporateLongName()).thenReturn(responseList);
		ResponseEntity<APIResponse<List<String>>> response = equipmentLesseController.getAllCorporateLongName();
		assertEquals(response.getStatusCodeValue(), 200);
	}
}
