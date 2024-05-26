package com.nscorp.obis.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nscorp.obis.dto.mapper.UmlerStackCarMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.nscorp.obis.domain.UmlerStackCar;
import com.nscorp.obis.dto.UmlerStackCarDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.SizeExceedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.UmlerStackCarService;

class UmlerStackCarControllerTest {
	@InjectMocks
	UmlerStackCarController umlerStackCarController;

	@Mock
	UmlerStackCarMapper umlerStackCarMapper;

	@Mock
	UmlerStackCarService umlerStackCarService;

	UmlerStackCarDTO umlerStackCarDTO;
	UmlerStackCar umlerStackCar;

	List<UmlerStackCarDTO> umlerStackCarDTOList;

	List<UmlerStackCar> umlerStackCarList;

	Map<String, String> httpHeaders;

	String carInit;

	String aarType;

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		umlerStackCarDTO = new UmlerStackCarDTO();
		umlerStackCar = new UmlerStackCar();
		umlerStackCarDTOList = new ArrayList<>();
		umlerStackCarList = new ArrayList<>();
		httpHeaders = new HashMap<>();
		umlerStackCarDTOList.add(umlerStackCarDTO);
		umlerStackCarList.add(umlerStackCar);
		httpHeaders.put("userid", "TEST");

	}

	@AfterEach
	void tearDown() throws Exception {
		umlerStackCarDTO = null;
		umlerStackCar = null;
		umlerStackCarDTOList = null;
		umlerStackCarList = null;
		httpHeaders = null;
	}

	@Test
	void testGetUmlerLowProfileCars() {
		when(umlerStackCarService.getUmlerStackCars(Mockito.any(), Mockito.any())).thenReturn(umlerStackCarList);
		ResponseEntity<APIResponse<List<UmlerStackCarDTO>>> getResponse = umlerStackCarController
				.getUmlerStackCars(aarType, carInit);
		assertEquals(HttpStatus.OK, getResponse.getStatusCode());

	}
	
	@Test
	void testAddStackCars() {
		when(umlerStackCarService.insertStackCar(Mockito.any(), Mockito.any())).thenReturn(umlerStackCar);
		ResponseEntity<APIResponse<UmlerStackCarDTO>> addResponse = umlerStackCarController.addStackCar(umlerStackCarDTO, httpHeaders);
		assertNotNull(addResponse.getBody());
	}

	@Test
	void testUpdateStack() {
		when(umlerStackCarMapper.umlerStackCarDTOToUmlerStackCar(Mockito.any())).thenReturn(umlerStackCar);
		when(umlerStackCarService.updateStackCar(Mockito.any(), Mockito.any())).thenReturn(umlerStackCar);
		when(umlerStackCarMapper.umlerStackCarToUmlerStackCarDTO(Mockito.any())).thenReturn(umlerStackCarDTO);
		ResponseEntity<APIResponse<UmlerStackCarDTO>> tableUpdated = umlerStackCarController.updateStackCar(umlerStackCarDTO, httpHeaders);
		assertNotNull(tableUpdated.getBody());
	}

	@Test
	void testNoRecordFoundException() {
		when(umlerStackCarService.getUmlerStackCars(Mockito.any(), Mockito.any()))
				.thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<List<UmlerStackCarDTO>>> getResponse = umlerStackCarController
				.getUmlerStackCars(aarType, carInit);
		assertEquals(HttpStatus.NOT_FOUND, getResponse.getStatusCode());
		
		when(umlerStackCarService.insertStackCar(Mockito.any(), Mockito.any())).thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<UmlerStackCarDTO>> addResponse = umlerStackCarController.addStackCar(umlerStackCarDTO, httpHeaders);
		assertEquals(addResponse.getStatusCodeValue(),404);

		when(umlerStackCarService.updateStackCar(Mockito.any(), Mockito.any())).thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<UmlerStackCarDTO>> updateResponse = umlerStackCarController.updateStackCar(umlerStackCarDTO, httpHeaders);
		assertEquals(updateResponse.getStatusCodeValue(),404);

		when(umlerStackCarService.deleteUmlerStackCar(Mockito.any())).thenThrow(new NoRecordsFoundException());
		ResponseEntity<List<APIResponse<UmlerStackCarDTO>>> deleteResponse = umlerStackCarController
				.deleteUmlerStackCar(umlerStackCarDTOList);
		assertEquals(deleteResponse.getStatusCodeValue(), 500);
	}

	@Test
	void TestException() {
		when(umlerStackCarService.getUmlerStackCars(Mockito.any(), Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<List<UmlerStackCarDTO>>> getResponse = umlerStackCarController
				.getUmlerStackCars(aarType, carInit);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, getResponse.getStatusCode());
		
		when(umlerStackCarService.insertStackCar(Mockito.any(), Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<UmlerStackCarDTO>> addResponse = umlerStackCarController.addStackCar(umlerStackCarDTO, httpHeaders);
		assertEquals(addResponse.getStatusCodeValue(),500);

		when(umlerStackCarService.updateStackCar(Mockito.any(), Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<UmlerStackCarDTO>> updateResponse = umlerStackCarController.updateStackCar(umlerStackCarDTO, httpHeaders);
		assertEquals(updateResponse.getStatusCodeValue(),500);

		when(umlerStackCarService.deleteUmlerStackCar(Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<List<APIResponse<UmlerStackCarDTO>>> deleteResponse = umlerStackCarController
				.deleteUmlerStackCar(umlerStackCarDTOList);
		assertEquals(deleteResponse.getStatusCodeValue(), 500);

	}
	
	@Test
	void testAddUpdateSizeExceedException() {
		when(umlerStackCarService.insertStackCar(Mockito.any(), Mockito.any())).thenThrow(new SizeExceedException());
		ResponseEntity<APIResponse<UmlerStackCarDTO>> addResponse = umlerStackCarController.addStackCar(umlerStackCarDTO, httpHeaders);
		assertEquals(addResponse.getStatusCodeValue(),411);

		when(umlerStackCarService.updateStackCar(Mockito.any(), Mockito.any())).thenThrow(new SizeExceedException());
		ResponseEntity<APIResponse<UmlerStackCarDTO>> updateResponse = umlerStackCarController.updateStackCar(umlerStackCarDTO, httpHeaders);
		assertEquals(updateResponse.getStatusCodeValue(),411);
	}
	
	@Test
	void testAddUpdateRecordNotAddedException() {
		when(umlerStackCarService.insertStackCar(Mockito.any(), Mockito.any())).thenThrow(new RecordNotAddedException());
		ResponseEntity<APIResponse<UmlerStackCarDTO>> addResponse = umlerStackCarController.addStackCar(umlerStackCarDTO, httpHeaders);
		assertEquals(addResponse.getStatusCodeValue(),406);

		when(umlerStackCarService.updateStackCar(Mockito.any(), Mockito.any())).thenThrow(new RecordNotAddedException());
		ResponseEntity<APIResponse<UmlerStackCarDTO>> updateResponse = umlerStackCarController.updateStackCar(umlerStackCarDTO, httpHeaders);
		assertEquals(updateResponse.getStatusCodeValue(),406);
	}
	
	@Test
	void testAddUpdateNullPointerException() {
		when(umlerStackCarService.insertStackCar(Mockito.any(), Mockito.any())).thenThrow(new NullPointerException());
		ResponseEntity<APIResponse<UmlerStackCarDTO>> addResponse = umlerStackCarController.addStackCar(umlerStackCarDTO, httpHeaders);
		assertEquals(addResponse.getStatusCodeValue(),400);

		when(umlerStackCarService.updateStackCar(Mockito.any(), Mockito.any())).thenThrow(new NullPointerException());
		ResponseEntity<APIResponse<UmlerStackCarDTO>> updateResponse = umlerStackCarController.updateStackCar(umlerStackCarDTO, httpHeaders);
		assertEquals(updateResponse.getStatusCodeValue(),400);
	}
	
	@Test
	void testAddUpdateRecordAlreadyExistsException() {
		when(umlerStackCarService.insertStackCar(Mockito.any(), Mockito.any())).thenThrow(new RecordAlreadyExistsException());
		ResponseEntity<APIResponse<UmlerStackCarDTO>> addResponse = umlerStackCarController.addStackCar(umlerStackCarDTO, httpHeaders);
		assertEquals(addResponse.getStatusCodeValue(),208);
	}

	@Test
	void testDeleteUmlerStackCar() {
		when(umlerStackCarService.deleteUmlerStackCar(Mockito.any())).thenReturn(umlerStackCar);
		ResponseEntity<List<APIResponse<UmlerStackCarDTO>>> deleteResponse = umlerStackCarController
				.deleteUmlerStackCar(umlerStackCarDTOList);
		assertEquals(deleteResponse.getStatusCodeValue(), 200);
	}

	@SuppressWarnings("unchecked")
	@Test
	void testDeleteEmptyList() {
		when(umlerStackCarService.deleteUmlerStackCar(Mockito.any())).thenReturn(umlerStackCar);
		ResponseEntity<List<APIResponse<UmlerStackCarDTO>>> deleteResponse = umlerStackCarController
				.deleteUmlerStackCar(Collections.EMPTY_LIST);
		assertEquals(deleteResponse.getStatusCodeValue(), 500);
	}

}
