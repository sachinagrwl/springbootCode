package com.nscorp.obis.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.SizeExceedException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.nscorp.obis.domain.RulesCircular;
import com.nscorp.obis.dto.RulesCircularDTO;
import com.nscorp.obis.dto.mapper.RulesCircularMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.RulesCircularService;

class RulesCircularControllerTest {

    @Mock
    RulesCircularService rulesCircularService;

    @Mock
    RulesCircularMapper rulesCircularMapper;

    @InjectMocks
    RulesCircularController rulesCircularController;

    RulesCircular rulesCircular;
    RulesCircularDTO rulesCircularDTO;
    List<RulesCircular> rulesCircularList;
    List<RulesCircularDTO> rulesCircularDTOList;
    Map<String, String> header;

    @SuppressWarnings("deprecation")
    @BeforeEach
    void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);
        rulesCircular = new RulesCircular();
        rulesCircular.setEquipmentType("C");
        rulesCircular.setEquipmentLength(20);
        rulesCircular.setMaximumShipWeight(2000);

        rulesCircularList = new ArrayList<>();
        rulesCircularList.add(rulesCircular);

        rulesCircularDTO = new RulesCircularDTO();
        rulesCircularDTO.setEquipmentType("C");
        rulesCircularDTO.setEquipmentLength(20);
        rulesCircularDTO.setMaximumShipWeight(2000);

        rulesCircularDTOList = new ArrayList<>();
        rulesCircularDTOList.add(rulesCircularDTO);

        header = new HashMap<String, String>();
        header.put("userid", "Test");
        header.put("extensionschema", "Test");
    }

    @AfterEach
    void tearDown() throws Exception{
        rulesCircular = null;
        rulesCircularDTO = null;
        rulesCircularList = null;
        rulesCircularDTOList = null;
    }

    @Test
    void getAllRules() {
        when(rulesCircularService.getAllRulesCircular()).thenReturn(rulesCircularList);
        ResponseEntity<APIResponse<List<RulesCircularDTO>>> responseEntity = rulesCircularController.getAllRules();
        assertEquals(responseEntity.getStatusCodeValue(),200);
    }

    @Test
    void getNoRecordsFoundException(){
        when(rulesCircularService.getAllRulesCircular()).thenThrow(new NoRecordsFoundException());
        ResponseEntity<APIResponse<List<RulesCircularDTO>>> responseEntity = rulesCircularController.getAllRules();
        assertEquals(responseEntity.getStatusCodeValue(),404);
    }

    @Test
    void getRuntimeException(){
        when(rulesCircularService.getAllRulesCircular()).thenThrow(new RuntimeException());
        ResponseEntity<APIResponse<List<RulesCircularDTO>>> responseEntity = rulesCircularController.getAllRules();
        assertEquals(responseEntity.getStatusCodeValue(),500);
    }

    @Test
    void testAddRulesCircular() {
        when(rulesCircularMapper.RulesCircularDTOToRulesCircular(Mockito.any())).thenReturn(rulesCircular);
        when(rulesCircularService.addRulesCircular(Mockito.any(), Mockito.any())).thenReturn(rulesCircular);
        when(rulesCircularMapper.RulesCircularToRulesCircularDTO(Mockito.any())).thenReturn(rulesCircularDTO);
        ResponseEntity<APIResponse<RulesCircularDTO>> responseEntity = rulesCircularController.addRulesCircular(rulesCircularDTO,
                header);
        assertNotNull(responseEntity.getBody());
    }


    @Test
    void testUpdateRulesCircular() {
        when(rulesCircularMapper.RulesCircularDTOToRulesCircular(Mockito.any())).thenReturn(rulesCircular);
        when(rulesCircularService.updateRulesCircular(Mockito.any(), Mockito.any())).thenReturn(rulesCircular);
        when(rulesCircularMapper.RulesCircularToRulesCircularDTO(Mockito.any())).thenReturn(rulesCircularDTO);
        ResponseEntity<APIResponse<RulesCircularDTO>> responseEntity = rulesCircularController.updateRulesCircular(rulesCircularDTO,
                header);
        assertNotNull(responseEntity.getBody());
    }

    @Test
    void testRulesCircularNoRecordsFoundException() {
        when(rulesCircularService.addRulesCircular(Mockito.any(),Mockito.any())).thenThrow(new NoRecordsFoundException());
        when(rulesCircularService.updateRulesCircular(Mockito.any(),Mockito.any())).thenThrow(new NoRecordsFoundException());

        ResponseEntity<APIResponse<RulesCircularDTO>> responseEntityAdd = rulesCircularController.addRulesCircular(Mockito.any(),Mockito.any());;
        ResponseEntity<APIResponse<RulesCircularDTO>> responseEntityUpdate = rulesCircularController.updateRulesCircular(Mockito.any(),Mockito.any());

        assertEquals(responseEntityAdd.getStatusCodeValue(),404);
        assertEquals(responseEntityUpdate.getStatusCodeValue(),404);
    }

    @Test
    void testRulesCircularSizeExceedException() {
        when(rulesCircularService.addRulesCircular(Mockito.any(),Mockito.any())).thenThrow(new SizeExceedException());
        when(rulesCircularService.updateRulesCircular(Mockito.any(),Mockito.any())).thenThrow(new SizeExceedException("Size Exceed"));

        ResponseEntity<APIResponse<RulesCircularDTO>> responseEntityAdd = rulesCircularController.addRulesCircular(Mockito.any(),Mockito.any());;
        ResponseEntity<APIResponse<RulesCircularDTO>> responseEntityUpdate = rulesCircularController.updateRulesCircular(Mockito.any(),Mockito.any());

        assertEquals(responseEntityAdd.getStatusCodeValue(),411);
        assertEquals(responseEntityUpdate.getStatusCodeValue(),411);
    }

    @Test
    void testRulesCircularRecordAlreadyExistsException() {
        when(rulesCircularService.addRulesCircular(Mockito.any(),Mockito.any())).thenThrow(new RecordAlreadyExistsException());
        ResponseEntity<APIResponse<RulesCircularDTO>> responseEntityAdd = rulesCircularController.addRulesCircular(Mockito.any(),Mockito.any());;
        assertEquals(responseEntityAdd.getStatusCodeValue(),208);
    }

    @Test
    void testRulesCircularRecordNotAddedException() {
        when(rulesCircularService.addRulesCircular(Mockito.any(),Mockito.any())).thenThrow(new RecordNotAddedException());
        ResponseEntity<APIResponse<RulesCircularDTO>> responseEntityAdd = rulesCircularController.addRulesCircular(Mockito.any(),Mockito.any());;
        assertEquals(responseEntityAdd.getStatusCodeValue(),406);
    }

    @Test
    void testRulesCircularNullPointerException() {
        when(rulesCircularService.addRulesCircular(Mockito.any(),Mockito.any())).thenThrow(new NullPointerException());
        when(rulesCircularService.updateRulesCircular(Mockito.any(),Mockito.any())).thenThrow(new NullPointerException("Null pointer"));

        ResponseEntity<APIResponse<RulesCircularDTO>> responseEntityAdd = rulesCircularController.addRulesCircular(Mockito.any(),Mockito.any());;
        ResponseEntity<APIResponse<RulesCircularDTO>> responseEntityUpdate = rulesCircularController.updateRulesCircular(Mockito.any(),Mockito.any());

        assertEquals(responseEntityAdd.getStatusCodeValue(),400);
        assertEquals(responseEntityUpdate.getStatusCodeValue(),400);
    }

    @Test
    void testRulesCircularException() {
        when(rulesCircularService.addRulesCircular(Mockito.any(),Mockito.any())).thenThrow(new RuntimeException());
        when(rulesCircularService.updateRulesCircular(Mockito.any(),Mockito.any())).thenThrow(new RuntimeException());

        ResponseEntity<APIResponse<RulesCircularDTO>> responseEntityAdd = rulesCircularController.addRulesCircular(Mockito.any(),Mockito.any());;
        ResponseEntity<APIResponse<RulesCircularDTO>> responseEntityUpdate = rulesCircularController.updateRulesCircular(Mockito.any(),Mockito.any());

        assertEquals(responseEntityAdd.getStatusCodeValue(), 500);
        assertEquals(responseEntityUpdate.getStatusCodeValue(), 500);
    }
    
    @Test
    void testDeleteRulesCircular() {
    	when(rulesCircularMapper.RulesCircularDTOToRulesCircular(any())).thenReturn(rulesCircular);
    	when(rulesCircularService.deleteRulesCircular(any())).thenReturn(rulesCircular);
		when(rulesCircularMapper.RulesCircularToRulesCircularDTO(any())).thenReturn(rulesCircularDTO);
    	ResponseEntity<List<APIResponse<RulesCircularDTO>>> responseEntity = rulesCircularController.deleteRulesCircular(rulesCircularDTOList);
        assertEquals(responseEntity.getStatusCodeValue(),200);
    }
    
    @Test
    @SuppressWarnings("unchecked")
    void testDeleteRulesCircularEmptyDTOList(){
		ResponseEntity<List<APIResponse<RulesCircularDTO>>> responseEntity = rulesCircularController.deleteRulesCircular(Collections.EMPTY_LIST);
        assertEquals(responseEntity.getStatusCodeValue(),500);
    }
    
    @Test
    void testDeleteRulesCircularException(){
    	when(rulesCircularService.deleteRulesCircular(any())).thenThrow(new RuntimeException());
        ResponseEntity<List<APIResponse<RulesCircularDTO>>> responseEntity = rulesCircularController.deleteRulesCircular(rulesCircularDTOList);
        assertEquals(responseEntity.getStatusCodeValue(),500);
    }
    
    @Test
    void testDeleteRulesCircularNoRecordFoundException(){
    	when(rulesCircularService.deleteRulesCircular(any())).thenThrow(new NoRecordsFoundException());
        ResponseEntity<List<APIResponse<RulesCircularDTO>>> responseEntity = rulesCircularController.deleteRulesCircular(rulesCircularDTOList);
        assertEquals(responseEntity.getStatusCodeValue(),500);
    }
}