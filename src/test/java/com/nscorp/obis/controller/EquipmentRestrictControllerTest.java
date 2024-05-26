package com.nscorp.obis.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

import com.nscorp.obis.domain.EquipmentRestrict;
import com.nscorp.obis.dto.EquipmentRestrictDTO;
import com.nscorp.obis.dto.mapper.EquipmentRestrictMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.SizeExceedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.EquipmentRestrictService;

class EquipmentRestrictControllerTest {

	@Mock
	EquipmentRestrictService equipmentRestrictService;

	@Mock
	EquipmentRestrictMapper equipmentRestrictMapper;

	@InjectMocks
	EquipmentRestrictController equipmentRestrictController;

	EquipmentRestrictDTO equipmentRestrictDto;
	EquipmentRestrict equipmentRestrict;
	List<EquipmentRestrict> equipmentRestrictList;
	List<EquipmentRestrictDTO> equipmentRestrictDtoList;

	Map<String, String> header;

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {

		MockitoAnnotations.initMocks(this);
		equipmentRestrict = new EquipmentRestrict();
		equipmentRestrict.setRestrictionId(41931813532L);
		equipmentRestrict.setEquipmentInit("Test");
		equipmentRestrict.setEquipmentType("C");
		equipmentRestrict.setEquipmentNumberLow(BigDecimal.valueOf(100));
		equipmentRestrict.setEquipmentNumberHigh(BigDecimal.valueOf(200));
		equipmentRestrict.setEquipmentRestrictionType("D");

		equipmentRestrictList = new ArrayList<>();
		equipmentRestrictList.add(equipmentRestrict);

		equipmentRestrictDto = new EquipmentRestrictDTO();
		equipmentRestrictDto.setRestrictionId(41931813532L);
		equipmentRestrictDto.setEquipmentInit("Test");
		equipmentRestrictDto.setEquipmentType("C");
		equipmentRestrictDto.setEquipmentNumberLow(BigDecimal.valueOf(100));
		equipmentRestrictDto.setEquipmentNumberHigh(BigDecimal.valueOf(200));
		equipmentRestrictDto.setEquipmentRestrictionType("D");

		equipmentRestrictDtoList = new ArrayList<>();
		equipmentRestrictDtoList.add(equipmentRestrictDto);


		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");
	}

	@AfterEach
	void tearDown() throws Exception {

		equipmentRestrictDto = null;
		equipmentRestrict = null;
		equipmentRestrictList = null;
		equipmentRestrictDtoList = null;
	}

	@Test
	void testGetAllEquipmentRestrictions() {
		when(equipmentRestrictService.getAllEquipRestrictions()).thenReturn(equipmentRestrictList);
		ResponseEntity<APIResponse<List<EquipmentRestrictDTO>>> eqRestrictList = equipmentRestrictController.getAllEquipmentRestrictions();
		assertNotNull(eqRestrictList.getBody());
	}

	@Test
	void testGetAllEquipmentRestrictionsNoRecordsFoundException() {
		when(equipmentRestrictService.getAllEquipRestrictions()).thenThrow(new NoRecordsFoundException());
		when(equipmentRestrictService.addEquipRestrictions(Mockito.any(),Mockito.any())).thenThrow(new NoRecordsFoundException());
		when(equipmentRestrictService.updateEquipRestriction(Mockito.any(),Mockito.any())).thenThrow(new NoRecordsFoundException());

		ResponseEntity<APIResponse<List<EquipmentRestrictDTO>>> eqRestrictlist = equipmentRestrictController.getAllEquipmentRestrictions();
		ResponseEntity<APIResponse<EquipmentRestrictDTO>> eqRestrictAdd = equipmentRestrictController.addEquipmentRestriction(Mockito.any(),Mockito.any());
		ResponseEntity<APIResponse<EquipmentRestrictDTO>> eqRestrictUpdate =  equipmentRestrictController.updateEquipmentRestriction(Mockito.any(),Mockito.any());
		assertEquals(eqRestrictlist.getStatusCodeValue(), 404);
		assertEquals(eqRestrictAdd.getStatusCodeValue(),404);
		assertEquals(eqRestrictUpdate.getStatusCodeValue(),404);
	}

	@Test
	void testGetAddUpdateEquipmentRestrictionsException() {
		when(equipmentRestrictService.getAllEquipRestrictions()).thenThrow(new RuntimeException());
		when(equipmentRestrictService.addEquipRestrictions(Mockito.any(),Mockito.any())).thenThrow(new RuntimeException());
		when(equipmentRestrictService.updateEquipRestriction(Mockito.any(),Mockito.any())).thenThrow(new RuntimeException());
		when(equipmentRestrictService.deleteEquipRestriction(Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<List<EquipmentRestrictDTO>>> eqRestrictlist = equipmentRestrictController.getAllEquipmentRestrictions();
		ResponseEntity<APIResponse<EquipmentRestrictDTO>> eqRestrictAdd = equipmentRestrictController.addEquipmentRestriction(Mockito.any(),Mockito.any());
		ResponseEntity<APIResponse<EquipmentRestrictDTO>> eqRestrictUpdate =  equipmentRestrictController.updateEquipmentRestriction(Mockito.any(),Mockito.any());
		ResponseEntity<List<APIResponse<EquipmentRestrictDTO>>> eqRestrictDelete =  equipmentRestrictController.deleteEquipmentRestriction(equipmentRestrictDtoList);

		assertEquals(eqRestrictlist.getStatusCodeValue(), 500);
		assertEquals(eqRestrictAdd.getStatusCodeValue(),500);
		assertEquals(eqRestrictUpdate.getStatusCodeValue(),500);
		assertEquals(eqRestrictDelete.getStatusCodeValue(),500);
	}

	@Test
	void testAddUpdateEquipmentRestrictionsSizeExceedException() {
		when(equipmentRestrictService.addEquipRestrictions(Mockito.any(),Mockito.any())).thenThrow(new SizeExceedException());
		when(equipmentRestrictService.updateEquipRestriction(Mockito.any(),Mockito.any())).thenThrow(new SizeExceedException());

		ResponseEntity<APIResponse<EquipmentRestrictDTO>> eqRestrictAdd = equipmentRestrictController.addEquipmentRestriction(Mockito.any(),Mockito.any());
		ResponseEntity<APIResponse<EquipmentRestrictDTO>> eqRestrictUpdate =  equipmentRestrictController.updateEquipmentRestriction(Mockito.any(),Mockito.any());

		assertEquals(eqRestrictAdd.getStatusCodeValue(),411);
		assertEquals(eqRestrictUpdate.getStatusCodeValue(),411);
	}

	@Test
	void testAddEquipmentRestrictionsRecordAlreadyExistsException() {
		when(equipmentRestrictService.addEquipRestrictions(Mockito.any(),Mockito.any())).thenThrow(new RecordAlreadyExistsException());
		ResponseEntity<APIResponse<EquipmentRestrictDTO>> eqRestrictAdd = equipmentRestrictController.addEquipmentRestriction(Mockito.any(),Mockito.any());
		assertEquals(eqRestrictAdd.getStatusCodeValue(),208);
	}

	@Test
	void testAddEquipmentRestrictionsRecordNotAddedException() {
		when(equipmentRestrictService.addEquipRestrictions(Mockito.any(),Mockito.any())).thenThrow(new RecordNotAddedException());
		ResponseEntity<APIResponse<EquipmentRestrictDTO>> eqRestrictAdd = equipmentRestrictController.addEquipmentRestriction(Mockito.any(),Mockito.any());
		assertEquals(eqRestrictAdd.getStatusCodeValue(),406);
	}

	@Test
	void testAddUpdateEquipmentRestrictionsNullPointerException() {
		when(equipmentRestrictService.addEquipRestrictions(Mockito.any(),Mockito.any())).thenThrow(new NullPointerException());
		when(equipmentRestrictService.updateEquipRestriction(Mockito.any(),Mockito.any())).thenThrow(new NullPointerException());
		ResponseEntity<APIResponse<EquipmentRestrictDTO>> eqRestrictAdd = equipmentRestrictController.addEquipmentRestriction(Mockito.any(),Mockito.any());
		ResponseEntity<APIResponse<EquipmentRestrictDTO>> eqRestrictUpdate =  equipmentRestrictController.updateEquipmentRestriction(Mockito.any(),Mockito.any());

		assertEquals(eqRestrictAdd.getStatusCodeValue(),400);
		assertEquals(eqRestrictUpdate.getStatusCodeValue(),400);
	}

	@Test
	void testAddEquipmentRestriction() {
		when(equipmentRestrictMapper.equipmentRestrictDTOToEquipmentRestrict(Mockito.any())).thenReturn(equipmentRestrict);
		when(equipmentRestrictService.addEquipRestrictions(Mockito.any(), Mockito.any())).thenReturn(equipmentRestrict);
		when(equipmentRestrictMapper.equipmentRestrictToEquipmentRestrictDTO(Mockito.any())).thenReturn(equipmentRestrictDto);
		ResponseEntity<APIResponse<EquipmentRestrictDTO>> addedEqRestriction = equipmentRestrictController.addEquipmentRestriction(equipmentRestrictDto,
				header);
		assertNotNull(addedEqRestriction.getBody());
		
	}

	@Test
	void testUpdateEquipmentRestriction() {
		when(equipmentRestrictMapper.equipmentRestrictDTOToEquipmentRestrict(Mockito.any())).thenReturn(equipmentRestrict);
		when(equipmentRestrictService.updateEquipRestriction(Mockito.any(), Mockito.any())).thenReturn(equipmentRestrict);
		when(equipmentRestrictMapper.equipmentRestrictToEquipmentRestrictDTO(Mockito.any())).thenReturn(equipmentRestrictDto);
		ResponseEntity<APIResponse<EquipmentRestrictDTO>> updatedEqRestriction = equipmentRestrictController.updateEquipmentRestriction(equipmentRestrictDto, header);
		assertNotNull(updatedEqRestriction.getBody());
	}

	@Test
	void testDeleteOverrideWeights() {
		Mockito.when(equipmentRestrictService.deleteEquipRestriction(equipmentRestrict)).thenReturn(equipmentRestrict);
		ResponseEntity<List<APIResponse<EquipmentRestrictDTO>>> responseEntity = equipmentRestrictController.deleteEquipmentRestriction(equipmentRestrictDtoList);
		assertEquals(responseEntity.getStatusCodeValue(),200);
	}
	
	@Test
	void testDeleteOverrideWeightsEmptyList() {
		ResponseEntity<List<APIResponse<EquipmentRestrictDTO>>> deletedEqRestriction = equipmentRestrictController.deleteEquipmentRestriction(Collections.emptyList());
		assertEquals(deletedEqRestriction.getStatusCodeValue(),500);
	}
	
	@Test
	void testDeleteOverrideWeightsNoRecordFoundException() {
		when(equipmentRestrictService.deleteEquipRestriction(Mockito.any())).thenThrow(new NoRecordsFoundException());
		ResponseEntity<List<APIResponse<EquipmentRestrictDTO>>> deleteEmbargo = equipmentRestrictController.deleteEquipmentRestriction(equipmentRestrictDtoList);
		assertEquals(deleteEmbargo.getStatusCodeValue(),500);
	}
}
