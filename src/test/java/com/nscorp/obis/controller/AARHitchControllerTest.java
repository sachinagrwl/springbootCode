package com.nscorp.obis.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.nscorp.obis.domain.AARHitch;
import com.nscorp.obis.dto.AARHitchDTO;
import com.nscorp.obis.dto.mapper.AARHitchMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.AARHitchService;

class AARHitchControllerTest {

	@InjectMocks
	AARHitchController aarHitchController;
	
	@Mock
	AARHitchMapper aarHitchMapper;
	
	@Mock
	AARHitchService aarHitchService;
	
	AARHitch aarHitch;
	AARHitchDTO aarHitchDto;
	List<AARHitch> aarHitchList;
	List<AARHitchDTO> aarHitchDtoList;
	
	Map<String, String> header;
	String aarType;
	String hitchLocation;
	
	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		aarHitch = new AARHitch();
		aarHitch.setAarType("P110");
		aarHitch.setHitchLocation("A1");
		aarHitchList = new ArrayList<>();
		aarHitchList.add(aarHitch);
		
		aarHitchDto = new AARHitchDTO();
		aarHitchDto.setAarType("P110");
		aarHitchDto.setHitchLocation("A1");
		aarHitchDtoList = new ArrayList<>();
		aarHitchDtoList.add(aarHitchDto);
	}

	@AfterEach
	void tearDown() throws Exception {
		aarHitch = null;
		aarHitchList = null;
		aarHitchDto = null;
		aarHitchDtoList = null;
	}

	@Test
	void testGetAllHitch() {
		when(aarHitchService.getAllHitch(aarType, hitchLocation)).thenReturn(aarHitchList);
		ResponseEntity<APIResponse<List<AARHitchDTO>>> getAARHitch = aarHitchController.getAllHitch(aarType, hitchLocation);
		assertEquals(getAARHitch.getStatusCodeValue(), 200);
	}
	
	@Test
	void testGetAllHitchNoRecordsFoundException() {
		when(aarHitchService.getAllHitch(aarType, hitchLocation)).thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<List<AARHitchDTO>>> getAARHitch = aarHitchController.getAllHitch(aarType, hitchLocation);
		assertEquals(getAARHitch.getStatusCodeValue(), 404);
	}
	
	@Test
	void testGetAllHitchException() {
		when(aarHitchService.getAllHitch(aarType, hitchLocation)).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<List<AARHitchDTO>>> getAARHitch = aarHitchController.getAllHitch(aarType, hitchLocation);
		assertEquals(getAARHitch.getStatusCodeValue(), 500);
	}

}
