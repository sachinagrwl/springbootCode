package com.nscorp.obis.services;


import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.nscorp.obis.domain.TerminalTrain;
import com.nscorp.obis.dto.TerminalTrainDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordNotDeletedException;
import com.nscorp.obis.repository.BlockRepository;
import com.nscorp.obis.repository.TerminalTrainRepository;

public class TerminalTrainServiceTest {
	
	@InjectMocks
	TerminalTrainServiceImpl terminalTrainService;

	@Mock
	TerminalTrainRepository terminalTrainRepository;
	@Mock
	BlockRepository blockRepository;
	
	TerminalTrain terminalTrain;
	TerminalTrain terminalTrainResource;
	TerminalTrainDTO terminalTrainDto;
	List<TerminalTrain> terminalTrainList;
	TerminalTrain addedLoad;
	TerminalTrain loadUpdated;
	TerminalTrain deleteTrain;
	
	Map<String, String> header;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		
		MockitoAnnotations.initMocks(this);
		System.out.println("Repo:- "+ terminalTrainRepository);
		
		terminalTrain= new TerminalTrain();

		terminalTrain.setTermId((long) 12345);
		terminalTrain.setTrainNr("123");
		terminalTrain.setOldTrainNr("122");
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
		
		terminalTrainList = new ArrayList<>();
		
		terminalTrainDto = new TerminalTrainDTO();
		terminalTrainDto.setTermId((long) 12345);
		terminalTrainDto.setTrainNr("123");
		terminalTrainDto.setOldTrainNr("122");
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
		
		terminalTrainResource =new TerminalTrain();
		terminalTrainResource.setTermId((long) 12345);
		terminalTrainResource.setTrainNr("123");
		terminalTrainResource.setOldTrainNr("122");
		terminalTrainResource.setTrainDesc("Description of a specific train");
		terminalTrainResource.setCutoffDefault(null);
		terminalTrainResource.setCutoffMon(null);
		terminalTrainResource.setCutoffTue(null);
		terminalTrainResource.setCutoffWed(null);
		terminalTrainResource.setCutoffThu(null);
		terminalTrainResource.setCutoffFri(null);
		terminalTrainResource.setCutoffSat(null);
		terminalTrainResource.setCutoffSun(null);
		terminalTrainResource.setTrainDir("Train Directory");
		terminalTrainResource.setMaxFootage(564738);
		
		terminalTrainList.add(terminalTrain);
			
		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");
	}
	
	@AfterEach
	void tearDown() throws Exception {
		terminalTrainList = null;
		terminalTrainResource = null;
		terminalTrainDto = null;
		terminalTrain = null;
	}
	
	

	    @Test
	    void testGetAllTerminalTrains() {
	        when(terminalTrainRepository.findAll()).thenReturn(terminalTrainList);
	        List<TerminalTrain> allTerminalTrains= terminalTrainService.getAllTerminalTrains();
	        assertEquals(terminalTrainList,allTerminalTrains);
		}
	@Test
	void testDeleteTerminalTrains() {
		when(terminalTrainRepository.existsByTermIdAndTrainNr(Mockito.any(),Mockito.any())).thenReturn(true);
		when(blockRepository.existsByTermIdAndTrainNr(Mockito.any(),Mockito.any())).thenReturn(true);
		terminalTrainService.deleteTrain(terminalTrain);
	}
	@Test
	void testDeleteTerminalTrainsNotDeletedException() {
		when(terminalTrainRepository.existsByTermIdAndTrainNr(Mockito.any(),Mockito.any())).thenReturn(false);
		RecordNotDeletedException exception = assertThrows(RecordNotDeletedException.class,
				() -> terminalTrainService.deleteTrain(terminalTrain));
		Assertions.assertNotNull(exception.getMessage());
	}
	   
	   @Test
		void testUpdateTrainDesc() {
		   when(terminalTrainRepository.existsByTermIdAndTrainNr(Mockito.any(),Mockito.any())).thenReturn(true);
		   when(terminalTrainRepository.findByTermIdAndTrainNr(Mockito.any(),Mockito.any())).thenReturn(terminalTrainResource);
		   when(terminalTrainRepository.save(Mockito.any())).thenReturn(terminalTrainResource);
			loadUpdated = terminalTrainService.updateTrainDesc(terminalTrain, header);
			assertEquals(loadUpdated.getTermId(), terminalTrain.getTermId());
	   }
		@Test
		void testUpdateTrainDescException() {
			when(terminalTrainRepository.existsByTermIdAndTrainNr(Mockito.any(),Mockito.any())).thenReturn(false);
			NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
					() -> when(terminalTrainService.updateTrainDesc(terminalTrain, header)));
			Assertions.assertNotNull(exception.getMessage());
		}
	}

	


	


	
	
	
	
	
	
	
	

