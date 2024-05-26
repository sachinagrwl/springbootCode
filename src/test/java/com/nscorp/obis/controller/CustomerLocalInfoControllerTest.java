package com.nscorp.obis.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.HashMap;
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

import com.nscorp.obis.domain.CustomerLocalInfo;
import com.nscorp.obis.dto.CustomerLocalInfoDTO;
import com.nscorp.obis.dto.mapper.CustomerLocalInfoMapper;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.CustomerLocalInfoServiceImpl;

public class CustomerLocalInfoControllerTest {

	@Mock
	CustomerLocalInfoServiceImpl customerLocalInfoService;

	@Mock
	CustomerLocalInfoMapper customerLocalInfoMapper;

	@InjectMocks
	CustomerLocalInfoController customerLocalInfoController;

	CustomerLocalInfo customerLocalInfo;
	CustomerLocalInfoDTO customerLocalInfoDTO;

	Map<String, String> headers;

	Long customerId;
	Long terminalId;
	String customerName;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		customerLocalInfo = new CustomerLocalInfo();
		customerLocalInfoDTO = new CustomerLocalInfoDTO();
		customerId = 619919702687L;
		terminalId = 99990010120811L;
		customerLocalInfo.setCustomerId(customerId);
		customerLocalInfo.setTerminalId(terminalId);
		headers = new HashMap<>();
		headers.put("userid", "test");
		headers.put("extensionschema", "test");
	}

	@AfterEach
	void tearDown() throws Exception {
		customerLocalInfo = null;
		customerLocalInfoDTO = null;
	}

	@Test
	void testGetCustomerLocalInfo() throws SQLException {
		when(customerLocalInfoService.fetchCustomerLocalInfo(Mockito.any(), Mockito.any()))
				.thenReturn(customerLocalInfo);
		when(customerLocalInfoMapper.customerLocalInfoDTOToCustomerLocalInfo(Mockito.any()))
				.thenReturn(customerLocalInfo);
		when(customerLocalInfoMapper.customerLocalInfoToCustomerLocalInfoDTO(Mockito.any()))
				.thenReturn(customerLocalInfoDTO);
		ResponseEntity<APIResponse<CustomerLocalInfoDTO>> getData = customerLocalInfoController
				.getCustomerLocalInfo(customerId, terminalId);
		assertEquals(getData.getStatusCodeValue(), 200);
	}

	@Test
	void testdeleteCustomerLocalInfo() {
		when(customerLocalInfoService.deleteCustomerLocalInfo(Mockito.any())).thenReturn(customerLocalInfoDTO);
		ResponseEntity<APIResponse<CustomerLocalInfoDTO>> deleteData = customerLocalInfoController
				.deleteCustomerLocalInfo(customerLocalInfoDTO);
		assertEquals(deleteData.getStatusCodeValue(), 200);

	}

	@Test
	void testaddCustomerLocalInfo() {
		when(customerLocalInfoService.addCustomerLocalInfo(Mockito.any(), Mockito.anyMap()))
				.thenReturn(customerLocalInfoDTO);
		ResponseEntity<APIResponse<CustomerLocalInfoDTO>> deleteData = customerLocalInfoController
				.addCustomerLocalInfo(customerLocalInfoDTO, headers);
		assertEquals(deleteData.getStatusCodeValue(), 200);

	}

	@Test
	void testUpdateCustomerLocalInfo() throws SQLException {
		when(customerLocalInfoService.updateCustomerLocalInfo(Mockito.any(), Mockito.anyMap()))
				.thenReturn(customerLocalInfo);
		ResponseEntity<APIResponse<CustomerLocalInfoDTO>> updateData = customerLocalInfoController
				.updateCustomerLocalInfo(customerLocalInfoDTO, headers);
		assertEquals(updateData.getStatusCodeValue(), 200);
	}

	@Test
	@DisplayName("NoRecordsFoundException")
	void testNoRecordsFoundException() throws SQLException {
		when(customerLocalInfoService.fetchCustomerLocalInfo(Mockito.any(), Mockito.any()))
				.thenThrow(NoRecordsFoundException.class);
		when(customerLocalInfoService.deleteCustomerLocalInfo(Mockito.any())).thenThrow(NoRecordsFoundException.class);
		when(customerLocalInfoService.addCustomerLocalInfo(Mockito.any(), Mockito.anyMap()))
				.thenThrow(NoRecordsFoundException.class);
		when(customerLocalInfoService.updateCustomerLocalInfo(Mockito.any(), Mockito.anyMap()))
				.thenThrow(NoRecordsFoundException.class);
		ResponseEntity<APIResponse<CustomerLocalInfoDTO>> getData = customerLocalInfoController
				.getCustomerLocalInfo(customerId, terminalId);
		ResponseEntity<APIResponse<CustomerLocalInfoDTO>> deleteData = customerLocalInfoController
				.deleteCustomerLocalInfo(customerLocalInfoDTO);
		ResponseEntity<APIResponse<CustomerLocalInfoDTO>> addData = customerLocalInfoController
				.addCustomerLocalInfo(customerLocalInfoDTO, headers);
		ResponseEntity<APIResponse<CustomerLocalInfoDTO>> updateData = customerLocalInfoController
				.updateCustomerLocalInfo(customerLocalInfoDTO, headers);
		assertEquals(getData.getStatusCodeValue(), 404);
		assertEquals(deleteData.getStatusCodeValue(), 404);
		assertEquals(addData.getStatusCodeValue(), 404);
		assertEquals(updateData.getStatusCodeValue(), 404);

	}

	@Test
	@DisplayName("Internal Server Error")
	void testException() throws SQLException {
		when(customerLocalInfoService.fetchCustomerLocalInfo(Mockito.any(), Mockito.any()))
				.thenThrow(RuntimeException.class);
		when(customerLocalInfoService.deleteCustomerLocalInfo(Mockito.any())).thenThrow(RuntimeException.class);
		when(customerLocalInfoService.addCustomerLocalInfo(Mockito.any(), Mockito.anyMap()))
				.thenThrow(RuntimeException.class);
		when(customerLocalInfoService.updateCustomerLocalInfo(Mockito.any(), Mockito.anyMap()))
		.thenThrow(RuntimeException.class);
		ResponseEntity<APIResponse<CustomerLocalInfoDTO>> getData = customerLocalInfoController
				.getCustomerLocalInfo(customerId, terminalId);
		ResponseEntity<APIResponse<CustomerLocalInfoDTO>> deleteData = customerLocalInfoController
				.deleteCustomerLocalInfo(customerLocalInfoDTO);
		ResponseEntity<APIResponse<CustomerLocalInfoDTO>> addData = customerLocalInfoController
				.addCustomerLocalInfo(customerLocalInfoDTO, headers);
		ResponseEntity<APIResponse<CustomerLocalInfoDTO>> updateData = customerLocalInfoController
				.updateCustomerLocalInfo(customerLocalInfoDTO, headers);
		assertEquals(getData.getStatusCodeValue(), 500);
		assertEquals(deleteData.getStatusCodeValue(), 500);
		assertEquals(addData.getStatusCodeValue(), 500);
		assertEquals(updateData.getStatusCodeValue(), 500);
	}

	@Test
	void testInavlidException() throws SQLException {

		when(customerLocalInfoService.addCustomerLocalInfo(Mockito.any(), Mockito.anyMap()))
				.thenThrow(InvalidDataException.class);
		ResponseEntity<APIResponse<CustomerLocalInfoDTO>> addData = customerLocalInfoController
				.addCustomerLocalInfo(customerLocalInfoDTO, headers);
		assertEquals(addData.getStatusCodeValue(), 400);
	}

}
