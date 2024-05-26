package com.nscorp.obis.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.domain.CustomerIndex;
import com.nscorp.obis.domain.CustomerNickname;
import com.nscorp.obis.domain.CustomerNotifyProfile;
import com.nscorp.obis.domain.DayOfWeek;
import com.nscorp.obis.domain.NorfolkSouthernEventLog;
import com.nscorp.obis.domain.NotifyQueue;
import com.nscorp.obis.domain.NotifyQueueRetry;
import com.nscorp.obis.domain.ReNotifyView;
import com.nscorp.obis.domain.Shift;
import com.nscorp.obis.domain.Shipment;
import com.nscorp.obis.dto.NorfolkSouthernEventLogDTO;
import com.nscorp.obis.dto.NotifyQueueDTO;
import com.nscorp.obis.dto.NotifyQueueRetryDTO;
import com.nscorp.obis.dto.NotifyQueueUpdatedDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.repository.CustomerIndexRepository;
import com.nscorp.obis.repository.CustomerNicknameRepository;
import com.nscorp.obis.repository.CustomerNotifyProfileRepository;
import com.nscorp.obis.repository.NorfolkSouthernEventLogRepository;
import com.nscorp.obis.repository.NotifyQueueRepository;
import com.nscorp.obis.repository.ReNotifyViewRepository;
import com.nscorp.obis.repository.ShipmentRepository;

class NotifyQueueServiceTest {

	@Mock
	NotifyQueueRepository notifyQueueRepository;

	@Mock
	NorfolkSouthernEventLogRepository nsEventRepo;

	@Mock
	ReNotifyViewRepository reNotifyViewRepository;

	@Mock
	ShipmentRepository shipmentRepository;
	@Mock
	CustomerIndexRepository customerIndexRepository;
	@Mock
	CustomerNotifyProfileRepository customerNotifyProfileRepository;
	@Mock
	CustomerNicknameRepository customerNicknameRepository;

	@InjectMocks
	NotifyQueueServiceImpl notifyQueueServiceImpl;

	NotifyQueue notifyQueue;
	NorfolkSouthernEventLog nsEventLog;
	NotifyQueueRetry notifyQueueRetry;
	List<NotifyQueueRetry> notifyQueueRetryList;
	List<NotifyQueue> notifyQueueList;
	List<ReNotifyView> reNotifyViewList;
	ReNotifyView reNotifyView;
	NorfolkSouthernEventLogDTO nsEventLogDto;
	List<NotifyQueueDTO> notifyQueueDtoList;
	NotifyQueueDTO notifyQueueDTO;
	NotifyQueueRetryDTO notifyQueueRetryDto;
	Shipment shipment;
	BigDecimal eqNumber = new BigDecimal(97304);
	Map<String, String> headers;
	NotifyQueueUpdatedDTO notifyQueueUpdatedDTO;
	Long longValue = 1000L;
	int intValue = 800;
	CustomerNotifyProfile customerNotifyProfile;
	List<CustomerNotifyProfile> customerNotifyProfileList;
	CustomerIndex customerIndex;
	CustomerNickname customerNickname;
	List<DayOfWeek> dayList;
	List<Shift> shiftList;

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
		reNotifyView = new ReNotifyView();
		notifyQueueRetry = new NotifyQueueRetry();
		notifyQueueRetryList = new ArrayList<>();
		nsEventLog = new NorfolkSouthernEventLog();
		nsEventLogDto = new NorfolkSouthernEventLogDTO();
		notifyQueueRetryDto = new NotifyQueueRetryDTO();

		reNotifyView.setNtfyQueueId(123L);
		reNotifyView.setRenotifyCnt(1);
		reNotifyViewList = new ArrayList<>();
		reNotifyViewList.add(reNotifyView);

		notifyQueueUpdatedDTO = new NotifyQueueUpdatedDTO();

		notifyQueueUpdatedDTO.setFlag(false);
		notifyQueueUpdatedDTO.setTermId(123L);
		notifyQueueUpdatedDTO.setCustomerName("ABC");

		notifyQueue = new NotifyQueue();
		notifyQueueList = new ArrayList<>();
		notifyQueueList.add(notifyQueue);

		notifyQueueDTO = new NotifyQueueDTO();
		notifyQueueDtoList = new ArrayList<>();
		notifyQueueDTO.setNtfyQueueId(10185038621092L);
		notifyQueueDTO.setRenotifyCnt(0);
		notifyQueueDTO.setNotifyStat("CONF");
		notifyQueueDtoList.add(notifyQueueDTO);
		notifyQueueUpdatedDTO.setNotifyQueueObjDto(notifyQueueDtoList);

		headers = new HashMap<String, String>();
		headers.put("userid", "Test");
		headers.put("extensionschema", "Test");

		notifyQueue.setEvtLogId(longValue);
		nsEventLog.setServiceId(longValue);
		nsEventLog.setTerminalId(longValue);
		LocalDateTime now = LocalDateTime.now();
		nsEventLog.setLclDateTime(Timestamp.valueOf(now));
		nsEventLog.setEvtCd("NTFY");
		;
		shipment = new Shipment();
		shipment.setShipStat("s");
		shipment.setNtfyOvrdAreaCd(intValue);
		shipment.setNtfyOvrdBase(intValue);
		shipment.setNtfyOvrdExch(intValue);
		shipment.setCustomerToNotify(longValue);

		customerNotifyProfile = new CustomerNotifyProfile();
		customerNotifyProfileList = new ArrayList<>();
		customerIndex = new CustomerIndex();
		customerNickname = new CustomerNickname();

		customerIndex.setCustomerId(longValue);
		DayOfWeek asList = DayOfWeek.of(1);
		Shift shift = Shift.getShiftByValue(1);
		dayList = new ArrayList<>();
		shiftList = new ArrayList<>();
		dayList.add(asList);
		shiftList.add(shift);
		customerNotifyProfile.setDayOfWeek(dayList);
		customerNotifyProfile.setShift(shiftList);
		customerNotifyProfileList.add(customerNotifyProfile);
	}

	@ParameterizedTest
	@ValueSource(strings = { "!", "", " " })
	void testUpdateNotifyQueue(String uVersion) {
		notifyQueue.setUversion(uVersion);
		when(notifyQueueRepository.findByNtfyQueueId(Mockito.any())).thenReturn(notifyQueue);
		when(notifyQueueRepository.findByNtfyQueueId(Mockito.any())).thenReturn(notifyQueue);
		when(notifyQueueRepository.save(Mockito.any())).thenReturn(notifyQueue);
		List<NotifyQueue> result = notifyQueueServiceImpl.updateNotifyQueue(notifyQueueUpdatedDTO, headers);

		notifyQueueUpdatedDTO.setFlag(true);
		when(reNotifyViewRepository.searchAllByTermIdAndCustomerName(notifyQueueUpdatedDTO.getTermId(),
				notifyQueueUpdatedDTO.getCustomerName())).thenReturn(reNotifyViewList);
		result = notifyQueueServiceImpl.updateNotifyQueue(notifyQueueUpdatedDTO, headers);

	}

	@Test
	void testUpdateNotifyQueueRetry() {
		nsEventLog.setEquipmentInit("MSKU");
		nsEventLog.setEvtCd("RMFC");
		nsEventLog.setEquipmentId("0");
		nsEventLog.setEquipmentType("T");
		nsEventLog.setEquipmentNumber(eqNumber);

		notifyQueueRetry.setNorfolkSouthernEventLog(nsEventLog);
		notifyQueueRetry.setNotifyQueue(notifyQueue);
		notifyQueueRetryList.add(notifyQueueRetry);

		when(notifyQueueRepository.existsByEvtLogId(Mockito.any())).thenReturn(true);
		when(nsEventRepo.findByEventLogId(Mockito.any())).thenReturn(nsEventLog);
		when(notifyQueueRepository.findByEvtLogId(Mockito.any())).thenReturn(notifyQueueList);
		NotifyQueueRetry result1 = notifyQueueServiceImpl.updateNotifyQueueRetry(notifyQueueRetry, headers);
		assertEquals(result1, notifyQueueRetry);
	}

	@Test
	void testAddTermIdNullPointerException() {
		headers.put("extensionschema", null);
		assertNull(headers.get(CommonConstants.EXTENSION_SCHEMA));
		when(notifyQueueRepository.findByNtfyQueueId(Mockito.any())).thenReturn(notifyQueue);
		NullPointerException exception = assertThrows(NullPointerException.class,
				() -> notifyQueueServiceImpl.updateNotifyQueue(notifyQueueUpdatedDTO, headers));
		assertEquals("Extension Schema should not be null, empty or blank", exception.getMessage());
	}

	@Test
	void testUpdateNotifyQueueRetryNoRecordsFoundException() {
		nsEventLog.setEquipmentInit("MSKU");
		nsEventLog.setEvtCd("RMFC");
		nsEventLog.setEquipmentId("0");
		nsEventLog.setEquipmentType("T");
		nsEventLog.setEquipmentNumber(eqNumber);

		notifyQueueRetry.setNorfolkSouthernEventLog(nsEventLog);
		notifyQueueRetry.setNotifyQueue(notifyQueue);
		notifyQueueRetryList.add(notifyQueueRetry);

		when(notifyQueueRepository.existsByEvtLogId(Mockito.any())).thenReturn(false);
		when(nsEventRepo.findByEventLogId(Mockito.any())).thenReturn(nsEventLog);
		when(notifyQueueRepository.findByEvtLogId(Mockito.any())).thenReturn(notifyQueueList);
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(notifyQueueServiceImpl.updateNotifyQueueRetry(notifyQueueRetry, headers)));
		assertEquals("Record with Event Log Id: null not Found!", exception.getMessage());
	}

	@Test
	void testUpdateNotifyetryNoRecordsFoundException() {
		nsEventLog.setEquipmentInit("MSKU");
		nsEventLog.setEvtCd("RMUC");
		nsEventLog.setEquipmentId("0");
		nsEventLog.setEquipmentType("T");
		nsEventLog.setEquipmentNumber(eqNumber);

		notifyQueueRetry.setNorfolkSouthernEventLog(nsEventLog);
		notifyQueueRetry.setNotifyQueue(notifyQueue);
		notifyQueueRetryList.add(notifyQueueRetry);
		when(notifyQueueRepository.existsByEvtLogId(Mockito.any())).thenReturn(true);
		when(nsEventRepo.findByEventLogId(Mockito.any())).thenReturn(nsEventLog);
		when(notifyQueueRepository.findByEvtLogId(Mockito.any())).thenReturn(notifyQueueList);
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(notifyQueueServiceImpl.updateNotifyQueueRetry(notifyQueueRetry, headers)));
		assertEquals("Record Not Found!", exception.getMessage());
	}

	@Test
	void testUpdateNotifyQueueNoRecordsFound() {
		when(notifyQueueRepository.findByNtfyQueueId(Mockito.any())).thenReturn(null);
		notifyQueueUpdatedDTO.setFlag(false);
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(notifyQueueServiceImpl.updateNotifyQueue(notifyQueueUpdatedDTO, headers)));

		notifyQueueUpdatedDTO.setFlag(true);
		when(reNotifyViewRepository.searchAllByTermIdAndCustomerName(notifyQueueUpdatedDTO.getTermId(),
				notifyQueueUpdatedDTO.getCustomerName())).thenReturn(Collections.emptyList());
		exception = assertThrows(NoRecordsFoundException.class,
				() -> when(notifyQueueServiceImpl.updateNotifyQueue(notifyQueueUpdatedDTO, headers)));

		when(reNotifyViewRepository.searchAllByTermIdAndCustomerName(notifyQueueUpdatedDTO.getTermId(),
				notifyQueueUpdatedDTO.getCustomerName())).thenReturn(reNotifyViewList);
		exception = assertThrows(NoRecordsFoundException.class,
				() -> when(notifyQueueServiceImpl.updateNotifyQueue(notifyQueueUpdatedDTO, headers)));
	}

	@Test
	void testAddPNotifyQueue() {
		when(notifyQueueRepository.save(Mockito.any())).thenReturn(notifyQueue);
		NotifyQueue addedQueue = notifyQueueServiceImpl.addNotifyQueue(notifyQueue, headers);
		assertNotNull(addedQueue);
	}

	@Test
	void testAddNullPointerException() {
		headers.put("extensionschema", null);
		NullPointerException exception1 = assertThrows(NullPointerException.class,
				() -> when(notifyQueueServiceImpl.addNotifyQueue(notifyQueue, headers)));
		assertEquals("Extension Schema should not be null, empty or blank.", exception1.getMessage());
	}

	@Test
	void testGetNotifyQueueRetryException() {
		when(notifyQueueRepository.findById(Mockito.any())).thenReturn(null);
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(notifyQueueServiceImpl.getNotifyQueueRetry(100L)));
		assertEquals("No Records Found!", exception.getMessage());
	}

	@Test
	void testGetNotifyQueueRetry() {
		when(notifyQueueRepository.findByNtfyQueueId(Mockito.any())).thenReturn(notifyQueue);
		when(nsEventRepo.findById(Mockito.any())).thenReturn(Optional.of(nsEventLog));
		when(nsEventRepo.existsById(Mockito.any())).thenReturn(true);
		when(shipmentRepository.existsBySvcId(Mockito.any())).thenReturn(true);
		when(shipmentRepository.findBySvcId(Mockito.any())).thenReturn(shipment);
		when(customerNicknameRepository.findByCustomerNicknameAndTerminalId(Mockito.any(), Mockito.any()))
				.thenReturn(customerNickname);
		when(customerIndexRepository.existsById(Mockito.any())).thenReturn(true);
		when(customerIndexRepository.findByCustomerId(Mockito.any())).thenReturn(customerIndex);
		when(customerNotifyProfileRepository
				.findByCustomer_CustomerIdAndEventCodeAndNotifyTerminalIdOrderByNotifyTerminalIdDesc(longValue, "NTFY",
						longValue))
				.thenReturn(customerNotifyProfileList);
		NotifyQueueRetry notifyRetry = notifyQueueServiceImpl.getNotifyQueueRetry(longValue);
		assertNotNull(notifyRetry);
	}

	@Test
	void testGetNotifyQueueRetryNegativeFlow() {
		when(notifyQueueRepository.findByNtfyQueueId(Mockito.any())).thenReturn(notifyQueue);
		when(nsEventRepo.findById(Mockito.any())).thenReturn(Optional.of(nsEventLog));
		when(shipmentRepository.findBySvcId(Mockito.any())).thenReturn(shipment);
		when(customerIndexRepository.findByCustomerId(Mockito.any())).thenReturn(customerIndex);
		when(customerNotifyProfileRepository
				.findByCustomer_CustomerIdAndEventCodeAndNotifyTerminalIdOrderByNotifyTerminalIdDesc(longValue, "NTFY",
						longValue))
				.thenReturn(customerNotifyProfileList);

		when(customerNicknameRepository.findByCustomerNicknameAndTerminalId(Mockito.any(), Mockito.any()))
				.thenReturn(customerNickname);

		when(nsEventRepo.existsById(Mockito.any())).thenReturn(false);
		NotifyQueueRetry notifyRetry = notifyQueueServiceImpl.getNotifyQueueRetry(longValue);
		assertNotNull(notifyRetry);

		when(nsEventRepo.existsById(Mockito.any())).thenReturn(true);
		when(shipmentRepository.existsBySvcId(Mockito.any())).thenReturn(false);
		NotifyQueueRetry notifyRetry1 = notifyQueueServiceImpl.getNotifyQueueRetry(longValue);
		assertNotNull(notifyRetry1);

		when(shipmentRepository.existsBySvcId(Mockito.any())).thenReturn(true);
		when(customerIndexRepository.existsById(Mockito.any())).thenReturn(false);
		NotifyQueueRetry notifyRetry2 = notifyQueueServiceImpl.getNotifyQueueRetry(longValue);
		assertNotNull(notifyRetry2);

	}

	@Test
	void testGetNotifyQueueRetryNegativeFlowNulls() {
		notifyQueue.setEvtLogId(null);
		when(notifyQueueRepository.findByNtfyQueueId(Mockito.any())).thenReturn(notifyQueue);
		when(nsEventRepo.findById(Mockito.any())).thenReturn(Optional.of(nsEventLog));
		NotifyQueueRetry notifyRetry = notifyQueueServiceImpl.getNotifyQueueRetry(longValue);
		assertNotNull(notifyRetry);
	}

	@Test
	void testGetNotifyQueueRetryNegativeFlow2() {
		when(notifyQueueRepository.findByNtfyQueueId(Mockito.any())).thenReturn(notifyQueue);
		nsEventLog.setServiceId(null);
		when(nsEventRepo.findById(Mockito.any())).thenReturn(Optional.of(nsEventLog));
		when(nsEventRepo.existsById(Mockito.any())).thenReturn(true);
		when(shipmentRepository.existsBySvcId(Mockito.any())).thenReturn(true);
		when(shipmentRepository.findBySvcId(Mockito.any())).thenReturn(shipment);
		when(customerNicknameRepository.findByCustomerNicknameAndTerminalId(Mockito.any(), Mockito.any()))
				.thenReturn(customerNickname);
		when(customerIndexRepository.existsById(Mockito.any())).thenReturn(true);
		when(customerIndexRepository.findByCustomerId(Mockito.any())).thenReturn(customerIndex);
		when(customerNotifyProfileRepository
				.findByCustomer_CustomerIdAndEventCodeAndNotifyTerminalIdOrderByNotifyTerminalIdDesc(longValue, "NTFY",
						longValue))
				.thenReturn(customerNotifyProfileList);
		NotifyQueueRetry notifyRetry = notifyQueueServiceImpl.getNotifyQueueRetry(longValue);
		assertNotNull(notifyRetry);

		when(notifyQueueRepository.findByNtfyQueueId(Mockito.any())).thenReturn(notifyQueue);
		nsEventLog.setServiceId(longValue);
		when(nsEventRepo.findById(Mockito.any())).thenReturn(Optional.of(nsEventLog));
		when(nsEventRepo.existsById(Mockito.any())).thenReturn(true);
		when(shipmentRepository.existsBySvcId(Mockito.any())).thenReturn(true);
		shipment.setShipStat(null);
		when(shipmentRepository.findBySvcId(Mockito.any())).thenReturn(shipment);
		when(customerNicknameRepository.findByCustomerNicknameAndTerminalId(Mockito.any(), Mockito.any()))
				.thenReturn(customerNickname);
		when(customerIndexRepository.existsById(Mockito.any())).thenReturn(true);
		when(customerIndexRepository.findByCustomerId(Mockito.any())).thenReturn(customerIndex);
		when(customerNotifyProfileRepository
				.findByCustomer_CustomerIdAndEventCodeAndNotifyTerminalIdOrderByNotifyTerminalIdDesc(longValue, "NTFY",
						longValue))
				.thenReturn(customerNotifyProfileList);
		NotifyQueueRetry notifyRetry2 = notifyQueueServiceImpl.getNotifyQueueRetry(longValue);
		assertNotNull(notifyRetry2);

		when(notifyQueueRepository.findByNtfyQueueId(Mockito.any())).thenReturn(notifyQueue);
		when(nsEventRepo.findById(Mockito.any())).thenReturn(Optional.of(nsEventLog));
		when(nsEventRepo.existsById(Mockito.any())).thenReturn(true);
		when(shipmentRepository.existsBySvcId(Mockito.any())).thenReturn(true);
		shipment.setShipStat("S");
		shipment.setCustomerToNotify(null);
		when(shipmentRepository.findBySvcId(Mockito.any())).thenReturn(shipment);
		when(customerNicknameRepository.findByCustomerNicknameAndTerminalId(Mockito.any(), Mockito.any()))
				.thenReturn(customerNickname);
		when(customerIndexRepository.existsById(Mockito.any())).thenReturn(true);
		when(customerIndexRepository.findByCustomerId(Mockito.any())).thenReturn(customerIndex);
		when(customerNotifyProfileRepository
				.findByCustomer_CustomerIdAndEventCodeAndNotifyTerminalIdOrderByNotifyTerminalIdDesc(longValue, "NTFY",
						longValue))
				.thenReturn(customerNotifyProfileList);
		NotifyQueueRetry notifyRetry3 = notifyQueueServiceImpl.getNotifyQueueRetry(longValue);
		assertNotNull(notifyRetry3);
	}
}