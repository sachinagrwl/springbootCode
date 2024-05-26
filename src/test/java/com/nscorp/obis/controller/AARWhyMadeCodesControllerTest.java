package com.nscorp.obis.controller;

import static org.junit.Assert.assertNotNull;
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

import com.nscorp.obis.domain.AARWhyMadeCodes;
import com.nscorp.obis.dto.AARWhyMadeCodesDTO;
import com.nscorp.obis.dto.mapper.AARWhyMadeCodesMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.AARWhyMadeCodesService;

public class AARWhyMadeCodesControllerTest {

	@Mock
	AARWhyMadeCodesService aarWhyMadeCodesService;

	@Mock
	AARWhyMadeCodesMapper aarWhyMadeCodesMapper;

	@InjectMocks
	AARWhyMadeCodesController aarWhyMadeCodesController;

	AARWhyMadeCodes aarWhyMadeCodes;
	AARWhyMadeCodesDTO aarWhyMadeCodesDto;
	List<AARWhyMadeCodes> aarWhyMadeCodesList;
	List<AARWhyMadeCodesDTO> aarWhyMadeCodesDtoList;
	Map<String, String> header;

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		aarWhyMadeCodes = new AARWhyMadeCodes();
		aarWhyMadeCodes.setAarDesc("TEST");
		aarWhyMadeCodes.setAarWhyMadeCd(12);

		aarWhyMadeCodesList = new ArrayList<>();

		aarWhyMadeCodesList.add(aarWhyMadeCodes);

		aarWhyMadeCodesDto = new AARWhyMadeCodesDTO();
		aarWhyMadeCodesDto.setAarWhyMadeCd(12);
		aarWhyMadeCodesDto.setAarDesc("TEST");

		aarWhyMadeCodesDtoList = new ArrayList<>();
		aarWhyMadeCodesDtoList.add(aarWhyMadeCodesDto);

		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");

	}

	@AfterEach
	void tearDown() throws Exception {
		aarWhyMadeCodes = null;
		aarWhyMadeCodesList = null;
		aarWhyMadeCodesDtoList = null;
		aarWhyMadeCodesDto = null;
	}

	@Test
	void testGetAllAARWhyMadeCodes() {
		when(aarWhyMadeCodesService.getAARWhyMadeCodes()).thenReturn(aarWhyMadeCodesList);
		ResponseEntity<APIResponse<List<AARWhyMadeCodesDTO>>> aarWhyMadeCodeList = aarWhyMadeCodesController
				.getAllAARWhyMadeCodes();
		assertEquals(aarWhyMadeCodeList.getStatusCodeValue(), 200);
	}

	@Test
	void testGetAllAARWhyMadeCodesNoRecordsFoundException() {
		when(aarWhyMadeCodesService.getAARWhyMadeCodes()).thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<List<AARWhyMadeCodesDTO>>> aarWhyMadeCodeList = aarWhyMadeCodesController
				.getAllAARWhyMadeCodes();
		assertEquals(aarWhyMadeCodeList.getStatusCodeValue(), 404);
	}

	@Test
	void testAARWhyMadeCodesException() {
		when(aarWhyMadeCodesService.getAARWhyMadeCodes()).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<List<AARWhyMadeCodesDTO>>> aarWhyMadeCodeList = aarWhyMadeCodesController
				.getAllAARWhyMadeCodes();
		assertEquals(aarWhyMadeCodeList.getStatusCodeValue(), 500);
		
		when(aarWhyMadeCodesService.updateAARWhyMadeCodes(Mockito.any(), Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<AARWhyMadeCodesDTO>> updateAARWhyMadeCodes = aarWhyMadeCodesController.updateAARWhyMadeCodes(aarWhyMadeCodesDto, header);
		assertEquals(updateAARWhyMadeCodes.getStatusCodeValue(), 500);

		when(aarWhyMadeCodesService.addAARWhyMadeCodes(Mockito.any(), Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<AARWhyMadeCodesDTO>> addAARWhyMadeCodes = aarWhyMadeCodesController.addAARWhyMadeCodes(aarWhyMadeCodesDto, header);
		assertEquals(addAARWhyMadeCodes.getStatusCodeValue(), 500);
		
		AARWhyMadeCodesDTO aarDto = new AARWhyMadeCodesDTO(); 
		List<AARWhyMadeCodesDTO> aarWhyMadeDTOList = new ArrayList<>();

		
		when(aarWhyMadeCodesMapper.aarWhyMadeCodesDTOToAARWhyMadeCodes(Mockito.any())).thenReturn(aarWhyMadeCodes);
		aarWhyMadeCodesService.deleteAARWhyMadeCodes(Mockito.any());
		when(aarWhyMadeCodesMapper.aarWhyMadeCodesToAARWhyMadeCodesDTO(Mockito.any())).thenReturn(aarDto);
		ResponseEntity<List<APIResponse<AARWhyMadeCodesDTO>>> deleteList = aarWhyMadeCodesController.deleteAARWhyMadeCodes(aarWhyMadeDTOList);
		assertEquals(deleteList.getStatusCodeValue(), 500);
	}

	@Test
	void testUpdateAARWhyMadeCodes() {
		when(aarWhyMadeCodesMapper.aarWhyMadeCodesDTOToAARWhyMadeCodes(Mockito.any())).thenReturn(aarWhyMadeCodes);
		when(aarWhyMadeCodesService.updateAARWhyMadeCodes(Mockito.any(), Mockito.any())).thenReturn(aarWhyMadeCodes);
		when(aarWhyMadeCodesMapper.aarWhyMadeCodesToAARWhyMadeCodesDTO(Mockito.any())).thenReturn(aarWhyMadeCodesDto);
		ResponseEntity<APIResponse<AARWhyMadeCodesDTO>> updateAARWhyMadeCode = aarWhyMadeCodesController
				.updateAARWhyMadeCodes(aarWhyMadeCodesDto, header);
		assertNotNull(updateAARWhyMadeCode.getBody());
	}

	@Test
	void testUpdateAARWhyMadeCodesNoRecordsFoundException() {
		when(aarWhyMadeCodesService.updateAARWhyMadeCodes(Mockito.any(), Mockito.any()))
				.thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<AARWhyMadeCodesDTO>> updateAARWhyMadeCode = aarWhyMadeCodesController
				.updateAARWhyMadeCodes(aarWhyMadeCodesDto, header);
		assertEquals(updateAARWhyMadeCode.getStatusCodeValue(), 404);
	}

	@Test
	void testAarWhyMadeCodeRecordAlreadyExistsException() {

		when(aarWhyMadeCodesService.updateAARWhyMadeCodes(Mockito.any(), Mockito.any())).thenThrow(new RecordAlreadyExistsException());
		ResponseEntity<APIResponse<AARWhyMadeCodesDTO>> updateAARWhyMadeCode = aarWhyMadeCodesController
				.updateAARWhyMadeCodes(aarWhyMadeCodesDto, header);
		assertEquals(updateAARWhyMadeCode.getStatusCodeValue(), 208);

		when(aarWhyMadeCodesService.addAARWhyMadeCodes(Mockito.any(), Mockito.any())).thenThrow(new RecordAlreadyExistsException());
		ResponseEntity<APIResponse<AARWhyMadeCodesDTO>> addAARWhyMadeCode = aarWhyMadeCodesController
				.addAARWhyMadeCodes(aarWhyMadeCodesDto, header);
		assertEquals(addAARWhyMadeCode.getStatusCodeValue(), 208);
	}
	
	@Test
	void testDeleteAARWhyMadeCode() {
		
		when(aarWhyMadeCodesMapper.aarWhyMadeCodesDTOToAARWhyMadeCodes(Mockito.any())).thenReturn(aarWhyMadeCodes);
		aarWhyMadeCodesService.deleteAARWhyMadeCodes(Mockito.any());
		when(aarWhyMadeCodesMapper.aarWhyMadeCodesToAARWhyMadeCodesDTO(Mockito.any())).thenReturn(aarWhyMadeCodesDto);
		ResponseEntity<List<APIResponse<AARWhyMadeCodesDTO>>> deleteList1 = aarWhyMadeCodesController.deleteAARWhyMadeCodes(aarWhyMadeCodesDtoList);
		assertEquals(deleteList1.getStatusCodeValue(), 200);
	
	}

	@Test
	void testAddAARWhyCodes() {
		when(aarWhyMadeCodesMapper.aarWhyMadeCodesToAARWhyMadeCodesDTO(Mockito.any())).thenReturn(aarWhyMadeCodesDto);
		when(aarWhyMadeCodesMapper.aarWhyMadeCodesDTOToAARWhyMadeCodes(Mockito.any())).thenReturn(aarWhyMadeCodes);
		when(aarWhyMadeCodesService.addAARWhyMadeCodes(Mockito.any(), Mockito.any())).thenReturn(aarWhyMadeCodes);

		ResponseEntity<APIResponse<AARWhyMadeCodesDTO>> data = aarWhyMadeCodesController.addAARWhyMadeCodes(aarWhyMadeCodesDto, header);
		assertEquals(data.getStatusCodeValue(), 200);
	}
		
	
}
