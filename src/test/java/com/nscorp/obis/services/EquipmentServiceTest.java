package com.nscorp.obis.services;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

import com.nscorp.obis.domain.Equipment;
import com.nscorp.obis.dto.EquipmentDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.repository.EquipmentRepository;

public class EquipmentServiceTest {
	
	@InjectMocks
	EquipmentServiceImpl equipService;

	@Mock
	EquipmentRepository equipRepository;
	
	Equipment equip;
	EquipmentDTO equipDto;
	List<Equipment> equipList;
	List<EquipmentDTO> equipDtoList;

	Map<String, String> header;
	String equipInit;
	Integer equipNbr;


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

		equipDto = new EquipmentDTO();
		equip = new Equipment();
		equip.setEquipInit("AAA");;
		equip.setEquipId("0");
		equip.setEquipType("C");
		equip.setEquipNbr(1234);
		equip.setQtUseRec("C");

		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");
	}

	@AfterEach
	void tearDown() throws Exception {
		equip = null;
		equipDto = null;
		equipDtoList = null;
		equipList = null;
	}
	
	@Test
	void testGetEquipment() {
		when(equipRepository.findByEquipInitAndEquipNbr(Mockito.any(), Mockito.any())).thenReturn(equipList);		
		List<Equipment> getEqui = equipService.getAllEquiList(equipInit, equipNbr);
		assertEquals(getEqui, equipList);

	}
		
	@Test
	void testGetEquipmentException() {
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(equipService.getAllEquiList(equipInit, equipNbr)));
		assertEquals("No records found", exception.getMessage());
		
	}

}
