package com.nscorp.obis.controller;

import com.nscorp.obis.domain.DVIRCodes;
import com.nscorp.obis.dto.DVIRCodesDTO;
import com.nscorp.obis.dto.DrayageScacDTO;
import com.nscorp.obis.dto.mapper.DVIRCodesMapper;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.DVIRCodesService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class DVIRCodesControllerTest {

    @Mock
    DVIRCodesService dvirCodesService;
    @InjectMocks
    DVIRCodesController dvirCodesController;
    @Mock
    DVIRCodesMapper dvirCodesMapper;
    DVIRCodes dvirCodes;
    List<DVIRCodes> dvirCodesList;
    DVIRCodesDTO dvirCodesDTO;
    List<DVIRCodesDTO> dvirCodesDTOList;

    Map<String, String> header;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dvirCodes = new DVIRCodes();

        dvirCodes.setDvirCd("abcd");
        dvirCodes.setDvirDesc(null);
        dvirCodes.setDvirHHDesc(null);
        dvirCodes.setDisplayCd(null);
        dvirCodes.setUversion("a");

        dvirCodesList = new ArrayList<>();
        dvirCodesList.add(dvirCodes);
        dvirCodesDTO = new DVIRCodesDTO();
        dvirCodesDTO = DVIRCodesMapper.INSTANCE.DvirCodesToDvirCodesDTO(dvirCodes);
        dvirCodesDTOList = new ArrayList<>();
        dvirCodesDTOList.add(dvirCodesDTO);
        header = new HashMap<String, String>();
        header.put("userid", "Test");
        header.put("extensionschema", "Test");
    }
    @AfterEach
    void tearDown() throws Exception {
        dvirCodes = null;
        dvirCodesList = null;
        dvirCodesDTO = null;
        dvirCodesDTOList = null;
    }

    @Test
    void testGetDVIRCodes() {
        when(dvirCodesService.getAllDVIRCodes()).thenReturn(dvirCodesList);
        ResponseEntity<APIResponse<List<DVIRCodesDTO>>> result = dvirCodesController.getDVIRCodes();
    }
    @Test
    void testDVIRCodesException() {
        when(dvirCodesService.getAllDVIRCodes()).thenThrow(new RuntimeException());
        ResponseEntity<APIResponse<List<DVIRCodesDTO>>> result = dvirCodesController.getDVIRCodes();
    }

    @Test
    void testAddDVIR() {
        //drayageScacDTO.setPhoneNumber("1001018999");
        when(dvirCodesMapper.DvirCodesToDvirCodesDTO(Mockito.any())).thenReturn(dvirCodesDTO);
        when(dvirCodesMapper.DvirCodesDtoToDvirCodes(Mockito.any())).thenReturn(dvirCodes);
        when(dvirCodesService.addDvirCodes(Mockito.any(), Mockito.any())).thenReturn(dvirCodes);
        ResponseEntity<APIResponse<DVIRCodesDTO>> addedCustomer = dvirCodesController.addDVIRCodes(dvirCodesDTO, header);
        assertEquals(addedCustomer.getStatusCodeValue(), 200);
    }
    @Test
    void testDVIRCodesNoRecordFoundException() {
        when(dvirCodesService.getAllDVIRCodes()).thenThrow(new NoRecordsFoundException());
        ResponseEntity<APIResponse<List<DVIRCodesDTO>>> result = dvirCodesController.getDVIRCodes();
    }

    @Test
    void testDeleteDVIRCodes() {
        ResponseEntity<List<APIResponse<DVIRCodesDTO>>> result = dvirCodesController.deleteDVIRCodes(dvirCodesDTOList);
        result = dvirCodesController.deleteDVIRCodes(null);
        result = dvirCodesController.deleteDVIRCodes(Collections.emptyList());
    }
    
    @Test
    void testupdateDVIRCodes() {
        when(dvirCodesService.updateDvirCodes(Mockito.any(), Mockito.any())).thenReturn(dvirCodes);
        ResponseEntity<APIResponse<DVIRCodesDTO>> result = dvirCodesController.updateDVIRCodes(dvirCodesDTO, header);
    }
    @Test
    void testupdateCodesException() {
        when(dvirCodesService.updateDvirCodes(Mockito.any(), Mockito.any())).thenThrow(new RuntimeException());
        ResponseEntity<APIResponse<DVIRCodesDTO>> result = dvirCodesController.updateDVIRCodes(dvirCodesDTO, header);
    }
    
    @Test
    void testupdateCodesNoRecordException() {
        when(dvirCodesService.updateDvirCodes(Mockito.any(), Mockito.any())).thenThrow(new NoRecordsFoundException());
        ResponseEntity<APIResponse<DVIRCodesDTO>> result = dvirCodesController.updateDVIRCodes(dvirCodesDTO, header);
    }
    
    @Test
    void testupdateCodesRecordNotAddedException() {
        when(dvirCodesService.updateDvirCodes(Mockito.any(), Mockito.any())).thenThrow(new RecordNotAddedException());
        ResponseEntity<APIResponse<DVIRCodesDTO>> result = dvirCodesController.updateDVIRCodes(dvirCodesDTO, header);
    }
    
    @Test
    void testupdateCodesAlreadyExistsException() {
        when(dvirCodesService.updateDvirCodes(Mockito.any(), Mockito.any())).thenThrow(new RecordAlreadyExistsException());
        ResponseEntity<APIResponse<DVIRCodesDTO>> result = dvirCodesController.updateDVIRCodes(dvirCodesDTO, header);
    }

    @Test
    void testNoRecordsAddedException(){
        when(dvirCodesService.addDvirCodes(Mockito.any(),Mockito.any())).thenThrow(RecordNotAddedException.class);
        ResponseEntity<APIResponse<DVIRCodesDTO>> addResponseEntity = dvirCodesController.addDVIRCodes(dvirCodesDTO, header);
        assertEquals(addResponseEntity.getStatusCodeValue(), 404);

    }

    @Test
    void testNoRecordsAlreadyExistException(){
        when(dvirCodesService.addDvirCodes(Mockito.any(),Mockito.any())).thenThrow(RecordAlreadyExistsException.class);
        ResponseEntity<APIResponse<DVIRCodesDTO>> addResponseEntity = dvirCodesController.addDVIRCodes(dvirCodesDTO, header);
        assertEquals(addResponseEntity.getStatusCodeValue(), 208);

    }

    @Test
    void testInvalidDataException(){
        when(dvirCodesService.addDvirCodes(Mockito.any(),Mockito.any())).thenThrow(InvalidDataException.class);
        ResponseEntity<APIResponse<DVIRCodesDTO>> addResponseEntity = dvirCodesController.addDVIRCodes(dvirCodesDTO, header);
        assertEquals(addResponseEntity.getStatusCodeValue(), 500);

    }
    @Test
    void testException(){
        when(dvirCodesService.addDvirCodes(Mockito.any(),Mockito.any())).thenThrow(new RuntimeException());
        ResponseEntity<APIResponse<DVIRCodesDTO>> addResponseEntity = dvirCodesController.addDVIRCodes(dvirCodesDTO, header);
        assertEquals(addResponseEntity.getStatusCodeValue(), 500);

    }

}
