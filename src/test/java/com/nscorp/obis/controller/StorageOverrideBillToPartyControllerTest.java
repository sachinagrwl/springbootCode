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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.nscorp.obis.domain.StorageOverrideBillToParty;
import com.nscorp.obis.dto.StorageOverrideBillToPartyDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.StorageOverrideBillToPartyService;

class StorageOverrideBillToPartyControllerTest {

	@InjectMocks
	StorageOverrideBillToPartyController billToPartyController;

	@Mock
	StorageOverrideBillToPartyService billToPartyService;

	StorageOverrideBillToParty billToParty;

	StorageOverrideBillToPartyDTO billToPartyDTO;

	Map<String, String> headers;

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		billToParty = new StorageOverrideBillToParty();
		billToPartyDTO = new StorageOverrideBillToPartyDTO();

		billToParty.setCorporateCustomerId(1000L);
		billToParty.setOverrideInd("Y");

		billToPartyDTO.setCorporateCustomerId(1000L);
		billToPartyDTO.setOverrideInd("Y");
		headers = new HashMap<>();
		headers.put("userid", "test");
		headers.put("extensionschema", "test");
	}

	@AfterEach
	void tearDown() throws Exception {
		billToParty = null;
		billToPartyDTO = null;
		headers = null;
	}

	@Test
	void testUpdateOverrideBillToParty() {
		when(billToPartyService.updateOverrideBillToParty(Mockito.any(), Mockito.any())).thenReturn(billToParty);
		ResponseEntity<APIResponse<StorageOverrideBillToPartyDTO>> updateOverrideBillToParty = billToPartyController
				.updateOverrideBillToParty(billToPartyDTO, headers);
		assertEquals(HttpStatus.OK, updateOverrideBillToParty.getStatusCode());
	}

	@Test
	void testUpdateOverrideBillToPartyNoRecordsFoundException() {
		when(billToPartyService.updateOverrideBillToParty(Mockito.any(), Mockito.any()))
				.thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<StorageOverrideBillToPartyDTO>> updateOverrideBillToParty = billToPartyController
				.updateOverrideBillToParty(billToPartyDTO, headers);
		assertEquals(HttpStatus.NOT_FOUND, updateOverrideBillToParty.getStatusCode());
	}

	@Test
	void testUpdateOverrideBillToPartyNullPointerException() {
		when(billToPartyService.updateOverrideBillToParty(Mockito.any(), Mockito.any()))
				.thenThrow(new NullPointerException());
		ResponseEntity<APIResponse<StorageOverrideBillToPartyDTO>> updateOverrideBillToParty = billToPartyController
				.updateOverrideBillToParty(billToPartyDTO, headers);
		assertEquals(HttpStatus.BAD_REQUEST, updateOverrideBillToParty.getStatusCode());
	}

	@Test
	void testUpdateOverrideBillToPartyException() {
		when(billToPartyService.updateOverrideBillToParty(Mockito.any(), Mockito.any()))
				.thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<StorageOverrideBillToPartyDTO>> updateOverrideBillToParty = billToPartyController
				.updateOverrideBillToParty(billToPartyDTO, headers);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, updateOverrideBillToParty.getStatusCode());
	}

}
