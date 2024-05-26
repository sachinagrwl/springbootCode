package com.nscorp.obis.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import org.springframework.http.ResponseEntity;

import com.nscorp.obis.domain.PlacardType;
import com.nscorp.obis.dto.PlacardTypeDTO;
import com.nscorp.obis.dto.mapper.PlacardTypeMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.SizeExceedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.PlacardTypeService;

public class PlacardTypeControllerTest {
	
	@Mock
	PlacardTypeService placardTypeService;
	
	@Mock
	PlacardTypeMapper placardTypeMapper;
	
	@InjectMocks
	PlacardTypeController placardTypeController;
	
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
		when(placardTypeService.getAllPlacard(Mockito.any())).thenReturn(placardTypeList);
		ResponseEntity<APIResponse<List<PlacardTypeDTO>>> result = placardTypeController.getPlacardType(placardCd);
		assertEquals(result.getStatusCodeValue(), 200);

	}
	
	@Test
	void testAddUpdatePlacard() {
		when(placardTypeService.addPlacard(placardType, header)).thenReturn(placardType);
		when(placardTypeService.updatePlacard(placardType, header)).thenReturn(placardType);
		ResponseEntity<APIResponse<PlacardTypeDTO>> result = placardTypeController.addPlacardType(placardTypeDto, header);
		assertEquals(result.getStatusCodeValue(), 200);
		result = placardTypeController.updatePlacardType(placardTypeDto, header);
		assertEquals(result.getStatusCodeValue(), 200);
	}
	
	@Test
    void testDeletePlacardType() {
        when(placardTypeService.deletePlacardType(Mockito.any())).thenReturn(placardTypeList);
        ResponseEntity<List<APIResponse<PlacardTypeDTO>>> deleteList = placardTypeController.deletePlacardType(placardTypeDtoList);
        assertEquals(200, deleteList.getStatusCodeValue());
    }
	
	@Test
	void testPlacardTypeNoRecordsFoundException() {
		when(placardTypeService.getAllPlacard(Mockito.any())).thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<List<PlacardTypeDTO>>> result = placardTypeController.getPlacardType(placardCd);
		assertEquals(result.getStatusCodeValue(), 404);
		
		when(placardTypeService.addPlacard(Mockito.any(), Mockito.any())).thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<PlacardTypeDTO>> result1 = placardTypeController.addPlacardType(placardTypeDto, header);
		assertEquals(result1.getStatusCodeValue(), 404);
		
		when(placardTypeService.updatePlacard(Mockito.any(), Mockito.any())).thenThrow(new NoRecordsFoundException());
		result1 = placardTypeController.updatePlacardType(placardTypeDto, header);
		assertEquals(result1 .getStatusCodeValue(), 404);
		
//		 when(placardTypeService.deletePlacardType(Mockito.any())).thenThrow(new NoRecordsFoundException());
//       ResponseEntity<List<APIResponse<PlacardTypeDTO>>> deleteList = placardTypeController.deletePlacardType(placardTypeDtoList);
//       assertEquals(deleteList .getStatusCodeValue(), 404);
	}
	
	@Test
	void testPlacardTypeException() {
		when(placardTypeService.getAllPlacard(Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<List<PlacardTypeDTO>>> result = placardTypeController.getPlacardType(placardCd);
		assertEquals(result.getStatusCodeValue(), 500);
		
		when(placardTypeService.addPlacard(Mockito.any(), Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<PlacardTypeDTO>> result1 = placardTypeController.addPlacardType(placardTypeDto, header);
		assertEquals(result1.getStatusCodeValue(), 500);
		
		when(placardTypeService.updatePlacard(Mockito.any(), Mockito.any())).thenThrow(new RuntimeException());
		result1 = placardTypeController.updatePlacardType(placardTypeDto, header);
		assertEquals(result1 .getStatusCodeValue(), 500);
		
		when(placardTypeService.deletePlacardType(Mockito.any())).thenThrow(new RuntimeException());
        ResponseEntity<List<APIResponse<PlacardTypeDTO>>> deleteList = placardTypeController.deletePlacardType(placardTypeDtoList);
        assertEquals(deleteList .getStatusCodeValue(), 500);
	}
	
	@Test
	void PlacardTypeNullPointer(){
		
		when(placardTypeService.addPlacard(Mockito.any(), Mockito.any())).thenThrow(new NullPointerException());
		ResponseEntity<APIResponse<PlacardTypeDTO>> result1 = placardTypeController.addPlacardType(placardTypeDto, header);
		assertEquals(result1.getStatusCodeValue(), 400);
		
		when(placardTypeService.updatePlacard(Mockito.any(), Mockito.any())).thenThrow(new NullPointerException());
		result1 = placardTypeController.updatePlacardType(placardTypeDto, header);
		assertEquals(result1 .getStatusCodeValue(), 400);
	}
	
	@Test
	void PlacardTypeSizeExceedException(){
		
		when(placardTypeService.addPlacard(Mockito.any(), Mockito.any())).thenThrow(new SizeExceedException());
		ResponseEntity<APIResponse<PlacardTypeDTO>> result1 = placardTypeController.addPlacardType(placardTypeDto, header);
		assertEquals(result1.getStatusCodeValue(), 411);
		
		when(placardTypeService.updatePlacard(Mockito.any(), Mockito.any())).thenThrow(new SizeExceedException());
		result1 = placardTypeController.updatePlacardType(placardTypeDto, header);
		assertEquals(result1 .getStatusCodeValue(), 411);
	}
	
	@Test
	void PlacardTypRecordNotAddedException(){
		
		when(placardTypeService.addPlacard(Mockito.any(), Mockito.any())).thenThrow(new RecordNotAddedException());
		ResponseEntity<APIResponse<PlacardTypeDTO>> result1 = placardTypeController.addPlacardType(placardTypeDto, header);
		assertEquals(result1.getStatusCodeValue(), 406);
		
		when(placardTypeService.updatePlacard(Mockito.any(), Mockito.any())).thenThrow(new RecordNotAddedException());
		result1 = placardTypeController.updatePlacardType(placardTypeDto, header);
		assertEquals(result1 .getStatusCodeValue(), 406);
	}

}
