package com.nscorp.obis.controller;

import com.nscorp.obis.domain.CodeTableSelection;
import com.nscorp.obis.dto.CodeTableSelectionDTO;
import com.nscorp.obis.dto.mapper.CodeTableSelectionMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.SizeExceedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.CodeTableSelectionService;
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

class CodeTableSelectionControllerTest {
	
	@Mock
	CodeTableSelectionService codeTableSelectionService;

	@Mock
	CodeTableSelectionMapper codeTableSelectionMapper;

	@InjectMocks
	CodeTableSelectionController codeTableSelectionController;

	CodeTableSelectionDTO codeTableSelectionDto;
	CodeTableSelection codeTableSelection;
	List<CodeTableSelection> codeTableSelectionList;
	List<CodeTableSelectionDTO> codeTableSelectionDtoList;
	
	Map<String, String> header;

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		
		MockitoAnnotations.initMocks(this);
		codeTableSelection = new CodeTableSelection();
		codeTableSelection.setGenericTable("Testin");
		codeTableSelection.setGenericTableDesc("Testin");
		codeTableSelection.setGenCdFldSize((short) 1);
		codeTableSelection.setResourceNm("Testin");
		codeTableSelectionList = new ArrayList<>();
		codeTableSelectionList.add(codeTableSelection);
		
		codeTableSelectionDto = new CodeTableSelectionDTO();
		codeTableSelectionDto.setGenericTable("Testin");
		codeTableSelectionDto.setGenericTableDesc("Testin");
		codeTableSelectionDto.setGenCdFldSize((short) 1);
		codeTableSelectionDto.setResourceNm("Testin");
		
		codeTableSelectionDtoList = new ArrayList<>();
		codeTableSelectionDtoList.add(codeTableSelectionDto);
		
		
		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");
	}

	@AfterEach
	void tearDown() throws Exception {
		codeTableSelectionDto = null;
		codeTableSelection = null;
		codeTableSelectionList = null;
		codeTableSelectionDtoList = null;
	}

	@Test
	void testGetAllGenericTables() {
		when(codeTableSelectionService.getAllTables()).thenReturn(codeTableSelectionList);
		ResponseEntity<APIResponse<List<CodeTableSelectionDTO>>> codeTableSelectionList = codeTableSelectionController.getAllGenericTables();
		assertNotNull(codeTableSelectionList.getBody());
	}

	@Test
	void testGetAllGenericTablesNoRecordsFoundException() {
		when(codeTableSelectionService.getAllTables()).thenThrow(new NoRecordsFoundException());
		when(codeTableSelectionService.insertTable(Mockito.any(),Mockito.any())).thenThrow(new NoRecordsFoundException());
		when(codeTableSelectionService.updateCodeTableSelection(Mockito.any(),Mockito.any())).thenThrow(new NoRecordsFoundException());

		ResponseEntity<APIResponse<List<CodeTableSelectionDTO>>> genericTablelist = codeTableSelectionController.getAllGenericTables();
		ResponseEntity<APIResponse<CodeTableSelectionDTO>> genericTablelistAdd = codeTableSelectionController.addTable(Mockito.any(),Mockito.any());
		ResponseEntity<APIResponse<CodeTableSelectionDTO>> genericTablelistUpdate =  codeTableSelectionController.updateTable(Mockito.any(),Mockito.any());

		assertEquals(genericTablelist.getStatusCodeValue(), 404);
		assertEquals(genericTablelistAdd.getStatusCodeValue(),404);
		assertEquals(genericTablelistUpdate.getStatusCodeValue(),404);
	}

	@Test
	void testGetAllGenericTablesException() {
		when(codeTableSelectionService.getAllTables()).thenThrow(new RuntimeException());
		when(codeTableSelectionService.insertTable(Mockito.any(),Mockito.any())).thenThrow(new RuntimeException());
		when(codeTableSelectionService.updateCodeTableSelection(Mockito.any(),Mockito.any())).thenThrow(new RuntimeException());

		ResponseEntity<APIResponse<List<CodeTableSelectionDTO>>> genericTablelist = codeTableSelectionController.getAllGenericTables();
		ResponseEntity<APIResponse<CodeTableSelectionDTO>> genericTablelistAdd = codeTableSelectionController.addTable(Mockito.any(),Mockito.any());
		ResponseEntity<APIResponse<CodeTableSelectionDTO>> genericTablelistUpdate =  codeTableSelectionController.updateTable(Mockito.any(),Mockito.any());

		assertEquals(genericTablelist.getStatusCodeValue(), 500);
		assertEquals(genericTablelistAdd.getStatusCodeValue(),500);
		assertEquals(genericTablelistUpdate.getStatusCodeValue(),500);
	}

	@Test
	void testGetAllGenericTablesSizeExceedException() {
		when(codeTableSelectionService.insertTable(Mockito.any(),Mockito.any())).thenThrow(new SizeExceedException());
		when(codeTableSelectionService.updateCodeTableSelection(Mockito.any(),Mockito.any())).thenThrow(new SizeExceedException());

		ResponseEntity<APIResponse<CodeTableSelectionDTO>> genericTablelistAdd = codeTableSelectionController.addTable(Mockito.any(),Mockito.any());
		ResponseEntity<APIResponse<CodeTableSelectionDTO>> genericTablelistUpdate =  codeTableSelectionController.updateTable(Mockito.any(),Mockito.any());

		assertEquals(genericTablelistAdd.getStatusCodeValue(),411);
		assertEquals(genericTablelistUpdate.getStatusCodeValue(),411);
	}

	@Test
	void testGetAllGenericTablesRecordAlreadyExistsException() {
		when(codeTableSelectionService.insertTable(Mockito.any(),Mockito.any())).thenThrow(new RecordAlreadyExistsException());
		ResponseEntity<APIResponse<CodeTableSelectionDTO>> genericTablelistAdd = codeTableSelectionController.addTable(Mockito.any(),Mockito.any());
		assertEquals(genericTablelistAdd.getStatusCodeValue(),208);
	}

	@Test
	void testGetAllGenericTablesRecordNotAddedException() {
		when(codeTableSelectionService.insertTable(Mockito.any(),Mockito.any())).thenThrow(new RecordNotAddedException());
		ResponseEntity<APIResponse<CodeTableSelectionDTO>> genericTablelistAdd = codeTableSelectionController.addTable(Mockito.any(),Mockito.any());
		assertEquals(genericTablelistAdd.getStatusCodeValue(),406);
	}

	@Test
	void testGetAllGenericTablesNullPointerException() {
		when(codeTableSelectionService.insertTable(Mockito.any(),Mockito.any())).thenThrow(new NullPointerException());
		when(codeTableSelectionService.updateCodeTableSelection(Mockito.any(),Mockito.any())).thenThrow(new NullPointerException());

		ResponseEntity<APIResponse<CodeTableSelectionDTO>> genericTablelistAdd = codeTableSelectionController.addTable(Mockito.any(),Mockito.any());
		ResponseEntity<APIResponse<CodeTableSelectionDTO>> genericTablelistUpdate =  codeTableSelectionController.updateTable(Mockito.any(),Mockito.any());

		assertEquals(genericTablelistAdd.getStatusCodeValue(),400);
		assertEquals(genericTablelistUpdate.getStatusCodeValue(),400);
	}

	@Test
	void testAddTable() {
		when(codeTableSelectionMapper.codeTableSelectionDTOToCodeTableSelection(Mockito.any())).thenReturn(codeTableSelection);
		when(codeTableSelectionService.insertTable(Mockito.any(), Mockito.any())).thenReturn(codeTableSelection);
		when(codeTableSelectionMapper.codeTableSelectionToCodeTableSelectionDTO(Mockito.any())).thenReturn(codeTableSelectionDto);
		ResponseEntity<APIResponse<CodeTableSelectionDTO>> addedTable = codeTableSelectionController.addTable(codeTableSelectionDto,
				header);
		assertNotNull(addedTable.getBody());
	}
	

	@Test
	void testUpdateTable() {
		when(codeTableSelectionMapper.codeTableSelectionDTOToCodeTableSelection(Mockito.any())).thenReturn(codeTableSelection);
		when(codeTableSelectionService.updateCodeTableSelection(Mockito.any(), Mockito.any())).thenReturn(codeTableSelection);
		when(codeTableSelectionMapper.codeTableSelectionToCodeTableSelectionDTO(Mockito.any())).thenReturn(codeTableSelectionDto);
		ResponseEntity<APIResponse<CodeTableSelectionDTO>> tableUpdated = codeTableSelectionController.updateTable(codeTableSelectionDto, header);
		assertNotNull(tableUpdated.getBody());
	}

	@Test
	void testDeleteGenericTable() {
		when(codeTableSelectionMapper.codeTableSelectionDTOToCodeTableSelection(Mockito.any())).thenReturn(codeTableSelection);
		codeTableSelectionService.deleteTable(Mockito.any());
		when(codeTableSelectionMapper.codeTableSelectionToCodeTableSelectionDTO(Mockito.any())).thenReturn(codeTableSelectionDto);
		ResponseEntity<List<APIResponse<CodeTableSelectionDTO>>> deleteList = codeTableSelectionController.deleteGenericTable(codeTableSelectionDtoList);
		assertEquals(deleteList.getStatusCodeValue(),200);
	}

	@Test
	void testErrorDeleteWeight() {
		CodeTableSelection codeTableSelection1 = new CodeTableSelection();
//		codeTableSelection1.setGenericTable("Testin");
//		codeTableSelection1.setGenericTableDesc("Testin");
//		codeTableSelection1.setGenCdFldSize((short) 1);
//		codeTableSelection1.setResourceNm("Testin");
		List<CodeTableSelection> codeList = new ArrayList<>();
		codeList.add(codeTableSelection1);

		CodeTableSelectionDTO codeTableSelectionDTO = new CodeTableSelectionDTO();
		List<CodeTableSelectionDTO> codeTableSelectionDTOList = new ArrayList<>();

//        Mockito.doThrow(new RuntimeException()).when(equipmentDefaultTareWeightMaintenanceService).deleteWeight(Mockito.any());
		when(codeTableSelectionMapper.codeTableSelectionDTOToCodeTableSelection(Mockito.any())).thenReturn(codeTableSelection1);
		codeTableSelectionService.deleteTable(codeTableSelection1);
		when(codeTableSelectionMapper.codeTableSelectionToCodeTableSelectionDTO(Mockito.any())).thenReturn(codeTableSelectionDTO);
		ResponseEntity<List<APIResponse<CodeTableSelectionDTO>>> deleteList = codeTableSelectionController.deleteGenericTable(codeTableSelectionDTOList);
		assertEquals(deleteList.getStatusCodeValue(),500);
	}

}
