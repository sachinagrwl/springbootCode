package com.nscorp.obis.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nscorp.obis.exception.InvalidOwneshipException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.nscorp.obis.domain.EquipmentOwnerPrefix;
import com.nscorp.obis.dto.EquipmentOwnerPrefixDTO;
import com.nscorp.obis.dto.mapper.EquipmentOwnerPrefixMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.EquipmentOwnerPrefixService;

public class EquipmentOwnerPrefixControllerTest {

	@Mock
	EquipmentOwnerPrefixService equipmentOwnerPrefixService;

	@Mock
	EquipmentOwnerPrefixMapper equipmentOwnerPrefixMapper;

	@InjectMocks
	EquipmentOwnerPrefixController equipmentOwnerPrefixController;

	EquipmentOwnerPrefixDTO equipmentOwnerPrefixDto;
	EquipmentOwnerPrefixDTO equipmentOwnerPrefixDto1;
	EquipmentOwnerPrefixDTO equipmentOwnerPrefixDto2;
	EquipmentOwnerPrefixDTO equipmentOwnerPrefixDto3;
	EquipmentOwnerPrefix equipmentOwnerPrefix;
	List<EquipmentOwnerPrefix> equipmentOwnerPrefixList;
	List<EquipmentOwnerPrefixDTO> equipmentOwnerPrefixDtoList;
	
	Map<String, String> header;
	
	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		
		MockitoAnnotations.initMocks(this);
		equipmentOwnerPrefix = new EquipmentOwnerPrefix();
		equipmentOwnerPrefix.setEquipInit("QWE");
		equipmentOwnerPrefix.setInterchangeCd("qw");
		equipmentOwnerPrefix.setOwnership("O");
		equipmentOwnerPrefixList = new ArrayList<>();
		equipmentOwnerPrefixList.add(equipmentOwnerPrefix);
		
		equipmentOwnerPrefixDto = new EquipmentOwnerPrefixDTO();
		equipmentOwnerPrefixDto.setEquipInit("QWE");
		equipmentOwnerPrefixDto.setInterchangeCd("ABCD");
		equipmentOwnerPrefixDto.setOwnership("F");

		equipmentOwnerPrefixDto1 = new EquipmentOwnerPrefixDTO();
		equipmentOwnerPrefixDto1.setEquipInit("QWE");
		equipmentOwnerPrefixDto1.setInterchangeCd(null);
		equipmentOwnerPrefixDto1.setOwnership("P");

		equipmentOwnerPrefixDto2 = new EquipmentOwnerPrefixDTO();
		equipmentOwnerPrefixDto2.setEquipInit("QWE");
		equipmentOwnerPrefixDto2.setInterchangeCd("ABCD");
		equipmentOwnerPrefixDto2.setOwnership("A");

		equipmentOwnerPrefixDto3 = new EquipmentOwnerPrefixDTO();
		equipmentOwnerPrefixDto3.setEquipInit("QWE");
		equipmentOwnerPrefixDto3.setInterchangeCd("ABCD");
		equipmentOwnerPrefixDto3.setOwnership(null);

		equipmentOwnerPrefixDtoList = new ArrayList<>();
		equipmentOwnerPrefixDtoList.add(equipmentOwnerPrefixDto);
		equipmentOwnerPrefixDtoList.add(equipmentOwnerPrefixDto1);
		equipmentOwnerPrefixDtoList.add(equipmentOwnerPrefixDto2);

		
		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");
	}

	@AfterEach
	void tearDown() throws Exception {
		equipmentOwnerPrefixDto = null;
		equipmentOwnerPrefix = null;
		equipmentOwnerPrefixList = null;
		equipmentOwnerPrefixDtoList = null;
	}

	@Test
	void testGetAllTables() {
		when(equipmentOwnerPrefixService.getAllTables()).thenReturn(equipmentOwnerPrefixList);
		ResponseEntity<APIResponse<List<EquipmentOwnerPrefixDTO>>> codeTableSelectionList = equipmentOwnerPrefixController.getAllTables();
		assertNotNull(codeTableSelectionList.getBody());
	}

	@Test
	void testGetAllTablesNoRecordsFoundException() {
		when(equipmentOwnerPrefixService.getAllTables()).thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<List<EquipmentOwnerPrefixDTO>>> genericTablelist = equipmentOwnerPrefixController.getAllTables();
		assertEquals(genericTablelist.getStatusCodeValue(), 404);

	}
	
	@Test
	void testGetAllGenericTablesException() {
		when(equipmentOwnerPrefixService.getAllTables()).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<List<EquipmentOwnerPrefixDTO>>> genericTablelist = equipmentOwnerPrefixController.getAllTables();
		assertEquals(genericTablelist.getStatusCodeValue(), 500);

	}
	@Test
	void testDeleteEquipmentOwnerPrefix() {
		when(equipmentOwnerPrefixMapper.equipmentOwnerPrefixDTOToequipmentOwnerPrefix(Mockito.any())).thenReturn(equipmentOwnerPrefix);
		equipmentOwnerPrefixService.deleteEquipmentOwnerPrefixTable(Mockito.any());
		when(equipmentOwnerPrefixMapper.equipmentOwnerPrefixToequipmentOwnerPrefixDTO(Mockito.any())).thenReturn(equipmentOwnerPrefixDto);
		ResponseEntity<List<APIResponse<EquipmentOwnerPrefixDTO>>> deleteList = equipmentOwnerPrefixController.deleteEquipmentOwnerPrefix(equipmentOwnerPrefixDtoList);
		assertEquals(deleteList.getStatusCodeValue(), 200);
	}
	@Test
	void testErrorDeleteEquipmentOwnerPrefix() {
		EquipmentOwnerPrefix equipmentOwnerPrefix1 = new EquipmentOwnerPrefix();
		equipmentOwnerPrefix1.setEquipInit("QWER");
		equipmentOwnerPrefix1.setOwnership("P");
		List<EquipmentOwnerPrefix> codeList1 = new ArrayList<>();
		codeList1.add(equipmentOwnerPrefix1);

		EquipmentOwnerPrefixDTO equipmentOwnerPrefixDTO2 = new EquipmentOwnerPrefixDTO();
		List<EquipmentOwnerPrefixDTO> EquipmentOwnerPrefixDTOList = new ArrayList<>();
		when(equipmentOwnerPrefixMapper.equipmentOwnerPrefixDTOToequipmentOwnerPrefix(Mockito.any())).thenReturn(equipmentOwnerPrefix1);
		equipmentOwnerPrefixService.deleteEquipmentOwnerPrefixTable(equipmentOwnerPrefix1);
		when(equipmentOwnerPrefixMapper.equipmentOwnerPrefixToequipmentOwnerPrefixDTO(Mockito.any())).thenReturn(equipmentOwnerPrefixDTO2);
		ResponseEntity<List<APIResponse<EquipmentOwnerPrefixDTO>>> deleteList = equipmentOwnerPrefixController.deleteEquipmentOwnerPrefix(EquipmentOwnerPrefixDTOList);
		assertEquals(deleteList.getStatusCodeValue(),500);
	}
	@Test
	void testUpdate(){
		when(equipmentOwnerPrefixService.updateEquipmentOwnerPrefix(Mockito.any(),Mockito.any())).thenReturn(equipmentOwnerPrefix);
		ResponseEntity<APIResponse<EquipmentOwnerPrefixDTO>> updateEquipmentCar = equipmentOwnerPrefixController.updateEquipmentCar(equipmentOwnerPrefixDto,header);
	}

	@Test
	void testUpdateNoRecordsFound(){
		when(equipmentOwnerPrefixService.updateEquipmentOwnerPrefix(Mockito.any(),Mockito.any())).thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<EquipmentOwnerPrefixDTO>> updateEquipmentCar = equipmentOwnerPrefixController.updateEquipmentCar(equipmentOwnerPrefixDto,header);
	}
	@Test
	void testAddRecordAlreadyExistsException(){
		when(equipmentOwnerPrefixService.addEquipmentOwnerPrefix(Mockito.any(),Mockito.any())).thenThrow(new RecordAlreadyExistsException());
		ResponseEntity<List<APIResponse<EquipmentOwnerPrefixDTO>>> insertEquipmentOwnerPrefix = equipmentOwnerPrefixController.insertEquipmentOwnerPrefix(equipmentOwnerPrefixDtoList,header);
	}
	@Test
	void testAddInvalidOwneshipException(){
		when(equipmentOwnerPrefixService.addEquipmentOwnerPrefix(Mockito.any(),Mockito.any())).thenThrow(new InvalidOwneshipException());
		ResponseEntity<List<APIResponse<EquipmentOwnerPrefixDTO>>> insertEquipmentOwnerPrefix = equipmentOwnerPrefixController.insertEquipmentOwnerPrefix(equipmentOwnerPrefixDtoList,header);
	}
	@Test
	void testUpdateRecordNotAddedException(){
		when(equipmentOwnerPrefixService.updateEquipmentOwnerPrefix(Mockito.any(),Mockito.any())).thenThrow(new RecordNotAddedException());
		ResponseEntity<APIResponse<EquipmentOwnerPrefixDTO>> updateEquipmentCar = equipmentOwnerPrefixController.updateEquipmentCar(equipmentOwnerPrefixDto,header);

		when(equipmentOwnerPrefixService.addEquipmentOwnerPrefix(Mockito.any(),Mockito.any())).thenThrow(new RecordNotAddedException());
		ResponseEntity<List<APIResponse<EquipmentOwnerPrefixDTO>>> insertEquipmentOwnerPrefix = equipmentOwnerPrefixController.insertEquipmentOwnerPrefix(equipmentOwnerPrefixDtoList,header);
	}
	@Test
	void testUpdateRuntimeException(){
		when(equipmentOwnerPrefixService.updateEquipmentOwnerPrefix(Mockito.any(),Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<EquipmentOwnerPrefixDTO>> updateEquipmentCar = equipmentOwnerPrefixController.updateEquipmentCar(equipmentOwnerPrefixDto,header);

		when(equipmentOwnerPrefixService.addEquipmentOwnerPrefix(Mockito.any(),Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<List<APIResponse<EquipmentOwnerPrefixDTO>>> insertEquipmentOwnerPrefix = equipmentOwnerPrefixController.insertEquipmentOwnerPrefix(equipmentOwnerPrefixDtoList,header);
	}
	@Test
	void testAdd(){
		when(equipmentOwnerPrefixService.addEquipmentOwnerPrefix(Mockito.any(),Mockito.any())).thenReturn(equipmentOwnerPrefix);
		ResponseEntity<List<APIResponse<EquipmentOwnerPrefixDTO>>> insertEquipmentOwnerPrefix = equipmentOwnerPrefixController.insertEquipmentOwnerPrefix(equipmentOwnerPrefixDtoList,header);
	}

}
