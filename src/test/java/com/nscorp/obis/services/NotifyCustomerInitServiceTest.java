package com.nscorp.obis.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.domain.CustomerIndex;
import com.nscorp.obis.domain.NotifyCustomerInit;
import com.nscorp.obis.dto.NotifyCustomerInitDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.repository.CustomerIndexRepository;
import com.nscorp.obis.repository.NotifyCustomerInitRepository;

public class NotifyCustomerInitServiceTest {

	@InjectMocks
	NotifyCustomerInitServiceImpl notifyCustomerInitService;

	@Mock
	NotifyCustomerInitRepository notifyCustomerInitRepository;

	@Mock
	CustomerIndexRepository customerIndexRepository;

	NotifyCustomerInit notifyCustomerInit;
	NotifyCustomerInit notifyCustomerInitDB;
	NotifyCustomerInitDTO notifyCustomerInitDto;
	List<NotifyCustomerInit> notifyCustomerInitList;
	List<NotifyCustomerInit> notifyCustomerInitListDB;
	List<NotifyCustomerInitDTO> notifyCustomerInitDtoList;
	Set<String> initials;
	Set<String> initialsDB;
	Map<String, String> header;

	CustomerIndex customerIndex;
	Long custId;

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {

		MockitoAnnotations.initMocks(this);
		notifyCustomerInit = new NotifyCustomerInit();
		notifyCustomerInit.setEqInit("Test");
		notifyCustomerInit.setCustId(100L);
		initials = new HashSet<>();
		initials.add("ABCD");
		initials.add("AAAA");
		initials.add("");
		notifyCustomerInit.setEqInitList(initials);
		notifyCustomerInitList = new ArrayList<>();
		notifyCustomerInitList.add(notifyCustomerInit);

		notifyCustomerInitDto = new NotifyCustomerInitDTO();
		notifyCustomerInitDto.setEqInit("Test");
		notifyCustomerInitDto.setCustId(100L);

		notifyCustomerInitDtoList = new ArrayList<>();
		notifyCustomerInitDtoList.add(notifyCustomerInitDto);

		custId = 100L;

		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");

		notifyCustomerInitDB = new NotifyCustomerInit();
		notifyCustomerInitDB.setEqInit("Test");
		notifyCustomerInitDB.setCustId(100L);
		initialsDB = new HashSet<>();
		initialsDB.add("ABCD");
		initialsDB.add("BBBB");
		notifyCustomerInitDB.setEqInitList(initialsDB);
		notifyCustomerInitListDB = new ArrayList<>();
		notifyCustomerInitListDB.add(notifyCustomerInit);

		customerIndex = new CustomerIndex();
		customerIndex.setCustomerId(100L);
		customerIndex.setCustomerName("TEST");
	}

	@AfterEach
	void tearDown() throws Exception {
		notifyCustomerInitDto = null;
		notifyCustomerInit = null;
		notifyCustomerInitList = null;
		notifyCustomerInitDtoList = null;
	}

	@Test
	void testGetCustomerInitials() {
		when(notifyCustomerInitRepository.findByCustIdOrderByEqInitAsc(Mockito.any()))
				.thenReturn(notifyCustomerInitList);
		List<NotifyCustomerInit> custInitials = notifyCustomerInitService.getCustomerInitials(custId);
		assertEquals(custInitials, notifyCustomerInitList);
	}

	@Test
	void testAddCustomerInitials() {
		when(notifyCustomerInitRepository.existsByEqInit(Mockito.any())).thenReturn(false);
		when(notifyCustomerInitRepository.saveAndFlush(Mockito.any())).thenReturn(notifyCustomerInit);
		NotifyCustomerInit custInitials = notifyCustomerInitService.addNotifyCustomerInit(notifyCustomerInit, header);
		assertEquals(custInitials, notifyCustomerInit);
	}

	@Test
	void testAddCustomerInitialsException() {
		NotifyCustomerInit notifyCustInit = new NotifyCustomerInit();
		notifyCustInit.setEqInit(null);

		NullPointerException exception = assertThrows(NullPointerException.class,
				() -> when(notifyCustomerInitService.addNotifyCustomerInit(notifyCustInit, header)));
		assertEquals("'eqInit' Should not be Blank or Null.", exception.getMessage());
	}

	@Test
	void testDeleteCustomerInitials() {
		when(notifyCustomerInitRepository.existsByEqInitAndCustIdAndUversion(Mockito.any(), Mockito.any(),
				Mockito.any())).thenReturn(true);
		when(notifyCustomerInitRepository.findByEqInitAndCustId(Mockito.any(), Mockito.any()))
				.thenReturn(notifyCustomerInit);
		notifyCustomerInitRepository.deleteByEqInitAndCustId(Mockito.any(), Mockito.any());
		NotifyCustomerInit custInitials = notifyCustomerInitService.deleteNotifyCustomerInit(notifyCustomerInit);
		assertEquals(custInitials, notifyCustomerInit);
	}

	@Test
	void testDeleteCustomerInitialsException() {
		NotifyCustomerInit notifyCustInit = new NotifyCustomerInit();
		notifyCustInit.setEqInit(null);

		NullPointerException exception = assertThrows(NullPointerException.class,
				() -> when(notifyCustomerInitService.deleteNotifyCustomerInit(notifyCustInit)));
		assertEquals("'eqInit' Should not be Blank or Null.", exception.getMessage());
	}

	@Test
	void testUpdateCustomerInitials() {
		when(notifyCustomerInitRepository.existsByEqInit(Mockito.any())).thenReturn(false);
		when(notifyCustomerInitRepository.existsByEqInitAndCustId(Mockito.any(), Mockito.any())).thenReturn(false);
		when(notifyCustomerInitRepository.existsByEqInitAndCustId("ABCD", 100L)).thenReturn(true);
		when(notifyCustomerInitRepository.saveAndFlush(Mockito.any())).thenReturn(notifyCustomerInit);
		when(notifyCustomerInitRepository.findByCustId(Mockito.any())).thenReturn(notifyCustomerInitListDB);
		List<NotifyCustomerInit> custInitials = notifyCustomerInitService.updateNotifyCustomerInit(notifyCustomerInit,
				header);
		assertEquals(custInitials, notifyCustomerInitList);
	}

	@Test
	void testGetCustomerInitialsException() {
		when(notifyCustomerInitRepository.findByCustIdOrderByEqInitAsc(Mockito.any()))
				.thenReturn(Collections.emptyList());
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(notifyCustomerInitService.getCustomerInitials(100L)));
		assertEquals("No Initials Found!", exception.getMessage());

		when(notifyCustomerInitRepository.existsByEqInit(Mockito.any())).thenReturn(true);
		when(notifyCustomerInitRepository.findByEqInit(Mockito.any())).thenReturn(notifyCustomerInit);
		when(customerIndexRepository.findById(Mockito.any())).thenReturn(Optional.of(customerIndex));
		RecordNotAddedException exception2 = assertThrows(RecordNotAddedException.class,
				() -> when(notifyCustomerInitService.addNotifyCustomerInit(notifyCustomerInit, header)));
		assertEquals("EqInIt : Test already exists for Customer : TEST", exception2.getMessage());

		header.put(CommonConstants.EXTENSION_SCHEMA, null);
		NullPointerException exception1 = assertThrows(NullPointerException.class,
				() -> when(notifyCustomerInitService.addNotifyCustomerInit(notifyCustomerInit, header)));
		assertEquals(CommonConstants.EXTENSION_SCHEMA_EXCEPTION_MESSAGE, exception1.getMessage());

		when(notifyCustomerInitRepository.existsByEqInitAndCustIdAndUversion(Mockito.any(), Mockito.any(),
				Mockito.any())).thenReturn(false);
		NoRecordsFoundException exception3 = assertThrows(NoRecordsFoundException.class,
				() -> when(notifyCustomerInitService.deleteNotifyCustomerInit(notifyCustomerInit)));
		assertEquals("No Record Found under EqInIt : Test , CustomerId : 100 and UVersion : null",
				exception3.getMessage());

		initials.add("ABCDBC");
		notifyCustomerInit.setEqInitList(initials);
		NullPointerException exception4 = assertThrows(NullPointerException.class,
				() -> when(notifyCustomerInitService.updateNotifyCustomerInit(notifyCustomerInit, header)));
		assertEquals("EqInIt Length should not be greater than 4 for values : [ABCDBC]", exception4.getMessage());

	}
}
