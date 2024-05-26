package com.nscorp.obis.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nscorp.obis.exception.RecordNotAddedException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.nscorp.obis.domain.EquipmentOverrideTareWeight;
import com.nscorp.obis.dto.EquipmentOverrideTareWeightDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.repository.EquipmentOverrideTareWeightRepository;

class EquipmentOverrideTareWeightServiceTest {
	
	@InjectMocks
	EquipmentOverrideTareWeightServiceImpl eqOverrideService;
	
	@Mock
	EquipmentOverrideTareWeightRepository eqOverrideRepo;
	
	EquipmentOverrideTareWeight eqTareWeight;
	EquipmentOverrideTareWeightDTO eqTareWeightDto;
	List<EquipmentOverrideTareWeight> eqTareWeightList;
	List<EquipmentOverrideTareWeightDTO> eqTareWeightDtoList;
	
	Map<String, String> header;
	
	BigDecimal eqNrLow = new BigDecimal(1000);
	BigDecimal eqNrHigh = new BigDecimal(10000);
	String eqInit;
	String eqType;
	Integer eqOverrideWgt;

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		eqTareWeight = new EquipmentOverrideTareWeight();
		eqTareWeight.setEquipmentInit("TEST");
		eqTareWeight.setEquipmentNumberLow(eqNrLow);
		eqTareWeight.setEquipmentNumberHigh(eqNrHigh);
		eqTareWeight.setEquipmentType("C");
		eqTareWeight.setEquipmentLength(123000);
		eqTareWeight.setOverrideId(123000L);
		eqTareWeight.setOverrideTareWeight(10000);
		
		eqTareWeightList = new ArrayList<>();
		eqTareWeightList.add(eqTareWeight);
		
		eqTareWeightDto = new EquipmentOverrideTareWeightDTO();
		eqTareWeightDto.setEquipmentInit("TEST");
		eqTareWeightDto.setEquipmentNumberLow(eqNrLow);
		eqTareWeightDto.setEquipmentNumberHigh(eqNrHigh);
		eqTareWeightDto.setEquipmentType("C");
		eqTareWeightDto.setEquipmentLength(123000);
		eqTareWeightDto.setOverrideId(123000L);
		eqTareWeightDto.setOverrideTareWeight(10000);
		
		eqTareWeightDtoList = new ArrayList<>();
		eqTareWeightDtoList.add(eqTareWeightDto);
		
		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");
	}

	@AfterEach
	void tearDown() throws Exception {
		eqTareWeightDto = null;
		eqTareWeightDtoList = null;
		eqTareWeightList = null;
		eqTareWeight = null;
	}

	@Test
	void testGetAllTareWeights() {
		when(eqOverrideRepo.findAll(eqInit, eqNrLow, eqNrHigh, eqType, eqOverrideWgt)).thenReturn(eqTareWeightList);
		List<EquipmentOverrideTareWeight> allTareWeights = eqOverrideService.getAllTareWeights(eqInit, eqNrLow, eqNrHigh, eqType, eqOverrideWgt);
		assertEquals(allTareWeights, eqTareWeightList);
	}

	@Test
	void testGetAllTareWeightsException() {
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(eqOverrideService.getAllTareWeights(eqInit, eqNrLow, eqNrHigh, eqType, eqOverrideWgt)));
		assertEquals("No Record Found under this search!", exception.getMessage());
	}

	@Test
	void testDeleteOverrideWeights() {
		when(eqOverrideRepo.existsByOverrideId(any())).thenReturn(true);
		when(eqOverrideRepo.findByOverrideId(any())).thenReturn(eqTareWeight);
		EquipmentOverrideTareWeight overrideTareWeightAfterDelete = eqOverrideService.deleteOverrideWeights(eqTareWeight);
		assertEquals(overrideTareWeightAfterDelete,eqTareWeight);
	}


	@Test
	void testDeleteOverrideWeightsNoRecordsFoundException() {
		when(eqOverrideRepo.existsByOverrideId(any())).thenReturn(false);
		assertThrows(NoRecordsFoundException.class,
				() -> eqOverrideService.deleteOverrideWeights(eqTareWeight));
	}
	@Test
	void testAddOverrideWeight() {
		when(eqOverrideRepo.findByOverrideId(Mockito.any())).thenReturn(eqTareWeight);
		when(eqOverrideRepo.save(Mockito.any())).thenReturn(eqTareWeight);
		EquipmentOverrideTareWeight addedOverrideWeight = eqOverrideService.addOverrideTareWeight(eqTareWeight, header);
		assertNotNull(addedOverrideWeight);
	}

	@Test
	void testAddTareWeightRecordNotAddedException() {

		EquipmentOverrideTareWeight obj2 = new EquipmentOverrideTareWeight();
		obj2.setEquipmentNumberLow(BigDecimal.valueOf(500));
		obj2.setEquipmentNumberHigh(BigDecimal.valueOf(1000));
		obj2.setEquipmentInit(null);
		obj2.setEquipmentLength(null);
		obj2.setEquipmentType("C");
		obj2.setOverrideTareWeight(5000);
		RecordNotAddedException exception2 = assertThrows(RecordNotAddedException.class,
				() -> when(eqOverrideService
						.addOverrideTareWeight(obj2, header)));
		assertEquals("Equipment Init value should be present!", exception2.getMessage());

		EquipmentOverrideTareWeight obj3 = new EquipmentOverrideTareWeight();
		obj3.setEquipmentNumberLow(BigDecimal.valueOf(50));
		obj3.setEquipmentNumberHigh(BigDecimal.valueOf(49));
		obj3.setEquipmentInit("JBHU");
		obj3.setEquipmentLength(null);
		obj3.setEquipmentType("C");
		obj3.setOverrideTareWeight(5000);
		RecordNotAddedException exception3 = assertThrows(RecordNotAddedException.class,
				() -> when(eqOverrideService
						.addOverrideTareWeight(obj3, header)));
		assertEquals("Equipment Low Number: 50 should be less than or equals to Equipment High Number: 49", exception3.getMessage());

		EquipmentOverrideTareWeight obj5 = new EquipmentOverrideTareWeight();
		obj5.setEquipmentNumberLow(BigDecimal.valueOf(8000));
		obj5.setEquipmentNumberHigh(BigDecimal.valueOf(10001));
		obj5.setEquipmentInit("TEST");
		obj5.setEquipmentLength(null);
		obj5.setEquipmentType("C");
		obj5.setOverrideTareWeight(5000);
		when(eqOverrideRepo.existsByEquipmentInit(Mockito.any())).thenReturn(true);
		when(eqOverrideRepo.findByEquipmentInit(Mockito.any())).thenReturn(eqTareWeightList);
		RecordNotAddedException exception5 = assertThrows(RecordNotAddedException.class,
				() -> when(eqOverrideService
						.addOverrideTareWeight(obj5, header)));
		assertEquals("Equipment Init and Equipment Number Range are overlapping with existing records", exception5.getMessage());

	}
	
	@Test
	void testupdateOverrideWeightException() {
		when(eqOverrideRepo.existsById(Mockito.any())).thenReturn(false);
		assertThrows(NoRecordsFoundException.class,
				() -> when(eqOverrideService
						.updateEquipmentOverrideTareWeight(eqTareWeight, header)));
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"!",""," "})
	void testUpdateOverrideWeight(String uVersion) {
		when(eqOverrideRepo.existsById(Mockito.any())).thenReturn(true);
		eqTareWeight.setUversion(uVersion);
		when(eqOverrideRepo.findByOverrideId(Mockito.any())).thenReturn(eqTareWeight);
		when(eqOverrideRepo.save(Mockito.any())).thenReturn(eqTareWeight);
		EquipmentOverrideTareWeight updateOverrideWeight = eqOverrideService.updateEquipmentOverrideTareWeight(eqTareWeight, header);
		assertNotNull(updateOverrideWeight);
	}
}
