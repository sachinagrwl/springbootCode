package com.nscorp.obis.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nscorp.obis.dto.DamageComponentDTO;
import com.nscorp.obis.dto.UnCdDTO;
import com.nscorp.obis.dto.mapper.DamageCodeConversionMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.nscorp.obis.domain.DamageCodeConversion;
import com.nscorp.obis.dto.DamageCodeConversionDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.DamageCodeConversionService;

class DamageCodeConversionControllerTest {
    @Mock
    DamageCodeConversionService damageCodeConversionService;
    @InjectMocks
    DamageCodeConversionController damageCodeConversionController;

    @Mock
    DamageCodeConversionMapper damageCodeConversionMapper;

    DamageCodeConversion damageCodeConversion;
    List<DamageCodeConversion> damageCodeConversionList;
    DamageCodeConversionDTO damageCodeConversionDto;
    List<DamageCodeConversionDTO> damageCodeConversionDtoList;
    
    Map<String, String> header;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        damageCodeConversion = new DamageCodeConversion();
        damageCodeConversionDto = new DamageCodeConversionDTO();
        damageCodeConversionList = new ArrayList<>();
        damageCodeConversionDtoList = new ArrayList<>();
		
		
        damageCodeConversion.setCatCd(123);;
        damageCodeConversion.setReasonCd("ABCD");
        damageCodeConversion.setAarJobCode(null);
        damageCodeConversion.setAarWhyMadeCode(null);
		
        damageCodeConversionList.add(damageCodeConversion);
	    
        damageCodeConversionDto.setCatCd(123);;
        damageCodeConversionDto.setReasonCd("ABCD");
        damageCodeConversionDto.setAarJobCode(null);
        damageCodeConversionDto.setAarWhyMadeCode(null);
	    
        damageCodeConversionDtoList.add(damageCodeConversionDto);
	    
	    header = new HashMap<String, String>();
        header.put("userid", "Test");
        header.put("extensionschema", "Test");
    }
    
    @AfterEach
    void tearDown() throws Exception {
    	damageCodeConversion = null;
    	damageCodeConversionDto = null;
    	damageCodeConversionList = null;
    	damageCodeConversionDtoList = null;
   
    }

  @Test
    void testGetAllDamageCodeConversions() {
        when(damageCodeConversionService.getAllDamageCodeConversions()).thenReturn(damageCodeConversionList);

        ResponseEntity<APIResponse<List<DamageCodeConversionDTO>>> result = damageCodeConversionController.getAllDamageCodeConversions(123, "reasonCd");
        //Assertions.assertEquals(null, result);
      assertEquals(result.getStatusCodeValue(),200);
    }


    @Test
    void testGetByCatCode(){
        when(damageCodeConversionService.getDamageCodeConversionByCatCode(any(), any())).thenReturn(new DamageCodeConversion("uversion", "createUserId", null, "updateUserId", null, "updateExtensionSchema", 123, "A", null, 123));
        ResponseEntity<APIResponse<List<DamageCodeConversionDTO>>> result = damageCodeConversionController.getAllDamageCodeConversions(123, "reasonCd");
        //Assertions.assertEquals(null, result);
        assertEquals(result.getStatusCodeValue(),200);
    }
  /*  @Test
    void testDeleteDamageCodeConversion() {
        ResponseEntity<List<APIResponse<DamageCodeConversionDTO>>> result = damageCodeConversionController.deleteDamageCodeConversion(null);
        Assertions.assertEquals(null, result);
    } */

    @Test
    void testAddDamageCodeConversion() {
        when(damageCodeConversionService.addDamageCodeConversion(any(), any())).thenReturn(damageCodeConversion);
        ResponseEntity<APIResponse<DamageCodeConversionDTO>> result = damageCodeConversionController.addDamageCodeConversion(damageCodeConversionDto,header);
        Assertions.assertEquals(200, result.getStatusCodeValue());
    }
    @Test
    void testAddDamageCodeConversionException() {
        when(damageCodeConversionService.addDamageCodeConversion(Mockito.any(), Mockito.any())).thenThrow(new RuntimeException());
        ResponseEntity<APIResponse<DamageCodeConversionDTO>> addDam = damageCodeConversionController.addDamageCodeConversion(damageCodeConversionDto,header);
        assertEquals(addDam.getStatusCodeValue(), 500);

       when(damageCodeConversionService.getAllDamageCodeConversions()).thenThrow(new RuntimeException());
        ResponseEntity<APIResponse<List<DamageCodeConversionDTO>>> getDam = damageCodeConversionController.getAllDamageCodeConversions(123, "A");
        //assertEquals(getDam.getStatusCodeValue(), 500);

        when(damageCodeConversionService.getDamageCodeConversionByCatCode(Mockito.any(), Mockito.any())).thenThrow(new RuntimeException());
        ResponseEntity<APIResponse<List<DamageCodeConversionDTO>>> getByCode = damageCodeConversionController.getAllDamageCodeConversions(123, "A");
        assertEquals(getByCode.getStatusCodeValue(), 500);

            }
    @Test
    void testAddDamageCodeConversionNoRecordsFoundException() {
        when(damageCodeConversionService.addDamageCodeConversion(Mockito.any(), Mockito.any())).thenThrow(new NoRecordsFoundException());
        ResponseEntity<APIResponse<DamageCodeConversionDTO>> addDam = damageCodeConversionController.addDamageCodeConversion(damageCodeConversionDto,header);
        assertEquals(addDam.getStatusCodeValue(), 404);

        when(damageCodeConversionService.getAllDamageCodeConversions()).thenThrow(new NoRecordsFoundException());
        ResponseEntity<APIResponse<List<DamageCodeConversionDTO>>> getDam = damageCodeConversionController.getAllDamageCodeConversions(123, "A");
       // assertEquals(getDam.getStatusCodeValue(), 404);

        when(damageCodeConversionService.getDamageCodeConversionByCatCode(Mockito.any(), Mockito.any())).thenThrow(new RuntimeException());
        ResponseEntity<APIResponse<List<DamageCodeConversionDTO>>> getByCode = damageCodeConversionController.getAllDamageCodeConversions(123, "A");
        assertEquals(getByCode.getStatusCodeValue(), 404);
    }
    @Test
    void testAddDamageCodeConversionRecordAlreadyExistsException() {
        when(damageCodeConversionService.addDamageCodeConversion(Mockito.any(), Mockito.any())).thenThrow(new RecordAlreadyExistsException());
        ResponseEntity<APIResponse<DamageCodeConversionDTO>> addDam = damageCodeConversionController.addDamageCodeConversion(damageCodeConversionDto,header);
        assertEquals(addDam.getStatusCodeValue(), 208);
    }


    
    
    @Test
    void testUpdateDamageCodeConversion() {
        when(damageCodeConversionService.updateDamageCodeConversion(any(), any())).thenReturn(damageCodeConversion);
        ResponseEntity<APIResponse<DamageCodeConversionDTO>> result = damageCodeConversionController.updateDamageCodeConversion(damageCodeConversionDto,header);
        Assertions.assertEquals(200, result.getStatusCodeValue());
    }
    @Test
    void testUpdateDamageCodeConversionNoRecordsFoundException() {
        when(damageCodeConversionService.updateDamageCodeConversion(Mockito.any(), Mockito.any())).thenThrow(new NoRecordsFoundException());
        ResponseEntity<APIResponse<DamageCodeConversionDTO>> response = damageCodeConversionController.updateDamageCodeConversion(damageCodeConversionDto,header);
        assertEquals(404, response.getStatusCodeValue());
    }
    @Test
    void testUpdateDamageCodeConversionRuntimeException() {
        when(damageCodeConversionService.updateDamageCodeConversion(Mockito.any(), Mockito.any())).thenThrow(new RuntimeException());
        ResponseEntity<APIResponse<DamageCodeConversionDTO>> response =damageCodeConversionController.updateDamageCodeConversion(damageCodeConversionDto,header);
        assertEquals(500, response.getStatusCodeValue());
    }
    @Test
    void testUpdateDamageCodeConversionRecordAlreadyExistsException() {
        when(damageCodeConversionService.updateDamageCodeConversion(Mockito.any(), Mockito.any())).thenThrow(new RecordAlreadyExistsException());
        ResponseEntity<APIResponse<DamageCodeConversionDTO>> response = damageCodeConversionController.updateDamageCodeConversion(damageCodeConversionDto,header);
        assertEquals(response.getStatusCodeValue(), 208);
    }

    @Test
    void deleteDamageCodeConversionTest() {
        when(damageCodeConversionMapper.damageCodeConversionDtoToDamageCodeConversion(Mockito.any())).thenReturn(damageCodeConversion);
        damageCodeConversionService.deleteCodeConversion(Mockito.any());
        when(damageCodeConversionMapper.damageCodeConversionToDamageCodeConversionDTO(Mockito.any())).thenReturn(damageCodeConversionDto);
        ResponseEntity<List<APIResponse<DamageCodeConversionDTO>>> deleteList = damageCodeConversionController.deleteDamageCodeConversion(damageCodeConversionDtoList);
        assertEquals(deleteList.getStatusCodeValue(),200);
    }
}

