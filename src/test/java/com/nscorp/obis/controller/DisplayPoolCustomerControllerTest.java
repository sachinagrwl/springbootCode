package com.nscorp.obis.controller;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.nscorp.obis.domain.Customer;
import com.nscorp.obis.domain.Pool;
import com.nscorp.obis.dto.PoolDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.DisplayPoolCustomerService;

class DisplayPoolCustomerControllerTest {

	@InjectMocks
	DisplayPoolCustomerController displayPoolCustomerController;

	@Mock
	DisplayPoolCustomerService displayPoolCustomerService;

	PoolDTO poolDTO;
	
	List<PoolDTO> poolDtoList;

	Pool pool;

	Customer customer;

	Set<Customer> customerSet;

	Map<String, String> headers;

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		pool = new Pool();
		poolDTO = new PoolDTO();
		customerSet = new HashSet<>();
		customer = new Customer();
		poolDtoList = new ArrayList<>();

		poolDTO.setPoolId(1000L);
		poolDTO.setCustomers(customerSet);

		customer.setCustomerId(1000L);
		customerSet.add(customer);
		pool.setCustomers(customerSet);
		pool.setPoolId(1000000L);
		
		poolDtoList.add(poolDTO);

		headers = new HashMap<>();
		headers.put("userid", "test");
		headers.put("extensionschema", "test");
	}

	@AfterEach
	void tearDown() throws Exception {
		pool = null;
		poolDTO = null;
		customerSet = null;
		headers = null;
		customer = null;
	}

	@Test
	void testAddpoolCustomer() {
		when(displayPoolCustomerService.addPoolCustomer(Mockito.any(), Mockito.any())).thenReturn(pool);
		ResponseEntity<APIResponse<PoolDTO>> addPoolCustomer = displayPoolCustomerController.addPoolCustomer(poolDTO,
				headers);
		assertEquals(HttpStatus.OK, addPoolCustomer.getStatusCode());
	}

	@Test
	void TestNoRecordFoundException() {
		when(displayPoolCustomerService.addPoolCustomer(Mockito.any(), Mockito.any()))
				.thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<PoolDTO>> addPoolCustomer = displayPoolCustomerController.addPoolCustomer(poolDTO,
				headers);
		assertEquals(HttpStatus.NOT_FOUND, addPoolCustomer.getStatusCode());
	}
	
	@Test
    void deletePool() {
        
       when(displayPoolCustomerService.deletePool(Mockito.any())).thenReturn(pool);
        ResponseEntity<List<APIResponse<PoolDTO>>> deleteList = displayPoolCustomerController.deletePool(poolDtoList);
        assertEquals(deleteList.getStatusCodeValue(),200);
    }

    @SuppressWarnings("unchecked")
	@Test
    void testDeletePoolEmptyDTOList(){
        ResponseEntity<List<APIResponse<PoolDTO>>> responseEntity = displayPoolCustomerController.deletePool(Collections.EMPTY_LIST);
        assertEquals(responseEntity.getStatusCodeValue(),500);
    }

	@Test
	void TestRecordNotAddedException() {
		when(displayPoolCustomerService.addPoolCustomer(Mockito.any(), Mockito.any()))
				.thenThrow(new RecordNotAddedException());
		ResponseEntity<APIResponse<PoolDTO>> addPoolCustomer = displayPoolCustomerController.addPoolCustomer(poolDTO,
				headers);
		assertEquals(HttpStatus.NOT_ACCEPTABLE, addPoolCustomer.getStatusCode());
	}

	@Test
	void TestNullPointerException() {
		when(displayPoolCustomerService.addPoolCustomer(Mockito.any(), Mockito.any()))
				.thenThrow(new NullPointerException());
		ResponseEntity<APIResponse<PoolDTO>> addPoolCustomer = displayPoolCustomerController.addPoolCustomer(poolDTO,
				headers);
		assertEquals(HttpStatus.BAD_REQUEST, addPoolCustomer.getStatusCode());
	}

	@Test
	void TestException() {
		when(displayPoolCustomerService.addPoolCustomer(Mockito.any(), Mockito.any()))
				.thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<PoolDTO>> addPoolCustomer = displayPoolCustomerController.addPoolCustomer(poolDTO,
				headers);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, addPoolCustomer.getStatusCode());
	}

}
