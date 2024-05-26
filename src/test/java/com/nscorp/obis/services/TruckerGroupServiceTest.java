package com.nscorp.obis.services;


import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nscorp.obis.repository.PoolRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.nscorp.obis.domain.TruckerGroup;
import com.nscorp.obis.dto.TruckerGroupDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.repository.TruckerGroupRepository;

class TruckerGroupServiceTest {
	
	@InjectMocks
	TruckerGroupServiceImpl truckerGroupService;
	
	@Mock
	TruckerGroupRepository truckerGroupRepository;

	@Mock
	PoolRepository poolRepo;
	
	TruckerGroup truckerGroup;
    List<TruckerGroup> truckerGroupList;
    TruckerGroupDTO truckerGroupDto;
    List<TruckerGroupDTO> truckerGroupDtoList;
    TruckerGroup addedTruckerGroup;
    TruckerGroup updatedTruckerGroup;
    Map<String, String> header;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		truckerGroupDto = new TruckerGroupDTO();
		truckerGroup = new TruckerGroup();
		truckerGroupDtoList = new ArrayList<>();
		truckerGroupList = new ArrayList<>();
		truckerGroup.setTruckerGroupCode("TEST");
		truckerGroup.setTruckerGroupDesc("TEST");
		truckerGroup.setSetupSchema(null);
		
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
		when(truckerGroupRepository.findAll()).thenReturn(truckerGroupList);
		List<TruckerGroup> truckerGroup = truckerGroupService.getAllTruckerGroups();
        assertEquals(truckerGroup,truckerGroupList);
	}
	
	@Test
	void testGetAllTruckerGroupsException() {
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
                () -> when(truckerGroupService.getAllTruckerGroups()));
        Assertions.assertEquals("No Records found for Trucker Groups", exception.getMessage());
	}

	@Test
	void testAddTruckerGroup() {
		when(truckerGroupRepository.existsByTruckerGroupCode(Mockito.any())).thenReturn(false);
		when(truckerGroupRepository.save(Mockito.any())).thenReturn(truckerGroup);
		addedTruckerGroup = truckerGroupService.addTruckerGroup(truckerGroup, header);
		assertNotNull(addedTruckerGroup);
	}
	
	@Test
	void testAddTruckerGroupException() {
		TruckerGroup d = new TruckerGroup();
		d.setTruckerGroupCode("TEST");
		d.setTruckerGroupDesc("TEST");
		d.setSetupSchema("TEST");
		
		when(truckerGroupRepository.existsByTruckerGroupCode(Mockito.any())).thenReturn(true);
		
		RecordAlreadyExistsException exception = assertThrows(RecordAlreadyExistsException.class,
				() -> when(truckerGroupService.addTruckerGroup(d, header)));
		assertEquals("Record with Trucker: TEST already exists!", exception.getMessage());
	}

	@Test
	void testUpdateTruckerGroup() {
		truckerGroup.setUversion("!");
		when(truckerGroupRepository.existsByTruckerGroupCodeAndUversion(Mockito.any(), Mockito.any())).thenReturn(true);
		when(truckerGroupRepository.findByTruckerGroupCode(Mockito.any())).thenReturn(truckerGroup);
		when(truckerGroupRepository.save(Mockito.any())).thenReturn(truckerGroup);
		updatedTruckerGroup = truckerGroupService.updateTruckerGroup(truckerGroup, header);
		updatedTruckerGroup.setTruckerGroupCode("ABCD");
		assertEquals(updatedTruckerGroup, truckerGroup);
	}
	
	@Test
	void testUpdateTruckerGroupNoRecordsFoundException() {
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> truckerGroupService.updateTruckerGroup(truckerGroup, header));
		assertEquals("No record found for this 'truckerGroupCode': TEST", exception.getMessage());
	}

	@Test
	void testDeleteTruckerGroup() {
		when(truckerGroupRepository.existsByTruckerGroupCode(Mockito.any())).thenReturn(true);
		when(poolRepo.existsByTruckerGroup_TruckerGroupCode(Mockito.any())).thenReturn(false);
		when(truckerGroupRepository.findByTruckerGroupCode(Mockito.any())).thenReturn(truckerGroup);
		
		
		TruckerGroup deleteTruckerGroup = truckerGroupService.deleteTruckerGroup(truckerGroup);
		assertNotNull(deleteTruckerGroup);
	}
	
	@Test
	void testDeleteTruckerGroupNoRecordsFoundException() {
		when(truckerGroupRepository.existsByTruckerGroupCode(Mockito.any())).thenReturn(false);
		when(poolRepo.existsByTruckerGroup_TruckerGroupCode(Mockito.any())).thenReturn(false);
		when(truckerGroupRepository.findByTruckerGroupCode(Mockito.any())).thenReturn(truckerGroup);

		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
                () -> when(truckerGroupService.deleteTruckerGroup(truckerGroup)));
        assertEquals("No record Found to delete Under this Trucker Group Code: TEST", exception.getMessage());
		
		
	}

	@Test
	void testDeleteTruckerGroupRecordNotAddedException() {
		truckerGroup.setSetupSchema("TES");
		when(truckerGroupRepository.existsByTruckerGroupCode(Mockito.any())).thenReturn(true);
		when(poolRepo.existsByTruckerGroup_TruckerGroupCode(Mockito.any())).thenReturn(false);
		when(truckerGroupRepository.findByTruckerGroupCode(Mockito.any())).thenReturn(truckerGroup);
		truckerGroupRepository.deleteByTruckerGroupCode(Mockito.any());

		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> truckerGroupService.deleteTruckerGroup(truckerGroup));
		assertEquals("Record with Trucker: TEST has Setup Schema value & cannot be deleted", exception.getMessage());
	}

	@Test
	void testDeletePoolTruckerGroupRecordNotAddedException() {
		when(truckerGroupRepository.existsByTruckerGroupCode(Mockito.any())).thenReturn(true);
		when(poolRepo.existsByTruckerGroup_TruckerGroupCode(Mockito.any())).thenReturn(true);
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> truckerGroupService.deleteTruckerGroup(truckerGroup));
		assertEquals("Record is already in use in Pool", exception.getMessage());
	}

}
