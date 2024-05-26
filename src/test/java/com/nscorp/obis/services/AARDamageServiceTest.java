package com.nscorp.obis.services;

import com.nscorp.obis.domain.AARDamage;
import com.nscorp.obis.domain.DamageLocationConversion;
import com.nscorp.obis.dto.AARDamageDTO;
import com.nscorp.obis.dto.DamageLocationConversionDTO;
import com.nscorp.obis.dto.mapper.AARDamageMapper;
import com.nscorp.obis.dto.mapper.DamageLocationConversionMapper;
import com.nscorp.obis.repository.AARDamageRepository;
import com.nscorp.obis.repository.DamageLocationConversionRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.mockito.Mockito.when;

public class AARDamageServiceTest {

    @Mock
    AARDamageRepository aarDamageRepository;
    @InjectMocks
    AARDamageServiceImpl aarDamageServiceImpl;
    AARDamage aarDamage;
    List<AARDamage> aarDamageList;
    AARDamageDTO aarDamageDTO;
    List<AARDamageDTO> aarDamageDTOList;
    Map<String, String> headers;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        aarDamage = new AARDamage();
        aarDamage.setJobCode(123);
        aarDamage.setDscr(null);
        aarDamage.setChassisInd(null);
        aarDamage.setFamilyCode(null);
        aarDamage.setUversion("abc");

        aarDamageList = new ArrayList<>();
        aarDamageList.add(aarDamage);
        aarDamageDTO = new AARDamageDTO();
        aarDamageDTO = AARDamageMapper.INSTANCE.AARDamageToAARDamageDTO(aarDamage);
        aarDamageDTOList = new ArrayList<>();
        aarDamageDTOList.add(aarDamageDTO);
        headers = new HashMap<>();
        headers.put("userid", "test");
        headers.put("extensionschema", "test");
    }

    @AfterEach
    void tearDown() throws Exception {
        aarDamage = null;
        aarDamageList = null;
        aarDamageDTO = null;
        aarDamageDTOList = null;
    }

    @Test
    void testGetAllAARDamage() {
        when(aarDamageRepository.findAll()).thenReturn(aarDamageList);
        List<AARDamage> result = aarDamageServiceImpl.getAllAarDamageCodes("abcd");
    }

    @Test
    void testGetAllAARDamageNoRecordsFound() {
        when(aarDamageRepository.findAll()).thenReturn(Collections.emptyList());
        List<AARDamage> result = aarDamageServiceImpl.getAllAarDamageCodes("abcd");
    }
}
