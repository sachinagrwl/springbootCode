package com.nscorp.obis.controller;

import com.nscorp.obis.domain.DamageComponentSize;
import com.nscorp.obis.dto.DamageComponentReasonDTO;
import com.nscorp.obis.dto.DamageComponentSizeDTO;
import com.nscorp.obis.dto.mapper.DamageComponentSizeMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.DamageComponentSizeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.OptimisticLockException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class DamageComponentSizeControllerTest {
    @Mock
    DamageComponentSizeService damageComponentSizeService;


    @InjectMocks
    DamageComponentSizeController damageComponentSizeController;

    DamageComponentSize damageComponentSize;
    DamageComponentSizeDTO damageComponentSizeDTO;
    List<DamageComponentSize> damageComponentSizeList;
    List<DamageComponentSizeDTO> damageComponentSizeDTOList;
    Map<String, String> header;

    Integer jobCode = 1103;
	Integer aarWhyMadeCode = 11;
	Long componentSizeCode = 1234L;


    @SuppressWarnings("deprecation")
    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        damageComponentSize = new DamageComponentSize();
        damageComponentSize.setJobCode(1111);
        damageComponentSize.setComponentSizeCode(111L);
        damageComponentSize.setAarWhyMadeCode(111);
        damageComponentSize.setUversion("!");
        damageComponentSize.setCreateUserId("Test");
        damageComponentSize.setUpdateUserId("Test");

        damageComponentSizeList = new ArrayList<>();

        damageComponentSizeDTO = new DamageComponentSizeDTO();
        damageComponentSizeDTO.setJobCode(1111);
        damageComponentSizeDTO.setComponentSizeCode(111L);
        damageComponentSizeDTO.setAarWhyMadeCode(111);
        damageComponentSizeDTO.setUversion("!");
        damageComponentSizeDTO.setCreateUserId("Test");
        damageComponentSizeDTO.setUpdateUserId("Test");

        damageComponentSizeDTOList = new ArrayList<>();

        damageComponentSizeList.add(damageComponentSize);
        damageComponentSizeDTOList.add(damageComponentSizeDTO);

        header = new HashMap<String, String>();
        header.put("userid", "Test");
        header.put("extensionschema", "Test");

    }

    @AfterEach
    void tearDown() throws Exception {
        damageComponentSizeDTOList = null;
        damageComponentSize = null;
        damageComponentSize = null;
        damageComponentSizeDTO = null;
    }


    @Test
        void testGetAllDamageComponentSizes() {
		when(damageComponentSizeService.fetchDamageComponentSize(Mockito.anyInt(), Mockito.anyInt(), Mockito.any())).thenReturn(damageComponentSizeDTOList);
		ResponseEntity<APIResponse<List<DamageComponentSizeDTO>>> response = damageComponentSizeController
				.getAllDamageComponentSizes(jobCode, aarWhyMadeCode, componentSizeCode );
		assertEquals(200, response.getStatusCodeValue());
	}

	@Test
	void testDeleteDamageComponentSize() {
		when(damageComponentSizeService.deleteDamageComponentSize(Mockito.any())).thenReturn(damageComponentSizeDTO);
		ResponseEntity<List<APIResponse<DamageComponentSizeDTO>>> response = damageComponentSizeController
				.deleteDamageComponentSizes(damageComponentSizeDTOList);
		assertEquals(200, response.getStatusCodeValue());
	}
	
	@Test
	void testNoRecordsFoundException() {
		when(damageComponentSizeService.fetchDamageComponentSize(Mockito.anyInt(), Mockito.anyInt(), Mockito.any())).thenThrow(NoRecordsFoundException.class);
		ResponseEntity<APIResponse<List<DamageComponentSizeDTO>>> response = damageComponentSizeController
				.getAllDamageComponentSizes(jobCode, aarWhyMadeCode, componentSizeCode );
		assertEquals(404, response.getStatusCodeValue());
		when(damageComponentSizeService.deleteDamageComponentSize(Mockito.any())).thenThrow(NoRecordsFoundException.class);
		ResponseEntity<List<APIResponse<DamageComponentSizeDTO>>> response2 = damageComponentSizeController
				.deleteDamageComponentSizes(damageComponentSizeDTOList);
		assertEquals(500, response2.getStatusCodeValue());
		
	}
	
	@Test
	void testException() {
		when(damageComponentSizeService.fetchDamageComponentSize(Mockito.anyInt(), Mockito.anyInt(), Mockito.any())).thenThrow(RuntimeException.class);
		ResponseEntity<APIResponse<List<DamageComponentSizeDTO>>> response = damageComponentSizeController
				.getAllDamageComponentSizes(jobCode, aarWhyMadeCode, componentSizeCode );
		assertEquals(500, response.getStatusCodeValue());
		when(damageComponentSizeService.deleteDamageComponentSize(Mockito.any())).thenThrow(OptimisticLockException.class);
		ResponseEntity<List<APIResponse<DamageComponentSizeDTO>>> response2 = damageComponentSizeController
				.deleteDamageComponentSizes(damageComponentSizeDTOList);
		assertEquals(500, response2.getStatusCodeValue());
	}


    @Test
    void testAddDamageComponentSize() throws SQLException {
        when(damageComponentSizeService.insertDamageComponentSize(Mockito.any(), Mockito.any()))
                .thenReturn(damageComponentSizeDTO);
        ResponseEntity<APIResponse<DamageComponentSizeDTO>> response = damageComponentSizeController
                .addDamageComponentSize(Mockito.any(), Mockito.any());
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);

    }

    @Test
    void testUpdateDamageCompLocation() throws SQLException {
        when(damageComponentSizeService.updateDamageCompSize(Mockito.any(), Mockito.any()))
                .thenReturn(damageComponentSizeDTO);
        ResponseEntity<APIResponse<DamageComponentSizeDTO>> response = damageComponentSizeController
                .updateDamageComponentSize(Mockito.any(), Mockito.any());
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);

    }

    @Test
    void testNoRecordFoundExceptionForAdd() throws SQLException {
        when(damageComponentSizeService.insertDamageComponentSize(Mockito.any(), Mockito.any()))
                .thenThrow(NoRecordsFoundException.class);
        ResponseEntity<APIResponse<DamageComponentSizeDTO>> response = damageComponentSizeController
                .addDamageComponentSize(Mockito.any(), Mockito.any());
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);

    }

    @Test
    void testRecordAlreadyExistExceptionForAdd() throws SQLException {
        when(damageComponentSizeService.insertDamageComponentSize(Mockito.any(), Mockito.any()))
                .thenThrow(RecordAlreadyExistsException.class);
        ResponseEntity<APIResponse<DamageComponentSizeDTO>> response = damageComponentSizeController
                .addDamageComponentSize(Mockito.any(), Mockito.any());
        assertEquals(response.getStatusCodeValue(), 400);

    }

    @Test
    void testInternalServerError() throws SQLException {
        when(damageComponentSizeService.insertDamageComponentSize(Mockito.any(), Mockito.any()))
                .thenThrow(RuntimeException.class);
        ResponseEntity<APIResponse<DamageComponentSizeDTO>> response = damageComponentSizeController
                .addDamageComponentSize(Mockito.any(), Mockito.any());
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);

        when(damageComponentSizeService.updateDamageCompSize(Mockito.any(), Mockito.any()))
                .thenThrow(RuntimeException.class);
        ResponseEntity<APIResponse<DamageComponentSizeDTO>> response2 = damageComponentSizeController
                .updateDamageComponentSize(Mockito.any(), Mockito.any());
        Assertions.assertEquals(response2.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void testNoRecordFoundExceptionForUpdate() throws SQLException {
        when(damageComponentSizeService.updateDamageCompSize(Mockito.any(), Mockito.any()))
                .thenThrow(NoRecordsFoundException.class);
        ResponseEntity<APIResponse<DamageComponentSizeDTO>> response = damageComponentSizeController
                .updateDamageComponentSize(Mockito.any(), Mockito.any());
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);

    }
}
