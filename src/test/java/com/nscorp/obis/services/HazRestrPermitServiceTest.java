package com.nscorp.obis.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.*;

import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.repository.UnCdRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.nscorp.obis.domain.Customer;
import com.nscorp.obis.domain.HazRestrPermit;
import com.nscorp.obis.dto.HazRestrPermitDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordNotDeletedException;
import com.nscorp.obis.repository.CustomerRepository;
import com.nscorp.obis.repository.HazRestrPermitRepository;

public class HazRestrPermitServiceTest {

	@InjectMocks
	HazRestrPermitServiceImpl hazRestrPermitServiceImpl;

	@Mock
	HazRestrPermitRepository hazRestrPermitRepo;
	
	@Mock
	CustomerRepository customerRepo;

	@Mock
	UnCdRepository unCdRepository;

	NoRecordsFoundException exception;
	Customer customer;
	HazRestrPermitDTO hazRestrPermitDto;
	List<HazRestrPermitDTO> hazRestrPermitDtoList;
	HazRestrPermit hazRestrPermit;
	List<HazRestrPermit> hazRestrPermitList;
	Map<String, String> header;
	
	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		customer= new Customer();
		customer.setCustomerName("ABC");
		hazRestrPermit= new HazRestrPermit();
		hazRestrPermitDto = new HazRestrPermitDTO();
		hazRestrPermitDtoList = new ArrayList<>();
		hazRestrPermitList = new ArrayList<>();
		hazRestrPermit.setUversion("!");
		hazRestrPermit.setUnCd("ABC");
		hazRestrPermit.setPermitNr("123");
		hazRestrPermit.setCustomerId(1234L);		
		hazRestrPermit.setCustomer(customer);
		
		hazRestrPermitDto.setUnCd("ABC");
		hazRestrPermitDto.setPermitNr("123");
		hazRestrPermitDto.setCustomerId(1234L);
		
		hazRestrPermitList.add(hazRestrPermit);
		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");
	}
	
	@AfterEach
	void tearDown() throws Exception {
		customer = null;
		hazRestrPermit=null;
		hazRestrPermitDto = null;
		hazRestrPermitList=null;
		hazRestrPermitDtoList=null;
		
	}
	
	@Test
	void testGetHazRestrPermitAllTables() {
		when(hazRestrPermitRepo.findAll()).thenReturn(hazRestrPermitList);
		when(customerRepo.findByCustomerId(Mockito.any())).thenReturn(customer);
		List<HazRestrPermit> allTable = hazRestrPermitServiceImpl.getHazRestrPermit();
		assertEquals(allTable, hazRestrPermitList);
		
	}

	@Test
	void testGetHazRestrPermitNoRecordFoundException() {
		when(hazRestrPermitRepo.findAll()).thenReturn(Collections.emptyList());
		exception = assertThrows(NoRecordsFoundException.class,
				() -> when(hazRestrPermitServiceImpl.getHazRestrPermit()));
		assertEquals("No Records found", exception.getMessage());
	}
	
	@Test
	void testDeleteHazRestrPermit() {

		when(hazRestrPermitRepo.existsByCustomerIdAndUnCd(any(),any())).thenReturn(true);
		when(hazRestrPermitRepo.findByCustomerIdAndUnCd(any(),any())).thenReturn(hazRestrPermit);
		HazRestrPermit delete = hazRestrPermitServiceImpl.deleteHazRestrPermit(hazRestrPermit);
		assertEquals(delete, hazRestrPermit);

	}

	@Test
	void testDeleteHazRestrPermitException() {
		when(hazRestrPermitRepo.existsByCustomerIdAndUnCd(any(),any())).thenReturn(false);
		RecordNotDeletedException exception = assertThrows(RecordNotDeletedException.class,
				() -> hazRestrPermitServiceImpl.deleteHazRestrPermit(hazRestrPermit));
		assertEquals("Record Not Found!", exception.getMessage());
	}
	@Test
	void testAddHazRestrPermit(){
		hazRestrPermit.setCustomer(customer);
		when(unCdRepository.existsByUnCd(Mockito.any())).thenReturn(true);
		when(customerRepo.existsByCustomerName(Mockito.any())).thenReturn(true);
		when(customerRepo.existsByCustomerId(Mockito.any())).thenReturn(true);
		when(hazRestrPermitRepo.save(Mockito.any())).thenReturn(hazRestrPermit);
		when(customerRepo.findByCustomerId(Mockito.any())).thenReturn(customer);
		HazRestrPermit response = hazRestrPermitServiceImpl.addHazRestrPermit(hazRestrPermit,header);
	}

	@Test
	void testAddHazRestrPermitNoRecordFound(){
		when(unCdRepository.existsByUnCd(Mockito.any())).thenReturn(false);
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> hazRestrPermitServiceImpl.addHazRestrPermit(hazRestrPermit,header));

		when(unCdRepository.existsByUnCd(Mockito.any())).thenReturn(true);
		hazRestrPermit.setCustomer(null);
		InvalidDataException exception1 = assertThrows(InvalidDataException.class,
				() -> hazRestrPermitServiceImpl.addHazRestrPermit(hazRestrPermit,header));

		when(unCdRepository.existsByUnCd(Mockito.any())).thenReturn(true);
		hazRestrPermit.setCustomer(customer);
		when(customerRepo.existsByCustomerName(Mockito.any())).thenReturn(false);
		exception = assertThrows(NoRecordsFoundException.class,
				() -> hazRestrPermitServiceImpl.addHazRestrPermit(hazRestrPermit,header));

		when(customerRepo.existsByCustomerName(Mockito.any())).thenReturn(true);
		when(customerRepo.existsByCustomerId(Mockito.any())).thenReturn(false);
		exception = assertThrows(NoRecordsFoundException.class,
				() -> hazRestrPermitServiceImpl.addHazRestrPermit(hazRestrPermit,header));
	}
	@Test
	void testUpdateHazRestrPermit(){
		when(hazRestrPermitRepo.existsByCustomerIdAndUnCd(Mockito.any(),Mockito.any())).thenReturn(true);
		when(unCdRepository.existsByUnCd(Mockito.any())).thenReturn(true);
		when(customerRepo.existsByCustomerId(Mockito.any())).thenReturn(true);
		when(hazRestrPermitRepo.save(Mockito.any())).thenReturn(hazRestrPermit);
		when(customerRepo.findByCustomerId(Mockito.any())).thenReturn(customer);
		HazRestrPermit response = hazRestrPermitServiceImpl.updateHazRestrPermit(hazRestrPermit,header);
		hazRestrPermit.setUversion(null);
		response = hazRestrPermitServiceImpl.updateHazRestrPermit(hazRestrPermit,header);
	}

	@Test
	void testUpdateHazRestrPermitNoRecordFound(){
		when(hazRestrPermitRepo.existsByCustomerIdAndUnCd(Mockito.any(),Mockito.any())).thenReturn(false);
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> hazRestrPermitServiceImpl.updateHazRestrPermit(hazRestrPermit,header));

		when(hazRestrPermitRepo.existsByCustomerIdAndUnCd(Mockito.any(),Mockito.any())).thenReturn(true);
		when(unCdRepository.existsByUnCd(Mockito.any())).thenReturn(false);
		exception = assertThrows(NoRecordsFoundException.class,
				() -> hazRestrPermitServiceImpl.updateHazRestrPermit(hazRestrPermit,header));

		when(hazRestrPermitRepo.existsByCustomerIdAndUnCd(Mockito.any(),Mockito.any())).thenReturn(true);
		when(unCdRepository.existsByUnCd(Mockito.any())).thenReturn(true);
		when(customerRepo.existsByCustomerId(Mockito.any())).thenReturn(false);
		exception = assertThrows(NoRecordsFoundException.class,
				() -> hazRestrPermitServiceImpl.updateHazRestrPermit(hazRestrPermit,header));
	}
}
