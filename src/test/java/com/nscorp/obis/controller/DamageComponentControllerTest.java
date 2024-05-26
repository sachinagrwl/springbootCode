package com.nscorp.obis.controller;

import com.nscorp.obis.domain.DamageComponent;
import com.nscorp.obis.dto.CarEmbargoDTO;
import com.nscorp.obis.dto.DamageComponentDTO;
import com.nscorp.obis.dto.EquipmentRackRangeDTO;
import com.nscorp.obis.dto.mapper.DamageComponentMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.DamageComponentService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class DamageComponentControllerTest {

    @Mock
    DamageComponentService damageComponentService;

    @Mock
    DamageComponentMapper damageComponentMapper;

    @InjectMocks
    DamageComponentController damageComponentController;

    DamageComponent damageComponent;
    DamageComponentDTO damageComponentDTO;
    List<DamageComponent> damageComponentList;
    List<DamageComponentDTO> damageComponentDTOList;
    Map<String,String> header;

    Integer jobCode;

    @SuppressWarnings("deprecation")
    @BeforeEach
    void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);
        damageComponent = new DamageComponent();
        damageComponent.setJobCode(1114);
        damageComponent.setUversion("!");
        damageComponent.setCreateUserId("Test");
        damageComponent.setUpdateUserId("Test");

        damageComponentList = new ArrayList<>();

        damageComponentDTO = new DamageComponentDTO();
        damageComponentDTO.setJobCode(1114);
        damageComponentDTO.setUversion("!");
        damageComponentDTO.setCreateUserId("Test");
        damageComponentDTO.setUpdateUserId("Test");

        damageComponentDTOList = new ArrayList<>();

        damageComponentList.add(damageComponent);
        damageComponentDTOList.add(damageComponentDTO);

        header = new HashMap<String, String>();
        header.put("userid", "Test");
        header.put("extensionschema", "Test");

        jobCode = 1114;
    }

    @AfterEach
    void tearDown() throws Exception{
        damageComponentDTOList = null;
        damageComponentList = null;
        damageComponent = null;
        damageComponentDTO = null;
    }

   @Test
    void getAllDamageComponentsTest() {
        when(damageComponentService.getAllDamageComponents()).thenReturn(damageComponentList);
        ResponseEntity<APIResponse<List<DamageComponentDTO>>> getDamageComp = damageComponentController.getAllDamageComponents(Mockito.any());
        assertEquals(getDamageComp.getStatusCodeValue(),200);
    }



    @Test
    void getDamageComponentsByJobCodeTest() {
        when(damageComponentService.getDamageComponentsByJobCode(jobCode)).thenReturn(damageComponent);
        ResponseEntity<APIResponse<List<DamageComponentDTO>>> getDamageComp = damageComponentController.getAllDamageComponents(Mockito.any());
        assertEquals(getDamageComp.getStatusCodeValue(),200);
    }



    @Test
    void getAllDamageComponentExceptionTest() {
        when(damageComponentService.getAllDamageComponents()).thenThrow(new RuntimeException());

        ResponseEntity<APIResponse<List<DamageComponentDTO>>> getDmgComp = damageComponentController.getAllDamageComponents(Mockito.any());

        assertEquals(getDmgComp.getStatusCodeValue(),500);

    }

    @Test
    void getAllDamageComponentsNoRecordsFoundExceptionTest() {
        when(damageComponentService.getAllDamageComponents()).thenThrow(new NoRecordsFoundException());

        ResponseEntity<APIResponse<List<DamageComponentDTO>>> getDmgComp = damageComponentController.getAllDamageComponents(Mockito.any());

        assertEquals(getDmgComp.getStatusCodeValue(),404);

    }

    @Test
    void deleteDamageComponentTest() {
        when(damageComponentMapper.damageComponentDTOToDamageComponent(Mockito.any())).thenReturn(damageComponent);
        damageComponentService.deleteDamageComponent(Mockito.any());
        when(damageComponentMapper.damageComponentToDamageComponentDTO(Mockito.any())).thenReturn(damageComponentDTO);
        ResponseEntity<List<APIResponse<DamageComponentDTO>>> deleteList = damageComponentController.deleteDamageComponent(damageComponentDTOList);
        assertEquals(deleteList.getStatusCodeValue(),200);
    }

    @SuppressWarnings("unchecked")
    @Test
    void testDamageComponentEmptyDTOList(){
       // ResponseEntity<List<APIResponse<CarEmbargoDTO>>> responseEntity = carEmbargoController.deleteCarEmbargo(Collections.EMPTY_LIST);
        ResponseEntity<List<APIResponse<DamageComponentDTO>>> deleteList = damageComponentController.deleteDamageComponent(null);
        assertEquals(deleteList.getStatusCodeValue(),200);
    }

    @Test
    public void testGetAllDamageComponentsWithJobCode() throws Exception {

        Mockito.when(damageComponentService.getDamageComponentsByJobCode(jobCode)).thenReturn(damageComponent);

        ResponseEntity<APIResponse<List<DamageComponentDTO>>> response = damageComponentController.getAllDamageComponents(jobCode);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        APIResponse<List<DamageComponentDTO>> apiResponse = response.getBody();
        assertEquals(100, apiResponse.getStatusCode());
        assertEquals("Successfully retrieved data!", apiResponse.getMessages().get(0));
        assertNotNull(apiResponse.getData());
        assertEquals(1, apiResponse.getData().size());
        assertEquals(damageComponent.getJobCode(), apiResponse.getData().get(0).getJobCode());
        assertEquals(damageComponent.getCreateUserId(), apiResponse.getData().get(0).getCreateUserId());
    }


    @Test
    public void testGetAllDamageComponentsWithoutJobCode() throws Exception {

        Mockito.when(damageComponentService.getAllDamageComponents()).thenReturn(damageComponentList);

        ResponseEntity<APIResponse<List<DamageComponentDTO>>> response = damageComponentController.getAllDamageComponents(null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        APIResponse<List<DamageComponentDTO>> apiResponse = response.getBody();
        assertEquals(100, apiResponse.getStatusCode());
        assertEquals("Successfully retrieved data!", apiResponse.getMessages().get(0));
        assertNotNull(apiResponse.getData());
        assertEquals(damageComponentList.size(), apiResponse.getData().size());
        for(int i=0; i<damageComponentList.size(); i++) {
            assertEquals(damageComponentList.get(i).getJobCode(), apiResponse.getData().get(i).getJobCode());
            assertEquals(damageComponentList.get(i).getCreateUserId(), apiResponse.getData().get(i).getCreateUserId());
        }
    }

    @Test
    void testAddDamageComponent() {
        when(damageComponentMapper.damageComponentDTOToDamageComponent(Mockito.any())).thenReturn(damageComponent);
        when(damageComponentService.insertDamageComponent(Mockito.any(), Mockito.any())).thenReturn(damageComponent);
        when(damageComponentMapper.damageComponentToDamageComponentDTO(Mockito.any())).thenReturn(damageComponentDTO);
        ResponseEntity<APIResponse<DamageComponentDTO>> addedDamageComp= damageComponentController.addDamageComponent(damageComponentDTO, header);
        assertNotNull(addedDamageComp.getBody());
    }

    @Test
    void testUpdateDamageComponent() {
        when(damageComponentMapper.damageComponentDTOToDamageComponent(Mockito.any())).thenReturn(damageComponent);
        when(damageComponentService.UpdateDamageComponent(Mockito.any(), Mockito.any())).thenReturn(damageComponent);
        when(damageComponentMapper.damageComponentToDamageComponentDTO(Mockito.any())).thenReturn(damageComponentDTO);
        ResponseEntity<APIResponse<DamageComponentDTO>> updateDamageComp= damageComponentController.updateDamageComponent(damageComponentDTO, header);
        assertNotNull(updateDamageComp.getBody());
    }

    @Test
    void testAddDamageComponentException() {
        when(damageComponentService.insertDamageComponent(Mockito.any(), Mockito.any())).thenThrow(new RuntimeException());
        ResponseEntity<APIResponse<DamageComponentDTO>> addedDamageComp = damageComponentController.addDamageComponent(damageComponentDTO, header);
        assertEquals(addedDamageComp.getStatusCodeValue(),500);

        when(damageComponentService.UpdateDamageComponent(Mockito.any(), Mockito.any())).thenThrow(new RuntimeException());
        ResponseEntity<APIResponse<DamageComponentDTO>> updateDamageComp = damageComponentController.updateDamageComponent(damageComponentDTO, header);
        assertEquals(updateDamageComp.getStatusCodeValue(),500);
    }

    @Test
    void testAddDamageComponentRecordAlreadyExistsException() {
        when(damageComponentService.insertDamageComponent(Mockito.any(), Mockito.any())).thenThrow(new RecordAlreadyExistsException());
        ResponseEntity<APIResponse<DamageComponentDTO>> addedDamageComp = damageComponentController.addDamageComponent(damageComponentDTO, header);
        assertEquals(addedDamageComp.getStatusCodeValue(),208);
    }

    @Test
    void testAddDamageComponentNoRecordsFoundException() {
        when(damageComponentService.insertDamageComponent(Mockito.any(), Mockito.any())).thenThrow(new NoRecordsFoundException());
        ResponseEntity<APIResponse<DamageComponentDTO>> addedDamageComp = damageComponentController.addDamageComponent(damageComponentDTO, header);
        assertEquals(addedDamageComp.getStatusCodeValue(),404);

        when(damageComponentService.UpdateDamageComponent(Mockito.any(), Mockito.any())).thenThrow(new NoRecordsFoundException());
        ResponseEntity<APIResponse<DamageComponentDTO>> updateDamageComp = damageComponentController.updateDamageComponent(damageComponentDTO, header);
        assertEquals(updateDamageComp.getStatusCodeValue(),404);
    }

}
