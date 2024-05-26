package com.nscorp.obis.controller;

import static org.junit.Assert.assertEquals;
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
import org.springframework.http.ResponseEntity;

import com.nscorp.obis.domain.CustomerCharge;
import com.nscorp.obis.domain.StorageRates;
import com.nscorp.obis.dto.StorageRatesDTO;
import com.nscorp.obis.dto.mapper.StorageRateDetailMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.StorageRateDetailService;

public class StorageRateDetailControllerTest {
	
	@Mock
	StorageRateDetailMapper storageRateDetailMapper;
	@Mock
	StorageRateDetailService storageRateDetailService;
	@InjectMocks
	StorageRateDetailController storageRateDetailController;

	StorageRatesDTO storageRatesDto;
	StorageRates storageRates;
	CustomerCharge customerCharge;
	
	Map<String, String> header;

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		customerCharge= new CustomerCharge();
		customerCharge.setChrgId(123L);

		storageRates= new StorageRates();
		storageRates.setStorageId(456L);
		
		storageRatesDto=new StorageRatesDTO();
		storageRatesDto.setStorageId(789L);
		
		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");

	}

	@AfterEach
	void tearDown() throws Exception {
		storageRates=null;
		storageRatesDto = null;
		
	}

	@Test
	void testGetStorageRates() {
		when(storageRateDetailService.getStorageRateDetail(Mockito.any())).thenReturn(storageRates);
		ResponseEntity<APIResponse<StorageRatesDTO>> storage = storageRateDetailController.getStorageRates(123L);
		assertEquals(storage.getStatusCodeValue(), 200);
	}

	@Test
	void testStorageRatesNoRecordsFoundException() {
		when(storageRateDetailService.getStorageRateDetail(Mockito.any())).thenThrow(new NoRecordsFoundException());
        ResponseEntity<APIResponse<StorageRatesDTO>> storage = storageRateDetailController.getStorageRates(123L);
        assertEquals(storage.getStatusCodeValue(), 404);
	}
	@Test
	void testStorageRatesException() {
		when(storageRateDetailService.getStorageRateDetail(Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<StorageRatesDTO>> storage = storageRateDetailController.getStorageRates(123L);
		assertEquals(storage.getStatusCodeValue(), 500);
	}
}
