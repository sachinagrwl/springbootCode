package com.nscorp.obis.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.nscorp.obis.domain.DamageLocation;
import com.nscorp.obis.dto.DamageLocationDTO;
import com.nscorp.obis.dto.mapper.DamageLocationMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.repository.DamageLocationRepository;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.DamageLocationService;

class DamageLocationControllerTest {

	@Mock
	DamageLocationService damageLocationService;

	@Mock
	DamageLocationMapper damageLocationMapper;

	@Mock
	DamageLocationRepository damageLocationRepository;

	@InjectMocks
	DamageLocationController damageLocationController;

	DamageLocation damageLocation;
	DamageLocationDTO damageLocationDto;
	List<DamageLocation> damageLocationList;
	List<DamageLocationDTO> damageLocationDtoList;
    Map<String, String> header;

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
		damageLocationDtoList = new ArrayList<>();
		damageLocation.setCatCd(12);
		damageLocation.setLocationDscr("ABC");
		damageLocationList.add(damageLocation);
		damageLocationDto.setCatCd(12);
		damageLocationDto.setLocationDscr("ABC");
		damageLocationDtoList.add(damageLocationDto);

		header = new HashMap<>();
        header.put("userid", "test");
        header.put("extensionschema", "test");
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGetDamageLocation()
	{
		when(damageLocationService.getAllDamageLocation()).thenReturn(damageLocationList);
		ResponseEntity<APIResponse<List<DamageLocationDTO>>> profiles = damageLocationController.getDamageLocation();
		assertEquals(profiles.getStatusCodeValue(), 200);
	}

	@Test
	void testAddDamageLocation() throws SQLException {

		when(damageLocationService.addDamageLocation(Mockito.any(), Mockito.anyMap())).thenReturn(damageLocationDto);
		ResponseEntity<APIResponse<DamageLocationDTO>> addData = damageLocationController.addDamageLocation(damageLocationDto, header);
		assertEquals(addData.getStatusCodeValue(), 200);

	}

	@Test
	void testAddDamageLocationException() throws SQLException {

		when(damageLocationService.addDamageLocation(Mockito.any(), Mockito.anyMap())).thenThrow(RuntimeException.class);
		ResponseEntity<APIResponse<DamageLocationDTO>> addData = damageLocationController.addDamageLocation(damageLocationDto, header);
		assertEquals(addData.getStatusCodeValue(), 500);

	}

	@Test
	void testUpdateDamageLocation() throws SQLException {

		when(damageLocationService.updateDamageLocation(Mockito.any(), Mockito.anyMap())).thenReturn(damageLocationDto);
		ResponseEntity<APIResponse<DamageLocationDTO>> updateData = damageLocationController.updateDamageLocation(damageLocationDto, header);
		assertEquals(updateData.getStatusCodeValue(), 200);

	}

	@Test
	void testUpdateDamageLocationException() throws SQLException {

		when(damageLocationService.updateDamageLocation(Mockito.any(), Mockito.anyMap())).thenThrow(RuntimeException.class);
		ResponseEntity<APIResponse<DamageLocationDTO>> updateData = damageLocationController.updateDamageLocation(damageLocationDto, header);
		assertEquals(updateData.getStatusCodeValue(), 500);

	}

	@Test
	void testDeleteDamageLocation() {
		
		ResponseEntity<List<APIResponse<DamageLocationDTO>>> deleteData = damageLocationController.deleteDamageLocation(damageLocationDtoList);
		assertEquals(deleteData.getStatusCodeValue(), 200);

		damageLocationList.remove(damageLocation);
		ResponseEntity<List<APIResponse<DamageLocationDTO>>> deleteData1 = damageLocationController.deleteDamageLocation(null);
		assertEquals(deleteData1.getStatusCodeValue(), 120);
	}

	@Test
	void testException() throws SQLException {
		
		ResponseEntity<List<APIResponse<DamageLocationDTO>>> deleteData = damageLocationController.deleteDamageLocation(damageLocationDtoList);
		assertEquals(deleteData.getStatusCodeValue(), 500);

	}

	@Test
	void testNoRecordsFoundException() throws SQLException {
		when(damageLocationService.addDamageLocation(Mockito.any(), Mockito.anyMap())).thenThrow(RecordAlreadyExistsException.class);
		ResponseEntity<APIResponse<DamageLocationDTO>> addData = damageLocationController.addDamageLocation(damageLocationDto, header);
		assertEquals(addData.getStatusCodeValue(), 400);

		when(damageLocationService.updateDamageLocation(Mockito.any(), Mockito.anyMap())).thenThrow(NoRecordsFoundException.class);
		ResponseEntity<APIResponse<DamageLocationDTO>> updateData = damageLocationController.updateDamageLocation(damageLocationDto, header);
		assertEquals(updateData.getStatusCodeValue(), 404);

		when(damageLocationService.getAllDamageLocation()).thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<List<DamageLocationDTO>>> profiles = damageLocationController.getDamageLocation();
		assertEquals(profiles.getStatusCodeValue(), 404);

	}

	@Test
    void getRuntimeException(){
		
		when(damageLocationService.getAllDamageLocation()).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<List<DamageLocationDTO>>> profiles = damageLocationController.getDamageLocation();
		assertEquals(profiles.getStatusCodeValue(), 500);
	}

}
