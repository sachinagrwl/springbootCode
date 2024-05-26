package com.nscorp.obis.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nscorp.obis.dto.CarEmbargoDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.nscorp.obis.domain.AARLocationCode;
import com.nscorp.obis.dto.AARLocationCodeDTO;
import com.nscorp.obis.dto.mapper.AARLocationCodeMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.AARLocationCodeService;

public class AARLocationCodeControllerTest {

	@Mock
	AARLocationCodeService aarLocationCodeService;

	@Mock
	AARLocationCodeMapper aarLocationCodeMapper;

	@InjectMocks
	AARLocationCodeController aarLocationCodeController;

	AARLocationCode aarLocationCode;
	AARLocationCodeDTO aarLocationCodeDTO;
	List<AARLocationCode> aarLocationCodeList;
	List<AARLocationCodeDTO> aarLocationCodeDTOList;
	Map<String, String> header;

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		aarLocationCode = new AARLocationCode();
		aarLocationCode.setLocCd("QWE");
		aarLocationCode.setLocDesc("Hello");

		aarLocationCodeList = new ArrayList<>();

		aarLocationCodeList.add(aarLocationCode);

		aarLocationCodeDTO = new AARLocationCodeDTO();

		aarLocationCodeDTOList = new ArrayList<>();
		aarLocationCodeDTOList.add(aarLocationCodeDTO);

		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");

	}

	@AfterEach
	void tearDown() throws Exception {
		aarLocationCode = null;
		aarLocationCodeList = null;
		aarLocationCodeDTO = null;
	}

	@Test
	void getAllAARCodes() {
		when(aarLocationCodeService.getAllAARLocationCodes()).thenReturn(aarLocationCodeList);
		ResponseEntity<APIResponse<List<AARLocationCodeDTO>>> getCarEmbargo = aarLocationCodeController.getAARLocationCodes("C");
		assertEquals(getCarEmbargo.getStatusCodeValue(),200);
	}

	@Test
	void testAddAARLocationCode() {
		when(aarLocationCodeMapper.aarLocationCodeDTOToAARLocationCode(Mockito.any())).thenReturn(aarLocationCode);
		when(aarLocationCodeService.addAARLocationCode(Mockito.any(), Mockito.any())).thenReturn(aarLocationCode);
		when(aarLocationCodeMapper.aarLocationCodeToAARLocationCodeDTO(Mockito.any())).thenReturn(aarLocationCodeDTO);
		ResponseEntity<APIResponse<AARLocationCodeDTO>> addedAARLocationCode = aarLocationCodeController
				.addAARLocationCode(aarLocationCodeDTO, header);
		assertNotNull(addedAARLocationCode.getBody());
	}

	@Test
	void testUpdateAARLocationCode() {
		when(aarLocationCodeMapper.aarLocationCodeDTOToAARLocationCode(Mockito.any())).thenReturn(aarLocationCode);
		when(aarLocationCodeService.updateAARLocationCode(Mockito.any(), Mockito.any())).thenReturn(aarLocationCode);
		when(aarLocationCodeMapper.aarLocationCodeToAARLocationCodeDTO(Mockito.any())).thenReturn(aarLocationCodeDTO);
		ResponseEntity<APIResponse<AARLocationCodeDTO>> updateAARLocationCode = aarLocationCodeController
				.updateAARLocationCode(aarLocationCodeDTO, header);
		assertNotNull(updateAARLocationCode.getBody());
	}

	@Test
	void testAARLocationCodeException() {
		when(aarLocationCodeService.getAllAARLocationCodes()).thenThrow(new RuntimeException());
		when(aarLocationCodeService.getAARLocationCodesByLocCode(Mockito.anyString())).thenThrow(new RuntimeException());
		when(aarLocationCodeService.updateAARLocationCode(Mockito.any(), Mockito.any()))
				.thenThrow(new RuntimeException());

		ResponseEntity<APIResponse<AARLocationCodeDTO>> addedAARLocationCode = aarLocationCodeController
				.updateAARLocationCode(Mockito.any(), Mockito.any());
		assertEquals(addedAARLocationCode.getStatusCodeValue(), 500);

		when(aarLocationCodeService.addAARLocationCode(Mockito.any(), Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<AARLocationCodeDTO>> updateAARLocationCode = aarLocationCodeController
				.addAARLocationCode(Mockito.any(), Mockito.any());
		assertEquals(updateAARLocationCode.getStatusCodeValue(), 500);

		ResponseEntity<APIResponse<List<AARLocationCodeDTO>>> getCodeList = aarLocationCodeController
				.getAARLocationCodes(Mockito.any());
		assertEquals(getCodeList.getStatusCodeValue(), 500);

		ResponseEntity<APIResponse<List<AARLocationCodeDTO>>> getCode = aarLocationCodeController
				.getAARLocationCodes((Mockito.any()));
		assertEquals(getCodeList.getStatusCodeValue(), 500);

	}

	@Test
	void testAARLocationCodeNoRecordsFoundException() {
		when(aarLocationCodeService.getAllAARLocationCodes()).thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<List<AARLocationCodeDTO>>> getListCodes = aarLocationCodeController
				.getAARLocationCodes(Mockito.any());
		assertEquals(getListCodes.getStatusCodeValue(), 404);

		when(aarLocationCodeService.getAARLocationCodesByLocCode(Mockito.any())).thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<List<AARLocationCodeDTO>>> getCodes = aarLocationCodeController
				.getAARLocationCodes(Mockito.any());
		assertEquals(getListCodes.getStatusCodeValue(), 404);


		when(aarLocationCodeService.updateAARLocationCode(Mockito.any(), Mockito.any()))
				.thenThrow(new NoRecordsFoundException());

		ResponseEntity<APIResponse<AARLocationCodeDTO>> addedAARLocationCode = aarLocationCodeController
				.updateAARLocationCode(Mockito.any(), Mockito.any());
		assertEquals(addedAARLocationCode.getStatusCodeValue(), 404);

		when(aarLocationCodeService.addAARLocationCode(Mockito.any(), Mockito.any()))
				.thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<AARLocationCodeDTO>> updateAARLocationCode = aarLocationCodeController
				.addAARLocationCode(Mockito.any(), Mockito.any());
		assertEquals(updateAARLocationCode.getStatusCodeValue(), 404);
	}

	@Test
	void testAarTypRecordAlreadyExistsException() {
		when(aarLocationCodeService.updateAARLocationCode(Mockito.any(), Mockito.any()))
				.thenThrow(new RecordAlreadyExistsException());
		ResponseEntity<APIResponse<AARLocationCodeDTO>> addedAARLocationCode = aarLocationCodeController
				.updateAARLocationCode(Mockito.any(), Mockito.any());
		assertEquals(addedAARLocationCode.getStatusCodeValue(), 208);

		when(aarLocationCodeService.addAARLocationCode(Mockito.any(), Mockito.any()))
				.thenThrow(new RecordAlreadyExistsException());
		ResponseEntity<APIResponse<AARLocationCodeDTO>> updateAARLocationCode = aarLocationCodeController
				.addAARLocationCode(Mockito.any(), Mockito.any());
		assertEquals(updateAARLocationCode.getStatusCodeValue(), 208);
	}
}
