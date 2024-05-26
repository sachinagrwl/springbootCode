package com.nscorp.obis.controller;

import com.nscorp.obis.domain.GenericCodeUpdate;
import com.nscorp.obis.dto.GenericCodeUpdateDTO;
import com.nscorp.obis.dto.mapper.GenericCodeUpdateMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.SizeExceedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.GenericCodeUpdateService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class GenericCodeUpdateControllerTest {

	@Mock
	GenericCodeUpdateService genericCodeUpdateService;

	@Mock
	GenericCodeUpdateMapper genericCodeUpdateMapper;

	@InjectMocks
	GenericCodeUpdateController genericCodeUpdateController;
	
	GenericCodeUpdateDTO genericCodeUpdateDto;
	GenericCodeUpdate genericCodeUpdate;
	List<GenericCodeUpdate> genericCodeUpdateList;
	List<GenericCodeUpdateDTO> genericCodeUpdateDtoList;
	Map<String, String> header;
	String tableName = "ABC";

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		genericCodeUpdate = new GenericCodeUpdate();
		genericCodeUpdateDto = new GenericCodeUpdateDTO();
		genericCodeUpdateDtoList = new ArrayList<>();
		genericCodeUpdateList = new ArrayList<>();

		genericCodeUpdateDto.setGenericTable("ABC");
		genericCodeUpdateDto.setGenericTableCode("DEMO");
		genericCodeUpdateDto.setGenericShortDescription("SRT DESC");
		genericCodeUpdateDto.setGenericLongDescription("LONG DESC");
		genericCodeUpdate.setGenericTable("ABC");
		genericCodeUpdate.setGenericTableCode("DEMO");
		genericCodeUpdate.setGenericShortDescription("SRT DESC");
		genericCodeUpdate.setGenericLongDescription("LONG DESC");

		genericCodeUpdateDtoList.add(genericCodeUpdateDto);
		genericCodeUpdateList.add(genericCodeUpdate);
		header = new HashMap<String,String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");
	}

	@AfterEach
	void tearDown() throws Exception {
		genericCodeUpdateList = null;
		genericCodeUpdateDtoList = null;
		genericCodeUpdateDto = null;
		genericCodeUpdate = null;
	}

	@Test
	void testEquipmentTareWeightsNoRecordsFoundException() {
		when(genericCodeUpdateService.getByTableName(Mockito.any())).thenThrow(new NoRecordsFoundException());
		when(genericCodeUpdateService.insertCode(Mockito.any(),Mockito.any())).thenThrow(new NoRecordsFoundException());
		when(genericCodeUpdateService.updateCode(Mockito.any(),Mockito.any())).thenThrow(new NoRecordsFoundException());

		ResponseEntity<APIResponse<List<GenericCodeUpdateDTO>>> genericCodeUpdateList = genericCodeUpdateController.getByGenericTable(Mockito.any());
		ResponseEntity<APIResponse<GenericCodeUpdateDTO>>  genericCodeUpdateListAdd = genericCodeUpdateController.insert(Mockito.any(),Mockito.any());
		ResponseEntity<APIResponse<GenericCodeUpdateDTO>>  genericCodeUpdateListUpdate =  genericCodeUpdateController.update(Mockito.any(),Mockito.any());

		assertEquals(genericCodeUpdateList.getStatusCodeValue(), 404);
		assertEquals(genericCodeUpdateListAdd.getStatusCodeValue(),404);
		assertEquals(genericCodeUpdateListUpdate.getStatusCodeValue(),404);
	}

	@Test
	void testEquipmentTareWeightsSizeExceedException() {
		when(genericCodeUpdateService.insertCode(Mockito.any(),Mockito.any())).thenThrow(new SizeExceedException());
		when(genericCodeUpdateService.updateCode(Mockito.any(),Mockito.any())).thenThrow(new SizeExceedException());

		ResponseEntity<APIResponse<GenericCodeUpdateDTO>>  genericCodeUpdateListAdd = genericCodeUpdateController.insert(Mockito.any(),Mockito.any());
		ResponseEntity<APIResponse<GenericCodeUpdateDTO>>  genericCodeUpdateListUpdate =  genericCodeUpdateController.update(Mockito.any(),Mockito.any());

		assertEquals(genericCodeUpdateListAdd.getStatusCodeValue(),411);
		assertEquals(genericCodeUpdateListUpdate.getStatusCodeValue(),411);
	}

	@Test
	void testEquipmentTareWeightsRecordAlreadyExistsException() {
		when(genericCodeUpdateService.insertCode(Mockito.any(),Mockito.any())).thenThrow(new RecordAlreadyExistsException());
		ResponseEntity<APIResponse<GenericCodeUpdateDTO>>  genericCodeUpdateListAdd = genericCodeUpdateController.insert(Mockito.any(),Mockito.any());
		assertEquals(genericCodeUpdateListAdd.getStatusCodeValue(),208);
	}

	@Test
	void testEquipmentTareWeightsRecordNotAddedException() {
		when(genericCodeUpdateService.insertCode(Mockito.any(),Mockito.any())).thenThrow(new RecordNotAddedException());
		ResponseEntity<APIResponse<GenericCodeUpdateDTO>>  genericCodeUpdateListAdd = genericCodeUpdateController.insert(Mockito.any(),Mockito.any());
		assertEquals(genericCodeUpdateListAdd.getStatusCodeValue(),406);
	}

	@Test
	void testEquipmentTareWeightsNullPointerException() {
		when(genericCodeUpdateService.insertCode(Mockito.any(),Mockito.any())).thenThrow(new NullPointerException());
		when(genericCodeUpdateService.updateCode(Mockito.any(),Mockito.any())).thenThrow(new NullPointerException());

		ResponseEntity<APIResponse<GenericCodeUpdateDTO>>  genericCodeUpdateListAdd = genericCodeUpdateController.insert(Mockito.any(),Mockito.any());
		ResponseEntity<APIResponse<GenericCodeUpdateDTO>>  genericCodeUpdateListUpdate =  genericCodeUpdateController.update(Mockito.any(),Mockito.any());

		assertEquals(genericCodeUpdateListAdd.getStatusCodeValue(),400);
		assertEquals(genericCodeUpdateListUpdate.getStatusCodeValue(),400);
	}

	@Test
	void testEquipmentTareWeightsException() {
		when(genericCodeUpdateService.getByTableName(tableName)).thenThrow(new RuntimeException());
		when(genericCodeUpdateService.insertCode(Mockito.any(),Mockito.any())).thenThrow(new RuntimeException());
		when(genericCodeUpdateService.updateCode(Mockito.any(),Mockito.any())).thenThrow(new RuntimeException());

		ResponseEntity<APIResponse<List<GenericCodeUpdateDTO>>> genericCodeUpdateList = genericCodeUpdateController.getByGenericTable(tableName);
		ResponseEntity<APIResponse<GenericCodeUpdateDTO>>  genericCodeUpdateListAdd = genericCodeUpdateController.insert(Mockito.any(),Mockito.any());
		ResponseEntity<APIResponse<GenericCodeUpdateDTO>>  genericCodeUpdateListUpdate =  genericCodeUpdateController.update(Mockito.any(),Mockito.any());

		assertEquals(genericCodeUpdateList.getStatusCodeValue(), 500);
		assertEquals(genericCodeUpdateListAdd.getStatusCodeValue(),500);
		assertEquals(genericCodeUpdateListUpdate.getStatusCodeValue(),500);
	}

	@Test
	void testGetByGenericTable() {
		when(genericCodeUpdateService.getByTableName(tableName)).thenReturn(genericCodeUpdateList);
		ResponseEntity<APIResponse<List<GenericCodeUpdateDTO>>> genericCodeUpdateLst = genericCodeUpdateController.getByGenericTable(tableName);
        assertEquals(genericCodeUpdateLst.getStatusCodeValue(), 200);
	}

	@Test
	void testInsert() {
		when(genericCodeUpdateMapper.GenericCodeUpdateDTOToGenericCodeUpdate(Mockito.any())).thenReturn(genericCodeUpdate);
		when(genericCodeUpdateService.insertCode(Mockito.any(),Mockito.any())).thenReturn(genericCodeUpdate);
		when(genericCodeUpdateMapper.GenericCodeUpdateToGenericCodeUpdateDTO(Mockito.any())).thenReturn(genericCodeUpdateDto);
		ResponseEntity<APIResponse<GenericCodeUpdateDTO>> codeAdded = genericCodeUpdateController.insert(genericCodeUpdateDto, header);
		assertNotNull(codeAdded.getBody());
	}

	@Test
	void testUpdate() {
		when(genericCodeUpdateMapper.GenericCodeUpdateDTOToGenericCodeUpdate(Mockito.any())).thenReturn(genericCodeUpdate);
		when(genericCodeUpdateService.updateCode(Mockito.any(),Mockito.any())).thenReturn(genericCodeUpdate);
		when(genericCodeUpdateMapper.GenericCodeUpdateToGenericCodeUpdateDTO(Mockito.any())).thenReturn(genericCodeUpdateDto);
		ResponseEntity<APIResponse<GenericCodeUpdateDTO>> codeUpdated = genericCodeUpdateController.update(genericCodeUpdateDto, header);
		assertNotNull(codeUpdated.getBody());
	}

	@Test
	void testDelete() {
		when(genericCodeUpdateMapper.GenericCodeUpdateDTOToGenericCodeUpdate(Mockito.any())).thenReturn(genericCodeUpdate);
		genericCodeUpdateService.deleteCode(Mockito.any());
		when(genericCodeUpdateMapper.GenericCodeUpdateToGenericCodeUpdateDTO(Mockito.any())).thenReturn(genericCodeUpdateDto);
		ResponseEntity<List<APIResponse<GenericCodeUpdateDTO>>> deleteList = genericCodeUpdateController.delete(genericCodeUpdateDtoList);
		assertEquals(deleteList.getStatusCodeValue(),200);
	}

	@Test
	void testErrorDeleteCode() {
		GenericCodeUpdate genericCodeUpdate1 = new GenericCodeUpdate();
		genericCodeUpdate1.setGenericTable("ABC");
		genericCodeUpdate1.setGenericTableCode("DEMO");
		genericCodeUpdate1.setGenericShortDescription("SRT DESC");
		genericCodeUpdate1.setGenericLongDescription("LONG DESC");
		List<GenericCodeUpdate> codeList = new ArrayList<>();
		codeList.add(genericCodeUpdate1);
		GenericCodeUpdateDTO codeUpdateDTO = new GenericCodeUpdateDTO();
		List<GenericCodeUpdateDTO> codeUpdateDTOList = new ArrayList<>();
		when(genericCodeUpdateMapper.GenericCodeUpdateDTOToGenericCodeUpdate(Mockito.any())).thenReturn(genericCodeUpdate1);
		genericCodeUpdateService.deleteCode(genericCodeUpdate1);
		when(genericCodeUpdateMapper.GenericCodeUpdateToGenericCodeUpdateDTO(Mockito.any())).thenReturn(codeUpdateDTO);
		ResponseEntity<List<APIResponse<GenericCodeUpdateDTO>>> deleteList = genericCodeUpdateController.delete(codeUpdateDTOList);
		assertEquals(deleteList.getStatusCodeValue(),500);
	}

}
