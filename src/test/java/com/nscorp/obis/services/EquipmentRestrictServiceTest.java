package com.nscorp.obis.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.nscorp.obis.domain.EquipmentRestrict;
import com.nscorp.obis.dto.EquipmentRestrictDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.repository.EquipmentRestrictRepository;

class EquipmentRestrictServiceTest {

	@InjectMocks
	EquipmentRestrictServiceImpl equipmentRestrictService;

	@Mock
	EquipmentRestrictRepository equipmentRestrictRepository;

	EquipmentRestrict equipmentRestrict;
	EquipmentRestrictDTO equipmentRestrictDto;
	List<EquipmentRestrict> equipmentRestrictList;
	List<EquipmentRestrictDTO> equipmentRestrictDTOList;
	EquipmentRestrict addedEqRestriction;
	EquipmentRestrict updatedEqRestriction;
	Map<String, String> header;

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		equipmentRestrict = new EquipmentRestrict();
		equipmentRestrict = new EquipmentRestrict();
		equipmentRestrict.setRestrictionId(41931813532L);
		equipmentRestrict.setEquipmentInit("Test");
		equipmentRestrict.setEquipmentType("C");
		equipmentRestrict.setEquipmentNumberLow(BigDecimal.valueOf(100));
		equipmentRestrict.setEquipmentNumberHigh(BigDecimal.valueOf(200));
		equipmentRestrict.setEquipmentRestrictionType("D");

		equipmentRestrictList = new ArrayList<>();
		equipmentRestrictList.add(equipmentRestrict);

		equipmentRestrictDto = new EquipmentRestrictDTO();
		equipmentRestrictDto.setRestrictionId(41931813532L);
		equipmentRestrictDto.setEquipmentInit("Test");
		equipmentRestrictDto.setEquipmentType("C");
		equipmentRestrictDto.setEquipmentNumberLow(BigDecimal.valueOf(100));
		equipmentRestrictDto.setEquipmentNumberHigh(BigDecimal.valueOf(200));
		equipmentRestrictDto.setEquipmentRestrictionType("D");

		equipmentRestrictDTOList = new ArrayList<>();
		equipmentRestrictDTOList.add(equipmentRestrictDto);

		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");
	}

	@AfterEach
	void tearDown() throws Exception {
		equipmentRestrictList = null;
		equipmentRestrictDTOList = null;
		equipmentRestrict = null;
		equipmentRestrictDto = null;
	}

	@Test
	void testGetAllEquipRestrictions() {
		when(equipmentRestrictRepository.findAll()).thenReturn(equipmentRestrictList);
		List<EquipmentRestrict> getRestrictions = equipmentRestrictService.getAllEquipRestrictions();
		assertEquals(getRestrictions, equipmentRestrictList);
	}

	@Test
	void testGetAllEquipRestrictionsException() {
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(equipmentRestrictService.getAllEquipRestrictions()));
		Assertions.assertEquals("No Records found for Equipment Restrictions", exception.getMessage());
	}

	@Test
	void testAddEquipRestrictions() {
		when(equipmentRestrictRepository.save(Mockito.any())).thenReturn(equipmentRestrict);
		addedEqRestriction = equipmentRestrictService.addEquipRestrictions(equipmentRestrict, header);
		assertNotNull(addedEqRestriction);
	}

	@Test
	void testAddEquipInitRecordNotAddedException() {
		equipmentRestrict.setEquipmentInit("Tes*");
		when(equipmentRestrictRepository.save(Mockito.any())).thenReturn(equipmentRestrict);
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> when(equipmentRestrictService.addEquipRestrictions(equipmentRestrict, header)));
		assertEquals("'equipmentInit' value should have only alphabets", exception.getMessage());
	}

	@Test
	void testAddEquipTypeRecordNotAddedException() {
		equipmentRestrict.setEquipmentType("T");
		when(equipmentRestrictRepository.save(Mockito.any())).thenReturn(equipmentRestrict);
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> when(equipmentRestrictService.addEquipRestrictions(equipmentRestrict, header)));
		assertEquals("'equipmentType' value should be only 'C' and 'F'", exception.getMessage());
	}

	@Test
	void testAddEqNrHighNullRecordNotAddedException() {
		equipmentRestrict.setEquipmentNumberHigh(null);
		when(equipmentRestrictRepository.save(Mockito.any())).thenReturn(equipmentRestrict);
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> when(equipmentRestrictService.addEquipRestrictions(equipmentRestrict, header)));
		assertEquals("'equipmentNumberHigh' value should be provided", exception.getMessage());
	}

	@Test
	void testAddEqNrLowNullRecordNotAddedException() {
		equipmentRestrict.setEquipmentNumberLow(null);
		when(equipmentRestrictRepository.save(Mockito.any())).thenReturn(equipmentRestrict);
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> when(equipmentRestrictService.addEquipRestrictions(equipmentRestrict, header)));
		assertEquals("'equipmentNumberLow' value should be provided", exception.getMessage());
	}

	@Test
	void testAddEqNrLowAndHighNullEquipRestrictions() {
		equipmentRestrict.setEquipmentNumberLow(null);
		equipmentRestrict.setEquipmentNumberHigh(null);
		when(equipmentRestrictRepository.save(Mockito.any())).thenReturn(equipmentRestrict);
		addedEqRestriction = equipmentRestrictService.addEquipRestrictions(equipmentRestrict, header);
		assertNotNull(addedEqRestriction);
	}

	@Test
	void testAddEqNrLowHigherThanEqNrHighRecordNotAddedException() {
		equipmentRestrict.setEquipmentNumberLow(BigDecimal.valueOf(200));
		equipmentRestrict.setEquipmentNumberHigh(BigDecimal.valueOf(100));
		when(equipmentRestrictRepository.save(Mockito.any())).thenReturn(equipmentRestrict);
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> when(equipmentRestrictService.addEquipRestrictions(equipmentRestrict, header)));
		assertEquals("'Equipment Low Number' should be less than 'Equipment High Number'", exception.getMessage());
	}

	@Test
	void testUpdateEquipRestriction() {
		equipmentRestrict.setUversion("!");
		when(equipmentRestrictRepository.existsById(Mockito.any())).thenReturn(true);
		when(equipmentRestrictRepository.findByRestrictionId(equipmentRestrict.getRestrictionId())).thenReturn(equipmentRestrict);
		when(equipmentRestrictRepository.save(Mockito.any())).thenReturn(equipmentRestrict);
		updatedEqRestriction = equipmentRestrictService.updateEquipRestriction(equipmentRestrict, header);
		assertEquals(updatedEqRestriction.getEquipmentType(), equipmentRestrict.getEquipmentType());
	}

	@Test
	void testUpdateNoRecordsFoundException() {
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(equipmentRestrictService.updateEquipRestriction(equipmentRestrict, header)));
		assertEquals("No record found for this 'restrictionId': "+equipmentRestrict.getRestrictionId(), exception.getMessage());
	}

	@Test
	void testDeleteEquipRestriction() {
		when(equipmentRestrictRepository.existsByRestrictionId(Mockito.any())).thenReturn(true);
		when(equipmentRestrictRepository.findByRestrictionId(Mockito.any())).thenReturn(equipmentRestrict);
		EquipmentRestrict eqRestrictAfterDelete = equipmentRestrictService.deleteEquipRestriction(equipmentRestrict);
		assertEquals(eqRestrictAfterDelete,equipmentRestrict);
	}

	@Test
	void testEquipRestrictionRecordNotDeletedException() {
		when(equipmentRestrictRepository.existsByRestrictionId(Mockito.any())).thenReturn(false);

		assertThrows(NoRecordsFoundException.class,
				() -> equipmentRestrictService.deleteEquipRestriction(equipmentRestrict));
	}

}
