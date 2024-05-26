package com.nscorp.obis.controller;

import com.nscorp.obis.domain.Customer;
import com.nscorp.obis.domain.CustomerNotifyProfile;
import com.nscorp.obis.domain.DeliveryDetail;
import com.nscorp.obis.dto.CustomerDTO;
import com.nscorp.obis.dto.CustomerNotifyProfileDTO;
import com.nscorp.obis.dto.DeliveryDetailDTO;
import com.nscorp.obis.dto.mapper.CustomerNotifyProfileMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.SizeExceedException;
import com.nscorp.obis.repository.CustomerNotifyProfileRepository;
import com.nscorp.obis.repository.CustomerRepository;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.CustomerNotifyProfileService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

//@SpringBootTest
class CustomerNotifyProfileControllerTest {
	@Mock
	CustomerNotifyProfileService service;

	@Mock
	CustomerNotifyProfileMapper mapper;

	@Mock
	CustomerRepository customerRepository;

	@Mock
	CustomerNotifyProfileRepository repository;

	@InjectMocks
	CustomerNotifyProfileController controller;

	CustomerNotifyProfile customerNotifyProfile;
	CustomerNotifyProfileDTO customerNotifyProfileDto;
	List<CustomerNotifyProfile> customerNotifyProfileList;
	List<CustomerNotifyProfileDTO> customerNotifyProfileDtoList;
	Customer customer;
	CustomerDTO customerDTO;
	DeliveryDetail deliveryDetail;
	DeliveryDetailDTO deliveryDetailDTO;
	List<APIResponse<CustomerNotifyProfile>> responseObjectList = new ArrayList<APIResponse<CustomerNotifyProfile>>();
	Map<String, String> header;
	Long customerId;
	Long notifyTerminalId;

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
		customerNotifyProfileDto = new CustomerNotifyProfileDTO();
		customerNotifyProfileList = new ArrayList<>();
		customerNotifyProfileDtoList = new ArrayList<>();
		customerId = 6789L;
		notifyTerminalId = 1200L;
		customerNotifyProfile.setNotifyProfileId(6788L);
		customerNotifyProfile.setNotifyTerminalId(5678L);
		customerNotifyProfile.setEventCode("SEAL");
		customerNotifyProfileDto.setNotifyProfileId(6788L);
		customerNotifyProfileDto.setNotifyTerminalId(5678L);
		customerNotifyProfileDto.setEventCode("SEAL");
		customerNotifyProfileList.add(customerNotifyProfile);
		customerNotifyProfileDtoList.add(customerNotifyProfileDto);
		responseObjectList.add(new APIResponse<CustomerNotifyProfile>(Arrays.asList("Records Deleted Successfully!"),
				ResponseStatusCode.SUCCESS.getStatusCode(), ResponseStatusCode.SUCCESS.toString()));
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
	}

	@AfterEach
	void tearDown() throws Exception {
		customerNotifyProfile = null;
		customerNotifyProfileDto = null;
		customerNotifyProfileList = null;
		customerNotifyProfileDtoList = null;
	}

	@Test
	void testGetCustomerNotifyProfiles() throws SQLException {
		when(service.fetchCustomerNotifyProfiles(Mockito.any())).thenReturn(customerNotifyProfileList);
		ResponseEntity<APIResponse<List<CustomerNotifyProfileDTO>>> customerProfiles = controller
				.getCustomerNotifyProfiles(customerId);
		assertEquals(customerProfiles.getStatusCodeValue(), 200);
	}

	@Test
	void testAddCustomerNotifyProfile() {
		when(mapper.customerNotifyProfileToCustomerNotifyProfileDto(Mockito.any()))
				.thenReturn(customerNotifyProfileDto);
		when(mapper.customerNotifyProfileDtoToCustomerNotifyProfile(Mockito.any())).thenReturn(customerNotifyProfile);
		when(service.insertCustomerNotifyProfile(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
				.thenReturn(customerNotifyProfile);
		ResponseEntity<APIResponse<CustomerNotifyProfileDTO>> addedCustomerNotifyProfile = controller
				.addCustomerNotifyProfile(customerNotifyProfileDto, notifyTerminalId, customerId, header);
		assertEquals(addedCustomerNotifyProfile.getStatusCodeValue(), 200);
	}

	@Test
	void testDeleteCustomerNotifyProfile() {
		
		ResponseEntity<List<APIResponse<CustomerNotifyProfileDTO>>> response = controller
				.deleteCustomerNotifyProfile(customerNotifyProfileDtoList);
		assertEquals(response.getStatusCodeValue(), 200);
		customerNotifyProfileList.remove(customerNotifyProfile);
		ResponseEntity<List<APIResponse<CustomerNotifyProfileDTO>>> response2 = controller
				.deleteCustomerNotifyProfile(null);
		assertEquals(response2.getStatusCodeValue(), 120);
	}

	@Test
	void testUpdateCustomerNotifyProfile() throws SQLException {
		when(customerRepository.existsByCustomerId(Mockito.any())).thenReturn(true);
		customer.setCustomerId(customerId);
		customer.setDeliveryDetail(deliveryDetail);
		customerNotifyProfile.setCustomer(customer);
		customerNotifyProfileList = new ArrayList<>();
		customerNotifyProfileList.add(customerNotifyProfile);

		deliveryDetailDTO = null;
		when(repository.findByCustomer_CustomerId(customerId)).thenReturn(customerNotifyProfileList);
		when(service.updateCustomerNotifyProfile(Mockito.any(), Mockito.any())).thenReturn(customerNotifyProfile);
		ResponseEntity<APIResponse<CustomerNotifyProfileDTO>> codeUpdated = controller
				.updateCustomerNotifyProfile(customerNotifyProfileDto, header);
		assertEquals(codeUpdated.getStatusCodeValue(), 200);
	}

	@Test
	void testNoRecordsFoundException() throws SQLException {
		when(mapper.customerNotifyProfileToCustomerNotifyProfileDto(Mockito.any()))
				.thenReturn(customerNotifyProfileDto);
		when(mapper.customerNotifyProfileDtoToCustomerNotifyProfile(Mockito.any())).thenReturn(customerNotifyProfile);
		when(service.fetchCustomerNotifyProfiles(Mockito.any())).thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<List<CustomerNotifyProfileDTO>>> response = controller
				.getCustomerNotifyProfiles(customerId);
		assertEquals(response.getStatusCodeValue(), 404);
		when(service.insertCustomerNotifyProfile(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
				.thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<CustomerNotifyProfileDTO>> response1 = controller
				.addCustomerNotifyProfile(customerNotifyProfileDto, notifyTerminalId, customerId, header);
		assertEquals(response1.getStatusCodeValue(), 404);
		when(service.updateCustomerNotifyProfile(Mockito.any(), Mockito.any()))
				.thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<CustomerNotifyProfileDTO>> response2 = controller
				.updateCustomerNotifyProfile(customerNotifyProfileDto, header);
		assertEquals(response2.getStatusCodeValue(), 404);
	}

	@Test
	@DisplayName("NullPointerException")
	void testNullPointerException() throws SQLException {
		when(service.updateCustomerNotifyProfile(Mockito.any(), Mockito.any())).thenThrow(new NullPointerException());
		ResponseEntity<APIResponse<CustomerNotifyProfileDTO>> response = controller
				.updateCustomerNotifyProfile(customerNotifyProfileDto, header);
		assertEquals(response.getStatusCodeValue(), 400);
	}

	@Test
	@DisplayName("Internal Server Error")
	void testException() throws SQLException {
		when(mapper.customerNotifyProfileToCustomerNotifyProfileDto(Mockito.any()))
				.thenReturn(customerNotifyProfileDto);
		when(mapper.customerNotifyProfileDtoToCustomerNotifyProfile(Mockito.any())).thenReturn(customerNotifyProfile);
		when(service.fetchCustomerNotifyProfiles(Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<List<CustomerNotifyProfileDTO>>> response = controller
				.getCustomerNotifyProfiles(customerId);
		assertEquals(response.getStatusCodeValue(), 500);
		when(service.insertCustomerNotifyProfile(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
				.thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<CustomerNotifyProfileDTO>> response1 = controller
				.addCustomerNotifyProfile(customerNotifyProfileDto, notifyTerminalId, customerId, header);
		assertEquals(response1.getStatusCodeValue(), 500);
		when(service.updateCustomerNotifyProfile(Mockito.any(), Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<CustomerNotifyProfileDTO>> response2 = controller
				.updateCustomerNotifyProfile(customerNotifyProfileDto, header);
		assertEquals(response2.getStatusCodeValue(), 500);
		ResponseEntity<List<APIResponse<CustomerNotifyProfileDTO>>> response3 = controller
				.deleteCustomerNotifyProfile(customerNotifyProfileDtoList);
		assertEquals(response3.getStatusCodeValue(), 500);
	}

	@Test
	void testRecordAlreadyExistsException() throws SQLException {
		when(mapper.customerNotifyProfileToCustomerNotifyProfileDto(Mockito.any()))
				.thenReturn(customerNotifyProfileDto);
		when(mapper.customerNotifyProfileDtoToCustomerNotifyProfile(Mockito.any())).thenReturn(customerNotifyProfile);
		when(service.insertCustomerNotifyProfile(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
				.thenThrow(new RecordAlreadyExistsException());
		ResponseEntity<APIResponse<CustomerNotifyProfileDTO>> response = controller
				.addCustomerNotifyProfile(customerNotifyProfileDto, notifyTerminalId, customerId, header);
		assertEquals(response.getStatusCodeValue(), 208);

	}

	@Test
	void testSizeExceedException() throws SQLException {
		when(mapper.customerNotifyProfileToCustomerNotifyProfileDto(Mockito.any()))
				.thenReturn(customerNotifyProfileDto);
		when(mapper.customerNotifyProfileDtoToCustomerNotifyProfile(Mockito.any())).thenReturn(customerNotifyProfile);
		when(service.insertCustomerNotifyProfile(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
				.thenThrow(new SizeExceedException());
		ResponseEntity<APIResponse<CustomerNotifyProfileDTO>> response = controller
				.addCustomerNotifyProfile(customerNotifyProfileDto, notifyTerminalId, customerId, header);
		assertEquals(response.getStatusCodeValue(), 411);
		when(service.updateCustomerNotifyProfile(Mockito.any(), Mockito.any())).thenThrow(new SizeExceedException());
		ResponseEntity<APIResponse<CustomerNotifyProfileDTO>> response1 = controller
				.updateCustomerNotifyProfile(customerNotifyProfileDto, header);
		assertEquals(response1.getStatusCodeValue(), 411);
		when(service.fetchCustomerNotifyProfiles(Mockito.any())).thenReturn(customerNotifyProfileList);
		ResponseEntity<APIResponse<List<CustomerNotifyProfileDTO>>> response2 = controller
				.getCustomerNotifyProfiles(customerId);
		assertEquals(response2.getStatusCodeValue(), 411);
	}

	@Test
	void testRecordNotAddedException() throws SQLException {
		when(mapper.customerNotifyProfileToCustomerNotifyProfileDto(Mockito.any()))
				.thenReturn(customerNotifyProfileDto);
		when(mapper.customerNotifyProfileDtoToCustomerNotifyProfile(Mockito.any())).thenReturn(customerNotifyProfile);
		when(service.insertCustomerNotifyProfile(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
				.thenThrow(new RecordNotAddedException());
		ResponseEntity<APIResponse<CustomerNotifyProfileDTO>> response = controller
				.addCustomerNotifyProfile(customerNotifyProfileDto, notifyTerminalId, customerId, header);
		assertEquals(response.getStatusCodeValue(), 406);

	}

}
