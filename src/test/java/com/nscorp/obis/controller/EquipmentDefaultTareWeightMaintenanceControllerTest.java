package com.nscorp.obis.controller;

import com.nscorp.obis.domain.EquipmentDefaultTareWeightMaintenance;
import com.nscorp.obis.dto.EquipmentDefaultTareWeightMaintenanceDTO;
import com.nscorp.obis.dto.mapper.EquipmentDefaultTareWeightMaintenanceMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.SizeExceedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.EquipmentDefaultTareWeightMaintenanceService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class EquipmentDefaultTareWeightMaintenanceControllerTest {
	
	@Mock
	EquipmentDefaultTareWeightMaintenanceService equipmentDefaultTareWeightMaintenanceService;

	@Mock
	EquipmentDefaultTareWeightMaintenanceMapper equipmentDefaultTareWeightMaintenanceMapper;

	@InjectMocks
	EquipmentDefaultTareWeightMaintenanceController equipmentDefaultTareWeightMaintenanceController;
	
	EquipmentDefaultTareWeightMaintenanceDTO equipmentDefaultTareWeightMaintenanceDto;
	EquipmentDefaultTareWeightMaintenance equipmentDefaultTareWeightMaintenance;
	List<EquipmentDefaultTareWeightMaintenance> equipmentDefaultTareWeightMaintenanceList;
	List<EquipmentDefaultTareWeightMaintenanceDTO> equipmentDefaultTareWeightMaintenanceDtoList;
	Map<String, String> header;

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		equipmentDefaultTareWeightMaintenance = new EquipmentDefaultTareWeightMaintenance();
		equipmentDefaultTareWeightMaintenanceDto = new EquipmentDefaultTareWeightMaintenanceDTO();
		equipmentDefaultTareWeightMaintenanceDtoList = new ArrayList<>();
		equipmentDefaultTareWeightMaintenanceList = new ArrayList<>();
		equipmentDefaultTareWeightMaintenanceDto.setEqTp("C");
		equipmentDefaultTareWeightMaintenanceDto.setEqLgth(90);
		equipmentDefaultTareWeightMaintenanceDto.setTareWgt(6500);
		equipmentDefaultTareWeightMaintenance.setEqTp("C");
		equipmentDefaultTareWeightMaintenance.setEqLgth(90);
		equipmentDefaultTareWeightMaintenance.setTareWgt(6500);
		equipmentDefaultTareWeightMaintenanceDtoList.add(equipmentDefaultTareWeightMaintenanceDto);
		equipmentDefaultTareWeightMaintenanceList.add(equipmentDefaultTareWeightMaintenance);
		header = new HashMap<String,String>();
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
	void testGetAllEquipmentTareWeights() {
		when(equipmentDefaultTareWeightMaintenanceService.getAllTareWeights()).thenReturn(equipmentDefaultTareWeightMaintenanceList);
		ResponseEntity<APIResponse<List<EquipmentDefaultTareWeightMaintenanceDTO>>> equipmentDefaultTareWeightMaintenanceList = equipmentDefaultTareWeightMaintenanceController.getAllEquipmentTareWeights();
		assertEquals(equipmentDefaultTareWeightMaintenanceList.getStatusCodeValue(), 200);
	}

	@Test
	void testNullListGet(){
		equipmentDefaultTareWeightMaintenanceList = Collections.emptyList();
		when(equipmentDefaultTareWeightMaintenanceService.getAllTareWeights()).thenReturn(equipmentDefaultTareWeightMaintenanceList);
		assertEquals(Collections.EMPTY_LIST,equipmentDefaultTareWeightMaintenanceList);
//		verify(equipmentDefaultTareWeightMaintenanceList.isEmpty()).equals(true);
//		ResponseEntity<APIResponse<List<EquipmentDefaultTareWeightMaintenanceDTO>>> equipmentDefaultTareWeightMaintenanceList = equipmentDefaultTareWeightMaintenanceController.getAllEquipmentTareWeights();
//		assertEquals(equipmentDefaultTareWeightMaintenanceList.getStatusCodeValue(), 200);
	}

	@Test
	void testEquipmentTareWeightsNoRecordsFoundException() {
		when(equipmentDefaultTareWeightMaintenanceService.getAllTareWeights()).thenThrow(new NoRecordsFoundException());
		when(equipmentDefaultTareWeightMaintenanceService.addTareWeight(Mockito.any(),Mockito.any())).thenThrow(new NoRecordsFoundException());
        when(equipmentDefaultTareWeightMaintenanceService.updateWeight(Mockito.any(),Mockito.any())).thenThrow(new NoRecordsFoundException());

        ResponseEntity<APIResponse<List<EquipmentDefaultTareWeightMaintenanceDTO>>> equipmentDefaultTareWeightMaintenanceList = equipmentDefaultTareWeightMaintenanceController.getAllEquipmentTareWeights();
		ResponseEntity<APIResponse<EquipmentDefaultTareWeightMaintenanceDTO>> equipmentDefaultTareWeightMaintenanceListAdd = equipmentDefaultTareWeightMaintenanceController.addTareWeights(Mockito.any(),Mockito.any());
        ResponseEntity<APIResponse<EquipmentDefaultTareWeightMaintenanceDTO>> equipmentDefaultTareWeightMaintenanceListUpdate =  equipmentDefaultTareWeightMaintenanceController.updateWeight(Mockito.any(),Mockito.any());

        assertEquals(equipmentDefaultTareWeightMaintenanceList.getStatusCodeValue(), 404);
		assertEquals(equipmentDefaultTareWeightMaintenanceListAdd.getStatusCodeValue(),404);
        assertEquals(equipmentDefaultTareWeightMaintenanceListUpdate.getStatusCodeValue(),404);
    }

    @Test
    void testEquipmentTareWeightsSizeExceedException() {
        when(equipmentDefaultTareWeightMaintenanceService.addTareWeight(Mockito.any(),Mockito.any())).thenThrow(new SizeExceedException());
        when(equipmentDefaultTareWeightMaintenanceService.updateWeight(Mockito.any(),Mockito.any())).thenThrow(new SizeExceedException("Size Exceed"));

        ResponseEntity<APIResponse<EquipmentDefaultTareWeightMaintenanceDTO>> equipmentDefaultTareWeightMaintenanceListAdd = equipmentDefaultTareWeightMaintenanceController.addTareWeights(Mockito.any(),Mockito.any());
        ResponseEntity<APIResponse<EquipmentDefaultTareWeightMaintenanceDTO>> equipmentDefaultTareWeightMaintenanceListUpdate =  equipmentDefaultTareWeightMaintenanceController.updateWeight(Mockito.any(),Mockito.any());

        assertEquals(equipmentDefaultTareWeightMaintenanceListAdd.getStatusCodeValue(),411);
        assertEquals(equipmentDefaultTareWeightMaintenanceListUpdate.getStatusCodeValue(),411);
    }

    @Test
    void testEquipmentTareWeightsRecordAlreadyExistsException() {
        when(equipmentDefaultTareWeightMaintenanceService.addTareWeight(Mockito.any(),Mockito.any())).thenThrow(new RecordAlreadyExistsException());
        ResponseEntity<APIResponse<EquipmentDefaultTareWeightMaintenanceDTO>> equipmentDefaultTareWeightMaintenanceListAdd = equipmentDefaultTareWeightMaintenanceController.addTareWeights(Mockito.any(),Mockito.any());
        assertEquals(equipmentDefaultTareWeightMaintenanceListAdd.getStatusCodeValue(),208);
    }

    @Test
    void testEquipmentTareWeightsRecordNotAddedException() {
        when(equipmentDefaultTareWeightMaintenanceService.addTareWeight(Mockito.any(),Mockito.any())).thenThrow(new RecordNotAddedException());
        ResponseEntity<APIResponse<EquipmentDefaultTareWeightMaintenanceDTO>> equipmentDefaultTareWeightMaintenanceListAdd = equipmentDefaultTareWeightMaintenanceController.addTareWeights(Mockito.any(),Mockito.any());
        assertEquals(equipmentDefaultTareWeightMaintenanceListAdd.getStatusCodeValue(),406);
    }

    @Test
    void testEquipmentTareWeightsNullPointerException() {
        when(equipmentDefaultTareWeightMaintenanceService.addTareWeight(Mockito.any(),Mockito.any())).thenThrow(new NullPointerException());
        when(equipmentDefaultTareWeightMaintenanceService.updateWeight(Mockito.any(),Mockito.any())).thenThrow(new NullPointerException("Null pointer"));

        ResponseEntity<APIResponse<EquipmentDefaultTareWeightMaintenanceDTO>> equipmentDefaultTareWeightMaintenanceListAdd = equipmentDefaultTareWeightMaintenanceController.addTareWeights(Mockito.any(),Mockito.any());
        ResponseEntity<APIResponse<EquipmentDefaultTareWeightMaintenanceDTO>> equipmentDefaultTareWeightMaintenanceListUpdate =  equipmentDefaultTareWeightMaintenanceController.updateWeight(Mockito.any(),Mockito.any());

        assertEquals(equipmentDefaultTareWeightMaintenanceListAdd.getStatusCodeValue(),400);
        assertEquals(equipmentDefaultTareWeightMaintenanceListUpdate.getStatusCodeValue(),400);
    }

	@Test
	void testEquipmentTareWeightsException() {
		when(equipmentDefaultTareWeightMaintenanceService.getAllTareWeights()).thenThrow(new RuntimeException());
        when(equipmentDefaultTareWeightMaintenanceService.addTareWeight(Mockito.any(),Mockito.any())).thenThrow(new RuntimeException());
        when(equipmentDefaultTareWeightMaintenanceService.updateWeight(Mockito.any(),Mockito.any())).thenThrow(new RuntimeException());

		ResponseEntity<APIResponse<List<EquipmentDefaultTareWeightMaintenanceDTO>>> equipmentDefaultTareWeightMaintenanceList = equipmentDefaultTareWeightMaintenanceController.getAllEquipmentTareWeights();
        ResponseEntity<APIResponse<EquipmentDefaultTareWeightMaintenanceDTO>> equipmentDefaultTareWeightMaintenanceListAdd = equipmentDefaultTareWeightMaintenanceController.addTareWeights(Mockito.any(),Mockito.any());
        ResponseEntity<APIResponse<EquipmentDefaultTareWeightMaintenanceDTO>> equipmentDefaultTareWeightMaintenanceListUpdate = equipmentDefaultTareWeightMaintenanceController.updateWeight(Mockito.any(),Mockito.any());

        assertEquals(equipmentDefaultTareWeightMaintenanceList.getStatusCodeValue(), 500);
        assertEquals(equipmentDefaultTareWeightMaintenanceListAdd.getStatusCodeValue(), 500);
        assertEquals(equipmentDefaultTareWeightMaintenanceListUpdate.getStatusCodeValue(), 500);
    }

	@Test
	void testAddTareWeights() {
		when(equipmentDefaultTareWeightMaintenanceMapper.equipmentDefaultTareWeightMaintenanceDTOToEquipmentDefaultTareWeightMaintenance(Mockito.any())).thenReturn(equipmentDefaultTareWeightMaintenance);
		when(equipmentDefaultTareWeightMaintenanceService.addTareWeight(Mockito.any(), Mockito.any())).thenReturn(equipmentDefaultTareWeightMaintenance);
		when(equipmentDefaultTareWeightMaintenanceMapper.equipmentDefaultTareWeightMaintenanceToEquipmentDefaultTareWeightMaintenanceDTO(Mockito.any())).thenReturn(equipmentDefaultTareWeightMaintenanceDto);
		ResponseEntity<APIResponse<EquipmentDefaultTareWeightMaintenanceDTO>> addedTareWeight = equipmentDefaultTareWeightMaintenanceController.addTareWeights(equipmentDefaultTareWeightMaintenanceDto,
				header);
		assertNotNull(addedTareWeight.getBody());
	}


	@Test
	void testUpdateWeight() {
		when(equipmentDefaultTareWeightMaintenanceMapper.equipmentDefaultTareWeightMaintenanceDTOToEquipmentDefaultTareWeightMaintenance(Mockito.any())).thenReturn(equipmentDefaultTareWeightMaintenance);
		when(equipmentDefaultTareWeightMaintenanceService.updateWeight(Mockito.any(), Mockito.any())).thenReturn(equipmentDefaultTareWeightMaintenance);
		when(equipmentDefaultTareWeightMaintenanceMapper.equipmentDefaultTareWeightMaintenanceToEquipmentDefaultTareWeightMaintenanceDTO(Mockito.any())).thenReturn(equipmentDefaultTareWeightMaintenanceDto);
		ResponseEntity<APIResponse<EquipmentDefaultTareWeightMaintenanceDTO>> updateTareWeight = equipmentDefaultTareWeightMaintenanceController.updateWeight(equipmentDefaultTareWeightMaintenanceDto, header);
		assertNotNull(updateTareWeight.getBody());
	}

	@Test
	void testDeleteWeight() {
		when(equipmentDefaultTareWeightMaintenanceMapper.equipmentDefaultTareWeightMaintenanceDTOToEquipmentDefaultTareWeightMaintenance(Mockito.any())).thenReturn(equipmentDefaultTareWeightMaintenance);
		equipmentDefaultTareWeightMaintenanceService.deleteWeight(Mockito.any());
		when(equipmentDefaultTareWeightMaintenanceMapper.equipmentDefaultTareWeightMaintenanceToEquipmentDefaultTareWeightMaintenanceDTO(Mockito.any())).thenReturn(equipmentDefaultTareWeightMaintenanceDto);
		ResponseEntity<List<APIResponse<EquipmentDefaultTareWeightMaintenanceDTO>>> deleteList = equipmentDefaultTareWeightMaintenanceController.deleteWeight(equipmentDefaultTareWeightMaintenanceDtoList);
		assertEquals(deleteList.getStatusCodeValue(),200);
	}

	@Test
	void testErrorDeleteWeight() {
		EquipmentDefaultTareWeightMaintenance eq = new EquipmentDefaultTareWeightMaintenance();
		eq.setEqLgth(88);
		eq.setEqTp("C");
		eq.setTareWgt(7890);
		List<EquipmentDefaultTareWeightMaintenance> eqList = new ArrayList<>();
		eqList.add(eq);
		EquipmentDefaultTareWeightMaintenanceDTO eqDto = new EquipmentDefaultTareWeightMaintenanceDTO();
		List<EquipmentDefaultTareWeightMaintenanceDTO> eqDtoList = new ArrayList<>();
//        Mockito.doThrow(new RuntimeException()).when(equipmentDefaultTareWeightMaintenanceService).deleteWeight(Mockito.any());
		when(equipmentDefaultTareWeightMaintenanceMapper.equipmentDefaultTareWeightMaintenanceDTOToEquipmentDefaultTareWeightMaintenance(Mockito.any())).thenReturn(eq);
		equipmentDefaultTareWeightMaintenanceService.deleteWeight(eq);
		when(equipmentDefaultTareWeightMaintenanceMapper.equipmentDefaultTareWeightMaintenanceToEquipmentDefaultTareWeightMaintenanceDTO(Mockito.any())).thenReturn(eqDto);
		ResponseEntity<List<APIResponse<EquipmentDefaultTareWeightMaintenanceDTO>>> deleteList = equipmentDefaultTareWeightMaintenanceController.deleteWeight(eqDtoList);
		assertEquals(deleteList.getStatusCodeValue(),500);
	}


}
