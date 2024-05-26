package com.nscorp.obis.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.nscorp.obis.domain.Ports;
import com.nscorp.obis.dto.PortsDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.repository.PortsRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.util.*;

class PortsServiceTest {

	@InjectMocks
	PortsServiceImpl portsService;

	@Mock
	PortsRepository portsRepository;

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
		ports.setUversion("!");

		portsDTO =  new PortsDTO();
		portsDTO.setPortId(123456789L);
		portsDTO.setUversion("!");

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
		when(portsRepository.findAll()).thenReturn(portsList);
		List<Ports> allPorts = portsService.getAllPorts();
		assertEquals(allPorts, portsList);
	}

	@Test
	void testPortsNoRecordsFoundException() {
		when(portsRepository.findAll())
				.thenReturn(Collections.EMPTY_LIST);
		NoRecordsFoundException exception1 = assertThrows(NoRecordsFoundException.class,
				() -> when(portsService.getAllPorts()));
		assertEquals("No Record Found under this search!", exception1.getMessage());

		when(portsRepository.existsByPortIdAndUversion(Mockito.any(),Mockito.any())).thenReturn(false);
		NoRecordsFoundException exception2 = assertThrows(NoRecordsFoundException.class,
				() -> when(portsService.updatePorts(ports, header)));
		assertEquals("No record Found Under this Port Id:123456789 and Uversion:!", exception2.getMessage());

		when(portsRepository.existsByPortIdAndUversion(Mockito.any(),Mockito.any())).thenReturn(false);
		NoRecordsFoundException exception3 = assertThrows(NoRecordsFoundException.class,
				() -> when(portsService.deletePorts(ports, header)));
		assertEquals("No record Found Under this Port Id:123456789 and Uversion:!", exception3.getMessage());
	}

	@Test
	void testPortsNullPointerException() {
		header.put("extensionschema", null);
		NullPointerException exception1 = assertThrows(NullPointerException.class,
				() -> when(portsService.addPorts(ports, header)));
		assertEquals("Extension Schema should not be null, empty or blank.", exception1.getMessage());

		header.put("extensionschema", null);
		NullPointerException exception2 = assertThrows(NullPointerException.class,
				() -> when(portsService.updatePorts(ports, header)));
		assertEquals("Extension Schema should not be null, empty or blank.", exception2.getMessage());

		header.put("extensionschema", null);
		NullPointerException exception3 = assertThrows(NullPointerException.class,
				() -> when(portsService.deletePorts(ports, header)));
		assertEquals("Extension Schema should not be null, empty or blank.", exception3.getMessage());
	}

	@Test
	void testAddPorts() {
		when(portsRepository.existsById(Mockito.any())).thenReturn(false);
		when(portsRepository.save(Mockito.any())).thenReturn(ports);
		Ports addedPorts = portsService.addPorts(ports, header);
		assertNotNull(addedPorts);
	}

	@ParameterizedTest
	@ValueSource(strings = {"!",""," "})
	void testUpdatePorts(String uVersion) {
		when(portsRepository.existsByPortIdAndUversion(Mockito.any(), Mockito.any())).thenReturn(true);
		when(portsRepository.findByPortId(Mockito.any())).thenReturn(ports);
		when(portsRepository.save(Mockito.any())).thenReturn(ports);
		ports.setUversion(uVersion);
		Ports updatePorts = portsService.updatePorts(ports, header);
		assertNotNull(updatePorts);
	}

	@ParameterizedTest
	@ValueSource(strings = {"!",""," "})
	void testUpdatePortsRecordNotAddedException(String uVersion) {
		when(portsRepository.existsByPortIdAndUversion(Mockito.any(), Mockito.any())).thenReturn(true);
		when(portsRepository.findByPortId(Mockito.any())).thenReturn(ports);
		ports.setExpiredDate(expireDate);
		RecordNotAddedException exception2 = assertThrows(RecordNotAddedException.class,
				() -> when(portsService.updatePorts(ports, header)));
		assertEquals("Record with Port Id:123456789 and Uversion:! has been marked for deletion, no updates allowed unless record is restored.", exception2.getMessage());
	}

    @ParameterizedTest
    @ValueSource(strings = {"!",""," "})
    void testDeletePorts(String uVersion) {
		Ports obj = new Ports();
		obj.setPortId(123456788L);
        when(portsRepository.existsByPortIdAndUversion(Mockito.any(), Mockito.any())).thenReturn(true);
        when(portsRepository.findByPortId(Mockito.any())).thenReturn(ports);
        when(portsRepository.save(Mockito.any())).thenReturn(ports);
        obj.setUversion(uVersion);
		obj.setExpiredDate(expireDate);
        Ports deletePorts = portsService.deletePorts(obj, header);
        assertNotNull(deletePorts);
    }

	@Test
	void testDeletePortsRecordNotAddedException() {
		when(portsRepository.existsByPortIdAndUversion(Mockito.any(), Mockito.any())).thenReturn(true);
		when(portsRepository.findByPortId(Mockito.any())).thenReturn(ports);
		ports.setExpiredDate(expireDate);
		RecordNotAddedException exception1 = assertThrows(RecordNotAddedException.class,
				() -> when(portsService.deletePorts(ports, header)));
		assertEquals("Record with Port Id:123456789 and Uversion:! is already expired.",
				exception1.getMessage());

		when(portsRepository.existsByPortIdAndUversion(Mockito.any(), Mockito.any())).thenReturn(true);
		when(portsRepository.findByPortId(Mockito.any())).thenReturn(ports);
		ports.setExpiredDate(null);
		RecordNotAddedException exception2 = assertThrows(RecordNotAddedException.class,
				() -> when(portsService.deletePorts(ports, header)));
		assertEquals("Record with Port Id:123456789 and Uversion:! is not currently marked for deletion.",
				exception2.getMessage());
	}

}
