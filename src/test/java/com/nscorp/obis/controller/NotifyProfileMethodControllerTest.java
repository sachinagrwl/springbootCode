package com.nscorp.obis.controller;

import com.nscorp.obis.domain.NotifyProfileMethod;
import com.nscorp.obis.dto.NotifyProfileMethodDTO;
import com.nscorp.obis.dto.mapper.NotifyProfileMethodMapper;
import com.nscorp.obis.exception.*;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.NotifyProfileMethodService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

//@SpringBootTest
public class NotifyProfileMethodControllerTest {

	@Mock
	NotifyProfileMethodService notifyProfileMethodService;

	@Mock
	NotifyProfileMethodMapper notifyProfileMethodMapper;

	@InjectMocks
	NotifyProfileMethodController notifyProfileMethodController;

	NotifyProfileMethod notifyProfileMethod;
	List<NotifyProfileMethod> notifyProfileMethodList;
	List<NotifyProfileMethodDTO> notifyProfileMethodDTOList;
	NotifyProfileMethodDTO notifyProfileMethodDTO;
	List<ResponseObject> responseObjectList = new ArrayList<ResponseObject>();

	String notificationName;
	String notificationMethod;
	String notificationType;
	String editBox;
	Integer notifyAreaCode;
	Integer notifyPrefix;
	Integer notifySuffix;
	Integer notifyPhoneExtension;
	java.lang.String notificationOrder;
	Character microwaveInd;
	Integer pageNo;
	Integer pageSize;
	Map<String, String> header;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {

	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		notifyProfileMethod = new NotifyProfileMethod();
		notifyProfileMethodDTO = new NotifyProfileMethodDTO();
		notifyProfileMethodList = new ArrayList<>();
		notifyProfileMethodDTOList = new ArrayList<>();
		notifyProfileMethod.setNotifyMethodId(6788L);
		notifyProfileMethod.setNotificationName("TEST");
		notifyProfileMethodList.add(notifyProfileMethod);
		notifyProfileMethodDTOList.add(notifyProfileMethodDTO);
		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");
	}

	@AfterEach
	void tearDown() throws Exception {
		notifyProfileMethod = null;
		notifyProfileMethodDTO = null;
		notifyProfileMethodList = null;
		notifyProfileMethodDTOList = null;
	}

	@Test
	void testGetNotifyProfileMethods() throws SQLException {
		when(notifyProfileMethodService.fetchNotifyProfileMethod(Mockito.any(), Mockito.any(), Mockito.any(),
				Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),
				Mockito.any(), Mockito.any())).thenReturn(notifyProfileMethodList);
		ResponseEntity<APIResponse<List<NotifyProfileMethodDTO>>> customerProfiles = notifyProfileMethodController
				.getNotifyProfileMethod(notificationName, notificationMethod, notificationType, editBox, notifyAreaCode,
						notifyPrefix, notifySuffix, notifyPhoneExtension, notificationOrder, microwaveInd, pageNo,
						pageSize);
		assertEquals(customerProfiles.getStatusCodeValue(), 200);
	}

	@Test
	void testAddNotifyProfileMethod() throws SQLException {
		when(notifyProfileMethodMapper.notifyProfileMethodToNotifyProfileMethodDTO(Mockito.any()))
				.thenReturn(notifyProfileMethodDTO);
		when(notifyProfileMethodMapper.notifyProfileMethodDTOToNotifyProfileMethod(Mockito.any()))
				.thenReturn(notifyProfileMethod);
		when(notifyProfileMethodService.insertNotifyProfileMethod(Mockito.any(), Mockito.any(), Mockito.any()))
				.thenReturn(notifyProfileMethod);
		ResponseEntity<APIResponse<NotifyProfileMethodDTO>> addedNotifyProfileMethod = notifyProfileMethodController
				.addNotifyProfileMethod(notifyProfileMethodDTO, header);
		assertEquals(addedNotifyProfileMethod.getStatusCodeValue(), 200);
	}

	@Test
	void testUpdateNotifyProfileMethod() throws SQLException {

		when(notifyProfileMethodService.updateNotifyProfileMethod(Mockito.any(), Mockito.any()))
				.thenReturn(notifyProfileMethod);
		ResponseEntity<APIResponse<NotifyProfileMethodDTO>> codeUpdated = notifyProfileMethodController
				.updateNotifyProfileMethod(notifyProfileMethodDTO, header);
		assertEquals(codeUpdated.getStatusCodeValue(), 200);
	}

	@Test
	void testNoRecordsFoundException() throws SQLException {
		when(notifyProfileMethodMapper.notifyProfileMethodToNotifyProfileMethodDTO(Mockito.any()))
				.thenReturn(notifyProfileMethodDTO);
		when(notifyProfileMethodMapper.notifyProfileMethodDTOToNotifyProfileMethod(Mockito.any()))
				.thenReturn(notifyProfileMethod);
		when(notifyProfileMethodService.fetchNotifyProfileMethod(Mockito.any(), Mockito.any(), Mockito.any(),
				Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),
				Mockito.any(), Mockito.any())).thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<List<NotifyProfileMethodDTO>>> response = notifyProfileMethodController
				.getNotifyProfileMethod(notificationName, notificationMethod, notificationType, editBox, notifyAreaCode,
						notifyPrefix, notifySuffix, notifyPhoneExtension, notificationOrder, microwaveInd, pageNo,
						pageSize);
		assertEquals(response.getStatusCodeValue(), 404);
		when(notifyProfileMethodService.insertNotifyProfileMethod(Mockito.any(), Mockito.any(), Mockito.any()))
				.thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<NotifyProfileMethodDTO>> response1 = notifyProfileMethodController
				.addNotifyProfileMethod(notifyProfileMethodDTO, header);
		assertEquals(response1.getStatusCodeValue(), 404);
		when(notifyProfileMethodService.updateNotifyProfileMethod(Mockito.any(), Mockito.any()))
				.thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<NotifyProfileMethodDTO>> response2 = notifyProfileMethodController
				.updateNotifyProfileMethod(notifyProfileMethodDTO, header);
		assertEquals(response2.getStatusCodeValue(), 404);
	}

	@Test
	@DisplayName("NullPointerException")
	void testNullPointerException() throws SQLException {
		when(notifyProfileMethodService.updateNotifyProfileMethod(Mockito.any(), Mockito.any()))
				.thenThrow(new NullPointerException());
		ResponseEntity<APIResponse<NotifyProfileMethodDTO>> response = notifyProfileMethodController
				.updateNotifyProfileMethod(notifyProfileMethodDTO, header);
		assertEquals(response.getStatusCodeValue(), 400);
	}

	@Test
	@DisplayName("Internal Server Error")
	void testException() throws SQLException {
		when(notifyProfileMethodMapper.notifyProfileMethodToNotifyProfileMethodDTO(Mockito.any()))
				.thenReturn(notifyProfileMethodDTO);
		when(notifyProfileMethodMapper.notifyProfileMethodDTOToNotifyProfileMethod(Mockito.any()))
				.thenReturn(notifyProfileMethod);
		when(notifyProfileMethodService.fetchNotifyProfileMethod(Mockito.any(), Mockito.any(), Mockito.any(),
				Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),
				Mockito.any(), Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<List<NotifyProfileMethodDTO>>> response = notifyProfileMethodController
				.getNotifyProfileMethod(notificationName, notificationMethod, notificationType, editBox, notifyAreaCode,
						notifyPrefix, notifySuffix, notifyPhoneExtension, notificationOrder, microwaveInd, pageNo,
						pageSize);
		assertEquals(response.getStatusCodeValue(), 500);
		when(notifyProfileMethodService.insertNotifyProfileMethod(Mockito.any(), Mockito.any(), Mockito.any()))
				.thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<NotifyProfileMethodDTO>> response1 = notifyProfileMethodController
				.addNotifyProfileMethod(notifyProfileMethodDTO, header);
		assertEquals(response1.getStatusCodeValue(), 500);
		when(notifyProfileMethodService.updateNotifyProfileMethod(Mockito.any(), Mockito.any()))
				.thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<NotifyProfileMethodDTO>> response2 = notifyProfileMethodController
				.updateNotifyProfileMethod(notifyProfileMethodDTO, header);
		assertEquals(response2.getStatusCodeValue(), 500);
	}

	@Test
	void testRecordAlreadyExistsException() throws SQLException {
		when(notifyProfileMethodMapper.notifyProfileMethodToNotifyProfileMethodDTO(Mockito.any()))
				.thenReturn(notifyProfileMethodDTO);
		when(notifyProfileMethodMapper.notifyProfileMethodDTOToNotifyProfileMethod(Mockito.any()))
				.thenReturn(notifyProfileMethod);
		when(notifyProfileMethodService.insertNotifyProfileMethod(Mockito.any(), Mockito.any(), Mockito.any()))
				.thenThrow(new RecordAlreadyExistsException());
		ResponseEntity<APIResponse<NotifyProfileMethodDTO>> response = notifyProfileMethodController
				.addNotifyProfileMethod(notifyProfileMethodDTO, header);
		assertEquals(response.getStatusCodeValue(), 208);
		when(notifyProfileMethodService.fetchNotifyProfileMethod(Mockito.any(), Mockito.any(), Mockito.any(),
				Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),
				Mockito.any(), Mockito.any())).thenThrow(new RecordAlreadyExistsException());
		ResponseEntity<APIResponse<List<NotifyProfileMethodDTO>>> response2 = notifyProfileMethodController
				.getNotifyProfileMethod(notificationName, notificationMethod, notificationType, editBox, notifyAreaCode,
						notifyPrefix, notifySuffix, notifyPhoneExtension, notificationOrder, microwaveInd, pageNo,
						pageSize);
		assertEquals(response2.getStatusCodeValue(), 208);
		
	}

	@Test
	void testSizeExceedException() throws SQLException {
		when(notifyProfileMethodMapper.notifyProfileMethodToNotifyProfileMethodDTO(Mockito.any()))
				.thenReturn(notifyProfileMethodDTO);
		when(notifyProfileMethodMapper.notifyProfileMethodDTOToNotifyProfileMethod(Mockito.any()))
				.thenReturn(notifyProfileMethod);
		when(notifyProfileMethodService.insertNotifyProfileMethod(Mockito.any(), Mockito.any(), Mockito.any()))
				.thenThrow(new SizeExceedException());
		ResponseEntity<APIResponse<NotifyProfileMethodDTO>> response = notifyProfileMethodController
				.addNotifyProfileMethod(notifyProfileMethodDTO, header);
		assertEquals(response.getStatusCodeValue(), 411);
		when(notifyProfileMethodService.updateNotifyProfileMethod(Mockito.any(), Mockito.any()))
				.thenThrow(new SizeExceedException());
		ResponseEntity<APIResponse<NotifyProfileMethodDTO>> response1 = notifyProfileMethodController
				.updateNotifyProfileMethod(notifyProfileMethodDTO, header);
		assertEquals(response1.getStatusCodeValue(), 411);
		when(notifyProfileMethodService.fetchNotifyProfileMethod(Mockito.any(), Mockito.any(), Mockito.any(),
				Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),
				Mockito.any(), Mockito.any())).thenThrow(new SizeExceedException());
		ResponseEntity<APIResponse<List<NotifyProfileMethodDTO>>> response2 = notifyProfileMethodController
				.getNotifyProfileMethod(notificationName, notificationMethod, notificationType, editBox, notifyAreaCode,
						notifyPrefix, notifySuffix, notifyPhoneExtension, notificationOrder, microwaveInd, pageNo,
						pageSize);
		assertEquals(response2.getStatusCodeValue(), 411);
	}

	@Test
	void testRecordNotAddedException() throws SQLException {
		when(notifyProfileMethodMapper.notifyProfileMethodToNotifyProfileMethodDTO(Mockito.any()))
				.thenReturn(notifyProfileMethodDTO);
		when(notifyProfileMethodMapper.notifyProfileMethodDTOToNotifyProfileMethod(Mockito.any()))
				.thenReturn(notifyProfileMethod);
		when(notifyProfileMethodService.insertNotifyProfileMethod(Mockito.any(), Mockito.any(), Mockito.any()))
				.thenThrow(new RecordNotAddedException());
		ResponseEntity<APIResponse<NotifyProfileMethodDTO>> response = notifyProfileMethodController
				.addNotifyProfileMethod(notifyProfileMethodDTO, header);
		assertEquals(response.getStatusCodeValue(), 406);
		when(notifyProfileMethodService.fetchNotifyProfileMethod(Mockito.any(), Mockito.any(), Mockito.any(),
				Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),
				Mockito.any(), Mockito.any())).thenThrow(new RecordNotAddedException());
		ResponseEntity<APIResponse<List<NotifyProfileMethodDTO>>> response2 = notifyProfileMethodController
				.getNotifyProfileMethod(notificationName, notificationMethod, notificationType, editBox, notifyAreaCode,
						notifyPrefix, notifySuffix, notifyPhoneExtension, notificationOrder, microwaveInd, pageNo,
						pageSize);
		assertEquals(response2.getStatusCodeValue(), 406);
	}

}
