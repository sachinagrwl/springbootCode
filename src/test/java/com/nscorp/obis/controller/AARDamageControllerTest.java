package com.nscorp.obis.controller;

import com.nscorp.obis.domain.AARDamage;
import com.nscorp.obis.dto.AARDamageDTO;
import com.nscorp.obis.dto.mapper.AARDamageMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.AARDamageService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;

public class AARDamageControllerTest {

    @Mock
    AARDamageService aarDamageService;
    @InjectMocks
    AARDamageController aarDamageController;
    @Mock
    AARDamageMapper aarDamageMapper;
    AARDamage aarDamage;
    List<AARDamage> aarDamageList;
    AARDamageDTO aarDamageDTO;
    List<AARDamageDTO> aarDamageDTOList;

    Map<String, String> header;

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

        header = new HashMap<String, String>();
        header.put("userid", "Test");
        header.put("extensionschema", "Test");
    }
    @AfterEach
    void tearDown() throws Exception {
        aarDamage = null;
        aarDamageList = null;
        aarDamageDTO = null;
        aarDamageDTOList = null;
    }

    @Test
    void testGetAARDamage() {
        when(aarDamageService.getAllAarDamageCodes(Mockito.any())).thenReturn(aarDamageList);
        ResponseEntity<APIResponse<List<AARDamageDTO>>> result = aarDamageController.getAarDamage("abcd");
    }
    @Test
    void testAARDamageException() {
        when(aarDamageService.getAllAarDamageCodes(Mockito.any())).thenThrow(new RuntimeException());
        ResponseEntity<APIResponse<List<AARDamageDTO>>> result = aarDamageController.getAarDamage("abcd");
    }
    @Test
    void testAARDamageNoRecordFoundException() {
        when(aarDamageService.getAllAarDamageCodes(Mockito.any())).thenThrow(new NoRecordsFoundException());
        ResponseEntity<APIResponse<List<AARDamageDTO>>> result = aarDamageController.getAarDamage("abcd");
    }

}
