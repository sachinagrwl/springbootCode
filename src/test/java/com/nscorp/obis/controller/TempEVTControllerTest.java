package com.nscorp.obis.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.nscorp.obis.domain.TempEVT;
import com.nscorp.obis.dto.TempEVTDTO;
import com.nscorp.obis.dto.mapper.TempEVTMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.TempEVTService;

class TempEVTControllerTest {
    @Mock
    TempEVTService tempEVTService;
    @InjectMocks
    TempEVTController tempEVTController;
    @Mock
    TempEVTMapper tempEVTMapper;
    TempEVT tempEvt;
    TempEVTDTO tempEvtDTO;
    Map<String, String> header;
    
	@SuppressWarnings("deprecation")
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        tempEvt = new TempEVT();
        tempEvtDTO = new TempEVTDTO();
        tempEvt.setEvtlogId(12345677L);
        tempEvt.setEvtCd("NTFY");
        tempEvt.setQueStat("R");
        tempEvt.setTermId(12345567L);
        tempEvt.setSvcId(12345567L);
        tempEvt.setEquipId("0");
        tempEvt.setEquipNbr(BigDecimal.valueOf(123456));
        tempEvt.setEquipTp("T");
        tempEvt.setEquipInit("ABCD");

        header = new HashMap<String, String>();
        header.put("userid", "Test");
        header.put("extensionschema", "Test");
    }
    @AfterEach
    void tearDown() throws Exception {
        tempEvt = null;
        tempEvtDTO = null;
    }
    @Test
    void testAddTempEVT() throws Exception {
        when(tempEVTMapper.tempEVTDTOToTempEVT(Mockito.any())).thenReturn(tempEvt);
        when(tempEVTService.addTempEVT(Mockito.any(), Mockito.any())).thenReturn(tempEvt);
        when(tempEVTMapper.tempEVTToTempEVTDTO(Mockito.any())).thenReturn(tempEvtDTO);
        ResponseEntity<APIResponse<TempEVTDTO>> result = tempEVTController.addTempEVT(tempEvtDTO, header);
        assertNotNull(result.getBody());
    }
    @Test
    void testAddTempEVTNoRecordFoundException() throws Exception {
        when(tempEVTService.addTempEVT(Mockito.any(), Mockito.any())).thenThrow(new NoRecordsFoundException());
        ResponseEntity<APIResponse<TempEVTDTO>> result = tempEVTController.addTempEVT(Mockito.any(), Mockito.any());
        assertEquals(result.getStatusCodeValue(), 404);
    }
    @Test
    void testAddTempEVTRecordAlreadyExistsException() throws Exception {
        when(tempEVTService.addTempEVT(Mockito.any(), Mockito.any())).thenThrow(new RecordAlreadyExistsException());
        ResponseEntity<APIResponse<TempEVTDTO>> result = tempEVTController.addTempEVT(Mockito.any(), Mockito.any());
        assertEquals(result.getStatusCodeValue(), 208);
    }
    @Test
    void testAddTempEVTException() throws Exception {
        when(tempEVTService.addTempEVT(Mockito.any(), Mockito.any())).thenThrow(new RuntimeException());
        ResponseEntity<APIResponse<TempEVTDTO>> result = tempEVTController.addTempEVT(Mockito.any(), Mockito.any());
        assertEquals(result.getStatusCodeValue(), 500);
    }
}
