package com.nscorp.obis.controller;

import static org.junit.Assert.assertNotNull;
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

import com.nscorp.obis.domain.AARType;
import com.nscorp.obis.dto.AARTypeDTO;
import com.nscorp.obis.dto.mapper.AARTypeMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.AARTypeService;

class AARTypeControllerTest {

	@Mock
	AARTypeService aarTypeService;

	@Mock
	AARTypeMapper aarTypeMapper;

	@InjectMocks
	AARTypeController aarTypeController;

	AARType aarType;
	AARTypeDTO aarTypeDTO;
	List<AARType> aarTypeList;
	List<AARTypeDTO> aarTypeDtoList;
	Map<String, String> header;
	String type2;
	List<String> type;
	List<String> description;
	List<Integer> capacity;
	String sType;
	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		aarType = new AARType();
		aarType.setAarCapacity(1);
		aarType.setAarType("P110");
		aarType.setAarDescription("DEMO");
		aarType.setImDescription("Demo");
		aarType.setStandardAarType("Demo");

		aarTypeList = new ArrayList<>();

		aarTypeList.add(aarType);

		aarTypeDTO = new AARTypeDTO();
		aarTypeDTO.setAarCapacity(2);
		aarTypeDTO.setAarType("p221");
		aarTypeDTO.setAarDescription("TDA");
		aarTypeDTO.setImDescription("TDM");
		aarTypeDTO.setStandardAarType("TIG");

		aarTypeDtoList = new ArrayList<>();
		aarTypeDtoList.add(aarTypeDTO);

		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");
		type = new ArrayList<>();
		type.add("car");
		description= new ArrayList<>();
		description.add("145300");
		capacity = new ArrayList<>();
		capacity.add(11);
		sType="car";
		type2 = "freight";
	}

	@AfterEach
	void tearDown() throws Exception {
		aarType = null;
		aarTypeList = null;
		aarTypeDTO = null;
	}


	@Test
	void testGetAllAARTypes() {
		when(aarTypeService.getAllAARTypes(sType)).thenReturn(aarTypeList);
		ResponseEntity<APIResponse<List<AARTypeDTO>>> aarTypeList = aarTypeController.getAllAARTypes(sType);
		assertEquals(aarTypeList.getStatusCodeValue(), 200);
	}

	@Test
	void testGetAllAARTypesNoRecordsFoundException() {
		when(aarTypeService.getAllAARTypes(Mockito.any())).thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<List<AARTypeDTO>>> corporateCustomerListGet = aarTypeController
				.getAllAARTypes(Mockito.any());
		assertEquals(corporateCustomerListGet.getStatusCodeValue(), 404);
	}

	@Test
	void testGetAllAARTypesException() {
		when(aarTypeService.getAllAARTypes(Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<List<AARTypeDTO>>> corporateCustomerListGet = aarTypeController
				.getAllAARTypes(Mockito.any());
		assertEquals(corporateCustomerListGet.getStatusCodeValue(), 500);
	}



	@Test
	void testgetAllAARTypesList() {
		when(aarTypeService.getAllAARTypesList(Mockito.any(), Mockito.any(), Mockito.any()))
				.thenReturn(aarTypeList);
		ResponseEntity<APIResponse<List<AARTypeDTO>>> aarTypeList1 = aarTypeController.getAllAARTypesList(type,
				description, capacity);
		assertNotNull(aarTypeList1.getBody());
	}

	@Test
	void testGetAllAARTypesListNoRecordsFoundException() {
		when(aarTypeService.getAllAARTypesList(Mockito.any(), Mockito.any(), Mockito.any()))
				.thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<List<AARTypeDTO>>> AarTypeListGet = aarTypeController
				.getAllAARTypesList(Mockito.any(), Mockito.any(), Mockito.any());
		assertEquals(AarTypeListGet.getStatusCodeValue(), 200);
	}

	@Test
	void testGetAllAARTypesListException() {
		when(aarTypeService.getAllAARTypesList(type, description, capacity)).thenReturn(aarTypeList);
		when(aarTypeService.getAllAARTypesList(Mockito.any(), Mockito.any(), Mockito.any()))
				.thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<List<AARTypeDTO>>> corporateCustomerListGet = aarTypeController
				.getAllAARTypesList(Mockito.any(), Mockito.any(), Mockito.any());
		assertEquals(corporateCustomerListGet.getStatusCodeValue(), 500);
	}


	@Test
	void testAddAarType() {
		when(aarTypeMapper.aarTypeDTOToAARType(Mockito.any())).thenReturn(aarType);
		when(aarTypeService.insertAARType(Mockito.any(), Mockito.any())).thenReturn(aarType);
		when(aarTypeMapper.aarTypeToAARTypeDTO(Mockito.any())).thenReturn(aarTypeDTO);
		ResponseEntity<APIResponse<AARTypeDTO>> addedAARType = aarTypeController.addAarType(aarTypeDTO, header);
		assertNotNull(addedAARType.getBody());
	}

	@Test
	void testAarTypeException() {
		when(aarTypeService.insertAARType(Mockito.any(), Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<AARTypeDTO>> addedAARType = aarTypeController.addAarType(Mockito.any(),
				Mockito.any());
		assertEquals(addedAARType.getStatusCodeValue(), 500);

		when(aarTypeService.updateAARType(Mockito.any(), Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<AARTypeDTO>> updateAARType = aarTypeController.updateAarType(Mockito.any(),
				Mockito.any());
		assertEquals(updateAARType.getStatusCodeValue(), 500);
	}

	@Test
	void testAarTypeNoRecordsFoundException() {
		when(aarTypeService.insertAARType(Mockito.any(), Mockito.any())).thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<AARTypeDTO>> addedAARType = aarTypeController.addAarType(Mockito.any(),
				Mockito.any());
		assertEquals(addedAARType.getStatusCodeValue(), 404);

		when(aarTypeService.updateAARType(Mockito.any(), Mockito.any())).thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<AARTypeDTO>> updateAARType = aarTypeController.updateAarType(Mockito.any(),
				Mockito.any());
		assertEquals(updateAARType.getStatusCodeValue(), 404);
	}

	@Test
	void testAarTypRecordAlreadyExistsException() {
		when(aarTypeService.insertAARType(Mockito.any(), Mockito.any())).thenThrow(new RecordAlreadyExistsException());
		ResponseEntity<APIResponse<AARTypeDTO>> addedAARType = aarTypeController.addAarType(Mockito.any(),
				Mockito.any());
		assertEquals(addedAARType.getStatusCodeValue(), 208);

		when(aarTypeService.updateAARType(Mockito.any(), Mockito.any())).thenThrow(new RecordAlreadyExistsException());
		ResponseEntity<APIResponse<AARTypeDTO>> updateAARType = aarTypeController.updateAarType(Mockito.any(),
				Mockito.any());
		assertEquals(updateAARType.getStatusCodeValue(), 208);
	}

	@Test
	void tesUpdateAarType() {
		when(aarTypeMapper.aarTypeDTOToAARType(Mockito.any())).thenReturn(aarType);
		when(aarTypeService.updateAARType(Mockito.any(), Mockito.any())).thenReturn(aarType);
		when(aarTypeMapper.aarTypeToAARTypeDTO(Mockito.any())).thenReturn(aarTypeDTO);
		ResponseEntity<APIResponse<AARTypeDTO>> updateAARType = aarTypeController.updateAarType(aarTypeDTO, header);
		assertNotNull(updateAARType.getBody());
	}

	@Test
	void testUpdateAarTypeNoRecordsFoundException() {
		when(aarTypeService.updateAARType(Mockito.any(), Mockito.any())).thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<AARTypeDTO>> updateAARType = aarTypeController.updateAarType(Mockito.any(),
				Mockito.any());
		assertEquals(updateAARType.getStatusCodeValue(), 404);
	}

	@Test
	void testDeleteAARType() {
		
		when(aarTypeMapper.aarTypeDTOToAARType(Mockito.any())).thenReturn(aarType);
		aarTypeService.deleteAARType(Mockito.any());
		when(aarTypeMapper.aarTypeToAARTypeDTO(Mockito.any())).thenReturn(aarTypeDTO);
		ResponseEntity<List<APIResponse<AARTypeDTO>>> deleteList1 = aarTypeController.deleteAARTypeDTO(aarTypeDtoList);
		assertEquals(deleteList1.getStatusCodeValue(), 200);
	
	}
	
	@Test
	void testDeleteAARTypeException() {
		when(aarTypeMapper.aarTypeDTOToAARType(Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<List<APIResponse<AARTypeDTO>>> deleteList = aarTypeController.deleteAARTypeDTO(aarTypeDtoList);
		assertEquals(deleteList.getStatusCodeValue(), 500);
	}

}
