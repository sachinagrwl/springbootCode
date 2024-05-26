package com.nscorp.obis.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
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

import com.nscorp.obis.domain.EquipmentRackRange;
import com.nscorp.obis.dto.EquipmentRackRangeDTO;
import com.nscorp.obis.dto.mapper.EquipmentRackRangeMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.EquipmentRackRangeService;


public class EquipmentRackRangeControllerTest {
	
	@Mock
	EquipmentRackRangeService equiService;

	@Mock
	EquipmentRackRangeMapper equiMapper;

	@InjectMocks
	EquipmentRackRangeController equiController;

	EquipmentRackRange equi;
	EquipmentRackRangeDTO equiDTO;
	List<EquipmentRackRange> equiList;
	List<EquipmentRackRangeDTO> equiDtoList;
	Map<String, String> header;
	BigDecimal eqNrLow = new BigDecimal(1000);
	BigDecimal eqNrHigh = new BigDecimal(1001);

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		equi = new EquipmentRackRange();
		equi.setEquipInd(null);;
		equi.setAarType(null);
		equi.setEquipRackRangeId(444L);
		equi.setEquipInit("AAA");;
		equi.setEquipLowNbr(eqNrLow);
		equi.setEquipHighNbr(eqNrHigh);
		equi.setEquipType("C");

		equiList = new ArrayList<>();

		equiList.add(equi);

		equiDTO = new EquipmentRackRangeDTO();
		equiDTO.setEquipInd(null);;
		equiDTO.setAarType(null);
		equiDTO.setEquipRackRangeId(444L);
		equiDTO.setEquipInit("AAA");;
		equiDTO.setEquipLowNbr(1L);
		equiDTO.setEquipHighNbr(2L);
		equiDTO.setEquipType("C");

		equiDtoList = new ArrayList<>();
		equiDtoList.add(equiDTO);

		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");
	}

	@AfterEach
	void tearDown() throws Exception {
		equi = null;
		equiList = null;
		equiDTO = null;
		equiDtoList = null;
	}

	
	@Test
	void testGetAllEquipmentRackRange() {
		when(equiService.getAllTables()).thenReturn(equiList);
		ResponseEntity<APIResponse<List<EquipmentRackRangeDTO>>> equiList = equiController.getAllTables();
		assertNotNull(equiList.getBody());
	}
	@Test
	void testEquipmentRackRangeNoRecordsFoundException() {
		when(equiService.getAllTables()).thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<List<EquipmentRackRangeDTO>>> equiList = equiController.getAllTables();
		assertEquals(equiList.getStatusCodeValue(),404); 
	}
	@Test
	void testEquipmentRackRangeException() {
		when(equiService.getAllTables()).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<List<EquipmentRackRangeDTO>>> equiList = equiController.getAllTables();
		assertEquals(equiList.getStatusCodeValue(),500);
	}
	
	
	
	@Test
	void testAddEqRackRange() {
		when(equiMapper.equipmentRackRangeDTOToEquipmentRackRange(Mockito.any())).thenReturn(equi);
		when(equiService.addEquipmentRackRange(Mockito.any(), Mockito.any())).thenReturn(equi);
		when(equiMapper.equipmentRackRangeToEquipmentRackRangeDTO(Mockito.any())).thenReturn(equiDTO);
		ResponseEntity<APIResponse<EquipmentRackRangeDTO>> addedEqRackRange = equiController.addEquipmentRackRange(equiDTO, header);
		assertNotNull(addedEqRackRange.getBody());
	}
	@Test
	void testAddeqRackNoRecordsFoundException() {
		when(equiService.addEquipmentRackRange(Mockito.any(), Mockito.any())).thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<EquipmentRackRangeDTO>> addedEqRackRange = equiController.addEquipmentRackRange(equiDTO, header);
		assertEquals(addedEqRackRange.getStatusCodeValue(),404);
	}
	@Test
	void testAddeqRackRecordAlreadyExistsException() {
		when(equiService.addEquipmentRackRange(Mockito.any(), Mockito.any())).thenThrow(new RecordAlreadyExistsException());
		ResponseEntity<APIResponse<EquipmentRackRangeDTO>> addedEqRackRange = equiController.addEquipmentRackRange(equiDTO, header);
		assertEquals(addedEqRackRange.getStatusCodeValue(),208);
	}
	@Test
	void testAddeqRackException() {
		when(equiService.addEquipmentRackRange(Mockito.any(), Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<EquipmentRackRangeDTO>> addedEqRackRange = equiController.addEquipmentRackRange(equiDTO, header);
		assertEquals(addedEqRackRange.getStatusCodeValue(),500);
	}
	
	
	
	@Test
	void testUpdateEquipmentRackRange() {
		when(equiMapper.equipmentRackRangeDTOToEquipmentRackRange(Mockito.any())).thenReturn(equi);
		when(equiService.updateEquipmentRackRange(Mockito.any(), Mockito.any())).thenReturn(equi);
		when(equiMapper.equipmentRackRangeToEquipmentRackRangeDTO(Mockito.any())).thenReturn(equiDTO);
		ResponseEntity<APIResponse<EquipmentRackRangeDTO>> updatedEquipmentRackRange = equiController.putEquipmentRackRange(equiDTO, header);
		assertNotNull(updatedEquipmentRackRange.getBody());
	}
	@Test
	void testUpdateEquipmentRackRangeNoRecordsFoundException() {
		when(equiService.updateEquipmentRackRange(Mockito.any(), Mockito.any())).thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<EquipmentRackRangeDTO>> updatedEquipmentRackRange = equiController.putEquipmentRackRange(equiDTO, header);
		assertEquals(updatedEquipmentRackRange.getStatusCodeValue(),404);
	}
	@Test
	void testtUpdateEquipmentRackRangeException() {
		when(equiService.updateEquipmentRackRange(Mockito.any(), Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<EquipmentRackRangeDTO>> updatedEquipmentRackRange = equiController.putEquipmentRackRange(equiDTO, header);
		assertEquals(updatedEquipmentRackRange.getStatusCodeValue(),500);
	
	}
	
	
	@Test
	void testDeleteEquipmentRackRange() {
		when(equiMapper.equipmentRackRangeDTOToEquipmentRackRange(Mockito.any())).thenReturn(equi);
		equiService.deleteEquipmentRackRange(Mockito.any());
		when(equiMapper.equipmentRackRangeToEquipmentRackRangeDTO(Mockito.any())).thenReturn(equiDTO);
		ResponseEntity<List<APIResponse<EquipmentRackRangeDTO>>> deleteList = equiController.deleteEquipmentRackRangeDto(equiDtoList);
		assertEquals(deleteList.getStatusCodeValue(), 200);
	}
	@Test
	void testErrorDeleteEquipmentRackRangeException() {
		EquipmentRackRange equipmentRackRange1 = new EquipmentRackRange();
		equipmentRackRange1.setEquipInd(null);
		equipmentRackRange1.setAarType(null);
		equipmentRackRange1.setEquipRackRangeId(444L);
		equipmentRackRange1.setEquipInit("AAA");
		equipmentRackRange1.setEquipLowNbr(eqNrLow);
		equipmentRackRange1.setEquipHighNbr(eqNrHigh);
		equipmentRackRange1.setEquipType("C");

		List<EquipmentRackRange> codeList1 = new ArrayList<>();
		codeList1.add(equipmentRackRange1);
		List<EquipmentRackRangeDTO> equipmentRackRangeDTOTOList = new ArrayList<>();
		when(equiMapper.equipmentRackRangeDTOToEquipmentRackRange(Mockito.any())).thenReturn(equipmentRackRange1);
		equiService.deleteEquipmentRackRange(equipmentRackRange1);
		when(equiMapper.equipmentRackRangeToEquipmentRackRangeDTO(equipmentRackRange1));
		ResponseEntity<List<APIResponse<EquipmentRackRangeDTO>>> deleteList = equiController
				.deleteEquipmentRackRangeDto(equipmentRackRangeDTOTOList);
		assertEquals(deleteList.getStatusCodeValue(), 500);
	}
}
