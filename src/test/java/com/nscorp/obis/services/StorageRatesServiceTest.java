package com.nscorp.obis.services;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.nscorp.obis.exception.InvalidDataException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
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
import com.nscorp.obis.domain.CustomerInfo;
import com.nscorp.obis.domain.StorageRates;
import com.nscorp.obis.dto.StorageRatesDTO;
import com.nscorp.obis.dto.mapper.StorageRatesMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.repository.CustomerInfoRepository;
import com.nscorp.obis.repository.EquipmentCustomerRangeRepository;
import com.nscorp.obis.repository.StorageRatesRepository;
import com.nscorp.obis.repository.TerminalRepository;
import com.nscorp.obis.response.data.PaginatedResponse;

class StorageRatesServiceTest {

	@Mock
	StorageRatesRepository storageRatesRepository;

	@Mock
	SpecificationGenerator specificationGenerator;

	@Mock
	CustomerInfoRepository customerInfoRepo;
	
	@Mock
	EquipmentCustomerRangeRepository equipmentCustomerRangeRepository;
	
	@Mock
	TerminalRepository terminalRepo;

	@Mock
	StorageRatesMapper mapper;

	@InjectMocks
	StorageRatesServiceImpl service;

	StorageRatesDTO storageRatesDTO;
	StorageRates storageRates;
	List<StorageRates> storageRatesList;

	Specification<StorageRates> specification;
	Page<StorageRates> page;

	String selectRateType = "Shipper";
	String incExpDate = "Y";
	String shipPrimSix = "123789";
	String customerPrimSix = "123456";
	String bnfPrimSix = "000124";
	String termId = "1245";
	String equipInit = "45";
	String[] termIds;
	String[] equipInits;
	String equipLgth = "12";
	Integer pageSize = 20;
	Integer pageNumber = 0;
	String[] sort = { "termId", "asc" };
	String[] filter = { "termId", "123" };

	Map<String, String> headers;

	Specification<StorageRates> specification2;
	StorageRatesDTO ratesDTO;
	String lclInterInd = "L";
	String gateInd = "O";
	String equipTp = "C";
	String ldEmptyCd = "L";
	String rrInd = "Y";
	Integer equipLength = 12;
	LocalDate effectiveDate = LocalDate.of(2023, 06, 05);
	LocalDate endDate = null;
	Long terminalId = 1234L;

	String forceAdd = "N";

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		specification = new SpecificationGenerator().storageRatesSpecificationExpired(selectRateType, shipPrimSix,
				bnfPrimSix, customerPrimSix, equipInits, equipLgth, termIds, filter);

		storageRatesList = new ArrayList<StorageRates>();
		page = new PageImpl(storageRatesList);

		storageRatesDTO = new StorageRatesDTO();
		storageRates = new StorageRates();
		storageRates.setStorageId(10313693711985L);
		storageRates.setEquipLgth(20);
		storageRates.setNotepadTxt("test");
		storageRates.setUversion("!");
		storageRates.setEffectiveDate(LocalDate.of(2021, 11, 24));
		storageRates.setEndDate(LocalDate.of(2021, 11, 25));
		storageRatesDTO.setStorageId(10313693711985L);
		storageRatesDTO.setEquipLgth(20);
		storageRatesDTO.setNotepadTxt("test");
		storageRatesDTO.setUversion("!");
		storageRatesDTO.setEffectiveDate(LocalDate.of(2021, 11, 24));
		storageRatesDTO.setEndDate(LocalDate.of(2021, 11, 25));

		headers = new HashMap<>();
		headers.put("userid", "test");
		headers.put("extensionschema", "test");

		specification2 = (Root<StorageRates> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();
			return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
		};

		ratesDTO = new StorageRatesDTO();
		ratesDTO.setShipPrimSix(shipPrimSix);
		ratesDTO.setBnfPrimSix(bnfPrimSix);
		ratesDTO.setCustomerPrimSix(customerPrimSix);
		ratesDTO.setEquipInit(equipInit);
		ratesDTO.setEquipLgth(equipLength);
		ratesDTO.setTermId(terminalId);
		ratesDTO.setLclInterInd(lclInterInd);
		ratesDTO.setEquipTp(equipTp);
		ratesDTO.setGateInd(gateInd);
		ratesDTO.setLdEmptyCd(ldEmptyCd);
		ratesDTO.setRrInd(rrInd);
		ratesDTO.setEffectiveDate(effectiveDate);
		ratesDTO.setEndDate(endDate);
		ratesDTO.setFreeDDLmt(10);
		ratesDTO.setRate1Amt(new BigDecimal(10));
		ratesDTO.setCntWeekend("Y");
		ratesDTO.setCntSaturday("Y");
	}

	@AfterEach
	void tearDown() throws Exception {
		storageRates = null;
		storageRatesDTO = null;
	}

	@Test
	void testFetchStorageRates() throws SQLException {
		when(specificationGenerator.storageRatesSpecificationExpired(selectRateType, shipPrimSix, bnfPrimSix,
				customerPrimSix, equipInits, equipLgth, termIds, filter)).thenReturn(specification);
		when(storageRatesRepository.findAll(Mockito.any(Specification.class), Mockito.any())).thenReturn(page);
		PaginatedResponse<StorageRatesDTO> response = service.fetchStorageRates(selectRateType, incExpDate, shipPrimSix,
				customerPrimSix, bnfPrimSix, termIds, equipInits, equipLgth, pageSize, pageNumber, sort,filter);
		assertNotNull(response);
		storageRatesList = new ArrayList<>();
		page = new PageImpl(storageRatesList);
		when(specificationGenerator.storageRatesSpecificationExpired(selectRateType, shipPrimSix, bnfPrimSix,
				customerPrimSix, equipInits, equipLgth, termIds, filter)).thenReturn(specification);
		when(storageRatesRepository.findAll(Mockito.any(Specification.class), Mockito.any())).thenReturn(page);
		assertThrows(NoRecordsFoundException.class, () -> service.fetchStorageRates(selectRateType, incExpDate,
				shipPrimSix, customerPrimSix, bnfPrimSix, termIds, equipInits, equipLgth, pageSize, pageNumber, sort,filter));
	}

	@Test
	void testUpdateStorageRates() throws SQLException {
		when(storageRatesRepository.findByStorageId(Mockito.anyLong())).thenReturn(storageRates);
		when(storageRatesRepository.save(Mockito.any())).thenReturn(storageRates);
		StorageRatesDTO added = service.updateStorageRate(storageRatesDTO, headers);
		assertEquals(storageRatesDTO.getStorageId(), added.getStorageId());

	}

	@Test
	void testExceptionForUpdateStorageRate() throws SQLException {

		storageRatesDTO.setStorageId(null);

		InvalidDataException exResponse= assertThrows(InvalidDataException.class,
				() -> when(service.updateStorageRate(storageRatesDTO,headers)));
		assertEquals(exResponse.getMessage(),"Storage Id can't be null");

		storageRatesDTO.setStorageId(10313693711985L);

		when(storageRatesRepository.findByStorageId(Mockito.anyLong())).thenReturn(null);
		assertThrows(NoRecordsFoundException.class, () -> service.updateStorageRate(storageRatesDTO, headers));

		when(storageRatesRepository.findByStorageId(Mockito.anyLong())).thenReturn(storageRates);

		storageRatesDTO.setEquipLgth(1);

		InvalidDataException exception1 = assertThrows(InvalidDataException.class,
				() -> when(service.updateStorageRate(storageRatesDTO, headers)));
		assertEquals(exception1.getMessage(), "A Valid Eq Lgth must be provided");

		storageRatesDTO.setEquipLgth(20);

		storageRatesDTO.setEndDate(LocalDate.of(2021, 11, 23));

		InvalidDataException exception2 = assertThrows(InvalidDataException.class,
				() -> when(service.updateStorageRate(storageRatesDTO, headers)));
		assertEquals(exception2.getMessage(), "Ending date must be greater than effective date");

		storageRatesDTO.setEndDate(LocalDate.of(2021, 11, 25));
		storageRates.setEndDate(LocalDate.of(2021, 11, 24));

		storageRates.setEffectiveDate(null);
		InvalidDataException exception3 = assertThrows(InvalidDataException.class,
				() -> when(service.updateStorageRate(storageRatesDTO, headers)));
		assertEquals(exception3.getMessage(), "Effective date is mandatory for having end date!");

	}

	@Test
	void testAddStorageRates() {
		selectRateType = "test";
		ratesDTO.setTermId(null);
		when(specificationGenerator.storageRatesDuplicateCheck(shipPrimSix, bnfPrimSix, customerPrimSix, equipInit,
				equipLength, terminalId, lclInterInd, gateInd, equipTp, ldEmptyCd, rrInd, effectiveDate, endDate))
						.thenReturn(specification2);
		storageRatesList.add(new StorageRates());
		when(storageRatesRepository.findAll(Mockito.eq(specification2))).thenReturn(storageRatesList);
		assertThrows(NoRecordsFoundException.class,
				() -> service.addStorageRates(selectRateType, forceAdd, ratesDTO, headers));
		storageRatesList = new ArrayList<>();
		when(storageRatesRepository.findAll(Mockito.eq(specification2))).thenReturn(storageRatesList);
		when(storageRatesRepository.save(Mockito.any())).thenReturn(storageRates);
		when(mapper.storageRatesToStorageRatesDTO(Mockito.any())).thenReturn(ratesDTO);
		StorageRatesDTO response = service.addStorageRates(selectRateType, forceAdd, ratesDTO, headers);
		assertNotNull(response);
		when(storageRatesRepository.save(Mockito.any())).thenReturn(null);
		assertThrows(RecordNotAddedException.class,
				() -> service.addStorageRates(selectRateType, forceAdd, ratesDTO, headers));
	}

	@Test
	void testAddShipper() {
		forceAdd = "Y";
		selectRateType = "Shipper";
		ratesDTO.setTermId(null);
		when(storageRatesRepository.save(Mockito.any())).thenReturn(storageRates);
		when(mapper.storageRatesToStorageRatesDTO(Mockito.any())).thenReturn(ratesDTO);
		assertThrows(InvalidDataException.class,
				() -> service.addStorageRates(selectRateType, forceAdd, ratesDTO, headers));
		ratesDTO.setShipCustomerNm("Test");
		ratesDTO.setShipPrimSix("123456");
		when(customerInfoRepo.checkByCustomerNameAndCustomerPrimarySix(Mockito.eq("Test"), Mockito.eq("123456")))
				.thenReturn(new ArrayList<>());
		assertThrows(NoRecordsFoundException.class,
				() -> service.addStorageRates(selectRateType, forceAdd, ratesDTO, headers));
		ratesDTO.setCustomerNm("TEST");
		ratesDTO.setCustomerPrimSix("123456");
		ratesDTO.setBnfCustomerNm("TEST");
		when(customerInfoRepo.checkByCustomerNameAndCustomerPrimarySix(Mockito.eq("Test"), Mockito.eq("123456")))
				.thenReturn(Arrays.asList("TEST"));
		assertThrows(InvalidDataException.class,
				() -> service.addStorageRates(selectRateType, forceAdd, ratesDTO, headers));
		ratesDTO.setBnfCustomerNm(null);
		when(customerInfoRepo.checkByCustomerNameAndCustomerPrimarySix(Mockito.eq("TEST"), Mockito.eq("123456")))
				.thenReturn(new ArrayList<>());
		assertThrows(NoRecordsFoundException.class,
				() -> service.addStorageRates(selectRateType, forceAdd, ratesDTO, headers));
		when(customerInfoRepo.checkByCustomerNameAndCustomerPrimarySix(Mockito.eq("TEST"), Mockito.eq("123456")))
				.thenReturn(Arrays.asList("TEST"));
		ratesDTO.setCustomerNm(null);
		ratesDTO.setCustomerPrimSix(null);
		ratesDTO.setBnfCustomerNm("TEST_BNF");
		ratesDTO.setBnfPrimSix("123456");
		when(customerInfoRepo.checkByCustomerNameAndCustomerPrimarySix(Mockito.eq("TEST_BNF"), Mockito.eq("123456")))
				.thenReturn(new ArrayList<>());
		assertThrows(NoRecordsFoundException.class,
				() -> service.addStorageRates(selectRateType, forceAdd, ratesDTO, headers));
		when(customerInfoRepo.checkByCustomerNameAndCustomerPrimarySix(Mockito.eq("TEST_BNF"), Mockito.eq("123456")))
				.thenReturn(Arrays.asList("TEST"));
		ratesDTO.setEquipInit("1234");
		assertThrows(InvalidDataException.class,
				() -> service.addStorageRates(selectRateType, forceAdd, ratesDTO, headers));
		ratesDTO.setBnfCustomerNm(null);
		ratesDTO.setBnfPrimSix(null);
		when(equipmentCustomerRangeRepository.existsByEquipmentInit(Mockito.anyString())).thenReturn(false);
		assertThrows(NoRecordsFoundException.class,
				() -> service.addStorageRates(selectRateType, forceAdd, ratesDTO, headers));
		when(equipmentCustomerRangeRepository.existsByEquipmentInit(Mockito.anyString())).thenReturn(true);
		StorageRatesDTO response = service.addStorageRates(selectRateType, forceAdd, ratesDTO, headers);
		assertNotNull(response);
	}
	
	@Test
	void testAddBeneficialCustomer() {
		forceAdd = "Y";
		selectRateType = "BenfCargoOwner";
		ratesDTO.setTermId(null);
		when(storageRatesRepository.save(Mockito.any())).thenReturn(storageRates);
		when(mapper.storageRatesToStorageRatesDTO(Mockito.any())).thenReturn(ratesDTO);
		assertThrows(InvalidDataException.class,
				() -> service.addStorageRates(selectRateType, forceAdd, ratesDTO, headers));
		ratesDTO.setBnfCustomerNm("Test");
		ratesDTO.setBnfPrimSix("123456");
		when(customerInfoRepo.checkByCustomerNameAndCustomerPrimarySix(Mockito.eq("Test"), Mockito.eq("123456")))
				.thenReturn(new ArrayList<>());
		assertThrows(NoRecordsFoundException.class,
				() -> service.addStorageRates(selectRateType, forceAdd, ratesDTO, headers));
		when(customerInfoRepo.checkByCustomerNameAndCustomerPrimarySix(Mockito.eq("Test"), Mockito.eq("123456")))
		.thenReturn(Arrays.asList("Test"));
		ratesDTO.setEquipInit("1234");
		when(equipmentCustomerRangeRepository.existsByEquipmentInit(Mockito.anyString())).thenReturn(false);
		assertThrows(NoRecordsFoundException.class,
				() -> service.addStorageRates(selectRateType, forceAdd, ratesDTO, headers));
		when(equipmentCustomerRangeRepository.existsByEquipmentInit(Mockito.anyString())).thenReturn(true);
		StorageRatesDTO response = service.addStorageRates(selectRateType, forceAdd, ratesDTO, headers);
		assertNotNull(response);
	}
	
	@Test
	void testAddNotifyCustomer() {
		forceAdd = "Y";
		selectRateType = "NotifyParty";
		ratesDTO.setTermId(null);
		when(storageRatesRepository.save(Mockito.any())).thenReturn(storageRates);
		when(mapper.storageRatesToStorageRatesDTO(Mockito.any())).thenReturn(ratesDTO);
		assertThrows(InvalidDataException.class,
				() -> service.addStorageRates(selectRateType, forceAdd, ratesDTO, headers));
		ratesDTO.setCustomerNm("Test");
		ratesDTO.setCustomerPrimSix("123456");
		when(customerInfoRepo.checkByCustomerNameAndCustomerPrimarySix(Mockito.eq("Test"), Mockito.eq("123456")))
				.thenReturn(new ArrayList<>());
		assertThrows(NoRecordsFoundException.class,
				() -> service.addStorageRates(selectRateType, forceAdd, ratesDTO, headers));
		when(customerInfoRepo.checkByCustomerNameAndCustomerPrimarySix(Mockito.eq("Test"), Mockito.eq("123456")))
		.thenReturn(Arrays.asList("Test"));
		StorageRatesDTO response = service.addStorageRates(selectRateType, forceAdd, ratesDTO, headers);
		assertNotNull(response);
	}
	
	@Test
	void testAddEquipment() {
		forceAdd = "Y";
		selectRateType = "Equipment";
		ratesDTO.setTermId(null);
		when(storageRatesRepository.save(Mockito.any())).thenReturn(storageRates);
		when(mapper.storageRatesToStorageRatesDTO(Mockito.any())).thenReturn(ratesDTO);
		ratesDTO.setEquipInit(null);
		assertThrows(InvalidDataException.class,
				() -> service.addStorageRates(selectRateType, forceAdd, ratesDTO, headers));
		ratesDTO.setEquipInit(equipInit);
		when(equipmentCustomerRangeRepository.existsByEquipmentInit(Mockito.anyString())).thenReturn(false);
		assertThrows(NoRecordsFoundException.class,
				() -> service.addStorageRates(selectRateType, forceAdd, ratesDTO, headers));
		when(equipmentCustomerRangeRepository.existsByEquipmentInit(Mockito.anyString())).thenReturn(true);
		StorageRatesDTO response = service.addStorageRates(selectRateType, forceAdd, ratesDTO, headers);
		assertNotNull(response);
	}
	
	@Test
	void testAddEquipmentLength() {
		forceAdd = "Y";
		selectRateType = "EqptLength";
		ratesDTO.setTermId(null);
		when(storageRatesRepository.save(Mockito.any())).thenReturn(storageRates);
		when(mapper.storageRatesToStorageRatesDTO(Mockito.any())).thenReturn(ratesDTO);
		ratesDTO.setEquipLgth(null);
		assertThrows(InvalidDataException.class,
				() -> service.addStorageRates(selectRateType, forceAdd, ratesDTO, headers));
		ratesDTO.setEquipLgth(100);
		assertThrows(InvalidDataException.class,
				() -> service.addStorageRates(selectRateType, forceAdd, ratesDTO, headers));
		ratesDTO.setEquipLgth(20);
		StorageRatesDTO response = service.addStorageRates(selectRateType, forceAdd, ratesDTO, headers);
		assertNotNull(response);
	}
	
	@Test
	void testAddTerminal() {
		forceAdd = "Y";
		selectRateType = "Terminal";
		ratesDTO.setTermId(null);
		when(storageRatesRepository.save(Mockito.any())).thenReturn(storageRates);
		when(mapper.storageRatesToStorageRatesDTO(Mockito.any())).thenReturn(ratesDTO);
		ratesDTO.setTermId(null);
		assertThrows(InvalidDataException.class,
				() -> service.addStorageRates(selectRateType, forceAdd, ratesDTO, headers));
		ratesDTO.setTermId(1234L);
		when(terminalRepo.existsByTerminalId(Mockito.anyLong())).thenReturn(false);
		assertThrows(NoRecordsFoundException.class,
				() -> service.addStorageRates(selectRateType, forceAdd, ratesDTO, headers));
		when(terminalRepo.existsByTerminalId(Mockito.anyLong())).thenReturn(true);
		StorageRatesDTO response = service.addStorageRates(selectRateType, forceAdd, ratesDTO, headers);
		assertNotNull(response);
	}
	
	@Test
	void testAddCommonRules() {
		forceAdd = "Y";
		selectRateType = "Default";
		ratesDTO.setTermId(null);
		when(storageRatesRepository.save(Mockito.any())).thenReturn(storageRates);
		when(mapper.storageRatesToStorageRatesDTO(Mockito.any())).thenReturn(ratesDTO);
		
		ratesDTO.setEndDate(LocalDate.of(2023, 06, 04));
		assertThrows(InvalidDataException.class,
				() -> service.addStorageRates(selectRateType, forceAdd, ratesDTO, headers));
		ratesDTO.setEndDate(LocalDate.now());
		ratesDTO.setGateInd("C");
		ratesDTO.setEquipTp("Z");
		assertThrows(InvalidDataException.class,
				() -> service.addStorageRates(selectRateType, forceAdd, ratesDTO, headers));
		ratesDTO.setEquipTp("C");
		ratesDTO.setTermId(1234L);
		when(terminalRepo.existsByTerminalId(Mockito.anyLong())).thenReturn(false);
		assertThrows(NoRecordsFoundException.class,
				() -> service.addStorageRates(selectRateType, forceAdd, ratesDTO, headers));
		when(terminalRepo.existsByTerminalId(Mockito.anyLong())).thenReturn(true);
		ratesDTO.setFreeDDLmt(null);
		assertThrows(InvalidDataException.class,
				() -> service.addStorageRates(selectRateType, forceAdd, ratesDTO, headers));
		ratesDTO.setFreeDDLmt(10);
		ratesDTO.setCntWeekend(null);
		assertThrows(InvalidDataException.class,
				() -> service.addStorageRates(selectRateType, forceAdd, ratesDTO, headers));
		ratesDTO.setCntWeekend("Y");
		ratesDTO.setRate1Amt(null);
		ratesDTO.setPeakRt1Amt(null);
		assertThrows(InvalidDataException.class,
				() -> service.addStorageRates(selectRateType, forceAdd, ratesDTO, headers));
		ratesDTO.setRate1Amt(new BigDecimal(10));
		ratesDTO.setCntSaturday(null);
		assertThrows(InvalidDataException.class,
				() -> service.addStorageRates(selectRateType, forceAdd, ratesDTO, headers));
		ratesDTO.setCntSaturday("Y");
		ratesDTO.setBondFreeDD(0);
		ratesDTO.setRateTp("M");
		assertThrows(InvalidDataException.class,
				() -> service.addStorageRates(selectRateType, forceAdd, ratesDTO, headers));
		ratesDTO.setCntWeekend("N");
		ratesDTO.setTermId(null);
		assertThrows(InvalidDataException.class,
				() -> service.addStorageRates(selectRateType, forceAdd, ratesDTO, headers));
		ratesDTO.setTermId(1234L);
		ratesDTO.setRateDDLmt(null);
		assertThrows(InvalidDataException.class,
				() -> service.addStorageRates(selectRateType, forceAdd, ratesDTO, headers));
		ratesDTO.setRateDDLmt(10);
		ratesDTO.setRate2DDLmt(null);
		assertThrows(InvalidDataException.class,
				() -> service.addStorageRates(selectRateType, forceAdd, ratesDTO, headers));
		ratesDTO.setRate2DDLmt(10);
		ratesDTO.setBondFreeDD(null);
		assertThrows(InvalidDataException.class,
				() -> service.addStorageRates(selectRateType, forceAdd, ratesDTO, headers));
		ratesDTO.setBondFreeDD(0);
		ratesDTO.setGateInd("O");
		ratesDTO.setRateTp("D");
		assertThrows(InvalidDataException.class,
				() -> service.addStorageRates(selectRateType, forceAdd, ratesDTO, headers));
		ratesDTO.setGateInd(null);
		ratesDTO.setRateDDLmt(10);
		ratesDTO.setFreeDDLmt(20);
		assertThrows(InvalidDataException.class,
				() -> service.addStorageRates(selectRateType, forceAdd, ratesDTO, headers));
		ratesDTO.setRateDDLmt(null);
		ratesDTO.setRate2DDLmt(10);
		ratesDTO.setFreeDDLmt(20);
		assertThrows(InvalidDataException.class,
				() -> service.addStorageRates(selectRateType, forceAdd, ratesDTO, headers));
		ratesDTO.setRate2DDLmt(null);
		ratesDTO.setSchedDelRateInd("Y");
		assertThrows(InvalidDataException.class,
				() -> service.addStorageRates(selectRateType, forceAdd, ratesDTO, headers));
		ratesDTO.setSchedDelFail(10);
		assertThrows(InvalidDataException.class,
				() -> service.addStorageRates(selectRateType, forceAdd, ratesDTO, headers));
		ratesDTO.setSchedDelRateInd("N");
		ratesDTO.setCntAfternoon("N");
		ratesDTO.setCntSunday("N");
		ratesDTO.setCntSaturday("N");
		ratesDTO.setPeakRt1Amt(new BigDecimal(1));
		ratesDTO.setPeakRt2Amt(new BigDecimal(0));
		assertThrows(InvalidDataException.class,
				() -> service.addStorageRates(selectRateType, forceAdd, ratesDTO, headers));
		ratesDTO.setPeakRt2Amt(new BigDecimal(2));
		ratesDTO.setOffPeakRt1Amt(new BigDecimal(1));
		ratesDTO.setOffPeakRate2Amt(new BigDecimal(0));
		assertThrows(InvalidDataException.class,
				() -> service.addStorageRates(selectRateType, forceAdd, ratesDTO, headers));
		ratesDTO.setOffPeakRate2Amt(new BigDecimal(2));
		ratesDTO.setOffPeakRt1Amt(new BigDecimal(2));
		assertThrows(InvalidDataException.class,
				() -> service.addStorageRates(selectRateType, forceAdd, ratesDTO, headers));
		ratesDTO.setOffPeakRt1Amt(new BigDecimal(1));
		StorageRatesDTO response = service.addStorageRates(selectRateType, forceAdd, ratesDTO, headers);
		assertNotNull(response);
	}
	
	

}
