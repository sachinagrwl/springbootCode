package com.nscorp.obis.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

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

import com.nscorp.obis.domain.TerminalFunction;
import com.nscorp.obis.dto.TerminalFunctionDTO;
import com.nscorp.obis.dto.mapper.TerminalFunctionMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.TerminalFunctionService;

class TerminalFunctionControllerTest {
	@Mock
	TerminalFunctionService terminalFunctionService;
	@InjectMocks
	TerminalFunctionController terminalFunctionController;
	@Mock
	TerminalFunctionMapper terminalFunctionMapper;
	TerminalFunction terminalFunction;
	TerminalFunctionDTO terminalFunctionDTO;

	List<TerminalFunction> terminalFunctionList;
	Map<String, String> header;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		terminalFunction = new TerminalFunction();
		terminalFunction.setTerminalId(1L);
		terminalFunctionList = new ArrayList<>();
		terminalFunctionList.add(terminalFunction);
		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");
	}

	@AfterEach
	void tearDown() throws Exception {
		terminalFunction = null;
		terminalFunctionDTO = null;
	}

	@Test
	void testGetTerminalFunction() {
		when(terminalFunctionService.getTerminalFunctionList(Mockito.any(), Mockito.any())).thenReturn(terminalFunctionList);
		ResponseEntity<APIResponse<List<TerminalFunctionDTO>>> result = terminalFunctionController.getTerminalFunction(Long.valueOf(1), "abcd");
		when(terminalFunctionService.getTerminalFunctionList(Mockito.any(), Mockito.any())).thenReturn(null);
		result = terminalFunctionController.getTerminalFunction(Long.valueOf(1), "abcd");
		when(terminalFunctionService.getTerminalFunctionList(Mockito.any(), Mockito.any())).thenReturn(terminalFunctionList);
		result = terminalFunctionController.getTerminalFunction(Long.valueOf(1), "abcd");
	}

	@Test
	void testGetTerminalFunctionNoRecordFound() {
		when(terminalFunctionService.getTerminalFunctionList(Mockito.any(), Mockito.any())).thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<List<TerminalFunctionDTO>>> result = terminalFunctionController.getTerminalFunction(Long.valueOf(1), "abcd");
	}

	@Test
	void testGetTerminalFunctionException() {
		when(terminalFunctionService.getTerminalFunctionList(Mockito.any(), Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<List<TerminalFunctionDTO>>> result = terminalFunctionController.getTerminalFunction(Long.valueOf(1), "abcd");
	}

	@Test
	void testTerminalFunction() throws Exception {
		when(terminalFunctionMapper.terminalFunctionDTOToTerminalFunction(Mockito.any())).thenReturn(terminalFunction);
		when(terminalFunctionService.updateTerminalFunction(Mockito.any(), Mockito.any())).thenReturn(terminalFunction);
		when(terminalFunctionMapper.terminalFunctionToTerminalFunctionDTO(Mockito.any()))
				.thenReturn(terminalFunctionDTO);
		ResponseEntity<APIResponse<TerminalFunctionDTO>> result = terminalFunctionController
				.updateTerminalFunction(terminalFunctionDTO, header);
		assertNotNull(result.getBody());
	}

	@Test
	void testUpdateTerminalFunctionNoRecordFoundException() throws Exception {
		when(terminalFunctionService.updateTerminalFunction(Mockito.any(), Mockito.any()))
				.thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<TerminalFunctionDTO>> result = terminalFunctionController
				.updateTerminalFunction(terminalFunctionDTO, header);
		assertEquals(result.getStatusCodeValue(), 404);
	}

	@Test
	void testUpdateTerminalFunctionRecordAlreadyExistsException() throws Exception {
		when(terminalFunctionService.updateTerminalFunction(Mockito.any(), Mockito.any()))
				.thenThrow(new RecordAlreadyExistsException());
		ResponseEntity<APIResponse<TerminalFunctionDTO>> result = terminalFunctionController
				.updateTerminalFunction(terminalFunctionDTO, header);
		assertEquals(result.getStatusCodeValue(), 208);
	}

	@Test
	void testUpdateTerminalFunctionException() throws Exception {
		when(terminalFunctionService.updateTerminalFunction(Mockito.any(), Mockito.any()))
				.thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<TerminalFunctionDTO>> result = terminalFunctionController
				.updateTerminalFunction(terminalFunctionDTO, header);
		assertEquals(result.getStatusCodeValue(), 500);
	}
}
