package com.nscorp.obis.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.nscorp.obis.domain.ActivityNotifyProfile;
import com.nscorp.obis.domain.DayOfWeek;
import com.nscorp.obis.domain.NotificationMethod;
import com.nscorp.obis.domain.NotificationOrder;
import com.nscorp.obis.domain.NotificationType;
import com.nscorp.obis.domain.NotifyProfileMethod;
import com.nscorp.obis.domain.Shift;
import com.nscorp.obis.domain.SpecialActivityNotifyProfile;
import com.nscorp.obis.dto.ActivityNotifyProfileDTO;
import com.nscorp.obis.dto.NotifyProfileMethodDTO;
import com.nscorp.obis.dto.SpecialActivityNotifyDTO;
import com.nscorp.obis.dto.SpecialActivityNotifyProfileDTO;
import com.nscorp.obis.dto.mapper.ActivityNotifyProfileMapper;
import com.nscorp.obis.dto.mapper.SpecialActivityNotifyProfileMapper;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.RecordNotDeletedException;
import com.nscorp.obis.repository.ActivityNotifyProfileRepository;
import com.nscorp.obis.repository.BaseActivityNotifyProfileRepository;
import com.nscorp.obis.repository.GenericCodeUpdateRepository;
import com.nscorp.obis.repository.NotifyProfileMethodRepository;
import com.nscorp.obis.repository.SpecialActivityNotifyProfileRepository;
import com.nscorp.obis.repository.TerminalRepository;

class SpecialActivityNotifyProfileServiceImplTest {

	@InjectMocks
	SpecialActivityNotifyProfileServiceImpl specialActivityNotifyProfileService;

	@Mock
	SpecialActivityNotifyProfileMapper specialActivityNotifyProfileMapper;

	@Mock
	SpecialActivityNotifyProfileRepository specialActivityNotifyProfileRepository;

	@Mock
	ActivityNotifyProfileRepository activityNotifyProfileRepository;

	@Mock
	ActivityNotifyProfileMapper activityNotifyProfileMapper;

	@Mock
	TerminalRepository terminalRepository;

	@Mock
	GenericCodeUpdateRepository genericCodeRepository;

	@Mock
	BaseActivityNotifyProfileRepository baseActivityNotifyProfileRepo;

	@Mock
	NotifyProfileMethodRepository notifyRepository;

	SpecialActivityNotifyProfile specialActivityNotifyProfile;
	SpecialActivityNotifyProfileDTO specialActivityNotifyProfileDTO;
	SpecialActivityNotifyDTO specialActivityNotifyDTO;
	ActivityNotifyProfileDTO activityNotifyProfileDTO;
	ActivityNotifyProfile activityNotifyProfile;
	List<SpecialActivityNotifyProfile> specialActivityNotifyProfileList;
	List<SpecialActivityNotifyDTO> specialActivityNotifyDTOList;
	List<ActivityNotifyProfileDTO> activityNotifyProfileDTOList;
	NotifyProfileMethod notifyProfileMethod;
	NotifyProfileMethodDTO notifyProfileMethodDTO;
	List<NotifyProfileMethodDTO> notifyProfileMethodsDtoList;

	Long notifyProfileId;
	Integer activityId = 12345;
	Map<String, String> headers = new HashMap<>();

	Optional<SpecialActivityNotifyProfile> custOptional;
	Optional<ActivityNotifyProfile> activityData;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		specialActivityNotifyProfile = new SpecialActivityNotifyProfile();
		specialActivityNotifyProfileDTO = new SpecialActivityNotifyProfileDTO();
		specialActivityNotifyDTO = new SpecialActivityNotifyDTO();
		specialActivityNotifyProfileList = new ArrayList<>();
		specialActivityNotifyDTOList = new ArrayList<>();
		activityNotifyProfileDTOList = new ArrayList<>();

		specialActivityNotifyProfile.setActivityId(activityId);
		specialActivityNotifyProfile.setActivityDesc("desc");
		specialActivityNotifyProfileDTO.setActivityId(activityId);
		specialActivityNotifyProfileDTO.setActivityDesc("description");
		
		specialActivityNotifyDTO.setActivityId(activityId);
		specialActivityNotifyProfileList.add(specialActivityNotifyProfile);
		specialActivityNotifyDTOList.add(specialActivityNotifyDTO);

		activityNotifyProfileDTO = new ActivityNotifyProfileDTO();
		activityNotifyProfileDTO.setDayOfWeek(new ArrayList<DayOfWeek>(java.util.Arrays.asList(DayOfWeek.MON)));
		activityNotifyProfileDTO.setShift(new ArrayList<Shift>(java.util.Arrays.asList(Shift.FIRST)));


		activityNotifyProfile = new ActivityNotifyProfile();
		activityNotifyProfileDTOList.add(activityNotifyProfileDTO);
		headers.put("userid", "test");
		custOptional = Optional.of(specialActivityNotifyProfile);
		activityData = Optional.of(activityNotifyProfile);

		notifyProfileMethod = new NotifyProfileMethod();
		notifyProfileMethodDTO = new NotifyProfileMethodDTO();
		notifyProfileMethodDTO.setNotificationName("name");
		notifyProfileMethodDTO.setNotificationMethod(NotificationMethod.EMAIL);
		notifyProfileMethodDTO.setNotificationOrder(NotificationOrder.SECONDARY);
		notifyProfileMethodDTO.setNotificationType(NotificationType.DELAY_SUMMARY);
		
		notifyProfileMethodsDtoList = new ArrayList<NotifyProfileMethodDTO>();

		specialActivityNotifyProfileDTO.setActivityNotifyProfiles(activityNotifyProfileDTOList);
		notifyProfileMethodsDtoList.add(notifyProfileMethodDTO);
		activityNotifyProfileDTO.setNotifyProfileMethods(notifyProfileMethodsDtoList);

		notifyProfileId = 12345L;

	}

	@AfterEach
	void tearDown() throws Exception {
		specialActivityNotifyProfileDTO = null;
		specialActivityNotifyDTO = null;
		activityNotifyProfileDTO = null;
		activityNotifyProfile = null;
		custOptional = null;
		activityData = null;

	}

	@Test
	void testFetchActivityDetails() throws SQLException {

		when(specialActivityNotifyProfileRepository.findAll()).thenReturn(specialActivityNotifyProfileList);
		List<SpecialActivityNotifyProfile> activityDetails = specialActivityNotifyProfileService.fetchActivityDetails();
		assertNotNull(activityDetails);
	}

	@Test
	void testFetchActivityDetailsException() throws SQLException {
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(specialActivityNotifyProfileService.fetchActivityDetails()));
		assertEquals("No Records found.", exception.getMessage());
	}

	@Test
	void testFetchActivityProfiles() throws SQLException {

		when(specialActivityNotifyProfileRepository.findByActivityId(activityId))
				.thenReturn(specialActivityNotifyProfileList);
		List<SpecialActivityNotifyProfile> activityDetails = specialActivityNotifyProfileService
				.fetchActivityProfiles(activityId);
		assertNotNull(activityDetails);
	}

	@Test
	void testFetchActivityProfilesException() throws SQLException {
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(specialActivityNotifyProfileService.fetchActivityProfiles(activityId)));
		assertEquals("No Records found for this activityId.", exception.getMessage());
	}

	@Test
	void testAddActivityProfile() throws SQLException {
		when(activityNotifyProfileRepository.existsByNotifyProfileId(Mockito.anyLong())).thenReturn(false);
		when(baseActivityNotifyProfileRepo.existsByDayOfWeekAndEventCodeAndShiftAndNotifyTerminalId(Mockito.anyString(),
				Mockito.anyString(), Mockito.anyString(), Mockito.anyLong())).thenReturn(false);
		when(specialActivityNotifyProfileRepository.existsById(Mockito.any())).thenReturn(true);
		when(activityNotifyProfileMapper.activityNotifyProfileDTOtoActivityNotifyProfile(Mockito.any()))
				.thenReturn(activityNotifyProfile);
		when(activityNotifyProfileMapper.activityNotifyProfiletoActivityNotifyProfileDTO(activityNotifyProfile))
				.thenReturn(activityNotifyProfileDTO);
		when(activityNotifyProfileRepository.save(Mockito.any())).thenReturn(activityNotifyProfile);
		ActivityNotifyProfileDTO activityDetails = specialActivityNotifyProfileService
				.addActivityNotifyProfile(activityNotifyProfileDTO, headers);
		assertNotNull(activityDetails);
		when(activityNotifyProfileRepository.existsByNotifyProfileId(Mockito.anyLong())).thenReturn(true);
		assertThrows(RecordAlreadyExistsException.class,
				() -> specialActivityNotifyProfileService.addActivityNotifyProfile(activityNotifyProfileDTO, headers));
		when(activityNotifyProfileRepository.existsByNotifyProfileId(Mockito.anyLong())).thenReturn(false);
		activityNotifyProfileDTO.setNotifyTerminalId(1234L);
		when(terminalRepository.existsByTerminalId(Mockito.anyLong())).thenReturn(false);
		assertThrows(NoRecordsFoundException.class,
				() -> specialActivityNotifyProfileService.addActivityNotifyProfile(activityNotifyProfileDTO, headers));
		activityNotifyProfileDTO.setNotifyTerminalId(null);
		activityNotifyProfileDTO.setEventCode("NTFY");
		assertThrows(InvalidDataException.class,
				() -> specialActivityNotifyProfileService.addActivityNotifyProfile(activityNotifyProfileDTO, headers));
		activityNotifyProfileDTO.setEventCode("TEST");
		when(genericCodeRepository.existsByGenericTableAndGenericTableCode(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(false);
		assertThrows(InvalidDataException.class,
				() -> specialActivityNotifyProfileService.addActivityNotifyProfile(activityNotifyProfileDTO, headers));
		when(genericCodeRepository.existsByGenericTableAndGenericTableCode(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(true);
		when(baseActivityNotifyProfileRepo.existsByDayOfWeekAndEventCodeAndShiftAndNotifyTerminalId(Mockito.any(),
				Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(true);
		assertThrows(RecordAlreadyExistsException.class,
				() -> specialActivityNotifyProfileService.addActivityNotifyProfile(activityNotifyProfileDTO, headers));
		when(baseActivityNotifyProfileRepo.existsByDayOfWeekAndEventCodeAndShiftAndNotifyTerminalId(Mockito.any(),
				Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(false);
		when(specialActivityNotifyProfileRepository.existsById(Mockito.any())).thenReturn(false);
		when(specialActivityNotifyProfileRepository.save(Mockito.any())).thenReturn(new SpecialActivityNotifyProfile());
		
		
		when(activityNotifyProfileRepository.save(Mockito.any())).thenReturn(null);
		assertThrows(RecordNotAddedException.class,
				() -> specialActivityNotifyProfileService.addActivityNotifyProfile(activityNotifyProfileDTO, headers));

	}

	@Test
	void testUpdateActivityProfile() throws SQLException {

        when(specialActivityNotifyProfileRepository.findById(Mockito.any())).thenReturn(custOptional);
		specialActivityNotifyProfile = custOptional.get();
		when(specialActivityNotifyProfileRepository.save(Mockito.any())).thenReturn(specialActivityNotifyProfile);

		when(activityNotifyProfileRepository.findById(Mockito.any())).thenReturn(activityData);
		activityNotifyProfile = activityData.get();

		activityNotifyProfileDTO.setEventCode("RMFC");
		when(genericCodeRepository.existsByGenericTableAndGenericTableCode(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
		// notifyProfileMethodsDtoList = null;
		when(activityNotifyProfileRepository.save(Mockito.any())).thenReturn(activityNotifyProfile);
		when(notifyRepository.save(Mockito.any())).thenReturn(notifyProfileMethod);

		SpecialActivityNotifyProfile details = specialActivityNotifyProfileService.updateActivityNotifyProfile(specialActivityNotifyProfileDTO, headers);
		assertNotNull(details);

	}

	@Test
	void testDeleteActivityProfile() throws SQLException {

		when(activityNotifyProfileRepository.existsByNotifyProfileId(Mockito.any())).thenReturn(true);
		activityNotifyProfile.setNotifyProfileId(6788L);
		specialActivityNotifyProfileService.deleteActivityNotifyProfile(activityNotifyProfile);
	}

	@Test
	void testRecordNotDeletedException() {
		when(activityNotifyProfileRepository.existsByNotifyProfileId(notifyProfileId)).thenReturn(false);
		activityNotifyProfile.setNotifyProfileId(notifyProfileId);
		RecordNotDeletedException exception = assertThrows(RecordNotDeletedException.class,
				() -> specialActivityNotifyProfileService.deleteActivityNotifyProfile(activityNotifyProfile));
		assertEquals(notifyProfileId + " Record Not Found!", exception.getMessage());
	}

	@Test
	void testUpdateActivityProfileException() throws SQLException{
		custOptional = Optional.empty();
		when(specialActivityNotifyProfileRepository.findById(Mockito.any())).thenReturn(custOptional);
		assertThrows(NoRecordsFoundException.class,
				() -> specialActivityNotifyProfileService.updateActivityNotifyProfile(specialActivityNotifyProfileDTO, headers));

		custOptional = Optional.of(specialActivityNotifyProfile);
		when(specialActivityNotifyProfileRepository.findById(Mockito.any())).thenReturn(custOptional);
	
		activityData = Optional.empty();
		when(activityNotifyProfileRepository.findById(Mockito.any())).thenReturn(activityData);
		assertThrows(NoRecordsFoundException.class,
				() -> specialActivityNotifyProfileService.updateActivityNotifyProfile(specialActivityNotifyProfileDTO, headers));

		activityData = Optional.of(activityNotifyProfile);
		when(activityNotifyProfileRepository.findById(Mockito.any())).thenReturn(activityData);
		activityNotifyProfileDTO.setEventCode("RMFC");
		when(genericCodeRepository.existsByGenericTableAndGenericTableCode(Mockito.anyString(), Mockito.anyString())).thenReturn(false);
		InvalidDataException exception1 = assertThrows(InvalidDataException.class,
				() -> specialActivityNotifyProfileService.updateActivityNotifyProfile(specialActivityNotifyProfileDTO, headers));
		assertEquals("Invalid Event Code", exception1.getMessage());

		when(genericCodeRepository.existsByGenericTableAndGenericTableCode(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
		activityNotifyProfileDTO.setEventCode("NTFY");
		assertThrows(InvalidDataException.class,
				() -> specialActivityNotifyProfileService.updateActivityNotifyProfile(specialActivityNotifyProfileDTO, headers));

		activityNotifyProfileDTO.setEventCode("RMFC");	

		notifyProfileMethodDTO.setNotificationName(null);
		assertThrows(InvalidDataException.class,
				() -> specialActivityNotifyProfileService.updateActivityNotifyProfile(specialActivityNotifyProfileDTO, headers));

		notifyProfileMethodDTO.setNotificationName("name");
		notifyProfileMethodDTO.setNotificationMethod(null);
		assertThrows(InvalidDataException.class,
				() -> specialActivityNotifyProfileService.updateActivityNotifyProfile(specialActivityNotifyProfileDTO, headers));

		notifyProfileMethodDTO.setNotificationMethod(NotificationMethod.EDI);
		notifyProfileMethodDTO.setNotificationOrder(null);
		assertThrows(InvalidDataException.class,
				() -> specialActivityNotifyProfileService.updateActivityNotifyProfile(specialActivityNotifyProfileDTO, headers));

		notifyProfileMethodDTO.setNotificationOrder(NotificationOrder.SECONDARY);
		notifyProfileMethodDTO.setNotificationType(null);
		assertThrows(InvalidDataException.class,
				() -> specialActivityNotifyProfileService.updateActivityNotifyProfile(specialActivityNotifyProfileDTO, headers));

		notifyProfileMethodDTO.setNotificationType(NotificationType.DELAY_SUMMARY);
		notifyProfileMethodDTO.setNotificationMethod(NotificationMethod.EDI);
		notifyProfileMethodDTO.setEdiBox(null);
		assertThrows(InvalidDataException.class,
				() -> specialActivityNotifyProfileService.updateActivityNotifyProfile(specialActivityNotifyProfileDTO, headers));

		notifyProfileMethodDTO.setEdiBox("EdiBox");
		notifyProfileMethodDTO.setNotificationType(NotificationType.DELAY_SUMMARY);
		assertThrows(InvalidDataException.class,
				() -> specialActivityNotifyProfileService.updateActivityNotifyProfile(specialActivityNotifyProfileDTO, headers));

		notifyProfileMethodDTO.setNotificationMethod(NotificationMethod.EMAIL);
		notifyProfileMethodDTO.setEdiBox("EdiBox");
		assertThrows(InvalidDataException.class,
				() -> specialActivityNotifyProfileService.updateActivityNotifyProfile(specialActivityNotifyProfileDTO, headers));

		// notifyProfileMethodDTO.setEdiBox(null);

		notifyProfileMethodDTO.setNotificationType(NotificationType.DEFERRED_DETAIL);
		activityNotifyProfileDTO.setEventCode("RMFC");	
		notifyProfileMethodDTO.setEdiBox(null);
		InvalidDataException exception12 = assertThrows(InvalidDataException.class,
				() -> specialActivityNotifyProfileService.updateActivityNotifyProfile(specialActivityNotifyProfileDTO, headers));
		assertEquals("Change notificationtype , Event code should not be RMFC for notificationType Deferred Details or Deferred Summary.", exception12.getMessage());

		notifyProfileMethodDTO.setNotificationType(NotificationType.DELAY_SUMMARY);
		notifyProfileMethodDTO.setNotificationOrder(NotificationOrder.SECONDARY);
		activityNotifyProfileDTO.setEventCode("SEAL");	
		InvalidDataException exception11 = assertThrows(InvalidDataException.class,
				() -> specialActivityNotifyProfileService.updateActivityNotifyProfile(specialActivityNotifyProfileDTO, headers));
		assertEquals("Secondary Notification order is only valid for event code RMFC or RCOV", exception11.getMessage());

		notifyProfileMethodDTO.setNotificationOrder(NotificationOrder.PRIMARY);
		notifyProfileMethodDTO.setNotificationType(NotificationType.DELAY_SUMMARY);
		InvalidDataException exception10= assertThrows(InvalidDataException.class,
				() -> specialActivityNotifyProfileService.updateActivityNotifyProfile(specialActivityNotifyProfileDTO, headers));
				assertEquals("Change notificationtype , Event code should be RMFC or IRFC for notificationType Delay Details or Delay Summary.", exception10.getMessage());
				
		notifyProfileMethodDTO.setNotificationType(NotificationType.DEFERRED_DETAIL);
		notifyProfileMethodDTO.setNotificationMethod(NotificationMethod.VOICE);
		notifyProfileMethodDTO.setNotifyAreaCode(null);
		notifyProfileMethodDTO.setNotifyPrefix(null);
		notifyProfileMethodDTO.setNotifySuffix(null);
		activityNotifyProfileDTO.setEventCode("SEAL");	
		notifyProfileMethodDTO.setEdiBox(null);
		InvalidDataException exception2 = assertThrows(InvalidDataException.class,
				() -> specialActivityNotifyProfileService.updateActivityNotifyProfile(specialActivityNotifyProfileDTO, headers));
		assertEquals("Must Enter Telephone No", exception2.getMessage());
				
		

		notifyProfileMethodDTO.setNotificationMethod(NotificationMethod.EMAIL);
		notifyProfileMethodDTO.setNotifyAreaCode("areacode");
		notifyProfileMethodDTO.setNotifyPrefix(null);
		InvalidDataException exception3 = assertThrows(InvalidDataException.class,
				() -> specialActivityNotifyProfileService.updateActivityNotifyProfile(specialActivityNotifyProfileDTO, headers));
		assertEquals("Phone Number Incomplete", exception3.getMessage());

		notifyProfileMethodDTO.setNotifyAreaCode("areacode");
		notifyProfileMethodDTO.setNotifyPrefix("prefix");
		notifyProfileMethodDTO.setNotifySuffix("suffix");
		notifyProfileMethodDTO.setNotificationMethod(NotificationMethod.FAX);
		notifyProfileMethodDTO.setMicrowaveIndicator(null);
		InvalidDataException exception4 = assertThrows(InvalidDataException.class,
				() -> specialActivityNotifyProfileService.updateActivityNotifyProfile(specialActivityNotifyProfileDTO, headers));
		assertEquals("Must Enter MicrowaveIndicator Y or N", exception4.getMessage());
		
		
		activityNotifyProfileDTO.setEventCode("SEAL");	
		notifyProfileMethodDTO.setNotificationMethod(NotificationMethod.EMAIL);	
		notifyProfileMethodDTO.setAutoRenotify("N");
		InvalidDataException exception5 = assertThrows(InvalidDataException.class,
				() -> specialActivityNotifyProfileService.updateActivityNotifyProfile(specialActivityNotifyProfileDTO, headers));
		assertEquals("The user can only enter the Auto renotify field if the event code is set as RMFC or RCOV and the notify method type is not set as EMAIL.", exception5.getMessage());
		
		notifyProfileMethodDTO.setNotificationMethod(NotificationMethod.VOICE);	
		InvalidDataException exception6 = assertThrows(InvalidDataException.class,
				() -> specialActivityNotifyProfileService.updateActivityNotifyProfile(specialActivityNotifyProfileDTO, headers));
		assertEquals("auto renotify fields populate only for the event code is RMFC or RCOV", exception6.getMessage());
		
		activityNotifyProfileDTO.setEventCode("RMFC");


		activityNotifyProfile.setNotifyProfileId(null);
		InvalidDataException exception18 = assertThrows(InvalidDataException.class,
				() -> specialActivityNotifyProfileService.deleteActivityNotifyProfile(activityNotifyProfile));
		assertEquals("Notify Profile Id should not be null", exception18.getMessage());
	}

}
