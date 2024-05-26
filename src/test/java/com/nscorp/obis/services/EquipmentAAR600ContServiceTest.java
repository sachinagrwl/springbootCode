package com.nscorp.obis.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nscorp.obis.exception.RecordNotDeletedException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.nscorp.obis.domain.EquipmentAAR600Cont;
import com.nscorp.obis.domain.EquipmentInitialSpeedCodeMaintenance;
import com.nscorp.obis.dto.EquipmentAAR600ContDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.repository.EquipmentAAR600ContRepository;
import com.nscorp.obis.repository.EquipmentInitialSpeedCodeMaintenanceRepository;

class EquipmentAAR600ContServiceTest {
	
	@InjectMocks
	EquipmentAAR600ContServiceImpl eqAARContService;
	
	@Mock
	EquipmentAAR600ContRepository eqAARContRepo;
	
	@Mock
	EquipmentInitialSpeedCodeMaintenanceRepository eqSpeedInitRepo;
	
	EquipmentAAR600Cont eqAARCont;
	EquipmentAAR600ContDTO eqAARContDto;
	List<EquipmentAAR600Cont> eqAARContList;
	List<EquipmentAAR600ContDTO> eqAARContDtoList;
	EquipmentInitialSpeedCodeMaintenance eqInitSpeedCode;
	
	Map<String, String> header;
	
	BigDecimal beginningEqNr = new BigDecimal(1000);
	BigDecimal endEqNr = new BigDecimal(1000);
	
	EquipmentAAR600Cont addedCont;

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		
		MockitoAnnotations.initMocks(this);
		eqAARCont = new EquipmentAAR600Cont();
		eqAARCont.setEquipInit("APUL");
		eqAARCont.setBeginningEqNr(beginningEqNr);
		eqAARCont.setEndEqNbr(endEqNr);
		
		eqAARContList = new ArrayList<>();
		eqAARContList.add(eqAARCont);
		
		eqAARContDto = new EquipmentAAR600ContDTO();
		eqAARContDto.setEquipInit("APUL");
		eqAARContDto.setBeginningEqNr(beginningEqNr);
		eqAARContDto.setEndEqNbr(endEqNr);
		
		eqAARContDtoList = new ArrayList<>();
		eqAARContDtoList.add(eqAARContDto);
		
		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");
		
	}

	@AfterEach
	void tearDown() throws Exception {
		eqInitSpeedCode = null;
		eqAARCont = null;
		eqAARContList = null;
		eqAARContDto = null;
		eqAARContDtoList = null;
	}

	@Test
	void testGetAllCont() {
		when(eqAARContRepo.findAllByOrderByEquipInit()).thenReturn(eqAARContList);
		List<EquipmentAAR600Cont> allAARCont = eqAARContService.getAllCont();
		assertEquals(allAARCont, eqAARContList);
	}
	
	@Test
	void testGetAllContException() {
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(eqAARContService.getAllCont()));
		assertEquals("No Record Found under this search!", exception.getMessage());
	}

	@Test
	void testAddEqCont() {
		when(eqAARContRepo.existsByEquipInitAndBeginningEqNrAndEndEqNbr(Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(false);
		when(eqAARContRepo.save(Mockito.any())).thenReturn(eqAARCont);
		addedCont = eqAARContService.addEqCont(eqAARCont, header);
		assertNotNull(addedCont);
	}
	
	@Test
	void testAddEqInitShort() {
		eqAARCont.setEquipInit("A");
		when(eqAARContRepo.existsByEquipInitAndBeginningEqNrAndEndEqNbr(Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(false);
		when(eqAARContRepo.existsByEquipInitAndBeginningEqNrAndEndEqNbr(Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(false);
		when(eqAARContRepo.save(Mockito.any())).thenReturn(eqAARCont);
		addedCont = eqAARContService.addEqCont(eqAARCont, header);
		assertNotNull(addedCont);
	}
	
	@Test
	void testAddEqContException() {
		EquipmentAAR600Cont eq = new EquipmentAAR600Cont();
		eq.setEquipInit("APUL");
		eq.setBeginningEqNr(beginningEqNr);
		eq.setEndEqNbr(endEqNr);
		when(eqAARContRepo.existsByEquipInitAndBeginningEqNrAndEndEqNbr(Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(true);
		RecordAlreadyExistsException exception = assertThrows(RecordAlreadyExistsException.class,
				() -> when(eqAARContService
							.addEqCont(eq, header)));
		assertEquals("Record already exists under Initial: APUL, BeginningNumber: 1000, EndingNumber: 1000", exception.getMessage());
	}

	@Test
	void testAddEqContRecordNotAddedException() {
		EquipmentAAR600Cont eq = new EquipmentAAR600Cont();
		eq.setEquipInit("APUL");
		eq.setBeginningEqNr(BigDecimal.valueOf(10));
		eq.setEndEqNbr(BigDecimal.valueOf(9));
		RecordNotAddedException exception1 = assertThrows(RecordNotAddedException.class,
				() -> when(eqAARContService
						.addEqCont(eq, header)));
		assertEquals("Ending Equipment Number should be greater than the Beginning Equipment Number", exception1.getMessage());
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> when(eqAARContService
						.addEqCont(eqAARCont, header)));
		assertEquals("Record Not added", exception.getMessage());
	}
	
	@Test
	void testAddEqContRecordAlreadyExistsException() {
		EquipmentAAR600Cont eq = new EquipmentAAR600Cont();
		
		eq.setEquipInit("APUL");
		eq.setBeginningEqNr(beginningEqNr);
		eq.setEndEqNbr(endEqNr);
		when(eqAARContRepo.existsByEquipInitAndBeginningEqNrAndEndEqNbr(eq.getEquipInit() ,eq.getBeginningEqNr(),eq.getEndEqNbr())).thenReturn(true);
		RecordAlreadyExistsException exception = assertThrows(RecordAlreadyExistsException.class,
				() -> when(eqAARContService
							.addEqCont(eq, header)));
		assertEquals("Record already exists under Initial: APUL, BeginningNumber: 1000, EndingNumber: 1000", exception.getMessage());
	}

	@Test
	void testDeleteTable() {
		when(eqAARContRepo.existsByEquipInitAndBeginningEqNrAndEndEqNbr(Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(true);
		eqAARContService.deleteEqCont(eqAARCont);
	}

	@Test
	void testDeleteTabletRecordNotAddedException() {
		RecordNotDeletedException exception = assertThrows(RecordNotDeletedException.class,
				() -> eqAARContService.deleteEqCont(eqAARCont));
		assertEquals(eqAARCont.getEquipInit()  + " and " + eqAARCont.getBeginningEqNr() + " and " + eqAARCont.getEndEqNbr() + " Record Not Found!", exception.getMessage());
	}
	
}
