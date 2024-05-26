package com.nscorp.obis.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.nscorp.obis.domain.CustomerIndex;
import com.nscorp.obis.dto.CustomerIndexDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.CustomerIndexService;

public class CustomerIndexControllerTest {

	@Mock
	CustomerIndexService customerIndexService;

	@InjectMocks
	CustomerIndexController controller;

	List<CustomerIndexDTO> customerIndexDTOs;
	CustomerIndex custIndex;

	String customerName = "UPS FREIGHT";
	String customerNumber = "4245005430299";
	String city = "city";
	String state = "state";
	String uniqueGroup = "n";
	String latest = "n";
	String[] sort = { "customerName", "asc" };
	Long corporateId = 1234L;
	Long notifyQueueId = 12345L;
	String fetchExpired="Y";

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		customerIndexDTOs = new ArrayList<>();
		customerIndexDTOs.add(new CustomerIndexDTO());
		custIndex = new CustomerIndex();
	}

	@AfterEach
	void tearDown() {
		customerIndexDTOs = null;
	}

	@Test
	void testGetCustomers() {
		when(customerIndexService.getCustomers(customerName, customerNumber, city, state, uniqueGroup, corporateId,
				latest, sort,fetchExpired)).thenReturn(customerIndexDTOs);
		ResponseEntity<APIResponse<List<CustomerIndexDTO>>> response = controller.getCustomers(customerName,
				customerNumber, city, state, corporateId, uniqueGroup, latest, sort,fetchExpired);
		assertEquals(response.getStatusCodeValue(), 200);
	}

	@Test
	void testGetCustomersException() {
		when(customerIndexService.getCustomers(customerName, customerNumber, city, state, uniqueGroup, corporateId,
				latest, sort,fetchExpired)).thenThrow(NoRecordsFoundException.class);
		ResponseEntity<APIResponse<List<CustomerIndexDTO>>> response = controller.getCustomers(customerName,
				customerNumber, city, state, corporateId, uniqueGroup, latest, sort,fetchExpired);
		assertEquals(response.getStatusCodeValue(), 404);
		
		when(customerIndexService.getCustIndex(notifyQueueId)).thenThrow(NoRecordsFoundException.class);
		ResponseEntity<APIResponse<CustomerIndexDTO>> exception = controller.getCustomerIndex(notifyQueueId);
		assertEquals(exception.getStatusCodeValue(), 404);
	}

	@Test
	void testGetCustomersRunTimeException() {
		when(customerIndexService.getCustomers(customerName, customerNumber, city, state, uniqueGroup, corporateId,
				latest, sort,fetchExpired)).thenThrow(RuntimeException.class);
		ResponseEntity<APIResponse<List<CustomerIndexDTO>>> response2 = controller.getCustomers(customerName,
				customerNumber, city, state, corporateId, uniqueGroup, latest, sort,fetchExpired);
		assertEquals(response2.getStatusCodeValue(), 500);
		
		when(customerIndexService.getCustIndex(notifyQueueId)).thenThrow(RuntimeException.class);
		ResponseEntity<APIResponse<CustomerIndexDTO>> exception = controller.getCustomerIndex(notifyQueueId);
		assertEquals(exception.getStatusCodeValue(), 500);
	}

	@Test
	void testGetCustomersParamException() {
		customerName = null;
		customerNumber = null;
		corporateId = null;
		when(customerIndexService.getCustomers(customerName, customerNumber, city, state, uniqueGroup, corporateId,
				latest, sort,fetchExpired)).thenThrow(RuntimeException.class);
		ResponseEntity<APIResponse<List<CustomerIndexDTO>>> response3 = controller.getCustomers(customerName,
				customerNumber, city, state, corporateId, uniqueGroup, latest, sort,fetchExpired);
		assertEquals(response3.getStatusCodeValue(), 400);
	}
	
	@Test
	void testGetCustomerIndex() {
		when(customerIndexService.getCustIndex(notifyQueueId)).thenReturn(custIndex);
		ResponseEntity<APIResponse<CustomerIndexDTO>> response = controller.getCustomerIndex(notifyQueueId);
		assertEquals(response.getStatusCodeValue(), 200);
	}

}
