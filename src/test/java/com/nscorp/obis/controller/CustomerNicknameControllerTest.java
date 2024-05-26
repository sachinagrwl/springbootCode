package com.nscorp.obis.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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

import com.nscorp.obis.domain.CustomerNickname;
import com.nscorp.obis.dto.CustomerNicknameDTO;
import com.nscorp.obis.dto.CustomerNicknameListDTO;
import com.nscorp.obis.dto.mapper.CustomerNicknameMapper;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.repository.CustomerNicknameRepository;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.CustomerNicknameService;

public class CustomerNicknameControllerTest {

	@Mock
	CustomerNicknameMapper customerNicknameMapper;

	@Mock
	CustomerNicknameRepository customerNicknameRepository;

	@Mock
	CustomerNicknameService customerNicknameService;

	@InjectMocks
	CustomerNicknameController customerNicknameController;

	CustomerNickname customerNickname;
	CustomerNicknameDTO customerNicknameDTO;
	CustomerNicknameListDTO customerNicknameListDTO;
	List<CustomerNickname> customerNicknames;
	List<CustomerNicknameDTO> customerNicknameDTOs;

	Long customerId;
	Long terminalId;
	String nickname;
	List<String> nicknames;
	Map<String, String> header;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		customerNickname = new CustomerNickname();
		customerNicknameDTO = new CustomerNicknameDTO();
		customerNicknameListDTO = new CustomerNicknameListDTO();
		customerNicknames = new ArrayList<CustomerNickname>();
		customerNicknameDTOs = new ArrayList<CustomerNicknameDTO>();
		nicknames = new ArrayList<String>();

		customerId = 68744832863098L;
		terminalId = 46544102182938L;
		nickname = "6305992358";
		nicknames = Arrays.asList("6305992358","6305992358");

		customerNickname.setCustomerId(customerId);
		customerNickname.setTerminalId(terminalId);
		customerNickname.setCustomerNickname(nickname);
		customerNicknames.add(customerNickname);
		customerNicknameDTO.setCustomerId(customerId);
		customerNicknameDTO.setTerminalId(terminalId);
		customerNicknameDTO.setCustomerNickname(nickname);
		customerNicknameListDTO.setCustomerId(customerId);
		customerNicknameListDTO.setTerminalId(terminalId);
		customerNicknameListDTO.setCustomerNickname(nicknames);
		customerNicknameDTOs.add(customerNicknameDTO);
		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");
	}

	@AfterEach
	void tearDown() throws Exception {
		customerNickname = null;
		customerNicknameDTO = null;
		customerNicknames = null;
		customerNicknameDTOs = null;
	}

	@Test
	void testGetCustomerNickname() throws SQLException {
		when(customerNicknameService.fetchCustomerNickname(Mockito.any(), Mockito.any(), Mockito.any()))
				.thenReturn(customerNicknames);
		when(customerNicknameMapper.customerNicknameDTOToCustomerNickname(Mockito.any())).thenReturn(customerNickname);
		when(customerNicknameMapper.customerNicknameToCustomerNicknameDTO(Mockito.any()))
				.thenReturn(customerNicknameDTO);
		ResponseEntity<APIResponse<List<CustomerNicknameDTO>>> getData = customerNicknameController
				.getCustomerNickname(customerId, terminalId, nickname);
		assertEquals(getData.getStatusCodeValue(), 200);
	}

	@Test
	@DisplayName("NoRecordsFoundException")
	void testNoRecordsFoundException() throws SQLException {
		when(customerNicknameService.fetchCustomerNickname(Mockito.any(), Mockito.any(), Mockito.any()))
				.thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<List<CustomerNicknameDTO>>> getData = customerNicknameController
				.getCustomerNickname(customerId, terminalId, nickname);
		assertEquals(getData.getStatusCodeValue(), 404);
	}

	@Test
	@DisplayName("Internal Server Error")
	void testException() throws SQLException {
		when(customerNicknameService.fetchCustomerNickname(Mockito.any(), Mockito.any(), Mockito.any()))
				.thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<List<CustomerNicknameDTO>>> getData = customerNicknameController
				.getCustomerNickname(customerId, terminalId, nickname);
		assertEquals(getData.getStatusCodeValue(), 500);
	}

	@Test
	void testaddCustomerNickname() {
		
		when(customerNicknameMapper.customerNicknameDTOToCustomerNickname(Mockito.any())).thenReturn(customerNickname);
		when(customerNicknameService.addCustomerNickname(Mockito.any(),Mockito.any())).thenReturn(customerNickname);
		when(customerNicknameMapper.customerNicknameToCustomerNicknameDTO(Mockito.any())).thenReturn(customerNicknameDTO);
		ResponseEntity<List<APIResponse<CustomerNicknameDTO>>> addData = customerNicknameController
				.addCustomerNickname(customerNicknameListDTO,header);
		assertEquals(addData.getStatusCodeValue(), 200);

	}

	@Test
	void testdeleteCustomerNickname() {
		
		when(customerNicknameMapper.customerNicknameDTOToCustomerNickname(Mockito.any())).thenReturn(customerNickname);
		when(customerNicknameService.deleteCustomerNickname(Mockito.any())).thenReturn(customerNickname);
		when(customerNicknameMapper.customerNicknameToCustomerNicknameDTO(Mockito.any())).thenReturn(customerNicknameDTO);
		ResponseEntity<List<APIResponse<CustomerNicknameDTO>>> deleteData = customerNicknameController
				.deleteCustomerNickname(customerNicknameListDTO);
		assertEquals(deleteData.getStatusCodeValue(), 200);

	}

	@Test
	void testExceptions(){

		when(customerNicknameService.addCustomerNickname(Mockito.any(),Mockito.any())).thenThrow(new NoRecordsFoundException());
		ResponseEntity<List<APIResponse<CustomerNicknameDTO>>> addData = customerNicknameController
				.addCustomerNickname(customerNicknameListDTO,header);
		assertEquals(addData.getStatusCodeValue(), 400);

		when(customerNicknameService.deleteCustomerNickname(Mockito.any())).thenThrow(new NoRecordsFoundException());
		ResponseEntity<List<APIResponse<CustomerNicknameDTO>>> deleteData = customerNicknameController
				.deleteCustomerNickname(customerNicknameListDTO);
		assertEquals(deleteData.getStatusCodeValue(), 400);
	}

	@Test
	@DisplayName("InvalidDataException")
	void testInvalidDataException() throws SQLException {

		customerNicknameListDTO.setCustomerNickname( null);
		InvalidDataException addexception = assertThrows(InvalidDataException.class,
				() -> when(customerNicknameController.addCustomerNickname(customerNicknameListDTO,header)));
		assertEquals("Nickname cant be empty. Enter Valid Nickname", addexception.getMessage());

		customerNicknameListDTO.setCustomerNickname( null);
		InvalidDataException deleteexception = assertThrows(InvalidDataException.class,
				() -> when(customerNicknameController.deleteCustomerNickname(customerNicknameListDTO)));
		assertEquals("Nickname cant be empty. Enter Valid Nickname", deleteexception.getMessage());

	}

}
