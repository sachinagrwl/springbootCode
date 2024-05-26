package com.nscorp.obis.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.nscorp.obis.domain.FhwaStopRange;
import com.nscorp.obis.dto.FhwaStopRangeDTO;
import com.nscorp.obis.dto.mapper.FhwaStopRangeMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.SizeExceedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.FhwaStopRangeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.*;

class FhwaStopRangeControllerTest {

	@InjectMocks
	FhwaStopRangeController fhwaStopRangeController;

	@Mock
	FhwaStopRangeMapper fhwaStopRangeMapper;

	@Mock
	FhwaStopRangeService fhwaStopRangeService;

	FhwaStopRange fhwaStopRange;
	FhwaStopRangeDTO fhwaStopRangeDTO;
	List<FhwaStopRange> fhwaStopRangeList;
	List<FhwaStopRangeDTO> fhwaStopRangeDTOList;

	Map<String, String> header;

	BigDecimal eqNrLow = new BigDecimal(1000);
	BigDecimal eqNrHigh = new BigDecimal(1000);
	String eqInit;
	String eqType;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		fhwaStopRange = new FhwaStopRange();
		fhwaStopRange.setEquipmentInit("TEST");
		fhwaStopRange.setEquipmentNumberLow(eqNrLow);
		fhwaStopRange.setEquipmentNumberHigh(eqNrHigh);
		fhwaStopRange.setEquipmentType("T");

		fhwaStopRangeList = new ArrayList<>();
		fhwaStopRangeList.add(fhwaStopRange);

		fhwaStopRangeDTO = new FhwaStopRangeDTO();
		fhwaStopRangeDTO.setEquipmentInit("TEST");
		fhwaStopRangeDTO.setEquipmentNumberLow(eqNrLow);
		fhwaStopRangeDTO.setEquipmentNumberHigh(eqNrHigh);
		fhwaStopRangeDTO.setEquipmentType("T");

		fhwaStopRangeDTOList = new ArrayList<>();
		fhwaStopRangeDTOList.add(fhwaStopRangeDTO);

		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");
	}

	@AfterEach
	void tearDown() throws Exception {
		fhwaStopRangeDTO = null;
		fhwaStopRangeDTOList = null;
		fhwaStopRangeList = null;
		fhwaStopRange = null;
	}

	@Test
	void testGetAllRanges() {
		when(fhwaStopRangeService.getAllFhwaStopRanges(eqType, eqInit, eqNrLow, eqNrHigh)).thenReturn(fhwaStopRangeList);
		ResponseEntity<APIResponse<List<FhwaStopRangeDTO>>> getAllRanges = fhwaStopRangeController.getAllRanges(eqType, eqInit, eqNrLow, eqNrHigh);
		assertEquals(getAllRanges.getStatusCodeValue(), 200);
	}

	@Test
	void testNullListGet(){
		fhwaStopRangeList = Collections.emptyList();
		when(fhwaStopRangeService.getAllFhwaStopRanges(eqType, eqInit, eqNrLow, eqNrHigh)).thenReturn(fhwaStopRangeList);
		assertEquals(Collections.EMPTY_LIST,fhwaStopRangeList);
	}
	@Test
	void testAddRange() {
		when(fhwaStopRangeMapper.fhwaStopRangeDTOToFhwaStopRange(Mockito.any())).thenReturn(fhwaStopRange);
		when(fhwaStopRangeService.addFhwaStopRange(Mockito.any(), Mockito.any())).thenReturn(fhwaStopRange);
		when(fhwaStopRangeMapper.fhwaStopRangeToFhwaStopRangeDTO(Mockito.any())).thenReturn(fhwaStopRangeDTO);
		ResponseEntity<APIResponse<FhwaStopRangeDTO>> addedRange = fhwaStopRangeController.addRange(fhwaStopRangeDTO,
				header);
		assertNotNull(addedRange.getBody());
	}

	@Test
	void testUpdateRange() {
			when(fhwaStopRangeMapper.fhwaStopRangeDTOToFhwaStopRange(Mockito.any())).thenReturn(fhwaStopRange);
			when(fhwaStopRangeService.updateFhwaStopRange(Mockito.any(), Mockito.any())).thenReturn(fhwaStopRange);
			when(fhwaStopRangeMapper.fhwaStopRangeToFhwaStopRangeDTO(Mockito.any())).thenReturn(fhwaStopRangeDTO);
			ResponseEntity<APIResponse<FhwaStopRangeDTO>> updateRange = fhwaStopRangeController.updateRange(fhwaStopRangeDTO, header);
			assertNotNull(updateRange.getBody());
	}

	@Test
	void testDeleteRanges() {
		when(fhwaStopRangeMapper.fhwaStopRangeDTOToFhwaStopRange(any())).thenReturn(fhwaStopRange);
		fhwaStopRangeService.deleteFhwaStopRange(Mockito.any());
		when(fhwaStopRangeMapper.fhwaStopRangeToFhwaStopRangeDTO(any())).thenReturn(fhwaStopRangeDTO);
		ResponseEntity<List<APIResponse<FhwaStopRangeDTO>>> responseEntity = fhwaStopRangeController.deleteRanges(fhwaStopRangeDTOList);
		assertEquals(responseEntity.getStatusCodeValue(),200);
	}

	@Test
	void testRangesNoRecordsFoundException() {
		when(fhwaStopRangeService.getAllFhwaStopRanges(eqType, eqInit, eqNrLow, eqNrHigh)).thenThrow(new NoRecordsFoundException());
		when(fhwaStopRangeService.addFhwaStopRange(Mockito.any(),Mockito.any())).thenThrow(new NoRecordsFoundException());
		when(fhwaStopRangeService.updateFhwaStopRange(Mockito.any(),Mockito.any())).thenThrow(new NoRecordsFoundException());

		ResponseEntity<APIResponse<List<FhwaStopRangeDTO>>> fhwaRangeList = fhwaStopRangeController.getAllRanges(eqType, eqInit, eqNrLow, eqNrHigh);
		ResponseEntity<APIResponse<FhwaStopRangeDTO>> fhwaRangetAdd = fhwaStopRangeController.addRange(Mockito.any(),Mockito.any());
		ResponseEntity<APIResponse<FhwaStopRangeDTO>> fhwaRangeUpdate =  fhwaStopRangeController.updateRange(Mockito.any(),Mockito.any());

		assertEquals(fhwaRangeList.getStatusCodeValue(), 404);
		assertEquals(fhwaRangetAdd.getStatusCodeValue(),404);
		assertEquals(fhwaRangeUpdate.getStatusCodeValue(),404);
	}

	@Test
	void testRangesSizeExceedException() {
		when(fhwaStopRangeService.addFhwaStopRange(Mockito.any(),Mockito.any())).thenThrow(new SizeExceedException());
		when(fhwaStopRangeService.updateFhwaStopRange(Mockito.any(),Mockito.any())).thenThrow(new SizeExceedException("Size Exceed"));

		ResponseEntity<APIResponse<FhwaStopRangeDTO>> fhwaRangetAdd = fhwaStopRangeController.addRange(Mockito.any(),Mockito.any());
		ResponseEntity<APIResponse<FhwaStopRangeDTO>> fhwaRangeUpdate =  fhwaStopRangeController.updateRange(Mockito.any(),Mockito.any());

		assertEquals(fhwaRangetAdd.getStatusCodeValue(),411);
		assertEquals(fhwaRangeUpdate.getStatusCodeValue(),411);
	}

	@Test
	void testRangesRecordAlreadyExistsException() {
		when(fhwaStopRangeService.addFhwaStopRange(Mockito.any(),Mockito.any())).thenThrow(new RecordAlreadyExistsException());
		ResponseEntity<APIResponse<FhwaStopRangeDTO>> fhwaRangetAdd = fhwaStopRangeController.addRange(Mockito.any(),Mockito.any());
		assertEquals(fhwaRangetAdd.getStatusCodeValue(),208);
	}

	@Test
	void testRangesRecordNotAddedException() {
		when(fhwaStopRangeService.addFhwaStopRange(Mockito.any(),Mockito.any())).thenThrow(new RecordNotAddedException());
		ResponseEntity<APIResponse<FhwaStopRangeDTO>> fhwaRangetAdd = fhwaStopRangeController.addRange(Mockito.any(),Mockito.any());
		assertEquals(fhwaRangetAdd.getStatusCodeValue(),406);

		when(fhwaStopRangeService.updateFhwaStopRange(Mockito.any(),Mockito.any())).thenThrow(new RecordNotAddedException());
		ResponseEntity<APIResponse<FhwaStopRangeDTO>> fhwaRangetUpdate = fhwaStopRangeController.updateRange(Mockito.any(),Mockito.any());
		assertEquals(fhwaRangetUpdate.getStatusCodeValue(),406);
	}

	@Test
	void testRangesNullPointerException() {
		when(fhwaStopRangeService.addFhwaStopRange(Mockito.any(),Mockito.any())).thenThrow(new NullPointerException());
		when(fhwaStopRangeService.updateFhwaStopRange(Mockito.any(),Mockito.any())).thenThrow(new NullPointerException("Null pointer"));

		ResponseEntity<APIResponse<FhwaStopRangeDTO>> fhwaRangetAdd = fhwaStopRangeController.addRange(Mockito.any(),Mockito.any());
		ResponseEntity<APIResponse<FhwaStopRangeDTO>> fhwaRangeUpdate =  fhwaStopRangeController.updateRange(Mockito.any(),Mockito.any());

		assertEquals(fhwaRangetAdd.getStatusCodeValue(),400);
		assertEquals(fhwaRangeUpdate.getStatusCodeValue(),400);
	}

	@Test
	void testRangesException() {
		when(fhwaStopRangeService.getAllFhwaStopRanges(eqType, eqInit, eqNrLow, eqNrHigh)).thenThrow(new RuntimeException());
		when(fhwaStopRangeService.addFhwaStopRange(Mockito.any(),Mockito.any())).thenThrow(new RuntimeException());
		when(fhwaStopRangeService.updateFhwaStopRange(Mockito.any(),Mockito.any())).thenThrow(new RuntimeException());

		ResponseEntity<APIResponse<List<FhwaStopRangeDTO>>> fhwaRangeList = fhwaStopRangeController.getAllRanges(eqType, eqInit, eqNrLow, eqNrHigh);
		ResponseEntity<APIResponse<FhwaStopRangeDTO>> fhwaRangetAdd = fhwaStopRangeController.addRange(Mockito.any(),Mockito.any());
		ResponseEntity<APIResponse<FhwaStopRangeDTO>> fhwaRangeUpdate =  fhwaStopRangeController.updateRange(Mockito.any(),Mockito.any());

		assertEquals(fhwaRangeList.getStatusCodeValue(), 500);
		assertEquals(fhwaRangetAdd.getStatusCodeValue(), 500);
		assertEquals(fhwaRangeUpdate.getStatusCodeValue(), 500);
	}

	@Test
	void testDeleteRangesEmptyDTOList(){
		ResponseEntity<List<APIResponse<FhwaStopRangeDTO>>> responseEntity = fhwaStopRangeController.deleteRanges(Collections.EMPTY_LIST);
		assertEquals(responseEntity.getStatusCodeValue(),500);
	}
}
