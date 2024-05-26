package com.nscorp.obis.services;

import static org.junit.jupiter.api.Assertions.*;
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
import org.mockito.MockitoAnnotations;

import com.nscorp.obis.domain.AARHitch;
import com.nscorp.obis.dto.AARHitchDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.repository.AARHitchRepository;

class AARHitchServiceTest {
	
	@InjectMocks
	AARHitchServiceImpl aarHitchService;
	
	@Mock
	AARHitchRepository aarHitchRepo;
	
	AARHitch aarHitch;
	AARHitchDTO aarHitchDto;
	List<AARHitch> aarHitchList;
	List<AARHitchDTO> aarHitchDtoList;
	
	Map<String, String> header;
	String aarType;
	String hitchLocation;

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		aarHitch = new AARHitch();
		aarHitch.setAarType("P110");
		aarHitch.setHitchLocation("A1");
		aarHitchList = new ArrayList<>();
		aarHitchList.add(aarHitch);
		
		aarHitchDto = new AARHitchDTO();
		aarHitchDto.setAarType("P110");
		aarHitchDto.setHitchLocation("A1");
		aarHitchDtoList = new ArrayList<>();
		aarHitchDtoList.add(aarHitchDto);
		
		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");
	}

	@AfterEach
	void tearDown() throws Exception {
		aarHitch = null;
		aarHitchList = null;
		aarHitchDto = null;
		aarHitchDtoList = null;
	}

	@Test
	void testGetAllHitch() {
				when(aarHitchRepo.findAARHitch(aarType, hitchLocation))
						.thenReturn(aarHitchList);
				List<AARHitch> allHitch = aarHitchService
						.getAllHitch(aarType, hitchLocation);
				assertEquals(allHitch, aarHitchList);
	}
	
	@Test
	void testGetAllHitchAARTypeExceptionStarts() {
		
		String aarType = "A110";
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(aarHitchService.getAllHitch(aarType, hitchLocation)));
		assertEquals("'aarType' should starts with 'P', 'Q', 'S', 'U' and 'Z'", exception.getMessage());

		aarHitch.setAarType("P121");
		NoRecordsFoundException exception1 = assertThrows(NoRecordsFoundException.class,
				() -> when(aarHitchService.getAllHitch(aarHitch.getAarType(), hitchLocation)));
		assertEquals("'aarType' should starts with 'P', 'Q', 'S', 'U' and 'Z'", exception.getMessage());
	}
		                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
	@Test
	void testGetAllHitchAARTypeException() {
		
		String aarType = "PE10";
		
		aarHitch.setAarType("S121");
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(aarHitchService.getAllHitch(aarType, hitchLocation)));
		assertEquals("AARType : Character 2 to 4 must be numeric", exception.getMessage());
	}
	
	@Test
	void testGetAllHitchLocationException() {
		
		String hitchLocation = "AA";
		
		aarHitch.setHitchLocation("A1");
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(aarHitchService.getAllHitch(aarType, hitchLocation)));
		assertEquals("HitchLocation : 2nd Character must be numeric", exception.getMessage());
	}
	
	@Test
	void testGetAllHitchException() {
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(aarHitchService.getAllHitch(aarType, hitchLocation)));
		assertEquals("No Records found!", exception.getMessage());
	}

}
