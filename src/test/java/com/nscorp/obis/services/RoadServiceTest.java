package com.nscorp.obis.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.*;


import com.nscorp.obis.exception.RecordNotDeletedException;
import com.nscorp.obis.repository.StationRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.nscorp.obis.domain.Road;
import com.nscorp.obis.dto.RoadDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.repository.RoadRepository;

class RoadServiceTest {

	@InjectMocks
	RoadServiceImpl roadService;

	@Mock
	RoadRepository roadRepository;

	@Mock
	StationRepository stationRepository;

	Road road;
	RoadDTO roadDTO;
	List<Road> roadList;
	List<RoadDTO> roadDTOList;

	Map<String, String> header;

	String roadNumber;
	String roadName;
	String roadFullName;
	String roadType;

	@BeforeEach
	void setUp() throws Exception {

		MockitoAnnotations.initMocks(this);
		road = new Road();
		road.setRoadNumber("0001");
		road.setRoadName("TEST");
		road.setRoadFullName("TEST ROAD FULL NAME");
		road.setRoadType("IS");

		roadDTO = new RoadDTO();
		roadDTO.setRoadNumber("0001");
		roadDTO.setRoadName("TEST");
		roadDTO.setRoadFullName("TEST ROAD FULL NAME");
		roadDTO.setRoadType("IS");

		roadList = new ArrayList<>();
		roadList.add(road);

		roadDTOList = new ArrayList<>();
		roadDTOList.add(roadDTO);

		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");
	}

	@AfterEach
	void tearDown() throws Exception {
		header = null;
		road = null;
		roadDTO = null;
		roadList = null;
		roadDTOList = null;
	}

	@Test
	void testGetAllRoads() {
		when(roadRepository.findAll(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(roadList);
		List<Road> allRoads = roadService.getAllRoads(roadNumber, roadName, roadFullName, roadType);
		assertEquals(allRoads, roadList);
	}

	@Test
	void testAddRoad() {
		when(roadRepository.existsById(Mockito.any())).thenReturn(false);
		when(roadRepository.existsByRoadName(Mockito.any())).thenReturn(false);
		when(roadRepository.save(Mockito.any())).thenReturn(road);
		Road addedRoad = roadService.addRoad(road, header);
		assertNotNull(addedRoad);

	}

	@ParameterizedTest
	@ValueSource(strings = {"!",""," "})
	void testUpdateRoad(String uVersion) {
		road.setUversion(uVersion);
		when(roadRepository.existsByRoadNumberAndUversion(Mockito.any(), Mockito.any())).thenReturn(true);
		when(roadRepository.findByRoadNumber(Mockito.any())).thenReturn(road);
		when(roadRepository.save(Mockito.any())).thenReturn(road);
		Road updateRoad = roadService.updateRoad(road, header);
		assertNotNull(updateRoad);
	}

	@Test
	void testUpdateRoadRecordAlreadyExistsException() {
		Road obj = new Road();
		obj.setRoadNumber("0001");
		obj.setRoadName("TES1");
		obj.setRoadFullName("TEST ROAD FULL NAME");
		obj.setRoadType("IS");
		when(roadRepository.existsByRoadNumberAndUversion(Mockito.any(), Mockito.any())).thenReturn(true);
		when(roadRepository.findByRoadNumber(Mockito.any())).thenReturn(road);
		when(roadRepository.existsByRoadName(Mockito.any())).thenReturn(true);
		RecordAlreadyExistsException exception1 = assertThrows(RecordAlreadyExistsException.class,
				() -> when(roadService.updateRoad(obj, header)));
		assertEquals("The Provided RoadName: TES1 is already exists.", exception1.getMessage());
	}

	@Test
	void testRoadNoRecordsFoundException() {
		when(roadRepository.findAll(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
				.thenReturn(Collections.EMPTY_LIST);
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(roadService.getAllRoads(roadNumber, roadName, roadFullName, roadType)));
		assertEquals("No Record Found under this search!", exception.getMessage());

		when(roadRepository.existsByRoadNumberAndUversion(Mockito.any(), Mockito.any())).thenReturn(false);
		NoRecordsFoundException exception1 = assertThrows(NoRecordsFoundException.class,
				() -> when(roadService.updateRoad(road,header)));
		assertEquals("No record Found Under this Road Number:"+road.getRoadNumber()
				+ " and Uversion:" + road.getUversion(), exception1.getMessage());
	}

	@Test
	void testRoadNullPointerException() {
		header.put("extensionschema", null);
		NullPointerException exception1 = assertThrows(NullPointerException.class,
				() -> when(roadService.addRoad(road, header)));
		assertEquals("Extension Schema should not be null, empty or blank.", exception1.getMessage());

		header.put("extensionschema", null);
		NullPointerException exception2 = assertThrows(NullPointerException.class,
				() -> when(roadService.updateRoad(road, header)));
		assertEquals("Extension Schema should not be null, empty or blank.", exception1.getMessage());
	}
	
	@Test
	void testRoadRecordAlreadyExistsException() {
		when(roadRepository.existsById(Mockito.any())).thenReturn(true);
		RecordAlreadyExistsException exception1 = assertThrows(RecordAlreadyExistsException.class,
				() -> when(roadService.addRoad(road, header)));
		assertEquals("The Provided RoadNumber: 0001 is already exists.", exception1.getMessage());

		when(roadRepository.existsById(Mockito.any())).thenReturn(false);
		when(roadRepository.existsByRoadName(Mockito.any())).thenReturn(true);
		RecordAlreadyExistsException exception2 = assertThrows(RecordAlreadyExistsException.class,
				() -> when(roadService.addRoad(road, header)));
		assertEquals("The Provided RoadName: TEST is already exists.", exception2.getMessage());
	}

	@Test
	void testDeleteRoad() {
		when(roadRepository.existsByRoadNumberAndUversion(Mockito.any(), Mockito.any())).thenReturn(true);
		when(stationRepository.existsByRoadNumber(any())).thenReturn(false);
		when(roadRepository.findById(Mockito.any())).thenReturn(Optional.ofNullable(road));
		Road deleteRoad = roadService.deleteRoad(road);
		assertNotNull(deleteRoad);
	}

	@Test
	void testDeleteRoadRecordNotDeleted() {
		when(roadRepository.existsByRoadNumberAndUversion(Mockito.any(), Mockito.any())).thenReturn(false);
		assertThrows(RecordNotDeletedException.class,
				() -> roadService.deleteRoad(road));

		when(roadRepository.existsByRoadNumberAndUversion(Mockito.any(), Mockito.any())).thenReturn(true);
		when(stationRepository.existsByRoadNumber(any())).thenReturn(true);
		assertThrows(RecordNotDeletedException.class,
				() -> roadService.deleteRoad(road));
	}
}
