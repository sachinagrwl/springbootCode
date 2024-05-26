package com.nscorp.obis.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.nscorp.obis.domain.PoolEquipmentRange;
import com.nscorp.obis.dto.PoolEquipmentRangeDTO;
import com.nscorp.obis.dto.mapper.PoolEquipmentRangeMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.PoolEquipmentRangeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.*;

class PoolEquipmentRangeControllerTest {

	@InjectMocks
	PoolEquipmentRangeController poolEquipmentRangeController;

	@Mock
	PoolEquipmentRangeMapper poolEquipmentRangeMapper;

	@Mock
	PoolEquipmentRangeService poolEquipmentRangeService;

	PoolEquipmentRange poolEquipmentRange;
	PoolEquipmentRangeDTO poolEquipmentRangeDTO;
	List<PoolEquipmentRange> poolEquipmentRangeList;
	List<PoolEquipmentRangeDTO> poolEquipmentRangeDTOList;

	Map<String, String> header;

	BigDecimal eqNrLow = new BigDecimal(1000);
	BigDecimal eqNrHigh = new BigDecimal(1000);

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		poolEquipmentRange = new PoolEquipmentRange();
		poolEquipmentRange.setPoolRangeId(123456789L);
		poolEquipmentRange.setPoolId(12345678L);
		poolEquipmentRange.setEquipmentInit("TEST");
		poolEquipmentRange.setEquipmentLowNumber(eqNrLow);
		poolEquipmentRange.setEquipmentHighNumber(eqNrHigh);
		poolEquipmentRange.setEquipmentType("C");

		poolEquipmentRangeList = new ArrayList<>();
		poolEquipmentRangeList.add(poolEquipmentRange);

		poolEquipmentRangeDTO = new PoolEquipmentRangeDTO();
		poolEquipmentRangeDTO.setPoolRangeId(123456789L);
		poolEquipmentRangeDTO.setPoolId(12345678L);
		poolEquipmentRangeDTO.setEquipmentInit("TEST");
		poolEquipmentRangeDTO.setEquipmentLowNumber(eqNrLow);
		poolEquipmentRangeDTO.setEquipmentHighNumber(eqNrHigh);
		poolEquipmentRangeDTO.setEquipmentType("C");

		poolEquipmentRangeDTOList = new ArrayList<>();
		poolEquipmentRangeDTOList.add(poolEquipmentRangeDTO);

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
	void testGetAllEquipmentRanges() {
		when(poolEquipmentRangeService.getAllPoolEquipmentRanges()).thenReturn(poolEquipmentRangeList);
		ResponseEntity<APIResponse<List<PoolEquipmentRangeDTO>>> getRanges = poolEquipmentRangeController.getAllEquipmentRanges();
		assertEquals(getRanges.getStatusCodeValue(), 200);
	}

	@Test
	void testAddPoolEquipmentRange() {
		when(poolEquipmentRangeMapper.poolEquipmentRangeDtoToPoolEquipmentRange(Mockito.any())).thenReturn(poolEquipmentRange);
		when(poolEquipmentRangeService.addPoolEquipmentRange(Mockito.any(), Mockito.any())).thenReturn(poolEquipmentRange);
		when(poolEquipmentRangeMapper.poolEquipmentRangeToPoolEquipmentRangeDto(Mockito.any())).thenReturn(poolEquipmentRangeDTO);
		ResponseEntity<APIResponse<PoolEquipmentRangeDTO>> addedRange = poolEquipmentRangeController.addPoolEquipmentRange(poolEquipmentRangeDTO,
				header);
		assertNotNull(addedRange.getBody());
	}

	@Test
	void testUpdatePoolEquipmentRange() {
		when(poolEquipmentRangeMapper.poolEquipmentRangeDtoToPoolEquipmentRange(Mockito.any())).thenReturn(poolEquipmentRange);
		when(poolEquipmentRangeService.updatePoolEquipmentRange(Mockito.any(), Mockito.any())).thenReturn(poolEquipmentRange);
		when(poolEquipmentRangeMapper.poolEquipmentRangeToPoolEquipmentRangeDto(Mockito.any())).thenReturn(poolEquipmentRangeDTO);
		ResponseEntity<APIResponse<PoolEquipmentRangeDTO>> updateRange = poolEquipmentRangeController.updatePoolEquipmentRange(poolEquipmentRangeDTO,
				header);
		assertNotNull(updateRange.getBody());
	}

	@Test
	void testDeleteRange() {
		when(poolEquipmentRangeService.deletePoolEquipmentRange(Mockito.any())).thenReturn(poolEquipmentRange);
		ResponseEntity<List<APIResponse<PoolEquipmentRangeDTO>>> deleteList = poolEquipmentRangeController.deleteRange(poolEquipmentRangeDTOList);
		assertEquals(deleteList.getStatusCodeValue(),200);
	}

	@SuppressWarnings("unchecked")
	@Test
	void testDeleteRangeEmptyDTOList(){
		ResponseEntity<List<APIResponse<PoolEquipmentRangeDTO>>> responseEntity = poolEquipmentRangeController.deleteRange(Collections.EMPTY_LIST);
		assertEquals(responseEntity.getStatusCodeValue(),500);
	}

	@Test
	void testEquipmentRangeNoRecordsFoundException() {
		when(poolEquipmentRangeService.getAllPoolEquipmentRanges()).thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<List<PoolEquipmentRangeDTO>>> getRanges = poolEquipmentRangeController.getAllEquipmentRanges();
		assertEquals(getRanges.getStatusCodeValue(), 404);

		when(poolEquipmentRangeService.addPoolEquipmentRange(Mockito.any(), Mockito.any())).thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<PoolEquipmentRangeDTO>> addRanges = poolEquipmentRangeController.addPoolEquipmentRange(poolEquipmentRangeDTO,
				header);
		assertEquals(getRanges.getStatusCodeValue(), 404);

		when(poolEquipmentRangeService.updatePoolEquipmentRange(Mockito.any(), Mockito.any())).thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<PoolEquipmentRangeDTO>> updateRanges = poolEquipmentRangeController.updatePoolEquipmentRange(poolEquipmentRangeDTO,
				header);
		assertEquals(getRanges.getStatusCodeValue(), 404);

		when(poolEquipmentRangeService.deletePoolEquipmentRange(any())).thenThrow(new NoRecordsFoundException());
		ResponseEntity<List<APIResponse<PoolEquipmentRangeDTO>>> responseEntity = poolEquipmentRangeController.deleteRange(poolEquipmentRangeDTOList);
		assertEquals(responseEntity.getStatusCodeValue(),500);
	}

	@Test
	void testRangeNullPointerException() {
		when(poolEquipmentRangeService.addPoolEquipmentRange(Mockito.any(), Mockito.any())).thenThrow(new NullPointerException());
		ResponseEntity<APIResponse<PoolEquipmentRangeDTO>> addRanges = poolEquipmentRangeController.addPoolEquipmentRange(poolEquipmentRangeDTO,
				header);
		assertEquals(addRanges.getStatusCodeValue(),400);

		when(poolEquipmentRangeService.updatePoolEquipmentRange(Mockito.any(), Mockito.any())).thenThrow(new NullPointerException());
		ResponseEntity<APIResponse<PoolEquipmentRangeDTO>> updateRanges = poolEquipmentRangeController.updatePoolEquipmentRange(poolEquipmentRangeDTO,
				header);
		assertEquals(updateRanges.getStatusCodeValue(),400);
	}

	@Test
	void testRangeRecordNotAddedException() {
		when(poolEquipmentRangeService.addPoolEquipmentRange(Mockito.any(), Mockito.any())).thenThrow(new RecordNotAddedException());
		ResponseEntity<APIResponse<PoolEquipmentRangeDTO>> addRanges = poolEquipmentRangeController.addPoolEquipmentRange(poolEquipmentRangeDTO,
				header);
		assertEquals(addRanges.getStatusCodeValue(),404);

		when(poolEquipmentRangeService.updatePoolEquipmentRange(Mockito.any(), Mockito.any())).thenThrow(new RecordNotAddedException());
		ResponseEntity<APIResponse<PoolEquipmentRangeDTO>> updateRanges = poolEquipmentRangeController.updatePoolEquipmentRange(poolEquipmentRangeDTO,
				header);
		assertEquals(updateRanges.getStatusCodeValue(),404);
	}

	@Test
	void testEquipmentRangeException() {
		when(poolEquipmentRangeService.getAllPoolEquipmentRanges()).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<List<PoolEquipmentRangeDTO>>> getRanges = poolEquipmentRangeController.getAllEquipmentRanges();
		assertEquals(getRanges.getStatusCodeValue(), 500);

		when(poolEquipmentRangeService.addPoolEquipmentRange(Mockito.any(), Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<PoolEquipmentRangeDTO>> addRanges = poolEquipmentRangeController.addPoolEquipmentRange(poolEquipmentRangeDTO,
				header);
		assertEquals(getRanges.getStatusCodeValue(), 500);

		when(poolEquipmentRangeService.updatePoolEquipmentRange(Mockito.any(), Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<PoolEquipmentRangeDTO>> updateRanges = poolEquipmentRangeController.updatePoolEquipmentRange(poolEquipmentRangeDTO,
				header);
		assertEquals(getRanges.getStatusCodeValue(), 500);

		when(poolEquipmentRangeService.deletePoolEquipmentRange(any())).thenThrow(new RuntimeException());
		ResponseEntity<List<APIResponse<PoolEquipmentRangeDTO>>> responseEntity = poolEquipmentRangeController.deleteRange(poolEquipmentRangeDTOList);
		assertEquals(responseEntity.getStatusCodeValue(),500);
	}

}
