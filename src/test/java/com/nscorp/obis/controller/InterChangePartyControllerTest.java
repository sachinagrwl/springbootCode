
package com.nscorp.obis.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.nscorp.obis.domain.InterChangeParty;
import com.nscorp.obis.dto.InterChangePartyDTO;
import com.nscorp.obis.dto.mapper.InterChangePartyMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.SizeExceedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.InterChangePartyService;

public class InterChangePartyControllerTest {

	@Mock
	InterChangePartyService interChangePartyService;

	@Mock
	InterChangePartyMapper interChangePartyMapper;

	@InjectMocks
	InterChangePartyController interChangePartyController;
	
	InterChangePartyDTO interChangePartyDTO;
	InterChangeParty interChangeParty;
	List<InterChangePartyDTO> interChangePartyDTOList;
	List<InterChangeParty> interChangePartyList;
	Map<String, String> header;
	 String ichgCode;
	 
	
	@SuppressWarnings("deprecation")

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		interChangeParty = new InterChangeParty();
		interChangeParty.setIchgCode("BAR");
		interChangeParty.setIchgCdDesc("Valid");
		interChangeParty.setRoadOtherInd("O");
		interChangeParty.setCreateUserId("use");

		interChangePartyList = new ArrayList<>();
		interChangePartyList.add(interChangeParty);
		
		interChangePartyDTO = new InterChangePartyDTO();
		interChangePartyDTO.setIchgCode("BAR");
		interChangePartyDTO.setIchgCdDesc("Valid");
		interChangePartyDTO.setRoadOtherInd("O");
		interChangePartyDTO.setCreateUserId("use");
		
		interChangePartyDTOList = new ArrayList<>();
		interChangePartyDTOList.add(interChangePartyDTO);
		
		ichgCode = "BAR";
		
		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");
		
	}
	@AfterEach
	void tearDown() throws Exception {
		interChangePartyDTO = null;
		interChangeParty = null;
		interChangePartyDTOList = null;
		interChangePartyList = null;
	}
	
	@Test
	void testGetInterChangeParty() {
		when(interChangePartyService.getAllTables(ichgCode)).thenReturn(interChangePartyList);
		ResponseEntity<APIResponse<List<InterChangePartyDTO>>> interchangeList = interChangePartyController.getAllTables(ichgCode);
		assertEquals(interchangeList.getStatusCodeValue(), 200);
	}
	
	@Test
	void testGetInterChangePartyNoRecordsFoundException() {
		when(interChangePartyService.getAllTables(ichgCode)).thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<List<InterChangePartyDTO>>> interchangeList = interChangePartyController.getAllTables(ichgCode);
		assertEquals(interchangeList.getStatusCodeValue(), 404);
	}
	
	@Test
	void testAddInterChangePartyNoRecordsFoundException() {
		when(interChangePartyService.insertInterChangeParty(Mockito.any(), Mockito.any())).thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<InterChangePartyDTO>> addInterchange= interChangePartyController.insertInterChangeParty(Mockito.any(),Mockito.any());
		assertEquals(addInterchange.getStatusCodeValue(), 404);
	}
	
	
	@Test
	void testGetInterChangePartyException() {
		when(interChangePartyService.getAllTables(ichgCode)).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<List<InterChangePartyDTO>>> interchangeList = interChangePartyController.getAllTables(ichgCode);
		assertEquals(interchangeList.getStatusCodeValue(), 500);
	}
	
	@Test
	void testAddInterChangePartyException() {
		when(interChangePartyService.insertInterChangeParty(Mockito.any(), Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<InterChangePartyDTO>> addInterchange = interChangePartyController.insertInterChangeParty(Mockito.any(),Mockito.any());
		assertEquals(addInterchange.getStatusCodeValue(), 500);
	}
	
	@Test
	void testDeleteInterChangeParty() {
		when(interChangePartyMapper.interChangePartyDTOTointerChangeParty(interChangePartyDTO)).thenReturn(interChangeParty);
		interChangePartyService.deleteInterChangeParty(Mockito.any());
		when(interChangePartyMapper.interChangePartyTointerChangePartyDTO(Mockito.any())).thenReturn(interChangePartyDTO);
		ResponseEntity<List<APIResponse<InterChangePartyDTO>>> deleteList = interChangePartyController.deleteInterchangePartyDto(interChangePartyDTOList);
		assertEquals(deleteList.getStatusCodeValue(), 200);
	}


	@Test
	void testInsertInterChangeParty() {
		when(interChangePartyMapper.interChangePartyDTOTointerChangeParty(Mockito.any())).thenReturn(interChangeParty);
		when(interChangePartyService.insertInterChangeParty(Mockito.any(), Mockito.any())).thenReturn(interChangeParty);
		when(interChangePartyMapper.interChangePartyTointerChangePartyDTO(Mockito.any()))
				.thenReturn(interChangePartyDTO);
		ResponseEntity<APIResponse<InterChangePartyDTO>> addedIchg = interChangePartyController
				.insertInterChangeParty(interChangePartyDTO, header);
		assertNotNull(addedIchg.getBody());
	}
	@Test
	void testErrorDeleteInterChangeParty() {
		InterChangeParty interChangeParty1 = new InterChangeParty();
		interChangeParty1.setIchgCode("BAR");
		interChangeParty1.setIchgCdDesc("Valid");
		interChangeParty1.setRoadOtherInd("O");
		interChangeParty1.setCreateUserId("use");
		
		List<InterChangeParty> codeList1 = new ArrayList<>();
		codeList1.add(interChangeParty1);

		List<InterChangePartyDTO> interChangePartyDTODTOList = new ArrayList<>();
		
		when(interChangePartyMapper.interChangePartyDTOTointerChangeParty(Mockito.any())).thenReturn(interChangeParty1);
		interChangePartyService.deleteInterChangeParty(interChangeParty1);
		when(interChangePartyMapper.interChangePartyTointerChangePartyDTO(interChangeParty1)).thenReturn(interChangePartyDTO);
		
		ResponseEntity<List<APIResponse<InterChangePartyDTO>>> deleteList = interChangePartyController.deleteInterchangePartyDto(interChangePartyDTODTOList);

		assertEquals(deleteList.getStatusCodeValue(),500);
	}
	
	
	@Test
    void testUpdateInterChangeParty() {//ok
        when(interChangePartyMapper.interChangePartyDTOTointerChangeParty(Mockito.any())).thenReturn(interChangeParty);
        when(interChangePartyService.updateInterChangeParty(Mockito.any(), Mockito.any())).thenReturn(interChangeParty);
        when(interChangePartyMapper.interChangePartyTointerChangePartyDTO(Mockito.any()))
                .thenReturn(interChangePartyDTO);
        ResponseEntity<APIResponse<InterChangePartyDTO>> update = interChangePartyController
                .updateInterChangeParty(interChangePartyDTO, header); 
        // assertNotNull(update.getBody().getData());
        assertEquals(update.getStatusCodeValue(), 200);

 

    }

 

    @Test
    void testUpdateInterChangePartyException() {//
    	 when(interChangePartyMapper.interChangePartyDTOTointerChangeParty(Mockito.any())).thenReturn(interChangeParty);
        when(interChangePartyService.updateInterChangeParty(Mockito.any(), Mockito.any()))
                .thenThrow(new RuntimeException());
        ResponseEntity<APIResponse<InterChangePartyDTO>> updateInterchange = interChangePartyController
                .updateInterChangeParty(interChangePartyDTO, header);
        assertEquals(updateInterchange.getStatusCodeValue(), 500);

 

    }

    @Test
    void testUpdateInterChangePartyNoRecordsFoundException1() {//
    	 when(interChangePartyMapper.interChangePartyDTOTointerChangeParty(Mockito.any())).thenReturn(interChangeParty);
        when(interChangePartyService.updateInterChangeParty(Mockito.any(), Mockito.any()))
                .thenThrow(new NoRecordsFoundException());
        ResponseEntity<APIResponse<InterChangePartyDTO>> updateInterchange = interChangePartyController
                .updateInterChangeParty(interChangePartyDTO, header);
        assertEquals(updateInterchange.getStatusCodeValue(), 404);

 

    }

    @Test
    void testUpdateInterChangePartySizeExceedException() {//
    	when(interChangePartyMapper.interChangePartyDTOTointerChangeParty(Mockito.any())).thenReturn(interChangeParty);
        when(interChangePartyService.updateInterChangeParty(Mockito.any(), Mockito.any()))
                .thenThrow(new SizeExceedException());
        ResponseEntity<APIResponse<InterChangePartyDTO>> updateInterchange = interChangePartyController
                .updateInterChangeParty(interChangePartyDTO, header);
        assertEquals(updateInterchange.getStatusCodeValue(), 411);

    }

	
	
}
