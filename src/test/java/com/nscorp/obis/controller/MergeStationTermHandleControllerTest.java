package com.nscorp.obis.controller;

import com.nscorp.obis.dto.MergeStationTermHandleDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.MergeStationTermHandleService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class MergeStationTermHandleControllerTest {

    @Mock
    MergeStationTermHandleService service;

    @InjectMocks
    MergeStationTermHandleController controller;

    MergeStationTermHandleDTO mergeStationTermHandleDTO;
    List<MergeStationTermHandleDTO> mergeStationTermHandleDTOS;
    Long terminalId = 219974351921L;
    Map<String, String> headers;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mergeStationTermHandleDTO = new MergeStationTermHandleDTO();
        mergeStationTermHandleDTOS = new ArrayList<>();
        mergeStationTermHandleDTOS.add(mergeStationTermHandleDTO);
        headers = new HashMap<>();
        headers.put("userid", "test");
        headers.put("extensionschema", "test");

    }

    @AfterEach
    void tearDown() {
        mergeStationTermHandleDTO = null;
        mergeStationTermHandleDTOS = null;
    }

    @Test
    void testFetchMergeStationTermHandle() {
        when(service.getMergeStationTermHandleDetails(Mockito.anyLong())).thenReturn(mergeStationTermHandleDTOS);
        ResponseEntity<APIResponse<List<MergeStationTermHandleDTO>>> response = controller
                .fetchMergeStationTermHandle(terminalId);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testInsertMergeStationTermHandle() {
        when(service.insertMergeStationTermHandle(Mockito.any(), Mockito.anyMap())).thenReturn(mergeStationTermHandleDTO);
        ResponseEntity<APIResponse<MergeStationTermHandleDTO>> response = controller
                .insertMergeStationTermHandle(mergeStationTermHandleDTO, headers);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testDeleteStationTermHandle() {
        when(service.deleteMergeStationTermHandle(Mockito.any())).thenReturn(mergeStationTermHandleDTO);
        ResponseEntity<List<APIResponse<MergeStationTermHandleDTO>>> response = controller.deleteMergeStationTerminalHandle(mergeStationTermHandleDTOS);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testNoRecordsException() {
        when(service.getMergeStationTermHandleDetails(Mockito.anyLong())).thenThrow(NoRecordsFoundException.class);
        ResponseEntity<APIResponse<List<MergeStationTermHandleDTO>>> response = controller.fetchMergeStationTermHandle(terminalId);
        assertEquals(404, response.getStatusCodeValue());
        when(service.insertMergeStationTermHandle(Mockito.any(), Mockito.anyMap())).thenThrow(NoRecordsFoundException.class);
        ResponseEntity<APIResponse<MergeStationTermHandleDTO>> response2 = controller.insertMergeStationTermHandle(mergeStationTermHandleDTO, headers);
        assertEquals(404, response2.getStatusCodeValue());
        when(service.deleteMergeStationTermHandle(Mockito.any())).thenThrow(NoRecordsFoundException.class);
        ResponseEntity<List<APIResponse<MergeStationTermHandleDTO>>> response3 = controller.deleteMergeStationTerminalHandle(mergeStationTermHandleDTOS);
        assertEquals(500, response3.getStatusCodeValue());
    }

    @Test
    void testException() {
        when(service.getMergeStationTermHandleDetails(Mockito.anyLong())).thenThrow(RuntimeException.class);
        ResponseEntity<APIResponse<List<MergeStationTermHandleDTO>>> response = controller.fetchMergeStationTermHandle(terminalId);
        assertEquals(500, response.getStatusCodeValue());
        when(service.insertMergeStationTermHandle(Mockito.any(), Mockito.anyMap())).thenThrow(RuntimeException.class);
        ResponseEntity<APIResponse<MergeStationTermHandleDTO>> response2 = controller.insertMergeStationTermHandle(mergeStationTermHandleDTO, headers);
        assertEquals(500, response2.getStatusCodeValue());
        when(service.deleteMergeStationTermHandle(Mockito.any())).thenThrow(RuntimeException.class);
        ResponseEntity<List<APIResponse<MergeStationTermHandleDTO>>> response3 = controller.deleteMergeStationTerminalHandle(mergeStationTermHandleDTOS);
        assertEquals(500, response3.getStatusCodeValue());
    }

}
