package com.nscorp.obis.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

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

import com.nscorp.obis.domain.DrayageScac;
import com.nscorp.obis.dto.DrayageScacDTO;
import com.nscorp.obis.dto.mapper.DrayageScacMapper;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.repository.DrayageSCACRepository;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.PaginatedResponse;
import com.nscorp.obis.services.DrayageScacServiceImpl;

public class DrayageScacControllerTest {

	@Mock
	DrayageSCACRepository drayageSCACRepository;

	@Mock
	DrayageScacServiceImpl drayageScacServiceImpl;

	@Mock
	DrayageScacMapper drayageScacMapper;

	@InjectMocks
	DrayageScacController drayageScacController;

	DrayageScacDTO drayageScacDTO;
	DrayageScac drayageScac;

	List<DrayageScacDTO> drayageScacDTOs;
	Map<String, String> header;

	PaginatedResponse<DrayageScacDTO> paginatedResponse;

	String[] sort = { "drayId", "asc" };

	String drayId;
	String carrierName;
	String carrierCity;
	String state;
	Integer pageNumber;
	Integer pageSize;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		drayageScacDTO = new DrayageScacDTO();
		drayageScacDTO.setDrayId("Test");
		drayageScac = new DrayageScac();

		paginatedResponse = new PaginatedResponse<>();
		drayId = "Test";
		carrierName = "test";
		carrierCity = "city";
		state = "NA";
		pageNumber = 0;
		pageSize = 15;
		header=new HashMap<>();
		header.put("userid", "test");
		header.put("extensionschema", "test");

	}

	@AfterEach
	void tearDown() {
		drayageScacDTO = null;
		drayageScac = null;
		paginatedResponse = null;
	}

	@Test
	void testGetDrayageScacList() {
		when(drayageScacServiceImpl.getDrayageScac(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
				Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt(), Mockito.any())).thenReturn(paginatedResponse);
		ResponseEntity<APIResponse<PaginatedResponse<DrayageScacDTO>>> responseEntity = drayageScacController
				.getDrayageScacList(drayId, carrierName, carrierCity, state, pageNumber, pageSize, sort);
		assertEquals(responseEntity.getStatusCodeValue(), 200);
	}
	
	@Test
	void testAddDrayageScac() {
		drayageScacDTO.setPhoneNumber("1001018999");
		when(drayageScacMapper.drayageScacToDrayageDto(Mockito.any())).thenReturn(drayageScacDTO);
		when(drayageScacMapper.drayageScacDtoToDrayageScac(Mockito.any())).thenReturn(drayageScac);
		when(drayageScacServiceImpl.addDrayageScac(Mockito.any(), Mockito.any())).thenReturn(drayageScac);
		ResponseEntity<APIResponse<DrayageScacDTO>> addedCustomer = drayageScacController.addDrayageScac(drayageScacDTO, header);
		assertEquals(addedCustomer.getStatusCodeValue(), 200);
	}

	@Test
	void testUpdateDrayageScac() {
		when(drayageScacServiceImpl.updateDrayageScac(Mockito.any(), Mockito.any())).thenReturn(drayageScacDTO);
		ResponseEntity<APIResponse<DrayageScacDTO>> updatedCustomer = drayageScacController.updateDrayageScac(drayageScacDTO, header);
		assertEquals(updatedCustomer.getStatusCodeValue(), 200);
	}

	@Test
	void testDeleteDrayageScac() {
		when(drayageScacServiceImpl.deleteDrayageScac(Mockito.any())).thenReturn(drayageScacDTO);
		ResponseEntity<APIResponse<DrayageScacDTO>> responseEntity = drayageScacController.deleteDrayageScac(drayageScacDTO);
		assertEquals(responseEntity.getStatusCodeValue(), 200);
	}

	@Test
	void testNoRecordsException() {
		when(drayageScacServiceImpl.getDrayageScac(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
				Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt(), Mockito.any())).thenThrow(NoRecordsFoundException.class);
		ResponseEntity<APIResponse<PaginatedResponse<DrayageScacDTO>>> exception = drayageScacController
				.getDrayageScacList(drayId, carrierName, carrierCity, state, pageNumber, pageSize, sort);
		assertEquals(exception.getStatusCodeValue(), 404);
		when(drayageScacServiceImpl.deleteDrayageScac(Mockito.any())).thenThrow(NoRecordsFoundException.class);
		ResponseEntity<APIResponse<DrayageScacDTO>> responseEntity = drayageScacController.deleteDrayageScac(drayageScacDTO);
		assertEquals(responseEntity.getStatusCodeValue(), 404);

		when(drayageScacServiceImpl.addDrayageScac(Mockito.any(),Mockito.any())).thenThrow(NoRecordsFoundException.class);
		ResponseEntity<APIResponse<DrayageScacDTO>> addResponseEntity = drayageScacController.addDrayageScac(drayageScacDTO, header);
		assertEquals(addResponseEntity.getStatusCodeValue(), 404);

		when(drayageScacServiceImpl.updateDrayageScac(Mockito.any(),Mockito.any())).thenThrow(NoRecordsFoundException.class);
		ResponseEntity<APIResponse<DrayageScacDTO>> updateResponseEntity = drayageScacController.updateDrayageScac(drayageScacDTO, header);
		assertEquals(updateResponseEntity.getStatusCodeValue(), 404);

	}

	@Test
	void testRecordNotAddedException(){
		when(drayageScacServiceImpl.addDrayageScac(Mockito.any(),Mockito.any())).thenThrow(RecordNotAddedException.class);
		ResponseEntity<APIResponse<DrayageScacDTO>> addResponseEntity = drayageScacController.addDrayageScac(drayageScacDTO, header);
		assertEquals(addResponseEntity.getStatusCodeValue(), 404);
	}
	@Test
	void testRecordAlreadyExistsException(){
		when(drayageScacServiceImpl.addDrayageScac(Mockito.any(),Mockito.any())).thenThrow(RecordAlreadyExistsException.class);
		ResponseEntity<APIResponse<DrayageScacDTO>> addResponseEntity = drayageScacController.addDrayageScac(drayageScacDTO, header);
		assertEquals(addResponseEntity.getStatusCodeValue(), 404);
	}
	
	@Test
	void testInvalidDataException(){
		when(drayageScacServiceImpl.updateDrayageScac(Mockito.any(),Mockito.any())).thenThrow(InvalidDataException.class);
		ResponseEntity<APIResponse<DrayageScacDTO>> updateResponseEntity = drayageScacController.updateDrayageScac(drayageScacDTO, header);
		assertEquals(updateResponseEntity.getStatusCodeValue(), 404);
	}
	@Test
	void testException() {
		when(drayageScacServiceImpl.getDrayageScac(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
				Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt(), Mockito.any())).thenThrow(InvalidDataException.class);
		ResponseEntity<APIResponse<PaginatedResponse<DrayageScacDTO>>> exception2 = drayageScacController
				.getDrayageScacList(drayId, carrierName, carrierCity, state, pageNumber, pageSize, sort);
		assertEquals(exception2.getStatusCodeValue(), 500);
		when(drayageScacServiceImpl.deleteDrayageScac(Mockito.any())).thenThrow(InvalidDataException.class);
		ResponseEntity<APIResponse<DrayageScacDTO>> responseEntity = drayageScacController.deleteDrayageScac(drayageScacDTO);
		assertEquals(responseEntity.getStatusCodeValue(), 500);

		when(drayageScacServiceImpl.addDrayageScac(Mockito.any(),Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<DrayageScacDTO>> addResponseEntity = drayageScacController.addDrayageScac(drayageScacDTO, header);
		assertEquals(addResponseEntity.getStatusCodeValue(), 500);

		when(drayageScacServiceImpl.updateDrayageScac(Mockito.any(),Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<DrayageScacDTO>>updateResponseEntity = drayageScacController.updateDrayageScac(drayageScacDTO, header);
		assertEquals(updateResponseEntity.getStatusCodeValue(), 500);
	}

	@Test
	void testInvalidDataExceptionForAdd(){
		drayageScacDTO.setPhoneNumber("0001001001");
		when(drayageScacServiceImpl.addDrayageScac(Mockito.any(),Mockito.any())).thenThrow(InvalidDataException.class);
		ResponseEntity<APIResponse<DrayageScacDTO>> addResponseEntity = drayageScacController.addDrayageScac(drayageScacDTO, header);
		assertEquals(addResponseEntity.getStatusCodeValue(), 400);
	}

}
