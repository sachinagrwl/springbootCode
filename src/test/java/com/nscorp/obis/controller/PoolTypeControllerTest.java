package com.nscorp.obis.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.*;

import com.nscorp.obis.dto.EquipmentRestrictDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.nscorp.obis.domain.PoolType;
import com.nscorp.obis.dto.PoolTypeDTO;
import com.nscorp.obis.dto.mapper.PoolTypeMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.SizeExceedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.PoolTypeService;

class PoolTypeControllerTest {
	
	@Mock
	PoolTypeService reserveTypeService;
	
	@Mock
	PoolTypeMapper poolTypeMapper;
	
	@InjectMocks
    PoolTypeController reserveTypeController;
	
	PoolType poolType;
	PoolTypeDTO poolTypeDto;
	List<PoolType> poolTypeList;
	List<PoolTypeDTO> poolTypeDtoList;
	Map<String,String> header;

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		poolType = new PoolType();
		poolTypeDto = new PoolTypeDTO();
		poolTypeList = new ArrayList<>();
		poolTypeDtoList = new ArrayList<>();
		
		poolType.setPoolTp("BN");
		poolType.setAdvAllowInd("N");
		poolType.setAdvOverride("Y");
		poolType.setAdvRqdInd("Y");
		poolType.setAgreementRqd("Y");
		poolType.setMultiRsrvInd("Y");
		poolType.setPoolTpDesc("TEST");
		poolType.setPuAllowInd("Y");
		poolType.setPuRqdInd("Y");
		poolType.setRsrvInd("Y");
		poolType.setUdfParam1("Y");
		poolType.setUdfParam2("Y");
		poolType.setUdfParam3("Y");
		poolType.setUdfParam4("Y");
		poolType.setUdfParam5("Y");
		
		poolTypeList.add(poolType);
		
		poolTypeDto.setPoolTp("BN");
		poolTypeDto.setAdvAllowInd("Y");
		poolTypeDto.setAdvOverride("Y");
		poolTypeDto.setAdvRqdInd("Y");
		poolTypeDto.setAgreementRqd("Y");
		poolTypeDto.setMultiRsrvInd("Y");
		poolTypeDto.setPoolTpDesc("TEST");
		poolTypeDto.setPuAllowInd("Y");
		poolTypeDto.setPuRqdInd("Y");
		poolTypeDto.setRsrvInd("Y");
		poolTypeDto.setUdfParam1("Y");
		poolTypeDto.setUdfParam2("Y");
		poolTypeDto.setUdfParam3("Y");
		poolTypeDto.setUdfParam4("Y");
		poolTypeDto.setUdfParam5("Y");
		
		poolTypeDtoList.add(poolTypeDto);
		
		header = new HashMap<String, String>();
        header.put("userid", "Test");
        header.put("extensionschema", "Test");
	}

	@AfterEach
	void tearDown() throws Exception {
		poolType = null;
		poolTypeDto = null;
		poolTypeList = null;
		poolTypeDtoList = null;
	}

	@Test
	void testGetAllReserveType() {
		when(reserveTypeService.getReserveType()).thenReturn(poolTypeList);
		ResponseEntity<APIResponse<List<PoolTypeDTO>>> getReserveType = reserveTypeController.getAllReserveType();
		assertEquals(getReserveType.getStatusCodeValue(),200);
	}
	
	@Test
    void poolRuntimeException() {
		when(reserveTypeService.getReserveType()).thenThrow(new RuntimeException());
		when(reserveTypeService.insertReservationType(Mockito.any(), Mockito.any())).thenThrow(new RuntimeException());
		when(reserveTypeService.updateReservationType(Mockito.any(), Mockito.any())).thenThrow(new RuntimeException());
		when(reserveTypeService.deleteReservationType(Mockito.any())).thenThrow(new RuntimeException());

		ResponseEntity<APIResponse<List<PoolTypeDTO>>> getReserveType = reserveTypeController.getAllReserveType();
		ResponseEntity<APIResponse<PoolTypeDTO>> addReserveType = reserveTypeController.insertReservationType(poolTypeDto, header);
		ResponseEntity<APIResponse<PoolTypeDTO>> updateReserveType = reserveTypeController.updateReservationType(poolTypeDto, header);
		ResponseEntity<List<APIResponse<PoolTypeDTO>>> deleteReserveType = reserveTypeController.deleteReservationType(poolTypeDtoList);
		
		assertEquals(getReserveType.getStatusCodeValue(),500);
		assertEquals(addReserveType.getStatusCodeValue(),500);
		assertEquals(updateReserveType.getStatusCodeValue(),500);
		assertEquals(deleteReserveType.getStatusCodeValue(),500);
	}
	
	@Test
    void poolNoRecordsFoundException() {
		when(reserveTypeService.getReserveType()).thenThrow(new NoRecordsFoundException());
		when(reserveTypeService.insertReservationType(Mockito.any(), Mockito.any())).thenThrow(new NoRecordsFoundException());
		when(reserveTypeService.updateReservationType(Mockito.any(), Mockito.any())).thenThrow(new NoRecordsFoundException());
		
		ResponseEntity<APIResponse<List<PoolTypeDTO>>> getReserveType = reserveTypeController.getAllReserveType();
		ResponseEntity<APIResponse<PoolTypeDTO>> addReserveType = reserveTypeController.insertReservationType(poolTypeDto, header);
		ResponseEntity<APIResponse<PoolTypeDTO>> updateReserveType = reserveTypeController.updateReservationType(poolTypeDto, header);
		
		assertEquals(getReserveType.getStatusCodeValue(),404);
		assertEquals(addReserveType.getStatusCodeValue(),404);
		assertEquals(updateReserveType.getStatusCodeValue(),404);
	}
	
	@Test
    void poolSizeExceedException() {
		when(reserveTypeService.insertReservationType(Mockito.any(), Mockito.any())).thenThrow(new SizeExceedException());
		when(reserveTypeService.updateReservationType(Mockito.any(), Mockito.any())).thenThrow(new SizeExceedException());

		ResponseEntity<APIResponse<PoolTypeDTO>> addReserveType = reserveTypeController.insertReservationType(poolTypeDto, header);
		assertEquals(addReserveType.getStatusCodeValue(),411);
		ResponseEntity<APIResponse<PoolTypeDTO>> updateReserveType = reserveTypeController.updateReservationType(poolTypeDto, header);
		assertEquals(updateReserveType.getStatusCodeValue(),411);
	}
	
	@Test
    void poolRecordAlreadyExistsException() {
		when(reserveTypeService.insertReservationType(Mockito.any(), Mockito.any())).thenThrow(new RecordAlreadyExistsException());
		ResponseEntity<APIResponse<PoolTypeDTO>> addReserveType = reserveTypeController.insertReservationType(poolTypeDto, header);
		assertEquals(addReserveType.getStatusCodeValue(),208);
	}
	
	@Test
    void poolRecordNotAddedException() {
		when(reserveTypeService.insertReservationType(Mockito.any(), Mockito.any())).thenThrow(new RecordNotAddedException());
		when(reserveTypeService.updateReservationType(Mockito.any(), Mockito.any())).thenThrow(new RecordNotAddedException());

		ResponseEntity<APIResponse<PoolTypeDTO>> addReserveType = reserveTypeController.insertReservationType(poolTypeDto, header);
		assertEquals(addReserveType.getStatusCodeValue(),406);
		ResponseEntity<APIResponse<PoolTypeDTO>> updateReserveType = reserveTypeController.updateReservationType(poolTypeDto, header);
		assertEquals(updateReserveType.getStatusCodeValue(),406);
	}
	
	@Test
    void poolNullPointerException() {
		when(reserveTypeService.insertReservationType(Mockito.any(), Mockito.any())).thenThrow(new NullPointerException());
		when(reserveTypeService.updateReservationType(Mockito.any(), Mockito.any())).thenThrow(new NullPointerException());

		ResponseEntity<APIResponse<PoolTypeDTO>> addReserveType = reserveTypeController.insertReservationType(poolTypeDto, header);
		assertEquals(addReserveType.getStatusCodeValue(),400);
		ResponseEntity<APIResponse<PoolTypeDTO>> updateReserveType = reserveTypeController.updateReservationType(poolTypeDto, header);
		assertEquals(updateReserveType.getStatusCodeValue(),400);
	}

	@Test
	void testInsertReservationType() {
		when(reserveTypeService.insertReservationType(Mockito.any(), Mockito.any())).thenReturn(poolType);
		ResponseEntity<APIResponse<PoolTypeDTO>> addReserveType = reserveTypeController.insertReservationType(poolTypeDto, header);
		assertNotNull(addReserveType.getBody());
	}

	@Test
	void testUpdateReservationType() {
		when(poolTypeMapper.poolTypeDtoToPoolType(Mockito.any())).thenReturn(poolType);
		when(reserveTypeService.updateReservationType(Mockito.any(), Mockito.any())).thenReturn(poolType);
		when(poolTypeMapper.poolTypeToPoolTypeDTO(Mockito.any())).thenReturn(poolTypeDto);
		ResponseEntity<APIResponse<PoolTypeDTO>> updatedPoolType= reserveTypeController.updateReservationType(poolTypeDto, header);
		assertNotNull(updatedPoolType.getBody());
	}

	@Test
	void testDeleteReservationType() {
		Mockito.when(reserveTypeService.deleteReservationType(Mockito.any())).thenReturn(poolType);
		ResponseEntity<List<APIResponse<PoolTypeDTO>>> responseEntity = reserveTypeController.deleteReservationType(poolTypeDtoList);
		assertEquals(responseEntity.getStatusCodeValue(),200);
	}

	@Test
	void testDeleteReservationTypeEmptyList() {
		ResponseEntity<List<APIResponse<PoolTypeDTO>>> deletedPoolType = reserveTypeController.deleteReservationType(Collections.emptyList());
		assertEquals(deletedPoolType.getStatusCodeValue(),500);
	}

	@Test
	void testDeleteReservationTypeNoRecordFoundException() {
		when(reserveTypeService.deleteReservationType(Mockito.any())).thenThrow(new NoRecordsFoundException());
		ResponseEntity<List<APIResponse<PoolTypeDTO>>> deleteEmbargo = reserveTypeController.deleteReservationType(poolTypeDtoList);
		assertEquals(deleteEmbargo.getStatusCodeValue(),500);
	}

}
