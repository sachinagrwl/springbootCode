package com.nscorp.obis.controller;

import static org.junit.Assert.assertEquals;
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

import com.nscorp.obis.dto.StationTermHandleDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.StationTermHandleService;

public class StationTermHandleControllerTest {

	@Mock
	StationTermHandleService service;

	@InjectMocks
	StationTermHandleController controller;

	StationTermHandleDTO stationTermhandleDTO;
	List<StationTermHandleDTO> stationTermHandleDTOS;
	Long terminalId = 219974351921L;
	Map<String, String> headers;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		stationTermhandleDTO = new StationTermHandleDTO();
		stationTermHandleDTOS = new ArrayList<>();
		stationTermHandleDTOS.add(stationTermhandleDTO);
		headers = new HashMap<>();
		headers.put("userid", "test");
		headers.put("extensionschema", "test");

	}

	@AfterEach
	void tearDown() {
		stationTermhandleDTO = null;
		stationTermHandleDTOS = null;
	}

	@Test
	void testFetchStationTermHandle() {
		when(service.getTermHandleDetails(Mockito.anyLong())).thenReturn(stationTermHandleDTOS);
		ResponseEntity<APIResponse<List<StationTermHandleDTO>>> response = controller
				.fetchStationTermHandle(terminalId);
		assertEquals(200, response.getStatusCodeValue());
	}

	@Test
	void testInsertStationTermHandle() {
		when(service.insertStationTermHandle(Mockito.any(), Mockito.anyMap())).thenReturn(stationTermhandleDTO);
		ResponseEntity<APIResponse<StationTermHandleDTO>> response = controller
				.insertStationTermHandle(stationTermhandleDTO, headers);
		assertEquals(200, response.getStatusCodeValue());
	}

	@Test
	void testDeleteStationTermHandle() {
		when(service.deleteStationTermHandle(Mockito.any())).thenReturn(stationTermhandleDTO);
		ResponseEntity<List<APIResponse<StationTermHandleDTO>>> response = controller.deleteStationTerminalHandle(stationTermHandleDTOS);
		assertEquals(200, response.getStatusCodeValue());
	}

	@Test
	void testNoRecordsException() {
		when(service.getTermHandleDetails(Mockito.anyLong())).thenThrow(NoRecordsFoundException.class);
		ResponseEntity<APIResponse<List<StationTermHandleDTO>>> response=controller.fetchStationTermHandle(terminalId);
		assertEquals(404, response.getStatusCodeValue());
		when(service.insertStationTermHandle(Mockito.any(), Mockito.anyMap())).thenThrow(NoRecordsFoundException.class);
		ResponseEntity<APIResponse<StationTermHandleDTO>> response2=controller.insertStationTermHandle(stationTermhandleDTO, headers);
		assertEquals(404, response2.getStatusCodeValue());
		when(service.deleteStationTermHandle(Mockito.any())).thenThrow(NoRecordsFoundException.class);
		ResponseEntity<List<APIResponse<StationTermHandleDTO>>> response3 = controller.deleteStationTerminalHandle(stationTermHandleDTOS);
		assertEquals(500, response3.getStatusCodeValue());
	}

	@Test
	void testException() {
		when(service.getTermHandleDetails(Mockito.anyLong())).thenThrow(RuntimeException.class);
		ResponseEntity<APIResponse<List<StationTermHandleDTO>>> response=controller.fetchStationTermHandle(terminalId);
		assertEquals(500, response.getStatusCodeValue());
		when(service.insertStationTermHandle(Mockito.any(), Mockito.anyMap())).thenThrow(RuntimeException.class);
		ResponseEntity<APIResponse<StationTermHandleDTO>> response2=controller.insertStationTermHandle(stationTermhandleDTO, headers);
		assertEquals(500, response2.getStatusCodeValue());
		when(service.deleteStationTermHandle(Mockito.any())).thenThrow(RuntimeException.class);
		ResponseEntity<List<APIResponse<StationTermHandleDTO>>> response3 = controller.deleteStationTerminalHandle(stationTermHandleDTOS);
		assertEquals(500, response3.getStatusCodeValue());
	}

}
