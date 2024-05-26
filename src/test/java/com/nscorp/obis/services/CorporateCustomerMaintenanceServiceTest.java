package com.nscorp.obis.services;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.nscorp.obis.domain.CorporateCustomer;
import com.nscorp.obis.domain.CorporateCustomerDetail;
import com.nscorp.obis.domain.TerminalInd;
import com.nscorp.obis.dto.CorporateCustomerDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.RecordNotDeletedException;
import com.nscorp.obis.repository.CorporateCustomerDetailRepository;
import com.nscorp.obis.repository.CorporateCustomerRepository;
import com.nscorp.obis.repository.CustomerRepository;
import com.nscorp.obis.repository.InterChangePartyRepository;

class CorporateCustomerMaintenanceServiceTest {
	@Mock
	CorporateCustomerRepository corporateCustomerMaintenanceRepository;
	
	@Mock
	CorporateCustomerDetailRepository corporateCustomerDetailRepository;

	@Mock
	InterChangePartyRepository interChangePartyRepo;
	
	@Mock
	CustomerRepository customerRepo;
	@InjectMocks
	CorporateCustomerMaintenanceServiceImpl corporateCustomerMaintenanceService;

	CorporateCustomer corporateCustomer;
	List<CorporateCustomer> corporateCustomerList;
	List<CorporateCustomerDetail> corporateCustomerDetail;
	
	CorporateCustomerDTO corporateCustomerDto;
	List<CorporateCustomerDTO> corporateCustomerDtoList;

	Map<String, String> header;

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		corporateCustomer = new CorporateCustomer();
		corporateCustomerList = new ArrayList<>();
		corporateCustomerDto = new CorporateCustomerDTO();
		corporateCustomerDtoList = new ArrayList<>();
		
		CorporateCustomerDetail corporateCustomerDetailObj = new CorporateCustomerDetail();
		corporateCustomerDetailObj.setCorpCust6("543452");
		corporateCustomerDetailObj.setCorpCustId(12345L);
		corporateCustomerDetailObj.setUversion("!");
		List<CorporateCustomerDetail> corporateCustomerDetail = new ArrayList<>();
		
		corporateCustomerDetail.add(corporateCustomerDetailObj);

		corporateCustomer.setCorporateCustomerId(123456L);
		corporateCustomer.setCorporateLongName("KLINE-THE RAILBRIDGE CORP");
		corporateCustomer.setCorporateShortName("KLNC");
		corporateCustomer.setCustomerId(12839942823L);
		corporateCustomer.setIcghCd("TEST");
		corporateCustomer.setPrimaryLob("I");
		corporateCustomer.setSecondaryLob("OCN CARR");
		corporateCustomer.setScac("TEST");
		corporateCustomer.setTerminalFeedEnabled("TEST");
		corporateCustomer.setAccountManager("TEST");
		corporateCustomer.setCorporateCustomerDetail(corporateCustomerDetail);
		corporateCustomerList.add(corporateCustomer);

		corporateCustomerDto.setCorporateCustomerId(123456L);
		corporateCustomerDto.setCorporateLongName("KLINE-THE RAILBRIDGE CORP");
		corporateCustomerDto.setCorporateShortName("KLNC");
		corporateCustomerDto.setCustomerId(12839942823L);
		corporateCustomerDto.setIcghCd("TEST");
		corporateCustomerDto.setPrimaryLob("I");
		corporateCustomerDto.setSecondaryLob("OCN CARR");
		corporateCustomerDto.setScac("TEST");
		corporateCustomerDto.setTerminalFeedEnabled("TEST");
		corporateCustomerDto.setAccountManager("TEST");
		corporateCustomerDto.setCorporateCustomerDetail(corporateCustomerDetail);
		
		
		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");

	}

	@AfterEach
	void tearDown() throws Exception {

		corporateCustomerDto = null;
		corporateCustomerDtoList = null;
		corporateCustomer = null;
		corporateCustomerList = null;
	}

	@Test
	void testUpdateCorporateCustomerData() {
		when(corporateCustomerMaintenanceRepository.findByCorporateCustomerId(Mockito.any()))
				.thenReturn(corporateCustomer);
		when(corporateCustomerMaintenanceRepository.existsByCorporateCustomerId(Mockito.any())).thenReturn(true);
		when(corporateCustomerMaintenanceRepository.existsByCorporateLongName(Mockito.any())
				&& corporateCustomerMaintenanceRepository.existsByCorporateShortName(Mockito.any())).thenReturn(false);
		when(interChangePartyRepo.existsByIchgCode(Mockito.any())).thenReturn(true);
		when(corporateCustomerMaintenanceRepository.save(corporateCustomer)).thenReturn(corporateCustomer);
		CorporateCustomer corpoCustomer = corporateCustomerMaintenanceService.updateCorporateCustomer(corporateCustomer,
				header);
		assertEquals(corpoCustomer.getCorporateCustomerId(), corporateCustomer.getCorporateCustomerId());
	}
	
	@Test
	void testAddCorporateCustomerData() {
		when(corporateCustomerMaintenanceRepository.existsByCorporateLongName(Mockito.any())
				&& corporateCustomerMaintenanceRepository.existsByCorporateShortName(Mockito.any())).thenReturn(false);
		when(customerRepo.existsByCustomerId(Mockito.any())).thenReturn(true);
		when(interChangePartyRepo.existsByIchgCode(Mockito.any())).thenReturn(true);
		when(corporateCustomerMaintenanceRepository.save(corporateCustomer)).thenReturn(corporateCustomer);
		when(corporateCustomerDetailRepository.saveAll(corporateCustomerDetail)).thenReturn(corporateCustomerDetail);
		CorporateCustomer corpoCustomer = corporateCustomerMaintenanceService.addCorporateCustomer(corporateCustomer,
				header);
		assertEquals(corpoCustomer.getCorporateCustomerId(), corporateCustomer.getCorporateCustomerId());
	}

	@Test
	void testUpdateNoRecordsFoundException() {

		when(corporateCustomerMaintenanceRepository.existsByCorporateCustomerId(Mockito.any())).thenReturn(false);
		assertThrows(NoRecordsFoundException.class,
				() -> corporateCustomerMaintenanceService.updateCorporateCustomer(corporateCustomer, header));
		when(corporateCustomer.getIcghCd() != null).thenReturn(false);
		when(interChangePartyRepo.existsByIchgCode(Mockito.any())).thenReturn(false);
		assertThrows(NoRecordsFoundException.class,
				() -> corporateCustomerMaintenanceService.updateCorporateCustomer(corporateCustomer, header));

	}

	@Test
	void testUpdateRecordAlreadyExistsException() {
		when(corporateCustomerMaintenanceRepository.existsByCorporateCustomerId(Mockito.any())).thenReturn(false);
		when(corporateCustomerMaintenanceRepository.existsByCorporateLongName(Mockito.any())
				&& corporateCustomerMaintenanceRepository.existsByCorporateShortName(Mockito.any())).thenReturn(true);
		assertThrows(NoRecordsFoundException.class,
				() -> corporateCustomerMaintenanceService.updateCorporateCustomer(corporateCustomer, header));

	}

	@Test
	void testAddCorporateCustomerMainRecordAlreadyExistsException() {

		corporateCustomer.setCorporateLongName("KLINE-THE RAILBRIDGE CORP");
		when(corporateCustomerMaintenanceRepository.existsByCorporateLongName(Mockito.any())).thenReturn(true);

		RecordAlreadyExistsException exception = assertThrows(RecordAlreadyExistsException.class,
				() -> when(corporateCustomerMaintenanceService.addCorporateCustomer(corporateCustomer, header)));
		assertEquals("Long Name already exists: KLINE-THE RAILBRIDGE CORP", exception.getMessage());

	}

	@Test
	void testAddCorporateCustomerMainRecordAlreadyExistsException1() {
		corporateCustomer.setCorporateLongName("KLINQQQQ");
		corporateCustomer.setCorporateShortName("KLNC");
		when(corporateCustomerMaintenanceRepository.existsByCorporateShortName(Mockito.any())).thenReturn(true);
		RecordAlreadyExistsException exception1 = assertThrows(RecordAlreadyExistsException.class,
				() -> when(corporateCustomerMaintenanceService.addCorporateCustomer(corporateCustomer, header)));
		assertEquals("Short Name already exists: KLNC", exception1.getMessage());
	}

	@Test
	void testAddCorporateCustomerMainNoRecordsFoundException() {

		corporateCustomer.setCorporateLongName(null);
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> corporateCustomerMaintenanceService.addCorporateCustomer(corporateCustomer, header));
		assertEquals("W-CORP_LONG_NM Required", exception.getMessage());

	}


	@Test
	void testAddCorporateCustomerMainNoRecordsFoundException1() {

		corporateCustomer.setCorporateShortName(null);
		NoRecordsFoundException exception1 = assertThrows(NoRecordsFoundException.class,
				() -> corporateCustomerMaintenanceService.addCorporateCustomer(corporateCustomer, header));
		assertEquals("W-CORP_SHORT_NM Required", exception1.getMessage());
	}

	@Test
	void testGetCorporateCustomerData() {
		when(corporateCustomerMaintenanceRepository.findAll()).thenReturn(new ArrayList<CorporateCustomer>());
		List<CorporateCustomer> result = corporateCustomerMaintenanceService.getCorporateCustomerData();
		Assertions.assertNotNull(result);
	}

	@Test
	void testDeleteCorporateCustomerMaintenance() {

		when(corporateCustomerMaintenanceRepository.existsByCorporateCustomerId(any())).thenReturn(true);
		when(corporateCustomerMaintenanceRepository.findByCorporateCustomerId(any())).thenReturn(corporateCustomer);
		CorporateCustomer delete = corporateCustomerMaintenanceService.deleteCorporateCustomerData(corporateCustomer);
		assertEquals(delete, corporateCustomer);

	}

	@Test
	void testDeleteCorporateCustomerMaintenanceException() {
		when(corporateCustomerMaintenanceRepository.existsByCorporateCustomerId(any())).thenReturn(false);
		RecordNotDeletedException exception = assertThrows(RecordNotDeletedException.class,
				() -> corporateCustomerMaintenanceService.deleteCorporateCustomerData(corporateCustomer));
		assertEquals("Record Not Found!", exception.getMessage());

	}

}
