package com.nscorp.obis.services;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.domain.StationRestriction;
import com.nscorp.obis.dto.StationRestrictionDTO;
import com.nscorp.obis.exception.*;
import com.nscorp.obis.repository.StationRestrictionRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class StationRestrictionServiceTest {
	
	@InjectMocks
	StationRestrictionServiceImpl stationRestrictionService;

	@Mock
	StationRestrictionRepository stationRestrictionRepository;
	
	StationRestriction  stationRestriction;
	List<StationRestriction>  stationRestrictionList;
	StationRestrictionDTO  stationRestrictionDto;
	List<StationRestrictionDTO> stationRestrictionDtoList;
	
	StationRestriction addedTermId;
	
	Long termId;
	String userId;
	String extensionSchema;
	
	
	Map<String, String> header;

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		
		MockitoAnnotations.initMocks(this);
		stationRestriction = new StationRestriction();
		stationRestrictionDto = new StationRestrictionDTO();
		stationRestrictionList = new ArrayList<>();
		stationRestrictionDtoList = new ArrayList<>();
		
		stationRestriction.setStationCrossReferenceId(100000000);
		stationRestriction.setCarType("S159");
		stationRestriction.setFreightType("U123");
		
		stationRestrictionDto.setStationCrossReferenceId(100000000);
		stationRestrictionDto.setCarType("S159");
		stationRestrictionDto.setFreightType("U123");
		
		stationRestrictionList.add(stationRestriction);
		stationRestrictionDtoList.add(stationRestrictionDto);
		termId = (long) 12354657;
		
		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");
		
	}

	@AfterEach
	void tearDown() throws Exception {
		
		stationRestriction = null;
		stationRestrictionDto = null;
		stationRestrictionList = null;
		stationRestrictionDtoList = null;
	}
	
	@Test
	void testGetAllStationRestrictions() {
		
				when(stationRestrictionRepository.findByStationCrossReferenceId(termId))
							.thenReturn(stationRestrictionList);
				List<StationRestriction> allRestrictions = stationRestrictionService
							.getStationRestriction(termId);
				assertEquals(allRestrictions, stationRestrictionList);
	}

	@Test
	void testGetAllStationRestrictionsException() {
		
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(stationRestrictionService.getStationRestriction(termId)));
		assertEquals("No Records are found for Stations", exception.getMessage());
		
	}
	
	@Test
	void testAddStationRestriction() {
		stationRestriction.setCarType("S15");
		stationRestriction.setFreightType("U12");
		assertNotNull(stationRestriction.getCarType());
		assertNotNull(stationRestriction.getFreightType());
		assertFalse(stationRestriction.getCarType().startsWith(CommonConstants.STN_REGEX));
		assertFalse(stationRestriction.getFreightType().startsWith(CommonConstants.STN_REGEX));
		when(stationRestrictionRepository.existsByStationCrossReferenceIdAndCarTypeAndFreightType(termId,stationRestriction.getCarType(),stationRestriction.getFreightType())).thenReturn(false);
		when(stationRestrictionRepository.save(Mockito.any())).thenReturn(stationRestriction);
		addedTermId = stationRestrictionService.addStationRestriction(termId, stationRestriction, header);
		assertEquals(addedTermId, stationRestriction);
	}

	@Test
	void testAddStationRestrictionCarTypeNull() {
		stationRestriction.setCarType(null);
		stationRestriction.setFreightType(null);
		SizeExceedException exception = assertThrows(SizeExceedException.class,
				() -> when(stationRestrictionService.addStationRestriction(termId,stationRestriction,header)));
		assertEquals("Car Type and Freight Type both should not be null or '_'", exception.getMessage());
	}

	@Test
	void testAddStationRestrictionRecordNotAddedException() {
		stationRestriction.setCarType(null);
		assertNotEquals(stationRestriction.getCarType(), "____");
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> when(stationRestrictionService.addStationRestriction(termId,stationRestriction,header)));
		assertEquals("Record with Reference Id 12354657, Car Type ____ and Freight Type U123 Not Added!", exception.getMessage());
	}

	@Test
	void testAddStationRestrictionCarTypeException() {
		stationRestriction.setCarType("S12U");
		assertNotEquals(stationRestriction.getCarType(), "____");
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> when(stationRestrictionService.addStationRestriction(termId,stationRestriction,header)));
		assertEquals("CarType : Character 2 to 4 must be numeric or '_'", exception.getMessage());
	}

	@Test
	void testAddStationRestrictionFrightTypeException() {
		stationRestriction.setFreightType("U12U");
		assertNotEquals(stationRestriction.getFreightType(), "____");
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> when(stationRestrictionService.addStationRestriction(termId,stationRestriction,header)));
		assertEquals("FreightType : Character 2 to 4 must be numeric or '_'", exception.getMessage());
	}

	@Test
	void testAddStationRestrictionCarTypeExceptionStarts() {
		stationRestriction.setCarType("U123");
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> when(stationRestrictionService.addStationRestriction(termId,stationRestriction,header)));
		assertEquals("Car Type should start with 'P', 'Q' & 'S' AND Car Type and Freight Type should not be null or '_'", exception.getMessage());
	}

	@Test
	void testAddStationRestrictionFrightTypeExceptionStarts() {
		stationRestriction.setFreightType("S123");
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> when(stationRestrictionService.addStationRestriction(termId,stationRestriction,header)));
		assertEquals("Freight Type should start with 'U' & 'Z' AND Car Type and Freight Type should not be null or '_'", exception.getMessage());
	}

	@Test
	void testAddStationRestrictionCarTypeExceptionStartWith() {
		stationRestriction.setCarType("___C");
		stationRestriction.setFreightType(null);
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> when(stationRestrictionService.addStationRestriction(termId,stationRestriction,header)));
		assertEquals("CarType : Character 2 to 4 must be '_'", exception.getMessage());
	}

	@Test
	void testAddStationRestrictionFreightTypeExceptionStartWith() {
		stationRestriction.setFreightType("___C");
		stationRestriction.setCarType(null);
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> when(stationRestrictionService.addStationRestriction(termId,stationRestriction,header)));
		assertEquals("FreightType : Character 2 to 4 must be '_'", exception.getMessage());
	}

	@Test
	void testAddStationRestrictionRecordAlreadyExistsException() {
		when(stationRestrictionRepository.existsByStationCrossReferenceIdAndCarTypeAndFreightType(termId,stationRestriction.getCarType(),stationRestriction.getFreightType())).thenReturn(true);
		RecordAlreadyExistsException exception = assertThrows(RecordAlreadyExistsException.class,
				() -> when(stationRestrictionService.addStationRestriction(termId,stationRestriction,header)));
		assertEquals("Record with Reference Id 12354657, Car Type S159 and Freight Type U123 Already Exists!", exception.getMessage());
	}

	@Test
	void testDeleteStationRestriction() {
		stationRestriction.setCarType(null);
		when(stationRestrictionRepository.existsByStationCrossReferenceIdAndCarTypeAndFreightType(termId,stationRestriction.getCarType(),stationRestriction.getFreightType())).thenReturn(true);
		stationRestrictionService.deleteStationRestriction(termId, stationRestriction);
	}

	@Test
	void testDeleteStationRestrictionRecordNotDeletedException() {
		stationRestriction.setFreightType(null);
		when(stationRestrictionRepository.existsByStationCrossReferenceIdAndCarTypeAndFreightType(termId,stationRestriction.getCarType(),stationRestriction.getFreightType())).thenReturn(false);
		RecordNotDeletedException exception = assertThrows(RecordNotDeletedException.class,
				() -> stationRestrictionService.deleteStationRestriction(termId,stationRestriction));
		assertEquals("12354657, S159 and null Record Not Found!", exception.getMessage());
	}

}
