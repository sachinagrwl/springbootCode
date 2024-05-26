package com.nscorp.obis.controller;

import static org.junit.Assert.assertEquals;
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

import com.nscorp.obis.dto.TrainDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.TrainService;

public class TrainControllerTest {

	@Mock
	TrainService trainService;

	@InjectMocks
	TrainController trainController;

	TrainDTO trainDTO;

	List<TrainDTO> trainDTOs;

	Map<String, String> headers;

	String trainNumber = "123";
	String trainDesc = "test";

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		trainDTO = new TrainDTO();
		trainDTO.setTrainNumber(trainNumber);
		trainDTOs = new ArrayList<>();
		trainDTOs.add(trainDTO);
		headers = new HashMap<>();
		headers.put("userid", "testApi");
	}

	@AfterEach
	void tearDown() {
		trainDTO = null;
		trainDTOs = null;
		headers = null;
	}

	@Test
	void testFetchTrains() {
		when(trainService.fetchTrainDetails(Mockito.anyString(), Mockito.any())).thenReturn(trainDTOs);
		ResponseEntity<APIResponse<List<TrainDTO>>> response = trainController.fetchTrains(trainNumber, trainDesc);
		assertEquals(200, response.getStatusCodeValue());
	}

	@Test
	void testAddTrains() {
		when(trainService.addTrain(Mockito.any(), Mockito.anyMap())).thenReturn(trainDTO);
		ResponseEntity<APIResponse<TrainDTO>> response = trainController.addTrain(trainDTO, headers);
		assertEquals(200, response.getStatusCodeValue());
	}

	@Test
	void testUpdateTrains() {
		when(trainService.updateTrain(Mockito.any(), Mockito.anyMap())).thenReturn(trainDTO);
		ResponseEntity<APIResponse<TrainDTO>> response = trainController.updateTrain(trainDTO, headers);
		assertEquals(200, response.getStatusCodeValue());
	}

	@Test
	void testDeleteTrains() {
		when(trainService.deleteTrain(Mockito.any())).thenReturn(trainDTO);
		ResponseEntity<APIResponse<TrainDTO>> response = trainController.deleteTrain(trainDTO);
		assertEquals(200, response.getStatusCodeValue());
	}

	@Test
	void testNoRecordsFoundException() {
		when(trainService.fetchTrainDetails(Mockito.anyString(), Mockito.any()))
				.thenThrow(NoRecordsFoundException.class);
		when(trainService.addTrain(Mockito.any(), Mockito.anyMap())).thenThrow(NoRecordsFoundException.class);
		when(trainService.updateTrain(Mockito.any(), Mockito.anyMap())).thenThrow(NoRecordsFoundException.class);
		when(trainService.deleteTrain(Mockito.any())).thenThrow(NoRecordsFoundException.class);
		ResponseEntity<APIResponse<List<TrainDTO>>> response = trainController.fetchTrains(trainNumber, trainDesc);
		ResponseEntity<APIResponse<TrainDTO>> response2 = trainController.addTrain(trainDTO, headers);
		ResponseEntity<APIResponse<TrainDTO>> response3 = trainController.updateTrain(trainDTO, headers);
		ResponseEntity<APIResponse<TrainDTO>> response4 = trainController.deleteTrain(trainDTO);
		assertEquals(404, response.getStatusCodeValue());
		assertEquals(404, response2.getStatusCodeValue());
		assertEquals(404, response3.getStatusCodeValue());
		assertEquals(404, response4.getStatusCodeValue());
	}

	@Test
	void testException() {
		when(trainService.fetchTrainDetails(Mockito.anyString(), Mockito.any())).thenThrow(RuntimeException.class);
		when(trainService.addTrain(Mockito.any(), Mockito.anyMap())).thenThrow(RuntimeException.class);
		when(trainService.updateTrain(Mockito.any(), Mockito.anyMap())).thenThrow(RuntimeException.class);
		when(trainService.deleteTrain(Mockito.any())).thenThrow(RuntimeException.class);
		ResponseEntity<APIResponse<List<TrainDTO>>> response = trainController.fetchTrains(trainNumber, trainDesc);
		ResponseEntity<APIResponse<TrainDTO>> response2 = trainController.addTrain(trainDTO, headers);
		ResponseEntity<APIResponse<TrainDTO>> response3 = trainController.updateTrain(trainDTO, headers);
		ResponseEntity<APIResponse<TrainDTO>> response4 = trainController.deleteTrain(trainDTO);
		assertEquals(500, response.getStatusCodeValue());
		assertEquals(500, response2.getStatusCodeValue());
		assertEquals(500, response3.getStatusCodeValue());
		assertEquals(500, response4.getStatusCodeValue());
	}

}
