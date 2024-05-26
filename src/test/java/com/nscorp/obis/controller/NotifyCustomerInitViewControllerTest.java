package com.nscorp.obis.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.nscorp.obis.domain.NotifyCustomerInitView;
import com.nscorp.obis.dto.NotifyCustomerInitViewDTO;
import com.nscorp.obis.dto.mapper.NotifyCustomerInitViewMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.NotifyCustomerInitViewService;

class NotifyCustomerInitViewControllerTest {
	
	@Mock
	NotifyCustomerInitViewService notifyCustomerInitViewService;

	@Mock
	NotifyCustomerInitViewMapper notifyCustomerInitViewMapper;

	@InjectMocks
	NotifyCustomerInitViewController notifyCustomerInitViewController;

	NotifyCustomerInitViewDTO notifyCustomerInitViewDto;
	NotifyCustomerInitView notifyCustomerInitView;
	List<NotifyCustomerInitView> notifyCustomerInitViewList;
	List<NotifyCustomerInitViewDTO> notifyCustomerInitViewDtoList;

	Timestamp ts = Timestamp.valueOf(String.format("%04d-%02d-%02d 00:00:00", 2022, 07, 01));
	Map<String, String> header;

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		notifyCustomerInitView = new NotifyCustomerInitView();
		notifyCustomerInitView.setCustomerId(100L);;
		notifyCustomerInitView.setEquipmentInit("TEST");;
		notifyCustomerInitView.setCustomerNo("TEST");
		notifyCustomerInitView.setCustomerName("TEST");
		notifyCustomerInitView.setUpdateDateTime(ts);
		notifyCustomerInitViewList = new ArrayList<>();
		notifyCustomerInitViewList.add(notifyCustomerInitView);

		notifyCustomerInitViewDto = new NotifyCustomerInitViewDTO();
		notifyCustomerInitViewDto.setCustomerId(100L);;
		notifyCustomerInitViewDto.setEquipmentInit("TEST");;
		notifyCustomerInitViewDto.setCustomerNo("TEST");
		notifyCustomerInitViewDto.setCustomerName("TEST");
		notifyCustomerInitViewDto.setUpdateDateTime(ts);

		notifyCustomerInitViewDtoList = new ArrayList<>();
		notifyCustomerInitViewDtoList.add(notifyCustomerInitViewDto);

		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");
	}

	@AfterEach
	void tearDown() throws Exception {
		notifyCustomerInitViewDto = null;
		notifyCustomerInitView = null;
		notifyCustomerInitViewList = null;
		notifyCustomerInitViewDtoList = null;
	}

	@Test
	void testGetAllCustomerInitialsView() {
		when(notifyCustomerInitViewService.getAllCustomerInitialsView()).thenReturn(notifyCustomerInitViewList);
		ResponseEntity<APIResponse<List<NotifyCustomerInitViewDTO>>> custInitialsList = notifyCustomerInitViewController
				.getAllCustomerInitialsView();
		assertNotNull(custInitialsList.getBody());
	}
	
	@Test
	void testGetAllCustomerInitialsViewException() {
		when(notifyCustomerInitViewService.getAllCustomerInitialsView()).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<List<NotifyCustomerInitViewDTO>>> custInitialsList = notifyCustomerInitViewController
				.getAllCustomerInitialsView();
		assertEquals(custInitialsList.getStatusCodeValue(), 500);
	}
	
	@Test
	void testGetAllCustomerNoRecordsFoundException() {
		when(notifyCustomerInitViewService.getAllCustomerInitialsView()).thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<List<NotifyCustomerInitViewDTO>>> custInitialsList = notifyCustomerInitViewController
				.getAllCustomerInitialsView();
		assertEquals(custInitialsList.getStatusCodeValue(), 404);
	}

}
