package com.nscorp.obis.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.nscorp.obis.domain.Pool;
import com.nscorp.obis.domain.Terminal;
import com.nscorp.obis.dto.PoolDTO;
import com.nscorp.obis.dto.TerminalDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.PoolTerminalConflictService;

class PoolTerminalConflictControllerTest {

	@InjectMocks
	PoolTerminalConflictController poolTerminalConflictController;

	@Mock
	PoolTerminalConflictService poolTerminalConflictService;

	PoolDTO poolDTO;

	Pool pool;

	Terminal terminal;

	TerminalDTO terminalDTO;

	Set<Terminal> terminalSet;

	Set<TerminalDTO> terminalDTOSet;

	Set<Pool> poolSet;

	Map<String, String> headers;

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		pool = new Pool();
		poolDTO = new PoolDTO();
		terminal = new Terminal();
		terminalDTO = new TerminalDTO();
		terminalSet = new HashSet<>();
		terminalDTOSet = new HashSet<>();
		poolSet = new HashSet<>();

		terminal.setTerminalId(1000L);
		terminalSet.add(terminal);
		pool.setTerminals(terminalSet);
		pool.setPoolId(1000000L);

		terminalDTO.setTerminalId(1000L);
		terminalDTOSet.add(terminalDTO);
		poolDTO.setTerminals(terminalSet);
		poolDTO.setPoolId(1000000L);

		poolSet.add(pool);
		headers = new HashMap<>();
		headers.put("userid", "test");
		headers.put("extensionschema", "test");
	}

	@AfterEach
	void tearDown() throws Exception {
		pool = null;
		poolDTO = null;
		terminal = null;
		terminalDTO = null;
		terminalSet = null;
		terminalDTOSet = null;
	}

	@Test
	void testvalidatePoolTerminalConflict() {
		when(poolTerminalConflictService.validatePoolTerminalConflict(Mockito.any(), Mockito.any()))
				.thenReturn(poolSet);
		ResponseEntity<APIResponse<List<PoolDTO>>> putResponse = poolTerminalConflictController
				.validatePoolTerminalConflict(poolDTO, headers);
		assertEquals(putResponse.getStatusCode(), HttpStatus.OK);

		when(poolTerminalConflictService.validatePoolTerminalConflict(Mockito.any(), Mockito.any()))
				.thenReturn(Collections.emptySet());
		ResponseEntity<APIResponse<List<PoolDTO>>> putEmptyResponse = poolTerminalConflictController
				.validatePoolTerminalConflict(poolDTO, headers);
		assertEquals(putEmptyResponse.getStatusCode(), HttpStatus.OK);

	}

	@Test
	void testNoRecordsFoundException() {
		when(poolTerminalConflictService.validatePoolTerminalConflict(Mockito.any(), Mockito.any()))
				.thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<List<PoolDTO>>> putResponse = poolTerminalConflictController
				.validatePoolTerminalConflict(poolDTO, headers);
		assertEquals(putResponse.getStatusCode(), HttpStatus.NOT_FOUND);

	}

	@Test
	void testException() {
		when(poolTerminalConflictService.validatePoolTerminalConflict(Mockito.any(), Mockito.any()))
				.thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<List<PoolDTO>>> putResponse = poolTerminalConflictController
				.validatePoolTerminalConflict(poolDTO, headers);
		assertEquals(putResponse.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);

	}

}
