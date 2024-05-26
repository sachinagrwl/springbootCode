package com.nscorp.obis.services;

import com.nscorp.obis.domain.*;
import com.nscorp.obis.dto.CustomerDTO;
import com.nscorp.obis.dto.CustomerNotifyProfileDTO;
import com.nscorp.obis.dto.DeliveryDetailDTO;
import com.nscorp.obis.dto.NotifyProfileMethodDTO;
import com.nscorp.obis.dto.mapper.NotifyProfileMethodMapper;
import com.nscorp.obis.exception.*;
import com.nscorp.obis.repository.CustomerNotifyProfileRepository;
import com.nscorp.obis.repository.CustomerRepository;
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

class CustomerNotifyProfileServiceImplTest {

	@Mock
	CustomerNotifyProfileRepository repository;

	@Mock
	TerminalRepository terminalRepository;

	@Mock
	CustomerRepository customerRepository;

	@Mock
	GenericCodeUpdateRepository genericRepository;

	@Mock
	NotifyProfileMethodMapper notifyProfileMethodMapper;

	@InjectMocks
	CustomerNotifyProfileServiceImpl serviceImpl;

	CustomerNotifyProfile customerNotifyProfile;
	List<CustomerNotifyProfile> customerNotifyProfileList;
	CustomerNotifyProfileDTO customerNotifyProfileDTO;
	List<CustomerNotifyProfileDTO> customerNotifyProfileDtoList;
	NotifyProfileMethod notifyProfileMethod;
	NotifyProfileMethodDTO notifyProfileMethodDTO;
	List<NotifyProfileMethod> notifyProfileMethods;
	List<NotifyProfileMethodDTO> notifyProfileMethodDTOList;
	List<ResponseObject> responseObjectList = new ArrayList<ResponseObject>();
	DeliveryDetail deliveryDetail;
	List<DeliveryDetail> deliveryDetails;
	DeliveryDetailDTO deliveryDetailDTO;
	List<DeliveryDetailDTO> deliveryDetailDTOs;
	List<Shift> shifts;
	List<DayOfWeek> dayOfWeeks;
	Customer customer;
	CustomerDTO customerDTO;
	NotificationOrder notificationOrder;
	Map<String, String> header;
	Long customerId;
	Long notifyProfileId;
	Long notifyTerminalId;
	String eventCode;

	CustomerNotifyProfile addCustomerNotifyProfile;
	CustomerNotifyProfile updatedCustomerNotifyProfile;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {

	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		customerNotifyProfile = new CustomerNotifyProfile();
		customerNotifyProfileList = new ArrayList<>();
		customerNotifyProfileDTO = new CustomerNotifyProfileDTO();
		customerNotifyProfileDtoList = new ArrayList<>();
		shifts = new ArrayList<Shift>();
		dayOfWeeks = new ArrayList<DayOfWeek>();
		customerId = 6789L;
		customer = new Customer();
		customer.setCustomerId(customerId);
		customerDTO = new CustomerDTO();
		customerDTO.setCustomerId(customerId);
		deliveryDetail = new DeliveryDetail();
		deliveryDetail.setCustomerId(customerId);
		
		deliveryDetail.setConfirmationTimeInterval(30);
		deliveryDetail.setSendSETG("T");
		deliveryDetail.setSendDETG("T");
		customer.setDeliveryDetail(deliveryDetail);
		customerDTO.setCustomerId(customerId);
		deliveryDetailDTO = new DeliveryDetailDTO();
		
		deliveryDetailDTO.setConfirmationTimeInterval(30);
		deliveryDetailDTO.setSendSETG(true);
		deliveryDetailDTO.setSendDETG(true);
		customerDTO.setDeliveryDetail(deliveryDetailDTO);
		customerDTO.setCustomerName("TEST");
		notifyProfileMethod = new NotifyProfileMethod();
		notifyProfileMethodDTO = new NotifyProfileMethodDTO();
		notifyProfileMethods = new ArrayList<NotifyProfileMethod>();
		notifyProfileMethodDTOList = new ArrayList<NotifyProfileMethodDTO>();
		
		notifyProfileId = 12345L;
		notifyTerminalId = 5454L;
		eventCode = "SEAL";
		shifts.add(Shift.FIRST);
		dayOfWeeks.add(DayOfWeek.SAT);
		dayOfWeeks.add(DayOfWeek.SUN);
		customerNotifyProfile.setShift(shifts);
		customerNotifyProfileDTO.setShift(shifts);
		customerNotifyProfile.setDayOfWeek(dayOfWeeks);
		customerNotifyProfileDTO.setDayOfWeek(dayOfWeeks);
		customerNotifyProfileDTO.setCustomer(customerDTO);
		customerNotifyProfile.setNotifyProfileId(notifyProfileId);
		customerNotifyProfile.setNotifyTerminalId(notifyTerminalId);
		customerNotifyProfile.setEventCode("SEAL");
		customerNotifyProfile.setCustomer(customer);
		customerNotifyProfile.setNotifyProfileMethods(notifyProfileMethods);
		customerNotifyProfileList.add(customerNotifyProfile);
		
		customerNotifyProfileDTO.setNotifyProfileId(notifyProfileId);
		customerNotifyProfileDTO.setNotifyTerminalId(notifyTerminalId);
		customerNotifyProfileDTO.setEventCode("SEAL");
		customerNotifyProfileDtoList.add(customerNotifyProfileDTO);
		
		responseObjectList.add(new ResponseObject(Arrays.asList("1 Records Deleted Successfully!"),
				ResponseStatusCode.SUCCESS.getStatusCode(), ResponseStatusCode.SUCCESS.toString()));
		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");
	}

	@AfterEach
	void tearDown() throws Exception {
		customerNotifyProfile = null;
		customerNotifyProfileList = null;
		customerNotifyProfileDTO = null;
		customerNotifyProfileDtoList = null;
		notifyProfileMethod = null;
		notifyProfileMethodDTO = null;
		deliveryDetail = null;
		customer = null;
		customerDTO = null;
	}

	@Test
	void testFetchCustomerNotifyProfiles() throws SQLException {
		when(repository.existsByNotifyProfileId(Mockito.any())).thenReturn(true);
		when(customerRepository.existsByCustomerId(Mockito.any())).thenReturn(true);
		customer.setCustomerId(customerId);
		customer.setDeliveryDetail(deliveryDetail);
		customerNotifyProfile.setCustomer(customer);
		customerNotifyProfile.setNotifyProfileMethods(notifyProfileMethods);
		customerNotifyProfileList = new ArrayList<>();
		customerNotifyProfileList.add(customerNotifyProfile);
		when(repository.findByCustomer_CustomerId(customerId)).thenReturn(customerNotifyProfileList);
		List<CustomerNotifyProfile> customerNotifyProfiles = serviceImpl.fetchCustomerNotifyProfiles(customerId);
		assertEquals(customerNotifyProfiles.get(0).getEventCode(), customerNotifyProfileList.get(0).getEventCode());
		assertEquals(customerNotifyProfiles.get(0).getNotifyProfileId(),
				customerNotifyProfileList.get(0).getNotifyProfileId());
		assertEquals(customerNotifyProfiles.get(0).getNotifyTerminalId(),
				customerNotifyProfileList.get(0).getNotifyTerminalId());
		assertEquals(customerNotifyProfiles.get(0).getCustomer(),
				customerNotifyProfileList.get(0).getCustomer());
		assertEquals(customerNotifyProfiles.get(0).getDayOfWeek(), customerNotifyProfileList.get(0).getDayOfWeek());
		assertEquals(customerNotifyProfiles.get(0).getShift(), customerNotifyProfileList.get(0).getShift());

	}

	@Test
	void testGetAllCustomerNotifyProfilesException() {

		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(serviceImpl.fetchCustomerNotifyProfiles(customerId)));
		assertEquals("No Records found for this customer.", exception.getMessage());
	}

	@Test
	void testInsertCustomerNotifyProfile() {
		when(terminalRepository.existsByTerminalId(Mockito.any())).thenReturn(true);
		when(repository.save(Mockito.any())).thenReturn(customerNotifyProfile);
		when(genericRepository.existsByGenericTableAndGenericTableCode(Mockito.any(), Mockito.any())).thenReturn(true);
		when(customerRepository.existsByCustomerId(Mockito.any())).thenReturn(true);
		CustomerNotifyProfile addedCustomerNotifyProfile = serviceImpl
				.insertCustomerNotifyProfile(customerNotifyProfile, header, notifyTerminalId, customerId);
		assertEquals(addedCustomerNotifyProfile, customerNotifyProfile);
	}

	@Test
	void testUpdateCustomerNotifyProfile() {
		when(repository.existsByNotifyProfileId(Mockito.any())).thenReturn(true);
		when(terminalRepository.existsByTerminalId(Mockito.any())).thenReturn(true);
		when(genericRepository.existsByGenericTableAndGenericTableCode(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(true);
		when(customerRepository.existsByCustomerId(Mockito.any())).thenReturn(true);
		when(repository.findByNotifyProfileId(Mockito.any())).thenReturn(customerNotifyProfile);
		when(repository.save(Mockito.any())).thenReturn(customerNotifyProfile);
		updatedCustomerNotifyProfile = serviceImpl.updateCustomerNotifyProfile(customerNotifyProfileDTO, header);
		updatedCustomerNotifyProfile.setNotifyProfileId(5678L);
		updatedCustomerNotifyProfile.setShift(shifts);
		updatedCustomerNotifyProfile.setDayOfWeek(dayOfWeeks);
		updatedCustomerNotifyProfile.setCustomer(customer);
		assertEquals(updatedCustomerNotifyProfile, customerNotifyProfile);
	}

	@Test
	void testDeleteCustomerNotifyProfile() {
		when(repository.existsByNotifyProfileId(Mockito.any())).thenReturn(true);
		serviceImpl.deleteCustomerNotifyProfiles(customerNotifyProfile);
	}

	@Test
	void testNoRecordsFoundException() throws SQLException {
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(serviceImpl.fetchCustomerNotifyProfiles(customerId)));
		assertEquals("No Records found for this customer.", exception.getMessage());
		NoRecordsFoundException exception1 = assertThrows(NoRecordsFoundException.class, () -> when(
				serviceImpl.insertCustomerNotifyProfile(customerNotifyProfile, header, customerId, notifyTerminalId)));
		assertEquals("No Terminal Found with this terminal id :" + customerId, exception1.getMessage());
		NoRecordsFoundException exception2 = assertThrows(NoRecordsFoundException.class,
				() -> when(serviceImpl.updateCustomerNotifyProfile(customerNotifyProfileDTO, header)));
		assertEquals("No record found for this profile Id : " + notifyProfileId, exception2.getMessage());
		customerNotifyProfile.setNotifyTerminalId(null);
		
	}

	@Test
	void testRecordNotAddedException() {
		when(terminalRepository.existsByTerminalId(Mockito.any())).thenReturn(true);
		when(customerRepository.existsByCustomerId(Mockito.any())).thenReturn(true);
		when(genericRepository.existsByGenericTableAndGenericTableCode(Mockito.any(), Mockito.any())).thenReturn(true);
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class, () -> when(
				serviceImpl.insertCustomerNotifyProfile(customerNotifyProfile, header, customerId, notifyTerminalId)));
		assertEquals("Record Not added to Database", exception.getMessage());
	}

	@Test
	void testRecordAlreadyExistsException() {
		when(terminalRepository.existsByTerminalId(Mockito.any())).thenReturn(true);
		when(customerRepository.existsByCustomerId(Mockito.any())).thenReturn(true);
		when(genericRepository.existsByGenericTableAndGenericTableCode(Mockito.any(), Mockito.any())).thenReturn(true);
		when(repository.existsByNotifyProfileId(Mockito.any())).thenReturn(true);
		RecordAlreadyExistsException exception = assertThrows(RecordAlreadyExistsException.class, () -> when(
				serviceImpl.insertCustomerNotifyProfile(customerNotifyProfile, header, customerId, notifyTerminalId)));
		assertEquals("This customer notify profile already exists with notify profile id: " + notifyProfileId,
				exception.getMessage());
	}

	@Test
	void testInvalidDataException() {
		when(terminalRepository.existsByTerminalId(Mockito.any())).thenReturn(true);
		when(customerRepository.existsByCustomerId(Mockito.any())).thenReturn(true);
		when(repository.existsById(Mockito.any())).thenReturn(true);
		when(genericRepository.existsByGenericTableAndGenericTableCode(Mockito.any(), Mockito.any())).thenReturn(false);
		InvalidDataException exception = assertThrows(InvalidDataException.class, () -> when(
				serviceImpl.insertCustomerNotifyProfile(customerNotifyProfile, header, customerId, notifyTerminalId)));
		assertEquals("Invalid Event Code", exception.getMessage());
		customerNotifyProfile.setEventCode(null);
		InvalidDataException exception1 = assertThrows(InvalidDataException.class, () -> when(
				serviceImpl.insertCustomerNotifyProfile(customerNotifyProfile, header, customerId, notifyTerminalId)));
		assertEquals("Event code should not be null", exception1.getMessage());
		customerNotifyProfile.setEventCode("SEAL");
		customerNotifyProfileDTO.setNotifyProfileId(null);
		InvalidDataException exception2 = assertThrows(InvalidDataException.class,
				() -> when(serviceImpl.updateCustomerNotifyProfile(customerNotifyProfileDTO, header)));
		customerNotifyProfileDTO.setNotifyProfileId(notifyProfileId);
		assertEquals("Notify Profile Id should not be null", exception2.getMessage());
		when(repository.existsByNotifyProfileId(Mockito.any())).thenReturn(true);
		when(repository.findByNotifyProfileId(Mockito.any())).thenReturn(customerNotifyProfile);
		InvalidDataException exception3 = assertThrows(InvalidDataException.class,
				() -> when(serviceImpl.updateCustomerNotifyProfile(customerNotifyProfileDTO, header)));
		assertEquals("Invalid Event Code", exception3.getMessage());
		customerNotifyProfileDTO.setEventCode(null);
		InvalidDataException exception4 = assertThrows(InvalidDataException.class,
				() -> when(serviceImpl.updateCustomerNotifyProfile(customerNotifyProfileDTO, header)));
		assertEquals("Event code should not be null", exception4.getMessage());
		customerNotifyProfileDTO.setEventCode("SEAL");
		notifyProfileMethodDTO.setNotifyMethodId(1234L);
		notifyProfileMethodDTOList.add(notifyProfileMethodDTO);
		customer.setDeliveryDetail(null);
		when(customerRepository.findByCustomerId(Mockito.any())).thenReturn(customer);
		when(genericRepository.existsByGenericTableAndGenericTableCode(Mockito.any(), Mockito.any())).thenReturn(true);
		customerNotifyProfileDTO.setNotifyProfileMethods(notifyProfileMethodDTOList);
		InvalidDataException exception5 = assertThrows(InvalidDataException.class,
				() -> when(serviceImpl.updateCustomerNotifyProfile(customerNotifyProfileDTO, header)));
		assertEquals("Notification Name should not be null", exception5.getMessage());
		notifyProfileMethodDTOList = new ArrayList<NotifyProfileMethodDTO>();
		notifyProfileMethodDTO.setNotificationName("TEST");
		notifyProfileMethodDTO.setNotificationMethod(NotificationMethod.EMAIL);
		notifyProfileMethodDTOList.add(notifyProfileMethodDTO);
		customerNotifyProfileDTO.setNotifyProfileMethods(notifyProfileMethodDTOList);
		InvalidDataException exception6 = assertThrows(InvalidDataException.class,
				() -> when(serviceImpl.updateCustomerNotifyProfile(customerNotifyProfileDTO, header)));
		assertEquals("Must Specify Notification Order", exception6.getMessage());
		notifyProfileMethodDTOList = new ArrayList<NotifyProfileMethodDTO>();
		notifyProfileMethodDTO.setNotificationMethod(null);
		notifyProfileMethodDTOList.add(notifyProfileMethodDTO);
		customerNotifyProfileDTO.setNotifyProfileMethods(notifyProfileMethodDTOList);
		InvalidDataException exception7 = assertThrows(InvalidDataException.class,
				() -> when(serviceImpl.updateCustomerNotifyProfile(customerNotifyProfileDTO, header)));
		assertEquals("Must Specify Notification Method.", exception7.getMessage());
		notifyProfileMethodDTOList = new ArrayList<NotifyProfileMethodDTO>();
		notifyProfileMethodDTO.setNotificationMethod(NotificationMethod.EDI);
		notifyProfileMethodDTOList.add(notifyProfileMethodDTO);
		customerNotifyProfileDTO.setNotifyProfileMethods(notifyProfileMethodDTOList);
		InvalidDataException exception8 = assertThrows(InvalidDataException.class,
				() -> when(serviceImpl.updateCustomerNotifyProfile(customerNotifyProfileDTO, header)));
		assertEquals("Must Specify Notification Order", exception8.getMessage());
		notifyProfileMethodDTOList = new ArrayList<NotifyProfileMethodDTO>();
		notifyProfileMethodDTO.setNotificationOrder(NotificationOrder.SECONDARY);
		notifyProfileMethodDTOList.add(notifyProfileMethodDTO);
		customerNotifyProfileDTO.setNotifyProfileMethods(notifyProfileMethodDTOList);
		InvalidDataException exception9 = assertThrows(InvalidDataException.class,
				() -> when(serviceImpl.updateCustomerNotifyProfile(customerNotifyProfileDTO, header)));
		assertEquals("Must Specify Notification Type.", exception9.getMessage());
		notifyProfileMethodDTOList = new ArrayList<NotifyProfileMethodDTO>();
		notifyProfileMethodDTO.setNotificationType(NotificationType.DELAY_SUMMARY);
		notifyProfileMethodDTO.setEdiBox(null);
		notifyProfileMethodDTOList.add(notifyProfileMethodDTO);
		customerNotifyProfileDTO.setNotifyProfileMethods(notifyProfileMethodDTOList);
		InvalidDataException exception10 = assertThrows(InvalidDataException.class,
				() -> when(serviceImpl.updateCustomerNotifyProfile(customerNotifyProfileDTO, header)));
		assertEquals("EDI Box is required field for Notification Method EDI", exception10.getMessage());
		customerNotifyProfileDTO.setEventCode("RMFC");
		InvalidDataException exception11 = assertThrows(InvalidDataException.class,
				() -> when(serviceImpl.updateCustomerNotifyProfile(customerNotifyProfileDTO, header)));
		assertEquals("EDI Box is required field for Notification Method EDI", exception11.getMessage());
		notifyProfileMethodDTOList = new ArrayList<NotifyProfileMethodDTO>();
		notifyProfileMethodDTO.setEdiBox("TEST");
		notifyProfileMethodDTOList.add(notifyProfileMethodDTO);
		customerNotifyProfileDTO.setNotifyProfileMethods(notifyProfileMethodDTOList);
		InvalidDataException exception12 = assertThrows(InvalidDataException.class,
				() -> when(serviceImpl.updateCustomerNotifyProfile(customerNotifyProfileDTO, header)));
		assertEquals("Notification Type not valid for Method EDI.", exception12.getMessage());
		notifyProfileMethodDTOList = new ArrayList<NotifyProfileMethodDTO>();
		notifyProfileMethodDTO.setNotificationType(NotificationType.DEFERRED_DETAIL);
		notifyProfileMethodDTOList.add(notifyProfileMethodDTO);
		customerNotifyProfileDTO.setNotifyProfileMethods(notifyProfileMethodDTOList);
		when(genericRepository.existsByGenericTableAndGenericTableCode("EDI_RSN",
				customerNotifyProfileDTO.getEventCode())).thenReturn(false);
		InvalidDataException exception13 = assertThrows(InvalidDataException.class,
				() -> when(serviceImpl.updateCustomerNotifyProfile(customerNotifyProfileDTO, header)));
		assertEquals("Notification Method EDI not valid for event code " + customerNotifyProfileDTO.getEventCode(),
				exception13.getMessage());
		notifyProfileMethodDTOList = new ArrayList<NotifyProfileMethodDTO>();
		notifyProfileMethodDTO.setNotificationMethod(NotificationMethod.FAX);
		notifyProfileMethodDTOList.add(notifyProfileMethodDTO);
		customerNotifyProfileDTO.setNotifyProfileMethods(notifyProfileMethodDTOList);
		when(genericRepository.existsByGenericTableAndGenericTableCode("EDI_RSN",
				customerNotifyProfileDTO.getEventCode())).thenReturn(true);
		InvalidDataException exception14 = assertThrows(InvalidDataException.class,
				() -> when(serviceImpl.updateCustomerNotifyProfile(customerNotifyProfileDTO, header)));
		assertEquals("Notify Method must be EDI before entering EDI box fields", exception14.getMessage());
		notifyProfileMethodDTOList = new ArrayList<NotifyProfileMethodDTO>();
		notifyProfileMethodDTO.setEdiBox(null);
		notifyProfileMethodDTOList.add(notifyProfileMethodDTO);
		customerNotifyProfileDTO.setNotifyProfileMethods(notifyProfileMethodDTOList);
		customerNotifyProfileDTO.setEventCode("RMFC");
		InvalidDataException exception15 = assertThrows(InvalidDataException.class,
				() -> when(serviceImpl.updateCustomerNotifyProfile(customerNotifyProfileDTO, header)));
		assertEquals(
				"Change notificationtype , Event code should not be RMFC for notificationType Deferred Details or Deferred Summary.",
				exception15.getMessage());
		notifyProfileMethodDTOList = new ArrayList<NotifyProfileMethodDTO>();
		notifyProfileMethodDTO.setNotificationType(NotificationType.DELAY_DETAIL);
		notifyProfileMethodDTO.setNotificationOrder(NotificationOrder.PRIMARY);
		notifyProfileMethodDTOList.add(notifyProfileMethodDTO);
		customerNotifyProfileDTO.setNotifyProfileMethods(notifyProfileMethodDTOList);
		customerNotifyProfileDTO.setEventCode("SEAL");
		InvalidDataException exception16 = assertThrows(InvalidDataException.class,
				() -> when(serviceImpl.updateCustomerNotifyProfile(customerNotifyProfileDTO, header)));
		assertEquals(
				"Change notificationtype , Event code should be RMFC or IRFC for notificationType Delay Details or Delay Summary.",
				exception16.getMessage());
		customerNotifyProfileDTO.setEventCode("RMFC");
		InvalidDataException exception17 = assertThrows(InvalidDataException.class,
				() -> when(serviceImpl.updateCustomerNotifyProfile(customerNotifyProfileDTO, header)));
		assertEquals("Must Enter Telephone No", exception17.getMessage());
		customerNotifyProfile.setNotifyProfileId(null);
		InvalidDataException exception18 = assertThrows(InvalidDataException.class,
				() -> serviceImpl.deleteCustomerNotifyProfiles(customerNotifyProfile));
		assertEquals("Notify Profile Id should not be null", exception18.getMessage());
		customerNotifyProfileDTO.setEventCode("NTFY");
		InvalidDataException exception19 = assertThrows(InvalidDataException.class,
				() -> when(serviceImpl.updateCustomerNotifyProfile(customerNotifyProfileDTO, header)));
		assertEquals("NTFY Event not valid, use RMFC", exception19.getMessage());
		notifyProfileMethodDTO.setNotificationType(null);
		notifyProfileMethodDTO.setNotificationMethod(null);
		InvalidDataException exception22 = assertThrows(InvalidDataException.class,
				() -> when(serviceImpl.updateCustomerNotifyProfile(customerNotifyProfileDTO, header)));
		assertEquals("NTFY Event not valid, use RMFC", exception22.getMessage());
		notifyProfileMethodDTO.setNotificationOrder(NotificationOrder.SECONDARY);
		customerNotifyProfileDTO.setEventCode("SEAL");
		InvalidDataException exception21 = assertThrows(InvalidDataException.class,
				() -> when(serviceImpl.updateCustomerNotifyProfile(customerNotifyProfileDTO, header)));
		assertEquals("Must Specify Notification Method.", exception21.getMessage());
		customerNotifyProfile.setEventCode("NTFY");
		InvalidDataException exception20 = assertThrows(InvalidDataException.class, () -> when(
				serviceImpl.insertCustomerNotifyProfile(customerNotifyProfile, header, notifyTerminalId, customerId)));
		assertEquals("NTFY event code is Invalid, use RMFC", exception20.getMessage());
		notifyProfileMethodDTO.setNotificationMethod(NotificationMethod.FAX);
		notifyProfileMethodDTO.setMicrowaveIndicator(null);
		notifyProfileMethodDTO.setNotificationName("TEST");
		notifyProfileMethodDTO.setNotificationOrder(NotificationOrder.PRIMARY);
		customerNotifyProfileDTO.setEventCode("RMFC");
		notifyProfileMethodDTO.setNotifyAreaCode("123");
		notifyProfileMethodDTO.setNotifyPrefix("111");
		notifyProfileMethodDTO.setNotifySuffix("222");
		notifyProfileMethodDTO.setNotificationType(NotificationType.IMMEDIATE);
		InvalidDataException exception23 = assertThrows(InvalidDataException.class,
				() -> when(serviceImpl.updateCustomerNotifyProfile(customerNotifyProfileDTO, header)));
		assertEquals("Must Enter MicrowaveIndicator Y or N", exception23.getMessage());
		notifyProfileMethodDTO.setNotificationType(null);
		InvalidDataException exception24 = assertThrows(InvalidDataException.class,
				() -> when(serviceImpl.updateCustomerNotifyProfile(customerNotifyProfileDTO, header)));
		assertEquals("Must Specify Notification Type.", exception24.getMessage());
		notifyProfileMethodDTO.setNotificationMethod(NotificationMethod.EDI);
		notifyProfileMethodDTO.setNotificationType(NotificationType.IMMEDIATE);
		InvalidDataException exception25 = assertThrows(InvalidDataException.class,
				() -> when(serviceImpl.updateCustomerNotifyProfile(customerNotifyProfileDTO, header)));
		assertEquals("EDI Box is required field for Notification Method EDI", exception25.getMessage());
		notifyProfileMethodDTO.setNotificationMethod(NotificationMethod.FAX);
		notifyProfileMethodDTO.setEdiBox("TEST");
		InvalidDataException exception26 = assertThrows(InvalidDataException.class,
				() -> when(serviceImpl.updateCustomerNotifyProfile(customerNotifyProfileDTO, header)));
		assertEquals("Notify Method must be EDI before entering EDI box fields", exception26.getMessage());
		notifyProfileMethodDTO.setNotificationType(NotificationType.DEFERRED_DETAIL);
		customerNotifyProfileDTO.setEventCode("RMFC");
		notifyProfileMethodDTO.setNotificationMethod(NotificationMethod.EDI);
		InvalidDataException exception27 = assertThrows(InvalidDataException.class,
				() -> when(serviceImpl.updateCustomerNotifyProfile(customerNotifyProfileDTO, header)));
		assertEquals(
				"Change notificationtype , Event code should not be RMFC for notificationType Deferred Details or Deferred Summary.",
				exception27.getMessage());
		notifyProfileMethodDTO.setNotificationOrder(NotificationOrder.SECONDARY);
		customerNotifyProfileDTO.setEventCode("SEAL");
		InvalidDataException exception28 = assertThrows(InvalidDataException.class,
				() -> when(serviceImpl.updateCustomerNotifyProfile(customerNotifyProfileDTO, header)));
		assertEquals("Secondary Notification order is only valid for event code RMFC or RCOV", exception28.getMessage());
		notifyProfileMethodDTO.setAutoRenotify("Y");
		customerNotifyProfileDTO.setEventCode("IRFC");
		notifyProfileMethodDTO.setNotificationMethod(NotificationMethod.EMAIL);
		notifyProfileMethodDTO.setEdiBox(null);
		InvalidDataException exception29 = assertThrows(InvalidDataException.class,
				() -> when(serviceImpl.updateCustomerNotifyProfile(customerNotifyProfileDTO, header)));
		assertEquals("Secondary Notification order is only valid for event code RMFC or RCOV", exception29.getMessage());
		
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
		customerNotifyProfileDTO.setNotifyProfileMethods(notifyProfileMethodDTOList);

		when(repository.existsByNotifyProfileId(Mockito.any())).thenReturn(true);
		when(repository.findByNotifyProfileId(Mockito.any())).thenReturn(customerNotifyProfile);
		when(genericRepository.existsByGenericTableAndGenericTableCode(Mockito.any(), Mockito.any())).thenReturn(true);
		when(terminalRepository.existsByTerminalId(Mockito.any())).thenReturn(true);
		when(notifyProfileMethodMapper.notifyProfileMethodToNotifyProfileMethodDTO(Mockito.any()))
				.thenReturn(notifyProfileMethodDTO);
		when(customerRepository.existsByCustomerId(Mockito.any())).thenReturn(true);
		when(notifyProfileMethodMapper.notifyProfileMethodDTOToNotifyProfileMethod(Mockito.any()))
				.thenReturn(notifyProfileMethod);
		CustomerNotifyProfile response = serviceImpl.updateCustomerNotifyProfile(customerNotifyProfileDTO, header);
	}

	@Test
	void testRecordNotDeletedException() {
		when(repository.existsByNotifyProfileId(notifyProfileId)).thenReturn(false);
		RecordNotDeletedException exception = assertThrows(RecordNotDeletedException.class,
				() -> serviceImpl.deleteCustomerNotifyProfiles(customerNotifyProfile));
		assertEquals(notifyProfileId + " Record Not Found!", exception.getMessage());
	}

}
