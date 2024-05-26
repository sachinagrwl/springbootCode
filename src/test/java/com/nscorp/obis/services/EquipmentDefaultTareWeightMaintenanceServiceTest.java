package com.nscorp.obis.services;

import com.nscorp.obis.domain.EquipmentDefaultTareWeightMaintenance;
import com.nscorp.obis.dto.EquipmentDefaultTareWeightMaintenanceDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.RecordNotDeletedException;
import com.nscorp.obis.repository.EquipmentDefaultTareWeightMaintenanceRepository;
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

class EquipmentDefaultTareWeightMaintenanceServiceTest {

	@InjectMocks
	EquipmentDefaultTareWeightMaintenanceServiceImpl equipmentDefaultTareWeightMaintenanceService;

	@Mock
	EquipmentDefaultTareWeightMaintenanceRepository equipmentDefaultTareWeightMaintenanceRepository;

	EquipmentDefaultTareWeightMaintenanceDTO equipmentDefaultTareWeightMaintenanceDto;
	EquipmentDefaultTareWeightMaintenance equipmentDefaultTareWeightMaintenance;
	List<EquipmentDefaultTareWeightMaintenance> equipmentDefaultTareWeightMaintenanceList;
	List<EquipmentDefaultTareWeightMaintenanceDTO> equipmentDefaultTareWeightMaintenanceDtoList;
	Map<String, String> header;

	EquipmentDefaultTareWeightMaintenance addedTareWeight;
	EquipmentDefaultTareWeightMaintenance updatedTareWeight;

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		equipmentDefaultTareWeightMaintenance = new EquipmentDefaultTareWeightMaintenance();
		equipmentDefaultTareWeightMaintenanceDto = new EquipmentDefaultTareWeightMaintenanceDTO();
		equipmentDefaultTareWeightMaintenanceDtoList = new ArrayList<>();
		equipmentDefaultTareWeightMaintenanceList = new ArrayList<>();
		equipmentDefaultTareWeightMaintenanceDto.setEqTp("C");
		equipmentDefaultTareWeightMaintenanceDto.setEqLgth(20);
		equipmentDefaultTareWeightMaintenanceDto.setTareWgt(6500);
		equipmentDefaultTareWeightMaintenance.setEqTp("C");
		equipmentDefaultTareWeightMaintenance.setEqLgth(20);
		equipmentDefaultTareWeightMaintenance.setTareWgt(6500);
		equipmentDefaultTareWeightMaintenanceDtoList.add(equipmentDefaultTareWeightMaintenanceDto);
		equipmentDefaultTareWeightMaintenanceList.add(equipmentDefaultTareWeightMaintenance);
		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");
	}

	@AfterEach
	void tearDown() throws Exception {
		equipmentDefaultTareWeightMaintenanceList = null;
		equipmentDefaultTareWeightMaintenanceDtoList = null;
		equipmentDefaultTareWeightMaintenanceDto = null;
		equipmentDefaultTareWeightMaintenance = null;
	}

	@Test
	void testGetAllTareWeights() {
		when(equipmentDefaultTareWeightMaintenanceRepository.findAll())
				.thenReturn(equipmentDefaultTareWeightMaintenanceList);
		List<EquipmentDefaultTareWeightMaintenance> allTareWeights = equipmentDefaultTareWeightMaintenanceService
				.getAllTareWeights();
		assertEquals(allTareWeights, equipmentDefaultTareWeightMaintenanceList);
	}

	@Test
	void testGetAllTareWeightsException() {

		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(equipmentDefaultTareWeightMaintenanceService.getAllTareWeights()));
		assertEquals("No records found", exception.getMessage());
	}

	@Test
	void testAddTareWeight() {
		when(equipmentDefaultTareWeightMaintenanceRepository.existsByEqTpAndEqLgth(Mockito.any(),Mockito.any())).thenReturn(false);
		when(equipmentDefaultTareWeightMaintenanceRepository.save(Mockito.any())).thenReturn(equipmentDefaultTareWeightMaintenance);
		addedTareWeight = equipmentDefaultTareWeightMaintenanceService.addTareWeight(equipmentDefaultTareWeightMaintenance, header);
		assertNotNull(addedTareWeight);

	}

	@Test
	void testAddTareWeightException() {
		EquipmentDefaultTareWeightMaintenance d = new EquipmentDefaultTareWeightMaintenance();
		d.setTareWgt(5000);
		d.setEqTp("C");
		d.setEqLgth(20);
		when(equipmentDefaultTareWeightMaintenanceRepository.existsByEqTpAndEqLgth(Mockito.any(),Mockito.any())).thenReturn(true);

		RecordAlreadyExistsException exception = assertThrows(RecordAlreadyExistsException.class,
				() -> when(equipmentDefaultTareWeightMaintenanceService
						.addTareWeight(equipmentDefaultTareWeightMaintenance, header)));
		assertEquals("Equipment tare weight is already exists under Equipment Type:C and Equipemnt Length:20", exception.getMessage());
	}

	@Test
	void testAddTareWeightRecordNotAddedException() {
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> when(equipmentDefaultTareWeightMaintenanceService
						.addTareWeight(equipmentDefaultTareWeightMaintenance, header)));
		assertEquals("Record Not added to Database", exception.getMessage());
	}

	@Test
	void testUpdateWeight() {
		when(equipmentDefaultTareWeightMaintenanceRepository.existsByEqTpAndEqLgth(Mockito.any(),Mockito.any())).thenReturn(true);
		when(equipmentDefaultTareWeightMaintenanceRepository.findByEqLgthAndEqTp(Mockito.any(),Mockito.any())).thenReturn(equipmentDefaultTareWeightMaintenance);
		when(equipmentDefaultTareWeightMaintenanceRepository.save(Mockito.any())).thenReturn(equipmentDefaultTareWeightMaintenance);
		updatedTareWeight = equipmentDefaultTareWeightMaintenanceService.updateWeight(equipmentDefaultTareWeightMaintenance,header);
		updatedTareWeight.setTareWgt(5000);
		assertEquals(updatedTareWeight,equipmentDefaultTareWeightMaintenance);
	}

	@Test
	void testUpdateWeightNoRecordsFoundException() {
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> equipmentDefaultTareWeightMaintenanceService.updateWeight(equipmentDefaultTareWeightMaintenance, header));
		assertEquals("No record Found Under this Equipment Type:C and Equipment Length:20", exception.getMessage());
	}

	@Test
	void testDeleteTareWeight() {
		when(equipmentDefaultTareWeightMaintenanceRepository.existsByEqTpAndEqLgth("C",20)).thenReturn(true);
		equipmentDefaultTareWeightMaintenanceService.deleteWeight(equipmentDefaultTareWeightMaintenance);
	}

	@Test
	void testDeleteTareWeightRecordNotDeletedException() {
		RecordNotDeletedException exception = assertThrows(RecordNotDeletedException.class,
				() -> equipmentDefaultTareWeightMaintenanceService.deleteWeight(equipmentDefaultTareWeightMaintenance));
		assertEquals("C and 20 Record Not Found!", exception.getMessage());
	}
}
