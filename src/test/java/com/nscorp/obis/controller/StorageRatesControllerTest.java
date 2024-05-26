package com.nscorp.obis.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.nscorp.obis.dto.EquipmentCustomerRangeDTO;
import com.nscorp.obis.dto.StorageRatesDTO;
import com.nscorp.obis.dto.StorageRatesListDTO;
import com.nscorp.obis.dto.mapper.StorageRatesListMapper;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.PaginatedResponse;
import com.nscorp.obis.services.StorageRatesService;

class StorageRatesControllerTest {

	@Mock
	StorageRatesService service;

	@Mock
	StorageRatesListMapper storageRatesListMapper;

	@InjectMocks
	StorageRatesController storageRatesController;

	StorageRatesDTO storageRatesDTO;

	PaginatedResponse<StorageRatesDTO> paginatedResponse;
	PaginatedResponse<EquipmentCustomerRangeDTO> paginatedResponse1;

	String selectRateType = "Shipper";
	String incExpDate;
	String shipPrimSix;
	String customerPrimSix;
	String bnfPrimSix;
	String[] termId;
	String[] equipInit;
	String equipLgth;
	Integer pageSize = 20;
	Integer pageNumber = 0;
	String[] sort = { "termId", "asc" };
	String[] filter = { "termId", "123" };
	Map<String, String> header;
	String forceAdd = "Y";
	StorageRatesListDTO storageRatesListDTO;
	ArrayList<String> loadEmpty;
	ArrayList<String> localInter;
	ArrayList<Long> termIds;
	ArrayList<String> equipInits;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		paginatedResponse = new PaginatedResponse<>();
		storageRatesDTO = new StorageRatesDTO();
		header = new HashMap<>();
		header.put("userid", "test");
		header.put("extensionschema", "test");
		loadEmpty = new ArrayList<>();
		loadEmpty.add("L");
		localInter = new ArrayList<>();
		localInter.add("L");
		storageRatesListDTO = new StorageRatesListDTO();
		storageRatesListDTO.setLdEmptyCds(loadEmpty);
		storageRatesListDTO.setLclInterInds(localInter);
		termIds=new ArrayList<>();
		termIds.add(1234L);
		equipInits=new ArrayList<>();
		equipInits.add("ABCD");
	}

	@AfterEach
	void tearDown() throws Exception {
		storageRatesDTO = null;
		storageRatesListDTO = null;
		header = null;
	}

	@Test
	void testGetStorageRates() throws SQLException {
		when(service.fetchStorageRates(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),
				Mockito.any(), Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.anyInt(), Mockito.any(),Mockito.any()))
						.thenReturn(paginatedResponse);
		ResponseEntity<APIResponse<PaginatedResponse<StorageRatesDTO>>> responseEntity = storageRatesController
				.getStorageRates(selectRateType, incExpDate, shipPrimSix, customerPrimSix, bnfPrimSix, termId,
						equipInit, equipLgth, pageNumber, pageSize, sort,filter);
		Assertions.assertEquals(responseEntity.getStatusCodeValue(), 200);
	}

	@Test
	void testGetEquipmentCustomerRange() throws SQLException {
		when(service.fetchEquipmentCustomerRange(Mockito.anyInt(), Mockito.anyInt())).thenReturn(paginatedResponse1);
		ResponseEntity<APIResponse<PaginatedResponse<EquipmentCustomerRangeDTO>>> responseEntity = storageRatesController
				.getEquipmentCustomerRange(pageNumber, pageSize);
		Assertions.assertEquals(responseEntity.getStatusCodeValue(), 200);
	}

	@Test
	void testUpdateStorageRates() throws SQLException {
		when(service.updateStorageRate(Mockito.any(), Mockito.any())).thenReturn(storageRatesDTO);
		ResponseEntity<APIResponse<StorageRatesDTO>> response = storageRatesController.updateStorageRate(Mockito.any(),
				Mockito.any());
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);

	}

	@Test
	void testNoRecordsExceptionOnUpdate() throws SQLException {
		when(service.updateStorageRate(Mockito.any(), Mockito.any())).thenThrow(NoRecordsFoundException.class);
		ResponseEntity<APIResponse<StorageRatesDTO>> response = storageRatesController.updateStorageRate(Mockito.any(),
				Mockito.any());
		assertEquals(404, response.getStatusCodeValue());
	}

	@Test
	void testInvalidDataExceptionOnUpdate() throws SQLException {
		when(service.updateStorageRate(Mockito.any(), Mockito.any())).thenThrow(InvalidDataException.class);
		ResponseEntity<APIResponse<StorageRatesDTO>> response = storageRatesController.updateStorageRate(Mockito.any(),
				Mockito.any());
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
	}

	@Test
	void testNoRecordsException() throws SQLException {
		when(service.fetchStorageRates(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),
				Mockito.any(), Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.anyInt(), Mockito.any(),Mockito.any()))
						.thenReturn(paginatedResponse);
		ResponseEntity<APIResponse<PaginatedResponse<StorageRatesDTO>>> exception = storageRatesController
				.getStorageRates(selectRateType, incExpDate, shipPrimSix, customerPrimSix, bnfPrimSix, termId,
						equipInit, equipLgth, pageNumber, pageSize, sort,filter);
		Assertions.assertEquals(exception.getStatusCodeValue(), 404);

		when(service.fetchEquipmentCustomerRange(Mockito.anyInt(), Mockito.anyInt())).thenReturn(paginatedResponse1);
		ResponseEntity<APIResponse<PaginatedResponse<EquipmentCustomerRangeDTO>>> exception1 = storageRatesController
				.getEquipmentCustomerRange(pageNumber, pageSize);
		Assertions.assertEquals(exception1.getStatusCodeValue(), 404);

	}

	@Test
	void testInvalidDataException() throws SQLException {
		when(service.fetchStorageRates(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),
				Mockito.any(), Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.anyInt(), Mockito.any(),Mockito.any()))
						.thenReturn(paginatedResponse);
		ResponseEntity<APIResponse<PaginatedResponse<StorageRatesDTO>>> exception = storageRatesController
				.getStorageRates(selectRateType, incExpDate, shipPrimSix, customerPrimSix, bnfPrimSix, termId,
						equipInit, equipLgth, pageNumber, pageSize, sort,filter);
		Assertions.assertEquals(exception.getStatusCodeValue(), 500);

		when(service.fetchEquipmentCustomerRange(Mockito.anyInt(), Mockito.anyInt())).thenReturn(paginatedResponse1);
		ResponseEntity<APIResponse<PaginatedResponse<EquipmentCustomerRangeDTO>>> exception1 = storageRatesController
				.getEquipmentCustomerRange(pageNumber, pageSize);
		Assertions.assertEquals(exception1.getStatusCodeValue(), 500);
	}

	@Test
	void testInternalServerErrorForUpdate() throws SQLException {
		when(service.updateStorageRate(Mockito.any(), Mockito.any())).thenThrow(RuntimeException.class);
		ResponseEntity<APIResponse<StorageRatesDTO>> response = storageRatesController.updateStorageRate(Mockito.any(),
				Mockito.any());
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Test
	void testAddStorageRates() {
		when(service.addStorageRates(Mockito.anyString(), Mockito.anyString(), Mockito.any(), Mockito.anyMap()))
				.thenReturn(storageRatesDTO);
		when(storageRatesListMapper.StorageRatesListDTOToStorageRatesDTO(Mockito.any())).thenReturn(storageRatesDTO);
		ResponseEntity<List<APIResponse<StorageRatesDTO>>> response = storageRatesController
				.addStorageRates(selectRateType, forceAdd, storageRatesListDTO, header);
		Assertions.assertEquals(response.getStatusCodeValue(), 200);
		storageRatesListDTO.setTermIds(termIds);
		storageRatesListDTO.setEquipInits(equipInits);
		ResponseEntity<List<APIResponse<StorageRatesDTO>>> response2 = storageRatesController
				.addStorageRates(selectRateType, forceAdd, storageRatesListDTO, header);
		Assertions.assertEquals(response2.getStatusCodeValue(), 200);
	}
	
	@Test
	void testAddStorageRatesException() {
		when(service.addStorageRates(Mockito.anyString(), Mockito.anyString(), Mockito.any(), Mockito.anyMap()))
				.thenThrow(RuntimeException.class);
		when(storageRatesListMapper.StorageRatesListDTOToStorageRatesDTO(Mockito.any())).thenReturn(storageRatesDTO);
		ResponseEntity<List<APIResponse<StorageRatesDTO>>> response = storageRatesController
				.addStorageRates(selectRateType, forceAdd, storageRatesListDTO, header);
		Assertions.assertEquals(response.getStatusCodeValue(), 400);
	}

}
