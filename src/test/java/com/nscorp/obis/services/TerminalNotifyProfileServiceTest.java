package com.nscorp.obis.services;

import com.nscorp.obis.domain.NotificationMethod;
import com.nscorp.obis.domain.NotificationOrder;
import com.nscorp.obis.domain.NotificationType;
import com.nscorp.obis.domain.TerminalNotifyProfile;
import com.nscorp.obis.dto.NotifyProfileMethodDTO;
import com.nscorp.obis.dto.TerminalNotifyProfileDTO;
import com.nscorp.obis.exception.*;
import com.nscorp.obis.repository.GenericCodeUpdateRepository;
import com.nscorp.obis.repository.TerminalNotifyProfileRepository;
import com.nscorp.obis.repository.TerminalRepository;
import com.nscorp.obis.response.data.ResponseStatusCode;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class TerminalNotifyProfileServiceTest {
    @InjectMocks
    TerminalNotifyProfileServiceImpl terminalNotifyProfileService;
    
    @Mock
    TerminalNotifyProfileRepository repository;
    
    @Mock
    TerminalRepository  terminalRepository;
    
    @Mock
    GenericCodeUpdateRepository genericRepository;
    
    TerminalNotifyProfile terminalNotifyProfile;
    TerminalNotifyProfileDTO terminalNotifyProfileDto;
    List<TerminalNotifyProfile> terminalNotifyProfileList;
    List<TerminalNotifyProfileDTO> terminalNotifyProfileDtoList;
    NotifyProfileMethodDTO notifyProfileMethodDTO;
    List<NotifyProfileMethodDTO> notifyProfileMethodDTOList;
    List<ResponseObject> responseObjectList = new ArrayList<ResponseObject>();
    Map<String, String> header;
    Long terminalId;
    Long profileId;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		terminalNotifyProfile = new TerminalNotifyProfile();
		terminalNotifyProfileDto = new TerminalNotifyProfileDTO();
		terminalNotifyProfileList = new ArrayList<>();
		terminalNotifyProfileDtoList = new ArrayList<>();
		notifyProfileMethodDTO = new NotifyProfileMethodDTO();
		notifyProfileMethodDTOList = new ArrayList<NotifyProfileMethodDTO>();
		header = new HashMap<String,String>();
		terminalId=1653L;
		profileId = 2785L;
		terminalNotifyProfile.setTerminalId(terminalId);
		terminalNotifyProfile.setEventCode("SEAL");
		terminalNotifyProfile.setNotifyProfileId(profileId);
		terminalNotifyProfileList.add(terminalNotifyProfile);
		terminalNotifyProfileDto.setTerminalId(terminalId);
		terminalNotifyProfileDto.setEventCode("SEAL");
		terminalNotifyProfileDto.setNotifyProfileId(profileId);
		terminalNotifyProfileDtoList.add(terminalNotifyProfileDto);
		responseObjectList.add(new ResponseObject(Arrays.asList("1 Records Deleted Successfully!"),ResponseStatusCode.SUCCESS.getStatusCode(), ResponseStatusCode.SUCCESS.toString()));		
		header.put("userid", "Test");
		header.put("extensionschema", "Test");
	}

	@AfterEach
	void tearDown() throws Exception {
		terminalNotifyProfile = null;
		terminalNotifyProfileList = null;
		terminalNotifyProfileDto =null;
		notifyProfileMethodDTO = null;
		notifyProfileMethodDTOList = null;
	}

	@Test
	void testFetchTerminalProfilesByTermId() throws SQLException {
		when(terminalRepository.existsByTerminalId(Mockito.any())).thenReturn(true);
		when(repository.findByTerminalId(terminalId)).thenReturn(terminalNotifyProfileList);
		List<TerminalNotifyProfile> terminalNotifyProfiles = terminalNotifyProfileService.fetchTerminalProfilesByTermId(terminalId);
		assertEquals(terminalNotifyProfiles.get(0).getEventCode(),terminalNotifyProfileList.get(0).getEventCode());
		assertEquals(terminalNotifyProfiles.get(0).getTerminalId(),terminalNotifyProfileList.get(0).getTerminalId());
		assertEquals(terminalNotifyProfiles.get(0).getDayOfWeek(),terminalNotifyProfileList.get(0).getDayOfWeek());
		assertEquals(terminalNotifyProfiles.get(0).getShift(),terminalNotifyProfileList.get(0).getShift());
		
	}
	@Test
	void testInsertTerminalNotifyProfile() {
		when(terminalRepository.existsByTerminalId(Mockito.any())).thenReturn(true);
		when(repository.save(Mockito.any())).thenReturn(terminalNotifyProfile);
		when(genericRepository.existsByGenericTableAndGenericTableCode(Mockito.any(),Mockito.any())).thenReturn(true);
		TerminalNotifyProfile response = terminalNotifyProfileService.insertTerminalNotifyProfile(terminalNotifyProfile, header, terminalId);
	    assertEquals(response, terminalNotifyProfile);
	}
	@Test
	void testUpdateTerminalNotifyProfile() {
		when(repository.existsByNotifyProfileId(Mockito.any())).thenReturn(true);
		when(repository.findByNotifyProfileId(Mockito.any())).thenReturn(terminalNotifyProfile);
		when(genericRepository.existsByGenericTableAndGenericTableCode(Mockito.any(),Mockito.any())).thenReturn(true);
		
		TerminalNotifyProfile  response = terminalNotifyProfileService.updateTerminalProfileByProfileId(terminalNotifyProfileDto, header);
	}
	@Test
	void testDeleteTerminalProfiles() {
		when(repository.existsByNotifyProfileId(Mockito.any())).thenReturn(true);
		terminalNotifyProfileService.deleteTerminalProfile(terminalNotifyProfile);
	}
	@Test
	void testNoRecordsFoundException() throws SQLException{
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(terminalNotifyProfileService.fetchTerminalProfilesByTermId(terminalId)));
		assertEquals("No Terminal Found with this terminal id :"+terminalId, exception.getMessage());
		NoRecordsFoundException exception1 = assertThrows(NoRecordsFoundException.class,
				() -> when(terminalNotifyProfileService.insertTerminalNotifyProfile(terminalNotifyProfile, header, terminalId)));
		assertEquals("No Terminal Found with this terminal id :"+terminalId, exception1.getMessage());
		NoRecordsFoundException exception2 = assertThrows(NoRecordsFoundException.class,
				() -> when(terminalNotifyProfileService.updateTerminalProfileByProfileId(terminalNotifyProfileDto, header)));
		assertEquals("No record found for this profile Id : " + profileId, exception2.getMessage());
	}
	@Test
	void testAddNotifyProfileMethod() {
		notifyProfileMethodDTO.setNotificationName("TEST");
		notifyProfileMethodDTO.setNotificationMethod(NotificationMethod.EDI);
		notifyProfileMethodDTO.setNotificationOrder(NotificationOrder.PRIMARY);
		notifyProfileMethodDTO.setNotificationType(NotificationType.IMMEDIATE);
		notifyProfileMethodDTO.setEdiBox("TEST");
		notifyProfileMethodDTO.setNotifyMethodId(1234L);
		notifyProfileMethodDTOList.add(notifyProfileMethodDTO);
		terminalNotifyProfileDto.setNotifyProfileMethods(notifyProfileMethodDTOList);
		when(repository.existsByNotifyProfileId(Mockito.any())).thenReturn(true);
		when(repository.findByNotifyProfileId(Mockito.any())).thenReturn(terminalNotifyProfile);
		when(genericRepository.existsByGenericTableAndGenericTableCode(Mockito.any(),Mockito.any())).thenReturn(true);
		
		TerminalNotifyProfile  response = terminalNotifyProfileService.updateTerminalProfileByProfileId(terminalNotifyProfileDto, header);
	
	}
	@Test
	void testRecordNotAddedException() {
		when(terminalRepository.existsByTerminalId(Mockito.any())).thenReturn(true);
		when(genericRepository.existsByGenericTableAndGenericTableCode(Mockito.any(),Mockito.any())).thenReturn(true);
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> when(terminalNotifyProfileService.insertTerminalNotifyProfile(terminalNotifyProfile, header, terminalId)));
		assertEquals("Record Not added to Database", exception.getMessage());
	}
	@Test
	void testRecordAlreadyExistsException() {
		when(terminalRepository.existsByTerminalId(Mockito.any())).thenReturn(true);
		when(genericRepository.existsByGenericTableAndGenericTableCode(Mockito.any(),Mockito.any())).thenReturn(true);
		when(repository.existsByNotifyProfileId(Mockito.any())).thenReturn(true);
		RecordAlreadyExistsException exception = assertThrows(RecordAlreadyExistsException.class,
				() -> when(terminalNotifyProfileService.insertTerminalNotifyProfile(terminalNotifyProfile, header, terminalId)));
		assertEquals("This terminal notify profile already exists with notify Profile Id :"+profileId,exception.getMessage());
	}
	@Test
	void testInvalidDataException() {
		when(terminalRepository.existsByTerminalId(Mockito.any())).thenReturn(true);
		when(genericRepository.existsByGenericTableAndGenericTableCode(Mockito.any(), Mockito.any())).thenReturn(false);
		InvalidDataException exception = assertThrows(InvalidDataException.class,
				() -> when(terminalNotifyProfileService.insertTerminalNotifyProfile(terminalNotifyProfile, header, terminalId)));
	    assertEquals("Invalid Event Code",exception.getMessage());
	    terminalNotifyProfile.setEventCode(null);
		InvalidDataException exception1 = assertThrows(InvalidDataException.class,
				() -> when(terminalNotifyProfileService.insertTerminalNotifyProfile(terminalNotifyProfile, header, terminalId)));
	    assertEquals("Event code should not be null",exception1.getMessage());
	    terminalNotifyProfile.setEventCode("SEAL");
        terminalNotifyProfileDto.setNotifyProfileId(null);
	    InvalidDataException exception2 = assertThrows(InvalidDataException.class,
				() -> when(terminalNotifyProfileService.updateTerminalProfileByProfileId(terminalNotifyProfileDto, header)));
	    terminalNotifyProfileDto.setNotifyProfileId(profileId);
	    assertEquals("Notify Profile Id should not be null",exception2.getMessage());
	    when(repository.existsByNotifyProfileId(Mockito.any())).thenReturn(true);
		when(repository.findByNotifyProfileId(Mockito.any())).thenReturn(terminalNotifyProfile);
		InvalidDataException exception3 = assertThrows(InvalidDataException.class,
				() -> when(terminalNotifyProfileService.updateTerminalProfileByProfileId(terminalNotifyProfileDto, header)));
	    assertEquals("Invalid Event Code",exception3.getMessage());
	    terminalNotifyProfileDto.setEventCode("SEAL");
		notifyProfileMethodDTO.setNotifyMethodId(1234L);
		notifyProfileMethodDTOList.add(notifyProfileMethodDTO);
		when(genericRepository.existsByGenericTableAndGenericTableCode(Mockito.any(), Mockito.any())).thenReturn(true);
		terminalNotifyProfileDto.setNotifyProfileMethods(notifyProfileMethodDTOList);
		InvalidDataException exception5 = assertThrows(InvalidDataException.class,
				() -> when(terminalNotifyProfileService.updateTerminalProfileByProfileId(terminalNotifyProfileDto, header)));
	    assertEquals("Notification Name should not be null",exception5.getMessage());
	    notifyProfileMethodDTOList = new ArrayList<NotifyProfileMethodDTO>();
		notifyProfileMethodDTO.setNotificationName("TEST");
		notifyProfileMethodDTO.setNotificationMethod(NotificationMethod.EMAIL);
		notifyProfileMethodDTOList.add(notifyProfileMethodDTO);
		terminalNotifyProfileDto.setNotifyProfileMethods(notifyProfileMethodDTOList);
		InvalidDataException exception6 = assertThrows(InvalidDataException.class,
				() -> when(terminalNotifyProfileService.updateTerminalProfileByProfileId(terminalNotifyProfileDto, header)));
	    assertEquals("EMAIL Notification Method is not valid",exception6.getMessage());
	    notifyProfileMethodDTOList = new ArrayList<NotifyProfileMethodDTO>();
		notifyProfileMethodDTO.setNotificationMethod(null);
		notifyProfileMethodDTOList.add(notifyProfileMethodDTO);
		terminalNotifyProfileDto.setNotifyProfileMethods(notifyProfileMethodDTOList);
		InvalidDataException exception7 = assertThrows(InvalidDataException.class,
				() -> when(terminalNotifyProfileService.updateTerminalProfileByProfileId(terminalNotifyProfileDto, header)));
	    assertEquals("Must Specify Notification Method.",exception7.getMessage());
	    notifyProfileMethodDTOList = new ArrayList<NotifyProfileMethodDTO>();
		notifyProfileMethodDTO.setNotificationMethod(NotificationMethod.EDI);
		notifyProfileMethodDTOList.add(notifyProfileMethodDTO);
		terminalNotifyProfileDto.setNotifyProfileMethods(notifyProfileMethodDTOList);
		InvalidDataException exception8 = assertThrows(InvalidDataException.class,
				() -> when(terminalNotifyProfileService.updateTerminalProfileByProfileId(terminalNotifyProfileDto, header)));
	    assertEquals("Must Specify Notification Order",exception8.getMessage());
	    notifyProfileMethodDTOList = new ArrayList<NotifyProfileMethodDTO>();
		notifyProfileMethodDTO.setNotificationOrder(NotificationOrder.SECONDARY);
		notifyProfileMethodDTOList.add(notifyProfileMethodDTO);
		terminalNotifyProfileDto.setNotifyProfileMethods(notifyProfileMethodDTOList);
		InvalidDataException exception9 = assertThrows(InvalidDataException.class,
				() -> when(terminalNotifyProfileService.updateTerminalProfileByProfileId(terminalNotifyProfileDto, header)));
	    assertEquals("Must Specify Notification Type.",exception9.getMessage());
	    notifyProfileMethodDTOList = new ArrayList<NotifyProfileMethodDTO>();
		notifyProfileMethodDTO.setNotificationType(NotificationType.DELAY_SUMMARY);
		notifyProfileMethodDTO.setEdiBox(null);
		notifyProfileMethodDTOList.add(notifyProfileMethodDTO);
		terminalNotifyProfileDto.setNotifyProfileMethods(notifyProfileMethodDTOList);
		InvalidDataException exception10 = assertThrows(InvalidDataException.class,
				() -> when(terminalNotifyProfileService.updateTerminalProfileByProfileId(terminalNotifyProfileDto, header)));
	    assertEquals("Secondary Notification order is only valid for event code RMFC or RCOV",exception10.getMessage());
	    terminalNotifyProfileDto.setEventCode("RMFC");
		InvalidDataException exception11 = assertThrows(InvalidDataException.class,
				() -> when(terminalNotifyProfileService.updateTerminalProfileByProfileId(terminalNotifyProfileDto, header)));
	    assertEquals("EDI Box is required field for Notification Method EDI",exception11.getMessage());
	    notifyProfileMethodDTOList = new ArrayList<NotifyProfileMethodDTO>();
		notifyProfileMethodDTO.setEdiBox("TEST");
		notifyProfileMethodDTOList.add(notifyProfileMethodDTO);
		terminalNotifyProfileDto.setNotifyProfileMethods(notifyProfileMethodDTOList);
		InvalidDataException exception12 = assertThrows(InvalidDataException.class,
				() -> when(terminalNotifyProfileService.updateTerminalProfileByProfileId(terminalNotifyProfileDto, header)));
	    assertEquals("Notification Type not valid for Method EDI.",exception12.getMessage());
	    notifyProfileMethodDTOList = new ArrayList<NotifyProfileMethodDTO>();
		notifyProfileMethodDTO.setNotificationType(NotificationType.DEFERRED_DETAIL);
		notifyProfileMethodDTOList.add(notifyProfileMethodDTO);
		terminalNotifyProfileDto.setNotifyProfileMethods(notifyProfileMethodDTOList);
		when(genericRepository.existsByGenericTableAndGenericTableCode("EDI_RSN",terminalNotifyProfileDto.getEventCode())).thenReturn(false);
		InvalidDataException exception13 = assertThrows(InvalidDataException.class,
				() -> when(terminalNotifyProfileService.updateTerminalProfileByProfileId(terminalNotifyProfileDto, header)));
	    assertEquals("Notification Method EDI not valid for event code "
							+ terminalNotifyProfileDto.getEventCode(),exception13.getMessage());
	    notifyProfileMethodDTOList = new ArrayList<NotifyProfileMethodDTO>();
		notifyProfileMethodDTO.setNotificationMethod(NotificationMethod.FAX);
		notifyProfileMethodDTOList.add(notifyProfileMethodDTO);
		terminalNotifyProfileDto.setNotifyProfileMethods(notifyProfileMethodDTOList);
		when(genericRepository.existsByGenericTableAndGenericTableCode("EDI_RSN",terminalNotifyProfileDto.getEventCode())).thenReturn(true);
		InvalidDataException exception14 = assertThrows(InvalidDataException.class,
				() -> when(terminalNotifyProfileService.updateTerminalProfileByProfileId(terminalNotifyProfileDto, header)));
	    assertEquals("Notify Method must be EDI before entering EDI box fields",exception14.getMessage());
	    notifyProfileMethodDTOList = new ArrayList<NotifyProfileMethodDTO>();
		notifyProfileMethodDTO.setEdiBox(null);
		notifyProfileMethodDTOList.add(notifyProfileMethodDTO);
		terminalNotifyProfileDto.setNotifyProfileMethods(notifyProfileMethodDTOList);
		terminalNotifyProfileDto.setEventCode("RMFC");
		InvalidDataException exception15 = assertThrows(InvalidDataException.class,
				() -> when(terminalNotifyProfileService.updateTerminalProfileByProfileId(terminalNotifyProfileDto, header)));
	    assertEquals("Change notificationtype , Event code should not be RMFC for notificationType Deferred Details or Deferred Summary.",exception15.getMessage());
	    notifyProfileMethodDTOList = new ArrayList<NotifyProfileMethodDTO>();
		notifyProfileMethodDTO.setNotificationType(NotificationType.DELAY_DETAIL);
		notifyProfileMethodDTO.setNotificationOrder(NotificationOrder.PRIMARY);
		notifyProfileMethodDTOList.add(notifyProfileMethodDTO);
		terminalNotifyProfileDto.setNotifyProfileMethods(notifyProfileMethodDTOList);
		terminalNotifyProfileDto.setEventCode("SEAL");
		InvalidDataException exception16 = assertThrows(InvalidDataException.class,
				() -> when(terminalNotifyProfileService.updateTerminalProfileByProfileId(terminalNotifyProfileDto, header)));
	    assertEquals("Change notificationtype , Event code should be RMFC or IRFC for notificationType Delay Details or Delay Summary.",exception16.getMessage());
	    terminalNotifyProfileDto.setEventCode("RMFC");
		InvalidDataException exception17 = assertThrows(InvalidDataException.class,
				() -> when(terminalNotifyProfileService.updateTerminalProfileByProfileId(terminalNotifyProfileDto, header)));
	    assertEquals("Must Enter Telephone No",exception17.getMessage());
	    
	}
	@Test
	void testRecordNotDeletedException() {
		when(repository.existsByNotifyProfileId(profileId)).thenReturn(false);
		RecordNotDeletedException exception = assertThrows(RecordNotDeletedException.class,
				() -> terminalNotifyProfileService.deleteTerminalProfile(terminalNotifyProfile));
		assertEquals(profileId + " Record Not Found!", exception.getMessage());
	}
}
