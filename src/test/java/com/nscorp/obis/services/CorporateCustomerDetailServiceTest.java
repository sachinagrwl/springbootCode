package com.nscorp.obis.services;

import static org.junit.Assert.assertNotNull;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.nscorp.obis.domain.CorporateCustomer;
import com.nscorp.obis.domain.CorporateCustomerDetail;
import com.nscorp.obis.domain.Customer;
import com.nscorp.obis.dto.CorporateCustomerDetailDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.repository.CorporateCustomerDetailRepository;
import com.nscorp.obis.repository.CorporateCustomerRepository;
import com.nscorp.obis.repository.CustomerRepository;

public class CorporateCustomerDetailServiceTest {

	@InjectMocks
	CorporateCustomerDetailServiceImpl corporateCustomerDetailService;

	@Mock
	CorporateCustomerDetailRepository corporateCustomerDetailRepo;

	@Mock
	CustomerRepository customerRepo;

	@Mock
	CorporateCustomerRepository corpCustRepo;

	CorporateCustomerDetail corporateCustomerDetail;
	CorporateCustomerDetail corporateCustomerDetail1;
	CorporateCustomer corpCustomer;
	CorporateCustomerDetailDTO corporateCustomerDetailDto;
	List<CorporateCustomerDetailDTO> corporateCustomerDetailDtoList;
	List<CorporateCustomerDetail> corporateCustomerDetailList;
	List<Customer> customer;
	Customer customers;
	Map<String, String> header;
	Long corpCustID = 1234L;
	String cropCust6 = "654788";

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
		corporateCustomerDetailList = new ArrayList<>();
		corporateCustomerDetail = new CorporateCustomerDetail();
		corporateCustomerDetail1 = new CorporateCustomerDetail();
		corporateCustomerDetailDto = new CorporateCustomerDetailDTO();
		customer = new ArrayList<>();
		customers = new Customer();
		corpCustomer = new CorporateCustomer();
		corporateCustomerDetail.setCorpCustId(121L);
		corporateCustomerDetail.setCorpCust6("123");
		corporateCustomerDetail1.setCorpCustId(122L);
		corporateCustomerDetail1.setCorpCust6("12");
		corporateCustomerDetailDto.setCorpCustId(927756874L);
		corporateCustomerDetailDto.setCorpCust6("123");
		corporateCustomerDetailList.add(corporateCustomerDetail);
		corporateCustomerDetailList.add(corporateCustomerDetail1);
		corpCustomer.setCorporateCustomerId(2442L);
		customers.setCustomerNumber("3242");
		customer.add(customers);
		header = new HashMap<>();
		header.put("userid", "test");
		header.put("extensionschema", "test");

	}

	@Test
	void testGetCorporateCustomerDetail() {
		when(customerRepo.findByCustomerNumber(Mockito.any())).thenReturn(customer);
		List<CorporateCustomerDetail> result = corporateCustomerDetailService.getCorporateCustomerDetails(Long.valueOf(0),cropCust6);
		when(corporateCustomerDetailRepo.existsByCorpCustId(Mockito.any())).thenReturn(true);
		result = corporateCustomerDetailService.getCorporateCustomerDetails(Long.valueOf(1),cropCust6);
		when(corporateCustomerDetailRepo.findByCorpCustId(Mockito.any())).thenReturn(corporateCustomerDetailList);
		result = corporateCustomerDetailService.getCorporateCustomerDetails(Long.valueOf(1),null);
		result = corporateCustomerDetailService.getCorporateCustomerDetails(Long.valueOf(0),null);
	}

	@Test
	void testNoRecordsFoundException() {
		when(customerRepo.findByCustomerNumber(Mockito.any())).thenReturn(Collections.emptyList());
		assertThrows(NoRecordsFoundException.class,
				() -> corporateCustomerDetailService.getCorporateCustomerDetails(null, cropCust6));

		when(corporateCustomerDetailRepo.existsByCorpCustId(Mockito.any())).thenReturn(false);
		assertThrows(NoRecordsFoundException.class,
				() -> corporateCustomerDetailService.getCorporateCustomerDetails(Long.valueOf(1), null));

		when(corporateCustomerDetailRepo.findByCorpCustId(Mockito.any())).thenReturn(Collections.emptyList());
		assertThrows(NoRecordsFoundException.class,
				() -> corporateCustomerDetailService.deleteCorpCustDetail(corporateCustomerDetail));

		when(corporateCustomerDetailRepo.findByCorpCustId(Mockito.any())).thenReturn(corporateCustomerDetailList);
		when(corporateCustomerDetailRepo.existsByCorpCustId(Mockito.any())).thenReturn(false);
		assertThrows(NoRecordsFoundException.class,
				() -> corporateCustomerDetailService.deleteCorpCustDetail(corporateCustomerDetail));

		corporateCustomerDetail.setCorpCustId(null);
		corporateCustomerDetail.setCorpCust6("123");
		corporateCustomerDetailList.add(corporateCustomerDetail);
		when(corporateCustomerDetailRepo.existsByCorpCust6(Mockito.any())).thenReturn(false);
		assertThrows(NoRecordsFoundException.class,
				() -> corporateCustomerDetailService.deleteCorpCustDetail(corporateCustomerDetail));

		corporateCustomerDetail.setCorpCust6(null);
		corporateCustomerDetailList.add(corporateCustomerDetail);
		when(corporateCustomerDetailRepo.findByCorpCustIdAndCorpCust6(Mockito.any(), Mockito.any()))
				.thenReturn(null);
		assertThrows(NoRecordsFoundException.class,
				() -> corporateCustomerDetailService.deleteCorpCustDetail(corporateCustomerDetail));

		when(customerRepo.findByCustomerNumber(Mockito.any())).thenReturn(Collections.emptyList());
        assertThrows(NoRecordsFoundException.class,
		() -> corporateCustomerDetailService.addPrimary6(corporateCustomerDetail, header));
	}

	@Test
	void testDeleteCorpCustDtl() {
		when(corporateCustomerDetailRepo.findByCorpCustId(Mockito.any())).thenReturn(corporateCustomerDetailList);
		when(corporateCustomerDetailRepo.existsByCorpCustId(Mockito.any())).thenReturn(true);
		when(corporateCustomerDetailRepo.existsByCorpCust6(Mockito.any())).thenReturn(true);
		when(corporateCustomerDetailRepo.findByCorpCustIdAndCorpCust6(Mockito.any(), Mockito.any()))
				.thenReturn(corporateCustomerDetail);
		corporateCustomerDetail.setCorpCustId(342352L);
		corporateCustomerDetail.setCorpCust6("123");
		when(corpCustRepo.findByCorporateCustomerId(Mockito.any())).thenReturn(corpCustomer);
		corporateCustomerDetailRepo.deleteByCorpCustIdAndCorpCust6(Mockito.any(),Mockito.any());
		CorporateCustomerDetail deleteCorpCust = corporateCustomerDetailService.deleteCorpCustDetail(corporateCustomerDetail);
	}

	@Test
	void testAddPrimary6() {
		when(customerRepo.findByCustomerNumber(Mockito.any())).thenReturn(customer);
		when(corporateCustomerDetailRepo.existsByCorpCust6(Mockito.any())).thenReturn(false);
		when(corporateCustomerDetailRepo.save(Mockito.any())).thenReturn(corporateCustomerDetail);
//		when(corporateCustomerDetailRepo.existsByCorpCust6(Mockito.any())).thenReturn(true);
//		when(corporateCustomerDetailRepo.inOnlyTest(Mockito.any(),Mockito.any(), Mockito.any())).thenReturn((short) 1);
//		CorporateCustomerDetail code = corporateCustomerDetailRepo.save(corporateCustomerDetail);
		CorporateCustomerDetail CustomerDtlAdded = corporateCustomerDetailService.addPrimary6(corporateCustomerDetail,
				header);
		assertEquals(CustomerDtlAdded, corporateCustomerDetail);
	}

	@Test
	void testAddRecordAlreadyExistsException() {
		when(customerRepo.findByCustomerNumber(Mockito.any())).thenReturn(customer);
		when(corporateCustomerDetailRepo.existsByCorpCust6(Mockito.any())).thenReturn(true);
		assertThrows(RecordAlreadyExistsException.class,
				() -> corporateCustomerDetailService.addPrimary6(corporateCustomerDetail, header));
	}
		
}
