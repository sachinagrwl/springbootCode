package com.nscorp.obis.services;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nscorp.obis.domain.DamageComponent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.nscorp.obis.domain.DamageCodeConversion;
import com.nscorp.obis.dto.DamageCodeConversionDTO;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.repository.AARDamageRepository;
import com.nscorp.obis.repository.AARWhyMadeCodesRepository;
import com.nscorp.obis.repository.DamageCategoryRepository;
import com.nscorp.obis.repository.DamageCodeConversionRepository;
import com.nscorp.obis.repository.DamageReasonRepository;

class DamageCodeConversionServiceImplTest {
    @Mock
    DamageCodeConversionRepository damageCodeConversionRepository;
    @Mock
    DamageCategoryRepository damageCategoryRepo;
    @Mock
    DamageReasonRepository damageReasonRepo;
    @Mock
    AARDamageRepository aarDamageRepo;
    @Mock
    AARWhyMadeCodesRepository aarWhyCodesRepo;
    @InjectMocks
    DamageCodeConversionServiceImpl damageCodeConversionServiceImpl;

    
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
    public void testGetAllDamageCodeComponents() throws Exception {

        List<DamageCodeConversion> damageComponentList = new ArrayList<>();
        damageComponentList.add(new DamageCodeConversion());
        Mockito.when(damageCodeConversionRepository.findAllByOrderByCatCd()).thenReturn(damageComponentList);

        List<DamageCodeConversion> result = damageCodeConversionServiceImpl.getAllDamageCodeConversions();

        assertNotNull(result);
        Assertions.assertEquals(damageComponentList.size(), result.size());
    }

    @Test
    public void testGetDamageComponentsByCatCd() throws Exception {


        Mockito.when(damageCodeConversionRepository.findByCatCdAndReasonCd(1123, "A")).thenReturn(damageCodeConversion);

        DamageCodeConversion result = damageCodeConversionServiceImpl.getDamageCodeConversionByCatCode(1123, "A");

        assertNotNull(result);
        Assertions.assertEquals(damageCodeConversion.getCatCd(), result.getCatCd());
        Assertions.assertEquals(damageCodeConversion.getUpdateUserId(), result.getUpdateUserId());
    }

    @Test
    void testDeleteCodeConversion() {
        when(damageCodeConversionRepository.findByCatCdAndReasonCdAndUversion(any(), any(), any())).thenReturn(new DamageCodeConversion("uversion", "createUserId", null, "updateUserId", null, "updateExtensionSchema", null, "reasonCd", null, null));
        when(damageCodeConversionRepository.existsByCatCdAndReasonCdAndUversion(any(), any(), any())).thenReturn(true);

        damageCodeConversionServiceImpl.deleteCodeConversion(new DamageCodeConversion("uversion", "createUserId", null, "updateUserId", null, "updateExtensionSchema", null, "reasonCd", null, null));
     }

    @Test
    void testAddDamageCodeConversion() {
        when(damageCodeConversionRepository.existsByCatCdAndReasonCd(any(), any())).thenReturn(false);
        when(damageCategoryRepo.existsByCatCd(any())).thenReturn(true);
        when(damageReasonRepo.existsByCatCdAndReasonCd(any(), any())).thenReturn(true);
        when(aarDamageRepo.existsByJobCode(any())).thenReturn(true);
        when(aarWhyCodesRepo.existsByAarWhyMadeCd(any())).thenReturn(true);
        damageCodeConversionServiceImpl.addDamageCodeConversion(damageCodeConversion,header);
    }
    
    @Test
	void testAddDamageCodeConversionException() {
    	when(damageCategoryRepo.existsByCatCd(any())).thenReturn(false);
        InvalidDataException exception = assertThrows(InvalidDataException.class,
                () -> when(damageCodeConversionServiceImpl.addDamageCodeConversion(damageCodeConversion,header)));

        when(damageCategoryRepo.existsByCatCd(any())).thenReturn(true);
    	when(damageReasonRepo.existsByCatCdAndReasonCd(any(), any())).thenReturn(false);
        exception = assertThrows(InvalidDataException.class,
                () -> when(damageCodeConversionServiceImpl.addDamageCodeConversion(damageCodeConversion,header)));

        when(damageCategoryRepo.existsByCatCd(any())).thenReturn(true);
        when(damageReasonRepo.existsByCatCdAndReasonCd(any(), any())).thenReturn(true);
    	when(aarDamageRepo.existsByJobCode(any())).thenReturn(false);
        exception = assertThrows(InvalidDataException.class,
                () -> when(damageCodeConversionServiceImpl.addDamageCodeConversion(damageCodeConversion,header)));

        when(damageCategoryRepo.existsByCatCd(any())).thenReturn(true);
        when(damageReasonRepo.existsByCatCdAndReasonCd(any(), any())).thenReturn(true);
        when(aarDamageRepo.existsByJobCode(any())).thenReturn(true);
    	when(aarWhyCodesRepo.existsByAarWhyMadeCd(any())).thenReturn(false);
        exception = assertThrows(InvalidDataException.class,
                () -> when(damageCodeConversionServiceImpl.addDamageCodeConversion(damageCodeConversion,header)));
	}
    
    @Test
    void testAddDamageCodeConversionRecordsAlreadyExistsException() {
        when(damageCategoryRepo.existsByCatCd(any())).thenReturn(true);
        when(damageReasonRepo.existsByCatCdAndReasonCd(any(), any())).thenReturn(true);
        when(aarDamageRepo.existsByJobCode(any())).thenReturn(true);
        when(aarWhyCodesRepo.existsByAarWhyMadeCd(any())).thenReturn(true);

        when(damageCodeConversionRepository.existsByCatCdAndReasonCd(any(), any())).thenReturn(true);
    	RecordAlreadyExistsException exception1 = assertThrows(RecordAlreadyExistsException.class,
                () -> when(damageCodeConversionServiceImpl.addDamageCodeConversion(damageCodeConversion,header)));
    	assertEquals("Duplicate Data", exception1.getMessage());
    }

    @Test
    void testUpdateDamageCodeConversion() {
        when(damageCodeConversionRepository.existsByAarJobCdAndAarWhyMadeCode(any(), any())).thenReturn(false);
        when(aarDamageRepo.existsByJobCode(any())).thenReturn(true);
        when(aarWhyCodesRepo.existsByAarWhyMadeCd(any())).thenReturn(true);

        damageCodeConversionServiceImpl.updateDamageCodeConversion(damageCodeConversion,header);
    }
    
    @Test
	void testUpdateDamageCodeConversionInvalidDataException(){
    	when(aarDamageRepo.existsByJobCode(any())).thenReturn(false);
        InvalidDataException exception = assertThrows(InvalidDataException.class,
                () -> when(damageCodeConversionServiceImpl.updateDamageCodeConversion(damageCodeConversion,header)));

        when(aarDamageRepo.existsByJobCode(any())).thenReturn(true);
    	when(aarWhyCodesRepo.existsByAarWhyMadeCd(any())).thenReturn(false);
        exception = assertThrows(InvalidDataException.class,
                () -> when(damageCodeConversionServiceImpl.updateDamageCodeConversion(damageCodeConversion,header)));
	}

	@Test
	void testUpdateDamageCodeConversionRecordsAlreadyExistsException() {
        when(aarDamageRepo.existsByJobCode(any())).thenReturn(true);
        when(aarWhyCodesRepo.existsByAarWhyMadeCd(any())).thenReturn(true);
        when(damageCodeConversionRepository.existsByAarJobCdAndAarWhyMadeCode(any(), any())).thenReturn(true);
		RecordAlreadyExistsException exception1 = assertThrows(RecordAlreadyExistsException.class,
                () -> when(damageCodeConversionServiceImpl.updateDamageCodeConversion(damageCodeConversion,header)));
    	assertEquals("Duplicate Data", exception1.getMessage());
	}
}

