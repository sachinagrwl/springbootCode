package com.nscorp.obis.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.*;

import com.nscorp.obis.domain.TiaTrucker;
import com.nscorp.obis.domain.Trucker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.nscorp.obis.dto.TruckerDTO;
import com.nscorp.obis.dto.mapper.TruckerMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.TiaTruckerService;

public class TiaTruckerControllerTest {
	@InjectMocks
    TiaTruckerController tiaTruckerController;
	@Mock
    TruckerMapper truckerMapper;
	@Mock
	TiaTruckerService tiaTruckerService;

	TruckerDTO truckerDTO;
	List<TruckerDTO> truckerDTOList;
	Trucker trucker;
	List<Trucker> truckerList;
	TiaTrucker tiaTrucker;

//	ResponseEntity<Object> responseEntity;

	String truckerCode;
	String truckerCode1;
	String truckerCode2;
	Map<String, String> header;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		tiaTrucker= new TiaTrucker();
		tiaTrucker.setTruckerCode("TGFJ");

		trucker= new Trucker();
		trucker.setTruckerName("Name");
		trucker.setTruckerCode("TGFJ");
		truckerList=new ArrayList<>();
		truckerList.add(trucker);

		truckerDTO=new TruckerDTO();
		truckerDTO.setTruckerCode("TGFJ");
		truckerDTO.setTruckerName("Name");
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
		trucker=null;
		truckerList = null;
		truckerDTO=null;
		truckerDTOList = null;
	}

	@Test
	void testGetTrucker() {
		when(tiaTruckerService.getTruckerNameByTruckerCode(Mockito.any())).thenReturn(truckerList);
		ResponseEntity<APIResponse<List<TruckerDTO>>> truckerList = tiaTruckerController.getTruckerNameByTruckerCode(truckerCode);
		System.out.println(truckerList);
		assertEquals(truckerList.getStatusCodeValue(), 200);
	}

	@Test
	void testTruckerNoRecordsFoundException() {
		when(tiaTruckerService.getTruckerNameByTruckerCode(Mockito.any())).thenThrow(new NoRecordsFoundException());
        ResponseEntity<APIResponse<List<TruckerDTO>>> truckerList = tiaTruckerController.getTruckerNameByTruckerCode(truckerCode1);
        assertEquals(truckerList.getStatusCodeValue(), 404);
	}
	@Test
	void testTruckerException() {
		when(tiaTruckerService.getTruckerNameByTruckerCode(Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<List<TruckerDTO>>> truckerList = tiaTruckerController.getTruckerNameByTruckerCode(truckerCode2);
		assertEquals(truckerList.getStatusCodeValue(), 500);
	}
}
