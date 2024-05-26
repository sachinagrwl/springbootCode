package com.nscorp.obis.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.nscorp.obis.domain.NorfolkSouthernEventLog;
import com.nscorp.obis.domain.VoiceNotify;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.repository.NorfolkSouthernEventLogRepository;
import com.nscorp.obis.repository.VoiceNotifyRepository;

class NorfolkSouthernEventLogServiceTest {

	@Mock
	VoiceNotifyRepository voiceNotifyRepo;
	@Mock
	NorfolkSouthernEventLogRepository eventLogRepository;
	@InjectMocks
	NorfolkSouthernEventLogServiceImpl eventLogServiceImpl;

	NorfolkSouthernEventLog eventLog;
	List<NorfolkSouthernEventLog> eventLogList;
	Long NOTIFY_QUEUE_ID = 1000L;

	VoiceNotify voiceNotify;

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		eventLog = new NorfolkSouthernEventLog();
		eventLogList = new ArrayList<>();
		eventLogList.add(eventLog);

		voiceNotify = new VoiceNotify();
		voiceNotify.setSvcId(NOTIFY_QUEUE_ID);
	}

	@AfterEach
	void tearDown() throws Exception {
		eventLog = null;
		eventLogList = null;
	}

	@Test
	void testGetNSEventLog() {
		when(voiceNotifyRepo.findByNotifyQueueId(NOTIFY_QUEUE_ID)).thenReturn(voiceNotify);
		when(eventLogRepository.existsByServiceId(NOTIFY_QUEUE_ID)).thenReturn(true);
		when(eventLogRepository.findByServiceId(NOTIFY_QUEUE_ID)).thenReturn(eventLogList);
		List<NorfolkSouthernEventLog> norfolkSouthernEventLog = eventLogServiceImpl
				.getNorfolkSouthernEventLog(NOTIFY_QUEUE_ID);
		assertNotNull(norfolkSouthernEventLog);
	}

	@Test
	void testNoRecordsFoundException() {
		when(voiceNotifyRepo.findByNotifyQueueId(NOTIFY_QUEUE_ID)).thenReturn(null);
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(eventLogServiceImpl.getNorfolkSouthernEventLog(NOTIFY_QUEUE_ID)));
		Assertions.assertEquals("No Records Found in VoiceNotify with notifyQueueId:" + NOTIFY_QUEUE_ID,
				exception.getMessage());
		
		when(voiceNotifyRepo.findByNotifyQueueId(NOTIFY_QUEUE_ID)).thenReturn(voiceNotify);
		when(eventLogRepository.existsByServiceId(NOTIFY_QUEUE_ID)).thenReturn(false);
		NoRecordsFoundException exception1 = assertThrows(NoRecordsFoundException.class,
				() -> when(eventLogServiceImpl.getNorfolkSouthernEventLog(NOTIFY_QUEUE_ID)));
		Assertions.assertEquals("No Records Found in NorfolkSouthernEventLog with SVC ID:"
				+ NOTIFY_QUEUE_ID + " of provided notifyQueueId:" + NOTIFY_QUEUE_ID,
				exception1.getMessage());
		
		when(voiceNotifyRepo.findByNotifyQueueId(NOTIFY_QUEUE_ID)).thenReturn(voiceNotify);
		voiceNotify.setSvcId(null);
		NoRecordsFoundException exception2 = assertThrows(NoRecordsFoundException.class,
				() -> when(eventLogServiceImpl.getNorfolkSouthernEventLog(NOTIFY_QUEUE_ID)));
		Assertions.assertEquals("No Records Found in NorfolkSouthernEventLog with SVC ID:"
				+ null + " of provided notifyQueueId:" + NOTIFY_QUEUE_ID,
				exception2.getMessage());
	}

}
