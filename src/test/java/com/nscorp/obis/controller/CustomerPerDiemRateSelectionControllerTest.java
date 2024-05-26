package com.nscorp.obis.controller;

import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.nscorp.obis.domain.CustomerPerDiemRateSelection;
import com.nscorp.obis.dto.CustomerPerDiemRateSelectionDTO;
import com.nscorp.obis.dto.mapper.CustomerPerDiemRateSelectionMapper;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.CustomerPerDiemRateSelectionServiceImpl;

public class CustomerPerDiemRateSelectionControllerTest {

	@InjectMocks

	CustomerPerDiemRateSelectionController customerPerDiemRateSelectionController;

	@Mock
	CustomerPerDiemRateSelectionServiceImpl customerPerDiemRateSelectionService;

	@Mock
	CustomerPerDiemRateSelectionMapper customerPerDiemRateSelectionMapper;

	CustomerPerDiemRateSelection customerPerDiemRateSelection;
	CustomerPerDiemRateSelectionDTO customerPerDiemRateSelectionDTO;
	List<CustomerPerDiemRateSelection> customerPerDiemRateSelectionList;
	List<CustomerPerDiemRateSelectionDTO> customerPerDiemRateSelectionDTOList;
	String custPrimSix;

	Map<String, String> headers;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		customerPerDiemRateSelectionList = new ArrayList<>();
		customerPerDiemRateSelection = new CustomerPerDiemRateSelection();
		customerPerDiemRateSelection.setPerDiemId(1234L);
		customerPerDiemRateSelection.setCustomerName("Test Cust");
		customerPerDiemRateSelection.setCustPrimSix("123456");
		customerPerDiemRateSelectionDTO = new CustomerPerDiemRateSelectionDTO();
		custPrimSix = "109957";
		customerPerDiemRateSelectionDTO.setPerDiemId(1234L);
		customerPerDiemRateSelectionDTO.setCustomerName("Test Cust");
		customerPerDiemRateSelectionDTO.setCustPrimSix("123456");
		customerPerDiemRateSelectionList.add(customerPerDiemRateSelection);
		headers = new HashMap<>();
		headers.put("userid", "test");
		headers.put("extensionschema", "test");

	}

	@AfterEach
	void tearDown() throws Exception {
		customerPerDiemRateSelection = null;
		customerPerDiemRateSelectionDTO = null;
	}

	@Test
	void testUpdatePerDiemRate() throws SQLException {
		when(customerPerDiemRateSelectionService.updateCustomerPerDiemRate(customerPerDiemRateSelectionDTO, headers))
				.thenReturn(customerPerDiemRateSelectionDTO);
		ResponseEntity<APIResponse<CustomerPerDiemRateSelectionDTO>> response = customerPerDiemRateSelectionController
				.updatePerDiemRate(customerPerDiemRateSelectionDTO, headers);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		when(customerPerDiemRateSelectionService.updateCustomerPerDiemRate(customerPerDiemRateSelectionDTO, headers))
				.thenThrow(InvalidDataException.class);
		ResponseEntity<APIResponse<CustomerPerDiemRateSelectionDTO>> response2 = customerPerDiemRateSelectionController
				.updatePerDiemRate(customerPerDiemRateSelectionDTO, headers);
		assertEquals(response2.getStatusCode(), HttpStatus.BAD_REQUEST);
	}

	@Test
	void testAddPerDiemRate() throws SQLException {


		customerPerDiemRateSelectionDTO.setEquipTp("G");
		customerPerDiemRateSelectionDTO.setIngateLoadEmptyStatus("E");
		customerPerDiemRateSelectionDTO.setOutgateLoadEmptyStatus("L");
		customerPerDiemRateSelectionDTO.setCustomerName("Test Cust");
		customerPerDiemRateSelectionDTO.setCustPrimSix("123456");

		when(customerPerDiemRateSelectionService.addCustomerPerDiemRate(customerPerDiemRateSelectionDTO, headers))
				.thenReturn(customerPerDiemRateSelectionDTO);
		ResponseEntity<APIResponse<CustomerPerDiemRateSelectionDTO>> response = customerPerDiemRateSelectionController
				.addPerDiemRate(customerPerDiemRateSelectionDTO, headers);
		assertEquals(response.getStatusCode(), HttpStatus.OK);


	}

	@Test
	void testNoRecordFoundExceptionForAdd() throws SQLException{
		customerPerDiemRateSelectionDTO.setTerminalId(123L);
		when(customerPerDiemRateSelectionService.addCustomerPerDiemRate(customerPerDiemRateSelectionDTO, headers))
				.thenThrow(NoRecordsFoundException.class);
		ResponseEntity<APIResponse<CustomerPerDiemRateSelectionDTO>> response3 = customerPerDiemRateSelectionController
				.addPerDiemRate(customerPerDiemRateSelectionDTO, headers);
		assertEquals(response3.getStatusCode(), HttpStatus.NOT_FOUND);
	}


	@Test
	void testInvalidDataExceptionForAdd() throws SQLException{
		customerPerDiemRateSelectionDTO.setEquipTp("N");
		when(customerPerDiemRateSelectionService.addCustomerPerDiemRate(customerPerDiemRateSelectionDTO, headers))
				.thenThrow(InvalidDataException.class);
		ResponseEntity<APIResponse<CustomerPerDiemRateSelectionDTO>> response2 = customerPerDiemRateSelectionController
				.addPerDiemRate(customerPerDiemRateSelectionDTO, headers);
		assertEquals(response2.getStatusCode(), HttpStatus.BAD_REQUEST);
	}

	@Test
	void testInternalServerError() throws SQLException {
		when(customerPerDiemRateSelectionService.updateCustomerPerDiemRate(customerPerDiemRateSelectionDTO, headers))
				.thenThrow(RuntimeException.class);
		ResponseEntity<APIResponse<CustomerPerDiemRateSelectionDTO>> response2 = customerPerDiemRateSelectionController
				.updatePerDiemRate(customerPerDiemRateSelectionDTO, headers);
		assertEquals(response2.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Test
	void testInternalServerErrorForAdd() throws SQLException {
		when(customerPerDiemRateSelectionService.addCustomerPerDiemRate(customerPerDiemRateSelectionDTO, headers))
				.thenThrow(RuntimeException.class);
		ResponseEntity<APIResponse<CustomerPerDiemRateSelectionDTO>> response2 = customerPerDiemRateSelectionController
				.addPerDiemRate(customerPerDiemRateSelectionDTO, headers);
		assertEquals(response2.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Test
	void testGetCustomerPerDiemRate() throws SQLException {
		when(customerPerDiemRateSelectionService.fetchCustomerPerDiemRate(Mockito.any()))
				.thenReturn(customerPerDiemRateSelectionList);
		ResponseEntity<APIResponse<List<CustomerPerDiemRateSelectionDTO>>> customerProfiles = customerPerDiemRateSelectionController
				.getCustomerPerDiemRate(custPrimSix);
		assertEquals(customerProfiles.getStatusCodeValue(), 200);
	}

	@Test
	void testNoRecordsFoundException() throws SQLException {
		when(customerPerDiemRateSelectionService.fetchCustomerPerDiemRate(Mockito.any()))
				.thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<List<CustomerPerDiemRateSelectionDTO>>> response = customerPerDiemRateSelectionController
				.getCustomerPerDiemRate(custPrimSix);
		assertEquals(response.getStatusCodeValue(), 404);
	}



	@Test
	void testException() throws SQLException {
		when(customerPerDiemRateSelectionService.fetchCustomerPerDiemRate(Mockito.any()))
				.thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<List<CustomerPerDiemRateSelectionDTO>>> response = customerPerDiemRateSelectionController
				.getCustomerPerDiemRate(custPrimSix);
		assertEquals(response.getStatusCodeValue(), 500);

	}

	@Test
	void testSizeExceedException() throws SQLException {
		when(customerPerDiemRateSelectionService.fetchCustomerPerDiemRate(Mockito.any())).thenReturn(customerPerDiemRateSelectionList);
		ResponseEntity<APIResponse<List<CustomerPerDiemRateSelectionDTO>>> response = customerPerDiemRateSelectionController
			.getCustomerPerDiemRate(custPrimSix);
		assertEquals(response.getStatusCodeValue(), 411);
	}


}
