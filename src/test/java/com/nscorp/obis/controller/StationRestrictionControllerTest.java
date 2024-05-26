package com.nscorp.obis.controller;

import com.nscorp.obis.domain.StationRestriction;
import com.nscorp.obis.dto.StationRestrictionDTO;
import com.nscorp.obis.dto.mapper.StationRestrictionMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.SizeExceedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.StationRestrictionService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class StationRestrictionControllerTest {
	
	@Mock
	StationRestrictionService stationRestrictionService;

	@Mock
	StationRestrictionMapper stationRestrictionMapper;

	@InjectMocks
	StationRestrictionController stationRestrictionController;

	StationRestrictionDTO stationRestrictionDto;

	StationRestriction stationRestriction;
	List<StationRestriction> stationRestrictionList;
	List<StationRestrictionDTO> stationRestrictionDtoList;

	Map<String, String> header;

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		
		MockitoAnnotations.initMocks(this);
		stationRestriction = new StationRestriction();
		stationRestriction.setStationCrossReferenceId(100000000);
		stationRestriction.setCarType("S159");
		stationRestriction.setFreightType("____");
		
		stationRestrictionList = new ArrayList<>();
		stationRestrictionList.add(stationRestriction);
		
		stationRestrictionDto = new StationRestrictionDTO();
		stationRestrictionDto.setStationCrossReferenceId(100000000);
		stationRestrictionDto.setCarType("S159");
		stationRestrictionDto.setFreightType("___");
		
		
		stationRestrictionDtoList = new ArrayList<>();
		stationRestrictionDtoList.add(stationRestrictionDto);
		
		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");
	}

	@AfterEach
	void tearDown() throws Exception {
		
		stationRestrictionDto = null;
		stationRestriction = null;
		stationRestrictionList = null;
		stationRestrictionDtoList = null;
	}

	
	@Test
	void testGetAllStationRestrictions() {
			when(stationRestrictionService.getStationRestriction(Mockito.any())).thenReturn(stationRestrictionList);
			ResponseEntity<APIResponse<List<StationRestrictionDTO>>> stationRestrictionList = stationRestrictionController.getStationRestrictions(Mockito.any());
			assertEquals(stationRestrictionList.getStatusCodeValue(), 200);
	}

	@Test
	void testGetAllStationRestrictionsNoRecordsFoundException() {
		when(stationRestrictionService.getStationRestriction(Mockito.any())).thenThrow(new NoRecordsFoundException());
		when(stationRestrictionService.addStationRestriction(Mockito.any(), Mockito.any(), Mockito.any())).thenThrow(new NoRecordsFoundException());

		ResponseEntity<APIResponse<List<StationRestrictionDTO>>> getAllStation = stationRestrictionController.getStationRestrictions(Mockito.any());
		ResponseEntity<APIResponse<StationRestrictionDTO>> addAllStation = stationRestrictionController.addStationRestrictions(stationRestriction.getStationCrossReferenceId(),stationRestrictionDto,header);

		assertEquals(getAllStation.getStatusCodeValue(), 404);
		assertEquals(addAllStation.getStatusCodeValue(),404);
	}

	@Test
	void testGetAllStationRestrictionsRuntimeException() {
		when(stationRestrictionService.getStationRestriction(Mockito.any())).thenThrow(new RuntimeException());
		when(stationRestrictionService.addStationRestriction(Mockito.any(), Mockito.any(), Mockito.any())).thenThrow(new RuntimeException());

		ResponseEntity<APIResponse<List<StationRestrictionDTO>>> getAllStation = stationRestrictionController.getStationRestrictions(Mockito.any());
		ResponseEntity<APIResponse<StationRestrictionDTO>> addAllStation = stationRestrictionController.addStationRestrictions(stationRestriction.getStationCrossReferenceId(),stationRestrictionDto,header);

		assertEquals(getAllStation.getStatusCodeValue(), 500);
		assertEquals(addAllStation.getStatusCodeValue(),500);
	}

	@Test
	void testGetAllStationRestrictionsRecordAlreadyExistsException() {
		when(stationRestrictionService.addStationRestriction(Mockito.any(), Mockito.any(), Mockito.any())).thenThrow(new RecordAlreadyExistsException());
		ResponseEntity<APIResponse<StationRestrictionDTO>> addAllStation = stationRestrictionController.addStationRestrictions(stationRestriction.getStationCrossReferenceId(),stationRestrictionDto,header);
		assertEquals(addAllStation.getStatusCodeValue(),208);
	}

	@Test
	void testAddAllStationRestrictionsSizeExceedException() {
		when(stationRestrictionService.addStationRestriction(Mockito.any(), Mockito.any(), Mockito.any())).thenThrow(new SizeExceedException());
		ResponseEntity<APIResponse<StationRestrictionDTO>> addAllStation = stationRestrictionController.addStationRestrictions(stationRestriction.getStationCrossReferenceId(),stationRestrictionDto,header);
		assertEquals(addAllStation.getStatusCodeValue(),411);
	}

	@Test
	void testGetAllStationRestrictionsRecordNotAddedException() {
		when(stationRestrictionService.addStationRestriction(Mockito.any(), Mockito.any(), Mockito.any())).thenThrow(new RecordNotAddedException());
		ResponseEntity<APIResponse<StationRestrictionDTO>> addAllStation = stationRestrictionController.addStationRestrictions(stationRestriction.getStationCrossReferenceId(),stationRestrictionDto,header);
		assertEquals(addAllStation.getStatusCodeValue(),406);
	}

	@Test
	void testGetAllStationRestrictionsNullPointerException() {
		when(stationRestrictionService.addStationRestriction(Mockito.any(), Mockito.any(), Mockito.any())).thenThrow(new NullPointerException());
		ResponseEntity<APIResponse<StationRestrictionDTO>> addAllStation = stationRestrictionController.addStationRestrictions(stationRestriction.getStationCrossReferenceId(),stationRestrictionDto,header);
		assertEquals(addAllStation.getStatusCodeValue(),400);
	}

	@Test
	void testAddStationRestrictions() {
			when(stationRestrictionMapper.stationRestrictionDTOToStationRestriction(Mockito.any())).thenReturn(stationRestriction);
			when(stationRestrictionService.addStationRestriction(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(stationRestriction);
			when(stationRestrictionMapper.stationRestrictionToStationRestrictionDTO(Mockito.any())).thenReturn(stationRestrictionDto);
			ResponseEntity<APIResponse<StationRestrictionDTO>> addedTermId = stationRestrictionController.addStationRestrictions(stationRestriction.getStationCrossReferenceId(),stationRestrictionDto,header);
			assertEquals(addedTermId.getStatusCodeValue(), 200);
	}

	@Test
	void testDeleteStationRestrictions() {
		when(stationRestrictionMapper.stationRestrictionDTOToStationRestriction(Mockito.any())).thenReturn(stationRestriction);
		stationRestrictionService.deleteStationRestriction(Mockito.any(), Mockito.any());
		when(stationRestrictionMapper.stationRestrictionToStationRestrictionDTO(Mockito.any())).thenReturn(stationRestrictionDto);
		ResponseEntity<List<APIResponse<StationRestrictionDTO>>> deleteStationRestrictions = stationRestrictionController.deleteStationRestrictions(stationRestriction.getStationCrossReferenceId(),stationRestrictionDtoList);
		assertEquals(deleteStationRestrictions.getStatusCodeValue(), 200);
	}

	@Test
	void testDeleteStationRestrictionsException() {
		StationRestrictionDTO stationRestrictionDTO = new StationRestrictionDTO();
		List<StationRestrictionDTO> stationRestrictionDTOList = new ArrayList<>();

		when(stationRestrictionMapper.stationRestrictionDTOToStationRestriction(Mockito.any())).thenReturn(stationRestriction);
		stationRestrictionService.deleteStationRestriction(Mockito.any(), Mockito.any());
		when(stationRestrictionMapper.stationRestrictionToStationRestrictionDTO(Mockito.any())).thenReturn(stationRestrictionDTO);
		ResponseEntity<List<APIResponse<StationRestrictionDTO>>> deleteStationRestrictions = stationRestrictionController.deleteStationRestrictions(stationRestriction.getStationCrossReferenceId(),stationRestrictionDTOList);
		assertEquals(deleteStationRestrictions.getStatusCodeValue(), 500);
	}

}
