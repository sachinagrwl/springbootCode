package com.nscorp.obis.services;

import com.nscorp.obis.domain.CorporateCustomer;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.repository.CorporateCustomerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class CorporateCustomerServiceTest {

	@InjectMocks
	CorporateCustomerServiceImpl corporateCustomerService;
	
	@Mock
	CorporateCustomerRepository corporateCustomerRepository;
	
	CorporateCustomer corporateCustomer;
	List<CorporateCustomer> corporateCustomerList;

	Map<String, String> header;
	
	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		
		MockitoAnnotations.initMocks(this);
		corporateCustomer = new CorporateCustomer();
		corporateCustomerList = new ArrayList<>();
		corporateCustomer.setCorporateCustomerId((long) 1.985733);
		corporateCustomer.setCorporateLongName("KLINE-THE RAILBRIDGE CORP");
		corporateCustomer.setCorporateShortName("KLNC");
		corporateCustomer.setCustomerId((long) 1.2839942823086E13);
		corporateCustomer.setIcghCd("TEST");
		corporateCustomer.setPrimaryLob("I");
		corporateCustomer.setSecondaryLob("OCN CARR");
		corporateCustomer.setScac("TEST");
		corporateCustomer.setTerminalFeedEnabled("TEST");
		corporateCustomer.setAccountManager("TEST");
		corporateCustomerList.add(corporateCustomer);

		
		header = new HashMap<String,String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");
	}
	
	@AfterEach
	void tearDown() throws Exception {
		corporateCustomer = null;
		corporateCustomerList = null;
	}

	@Test
	void testGetAllCorporateCustomers() {

		when(corporateCustomerRepository.findAllByOrderByCorporateLongName())
				.thenReturn(corporateCustomerList);
		List<CorporateCustomer> allCustomers = corporateCustomerService
				.getAllCorporateCustomers();
		assertEquals(allCustomers, corporateCustomerList);
	}

	@Test
	void testGetAllCorporateCustomersException() {
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(corporateCustomerService.getAllCorporateCustomers()));
		assertEquals("No Records found", exception.getMessage());
	}
}
