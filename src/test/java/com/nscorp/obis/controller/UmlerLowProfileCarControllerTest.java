package com.nscorp.obis.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nscorp.obis.dto.mapper.UmlerLowProfileCarMapper;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.SizeExceedException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.nscorp.obis.domain.UmlerLowProfileCar;
import com.nscorp.obis.dto.UmlerLowProfileCarDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.UmlerLowProfileCarService;

class UmlerLowProfileCarControllerTest {
	
	@InjectMocks
	UmlerLowProfileCarController umlerLowProfileCarController;

	@Mock
	UmlerLowProfileCarMapper umlerLowProfileCarMapper;

	@Mock
	UmlerLowProfileCarService umlerLowProfileCarService;

	UmlerLowProfileCarDTO umlerLowProfileCarDTO;
	UmlerLowProfileCar umlerLowProfileCar;

	List<UmlerLowProfileCarDTO> umlerLowProfileCarDTOList;

	List<UmlerLowProfileCar> umlerLowProfileCarList;

	Map<String, String> httpHeaders;

	String carInit;

	String aarType;
	Map<String, String> header;

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		umlerLowProfileCarDTO = new UmlerLowProfileCarDTO();
		umlerLowProfileCar = new UmlerLowProfileCar();
		umlerLowProfileCarDTOList = new ArrayList<>();
		umlerLowProfileCarList = new ArrayList<>();
		httpHeaders = new HashMap<>();
		umlerLowProfileCarDTOList.add(umlerLowProfileCarDTO);
		umlerLowProfileCarList.add(umlerLowProfileCar);
		httpHeaders.put("userid", "TEST");
		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");
	}

	@AfterEach
	void tearDown() throws Exception {
		umlerLowProfileCarDTO = null;
		umlerLowProfileCar = null;
		umlerLowProfileCarDTOList = null;
		umlerLowProfileCarList = null;
		httpHeaders = null;
	}

	@Test
	void testGetUmlerLowProfileCars() {
		when(umlerLowProfileCarService.getUmlerLowProfileCars(Mockito.any(), Mockito.any()))
				.thenReturn(umlerLowProfileCarList);
		ResponseEntity<APIResponse<List<UmlerLowProfileCarDTO>>> getResponse = umlerLowProfileCarController
				.getUmlerLowProfileCars(aarType, carInit);
		assertEquals(HttpStatus.OK, getResponse.getStatusCode());

	}

	@Test
	void testUpdateUmlerLowProfileCars() {
		when(umlerLowProfileCarMapper.UmlerLowProfileCarDTOToUmlerLowProfileCar(Mockito.any())).thenReturn(umlerLowProfileCar);
		when(umlerLowProfileCarService.updateUmlerLowProfileCars(Mockito.any(), Mockito.any())).thenReturn(umlerLowProfileCar);
		when(umlerLowProfileCarMapper.UmlerLowProfileCarToUmlerLowProfileCarDTO(Mockito.any())).thenReturn(umlerLowProfileCarDTO);
		ResponseEntity<APIResponse<UmlerLowProfileCarDTO>> tableUpdated = umlerLowProfileCarController.updateLowProfileCar(umlerLowProfileCarDTO, header);
		assertNotNull(tableUpdated.getBody());
	}

	@Test
	void testNoRecordFoundException() {
		when(umlerLowProfileCarService.getUmlerLowProfileCars(Mockito.any(), Mockito.any()))
				.thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<List<UmlerLowProfileCarDTO>>> getResponse = umlerLowProfileCarController
				.getUmlerLowProfileCars(aarType, carInit);
		assertEquals(HttpStatus.NOT_FOUND, getResponse.getStatusCode());

		when(umlerLowProfileCarService.updateUmlerLowProfileCars(Mockito.any(),Mockito.any())).thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<UmlerLowProfileCarDTO>> updateResponse =  umlerLowProfileCarController.updateLowProfileCar(Mockito.any(),Mockito.any());
		assertEquals(updateResponse.getStatusCodeValue(),404);
		
		when(umlerLowProfileCarService.deleteProfileLoad(Mockito.any()))
			.thenThrow(new NoRecordsFoundException());
		ResponseEntity<List<APIResponse<UmlerLowProfileCarDTO>>> deleteResponse = umlerLowProfileCarController
			.deleteProfileCar(umlerLowProfileCarDTOList);
		assertEquals(deleteResponse.getStatusCodeValue(), 500);
	}

	@Test
	void TestAddUpdateRecordNotAddedException() {
		when(umlerLowProfileCarService.updateUmlerLowProfileCars(Mockito.any(),Mockito.any())).thenThrow(new RecordNotAddedException());
		ResponseEntity<APIResponse<UmlerLowProfileCarDTO>> updateResponse =  umlerLowProfileCarController.updateLowProfileCar(Mockito.any(),Mockito.any());
		assertEquals(updateResponse.getStatusCodeValue(),406);
	}

	@Test
	void TestAddUpdateNullPointerException() {
		when(umlerLowProfileCarService.updateUmlerLowProfileCars(Mockito.any(),Mockito.any())).thenThrow(new NullPointerException());
		ResponseEntity<APIResponse<UmlerLowProfileCarDTO>> updateResponse =  umlerLowProfileCarController.updateLowProfileCar(Mockito.any(),Mockito.any());
		assertEquals(updateResponse.getStatusCodeValue(),400);
	}

	@Test
	void TestAddUpdateSizeExceedException() {
		when(umlerLowProfileCarService.updateUmlerLowProfileCars(Mockito.any(),Mockito.any())).thenThrow(new SizeExceedException());
		ResponseEntity<APIResponse<UmlerLowProfileCarDTO>> updateResponse =  umlerLowProfileCarController.updateLowProfileCar(Mockito.any(),Mockito.any());
		assertEquals(updateResponse.getStatusCodeValue(),411);
	}

	@Test
	void TestException() {
		when(umlerLowProfileCarService.getUmlerLowProfileCars(Mockito.any(), Mockito.any()))
				.thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<List<UmlerLowProfileCarDTO>>> getResponse = umlerLowProfileCarController
				.getUmlerLowProfileCars(aarType, carInit);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, getResponse.getStatusCode());

		when(umlerLowProfileCarService.updateUmlerLowProfileCars(Mockito.any(),Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<UmlerLowProfileCarDTO>> updateResponse =  umlerLowProfileCarController.updateLowProfileCar(Mockito.any(),Mockito.any());
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,updateResponse.getStatusCode());
		
		when(umlerLowProfileCarService.deleteProfileLoad(Mockito.any()))
			.thenThrow(new RuntimeException());
		ResponseEntity<List<APIResponse<UmlerLowProfileCarDTO>>> deleteResponse = umlerLowProfileCarController
			.deleteProfileCar(umlerLowProfileCarDTOList);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, deleteResponse.getStatusCode());

	}
	
	@Test
	void testDeleteUmlerLowProfileCars() {
		when(umlerLowProfileCarService.deleteProfileLoad(Mockito.any())).thenReturn(umlerLowProfileCar);
		ResponseEntity<List<APIResponse<UmlerLowProfileCarDTO>>> deleteResponse = umlerLowProfileCarController.deleteProfileCar(umlerLowProfileCarDTOList);
		assertEquals(deleteResponse.getStatusCodeValue(), 200);
	}
	
	@Test
	void testDeleteEmptyList(){
		when(umlerLowProfileCarService.deleteProfileLoad(Mockito.any())).thenReturn(umlerLowProfileCar);
		ResponseEntity<List<APIResponse<UmlerLowProfileCarDTO>>> deleteException = umlerLowProfileCarController.deleteProfileCar(Collections.emptyList());
		assertEquals(deleteException.getStatusCodeValue(), 500);
	}

}
