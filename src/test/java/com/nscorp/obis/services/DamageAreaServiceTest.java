package com.nscorp.obis.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import com.nscorp.obis.domain.DamageArea;
import com.nscorp.obis.domain.DamageCompLoc;
import com.nscorp.obis.dto.DamageAreaDTO;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotDeletedException;
import com.nscorp.obis.repository.DamageAreaCompRepository;
import com.nscorp.obis.repository.DamageAreaRepository;
import com.nscorp.obis.repository.DamageCompLocRepository;

public class DamageAreaServiceTest {

	@InjectMocks
	DamageAreaServiceImpl damageAreaService;

	@Mock
	DamageAreaRepository damageAreaRepository;

	@Mock
	DamageAreaCompRepository damageAreaCompRepository;

	@Mock
	DamageCompLocRepository damageCompLocRepository;

	DamageArea damageArea;

	DamageCompLoc damageCompLoc;
	DamageAreaDTO damageAreaDto;
	List<DamageArea> damageAreaList;
	List<DamageAreaDTO> damageAreaDtoList;

	List<DamageCompLoc> damageCompLocList;

	Map<String, String> header;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		damageAreaList = new ArrayList<>();
		damageCompLocList = new ArrayList<>();
		damageAreaDtoList = new ArrayList<>();
		damageArea = new DamageArea();
		damageArea.setAreaCd("A");
		damageArea.setAreaDscr("Nose");
		damageArea.setDisplayCd("Y");
		damageAreaList.add(damageArea);

		damageAreaDto = new DamageAreaDTO();
		damageAreaDto.setAreaCd("A");
		damageAreaDto.setAreaDscr("Nose");
		damageAreaDto.setDisplayCd("Y");
		damageAreaDtoList.add(damageAreaDto);

		damageCompLoc = new DamageCompLoc();
		damageCompLoc.setDamageArea(damageArea);
		damageCompLoc.setCompLocCode("TES");
		damageCompLocList.add(damageCompLoc);

		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");

	}

	@AfterEach
	void tearDown() throws Exception {
		damageArea = null;
		damageAreaDtoList = null;
		damageAreaList = null;
		damageAreaDto = null;
	}

	@Test
	void testGetDamageArea() {
		when(damageAreaRepository.findAll()).thenReturn(damageAreaList);
		List<DamageArea> damageArea = damageAreaService.getAllDamageArea();
		assertEquals(damageArea, damageAreaList);
	}

	@Test
	void testDeleteDamageArea() {
		when(damageAreaCompRepository.existsByAreaCd(Mockito.anyString())).thenReturn(false);
		when(damageAreaRepository.existsById(Mockito.anyString())).thenReturn(true);
		damageAreaRepository.deleteById(Mockito.any());
		damageAreaService.deleteDamageArea(damageArea);
		when(damageCompLocRepository.findByDamageArea_AreaCd(Mockito.anyString())).thenReturn(damageCompLoc);
		damageCompLocRepository.deleteById(Mockito.any());
	}
//	@Test
//	void testDeleteDamageAreaNoRecordFoundException() {
//		when(damageAreaCompRepository.existsByAreaCd(Mockito.anyString())).thenReturn(false);
//		when(damageAreaRepository.existsById(Mockito.anyString())).thenReturn(false);
//		damageCompLoc.setAreaCd("Q");
//		when(damageCompLocRepository.findByAreaCd(Mockito.anyString())).thenReturn(damageCompLoc);
//		int dateTime=1;
//		NoRecordsFoundException exception1 = assertThrows(NoRecordsFoundException.class,
//				() -> when(damageAreaService.deleteDamageArea(damageCompLoc)));
//		assertEquals("Not deleting historical records, Contact SIMS Programmer to remove", exception1.getMessage());
//	}

	@Test
	void testDeleteDamageAreaException() {
		when(damageAreaCompRepository.existsByAreaCd(Mockito.anyString())).thenReturn(true);
		RecordNotDeletedException exception = assertThrows(RecordNotDeletedException.class,
				() -> damageAreaService.deleteDamageArea(damageArea));
		assertEquals("Entity Damage_Area still has restricted links to DAMAGE_AREA_COMP", exception.getMessage());

		when(damageAreaCompRepository.existsByAreaCd(Mockito.anyString())).thenReturn(false);
		when(damageAreaRepository.existsById(Mockito.anyString())).thenReturn(false);
		RecordNotDeletedException exception1 = assertThrows(RecordNotDeletedException.class,
				() -> damageAreaService.deleteDamageArea(damageArea));
		assertEquals("Record Not Found!", exception1.getMessage());
	}

	@Test
	void testDamageAreaNoRecordFoundException1() {
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(damageAreaService.getAllDamageArea()));
		assertEquals("No Records found!", exception.getMessage());
	}

	@Test
	void testAddDamageArea() {
		when(damageAreaRepository.existsById(Mockito.any())).thenReturn(false);
		when(damageAreaRepository.save(Mockito.any())).thenReturn(damageArea);
		DamageArea data = damageAreaService.addDamageArea(damageArea, header);
	}

	@Test
	void testAddInvalidDataException() {
		damageArea.setAreaCd(" ");
		InvalidDataException exception = assertThrows(InvalidDataException.class,
				() -> when(damageAreaService.addDamageArea(damageArea, header)));
	}

	@Test
	void testAddDamageAreaRecordAlreadyExistsException() {
		when(damageAreaRepository.existsById(Mockito.any())).thenReturn(true);
		RecordAlreadyExistsException exception = assertThrows(RecordAlreadyExistsException.class,
				() -> when(damageAreaService.addDamageArea(damageArea, header)));

		when(damageAreaRepository.existsById(Mockito.any())).thenReturn(false);
		when(damageAreaRepository.existsById(Mockito.any())).thenReturn(true);
		RecordAlreadyExistsException exception1 = assertThrows(RecordAlreadyExistsException.class,
				() -> when(damageAreaService.addDamageArea(damageArea, header)));

	}
	
	@Test
	void testUpdateDamageArea(){
		when(damageAreaRepository.existsByAreaCd(Mockito.any())).thenReturn(true);
		DamageArea response = damageAreaService.updateDamageArea(damageArea,header);		
	}

	@Test
	void testUpdateDamageAreaRecordAlreadyExistsException(){
		when(damageAreaRepository.existsByAreaCd(Mockito.any())).thenReturn(false);
		RecordAlreadyExistsException exception = assertThrows(RecordAlreadyExistsException.class,
				() -> damageAreaService.updateDamageArea(damageArea,header));
		assertEquals("Invalid Change", exception.getMessage());
	}
}
