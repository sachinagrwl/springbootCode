package com.nscorp.obis.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
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

import com.nscorp.obis.domain.EquipmentAAR600Cont;
import com.nscorp.obis.dto.EquipmentAAR600ContDTO;
import com.nscorp.obis.dto.mapper.EquipmentAAR600ContMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.SizeExceedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.EquipmentAAR600ContService;

class EquipmentAAR600ContControllerTest {
	
	@Mock
	EquipmentAAR600ContService eqAARContService;
	
	@Mock
	EquipmentAAR600ContMapper eqAARContMapper;
	
	@InjectMocks
	EquipmentAAR600ContController eqAARContController;
	
	EquipmentAAR600Cont eqAARCont;
	EquipmentAAR600ContDTO eqAARContDto;
	List<EquipmentAAR600Cont> eqAARContList;
	List<EquipmentAAR600ContDTO> eqAARContDtoList;
	
	Map<String, String> header;
	
	BigDecimal beginningEqNr = new BigDecimal(1000);
	BigDecimal endEqNr = new BigDecimal(1000);

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		
		MockitoAnnotations.initMocks(this);
		eqAARCont = new EquipmentAAR600Cont();
		eqAARCont.setEquipInit("APUL");
		eqAARCont.setBeginningEqNr(beginningEqNr);
		eqAARCont.setEndEqNbr(endEqNr);
		
		eqAARContList = new ArrayList<>();
		eqAARContList.add(eqAARCont);
		
		eqAARContDto = new EquipmentAAR600ContDTO();
		eqAARContDto.setEquipInit("APUL");
		eqAARContDto.setBeginningEqNr(beginningEqNr);
		eqAARContDto.setEndEqNbr(endEqNr);
		
		eqAARContDtoList = new ArrayList<>();
		eqAARContDtoList.add(eqAARContDto);
		
		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");
		
	}

	@AfterEach
	void tearDown() throws Exception {
		eqAARCont = null;
		eqAARContList = null;
		eqAARContDto = null;
		eqAARContDtoList = null;
	}

	@Test
	void testGetAllAarCont() {
		when(eqAARContService.getAllCont()).thenReturn(eqAARContList);
		ResponseEntity<APIResponse<List<EquipmentAAR600ContDTO>>> getCont = eqAARContController.getAllAarCont();
		assertEquals(getCont.getStatusCodeValue(), 200);
	}

	@Test
	void testAddEqCont() {
		when(eqAARContMapper.EquipmentAAR600ContDTOToEquipmentAAR600Cont(Mockito.any())).thenReturn(eqAARCont);
		when(eqAARContService.addEqCont(Mockito.any(), Mockito.any())).thenReturn(eqAARCont);
		when(eqAARContMapper.EquipmentAAR600ContToEquipmentAAR600ContDTO(Mockito.any())).thenReturn(eqAARContDto);
		ResponseEntity<APIResponse<EquipmentAAR600ContDTO>> addedCont = eqAARContController.addEqCont(eqAARContDto, header);
		assertEquals(addedCont.getStatusCodeValue(),200);
	}
	
	@Test
	void testAddEqContRecordAlreadyExistsException() {
		when(eqAARContService.addEqCont(Mockito.any(),Mockito.any())).thenThrow(new RecordAlreadyExistsException());
		ResponseEntity<APIResponse<EquipmentAAR600ContDTO>> addCont = eqAARContController.addEqCont(Mockito.any(),Mockito.any());
		assertEquals(addCont.getStatusCodeValue(),208);
	}

	@Test
	void testAddEqContRecordNotAddedException() {
		when(eqAARContService.addEqCont(Mockito.any(),Mockito.any())).thenThrow(new RecordNotAddedException());
		ResponseEntity<APIResponse<EquipmentAAR600ContDTO>> addCont = eqAARContController.addEqCont(Mockito.any(),Mockito.any());
		assertEquals(addCont.getStatusCodeValue(),406);
	}

	@Test
	void testAddEqContNoRecordsFoundException() {
		when(eqAARContService.getAllCont()).thenThrow(new NoRecordsFoundException());
		when(eqAARContService.addEqCont(Mockito.any(),Mockito.any())).thenThrow(new NoRecordsFoundException());
		
		ResponseEntity<APIResponse<List<EquipmentAAR600ContDTO>>> getCont = eqAARContController.getAllAarCont();
		ResponseEntity<APIResponse<EquipmentAAR600ContDTO>> addCont = eqAARContController.addEqCont(Mockito.any(),Mockito.any());
		
		assertEquals(getCont.getStatusCodeValue(),404);
		assertEquals(addCont.getStatusCodeValue(),404);
	}
	
	@Test
	void testAddEqContRuntimeException() {
		when(eqAARContService.getAllCont()).thenThrow(new RuntimeException());
		when(eqAARContService.addEqCont(Mockito.any(),Mockito.any())).thenThrow(new RuntimeException());
		
		ResponseEntity<APIResponse<List<EquipmentAAR600ContDTO>>> getCont = eqAARContController.getAllAarCont();
		ResponseEntity<APIResponse<EquipmentAAR600ContDTO>> addCont = eqAARContController.addEqCont(Mockito.any(),Mockito.any());
		
		assertEquals(getCont.getStatusCodeValue(),500);
		assertEquals(addCont.getStatusCodeValue(),500);
	}
	
	@Test
	void testAddEqContNullPointerException() {
		when(eqAARContService.addEqCont(Mockito.any(),Mockito.any())).thenThrow(new NullPointerException());
		ResponseEntity<APIResponse<EquipmentAAR600ContDTO>> addCont = eqAARContController.addEqCont(Mockito.any(),Mockito.any());
		assertEquals(addCont.getStatusCodeValue(),400);
	}
	
	@Test
	void testAddEqContSizeExceedException() {
		when(eqAARContService.addEqCont(Mockito.any(),Mockito.any())).thenThrow(new SizeExceedException());
		ResponseEntity<APIResponse<EquipmentAAR600ContDTO>> addCont = eqAARContController.addEqCont(Mockito.any(),Mockito.any());
		assertEquals(addCont.getStatusCodeValue(),411);
	}

	@Test
	void testDeleteEqCont() {
		when(eqAARContMapper.EquipmentAAR600ContDTOToEquipmentAAR600Cont(Mockito.any())).thenReturn(eqAARCont);
		eqAARContService.deleteEqCont(Mockito.any());
		when(eqAARContMapper.EquipmentAAR600ContToEquipmentAAR600ContDTO(Mockito.any())).thenReturn(eqAARContDto);
		ResponseEntity<List<APIResponse<EquipmentAAR600ContDTO>>> deleteList = eqAARContController.deleteEqCont(eqAARContDtoList);
		assertEquals(deleteList.getStatusCodeValue(),200);
	}

	@Test
	void testErrorDeleteEqCont() {
		EquipmentAAR600Cont eqAAR600cont = new EquipmentAAR600Cont();
//		codeTableSelection1.setGenericTable("Testin");
//		codeTableSelection1.setGenericTableDesc("Testin");
//		codeTableSelection1.setGenCdFldSize((short) 1);
//		codeTableSelection1.setResourceNm("Testin");
		List<EquipmentAAR600Cont> eqAAR600contList = new ArrayList<>();
		eqAAR600contList.add(eqAAR600cont);

		EquipmentAAR600ContDTO equipmentAAR600ContDTO = new EquipmentAAR600ContDTO();
		List<EquipmentAAR600ContDTO> equipmentAAR600ContDTOList = new ArrayList<>();

//        Mockito.doThrow(new RuntimeException()).when(equipmentDefaultTareWeightMaintenanceService).deleteWeight(Mockito.any());
		when(eqAARContMapper.EquipmentAAR600ContDTOToEquipmentAAR600Cont(Mockito.any())).thenReturn(eqAAR600cont);
		eqAARContService.deleteEqCont(eqAAR600cont);
		when(eqAARContMapper.EquipmentAAR600ContToEquipmentAAR600ContDTO(Mockito.any())).thenReturn(equipmentAAR600ContDTO);
		ResponseEntity<List<APIResponse<EquipmentAAR600ContDTO>>> deleteList = eqAARContController.deleteEqCont(equipmentAAR600ContDTOList);
		assertEquals(deleteList.getStatusCodeValue(),500);
	}

}
