package com.nscorp.obis.services;

import com.nscorp.obis.dto.NotificationTypesResponseDTO;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class NotificationTypesServiceTest {

	@InjectMocks
	NotificationTypesServiceImpl notificationTypesService;
	
	
	NotificationTypesResponseDTO notificationTypesResponseDTO;
	List<NotificationTypesResponseDTO> notificationTypesResponseDTOList;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}
	
	@SuppressWarnings("deprecation")
	@BeforeEach
	void setup() throws Exception{
		MockitoAnnotations.initMocks(this);
		
		notificationTypesResponseDTO = new NotificationTypesResponseDTO();
		notificationTypesResponseDTOList = new ArrayList<>();
		
	}
	
	@AfterEach
	void tearDown() throws Exception{
		
		notificationTypesResponseDTO = null;
		notificationTypesResponseDTOList= null;
	}
	
	@Test
	void testFetchNotificationTypes() throws SQLException{
		List<NotificationTypesResponseDTO> notificationTypesResponseDTO = notificationTypesService.fetchNotificationTypes();
		assertEquals(notificationTypesResponseDTO.get(0).getCode(),notificationTypesResponseDTOList.get(0).getCode());
		assertEquals(notificationTypesResponseDTO.get(0).getDescription(),notificationTypesResponseDTOList.get(0).getDescription());
		assertEquals(notificationTypesResponseDTO.get(0).getInvalidEventCodes(),notificationTypesResponseDTOList.get(0).getInvalidEventCodes());
		
	}
}
