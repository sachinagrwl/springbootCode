package com.nscorp.obis.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.nscorp.obis.domain.DamageLocation;
import com.nscorp.obis.dto.BeneficialOwnerDTO;
import com.nscorp.obis.dto.DamageLocationDTO;
import com.nscorp.obis.dto.mapper.DamageLocationMapper;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotDeletedException;
import com.nscorp.obis.repository.DamageCategoryRepository;
import com.nscorp.obis.repository.DamageLocationRepository;

class DamageLocationServiceTest {

	@Mock 
	DamageLocationRepository damageLocationRepository;

	@Mock
	DamageCategoryRepository damageCategoryRepository;

	@Mock 
	DamageLocationMapper damageLocationMapper;

	@InjectMocks
	DamageLocationServiceImpl damageLocationServiceImpl;

	DamageLocation damageLocation;
	DamageLocationDTO damageLocationDto;
	List<DamageLocation> damageLocationList;
	Map<String, String> header;
	Optional<DamageLocation> optional;


	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		damageLocation = new DamageLocation();
		damageLocationDto = new DamageLocationDTO();
		damageLocationList = new ArrayList<>();

		damageLocation.setCatCd(12);
		damageLocation.setLocCd(3);
		damageLocation.setLocationDscr("ABC");

		damageLocationList.add(damageLocation);
		header = new HashMap<>();
		header.put("userid", "test");
		optional = Optional.of(damageLocation);
	}


	@AfterEach
	void tearDown() throws Exception {
		damageLocation = null;
		damageLocationList = null;
	}

	@Test
	void testGetAllDamageLocation(){
		when(damageLocationRepository.findAll()).thenReturn(damageLocationList);
		List<DamageLocation> getDamage = damageLocationServiceImpl.getAllDamageLocation();
		assertEquals(getDamage,damageLocationList);
	}

	@Test
	void testGetAllDamageLocationException(){
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
	                () -> when(damageLocationServiceImpl.getAllDamageLocation()));
	        assertEquals("No Records found!", exception.getMessage());
	}

	@Test
	void testAddDamageLocation() throws SQLException {

		when(damageCategoryRepository.existsByCatCd(Mockito.any())).thenReturn(true);
		when(damageLocationRepository.existsByCatCdAndLocCd(Mockito.any(),Mockito.any())).thenReturn(false);
		when(damageLocationMapper.damageLocationDTOToDamageLocation(Mockito.any())).thenReturn(damageLocation);
		when(damageLocationRepository.save(Mockito.any())).thenReturn(damageLocation);
		when(damageLocationMapper.damageLocationToDamageLocationDTO(Mockito.any())).thenReturn(damageLocationDto);
		DamageLocationDTO response = damageLocationServiceImpl.addDamageLocation(damageLocationDto, header);
		assertNotNull(response);

	}

	@Test
	void testAddDamageLocationExceptions() throws SQLException {

		when(damageCategoryRepository.existsByCatCd(Mockito.any())).thenReturn(true);
		when(damageLocationRepository.existsByCatCdAndLocCd(Mockito.any(),Mockito.any())).thenReturn(true);
		assertThrows(RecordAlreadyExistsException.class, ()->damageLocationServiceImpl.addDamageLocation(damageLocationDto, header));

		when(damageCategoryRepository.existsByCatCd(Mockito.any())).thenReturn(false);
		assertThrows(NoRecordsFoundException.class, ()->damageLocationServiceImpl.addDamageLocation(damageLocationDto, header));
		
	}

	@Test
	void testUpdateDamageLocation() throws SQLException {

		when(damageLocationRepository.findByCatCdAndLocCd(Mockito.any(),Mockito.any())).thenReturn(optional);
		when(damageLocationRepository.save(Mockito.any())).thenReturn(damageLocation);
		when(damageLocationMapper.damageLocationToDamageLocationDTO(Mockito.any())).thenReturn(damageLocationDto);
		DamageLocationDTO response = damageLocationServiceImpl.updateDamageLocation(damageLocationDto, header);
		assertNotNull(response);

	}

	@Test
	void testUpdateDamageLocationExceptions() throws SQLException {

		NoRecordsFoundException exception1 = assertThrows(NoRecordsFoundException.class,
				() ->when(damageLocationServiceImpl.updateDamageLocation(damageLocationDto, header)));
		assertEquals("No Record Found For Given CatCd and LocCd", exception1.getMessage());

	}

	@Test
	void testDeleteDamageLocation(){

		when(damageLocationRepository.existsByCatCdAndLocCd(Mockito.any(),Mockito.any())).thenReturn(true);
		damageLocationServiceImpl.deleteDamageLocation(damageLocation);
	}

	@Test
	void testInvalidDataException() {
		damageLocation.setCatCd(null);
		InvalidDataException exception1 = assertThrows(InvalidDataException.class,
				() -> damageLocationServiceImpl.deleteDamageLocation(damageLocation));
		assertEquals("CatCd should not be null", exception1.getMessage());

		damageLocation.setCatCd(12);
		damageLocation.setLocCd(null);
		InvalidDataException exception2 = assertThrows(InvalidDataException.class,
				() -> damageLocationServiceImpl.deleteDamageLocation(damageLocation));
		assertEquals("LocCd should not be null", exception2.getMessage());

	}

	@Test
	void testRecordNotDeletedException() {

		when(damageLocationRepository.existsByCatCdAndLocCd(Mockito.any(),Mockito.any())).thenReturn(false);
		RecordNotDeletedException exception = assertThrows(RecordNotDeletedException.class,
				() -> damageLocationServiceImpl.deleteDamageLocation(damageLocation));
		assertEquals(" Record Not Found!", exception.getMessage());
	}
}
