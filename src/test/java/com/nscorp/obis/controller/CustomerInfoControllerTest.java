package com.nscorp.obis.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpServerErrorException;

import com.nscorp.obis.domain.CustomerInfo;
import com.nscorp.obis.dto.CustomerInfoDTO;
import com.nscorp.obis.dto.mapper.CustomerInfoMapper;

import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.PaginatedResponse;
import com.nscorp.obis.services.CustomerInfoService;

public class CustomerInfoControllerTest {

	@Mock
	CustomerInfoService customerInfoService;

	@Mock
	CustomerInfoMapper customerInfoMapper;

	@InjectMocks
	CustomerInfoController customerInfoController;

	CustomerInfoDTO customerInfoDto;
	CustomerInfo customerInfo;
	Map<String, String> header;
	List<CustomerInfoDTO> customerInfos;
	PaginatedResponse<CustomerInfoDTO> paginatedResponse;


	Long customerId;
	String customerName;
	String customerNumber;
	Integer pageNumber;
	Integer pageSize;
	String[] sort={"customerId","asc"};
	String[] filter= {"city","los angeles"};
	Map<String, String> headers;
	CustomerInfoDTO custInfoDto;
	String fetchExpired="N";

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		customerId = 4245005430299L;
		customerName = "UPS FREIGHT";
		customerNumber = "8013960706";
		customerInfos = Arrays.asList(new CustomerInfoDTO());
		customerInfoDto = new CustomerInfoDTO();
		customerInfo = new CustomerInfo();
		custInfoDto=new CustomerInfoDTO();
		custInfoDto.setCustomerId(customerId);
		custInfoDto.setCustomerName(customerName);
		custInfoDto.setCustomerNumber(customerNumber);
		custInfoDto.setBillToCustomerId(123456L);
		custInfoDto.setBillToCustomerName("test");
		custInfoDto.setBillToCustomerNumber("1234");
		headers=new HashMap<>();
		headers.put("userid", "test");
		headers.put("extensionschema", "test");
		paginatedResponse=new PaginatedResponse<>();
		paginatedResponse.setTotalCountOfRecords(2L);
		paginatedResponse.setContent(Arrays.asList(custInfoDto));
		pageNumber=0;
		pageSize=20;

	}

	@AfterEach
	void tearDown() {
		customerInfos = null;
		customerId = null;
		customerName = null;
		customerNumber = null;
		customerInfoDto = null;
		customerInfo = null;
		
	}

	@Test
	void testFetchCustomers() {
		when(customerInfoService.fetchCustomers(Mockito.anyLong(), Mockito.anyString(), Mockito.anyString(),Mockito.anyInt(),Mockito.anyInt(),Mockito.any(),Mockito.any(),Mockito.any()))
				.thenReturn(paginatedResponse);
		ResponseEntity<APIResponse<PaginatedResponse<CustomerInfoDTO>>> response = customerInfoController.fetchCustomers(customerId,
				customerName, customerNumber, pageNumber , pageSize ,sort,filter,fetchExpired);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	void testUpdateCustomers() {
		when(customerInfoService.updateCustomer(custInfoDto, headers)).thenReturn(custInfoDto);
		ResponseEntity<APIResponse<CustomerInfoDTO>> response = customerInfoController.updateCustomerInfo(custInfoDto, headers);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	void testFetchCustomersParamsError() {
		customerId = null;
		customerName = null;
		customerNumber = null;
		when(customerInfoService.fetchCustomers(Mockito.anyLong(), Mockito.anyString(), Mockito.anyString(),Mockito.anyInt(),Mockito.anyInt(),Mockito.any(),Mockito.any(),Mockito.any()))
				.thenReturn(paginatedResponse);
		ResponseEntity<APIResponse<PaginatedResponse<CustomerInfoDTO>>> response = customerInfoController.fetchCustomers(customerId,
				customerName, customerNumber,pageNumber,pageSize,sort,filter,fetchExpired);
		assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	
	@Test
	void testAddCustomerInfo() {
		when(customerInfoMapper.customerInfoToCustomerInfoDTO(Mockito.any()))
				.thenReturn(customerInfoDto);
		when(customerInfoMapper.customerInfoDTOToCustomerInfo(Mockito.any())).thenReturn(customerInfo);
		when(customerInfoService.addCustomer(Mockito.any(), Mockito.any()))
				.thenReturn(customerInfo);
		ResponseEntity<APIResponse<CustomerInfoDTO>> addedCustomer = customerInfoController
				.addCustomerInfo(customerInfoDto, header);
		assertEquals(addedCustomer.getStatusCodeValue(), 200);
	}

	@Test
	void TestNoRecordException() {
		when(customerInfoService.fetchCustomers(Mockito.anyLong(), Mockito.anyString(), Mockito.anyString(),Mockito.anyInt(),Mockito.anyInt(),Mockito.any(),Mockito.any(),Mockito.any()))
				.thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<PaginatedResponse<CustomerInfoDTO>>> response = customerInfoController.fetchCustomers(customerId,
				customerName, customerNumber, pageNumber,pageSize ,sort,filter,fetchExpired);
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);

		when(customerInfoService.addCustomer(Mockito.any(),Mockito.any()))
				.thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<CustomerInfoDTO>> response2 = customerInfoController
				.addCustomerInfo(customerInfoDto, header);
		assertEquals(response2.getStatusCodeValue(), 404);
		when(customerInfoService.updateCustomer(custInfoDto, headers)).thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<CustomerInfoDTO>> response3 = customerInfoController.updateCustomerInfo(custInfoDto, headers);
		assertEquals(response3.getStatusCode(), HttpStatus.NOT_FOUND);	
	}


	@Test
	void testException()  {
		when(customerInfoService.addCustomer(Mockito.any(),Mockito.any()))
				.thenThrow(new RuntimeException());
				ResponseEntity<APIResponse<CustomerInfoDTO>> response1 = customerInfoController
				.addCustomerInfo(customerInfoDto, header);
		assertEquals(response1.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);	
	}
	
	@Test
	void testInvalidDataException() {
		when(customerInfoService.updateCustomer(custInfoDto, headers)).thenThrow(new InvalidDataException("Error"));
		ResponseEntity<APIResponse<CustomerInfoDTO>> response3 = customerInfoController.updateCustomerInfo(custInfoDto, headers);
		assertEquals(response3.getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	
	@Test
	void testInternalServerException() {
		when(customerInfoService.fetchCustomers(Mockito.anyLong(), Mockito.anyString(), Mockito.anyString(),Mockito.anyInt(),Mockito.anyInt(),Mockito.any(),Mockito.any(),Mockito.any()))
				.thenThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR));
		ResponseEntity<APIResponse<PaginatedResponse<CustomerInfoDTO>>> response = customerInfoController.fetchCustomers(customerId,
				customerName, customerNumber, pageNumber,pageSize,sort,filter,fetchExpired);
		assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
		when(customerInfoService.updateCustomer(custInfoDto, headers)).thenThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR));
		ResponseEntity<APIResponse<CustomerInfoDTO>> response2 = customerInfoController.updateCustomerInfo(custInfoDto, headers);
		assertEquals(response2.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
