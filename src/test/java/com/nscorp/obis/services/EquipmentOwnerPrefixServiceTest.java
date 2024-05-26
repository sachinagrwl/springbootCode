package com.nscorp.obis.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nscorp.obis.exception.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.nscorp.obis.domain.CorporateCustomerDetail;
import com.nscorp.obis.domain.EquipmentOwnerPrefix;
import com.nscorp.obis.dto.EquipmentOwnerPrefixDTO;
import com.nscorp.obis.repository.EquipmentOwnerPrefixRepository;

public class EquipmentOwnerPrefixServiceTest {
	
	@InjectMocks
	EquipmentOwnerPrefixServiceImpl equipmentOwnerPrefixService;

	@Mock
	EquipmentOwnerPrefixRepository equipmentOwnerPrefixRepo;


	EquipmentOwnerPrefixDTO equipmentOwnerPrefixDto;
	EquipmentOwnerPrefix equipmentOwnerPrefix;
	List<EquipmentOwnerPrefix> equipmentOwnerPrefixList;
	List<EquipmentOwnerPrefixDTO> equipmentOwnerPrefixDtoList;
	
	Map<String, String> header;
	
	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		
		MockitoAnnotations.initMocks(this);
		equipmentOwnerPrefix = new EquipmentOwnerPrefix();
		equipmentOwnerPrefix.setEquipInit("QWE");
		equipmentOwnerPrefix.setInterchangeCd("qw");
		equipmentOwnerPrefix.setOwnership("O");
		equipmentOwnerPrefixList = new ArrayList<>();
		equipmentOwnerPrefixList.add(equipmentOwnerPrefix);
		
		equipmentOwnerPrefixDto = new EquipmentOwnerPrefixDTO();
		equipmentOwnerPrefixDto.setEquipInit("QWE");
		equipmentOwnerPrefixDto.setInterchangeCd("qw");
		equipmentOwnerPrefixDto.setOwnership("O");
		
		equipmentOwnerPrefixDtoList = new ArrayList<>();
		
		
		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");
	}

	@AfterEach
	void tearDown() throws Exception {
		equipmentOwnerPrefixDto = null;
		equipmentOwnerPrefix = null;
		equipmentOwnerPrefixList = null;
		equipmentOwnerPrefixDtoList = null;
	}


	@Test
	void testGetAllTables() {
		when(equipmentOwnerPrefixRepo.findAll()).thenReturn(equipmentOwnerPrefixList);
		List<EquipmentOwnerPrefix> allTable = equipmentOwnerPrefixService.getAllTables();
		assertEquals(allTable, equipmentOwnerPrefixList);
	}
	
	@Test
	void testGetAllTablesNoRecordsFoundException() {
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(equipmentOwnerPrefixService.getAllTables()));
		Assertions.assertEquals("No Records found", exception.getMessage());
	}
	@Test
	void testDeleteEquipmentOwnerPrefix() {
		when(equipmentOwnerPrefixRepo.existsByEquipInit(Mockito.anyString())).thenReturn(true);
		equipmentOwnerPrefixRepo.deleteByEquipInit(Mockito.any());
		equipmentOwnerPrefixService.deleteEquipmentOwnerPrefixTable(equipmentOwnerPrefix);
	}

	@Test
	void testEquipmentOwnerPrefixRecordNotDeletedException() {
		when(equipmentOwnerPrefixRepo.existsByEquipInit(Mockito.anyString())).thenReturn(false);

		RecordNotDeletedException exception = assertThrows(RecordNotDeletedException.class,
				() -> equipmentOwnerPrefixService.deleteEquipmentOwnerPrefixTable(equipmentOwnerPrefix));
		assertEquals("Record Not Found!", exception.getMessage());
	}
	
	@Test
	void testAddEquipmentOwnerPrefix() {
		when(equipmentOwnerPrefixRepo.existsByEquipInit(Mockito.any())).thenReturn(false);
		when(equipmentOwnerPrefixRepo.save(Mockito.any())).thenReturn(equipmentOwnerPrefix);
		EquipmentOwnerPrefix EqAdded = equipmentOwnerPrefixService.addEquipmentOwnerPrefix(equipmentOwnerPrefix,header);
		assertEquals(EqAdded, equipmentOwnerPrefix);
		equipmentOwnerPrefix.setOwnership("F");
		equipmentOwnerPrefix.setInterchangeCd("AN");
		EqAdded = equipmentOwnerPrefixService.addEquipmentOwnerPrefix(equipmentOwnerPrefix,header);
		equipmentOwnerPrefix.setOwnership("P");
		equipmentOwnerPrefix.setInterchangeCd(null);
		EqAdded = equipmentOwnerPrefixService.addEquipmentOwnerPrefix(equipmentOwnerPrefix,header);
	}
	
	@Test
	void testAddEquipmentOwnerPrefixRecordAlreadyExistsException() {
		when(equipmentOwnerPrefixRepo.existsByEquipInit(Mockito.any())).thenReturn(true);
		assertThrows(RecordAlreadyExistsException.class,
				() -> equipmentOwnerPrefixService.addEquipmentOwnerPrefix(equipmentOwnerPrefix, header));
	}
	@Test
	void testUpdateEquipmentOwnerPrefix(){
		equipmentOwnerPrefix.setOwnership("P");
		equipmentOwnerPrefix.setInterchangeCd(null);
		when(equipmentOwnerPrefixRepo.existsByEquipInit(Mockito.any())).thenReturn(true);
		when(equipmentOwnerPrefixRepo.save(Mockito.any())).thenReturn(equipmentOwnerPrefix);
		EquipmentOwnerPrefix equipOwnerUpdate = equipmentOwnerPrefixService.updateEquipmentOwnerPrefix(equipmentOwnerPrefix,header);

		equipmentOwnerPrefix.setOwnership("F");
		equipmentOwnerPrefix.setInterchangeCd("ANNB");
		equipOwnerUpdate = equipmentOwnerPrefixService.updateEquipmentOwnerPrefix(equipmentOwnerPrefix,header);

		equipmentOwnerPrefix.setOwnership("F");
		equipmentOwnerPrefix.setInterchangeCd("AN");
		equipOwnerUpdate = equipmentOwnerPrefixService.updateEquipmentOwnerPrefix(equipmentOwnerPrefix,header);
	}
	@Test
	void testUpdateEquipmentOwnerPrefixNoRecordFoundException(){
		when(equipmentOwnerPrefixRepo.existsByEquipInit(Mockito.any())).thenReturn(false);
		assertThrows(NoRecordsFoundException.class,
				() -> equipmentOwnerPrefixService.updateEquipmentOwnerPrefix(equipmentOwnerPrefix, header));
	}
	@Test
	void testUpdateEquipmentOwnerPrefixInvalidDataException(){
		equipmentOwnerPrefix.setOwnership("F");
		equipmentOwnerPrefix.setInterchangeCd(null);
		when(equipmentOwnerPrefixRepo.existsByEquipInit(Mockito.any())).thenReturn(true);
		assertThrows(InvalidDataException.class,
				() -> equipmentOwnerPrefixService.updateEquipmentOwnerPrefix(equipmentOwnerPrefix, header));

		equipmentOwnerPrefix.setOwnership("A");
		equipmentOwnerPrefix.setInterchangeCd(null);
		assertThrows(InvalidDataException.class,
				() -> equipmentOwnerPrefixService.updateEquipmentOwnerPrefix(equipmentOwnerPrefix, header));

		equipmentOwnerPrefix.setOwnership("P");
		equipmentOwnerPrefix.setInterchangeCd(null);
		when(equipmentOwnerPrefixRepo.existsByEquipInit(Mockito.any())).thenReturn(true);
		when(equipmentOwnerPrefixRepo.save(Mockito.any())).thenReturn(null);
		assertThrows(RecordNotAddedException.class,
				() -> equipmentOwnerPrefixService.updateEquipmentOwnerPrefix(equipmentOwnerPrefix, header));

	}
}
