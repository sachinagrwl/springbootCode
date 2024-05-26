package com.nscorp.obis.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.jpa.domain.Specification;

import com.nscorp.obis.common.SpecificationGenerator;
import com.nscorp.obis.domain.CustomerTerm;
import com.nscorp.obis.domain.CustomerTerminal;
import com.nscorp.obis.dto.CustomerTermDTO;
import com.nscorp.obis.dto.CustomerTerminalDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.repository.CustomerRepository;
import com.nscorp.obis.repository.CustomerTermRepository;
import com.nscorp.obis.repository.CustomerTerminalRepository;
import com.nscorp.obis.repository.TerminalRepository;

public class CustomerTermServiceTest {

	@Mock
	CustomerTermRepository customerTermRepository;
	@Mock
	CustomerTerminalRepository customerTerminalRepository;
	@Mock
	SpecificationGenerator specificationGenerator;

	@InjectMocks
	CustomerTermServiceImpl customerTermService;

	@Mock
	TerminalRepository terminalRepository;

	@Mock
	CustomerRepository customerRepository;

	CustomerTerm customerTerm;
	CustomerTermDTO customerTermDTO;
	List<CustomerTerm> customerTerms;
	List<CustomerTermDTO> customerTermDTOs;
	CustomerTerminal customerTerminal;
	CustomerTerminalDTO customerTerminalDTO;
	List<CustomerTerminal> customerTerminals;
	List<CustomerTerminalDTO> customerTerminalDTOs;
	Map<String, String> header;
	Specification<CustomerTerm> specification;

	Long customerId;
	Long terminalId;
	String customerName;
	String customerDescription;
	String customerCity;
	String custormerState;
	String customerNumber;

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
		customerTermDTO = new CustomerTermDTO();
		customerTerms = new ArrayList<CustomerTerm>();
		customerTermDTOs = new ArrayList<CustomerTermDTO>();
		customerTerminal = new CustomerTerminal();
		customerTerminals = new ArrayList<>();
		terminalId = 1002004L;
		customerName = "CASCO SERVICES INC";
		customerId = 1138L;
		customerDescription = "TRAILER USE CHARGES";
		customerCity = "NEWARK";
		custormerState = "NJ";
		customerNumber = "1328610018";
		customerTerminal.setCustomerId(customerId);
		customerTerminal.setTerminalId(terminalId);
		customerTerminals.add(customerTerminal);
		customerTerm.setCustomerId(customerId);
		customerTerm.setTerminalId(terminalId);
		customerTerm.setCustomerName(customerName);
		customerTerm.setCustomerDescription(customerDescription);
		customerTerm.setCustomerCity(customerCity);
		customerTerm.setCustormerState(custormerState);
		customerTerm.setCustomerNumber(customerNumber);
		customerTerms.add(customerTerm);
		customerTermDTO.setCustomerId(customerId);
		customerTermDTO.setTerminalId(terminalId);
		customerTermDTO.setCustomerName(customerName);
		customerTermDTO.setCustomerDescription(customerDescription);
		customerTermDTO.setCustomerCity(customerCity);
		customerTermDTO.setCustormerState(custormerState);
		customerTermDTO.setCustomerNumber(customerNumber);
		customerTermDTOs.add(customerTermDTO);
		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");

		specification = new Specification<CustomerTerm>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 386803734047311080L;

			@Override
			public Predicate toPredicate(Root<CustomerTerm> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				if (Objects.nonNull(terminalId)) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("terminalId"), terminalId)));
				}
				if (Objects.nonNull(customerName)) {
					predicates.add(criteriaBuilder
							.and(criteriaBuilder.like(root.get("customerName"), "%" + customerName + "%")));
				}
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};

	}

	@AfterEach
	void tearDown() throws Exception {
		customerTerm = null;
		customerTermDTO = null;
		customerTerms = null;
		customerTermDTOs = null;
	}

	@SuppressWarnings({ "unchecked", "unused" })
	@Test
	void testFetchCustomerTerm() throws SQLException {
		when(specificationGenerator.customerTermSpecification(terminalId, customerName)).thenReturn(specification);
		when(customerTermRepository.findAll(specification)).thenReturn(customerTerms);
		List<CustomerTerm> customerTerms = customerTermService.fetchCustomerNotifyProfiles(terminalId, customerName);
	}

	@Test
	void testFetchCustomerTerminal() throws SQLException {
		when(customerTerminalRepository.findByCustomerId(Mockito.any())).thenReturn(customerTerminals);
		List<CustomerTerminal> response = customerTermService.fetchCustomerTerminal(customerId);
		assertEquals(response, customerTerminals);
	}

	@Test
	void testNoRecordsFoundException() {
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(customerTermService.fetchCustomerNotifyProfiles(terminalId, customerName)));
		assertEquals("No Records found for this Combination.", exception.getMessage());
		// when(customerTerminalRepository.findByCustomerId(Mockito.any())).thenThrow(new
		// NoRecordsFoundException("No records found for this customer"));
		NoRecordsFoundException exception2 = assertThrows(NoRecordsFoundException.class,
				() -> when(customerTermService.fetchCustomerTerminal(customerId)));
		assertEquals(exception2.getMessage(), "No Records found for this Customer.");
	}

	@Test
	void testupdateCustomerTerminals() throws SQLException {
		when(customerRepository.existsByCustomerId(customerTerminal.getCustomerId())).thenReturn(true);
		when(terminalRepository.existsByTerminalId(customerTerminal.getTerminalId())).thenReturn(true);
		when(customerTerminalRepository.save(customerTerminal)).thenReturn(customerTerminal);
		when(customerTermService.updateCustomerTerminals(customerTerminal, header)).thenReturn(customerTerminal);

	}

	@Test
	void testdeleteByCustomerId() throws SQLException {

		when(customerRepository.existsByCustomerId(customerId)).thenReturn(true);
		customerTermService.deleteByCustomerId(customerId);

	}

	@Test
	void testupdateNoRecordsFoundException() {

		when(customerRepository.existsByCustomerId(customerId)).thenReturn(false);
		NoRecordsFoundException updateexception1 = assertThrows(NoRecordsFoundException.class,
				() -> when(customerTermService.updateCustomerTerminals(customerTerminal, header)));
		assertEquals("No Customer Found with this customer id : " + customerId, updateexception1.getMessage());

		when(customerRepository.existsByCustomerId(customerId)).thenReturn(true);
		when(terminalRepository.existsByTerminalId(terminalId)).thenReturn(false);
		NoRecordsFoundException updateexception2 = assertThrows(NoRecordsFoundException.class,
				() -> when(customerTermService.updateCustomerTerminals(customerTerminal, header)));
		assertEquals("No Terminal Found with this terminal id : " + terminalId, updateexception2.getMessage());

		customerTerminal.setTerminalId(null);
		NoRecordsFoundException updateexception3 = assertThrows(NoRecordsFoundException.class,
				() -> when(customerTermService.updateCustomerTerminals(customerTerminal, header)));
		assertEquals("terminalId cant be null. Enter Valid terminalId", updateexception3.getMessage());

		customerTerminal.setCustomerId(null);
		NoRecordsFoundException updateexception4 = assertThrows(NoRecordsFoundException.class,
				() -> when(customerTermService.updateCustomerTerminals(customerTerminal, header)));
		assertEquals("customerId cant be null. Enter Valid customerId", updateexception4.getMessage());

	}

	

	@Test
	void testRecordNotAddedException() {
		when(customerRepository.existsByCustomerId(customerId)).thenReturn(true);
		when(terminalRepository.existsByTerminalId(terminalId)).thenReturn(true);
		when(customerTerminalRepository.save(customerTerminal)).thenReturn(null);
		RecordNotAddedException addexception5 = assertThrows(RecordNotAddedException.class,
				() -> when(customerTermService.updateCustomerTerminals(customerTerminal, header)));
		assertEquals("Record Not added to Database", addexception5.getMessage());

	}


}
