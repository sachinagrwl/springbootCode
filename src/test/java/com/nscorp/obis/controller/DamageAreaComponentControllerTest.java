package com.nscorp.obis.controller;

import com.nscorp.obis.domain.DamageAreaComp;
import com.nscorp.obis.dto.DamageAreaComponentDTO;
import com.nscorp.obis.dto.DamageComponentSizeDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.DamageAreaComponentService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


public class DamageAreaComponentControllerTest {
    @Mock
    DamageAreaComponentService damageAreaComponentService;
    @InjectMocks
    DamageAreaComponentController damageAreaComponentController;

    DamageAreaComp damageAreaComponent;
    DamageAreaComponentDTO damageAreaComponentDTO;

    List<DamageAreaComp> damageAreaComponentList;
    List<DamageAreaComponentDTO> damageAreaComponentDTOList;
    String areaCd = "A";
    Integer jobCode = 1100;

    Map<String, String> headers;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
    }

    @AfterAll
    static void tearDownAfterClass() throws Exception {
    }

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        damageAreaComponent = new DamageAreaComp();
        damageAreaComponent.setJobCode(1100);
        damageAreaComponent.setAreaCd("A");
        damageAreaComponent.setOrderCode("A");
        damageAreaComponent.setUversion("!");
        damageAreaComponent.setCreateUserId("Test");
        damageAreaComponent.setUpdateUserId("Test");
        damageAreaComponentDTO = new DamageAreaComponentDTO();
        damageAreaComponentDTO.setJobCode(1100);
        damageAreaComponentDTO.setAreaCd("A");
        damageAreaComponentDTO.setOrderCode("A");
        damageAreaComponentDTO.setUversion("!");
        damageAreaComponentDTO.setCreateUserId("Test");
        damageAreaComponentDTO.setUpdateUserId("Test");
        damageAreaComponentList = new ArrayList<>();
        damageAreaComponentDTOList = new ArrayList<>();
        damageAreaComponentList.add(damageAreaComponent);
        damageAreaComponentDTOList.add(damageAreaComponentDTO);
        headers = new HashMap<>();
        headers.put("userid", "test");
        headers.put("extensionschema", "test");
    }

    @AfterEach
    void tearDown() throws Exception {
        damageAreaComponent = null;
        damageAreaComponentDTO = null;
        damageAreaComponentDTOList =null;
        damageAreaComponentList = null;
    }

    @Test
    void testGetDamageAreaComponent() {
        when(damageAreaComponentService.fetchDamageComponentSize(jobCode, areaCd)).thenReturn(damageAreaComponentDTOList);
        ResponseEntity<APIResponse<List<DamageAreaComponentDTO>>> getDamageComp = damageAreaComponentController.getAllDamageAreaComponents(jobCode, areaCd);
        assertEquals(getDamageComp.getStatusCodeValue(), 200);
    }

    @Test
    void testGetDamageAreaComponentNoRecordsFoundException() {
        when(damageAreaComponentService.fetchDamageComponentSize(jobCode, areaCd)).thenThrow(new NoRecordsFoundException());
        ResponseEntity<APIResponse<List<DamageAreaComponentDTO>>> exception1 = damageAreaComponentController.getAllDamageAreaComponents(jobCode, areaCd);
        assertEquals(exception1.getStatusCodeValue(), 404);
    }

    @Test
    void testGetDamageAreaComponentException() {
        when(damageAreaComponentService.fetchDamageComponentSize(jobCode, areaCd)).thenThrow(new RuntimeException());
        ResponseEntity<APIResponse<List<DamageAreaComponentDTO>>> exception1 = damageAreaComponentController.getAllDamageAreaComponents(jobCode, areaCd);
        assertEquals(exception1.getStatusCodeValue(), 500);
    }

    @Test
    void testDeleteDamageAreaComponent() {

        ResponseEntity<List<APIResponse<DamageAreaComponentDTO>>> deleteList = damageAreaComponentController.deleteDamageAreaComponent(damageAreaComponentDTOList);
        assertEquals(deleteList.getStatusCodeValue(),200);
    }
    @Test
    void testDeleteDamageAreaComponentException() {
        ResponseEntity<List<APIResponse<DamageAreaComponentDTO>>> emptyList = damageAreaComponentController.deleteDamageAreaComponent(new ArrayList<>());
        assertEquals(emptyList.getStatusCodeValue(),500);

        doThrow(new RuntimeException()).when(damageAreaComponentService).deleteDamageAreaComponent(Mockito.any());
        ResponseEntity<List<APIResponse<DamageAreaComponentDTO>>> exception1 = damageAreaComponentController.deleteDamageAreaComponent(damageAreaComponentDTOList);
        assertEquals(exception1.getStatusCodeValue(), 500);

    }

    @Test
    void testAddDamageAreaComponent() throws SQLException {
        when(damageAreaComponentService.addDamageAreaComponent(Mockito.any(), Mockito.any())).thenReturn(damageAreaComponentDTO);
        ResponseEntity<APIResponse<DamageAreaComponentDTO>> response = damageAreaComponentController.addDamageAreaComponent(Mockito.any(), Mockito.any());
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void testUpdateDamageAreaComponent() throws SQLException {
        when(damageAreaComponentService.updateDamageAreaComponent(Mockito.any(), Mockito.any())).thenReturn(damageAreaComponentDTO);
        ResponseEntity<APIResponse<DamageAreaComponentDTO>> response = damageAreaComponentController.updateDamageAreaComponent(Mockito.any(), Mockito.any());
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);

    }

    @Test
    void testNoRecordFoundExceptionForAdd() throws SQLException {
        when(damageAreaComponentService.addDamageAreaComponent(Mockito.any(), Mockito.any())).thenThrow(NoRecordsFoundException.class);
        ResponseEntity<APIResponse<DamageAreaComponentDTO>> response = damageAreaComponentController.addDamageAreaComponent(Mockito.any(), Mockito.any());
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);

    }

    @Test
    void testRecordAlreadyExistExceptionForAdd() throws SQLException {
        when(damageAreaComponentService.addDamageAreaComponent(Mockito.any(), Mockito.any())).thenThrow(RecordAlreadyExistsException.class);
        ResponseEntity<APIResponse<DamageAreaComponentDTO>> response = damageAreaComponentController.addDamageAreaComponent(Mockito.any(), Mockito.any());
        assertEquals(response.getStatusCodeValue(), 500);

    }

    @Test
    void testInternalServerError() throws SQLException {
        when(damageAreaComponentService.addDamageAreaComponent(Mockito.any(), Mockito.any())).thenThrow(RuntimeException.class);
        ResponseEntity<APIResponse<DamageAreaComponentDTO>> response = damageAreaComponentController.addDamageAreaComponent(Mockito.any(), Mockito.any());
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);

        when(damageAreaComponentService.updateDamageAreaComponent(Mockito.any(), Mockito.any())).thenThrow(RuntimeException.class);
        ResponseEntity<APIResponse<DamageAreaComponentDTO>> response2 = damageAreaComponentController.updateDamageAreaComponent(Mockito.any(), Mockito.any());
        Assertions.assertEquals(response2.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void testNoRecordFoundExceptionForUpdate() throws SQLException {
        when(damageAreaComponentService.updateDamageAreaComponent(Mockito.any(), Mockito.any())).thenThrow(NoRecordsFoundException.class);
        ResponseEntity<APIResponse<DamageAreaComponentDTO>> response2 = damageAreaComponentController.updateDamageAreaComponent(Mockito.any(), Mockito.any());
        Assertions.assertEquals(response2.getStatusCode(), HttpStatus.NOT_FOUND);

    }

}
