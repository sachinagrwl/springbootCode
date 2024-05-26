package com.nscorp.obis.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
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

import com.nscorp.obis.domain.DamageArea;
import com.nscorp.obis.dto.DamageAreaDTO;
import com.nscorp.obis.dto.HazRestrPermitDTO;
import com.nscorp.obis.dto.mapper.DamageAreaMapper;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.DamageAreaService;

public class DamageAreaControllerTest {

	@Mock
	DamageAreaService damageAreaService;

	@Mock
	DamageAreaMapper damageAreaMapper;

	@InjectMocks
	DamageAreaController damageAreaController;

	DamageAreaDTO damageAreaDto;
	DamageArea damageArea;
	List<DamageArea> damageAreaList;
	List<DamageAreaDTO> damageAreaDtoList;

	Map<String, String> header;

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		damageArea = new DamageArea();
		damageAreaDto = new DamageAreaDTO();
		damageAreaDtoList = new ArrayList<>();
		damageAreaList = new ArrayList<>();
		damageArea.setAreaCd("A");
		damageArea.setAreaDscr("Nose");
		damageArea.setDisplayCd("Y");

		damageAreaDto = new DamageAreaDTO();
		damageAreaDto.setAreaCd("A");
		damageAreaDto.setAreaDscr("Nose");
		damageAreaDto.setDisplayCd("Y");

		damageAreaDtoList.add(damageAreaDto);

		damageAreaList = new ArrayList<>();
		damageAreaList.add(damageArea);

		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");

	}

	@AfterEach
	void tearDown() throws Exception {
		damageArea = null;
		damageAreaList = null;
		damageAreaDto = null;
		damageAreaDtoList = null;

	}

	@Test
	void testGetAllDamageArea() {
		when(damageAreaService.getAllDamageArea()).thenReturn(damageAreaList);
		ResponseEntity<APIResponse<List<DamageAreaDTO>>> damageAreaList = damageAreaController.getAllDamageArea();
		assertNotNull(damageAreaList.getBody());
	}

	@Test
	void testDamageAreaException() {
		when(damageAreaService.getAllDamageArea()).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<List<DamageAreaDTO>>> damageAreaList = damageAreaController.getAllDamageArea();
		assertEquals(damageAreaList.getStatusCodeValue(), 500);
	}

	@Test
	void testDeleteDamageAreaException() {
		DamageArea damageArea1 = new DamageArea();
		damageArea1.setAreaCd(null);

		List<DamageArea> codeList1 = new ArrayList<>();
		codeList1.add(damageArea1);

		List<DamageAreaDTO> damageAreaDTODTOList = new ArrayList<>();
		when(damageAreaMapper.DamageAreaDTOToDamageArea(Mockito.any())).thenReturn(damageArea1);
		damageAreaService.deleteDamageArea(damageArea1);
		when(damageAreaMapper.DamageAreaToDamageAreaDTO(damageArea1)).thenReturn(damageAreaDto);
		ResponseEntity<List<APIResponse<DamageAreaDTO>>> deleteList = damageAreaController
				.deleteDamageArea(damageAreaDTODTOList);
		assertEquals(deleteList.getStatusCodeValue(), 500);
	}

	@Test
	void testDamageAreaNoRecordsFoundException() {
		when(damageAreaService.getAllDamageArea()).thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<List<DamageAreaDTO>>> damageAreaList = damageAreaController.getAllDamageArea();
		assertEquals(damageAreaList.getStatusCodeValue(), 404);
	}

	@Test
	void testAddDamageArea() {
		when(damageAreaService.addDamageArea(Mockito.any(), Mockito.any())).thenReturn(damageArea);
		ResponseEntity<APIResponse<DamageAreaDTO>> data = damageAreaController.addDamageArea(damageAreaDto, header);
	}

	@Test
	void testDeleteDamageArea() {
		when(damageAreaMapper.DamageAreaDTOToDamageArea(Mockito.any())).thenReturn(damageArea);
		damageAreaService.deleteDamageArea(Mockito.any());
		when(damageAreaMapper.DamageAreaToDamageAreaDTO(Mockito.any())).thenReturn(damageAreaDto);
		ResponseEntity<List<APIResponse<DamageAreaDTO>>> responseEntity = damageAreaController
				.deleteDamageArea(damageAreaDtoList);
		assertEquals(responseEntity.getStatusCodeValue(), 200);
	}

	@Test
	void testAddDamageAreaRecordAlreadyExistsException() {
		when(damageAreaService.addDamageArea(Mockito.any(), Mockito.any()))
				.thenThrow(new RecordAlreadyExistsException());
		ResponseEntity<APIResponse<DamageAreaDTO>> data = damageAreaController.addDamageArea(damageAreaDto, header);
	}

	@Test
	void testAddDamageAreaInvalidDataException() {
		when(damageAreaService.addDamageArea(Mockito.any(), Mockito.any())).thenThrow(new InvalidDataException());
		ResponseEntity<APIResponse<DamageAreaDTO>> data = damageAreaController.addDamageArea(damageAreaDto, header);
	}

	@Test
	void testAddDamageAreaException() {
		when(damageAreaService.addDamageArea(Mockito.any(), Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<DamageAreaDTO>> data = damageAreaController.addDamageArea(damageAreaDto, header);
	}
	
	@Test
    void testUpdateDamageArea() {
        when(damageAreaService.updateDamageArea(Mockito.any(), Mockito.any())).thenReturn(damageArea);
        ResponseEntity<APIResponse<DamageAreaDTO>> response = damageAreaController.updateDamageArea(damageAreaDto, header);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testUpdateDamageAreaNoRecordsFoundException() {
        when(damageAreaService.updateDamageArea(Mockito.any(), Mockito.any())).thenThrow(new NoRecordsFoundException());
        ResponseEntity<APIResponse<DamageAreaDTO>> response = damageAreaController.updateDamageArea(damageAreaDto, header);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testUpdateDamageAreaRuntimeException() {
        when(damageAreaService.updateDamageArea(Mockito.any(), Mockito.any())).thenThrow(new RuntimeException());
        ResponseEntity<APIResponse<DamageAreaDTO>> response = damageAreaController.updateDamageArea(damageAreaDto, header);
        assertEquals(500, response.getStatusCodeValue());
    }

}
