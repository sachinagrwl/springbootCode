package com.nscorp.obis.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import com.nscorp.obis.domain.CustomerPool;
import com.nscorp.obis.dto.CustomerPoolDTO;

import com.nscorp.obis.dto.PoolListDTO;
import com.nscorp.obis.dto.mapper.CustomerPoolMapper;

import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.repository.CustomerPoolRepository;

import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.CustomerPoolService;

public class CustomerPoolControllerTest {

	@Mock
	CustomerPoolMapper customerPoolMapper;

	@Mock
	CustomerPoolRepository customerPoolRepository;

	@Mock
	CustomerPoolService customerPoolService;

	@InjectMocks
	CustomerPoolController customerPoolController;

	CustomerPool customerPool;
	PoolListDTO poolListDTO;
	List<CustomerPool> customerPoolList;
	List<CustomerPoolDTO> customerPoolDtos;
	Map<String, String> header;

	Long poolId;
	Long customerId;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		customerPool = new CustomerPool();
		customerPoolList = new ArrayList<>();
		poolId = 1001004L;
		customerId = 10024L;
		customerPoolDtos = Arrays.asList(new CustomerPoolDTO());
		poolListDTO = new PoolListDTO();
		poolListDTO.setCustomerId(customerId);
		poolListDTO.setPoolIds(Arrays.asList(poolId));

	}

	@AfterEach
	void tearDown() throws Exception {
		customerPoolDtos = null;
		poolListDTO = null;
		header = null;

	}

	@Test
	void testGetCustomerPool() throws SQLException {
		when(customerPoolService.fetchCustomerPool(Mockito.any())).thenReturn(customerPoolList);
		ResponseEntity<APIResponse<List<CustomerPoolDTO>>> result = customerPoolController.getCustomerPools(customerId);
		assertEquals(result.getStatusCodeValue(), 200);
	}

	@Test
	void testUpdateCustomerPools() throws SQLException {
		when(customerPoolService.updateCustomerPools(Mockito.any(), Mockito.anyMap())).thenReturn(customerPoolDtos);
		ResponseEntity<APIResponse<List<CustomerPoolDTO>>> result = customerPoolController
				.updateCustomerPools(poolListDTO, header);
		assertEquals(result.getStatusCodeValue(), 200);
	}

	@Test
	@DisplayName("NoRecordsFoundException")
	void testNoRecordsFoundException() throws SQLException {
		when(customerPoolService.fetchCustomerPool(Mockito.any())).thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<List<CustomerPoolDTO>>> exception = customerPoolController
				.getCustomerPools(customerId);
		assertEquals(exception.getStatusCodeValue(), 404);
		when(customerPoolService.updateCustomerPools(poolListDTO,header))
				.thenThrow(InvalidDataException.class);
		ResponseEntity<APIResponse<List<CustomerPoolDTO>>> exception1 = customerPoolController
				.updateCustomerPools(poolListDTO, header);
		assertEquals(exception1.getStatusCodeValue(), 400);
	}

	@Test
	@DisplayName("Internal Server Error")
	void testException() throws SQLException {
		when(customerPoolService.fetchCustomerPool(Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<List<CustomerPoolDTO>>> exception = customerPoolController
				.getCustomerPools(customerId);
		assertEquals(exception.getStatusCodeValue(), 500);
		when(customerPoolService.updateCustomerPools(poolListDTO,header))
				.thenThrow(RuntimeException.class);
		ResponseEntity<APIResponse<List<CustomerPoolDTO>>> exception1 = customerPoolController
				.updateCustomerPools(poolListDTO, header);
		assertEquals(exception1.getStatusCodeValue(), 500);
	}
}
