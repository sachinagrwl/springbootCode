package com.nscorp.obis.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

import com.nscorp.obis.domain.Equipment;
import com.nscorp.obis.dto.EquipmentDTO;
import com.nscorp.obis.dto.mapper.EquipmentMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.EquipmentService;

public class EquipmentControllerTest {
	
	@Mock
	EquipmentService equipService;

	@Mock
	EquipmentMapper equipMapper;

	@InjectMocks
	EquipmentController equipController;

	Equipment equip;
	EquipmentDTO equipDTO;
	List<Equipment> equipList;
	List<EquipmentDTO> equipDtoList;
	Map<String, String> header;
	String equipInit;
	int equipNbr;


	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		equip = new Equipment();
		equip.setEquipInit("AAA");;
		equip.setEquipId("0");
		equip.setEquipType("C");
		equip.setEquipNbr(1234);
		equip.setQtUseRec("C");
		

		equipList = new ArrayList<>();

		equipList.add(equip);

		equipDTO = new EquipmentDTO();
		equip.setEquipInit("AAA");;
		equip.setEquipId("0");
		equip.setEquipType("C");
		equip.setEquipNbr(1234);
		equip.setQtUseRec("C");

		equipDtoList = new ArrayList<>();
		equipDtoList.add(equipDTO);

		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");
	}

	@AfterEach
	void tearDown() throws Exception {
		equip = null;
		equipList = null;
		equipDTO = null;
	}
	
	@Test
	void testGetAllEquipment() {

		when(equipService.getAllEquiList(Mockito.any(), Mockito.any())).thenReturn(equipList);
		ResponseEntity<APIResponse<List<EquipmentDTO>>> EquipList = equipController.getAllEquiList(equipInit, equipNbr);
		assertEquals(EquipList.getStatusCodeValue(), 200);
	}
	
	@Test
	void testEquipmentNoRecordsFoundException() {
		when(equipService.getAllEquiList(Mockito.any(), Mockito.any())).thenThrow (new NoRecordsFoundException());
		ResponseEntity<APIResponse<List<EquipmentDTO>>> equipList = equipController.getAllEquiList(equipInit, equipNbr);
		assertEquals(equipList.getStatusCodeValue(),200); 
	}
	
	@Test
	void testEquipmentException() {
		when(equipService.getAllEquiList(Mockito.any(), Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<List<EquipmentDTO>>> equiList = equipController.getAllEquiList(equipInit, equipNbr);
		assertEquals(equiList.getStatusCodeValue(),500);
	}


}
