package com.nscorp.obis.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nscorp.obis.domain.*;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.repository.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.nscorp.obis.dto.VoiceNotifyDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;

class VoiceNotifyServiceTest {
	@Mock
	VoiceNotifyRepository voiceNotifyRepo;
	@Mock
	EquipmentLocationRepository equipmentLocationRepo;
	@Mock
	EquipmentChassisRepository equipmentChassisRepo;
	@Mock
	TerminalRepository terminalRepo;
	@Mock
	VoiceNotify2Repository voiceNotify2Repo;
	@Mock
	StationRepository stationRepo;
	@Mock
	GenericCodeUpdateRepository genericCodeUpdateRepository;
	@Mock
	CustomerRepository customerRepo;
	
	@Mock
	NotepadEntryRepository noteRepo;

	@Mock
	NotifyQueueRepository notifyQueueRepository;

	@Mock
	TempEVTRepository tempEVTRepository;

	@Mock
	NorfolkSouthernEventLogRepository norfolkSouthernEventLogRepository;
	
	@InjectMocks
	VoiceNotifyServiceImpl voiceNotifyServiceImpl;
	Station station;
	Customer customer;
	NotepadEntry notepadEntry;
	List<NotepadEntry> notepadEntryList;
	List<Station> stationName;
	VoiceNotify voiceNotify;
	List<VoiceNotifyDTO> voiceNotifyDTOList;
	List<VoiceNotify> voiceNotifyList;
	VoiceNotify2 voiceNotify2;
	VoiceNotifyDTO voiceNotifyDTO;
	EquipmentChassis equipmentChassis;
	EquipmentLocation equipmentLocation;
	CustomerInfo customerInfo;
	long notifyQueueId;
	String notifyStat;
    Long termId;
    String notifyMethod;
	Map<String, String> header;

	NotifyQueue notifyQueue;
	List<NorfolkSouthernEventLog> listEventLog;
	
	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
		voiceNotify = new VoiceNotify();
		customer = new Customer();
		notepadEntryList = new ArrayList<>();
		notepadEntry = new NotepadEntry();
		voiceNotify2 = new VoiceNotify2();
		voiceNotifyDTO = new VoiceNotifyDTO();
		equipmentChassis = new EquipmentChassis();
		equipmentLocation = new EquipmentLocation();
		customerInfo = new CustomerInfo();
		station = new Station();
		voiceNotifyList = new ArrayList<>();
		voiceNotifyDTOList = new ArrayList<>();
		stationName = new ArrayList<>();
		notifyQueue = new NotifyQueue();
		listEventLog = new ArrayList<>();
		station.setChar8Spell("qwe");
		stationName.add(station);
		
		voiceNotify2.setSvcId(12342L);
		voiceNotify.setVoiceNotify2(voiceNotify2);
		voiceNotify.setNotifyQueueId(1234L);
		voiceNotify.setEquipId("asd");
		voiceNotify.setEquipInit("qwe");
		voiceNotify.setEquipNbr(BigDecimal.valueOf(1234));
		voiceNotify.setEquipTp("C");
		voiceNotify.setChasId("qwe");
		voiceNotify.setChasNbr(BigDecimal.valueOf(1234));
		voiceNotify.setDmgInd("Y");
		voiceNotify.setEventCd("RMFC");
		voiceNotify.setEvtDesc("GROUNDED-REMOVED FROM FLATCAR");
		voiceNotify.setStationName(station);
		
		voiceNotifyList.add(voiceNotify);
		
		voiceNotifyDTO.setEquipId("asd");
        voiceNotifyDTO.setEquipInit("qwe");
        voiceNotifyDTO.setEquipNbr(BigDecimal.valueOf(123456));
        voiceNotifyDTO.setEquipTp("qwe");
        voiceNotifyDTO.setChasId("qwe");
        voiceNotifyDTO.setChasNbr(BigDecimal.valueOf(123456));
        voiceNotifyDTO.setDmgInd("qwe");
        voiceNotifyDTO.setEventCd("RMFC");
        voiceNotifyDTO.setEvtDesc("GROUNDED-REMOVED FROM FLATCAR");
        
        voiceNotifyDTOList.add(voiceNotifyDTO);
        
		equipmentChassis.setChasTp("T");
		equipmentChassis.setChasEqTp("C");
		equipmentChassis.setChasId("qwe");
		equipmentChassis.setChasNbr(BigDecimal.valueOf(1234));
		equipmentChassis.setDmgInd("qwe");
		equipmentLocation.setChasId("qwe");
		equipmentLocation.setChasNbr(BigDecimal.valueOf(1234));
		equipmentLocation.setChasInit("QWER");
		equipmentLocation.setChasTp("O");
		notifyQueueId = 619919702687L;

		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");

	}

	@Test
	void testGetAllTables() {
		voiceNotify.setEquipTp("C");
		voiceNotify.setEventCd("RMFC");
		when(voiceNotifyRepo.findByNotifyQueueId(notifyQueueId)).thenReturn(voiceNotify);
		when(equipmentLocationRepo.findByEquipInitAndEquipNbrAndEquipTpAndEquipId(Mockito.any(), Mockito.any(),
				Mockito.any(), Mockito.any())).thenReturn(equipmentLocation);
		when(equipmentChassisRepo.findByChasInitAndChasNbrAndChasTpAndChasId(Mockito.any(), Mockito.any(),
				Mockito.any(), Mockito.any())).thenReturn(equipmentChassis);
		when( stationRepo.findByChar8Spell(Mockito.any())).thenReturn(stationName);
		VoiceNotify result = voiceNotifyServiceImpl.getVoiceNotify(notifyQueueId);
		
		voiceNotify.setEquipTp("C");
		voiceNotify.setEventCd("ICHR");
		when(voiceNotifyRepo.findByNotifyQueueId(notifyQueueId)).thenReturn(voiceNotify);
		when(equipmentLocationRepo.findByEquipInitAndEquipNbrAndEquipTpAndEquipId(Mockito.any(), Mockito.any(),
				Mockito.any(), Mockito.any())).thenReturn(equipmentLocation);
		when(equipmentChassisRepo.findByChasInitAndChasNbrAndChasTpAndChasId(Mockito.any(), Mockito.any(),
				Mockito.any(), Mockito.any())).thenReturn(null);
		result = voiceNotifyServiceImpl.getVoiceNotify(notifyQueueId);
		
		
		voiceNotify.setEquipTp("D");
		voiceNotify.setEventCd("ICHD");
		when(voiceNotifyRepo.findByNotifyQueueId(notifyQueueId)).thenReturn(voiceNotify);
		when(equipmentLocationRepo.findByEquipInitAndEquipNbrAndEquipTpAndEquipId(Mockito.any(), Mockito.any(),
				Mockito.any(), Mockito.any())).thenReturn(equipmentLocation);
		when(equipmentChassisRepo.findByChasInitAndChasNbrAndChasTpAndChasId(Mockito.any(), Mockito.any(),
				Mockito.any(), Mockito.any())).thenReturn(equipmentChassis);
		when( stationRepo.findByChar8Spell(Mockito.any())).thenReturn(stationName);
		result = voiceNotifyServiceImpl.getVoiceNotify(notifyQueueId);
		
		voiceNotify.setEventCd("LDFC");
		result = voiceNotifyServiceImpl.getVoiceNotify(notifyQueueId);
		
		voiceNotify.setEventCd("ABCD");
		result = voiceNotifyServiceImpl.getVoiceNotify(notifyQueueId);
		
//		when(voiceNotifyRepo.findByNotifyQueueId(notifyQueueId)).thenReturn(voiceNotify);
//		assertNotNull(voiceNotify);
//		when(equipmentLocationRepo.findByEquipInitAndEquipNbrAndEquipTpAndEquipId(Mockito.any(), Mockito.any(),
//				Mockito.any(), Mockito.any())).thenReturn(equipmentLocation);
//		assertNotNull(equipmentLocation);
//
//		when(equipmentChassisRepo.findByChasInitAndChasNbrAndChasTpAndChasId(Mockito.any(), Mockito.any(),
//				Mockito.any(), Mockito.any())).thenReturn(equipmentChassis);
//		assertNotNull(equipmentChassis);
//		voiceNotify.setEvtDesc("SHIPMENT INTERCHANGE RECEIVED");
//		voiceNotify.setEquipId("C");
//		VoiceNotify result1 = voiceNotifyServiceImpl.getVoiceNotify(notifyQueueId);
//		assertNotNull(result1);

	}

	@Test
	void testGetAllTablesNoRecordsFoundException() {
		when(voiceNotifyRepo.findByNotifyQueueId(anyLong())).thenReturn(null);
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(voiceNotifyServiceImpl.getVoiceNotify(anyLong())));
		assertEquals("No records found", exception.getMessage());

//		voiceNotify.setEquipTp("T");
//		when(voiceNotifyRepo.findByNotifyQueueId(anyLong())).thenReturn(voiceNotify);
//		when(equipmentLocationRepo.findByEquipInitAndEquipNbrAndEquipTpAndEquipId(anyString(), Mockito.any(),
//				anyString(), anyString())).thenReturn(equipmentLocation);
//		when(equipmentChassisRepo.findByChasInitAndChasNbrAndChasTpAndChasId(Mockito.any(), Mockito.any(), Mockito.any(),
//				Mockito.any())).thenReturn(equipmentChassis);

	}
	
	@Test
	void testVoiceNotifyList() {
		when(voiceNotifyRepo.findByNotifyStatAndTermIdAndNotifyMethod(notifyStat, termId, notifyMethod)).thenReturn(voiceNotifyList);
		when(customerRepo.findByCustomerId(Mockito.any())).thenReturn(customer);
		when(noteRepo.findBySvcIdAndCreateDateTimeLessThanOrderByCreateDateTime(Mockito.any(), Mockito.any())).thenReturn(notepadEntryList);
		List<VoiceNotify> voiceNtfyList = voiceNotifyServiceImpl.getVoiceNtfyList(notifyStat, termId, notifyMethod);
		assertEquals(voiceNtfyList, voiceNotifyList);
	}

	@Test
	void testUpdateVoiceNotifyIfEventCode() {
		voiceNotify.setNotifyStat("SENT");
		voiceNotify.setPersonField("Test");
		voiceNotify.setEventCd("RMFC");
		voiceNotify.setNotifyCustId(Long.valueOf(1));
		when(voiceNotifyRepo.existsByNotifyQueueId(Mockito.any())).thenReturn(true);
		when(voiceNotifyRepo.findByNotifyQueueId(Mockito.any())).thenReturn(voiceNotify);
		when(notifyQueueRepository.findByNtfyQueueId(Mockito.any())).thenReturn(notifyQueue);
		when(norfolkSouthernEventLogRepository.findByServiceIdAndEvtCd(Mockito.any(),Mockito.any())).thenReturn(listEventLog);
		when(tempEVTRepository.SGK()).thenReturn(null);
		VoiceNotify updateData = voiceNotifyServiceImpl.updateVoiceNotify(voiceNotify, header);
		Assertions.assertNotNull(updateData);
	}

	@Test
	void testUpdateVoiceNotifyElseEventCode() {
		voiceNotify.setNotifyStat("SENT");
		voiceNotify.setPersonField("Test");
		voiceNotify.setEventCd("TEST");
		voiceNotify.setNotifyCustId(Long.valueOf(1));
		when(voiceNotifyRepo.existsByNotifyQueueId(Mockito.any())).thenReturn(true);
		when(voiceNotifyRepo.findByNotifyQueueId(Mockito.any())).thenReturn(voiceNotify);
		when(notifyQueueRepository.findByNtfyQueueId(Mockito.any())).thenReturn(notifyQueue);
		when(norfolkSouthernEventLogRepository.findByServiceIdAndEvtCd(Mockito.any(),Mockito.any())).thenReturn(listEventLog);
		when(tempEVTRepository.SGK()).thenReturn(null);
		VoiceNotify updateData = voiceNotifyServiceImpl.updateVoiceNotify(voiceNotify, header);
		Assertions.assertNotNull(updateData);
	}

	@Test
	void testUpdateVoiceNotifyNullPointerException() {
		Map<String, String> headers;
		headers = new HashMap<String, String>();
		headers.put("userid", "Test");
		headers.put("extensionschema", null);
		NullPointerException exception = assertThrows(NullPointerException.class,
				() -> when(voiceNotifyServiceImpl.updateVoiceNotify(voiceNotify,headers)));
		assertEquals("Extension Schema should not be null, empty or blank.", exception.getMessage());
	}

	@Test
	void testUpdateRecordNotAddedException() {
		voiceNotify.setNotifyStat("SENT");
		voiceNotify.setPersonField("Test");
		when(voiceNotifyRepo.existsByNotifyQueueId(Mockito.any())).thenReturn(true);
		when(voiceNotifyRepo.findByNotifyQueueId(Mockito.any())).thenReturn(voiceNotify);
		assertThrows(RecordNotAddedException.class,
				() -> when(voiceNotifyServiceImpl
						.updateVoiceNotify(voiceNotify, header)));
	}

	@Test
	void testUpdateNotifyStatFailRecordNotAddedException() {
		voiceNotify.setNotifyStat("FAIL");
		when(voiceNotifyRepo.existsByNotifyQueueId(Mockito.any())).thenReturn(true);
		when(voiceNotifyRepo.findByNotifyQueueId(Mockito.any())).thenReturn(voiceNotify);
		assertThrows(RecordNotAddedException.class,
				() -> when(voiceNotifyServiceImpl
						.updateVoiceNotify(voiceNotify, header)));
	}

	@Test
	void testUpdateRecordNotAddedElseException() {
		voiceNotify.setNotifyStat("SENT");
		voiceNotify.setPersonField("");
		when(voiceNotifyRepo.existsByNotifyQueueId(Mockito.any())).thenReturn(true);
		when(voiceNotifyRepo.findByNotifyQueueId(Mockito.any())).thenReturn(voiceNotify);
		assertThrows(RecordNotAddedException.class,
				() -> when(voiceNotifyServiceImpl
						.updateVoiceNotify(voiceNotify, header)));
	}

	@Test
	void testUpdateNoRecordFoundException() {
		when(voiceNotifyRepo.existsByNotifyQueueId(Mockito.any())).thenReturn(false);
		assertThrows(NoRecordsFoundException.class,
				() -> when(voiceNotifyServiceImpl
						.updateVoiceNotify(voiceNotify, header)));
	}

	@Test
	void testUpdateVoiceNotifyEmptyPersonFiled() {
		voiceNotify.setNotifyStat("SENT");
		voiceNotify.setPersonField("");
		voiceNotify.setEventCd("RMFC");
		voiceNotify.setNotifyCustId(Long.valueOf(1));
		when(voiceNotifyRepo.existsByNotifyQueueId(Mockito.any())).thenReturn(true);
		when(voiceNotifyRepo.findByNotifyQueueId(Mockito.any())).thenReturn(voiceNotify);
		when(notifyQueueRepository.findByNtfyQueueId(Mockito.any())).thenReturn(notifyQueue);
		when(norfolkSouthernEventLogRepository.findByServiceIdAndEvtCd(Mockito.any(),Mockito.any())).thenReturn(listEventLog);
		when(tempEVTRepository.SGK()).thenReturn(null);
		VoiceNotify updateData = voiceNotifyServiceImpl.updateVoiceNotify(voiceNotify, header);
		Assertions.assertNotNull(updateData);
	}
	
	@Test
	void testVoiceNtfyList() {
		
		VoiceNotify voiceNtfy = new VoiceNotify();
		List<VoiceNotify> voiceList = new ArrayList<>();		
		voiceNtfy.setNotifyCustId(12345L);
		voiceList.add(voiceNtfy);
		voiceNtfy.setCustomerName(customer.getCustomerName());
		
		voiceNtfy.setSvcId(12345L);
		NotepadEntry notePad = new NotepadEntry();
		List<NotepadEntry> notePadList = new ArrayList<>();
		voiceNtfy.setNote(notePadList);
		
		notePadList.add(notePad);
		
		when(voiceNotifyRepo.findByNotifyStatAndTermIdAndNotifyMethod(notifyStat, termId, notifyMethod)).thenReturn(voiceList);
		when(customerRepo.findByCustomerId(voiceNtfy.getNotifyCustId())).thenReturn(customer);
		when(noteRepo.findBySvcIdAndCreateDateTimeLessThanOrderByCreateDateTime(Mockito.any(), Mockito.any())).thenReturn(notepadEntryList);
		List<VoiceNotify> voiceNtfyList = voiceNotifyServiceImpl.getVoiceNtfyList(notifyStat, termId, notifyMethod);
		assertEquals(voiceNtfyList, voiceList);
	}
	
	@Test
	void testGetVoiceNtfyListException() {
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(voiceNotifyServiceImpl.getVoiceNtfyList(notifyStat, termId, notifyMethod)));
		assertEquals("No Records Found!", exception.getMessage());
	}
	
}
