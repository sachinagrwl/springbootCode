package com.nscorp.obis.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.jpa.domain.Specification;

import com.nscorp.obis.common.SpecificationGenerator;
import com.nscorp.obis.domain.CustomerNickname;
import com.nscorp.obis.dto.CustomerNicknameDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.repository.CustomerNicknameRepository;
import com.nscorp.obis.repository.CustomerRepository;
import com.nscorp.obis.repository.TerminalRepository;

public class CustomerNicknameServiceTest {

	@Mock
	CustomerNicknameRepository customerNicknameRepository;

	@Mock
	SpecificationGenerator specificationGenerator;

	@InjectMocks
	CustomerNicknameServiceImpl customerNicknameService;

	@Mock
	TerminalRepository terminalRepository;

	@Mock
	CustomerRepository customerRepository;

	CustomerNickname customerNickname;
	CustomerNicknameDTO customerNicknameDTO;
	List<CustomerNickname> customerNicknames;
	List<CustomerNicknameDTO> customerNicknameDTOs;
	Specification<CustomerNickname> specification;

	Long customerId;
	Long terminalId;
	String nickname;
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
		customerNicknames = new ArrayList<CustomerNickname>();
		customerNicknameDTOs = new ArrayList<CustomerNicknameDTO>();

		customerId = 68744832863098L;
		terminalId = 46544102182938L;
		nickname = "6305992358";

		customerNickname.setCustomerId(customerId);
		customerNickname.setTerminalId(terminalId);
		customerNickname.setCustomerNickname(nickname);
		customerNicknames.add(customerNickname);
		customerNicknameDTO.setCustomerId(customerId);
		customerNicknameDTO.setTerminalId(terminalId);
		customerNicknameDTO.setCustomerNickname(nickname);
		customerNicknameDTOs.add(customerNicknameDTO);
		specification = specificationGenerator.customerNicknameSpecification(terminalId, customerId, nickname);
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
	void testFetchCustomerNickname() throws SQLException {
		when(terminalRepository.existsByTerminalId(customerNickname.getTerminalId())).thenReturn(true);
		when(customerRepository.existsByCustomerId(customerNickname.getCustomerId())).thenReturn(true);
		when(customerNicknameRepository.findAll(specification)).thenReturn(customerNicknames);
		when(customerNicknameService.fetchCustomerNickname(customerId, terminalId, nickname))
				.thenReturn(customerNicknames);
	}

	@Test
	void testaddCustomerNickname() throws SQLException {
		when(terminalRepository.existsByTerminalId(customerNickname.getTerminalId())).thenReturn(true);
		when(customerRepository.existsByCustomerId(customerNickname.getCustomerId())).thenReturn(true);
		when(customerNicknameRepository.existsByCustomerNickname(customerNickname.getCustomerNickname()))
				.thenReturn(false);
		when(customerNicknameRepository.existsByCustomerNicknameAndTerminalId(customerNickname.getCustomerNickname(),customerNickname.getTerminalId()))
		.thenReturn(false);

		when(customerNicknameRepository.save(customerNickname)).thenReturn(customerNickname);
		when(customerNicknameService.addCustomerNickname(customerNickname,header)).thenReturn(customerNickname);

	}

	@Test
	void testdeleteCustomerNickname() throws SQLException {
		when(terminalRepository.existsByTerminalId(customerNickname.getTerminalId())).thenReturn(true);
		when(customerRepository.existsByCustomerId(customerNickname.getCustomerId())).thenReturn(true);
		when(customerNicknameRepository.existsByCustomerNickname(customerNickname.getCustomerNickname()))
				.thenReturn(true);
		when(customerNicknameRepository.existsByCustomerNicknameAndTerminalId(customerNickname.getCustomerNickname(),customerNickname.getTerminalId()))
		.thenReturn(true);

		// when(customerNicknameRepository.deleteByCustomerNickname(customerNickname.getCustomerNickname())).thenReturn(customerNickname);
		//when(customerNicknameService.deleteCustomerNickname(customerNickname)).thenReturn(customerNickname);
		customerNicknameService.deleteCustomerNickname(customerNickname);
	}

	@Test
	void testNoRecordFoundException() throws SQLException {
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(customerNicknameService.fetchCustomerNickname(customerId, terminalId, nickname)));
		assertEquals("No Terminal Found with this terminal id : " + terminalId, exception.getMessage());
		when(customerRepository.existsByCustomerId(customerNickname.getCustomerId())).thenReturn(false);
		when(terminalRepository.existsByTerminalId(customerNickname.getTerminalId())).thenReturn(true);
		NoRecordsFoundException exception2 = assertThrows(NoRecordsFoundException.class,
				() -> when(customerNicknameService.fetchCustomerNickname(customerId, terminalId, nickname)));
		assertEquals("No Customer Found with this customer id : " + customerId, exception2.getMessage());
		customerId = null;
		terminalId = null;
		nickname = null;
		NoRecordsFoundException exception3 = assertThrows(NoRecordsFoundException.class,
				() -> when(customerNicknameService.fetchCustomerNickname(customerId, terminalId, nickname)));
		assertEquals("Pass any parameter", exception3.getMessage());

		customerId = 68744832863098L;
		terminalId = 46544102182938L;
		nickname = "6305992358";
		when(customerNicknames.isEmpty()).thenReturn(true);
		NoRecordsFoundException exception4 = assertThrows(NoRecordsFoundException.class,
				() -> when(customerNicknameService.fetchCustomerNickname(customerId, terminalId, nickname)));
		assertEquals("No Record found for this combination", exception4.getMessage());

		// Exceptions in addCustomerNickname

		when(terminalRepository.existsByTerminalId(terminalId)).thenReturn(false);
		NoRecordsFoundException addexception1 = assertThrows(NoRecordsFoundException.class,
				() -> when(customerNicknameService.addCustomerNickname(customerNickname,header)));
		assertEquals("No Terminal Found with this terminal id : " + terminalId, addexception1.getMessage());

		when(terminalRepository.existsByTerminalId(terminalId)).thenReturn(true);
		when(customerRepository.existsByCustomerId(customerId)).thenReturn(false);
		NoRecordsFoundException addexception2 = assertThrows(NoRecordsFoundException.class,
				() -> when(customerNicknameService.addCustomerNickname(customerNickname,header)));
		assertEquals("No Customer Found with this customer id : " + customerId, addexception2.getMessage());

		when(customerRepository.existsByCustomerId(customerId)).thenReturn(true);
		customerNickname.setCustomerNickname(" ");
		NoRecordsFoundException addexception6 = assertThrows(NoRecordsFoundException.class,
				() -> when(customerNicknameService.addCustomerNickname(customerNickname,header)));
		assertEquals("Nickname cant be empty. Enter Valid Nickname", addexception6.getMessage());

		customerNickname.setCustomerId(null);
		NoRecordsFoundException addexception5 = assertThrows(NoRecordsFoundException.class,
				() -> when(customerNicknameService.addCustomerNickname(customerNickname,header)));
		assertEquals("customerId cant be empty. Enter Valid customerId", addexception5.getMessage());

		customerNickname.setTerminalId(null);
		NoRecordsFoundException addexception4 = assertThrows(NoRecordsFoundException.class,
				() -> when(customerNicknameService.addCustomerNickname(customerNickname,header)));
		assertEquals("terminalId  cant be empty. Enter Valid terminalId", addexception4.getMessage());

		// Exceptions in deleteCustomerNickname

		customerNickname.setCustomerId(customerId);
		customerNickname.setTerminalId(terminalId);
		customerNickname.setCustomerNickname(nickname);
		
		when(terminalRepository.existsByTerminalId(terminalId)).thenReturn(false);
		NoRecordsFoundException deleteexception1 = assertThrows(NoRecordsFoundException.class,
				() -> when(customerNicknameService.deleteCustomerNickname(customerNickname)));
		assertEquals("No Terminal Found with this terminal id : " + terminalId, deleteexception1.getMessage());

		when(terminalRepository.existsByTerminalId(terminalId)).thenReturn(true);
		when(customerRepository.existsByCustomerId(customerId)).thenReturn(false);
		NoRecordsFoundException deleteexception2 = assertThrows(NoRecordsFoundException.class,
				() -> when(customerNicknameService.deleteCustomerNickname(customerNickname)));
		assertEquals("No Customer Found with this customer id : " + customerId, deleteexception2.getMessage());

		when(customerRepository.existsByCustomerId(customerId)).thenReturn(true);
		when(customerNicknameRepository.existsByCustomerNickname(nickname)).thenReturn(false);
		when(customerNicknameRepository.existsByCustomerNicknameAndTerminalId(nickname,terminalId))
		.thenReturn(false);
		NoRecordsFoundException deleteexception7 = assertThrows(NoRecordsFoundException.class,
				() -> when(customerNicknameService.deleteCustomerNickname(customerNickname)));
		assertEquals("Nickname does not exist. Change the nickname", deleteexception7.getMessage());

		when(customerRepository.existsByCustomerId(customerId)).thenReturn(true);
		customerNickname.setCustomerNickname(" ");
		NoRecordsFoundException deleteexception6 = assertThrows(NoRecordsFoundException.class,
				() -> when(customerNicknameService.deleteCustomerNickname(customerNickname)));
		assertEquals("Nickname cant be empty. Enter Valid Nickname", deleteexception6.getMessage());

		customerNickname.setCustomerId(null);
		NoRecordsFoundException deleteexception5 = assertThrows(NoRecordsFoundException.class,
				() -> when(customerNicknameService.deleteCustomerNickname(customerNickname)));
		assertEquals("customerId cant be empty. Enter Valid customerId", deleteexception5.getMessage());

		customerNickname.setTerminalId(null);
		NoRecordsFoundException deleteexception4 = assertThrows(NoRecordsFoundException.class,
				() -> when(customerNicknameService.deleteCustomerNickname(customerNickname)));
		assertEquals("terminalId  cant be empty. Enter Valid terminalId", deleteexception4.getMessage());

	}

	@Test
	void testRecordAlreadyExistsException() {
		when(terminalRepository.existsByTerminalId(terminalId)).thenReturn(true);
		when(customerRepository.existsByCustomerId(customerId)).thenReturn(true);
		when(customerNicknameRepository.existsByCustomerNickname(nickname)).thenReturn(true);
		when(customerNicknameRepository.existsByCustomerNicknameAndTerminalId(nickname,terminalId))
		.thenReturn(true);
		RecordAlreadyExistsException addexception7 = assertThrows(RecordAlreadyExistsException.class,
				() -> when(customerNicknameService.addCustomerNickname(customerNickname,header)));
		assertEquals("A customer with this alias already exists for this terminal. Change the alias to make it unique for this terminal.",
				addexception7.getMessage());
	}

	@Test
	void testRecordNotAddedException() {
		// customerNickname.setCustomerId(null);
		when(terminalRepository.existsByTerminalId(terminalId)).thenReturn(true);
		when(customerRepository.existsByCustomerId(customerId)).thenReturn(true);
		when(customerNicknameRepository.save(customerNickname)).thenReturn(null);
		RecordNotAddedException addexception8 = assertThrows(RecordNotAddedException.class,
				() -> when(customerNicknameService.addCustomerNickname(customerNickname,header)));
		assertEquals("Record Not added to Database", addexception8.getMessage());

	}
}
