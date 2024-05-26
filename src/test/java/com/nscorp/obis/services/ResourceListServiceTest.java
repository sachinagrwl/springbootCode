package com.nscorp.obis.services;

import com.nscorp.obis.domain.ResourceList;
import com.nscorp.obis.dto.ResourceListDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.repository.ResourceListRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class ResourceListServiceTest {
	
	@InjectMocks
	ResourceListServiceImpl resourceListService;

	@Mock
	ResourceListRepository resourceListRepository;
	
	ResourceList resource;
	List<ResourceList> resourceList;
	ResourceListDTO resourceDto;
	List<ResourceListDTO> resourceDtoList;
	
	Map<String, String> header;

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		
		MockitoAnnotations.initMocks(this);
		System.out.println("Repo:- "+ resourceListRepository);
		
		resource = new ResourceList();
		resourceDto = new ResourceListDTO();
		resourceList = new ArrayList<>();
		resourceDtoList = new ArrayList<>();
		
		resourceDto.setResourceName("TEST");
		resourceDto.setResourceDescription("TEST");
		
		resource.setResourceName("TEST");
		resource.setResourceDescription("TEST");
		
		resourceList.add(resource);
		resourceDtoList.add(resourceDto);
		
		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");
		
	}

	@AfterEach
	void tearDown() throws Exception {
		
		resource = null;
		resourceDto = null;
		resourceList = null;
		resourceDtoList = null;
		
	}
	
	@Test
	void testGetAllResourceList() {
		
		when(resourceListRepository.findAll())
					.thenReturn(resourceList);
		List<ResourceList> allResources = resourceListService
					.getAllResourceList();
		assertEquals(allResources, resourceList);
	}
	
	@Test
	void testGetAllResourceListException() {
		
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(resourceListService.getAllResourceList()));
		assertEquals("Record Not found", exception.getMessage());
		
	}

}
