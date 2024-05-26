package com.nscorp.obis.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.nscorp.obis.domain.GuaranteeCustCrossRef;
import com.nscorp.obis.dto.GuaranteeCustCrossRefDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.GuaranteeCustCrossRefService;

class GuaranteeCustCrossRefControllerTest {

	@InjectMocks
	GuaranteeCustCrossRefController crossRefController;

	@Mock
	GuaranteeCustCrossRefService crossRefService;

	GuaranteeCustCrossRefDTO crossRefDTO;
	GuaranteeCustCrossRef crossRef;

	List<GuaranteeCustCrossRefDTO> crossRefDTOList;

	List<GuaranteeCustCrossRef> crossRefList;

	Map<String, String> httpHeaders;

	String customerName;

	String terminalName;

	String customerNumber;

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		crossRefDTO = new GuaranteeCustCrossRefDTO();
		crossRef = new GuaranteeCustCrossRef();
		crossRefDTOList = new ArrayList<>();
		crossRefList = new ArrayList<>();
		httpHeaders = new HashMap<>();
		customerName = "TEST";
		terminalName = "TEST";
		customerNumber = "TEST";
		crossRef.setGuaranteeCustXrefId(100L);
		crossRefList.add(crossRef);
		crossRefDTO.setGuaranteeCustXrefId(100L);
		crossRefDTOList.add(crossRefDTO);

		httpHeaders.put("userid", "TEST");
	}

	@AfterEach
	void tearDown() throws Exception {
		crossRefDTO = null;
		crossRef = null;
		crossRefDTOList = null;
		crossRefList = null;
		httpHeaders = null;
	}

	@Test
	void testGetAllGuaranteeCustCrossRef() {
		when(crossRefService.getAllGuaranteeCustCrossRef(Mockito.any(), Mockito.any(), Mockito.any()))
				.thenReturn(crossRefList);
		ResponseEntity<APIResponse<List<GuaranteeCustCrossRefDTO>>> allGuaranteeCustCrossRef = crossRefController
				.getAllGuaranteeCustCrossRef(customerName, customerNumber, terminalName);
		assertEquals(HttpStatus.OK, allGuaranteeCustCrossRef.getStatusCode());

	}

	@Test
	void testAddGuaranteeCustCrossRef() {
		when(crossRefService.addGuaranteeCustCrossRef(Mockito.any(), Mockito.any())).thenReturn(crossRef);
		ResponseEntity<APIResponse<GuaranteeCustCrossRefDTO>> addGuaranteeCustCrossRef = crossRefController
				.addGuaranteeCustCrossRef(crossRefDTO, httpHeaders);
		assertEquals(HttpStatus.OK, addGuaranteeCustCrossRef.getStatusCode());
	}

	@Test
	void testUpdateGuaranteeCustCrossRef() {
		when(crossRefService.updateGuaranteeCustCrossRef(Mockito.any(), Mockito.any())).thenReturn(crossRef);
		ResponseEntity<APIResponse<GuaranteeCustCrossRefDTO>> updateGuaranteeCustCrossRef = crossRefController
				.updateGuaranteeCustCrossRef(crossRefDTO, httpHeaders);
		assertEquals(HttpStatus.OK, updateGuaranteeCustCrossRef.getStatusCode());
	}

	@Test
	void testDeleteGuaranteeCustCrossRef() {
		when(crossRefService.deleteGuaranteeCustCrossRef(Mockito.any(), Mockito.any())).thenReturn(crossRef);
		ResponseEntity<List<APIResponse<GuaranteeCustCrossRefDTO>>> deleteGuaranteeCustCrossRef = crossRefController
				.deleteGuaranteeCustCrossRef(crossRefDTOList, httpHeaders);
		assertEquals(HttpStatus.OK, deleteGuaranteeCustCrossRef.getStatusCode());
	}

	@Test
	void testNoRecordFoundException() {
		when(crossRefService.getAllGuaranteeCustCrossRef(Mockito.any(), Mockito.any(), Mockito.any()))
				.thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<List<GuaranteeCustCrossRefDTO>>> allGuaranteeCustCrossRef = crossRefController
				.getAllGuaranteeCustCrossRef(customerName, customerNumber, terminalName);
		assertEquals(HttpStatus.NOT_FOUND, allGuaranteeCustCrossRef.getStatusCode());

		when(crossRefService.updateGuaranteeCustCrossRef(Mockito.any(), Mockito.any()))
				.thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<GuaranteeCustCrossRefDTO>> updateGuaranteeCustCrossRef = crossRefController
				.updateGuaranteeCustCrossRef(crossRefDTO, httpHeaders);
		assertEquals(HttpStatus.NOT_FOUND, updateGuaranteeCustCrossRef.getStatusCode());

		when(crossRefService.deleteGuaranteeCustCrossRef(Mockito.any(), Mockito.any()))
				.thenThrow(new NoRecordsFoundException());
		ResponseEntity<List<APIResponse<GuaranteeCustCrossRefDTO>>> deleteGuaranteeCustCrossRef = crossRefController
				.deleteGuaranteeCustCrossRef(crossRefDTOList, httpHeaders);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, deleteGuaranteeCustCrossRef.getStatusCode());
	}

	@Test
	void testRecordNotAddedException() {
		when(crossRefService.addGuaranteeCustCrossRef(Mockito.any(), Mockito.any()))
				.thenThrow(new RecordNotAddedException());
		ResponseEntity<APIResponse<GuaranteeCustCrossRefDTO>> addGuaranteeCustCrossRef = crossRefController
				.addGuaranteeCustCrossRef(crossRefDTO, httpHeaders);
		assertEquals(HttpStatus.NOT_ACCEPTABLE, addGuaranteeCustCrossRef.getStatusCode());

		when(crossRefService.updateGuaranteeCustCrossRef(Mockito.any(), Mockito.any()))
				.thenThrow(new RecordNotAddedException());
		ResponseEntity<APIResponse<GuaranteeCustCrossRefDTO>> updateGuaranteeCustCrossRef = crossRefController
				.updateGuaranteeCustCrossRef(crossRefDTO, httpHeaders);
		assertEquals(HttpStatus.NOT_ACCEPTABLE, updateGuaranteeCustCrossRef.getStatusCode());

	}

	@Test
	void testRecordAlreadyExistsException() {
		when(crossRefService.addGuaranteeCustCrossRef(Mockito.any(), Mockito.any()))
				.thenThrow(new RecordAlreadyExistsException());
		ResponseEntity<APIResponse<GuaranteeCustCrossRefDTO>> addGuaranteeCustCrossRef = crossRefController
				.addGuaranteeCustCrossRef(crossRefDTO, httpHeaders);
		assertEquals(HttpStatus.NOT_ACCEPTABLE, addGuaranteeCustCrossRef.getStatusCode());

		when(crossRefService.updateGuaranteeCustCrossRef(Mockito.any(), Mockito.any()))
				.thenThrow(new RecordAlreadyExistsException());
		ResponseEntity<APIResponse<GuaranteeCustCrossRefDTO>> updateGuaranteeCustCrossRef = crossRefController
				.updateGuaranteeCustCrossRef(crossRefDTO, httpHeaders);
		assertEquals(HttpStatus.NOT_ACCEPTABLE, updateGuaranteeCustCrossRef.getStatusCode());
	}

	@Test
	void TestNullPointerException() {
		when(crossRefService.addGuaranteeCustCrossRef(Mockito.any(), Mockito.any()))
				.thenThrow(new NullPointerException());
		ResponseEntity<APIResponse<GuaranteeCustCrossRefDTO>> addGuaranteeCustCrossRef = crossRefController
				.addGuaranteeCustCrossRef(crossRefDTO, httpHeaders);
		assertEquals(HttpStatus.BAD_REQUEST, addGuaranteeCustCrossRef.getStatusCode());

		when(crossRefService.updateGuaranteeCustCrossRef(Mockito.any(), Mockito.any()))
				.thenThrow(new NullPointerException());
		ResponseEntity<APIResponse<GuaranteeCustCrossRefDTO>> updateGuaranteeCustCrossRef = crossRefController
				.updateGuaranteeCustCrossRef(crossRefDTO, httpHeaders);
		assertEquals(HttpStatus.BAD_REQUEST, updateGuaranteeCustCrossRef.getStatusCode());
	}

	@Test
	void TestException() {
		when(crossRefService.getAllGuaranteeCustCrossRef(Mockito.any(), Mockito.any(), Mockito.any()))
				.thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<List<GuaranteeCustCrossRefDTO>>> allGuaranteeCustCrossRef = crossRefController
				.getAllGuaranteeCustCrossRef(customerName, customerNumber, terminalName);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, allGuaranteeCustCrossRef.getStatusCode());
		when(crossRefService.addGuaranteeCustCrossRef(Mockito.any(), Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<GuaranteeCustCrossRefDTO>> addGuaranteeCustCrossRef = crossRefController
				.addGuaranteeCustCrossRef(crossRefDTO, httpHeaders);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, addGuaranteeCustCrossRef.getStatusCode());

		when(crossRefService.updateGuaranteeCustCrossRef(Mockito.any(), Mockito.any()))
				.thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<GuaranteeCustCrossRefDTO>> updateGuaranteeCustCrossRef = crossRefController
				.updateGuaranteeCustCrossRef(crossRefDTO, httpHeaders);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, updateGuaranteeCustCrossRef.getStatusCode());

		when(crossRefService.deleteGuaranteeCustCrossRef(Mockito.any(), Mockito.any()))
				.thenThrow(new RuntimeException());
		ResponseEntity<List<APIResponse<GuaranteeCustCrossRefDTO>>> deleteGuaranteeCustCrossRef = crossRefController
				.deleteGuaranteeCustCrossRef(crossRefDTOList, httpHeaders);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, deleteGuaranteeCustCrossRef.getStatusCode());
	}
}
