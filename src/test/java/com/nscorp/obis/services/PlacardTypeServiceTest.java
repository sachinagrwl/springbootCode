package com.nscorp.obis.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
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

import com.nscorp.obis.domain.PlacardType;
import com.nscorp.obis.dto.PlacardTypeDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.repository.PlacardTypeRepository;

public class PlacardTypeServiceTest {

	@Mock
	PlacardTypeRepository placardTypeRepository;

	@InjectMocks
	PlacardTypeServiceImpl placardTypeServiceImpl;
	
	NoRecordsFoundException exception;
	PlacardTypeDTO placardTypeDto;
	PlacardType placardType;
	List<PlacardType> placardTypeList;
	List<PlacardTypeDTO> placardTypeDtoList;
	Map<String, String> header;

	String placardCd;
	
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		placardTypeList = new ArrayList<>();
		placardType = new PlacardType();
		placardTypeDto = new PlacardTypeDTO();
		placardTypeDtoList = new ArrayList<>();
		placardType.setPlacardCd("1A");
		placardType.setPlacardLongDesc("ABCD");
		placardType.setPlacardShortDesc("abcd");
		placardTypeList.add(placardType);
		
		placardTypeDto.setPlacardCd("1A");
		placardTypeDto.setPlacardLongDesc("ABCD");
		placardTypeDto.setPlacardShortDesc("abcd");
		placardTypeDtoList.add(placardTypeDto);
		
		placardCd = "1A";
		
		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");
	}
	
	@AfterEach
	void tearDown() throws Exception {
		placardType = null;
		placardTypeDto = null;
		placardTypeList = null;
		placardTypeDtoList = null;
	}
	
	@Test
	void testGetAllPlacardType() {
		when(placardTypeRepository.findAllByOrderByPlacardCdAsc()).thenReturn(placardTypeList);
		List<PlacardType> placard = placardTypeServiceImpl.getAllPlacard(placardCd);
		assertEquals(placard, placardTypeList);
	}
	
	@Test
	void testDeletePlacardType() {
		when(placardTypeRepository.existsByPlacardCdAndUversion(Mockito.any(),Mockito.any())).thenReturn(true);
		when(placardTypeRepository.findByPlacardCdAndUversion(Mockito.any(),Mockito.any())).thenReturn(placardTypeList);
		List<PlacardType> delete = placardTypeServiceImpl.deletePlacardType(placardType);
		assertEquals(delete, placardTypeList);

	}
	
	@Test
	void testPlacardTypeNoRecordFoundException() {
		when(placardTypeRepository.findAllByOrderByPlacardCdAsc()).thenReturn(Collections.emptyList());
		exception = assertThrows(NoRecordsFoundException.class,
				() -> when(placardTypeServiceImpl.getAllPlacard(placardCd)));
		assertEquals("No Records found!", exception.getMessage());
		
		when(placardTypeRepository.existsByPlacardCd(Mockito.any())).thenReturn(true);
		RecordNotAddedException exception1 = assertThrows(RecordNotAddedException.class,
				() -> when(placardTypeServiceImpl.addPlacard(placardType, header)));
		assertEquals("PlacardCd Already Exists", exception1.getMessage());
		
//		when(placardTypeRepository.existsByPlacardCdAndUversion(Mockito.any(),Mockito.any())).thenReturn(true);
//		NoRecordsFoundException exception2 = assertThrows(NoRecordsFoundException.class,
//				() -> when(placardTypeServiceImpl.deletePlacardType(placardType)));
//		assertEquals("No Records Found!", exception2.getMessage());
	}
	
	@Test
	void testAddPlacardTypr() {
		when(placardTypeRepository.existsByPlacardCd(Mockito.any())).thenReturn(false);
		when(placardTypeRepository.save(Mockito.any())).thenReturn(placardType);
		PlacardType placard = placardTypeServiceImpl.addPlacard(placardType, header);
		assertEquals(placard, placardType);
	}
	
	@Test
	void testUpdatePlacardTypr() {
		when(placardTypeRepository.existsByPlacardCd(Mockito.any())).thenReturn(true);
		when(placardTypeRepository.findByPlacardCd(Mockito.any())).thenReturn(placardType);
		when(placardTypeRepository.save(Mockito.any())).thenReturn(placardType);
		PlacardType placard = placardTypeServiceImpl.updatePlacard(placardType, header);
		assertEquals(placard, placardType);
	}
}
