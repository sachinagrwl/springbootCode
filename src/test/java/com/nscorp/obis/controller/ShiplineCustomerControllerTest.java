package com.nscorp.obis.controller;

import com.nscorp.obis.domain.ShiplineCustomer;
import com.nscorp.obis.dto.ShiplineCustomerDTO;
import com.nscorp.obis.dto.mapper.ShiplineCustomerMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.SizeExceedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.ShiplineCustomerService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class ShiplineCustomerControllerTest {
	@Mock
	ShiplineCustomerService steamshipCustomerService;

	@Mock
	ShiplineCustomerMapper steamshipCustomerMapper;

	@InjectMocks
	ShiplineCustomerController steamshipCustomerController;
	
	ShiplineCustomerDTO steamshipCustomerDto;
	ShiplineCustomer steamshipCustomer;
	List<ShiplineCustomer> steamshipCustomerList;
	List<ShiplineCustomerDTO> steamshipCustomerDtoList;
	Map<String, String> header;

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		steamshipCustomer = new ShiplineCustomer();
		steamshipCustomerDto = new ShiplineCustomerDTO();
		steamshipCustomerDtoList = new ArrayList<>();
		steamshipCustomerList = new ArrayList<>();
		steamshipCustomerDto.setShiplineNumber("123AB");
		steamshipCustomerDto.setCustomerId((long) 123.000);
		steamshipCustomerDto.setDescription("TEST");
		steamshipCustomer.setShiplineNumber("123AB");
		steamshipCustomer.setCustomerId((long) 123.000);
		steamshipCustomer.setDescription("TEST");
		steamshipCustomerDtoList.add(steamshipCustomerDto);
		steamshipCustomerList.add(steamshipCustomer);
		header = new HashMap<String,String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");
	}

	@AfterEach
	void tearDown() throws Exception {
		steamshipCustomer = null;
		steamshipCustomerDto = null;
		steamshipCustomerDtoList = null;
		steamshipCustomerList = null;
	}

	@Test
	void testGetAllSteamshipCustomer() {
		when(steamshipCustomerService.getAllSteamshipCustomers()).thenReturn(steamshipCustomerList);
		ResponseEntity<APIResponse<List<ShiplineCustomerDTO>>> getSteamshipCustomersList = steamshipCustomerController.getAllSteamshipCustomer();
		assertEquals(getSteamshipCustomersList.getStatusCodeValue(),200);
	}

	@Test
	void testGetAllSteamshipCustomerNoRecordsFoundException() {
		when(steamshipCustomerService.getAllSteamshipCustomers()).thenThrow(new NoRecordsFoundException());
		when(steamshipCustomerService.addSteamshipCustomer(Mockito.any(),Mockito.any())).thenThrow(new NoRecordsFoundException());

		ResponseEntity<APIResponse<List<ShiplineCustomerDTO>>> getAllCustomer = steamshipCustomerController.getAllSteamshipCustomer();
		ResponseEntity<APIResponse<ShiplineCustomerDTO>> addAllCustomer = steamshipCustomerController.addSteamshipCustomer(Mockito.any(),Mockito.any());

		assertEquals(getAllCustomer.getStatusCodeValue(), 404);
		assertEquals(addAllCustomer.getStatusCodeValue(),404);
	}

	@Test
	void testGetAllSteamshipCustomerRuntimeException() {
		when(steamshipCustomerService.getAllSteamshipCustomers()).thenThrow(new RuntimeException());
		when(steamshipCustomerService.addSteamshipCustomer(Mockito.any(),Mockito.any())).thenThrow(new RuntimeException());

		ResponseEntity<APIResponse<List<ShiplineCustomerDTO>>> getAllCustomer = steamshipCustomerController.getAllSteamshipCustomer();
		ResponseEntity<APIResponse<ShiplineCustomerDTO>> addAllCustomer = steamshipCustomerController.addSteamshipCustomer(Mockito.any(),Mockito.any());

		assertEquals(getAllCustomer.getStatusCodeValue(), 500);
		assertEquals(addAllCustomer.getStatusCodeValue(),500);
	}

	@Test
	void testGetAllSteamshipCustomerSizeExceedException() {
		when(steamshipCustomerService.addSteamshipCustomer(Mockito.any(),Mockito.any())).thenThrow(new SizeExceedException());
		ResponseEntity<APIResponse<ShiplineCustomerDTO>> addAllCustomer = steamshipCustomerController.addSteamshipCustomer(Mockito.any(),Mockito.any());
		assertEquals(addAllCustomer.getStatusCodeValue(),411);
	}

	@Test
	void testGetAllSteamshipCustomerRecordAlreadyExistsException() {
		when(steamshipCustomerService.addSteamshipCustomer(Mockito.any(),Mockito.any())).thenThrow(new RecordAlreadyExistsException());
		ResponseEntity<APIResponse<ShiplineCustomerDTO>> addAllCustomer = steamshipCustomerController.addSteamshipCustomer(Mockito.any(),Mockito.any());
		assertEquals(addAllCustomer.getStatusCodeValue(),208);
	}

	@Test
	void testGetAllSteamshipCustomerRecordNotAddedException() {
		when(steamshipCustomerService.addSteamshipCustomer(Mockito.any(),Mockito.any())).thenThrow(new RecordNotAddedException());
		ResponseEntity<APIResponse<ShiplineCustomerDTO>> addAllCustomer = steamshipCustomerController.addSteamshipCustomer(Mockito.any(),Mockito.any());
		assertEquals(addAllCustomer.getStatusCodeValue(),406);
	}

	@Test
	void testGetAllSteamshipCustomerNullPointerException() {
		when(steamshipCustomerService.addSteamshipCustomer(Mockito.any(),Mockito.any())).thenThrow(new NullPointerException());
		ResponseEntity<APIResponse<ShiplineCustomerDTO>> addAllCustomer = steamshipCustomerController.addSteamshipCustomer(Mockito.any(),Mockito.any());
		assertEquals(addAllCustomer.getStatusCodeValue(),400);
	}

	@Test
	void testAddSteamshipCustomer() {
		when(steamshipCustomerMapper.steamshipCustomerDTOToSteamshipCustomer(Mockito.any())).thenReturn(steamshipCustomer);
		when(steamshipCustomerService.addSteamshipCustomer(Mockito.any(), Mockito.any())).thenReturn(steamshipCustomer);
		when(steamshipCustomerMapper.steamshipCustomerToSteamshipCustomerDTO(Mockito.any())).thenReturn(steamshipCustomerDto);
		ResponseEntity<APIResponse<ShiplineCustomerDTO>> addedSteamshipCustomer = steamshipCustomerController.addSteamshipCustomer(steamshipCustomerDto, header);
		assertNotNull(addedSteamshipCustomer.getBody());
	}

	@Test
	void testDeleteSteamshipCustomer() {
		when(steamshipCustomerMapper.steamshipCustomerDTOToSteamshipCustomer(Mockito.any())).thenReturn(steamshipCustomer);
		steamshipCustomerService.deleteCustomer(steamshipCustomer);
		when(steamshipCustomerMapper.steamshipCustomerToSteamshipCustomerDTO(Mockito.any())).thenReturn(steamshipCustomerDto);
		ResponseEntity<List<APIResponse<ShiplineCustomerDTO>>> deleteSteamshipCustomer = steamshipCustomerController.deleteSteamshipCustomer(steamshipCustomerDtoList);
		assertEquals(deleteSteamshipCustomer.getStatusCodeValue(),200);
	}

	@Test
	void testErrorDeleteCustomer() {
		ShiplineCustomerDTO shiplineCustomerDTO = new ShiplineCustomerDTO();
		List<ShiplineCustomerDTO> shiplineCustomerDTOList = new ArrayList<>();
		when(steamshipCustomerMapper.steamshipCustomerDTOToSteamshipCustomer(Mockito.any())).thenReturn(steamshipCustomer);
		steamshipCustomerService.deleteCustomer(steamshipCustomer);
		when(steamshipCustomerMapper.steamshipCustomerToSteamshipCustomerDTO(Mockito.any())).thenReturn(shiplineCustomerDTO);
		ResponseEntity<List<APIResponse<ShiplineCustomerDTO>>> deleteSteamshipCustomer = steamshipCustomerController.deleteSteamshipCustomer(shiplineCustomerDTOList);
		assertEquals(deleteSteamshipCustomer.getStatusCodeValue(),500);
	}

}
