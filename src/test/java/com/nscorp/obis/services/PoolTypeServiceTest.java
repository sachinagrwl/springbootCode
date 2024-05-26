package com.nscorp.obis.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.domain.PoolType;
import com.nscorp.obis.dto.PoolTypeDTO;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.repository.PoolRepository;
import com.nscorp.obis.repository.PoolTypeRepository;

class PoolTypeServiceTest {
	
	@InjectMocks
    PoolTypeServiceImpl reserveTypeService;
	
	@Mock
	PoolTypeRepository reserveTypeRepo;
	
	@Mock
	PoolRepository poolRepo;
	
	PoolType poolType;
	PoolTypeDTO poolTypeDto;
	List<PoolType> poolTypeList;
	List<PoolTypeDTO> poolTypeDtoList;
	PoolType addedPoolType;
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
		poolType.setAdvAllowInd("Y");
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
	void testGetReserveType() {
		when(reserveTypeRepo.findAll()).thenReturn(poolTypeList);
		List<PoolType> poolType = reserveTypeService.getReserveType();
		assertEquals(poolType, poolTypeList);
	}
	
	@Test
	void testNoRecordsFoundException() {
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
	            () -> when(reserveTypeService.getReserveType()));
	    Assertions.assertEquals("No Records Found!", exception.getMessage());
	}

	@Test
	void testInsertReservationType() {
		when(reserveTypeRepo.existsById(Mockito.any())).thenReturn(false);
		when(reserveTypeRepo.save(Mockito.any())).thenReturn(poolType);
		addedPoolType = reserveTypeService.insertReservationType(poolType, header);
		assertNotNull(addedPoolType);
	}
	
	@Test
	void testAddTermIdNullPointerException() {
		header.put("extensionschema", null);
		assertNull(header.get(CommonConstants.EXTENSION_SCHEMA));
		NullPointerException exception = assertThrows(NullPointerException.class,
				() -> reserveTypeService.insertReservationType(poolType, header));
		assertEquals("Extension Schema should not be null, empty or blank", exception.getMessage());
	}
	
	@Test
	void testInsertRecordAlreadyExistsException() {
		when(reserveTypeRepo.existsById(Mockito.any())).thenReturn(true);
		
		RecordAlreadyExistsException exception = assertThrows(RecordAlreadyExistsException.class,
				() -> reserveTypeService.insertReservationType(poolType, header));
		assertEquals("Record Already Exists under the Code: BN", exception.getMessage());
	}
	
	@Test
	void testInsertRecordNotAddedException() {
//		PoolType poolTp = new PoolType();
		
		poolType.setAdvOverride("Y");
		poolType.setAdvRqdInd("N");
		
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> reserveTypeService.insertReservationType(poolType, header));
		assertEquals("Advance Reservation must be required for the EMP Exception", exception.getMessage());
	}
	
	@Test
	void testRecordNotAddedException() {

		poolType.setUdfParam1("Y");
		poolType.setAdvAllowInd("N");
		poolType.setUversion("!");
		when(reserveTypeRepo.existsById(Mockito.any())).thenReturn(false);
		when(reserveTypeRepo.findById(Mockito.any())).thenReturn(Optional.of(poolType));
		
		RecordNotAddedException exceptions = assertThrows(RecordNotAddedException.class,
				() -> reserveTypeService.updateReservationType(poolType, header));
		assertEquals("Advanced Reservations must be allowed to require specific Eq", exceptions.getMessage());
	}
	
	@Test
	void testInvalidDataException() {

		poolType.setAdvRqdInd("N");
		poolType.setAdvAllowInd(null);
		
		InvalidDataException exceptions = assertThrows(InvalidDataException.class,
				() -> reserveTypeService.insertReservationType(poolType, header));
		assertEquals("'advAllowInd' value should not be Blank or Null.", exceptions.getMessage());
	}

	@Test
	void testUpdateReservationType() {
		poolType.setUversion("!");
		when(reserveTypeRepo.existsById(Mockito.any())).thenReturn(true);
		when(reserveTypeRepo.findById(Mockito.any())).thenReturn(Optional.of(poolType));
		when(reserveTypeRepo.save(Mockito.any())).thenReturn(poolType);
		PoolType poolTp = reserveTypeService.updateReservationType(poolType, header);
		assertEquals(poolTp.getPoolTp(), poolType.getPoolTp());
	}

	@Test
	void testUpdateNoRecordsFoundException() {
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(reserveTypeService.updateReservationType(poolType, header)));
		assertEquals("No record found for this 'poolType': "+poolType.getPoolTp(), exception.getMessage());
	}

	@Test
	void testDeleteEquipRestriction() {
		when(reserveTypeRepo.existsById(Mockito.any())).thenReturn(true);
		when(reserveTypeRepo.findById(Mockito.any())).thenReturn(Optional.of(poolType));
		when(poolRepo.existsByPoolReservationType(Mockito.any())).thenReturn(false);
		PoolType poolTp = reserveTypeService.deleteReservationType(poolType);
		assertEquals(poolTp,poolType);
	}
	
	@Test
	void testDeleteEquipRestrictionException() {
		when(reserveTypeRepo.existsById(Mockito.any())).thenReturn(true);
		when(reserveTypeRepo.findById(Mockito.any())).thenReturn(Optional.of(poolType));
		when(poolRepo.existsByPoolReservationType(Mockito.any())).thenReturn(true);
		
		RecordAlreadyExistsException exception = assertThrows(RecordAlreadyExistsException.class,
				() -> when(reserveTypeService.deleteReservationType(poolType)));
		assertEquals("The Pool Type : BN is already in use in Pool Maintenance and cannot be deleted", exception.getMessage());
	}

	@Test
	void testEquipRestrictionRecordNotDeletedException() {
		when(reserveTypeRepo.existsById(Mockito.any())).thenReturn(false);

		assertThrows(NoRecordsFoundException.class,
				() -> reserveTypeService.deleteReservationType(poolType));
	}

}
