/**
 * 
 */
package com.nscorp.obis.controller;

import com.nscorp.obis.domain.PositionalWeightLimitMaintenance;
import com.nscorp.obis.dto.PositionalWeightLimitMaintenanceDTO;
import com.nscorp.obis.dto.mapper.PositionalWeightLimitMaintenanceMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.SizeExceedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.PositionalWeightLimitMaintenanceService;
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
import static org.mockito.Mockito.when;

class PositionalWeightLimitMaintenanceControllerTest {
	@Mock
	PositionalWeightLimitMaintenanceService positionalWeightLimitMaintenanceService;

	@Mock
	PositionalWeightLimitMaintenanceMapper positionalWeightLimitMaintenanceMapper;

	@InjectMocks
	PositionalWeightLimitMaintenanceController positionalWeightLimitMaintenanceController;

	PositionalWeightLimitMaintenanceDTO positionalWeightLimitMaintenanceDto;
	PositionalWeightLimitMaintenance positionalWeightLimitMaintenance;
	List<PositionalWeightLimitMaintenance> positionalWeightLimitMaintenanceList;
	List<PositionalWeightLimitMaintenanceDTO> positionalWeightLimitMaintenanceDtoList;

	Map<String, String> header;

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		
		MockitoAnnotations.initMocks(this);
		positionalWeightLimitMaintenance = new PositionalWeightLimitMaintenance();
		positionalWeightLimitMaintenance.setCarInit("TEST");
		positionalWeightLimitMaintenance.setCarNrLow((long) 12345);
		positionalWeightLimitMaintenance.setCarNrHigh((long) 45678);
		positionalWeightLimitMaintenance.setCarEquipmentType("F");
		positionalWeightLimitMaintenance.setAarType("R476");
		positionalWeightLimitMaintenance.setCarOwner("TTX");
		positionalWeightLimitMaintenance.setC20MaxWeight(564738);
		positionalWeightLimitMaintenance.setCarDescription("6 articulated 100 ton well car");
		
		positionalWeightLimitMaintenanceList = new ArrayList<>();
		positionalWeightLimitMaintenanceList.add(positionalWeightLimitMaintenance);
		
		positionalWeightLimitMaintenanceDto = new PositionalWeightLimitMaintenanceDTO();
		positionalWeightLimitMaintenanceDto.setCarInit("TEST");
		positionalWeightLimitMaintenanceDto.setCarNrLow((long) 12345);
		positionalWeightLimitMaintenanceDto.setCarNrHigh((long) 45678);
		positionalWeightLimitMaintenanceDto.setCarEquipmentType("F");
		positionalWeightLimitMaintenanceDto.setAarType("R476");
		positionalWeightLimitMaintenanceDto.setCarOwner("TTX");
		positionalWeightLimitMaintenanceDto.setC20MaxWeight(564738);
		positionalWeightLimitMaintenanceDto.setCarDescription("6 articulated 100 ton well car");
		
		positionalWeightLimitMaintenanceDtoList = new ArrayList<>();
		positionalWeightLimitMaintenanceDtoList.add(positionalWeightLimitMaintenanceDto);
		
		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");
	}

	@AfterEach
	void tearDown() throws Exception {
		
		positionalWeightLimitMaintenanceDto = null;
		positionalWeightLimitMaintenance = null;
		positionalWeightLimitMaintenanceList = null;
		positionalWeightLimitMaintenanceDtoList = null;
		
	}

	@Test
	void testGetAllPositionalLoadLimits() {
		when(positionalWeightLimitMaintenanceService.getAllLoadLimits()).thenReturn(positionalWeightLimitMaintenanceList);
		ResponseEntity<APIResponse<List<PositionalWeightLimitMaintenanceDTO>>> positionalWeightLimitMaintenanceList = positionalWeightLimitMaintenanceController.getAllPositionalLoadLimits();
		assertEquals(positionalWeightLimitMaintenanceList.getStatusCodeValue(),200);
	}

	@Test
	void testLoadLimitNoRecordsFoundException() {
		when(positionalWeightLimitMaintenanceService.getAllLoadLimits()).thenThrow(new NoRecordsFoundException());
		when(positionalWeightLimitMaintenanceService.insertLoad(Mockito.any(),Mockito.any())).thenThrow(new NoRecordsFoundException());
		when(positionalWeightLimitMaintenanceService.updatePositionalWeightLimitMaintenance(Mockito.any(),Mockito.any())).thenThrow(new NoRecordsFoundException());

		ResponseEntity<APIResponse<List<PositionalWeightLimitMaintenanceDTO>>> getLoad = positionalWeightLimitMaintenanceController.getAllPositionalLoadLimits();
		ResponseEntity<APIResponse<PositionalWeightLimitMaintenanceDTO>> addLoad = positionalWeightLimitMaintenanceController.addLoadLimits(Mockito.any(),Mockito.any());
		ResponseEntity<APIResponse<PositionalWeightLimitMaintenanceDTO>> updateLoad =  positionalWeightLimitMaintenanceController.updatePositionalWeightLimitMaintenance(Mockito.any(),Mockito.any());

		assertEquals(getLoad.getStatusCodeValue(), 404);
		assertEquals(addLoad.getStatusCodeValue(),404);
		assertEquals(updateLoad.getStatusCodeValue(),404);
	}

	@Test
	void testLoadLimitRuntimeException() {
		when(positionalWeightLimitMaintenanceService.getAllLoadLimits()).thenThrow(new RuntimeException());
		when(positionalWeightLimitMaintenanceService.insertLoad(Mockito.any(),Mockito.any())).thenThrow(new RuntimeException());
		when(positionalWeightLimitMaintenanceService.updatePositionalWeightLimitMaintenance(Mockito.any(),Mockito.any())).thenThrow(new RuntimeException());

		ResponseEntity<APIResponse<List<PositionalWeightLimitMaintenanceDTO>>> getLoad = positionalWeightLimitMaintenanceController.getAllPositionalLoadLimits();
		ResponseEntity<APIResponse<PositionalWeightLimitMaintenanceDTO>> addLoad = positionalWeightLimitMaintenanceController.addLoadLimits(Mockito.any(),Mockito.any());
		ResponseEntity<APIResponse<PositionalWeightLimitMaintenanceDTO>> updateLoad =  positionalWeightLimitMaintenanceController.updatePositionalWeightLimitMaintenance(Mockito.any(),Mockito.any());

		assertEquals(getLoad.getStatusCodeValue(), 500);
		assertEquals(addLoad.getStatusCodeValue(),500);
		assertEquals(updateLoad.getStatusCodeValue(),500);
	}

	@Test
	void testLoadLimitNullPointerException() {
		when(positionalWeightLimitMaintenanceService.insertLoad(Mockito.any(),Mockito.any())).thenThrow(new NullPointerException());
		when(positionalWeightLimitMaintenanceService.updatePositionalWeightLimitMaintenance(Mockito.any(),Mockito.any())).thenThrow(new NullPointerException());

		ResponseEntity<APIResponse<PositionalWeightLimitMaintenanceDTO>> addLoad = positionalWeightLimitMaintenanceController.addLoadLimits(Mockito.any(),Mockito.any());
		ResponseEntity<APIResponse<PositionalWeightLimitMaintenanceDTO>> updateLoad =  positionalWeightLimitMaintenanceController.updatePositionalWeightLimitMaintenance(Mockito.any(),Mockito.any());

		assertEquals(addLoad.getStatusCodeValue(),400);
		assertEquals(updateLoad.getStatusCodeValue(),400);
	}

	@Test
	void testLoadLimitSizeExceedException() {
		when(positionalWeightLimitMaintenanceService.insertLoad(Mockito.any(),Mockito.any())).thenThrow(new SizeExceedException());
		when(positionalWeightLimitMaintenanceService.updatePositionalWeightLimitMaintenance(Mockito.any(),Mockito.any())).thenThrow(new SizeExceedException());

		ResponseEntity<APIResponse<PositionalWeightLimitMaintenanceDTO>> addLoad = positionalWeightLimitMaintenanceController.addLoadLimits(Mockito.any(),Mockito.any());
		ResponseEntity<APIResponse<PositionalWeightLimitMaintenanceDTO>> updateLoad =  positionalWeightLimitMaintenanceController.updatePositionalWeightLimitMaintenance(Mockito.any(),Mockito.any());

		assertEquals(addLoad.getStatusCodeValue(),411);
		assertEquals(updateLoad.getStatusCodeValue(),411);
	}

	@Test
	void testLoadLimitRecordAlreadyExistsException() {
		when(positionalWeightLimitMaintenanceService.insertLoad(Mockito.any(),Mockito.any())).thenThrow(new RecordAlreadyExistsException());
		ResponseEntity<APIResponse<PositionalWeightLimitMaintenanceDTO>> addLoad = positionalWeightLimitMaintenanceController.addLoadLimits(Mockito.any(),Mockito.any());
		assertEquals(addLoad.getStatusCodeValue(),208);
	}

	@Test
	void testLoadLimitRecordNotAddedException() {
		when(positionalWeightLimitMaintenanceService.insertLoad(Mockito.any(),Mockito.any())).thenThrow(new RecordNotAddedException());
		ResponseEntity<APIResponse<PositionalWeightLimitMaintenanceDTO>> addLoad = positionalWeightLimitMaintenanceController.addLoadLimits(Mockito.any(),Mockito.any());
		assertEquals(addLoad.getStatusCodeValue(),406);
	}

	@Test
	void testAddLoadLimits() {
		when(positionalWeightLimitMaintenanceMapper.positionalWeightLimitMaintenanceDTOToPositionalWeightLimitMaintenance(Mockito.any())).thenReturn(positionalWeightLimitMaintenance);
		when(positionalWeightLimitMaintenanceService.insertLoad(Mockito.any(), Mockito.any())).thenReturn(positionalWeightLimitMaintenance);
		when(positionalWeightLimitMaintenanceMapper.positionalWeightLimitMaintenanceToPositionalWeightLimitMaintenanceDTO(Mockito.any())).thenReturn(positionalWeightLimitMaintenanceDto);
		ResponseEntity<APIResponse<PositionalWeightLimitMaintenanceDTO>> addedLoad = positionalWeightLimitMaintenanceController.addLoadLimits(positionalWeightLimitMaintenanceDto,
				header);
		assertEquals(addedLoad.getStatusCodeValue(),200);
	}

	@Test
	void testUpdatePositionalWeightLimitMaintenance() {
		when(positionalWeightLimitMaintenanceMapper.positionalWeightLimitMaintenanceDTOToPositionalWeightLimitMaintenance(Mockito.any())).thenReturn(positionalWeightLimitMaintenance);
		when(positionalWeightLimitMaintenanceService.updatePositionalWeightLimitMaintenance(Mockito.any(), Mockito.any())).thenReturn(positionalWeightLimitMaintenance);
		when(positionalWeightLimitMaintenanceMapper.positionalWeightLimitMaintenanceToPositionalWeightLimitMaintenanceDTO(Mockito.any())).thenReturn(positionalWeightLimitMaintenanceDto);
		ResponseEntity<APIResponse<PositionalWeightLimitMaintenanceDTO>> loadUpdated = positionalWeightLimitMaintenanceController.updatePositionalWeightLimitMaintenance(positionalWeightLimitMaintenanceDto, header);
		assertEquals(loadUpdated.getStatusCodeValue(),200);
	}

	@Test
	void testDeletePositionalWeightLimitMaintenance() {
		when(positionalWeightLimitMaintenanceMapper.positionalWeightLimitMaintenanceDTOToPositionalWeightLimitMaintenance(Mockito.any())).thenReturn(positionalWeightLimitMaintenance);
		positionalWeightLimitMaintenanceController.deletePositionalWeightLimitMaintenance(positionalWeightLimitMaintenanceDtoList);
		when(positionalWeightLimitMaintenanceMapper.positionalWeightLimitMaintenanceToPositionalWeightLimitMaintenanceDTO(Mockito.any())).thenReturn(positionalWeightLimitMaintenanceDto);
		ResponseEntity<List<APIResponse<PositionalWeightLimitMaintenanceDTO>>> deleteList = positionalWeightLimitMaintenanceController.deletePositionalWeightLimitMaintenance(positionalWeightLimitMaintenanceDtoList);
		assertEquals(deleteList.getStatusCodeValue(),200);
	}

	@Test
	void testDeletePositionalWeightLimitMaintenancePartial() {
		PositionalWeightLimitMaintenanceDTO positionalWeightLimitMaintenanceDTO = new PositionalWeightLimitMaintenanceDTO();
		List<PositionalWeightLimitMaintenanceDTO> positionalWeightLimitMaintenanceDTOList = new ArrayList<>();
		when(positionalWeightLimitMaintenanceMapper.positionalWeightLimitMaintenanceDTOToPositionalWeightLimitMaintenance(Mockito.any())).thenReturn(positionalWeightLimitMaintenance);
		positionalWeightLimitMaintenanceController.deletePositionalWeightLimitMaintenance(positionalWeightLimitMaintenanceDtoList);
		when(positionalWeightLimitMaintenanceMapper.positionalWeightLimitMaintenanceToPositionalWeightLimitMaintenanceDTO(Mockito.any())).thenReturn(positionalWeightLimitMaintenanceDTO);
		ResponseEntity<List<APIResponse<PositionalWeightLimitMaintenanceDTO>>> deleteList = positionalWeightLimitMaintenanceController.deletePositionalWeightLimitMaintenance(positionalWeightLimitMaintenanceDTOList);
		assertEquals(deleteList.getStatusCodeValue(),500);
	}
}
