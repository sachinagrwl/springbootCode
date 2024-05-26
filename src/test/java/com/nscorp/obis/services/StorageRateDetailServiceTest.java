package com.nscorp.obis.services;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.nscorp.obis.domain.CustomerCharge;
import com.nscorp.obis.domain.StorageRates;
import com.nscorp.obis.domain.Terminal;
import com.nscorp.obis.dto.StorageRatesDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.repository.CustomerChargeRepository;
import com.nscorp.obis.repository.StorageRatesRepository;
import com.nscorp.obis.repository.TerminalRepository;

public class StorageRateDetailServiceTest {

	@InjectMocks
	StorageRateDetailServiceImpl storageRateDetailServiceImpl;

	@Mock
	StorageRatesRepository storageRatesRepo;
	
	@Mock
    CustomerChargeRepository customerChargeRepo;
	
	@Mock
	TerminalRepository terminalRepo;

	@Mock
	StorageRates storageRates;
	CustomerCharge customerCharge;
	Terminal terminal;
	
	StorageRatesDTO storageRatesDto;
	
	Long storageCode;
	Long storageCode1;
	Map<String, String> header;
	
	
	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		storageRates= new StorageRates();
		storageRates.setStorageId(456L);
		
		customerCharge= new CustomerCharge();
		customerCharge.setChrgId(123L);
		
		terminal = new Terminal();
		terminal.setTerminalId(987L);
	
		storageRatesDto=new StorageRatesDTO();

		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");

		
	}
	
	@AfterEach
	void tearDown() throws Exception {
		storageRates = null;
		customerCharge = null;
		storageRatesDto = null;
		
	}
	
	@Test
	void testGetStorageRateDetail() {
		when(customerChargeRepo.findByChrgId(Mockito.any())).thenReturn(customerCharge);
		when(storageRatesRepo.findByStorageId(Mockito.any())).thenReturn(storageRates);
		when(terminalRepo.findByTerminalId(Mockito.any())).thenReturn(terminal);
		StorageRates storage = storageRateDetailServiceImpl.getStorageRateDetail(123L);
		assertEquals(storage, storageRates);
		
		when(terminalRepo.findByTerminalId(Mockito.any())).thenReturn(null);
		storage = storageRateDetailServiceImpl.getStorageRateDetail(123L);
		assertEquals(storage, storageRates);
		
		when(storageRatesRepo.findByStorageId(Mockito.any())).thenReturn(null);
		when(terminalRepo.findByTerminalId(Mockito.any())).thenReturn(null);
		storage = storageRateDetailServiceImpl.getStorageRateDetail(123L);
		assertEquals(storage, storageRates);
		
	}

	@Test
	void testGetStorageRateDetailNoRecordFoundException() {
		customerCharge = null;
		when(customerChargeRepo.findByChrgId(storageCode1)).thenReturn(customerCharge);
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(storageRateDetailServiceImpl.getStorageRateDetail(storageCode1)));
		assertEquals("No Records Found!", exception.getMessage());

		
		storageRates = null;
		when(storageRatesRepo.findByStorageId(storageCode1)).thenReturn(storageRates);
		NoRecordsFoundException exception1 = assertThrows(NoRecordsFoundException.class,
				() -> when(storageRateDetailServiceImpl.getStorageRateDetail(storageCode1)));
		assertEquals("No Records Found!", exception1.getMessage());
	}

}
