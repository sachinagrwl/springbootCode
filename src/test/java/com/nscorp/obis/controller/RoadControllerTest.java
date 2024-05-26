package com.nscorp.obis.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.nscorp.obis.domain.Road;
import com.nscorp.obis.dto.RoadDTO;
import com.nscorp.obis.dto.mapper.RoadMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.RoadService;

class RoadControllerTest {
	
	@InjectMocks
	RoadController roadController;
	
	@Mock
	RoadMapper roadMapper;
	
	@Mock
	RoadService roadService;
	
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
		when(roadService.getAllRoads(Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(roadList);
		ResponseEntity<APIResponse<List<RoadDTO>>> getRoads = roadController.getAllRoads(roadNumber,roadName,roadFullName,roadType);
		assertEquals(getRoads.getStatusCodeValue(), 200);
	}
	
	@Test
	void testAddEqEmbargo() {
		when(roadService.addRoad(Mockito.any(), Mockito.any())).thenReturn(road);
		ResponseEntity<APIResponse<RoadDTO>> addedRoad = roadController.addRoad(roadDTO, header);
		assertNotNull(addedRoad.getBody());
	}
	
	@Test
	void testNoRecordsFoundException() {
		when(roadService.getAllRoads(Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any())).thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<List<RoadDTO>>> getRoads =  roadController.getAllRoads(roadNumber,roadName,roadFullName,roadType);
		assertEquals(getRoads.getStatusCodeValue(), 404);
		
		when(roadService.addRoad(Mockito.any(),Mockito.any())).thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<RoadDTO>> addedRoad = roadController.addRoad(roadDTO, header);
		assertEquals(addedRoad.getStatusCodeValue(), 404);

		when(roadService.updateRoad(Mockito.any(),Mockito.any())).thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<RoadDTO>> updateRoad = roadController.updateRoad(roadDTO, header);
		assertEquals(updateRoad.getStatusCodeValue(), 404);
	}
	
	@Test
	void testRoadsException() {
		when(roadService.getAllRoads(Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<List<RoadDTO>>> getEmbargo =  roadController.getAllRoads(roadNumber,roadName,roadFullName,roadType);
		assertEquals(getEmbargo.getStatusCodeValue(), 500);
		
		when(roadService.addRoad(Mockito.any(),Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<RoadDTO>> addedRoad = roadController.addRoad(roadDTO, header);
		assertEquals(addedRoad.getStatusCodeValue(), 500);

		when(roadService.updateRoad(Mockito.any(),Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<RoadDTO>> updateRoad = roadController.updateRoad(roadDTO, header);
		assertEquals(updateRoad.getStatusCodeValue(), 500);

		when(roadService.deleteRoad(Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<List<APIResponse<RoadDTO>>> deleteRoad = roadController.deleteRoad(roadDTOList);
		assertEquals(deleteRoad.getStatusCodeValue(),500);
	}

	@Test
	void testRoadNullPointerException() {
		when(roadService.addRoad(Mockito.any(), Mockito.any())).thenThrow(new NullPointerException());
		ResponseEntity<APIResponse<RoadDTO>> addedRoad = roadController.addRoad(roadDTO, header);
		assertEquals(addedRoad.getStatusCodeValue(),400);

		when(roadService.updateRoad(Mockito.any(), Mockito.any())).thenThrow(new NullPointerException());
		ResponseEntity<APIResponse<RoadDTO>> updateRoad = roadController.updateRoad(roadDTO, header);
		assertEquals(updateRoad.getStatusCodeValue(),400);
	}
	
	@Test
	void testRoadRecordAlreadyExistsException() {
		when(roadService.addRoad(Mockito.any(), Mockito.any())).thenThrow(new RecordAlreadyExistsException());
		ResponseEntity<APIResponse<RoadDTO>> addedRoad = roadController.addRoad(roadDTO, header);
		assertEquals(addedRoad.getStatusCodeValue(),406);

		when(roadService.updateRoad(Mockito.any(), Mockito.any())).thenThrow(new RecordAlreadyExistsException());
		ResponseEntity<APIResponse<RoadDTO>> updateRoad = roadController.updateRoad(roadDTO, header);
		assertEquals(updateRoad.getStatusCodeValue(),406);
	}

	@Test
	void testUpdateRoad() {
		when(roadMapper.RoadDTOToRoad(Mockito.any())).thenReturn(road);
		when(roadService.updateRoad(Mockito.any(), Mockito.any())).thenReturn(road);
		when(roadMapper.RoadToRoadDTO(Mockito.any())).thenReturn(roadDTO);
		ResponseEntity<APIResponse<RoadDTO>> updateRoad = roadController.updateRoad(roadDTO,
				header);
		assertEquals(updateRoad.getStatusCodeValue(), 200);
	}

	@Test
	void testDeleteRoad() {
		when(roadMapper.RoadDTOToRoad(Mockito.any())).thenReturn(road);
		roadService.deleteRoad(Mockito.any());
		when(roadMapper.RoadToRoadDTO(Mockito.any())).thenReturn(roadDTO);
		ResponseEntity<List<APIResponse<RoadDTO>>> deleteList = roadController.deleteRoad(roadDTOList);
		assertEquals(deleteList.getStatusCodeValue(),200);
	}

	@Test
	void testDeleteRangesEmptyDTOList(){
		ResponseEntity<List<APIResponse<RoadDTO>>> responseEntity = roadController.deleteRoad(Collections.EMPTY_LIST);
		assertEquals(responseEntity.getStatusCodeValue(),500);
	}

}
