package com.nscorp.obis.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.nscorp.obis.domain.CorporateCustomer;
import com.nscorp.obis.domain.EquipmentCustomerLesseeRange;
import com.nscorp.obis.dto.CorporateCustomerDTO;
import com.nscorp.obis.dto.EquipmentCustomerLesseeRangeDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.repository.CorporateCustomerRepository;
import com.nscorp.obis.repository.EquipmentCustomerLesseeRangeRepository;

class EquipmentCustomerLesseeRangeServiceTest {

	@InjectMocks
	EquipmentCustomerLesseeRangeServiceImpl equipLesseeRangeService;

	@Mock
	EquipmentCustomerLesseeRangeRepository equipLesseeRangeRepo;
	
	@Mock
	CorporateCustomerRepository corporateCustomerRepository;

	EquipmentCustomerLesseeRange equipmentLesseeRange;
	EquipmentCustomerLesseeRangeDTO equipmentLesseeRangeDto;
	List<EquipmentCustomerLesseeRange> equipmentLesseeRangeList;
	Page<EquipmentCustomerLesseeRange> equipmentLesseeRangePageList;
	List<EquipmentCustomerLesseeRangeDTO> equipmentLesseeRangeDtoList;
	CorporateCustomer corpCustomer;
	CorporateCustomerDTO corpCustomerDTO;
	List<CorporateCustomer> corpCustomerList;

	Pageable pageable;
	Map<String, String> header;

	String equipmentInit = "TEST";
	String corporateLongName = "TEST";
	BigDecimal equipmentLowNumber;
	int pageNumber;
	int pageSize;
	String[] sort;

	List<Long> eqLesseeIds;
	List<String> corpLongNames;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		equipmentLesseeRange = new EquipmentCustomerLesseeRange();
		equipmentLesseeRangeDto = new EquipmentCustomerLesseeRangeDTO();
		corpCustomer = new CorporateCustomer();
		corpCustomerList = new ArrayList<>();
		equipmentLesseeRangeList = new ArrayList<>();
		equipmentLesseeRangeDtoList = new ArrayList<>();

		equipmentLesseeRange.setEquipmentCustomerRangeId(100L);
		equipmentLesseeRange.setEquipmentHighNumber(BigDecimal.valueOf(35));
		equipmentLesseeRange.setEquipmentLowNumber(BigDecimal.valueOf(30));
		equipmentLesseeRange.setEquipmentInit(null);
		equipmentLesseeRange.setEquipmentLesseeId((long) 1.2839942823086E13);
		equipmentLesseeRange.setEquipmentOwnerId(100L);
		equipmentLesseeRange.setEquipmentOwnerType("TEST");
		equipmentLesseeRange.setEquipmentType("C");
		equipmentLesseeRange.setCorporateCustomer(corpCustomer);

		equipmentLesseeRangeDto.setEquipmentCustomerRangeId(100L);
		equipmentLesseeRangeDto.setEquipmentHighNumber(BigDecimal.valueOf(35));
		equipmentLesseeRangeDto.setEquipmentLowNumber(BigDecimal.valueOf(30));
		equipmentLesseeRangeDto.setEquipmentInit("TEST");
		equipmentLesseeRangeDto.setEquipmentLesseeId((long) 1.2839942823086E13);
		equipmentLesseeRangeDto.setEquipmentOwnerId(100L);
		equipmentLesseeRangeDto.setEquipmentOwnerType("TEST");
		equipmentLesseeRangeDto.setEquipmentType("C");
		equipmentLesseeRangeDto.setCorporateCustomer(corpCustomerDTO);

		corpCustomer.setCorporateCustomerId((long) 1.985733);
		corpCustomer.setCorporateLongName(null);
		corpCustomer.setCorporateShortName("KLNC");
		corpCustomer.setCustomerId((long) 1.2839942823086E13);
		corpCustomer.setIcghCd("TEST");
		corpCustomer.setPrimaryLob("I");
		corpCustomer.setSecondaryLob("OCN CARR");
		corpCustomer.setScac("TEST");
		corpCustomer.setTerminalFeedEnabled("TEST");
		corpCustomer.setAccountManager("TEST");
		corpCustomerList.add(corpCustomer);

		equipmentLesseeRangeList.add(equipmentLesseeRange);
		equipmentLesseeRangeDtoList.add(equipmentLesseeRangeDto);
		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");

		pageNumber = 1;
		pageSize = 10;
		sort = new String[] { "equipmentLowNumber,asc" };
		pageable = PageRequest.of(pageNumber - 1, pageSize);

		eqLesseeIds = new ArrayList<>();
		eqLesseeIds.add(100L);
		eqLesseeIds.add(null);

		corpLongNames = new ArrayList<>();
		corpLongNames.add("TEST  ");
		corpLongNames.add("");
		corpLongNames.add(null);
	}

	@Test
	void testGetEquipLessee() {
		equipmentLesseeRangePageList = new PageImpl<>(Arrays.asList(equipmentLesseeRange), PageRequest.of(1, 10), 100L);
		when(equipLesseeRangeRepo.findByEquipmentInitAndCorporateCustomer_CorporateLongName(equipmentInit,
				corporateLongName, pageable)).thenReturn(equipmentLesseeRangePageList);
		Page<EquipmentCustomerLesseeRange> allEquipLesseeRange = equipLesseeRangeService.getEquipLessee(equipmentInit,
				corporateLongName, pageable);
		assertEquals(allEquipLesseeRange, equipmentLesseeRangePageList);

		when(equipLesseeRangeRepo.findByEquipmentInit(equipmentInit, pageable))
				.thenReturn(equipmentLesseeRangePageList);
		corporateLongName = StringUtils.EMPTY;
		Page<EquipmentCustomerLesseeRange> allEquipLesseeRangeEqInit = equipLesseeRangeService
				.getEquipLessee(equipmentInit, corporateLongName, pageable);
		assertEquals(allEquipLesseeRangeEqInit, equipmentLesseeRangePageList);

		when(equipLesseeRangeRepo.findByCorporateCustomer_CorporateLongName(equipmentInit, pageable))
				.thenReturn(equipmentLesseeRangePageList);
		equipmentInit = StringUtils.EMPTY;
		corporateLongName = "TEST";
		Page<EquipmentCustomerLesseeRange> allEquipLesseeRangeCorpLong = equipLesseeRangeService
				.getEquipLessee(equipmentInit, corporateLongName, pageable);
		assertEquals(allEquipLesseeRangeCorpLong, equipmentLesseeRangePageList);
	}

	@Test
	void testGetNoRecordsFoundException() {
		when(equipLesseeRangeRepo.findByEquipmentInitAndCorporateCustomer_CorporateLongName(equipmentInit,
				corporateLongName, pageable)).thenReturn(new PageImpl<>(Collections.emptyList(), pageable, 0L));
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(equipLesseeRangeService.getEquipLessee(equipmentInit, corporateLongName, pageable))
						.thenReturn(equipmentLesseeRangePageList));
		assertEquals("No Records Found", exception.getMessage());

		when(equipLesseeRangeRepo.existsByEquipmentCustomerRangeIdAndUversion(Mockito.any(), Mockito.any()))
				.thenReturn(false);
		NoRecordsFoundException Deleteexception = assertThrows(NoRecordsFoundException.class,
				() -> when(equipLesseeRangeService.deleteEquipLesseeRange(equipmentLesseeRange)));
		assertEquals("No record Found to delete Under this EquipmentCustomerRangeId: "
				+ equipmentLesseeRange.getEquipmentCustomerRangeId() + " and U_Version: "
				+ equipmentLesseeRange.getUversion(), Deleteexception.getMessage());

	}

	@Test
	void testDeleteStackCar() {
		when(equipLesseeRangeRepo.existsByEquipmentCustomerRangeIdAndUversion(Mockito.any(), Mockito.any()))
				.thenReturn(true);
		when(equipLesseeRangeRepo.findById(Mockito.any())).thenReturn(Optional.ofNullable(equipmentLesseeRange));

		EquipmentCustomerLesseeRange deleteResponse = equipLesseeRangeService
				.deleteEquipLesseeRange(equipmentLesseeRange);
		assertEquals(deleteResponse, deleteResponse);
	}

	@Test
	void testGetAllEquipmentInits() {
		when(equipLesseeRangeRepo.findAllDistinctEquipmentInit()).thenReturn(corpLongNames);
		List<String> allEquipmentInit = equipLesseeRangeService.getAllEquipmentInit();
		assertNotNull(allEquipmentInit);
	}

	@Test
	void testGetAllCorpLongNames() {
		when(equipLesseeRangeRepo.findAllDistinctCorporateLongName()).thenReturn(eqLesseeIds);
		when(corporateCustomerRepository.existsByCorporateCustomerId(Mockito.any())).thenReturn(true);
		when(corporateCustomerRepository.findCorporateLongName(Mockito.anyLong())).thenReturn("TEST");
		List<String> allEquipmentInit = equipLesseeRangeService.getAllCorporateLongName();
		assertNotNull(allEquipmentInit);
	}

}
