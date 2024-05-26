package com.nscorp.obis.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.nscorp.obis.domain.CustomerTerm;
import com.nscorp.obis.domain.CustomerTerminal;
import com.nscorp.obis.dto.CustomerTermDTO;
import com.nscorp.obis.dto.CustomerTerminalDTO;
import com.nscorp.obis.dto.CustomerTerminalListDTO;
import com.nscorp.obis.dto.mapper.CustomerTermMapper;
import com.nscorp.obis.dto.mapper.CustomerTerminalMapper;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.CustomerTermService;

public class CustomerTermControllerTest {

	@Mock
	CustomerTermMapper customerTermMapper;

	@Mock
	CustomerTerminalMapper customerTerminalMapper;


	@Mock
	CustomerTermService customerTermService;

	@InjectMocks
	CustomerTermController customerTermController;

	CustomerTerm customerTerm;
	CustomerTerminal customerTerminal;
	CustomerTerminalDTO customerTerminalDTO;
	CustomerTerminalListDTO customerTerminalListDTO;
	CustomerTermDTO customerTermDTO;
	List<CustomerTerm> customerTerms;
	List<CustomerTermDTO> customerTermDTOs;
	List<CustomerTerminal> customerTerminals;
	List<APIResponse<CustomerTerm>> responseObjectList = new ArrayList<APIResponse<CustomerTerm>>();
	Map<String, String> header;

	Long terminalId;
	Long customerId;
	String customerName;
	List<Long> terminalIds;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		customerTerm = new CustomerTerm();
		customerTerminal = new CustomerTerminal();
		customerTermDTO = new CustomerTermDTO();
		customerTerminalDTO = new CustomerTerminalDTO();
		terminalIds = new ArrayList<Long>();
		customerTerminalListDTO = new CustomerTerminalListDTO();
		customerTerms = new ArrayList<CustomerTerm>();
		customerTermDTOs = new ArrayList<CustomerTermDTO>();
		customerTerminals = new ArrayList<CustomerTerminal>();
		terminalId = 1002L;
		customerId = 10024L;
		terminalIds = Arrays.asList(10022043461004L, 10022043461004L);
		customerName = "CASCO SERVICES INC";
        customerTerminal.setTerminalId(terminalId);
        customerTerminal.setCustomerId(customerId);
        customerTerminals.add(customerTerminal);
		customerTerminalListDTO.setCustomerId(customerId);
		customerTerminalListDTO.setTerminalId(terminalIds);

	}

	@AfterEach
	void tearDown() throws Exception {
		customerTerm = null;
		customerTermDTO = null;
		customerTerminalDTO = null;
		customerTerms = null;
		customerTermDTOs = null;
	}

	@Test
	void testGetCustomerTerm() throws SQLException {
		when(customerTermService.fetchCustomerNotifyProfiles(Mockito.any(), Mockito.any())).thenReturn(customerTerms);
		ResponseEntity<APIResponse<List<CustomerTermDTO>>> result = customerTermController.getCustomerTerm(terminalId,
				customerName);
		assertEquals(result.getStatusCodeValue(), 200);
	}

	@Test
	void testGetCustomerTerminal() throws SQLException {
		when(customerTermService.fetchCustomerTerminal(Mockito.any())).thenReturn(customerTerminals);
		ResponseEntity<APIResponse<List<CustomerTerminalDTO>>> result = customerTermController
				.getCustomerTerminals(customerId);
		assertEquals(result.getStatusCodeValue(), 200);
	}

	@Test
	@DisplayName("NoRecordsFoundException")
	void testNoRecordsFoundException() throws SQLException {
//		when(customerTermMapper.CustomerTermToCustomerTermDTO(Mockito.any())).thenReturn(customerTermDTO);
//		when(customerTermMapper.CustomerTermDTOToCustomerTerm(Mockito.any())).thenReturn(customerTerm);
		when(customerTermService.fetchCustomerNotifyProfiles(Mockito.any(), Mockito.any()))
				.thenThrow(new NoRecordsFoundException("No records found."));
		ResponseEntity<APIResponse<List<CustomerTermDTO>>> result = customerTermController.getCustomerTerm(terminalId,
				customerName);
		assertEquals(result.getStatusCodeValue(), 404);
		when(customerTermService.fetchCustomerTerminal(Mockito.any())).thenThrow(new NoRecordsFoundException("No records found."));
		ResponseEntity<APIResponse<List<CustomerTerminalDTO>>> exception = customerTermController
				.getCustomerTerminals(customerId);
		assertEquals(exception.getStatusCodeValue(), 404);
	}

	@Test
	@DisplayName("Internal Server Error")
	void testException() throws SQLException {
		when(customerTermMapper.CustomerTermToCustomerTermDTO(Mockito.any())).thenReturn(customerTermDTO);
		when(customerTermMapper.CustomerTermDTOToCustomerTerm(Mockito.any())).thenReturn(customerTerm);
		when(customerTermService.fetchCustomerNotifyProfiles(Mockito.any(), Mockito.any()))
				.thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<List<CustomerTermDTO>>> result = customerTermController.getCustomerTerm(terminalId,
				customerName);
		assertEquals(result.getStatusCodeValue(), 500);
		when(customerTermService.fetchCustomerTerminal(Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<List<CustomerTerminalDTO>>> exception = customerTermController
				.getCustomerTerminals(customerId);
		assertEquals(exception.getStatusCodeValue(), 500);
	}

	@Test
	void testupdateCustomerTerminals() throws SQLException {

		when(customerTerminalMapper.CustomerTerminalDTOToCustomerTerminal(Mockito.any())).thenReturn(customerTerminal);
		when(customerTermService.updateCustomerTerminals(Mockito.any(), Mockito.any())).thenReturn(customerTerminal);
		when(customerTerminalMapper.CustomerTerminalToCustomerTerminalDTO(Mockito.any()))
				.thenReturn(customerTerminalDTO);
		ResponseEntity<List<APIResponse<CustomerTerminalDTO>>> updateData = customerTermController
				.updateCustomerTerminals(customerTerminalListDTO, header);
		assertEquals(updateData.getStatusCodeValue(), 200);

	}

	@Test
	void testExceptions() throws SQLException {

		when(customerTermService.updateCustomerTerminals(Mockito.any(), Mockito.any()))
				.thenThrow(new NoRecordsFoundException());
		ResponseEntity<List<APIResponse<CustomerTerminalDTO>>> updatedData = customerTermController
				.updateCustomerTerminals(customerTerminalListDTO, header);
		assertEquals(updatedData.getStatusCodeValue(), 400);


	}

//	@Test
	@DisplayName("InvalidDataException")
	void testInvalidDataException() throws SQLException {

		customerTerminalListDTO.setTerminalId(null);
		InvalidDataException addexception = assertThrows(InvalidDataException.class,
				() -> when(customerTermController.updateCustomerTerminals(customerTerminalListDTO, header)));
		assertEquals("Terminal Id cant be empty. Enter Valid TerminalId", addexception.getMessage());

	}

}
