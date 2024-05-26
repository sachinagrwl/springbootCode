package com.nscorp.obis.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nscorp.obis.exception.RecordAlreadyExistsException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.nscorp.obis.domain.EndorsementCode;
import com.nscorp.obis.dto.EndorsementCodeDTO;
import com.nscorp.obis.dto.mapper.EndorsementCodeMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.SizeExceedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.EndorsementCodeService;

public class EndorsementCodeControllerTest {

	
	@Mock
	EndorsementCodeService endorsementCodeService;
	
	@Mock
	EndorsementCodeMapper endorsementCodeMapper;
	
	@InjectMocks
	EndorsementCodeController endorsementCodeController;
	
	EndorsementCodeDTO endorsementCodeDTO;
	EndorsementCode endorsementCode;
	List<EndorsementCode> endorsementCodeList;
	List<EndorsementCodeDTO> endorsementCodeDTOList;
	Map<String, String> header;

	String endorsementCd;
	String endorseCdDesc;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		endorsementCodeList = new ArrayList<>();
		endorsementCode = new EndorsementCode();
		endorsementCodeDTO = new EndorsementCodeDTO();
		endorsementCodeDTOList = new ArrayList<>();
		endorsementCode.setEndorsementCd("D4");
		endorsementCode.setEndorseCdDesc("Hello");
		
		endorsementCodeDTO.setEndorsementCd("D4");
		endorsementCodeDTO.setEndorseCdDesc("Hello");
		endorsementCodeDTOList.add(endorsementCodeDTO);
		endorsementCd = "D3";
		endorseCdDesc ="Hello";
		
		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");
	}
	
	@AfterEach
	void tearDown() throws Exception {
		endorsementCode = null;
		endorsementCodeList = null;
		endorsementCodeDTO = null;
		endorsementCodeDTOList = null;
	}
	@Test
	void testGetendorsementCode() {
		when(endorsementCodeService.getAllTables(endorsementCd, endorseCdDesc)).thenReturn(endorsementCodeList);
		ResponseEntity<APIResponse<List<EndorsementCodeDTO>>> result = endorsementCodeController.getAllTables(endorsementCd, endorseCdDesc);
		assertEquals(result.getStatusCodeValue(), 200);
	}
	
	@Test
	void testAddendorsementCode() {
		when(endorsementCodeService.addEndorsementCode(endorsementCode, header)).thenReturn(endorsementCode);
		ResponseEntity<APIResponse<EndorsementCodeDTO>> result = endorsementCodeController.addEndorsementCode(endorsementCodeDTO, header);
		assertEquals(result.getStatusCodeValue(), 200);
	}
	
	@Test
	void testUpdateendorsementCode() {
		when(endorsementCodeService.updateEndorsementCode(endorsementCode, header)).thenReturn(endorsementCode);
		ResponseEntity<APIResponse<EndorsementCodeDTO>> result = endorsementCodeController.updateEndorsementCode(endorsementCodeDTO, header);
		assertEquals(result.getStatusCodeValue(), 200);
	}
	@Test
	void testNoRecordsFoundException() {

		when(endorsementCodeService.getAllTables(endorsementCd, endorseCdDesc)).thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<List<EndorsementCodeDTO>>> result = endorsementCodeController.getAllTables(endorsementCd, endorseCdDesc);
		assertEquals(result.getStatusCodeValue(), 404);
		
		when(endorsementCodeService.addEndorsementCode(Mockito.any(), Mockito.any())).thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<EndorsementCodeDTO>> result1 = endorsementCodeController.addEndorsementCode(endorsementCodeDTO, header);
		assertEquals(result1.getStatusCodeValue(), 404);
		
		when(endorsementCodeService.updateEndorsementCode(Mockito.any(), Mockito.any())).thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<EndorsementCodeDTO>> result2 = endorsementCodeController.updateEndorsementCode(endorsementCodeDTO, header);
		assertEquals(result2.getStatusCodeValue(), 404);
	}
	
	@Test
	void testException() {
		when(endorsementCodeService.getAllTables(endorsementCd, endorseCdDesc)).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<List<EndorsementCodeDTO>>> result =endorsementCodeController.getAllTables(endorsementCd, endorseCdDesc);
		assertEquals(result.getStatusCodeValue(), 500);
		
		when(endorsementCodeService.addEndorsementCode(Mockito.any(), Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<EndorsementCodeDTO>> result1 = endorsementCodeController.addEndorsementCode(endorsementCodeDTO, header);
		assertEquals(result1.getStatusCodeValue(), 500);
		
		when(endorsementCodeService.updateEndorsementCode(Mockito.any(), Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<EndorsementCodeDTO>> result2 = endorsementCodeController.updateEndorsementCode(endorsementCodeDTO, header);
		assertEquals(result2.getStatusCodeValue(), 500);
	}
	
	@Test
	void testSizeExceedException()
	{
		when(endorsementCodeService.addEndorsementCode(Mockito.any(), Mockito.any())).thenThrow(new SizeExceedException());
		ResponseEntity<APIResponse<EndorsementCodeDTO>> result1 = endorsementCodeController.addEndorsementCode(endorsementCodeDTO, header);
		System.out.println(result1.getStatusCodeValue());
		assertEquals(result1.getStatusCodeValue(), 411);
		
		when(endorsementCodeService.updateEndorsementCode(Mockito.any(), Mockito.any())).thenThrow(new SizeExceedException());
		ResponseEntity<APIResponse<EndorsementCodeDTO>> result2 = endorsementCodeController.updateEndorsementCode(endorsementCodeDTO, header);
		assertEquals(result2.getStatusCodeValue(), 411);
	}
	
	@Test
	void testNullPointerException() {
		when(endorsementCodeService.addEndorsementCode(Mockito.any(), Mockito.any())).thenThrow(new NullPointerException());
		ResponseEntity<APIResponse<EndorsementCodeDTO>> result1 = endorsementCodeController.addEndorsementCode(endorsementCodeDTO, header);
		System.out.println(result1.getStatusCodeValue());
		assertEquals(result1.getStatusCodeValue(), 400);
		
		when(endorsementCodeService.updateEndorsementCode(Mockito.any(), Mockito.any())).thenThrow(new NullPointerException());
		ResponseEntity<APIResponse<EndorsementCodeDTO>> result2 = endorsementCodeController.updateEndorsementCode(endorsementCodeDTO, header);
		assertEquals(result2.getStatusCodeValue(), 400);
	}
	@Test
	void testRecordAlreadyExistsException(){
		when(endorsementCodeService.addEndorsementCode(Mockito.any(), Mockito.any())).thenThrow(new RecordAlreadyExistsException());
		ResponseEntity<APIResponse<EndorsementCodeDTO>> result1 = endorsementCodeController.addEndorsementCode(endorsementCodeDTO, header);
	}

	@Test
	void testDeleteEndorsementCode() {
		endorsementCodeService.deleteEndorsementCode(Mockito.any());
		ResponseEntity<List<APIResponse<EndorsementCodeDTO>>> deleteList = endorsementCodeController.deleteEndorsementCode(endorsementCodeDTOList);
		assertEquals(deleteList.getStatusCodeValue(), 200);
		
		endorsementCodeService.deleteEndorsementCode(Mockito.any());
		ResponseEntity<List<APIResponse<EndorsementCodeDTO>>> deleteList1 = endorsementCodeController.deleteEndorsementCode(null);
		assertEquals(deleteList1.getStatusCodeValue(), 200);
		
		endorsementCodeService.deleteEndorsementCode(Mockito.any());
		ResponseEntity<List<APIResponse<EndorsementCodeDTO>>> deleteList2 = endorsementCodeController.deleteEndorsementCode(Collections.emptyList());
		assertEquals(deleteList2.getStatusCodeValue(), 200);
	}
	
	@Test
	void testErrorDeleteEndorsementCode() {
		EndorsementCode endorsementCode = new EndorsementCode();
		endorsementCode.setEndorsementCd("QA");
	
		
		List<EndorsementCode> codeList1 = new ArrayList<>();
		codeList1.add(endorsementCode);

		List<EndorsementCodeDTO> endorsementCodeDTODTOList = new ArrayList<>();
		
		when(endorsementCodeMapper.endorsementCodeDTOToEndorsementCode(Mockito.any())).thenReturn(endorsementCode);
		endorsementCodeService.deleteEndorsementCode(endorsementCode);
		when(endorsementCodeMapper.endorsementCodeToEndorsementCodeDTO(Mockito.any())).thenReturn(endorsementCodeDTO);
		
		ResponseEntity<List<APIResponse<EndorsementCodeDTO>>> deleteList = endorsementCodeController.deleteEndorsementCode(endorsementCodeDTODTOList);
		assertEquals(deleteList.getStatusCodeValue(), 500);
	}
}
