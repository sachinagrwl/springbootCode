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

import com.nscorp.obis.domain.Pool;
import com.nscorp.obis.domain.PoolCarStorageExempt;
import com.nscorp.obis.dto.PoolCarStorageExemptDTO;
import com.nscorp.obis.dto.PoolDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.PoolCarStorageExemptService;

class PoolCarStorageExemptControllerTest {

	@Mock
	PoolCarStorageExemptService carStorageExemptService;

	@InjectMocks
	PoolCarStorageExemptController carStorageExemptController;

	PoolCarStorageExemptDTO carStorageExemptDTO;

	PoolCarStorageExempt carStorageExempt;

	Pool pool;

	PoolDTO poolDTO;

	List<PoolCarStorageExemptDTO> carStorageExemptDTOList;

	List<PoolCarStorageExempt> carStorageExemptList;

	Map<String, String> header;

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		carStorageExemptDTO = new PoolCarStorageExemptDTO();
		poolDTO = new PoolDTO();
		poolDTO.setPoolId(10000L);
		poolDTO.setPoolReservationType("PT");
		carStorageExemptDTO.setPoolId(10000L);
		carStorageExemptDTO.setPool(poolDTO);

		carStorageExempt = new PoolCarStorageExempt();
		pool = new Pool();
		pool.setPoolId(10000L);
		pool.setPoolReservationType("PT");
		carStorageExempt.setPoolId(10000L);
		carStorageExempt.setPool(pool);

		carStorageExemptDTOList = new ArrayList<>();
		carStorageExemptList = new ArrayList<>();

		carStorageExemptDTOList.add(carStorageExemptDTO);
		carStorageExemptList.add(carStorageExempt);

		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");

	}

	@AfterEach
	void tearDown() throws Exception {
		carStorageExemptList = null;
		carStorageExemptDTOList = null;
		carStorageExempt = null;
		carStorageExemptDTO = null;
		header = null;
	}

	@Test
	void testGetAllPoolCarStorageExempts() {
		when(carStorageExemptService.getAllPoolCarStorageExempts()).thenReturn(carStorageExemptList);
		ResponseEntity<APIResponse<List<PoolCarStorageExemptDTO>>> getCarExempt = carStorageExemptController
				.getAllPoolCarStorageExempts();
		assertEquals(getCarExempt.getStatusCodeValue(), 200);
	}

	@Test
	void testAddPoolCarStorageExempts() {
		when(carStorageExemptService.addPoolCarStorageExempt(Mockito.any(), Mockito.any()))
				.thenReturn(carStorageExempt);
		ResponseEntity<List<APIResponse<PoolCarStorageExemptDTO>>> addCarExempt = carStorageExemptController
				.addPoolCarStorageExempt(carStorageExemptDTOList, header);
		assertEquals(addCarExempt.getStatusCodeValue(), 200);
	}

	@Test
	void testDeletePoolCarStorageExempts() {
		when(carStorageExemptService.deletePoolCarStorageExempt(Mockito.any())).thenReturn(carStorageExempt);
		ResponseEntity<List<APIResponse<PoolCarStorageExemptDTO>>> deleteCarExempt = carStorageExemptController
				.deletePoolCarStorageExempt(carStorageExemptDTOList);
		assertEquals(deleteCarExempt.getStatusCodeValue(), 200);
	}

	@Test
	void testNoRecordsFoundException() {
		when(carStorageExemptService.getAllPoolCarStorageExempts()).thenThrow(new NoRecordsFoundException());
		when(carStorageExemptService.deletePoolCarStorageExempt(Mockito.any()))
				.thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<List<PoolCarStorageExemptDTO>>> getCarExempt = carStorageExemptController
				.getAllPoolCarStorageExempts();
		assertEquals(getCarExempt.getStatusCodeValue(), 404);

		ResponseEntity<List<APIResponse<PoolCarStorageExemptDTO>>> deleteCarExempt = carStorageExemptController
				.deletePoolCarStorageExempt(carStorageExemptDTOList);
		assertEquals(deleteCarExempt.getStatusCodeValue(), 500);
	}

	@Test
	void testRecordNotAddedException() {
		when(carStorageExemptService.addPoolCarStorageExempt(Mockito.any(), Mockito.any()))
				.thenThrow(new RecordNotAddedException());
		ResponseEntity<List<APIResponse<PoolCarStorageExemptDTO>>> addCarExempt = carStorageExemptController
				.addPoolCarStorageExempt(carStorageExemptDTOList, header);
		assertEquals(addCarExempt.getStatusCodeValue(), 500);
	}

	@Test
	void testRecordAlreadyExistsException() {
		when(carStorageExemptService.addPoolCarStorageExempt(Mockito.any(), Mockito.any()))
				.thenThrow(new RecordAlreadyExistsException());
		ResponseEntity<List<APIResponse<PoolCarStorageExemptDTO>>> addCarExempt = carStorageExemptController
				.addPoolCarStorageExempt(carStorageExemptDTOList, header);
		assertEquals(addCarExempt.getStatusCodeValue(), 500);
	}

	@Test
	void testException() {
		when(carStorageExemptService.addPoolCarStorageExempt(Mockito.any(), Mockito.any()))
				.thenThrow(new RuntimeException());
		ResponseEntity<List<APIResponse<PoolCarStorageExemptDTO>>> addCarExempt = carStorageExemptController
				.addPoolCarStorageExempt(carStorageExemptDTOList, header);
		assertEquals(addCarExempt.getStatusCodeValue(), 500);
		when(carStorageExemptService.deletePoolCarStorageExempt(Mockito.any())).thenThrow(new RuntimeException());

		ResponseEntity<List<APIResponse<PoolCarStorageExemptDTO>>> deleteCarExempt = carStorageExemptController
				.deletePoolCarStorageExempt(carStorageExemptDTOList);
		assertEquals(deleteCarExempt.getStatusCodeValue(), 500);

		when(carStorageExemptService.getAllPoolCarStorageExempts()).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<List<PoolCarStorageExemptDTO>>> getCarExempt = carStorageExemptController
				.getAllPoolCarStorageExempts();
		assertEquals(getCarExempt.getStatusCodeValue(), 500);
	}

	@Test
	void testEmptyListResponse() {
			when(carStorageExemptService.getAllPoolCarStorageExempts()).thenReturn(Collections.emptyList());
			ResponseEntity<APIResponse<List<PoolCarStorageExemptDTO>>> getCarExempt = carStorageExemptController
					.getAllPoolCarStorageExempts();
			assertEquals(getCarExempt.getStatusCodeValue(), 200);
			when(carStorageExemptService.addPoolCarStorageExempt(Mockito.any(), Mockito.any()))
					.thenReturn(carStorageExempt);
			ResponseEntity<List<APIResponse<PoolCarStorageExemptDTO>>> addCarExempt = carStorageExemptController
					.addPoolCarStorageExempt(Collections.emptyList(), header);
			assertEquals(addCarExempt.getStatusCodeValue(), 500);
			when(carStorageExemptService.deletePoolCarStorageExempt(Mockito.any())).thenReturn(carStorageExempt);
			ResponseEntity<List<APIResponse<PoolCarStorageExemptDTO>>> deleteCarExempt = carStorageExemptController
					.deletePoolCarStorageExempt(Collections.emptyList());
			assertEquals(deleteCarExempt.getStatusCodeValue(), 500);
	}
}
