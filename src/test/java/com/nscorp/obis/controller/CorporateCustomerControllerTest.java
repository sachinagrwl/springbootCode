package com.nscorp.obis.controller;

import com.nscorp.obis.domain.CorporateCustomer;
import com.nscorp.obis.dto.CorporateCustomerDTO;
import com.nscorp.obis.dto.mapper.CorporateCustomerMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.CorporateCustomerService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class CorporateCustomerControllerTest {

	@Mock
	CorporateCustomerService corporateCustomerService;

	@Mock
	CorporateCustomerMapper corporateCustomerMapper;

	@InjectMocks
	CorporateCustomerController corporateCustomerController;

	CorporateCustomerDTO corporateCustomerDto;
	CorporateCustomer corporateCustomer;
	List<CorporateCustomer> corporateCustomerList;
	List<CorporateCustomerDTO> corporateCustomerDtoList;
	Map<String, String> header;

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		corporateCustomer = new CorporateCustomer();
		corporateCustomerDto = new CorporateCustomerDTO();
		corporateCustomerDtoList = new ArrayList<>();
		corporateCustomerList = new ArrayList<>();
		corporateCustomerDto.setCorporateCustomerId((long) 1.985733);
		corporateCustomerDto.setCorporateLongName("KLINE-THE RAILBRIDGE CORP");
		corporateCustomerDto.setCorporateShortName("KLNC");
		corporateCustomerDto.setCustomerId((long) 1.2839942823086E13);
		corporateCustomerDto.setIcghCd(null);
		corporateCustomerDto.setPrimaryLob("I");
		corporateCustomerDto.setSecondaryLob("OCN CARR");
		corporateCustomerDto.setScac(null);
		corporateCustomerDto.setTerminalFeedEnabled(null);
		corporateCustomerDto.setAccountManager(null);
		corporateCustomerDtoList.add(corporateCustomerDto);

		corporateCustomer.setCorporateCustomerId((long) 1.985733);
		corporateCustomer.setCorporateLongName("KLINE-THE RAILBRIDGE CORP");
		corporateCustomer.setCorporateShortName("KLNC");
		corporateCustomer.setCustomerId((long) 1.2839942823086E13);
		corporateCustomer.setIcghCd(null);
		corporateCustomer.setPrimaryLob("I");
		corporateCustomer.setSecondaryLob("OCN CARR");
		corporateCustomer.setScac(null);
		corporateCustomer.setTerminalFeedEnabled(null);
		corporateCustomer.setAccountManager(null);
		corporateCustomerList.add(corporateCustomer);

		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");
	}

	@AfterEach
	void tearDown() throws Exception {
		corporateCustomer = null;
		corporateCustomerDto = null;
		corporateCustomerDtoList = null;
		corporateCustomerList = null;
	}

	@Test
	@DisplayName("Get Corporate Customers")
	void testGetCorporateCustomers() {
		when(corporateCustomerService.getAllCorporateCustomers()).thenReturn(corporateCustomerList);
		ResponseEntity<APIResponse<List<CorporateCustomerDTO>>> getCorporateCustomersList = corporateCustomerController
				.getCorporateCustomers();
		assertEquals(getCorporateCustomersList.getStatusCodeValue(), 200);
	}

	@Test
	@DisplayName("No Corporate Customers Found")
	void testCorporateCustomersNoRecordsFoundException() {
		when(corporateCustomerService.getAllCorporateCustomers()).thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<List<CorporateCustomerDTO>>> corporateCustomerListGet = corporateCustomerController
				.getCorporateCustomers();
		assertEquals(corporateCustomerListGet.getStatusCodeValue(), 404);

	}

	@Test
	@DisplayName("Internal Server Error")
	void testCorporateCustomersException() {
		when(corporateCustomerService.getAllCorporateCustomers()).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<List<CorporateCustomerDTO>>> corporateCustomerListGet = corporateCustomerController
				.getCorporateCustomers();
		assertEquals(corporateCustomerListGet.getStatusCodeValue(), 500);

	}

}
