package com.nscorp.obis.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.nscorp.obis.domain.Ports;
import com.nscorp.obis.dto.PortsDTO;
import com.nscorp.obis.dto.mapper.PortsMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.PortsService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class PortsControllerTest {

	@InjectMocks
	PortsController portsController;

	@Mock
	PortsMapper portsMapper;

	@Mock
	PortsService portsService;

	Ports ports;
	PortsDTO portsDTO;
	List<Ports> portsList;
	List<PortsDTO> portsDTOList;
	Map<String, String> header;

	Timestamp expireDate = new Timestamp(System.currentTimeMillis());

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		ports = new Ports();
		ports.setPortId(123456789L);
		ports.setExpiredDate(expireDate);

		portsDTO =  new PortsDTO();
		portsDTO.setPortId(123456789L);
		portsDTO.setExpiredDate(expireDate);

		portsList = new ArrayList<>();
		portsList.add(ports);

		portsDTOList = new ArrayList<>();
		portsDTOList.add(portsDTO);

		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");
	}

	@AfterEach
	void tearDown() throws Exception {
		header = null;
		ports = null;
		portsDTO = null;
		portsList = null;
		portsDTOList = null;
	}

	@Test
	void testGetAllPorts() {
		when(portsService.getAllPorts()).thenReturn(portsList);
		ResponseEntity<APIResponse<List<PortsDTO>>> getPorts = portsController.getAllPorts();
		assertEquals(getPorts.getStatusCodeValue(), 200);
	}

	@Test
	void testAddPorts() {
		when(portsService.addPorts(Mockito.any(), Mockito.any())).thenReturn(ports);
		ResponseEntity<APIResponse<PortsDTO>> addedPorts = portsController.addPorts(portsDTO, header);
		assertNotNull(addedPorts.getBody());
	}

	@Test
	void testUpdatePorts() {
		when(portsMapper.PortsDTOToPorts(Mockito.any())).thenReturn(ports);
		when(portsService.updatePorts(Mockito.any(), Mockito.any())).thenReturn(ports);
		when(portsMapper.PortsToPortsDTO(Mockito.any())).thenReturn(portsDTO);
		ResponseEntity<APIResponse<PortsDTO>> updatePorts = portsController.updatePorts(portsDTO,
				header);
		assertEquals(updatePorts.getStatusCodeValue(), 200);
	}

	@Test
	void testDeletePorts() {
		when(portsMapper.PortsDTOToPorts(Mockito.any())).thenReturn(ports);
		when(portsService.deletePorts(Mockito.any(), Mockito.any())).thenReturn(ports);
		when(portsMapper.PortsToPortsDTO(Mockito.any())).thenReturn(portsDTO);
		ResponseEntity<APIResponse<PortsDTO>> deletePorts = portsController.deletePorts(portsDTO,
				header);
		assertEquals(deletePorts.getStatusCodeValue(), 200);
	}

	@Test
	void testNoRecordsFoundException() {
		when(portsService.getAllPorts()).thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<List<PortsDTO>>> getPorts =  portsController.getAllPorts();
		assertEquals(getPorts.getStatusCodeValue(), 404);

		when(portsService.updatePorts(Mockito.any(),Mockito.any())).thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<PortsDTO>> updatePorts = portsController.updatePorts(portsDTO, header);
		assertEquals(updatePorts.getStatusCodeValue(), 404);

		when(portsService.deletePorts(Mockito.any(),Mockito.any())).thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<PortsDTO>> deletePorts = portsController.deletePorts(portsDTO, header);
		assertEquals(deletePorts.getStatusCodeValue(), 404);
	}

	@Test
	void testPortsException() {
		when(portsService.getAllPorts()).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<List<PortsDTO>>> getPorts =  portsController.getAllPorts();
		assertEquals(getPorts.getStatusCodeValue(), 500);

		when(portsService.addPorts(Mockito.any(),Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<PortsDTO>> addPorts = portsController.addPorts(portsDTO, header);
		assertEquals(addPorts.getStatusCodeValue(), 500);

		when(portsService.updatePorts(Mockito.any(),Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<PortsDTO>> updatePorts = portsController.updatePorts(portsDTO, header);
		assertEquals(updatePorts.getStatusCodeValue(), 500);

		when(portsService.deletePorts(Mockito.any(),Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<PortsDTO>> deletePorts = portsController.deletePorts(portsDTO, header);
		assertEquals(deletePorts.getStatusCodeValue(),500);
	}

	@Test
	void testRecordNotAddedException() {
		when(portsService.updatePorts(Mockito.any(),Mockito.any())).thenThrow(new RecordNotAddedException());
		ResponseEntity<APIResponse<PortsDTO>> updatePorts = portsController.updatePorts(portsDTO, header);
		assertEquals(updatePorts.getStatusCodeValue(), 404);

		when(portsService.deletePorts(Mockito.any(),Mockito.any())).thenThrow(new RecordNotAddedException());
		ResponseEntity<APIResponse<PortsDTO>> deletePorts = portsController.deletePorts(portsDTO, header);
		assertEquals(deletePorts.getStatusCodeValue(),404);
	}

	@Test
	void testNullPointerException() {
		when(portsService.addPorts(Mockito.any(),Mockito.any())).thenThrow(new NullPointerException());
		ResponseEntity<APIResponse<PortsDTO>> addPorts = portsController.addPorts(portsDTO, header);
		assertEquals(addPorts.getStatusCodeValue(), 400);

		when(portsService.updatePorts(Mockito.any(),Mockito.any())).thenThrow(new NullPointerException());
		ResponseEntity<APIResponse<PortsDTO>> updatePorts = portsController.updatePorts(portsDTO, header);
		assertEquals(updatePorts.getStatusCodeValue(), 400);

		when(portsService.deletePorts(Mockito.any(),Mockito.any())).thenThrow(new NullPointerException());
		ResponseEntity<APIResponse<PortsDTO>> deletePorts = portsController.deletePorts(portsDTO, header);
		assertEquals(deletePorts.getStatusCodeValue(),400);
	}
}
