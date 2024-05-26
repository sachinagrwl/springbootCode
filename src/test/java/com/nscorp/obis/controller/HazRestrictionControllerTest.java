package com.nscorp.obis.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nscorp.obis.dto.CarEmbargoDTO;
import com.nscorp.obis.exception.InvalidDataException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.nscorp.obis.domain.HazRestriction;
import com.nscorp.obis.dto.HazRestrictionDTO;
import com.nscorp.obis.dto.mapper.HazRestrictionMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.repository.HazRestrictionRepository;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.HazRestrictionService;

public class HazRestrictionControllerTest {

    @Mock
    private HazRestrictionService hazRestrictionService;
    @Mock
    private HazRestrictionRepository hazRestrictionRepository;

    @Mock
    private HazRestrictionMapper hazRestrictionMapper;
    @InjectMocks
    private HazRestrictionController hazRestrictionController;

    HazRestriction hazRestriction;
    HazRestrictionDTO hazRestrictionDTO;
    List<HazRestriction> hazRestrictionList;
    List<HazRestrictionDTO> hazRestrictionDTOList;
    Map<String,String> header;

    String unCd;

    @BeforeEach
    void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);
        hazRestrictionDTOList=new ArrayList<>();
        hazRestriction = new HazRestriction();
        hazRestriction.setUnCd("BN123");
        hazRestriction.setRestoreClass("A");

        hazRestrictionDTO = new HazRestrictionDTO();
        hazRestrictionDTO.setUnCd("BN123");
        hazRestrictionDTO.setRestoreClass("A");
        hazRestrictionDTOList.add(hazRestrictionDTO);
        header = new HashMap<String, String>();
        header.put("userid", "Test");
        header.put("extensionschema", "Test");
    }

    @AfterEach
    void tearDown() throws Exception{
        hazRestrictionDTOList = null;
        hazRestrictionList = null;
        hazRestriction = null;
        hazRestrictionDTO = null;
    }

    @Test
    void testGetAllHazRestrictionException() {

        when(hazRestrictionService.insertHazRestriction(Mockito.any(),Mockito.any())).thenThrow(new RuntimeException());
        when(hazRestrictionService.updateHazRestriction(Mockito.any(),Mockito.any())).thenThrow(new RuntimeException());

        ResponseEntity<APIResponse<HazRestrictionDTO>> addHaz = hazRestrictionController.insertHazRestriction(Mockito.any(),Mockito.any());
        ResponseEntity<APIResponse<HazRestrictionDTO>> updateHaz = hazRestrictionController.updateHazRestriction(Mockito.any(),Mockito.any());

        assertEquals(addHaz.getStatusCodeValue(),500);
        assertEquals(updateHaz.getStatusCodeValue(),500);

    }

    @Test
    void testGetAllHazRestrictionNoRecordsFoundException() {

        when(hazRestrictionService.insertHazRestriction(Mockito.any(),Mockito.any())).thenThrow(new NoRecordsFoundException());
        when(hazRestrictionService.updateHazRestriction(Mockito.any(),Mockito.any())).thenThrow(new NoRecordsFoundException());

        ResponseEntity<APIResponse<HazRestrictionDTO>> addHaz = hazRestrictionController.insertHazRestriction(Mockito.any(),Mockito.any());
        ResponseEntity<APIResponse<HazRestrictionDTO>> updateHaz = hazRestrictionController. updateHazRestriction(Mockito.any(),Mockito.any());

        assertEquals(addHaz.getStatusCodeValue(),404);
        assertEquals(updateHaz.getStatusCodeValue(),404);

    }

    @Test
    void testGetAllHazRestrictionRecordAlreadyExistsException() {
        when(hazRestrictionService.insertHazRestriction(Mockito.any(),Mockito.any())).thenThrow(new RecordAlreadyExistsException());
        ResponseEntity<APIResponse<HazRestrictionDTO>> addHaz = hazRestrictionController.insertHazRestriction(Mockito.any(),Mockito.any());
        assertEquals(addHaz.getStatusCodeValue(),208);
    }

    @Test
    void testInsertHazRestriction() {
        when(hazRestrictionMapper.hazRestrictionDTOToHazRestriction(Mockito.any())).thenReturn(hazRestriction);
        when(hazRestrictionService.insertHazRestriction(Mockito.any(), Mockito.any())).thenReturn(hazRestriction);
        when(hazRestrictionMapper.hazRestrictionToHazRestrictionDTO(Mockito.any())).thenReturn(hazRestrictionDTO);
        ResponseEntity<APIResponse<HazRestrictionDTO>> addHaz = hazRestrictionController.insertHazRestriction(Mockito.any(), Mockito.any());
    }

    @Test
    void testGetHazRestriction() {
        when(hazRestrictionService.getHazRestriction(unCd)).thenReturn(hazRestrictionList);
        ResponseEntity<APIResponse<List<HazRestrictionDTO>>> getHR = hazRestrictionController.getHazRestriction(unCd);
        assertEquals(getHR.getStatusCodeValue(), 200);
    }

    @Test
    void testGetHazRestrictionNoRecordsFoundException() {
        when(hazRestrictionService.getHazRestriction(unCd)).thenThrow(new NoRecordsFoundException());
        ResponseEntity<APIResponse<List<HazRestrictionDTO>>> hazRestrictionList = hazRestrictionController.getHazRestriction(unCd);
        assertEquals(hazRestrictionList.getStatusCodeValue(),404);
    }

    @Test
    void testGetHazRestrictionException() {

        when(hazRestrictionService.getHazRestriction(unCd)).thenThrow(new RuntimeException());
        ResponseEntity<APIResponse<List<HazRestrictionDTO>>> hazRestrictionList = hazRestrictionController.getHazRestriction(unCd);
        assertEquals(hazRestrictionList.getStatusCodeValue(), 500);
    }

    @Test
    void testDeleteHazRestriction() {
        when(hazRestrictionMapper.hazRestrictionDTOToHazRestriction(Mockito.any())).thenReturn(hazRestriction);
        when(hazRestrictionService.deleteHazRestriction(Mockito.any())).thenReturn(hazRestriction);
        when(hazRestrictionMapper.hazRestrictionToHazRestrictionDTO(Mockito.any())).thenReturn(hazRestrictionDTO);
        ResponseEntity<List<APIResponse<HazRestrictionDTO>>> deleteList1 = hazRestrictionController.deleteHazRestricition(hazRestrictionDTOList);
        assertEquals(deleteList1.getStatusCodeValue(), 200);

    }

    @Test
    void testDeleteHazRestrictionException() {
        when(hazRestrictionService.deleteHazRestriction(Mockito.any())).thenThrow(new RuntimeException());
        ResponseEntity<List<APIResponse<HazRestrictionDTO>>> deleteList = hazRestrictionController.deleteHazRestricition(hazRestrictionDTOList);
        assertEquals(deleteList.getStatusCodeValue(), 500);
    }

 @Test
    void testDeleteHazRestrictionNoRecordException() {
        when(hazRestrictionService.deleteHazRestriction(Mockito.any())).thenThrow(new NoRecordsFoundException());
        ResponseEntity<List<APIResponse<HazRestrictionDTO>>> deleteList = hazRestrictionController.deleteHazRestricition(hazRestrictionDTOList);
        assertEquals(deleteList.getStatusCodeValue(), 500);
    }

    @Test
    void testUpdateHazRestriction() {
        when(hazRestrictionMapper.hazRestrictionDTOToHazRestriction(Mockito.any())).thenReturn(hazRestriction);
        when(hazRestrictionService.updateHazRestriction(Mockito.any(), Mockito.any())).thenReturn(hazRestriction);
        when(hazRestrictionMapper.hazRestrictionToHazRestrictionDTO(Mockito.any())).thenReturn(hazRestrictionDTO);
        ResponseEntity<APIResponse<HazRestrictionDTO>> updateHaz = hazRestrictionController.updateHazRestriction(Mockito.any(),Mockito.any());

        assertNotNull(updateHaz.getBody());
    }

    @Test
    void testHazRestrictionNullPointerException() {
        when(hazRestrictionService.updateHazRestriction(Mockito.any(),Mockito.any())).thenThrow(new NullPointerException());
        ResponseEntity<APIResponse<HazRestrictionDTO>> updateHaz = hazRestrictionController.updateHazRestriction(Mockito.any(),Mockito.any());
        assertEquals(updateHaz.getStatusCodeValue(),400);
    }

    @Test
    void testHazRestrictionInvalidDataException() {
        when(hazRestrictionService.updateHazRestriction(Mockito.any(),Mockito.any())).thenThrow(new InvalidDataException());
        ResponseEntity<APIResponse<HazRestrictionDTO>> updateHaz = hazRestrictionController.updateHazRestriction(Mockito.any(),Mockito.any());
        assertEquals(updateHaz.getStatusCodeValue(),406);
    }

}
