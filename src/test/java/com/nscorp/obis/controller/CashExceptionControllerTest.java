package com.nscorp.obis.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nscorp.obis.exception.InvalidDataException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.nscorp.obis.dto.CashExceptionDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.CashExceptionService;

public class CashExceptionControllerTest {

	@Mock
	CashExceptionService cashExceptionService;

	@InjectMocks
	CashExceptionController cashExceptionController;

	CashExceptionDTO cashExceptionDTO;

	List<CashExceptionDTO> cashExceptionDtos;

	String customerName = "Test Customer";
	String customerPrimarySix = "123456";

	Map<String, String> header;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		cashExceptionDTO = new CashExceptionDTO();
		cashExceptionDtos = new ArrayList<CashExceptionDTO>();
		cashExceptionDTO.setCashExceptionId(11450798953015L);
		cashExceptionDtos.add(cashExceptionDTO);
		header=new HashMap<>();
		header.put("userid", "test");
		header.put("extensionschema", "test");
	}

	@AfterEach
	void tearDown() {
		cashExceptionDTO = null;
		cashExceptionDtos = null;
	}

	@Test
	void testfetchCashExceptions() {
		when(cashExceptionService.getCashException(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(cashExceptionDtos);
		ResponseEntity<APIResponse<List<CashExceptionDTO>>> response = cashExceptionController
				.fetchCashExceptions(customerName, customerPrimarySix);
		assertEquals(200, response.getStatusCodeValue());
	}
	
	@Test
	void testNoRecordsException() {
		when(cashExceptionService.getCashException(Mockito.anyString(), Mockito.anyString()))
				.thenThrow(NoRecordsFoundException.class);
		ResponseEntity<APIResponse<List<CashExceptionDTO>>> response = cashExceptionController
				.fetchCashExceptions(customerName, customerPrimarySix);
		assertEquals(404, response.getStatusCodeValue());
	}
	
	@Test
	void testException() {
		when(cashExceptionService.getCashException(Mockito.anyString(), Mockito.anyString()))
				.thenThrow(RuntimeException.class);
		ResponseEntity<APIResponse<List<CashExceptionDTO>>> response = cashExceptionController
				.fetchCashExceptions(customerName, customerPrimarySix);
		assertEquals(500, response.getStatusCodeValue());
	}

	@Test
	void testAddCashException() throws SQLException {

		cashExceptionDTO.setEquipType("T");
		cashExceptionDTO.setCustomerName("Test Cust");
		cashExceptionDTO.setCustomerPrimarySix("123456");
		cashExceptionDTO.setEffectiveDate(new Date(11-24-2021).toLocalDate());


		when(cashExceptionService.addCashException(cashExceptionDTO, header))
				.thenReturn(cashExceptionDTO);
		ResponseEntity<APIResponse<CashExceptionDTO>> response = cashExceptionController
				.addCashExceptions(cashExceptionDTO, header);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);

	}
	
	@Test
	void testUpdateCashException() throws SQLException {

		cashExceptionDTO.setEquipType("T");
		cashExceptionDTO.setCustomerName("Test Cust");
		cashExceptionDTO.setCustomerPrimarySix("123456");
		cashExceptionDTO.setEffectiveDate(new Date(11-24-2021).toLocalDate());


		when(cashExceptionService.updateCashException(cashExceptionDTO, header))
				.thenReturn(cashExceptionDTO);
		ResponseEntity<APIResponse<CashExceptionDTO>> response = cashExceptionController
				.updateCashExceptions(cashExceptionDTO, header);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);

	}

	@Test
	void testNoRecordFoundExceptionForAdd() throws SQLException{
		cashExceptionDTO.setTermId(123L);
		when(cashExceptionService.addCashException(cashExceptionDTO, header))
				.thenThrow(NoRecordsFoundException.class);
		ResponseEntity<APIResponse<CashExceptionDTO>> response = cashExceptionController
				.addCashExceptions(cashExceptionDTO, header);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);

	}

	@Test
	void testInvalidDataExceptionForAdd() throws SQLException{
		cashExceptionDTO.setEquipType("N");
		when(cashExceptionService.addCashException(cashExceptionDTO, header))
				.thenThrow(InvalidDataException.class);
		ResponseEntity<APIResponse<CashExceptionDTO>> response= cashExceptionController
				.addCashExceptions(cashExceptionDTO, header);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
	}

	@Test
	void testInternalServerErrorForAdd() throws SQLException {
		when(cashExceptionService.addCashException(cashExceptionDTO, header))
				.thenThrow(RuntimeException.class);
		ResponseEntity<APIResponse<CashExceptionDTO>> response = cashExceptionController
				.addCashExceptions(cashExceptionDTO, header);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
	}



}
