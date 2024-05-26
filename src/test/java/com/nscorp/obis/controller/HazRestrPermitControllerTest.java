package com.nscorp.obis.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nscorp.obis.exception.RecordAlreadyExistsException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.nscorp.obis.domain.HazRestrPermit;
import com.nscorp.obis.dto.HazRestrPermitDTO;
import com.nscorp.obis.dto.mapper.HazRestrPermitMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.HazRestrPermitService;

public class HazRestrPermitControllerTest {

    @Mock
    HazRestrPermitMapper hazRestrPermitMapper;
    @Mock
    HazRestrPermitService hazRestrPermitService;
    @InjectMocks
    HazRestrPermitController hazRestrPermitController;

    HazRestrPermitDTO hazRestrPermitDto;
    List<HazRestrPermitDTO> hazRestrPermitDtoList;
    HazRestrPermit hazRestrPermit;
    List<HazRestrPermit> hazRestrPermitList;
    Map<String, String> header;

    @SuppressWarnings("deprecation")
    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        hazRestrPermit = new HazRestrPermit();
        hazRestrPermitDto = new HazRestrPermitDTO();
        hazRestrPermitDtoList = new ArrayList<>();
        hazRestrPermitList = new ArrayList<>();

        hazRestrPermit.setUnCd("ABC");
        hazRestrPermit.setPermitNr("123");
        hazRestrPermit.setCustomerId(1234L);

        hazRestrPermitDto.setUnCd("ABC");
        hazRestrPermitDto.setPermitNr("123");
        hazRestrPermitDto.setCustomerId(1234L);
        hazRestrPermitDtoList.add(hazRestrPermitDto);
        header = new HashMap<String, String>();
        header.put("userid", "Test");
        header.put("extensionschema", "Test");
    }

    @AfterEach
    void tearDown() throws Exception {
        hazRestrPermit = null;
        hazRestrPermitDto = null;
        hazRestrPermitList = null;
        hazRestrPermitDtoList = null;

    }

    @Test
    void testGetHazRestrPermit() {
        when(hazRestrPermitService.getHazRestrPermit()).thenReturn(hazRestrPermitList);
        ResponseEntity<APIResponse<List<HazRestrPermitDTO>>> hazPermit = hazRestrPermitController.getHazRestrPermit();
        assertEquals(hazPermit.getStatusCodeValue(), 200);
    }

    @Test
    void testHazRestrPermitNoRecordsFoundException() {
        when(hazRestrPermitService.getHazRestrPermit()).thenThrow(new NoRecordsFoundException());
        ResponseEntity<APIResponse<List<HazRestrPermitDTO>>> hazPermit = hazRestrPermitController.getHazRestrPermit();
        assertEquals(hazPermit.getStatusCodeValue(), 404);
    }

    @Test
    void testHazRestrPermitException() {
        when(hazRestrPermitService.getHazRestrPermit()).thenThrow(new RuntimeException());
        ResponseEntity<APIResponse<List<HazRestrPermitDTO>>> hazPermit = hazRestrPermitController.getHazRestrPermit();
        assertEquals(hazPermit.getStatusCodeValue(), 500);
    }

    @Test
    void testDeleteHazRestrPermit() {
        when(hazRestrPermitService.deleteHazRestrPermit(Mockito.any()))
                .thenReturn(hazRestrPermit);
        ResponseEntity<List<APIResponse<HazRestrPermitDTO>>> deleteList = hazRestrPermitController
                .deleteHazRestrPermit(hazRestrPermitDtoList);
        assertEquals(200, deleteList.getStatusCodeValue());
    }

    @Test
    void testDeleteHazRestrPermitException() {
        when(hazRestrPermitService.deleteHazRestrPermit(Mockito.any()))
                .thenThrow(new RuntimeException());
        ResponseEntity<List<APIResponse<HazRestrPermitDTO>>> deleteList = hazRestrPermitController
                .deleteHazRestrPermit(hazRestrPermitDtoList);
        assertNotNull(deleteList.getStatusCodeValue());
    }

    @Test
    void testDeleteHazRestrPermitNoRecordFoundException() {
        when(hazRestrPermitService.deleteHazRestrPermit(Mockito.any()))
                .thenThrow(new NoRecordsFoundException());
        ResponseEntity<List<APIResponse<HazRestrPermitDTO>>> deleteList = hazRestrPermitController
                .deleteHazRestrPermit(hazRestrPermitDtoList);
        assertNotNull(deleteList.getStatusCodeValue());
    }

    @Test
    void testAddHazRestrPermit() {
        when(hazRestrPermitService.addHazRestrPermit(Mockito.any(), Mockito.any())).thenReturn(hazRestrPermit);
        ResponseEntity<APIResponse<HazRestrPermitDTO>> addHaz = hazRestrPermitController.addHazRestrPermit(hazRestrPermitDto, header);
        assertNotNull(addHaz);
    }


    @Test
    void testAddHazRestrPermitException() {
        when(hazRestrPermitService.addHazRestrPermit(Mockito.any(), Mockito.any())).thenThrow(new RuntimeException());
        ResponseEntity<APIResponse<HazRestrPermitDTO>> addHaz = hazRestrPermitController.addHazRestrPermit(hazRestrPermitDto, header);
        assertEquals(addHaz.getStatusCodeValue(), 500);
    }


    @Test
    void testAddHazRestrPermitNoRecordsFoundException() {
        when(hazRestrPermitService.addHazRestrPermit(Mockito.any(), Mockito.any())).thenThrow(new NoRecordsFoundException());
        ResponseEntity<APIResponse<HazRestrPermitDTO>> addHaz = hazRestrPermitController.addHazRestrPermit(hazRestrPermitDto, header);
        assertEquals(addHaz.getStatusCodeValue(), 404);
    }


    @Test
    void testAddHazRestrPermitRecordAlreadyExistsException() {
        when(hazRestrPermitService.addHazRestrPermit(Mockito.any(), Mockito.any())).thenThrow(new RecordAlreadyExistsException());
        ResponseEntity<APIResponse<HazRestrPermitDTO>> addHaz = hazRestrPermitController.addHazRestrPermit(hazRestrPermitDto, header);
        assertEquals(addHaz.getStatusCodeValue(), 208);
    }

    @Test
    void testUpdateHazRestrPermit() {
        when(hazRestrPermitService.updateHazRestrPermit(Mockito.any(), Mockito.any())).thenReturn(hazRestrPermit);
        ResponseEntity<APIResponse<HazRestrPermitDTO>> response = hazRestrPermitController
                .updateHazRestrPermit(hazRestrPermitDto, header);
        assertEquals(200, response.getStatusCodeValue());

    }

    @Test
    void testUpdateHazRestrPermitNoRecordsFoundException() {
        when(hazRestrPermitService.updateHazRestrPermit(Mockito.any(), Mockito.any())).thenThrow(new NoRecordsFoundException());
        ResponseEntity<APIResponse<HazRestrPermitDTO>> response = hazRestrPermitController
                .updateHazRestrPermit(hazRestrPermitDto, header);
        assertEquals(404, response.getStatusCodeValue());

    }

    @Test
    void testUpdateHazRestrPermitRuntimeException() {
        when(hazRestrPermitService.updateHazRestrPermit(Mockito.any(), Mockito.any())).thenThrow(new RuntimeException());
        ResponseEntity<APIResponse<HazRestrPermitDTO>> response = hazRestrPermitController
                .updateHazRestrPermit(hazRestrPermitDto, header);
        assertEquals(500, response.getStatusCodeValue());

    }
}
