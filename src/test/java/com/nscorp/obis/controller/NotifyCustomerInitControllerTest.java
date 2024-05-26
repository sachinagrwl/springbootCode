package com.nscorp.obis.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import com.nscorp.obis.domain.NotifyCustomerInit;
import com.nscorp.obis.dto.NotifyCustomerInitDTO;
import com.nscorp.obis.dto.mapper.NotifyCustomerInitMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.NotifyCustomerInitService;

public class NotifyCustomerInitControllerTest {

	@Mock
	NotifyCustomerInitService notifyCustomerInitService;

	@Mock
	NotifyCustomerInitMapper notifyCustomerInitMapper;

	@InjectMocks
	NotifyCustomerInitController notifyCustomerInitController;

	NotifyCustomerInitDTO notifyCustomerInitDto;
	NotifyCustomerInit notifyCustomerInit;
	List<NotifyCustomerInit> notifyCustomerInitList;
	List<NotifyCustomerInitDTO> notifyCustomerInitDtoList;

	Map<String, String> header;
	Long custId;

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {

		MockitoAnnotations.initMocks(this);
		notifyCustomerInit = new NotifyCustomerInit();
		notifyCustomerInit.setEqInit("Test");
		notifyCustomerInit.setCustId(100L);
		notifyCustomerInitList = new ArrayList<>();
		notifyCustomerInitList.add(notifyCustomerInit);

		notifyCustomerInitDto = new NotifyCustomerInitDTO();
		notifyCustomerInitDto.setEqInit("Test");
		notifyCustomerInitDto.setCustId(100L);

		notifyCustomerInitDtoList = new ArrayList<>();
		notifyCustomerInitDtoList.add(notifyCustomerInitDto);

		custId = 100L;

		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");
	}

	@AfterEach
	void tearDown() throws Exception {
		notifyCustomerInitDto = null;
		notifyCustomerInit = null;
		notifyCustomerInitList = null;
		notifyCustomerInitDtoList = null;
	}

	@Test
	void testGetAllCustomerInitials() {
		when(notifyCustomerInitService.getCustomerInitials(Mockito.any())).thenReturn(notifyCustomerInitList);
		ResponseEntity<APIResponse<List<NotifyCustomerInitDTO>>> custInitialsList = notifyCustomerInitController
				.getAllCustomerInitials(custId);
		assertNotNull(custInitialsList.getBody());
	}

	@Test
	void testAddCustomerInitials() {
		when(notifyCustomerInitService.addNotifyCustomerInit(Mockito.any(), Mockito.any()))
				.thenReturn(notifyCustomerInit);
		ResponseEntity<APIResponse<NotifyCustomerInitDTO>> responseObj = notifyCustomerInitController
				.addNotifyCustomerInit(notifyCustomerInitDto, header);
		assertNotNull(responseObj.getBody());
	}

	@Test
	void testDeleteCustomerInitials() {
		when(notifyCustomerInitService.deleteNotifyCustomerInit(Mockito.any())).thenReturn(notifyCustomerInit);
		ResponseEntity<APIResponse<NotifyCustomerInitDTO>> responseObj = notifyCustomerInitController
				.deleteNotifyCustomerInit(notifyCustomerInitDto);
		assertNotNull(responseObj.getBody());
	}

	@Test
	void testUpdateCustomerInitials() {
		when(notifyCustomerInitService.updateNotifyCustomerInit(Mockito.any(), Mockito.any()))
				.thenReturn(notifyCustomerInitList);
		ResponseEntity<APIResponse<List<NotifyCustomerInitDTO>>> responseObj = notifyCustomerInitController
				.updateNotifyCustomerInit(notifyCustomerInitDto, header);
		assertNotNull(responseObj.getBody());
	}

	@Test
	void testCustomerInitialsNoRecordsFoundException() {
		when(notifyCustomerInitService.getCustomerInitials(Mockito.any())).thenThrow(new NoRecordsFoundException());

		ResponseEntity<APIResponse<List<NotifyCustomerInitDTO>>> custInitialslist = notifyCustomerInitController
				.getAllCustomerInitials(custId);

		assertEquals(custInitialslist.getStatusCodeValue(), 404);

		when(notifyCustomerInitService.updateNotifyCustomerInit(Mockito.any(), Mockito.any()))
				.thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<List<NotifyCustomerInitDTO>>> responseObjUpdate = notifyCustomerInitController
				.updateNotifyCustomerInit(notifyCustomerInitDto, header);
		assertEquals(responseObjUpdate.getStatusCodeValue(), 404);

		when(notifyCustomerInitService.deleteNotifyCustomerInit(Mockito.any()))
				.thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<NotifyCustomerInitDTO>> responseObjDelete = notifyCustomerInitController
				.deleteNotifyCustomerInit(notifyCustomerInitDto);
		assertEquals(responseObjDelete.getStatusCodeValue(), 404);
	}

	@Test
	void testCustomerInitialsRecordNotAddedException() {

		when(notifyCustomerInitService.updateNotifyCustomerInit(Mockito.any(), Mockito.any()))
				.thenThrow(new RecordNotAddedException());
		ResponseEntity<APIResponse<List<NotifyCustomerInitDTO>>> responseObjUpdate = notifyCustomerInitController
				.updateNotifyCustomerInit(notifyCustomerInitDto, header);
		assertEquals(responseObjUpdate.getStatusCodeValue(), 406);

		when(notifyCustomerInitService.addNotifyCustomerInit(Mockito.any(), Mockito.any()))
				.thenThrow(new RecordNotAddedException());
		ResponseEntity<APIResponse<NotifyCustomerInitDTO>> responseObjAdd = notifyCustomerInitController
				.addNotifyCustomerInit(notifyCustomerInitDto, header);
		assertEquals(responseObjAdd.getStatusCodeValue(), 406);
	}

	@Test
	void testCustomerInitialsNullPointerException() {
		when(notifyCustomerInitService.updateNotifyCustomerInit(Mockito.any(), Mockito.any()))
				.thenThrow(new NullPointerException());
		ResponseEntity<APIResponse<List<NotifyCustomerInitDTO>>> responseObjUpdate = notifyCustomerInitController
				.updateNotifyCustomerInit(notifyCustomerInitDto, header);
		assertEquals(responseObjUpdate.getStatusCodeValue(), 400);

		when(notifyCustomerInitService.addNotifyCustomerInit(Mockito.any(), Mockito.any()))
				.thenThrow(new NullPointerException());
		ResponseEntity<APIResponse<NotifyCustomerInitDTO>> responseObjAdd = notifyCustomerInitController
				.addNotifyCustomerInit(notifyCustomerInitDto, header);
		assertEquals(responseObjAdd.getStatusCodeValue(), 400);

		when(notifyCustomerInitService.deleteNotifyCustomerInit(Mockito.any()))
				.thenThrow(new NullPointerException());
		ResponseEntity<APIResponse<NotifyCustomerInitDTO>> responseObjDel = notifyCustomerInitController
				.deleteNotifyCustomerInit(notifyCustomerInitDto);
		assertEquals(responseObjDel.getStatusCodeValue(), 400);
	}

	@Test
	void testCustomerInitialsException() {
		when(notifyCustomerInitService.getCustomerInitials(Mockito.any())).thenThrow(new RuntimeException());

		ResponseEntity<APIResponse<List<NotifyCustomerInitDTO>>> custInitialslist = notifyCustomerInitController
				.getAllCustomerInitials(custId);

		assertEquals(custInitialslist.getStatusCodeValue(), 500);

		when(notifyCustomerInitService.updateNotifyCustomerInit(Mockito.any(), Mockito.any()))
				.thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<List<NotifyCustomerInitDTO>>> responseObjUpdate = notifyCustomerInitController
				.updateNotifyCustomerInit(notifyCustomerInitDto, header);
		assertEquals(responseObjUpdate.getStatusCodeValue(), 500);

		when(notifyCustomerInitService.deleteNotifyCustomerInit(Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<NotifyCustomerInitDTO>> responseObjDelete = notifyCustomerInitController
				.deleteNotifyCustomerInit(notifyCustomerInitDto);
		assertEquals(responseObjDelete.getStatusCodeValue(), 500);

		when(notifyCustomerInitService.addNotifyCustomerInit(Mockito.any(), Mockito.any()))
				.thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<NotifyCustomerInitDTO>> responseObjAdd = notifyCustomerInitController
				.addNotifyCustomerInit(notifyCustomerInitDto, header);
		assertEquals(responseObjAdd.getStatusCodeValue(), 500);
	}
}
