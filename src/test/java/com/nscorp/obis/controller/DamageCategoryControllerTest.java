package com.nscorp.obis.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nscorp.obis.dto.UnCdDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.nscorp.obis.domain.DamageCategory;
import com.nscorp.obis.dto.DamageCategoryDTO;
import com.nscorp.obis.dto.mapper.DamageCategoryMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.SizeExceedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.DamageCategoryService;

public class DamageCategoryControllerTest {
	
	@Mock
	DamageCategoryService damageCategoryService;

    @Mock
    DamageCategoryMapper damageCategoryMapper;

    @InjectMocks
    DamageCategoryController damageCategoryController;

    DamageCategory damageCategory;
    DamageCategoryDTO damageCategoryDTO;
    List<DamageCategory> damageCategoryList;
    List<DamageCategoryDTO> damageCategoryDTOList;
    Map<String, String> header;
    
    Integer catCd;

    @SuppressWarnings("deprecation")
    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        
        damageCategory = new DamageCategory();
        damageCategory.setCatCd(12);
        damageCategory.setCatDscr("ABC");
        damageCategory.setPrtOrder(20);

        damageCategoryList = new ArrayList<>();
        damageCategoryList.add(damageCategory);

        damageCategoryDTO = new DamageCategoryDTO();
        damageCategoryDTO.setCatCd(12);
        damageCategoryDTO.setCatDscr("ABC");
        damageCategoryDTO.setPrtOrder(20);

        damageCategoryDTOList = new ArrayList<>();
        damageCategoryDTOList.add(damageCategoryDTO);
        
        catCd = 12;

        header = new HashMap<String, String>();
        header.put("userid", "Test");
        header.put("extensionschema", "Test");
    }

    @AfterEach
    void tearDown() throws Exception{
    	damageCategory = null;
        damageCategoryDTO = null;
        damageCategoryList = null;
        damageCategoryDTOList = null;
    }

    @Test
    void getAllDamageCategory() {
        when(damageCategoryService.getAllDamageCategory(catCd)).thenReturn(damageCategoryList);      
		ResponseEntity<APIResponse<List<DamageCategoryDTO>>> responseEntity = damageCategoryController.getDamageCategory(catCd);
        assertEquals(responseEntity.getStatusCodeValue(),200);
    }

    @Test
    void getNoRecordsFoundException(){
        when(damageCategoryService.getAllDamageCategory(catCd)).thenThrow(new NoRecordsFoundException());
        ResponseEntity<APIResponse<List<DamageCategoryDTO>>> responseEntity = damageCategoryController.getDamageCategory(catCd);
        assertEquals(responseEntity.getStatusCodeValue(),404);
    }

    @Test
    void getRuntimeException(){
        when(damageCategoryService.getAllDamageCategory(catCd)).thenThrow(new RuntimeException());
        ResponseEntity<APIResponse<List<DamageCategoryDTO>>> responseEntity = damageCategoryController.getDamageCategory(catCd);
        assertEquals(responseEntity.getStatusCodeValue(),500);
    }


    
    @Test
    void testDeleteDamageCategory() {
    	when(damageCategoryMapper.damageCategoryDtoToDamageCategory(Mockito.any())).thenReturn(damageCategory);
    	when(damageCategoryService.deleteDamageCategory(Mockito.any())).thenReturn(damageCategoryList);
		when(damageCategoryMapper.damageCategoryToDamageCategoryDTO(Mockito.any())).thenReturn(damageCategoryDTO);
    	ResponseEntity<List<APIResponse<DamageCategoryDTO>>> responseEntity = damageCategoryController.deleteDamageCategory(damageCategoryDTOList);
        assertEquals(responseEntity.getStatusCodeValue(),200);
    }
    
    @Test
    void testDeleteDamageCategoryException(){
    	when(damageCategoryService.deleteDamageCategory(Mockito.any())).thenThrow(new RuntimeException());
        ResponseEntity<List<APIResponse<DamageCategoryDTO>>> responseEntity = damageCategoryController.deleteDamageCategory(damageCategoryDTOList);
        assertEquals(responseEntity.getStatusCodeValue(),500);
    }
    
    @Test
    void testDeleteDamageCategoryNoRecordFoundException(){
    	when(damageCategoryService.deleteDamageCategory(Mockito.any())).thenThrow(new NoRecordsFoundException());
        ResponseEntity<List<APIResponse<DamageCategoryDTO>>> responseEntity = damageCategoryController.deleteDamageCategory(damageCategoryDTOList);
        assertEquals(responseEntity.getStatusCodeValue(),500);
    }
    
    @Test
	void testAddDamageCategory() {
		when(damageCategoryService.addDamageCategory(damageCategory, header)).thenReturn(damageCategory);
		ResponseEntity<APIResponse<DamageCategoryDTO>> result = damageCategoryController.addDamageCategory(damageCategoryDTO, header);
		assertEquals(result.getStatusCodeValue(), 200);
    }
    
    @Test
	void testAddDamageCategoryNoRecordsFoundException() {		
		when(damageCategoryService.addDamageCategory(Mockito.any(), Mockito.any())).thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<DamageCategoryDTO>> result1 = damageCategoryController.addDamageCategory(damageCategoryDTO, header);
		assertEquals(result1.getStatusCodeValue(), 404);
    }
    
    @Test
	void testAddDamageCategoryException() {
		when(damageCategoryService.addDamageCategory(Mockito.any(), Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<DamageCategoryDTO>> result1 = damageCategoryController.addDamageCategory(damageCategoryDTO, header);
		assertEquals(result1.getStatusCodeValue(), 500);
    }
    @Test
	void testAddDamageCategoryNullPointer(){	
		when(damageCategoryService.addDamageCategory(Mockito.any(), Mockito.any())).thenThrow(new NullPointerException());
		ResponseEntity<APIResponse<DamageCategoryDTO>> result1 = damageCategoryController.addDamageCategory(damageCategoryDTO, header);
		assertEquals(result1.getStatusCodeValue(), 400);
	}
	
	@Test
	void testAddDamageCategorySizeExceedException(){	
		when(damageCategoryService.addDamageCategory(Mockito.any(), Mockito.any())).thenThrow(new SizeExceedException());
		ResponseEntity<APIResponse<DamageCategoryDTO>> result1 = damageCategoryController.addDamageCategory(damageCategoryDTO, header);
		assertEquals(result1.getStatusCodeValue(), 411);
	}
	
	@Test
	void testAddDamageCategoryRecordNotAddedException(){	
		when(damageCategoryService.addDamageCategory(Mockito.any(), Mockito.any())).thenThrow(new RecordNotAddedException());
		ResponseEntity<APIResponse<DamageCategoryDTO>> result1 = damageCategoryController.addDamageCategory(damageCategoryDTO, header);
		assertEquals(result1.getStatusCodeValue(), 406);
	}

    @Test
    void testNoRecordFoundException(){
        when(damageCategoryService.updateDamageCategory(Mockito.any(), Mockito.any())).thenThrow(new NoRecordsFoundException());
        ResponseEntity<APIResponse<DamageCategoryDTO>> exception = damageCategoryController.updateDamageCategory(damageCategoryDTO, header);
        assertEquals(exception.getStatusCodeValue(), 404);
    }

    @Test
    void testUpdateRuntimeException(){
        when(damageCategoryService.updateDamageCategory(Mockito.any(), Mockito.any())).thenThrow(new RuntimeException());
        ResponseEntity<APIResponse<DamageCategoryDTO>> ex = damageCategoryController.updateDamageCategory(damageCategoryDTO, header);
        assertEquals(ex.getStatusCodeValue(), 500);
    }

    @Test
    void testUpdateDmgCategory() {
        when(damageCategoryMapper.damageCategoryDtoToDamageCategory(Mockito.any())).thenReturn(damageCategory);
        when(damageCategoryMapper.damageCategoryToDamageCategoryDTO(Mockito.any())).thenReturn(damageCategoryDTO);
        ResponseEntity<APIResponse<DamageCategoryDTO>> data = damageCategoryController.updateDamageCategory(damageCategoryDTO, header);
        assertEquals(data.getStatusCodeValue(), 200);
    }


}

