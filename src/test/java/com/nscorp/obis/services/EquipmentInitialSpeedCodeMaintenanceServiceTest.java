package com.nscorp.obis.services;

import static org.junit.jupiter.api.Assertions.*;
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
import org.mockito.MockitoAnnotations;

import com.nscorp.obis.domain.EquipmentInitialSpeedCodeMaintenance;
import com.nscorp.obis.dto.EquipmentInitialSpeedCodeMaintenanceDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.repository.EquipmentInitialSpeedCodeMaintenanceRepository;

class EquipmentInitialSpeedCodeMaintenanceServiceTest {
	
	@InjectMocks
	EquipmentInitialSpeedCodeMaintenanceServiceImpl eqInitSpeedCodeService;
	
	@Mock
	EquipmentInitialSpeedCodeMaintenanceRepository eqInitSpeedCodeRepo;
	
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
		
		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");
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
		when(eqInitSpeedCodeRepo.findAll(eqInit)).thenReturn(eqInitSpeedCodeList);
		List<EquipmentInitialSpeedCodeMaintenance> alllSpeedCode = eqInitSpeedCodeService.getAllInitSpeedCode(eqInit);
		assertEquals(alllSpeedCode, eqInitSpeedCodeList);
	}
	
	@Test
	void testGetAllInitSpeedCodeException() {
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(eqInitSpeedCodeService.getAllInitSpeedCode(eqInit)));
		assertEquals("No Records found!", exception.getMessage());
	}

}
