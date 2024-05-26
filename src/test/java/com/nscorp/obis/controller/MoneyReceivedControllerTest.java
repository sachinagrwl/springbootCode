package com.nscorp.obis.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nscorp.obis.domain.SortFilter;
import com.nscorp.obis.dto.MoneyReceivedResponseDTO;
import com.nscorp.obis.response.data.PaginationWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;

import com.nscorp.obis.domain.GenericCodeUpdate;
import com.nscorp.obis.domain.MoneyReceived;
import com.nscorp.obis.dto.MoneyReceivedDTO;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.MoneyReceivedService;

class MoneyReceivedControllerTest {
	@Mock
	MoneyReceivedService moneyReceivedService;
	@InjectMocks
	MoneyReceivedController moneyReceivedController;
	
	MoneyReceivedDTO moneyReceivedDTO;
	MoneyReceived moneyReceived;

	GenericCodeUpdate genericCodeUpdate;
	GenericCodeUpdate genericCodeUpdate1;
	List<GenericCodeUpdate> genericCodeUpdates;
	List<GenericCodeUpdate> genericCodeUpdates1;
	Map<String, String> headers;
	String[] sort ;
	Long termId =123L;
	Long customerId = 234L;
	String termChkInd = "Y";
	String moneyChkInd = "Y";
	String equipInit = "EMHU";
	Integer equipNbr = 456;
	int pageNumber;
	int pageSize;
	PaginationWrapper paginationWrapper;
	Pageable pageable;
	Page<MoneyReceived> moneyReceivedPage;
	List<MoneyReceived> moneyReceivedList;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		moneyReceivedList = new ArrayList<>();
		moneyReceived = new MoneyReceived();
		moneyReceivedDTO = new MoneyReceivedDTO();
		sort = new String[]{"equipInit"};
		moneyReceived.setTermId(12L);
		moneyReceived.setCustomerId(12L);
		moneyReceived.setPaidByCustId(12L);
		moneyReceived.setTpPayment("CR");
		moneyReceived.setTpSvcCd("STO");

		moneyReceivedList.add(moneyReceived);
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
		moneyReceivedPage = new PageImpl<>(moneyReceivedList, PageRequest.of(0, 10), 100L);
		pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.by(SortFilter.sortOrder(sort)));
		paginationWrapper = new PaginationWrapper(moneyReceivedList, moneyReceivedPage.getNumber() + 1,
				moneyReceivedPage.getTotalPages(), moneyReceivedPage.getTotalElements());
		

	}

	@Test
	void testGetmoneyReceived() {
	//	moneyReceivedList = new PageImpl<>(Arrays.asList(moneyReceived),PageRequest.of(1, 10), 100L);
		when(moneyReceivedService.getMoneyReceived(termId,
				customerId,equipInit,equipNbr,termChkInd,moneyChkInd,pageable)).thenReturn(moneyReceivedPage);
		ResponseEntity<APIResponse<MoneyReceivedResponseDTO>> result = moneyReceivedController.getAllTables(termId,
				customerId,equipInit,equipNbr,termChkInd,moneyChkInd, pageNumber, pageSize,sort);
		assertEquals(result.getStatusCodeValue(), 200);
	}

	@Test
	void testNoRecordsFoundException() {

		when(moneyReceivedService.getMoneyReceived(termId,
				customerId,equipInit,equipNbr,termChkInd,moneyChkInd,pageable))
				.thenThrow(new NoRecordsFoundException());
		when(moneyReceivedService.updatePayment(Mockito.any(), Mockito.any()))
				.thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<MoneyReceivedResponseDTO>> result = moneyReceivedController.getAllTables(termId,
				customerId,equipInit,equipNbr,termChkInd,moneyChkInd, pageNumber, pageSize,sort);
		assertEquals(result.getStatusCodeValue(), 200);
		ResponseEntity<APIResponse<MoneyReceivedDTO>> result1 = moneyReceivedController.updatePayment(moneyReceivedDTO,
				headers);
		assertEquals(result1.getStatusCodeValue(), 404);

	}
	
	@Test
	void testGetmoneyReceivedException() {
		when(moneyReceivedService.getMoneyReceived(Mockito.any(), Mockito.any(),Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),Mockito.any()))
		.thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<MoneyReceivedResponseDTO>> result = moneyReceivedController.getAllTables(termId,
				customerId,equipInit,equipNbr,termChkInd,moneyChkInd, pageNumber, pageSize,sort);
		assertEquals(result.getStatusCodeValue(), 500);
	}

	@Test
	void testAddUpdatePayment() {
		when(moneyReceivedService.addPayment(moneyReceived, headers)).thenReturn(moneyReceived);
		when(moneyReceivedService.updatePayment(moneyReceived, headers)).thenReturn(moneyReceived);
		ResponseEntity<APIResponse<MoneyReceivedDTO>> result = moneyReceivedController.addPayment(moneyReceivedDTO,
				headers);
		assertEquals(result.getStatusCodeValue(), 200);
		result = moneyReceivedController.updatePayment(moneyReceivedDTO,
				headers);
		assertEquals(result.getStatusCodeValue(), 200);
	}

	@Test
	void testAddRecordAlreadyExistsException() {
		when(moneyReceivedService.addPayment(Mockito.any(), Mockito.any()))
				.thenThrow(new RecordAlreadyExistsException());
		ResponseEntity<APIResponse<MoneyReceivedDTO>> result = moneyReceivedController.addPayment(moneyReceivedDTO,
				headers);
		assertEquals(result.getStatusCodeValue(), 406);
	}

	@Test
	void testAddUpdateInvalidDataException() {
		when(moneyReceivedService.addPayment(Mockito.any(), Mockito.any())).thenThrow(new InvalidDataException());
		when(moneyReceivedService.updatePayment(Mockito.any(), Mockito.any())).thenThrow(new InvalidDataException());
		ResponseEntity<APIResponse<MoneyReceivedDTO>> result = moneyReceivedController.addPayment(moneyReceivedDTO,
				headers);
		assertEquals(result.getStatusCodeValue(), 400);
		result = moneyReceivedController.updatePayment(moneyReceivedDTO,
				headers);
		assertEquals(result.getStatusCodeValue(), 400);
	}

	@Test
	void testAddUpdateNullPointerException() {
		when(moneyReceivedService.addPayment(Mockito.any(), Mockito.any())).thenThrow(new NullPointerException());
		ResponseEntity<APIResponse<MoneyReceivedDTO>> result = moneyReceivedController.addPayment(moneyReceivedDTO,
				headers);
		assertEquals(result.getStatusCodeValue(), 400);
		when(moneyReceivedService.updatePayment(Mockito.any(), Mockito.any())).thenThrow(new NullPointerException());
		result = moneyReceivedController.updatePayment(moneyReceivedDTO,
				headers);
		assertEquals(result.getStatusCodeValue(), 400);
	}

	@Test
	void testAddUpdateRuntimeException() {
		when(moneyReceivedService.addPayment(Mockito.any(), Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<MoneyReceivedDTO>> result = moneyReceivedController.addPayment(moneyReceivedDTO,
				headers);
		assertEquals(result.getStatusCodeValue(), 500);
		when(moneyReceivedService.updatePayment(Mockito.any(), Mockito.any())).thenThrow(new RuntimeException());
		result = moneyReceivedController.updatePayment(moneyReceivedDTO,
				headers);
		assertEquals(result.getStatusCodeValue(), 500);
	}
}
