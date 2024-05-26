package com.nscorp.obis.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
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

import com.nscorp.obis.domain.NotifyCustomerInit;
import com.nscorp.obis.domain.NotifyCustomerInitView;
import com.nscorp.obis.dto.NotifyCustomerInitViewDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.repository.NotifyCustomerInitRepository;
import com.nscorp.obis.repository.NotifyCustomerInitViewRepository;

class NotifyCustomerInitViewServiceTest {
	
	@InjectMocks
	NotifyCustomerInitViewServiceImpl notifyCustomerInitViewService;

	@Mock
	NotifyCustomerInitViewRepository notifyCustomerInitViewRepository;
	
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
		when(notifyCustomerInitViewRepository.findAllByOrderByCustomerNameAsc()).thenReturn(notifyCustomerInitViewList);
		List<NotifyCustomerInitView> custInitials = notifyCustomerInitViewService.getAllCustomerInitialsView();
		assertEquals(custInitials, notifyCustomerInitViewList);
	}
	
	@Test
	void testGetAllCustomerInitialsViewExceptions() {
		when(notifyCustomerInitViewRepository.findAllByOrderByCustomerNameAsc())
				.thenReturn(Collections.emptyList());
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(notifyCustomerInitViewService.getAllCustomerInitialsView()));
		assertEquals("No Records are found!", exception.getMessage());
	}

}
