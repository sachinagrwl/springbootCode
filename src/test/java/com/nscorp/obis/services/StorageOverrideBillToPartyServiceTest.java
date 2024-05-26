package com.nscorp.obis.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.nscorp.obis.domain.StorageOverrideBillToParty;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.repository.CorporateCustomerRepository;
import com.nscorp.obis.repository.StorageOverrideBillToPartyRepository;

class StorageOverrideBillToPartyServiceTest {

	@InjectMocks
	StorageOverrideBillToPartyServiceImpl billToPartyServiceImpl;

	@Mock
	StorageOverrideBillToPartyRepository overrideBillToPartyRepository;

	@Mock
	CorporateCustomerRepository corporateCustomerRepository;

	StorageOverrideBillToParty billToParty;

	Map<String, String> headers;

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		billToParty = new StorageOverrideBillToParty();

		billToParty.setCorporateCustomerId(1000L);
		billToParty.setOverrideInd("Y");

		headers = new HashMap<>();
		headers.put("userid", "test");
		headers.put("extensionschema", "test");
	}

	@AfterEach
	void tearDown() throws Exception {
		billToParty = null;
		headers = null;
	}

	@Test
	void testUpdateOverrideBillToPartyInsert() {
		when(corporateCustomerRepository.existsById(Mockito.any())).thenReturn(true);
		when(overrideBillToPartyRepository.existsById(Mockito.any())).thenReturn(false);
		when(overrideBillToPartyRepository.save(Mockito.any())).thenReturn(billToParty);
		StorageOverrideBillToParty updateOverrideBillToParty = billToPartyServiceImpl
				.updateOverrideBillToParty(billToParty, headers);
		assertNotNull(updateOverrideBillToParty);
	}

	@Test
	void testUpdateOverrideBillToPartyUpdate() {
		when(corporateCustomerRepository.existsById(Mockito.any())).thenReturn(true);
		when(overrideBillToPartyRepository.existsById(Mockito.any())).thenReturn(true);
		when(overrideBillToPartyRepository.existsByCorporateCustomerIdAndUversion(Mockito.any(), Mockito.any()))
				.thenReturn(true);
		when(overrideBillToPartyRepository.findById(Mockito.any())).thenReturn(Optional.of(billToParty));
		when(overrideBillToPartyRepository.save(Mockito.any())).thenReturn(billToParty);
		StorageOverrideBillToParty updateOverrideBillToParty = billToPartyServiceImpl
				.updateOverrideBillToParty(billToParty, headers);
		assertNotNull(updateOverrideBillToParty);
	}

	@Test
	void testUpdateOverrideBillToPartyNoRecordFoundException() {
		when(corporateCustomerRepository.existsById(Mockito.any())).thenReturn(false);
		NoRecordsFoundException updateOverrideBillToPartyEx1 = assertThrows(NoRecordsFoundException.class,
				() -> when(billToPartyServiceImpl.updateOverrideBillToParty(billToParty, headers)));
		assertEquals(
				"Provided Corporate CustId: " + billToParty.getCorporateCustomerId()
						+ "is not valid as it doesn't exists in CORPORATE CUSTOMER",
				updateOverrideBillToPartyEx1.getMessage());

		when(corporateCustomerRepository.existsById(Mockito.any())).thenReturn(true);
		when(overrideBillToPartyRepository.existsById(Mockito.any())).thenReturn(true);
		when(overrideBillToPartyRepository.existsByCorporateCustomerIdAndUversion(Mockito.any(), Mockito.any()))
				.thenReturn(false);
		NoRecordsFoundException updateOverrideBillToPartyEx2 = assertThrows(NoRecordsFoundException.class,
				() -> when(billToPartyServiceImpl.updateOverrideBillToParty(billToParty, headers)));
		assertEquals("No record Found Under this CorporateCustomerId:" + billToParty.getCorporateCustomerId()
				+ " and Uversion:" + billToParty.getUversion(), updateOverrideBillToPartyEx2.getMessage());

	}

	@Test
	void testUpdateOverrideBillToPartyNullPointerException() {
		headers.put("extensionschema", null);
		NullPointerException updateOverrideBillToPartyEx = assertThrows(NullPointerException.class,
				() -> when(billToPartyServiceImpl.updateOverrideBillToParty(billToParty, headers)));
		assertEquals("Extension Schema should not be null, empty or blank", updateOverrideBillToPartyEx.getMessage());
	}

}
