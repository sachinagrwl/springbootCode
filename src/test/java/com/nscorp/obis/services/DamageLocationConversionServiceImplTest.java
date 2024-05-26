package com.nscorp.obis.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
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

import com.nscorp.obis.domain.DamageLocationConversion;
import com.nscorp.obis.dto.DamageLocationConversionDTO;
import com.nscorp.obis.dto.mapper.DamageLocationConversionMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.repository.DamageLocationConversionRepository;

class DamageLocationConversionServiceImplTest {
    @Mock
    DamageLocationConversionRepository damageLocationConversionRepository;
    @InjectMocks
    DamageLocationConversionServiceImpl damageLocationConversionServiceImpl;
    DamageLocationConversion damageLocationConversion;
    List<DamageLocationConversion> damageLocationConversionList;
    DamageLocationConversionDTO damageLocationConversionDTO;
    List<DamageLocationConversionDTO> damageLocationConversionDTOList;
	Map<String, String> headers;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        damageLocationConversion = new DamageLocationConversion();
        damageLocationConversion.setLocDesc("abcq");
        damageLocationConversion.setLocCd("abc");
        damageLocationConversion.setLocDscr("abe");
        damageLocationConversion.setUversion("a");
        damageLocationConversionList = new ArrayList<>();
        damageLocationConversionList.add(damageLocationConversion);
        damageLocationConversionDTO = new DamageLocationConversionDTO();
        damageLocationConversionDTO = DamageLocationConversionMapper.INSTANCE.damageLocationConversionToDamageLocationConversionDTO(damageLocationConversion);
        damageLocationConversionDTOList = new ArrayList<>();
        damageLocationConversionDTOList.add(damageLocationConversionDTO);
        headers = new HashMap<>();
		headers.put("userid", "test");
		headers.put("extensionschema", "test");
	}

	@AfterEach
	void tearDown() throws Exception {
		damageLocationConversion = null;
		damageLocationConversionList = null;
		damageLocationConversionDTO = null;
		damageLocationConversionDTOList = null;
	}

    @Test
    void testGetAllDamageLocationConversion() {
        when(damageLocationConversionRepository.findAll()).thenReturn(damageLocationConversionList);
        List<DamageLocationConversion> result = damageLocationConversionServiceImpl.getAllDamageLocationConversion();
    }

    @Test
    void testGetAllDamageLocationConversionNoRecordsFound() {
        when(damageLocationConversionRepository.findAll()).thenReturn(Collections.emptyList());
        List<DamageLocationConversion> result = damageLocationConversionServiceImpl.getAllDamageLocationConversion();
    }

    @Test
    void testDeleteDamageLocationConversion() {
        when(damageLocationConversionRepository.existsByLocDescAndUversion(anyString(), anyString())).thenReturn(true);
        when(damageLocationConversionRepository.findByLocDescAndUversion(anyString(), anyString())).thenReturn(damageLocationConversion);
        damageLocationConversionServiceImpl.deleteDamageLocationConversion(damageLocationConversion);
    }
    @Test
    void testDeleteDamageLocationConversionNoRecordsFound() {
        when(damageLocationConversionRepository.existsByLocDescAndUversion(anyString(), anyString())).thenReturn(false);
        NoRecordsFoundException exe = assertThrows(NoRecordsFoundException.class,
                () -> damageLocationConversionServiceImpl.deleteDamageLocationConversion(damageLocationConversion));
    }
    
    @Test
    void testinsertDamageLocationConversion() {
        when(damageLocationConversionRepository.existsByLocDscr(Mockito.any())).thenReturn(false);
		when(damageLocationConversionRepository.save(Mockito.any())).thenReturn(damageLocationConversion);
        DamageLocationConversion dmgLocConversion = damageLocationConversionServiceImpl.insertDamageLocationConversion(damageLocationConversion, headers);
		assertEquals(dmgLocConversion, damageLocationConversion);

    }
    
    @Test
	void testDamageLocationConversionRecordAlreadyExistsException() {
		when(damageLocationConversionRepository.existsByLocDscr(Mockito.any())).thenReturn(true);
		RecordAlreadyExistsException exception = assertThrows(RecordAlreadyExistsException.class,
				() -> when(damageLocationConversionServiceImpl.insertDamageLocationConversion(damageLocationConversion, headers)));
		assertEquals("Location Description already exists ", exception.getMessage());
		
		}
    @Test
    void testNoRecordsFoundException() {
        when(damageLocationConversionRepository.existsByLocDscrAndUversion(Mockito.any(), Mockito.any())).thenReturn(false);
        NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
                () -> when(damageLocationConversionServiceImpl.updateDamageLocationConversion(damageLocationConversion, headers)));
        assertEquals("Not a valid location description", exception.getMessage());
    }
    
    @Test
    void testUpdateDamageLocationConversion() {
        when(damageLocationConversionRepository.existsByLocDscrAndUversion(Mockito.any(),Mockito.any())).thenReturn(true);
        when(damageLocationConversionRepository.findByLocDscr(Mockito.any())).thenReturn(damageLocationConversion);
		when(damageLocationConversionRepository.save(Mockito.any())).thenReturn(damageLocationConversion);
        DamageLocationConversion dmgLocConversion = damageLocationConversionServiceImpl.updateDamageLocationConversion(damageLocationConversion, headers);
		assertEquals(dmgLocConversion, damageLocationConversion);

    }
}
