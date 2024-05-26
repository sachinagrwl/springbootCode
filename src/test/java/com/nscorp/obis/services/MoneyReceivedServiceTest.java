package com.nscorp.obis.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.*;

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

import com.nscorp.obis.domain.GenericCodeUpdate;
import com.nscorp.obis.domain.MoneyReceived;
import com.nscorp.obis.dto.MoneyReceivedDTO;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.repository.CustomerRepository;
import com.nscorp.obis.repository.GenericCodeUpdateRepository;
import com.nscorp.obis.repository.MoneyReceivedRepository;
import com.nscorp.obis.repository.TerminalRepository;

class MoneyReceivedServiceTest {
	@Mock
	MoneyReceivedRepository moneyReceivedRepo;
	@Mock
	TerminalRepository terminalRepo;
	@Mock
	CustomerRepository customerRepo;
	@Mock
	GenericCodeUpdateRepository genericCodeUpdateRepo;
	@InjectMocks
	MoneyReceivedServiceImpl moneyReceivedServiceImpl;

	@Mock
	Pageable pageable;

	MoneyReceivedDTO moneyReceivedDTO;
	MoneyReceived moneyReceived;
	Page<MoneyReceived> moneyReceivedList;
	GenericCodeUpdate genericCodeUpdate;
	GenericCodeUpdate genericCodeUpdate1;
	List<GenericCodeUpdate> genericCodeUpdates;
	List<GenericCodeUpdate> genericCodeUpdates1;
	Map<String, String> headers;

	Long termId = 123L;
	Long customerId = 234L;
	String termChkInd = "Y";
	String moneyChkInd = "Y";
	String equipInit = "EMHU";
	List<String> supressTerm;
	List<String> supressFinal;
	Integer equipNbr = 456;
	int pageNumber;
	int pageSize;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
		moneyReceived = new MoneyReceived();
		moneyReceived.setTermId(12L);
		moneyReceived.setCustomerId(12L);
		moneyReceived.setPaidByCustId(12L);
		moneyReceived.setTpPayment("CR");
		moneyReceived.setTpSvcCd("STO");
		moneyReceivedDTO = new MoneyReceivedDTO();
		moneyReceivedDTO.setTermId(12L);
		moneyReceivedDTO.setCustomerId(123L);
		moneyReceivedDTO.setPaidByCustId(234L);
		moneyReceivedDTO.setTpPayment("CR");
		moneyReceivedDTO.setTpSvcCd("STO");
		genericCodeUpdate = new GenericCodeUpdate();
		genericCodeUpdate1 = new GenericCodeUpdate();
		genericCodeUpdates = new ArrayList<>();
		genericCodeUpdates1 = new ArrayList<>();
		genericCodeUpdate.setGenericTableCode("STO");
		genericCodeUpdates.add(genericCodeUpdate);
		genericCodeUpdate1.setGenericTableCode("CR");
		genericCodeUpdates.add(genericCodeUpdate1);
		headers = new HashMap<>();
		headers.put("userid", "test");
		headers.put("extensionschema", "test");

		pageNumber = 1;
		pageSize = 10;
		pageable = PageRequest.of(pageNumber - 1, pageSize);

	}

	@Test
	void testGetMoneyReceived() {
		moneyReceivedList = new PageImpl<>(Arrays.asList(moneyReceived), PageRequest.of(1, 10), 100L);
		when(moneyReceivedRepo.searchAll(termId, customerId,equipInit,equipNbr,supressTerm,supressFinal, pageable)).thenReturn(moneyReceivedList);
		Page<MoneyReceived> moneyrcd = moneyReceivedServiceImpl.getMoneyReceived(termId, customerId,equipInit,equipNbr,termChkInd,moneyChkInd, pageable);
		assertEquals(moneyrcd, moneyReceivedList);

		supressTerm=null;
		supressFinal=null;
		 moneyrcd = moneyReceivedServiceImpl.getMoneyReceived(termId, customerId,equipInit,equipNbr,termChkInd,moneyChkInd, pageable);

	}

	@Test
	void testGetNoRecordsFoundException() {
		when(moneyReceivedRepo.searchAll(Mockito.any(), Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any(), Mockito.any())).thenReturn(new PageImpl<>(Collections.emptyList(), pageable, 0L));
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(moneyReceivedServiceImpl.getMoneyReceived(termId, customerId,equipInit,equipNbr,termChkInd,moneyChkInd, pageable)).thenReturn(moneyReceivedList));
		assertEquals("No Records Found", exception.getMessage());
	}

	@Test
	void testAddUpdatePayment() {
		when(moneyReceivedRepo.existsByMoneyTdrId(anyLong())).thenReturn(true);
		when(terminalRepo.existsByTerminalId(anyLong())).thenReturn(true);
		when(customerRepo.existsByCustomerId(anyLong())).thenReturn(true);
		when(genericCodeUpdateRepo.findByGenericTable(anyString())).thenReturn(genericCodeUpdates);
		when(moneyReceivedRepo.save(any())).thenReturn(moneyReceived);
		MoneyReceived result = moneyReceivedServiceImpl.addPayment(moneyReceived, headers);
		result = moneyReceivedServiceImpl.updatePayment(moneyReceived, headers);
		when(genericCodeUpdateRepo.findByGenericTable(anyString())).thenReturn(null);
		moneyReceived.setPaidByCustId(null);
		result = moneyReceivedServiceImpl.addPayment(moneyReceived, headers);
		result = moneyReceivedServiceImpl.updatePayment(moneyReceived, headers);
	}

	@Test
	void testInValidDataException() {
		genericCodeUpdate.setGenericTableCode("abc");
		genericCodeUpdates1.add(genericCodeUpdate);
		when(moneyReceivedRepo.existsByMoneyTdrId(anyLong())).thenReturn(true);
		when(terminalRepo.existsByTerminalId(anyLong())).thenReturn(false);
		InvalidDataException result = assertThrows(InvalidDataException.class,
				() -> moneyReceivedServiceImpl.addPayment(moneyReceived, headers));
		InvalidDataException result1 = assertThrows(InvalidDataException.class,
				() -> moneyReceivedServiceImpl.updatePayment(moneyReceived, headers));
		when(terminalRepo.existsByTerminalId(anyLong())).thenReturn(true);
		when(customerRepo.existsByCustomerId(123L)).thenReturn(false);
		result = assertThrows(InvalidDataException.class,
				() -> moneyReceivedServiceImpl.addPayment(moneyReceived, headers));
		result1 = assertThrows(InvalidDataException.class,
				() -> moneyReceivedServiceImpl.updatePayment(moneyReceived, headers));
		when(customerRepo.existsByCustomerId(123L)).thenReturn(true);
		when(customerRepo.existsByCustomerId(234L)).thenReturn(false);
		result = assertThrows(InvalidDataException.class,
				() -> moneyReceivedServiceImpl.addPayment(moneyReceived, headers));
		result1 = assertThrows(InvalidDataException.class,
				() -> moneyReceivedServiceImpl.updatePayment(moneyReceived, headers));
		when(terminalRepo.existsByTerminalId(anyLong())).thenReturn(true);
		when(customerRepo.existsByCustomerId(anyLong())).thenReturn(true);
		when(genericCodeUpdateRepo.findByGenericTable("TP_PAY")).thenReturn(genericCodeUpdates1);
		result = assertThrows(InvalidDataException.class,
				() -> moneyReceivedServiceImpl.addPayment(moneyReceived, headers));
		result1 = assertThrows(InvalidDataException.class,
				() -> moneyReceivedServiceImpl.updatePayment(moneyReceived, headers));
		when(genericCodeUpdateRepo.findByGenericTable("TP_PAY")).thenReturn(genericCodeUpdates);
		when(genericCodeUpdateRepo.findByGenericTable("TP_SVC")).thenReturn(genericCodeUpdates1);
		result = assertThrows(InvalidDataException.class,
				() -> moneyReceivedServiceImpl.addPayment(moneyReceived, headers));
		result1 = assertThrows(InvalidDataException.class,
				() -> moneyReceivedServiceImpl.updatePayment(moneyReceived, headers));
		;
	}

	@Test
	public void testRecordNotAddedException() {
		when(moneyReceivedRepo.existsByMoneyTdrId(anyLong())).thenReturn(true);
		when(terminalRepo.existsByTerminalId(anyLong())).thenReturn(true);
		when(customerRepo.existsByCustomerId(anyLong())).thenReturn(true);
		when(genericCodeUpdateRepo.findByGenericTable(anyString())).thenReturn(genericCodeUpdates);
		when(moneyReceivedRepo.save(any())).thenReturn(null);
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> moneyReceivedServiceImpl.addPayment(moneyReceived, headers));
		RecordNotAddedException exception1 = assertThrows(RecordNotAddedException.class,
				() -> moneyReceivedServiceImpl.updatePayment(moneyReceived, headers));
	}

	@Test
	public void testRecordNotFoundException() {
		when(moneyReceivedRepo.existsByMoneyTdrId(anyLong())).thenReturn(false);
		NoRecordsFoundException exception1 = assertThrows(NoRecordsFoundException.class,
				() -> moneyReceivedServiceImpl.updatePayment(moneyReceived, headers));

		moneyReceived.setMoneyTdrId(null);
		exception1 = assertThrows(NoRecordsFoundException.class,
				() -> moneyReceivedServiceImpl.updatePayment(moneyReceived, headers));

	}
}
