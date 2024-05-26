package com.nscorp.obis.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.*;

import com.nscorp.obis.domain.UnCd;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.nscorp.obis.domain.DamageCategory;
import com.nscorp.obis.dto.DamageCategoryDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.repository.DamageCategoryRepository;

public class DamageCategoryServiceTest {
	
	 	@InjectMocks
	 	DamageCategoryServiceImpl damageCategoryService;

	    @Mock
	    DamageCategoryRepository damageCategoryRepository;

	    DamageCategory damageCategory;
	    DamageCategoryDTO damageCategoryDTO;
	    List<DamageCategory> damageCategoryList;
	    List<DamageCategoryDTO> damageCategoryDTOList;
	    Map<String, String> header;
	    
	    Integer catCd;

	    @SuppressWarnings("deprecation")
	    @BeforeEach
	    void setUp() throws Exception{
	        MockitoAnnotations.initMocks(this);
	        
	        damageCategory = new DamageCategory();
	        damageCategoryDTO = new DamageCategoryDTO();
	        damageCategoryList = new ArrayList<>();
	        damageCategoryDTOList = new ArrayList<>();
	        
	        damageCategory.setCatCd(12);
	        damageCategory.setCatDscr("ABC");
	        damageCategory.setPrtOrder(20);
			damageCategory.setUversion("!");

	        damageCategoryDTO.setCatCd(12);
	        damageCategoryDTO.setCatDscr("ABC");
	        damageCategoryDTO.setPrtOrder(20);
			damageCategoryDTO.setUversion("!");
	        
	        damageCategoryList.add(damageCategory);
	        
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
	        when(damageCategoryRepository.findAll()).thenReturn(damageCategoryList);
	        List<DamageCategory> getDamage = damageCategoryService.getAllDamageCategory(catCd);
	        assertEquals(getDamage,damageCategoryList);
	    }

	    @Test
	    void getAllDamageCategoryNoRecordsFoundException() {
	        NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
	                () -> when(damageCategoryService.getAllDamageCategory(catCd)));
	        assertEquals("No Records found!", exception.getMessage());

			NoRecordsFoundException exception1 = assertThrows(NoRecordsFoundException.class,
					() -> when(damageCategoryService.updateDamageCategory(damageCategory, header)));
			assertEquals("No Records found!", exception.getMessage());


	    }
	    
	    @Test
	    void testDeleteRulesCircular() {
	    	when(damageCategoryRepository.existsByCatCd(Mockito.any())).thenReturn(true);
	    	when(damageCategoryRepository.findByCatCd(Mockito.any())).thenReturn(damageCategoryList);
	    	List<DamageCategory> delDamage = damageCategoryService.deleteDamageCategory(damageCategory);
	        assertEquals(delDamage,damageCategoryList);
	    }
	    
	    @Test
	    void testDeleteRulesCircularNoRecordsFoundException() {
	    	when(damageCategoryRepository.existsByCatCd(Mockito.any())).thenReturn(false);
	    	assertThrows(NoRecordsFoundException.class,
	                () -> damageCategoryService.deleteDamageCategory(damageCategory));
	    }
	    
	    @Test
		void testAddDamageCategory() {
			when(damageCategoryRepository.existsByCatCd(Mockito.any())).thenReturn(false);
			when(damageCategoryRepository.save(Mockito.any())).thenReturn(damageCategory);
			DamageCategory damCat = damageCategoryService.addDamageCategory(damageCategory, header);
			assertEquals(damCat, damageCategory);
		}
	    @Test
		void testDamageCategoryRecordAlreadyExistsException() {
			when(damageCategoryRepository.existsByCatCd(Mockito.any())).thenReturn(true);
			RecordAlreadyExistsException exception1 = assertThrows(RecordAlreadyExistsException.class,
					() -> when(damageCategoryService.addDamageCategory(damageCategory, header)));
			assertEquals("Records Already Exists!", exception1.getMessage());
	    }

	@Test
	void testUpdateDamageCategory() {

		when(damageCategoryRepository.existsByCatCdAndUversion(Mockito.anyInt(), Mockito.anyString())).thenReturn(true);
		when(damageCategoryRepository.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable((damageCategory)));
		when(damageCategoryRepository.save(Mockito.any())).thenReturn(damageCategory);
		DamageCategory updatedDmg = damageCategoryService.updateDamageCategory(damageCategory, header);
		assertNotNull(updatedDmg);

	}

	@Test
	void testNoRecordFoundExcptionforUpdate(){
		when(damageCategoryRepository.existsByCatCdAndUversion(Mockito.any(),Mockito.any())).thenReturn(false);
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(damageCategoryService.updateDamageCategory(damageCategory, header)));
		assertEquals("No record found for this 'CAT_CD': 12", exception.getMessage());
	}

}
