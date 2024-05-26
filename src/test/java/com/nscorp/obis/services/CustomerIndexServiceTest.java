package com.nscorp.obis.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import com.nscorp.obis.common.SpecificationGenerator;
import com.nscorp.obis.domain.CustomerIndex;
import com.nscorp.obis.domain.SortFilter;
import com.nscorp.obis.domain.VoiceNotify;
import com.nscorp.obis.dto.CustomerIndexDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.repository.CustomerIndexRepository;
import com.nscorp.obis.repository.VoiceNotifyRepository;

public class CustomerIndexServiceTest {
	
	@Mock
	CustomerIndexRepository customerIndexRepository;
	
	@Mock
	SpecificationGenerator specification;
	
	@Mock
	VoiceNotifyRepository voiceNotifyRepo;
	
	@InjectMocks
	CustomerIndexServiceImpl customerIndexServiceImpl;
	
	@Mock
	EntityManager entityManager;
	
	Specification<CustomerIndex> specs;
	
	CustomerIndex customerIndex;
	
	CustomerIndex customerIndex2;
	
	VoiceNotify voiceNotify;
	
	Slice<CustomerIndex> slice;
	Slice<CustomerIndex> slice2;
	Slice<CustomerIndex> uniqueSlice;
	Slice<CustomerIndex> latestSlice;
	List<CustomerIndex> customerIndexs;
	
	String customerName = "UPS FREIGHT";
	String customerNumber = "424";
	String city = "city";
	String state = "state";
	String uniqueGroup = "n";
	String latest = "n";
	String[] sort = { "customerName", "asc" };
	String[] latestSort = { "updateDateTime", "desc" };
	String[] defaultSort = { "customerName,asc", "customerNumber,asc", "city,asc", "state,asc" };
	Long corporateId=1234L;
	Long notifyQueueId = 12345L;
	String fetchExpired="Y";
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		specs = (Root<CustomerIndex> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();
			return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
		};
		voiceNotify = new VoiceNotify();
		customerIndexs=new ArrayList<>();
		customerIndex=new CustomerIndex();
		customerIndex.setCustomerId(1234L);
		customerIndex.setCustomerName(customerName);
		customerIndex.setCustomerNumber(customerNumber);
		customerIndex2=new CustomerIndex();
		customerIndex2.setCustomerId(1234L);
		customerIndex2.setCustomerName(customerName);
		customerIndex2.setCustomerNumber(customerNumber);
		customerIndexs.add(customerIndex2);
		slice=new SliceImpl<>(customerIndexs,PageRequest.of(0, 5000, Sort.by(SortFilter.sortOrder(sort))),true);
		slice2=new SliceImpl<>(customerIndexs,PageRequest.of(1, 5000, Sort.by(SortFilter.sortOrder(sort))),false);
		latestSlice = new SliceImpl<>(customerIndexs,PageRequest.of(0, 1, Sort.by(SortFilter.sortOrder(latestSort))),false);
		uniqueSlice = new SliceImpl<>(customerIndexs,PageRequest.of(0, 100, Sort.by(SortFilter.sortOrder(sort))),false);
	}
	
	@AfterEach
	void tearDown() {
		customerIndexs=null;
	}
	

	@Test
	void testGetCustomers() {
		doNothing().when(entityManager).clear();
		when(specification.customerIndexSpecification(customerName, customerNumber ,corporateId,fetchExpired)).thenReturn(specs);
		when(customerIndexRepository.findAll(specs,PageRequest.of(0, 5000, Sort.by(SortFilter.sortOrder(sort))))).thenReturn(slice);
		when(customerIndexRepository.findAll(specs,PageRequest.of(1, 5000, Sort.by(SortFilter.sortOrder(sort))))).thenReturn(slice2);
		List<CustomerIndexDTO> customerIndexDTOs =customerIndexServiceImpl.getCustomers(customerName, customerNumber, city, state, uniqueGroup,corporateId, latest, sort,fetchExpired);
		assertEquals(customerIndexDTOs.size(),2);
	}
	@Test
	void testGetUniqueCustomers() {
		uniqueGroup ="y";
		doNothing().when(entityManager).clear();
        when(specification.customerIndexUniqueSpecification(customerName, customerNumber, corporateId,fetchExpired)).thenReturn(specs);
		
		when(customerIndexRepository.findAll(specs,PageRequest.of(0, 100, Sort.by(SortFilter.sortOrder(defaultSort))))).thenReturn(uniqueSlice);
		List<CustomerIndexDTO> customerIndexDTOs =customerIndexServiceImpl.getCustomers(customerName, customerNumber, city, state, uniqueGroup,corporateId, latest, sort,fetchExpired);
		
	}
	@Test
	void testGetLatestCustomers() {
		latest ="y";
		doNothing().when(entityManager).clear();
		when(specification.customerIndexLatestSpecification(customerName, customerNumber,city,state,corporateId,fetchExpired)).thenReturn(specs);
		
		when(customerIndexRepository.findAll(specs,PageRequest.of(0, 1, Sort.by(SortFilter.sortOrder(latestSort))))).thenReturn(latestSlice);
		
		List<CustomerIndexDTO> customerIndexDTOs =customerIndexServiceImpl.getCustomers(customerName, customerNumber, city, state, uniqueGroup,corporateId, latest, sort,fetchExpired);
		
	}
	@Test
	void testGetCustomerException() {
		slice=new SliceImpl<>(new ArrayList<>(),PageRequest.of(0, 5000, Sort.by(SortFilter.sortOrder(sort))),false);
		doNothing().when(entityManager).clear();
		when(specification.customerIndexSpecification(customerName, customerNumber,corporateId,fetchExpired)).thenReturn(specs);
		when(customerIndexRepository.findAll(specs,PageRequest.of(0, 5000, Sort.by(SortFilter.sortOrder(sort))))).thenReturn(slice);
		assertThrows(NoRecordsFoundException.class, ()->customerIndexServiceImpl.getCustomers(customerName, customerNumber, city, state, uniqueGroup,corporateId, latest, sort,fetchExpired));
	}
	
	@Test
	void testGetCustomerIndex() {
		when(voiceNotifyRepo.findByNotifyQueueId(notifyQueueId)).thenReturn(voiceNotify);
		when(customerIndexRepository.findByCustomerId(Mockito.any())).thenReturn(customerIndex);
		CustomerIndex custIndex = customerIndexServiceImpl.getCustIndex(notifyQueueId);
		assertEquals(custIndex, customerIndex);
	}
	
	@Test
	void testGetCustomerIndexException() {
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(customerIndexServiceImpl.getCustIndex(notifyQueueId)));
		assertEquals("No Records Found in Voice Notify with this Notify Queue Id: 12345", exception.getMessage());
		
		
		CustomerIndex custIndex = new CustomerIndex();
		custIndex = null;
		when(voiceNotifyRepo.findByNotifyQueueId(notifyQueueId)).thenReturn(voiceNotify);
		when(customerIndexRepository.findByCustomerId(Mockito.any())).thenReturn(custIndex);
		NoRecordsFoundException exception1 = assertThrows(NoRecordsFoundException.class,
				() -> when(customerIndexServiceImpl.getCustIndex(notifyQueueId)));
		assertEquals("No Records Found in Customer Index with this Notify Cust Id: null", exception1.getMessage());
	}
	
}

