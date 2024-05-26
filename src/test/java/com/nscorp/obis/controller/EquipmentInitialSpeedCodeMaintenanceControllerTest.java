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

import com.nscorp.obis.domain.EquipmentInitialSpeedCodeMaintenance;
import com.nscorp.obis.dto.EquipmentInitialSpeedCodeMaintenanceDTO;
import com.nscorp.obis.dto.mapper.EquipmentInitialSpeedCodeMaintenanceMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.EquipmentInitialSpeedCodeMaintenanceService;

class EquipmentInitialSpeedCodeMaintenanceControllerTest {
	
	@InjectMocks
	EquipmentInitialSpeedCodeMaintenanceController eqInitSpeedCodeController;
	
	@Mock
	EquipmentInitialSpeedCodeMaintenanceMapper eqInitSpeedCodeMapper;
	
	@Mock
	EquipmentInitialSpeedCodeMaintenanceService eqInitSpeedCodeService;
	
	EquipmentInitialSpeedCodeMaintenance eqInitSpeedCode;
	EquipmentInitialSpeedCodeMaintenanceDTO eqInitSpeedCodeDto;
	List<EquipmentInitialSpeedCodeMaintenance> eqInitSpeedCodeList;
	List<EquipmentInitialSpeedCodeMaintenanceDTO> eqInitSpeedCodeDtoList;
	
	Map<String, String> header;

	String eqType;
	String eqInitShort;
	String eqInit;
	
	
	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		eqInitSpeedCode = new EquipmentInitialSpeedCodeMaintenance();
		eqInitSpeedCode.setEqType("C");
		eqInitSpeedCode.setEqInitShort("A");
		eqInitSpeedCode.setEqInit("APUL");
		
		eqInitSpeedCodeList = new ArrayList<>();
		eqInitSpeedCodeList.add(eqInitSpeedCode);
		
		eqInitSpeedCodeDto = new EquipmentInitialSpeedCodeMaintenanceDTO();
		eqInitSpeedCodeDto.setEqType("C");
		eqInitSpeedCodeDto.setEqInitShort("A");
		eqInitSpeedCodeDto.setEqInit("APUL");
		
		eqInitSpeedCodeDtoList = new ArrayList<>();
		eqInitSpeedCodeDtoList.add(eqInitSpeedCodeDto);
	}

	@AfterEach
	void tearDown() throws Exception {
		eqInitSpeedCode = null;
		eqInitSpeedCodeList = null;
		eqInitSpeedCodeDto = null;
		eqInitSpeedCodeDtoList = null;
	}

	@Test
	void testGetAllInitSpeedCode() {
		when(eqInitSpeedCodeService.getAllInitSpeedCode(eqInit)).thenReturn(eqInitSpeedCodeList);
		ResponseEntity<APIResponse<List<EquipmentInitialSpeedCodeMaintenanceDTO>>> getInitSpeedCode = eqInitSpeedCodeController.getAllInitSpeedCode(eqInit);
		assertEquals(getInitSpeedCode.getStatusCodeValue(), 200);
	}
	
	@Test
	void testGetAllInitSpeedCodeNoRecordsFoundException() {
		when(eqInitSpeedCodeService.getAllInitSpeedCode(eqInit)).thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<List<EquipmentInitialSpeedCodeMaintenanceDTO>>> getInitSpeedCode = eqInitSpeedCodeController.getAllInitSpeedCode(eqInit);
		assertEquals(getInitSpeedCode.getStatusCodeValue(), 404);
	}

	@Test
	void testGetAllInitSpeedCodeException() {
		when(eqInitSpeedCodeService.getAllInitSpeedCode(eqInit)).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<List<EquipmentInitialSpeedCodeMaintenanceDTO>>> getInitSpeedCode = eqInitSpeedCodeController.getAllInitSpeedCode(eqInit);
		assertEquals(getInitSpeedCode.getStatusCodeValue(), 500);
	}
}
