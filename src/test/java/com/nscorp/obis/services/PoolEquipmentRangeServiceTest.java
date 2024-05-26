package com.nscorp.obis.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.nscorp.obis.domain.*;
import com.nscorp.obis.dto.PoolEquipmentRangeDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.RecordNotDeletedException;
import com.nscorp.obis.repository.PoolEquipmentConflictRangeRepository;
import com.nscorp.obis.repository.PoolEquipmentRangeRepository;
import com.nscorp.obis.repository.PoolRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
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

class PoolEquipmentRangeServiceTest {

	@InjectMocks
	PoolEquipmentRangeServiceImpl poolEquipmentRangeService;

	@Mock
	PoolEquipmentRangeRepository poolEquipmentRangeRepository;

	@Mock
	PoolEquipmentConflictRangeRepository conflictRangeRepository;

	@Mock
	PoolRepository poolRepository;

	PoolEquipmentRange poolEquipmentRange;

	PoolEquipmentConflictRange poolEquipmentConflictRange;

	Pool pool;
	Terminal terminal;
	PoolEquipmentRangeDTO poolEquipmentRangeDTO;
	List<PoolEquipmentRange> poolEquipmentRangeList;
	List<PoolEquipmentRangeDTO> poolEquipmentRangeDTOList;

	Map<String, String> header;

	BigDecimal eqNrLow = new BigDecimal(1000);
	BigDecimal eqNrHigh = new BigDecimal(10000);

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		poolEquipmentRange = new PoolEquipmentRange();
		poolEquipmentRange.setPoolRangeId(123456789L);
		poolEquipmentRange.setPoolId(14462411580444L);
		poolEquipmentRange.setEquipmentInit("TEST");
		poolEquipmentRange.setEquipmentLowNumber(eqNrLow);
		poolEquipmentRange.setEquipmentHighNumber(eqNrHigh);
		poolEquipmentRange.setEquipmentType("C");

		poolEquipmentRangeList = new ArrayList<>();
		poolEquipmentRangeList.add(poolEquipmentRange);

		poolEquipmentRangeDTO = new PoolEquipmentRangeDTO();
		poolEquipmentRangeDTO.setPoolRangeId(123456789L);
		poolEquipmentRangeDTO.setPoolId(14462411580444L);
		poolEquipmentRangeDTO.setEquipmentInit("TEST");
		poolEquipmentRangeDTO.setEquipmentLowNumber(eqNrLow);
		poolEquipmentRangeDTO.setEquipmentHighNumber(eqNrHigh);
		poolEquipmentRangeDTO.setEquipmentType("C");

		poolEquipmentRangeDTOList = new ArrayList<>();
		poolEquipmentRangeDTOList.add(poolEquipmentRangeDTO);

		pool = new Pool();
		pool.setPoolName("Test");
		terminal = new Terminal();
		terminal.setTerminalName("Test");
		poolEquipmentConflictRange = new PoolEquipmentConflictRange();
		poolEquipmentConflictRange.setPool(pool);
		poolEquipmentConflictRange.setRangeType("C");
		poolEquipmentConflictRange.setRangeInit("TEST");
		poolEquipmentConflictRange.setRangeLowNumber(eqNrLow);
		poolEquipmentConflictRange.setRangeHighNumber(eqNrHigh);
		poolEquipmentConflictRange.setTerminal(terminal);

		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");
	}

	@AfterEach
	void tearDown() throws Exception {
		poolEquipmentRange = null;
		poolEquipmentRangeDTO = null;
		poolEquipmentRangeList = null;
		poolEquipmentRangeDTOList = null;
	}

	@Test
	void testGetAllPoolEquipmentRanges() {
		when(poolEquipmentRangeRepository.findAll()).thenReturn(poolEquipmentRangeList);
		List<PoolEquipmentRange> getAllRanges = poolEquipmentRangeService.getAllPoolEquipmentRanges();
		assertEquals(getAllRanges, poolEquipmentRangeList);
	}

	@Test
	void testGetAllTareWeightsException() {
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(poolEquipmentRangeService.getAllPoolEquipmentRanges()));
		assertEquals("No Record Found!", exception.getMessage());
	}

	@Test
	void testAddPoolEquipmentRange() {
		when(poolRepository.existsByPoolId(Mockito.any())).thenReturn(true);
		when(poolEquipmentRangeRepository.existsByPoolRangeId(Mockito.any())).thenReturn(false);
		when(conflictRangeRepository.existsByPoolRangeIdAndRangeTypeAndRangeInitAndRangeLowNumberAndRangeHighNumber(
				Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(false);
		when(poolEquipmentRangeRepository.save(Mockito.any())).thenReturn(poolEquipmentRange);
		PoolEquipmentRange addedRange = poolEquipmentRangeService.addPoolEquipmentRange(poolEquipmentRange, header);
		Assertions.assertNotNull(addedRange);
	}

	@Test
	void testAddPoolEquipmentRangeRecordNotAddedException() {
		when(poolRepository.existsByPoolId(Mockito.any())).thenReturn(true);
		when(poolEquipmentRangeRepository.existsByPoolRangeId(Mockito.any())).thenReturn(true);
		assertThrows(RecordNotAddedException.class,
				() -> when(poolEquipmentRangeService
						.addPoolEquipmentRange(poolEquipmentRange, header)));

		PoolEquipmentRange obj = new PoolEquipmentRange();
		obj.setEquipmentLowNumber(new BigDecimal(50));
		obj.setEquipmentHighNumber(new BigDecimal(40));
		when(poolRepository.existsByPoolId(Mockito.any())).thenReturn(true);
		when(poolEquipmentRangeRepository.existsByPoolRangeId(Mockito.any())).thenReturn(false);
		assertThrows(RecordNotAddedException.class,
				() -> when(poolEquipmentRangeService
						.addPoolEquipmentRange(obj, header)));
	}

	@Test
	void testAddPoolEquipmentRangeOverlappingError() {
		PoolEquipmentRange obj2 = new PoolEquipmentRange();
		obj2.setPoolId(14462411580444L);
		obj2.setEquipmentInit("TEST");
		obj2.setEquipmentLowNumber(BigDecimal.valueOf(8000));
		obj2.setEquipmentHighNumber(BigDecimal.valueOf(10001));
		obj2.setEquipmentType("C");
		when(poolRepository.existsByPoolId(Mockito.any())).thenReturn(true);
		when(poolEquipmentRangeRepository.existsByPoolRangeId(Mockito.any())).thenReturn(false);
		when(poolEquipmentRangeRepository.existsByPoolIdAndEquipmentInitAndEquipmentType(Mockito.any(), Mockito.any(),
				Mockito.any())).thenReturn(true);
		when(poolEquipmentRangeRepository.findByPoolIdAndEquipmentInitAndEquipmentType(Mockito.any(), Mockito.any(),
				Mockito.any())).thenReturn(poolEquipmentRangeList);
		RecordNotAddedException exception1 = assertThrows(RecordNotAddedException.class,
				() -> when(poolEquipmentRangeService
						.addPoolEquipmentRange(obj2, header)));
		assertEquals("Equipment Init and Equipment Number Range are overlapping with existing records", exception1.getMessage());
	}

	@Test
	void testAddPoolEquipmentRangeConflict() {
		when(poolRepository.existsByPoolId(Mockito.any())).thenReturn(true);
		when(poolEquipmentRangeRepository.existsByPoolRangeId(Mockito.any())).thenReturn(false);
		when(poolEquipmentRangeRepository.existsByPoolIdAndEquipmentInitAndEquipmentType(
				Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(false);
		when(conflictRangeRepository.existsByPoolRangeIdAndRangeTypeAndRangeInitAndRangeLowNumberAndRangeHighNumber(
				Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(true);
		when(conflictRangeRepository.findByPoolRangeIdAndRangeTypeAndRangeInitAndRangeLowNumberAndRangeHighNumber(
				Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(poolEquipmentConflictRange);
		pool = mock(Pool.class);
		when(poolRepository.getByPoolId(Mockito.any())).thenReturn(pool);
		assertThrows(RecordNotAddedException.class,
				() -> when(poolEquipmentRangeService
						.addPoolEquipmentRange(poolEquipmentRange, header)));
	}

	@Test
	void testAddPoolEquipmentRangeNoRecordFoundException() {
		when(poolRepository.existsByPoolId(Mockito.any())).thenReturn(false);
		when(poolEquipmentRangeRepository.existsByPoolRangeId(Mockito.any())).thenReturn(false);
		assertThrows(NoRecordsFoundException.class,
				() -> when(poolEquipmentRangeService
						.addPoolEquipmentRange(poolEquipmentRange, header)));
	}

	@Test
	void testAddPoolEquipmentRangeNullPointerException() {
		when(poolEquipmentRangeRepository.existsByPoolRangeId(Mockito.any())).thenReturn(false);
		Map<String, String> headerTest;
		headerTest = new HashMap<String, String>();
		headerTest.put("userid", "Test");
		assertThrows(NullPointerException.class,
				() -> when(poolEquipmentRangeService
						.addPoolEquipmentRange(poolEquipmentRange, headerTest)));
	}

	@ParameterizedTest
	@ValueSource(strings = {"!",""," "})
	void testUpdatePoolEquipmentRange(String uVersion) {
		when(poolEquipmentRangeRepository.existsByPoolRangeIdAndUversion(Mockito.any(), Mockito.any())).thenReturn(true);
		when(poolRepository.existsByPoolId(Mockito.any())).thenReturn(true);
		when(conflictRangeRepository.existsByPoolRangeIdAndRangeTypeAndRangeInitAndRangeLowNumberAndRangeHighNumber(
				Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(false);

		poolEquipmentRange.setUversion(uVersion);
		when(poolEquipmentRangeRepository.findByPoolRangeIdAndUversion(Mockito.any(), Mockito.any())).thenReturn(poolEquipmentRange);
		when(poolEquipmentRangeRepository.save(Mockito.any())).thenReturn(poolEquipmentRange);
		PoolEquipmentRange updateRange = poolEquipmentRangeService.updatePoolEquipmentRange(poolEquipmentRange, header);
		Assertions.assertNotNull(updateRange);
	}

	@Test
	void testUpdateOverrideWeightException() {
		when(poolEquipmentRangeRepository.existsByPoolRangeIdAndUversion(Mockito.any(), Mockito.any())).thenReturn(false);
		assertThrows(NoRecordsFoundException.class,
				() -> when(poolEquipmentRangeService
						.updatePoolEquipmentRange(poolEquipmentRange, header)));
	}

	@Test
	void testDeletePoolEquipmentRange() {
		when(poolEquipmentRangeRepository.existsByPoolRangeIdAndUversion(any(), any())).thenReturn(true);
		when(poolEquipmentRangeRepository.findByPoolRangeIdAndUversion(any(), any())).thenReturn(poolEquipmentRange);
		PoolEquipmentRange rangeDelete = poolEquipmentRangeService.deletePoolEquipmentRange(poolEquipmentRange);
		assertEquals(rangeDelete,poolEquipmentRange);
	}

	@Test
	void testDeleteRecordNotDeletedException() {
		when(poolEquipmentRangeRepository.existsByPoolRangeIdAndUversion(any(), any())).thenReturn(false);
		assertThrows(RecordNotDeletedException.class,
				() -> poolEquipmentRangeService.deletePoolEquipmentRange(poolEquipmentRange));
	}

}
