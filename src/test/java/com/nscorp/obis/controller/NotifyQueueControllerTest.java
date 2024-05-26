package com.nscorp.obis.controller;

import com.nscorp.obis.domain.NotifyQueue;
import com.nscorp.obis.domain.NotifyQueueRetry;
import com.nscorp.obis.dto.NotifyQueueDTO;
import com.nscorp.obis.dto.NotifyQueueRetryDTO;
import com.nscorp.obis.dto.NotifyQueueUpdatedDTO;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.NotifyQueueService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

class NotifyQueueControllerTest {

	@Mock
	NotifyQueueService notifyQueueService;

	@InjectMocks
	NotifyQueueController notifyQueueController;

	NotifyQueue notifyQueue;
	NotifyQueueUpdatedDTO notifyQueueUpdatedDTO;
	NotifyQueueRetryDTO notifyQueueRetryDto;
	NotifyQueueRetry notifyQueueRetry;
	List<NotifyQueue> notifyQueueList;
	List<NotifyQueueDTO> notifyQueueDtoList;
	NotifyQueueDTO notifyQueueDTO;
	Map<String, String> headers;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		notifyQueueUpdatedDTO = new NotifyQueueUpdatedDTO();
		notifyQueueRetryDto = new NotifyQueueRetryDTO();
		notifyQueueRetry = new NotifyQueueRetry();
		notifyQueueUpdatedDTO.setFlag(true);
		notifyQueueUpdatedDTO.setTermId(123L);
		notifyQueueUpdatedDTO.setCustomerName("ABC");
		notifyQueue = new NotifyQueue();
		notifyQueueList = new ArrayList<>();
		notifyQueue.setNotifyQueueId(10185038621092L);
		notifyQueue.setRenotifyCnt(0);
		notifyQueue.setNotifyStat("CONF");
		notifyQueueList.add(notifyQueue);

		notifyQueueDTO = new NotifyQueueDTO();
		notifyQueueDtoList = new ArrayList<>();
		notifyQueueDtoList.add(notifyQueueDTO);
		notifyQueueUpdatedDTO.setNotifyQueueObjDto(notifyQueueDtoList);
		notifyQueueDTO.setNtfyQueueId(10185038621092L);
		notifyQueueDTO.setRenotifyCnt(0);
		notifyQueueDTO.setNotifyStat("CONF");

		headers = new HashMap<String, String>();
		headers.put("userid", "Test");
		headers.put("extensionschema", "Test");
	}

	@Test
	void testUpdateNotifyQueue() {
		when(notifyQueueService.updateNotifyQueue(Mockito.any(), Mockito.any())).thenReturn(notifyQueueList);
		ResponseEntity<APIResponse<List<NotifyQueue>>> result = notifyQueueController
				.updateNotifyQueue(notifyQueueUpdatedDTO, headers);
		assertEquals(200, result.getStatusCodeValue());
	}

	@Test
	void testAddNotifyQueue() {
		when(notifyQueueService.addNotifyQueue(Mockito.any(), Mockito.any())).thenReturn(notifyQueue);
		ResponseEntity<APIResponse<NotifyQueueDTO>> result = notifyQueueController.addNotifyQueue(notifyQueueDTO,
				headers);
		assertEquals(200, result.getStatusCodeValue());
	}

	@Test
	void testUpdateNotifyRetry() {
		when(notifyQueueService.updateNotifyQueueRetry(Mockito.any(), Mockito.any())).thenReturn(notifyQueueRetry);
		ResponseEntity<APIResponse<NotifyQueueRetryDTO>> result = notifyQueueController
				.updateNotifyQueue(notifyQueueRetryDto, headers);
		assertEquals(200, result.getStatusCodeValue());
	}

	@Test
	void testNullPointerException() {
		when(notifyQueueService.addNotifyQueue(Mockito.any(), Mockito.any())).thenThrow(new NullPointerException());
		ResponseEntity<APIResponse<NotifyQueueDTO>> result = notifyQueueController.addNotifyQueue(notifyQueueDTO,
				headers);
		assertEquals(result.getStatusCodeValue(), 400);

		when(notifyQueueService.updateNotifyQueue(Mockito.any(), Mockito.any())).thenThrow(new NullPointerException());
		ResponseEntity<APIResponse<List<NotifyQueue>>> update = notifyQueueController
				.updateNotifyQueue(notifyQueueUpdatedDTO, headers);
		assertEquals(update.getStatusCodeValue(), 400);

		when(notifyQueueService.updateNotifyQueueRetry(Mockito.any(), Mockito.any()))
				.thenThrow(new NullPointerException());
		ResponseEntity<APIResponse<NotifyQueueRetryDTO>> result1 = notifyQueueController
				.updateNotifyQueue(notifyQueueRetryDto, headers);
		assertEquals(result1.getStatusCodeValue(), 400);

		when(notifyQueueService.getNotifyQueueRetry(Mockito.any())).thenThrow(new NullPointerException());
		ResponseEntity<APIResponse<NotifyQueueRetryDTO>> result2 = notifyQueueController.getNotifyQueue(100L);
		assertEquals(result2.getStatusCodeValue(), 400);
	}

	@Test
	void testAddNotifyQueueException() {
		when(notifyQueueService.addNotifyQueue(Mockito.any(), Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<NotifyQueueDTO>> result = notifyQueueController.addNotifyQueue(notifyQueueDTO,
				headers);
		assertEquals(result.getStatusCodeValue(), 500);
	}

	@Test
	void testUpdateNotifyQueueNoRecordsFound() {
		when(notifyQueueService.updateNotifyQueue(Mockito.any(), Mockito.any()))
				.thenThrow(new NoRecordsFoundException("Record Not Found!"));
		ResponseEntity<APIResponse<List<NotifyQueue>>> result = notifyQueueController
				.updateNotifyQueue(notifyQueueUpdatedDTO, headers);
		assertEquals(404, result.getStatusCodeValue());

		when(notifyQueueService.updateNotifyQueueRetry(Mockito.any(), Mockito.any()))
				.thenThrow(new NoRecordsFoundException("Record Not Found!"));
		ResponseEntity<APIResponse<NotifyQueueRetryDTO>> result1 = notifyQueueController
				.updateNotifyQueue(notifyQueueRetryDto, headers);
		assertEquals(result1.getStatusCodeValue(), 404);

		when(notifyQueueService.getNotifyQueueRetry(Mockito.any()))
				.thenThrow(new NoRecordsFoundException("Record Not Found!"));
		ResponseEntity<APIResponse<NotifyQueueRetryDTO>> result2 = notifyQueueController.getNotifyQueue(100L);
		assertEquals(result2.getStatusCodeValue(), 404);
	}

	@Test
	void testUpdateNotifyQueueException() {
		when(notifyQueueService.updateNotifyQueue(Mockito.any(), Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<List<NotifyQueue>>> result = notifyQueueController
				.updateNotifyQueue(notifyQueueUpdatedDTO, headers);
		assertEquals(500, result.getStatusCodeValue());

		when(notifyQueueService.updateNotifyQueueRetry(Mockito.any(), Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<NotifyQueueRetryDTO>> result1 = notifyQueueController
				.updateNotifyQueue(notifyQueueRetryDto, headers);
		assertEquals(result1.getStatusCodeValue(), 500);

		when(notifyQueueService.getNotifyQueueRetry(Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<NotifyQueueRetryDTO>> result2 = notifyQueueController.getNotifyQueue(100L);
		assertEquals(result2.getStatusCodeValue(), 500);
	}

	@Test
	void testUpdateNotifyQueueInvalidDataException() {
		when(notifyQueueService.updateNotifyQueueRetry(Mockito.any(), Mockito.any()))
				.thenThrow(new InvalidDataException());
		ResponseEntity<APIResponse<NotifyQueueRetryDTO>> result1 = notifyQueueController
				.updateNotifyQueue(notifyQueueRetryDto, headers);
		assertEquals(result1.getStatusCodeValue(), 406);
	}

	@Test
	void testgetotifyQueueRetry() {
		when(notifyQueueService.getNotifyQueueRetry(Mockito.any())).thenReturn(notifyQueueRetry);
		ResponseEntity<APIResponse<NotifyQueueRetryDTO>> result = notifyQueueController.getNotifyQueue(100L);
		assertEquals(result.getStatusCodeValue(), 200);
	}

}
