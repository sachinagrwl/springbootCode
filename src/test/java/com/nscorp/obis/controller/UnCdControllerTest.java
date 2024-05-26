package com.nscorp.obis.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

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

import com.nscorp.obis.domain.UnCd;
import com.nscorp.obis.dto.UnCdDTO;
import com.nscorp.obis.dto.mapper.UnCdMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.UnCdService;

public class UnCdControllerTest {

	@Mock
	UnCdService unCdService;

	@Mock
	UnCdMapper unCdMapper;

	@InjectMocks
	UnCdController unCdController;

	UnCdDTO unCdDTO;
	UnCd uncd;
	List<UnCd> unCdList;
	List<UnCdDTO> unCdDTOList;
	Map<String, String> header;

	String unCd;
	String unDsc;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		unCdList = new ArrayList<>();
		uncd = new UnCd();
		unCdDTO = new UnCdDTO();
		unCdDTOList = new ArrayList<>();
		uncd.setUnCd("QW235");
		uncd.setUnDsc("Hello");
		unCdList.add(uncd);
		unCdDTO.setUnCd("QW235");
		unCdDTO.setUnDsc("Hello");
		unCdDTOList.add(unCdDTO);
		unCd = "D3";
		unDsc = "Hello";

		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");
	}

	@AfterEach
	void tearDown() throws Exception {
		uncd = null;
		unCdDTO = null;
		unCdList = null;
		unCdDTOList = null;
	}

	@Test
	void testGetAll() {
		when(unCdService.getAllTables(Mockito.any())).thenReturn(unCdList);
		ResponseEntity<APIResponse<List<UnCdDTO>>> result = unCdController.getAllTables(unCd);
		assertEquals(result.getStatusCodeValue(), 200);
		when(unCdService.getAllTables(Mockito.any())).thenReturn(null);
		result = unCdController.getAllTables(unCd);
		when(unCdService.getAllTables(Mockito.any())).thenReturn(Collections.emptyList());
		result = unCdController.getAllTables(unCd);
	}

	@Test
	void testDeleteUnCd() {
		when(unCdService.deleteUnCode(Mockito.any())).thenReturn(uncd);
		when(unCdService.deleteUnCode(null)).thenThrow(new NoRecordsFoundException());
		ResponseEntity<List<APIResponse<UnCdDTO>>> result = unCdController.deleteUnCode(unCdDTOList);
		assertEquals(result.getStatusCodeValue(), 200);
		result = unCdController.deleteUnCode(null);
		result = unCdController.deleteUnCode(Collections.emptyList());
		unCdDTOList.add(unCdDTO);
		unCdDTOList.add(null);
		unCdDTOList.add(unCdDTO);
		result = unCdController.deleteUnCode(unCdDTOList);
	}

	@Test
	void testNoRecordsFoundException() {

		when(unCdService.getAllTables(Mockito.any())).thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<List<UnCdDTO>>> result = unCdController.getAllTables(unCd);
		assertEquals(result.getStatusCodeValue(), 404);

		when(unCdService.deleteUnCode(Mockito.any())).thenThrow(new NoRecordsFoundException());
		ResponseEntity<List<APIResponse<UnCdDTO>>> result1 = unCdController.deleteUnCode(unCdDTOList);

		when(unCdService.updateUnDesc(Mockito.any(), Mockito.any())).thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<UnCdDTO>> exception = unCdController.updateUnCdDesc(unCdDTO, header);
		assertEquals(exception.getStatusCodeValue(), 404);
		
		when(unCdService.addUnCode(Mockito.any(), Mockito.any())).thenThrow(new NoRecordsFoundException());
		exception = unCdController.addUnCode(unCdDTO, header);
		assertEquals(exception.getStatusCodeValue(), 404);
	}

	@Test
	void testException() {
		when(unCdService.getAllTables(Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<List<UnCdDTO>>> result = unCdController.getAllTables(unCd);
		assertEquals(result.getStatusCodeValue(), 500);

		when(unCdService.deleteUnCode(Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<List<APIResponse<UnCdDTO>>> deleteList = unCdController.deleteUnCode(unCdDTOList);

		when(unCdService.updateUnDesc(Mockito.any(), Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<UnCdDTO>> ex = unCdController.updateUnCdDesc(unCdDTO, header);
		assertEquals(ex.getStatusCodeValue(), 500);

		when(unCdService.addUnCode(Mockito.any(), Mockito.any())).thenThrow(new RuntimeException());
		ex = unCdController.addUnCode(unCdDTO, header);
		assertEquals(ex.getStatusCodeValue(), 500);
	}
	
	@Test
	void testRecordNotAddedException () {
		when(unCdService.addUnCode(Mockito.any(), Mockito.any())).thenThrow(new RecordNotAddedException());
		ResponseEntity<APIResponse<UnCdDTO>> ex = unCdController.addUnCode(unCdDTO, header);
		assertEquals(ex.getStatusCodeValue(), 502);
	}

	@Test
	void testUpdateUnCode() {
		when(unCdMapper.UnCdToUnCdDTO(Mockito.any())).thenReturn(unCdDTO);
		when(unCdMapper.UnCdDTOToUnCd(Mockito.any())).thenReturn(uncd);
		when(unCdService.updateUnDesc(Mockito.any(), Mockito.any())).thenReturn(uncd);
		ResponseEntity<APIResponse<UnCdDTO>> data = unCdController.updateUnCdDesc(unCdDTO, header);
		assertEquals(data.getStatusCodeValue(), 200);
	}

	@Test
	void testAddUnCode() {
		when(unCdMapper.UnCdToUnCdDTO(Mockito.any())).thenReturn(unCdDTO);
		when(unCdMapper.UnCdDTOToUnCd(Mockito.any())).thenReturn(uncd);
		when(unCdService.addUnCode(Mockito.any(), Mockito.any())).thenReturn(uncd);
		ResponseEntity<APIResponse<UnCdDTO>> data = unCdController.addUnCode(unCdDTO, header);
		assertEquals(data.getStatusCodeValue(), 200);
	}

}
