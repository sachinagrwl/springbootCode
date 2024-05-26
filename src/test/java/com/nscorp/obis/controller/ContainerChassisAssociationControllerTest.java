package com.nscorp.obis.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
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

import com.nscorp.obis.domain.ContainerChassisAssociation;
import com.nscorp.obis.dto.ContainerChassisAssociationDTO;
import com.nscorp.obis.dto.mapper.ContainerChassisAssociationMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.SizeExceedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.ContainerChassisAssociationService;

public class ContainerChassisAssociationControllerTest {

    @Mock
    ContainerChassisAssociationService containerChassisAssociationService;

    @Mock
    ContainerChassisAssociationMapper containerChassisAssociationMapper;

    @InjectMocks
    ContainerChassisAssociationController containerChassisAssociationController;

    ContainerChassisAssociationDTO containerChassisAssociationDto;
    ContainerChassisAssociation containerChassisAssociation;
    List<ContainerChassisAssociation> containerChassisAssociationList;
    List<ContainerChassisAssociationDTO> containerChassisAssociationDtoList;

    Map<String, String> header;

    @BeforeEach
    void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
        containerChassisAssociation = new ContainerChassisAssociation();
        containerChassisAssociation.setAssociationId(41931813532L);
        containerChassisAssociation.setContainerInit("Test");
        containerChassisAssociation.setContainerLowNumber(BigDecimal.valueOf(100));
        containerChassisAssociation.setContainerHighNumber(BigDecimal.valueOf(200));
        containerChassisAssociation.setContainerLength(20);

        containerChassisAssociation.setChassisInit("Test");
        containerChassisAssociation.setChassisLowNumber(BigDecimal.valueOf(100));
        containerChassisAssociation.setChassisHighNumber(BigDecimal.valueOf(200));
        containerChassisAssociation.setChassisLength(20);

        containerChassisAssociation.setAllowIndicator("Y");
        containerChassisAssociation.setExpiredDateTime(null);

        containerChassisAssociationList = new ArrayList<>();
        containerChassisAssociationList.add(containerChassisAssociation);

        containerChassisAssociationDto = new ContainerChassisAssociationDTO();
        containerChassisAssociationDto.setAssociationId(41931813532L);
        containerChassisAssociationDto.setContainerInit("Test");
        containerChassisAssociationDto.setContainerLowNumber(BigDecimal.valueOf(100));
        containerChassisAssociationDto.setContainerHighNumber(BigDecimal.valueOf(200));
        containerChassisAssociationDto.setContainerLength(20);

        containerChassisAssociationDto.setChassisInit("Test");
        containerChassisAssociationDto.setChassisLowNumber(BigDecimal.valueOf(100));
        containerChassisAssociationDto.setChassisHighNumber(BigDecimal.valueOf(200));
        containerChassisAssociationDto.setChassisLength(20);

        containerChassisAssociationDto.setAllowIndicator("Y");
        containerChassisAssociationDto.setExpiredDateTime(null);

        containerChassisAssociationDtoList = new ArrayList<>();
        containerChassisAssociationDtoList.add(containerChassisAssociationDto);

        header = new HashMap<String, String>();
        header.put("userid", "Test");
        header.put("extensionschema", "Test");
    }

    @AfterEach
    void tearDown() throws Exception {

        containerChassisAssociationDto = null;
        containerChassisAssociation = null;
        containerChassisAssociationList = null;
        containerChassisAssociationDtoList = null;
    }

    @Test
    void testGetAllContainerChassisAssociations() {
        when(containerChassisAssociationService.getAllControllerChassisAssociations()).thenReturn(containerChassisAssociationList);
        ResponseEntity<APIResponse<List<ContainerChassisAssociationDTO>>> contChassisList = containerChassisAssociationController.getAllControllerChassisAssociations();
        assertNotNull(contChassisList.getBody());
    }

    @Test
    void testGetAllContainerChassisAssociationsNoRecordsFoundException() {
        when(containerChassisAssociationService.getAllControllerChassisAssociations()).thenThrow(new NoRecordsFoundException());
        when(containerChassisAssociationService.addContainerChassisAssociation(Mockito.any(),Mockito.any())).thenThrow(new NoRecordsFoundException());

        ResponseEntity<APIResponse<List<ContainerChassisAssociationDTO>>> contChassislist = containerChassisAssociationController.getAllControllerChassisAssociations();
        ResponseEntity<APIResponse<ContainerChassisAssociationDTO>> contChassisAdd = containerChassisAssociationController.addContainerChassisAssociation(containerChassisAssociationDto,header);

        assertEquals(contChassislist.getStatusCodeValue(), 404);
        assertEquals(contChassisAdd.getStatusCodeValue(),404);
        
        when(containerChassisAssociationService.updateContainerChassisAssociation(Mockito.any(), Mockito.any())).thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<ContainerChassisAssociationDTO>> updateEmbargo = containerChassisAssociationController.updateContainerChassisAssociation(containerChassisAssociationDto, header);
		assertEquals(updateEmbargo.getStatusCodeValue(),404);

		when(containerChassisAssociationService.expireContainerChassisAssociation(Mockito.any(),Mockito.any())).thenThrow(new NoRecordsFoundException());
		ResponseEntity<List<APIResponse<ContainerChassisAssociationDTO>>> deleteEmbargo = containerChassisAssociationController.expireContainerChassisAssociation(containerChassisAssociationDtoList,header);
		assertEquals(deleteEmbargo.getStatusCodeValue(),500);
    }

    @Test
    void testGetAddUpdateContainerChassisAssociationsException() {
        when(containerChassisAssociationService.getAllControllerChassisAssociations()).thenThrow(new RuntimeException());
        when(containerChassisAssociationService.addContainerChassisAssociation(Mockito.any(),Mockito.any())).thenThrow(new RuntimeException());

        ResponseEntity<APIResponse<List<ContainerChassisAssociationDTO>>> contChassislist = containerChassisAssociationController.getAllControllerChassisAssociations();
        ResponseEntity<APIResponse<ContainerChassisAssociationDTO>> contChassisAdd = containerChassisAssociationController.addContainerChassisAssociation(containerChassisAssociationDto,header);

        assertEquals(contChassislist.getStatusCodeValue(), 500);
        assertEquals(contChassisAdd.getStatusCodeValue(),500);
        
        when(containerChassisAssociationService.updateContainerChassisAssociation(Mockito.any(),Mockito.any())).thenThrow(new RuntimeException());
        when(containerChassisAssociationService.expireContainerChassisAssociation(Mockito.any(),Mockito.any())).thenThrow(new RuntimeException());
        ResponseEntity<List<APIResponse<ContainerChassisAssociationDTO>>> contChassisExpire = containerChassisAssociationController.expireContainerChassisAssociation(containerChassisAssociationDtoList,header);
        ResponseEntity<APIResponse<ContainerChassisAssociationDTO>> contChassisUpdate = containerChassisAssociationController.updateContainerChassisAssociation(containerChassisAssociationDto,header);
        assertEquals(contChassisExpire.getStatusCodeValue(), 500);
        assertEquals(contChassisUpdate.getStatusCodeValue(),500);
    }

    @Test
    void testAddUpdateContainerChassisAssociationsSizeExceedException() {
        when(containerChassisAssociationService.addContainerChassisAssociation(Mockito.any(),Mockito.any())).thenThrow(new SizeExceedException());

        ResponseEntity<APIResponse<ContainerChassisAssociationDTO>> contChassisAdd = containerChassisAssociationController.addContainerChassisAssociation(containerChassisAssociationDto,header);

        assertEquals(contChassisAdd.getStatusCodeValue(),411);
    }

    @Test
    void testAddContainerChassisAssociationsRecordAlreadyExistsException() {
        when(containerChassisAssociationService.addContainerChassisAssociation(Mockito.any(),Mockito.any())).thenThrow(new RecordAlreadyExistsException());
        ResponseEntity<APIResponse<ContainerChassisAssociationDTO>> contChassisAdd = containerChassisAssociationController.addContainerChassisAssociation(containerChassisAssociationDto,header);
        assertEquals(contChassisAdd.getStatusCodeValue(),208);
        
        when(containerChassisAssociationService.expireContainerChassisAssociation(Mockito.any(),Mockito.any())).thenThrow(new RecordAlreadyExistsException());
        ResponseEntity<List<APIResponse<ContainerChassisAssociationDTO>>> contChassisExpire = containerChassisAssociationController.expireContainerChassisAssociation(containerChassisAssociationDtoList,header);
        assertEquals(contChassisExpire.getStatusCodeValue(),500);
    }

    @Test
    void testAddContainerChassisAssociationsRecordNotAddedException() {
        when(containerChassisAssociationService.addContainerChassisAssociation(Mockito.any(),Mockito.any())).thenThrow(new RecordNotAddedException());
        ResponseEntity<APIResponse<ContainerChassisAssociationDTO>> contChassisAdd = containerChassisAssociationController.addContainerChassisAssociation(containerChassisAssociationDto,header);
        assertEquals(contChassisAdd.getStatusCodeValue(),406);
        
        when(containerChassisAssociationService.updateContainerChassisAssociation(Mockito.any(),Mockito.any())).thenThrow(new RecordNotAddedException());
        ResponseEntity<APIResponse<ContainerChassisAssociationDTO>> contChassisUpdate = containerChassisAssociationController.updateContainerChassisAssociation(containerChassisAssociationDto,header);
        assertEquals(contChassisUpdate.getStatusCodeValue(),406);
    }

    @Test
    void testAddUpdateContainerChassisAssociationsNullPointerException() {
        when(containerChassisAssociationService.addContainerChassisAssociation(Mockito.any(),Mockito.any())).thenThrow(new NullPointerException());

        ResponseEntity<APIResponse<ContainerChassisAssociationDTO>> contChassisAdd = containerChassisAssociationController.addContainerChassisAssociation(containerChassisAssociationDto,header);

        assertEquals(contChassisAdd.getStatusCodeValue(),400);
    }

    @Test
    void testAddContainerChassisAssociations() {
        when(containerChassisAssociationService.addContainerChassisAssociation(Mockito.any(), Mockito.any())).thenReturn(containerChassisAssociation);
        ResponseEntity<APIResponse<ContainerChassisAssociationDTO>> addedContChassisAssoc = containerChassisAssociationController.addContainerChassisAssociation(containerChassisAssociationDto,
                header);
        assertNotNull(addedContChassisAssoc.getBody());
    }
    
    @Test
    void testUpdateChassisAssociations() {
        when(containerChassisAssociationService.updateContainerChassisAssociation(Mockito.any(), Mockito.any())).thenReturn(containerChassisAssociation);
        ResponseEntity<APIResponse<ContainerChassisAssociationDTO>> updateContChassisAssoc = containerChassisAssociationController.updateContainerChassisAssociation(containerChassisAssociationDto,
                header);
        assertEquals(updateContChassisAssoc.getStatusCodeValue(), 200);
    }
    
    @Test
	void testDeleteChassisAssociations() {
		when(containerChassisAssociationService.expireContainerChassisAssociation(Mockito.any(), Mockito.any())).thenReturn(containerChassisAssociation);
		ResponseEntity<List<APIResponse<ContainerChassisAssociationDTO>>> responseEntity = containerChassisAssociationController.expireContainerChassisAssociation(containerChassisAssociationDtoList
				,header);
		assertEquals(responseEntity.getStatusCodeValue(),200);
		
		@SuppressWarnings("unchecked")
		ResponseEntity<List<APIResponse<ContainerChassisAssociationDTO>>> responseEntityEmptyList = containerChassisAssociationController.expireContainerChassisAssociation(Collections.EMPTY_LIST
				,header);
		assertEquals(responseEntityEmptyList.getStatusCodeValue(),500);
	}
}
