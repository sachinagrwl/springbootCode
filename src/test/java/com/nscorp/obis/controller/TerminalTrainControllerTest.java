package com.nscorp.obis.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

import com.nscorp.obis.domain.TerminalTrain;
import com.nscorp.obis.dto.TerminalTrainDTO;
import com.nscorp.obis.dto.mapper.TerminalTrainMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.SizeExceedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.TerminalTrainService;



public class TerminalTrainControllerTest {
	
	
	@Mock
	TerminalTrainService terminalTrainService;

	@Mock
	TerminalTrainMapper terminalTrainMapper;

	@InjectMocks
	TerminalTrainController terminalTrainController;

	TerminalTrainDTO terminalTrainDto;
//	TerminalTrainList terminalTrainDtoList;
	TerminalTrain terminalTrain;
	List<TerminalTrain> terminalTrainList;
	List<TerminalTrainDTO> terminalTrainDtoList;

	Map<String, String> header;

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		
		MockitoAnnotations.initMocks(this);
		terminalTrain= new TerminalTrain();
		terminalTrainDto = new TerminalTrainDTO();
		terminalTrainDtoList = new ArrayList<>();
		terminalTrainList = new ArrayList<>();
		terminalTrain.setTermId((long) 12345);
		terminalTrain.setTrainNr("123");
		terminalTrain.setTrainDesc("Description of a specific train");
		terminalTrain.setCutoffDefault(null);
		terminalTrain.setCutoffMon(null);
		terminalTrain.setCutoffTue(null);
		terminalTrain.setCutoffWed(null);
		terminalTrain.setCutoffThu(null);
		terminalTrain.setCutoffFri(null);
		terminalTrain.setCutoffSat(null);
		terminalTrain.setCutoffSun(null);
		terminalTrain.setTrainDir("Train Directory");
		terminalTrain.setMaxFootage(564738);
		
		terminalTrainDto.setTermId((long) 12345);
		terminalTrainDto.setTrainNr("123");
		terminalTrainDto.setTrainDesc("Description of a specific train");
		terminalTrainDto.setCutoffDefault(null);
		terminalTrainDto.setCutoffMon(null);
		terminalTrainDto.setCutoffTue(null);
		terminalTrainDto.setCutoffWed(null);
		terminalTrainDto.setCutoffThu(null);
		terminalTrainDto.setCutoffFri(null);
		terminalTrainDto.setCutoffSat(null);
		terminalTrainDto.setCutoffSun(null);
		terminalTrainDto.setTrainDir("Train Directory");
		terminalTrainDto.setMaxFootage(564738);
		
		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
		
		terminalTrain = null;
		terminalTrainDto = null;
		terminalTrainDtoList = null;
		terminalTrainList = null;
		
	}

	
	@Test
	void testGetAllTerminalTrains() {
		when(terminalTrainService.getAllTerminalTrains()).thenReturn(terminalTrainList);
		ResponseEntity<APIResponse<List<TerminalTrainDTO>>> terminalTrainList = terminalTrainController.getAllTerminalTrains();
		assertNotNull(terminalTrainList.getBody());
	}
	
	
	@Test
	void testUpdateTrainDesc() {
		when(terminalTrainMapper.terminalTrainDTOToTerminalTrain(Mockito.any())).thenReturn(terminalTrain);
		when(terminalTrainService.updateTrainDesc(Mockito.any(),Mockito.any())).thenReturn(terminalTrain);
		when(terminalTrainMapper.terminalTrainToTerminalTrainDTO(Mockito.any())).thenReturn(terminalTrainDto);
		ResponseEntity<APIResponse<TerminalTrainDTO>> codeUpdated = terminalTrainController.updateTrainDesc(terminalTrainDto, header);
		assertNotNull(codeUpdated.getBody().getData()); 
	}
	

	@Test
	void testTerminalTrainNoRecordsFoundException() {
		when(terminalTrainService.getAllTerminalTrains()).thenThrow(new NoRecordsFoundException());
		when(terminalTrainService.updateTrainDesc(Mockito.any(),Mockito.any())).thenThrow(new NoRecordsFoundException());
        
        ResponseEntity<APIResponse<List<TerminalTrainDTO>>> terminalTrainList = terminalTrainController.getAllTerminalTrains();
		ResponseEntity<APIResponse<TerminalTrainDTO>> codeUpdated  = terminalTrainController.updateTrainDesc(Mockito.any(),Mockito.any());
		
		assertEquals(terminalTrainList.getStatusCodeValue(),404);
		assertEquals(codeUpdated.getStatusCodeValue(),404);	
	}
	

	@Test
	void testTerminalTrainException() {
		when(terminalTrainService.getAllTerminalTrains()).thenThrow(new RuntimeException());
        when(terminalTrainService.updateTrainDesc(Mockito.any(),Mockito.any())).thenThrow(new RuntimeException());
        
		ResponseEntity<APIResponse<List<TerminalTrainDTO>>> terminalTrainList = terminalTrainController.getAllTerminalTrains();
        ResponseEntity<APIResponse<TerminalTrainDTO>> codeUpdated = terminalTrainController.updateTrainDesc(Mockito.any(),Mockito.any());
        
        assertEquals(terminalTrainList.getStatusCodeValue(),500);
        assertEquals(codeUpdated.getStatusCodeValue(),500);	
	
	}
	
	@Test
	void testTerminalTrainNullPointerexception() {
        when(terminalTrainService.updateTrainDesc(Mockito.any(),Mockito.any())).thenThrow(new NullPointerException());
        ResponseEntity<APIResponse<TerminalTrainDTO>> codeUpdated = terminalTrainController.updateTrainDesc(Mockito.any(),Mockito.any());
        assertEquals(codeUpdated.getStatusCodeValue(),400);
		
	}
	@Test
	void testTerminalTrainSizeExceedException() {
		  when(terminalTrainService.updateTrainDesc(Mockito.any(),Mockito.any())).thenThrow(new SizeExceedException());
		  ResponseEntity<APIResponse<TerminalTrainDTO>> codeUpdated = terminalTrainController.updateTrainDesc(Mockito.any(),Mockito.any());
		  assertEquals(codeUpdated.getStatusCodeValue(),411);		
	}
	
	@Test
	void testDeleteTerminalTrain() {
		when(terminalTrainMapper.terminalTrainDTOToTerminalTrain(Mockito.any())).thenReturn(terminalTrain);
		terminalTrainService.deleteTrain(Mockito.any());
		when(terminalTrainMapper.terminalTrainToTerminalTrainDTO(Mockito.any())).thenReturn(terminalTrainDto); 
		ResponseEntity<APIResponse<TerminalTrainDTO>> deleteList = terminalTrainController.deleteTrain(terminalTrainDto);
		assertEquals(deleteList.getStatusCodeValue(),200);
}
}
