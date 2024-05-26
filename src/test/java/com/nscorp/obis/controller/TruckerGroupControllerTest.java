package com.nscorp.obis.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
import org.springframework.http.ResponseEntity;

import com.nscorp.obis.domain.TruckerGroup;
import com.nscorp.obis.dto.TruckerGroupDTO;
import com.nscorp.obis.dto.mapper.TruckerGroupMapper;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.SizeExceedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.TruckerGroupService;

class TruckerGroupControllerTest {
	
	@Mock
	TruckerGroupService truckerGroupService;
	
	@Mock
	TruckerGroupMapper truckerGroupMapper;
    
    @InjectMocks
    TruckerGroupController truckerGroupController;
    
    TruckerGroup truckerGroup;
    List<TruckerGroup> truckerGroupList;
    TruckerGroupDTO truckerGroupDto;
    List<TruckerGroupDTO> truckerGroupDtoList;
    Map<String, String> header;

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		truckerGroupDto = new TruckerGroupDTO();
		truckerGroup = new TruckerGroup();
		truckerGroupDtoList = new ArrayList<>();
		truckerGroupList = new ArrayList<>();
		truckerGroup.setTruckerGroupCode("TEST");
		truckerGroup.setTruckerGroupDesc("TEST");
		truckerGroup.setSetupSchema("TEST");
		
		truckerGroupList.add(truckerGroup);
	    
	    truckerGroupDto.setTruckerGroupCode("TEST");
	    truckerGroupDto.setTruckerGroupDesc("TEST");
	    truckerGroupDto.setSetupSchema("TEST");
	    
	    truckerGroupDtoList.add(truckerGroupDto);
	    
	    header = new HashMap<String, String>();
        header.put("userid", "Test");
        header.put("extensionschema", "Test");
		
	}

	@AfterEach
	void tearDown() throws Exception {
		truckerGroup = null;
		truckerGroupList = null;
		truckerGroupDto = null;
		truckerGroupDtoList = null;
	}

	@Test
	void testGetAllTruckerGroups() {
		when(truckerGroupService.getAllTruckerGroups()).thenReturn(truckerGroupList);
        ResponseEntity<APIResponse<List<TruckerGroupDTO>>> getTruckerGroup = truckerGroupController.getAllTruckerGroups();
        assertEquals(getTruckerGroup.getStatusCodeValue(),200);
	}
	
	@Test
	void testGetAllTruckerGroupsException() {
		when(truckerGroupService.getAllTruckerGroups()).thenThrow(new RuntimeException());
		when(truckerGroupService.addTruckerGroup(Mockito.any(),Mockito.any())).thenThrow(new RuntimeException());
		when(truckerGroupService.updateTruckerGroup(Mockito.any(),Mockito.any())).thenThrow(new RuntimeException());
		when(truckerGroupService.deleteTruckerGroup(Mockito.any())).thenThrow(new RuntimeException());
		
		ResponseEntity<APIResponse<List<TruckerGroupDTO>>> getTruckerGroup = truckerGroupController.getAllTruckerGroups();
		ResponseEntity<APIResponse<TruckerGroupDTO>> addTruckerGroup = truckerGroupController.addTruckerGroup(Mockito.any(),Mockito.any());
        ResponseEntity<APIResponse<TruckerGroupDTO>> updateTruckerGroup = truckerGroupController.updateTruckerGroup(Mockito.any(),Mockito.any());
        ResponseEntity<List<APIResponse<TruckerGroupDTO>>> deleteTruckerGroup = truckerGroupController.deleteTruckerGroup(truckerGroupDtoList);
        
        assertEquals(getTruckerGroup.getStatusCodeValue(),500);
        assertEquals(addTruckerGroup.getStatusCodeValue(),500);
        assertEquals(updateTruckerGroup.getStatusCodeValue(),500);
        assertEquals(deleteTruckerGroup.getStatusCodeValue(),500);
	}
	
	@Test
	void testGetAllTruckerGroupsNoRecordsFoundException() {
		when(truckerGroupService.getAllTruckerGroups()).thenThrow(new NoRecordsFoundException());
		when(truckerGroupService.addTruckerGroup(Mockito.any(),Mockito.any())).thenThrow(new NoRecordsFoundException());
		when(truckerGroupService.updateTruckerGroup(Mockito.any(),Mockito.any())).thenThrow(new NoRecordsFoundException());
		
		ResponseEntity<APIResponse<List<TruckerGroupDTO>>> getTruckerGroup = truckerGroupController.getAllTruckerGroups();
		ResponseEntity<APIResponse<TruckerGroupDTO>> addTruckerGroup = truckerGroupController.addTruckerGroup(Mockito.any(),Mockito.any());
        ResponseEntity<APIResponse<TruckerGroupDTO>> updateTruckerGroup = truckerGroupController.updateTruckerGroup(Mockito.any(),Mockito.any());
        
        assertEquals(getTruckerGroup.getStatusCodeValue(),404);
        assertEquals(addTruckerGroup.getStatusCodeValue(),404);
        assertEquals(updateTruckerGroup.getStatusCodeValue(),404);
	}
	
	@Test
	void testGetAllTruckerGroupsSizeExceededException() {
		when(truckerGroupService.addTruckerGroup(Mockito.any(),Mockito.any())).thenThrow(new SizeExceedException());
		ResponseEntity<APIResponse<TruckerGroupDTO>> addTruckerGroup = truckerGroupController.addTruckerGroup(Mockito.any(),Mockito.any());
		assertEquals(addTruckerGroup.getStatusCodeValue(),411);
	}
	
	@Test
	void testGetAllTruckerGroupsRecordNotException() {
	
		when(truckerGroupService.addTruckerGroup(Mockito.any(),Mockito.any())).thenThrow(new RecordNotAddedException());
		ResponseEntity<APIResponse<TruckerGroupDTO>> addTruckerGroup = truckerGroupController.addTruckerGroup(Mockito.any(),Mockito.any());
		assertEquals(addTruckerGroup.getStatusCodeValue(),406);
	}
	
	@Test
	void testGetAllTruckerGroupsRecordAlreadyExistsException() {
		when(truckerGroupService.addTruckerGroup(Mockito.any(),Mockito.any())).thenThrow(new RecordAlreadyExistsException());
		ResponseEntity<APIResponse<TruckerGroupDTO>> addTruckerGroup = truckerGroupController.addTruckerGroup(Mockito.any(),Mockito.any());
		assertEquals(addTruckerGroup.getStatusCodeValue(),208);
	}
	
	@Test
	void testGetAllTruckerGroupsInvalidDataException() {
		when(truckerGroupService.updateTruckerGroup(Mockito.any(),Mockito.any())).thenThrow(new InvalidDataException());
		ResponseEntity<APIResponse<TruckerGroupDTO>> updateTruckerGroup = truckerGroupController.updateTruckerGroup(Mockito.any(),Mockito.any());
		assertEquals(updateTruckerGroup.getStatusCodeValue(),406);
	}
	
	@Test
	void testGetAllTruckerGroupsRecordNotAddedException() {
		when(truckerGroupService.updateTruckerGroup(Mockito.any(),Mockito.any())).thenThrow(new RecordNotAddedException());
		ResponseEntity<APIResponse<TruckerGroupDTO>> updateTruckerGroup = truckerGroupController.updateTruckerGroup(Mockito.any(),Mockito.any());
		assertEquals(updateTruckerGroup.getStatusCodeValue(),406);
	}
	
	@Test
	void testGetAllTruckerGroupsNullPointerException() {
		
		when(truckerGroupService.addTruckerGroup(Mockito.any(),Mockito.any())).thenThrow(new NullPointerException());
		ResponseEntity<APIResponse<TruckerGroupDTO>> addTruckerGroup = truckerGroupController.addTruckerGroup(Mockito.any(),Mockito.any());
		assertEquals(addTruckerGroup.getStatusCodeValue(),400);
		
		when(truckerGroupService.updateTruckerGroup(Mockito.any(),Mockito.any())).thenThrow(new NullPointerException());
		ResponseEntity<APIResponse<TruckerGroupDTO>> updateTruckerGroup = truckerGroupController.updateTruckerGroup(Mockito.any(),Mockito.any());
		assertEquals(updateTruckerGroup.getStatusCodeValue(),400);
	}

	@Test
	void testAddTruckerGroup() {
		when(truckerGroupMapper.truckerGroupDTOToTruckerGroup(Mockito.any())).thenReturn(truckerGroup);
		when(truckerGroupService.addTruckerGroup(Mockito.any(),Mockito.any())).thenReturn(truckerGroup);
		when(truckerGroupMapper.truckerGroupToTruckerGroupDTO(Mockito.any())).thenReturn(truckerGroupDto);
		ResponseEntity<APIResponse<TruckerGroupDTO>> addTruckerGroup = truckerGroupController.addTruckerGroup(Mockito.any(),Mockito.any());
		assertNotNull(addTruckerGroup.getBody());
	}

	@Test
	void testUpdateTruckerGroup() {
		when(truckerGroupMapper.truckerGroupDTOToTruckerGroup(Mockito.any())).thenReturn(truckerGroup);
		when(truckerGroupService.addTruckerGroup(Mockito.any(),Mockito.any())).thenReturn(truckerGroup);
		when(truckerGroupMapper.truckerGroupToTruckerGroupDTO(Mockito.any())).thenReturn(truckerGroupDto);
		ResponseEntity<APIResponse<TruckerGroupDTO>> updateTruckerGroup = truckerGroupController.updateTruckerGroup(Mockito.any(),Mockito.any());
		assertNotNull(updateTruckerGroup.getBody());
	}

	@Test
	void testDeleteTruckerGroup() {
		when(truckerGroupMapper.truckerGroupDTOToTruckerGroup(Mockito.any())).thenReturn(truckerGroup);
		truckerGroupService.deleteTruckerGroup(Mockito.any());
		when(truckerGroupMapper.truckerGroupToTruckerGroupDTO(Mockito.any())).thenReturn(truckerGroupDto);
		ResponseEntity<List<APIResponse<TruckerGroupDTO>>> deleteTruckerGroup = truckerGroupController.deleteTruckerGroup(truckerGroupDtoList);
		assertEquals(deleteTruckerGroup.getStatusCodeValue(),200);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	void testDeleteTruckerGroupDTOList() {
		ResponseEntity<List<APIResponse<TruckerGroupDTO>>> deleteTruckerGroup = truckerGroupController.deleteTruckerGroup(Collections.EMPTY_LIST);
		assertEquals(deleteTruckerGroup.getStatusCodeValue(),500);
	}

}
