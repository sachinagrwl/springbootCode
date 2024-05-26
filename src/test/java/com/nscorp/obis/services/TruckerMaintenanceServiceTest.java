package com.nscorp.obis.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.*;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.nscorp.obis.domain.CustomerIndex;
import com.nscorp.obis.domain.CustomerScac;
import com.nscorp.obis.domain.DrayageCompany;
import com.nscorp.obis.domain.DrayageCustomer;
import com.nscorp.obis.domain.DrayageCustomerInfo;
import com.nscorp.obis.dto.CustomerScacDTO;
import com.nscorp.obis.dto.DrayageCustomerInfoDTO;
import com.nscorp.obis.dto.mapper.CustomerScacMapper;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.repository.CustomerInfoRepository;
import com.nscorp.obis.repository.CustomerRepository;
import com.nscorp.obis.repository.CustomerScacRepository;
import com.nscorp.obis.repository.DrayageCompanyRepository;
import com.nscorp.obis.repository.DrayageCustomerInfoRepository;
import com.nscorp.obis.repository.DrayageCustomerRepository;
import com.nscorp.obis.repository.DrayageSCACRepository;

public class TruckerMaintenanceServiceTest {

	@Mock
	DrayageCustomerRepository drayageCustomerRepository;
	
	@Mock
	DrayageCompanyRepository drayageCompanyRepository;
	
	@InjectMocks
	TruckerMaintenanceServiceImpl truckerMaintenanceService;

	@Mock
	CustomerRepository customerRepository;
	
	@Mock
	CustomerInfoRepository customerInfoRepository;
	
	@Mock
	DrayageCustomerInfoRepository drayageCustomerInfoRepository;
	
	@Mock
	DrayageSCACRepository drayageSCACRepository;
	
	@Mock
	SpecificationGenerator generator;
	
	@Mock
	CustomerScacMapper customerScacMapper;
	
	@Mock
	CustomerScacRepository repository;

	DrayageCustomerInfoDTO drayageCustomerInfoDTO;
	DrayageCustomer drayageCustomer;
	DrayageCustomerInfo drayageCustomerInfo;
	List<DrayageCustomerInfo> drayageCustomerInfos;
	List<DrayageCustomer> drayageCustomerList;
	DrayageCompany drayageCompany;
	List<DrayageCompany> drayageCompanyList;
	Map<String, String> header;
	List<DrayageCustomerInfoDTO> drayageCustomerInfoDTOs;
	
	List<CustomerScac> customerScacs;
	List<CustomerScacDTO> customerScacDTOs;
	Specification<CustomerScac> specification;

	Long customerId;
	String drayageId;
	String customerName;
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
		
		customerName = "CASCO SERVICES INC";
		customerId = 1138L;
		drayageId = "ABCD";
		customerNumber = "1328610018";
		drayageCustomer = new DrayageCustomer();
		drayageCustomerInfo=new DrayageCustomerInfo();
		drayageCustomerInfoDTO=new DrayageCustomerInfoDTO();
		drayageCustomerInfoDTOs = new ArrayList<>();
		drayageCustomerInfoDTO.setCustomerId(customerId);
		drayageCustomerInfoDTO.setDrayageId(drayageId);
		drayageCustomer.setCustomerNumber(customerNumber);
		drayageCustomer.setCustomerName(customerName);
		drayageCustomer.setDrayageId(drayageId);
		drayageCustomerList = new ArrayList<>();
		drayageCustomerList.add(drayageCustomer);
		drayageCompany = new DrayageCompany();
		drayageCompany.setDrayageId(drayageId);
		drayageCompany.setTiaInd("S");
		drayageCompany.setBondedCarrier("N");
		drayageCompany.setTiaSuspendDate(new Date());
		drayageCompanyList = new ArrayList<>();
		drayageCompanyList.add(drayageCompany);
		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");
		drayageCustomerInfo.setCustomerId(customerId);
		drayageCustomerInfo.setDrayageId(drayageId);
		drayageCustomerInfo.setCustomerName(customerName);
		drayageCustomerInfo.setCustomerNumber(customerNumber);
		drayageCustomerInfos=Arrays.asList(drayageCustomerInfo);
		drayageCustomerInfoDTOs.add(drayageCustomerInfoDTO);
		customerScacs=Arrays.asList(new CustomerScac());
		customerScacDTOs=Arrays.asList(new CustomerScacDTO());
		
		specification = (Root<CustomerScac> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();
			return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
		};
	}

	@AfterEach
	void tearDown() throws Exception {
		drayageCustomer = null;
		drayageCustomerList = null;
		drayageCompany = null;
		drayageCompanyList = null;
		drayageCustomerInfo=null;
		drayageCustomerInfos=null;
		drayageCustomerInfoDTO=null;
		customerScacs=null;
		customerScacDTOs=null;
	}

	@Test
	void testFetchDrayageCustomers() throws SQLException {
		when(customerRepository.existsByCustomerId(Mockito.any())).thenReturn(true);
		when(drayageCustomerRepository.findByCustomerCustomerIdAndDrayageId(Mockito.any(), Mockito.any())).thenReturn(drayageCustomerList);
		List<DrayageCustomer> drayageCustomers = truckerMaintenanceService.fetchDrayageCustomers(customerId, drayageId);
		drayageId = null;
		when(drayageCustomerRepository.findByCustomerCustomerId(Mockito.any())).thenReturn(drayageCustomerList);
		List<DrayageCustomer> drayageCustomers1 = truckerMaintenanceService.fetchDrayageCustomers(customerId, drayageId);
		drayageId = "AACG";
		customerId=null;
		when(drayageCustomerRepository.findByDrayageId(Mockito.any())).thenReturn(drayageCustomerList);
		List<DrayageCustomer> drayageCustomers2 = truckerMaintenanceService.fetchDrayageCustomers(customerId, drayageId);
	}
	@Test
	void testFetchDrayageCompany() {
		when(drayageCompanyRepository.findByDrayageId(Mockito.any())).thenReturn(drayageCompanyList);
		List<DrayageCompany> drayageCompany = truckerMaintenanceService.fetchDrayageCompany(drayageId);
		assertEquals(drayageCompany,drayageCompanyList);
	}
	
	@Test
	void testFetchDrayageCustomersByPrimarysix() {
		when(generator.customerScacSpecification(Mockito.anyString())).thenReturn(specification);
		when(repository.findAll(Mockito.eq(specification))).thenReturn(customerScacs);
		when(customerScacMapper.customerScacTCustomerScacDTO(Mockito.any())).thenReturn(new CustomerScacDTO());
		List<CustomerScacDTO> response = truckerMaintenanceService.fetchDrayageCustomersByPrimarySix(customerNumber);
		assertEquals(customerScacDTOs,response);
	}

	@Test
	void testAddDrayageCustomer() {
		when(customerInfoRepository.existsById(Mockito.anyLong())).thenReturn(true);
		when(drayageCustomerInfoRepository.existsByCustomerId(Mockito.anyLong())).thenReturn(false);
		when(drayageCustomerInfoRepository.save(Mockito.any())).thenReturn(drayageCustomerInfo);
		DrayageCustomerInfo added = truckerMaintenanceService.addDrayageCustomer(drayageCustomerInfo, header,"N");
		assertEquals(added, drayageCustomerInfo);
	}
	@Test
	void testAddDrayageCustomerAndRemoveLink() {
		when(customerInfoRepository.existsById(Mockito.anyLong())).thenReturn(true);
		when(drayageSCACRepository.existsById(Mockito.any())).thenReturn(true);
		when(drayageCustomerInfoRepository.existsByCustomerId(Mockito.anyLong())).thenReturn(false);
		when(drayageCustomerInfoRepository.save(Mockito.any())).thenReturn(drayageCustomerInfo);
		when(drayageCustomerInfoRepository.findByCustomerIdAndDrayageId(Mockito.anyLong(), Mockito.anyString())).thenReturn(drayageCustomerInfos);
		
		DrayageCustomerInfo added = truckerMaintenanceService.addDrayageCustomerAndRemoveLink(drayageCustomerInfo,drayageId, header);
		assertEquals(added, drayageCustomerInfo);
	}
	
	@Test
	void testRemoveDrayageCustomerLink() {
		when(customerInfoRepository.existsById(Mockito.anyLong())).thenReturn(true);
		when(drayageSCACRepository.existsById(Mockito.any())).thenReturn(true);
		when(drayageCustomerInfoRepository.findByCustomerIdAndDrayageId(Mockito.anyLong(), Mockito.anyString()))
		.thenReturn(null);
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(truckerMaintenanceService.addDrayageCustomerAndRemoveLink(drayageCustomerInfo,drayageId, header)));
		assertEquals(exception.getMessage(),"No Records Found For Given Customer Id "+customerId+" And Drayage Id "+drayageId);
		
	}
	@Test
	void testDeleteDrayageCustomer() {
		when(customerInfoRepository.existsById(Mockito.anyLong())).thenReturn(true);
		when(drayageSCACRepository.existsById(Mockito.anyString())).thenReturn(true);
		when(drayageCustomerInfoRepository.findByCustomerIdAndDrayageId(Mockito.anyLong(), Mockito.anyString())).thenReturn(drayageCustomerInfos);
		List<DrayageCustomerInfoDTO> customerInfoDTO=truckerMaintenanceService.deleteDrayageCustomerInfo(drayageCustomerInfoDTOs);
		assertNotNull(customerInfoDTO);
	}

	@Test
	void testAddDrayageCompany() {
//		when(drayageCompanyRepository.getByDrayageId(Mockito.anyString())).thenReturn(new DrayageCompany());
		when(drayageSCACRepository.existsById(Mockito.anyString())).thenReturn(true);
		when(drayageCompanyRepository.existsByDrayageId(Mockito.anyString())).thenReturn(false);
		when(drayageCompanyRepository.save(Mockito.any())).thenReturn(drayageCompany);
		DrayageCompany addedDryageCompany = truckerMaintenanceService.addDrayageCompany(drayageCompany, header);
		assertNotNull(addedDryageCompany);

	}
	@Test
	void testUpdateDrayageCompany() {
		when(drayageCompanyRepository.getByDrayageId(Mockito.anyString())).thenReturn(new DrayageCompany());
		when(drayageSCACRepository.existsById(Mockito.anyString())).thenReturn(true);
		when(drayageCompanyRepository.existsByDrayageId(Mockito.anyString())).thenReturn(true);
		when(drayageCompanyRepository.save(Mockito.any())).thenReturn(drayageCompany);
		DrayageCompany updatedDryageCompany = truckerMaintenanceService.updateDrayageCompany(drayageCompany, header);
		assertNotNull(updatedDryageCompany);

	}

	@Test
	void testDeleteDrayageCustomerException() {
		when(customerInfoRepository.existsById(Mockito.anyLong())).thenReturn(false);
		when(drayageSCACRepository.existsById(Mockito.anyString())).thenReturn(true);
		when(drayageCustomerInfoRepository.findByCustomerIdAndDrayageId(Mockito.anyLong(), Mockito.anyString())).thenReturn(drayageCustomerInfos);
		assertThrows(NoRecordsFoundException.class, ()->truckerMaintenanceService.deleteDrayageCustomerInfo(drayageCustomerInfoDTOs));		
		when(customerInfoRepository.existsById(Mockito.anyLong())).thenReturn(true);
		when(drayageSCACRepository.existsById(Mockito.anyString())).thenReturn(false);
		when(drayageCustomerInfoRepository.findByCustomerIdAndDrayageId(Mockito.anyLong(), Mockito.anyString())).thenReturn(drayageCustomerInfos);
		assertThrows(NoRecordsFoundException.class, ()->truckerMaintenanceService.deleteDrayageCustomerInfo(drayageCustomerInfoDTOs));	
		when(customerInfoRepository.existsById(Mockito.anyLong())).thenReturn(true);
		when(drayageSCACRepository.existsById(Mockito.anyString())).thenReturn(true);
		when(drayageCustomerInfoRepository.findByCustomerIdAndDrayageId(Mockito.anyLong(), Mockito.anyString())).thenReturn(Arrays.asList());
		assertThrows(NoRecordsFoundException.class, ()->truckerMaintenanceService.deleteDrayageCustomerInfo(drayageCustomerInfoDTOs));		
	}
	
    @Test
    void testInvalidDataException() {
    	when(customerRepository.existsByCustomerId(Mockito.any())).thenReturn(false);
    	InvalidDataException exception = assertThrows(InvalidDataException.class,
				() -> when(truckerMaintenanceService.fetchDrayageCustomers(customerId, drayageId)));
    	assertEquals(exception.getMessage(),"Customer Id is invalid.");
    	drayageId = null;
		customerId=null;
    	when(customerRepository.existsByCustomerId(Mockito.any())).thenReturn(true);
    	InvalidDataException exception1 = assertThrows(InvalidDataException.class,
				() -> when(truckerMaintenanceService.fetchDrayageCustomers(customerId, drayageId)));
	    assertEquals(exception1.getMessage(),"Pass any query parameter.");

		drayageCompany.setTiaSuspendDate(new Date());
		drayageCompany.setTiaInd("A");
		when(drayageSCACRepository.existsById(Mockito.any())).thenReturn(true);
		InvalidDataException e = assertThrows(InvalidDataException.class,
				() -> when(truckerMaintenanceService.addDrayageCompany(drayageCompany, header)));
		assertEquals(e.getMessage(),"Suspend date only allowed with TIA code of S");
		InvalidDataException e1 = assertThrows(InvalidDataException.class,
				() -> when(truckerMaintenanceService.updateDrayageCompany(drayageCompany, header)));
		assertEquals(e1.getMessage(),"Suspend date only allowed with TIA code of S");
    }
	
	@Test
	void testNoRecordsFoundException() {
		drayageCustomerList = new ArrayList<>();
		when(customerRepository.existsByCustomerId(Mockito.any())).thenReturn(true);
		when(drayageCustomerRepository.findByCustomerCustomerIdAndDrayageId(Mockito.any(), Mockito.any())).thenReturn(drayageCustomerList);
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(truckerMaintenanceService.fetchDrayageCustomers(customerId, drayageId)));
		assertEquals(exception.getMessage(),"No Records found for this query.");
		drayageCompanyList = new ArrayList<>();
		when(drayageCompanyRepository.findByDrayageId(Mockito.any())).thenReturn(drayageCompanyList);
		NoRecordsFoundException exception1 = assertThrows(NoRecordsFoundException.class,
				() -> when(truckerMaintenanceService.fetchDrayageCompany(drayageId)));
		assertEquals(exception1.getMessage(),"No Records found for this query.");
		
		when(drayageCompanyRepository.findByDrayageId(Mockito.any())).thenReturn(null);
		NoRecordsFoundException ex = assertThrows(NoRecordsFoundException.class,
				() -> when(truckerMaintenanceService.updateDrayageCompany(drayageCompany,header)));
		when(drayageSCACRepository.existsById(Mockito.any())).thenReturn(false);
		NoRecordsFoundException exception2 = assertThrows(NoRecordsFoundException.class,
				() -> when(truckerMaintenanceService.addDrayageCustomerAndRemoveLink(drayageCustomerInfo,drayageId, header)));
	    assertEquals(exception2.getMessage(),"Drayage record not found with given Drayage Id :" + drayageId);
	    drayageCustomerInfo.setDrayageId(null);
		when(customerInfoRepository.existsById(Mockito.any())).thenReturn(false);
		NoRecordsFoundException exception3 = assertThrows(NoRecordsFoundException.class,
				() -> when(truckerMaintenanceService.addDrayageCustomer(drayageCustomerInfo, header,"N")));
		assertEquals(exception3.getMessage(),"No record found for this customer id ");
		when(drayageSCACRepository.existsById(Mockito.any())).thenReturn(true);
		NoRecordsFoundException exception4 = assertThrows(NoRecordsFoundException.class,
				() -> when(truckerMaintenanceService.addDrayageCustomerAndRemoveLink(drayageCustomerInfo,drayageId, header)));
		assertEquals(exception4.getMessage(),"No record found for this customer id :"+drayageCustomerInfo.getCustomerId());
		when(customerInfoRepository.existsById(Mockito.anyLong())).thenReturn(true);
		drayageCustomerInfo.setDrayageId(null);
		drayageCustomerInfo.setCustomerName(null);
		NoRecordsFoundException exception5 = assertThrows(NoRecordsFoundException.class,
				() -> when(truckerMaintenanceService.addDrayageCustomer(drayageCustomerInfo, header,"N")));
		assertEquals(exception5.getMessage(),"Customer Name can't be null");
		NoRecordsFoundException exception6 = assertThrows(NoRecordsFoundException.class,
				() -> when(truckerMaintenanceService.addDrayageCustomerAndRemoveLink(drayageCustomerInfo,drayageId, header)));
		assertEquals(exception6.getMessage(),"Customer Name can't be null");

		drayageCustomerInfo.setCustomerName(customerName);
		drayageCustomerInfo.setCustomerNumber(null);
		NoRecordsFoundException exception7 = assertThrows(NoRecordsFoundException.class,
			() -> when(truckerMaintenanceService.addDrayageCustomer(drayageCustomerInfo, header,"N")));
		assertEquals(exception7.getMessage(),"Customer Number can't be null");
		NoRecordsFoundException exception8 = assertThrows(NoRecordsFoundException.class,
				() -> when(truckerMaintenanceService.addDrayageCustomerAndRemoveLink(drayageCustomerInfo,drayageId, header)));
			assertEquals(exception8.getMessage(),"Customer Number can't be null");

	 }

	@Test
	void testRecordNotAddedException() {
		when(customerInfoRepository.existsById(Mockito.anyLong())).thenReturn(true);
		when(drayageCustomerInfoRepository.existsByCustomerId(Mockito.anyLong())).thenReturn(false);
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> when(truckerMaintenanceService.addDrayageCustomer(drayageCustomerInfo, header,"N")));
		assertEquals(exception.getMessage(),"Record Not added to Database");
		when(drayageSCACRepository.existsById(Mockito.any())).thenReturn(true);
		drayageCustomerInfo.setDrayageId(null);
		RecordNotAddedException exception2 = assertThrows(RecordNotAddedException.class,
				() -> when(truckerMaintenanceService.addDrayageCustomerAndRemoveLink(drayageCustomerInfo,drayageId, header)));
		assertEquals(exception2.getMessage(),"Record Not added to Database");
	}

	@Test
	void testRecordAlreadyExistsException() {
		when(customerInfoRepository.existsById(Mockito.anyLong())).thenReturn(true);
		when(drayageCustomerInfoRepository.existsByCustomerId(Mockito.anyLong())).thenReturn(true);
		RecordAlreadyExistsException exception = assertThrows(RecordAlreadyExistsException.class,
				() -> when(truckerMaintenanceService.addDrayageCustomer(drayageCustomerInfo, header,"N")));
		assertEquals(exception.getMessage(),"Association already exist for this CustomerId");
		drayageCustomerInfo.setDrayageId(null);
		when(drayageSCACRepository.existsById(Mockito.any())).thenReturn(true);
		RecordAlreadyExistsException exception2 = assertThrows(RecordAlreadyExistsException.class,
				() -> when(truckerMaintenanceService.addDrayageCustomerAndRemoveLink(drayageCustomerInfo,drayageId, header)));
		assertEquals(exception2.getMessage(),"Association already exist for this CustomerId");
	}

	@Test
	void testRecordAlreadyExistsExceptionForAddCompany() {
		when(drayageSCACRepository.existsById(Mockito.anyString())).thenReturn(true);
		when(drayageCompanyRepository.existsByDrayageId(Mockito.anyString())).thenReturn(true);
		RecordAlreadyExistsException exception = assertThrows(RecordAlreadyExistsException.class,
				() -> when(truckerMaintenanceService.addDrayageCompany(drayageCompany, header)));
		assertEquals(exception.getMessage(),"Association already exist");
	}
}
