package com.nscorp.obis.services;

import com.nscorp.obis.domain.DestinationTerminalNotifyProfile;
import com.nscorp.obis.domain.NotificationMethod;
import com.nscorp.obis.domain.NotificationOrder;
import com.nscorp.obis.domain.NotificationType;
import com.nscorp.obis.dto.DestinationTerminalNotifyProfileDTO;
import com.nscorp.obis.dto.NotifyProfileMethodDTO;
import com.nscorp.obis.exception.*;
import com.nscorp.obis.repository.DestinationTerminalNotifyProfileRepository;
import com.nscorp.obis.repository.GenericCodeUpdateRepository;
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

class DestinationTerminalNotifyProfileServiceTest {
    @InjectMocks
    DestinationTerminalNotifyProfileServiceImpl destTerminalNotifyProfileService;
    
    @Mock
    DestinationTerminalNotifyProfileRepository repository;
    
    @Mock
    GenericCodeUpdateRepository genericRepository;
    
    @Mock
    TerminalRepository terminalRepository;
    
    DestinationTerminalNotifyProfile destTerminalNotifyProfile;
    DestinationTerminalNotifyProfileDTO destTerminalNotifyProfileDto;
    List<DestinationTerminalNotifyProfile> destTerminalNotifyProfileList;
    List<DestinationTerminalNotifyProfileDTO> destTerminalNotifyProfileDtoList;
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
		destTerminalNotifyProfile = new DestinationTerminalNotifyProfile();
		destTerminalNotifyProfileDto = new DestinationTerminalNotifyProfileDTO();
		destTerminalNotifyProfileList = new ArrayList<>();
		destTerminalNotifyProfileDtoList = new ArrayList<>();
		header = new HashMap<String,String>();
		notifyProfileMethodDTO = new NotifyProfileMethodDTO();
		notifyProfileMethodDTOList = new ArrayList<NotifyProfileMethodDTO>();
		terminalId=1653L;
		profileId = 2785L;
		destTerminalNotifyProfile.setTerminalId(terminalId);
		destTerminalNotifyProfile.setEventCode("SEAL");
		destTerminalNotifyProfile.setNotifyProfileId(profileId);
		destTerminalNotifyProfileList.add(destTerminalNotifyProfile);
		destTerminalNotifyProfileDto.setTerminalId(terminalId);
		destTerminalNotifyProfileDto.setEventCode("SEAL");
		destTerminalNotifyProfileDto.setNotifyProfileId(profileId);
		destTerminalNotifyProfileDtoList.add(destTerminalNotifyProfileDto);
		responseObjectList.add(new ResponseObject(Arrays.asList("1 Records Deleted Successfully!"),ResponseStatusCode.SUCCESS.getStatusCode(), ResponseStatusCode.SUCCESS.toString()));
		
		header.put("userid", "Test");
		header.put("extensionschema", "Test");
	}

	@AfterEach
	void tearDown() throws Exception {
		destTerminalNotifyProfile = null;
		destTerminalNotifyProfileList = null;
		destTerminalNotifyProfileDto =null;
		notifyProfileMethodDTO = null;
		notifyProfileMethodDTOList = null;
	}

	@Test
	void testFetchTerminalProfilesByTermId() throws SQLException {
		when(terminalRepository.existsByTerminalId(Mockito.any())).thenReturn(true);
		when(repository.findByTerminalId(Mockito.any())).thenReturn(destTerminalNotifyProfileList);
		List<DestinationTerminalNotifyProfile> destTerminalNotifyProfiles = destTerminalNotifyProfileService.fetchDestinationTerminalProfilesByTermId(terminalId);
		assertEquals(destTerminalNotifyProfiles.get(0).getEventCode(),destTerminalNotifyProfileList.get(0).getEventCode());
		assertEquals(destTerminalNotifyProfiles.get(0).getTerminalId(),destTerminalNotifyProfileList.get(0).getTerminalId());
		assertEquals(destTerminalNotifyProfiles.get(0).getDayOfWeek(),destTerminalNotifyProfileList.get(0).getDayOfWeek());
		assertEquals(destTerminalNotifyProfiles.get(0).getShift(),destTerminalNotifyProfileList.get(0).getShift());
		
	}
	@Test
	void testInsertTerminalNotifyProfile() {
		when(terminalRepository.existsByTerminalId(Mockito.any())).thenReturn(true);
		when(repository.save(Mockito.any())).thenReturn(destTerminalNotifyProfile);
		when(genericRepository.existsByGenericTableAndGenericTableCode(Mockito.any(),Mockito.any())).thenReturn(true);
		DestinationTerminalNotifyProfile response = destTerminalNotifyProfileService.insertDestinationTerminalNotifyProfile(destTerminalNotifyProfile, header, terminalId);
	    assertEquals(response, destTerminalNotifyProfile);
	}
	@Test
	void testUpdateTerminalNotifyProfile() throws SQLException {
		when(repository.existsByNotifyProfileId(Mockito.any())).thenReturn(true);
		when(repository.findByNotifyProfileId(Mockito.any())).thenReturn(destTerminalNotifyProfile);
		when(genericRepository.existsByGenericTableAndGenericTableCode(Mockito.any(),Mockito.any())).thenReturn(true);
		
		DestinationTerminalNotifyProfile  response = destTerminalNotifyProfileService.updateDestinationTerminalProfile(destTerminalNotifyProfileDto, header);
	}
	@Test
	void testDeleteTerminalProfiles() {
		when(repository.existsByNotifyProfileId(Mockito.any())).thenReturn(true);
		destTerminalNotifyProfileService.deleteDestinationTerminalProfile(destTerminalNotifyProfile);
	}
	@Test
	void testNoRecordsFoundException() throws SQLException{
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(destTerminalNotifyProfileService.fetchDestinationTerminalProfilesByTermId(terminalId)));
		assertEquals("No Terminal Found with this terminal id :"+terminalId, exception.getMessage());
		NoRecordsFoundException exception1 = assertThrows(NoRecordsFoundException.class,
				() -> when(destTerminalNotifyProfileService.insertDestinationTerminalNotifyProfile(destTerminalNotifyProfile, header, terminalId)));
		assertEquals("No Terminal Found with this terminal id :"+terminalId, exception1.getMessage());
		NoRecordsFoundException exception2 = assertThrows(NoRecordsFoundException.class,
				() -> when(destTerminalNotifyProfileService.updateDestinationTerminalProfile(destTerminalNotifyProfileDto, header)));
		assertEquals("No record found for this profile Id : " + profileId, exception2.getMessage());
	}
	@Test
	void testAddNotifyProfileMethod() throws SQLException {
		notifyProfileMethodDTO.setNotificationName("TEST");
		notifyProfileMethodDTO.setNotificationMethod(NotificationMethod.EDI);
		notifyProfileMethodDTO.setNotificationOrder(NotificationOrder.PRIMARY);
		notifyProfileMethodDTO.setNotificationType(NotificationType.IMMEDIATE);
		notifyProfileMethodDTO.setEdiBox("TEST");
		notifyProfileMethodDTO.setNotifyMethodId(1234L);
		notifyProfileMethodDTOList.add(notifyProfileMethodDTO);
		destTerminalNotifyProfileDto.setNotifyProfileMethods(notifyProfileMethodDTOList);
		when(repository.existsByNotifyProfileId(Mockito.any())).thenReturn(true);
		when(repository.findByNotifyProfileId(Mockito.any())).thenReturn(destTerminalNotifyProfile);
		when(genericRepository.existsByGenericTableAndGenericTableCode(Mockito.any(),Mockito.any())).thenReturn(true);
		
		DestinationTerminalNotifyProfile  response = destTerminalNotifyProfileService.updateDestinationTerminalProfile(destTerminalNotifyProfileDto, header);
	
	}
	@Test
	void testRecordNotAddedException() {
		when(terminalRepository.existsByTerminalId(Mockito.any())).thenReturn(true);
		when(genericRepository.existsByGenericTableAndGenericTableCode(Mockito.any(),Mockito.any())).thenReturn(true);
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> when(destTerminalNotifyProfileService.insertDestinationTerminalNotifyProfile(destTerminalNotifyProfile, header, terminalId)));
		assertEquals("Record Not added to Database", exception.getMessage());
	}
	@Test
	void testRecordAlreadyExistsException() {
		when(terminalRepository.existsByTerminalId(Mockito.any())).thenReturn(true);
		when(genericRepository.existsByGenericTableAndGenericTableCode(Mockito.any(),Mockito.any())).thenReturn(true);
		when(repository.existsByNotifyProfileId(Mockito.any())).thenReturn(true);
		RecordAlreadyExistsException exception = assertThrows(RecordAlreadyExistsException.class,
				() -> when(destTerminalNotifyProfileService.insertDestinationTerminalNotifyProfile(destTerminalNotifyProfile, header, terminalId)));
		assertEquals("This terminal notify profile already exists with notify Profile Id :"+profileId,exception.getMessage());
	}
	@Test
	void testInvalidDataException() {
		when(terminalRepository.existsByTerminalId(Mockito.any())).thenReturn(true);
		when(genericRepository.existsByGenericTableAndGenericTableCode(Mockito.any(), Mockito.any())).thenReturn(false);
		InvalidDataException exception = assertThrows(InvalidDataException.class,
				() -> when(destTerminalNotifyProfileService.insertDestinationTerminalNotifyProfile(destTerminalNotifyProfile, header, terminalId)));
	    assertEquals("Invalid Event Code",exception.getMessage());
	    destTerminalNotifyProfile.setEventCode(null);
		InvalidDataException exception1 = assertThrows(InvalidDataException.class,
				() -> when(destTerminalNotifyProfileService.insertDestinationTerminalNotifyProfile(destTerminalNotifyProfile, header, terminalId)));
	    assertEquals("Event code should not be null",exception1.getMessage());
	    destTerminalNotifyProfile.setEventCode("SEAL");
        destTerminalNotifyProfileDto.setNotifyProfileId(null);
	    InvalidDataException exception2 = assertThrows(InvalidDataException.class,
				() -> when(destTerminalNotifyProfileService.updateDestinationTerminalProfile(destTerminalNotifyProfileDto, header)));
	    destTerminalNotifyProfileDto.setNotifyProfileId(profileId);
	    assertEquals("Notify Profile Id should not be null",exception2.getMessage());
	    when(repository.existsByNotifyProfileId(Mockito.any())).thenReturn(true);
		when(repository.findByNotifyProfileId(Mockito.any())).thenReturn(destTerminalNotifyProfile);
		InvalidDataException exception3 = assertThrows(InvalidDataException.class,
				() -> when(destTerminalNotifyProfileService.updateDestinationTerminalProfile(destTerminalNotifyProfileDto, header)));
	    assertEquals("Invalid Event Code",exception3.getMessage());
	    destTerminalNotifyProfileDto.setEventCode("SEAL");
		notifyProfileMethodDTO.setNotifyMethodId(1234L);
		notifyProfileMethodDTOList.add(notifyProfileMethodDTO);
		when(genericRepository.existsByGenericTableAndGenericTableCode(Mockito.any(), Mockito.any())).thenReturn(true);
		destTerminalNotifyProfileDto.setNotifyProfileMethods(notifyProfileMethodDTOList);
		InvalidDataException exception5 = assertThrows(InvalidDataException.class,
				() -> when(destTerminalNotifyProfileService.updateDestinationTerminalProfile(destTerminalNotifyProfileDto, header)));
	    assertEquals("Notification Name should not be null",exception5.getMessage());
	    notifyProfileMethodDTOList = new ArrayList<NotifyProfileMethodDTO>();
		notifyProfileMethodDTO.setNotificationName("TEST");
		notifyProfileMethodDTO.setNotificationMethod(NotificationMethod.EMAIL);
		notifyProfileMethodDTOList.add(notifyProfileMethodDTO);
		destTerminalNotifyProfileDto.setNotifyProfileMethods(notifyProfileMethodDTOList);
		InvalidDataException exception6 = assertThrows(InvalidDataException.class,
				() -> when(destTerminalNotifyProfileService.updateDestinationTerminalProfile(destTerminalNotifyProfileDto, header)));
	    assertEquals("EMAIL Notification Method is not valid",exception6.getMessage());
	    notifyProfileMethodDTOList = new ArrayList<NotifyProfileMethodDTO>();
		notifyProfileMethodDTO.setNotificationMethod(null);
		notifyProfileMethodDTOList.add(notifyProfileMethodDTO);
		destTerminalNotifyProfileDto.setNotifyProfileMethods(notifyProfileMethodDTOList);
		InvalidDataException exception7 = assertThrows(InvalidDataException.class,
				() -> when(destTerminalNotifyProfileService.updateDestinationTerminalProfile(destTerminalNotifyProfileDto, header)));
	    assertEquals("Must Specify Notification Method.",exception7.getMessage());
	    notifyProfileMethodDTOList = new ArrayList<NotifyProfileMethodDTO>();
		notifyProfileMethodDTO.setNotificationMethod(NotificationMethod.EDI);
		notifyProfileMethodDTOList.add(notifyProfileMethodDTO);
		destTerminalNotifyProfileDto.setNotifyProfileMethods(notifyProfileMethodDTOList);
		InvalidDataException exception8 = assertThrows(InvalidDataException.class,
				() -> when(destTerminalNotifyProfileService.updateDestinationTerminalProfile(destTerminalNotifyProfileDto, header)));
	    assertEquals("Must Specify Notification Order",exception8.getMessage());
	    notifyProfileMethodDTOList = new ArrayList<NotifyProfileMethodDTO>();
		notifyProfileMethodDTO.setNotificationOrder(NotificationOrder.SECONDARY);
		notifyProfileMethodDTOList.add(notifyProfileMethodDTO);
		destTerminalNotifyProfileDto.setNotifyProfileMethods(notifyProfileMethodDTOList);
		InvalidDataException exception9 = assertThrows(InvalidDataException.class,
				() -> when(destTerminalNotifyProfileService.updateDestinationTerminalProfile(destTerminalNotifyProfileDto, header)));
	    assertEquals("Must Specify Notification Type.",exception9.getMessage());
	    notifyProfileMethodDTOList = new ArrayList<NotifyProfileMethodDTO>();
		notifyProfileMethodDTO.setNotificationType(NotificationType.DELAY_SUMMARY);
		notifyProfileMethodDTO.setEdiBox(null);
		notifyProfileMethodDTOList.add(notifyProfileMethodDTO);
		destTerminalNotifyProfileDto.setNotifyProfileMethods(notifyProfileMethodDTOList);
		InvalidDataException exception10 = assertThrows(InvalidDataException.class,
				() -> when(destTerminalNotifyProfileService.updateDestinationTerminalProfile(destTerminalNotifyProfileDto, header)));
	    assertEquals("Secondary Notification order is only valid for event code RMFC or RCOV",exception10.getMessage());
	    destTerminalNotifyProfileDto.setEventCode("RMFC");
		InvalidDataException exception11 = assertThrows(InvalidDataException.class,
				() -> when(destTerminalNotifyProfileService.updateDestinationTerminalProfile(destTerminalNotifyProfileDto, header)));
	    assertEquals("EDI Box is required field for Notification Method EDI",exception11.getMessage());
	    notifyProfileMethodDTOList = new ArrayList<NotifyProfileMethodDTO>();
		notifyProfileMethodDTO.setEdiBox("TEST");
		notifyProfileMethodDTOList.add(notifyProfileMethodDTO);
		destTerminalNotifyProfileDto.setNotifyProfileMethods(notifyProfileMethodDTOList);
		InvalidDataException exception12 = assertThrows(InvalidDataException.class,
				() -> when(destTerminalNotifyProfileService.updateDestinationTerminalProfile(destTerminalNotifyProfileDto, header)));
	    assertEquals("Notification Type not valid for Method EDI.",exception12.getMessage());
	    notifyProfileMethodDTOList = new ArrayList<NotifyProfileMethodDTO>();
		notifyProfileMethodDTO.setNotificationType(NotificationType.DEFERRED_DETAIL);
		notifyProfileMethodDTOList.add(notifyProfileMethodDTO);
		destTerminalNotifyProfileDto.setNotifyProfileMethods(notifyProfileMethodDTOList);
		when(genericRepository.existsByGenericTableAndGenericTableCode("EDI_RSN",destTerminalNotifyProfileDto.getEventCode())).thenReturn(false);
		InvalidDataException exception13 = assertThrows(InvalidDataException.class,
				() -> when(destTerminalNotifyProfileService.updateDestinationTerminalProfile(destTerminalNotifyProfileDto, header)));
	    assertEquals("Notification Method EDI not valid for event code "
							+ destTerminalNotifyProfileDto.getEventCode(),exception13.getMessage());
	    notifyProfileMethodDTOList = new ArrayList<NotifyProfileMethodDTO>();
		notifyProfileMethodDTO.setNotificationMethod(NotificationMethod.FAX);
		notifyProfileMethodDTOList.add(notifyProfileMethodDTO);
		destTerminalNotifyProfileDto.setNotifyProfileMethods(notifyProfileMethodDTOList);
		when(genericRepository.existsByGenericTableAndGenericTableCode("EDI_RSN",destTerminalNotifyProfileDto.getEventCode())).thenReturn(true);
		InvalidDataException exception14 = assertThrows(InvalidDataException.class,
				() -> when(destTerminalNotifyProfileService.updateDestinationTerminalProfile(destTerminalNotifyProfileDto, header)));
	    assertEquals("Notify Method must be EDI before entering EDI box fields",exception14.getMessage());
	    notifyProfileMethodDTOList = new ArrayList<NotifyProfileMethodDTO>();
		notifyProfileMethodDTO.setEdiBox(null);
		notifyProfileMethodDTOList.add(notifyProfileMethodDTO);
		destTerminalNotifyProfileDto.setNotifyProfileMethods(notifyProfileMethodDTOList);
		destTerminalNotifyProfileDto.setEventCode("RMFC");
		InvalidDataException exception15 = assertThrows(InvalidDataException.class,
				() -> when(destTerminalNotifyProfileService.updateDestinationTerminalProfile(destTerminalNotifyProfileDto, header)));
	    assertEquals("Change notificationtype , Event code should not be RMFC for notificationType Deferred Details or Deferred Summary.",exception15.getMessage());
	    notifyProfileMethodDTOList = new ArrayList<NotifyProfileMethodDTO>();
		notifyProfileMethodDTO.setNotificationType(NotificationType.DELAY_DETAIL);
		notifyProfileMethodDTO.setNotificationOrder(NotificationOrder.PRIMARY);
		notifyProfileMethodDTOList.add(notifyProfileMethodDTO);
		destTerminalNotifyProfileDto.setNotifyProfileMethods(notifyProfileMethodDTOList);
		destTerminalNotifyProfileDto.setEventCode("SEAL");
		InvalidDataException exception16 = assertThrows(InvalidDataException.class,
				() -> when(destTerminalNotifyProfileService.updateDestinationTerminalProfile(destTerminalNotifyProfileDto, header)));
	    assertEquals("Change notificationtype , Event code should be RMFC or IRFC for notificationType Delay Details or Delay Summary.",exception16.getMessage());
	    destTerminalNotifyProfileDto.setEventCode("RMFC");
		InvalidDataException exception17 = assertThrows(InvalidDataException.class,
				() -> when(destTerminalNotifyProfileService.updateDestinationTerminalProfile(destTerminalNotifyProfileDto, header)));
	    assertEquals("Must Enter Telephone No",exception17.getMessage());
	    
	}
	@Test
	void testRecordNotDeletedException() {
		when(repository.existsByNotifyProfileId(profileId)).thenReturn(false);
		RecordNotDeletedException exception = assertThrows(RecordNotDeletedException.class,
				() -> destTerminalNotifyProfileService.deleteDestinationTerminalProfile(destTerminalNotifyProfile));
		assertEquals(profileId + " Record Not Found!", exception.getMessage());
	}
}
