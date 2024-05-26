package com.nscorp.obis.controller;

import com.nscorp.obis.domain.ResourceList;
import com.nscorp.obis.dto.ResourceListDTO;
import com.nscorp.obis.dto.mapper.ResourceListMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.ResourceListService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class ResourceListControllerTest {
	
	@Mock
	ResourceListService resourceListService;

	@Mock
	ResourceListMapper resourceListMapper;

	@InjectMocks
	ResourceListController resourceListController;

	ResourceListDTO resourceListDto;
//	PositionalWeightLimitMaintenanceDTOList positionalWeightLimitMaintenanceDtoList;
	ResourceList resourceList;
	List<ResourceList> resourceListList;
	List<ResourceListDTO> resourceListDtoList;

	Map<String, String> header;

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		
		MockitoAnnotations.initMocks(this);
		resourceList = new ResourceList();
		resourceList.setResourceName("TEST");
		resourceList.setResourceDescription("TEST");
		
		resourceListList = new ArrayList<>();
		resourceListList.add(resourceList);
		
		resourceListDto = new ResourceListDTO();
		resourceListDto.setResourceName("TEST");
		resourceListDto.setResourceDescription("TEST");
		
		resourceListDtoList = new ArrayList<>();
		resourceListDtoList.add(resourceListDto);
		
		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");
	}

	@AfterEach
	void tearDown() throws Exception {
		
		resourceListDto = null;
		resourceList = null;
		resourceListList = null;
		resourceListDtoList = null;
		
	}

	@Test
	@DisplayName("Get All Resource List")
	void testGetAllResourceList() {
		when(resourceListService.getAllResourceList()).thenReturn(resourceListList);
		ResponseEntity<APIResponse<List<ResourceListDTO>>> resourceListList = resourceListController.getAllResourceList();
		assertNotNull(resourceListList.getBody());
	}

	@Test
	@DisplayName("No Resource List Found")
	void testGetAllResourceListNoRecordsFoundException() {
		when(resourceListService.getAllResourceList()).thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<List<ResourceListDTO>>> resourceListGet = resourceListController.getAllResourceList();
		assertEquals(resourceListGet.getStatusCodeValue(),404);
	}

	@Test
	@DisplayName("Internal Server Error")
	void testGetAllResourceListException() {
		when(resourceListService.getAllResourceList()).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<List<ResourceListDTO>>> resourceListGet = resourceListController.getAllResourceList();
		assertEquals(resourceListGet.getStatusCodeValue(),500);
	}

}
