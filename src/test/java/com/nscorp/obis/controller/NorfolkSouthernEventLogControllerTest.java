package com.nscorp.obis.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.nscorp.obis.domain.NorfolkSouthernEventLog;
import com.nscorp.obis.dto.NorfolkSouthernEventLogDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.NorfolkSouthernEventLogService;

class NorfolkSouthernEventLogControllerTest {

	@Mock
	NorfolkSouthernEventLogService eventLogService;
	@InjectMocks
	NorfolkSouthernEventLogController eventLogController;

	NorfolkSouthernEventLog eventLog;
	NorfolkSouthernEventLogDTO eventLogDTO;
	List<NorfolkSouthernEventLog> eventLogList;
	Long NOTIFY_QUEUE_ID = 1000L;

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		eventLog = new NorfolkSouthernEventLog();
		eventLogDTO = new NorfolkSouthernEventLogDTO();
		eventLogList = new ArrayList<>();

		eventLogList.add(eventLog);
	}

	@AfterEach
	void tearDown() throws Exception {
		eventLog = null;
		eventLogDTO = null;
		eventLogList = null;
	}

	@Test
	void testGetNSEventLog() {
		when(eventLogService.getNorfolkSouthernEventLog(Mockito.any())).thenReturn(eventLogList);
		ResponseEntity<APIResponse<List<NorfolkSouthernEventLogDTO>>> getResponse = eventLogController
				.getNorfolkSouthernEventLog(NOTIFY_QUEUE_ID);
		assertEquals(getResponse.getStatusCodeValue(), 200);
	}

	@Test
	void testNoRecordsFoundException() {
		when(eventLogService.getNorfolkSouthernEventLog(Mockito.any())).thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<List<NorfolkSouthernEventLogDTO>>> getResponse = eventLogController
				.getNorfolkSouthernEventLog(NOTIFY_QUEUE_ID);
		assertEquals(getResponse.getStatusCodeValue(), 404);
	}

	@Test
	void testException() {
		when(eventLogService.getNorfolkSouthernEventLog(Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<List<NorfolkSouthernEventLogDTO>>> getResponse = eventLogController
				.getNorfolkSouthernEventLog(NOTIFY_QUEUE_ID);
		assertEquals(getResponse.getStatusCodeValue(), 500);
	}
}
