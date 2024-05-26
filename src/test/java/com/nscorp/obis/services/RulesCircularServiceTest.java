package com.nscorp.obis.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import org.apache.tomcat.util.digester.Rules;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.nscorp.obis.domain.RulesCircular;
import com.nscorp.obis.dto.RulesCircularDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.repository.RulesCircularRepository;

class RulesCircularServiceTest {

    @InjectMocks
    RulesCircularServiceImpl rulesCircularService;

    @Mock
    RulesCircularRepository rulesCircularRepository;

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
    void getAllRulesCircular() {
        when(rulesCircularRepository.findAllByOrderByEquipmentTypeAscEquipmentLengthAsc()).thenReturn(rulesCircularList);
        List<RulesCircular> rulesCirculars = rulesCircularService.getAllRulesCircular();
        assertEquals(rulesCirculars,rulesCircularList);
    }

    @Test
    void getAllRulesCircularNoRecordsFoundException() {
        NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
                () -> when(rulesCircularService.getAllRulesCircular()));
        assertEquals("No Record Found under this search!", exception.getMessage());
    }

    @Test
    void testAddRulesCircular() {
        when(rulesCircularRepository.existsByEquipmentTypeAndEquipmentLength(Mockito.any(),Mockito.any())).thenReturn(false);
        when(rulesCircularRepository.save(Mockito.any())).thenReturn(rulesCircular);
        RulesCircular addedRulesCircular = rulesCircularService.addRulesCircular(rulesCircular, header);
        assertNotNull(addedRulesCircular);

    }

    @Test
    void testAddRulesCircularRecordAlreadyExistsException() {
        RulesCircular obj = new RulesCircular();
        obj.setEquipmentType("C");
        obj.setEquipmentLength(20);
        when(rulesCircularRepository.existsByEquipmentTypeAndEquipmentLength(Mockito.any(),Mockito.any())).thenReturn(true);
        RecordAlreadyExistsException exception = assertThrows(RecordAlreadyExistsException.class,
                () -> when(rulesCircularService
                        .addRulesCircular(rulesCircular, header)));
        assertEquals("Rules Circular already exists under Equipment Type:C and Equipemnt Length:20", exception.getMessage());
    }

    @Test
    void testAddRulesCircularException() {
        RulesCircular obj = new RulesCircular();
        obj.setEquipmentType("A");
        obj.setEquipmentLength(20);
        RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
                () -> when(rulesCircularService
                        .addRulesCircular(obj, header)));
        assertEquals("EquipmentType value must be 'C', 'T', 'Z', 'F' and 'G'", exception.getMessage());

        RulesCircular obj1 = new RulesCircular();
        obj1.setEquipmentType("C");
        obj1.setEquipmentLength(80);
        RecordNotAddedException exception1 = assertThrows(RecordNotAddedException.class,
                () -> when(rulesCircularService
                        .addRulesCircular(obj1, header)));
        assertEquals("Equipment Length: 80 is not valid for Equipment Type: C", exception1.getMessage());

        RulesCircular obj2 = new RulesCircular();
        obj2.setEquipmentType("T");
        obj2.setEquipmentLength(50);
        RecordNotAddedException exception2 = assertThrows(RecordNotAddedException.class,
                () -> when(rulesCircularService
                        .addRulesCircular(obj2, header)));
        assertEquals("Equipment Length: 50 is not valid for Equipment Type: T", exception2.getMessage());

        RulesCircular obj3 = new RulesCircular();
        obj3.setEquipmentType("Z");
        obj3.setEquipmentLength(50);
        RecordNotAddedException exception3 = assertThrows(RecordNotAddedException.class,
                () -> when(rulesCircularService
                        .addRulesCircular(obj3, header)));
        assertEquals("Equipment Length: 50 is not valid for Equipment Type: Z", exception3.getMessage());
    }

    @Test
    void testAddRulesCircularRecordNotAddedException() {
        RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
                () -> when(rulesCircularService
                        .addRulesCircular(rulesCircular, header)));
        assertEquals("Record Not added to Database", exception.getMessage());
    }

    @Test
    void testUpdateRulesCircular() {
        when(rulesCircularRepository.existsByEquipmentTypeAndEquipmentLength(Mockito.any(),Mockito.any())).thenReturn(true);
        when(rulesCircularRepository.findByEquipmentTypeAndEquipmentLength(Mockito.any(),Mockito.any())).thenReturn(rulesCircular);
        when(rulesCircularRepository.save(Mockito.any())).thenReturn(rulesCircular);
        RulesCircular updatedRulesCircular = rulesCircularService.updateRulesCircular(rulesCircular,header);
        assertEquals(updatedRulesCircular,rulesCircular);
    }

    @Test
    void testUpdateRulesCircularNoRecordsFoundException() {
        NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
                () -> rulesCircularService.updateRulesCircular(rulesCircular, header));
        assertEquals("No record Found Under this Equipment Type:C and Equipment Length:20", exception.getMessage());
    }
    
    @Test
    void testDeleteRulesCircular() {
    	when(rulesCircularRepository.existsByEquipmentTypeAndEquipmentLength(any(),any())).thenReturn(true);
    	when(rulesCircularRepository.findByEquipmentTypeAndEquipmentLength(any(),any())).thenReturn(rulesCircular);
    	RulesCircular rulesCircularAfterDelete = rulesCircularService.deleteRulesCircular(rulesCircular);
        assertEquals(rulesCircularAfterDelete,rulesCircular);
    }
    
    @Test
    void testDeleteRulesCircularNoRecordsFoundException() {
    	when(rulesCircularRepository.existsByEquipmentTypeAndEquipmentLength(any(),any())).thenReturn(false);
    	assertThrows(NoRecordsFoundException.class,
                () -> rulesCircularService.deleteRulesCircular(rulesCircular));
    }
}