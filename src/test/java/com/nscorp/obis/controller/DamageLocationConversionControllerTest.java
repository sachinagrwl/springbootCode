package com.nscorp.obis.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.nscorp.obis.domain.DamageLocationConversion;
import com.nscorp.obis.dto.DamageLocationConversionDTO;
import com.nscorp.obis.dto.mapper.DamageLocationConversionMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.DamageLocationConversionService;

class DamageLocationConversionControllerTest {
    @Mock
    DamageLocationConversionService damageLocationConversionService;
    @InjectMocks
    DamageLocationConversionController damageLocationConversionController;
    @Mock
    DamageLocationConversionMapper damageLocationConversionMapper;
    DamageLocationConversion damageLocationConversion;
    List<DamageLocationConversion> damageLocationConvList;
    DamageLocationConversionDTO damageLocationConversionDTO;
    List<DamageLocationConversionDTO> damageLocationConversionDTOList;
    
	Map<String, String> header;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        damageLocationConversion = new DamageLocationConversion();
        damageLocationConversion.setLocDesc("abc");
        damageLocationConversion.setLocCd("abc");
        damageLocationConversion.setLocDscr("abc");
        damageLocationConversion.setUversion("abc");
        damageLocationConvList = new ArrayList<>();
        damageLocationConvList.add(damageLocationConversion);
        damageLocationConversionDTO = new DamageLocationConversionDTO();
        damageLocationConversionDTO = DamageLocationConversionMapper.INSTANCE.damageLocationConversionToDamageLocationConversionDTO(damageLocationConversion);
        damageLocationConversionDTOList = new ArrayList<>();
        damageLocationConversionDTOList.add(damageLocationConversionDTO);
        header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");
    }
    @AfterEach
	void tearDown() throws Exception {
    	damageLocationConversion = null;
    	damageLocationConvList = null;
    	damageLocationConversionDTO = null;
    	damageLocationConversionDTOList = null;
	}

    @Test
    void testGetDamageLocationConversion() {
        when(damageLocationConversionService.getAllDamageLocationConversion()).thenReturn(damageLocationConvList);
        ResponseEntity<APIResponse<List<DamageLocationConversionDTO>>> result = damageLocationConversionController.getDamageLocationConversion();
    }
    @Test
    void testDamageLocationConversionException() {
        when(damageLocationConversionService.getAllDamageLocationConversion()).thenThrow(new RuntimeException());
        ResponseEntity<APIResponse<List<DamageLocationConversionDTO>>> result = damageLocationConversionController.getDamageLocationConversion();
        
        when(damageLocationConversionService.insertDamageLocationConversion(Mockito.any(), Mockito.any())).thenThrow(new RuntimeException());
        ResponseEntity<APIResponse<DamageLocationConversionDTO>> result1 = damageLocationConversionController.addDamageLocationConversion(Mockito.any(), Mockito.any());
        assertEquals(result1.getStatusCodeValue(), 500);

        when(damageLocationConversionService.updateDamageLocationConversion(Mockito.any(), Mockito.any())).thenThrow(new RuntimeException());
        ResponseEntity<APIResponse<DamageLocationConversionDTO>> result2 = damageLocationConversionController.updateDamageLocationConversion(Mockito.any(), Mockito.any());
        assertEquals(result1.getStatusCodeValue(), 500);

    }
    @Test
    void testDamageLocationConversionNoRecordFoundException() {
        when(damageLocationConversionService.getAllDamageLocationConversion()).thenThrow(new NoRecordsFoundException());
        ResponseEntity<APIResponse<List<DamageLocationConversionDTO>>> result = damageLocationConversionController.getDamageLocationConversion();
        
        when(damageLocationConversionService.insertDamageLocationConversion(Mockito.any(), Mockito.any())).thenThrow(new NoRecordsFoundException());
        ResponseEntity<APIResponse<DamageLocationConversionDTO>> result1 = damageLocationConversionController.addDamageLocationConversion(Mockito.any(), Mockito.any());
        assertEquals(result1.getStatusCodeValue(), 404);
        
        when(damageLocationConversionService.updateDamageLocationConversion(Mockito.any(), Mockito.any())).thenThrow(new NoRecordsFoundException());
        ResponseEntity<APIResponse<DamageLocationConversionDTO>> result2 = damageLocationConversionController.updateDamageLocationConversion(Mockito.any(), Mockito.any());
        assertEquals(result2.getStatusCodeValue(), 404);
    }
    
    @Test
    void testRecordAlreadyExistsException() {
       
        
        when(damageLocationConversionService.insertDamageLocationConversion(Mockito.any(), Mockito.any())).thenThrow(new RecordAlreadyExistsException());
        ResponseEntity<APIResponse<DamageLocationConversionDTO>> result1 = damageLocationConversionController.addDamageLocationConversion(Mockito.any(), Mockito.any());
        assertEquals(result1.getStatusCodeValue(), 208);

        when(damageLocationConversionService.updateDamageLocationConversion(Mockito.any(), Mockito.any())).thenThrow(new RecordAlreadyExistsException());
        ResponseEntity<APIResponse<DamageLocationConversionDTO>> result2 = damageLocationConversionController.updateDamageLocationConversion(Mockito.any(), Mockito.any());
        assertEquals(result2.getStatusCodeValue(), 208);

    }

    @Test
    void testDeleteDamageReason() {
        ResponseEntity<List<APIResponse<DamageLocationConversionDTO>>> result = damageLocationConversionController.deleteDamageReason(damageLocationConversionDTOList);
        result = damageLocationConversionController.deleteDamageReason(null);
        result = damageLocationConversionController.deleteDamageReason(Collections.emptyList());
    }
    
    @Test
	void testAddDamageLocationConversion() {
		when(damageLocationConversionMapper.damageLocationConversionDtoToDamageLocationConversion(Mockito.any())).thenReturn(damageLocationConversion);
		when(damageLocationConversionService.insertDamageLocationConversion(Mockito.any(), Mockito.any())).thenReturn(damageLocationConversion);
		when(damageLocationConversionMapper.damageLocationConversionToDamageLocationConversionDTO(Mockito.any())).thenReturn(damageLocationConversionDTO);
		ResponseEntity<APIResponse<DamageLocationConversionDTO>> addedDamageLocationCon = damageLocationConversionController.addDamageLocationConversion(damageLocationConversionDTO, header);
		assertNotNull(addedDamageLocationCon.getBody());
	}
    
    @Test
    void testupdateDamageLocationConversion() {
    	when(damageLocationConversionMapper.damageLocationConversionDtoToDamageLocationConversion(Mockito.any())).thenReturn(damageLocationConversion);
		when(damageLocationConversionService.updateDamageLocationConversion(Mockito.any(), Mockito.any())).thenReturn(damageLocationConversion);
		when(damageLocationConversionMapper.damageLocationConversionToDamageLocationConversionDTO(Mockito.any())).thenReturn(damageLocationConversionDTO);
		ResponseEntity<APIResponse<DamageLocationConversionDTO>> updateDamageLocationCon = damageLocationConversionController.updateDamageLocationConversion(damageLocationConversionDTO, header);
		assertNotNull(updateDamageLocationCon.getBody());
    }
}
