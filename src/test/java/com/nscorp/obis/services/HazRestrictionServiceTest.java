package com.nscorp.obis.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.*;

import com.nscorp.obis.domain.CarEmbargo;
import com.nscorp.obis.response.data.APIResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.nscorp.obis.domain.HazRestriction;
import com.nscorp.obis.dto.HazRestrictionDTO;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordNotDeletedException;
import com.nscorp.obis.repository.HazRestrictionRepository;
import org.springframework.http.ResponseEntity;

public class HazRestrictionServiceTest {

    @InjectMocks
    HazRestrictionServiceImpl hazRestrictionService;

    @Mock
    HazRestrictionRepository hazRestrictionRepository;

    HazRestriction hazRestriction;

    HazRestrictionDTO hazRestrictionDTO;

    List<HazRestrictionDTO> hazRestrictionDTOList;

    List<HazRestriction> hazRestrictionList;

    String unCd;

    Map<String, String> header;
    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        hazRestriction = new HazRestriction();
        hazRestriction.setUnCd("NA0124");
        hazRestriction.setRestoreClass("E");

        hazRestrictionList = new ArrayList<>();
        hazRestrictionList.add(hazRestriction);

        hazRestrictionDTO = new HazRestrictionDTO();
        hazRestrictionDTO.setUnCd("NA0124");
        hazRestrictionDTO.setRestoreClass("E");


        hazRestrictionDTOList = new ArrayList<>();
        hazRestrictionDTOList.add(hazRestrictionDTO);

        header = new HashMap<String, String>();
        header.put("userid", "Test");
        header.put("extensionschema", "Test");


    }

    @AfterEach
    void tearDown() throws Exception {
        hazRestrictionDTO = null;
        hazRestrictionDTOList = null;
        hazRestrictionList = null;
        hazRestriction = null;
    }


    @Test
    void testGetHazRestriction() {
        when(hazRestrictionRepository.findAll(unCd)).thenReturn(hazRestrictionList);
        List<HazRestriction> getHr = hazRestrictionService.getHazRestriction(unCd);
        assertEquals(getHr, hazRestrictionList);
    }

    @Test
    void testGetHazRestrictionNoRecordFoundException() {
        NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
                () -> when(hazRestrictionService.getHazRestriction(unCd)));
        assertEquals("No records found", exception.getMessage());
    }

    @Test
    void testInsertHazRestriction() {
        when(hazRestrictionRepository.save(Mockito.any())).thenReturn(hazRestriction);
        hazRestriction = hazRestrictionService.insertHazRestriction(hazRestriction, header);
        assertNotNull(hazRestriction);
    }

    @Test
    void testInsertHazRestrictionInvalidUnCode() {
        hazRestriction.setUnCd(null);
        InvalidDataException exception = assertThrows(InvalidDataException.class,
                () -> when(hazRestrictionService.insertHazRestriction(hazRestriction,header)));
        assertEquals("Please check for UNCode value, cannot be null or empty",exception.getMessage());
    }

    @Test
    void testInsertHazRestrictionInvalidRestrictionClass() {
        hazRestriction.setRestoreClass(null);
        InvalidDataException exception = assertThrows(InvalidDataException.class,
                () -> when(hazRestrictionService.insertHazRestriction(hazRestriction,header)));
        assertEquals("Please check for restriction class value, cannot be null or empty",exception.getMessage());
    }

    @Test
    void testDeletetHazRestriction() {
        when(hazRestrictionRepository.existsByUnCd(any())).thenReturn(true);
        when(hazRestrictionRepository.findAll((String) any())).thenReturn(hazRestrictionList);
        hazRestrictionService.deleteHazRestriction(hazRestriction);
    }
    @Test
    void testDeleteHazRestrictionNoRecordsFoundException() {
        when(hazRestrictionRepository.existsByUnCd(any())).thenReturn(false);
        assertThrows(RecordNotDeletedException.class,
                () -> hazRestrictionService.deleteHazRestriction(hazRestriction));
    }
    @Test
    void testUpdateHazRestriction() {

        when(hazRestrictionRepository.existsByUnCd(Mockito.any())).thenReturn(true);
        when(hazRestrictionRepository.findByUnCd(Mockito.any())).thenReturn(hazRestriction);
        when(hazRestrictionRepository.save(Mockito.any())).thenReturn(hazRestriction);
        HazRestriction updateHaz = hazRestrictionService.updateHazRestriction(hazRestriction, header);
        assertNotNull(updateHaz);
    }

    @Test
    void testHazRestrictionNoRecordsFoundException() {

        when(hazRestrictionRepository.existsByUnCd(Mockito.any())).thenReturn(false);

        NoRecordsFoundException exception1 = assertThrows(NoRecordsFoundException.class,
                () -> when(hazRestrictionService.updateHazRestriction(hazRestriction,header)));
        assertEquals("No record Found to update for UN CODE:"+hazRestriction.getUnCd()
                + " and Uversion:" + hazRestriction.getUversion(), exception1.getMessage());
    }



}
