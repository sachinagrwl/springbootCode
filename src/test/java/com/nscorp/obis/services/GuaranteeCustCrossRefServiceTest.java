package com.nscorp.obis.services;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.nscorp.obis.common.CommonKeyGenerator;
import com.nscorp.obis.domain.GuaranteeCustCrossRef;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.repository.CorporateCustomerRepository;
import com.nscorp.obis.repository.GuaranteeCustCrossRefRepository;

class GuaranteeCustCrossRefServiceTest {

	@InjectMocks
	GuaranteeCustCrossRefServiceImpl crossRefServiceImpl;

	@Mock
	GuaranteeCustCrossRefRepository crossRefRepository;

	@Mock
	CorporateCustomerRepository corporateCustomerRepository;

	@Mock
	CommonKeyGenerator commonKeyGenerator;

	List<GuaranteeCustCrossRef> crossRefList;

	GuaranteeCustCrossRef crossRef;

	Map<String, String> headers;

	String customerName;
	String customerNumber;
	String terminalName;

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		crossRefList = new ArrayList<>();
		crossRef = new GuaranteeCustCrossRef();
		headers = new HashMap<>();

		crossRef.setGuaranteeCustXrefId(1000L);
		crossRef.setGuaranteeCustomerNumber("1000");
		crossRef.setCorpCustId(1000L);
		crossRefList.add(crossRef);

		headers.put("userid", "TEST");
		headers.put("extensionschema", "TEST");
	}

	@AfterEach
	void tearDown() throws Exception {
		crossRefList = null;
		crossRef = null;
		headers = null;
	}

	@Test
	void testGetAllGuaranteeCustCrossRef() {
		when(crossRefRepository.findGuaranteeCustCrossRef(Mockito.any(), Mockito.any(), Mockito.any()))
				.thenReturn(crossRefList);
		List<GuaranteeCustCrossRef> allGuaranteeCustCrossRef = crossRefServiceImpl
				.getAllGuaranteeCustCrossRef(customerName, customerNumber, terminalName);
		assertFalse(allGuaranteeCustCrossRef.isEmpty());
	}

	@Test
	void testAddGuaranteeCustCrossRef() {
		when(corporateCustomerRepository.existsByCorporateCustomerId(Mockito.any())).thenReturn(true);
		when(crossRefRepository.existsByGuaranteeCustLongNameAndGuaranteeCustomerNumberAndTerminalName(Mockito.any(),
				Mockito.any(), Mockito.any())).thenReturn(false);
		when(commonKeyGenerator.SGKLong()).thenReturn(1000L);
		when(crossRefRepository.save(Mockito.any())).thenReturn(crossRef);
		GuaranteeCustCrossRef addGuaranteeCustCrossRef = crossRefServiceImpl.addGuaranteeCustCrossRef(crossRef,
				headers);
		assertNotNull(addGuaranteeCustCrossRef);

	}

	@Test
	void testUpdateGuaranteeCustCrossRef() {
		when(corporateCustomerRepository.existsByCorporateCustomerId(Mockito.any())).thenReturn(true);
		when(crossRefRepository.existsByGuaranteeCustXrefIdAndCorpCustIdAndUversion(Mockito.any(), Mockito.any(),
				Mockito.any())).thenReturn(true);
		when(crossRefRepository.existsByGuaranteeCustLongNameAndGuaranteeCustomerNumberAndTerminalName(Mockito.any(),
				Mockito.any(), Mockito.any())).thenReturn(false);
		when(crossRefRepository.findByGuaranteeCustXrefIdAndCorpCustId(Mockito.any(), Mockito.any()))
				.thenReturn(crossRef);
		when(crossRefRepository.save(Mockito.any())).thenReturn(crossRef);
		GuaranteeCustCrossRef updateGuaranteeCustCrossRef = crossRefServiceImpl.updateGuaranteeCustCrossRef(crossRef,
				headers);
		assertNotNull(updateGuaranteeCustCrossRef);

	}

	@Test
	void testDeleteGuaranteeCustCrossRef() {
		when(crossRefRepository.existsByGuaranteeCustXrefIdAndCorpCustIdAndUversion(Mockito.any(), Mockito.any(),
				Mockito.any())).thenReturn(true);
		when(crossRefRepository.findByGuaranteeCustXrefIdAndCorpCustId(Mockito.any(), Mockito.any()))
				.thenReturn(crossRef);
		GuaranteeCustCrossRef deleteGuaranteeCustCrossRef = crossRefServiceImpl.deleteGuaranteeCustCrossRef(crossRef,
				headers);
		assertNotNull(deleteGuaranteeCustCrossRef);

	}

	@SuppressWarnings("unchecked")
	@Test
	void testNoRecordFoundException() {
		when(crossRefRepository.findGuaranteeCustCrossRef(Mockito.any(), Mockito.any(), Mockito.any()))
				.thenReturn(Collections.EMPTY_LIST);
		NoRecordsFoundException getException = assertThrows(NoRecordsFoundException.class, () -> when(
				crossRefServiceImpl.getAllGuaranteeCustCrossRef(customerName, customerNumber, terminalName)));
		assertEquals("No Records found!", getException.getMessage());

		when(crossRefRepository.existsByGuaranteeCustXrefIdAndCorpCustIdAndUversion(Mockito.any(), Mockito.any(),
				Mockito.any())).thenReturn(false);
		NoRecordsFoundException updateException = assertThrows(NoRecordsFoundException.class,
				() -> when(crossRefServiceImpl.updateGuaranteeCustCrossRef(crossRef, headers)));
		assertEquals(
				"No record Found to update Under this GuaranteeCustXrefId: " + crossRef.getGuaranteeCustXrefId()
						+ " ,CorpCustId: " + crossRef.getCorpCustId() + " and U_Version: " + crossRef.getUversion(),
				updateException.getMessage());

		NoRecordsFoundException deleteException = assertThrows(NoRecordsFoundException.class,
				() -> when(crossRefServiceImpl.deleteGuaranteeCustCrossRef(crossRef, headers)));
		assertEquals(
				"No record Found to delete Under this GuaranteeCustXrefId: " + crossRef.getGuaranteeCustXrefId()
						+ " ,CorpCustId: " + crossRef.getCorpCustId() + " and U_Version: " + crossRef.getUversion(),
				deleteException.getMessage());
	}

	@Test
	void testNullPointerException() {
		headers.put("extensionschema", null);
		NullPointerException addException = assertThrows(NullPointerException.class,
				() -> when(crossRefServiceImpl.addGuaranteeCustCrossRef(crossRef, headers)));
		assertEquals("Extension Schema should not be null, empty or blank", addException.getMessage());

		when(crossRefRepository.existsByGuaranteeCustXrefIdAndCorpCustIdAndUversion(Mockito.any(), Mockito.any(),
				Mockito.any())).thenReturn(true);
		NullPointerException updateException = assertThrows(NullPointerException.class,
				() -> when(crossRefServiceImpl.addGuaranteeCustCrossRef(crossRef, headers)));
		assertEquals("Extension Schema should not be null, empty or blank", updateException.getMessage());
	}

	@Test
	void testRecordNotAddedException() {
		when(corporateCustomerRepository.existsByCorporateCustomerId(Mockito.any())).thenReturn(false);
		RecordNotAddedException addException = assertThrows(RecordNotAddedException.class,
				() -> when(crossRefServiceImpl.addGuaranteeCustCrossRef(crossRef, headers)));
		assertEquals("Corporate Customer Id: " + crossRef.getCorpCustId()
				+ " is not valid as it doesn't exists in CORP CUST", addException.getMessage());
		crossRef.setGuaranteeCustLongName(null);
		crossRef.setGuaranteeCustomerNumber(null);
		RecordNotAddedException addException1 = assertThrows(RecordNotAddedException.class,
				() -> when(crossRefServiceImpl.addGuaranteeCustCrossRef(crossRef, headers)));
		assertEquals("Customer Name and Customer Number both should not be null/Blank!", addException1.getMessage());
	}

	@Test
	void testRecordAlreadyExistsException() {
		when(corporateCustomerRepository.existsByCorporateCustomerId(Mockito.any())).thenReturn(true);
		when(crossRefRepository.existsByGuaranteeCustLongNameAndGuaranteeCustomerNumberAndTerminalName(Mockito.any(),
				Mockito.any(), Mockito.any())).thenReturn(true);
		RecordAlreadyExistsException addException = assertThrows(RecordAlreadyExistsException.class,
				() -> when(crossRefServiceImpl.addGuaranteeCustCrossRef(crossRef, headers)));
		assertEquals("Record with Combination of Corporate Customer: " + crossRef.getGuaranteeCustLongName()
				+ " ,Customer: " + crossRef.getGuaranteeCustomerNumber() + " and Terminal: "
				+ crossRef.getTerminalName() + " is already exists!", addException.getMessage());
	}
}
