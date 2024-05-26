package com.nscorp.obis.services;

import com.nscorp.obis.domain.CorporateCustomer;
import com.nscorp.obis.domain.ShiplineCustomer;
import com.nscorp.obis.dto.ShiplineCustomerDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.RecordNotDeletedException;
import com.nscorp.obis.repository.CorporateCustomerRepository;
import com.nscorp.obis.repository.ShiplineCustomerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class ShiplineCustomerServiceTest {
	
	@InjectMocks
	ShiplineCustomerServiceImpl steamshipCustomerService;

	@Mock
	ShiplineCustomerRepository steamshipCustomerRepository;
	
	@Mock
	CorporateCustomerRepository corporateCustomerRepo;
	
	ShiplineCustomerDTO steamshipCustomerDto;
	ShiplineCustomer steamshipCustomer;
	CorporateCustomer corporateCustomer;
	List<ShiplineCustomer> steamshipCustomerList;
	List<ShiplineCustomerDTO> steamshipCustomerDtoList;

	ShiplineCustomer addedCorpCust;
	
	Map<String, String> header;

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		
		MockitoAnnotations.initMocks(this);
		steamshipCustomer = new ShiplineCustomer();
		steamshipCustomerDto = new ShiplineCustomerDTO();
		steamshipCustomerDtoList = new ArrayList<>();
		steamshipCustomerList = new ArrayList<>();
		
		steamshipCustomerDto.setShiplineNumber("123AB");
		steamshipCustomerDto.setCustomerId((long) 123000);
		steamshipCustomerDto.setDescription("TEST");
		
		steamshipCustomer.setShiplineNumber("123AB");
		steamshipCustomer.setCustomerId((long) 123000);
		steamshipCustomer.setDescription("TEST");
		steamshipCustomer.setCustomerCustomer(corporateCustomer);
		
		steamshipCustomerList.add(steamshipCustomer);
		steamshipCustomerDtoList.add(steamshipCustomerDto);
		
		header = new HashMap<String,String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");
		
	}

	@AfterEach
	void tearDown() throws Exception {
		
		steamshipCustomer = null;
		steamshipCustomerDto = null;
		steamshipCustomerDtoList = null;
		steamshipCustomerList = null;
		corporateCustomer = null;
		
	}

	@Test
	void testGetAllSteamshipCustomers() {
		when(steamshipCustomerRepository.findAll()).thenReturn(steamshipCustomerList);
		List<ShiplineCustomer> allSteamshipCustomers = steamshipCustomerService.getAllSteamshipCustomers();
		assertEquals(allSteamshipCustomers,steamshipCustomerList);
	}
	
	@Test
	void testGetAllSteamshipCustomersException() {
		
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(steamshipCustomerService.getAllSteamshipCustomers()));
		assertEquals("No Records Found!", exception.getMessage());
		
	}

	@Test
	void testAddSteamshipCustomer() {
		
				when(corporateCustomerRepo.existsByCorporateLongNameAndCustomerId(Mockito.any(), Mockito.any()))
							.thenReturn(true);
				when(steamshipCustomerRepository.save(Mockito.any()))
							.thenReturn(steamshipCustomer);
				addedCorpCust = steamshipCustomerService
							.addSteamshipCustomer(steamshipCustomer, header);
				assertEquals(addedCorpCust, steamshipCustomer);
	}
	
	@Test
	void testAddSteamshipCustomerRecordAlreadyExistsException() {

		when(steamshipCustomerRepository.existsByShiplineNumber(Mockito.any())).thenReturn(true);

		RecordAlreadyExistsException exception = assertThrows(RecordAlreadyExistsException.class,
				() -> when(steamshipCustomerService
						.addSteamshipCustomer(steamshipCustomer, header)));
		assertEquals("Record with Shipline Number: 123AB Already Exists!", exception.getMessage());
		
	}
	
	@Test
	void testAddSteamshipCustomerRecordNotAddedException() {
		
		ShiplineCustomer shipCustomer = new ShiplineCustomer();
		shipCustomer = null;
		
		when(corporateCustomerRepo.existsByCorporateLongNameAndCustomerId(Mockito.any(), Mockito.any()))
								.thenReturn(true);
		when(steamshipCustomerRepository.save(Mockito.any()))
								.thenReturn(shipCustomer);
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> when(steamshipCustomerService
						.addSteamshipCustomer(steamshipCustomer, header)));
		assertEquals("Record with Shipline Number: 123AB Not Added !", exception.getMessage());
		
	}
	
	@Test
	void testAddSteamshipCustomerException() {
		when(steamshipCustomerRepository.existsByShiplineNumber(Mockito.any())).thenReturn(false);
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(steamshipCustomerService.addSteamshipCustomer(steamshipCustomer, header)));
		assertEquals("No Record found for Customer Name: TEST", exception.getMessage());
		
	}
	
	@Test
	void testDeleteTareWeight() {
		
		when(steamshipCustomerRepository.existsByShiplineNumber(Mockito.any())).thenReturn(true);
		steamshipCustomerService.deleteCustomer(steamshipCustomer);
		
	}
	
	@Test
	void testDeleteCustomer() throws RecordNotDeletedException {

		RecordNotDeletedException exception = assertThrows(RecordNotDeletedException.class,
				() -> steamshipCustomerService.deleteCustomer(steamshipCustomer));
		assertEquals("123AB and TEST Record Not Found!", exception.getMessage());
	}

}
