package com.nscorp.obis.controller;

import com.nscorp.obis.domain.DamageCompLoc;
import com.nscorp.obis.dto.DamageCompLocDTO;
import com.nscorp.obis.dto.mapper.DamageCompLocMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.DamageComponentLocationService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class DamageComponentLocationControllerTest {

    @Mock
    DamageComponentLocationService damageCompLocService;

    @Mock
    DamageCompLocMapper damageCompLocMapper;

    @InjectMocks
    DamageComponentLocationController damageComponentLocationController;

    DamageCompLoc damageCompLoc;
    DamageCompLocDTO damageCompLocDTO;
    List<DamageCompLoc> damageCompLocList;
    List<DamageCompLocDTO> damageCompLocDTOList;
    Map<String,String> header;

    Integer jobCode;
    String areaCode;

    @SuppressWarnings("deprecation")
    @BeforeEach
    void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);
        damageCompLoc = new DamageCompLoc();
        damageCompLoc.setCompLocCode("TES");
        damageCompLoc.setUversion("!");
        damageCompLoc.setCreateUserId("Test");
        damageCompLoc.setUpdateUserId("Test");

        damageCompLocList = new ArrayList<>();

        damageCompLocDTO = new DamageCompLocDTO();
        damageCompLocDTO.setJobCode(01);
        damageCompLocDTO.setCompLocCode("TES");
        damageCompLocDTO.setAreaCd("T");
        damageCompLocDTO.setUversion("!");
        damageCompLocDTO.setCreateUserId("Test");
        damageCompLocDTO.setUpdateUserId("Test");

        damageCompLocDTOList = new ArrayList<>();

        damageCompLocList.add(damageCompLoc);
        damageCompLocDTOList.add(damageCompLocDTO);

        header = new HashMap<String, String>();
        header.put("userid", "Test");
        header.put("extensionschema", "Test");

        jobCode = 01;
        areaCode="T";
    }

    @AfterEach
    void tearDown() throws Exception{
        damageCompLocDTOList = null;
        damageCompLocList = null;
        damageCompLoc = null;
        damageCompLocDTO = null;
    }

   @Test
    void testGetDamageComponentLocation() {
        when(damageCompLocService.getDamageComponentLocation(jobCode, areaCode)).thenReturn(damageCompLocList);
        ResponseEntity<APIResponse<List<DamageCompLocDTO>>> getDamageComp = damageComponentLocationController.getDamageComponentLocation(jobCode, areaCode);
        assertEquals(getDamageComp.getStatusCodeValue(),200);
    }
   @Test
   void testGetDamageComponentLocationNoRecordsFoundException() {
	   when(damageCompLocService.getDamageComponentLocation(jobCode, areaCode)).thenThrow(new NoRecordsFoundException());
       ResponseEntity<APIResponse<List<DamageCompLocDTO>>> exception1 = damageComponentLocationController.getDamageComponentLocation(jobCode, areaCode);
       assertEquals(exception1.getStatusCodeValue(),404);
   }
    @Test
    void testGetDamageComponentLocationException() {
        
        when(damageCompLocService.getDamageComponentLocation(jobCode, areaCode)).thenThrow(new RuntimeException());
        ResponseEntity<APIResponse<List<DamageCompLocDTO>>> exception = damageComponentLocationController.getDamageComponentLocation(jobCode, areaCode);
        assertEquals(exception.getStatusCodeValue(),500);
    }

    @Test
    void testDeleteDamageComponent() {
        ResponseEntity<List<APIResponse<DamageCompLocDTO>>> deleteList = damageComponentLocationController.deleteDamageComponent(damageCompLocDTOList);
        assertEquals(deleteList.getStatusCodeValue(),200);
    }
    @Test
    void testDeleteDamageComponentException() {
        ResponseEntity<List<APIResponse<DamageCompLocDTO>>> deleteList = damageComponentLocationController.deleteDamageComponent(null);
        assertEquals(deleteList.getStatusCodeValue(),500);
    }

    @Test
    void testAddDamageCompLocation() throws SQLException {
        when(damageCompLocService.addDamageCompLocation(Mockito.any(), Mockito.any()))
                .thenReturn(damageCompLocDTO);
        ResponseEntity<APIResponse<DamageCompLocDTO>> response = damageComponentLocationController
                .addDamageCompLocation(Mockito.any(), Mockito.any());
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);

    }

    @Test
    void testUpdateDamageCompLocation() throws SQLException {
        when(damageCompLocService.updateDamageCompLocation(Mockito.any(), Mockito.any()))
                .thenReturn(damageCompLocDTO);
        ResponseEntity<APIResponse<DamageCompLocDTO>> response = damageComponentLocationController
                .updateDamageCompLocation(damageCompLocDTO, header);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);

    }
    @Test
    void testNoRecordFoundExceptionForAdd() throws SQLException{
        when(damageCompLocService.addDamageCompLocation(Mockito.any(), Mockito.any()))
                .thenThrow(NoRecordsFoundException.class);
        ResponseEntity<APIResponse<DamageCompLocDTO>> response = damageComponentLocationController
                .addDamageCompLocation(Mockito.any(), Mockito.any());
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);

    }
    @Test
    void testRecordAlreadyExistExceptionForAdd() throws SQLException{
        when(damageCompLocService.addDamageCompLocation(Mockito.any(), Mockito.any()))
                .thenThrow(RecordAlreadyExistsException.class);
        ResponseEntity<APIResponse<DamageCompLocDTO>> response = damageComponentLocationController
                .addDamageCompLocation(Mockito.any(), Mockito.any());
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);

    }
    @Test
    void testInternalServerError() throws SQLException {
        when(damageCompLocService.addDamageCompLocation(Mockito.any(), Mockito.any()))
                .thenThrow(RuntimeException.class);
        ResponseEntity<APIResponse<DamageCompLocDTO>> response = damageComponentLocationController
                .addDamageCompLocation(Mockito.any(), Mockito.any());
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);

        when(damageCompLocService.updateDamageCompLocation(Mockito.any(), Mockito.any()))
                .thenThrow(RuntimeException.class);
        ResponseEntity<APIResponse<DamageCompLocDTO>> response2 = damageComponentLocationController
                .updateDamageCompLocation(Mockito.any(), Mockito.any());
        Assertions.assertEquals(response2.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void testNoRecordFoundExceptionForUpdate() throws SQLException{
        when(damageCompLocService.updateDamageCompLocation(Mockito.any(), Mockito.any()))
                .thenThrow(NoRecordsFoundException.class);
        ResponseEntity<APIResponse<DamageCompLocDTO>> response = damageComponentLocationController
                .updateDamageCompLocation(Mockito.any(), Mockito.any());
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);

    }
}
