package com.nscorp.obis.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.OptimisticLockException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.nscorp.obis.dto.DamageComponentReasonDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.DamageComponentReasonService;

public class DamageComponentReasonControllerTest {

	@InjectMocks
	DamageComponentReasonController controller;

	@Mock
	DamageComponentReasonService service;

	DamageComponentReasonDTO dto;

	List<DamageComponentReasonDTO> dtos;

	Integer jobCode = 1103;
	Integer aarWhyMadeCode = 11;
	String orderCode = "A";
	String sizeRequired = "Y";
	Map<String, String> headers=new HashMap<>();

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		dto = new DamageComponentReasonDTO();
		dtos = new ArrayList<>();
		dtos.add(dto);
	}

	@AfterEach
	void tearDown() {
		dto = null;
		dtos = null;
	}

	@Test
	void testGetAllDamageComponentReasons() {
		when(service.getDamageComponentReasons(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(),
				Mockito.anyString())).thenReturn(dtos);
		ResponseEntity<APIResponse<List<DamageComponentReasonDTO>>> response = controller
				.getAllDamageComponentReasons(jobCode, aarWhyMadeCode, orderCode, sizeRequired);
		assertEquals(200, response.getStatusCodeValue());
	}

	@Test
	void testDeleteDamageComponentReason() {
		when(service.deleteDamageComponentReason(Mockito.any())).thenReturn(dto);
		ResponseEntity<List<APIResponse<DamageComponentReasonDTO>>> response = controller
				.deleteDamageComponentReason(dtos);
		assertEquals(200, response.getStatusCodeValue());
	}
	
	@Test
	void testCreateDamageComponentReason() {
		when(service.createDamageComponentReason(Mockito.any(),Mockito.anyMap())).thenReturn(dto);
		ResponseEntity<APIResponse<DamageComponentReasonDTO>> response = controller
				.createDamageComponentReasons(dto, headers);
		assertEquals(200, response.getStatusCodeValue());
	}
	
	@Test
	void testUpdateDamageComponentReason() {
		when(service.updateDamageComponentReason(Mockito.any(),Mockito.anyMap())).thenReturn(dto);
		ResponseEntity<APIResponse<DamageComponentReasonDTO>> response = controller
				.updateDamageComponentReasons(dto, headers);
		assertEquals(200, response.getStatusCodeValue());
	}
	
	@Test
	void testNoRecordsFoundException() {
		when(service.getDamageComponentReasons(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(),
				Mockito.anyString())).thenThrow(NoRecordsFoundException.class);
		ResponseEntity<APIResponse<List<DamageComponentReasonDTO>>> response = controller
				.getAllDamageComponentReasons(jobCode, aarWhyMadeCode, orderCode, sizeRequired);
		assertEquals(404, response.getStatusCodeValue());
		when(service.deleteDamageComponentReason(Mockito.any())).thenThrow(NoRecordsFoundException.class);
		ResponseEntity<List<APIResponse<DamageComponentReasonDTO>>> response2 = controller
				.deleteDamageComponentReason(dtos);
		assertEquals(500, response2.getStatusCodeValue());
		when(service.createDamageComponentReason(Mockito.any(),Mockito.anyMap())).thenThrow(NoRecordsFoundException.class);
		ResponseEntity<APIResponse<DamageComponentReasonDTO>> response3 = controller
				.createDamageComponentReasons(dto, headers);
		assertEquals(404, response3.getStatusCodeValue());
		when(service.updateDamageComponentReason(Mockito.any(),Mockito.anyMap())).thenThrow(NoRecordsFoundException.class);
		ResponseEntity<APIResponse<DamageComponentReasonDTO>> response4 = controller
				.updateDamageComponentReasons(dto, headers);
		assertEquals(404, response4.getStatusCodeValue());
		
	}
	
	@Test
	void testException() {
		when(service.getDamageComponentReasons(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(),
				Mockito.anyString())).thenThrow(RuntimeException.class);
		ResponseEntity<APIResponse<List<DamageComponentReasonDTO>>> response = controller
				.getAllDamageComponentReasons(jobCode, aarWhyMadeCode, orderCode, sizeRequired);
		assertEquals(500, response.getStatusCodeValue());
		when(service.deleteDamageComponentReason(Mockito.any())).thenThrow(OptimisticLockException.class);
		ResponseEntity<List<APIResponse<DamageComponentReasonDTO>>> response2 = controller
				.deleteDamageComponentReason(dtos);
		assertEquals(500, response2.getStatusCodeValue());
		when(service.createDamageComponentReason(Mockito.any(),Mockito.anyMap())).thenThrow(RuntimeException.class);
		ResponseEntity<APIResponse<DamageComponentReasonDTO>> response3 = controller
				.createDamageComponentReasons(dto, headers);
		assertEquals(500, response3.getStatusCodeValue());
		when(service.updateDamageComponentReason(Mockito.any(),Mockito.anyMap())).thenThrow(RuntimeException.class);
		ResponseEntity<APIResponse<DamageComponentReasonDTO>> response4 = controller
				.updateDamageComponentReasons(dto, headers);
		assertEquals(500, response4.getStatusCodeValue());
	}

}
