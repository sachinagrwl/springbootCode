package com.nscorp.obis.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.domain.Specification;

import com.nscorp.obis.common.SpecificationGenerator;
import com.nscorp.obis.domain.CityState;
import com.nscorp.obis.domain.CustomerInfo;
import com.nscorp.obis.domain.DrayageCustomer;
import com.nscorp.obis.dto.CustomerInfoDTO;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.repository.CityStateRepository;
import com.nscorp.obis.repository.CustomerInfoRepository;
import com.nscorp.obis.repository.CustomerNicknameRepository;
import com.nscorp.obis.repository.DrayageCustomerRepository;
import com.nscorp.obis.response.data.PaginatedResponse;

public class CustomerInfoServiceTest {

	@Mock
	CustomerInfoRepository customerInfoRepository;

	@Mock
	SpecificationGenerator specificationGenerator;
	
	@Mock
	CityStateRepository cityStateRepo;

	@Mock
	CustomerNicknameRepository custNickNameRepo;

	@Mock
	DrayageCustomerRepository drayageRepo;

	@InjectMocks
	CustomerInfoServiceImpl customerInfoServiceImpl;

	CustomerInfo customerInfo;
	Map<String, String> header;
    Optional<CustomerInfo> optional;
	List<CustomerInfo> customerInfos;
	DrayageCustomer drayage;
	List<DrayageCustomer> drayages;
	Specification<CustomerInfo> specification;
	Long customerId;
	String customerName;
	String customerNumber;
	String[] sort={"customerId","asc"};
	
	CustomerInfoDTO custInfoDto;
	Map<String,String> headers;
	List<CityState> states;
	Page<CustomerInfo> page;
	Integer pageNumber;
	Integer pageSize;
	String fetchExpired ="Y";
	
	

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		customerInfo = new CustomerInfo();
		customerInfo.setAddress1("Delhi");
		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");
		customerId = 4245005430299L;
		customerName = "UPS FREIGHT";
		customerNumber = "S8013960706";
		customerInfo=new CustomerInfo();
		customerInfo.setCustomerId(customerId);
		customerInfo.setCustomerName(customerName);
		customerInfo.setCustomerNumber(customerNumber);
		customerInfo.setBillToCustomerId(customerId);
		customerInfo.setBillToCustomerNumber(customerNumber);
		customerInfos = Arrays.asList(customerInfo);
		optional=Optional.of(customerInfo);
		drayage=new DrayageCustomer();
		drayage.setDrayageId("TEST");
		drayages=Arrays.asList(drayage);
		specification = (Root<CustomerInfo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();
			return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
		};
		
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
		states=Arrays.asList(new CityState());
		page=new PageImpl<CustomerInfo>(customerInfos);
		pageNumber=0;
		pageSize=20;
	}

	@AfterEach
	void tearDown() {
		customerInfos = null;
		specification=null;

		custInfoDto=null;
		headers=null;
		customerInfo=null;
		optional=null;
	}

	@Test
	void testFetchCustomers() {
		when(specificationGenerator.customerInfoSpecification(customerId, customerName, customerNumber,null,fetchExpired)).thenReturn(specification);
		when(customerInfoRepository.findAll(Mockito.eq(specification),Mockito.any())).thenReturn(page);
		when(customerInfoRepository.findById(Mockito.anyLong())).thenReturn(optional);
		when(customerInfoRepository.existsByCustomerNumber(customerNumber)).thenReturn(true);
		when(drayageRepo.findByCustomerCustomerId(Mockito.anyLong())).thenReturn(drayages);
		PaginatedResponse<CustomerInfoDTO> infoDTOs=customerInfoServiceImpl.fetchCustomers(customerId, customerName, customerNumber,pageSize,pageNumber,sort,null,fetchExpired);
		assertNotNull(infoDTOs);
	}
	
	@Test
	void testFetchCustomersException() {
		customerInfos=new ArrayList<>();
		when(customerInfoRepository.existsByCustomerNumber(customerNumber)).thenReturn(true);
		when(specificationGenerator.customerInfoSpecification(customerId, customerName, customerNumber, null,fetchExpired)).thenReturn(specification);
		when(customerInfoRepository.findAll(Mockito.eq(specification),Mockito.any())).thenReturn(page);
		try {
			customerInfoServiceImpl.fetchCustomers(customerId, customerName, customerNumber, pageSize, pageNumber ,sort, null,fetchExpired);
		}
		catch(Exception err) {
			assertEquals(err.getMessage(), "No Records Found For Requested Combination");
		}
		
	}
	
	@Test
	void testUpdateCustomer() {
		when(customerInfoRepository.findById(Mockito.anyLong())).thenReturn(optional);
		when(customerInfoRepository.existsById(Mockito.anyLong())).thenReturn(true);
		when(customerInfoRepository.existsByCustomerNumber(Mockito.anyString())).thenReturn(true);
		when(customerInfoRepository.save(Mockito.any())).thenReturn(customerInfo);
		CustomerInfoDTO customerDTO=customerInfoServiceImpl.updateCustomer(custInfoDto, headers);
		assertEquals(customerDTO.getCustomerId(), custInfoDto.getCustomerId());
	}

	
	@Test
	void testUpdateCustomerException() {
		when(customerInfoRepository.existsByCustomerNumber(customerNumber)).thenReturn(true);
		when(customerInfoRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		assertThrows(NoRecordsFoundException.class,()->customerInfoServiceImpl.updateCustomer(custInfoDto, headers));
		custInfoDto.setExpiredDate(new Date(2022212L));
		when(customerInfoRepository.findById(Mockito.anyLong())).thenReturn(optional);
		assertThrows(InvalidDataException.class, ()->customerInfoServiceImpl.updateCustomer(custInfoDto, headers));
		custInfoDto.setExpiredDate(null);
		when(customerInfoRepository.findById(Mockito.anyLong())).thenReturn(optional);
		when(customerInfoRepository.existsById(Mockito.anyLong())).thenReturn(true);
		when(customerInfoRepository.existsByCustomerNumber(Mockito.anyString())).thenReturn(false);
		assertThrows(NoRecordsFoundException.class,()->customerInfoServiceImpl.updateCustomer(custInfoDto, headers));
		when(customerInfoRepository.existsById(Mockito.anyLong())).thenReturn(false);
		assertThrows(NoRecordsFoundException.class,()->customerInfoServiceImpl.updateCustomer(custInfoDto, headers));

		custInfoDto.setPrimeContact("test");
		customerInfo.setCustomerNumber("012345");
		custInfoDto.setCustomerNumber("012345");
		customerInfo.setBillToCustomerId(123456L);
		customerInfo.setBillToCustomerNumber("1234");
		optional=Optional.of(customerInfo);
		when(customerInfoRepository.findById(Mockito.anyLong())).thenReturn(optional);
		assertThrows(InvalidDataException.class,()->customerInfoServiceImpl.updateCustomer(custInfoDto, headers));
		custInfoDto.setState("VA");
		when(drayageRepo.findByCustomerCustomerId(Mockito.anyLong())).thenReturn(Arrays.asList());
		assertThrows(NoRecordsFoundException.class,()->customerInfoServiceImpl.updateCustomer(custInfoDto, headers));
		
	}
	
	
	@Test
	void testDeleteCustomer() {
		custInfoDto.setExpiredDate(new Date(System.currentTimeMillis()));
		when(customerInfoRepository.findById(Mockito.anyLong())).thenReturn(optional);
		when(customerInfoRepository.save(Mockito.any())).thenReturn(customerInfo);
		CustomerInfoDTO customerDTO=customerInfoServiceImpl.updateCustomer(custInfoDto, headers);
		assertEquals(customerDTO.getExpiredDate(), custInfoDto.getExpiredDate());
	}
	
	@Test
	void testRestoreCustomer() {
		customerInfo.setExpiredDate(new Date(System.currentTimeMillis()));
		optional=Optional.of(customerInfo);
		when(customerInfoRepository.findById(Mockito.anyLong())).thenReturn(optional);
		when(customerInfoRepository.save(Mockito.any())).thenReturn(customerInfo);
		CustomerInfoDTO customerDTO=customerInfoServiceImpl.updateCustomer(custInfoDto, headers);
		assertEquals(customerDTO.getCustomerId(), custInfoDto.getCustomerId());
	}
	
	

	@Test
	void testInsertCustomer() {
		customerInfo.setAddress1("Test Address");
		customerInfo.setAddress2("Test Address2");
		when(customerInfoRepository.save(Mockito.any())).thenReturn(customerInfo);
		when(customerInfoRepository.existsById(Mockito.anyLong())).thenReturn(true);
		when(customerInfoRepository.existsByCustomerNumber(Mockito.anyString())).thenReturn(true);
		CustomerInfo addedCustomer = customerInfoServiceImpl.addCustomer(customerInfo, header);
		assertEquals(addedCustomer, customerInfo);
		customerInfo.setState("TEST");
		when(cityStateRepo.findAllByStateAbbreviation(Mockito.anyString())).thenReturn(Arrays.asList());
		assertThrows(NoRecordsFoundException.class, ()->customerInfoServiceImpl.addCustomer(customerInfo, header));
		customerInfo.setState(null);
		customerInfo.setExpiredDate(Date.valueOf("2023-01-22"));
		assertThrows(InvalidDataException.class, ()->customerInfoServiceImpl.addCustomer(customerInfo, header));
		customerInfo.setExpiredDate(null);
		when(customerInfoRepository.existsById(Mockito.anyLong())).thenReturn(false);
		when(customerInfoRepository.existsByCustomerNumber(Mockito.anyString())).thenReturn(true);
		assertThrows(NoRecordsFoundException.class, ()->customerInfoServiceImpl.addCustomer(customerInfo, header));
		customerInfo.setBillToCustomerId(customerId);
		when(customerInfoRepository.existsById(Mockito.anyLong())).thenReturn(true);
		when(customerInfoRepository.existsByCustomerNumber(Mockito.anyString())).thenReturn(false);
		assertThrows(NoRecordsFoundException.class, ()->customerInfoServiceImpl.addCustomer(customerInfo, header));
		when(customerInfoRepository.save(Mockito.any())).thenReturn(null);
		when(customerInfoRepository.existsById(Mockito.anyLong())).thenReturn(true);
		when(customerInfoRepository.existsByCustomerNumber(Mockito.anyString())).thenReturn(true);
		assertThrows(RecordNotAddedException.class, ()->customerInfoServiceImpl.addCustomer(customerInfo, header));
	}

	@Test
	void testInvalidDataException() {
		customerInfo.setAddress1(null);
		InvalidDataException addexception1 = assertThrows(InvalidDataException.class,
				() -> when(customerInfoServiceImpl.addCustomer(customerInfo, header)));
		assertEquals("Customer Address should not be null", addexception1.getMessage());

	}

}
