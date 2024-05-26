package com.nscorp.obis.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import com.nscorp.obis.common.SpecificationGenerator;
import com.nscorp.obis.domain.NotificationMethod;
import com.nscorp.obis.domain.NotificationOrder;
import com.nscorp.obis.domain.NotificationType;
import com.nscorp.obis.domain.NotifyProfileMethod;
import com.nscorp.obis.dto.NotifyProfileMethodDTO;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.repository.NotifyProfileMethodRepository;

public class NotifyProfileMethodServiceTest {

	@InjectMocks
	NotifyProfileMethodServiceImpl notifyProfileMethodService;

	@Mock
	NotifyProfileMethodRepository notifyProfileMethodRepository;
	
	@Mock
	SpecificationGenerator specificationGenerator;
	

	NotifyProfileMethod notifyProfileMethod;
	NotifyProfileMethodDTO notifyProfileMethodDTO;
	List<NotifyProfileMethod> notifyProfileMethodList;
	List<NotifyProfileMethodDTO> notifyProfileMethodDTOs;
	Specification<NotifyProfileMethod> specification;
	Page<NotifyProfileMethod> page;
	Optional<NotifyProfileMethod> optional;
	List<Predicate> predicates;
	Root<NotifyProfileMethod> root;

	NotifyProfileMethod updatedNotifyProfileMethod;

	String notificationName;
	Long notifyMethodId;
	String notificationMethod;
	String notificationType;
	String ediBox;
	Integer notifyAreaCode;
	Integer notifyPrefix;
	Integer pageNo;
	Integer pageSize;
	Character microwaveIndicator;
	String notificationOrder;
	Integer notifyPhoneExtension;
	Integer notifySuffix;
	String autoRenotify;
	String notifyEmail;
	String notifyEmailId;
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
		notifyProfileMethodList = new ArrayList<>();
		notifyProfileMethodDTO = new NotifyProfileMethodDTO();
		notifyProfileMethodDTOs = new ArrayList<NotifyProfileMethodDTO>();
		optional = Optional.of(new NotifyProfileMethod());
		predicates = new ArrayList<>();
		// predicates.add(CriteriaBuilder.and(CriteriaBuilder.like(root.get("notificationName"),
		// "%" + notificationName + "%")));

		notifyMethodId = 15285834852735L;
		notificationName = "ABF";
		notificationMethod = NotificationMethod.FAX.toString();
		notificationType = NotificationType.DELAY_SUMMARY.toString();
		ediBox = "TESTEDI";
		notifyAreaCode = 111;
		notifyPrefix = 222;
		pageNo = 0;
		pageSize = 1;
		microwaveIndicator = 'Y';
		notificationOrder = NotificationOrder.PRIMARY.toString();
		notifyPhoneExtension = 123;
		notifySuffix = 1234;
		notifyProfileMethod.setNotifyMethodId(notifyMethodId);
		notifyProfileMethodDTO.setNotifyMethodId(notifyMethodId);
		notifyProfileMethod.setNotificationName(notificationName);
		notifyProfileMethodDTO.setNotificationName(notificationName);
		notifyProfileMethod.setNotificationMethod(NotificationMethod.EDI);
		notifyProfileMethodDTO.setNotificationMethod(NotificationMethod.EDI);
		notifyProfileMethod.setNotificationOrder(NotificationOrder.PRIMARY);
		notifyProfileMethodDTO.setNotificationOrder(NotificationOrder.PRIMARY);
		notifyProfileMethod.setNotificationType(NotificationType.IMMEDIATE);
		notifyProfileMethodDTO.setNotificationType(NotificationType.IMMEDIATE);
		notifyProfileMethod.setNotifyAreaCode(111);
		notifyProfileMethodDTO.setNotifyAreaCode("111");
		notifyProfileMethod.setNotifyPrefix(222);
		notifyProfileMethodDTO.setNotifyPrefix("222");
		notifyProfileMethod.setNotifySuffix(333);
		notifyProfileMethodDTO.setNotifySuffix("333");
		notifyProfileMethod.setMicrowaveIndicator('Y');
		notifyProfileMethodDTO.setMicrowaveIndicator("Y");
		notifyProfileMethod.setAutoRenotify('Y');
		notifyProfileMethodDTO.setAutoRenotify("Y");
		notifyProfileMethod.setEdiBox(ediBox);
		notifyProfileMethodDTO.setEdiBox(ediBox);
		notifyProfileMethod.setNotifyEmail(notifyEmail);
		notifyProfileMethodDTO.setNotifyEmail(notifyEmail);
		notifyProfileMethod.setNotifyEmailId(notifyEmailId);
		notifyProfileMethodDTO.setNotifyEmailId(notifyEmailId);
		notifyProfileMethodList.add(notifyProfileMethod);
		notifyProfileMethodDTOs.add(notifyProfileMethodDTO);
		page = new PageImpl<NotifyProfileMethod>(notifyProfileMethodList);

		specification = new Specification<NotifyProfileMethod>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 386803734047311080L;

			@Override
			public Predicate toPredicate(Root<NotifyProfileMethod> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};

		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");

	}

	@AfterEach
	void tearDown() throws Exception {
		notifyProfileMethod = null;
		notifyProfileMethodList = null;
		notifyProfileMethodDTO = null;
		notifyProfileMethodDTOs = null;
	}

	@Test
	void testFetchNotifyProfileMethods() throws SQLException {

		notificationType = "IMMEDIATE";
		notificationMethod = "EMAIL";
		notificationOrder = "PRIMARY";
		notificationName = "UNITS BACK FROM BAD ORDER";
		autoRenotify = "Y";
		ediBox = null;
		microwaveIndicator = 'N';
		notifyAreaCode = null;
		notifyEmail = "AshlandINT@roadone.com";
		notifyEmailId = null;
		notifyPhoneExtension = null;
		notifyPrefix = null;
		notifySuffix = null;
		pageNo = 0;
		pageSize = 1;

	
		// notifyProfileMethodList.add(notifyProfileMethod);
		when(notifyProfileMethodRepository.existsByNotifyMethodId(Mockito.any())).thenReturn(true);
		when(notifyProfileMethodRepository.findAll(Mockito.any(Specification.class))).thenReturn(notifyProfileMethodList);
		Pageable pageable = PageRequest.of(pageNo, pageSize,
				Sort.by(notifyProfileMethod.getNotificationName()).ascending());
		when(notifyProfileMethodRepository.findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class))).thenReturn(page);
//		notifyProfileMethodService.fetchNotifyProfileMethod(notificationName, notificationMethod, notificationType, ediBox, notifyAreaCode, notifyPrefix, notifySuffix, notifyPhoneExtension, notificationOrder, microwaveIndicator, pageNo, pageSize);

	}

	@Test
	void testGetAllNotifyProfileMethodsException() {
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(notifyProfileMethodService.fetchNotifyProfileMethod(notificationName, notificationMethod,
						notificationType, ediBox, notifyAreaCode, notifyPrefix, notifySuffix, notifyPhoneExtension,
						notificationOrder, microwaveIndicator, pageNo, pageSize)));
		assertEquals("No Records found for this Combination.", exception.getMessage());
	}

	@Test
	void testInsertNotifyProfileMethod() {
		notifyProfileMethod.setNotifyMethodId(1001L);
		notifyProfileMethod.setNotificationMethod(NotificationMethod.EMAIL);
		notifyProfileMethodDTO.setAutoRenotify("Y");
		notifyProfileMethod.setAutoRenotify(notifyProfileMethodDTO.getAutoRenotify().charAt(0));
		notifyProfileMethodDTO.setMicrowaveIndicator("N");
		notifyProfileMethod.setMicrowaveIndicator(notifyProfileMethodDTO.getMicrowaveIndicator().charAt(0));
		notifyProfileMethod.setEdiBox(null);
		when(notifyProfileMethodRepository.existsByNotifyMethodId(Mockito.any())).thenReturn(false);
		when(notifyProfileMethodRepository.save(Mockito.any())).thenReturn(notifyProfileMethod);
		NotifyProfileMethod addedNotifyProfileMethod = notifyProfileMethodService
				.insertNotifyProfileMethod(notifyProfileMethod, notifyProfileMethodDTO, header);
		assertEquals(addedNotifyProfileMethod, notifyProfileMethod);
	}

	@Test
	void testUpdateNotifyProfileMethod() {
		when(notifyProfileMethodRepository.existsByNotifyMethodId(Mockito.any())).thenReturn(true);
		when(notifyProfileMethodRepository.findByNotifyMethodId(Mockito.any())).thenReturn(notifyProfileMethod);
		when(notifyProfileMethodRepository.save(Mockito.any())).thenReturn(notifyProfileMethod);
		NotifyProfileMethod updated = notifyProfileMethodService.updateNotifyProfileMethod(notifyProfileMethodDTO,
				header);

	}

	@Test
	@DisplayName("RecordNotAddedException")
	void testRecordNotAddedException() throws SQLException {

		when(notifyProfileMethodRepository.existsByNotifyMethodId(Mockito.any())).thenReturn(false);
		RecordNotAddedException exception1 = assertThrows(RecordNotAddedException.class,
				() -> when(notifyProfileMethodService.insertNotifyProfileMethod(notifyProfileMethod,
						notifyProfileMethodDTO, header)));
		assertEquals("Record Not added to Database", exception1.getMessage());

	}

	@Test
	@DisplayName("NoRecordsFoundException")
	void testNoRecordsFoundException() {
		when(notifyProfileMethodRepository.existsByNotifyMethodId(Mockito.any())).thenReturn(false);
		notifyProfileMethodDTO.setNotificationName(null);
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(notifyProfileMethodService.updateNotifyProfileMethod(notifyProfileMethodDTO, header)));
		assertEquals("No record found for this profile Id : " + notifyMethodId, exception.getMessage());
		// when(notifyProfileMethodRepository.findAll(specification,
		// pageable)).thenReturn(page);
		NoRecordsFoundException exception1 = assertThrows(NoRecordsFoundException.class,
				() -> when(notifyProfileMethodService.fetchNotifyProfileMethod(notificationName, notificationMethod,
						notificationType, ediBox, notifyAreaCode, notifyPrefix, notifySuffix, notifyPhoneExtension,
						notificationOrder, microwaveIndicator, pageNo, null)));

		assertEquals("No Records found for this Combination.", exception1.getMessage());
	}

	@Test
	void testRecordAlreadyExistsException() {
		when(notifyProfileMethodRepository.existsByNotifyMethodId(Mockito.any())).thenReturn(true);
		RecordAlreadyExistsException exception = assertThrows(RecordAlreadyExistsException.class,
				() -> when(notifyProfileMethodService.insertNotifyProfileMethod(notifyProfileMethod,
						notifyProfileMethodDTO, header)));
		assertEquals("This notify profile method already exists with nothify method id: " + notifyMethodId,
				exception.getMessage());
	}

	@Test
	void testInvalidDataException() {
		when(notifyProfileMethodRepository.existsByNotifyMethodId(Mockito.any())).thenReturn(true);
		when(notifyProfileMethodRepository.findByNotifyMethodId(Mockito.any())).thenReturn(notifyProfileMethod);
		notifyProfileMethodDTO.setNotifyMethodId(null);
		InvalidDataException exception = assertThrows(InvalidDataException.class,
				() -> when(notifyProfileMethodService.updateNotifyProfileMethod(notifyProfileMethodDTO, header)));
		assertEquals("Notify Profile Method Id is required field.", exception.getMessage());
		notifyProfileMethodDTO.setNotificationName(null);
		notifyProfileMethodDTO.setNotifyMethodId(notifyMethodId);
		InvalidDataException exception2 = assertThrows(InvalidDataException.class,
				() -> when(notifyProfileMethodService.updateNotifyProfileMethod(notifyProfileMethodDTO, header)));
		assertEquals("Notification Name should not be null", exception2.getMessage());
		notifyProfileMethodDTO.setNotificationName("");
		InvalidDataException exception21 = assertThrows(InvalidDataException.class,
				() -> when(notifyProfileMethodService.updateNotifyProfileMethod(notifyProfileMethodDTO, header)));
		assertEquals("Notification Name should not be null", exception21.getMessage());
		notifyProfileMethodDTO.setNotificationName(notificationName);
		notifyProfileMethodDTO.setEdiBox(null);
		InvalidDataException exception3 = assertThrows(InvalidDataException.class,
				() -> when(notifyProfileMethodService.updateNotifyProfileMethod(notifyProfileMethodDTO, header)));
		notifyProfileMethodDTO.setEdiBox("");
		assertEquals("EDI Box is required field for Notification Method EDI", exception3.getMessage());
		InvalidDataException exception31 = assertThrows(InvalidDataException.class,
				() -> when(notifyProfileMethodService.updateNotifyProfileMethod(notifyProfileMethodDTO, header)));
		assertEquals("EDI Box is required field for Notification Method EDI", exception31.getMessage());
		notifyProfileMethodDTO.setEdiBox(ediBox);
		notifyProfileMethodDTO.setNotificationType(NotificationType.DELAY_SUMMARY);
		InvalidDataException exception4 = assertThrows(InvalidDataException.class,
				() -> when(notifyProfileMethodService.updateNotifyProfileMethod(notifyProfileMethodDTO, header)));
		assertEquals("Notification Type not valid for Method EDI.", exception4.getMessage());
		notifyProfileMethodDTO.setNotificationType(NotificationType.DEFERRED_SUMMARY);
		InvalidDataException exception41 = assertThrows(InvalidDataException.class,
				() -> when(notifyProfileMethodService.updateNotifyProfileMethod(notifyProfileMethodDTO, header)));
		assertEquals("Notification Type not valid for Method EDI.", exception41.getMessage());
		notifyProfileMethodDTO.setNotificationType(NotificationType.IMMEDIATE);
		notifyProfileMethodDTO.setNotificationMethod(null);
		InvalidDataException exception5 = assertThrows(InvalidDataException.class,
				() -> when(notifyProfileMethodService.updateNotifyProfileMethod(notifyProfileMethodDTO, header)));
		assertEquals("Notify Method must be EDI before entering EDI box fields", exception5.getMessage());
		notifyProfileMethodDTO.setEdiBox(null);
		InvalidDataException exception6 = assertThrows(InvalidDataException.class,
				() -> when(notifyProfileMethodService.updateNotifyProfileMethod(notifyProfileMethodDTO, header)));
		assertEquals("Must Specify Notification Method.", exception6.getMessage());
		notifyProfileMethodDTO.setNotificationMethod(NotificationMethod.FAX);
		notifyProfileMethodDTO.setNotificationType(null);
		InvalidDataException exception7 = assertThrows(InvalidDataException.class,
				() -> when(notifyProfileMethodService.updateNotifyProfileMethod(notifyProfileMethodDTO, header)));
		assertEquals("Must Specify Notification Type.", exception7.getMessage());
		notifyProfileMethodDTO.setNotificationType(NotificationType.IMMEDIATE);
		notifyProfileMethodDTO.setNotificationMethod(null);
		InvalidDataException exception8 = assertThrows(InvalidDataException.class,
				() -> when(notifyProfileMethodService.updateNotifyProfileMethod(notifyProfileMethodDTO, header)));
		assertEquals("Must Specify Notification Method.", exception8.getMessage());
		notifyProfileMethodDTO.setNotificationMethod(NotificationMethod.FAX);
		notifyProfileMethodDTO.setNotificationOrder(null);
		InvalidDataException exception9 = assertThrows(InvalidDataException.class,
				() -> when(notifyProfileMethodService.updateNotifyProfileMethod(notifyProfileMethodDTO, header)));
		assertEquals("Must Specify Notification Order", exception9.getMessage());
		notifyProfileMethodDTO.setNotificationOrder(NotificationOrder.PRIMARY);
		notifyProfileMethodDTO.setMicrowaveIndicator(null);
		InvalidDataException exception10 = assertThrows(InvalidDataException.class,
				() -> when(notifyProfileMethodService.updateNotifyProfileMethod(notifyProfileMethodDTO, header)));
		assertEquals("Must Enter MicrowaveIndicator Y or N", exception10.getMessage());
		notifyProfileMethodDTO.setNotifyAreaCode(null);
		notifyProfileMethodDTO.setNotifyPrefix(null);
		notifyProfileMethodDTO.setNotifySuffix(null);
		InvalidDataException exception11 = assertThrows(InvalidDataException.class,
				() -> when(notifyProfileMethodService.updateNotifyProfileMethod(notifyProfileMethodDTO, header)));
		assertEquals("Must Enter Telephone No", exception11.getMessage());
		notifyProfileMethodDTO.setNotificationMethod(NotificationMethod.VOICE);
		notifyProfileMethodDTO.setNotifyAreaCode(null);
		notifyProfileMethodDTO.setNotifyPrefix(null);
		notifyProfileMethodDTO.setNotifySuffix(null);
		InvalidDataException exception111 = assertThrows(InvalidDataException.class,
				() -> when(notifyProfileMethodService.updateNotifyProfileMethod(notifyProfileMethodDTO, header)));
		assertEquals("Must Enter Telephone No", exception111.getMessage());
		notifyProfileMethodDTO.setNotifySuffix("1234");
		InvalidDataException exception12 = assertThrows(InvalidDataException.class,
				() -> when(notifyProfileMethodService.updateNotifyProfileMethod(notifyProfileMethodDTO, header)));
		assertEquals("Phone Number Incomplete", exception12.getMessage());
		notifyProfileMethodDTO.setNotifyPrefix("111");
		notifyProfileMethodDTO.setNotifySuffix(null);
		InvalidDataException exception121 = assertThrows(InvalidDataException.class,
				() -> when(notifyProfileMethodService.updateNotifyProfileMethod(notifyProfileMethodDTO, header)));
		assertEquals("Phone Number Incomplete", exception121.getMessage());

		when(notifyProfileMethodRepository.existsByNotifyMethodId(Mockito.any())).thenReturn(false);
		notifyProfileMethod.setNotificationName(null);
		InvalidDataException insert1 = assertThrows(InvalidDataException.class, () -> when(notifyProfileMethodService
				.insertNotifyProfileMethod(notifyProfileMethod, notifyProfileMethodDTO, header)));
		assertEquals("Notification Name should not be null if notification method is provided.", insert1.getMessage());
		notifyProfileMethod.setNotificationName(notificationName);
		notifyProfileMethod.setNotificationMethod(null);
		InvalidDataException insert2 = assertThrows(InvalidDataException.class, () -> when(notifyProfileMethodService
				.insertNotifyProfileMethod(notifyProfileMethod, notifyProfileMethodDTO, header)));
		assertEquals("Notification Method should not be null if notification Name is provided.", insert2.getMessage());
		notifyProfileMethod.setNotificationMethod(NotificationMethod.FAX);
		notifyProfileMethod.setNotificationType(null);
		InvalidDataException insert3 = assertThrows(InvalidDataException.class, () -> when(notifyProfileMethodService
				.insertNotifyProfileMethod(notifyProfileMethod, notifyProfileMethodDTO, header)));
		assertEquals("Must Specify Notification Type.", insert3.getMessage());
		notifyProfileMethod.setNotificationType(NotificationType.IMMEDIATE);
		notifyProfileMethod.setNotificationOrder(null);
		InvalidDataException insert4 = assertThrows(InvalidDataException.class, () -> when(notifyProfileMethodService
				.insertNotifyProfileMethod(notifyProfileMethod, notifyProfileMethodDTO, header)));
		assertEquals("Must Specify Notification Order", insert4.getMessage());
		notifyProfileMethod.setNotificationOrder(NotificationOrder.PRIMARY);
		InvalidDataException insert5 = assertThrows(InvalidDataException.class, () -> when(notifyProfileMethodService
				.insertNotifyProfileMethod(notifyProfileMethod, notifyProfileMethodDTO, header)));
		assertEquals("Notify Method must be EDI before entering EDI box fields", insert5.getMessage());
		notifyProfileMethod.setEdiBox(ediBox);
		notifyProfileMethod.setNotificationMethod(NotificationMethod.EDI);
		notifyProfileMethod.setNotificationType(NotificationType.DEFERRED_SUMMARY);
		InvalidDataException insert6 = assertThrows(InvalidDataException.class, () -> when(notifyProfileMethodService
				.insertNotifyProfileMethod(notifyProfileMethod, notifyProfileMethodDTO, header)));
		assertEquals("Notification Type not valid for Method EDI.", insert6.getMessage());
		notifyProfileMethod.setEdiBox(null);
		notifyProfileMethod.setNotificationType(NotificationType.IMMEDIATE);
		InvalidDataException insert7 = assertThrows(InvalidDataException.class, () -> when(notifyProfileMethodService
				.insertNotifyProfileMethod(notifyProfileMethod, notifyProfileMethodDTO, header)));
		assertEquals("EDI Box is required field for Notification Method EDI", insert7.getMessage());

		notifyProfileMethod.setNotificationMethod(NotificationMethod.FAX);
		notifyProfileMethod.setMicrowaveIndicator(null);
		InvalidDataException insert8 = assertThrows(InvalidDataException.class, () -> when(notifyProfileMethodService
				.insertNotifyProfileMethod(notifyProfileMethod, notifyProfileMethodDTO, header)));
		assertEquals("Must Enter MicrowaveIndicator Y or N", insert8.getMessage());
		notifyProfileMethod.setNotifyAreaCode(null);
		notifyProfileMethod.setNotifyPrefix(null);
		notifyProfileMethod.setNotifySuffix(null);
		notifyProfileMethod.setMicrowaveIndicator('Y');
		InvalidDataException insert9 = assertThrows(InvalidDataException.class, () -> when(notifyProfileMethodService
				.insertNotifyProfileMethod(notifyProfileMethod, notifyProfileMethodDTO, header)));
		assertEquals("Must Enter Telephone No", insert9.getMessage());
		notifyProfileMethod.setNotifySuffix(1234);
		InvalidDataException insert10 = assertThrows(InvalidDataException.class, () -> when(notifyProfileMethodService
				.insertNotifyProfileMethod(notifyProfileMethod, notifyProfileMethodDTO, header)));
		assertEquals("Phone Number Incomplete", insert10.getMessage());
		notifyProfileMethod.setNotificationMethod(NotificationMethod.EDI);
		notifyProfileMethod.setEdiBox("");
		InvalidDataException insert11 = assertThrows(InvalidDataException.class, () -> when(notifyProfileMethodService
				.insertNotifyProfileMethod(notifyProfileMethod, notifyProfileMethodDTO, header)));
		assertEquals("EDI Box is required field for Notification Method EDI", insert11.getMessage());
		notifyProfileMethod.setEdiBox(ediBox);
		notifyProfileMethod.setNotificationName("");
		InvalidDataException insert12 = assertThrows(InvalidDataException.class, () -> when(notifyProfileMethodService
				.insertNotifyProfileMethod(notifyProfileMethod, notifyProfileMethodDTO, header)));
		assertEquals("Notification Name should not be null if notification method is provided.", insert12.getMessage());
		notifyProfileMethod.setNotificationName(notificationName);
		notifyProfileMethod.setNotificationType(NotificationType.DELAY_SUMMARY);
		InvalidDataException insert13 = assertThrows(InvalidDataException.class, () -> when(notifyProfileMethodService
				.insertNotifyProfileMethod(notifyProfileMethod, notifyProfileMethodDTO, header)));
		assertEquals("Notification Type not valid for Method EDI.", insert13.getMessage());
		notifyProfileMethod.setNotificationMethod(NotificationMethod.VOICE);
		notifyProfileMethod.setNotifyAreaCode(null);
		notifyProfileMethod.setNotifyPrefix(null);
		notifyProfileMethod.setNotifySuffix(null);
		notifyProfileMethod.setEdiBox(null);
		InvalidDataException insert14 = assertThrows(InvalidDataException.class, () -> when(notifyProfileMethodService
				.insertNotifyProfileMethod(notifyProfileMethod, notifyProfileMethodDTO, header)));
		assertEquals("Must Enter Telephone No", insert14.getMessage());
		notifyProfileMethod.setNotifyAreaCode(1234);
		InvalidDataException insert15 = assertThrows(InvalidDataException.class, () -> when(notifyProfileMethodService
				.insertNotifyProfileMethod(notifyProfileMethod, notifyProfileMethodDTO, header)));
		assertEquals("Phone Number Incomplete", insert15.getMessage());

		notifyProfileMethod.setNotifyMethodId(1234L);
		notifyProfileMethod.setNotificationType(null);
		notifyProfileMethod.setNotificationOrder(null);
		notifyProfileMethod.setNotificationMethod(null);
		notifyProfileMethod.setNotificationName(null);
		notifyProfileMethod.setAutoRenotify(null);
		notifyProfileMethod.setNotifyPhoneExtension(null);
		notifyProfileMethod.setNotifyAreaCode(null);
		notifyProfileMethod.setNotifyPrefix(null);
		notifyProfileMethod.setNotifySuffix(null);
		notifyProfileMethod.setMicrowaveIndicator(null);
		notifyProfileMethod.setNotifyEmail(null);
		notifyProfileMethod.setNotifyEmailId(null);
		notifyProfileMethod.setEdiBox(null);
		InvalidDataException insert16 = assertThrows(InvalidDataException.class, () -> when(notifyProfileMethodService
				.insertNotifyProfileMethod(notifyProfileMethod, notifyProfileMethodDTO, header)));
		assertEquals("W_NTFY_M Required", insert16.getMessage());

	}

}
