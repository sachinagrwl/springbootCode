package com.nscorp.obis.services;

import com.nscorp.obis.domain.PositionalWeightLimitMaintenance;
import com.nscorp.obis.dto.PositionalWeightLimitMaintenanceDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.RecordNotDeletedException;
import com.nscorp.obis.repository.PositionalWeightLimitMaintenanceRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class PositionalWeightLimitMaintenanceServiceTest {
	
	@InjectMocks
	PositionalWeightLimitMaintenanceServiceImpl positionalWeightLimitMaintenanceService;

	@Mock
	//@Autowired
	PositionalWeightLimitMaintenanceRepository positionalWeightLimitMaintenanceRepository;
	
	List<PositionalWeightLimitMaintenanceDTO> positionalWeightLimitMaintenanceDtoList;
	PositionalWeightLimitMaintenanceDTO positionalWeightLimitMaintenanceDto;
	PositionalWeightLimitMaintenance positionalWeightLimitMaintenance;
	List<PositionalWeightLimitMaintenance> positionalWeightLimitMaintenanceList;
	
	Map<String, String> header;
	
	PositionalWeightLimitMaintenance addedLoad;
	PositionalWeightLimitMaintenance loadUpdated;


	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		
		MockitoAnnotations.initMocks(this);
		
		positionalWeightLimitMaintenance = new PositionalWeightLimitMaintenance();
		positionalWeightLimitMaintenanceDto = new PositionalWeightLimitMaintenanceDTO();
		positionalWeightLimitMaintenanceList = new ArrayList<>();
		positionalWeightLimitMaintenanceDtoList = new ArrayList<>();
		positionalWeightLimitMaintenanceDto.setCarInit("DTTX");
		positionalWeightLimitMaintenanceDto.setCarNrLow((long) 67453);
		positionalWeightLimitMaintenanceDto.setCarNrHigh((long) 983546);
		positionalWeightLimitMaintenanceDto.setCarEquipmentType("F");
		positionalWeightLimitMaintenanceDto.setAarType("S365");
		positionalWeightLimitMaintenanceDto.setCarOwner("RRV");
		positionalWeightLimitMaintenanceDto.setC20MaxWeight(12354);
		positionalWeightLimitMaintenanceDto.setCarDescription("8 articulated 100 ton well car");
		positionalWeightLimitMaintenance.setCarInit("DTTX");
		positionalWeightLimitMaintenance.setCarNrLow((long) 67453);
		positionalWeightLimitMaintenance.setCarNrHigh((long) 983546);
		positionalWeightLimitMaintenance.setCarEquipmentType("F");
		positionalWeightLimitMaintenance.setAarType("S365");
		positionalWeightLimitMaintenance.setCarOwner("RRV");
		positionalWeightLimitMaintenance.setC20MaxWeight(12354);
		positionalWeightLimitMaintenance.setCarDescription("8 articulated 100 ton well car");
		positionalWeightLimitMaintenanceList.add(positionalWeightLimitMaintenance);
		positionalWeightLimitMaintenanceDtoList.add(positionalWeightLimitMaintenanceDto);
		
		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");
	}
	

	@AfterEach
	void tearDown() throws Exception {
		
		positionalWeightLimitMaintenance = null;
		positionalWeightLimitMaintenanceList = null;
		positionalWeightLimitMaintenanceDto = null;
		positionalWeightLimitMaintenanceDtoList = null;
		
	}

	@Test
	void testGetAllLoadLimits() {
				when(positionalWeightLimitMaintenanceRepository.findAll())
				.thenReturn(positionalWeightLimitMaintenanceList);
		List<PositionalWeightLimitMaintenance> allLoadLimits = positionalWeightLimitMaintenanceService
				.getAllLoadLimits();
		assertEquals(allLoadLimits, positionalWeightLimitMaintenanceList);
	}

	@Test
	void testGetAllLoadLimitsException() {

		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(positionalWeightLimitMaintenanceService.getAllLoadLimits()));
		assertEquals("No Records Found!", exception.getMessage());
	}
	
	@Test
	void testInsertLoad() {
		
		when(positionalWeightLimitMaintenanceRepository.existsByCarInitAndCarNrLowAndCarNrHighAndCarEquipmentType(Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(false);
		when(positionalWeightLimitMaintenanceRepository.findByCarInitAndCarNrLowAndCarNrHighAndCarEquipmentType(Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(positionalWeightLimitMaintenance);
		when(positionalWeightLimitMaintenanceRepository.save(Mockito.any())).thenReturn(positionalWeightLimitMaintenance);
		addedLoad = positionalWeightLimitMaintenanceService.insertLoad(positionalWeightLimitMaintenance, header);
		assertNotNull(addedLoad);
		
	}
	
	@Test
	void testInsertLoadException() {
		PositionalWeightLimitMaintenance d = new PositionalWeightLimitMaintenance();
		d.setCarInit("DTTX");
		d.setCarNrHigh(67453L);
		d.setCarNrLow(983546L);
		d.setCarEquipmentType("F");
		d.setCarOwner("RRV");
		d.setAarType("S365");
		d.setC20MaxWeight(12345);
		d.setCarDescription("8 articulated 100 ton well car");
		when(positionalWeightLimitMaintenanceRepository.existsByCarInitAndCarNrLowAndCarNrHighAndCarEquipmentType(Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(true);
		
		RecordAlreadyExistsException exception = assertThrows(RecordAlreadyExistsException.class,
				() -> when(positionalWeightLimitMaintenanceService
							.insertLoad(d, header)));
		assertEquals("Record already exists under CAR INIT: DTTX, LOW NR: 983546, HIGH NR: 67453", exception.getMessage());

	}

	@Test
	void testInsertLoadRecordNotAddedException() {
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> when(positionalWeightLimitMaintenanceService
						.insertLoad(positionalWeightLimitMaintenance, header)));
		assertEquals("Record Not added", exception.getMessage());
	}
	
	@Test
	void testUpdatePositionalWeightLimitMaintenance() {
		
		when(positionalWeightLimitMaintenanceRepository.existsByCarInitAndCarNrLowAndCarNrHighAndCarEquipmentType(Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(true);
		when(positionalWeightLimitMaintenanceRepository.findByCarInitAndCarNrLowAndCarNrHighAndCarEquipmentType(Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(positionalWeightLimitMaintenance);
		when(positionalWeightLimitMaintenanceRepository.save(Mockito.any())).thenReturn(positionalWeightLimitMaintenance);
		loadUpdated = positionalWeightLimitMaintenanceService.updatePositionalWeightLimitMaintenance(positionalWeightLimitMaintenance,header);
		loadUpdated.setAarType("S365");
		assertEquals(loadUpdated, positionalWeightLimitMaintenance);
		
	}
	
	@Test
	void testUpdatePositionalWeightLimitMaintenanceNoRecordsFoundException() {
		
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> positionalWeightLimitMaintenanceService.updatePositionalWeightLimitMaintenance(positionalWeightLimitMaintenance, header));
		assertEquals("No record Found for CAR INIT: DTTX, LOW NR: 67453, HIGH NR: 983546", exception.getMessage());
		
	}

	@Test
	void testDeletePositionalWeightLimitMaintenance() {
		
		when(positionalWeightLimitMaintenanceRepository.existsByCarInitAndCarNrLowAndCarNrHighAndCarEquipmentType("DTTX",67453L,983546L,"F")).thenReturn(true);
		positionalWeightLimitMaintenanceService.deletePositionalWeightLimitMaintenance(positionalWeightLimitMaintenance);
	}
	
	@Test
	void testDeletePositionalWeightLimitMaintenanceRecordNotAddedException() {
		RecordNotDeletedException exception = assertThrows(RecordNotDeletedException.class,
				() -> positionalWeightLimitMaintenanceService.deletePositionalWeightLimitMaintenance(positionalWeightLimitMaintenance));
		assertEquals("DTTX and 67453 and 983546 and F Record Not Found!", exception.getMessage());
	}

}
