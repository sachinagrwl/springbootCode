package com.nscorp.obis.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.nscorp.obis.domain.FhwaStopRange;
import com.nscorp.obis.dto.FhwaStopRangeDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.repository.FhwaStopRangeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class FhwaStopRangeServiceTest {

	@InjectMocks
	FhwaStopRangeServiceImpl fhwaStopRangeService;

	@Mock
	FhwaStopRangeRepository fhwaStopRangeRepository;

	FhwaStopRange fhwaStopRange;
	FhwaStopRangeDTO fhwaStopRangeDTO;
	List<FhwaStopRange> fhwaStopRangeList;
	List<FhwaStopRangeDTO> fhwaStopRangeDTOList;

	Map<String, String> header;

	BigDecimal eqNrLow = new BigDecimal(1000);
	BigDecimal eqNrHigh = new BigDecimal(1500);
	String eqInit;
	String eqType;
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		fhwaStopRange = new FhwaStopRange();
		fhwaStopRange.setEquipmentInit("TEST");
		fhwaStopRange.setEquipmentNumberLow(eqNrLow);
		fhwaStopRange.setEquipmentNumberHigh(eqNrHigh);
		fhwaStopRange.setEquipmentType("T");

		fhwaStopRangeList = new ArrayList<>();
		fhwaStopRangeList.add(fhwaStopRange);
		fhwaStopRange = new FhwaStopRange();
		fhwaStopRange.setEquipmentNumberLow(BigDecimal.valueOf(1));
		fhwaStopRange.setEquipmentNumberHigh(BigDecimal.valueOf(999999));
		fhwaStopRange.setEquipmentInit("TEST");
		fhwaStopRange.setEquipmentType("Z");
		fhwaStopRangeList.add(fhwaStopRange);
		fhwaStopRange = new FhwaStopRange();
		fhwaStopRange.setEquipmentNumberLow(BigDecimal.valueOf(2000));
		fhwaStopRange.setEquipmentNumberHigh(BigDecimal.valueOf(3000));
		fhwaStopRange.setEquipmentInit("ABCD");
		fhwaStopRange.setEquipmentType("Z");
		fhwaStopRangeList.add(fhwaStopRange);

		fhwaStopRangeDTO = new FhwaStopRangeDTO();
		fhwaStopRangeDTO.setEquipmentInit("TEST");
		fhwaStopRangeDTO.setEquipmentNumberLow(eqNrLow);
		fhwaStopRangeDTO.setEquipmentNumberHigh(eqNrHigh);
		fhwaStopRangeDTO.setEquipmentType("T");

		fhwaStopRangeDTOList = new ArrayList<>();
		fhwaStopRangeDTOList.add(fhwaStopRangeDTO);
		fhwaStopRangeDTO = new FhwaStopRangeDTO();
		fhwaStopRangeDTO.setEquipmentNumberLow(BigDecimal.valueOf(1));
		fhwaStopRangeDTO.setEquipmentNumberHigh(BigDecimal.valueOf(999999));
		fhwaStopRangeDTO.setEquipmentInit("TEST");
		fhwaStopRangeDTO.setEquipmentType("Z");
		fhwaStopRangeDTOList.add(fhwaStopRangeDTO);
		fhwaStopRangeDTO = new FhwaStopRangeDTO();
		fhwaStopRangeDTO.setEquipmentNumberLow(BigDecimal.valueOf(2000));
		fhwaStopRangeDTO.setEquipmentNumberHigh(BigDecimal.valueOf(3000));
		fhwaStopRangeDTO.setEquipmentInit("ABCD");
		fhwaStopRangeDTO.setEquipmentType("Z");
		fhwaStopRangeDTOList.add(fhwaStopRangeDTO);

		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");
	}

	@AfterEach
	void tearDown() throws Exception {
		fhwaStopRangeDTO = null;
		fhwaStopRangeDTOList = null;
		fhwaStopRangeList = null;
		fhwaStopRange = null;
	}

	@Test
	void testGetAllFhwaStopRanges() {
		when(fhwaStopRangeRepository.findAll(eqInit, eqType, eqNrLow, eqNrHigh)).thenReturn(fhwaStopRangeList);
		List<FhwaStopRange> allRanges = fhwaStopRangeService.getAllFhwaStopRanges(eqInit, eqType, eqNrLow, eqNrHigh);
		assertEquals(allRanges, fhwaStopRangeList);
	}

	@Test
	void testGetAllFhwaException() {
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(fhwaStopRangeService.getAllFhwaStopRanges(eqInit, eqType, eqNrLow, eqNrHigh)));
		assertEquals("No Record Found under this search!", exception.getMessage());
	}
	@Test
	void testAddFhwaStopRange() {
		when(fhwaStopRangeRepository.findByRangeId(Mockito.any())).thenReturn(fhwaStopRange);
		when(fhwaStopRangeRepository.save(Mockito.any())).thenReturn(fhwaStopRange);
		FhwaStopRange addedRange = fhwaStopRangeService.addFhwaStopRange(fhwaStopRange, header);
		assertNotNull(addedRange);
	}

	@Test
	void testAddEqNrLowAndEqNrHighFhwaStopRange() {
		fhwaStopRange.setEquipmentNumberLow(BigDecimal.valueOf(1));
		fhwaStopRange.setEquipmentNumberHigh(BigDecimal.valueOf(999999));
		when(fhwaStopRangeRepository.existsByEquipmentTypeAndEquipmentInitAndEquipmentNumberLowAndEquipmentNumberHigh(Mockito.any(),
				Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(true);
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> when(fhwaStopRangeService
						.addFhwaStopRange(fhwaStopRange, header)));
		assertEquals("Full Range already exists for Equipment Type and Init.", exception.getMessage());
	}

	@Test
	void testAddFhwaRangeRecordNotAddedException() {

		FhwaStopRange obj = new FhwaStopRange();
		obj.setEquipmentNumberLow(BigDecimal.valueOf(50));
		obj.setEquipmentNumberHigh(BigDecimal.valueOf(49));
		obj.setEquipmentInit("TEST");
		obj.setEquipmentType("T");
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> when(fhwaStopRangeService
						.addFhwaStopRange(obj, header)));
		assertEquals("Equipment Low Number: 50 should be less than or equals to Equipment High Number: 49", exception.getMessage());


		FhwaStopRange obj2 = new FhwaStopRange();
		obj2.setEquipmentNumberLow(BigDecimal.valueOf(2000));
		obj2.setEquipmentNumberHigh(BigDecimal.valueOf(3000));
		obj2.setEquipmentInit("ABCD");
		obj2.setEquipmentType("Z");
		when(fhwaStopRangeRepository.existsByEquipmentTypeAndEquipmentInit(
				Mockito.any(), Mockito.any())).thenReturn(true);
		when(fhwaStopRangeRepository.findByEquipmentTypeAndEquipmentInit(Mockito.any(),Mockito.any())).thenReturn(fhwaStopRangeList);
		RecordNotAddedException exception2 = assertThrows(RecordNotAddedException.class,
				() -> when(fhwaStopRangeService
						.addFhwaStopRange(obj2, header)));
		assertEquals("The Range entered conflicts with other ranges for these association", exception2.getMessage());

//		FhwaStopRange obj3 = new FhwaStopRange();
//		obj3.setEquipmentNumberLow(BigDecimal.valueOf(1));
//		obj3.setEquipmentNumberHigh(BigDecimal.valueOf(999999));
//		obj3.setEquipmentInit("TEST");
//		obj3.setEquipmentType("Z");
//		when(fhwaStopRangeRepository.existsByEquipmentTypeAndEquipmentInitAndEquipmentNumberLowAndEquipmentNumberHigh(
//				obj3.getEquipmentType(), obj3.getEquipmentInit(),
//				obj3.getEquipmentNumberLow(), obj3.getEquipmentNumberHigh())).thenReturn(true);
//		RecordNotAddedException exception3 = assertThrows(RecordNotAddedException.class,
//				() -> when(fhwaStopRangeService
//						.addFhwaStopRange(obj3, header)));
//		assertEquals("Full Range already exists for Equipment Type and Init.", exception3.getMessage());
	}

	@ParameterizedTest
	@ValueSource(strings = {"!",""," "})
	void testUpdateFhwaStopRange(String uVersion) {
		FhwaStopRange fhwa = new FhwaStopRange();
		fhwa.setEquipmentInit("TEST");
		fhwa.setEquipmentNumberLow(BigDecimal.valueOf(2000));
		fhwa.setEquipmentNumberHigh(BigDecimal.valueOf(3000));
		fhwa.setEquipmentType("T");
		fhwaStopRangeList.add(fhwa);
		when(fhwaStopRangeRepository.existsById(Mockito.any())).thenReturn(true);
		fhwaStopRange.setUversion(uVersion);
		when(fhwaStopRangeRepository.findByRangeId(Mockito.any())).thenReturn(fhwaStopRange);
		when(fhwaStopRangeRepository.findByEquipmentTypeAndEquipmentInit(Mockito.any(), Mockito.any())).thenReturn(fhwaStopRangeList);
		when(fhwaStopRangeRepository.save(Mockito.any())).thenReturn(fhwaStopRange);
		FhwaStopRange updateRange = fhwaStopRangeService.updateFhwaStopRange(fhwaStopRange, header);
		assertNotNull(updateRange);
	}

	@Test
	void testupdateFhwaException() {
		when(fhwaStopRangeRepository.existsById(Mockito.any())).thenReturn(false);
		assertThrows(NoRecordsFoundException.class,
				() -> when(fhwaStopRangeService
						.updateFhwaStopRange(fhwaStopRange, header)));
	}
	@Test
	void testDeleteFhwaStopRange() {
		when(fhwaStopRangeRepository.existsById(any())).thenReturn(true);
		when(fhwaStopRangeRepository.findByRangeId(any())).thenReturn(fhwaStopRange);
		fhwaStopRangeService.deleteFhwaStopRange(fhwaStopRange);
	}

	@Test
	void testDeleteFhwaNoRecordsFoundException() {
		when(fhwaStopRangeRepository.existsById(any())).thenReturn(false);
		assertThrows(NoRecordsFoundException.class,
				() -> fhwaStopRangeService.deleteFhwaStopRange(fhwaStopRange));
	}

}
