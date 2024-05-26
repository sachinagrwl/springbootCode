package com.nscorp.obis.controller;


import com.nscorp.obis.dto.NotificationTypesResponseDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.NotificationTypesService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;



public class NotificationTypesControllerTest {
	
	@Mock
	NotificationTypesService notificationTypesService;

	@InjectMocks
	NotificationTypesController notificationTypesController;
	
	
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
	void testGetNotificationTypes() throws SQLException{
		when(notificationTypesService.fetchNotificationTypes()).thenReturn(notificationTypesResponseDTOList);
		ResponseEntity<APIResponse<List<NotificationTypesResponseDTO>>> notificationTypesResponseDTO = notificationTypesController.getNotificationTypes();
		assertEquals(notificationTypesResponseDTO.getBody().getData(),notificationTypesResponseDTOList);
		assertEquals(notificationTypesResponseDTO.getStatusCodeValue(),200);		
	}
	@Test
	void testNoRecordsFoundException() throws SQLException {
		when(notificationTypesService.fetchNotificationTypes()).thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<List<NotificationTypesResponseDTO>>> notificationTypesResponseDTO = notificationTypesController.getNotificationTypes();
		assertEquals(notificationTypesResponseDTO.getStatusCodeValue(),404);
	}
}
