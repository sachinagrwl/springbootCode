package com.nscorp.obis.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.nscorp.obis.domain.EquipmentOverrideTareWeight;
import com.nscorp.obis.dto.EquipmentOverrideTareWeightDTO;
import com.nscorp.obis.dto.mapper.EquipmentOverrideTareWeightMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.SizeExceedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.EquipmentOverrideTareWeightService;

class EquipmentOverrideTareWeightControllerTest {
	
	@InjectMocks
	EquipmentOverrideTareWeightController eqOverrideController;
	
	@Mock
	EquipmentOverrideTareWeightMapper eqOverrideMapper;
	
	@Mock
	EquipmentOverrideTareWeightService eqOverrideService;
	
	EquipmentOverrideTareWeight eqTareWeight;
	EquipmentOverrideTareWeightDTO eqTareWeightDto;
	List<EquipmentOverrideTareWeight> eqTareWeightList;
	List<EquipmentOverrideTareWeightDTO> eqTareWeightDtoList;
	
	Map<String, String> header;
	
	BigDecimal eqNrLow = new BigDecimal(1000);
	BigDecimal eqNrHigh = new BigDecimal(1000);
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
	void testGetAllOverrideWeights() {
		when(eqOverrideService.getAllTareWeights(eqInit, eqNrHigh, eqNrHigh, eqType, eqOverrideWgt)).thenReturn(eqTareWeightList);
		ResponseEntity<APIResponse<List<EquipmentOverrideTareWeightDTO>>> getTareWeight = eqOverrideController.getAllOverrideWeights(eqInit, eqNrLow, eqNrHigh, eqType, eqOverrideWgt);
		assertEquals(getTareWeight.getStatusCodeValue(), 200);
	}
	
	@Test
	void testGetAllOverrideWeightsNoRecordsFoundException() {
		when(eqOverrideService.getAllTareWeights(eqInit, eqNrHigh, eqNrHigh, eqType, eqOverrideWgt)).thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<List<EquipmentOverrideTareWeightDTO>>> getTareWeight = eqOverrideController.getAllOverrideWeights(eqInit, eqNrLow, eqNrHigh, eqType, eqOverrideWgt);
		assertEquals(getTareWeight.getStatusCodeValue(), 404);
	}
	
	@Test
	void testGetAllOverrideWeightsException() {
		when(eqOverrideService.getAllTareWeights(eqInit, eqNrHigh, eqNrHigh, eqType, eqOverrideWgt)).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<List<EquipmentOverrideTareWeightDTO>>> getTareWeight = eqOverrideController.getAllOverrideWeights(eqInit, eqNrLow, eqNrHigh, eqType, eqOverrideWgt);
		assertEquals(getTareWeight.getStatusCodeValue(), 500);
	}

	@Test
	void testDeleteOverrideWeights() {
		when(eqOverrideMapper.EquipmentOverrideTareWeightDTOToEquipmentOverrideTareWeight(any())).thenReturn(eqTareWeight);
		when(eqOverrideService.deleteOverrideWeights(any())).thenReturn(eqTareWeight);
		when(eqOverrideMapper.EquipmentOverrideTareWeightToEquipmentOverrideTareWeightDTO(any())).thenReturn(eqTareWeightDto);
		ResponseEntity<List<APIResponse<EquipmentOverrideTareWeightDTO>>> responseEntity = eqOverrideController.deleteOverrideWeights(eqTareWeightDtoList);
		assertEquals(responseEntity.getStatusCodeValue(),200);
	}

	@SuppressWarnings("unchecked")
	@Test
	void testDeleteOverrideWeightsEmptyDTOList(){
		ResponseEntity<List<APIResponse<EquipmentOverrideTareWeightDTO>>> responseEntity = eqOverrideController.deleteOverrideWeights(Collections.EMPTY_LIST);
		assertEquals(responseEntity.getStatusCodeValue(),500);
	}

	@Test
	void testDeleteOverrideWeightsException(){
		when(eqOverrideService.deleteOverrideWeights(any())).thenThrow(new RuntimeException());
		ResponseEntity<List<APIResponse<EquipmentOverrideTareWeightDTO>>> responseEntity = eqOverrideController.deleteOverrideWeights(eqTareWeightDtoList);
		assertEquals(responseEntity.getStatusCodeValue(),500);
	}

	@Test
	void testDeleteOverrideWeightsNoRecordFoundException(){
		when(eqOverrideService.deleteOverrideWeights(any())).thenThrow(new NoRecordsFoundException());
		ResponseEntity<List<APIResponse<EquipmentOverrideTareWeightDTO>>> responseEntity = eqOverrideController.deleteOverrideWeights(eqTareWeightDtoList);
		assertEquals(responseEntity.getStatusCodeValue(),500);
	}

	@Test
	void testAddOverrideWeightsException() {
		when(eqOverrideMapper.EquipmentOverrideTareWeightDTOToEquipmentOverrideTareWeight(Mockito.any())).thenReturn(eqTareWeight);
		when(eqOverrideService.addOverrideTareWeight(Mockito.any(), Mockito.any())).thenReturn(eqTareWeight);
		when(eqOverrideMapper.EquipmentOverrideTareWeightToEquipmentOverrideTareWeightDTO(Mockito.any())).thenReturn(eqTareWeightDto);
		ResponseEntity<APIResponse<EquipmentOverrideTareWeightDTO>> addedOverrideWeight = eqOverrideController.addEqOverride(eqTareWeightDto,
				header);
		assertNotNull(addedOverrideWeight.getBody());
	}

	@Test
	void testOverrideWeightsNoRecordsFoundException() {
		when(eqOverrideService.addOverrideTareWeight(Mockito.any(), Mockito.any())).thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<EquipmentOverrideTareWeightDTO>> addedOverrideWeight = eqOverrideController.addEqOverride(eqTareWeightDto,
				header);
		assertEquals(addedOverrideWeight.getStatusCodeValue(),404);
		when(eqOverrideService.updateEquipmentOverrideTareWeight(Mockito.any(), Mockito.any())).thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<EquipmentOverrideTareWeightDTO>> updateOverrideWeight = eqOverrideController.updateEquipmentOverrideTareWeight(eqTareWeightDto,
				header);
		assertEquals(updateOverrideWeight.getStatusCodeValue(),404);
	}

	@Test
	void testOverrideWeightsSizeExceedException() {
		when(eqOverrideService.addOverrideTareWeight(Mockito.any(), Mockito.any())).thenThrow(new SizeExceedException());
		ResponseEntity<APIResponse<EquipmentOverrideTareWeightDTO>> addedOverrideWeight = eqOverrideController.addEqOverride(eqTareWeightDto,
				header);
		assertEquals(addedOverrideWeight.getStatusCodeValue(),411);
	}

	@Test
	void testOverrideWeightsRecordAlreadyExistsException() {
		when(eqOverrideService.addOverrideTareWeight(Mockito.any(), Mockito.any())).thenThrow(new RecordAlreadyExistsException());
		ResponseEntity<APIResponse<EquipmentOverrideTareWeightDTO>> addedOverrideWeight = eqOverrideController.addEqOverride(eqTareWeightDto,
				header);
		assertEquals(addedOverrideWeight.getStatusCodeValue(),208);
	}

	@Test
	void testOverrideWeightsRecordNotAddedException() {
		when(eqOverrideService.addOverrideTareWeight(Mockito.any(), Mockito.any())).thenThrow(new RecordNotAddedException());
		ResponseEntity<APIResponse<EquipmentOverrideTareWeightDTO>> addedOverrideWeight = eqOverrideController.addEqOverride(eqTareWeightDto,
				header);
		assertEquals(addedOverrideWeight.getStatusCodeValue(),406);
		when(eqOverrideService.updateEquipmentOverrideTareWeight(Mockito.any(), Mockito.any())).thenThrow(new RecordNotAddedException());
		ResponseEntity<APIResponse<EquipmentOverrideTareWeightDTO>> updateOverrideWeight = eqOverrideController.updateEquipmentOverrideTareWeight(eqTareWeightDto,
				header);
		assertEquals(updateOverrideWeight.getStatusCodeValue(),208);
	}

	@Test
	void testOverrideWeightsNullPointerException() {
		when(eqOverrideService.addOverrideTareWeight(Mockito.any(), Mockito.any())).thenThrow(new NullPointerException());
		ResponseEntity<APIResponse<EquipmentOverrideTareWeightDTO>> addedOverrideWeight = eqOverrideController.addEqOverride(eqTareWeightDto,
				header);
		assertEquals(addedOverrideWeight.getStatusCodeValue(),400);

	}

	@Test
	void testOverrideWeightsException() {
		when(eqOverrideService.addOverrideTareWeight(Mockito.any(), Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<EquipmentOverrideTareWeightDTO>> addedOverrideWeight = eqOverrideController.addEqOverride(eqTareWeightDto,
				header);
		assertEquals(addedOverrideWeight.getStatusCodeValue(), 500);
		when(eqOverrideService.updateEquipmentOverrideTareWeight(Mockito.any(), Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<EquipmentOverrideTareWeightDTO>> updateOverrideWeight = eqOverrideController.updateEquipmentOverrideTareWeight(eqTareWeightDto,
				header);
		assertEquals(updateOverrideWeight.getStatusCodeValue(), 500);
	}
	
	@Test
	void testUpdateOverrideWeights() {
		when(eqOverrideService.updateEquipmentOverrideTareWeight(Mockito.any(), Mockito.any())).thenReturn(eqTareWeight);
		ResponseEntity<APIResponse<EquipmentOverrideTareWeightDTO>> updateTareWeight = eqOverrideController.updateEquipmentOverrideTareWeight(eqTareWeightDto,
				header);
		assertEquals(updateTareWeight.getStatusCodeValue(), 200);
	}

}
