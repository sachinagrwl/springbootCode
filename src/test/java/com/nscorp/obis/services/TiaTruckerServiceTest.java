package com.nscorp.obis.services;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.*;

import com.nscorp.obis.domain.TiaTrucker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import com.nscorp.obis.repository.TruckerRepository;
import com.nscorp.obis.domain.Trucker;
import com.nscorp.obis.dto.TruckerDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.repository.TiaTruckerRepository;

public class TiaTruckerServiceTest {
	@InjectMocks
	TiaTruckerServiceImpl truckerService;

	@Mock
	TruckerRepository truckerRepository;

	@Mock
	TiaTruckerRepository tiaTruckerRepository;
	TiaTrucker tiaTrucker;
	Trucker trucker;
	List<Trucker> truckerList;
	List<Trucker> truckerList1;
	TruckerDTO truckerDTO;
	List<TruckerDTO> truckerDTOList;
//	ResponseEntity<Object> responseEntity;
//	String url;
	Map<String, String> header;
	String truckerCode;
	String truckerCode1;
	String truckerCode2;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		tiaTrucker= new TiaTrucker();
		tiaTrucker.setTruckerCode("TGFJ");

		trucker= new Trucker();
		trucker.setTruckerName("TOLL GLOBAL FORWARDING SCS (US");
		trucker.setTruckerCode("TGFJ");
		truckerList=new ArrayList<>();
		truckerList.add(trucker);

		truckerDTO=new TruckerDTO();
		truckerDTO.setTruckerCode("TGFJ");
		truckerDTO.setTruckerName("TOLL GLOBAL FORWARDING SCS (US");
		truckerDTOList=new ArrayList<>();
		truckerDTOList.add(truckerDTO);

		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");

		truckerCode="TGFJ";
		truckerCode1="TLLD";
	}
	
	@AfterEach
	void tearDown() throws Exception {
		trucker = null;
		truckerList = null;
		truckerDTO = null;
		truckerDTOList = null;
	}
	
	@Test
	void testGetTruckerNameByTruckerCode() {
		when(truckerRepository.findTruckerByTruckerCode(truckerCode)).thenReturn(truckerList);
//		when(truckerRepository.findTruckerByTruckerCode(truckerCode).isEmpty()).thenReturn(false);
//		assertNotNull(truckerRepository.findTruckerByTruckerCode(truckerCode));
		when(tiaTruckerRepository.existsByTruckerCode(truckerCode)).thenReturn(true);
		List<Trucker> getTrucker = truckerService.getTruckerNameByTruckerCode(truckerCode);
		assertEquals(getTrucker, truckerList);
	}

	@Test
	void testGetTruckerNameByTruckerCodeNoRecordFoundException() {
//		when(truckerRepository.findTruckerByTruckerCode(truckerCode1).isEmpty()).thenReturn(true);
		when(truckerRepository.findTruckerByTruckerCode(truckerCode1)).thenReturn(Collections.emptyList());
		when(tiaTruckerRepository.existsByTruckerCode(truckerCode1)).thenReturn(false);
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(truckerService.getTruckerNameByTruckerCode(truckerCode1)));
		assertEquals("No records found", exception.getMessage());

		when(truckerRepository.findTruckerByTruckerCode(truckerCode1)).thenReturn(truckerList);
		when(tiaTruckerRepository.existsByTruckerCode(truckerCode1)).thenReturn(false);
		NoRecordsFoundException exception1 = assertThrows(NoRecordsFoundException.class,
				() -> when(truckerService.getTruckerNameByTruckerCode(truckerCode1)));
		assertEquals("No records found", exception1.getMessage());
	}
}