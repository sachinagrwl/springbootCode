package com.nscorp.obis.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nscorp.obis.domain.TruckerGroup;
import com.nscorp.obis.dto.EquipmentRestrictDTO;
import com.nscorp.obis.dto.TruckerGroupDTO;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.nscorp.obis.domain.Pool;
import com.nscorp.obis.dto.PoolDTO;
import com.nscorp.obis.dto.mapper.PoolMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.SizeExceedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.PoolService;

//@SpringBootTest
public class PoolControllerTest {
	
	@Mock
	PoolService poolService;
	
	@Mock
	PoolMapper mapper;
	
	@InjectMocks
	PoolController controller;
	
	Long poolId;
	String poolName;
	String fetchAll;
	List<Pool> pools;

	TruckerGroup truckerGroup;
	Pool pool;
	PoolDTO poolDto;
	List<PoolDTO> poolDtoList;
	Map<String, String> header;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);

		fetchAll = "yes";

		truckerGroup = new TruckerGroup();
		truckerGroup.setTruckerGroupCode("TEST");
		truckerGroup.setTruckerGroupDesc("TEST");
		truckerGroup.setSetupSchema("TEST");

		pools = new ArrayList<>();
		pool = new Pool();

		pool.setPoolId(100L);
		pool.setPoolName("Test1");
		pool.setDescription("Test Desc1");
		pool.setPoolReservationType("HJ");
		pool.setTruckerGroup(truckerGroup);
		pool.setAgreementRequired("T");
		pool.setCheckTrucker("B");
		pools.add(pool);

		poolDto = new PoolDTO();
		poolDto.setPoolId(100L);
		poolDto.setPoolName("Test1");
		poolDto.setDescription("Test Desc1");
		poolDto.setPoolReservationType("HJ");
		poolDto.setTruckerGroup(truckerGroup);
		poolDto.setAgreementRequired("T");
		poolDto.setCheckTrucker("B");

		poolDtoList = new ArrayList<>();
		poolDtoList.add(poolDto);
		
		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");
	}
	@AfterEach
	void tearDown() throws Exception {
	}
	
	@Test
	void testGetPoolsFetchAll() {
		when(poolService.getPools(Mockito.any(), Mockito.any())).thenReturn(pools);
		ResponseEntity<APIResponse<List<PoolDTO>>> pools = controller.getPools(poolId, poolName, fetchAll);
		assertEquals(pools.getStatusCode(), HttpStatus.OK);
	}

	@Test
	void testGetPools() {
		fetchAll = null;
		when(poolService.getPools(Mockito.any(), Mockito.any())).thenReturn(pools);
		ResponseEntity<APIResponse<List<PoolDTO>>> pools = controller.getPools(poolId, poolName, fetchAll);
		assertEquals(pools.getStatusCode(), HttpStatus.OK);
	}

	@Test
	void testNoRecordsFoundException() {
		when(poolService.getPools(Mockito.any(), Mockito.any())).thenThrow(new NoRecordsFoundException("No record found."));
		ResponseEntity<APIResponse<List<PoolDTO>>> pools = controller.getPools(poolId, poolName, fetchAll);
		assertEquals(pools.getStatusCode(), HttpStatus.NOT_FOUND);
		
		when(poolService.updatePoolTerminal(Mockito.any(), Mockito.any())).thenThrow(new NoRecordsFoundException("No record found."));
		ResponseEntity<APIResponse<PoolDTO>> poolTerminalUpdated = controller.updatePoolTerminal(poolDto, header);
		assertEquals(poolTerminalUpdated.getStatusCodeValue(),404);

		when(poolService.addPool(Mockito.any(), Mockito.any())).thenThrow(new NoRecordsFoundException());
		when(poolService.updatePool(Mockito.any(), Mockito.any())).thenThrow(new NoRecordsFoundException());
		when(poolService.deletePool(Mockito.any())).thenThrow(new NoRecordsFoundException());

		ResponseEntity<APIResponse<PoolDTO>> poolAdded = controller.addPool(poolDto, header);
		assertEquals(poolAdded.getStatusCodeValue(),404);
		ResponseEntity<APIResponse<PoolDTO>> poolUpdated = controller.updatePool(poolDto, header);
		assertEquals(poolUpdated.getStatusCodeValue(),404);
		ResponseEntity<List<APIResponse<PoolDTO>>> poolDeleted = controller.deletePool(poolDtoList);
		assertEquals(poolUpdated.getStatusCodeValue(),404);
	}
	
	@Test
	void testLoadLimitRuntimeException() {
		when(poolService.updatePoolTerminal(Mockito.any(), Mockito.any())).thenThrow(new RuntimeException());
		when(poolService.addPool(Mockito.any(), Mockito.any())).thenThrow(new RuntimeException());
		when(poolService.updatePool(Mockito.any(), Mockito.any())).thenThrow(new RuntimeException());
		when(poolService.deletePool(Mockito.any())).thenThrow(new RuntimeException());

		ResponseEntity<APIResponse<PoolDTO>> poolTerminalUpdated = controller.updatePoolTerminal(poolDto, header);
		assertEquals(poolTerminalUpdated.getStatusCodeValue(),500);
		ResponseEntity<APIResponse<PoolDTO>> poolAdded = controller.addPool(poolDto, header);
		assertEquals(poolAdded.getStatusCodeValue(),500);
		ResponseEntity<APIResponse<PoolDTO>> poolUpdated = controller.updatePool(poolDto, header);
		assertEquals(poolUpdated.getStatusCodeValue(),500);
		ResponseEntity<List<APIResponse<PoolDTO>>> poolDeleted = controller.deletePool(poolDtoList);
		assertEquals(poolUpdated.getStatusCodeValue(),500);
	}
	
	@Test
	void testLoadLimitNullPointerException() {
		when(poolService.updatePoolTerminal(Mockito.any(), Mockito.any())).thenThrow(new NullPointerException());
		when(poolService.addPool(Mockito.any(), Mockito.any())).thenThrow(new NullPointerException());
		when(poolService.updatePool(Mockito.any(), Mockito.any())).thenThrow(new NullPointerException());

		ResponseEntity<APIResponse<PoolDTO>> poolTerminalUpdated = controller.updatePoolTerminal(poolDto, header);
		assertEquals(poolTerminalUpdated.getStatusCodeValue(),400);
		ResponseEntity<APIResponse<PoolDTO>> poolAdded = controller.addPool(poolDto, header);
		assertEquals(poolAdded.getStatusCodeValue(),400);
		ResponseEntity<APIResponse<PoolDTO>> poolUpdated = controller.updatePool(poolDto, header);
		assertEquals(poolUpdated.getStatusCodeValue(),400);
	}

	@Test
	void testUpdateRecordAlreadyExistsException() {
		when(poolService.addPool(Mockito.any(), Mockito.any())).thenThrow(new RecordAlreadyExistsException());

		ResponseEntity<APIResponse<PoolDTO>> poolUpdated = controller.addPool(poolDto, header);
		assertEquals(poolUpdated.getStatusCodeValue(),208);
	}

	@Test
	void testAddUpdateRecordNotAddedException() {
		when(poolService.addPool(Mockito.any(), Mockito.any())).thenThrow(new RecordNotAddedException());
		when(poolService.updatePool(Mockito.any(), Mockito.any())).thenThrow(new RecordNotAddedException());

		ResponseEntity<APIResponse<PoolDTO>> poolAdded = controller.addPool(poolDto, header);
		assertEquals(poolAdded.getStatusCodeValue(),406);
		ResponseEntity<APIResponse<PoolDTO>> poolUpdated = controller.updatePool(poolDto, header);
		assertEquals(poolUpdated.getStatusCodeValue(),406);
	}
	
	@Test
	void testLoadLimitSizeExceedException() {
		when(poolService.updatePoolTerminal(Mockito.any(), Mockito.any())).thenThrow(new SizeExceedException());
		when(poolService.addPool(Mockito.any(), Mockito.any())).thenThrow(new SizeExceedException());
		when(poolService.updatePool(Mockito.any(), Mockito.any())).thenThrow(new SizeExceedException());

		ResponseEntity<APIResponse<PoolDTO>> poolTerminalUpdated = controller.updatePoolTerminal(poolDto, header);
		assertEquals(poolTerminalUpdated.getStatusCodeValue(),411);
		ResponseEntity<APIResponse<PoolDTO>> poolAdded = controller.addPool(poolDto, header);
		assertEquals(poolAdded.getStatusCodeValue(),411);
		ResponseEntity<APIResponse<PoolDTO>> poolUpdated = controller.updatePool(poolDto, header);
		assertEquals(poolUpdated.getStatusCodeValue(),411);
	}
	
	@Test
	void testException() {
		when(poolService.getPools(Mockito.any(), Mockito.any())).thenThrow(new RuntimeException("exception occurred"));
		ResponseEntity<APIResponse<List<PoolDTO>>> pools = controller.getPools(poolId, poolName, fetchAll);
		assertEquals(pools.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

//	@Test
//	void testEquipmentTareWeightsOptimisticLockException() {
//		when(poolService.addPool(Mockito.any(),Mockito.any())).thenThrow(new RuntimeException("Test", new ConstraintViolationException()));
//
//		ResponseEntity<APIResponse<PoolDTO>> addedPool = controller.addPool(Mockito.any(),Mockito.any());
//
//		assertEquals(addedPool.getStatusCodeValue(), 400);
//	}
	
	@Test
	void testUpdatePool() {
		when(mapper.poolDtoToPool(Mockito.any())).thenReturn(pool);
		when(poolService.updatePoolTerminal(Mockito.any(), Mockito.any())).thenReturn(pool);
		when(mapper.poolToPoolDto(Mockito.any())).thenReturn(null);
		ResponseEntity<APIResponse<PoolDTO>> poolUpdated = controller.updatePoolTerminal(poolDto, header);
		assertEquals(poolUpdated.getStatusCodeValue(),200);
		
	}

	@Test
	void testAddEquipmentRestriction() {
		when(mapper.poolDtoToPool(Mockito.any())).thenReturn(pool);
		when(poolService.addPool(Mockito.any(), Mockito.any())).thenReturn(pool);
		when(mapper.poolToPoolDto(Mockito.any())).thenReturn(poolDto);
		ResponseEntity<APIResponse<PoolDTO>> addedPool = controller.addPool(poolDto,
				header);
		assertNotNull(addedPool.getBody());
	}

	@Test
	void testUpdateEquipmentRestriction() {
		when(mapper.poolDtoToPool(Mockito.any())).thenReturn(pool);
		when(poolService.updatePool(Mockito.any(), Mockito.any())).thenReturn(pool);
		when(mapper.poolToPoolDto(Mockito.any())).thenReturn(poolDto);
		ResponseEntity<APIResponse<PoolDTO>> updatedEqRestriction = controller.updatePool(poolDto, header);
		assertNotNull(updatedEqRestriction.getBody());
	}

	@Test
	void testDeleteOverrideWeights() {
		Mockito.when(poolService.deletePool(Mockito.any())).thenReturn(pool);
		ResponseEntity<List<APIResponse<PoolDTO>>> responseEntity = controller.deletePool(poolDtoList);
		assertEquals(responseEntity.getStatusCodeValue(),200);
	}
}
