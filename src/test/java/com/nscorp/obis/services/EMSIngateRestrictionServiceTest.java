package com.nscorp.obis.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.domain.DayOfWeek;
import com.nscorp.obis.domain.EMSEquipmentLengthRestriction;
import com.nscorp.obis.domain.EMSIngateRestriction;
import com.nscorp.obis.domain.EquipmentType;
import com.nscorp.obis.domain.Station;
import com.nscorp.obis.dto.EMSIngateRestrictionDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.repository.CorporateCustomerRepository;
import com.nscorp.obis.repository.EMSIngateRestrictionRepository;
import com.nscorp.obis.repository.StationRepository;
import com.nscorp.obis.repository.TerminalRepository;

class EMSIngateRestrictionServiceTest {

	@InjectMocks
	EMSIngateRestrictionServiceImpl emsIngateRestrictionService;
	@Mock
	EMSIngateRestrictionRepository emsIngateRestrictionRepository;

	@Mock
	TerminalRepository terminalRepository;

	@Mock
	StationRepository stationRepository;

	@Mock
	CorporateCustomerRepository corporateCustomerRepository;

	EMSIngateRestrictionDTO emsIngateRestrictionDto;
	EMSIngateRestriction emsIngateRestriction;
	List<EMSIngateRestriction> emsIngateRestrictionList;
	List<EMSIngateRestrictionDTO> emsIngateRestrictionDtoList;
	Station station;
	Map<String, String> header;
	Long ingateTerminalId = 16535306729700L;
	List<String> lob = new ArrayList<String>() ;
	List<String> trafficType = new ArrayList<String>();
	List<EquipmentType> eqTp = new ArrayList<>(EnumSet.allOf(EquipmentType.class));
	List<DayOfWeek> days = new ArrayList<>(EnumSet.allOf(DayOfWeek.class));
	List<EMSEquipmentLengthRestriction> lengthRestriction;
	LocalDate startDate;
	LocalDate endDate;
	LocalTime startTime = LocalTime.parse("00:00:00");
	LocalTime endTime = LocalTime.parse("23:59:59");
	Timestamp ts = Timestamp.valueOf(String.format("%04d-%02d-%02d 00:00:00",
			2022, 07, 01));
	Timestamp ts1 = Timestamp.valueOf(String.format("%04d-%02d-%02d 00:00:00",
			2022, 05, 01));
	String local = null;
	String steelWheel = null;
	String rubberTire = null;
	String domestic = null;
	String international = null;
	String premium = null;

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		emsIngateRestriction = new EMSIngateRestriction();
		lengthRestriction = new ArrayList<>();
		emsIngateRestriction.setRestrictId(1327341890048L);
		emsIngateRestriction.setIngateTerminalId(ingateTerminalId);
		emsIngateRestriction.setUversion("8");
//		emsIngateRestriction.getOnlineOriginStation().setTermId(11668022820698L);
//		emsIngateRestriction.getOnlineDestinationStation().setTermId(null);
//		emsIngateRestriction.getOfflineDestinationStation().setTermId(null);
		emsIngateRestriction.setOnlineOriginStation(station);
		emsIngateRestriction.setOnlineDestinationStation(null);
		emsIngateRestriction.setOfflineDestinationStation(null);
		emsIngateRestriction.setEquipmentInit("EMHU");
		emsIngateRestriction.setEquipmentLowestNumber(null);
		emsIngateRestriction.setEquipmentHighestNumber(null);
		emsIngateRestriction.setCorporateCustomerId(null);
		emsIngateRestriction.setLoadEmptyCode("E");
		emsIngateRestriction.setEquipmentLength(null);
		emsIngateRestriction.setGrossWeight(null);
//		eqTp.add(EquipmentType.CONTAINER);
//		emsIngateRestriction.setEquipmentTypes(eqTp);
		lob.add("DOMESTIC");
		lob.add("INTERNATIONAL");
		lob.add("PREMIUM");
		domestic = "T";
		international = "T";
		premium = "T";
		emsIngateRestriction.setWayBillRoute("N");
		emsIngateRestriction.setHazardousIndicator(null);
		emsIngateRestriction.setWeightQualifier(null);
		emsIngateRestriction.setActive("T");
		emsIngateRestriction.setStartDate(startDate);
		emsIngateRestriction.setStartTime(startTime);
		emsIngateRestriction.setEndDate(endDate);
		emsIngateRestriction.setEndTime(endTime);
		emsIngateRestriction.setCreateDateTime(ts);
		emsIngateRestriction.setCreateUserId("test");
		emsIngateRestriction.setCreateExtensionSchema("IMS08120");
		emsIngateRestriction.setUpdateDateTime(ts1);
		emsIngateRestriction.setUpdateUserId("test");
		emsIngateRestriction.setUpdateExtensionSchema("NGTNMR");
		days.add(DayOfWeek.SUN);
		days.add(DayOfWeek.MON);
		days.add(DayOfWeek.TUE);
		days.add(DayOfWeek.WED);
		days.add(DayOfWeek.THU);
		days.add(DayOfWeek.FRI);
		days.add(DayOfWeek.SAT);
		emsIngateRestriction.setRestrictedDays(days);
		emsIngateRestriction.setReeferIndicator(null);
		trafficType.add("LOCAL");
		trafficType.add("STEEL_WHEEL");
		trafficType.add("RUBBER_TIRE");
		local = "T";
		steelWheel = "T";
		rubberTire = "T";
		emsIngateRestriction.setReeferIndicator(null);
		emsIngateRestriction.setTemporaryIndicator("T");
		emsIngateRestrictionList = new ArrayList<>();
		emsIngateRestrictionList.add(emsIngateRestriction);

		emsIngateRestrictionDtoList = new ArrayList<>();
		emsIngateRestrictionDto = new EMSIngateRestrictionDTO();
		emsIngateRestrictionDto.setRestrictId(1327341890048L);
		emsIngateRestrictionDto.setIngateTerminalId(ingateTerminalId);
		emsIngateRestrictionDto.setUversion("8");
//		emsIngateRestrictionDto.getOnlineOriginStation().setTermId(11668022820698L);
//		emsIngateRestrictionDto.getOnlineDestinationStation().setTermId(null);
//		emsIngateRestrictionDto.getOfflineDestinationStation().setTermId(null);
		emsIngateRestrictionDto.setOnlineOriginStation(station);
		emsIngateRestrictionDto.setOnlineDestinationStation(null);
		emsIngateRestrictionDto.setOfflineDestinationStation(null);
		emsIngateRestrictionDto.setEquipmentInit("EMHU");
		emsIngateRestrictionDto.setEquipmentLowestNumber(null);
		emsIngateRestrictionDto.setEquipmentHighestNumber(null);
		emsIngateRestrictionDto.setCorporateCustomerId(null);
		emsIngateRestrictionDto.setLoadEmptyCode("E");
		emsIngateRestrictionDto.setEquipmentLength(null);
		emsIngateRestrictionDto.setGrossWeight(null);
		eqTp.add(EquipmentType.CONTAINER);
		emsIngateRestrictionDto.setEquipmentTypes(eqTp);
		lob.add("DOMESTIC");
		lob.add("INTERNATIONAL");
		lob.add("PREMIUM");
		domestic = "T";
		international = "T";
		premium = "T";
		emsIngateRestrictionDto.setWayBillRoute("N");
		emsIngateRestrictionDto.setHazardousIndicator(null);
		emsIngateRestrictionDto.setWeightQualifier(null);
		emsIngateRestrictionDto.setActive("T");
		emsIngateRestrictionDto.setStartDate(startDate);
		emsIngateRestrictionDto.setStartTime(startTime);
		emsIngateRestrictionDto.setEndDate(endDate);
		emsIngateRestrictionDto.setEndTime(endTime);
		emsIngateRestrictionDto.setCreateDateTime(ts);
		emsIngateRestrictionDto.setCreateUserId("test");
		emsIngateRestrictionDto.setCreateExtensionSchema("IMS08120");
		emsIngateRestrictionDto.setUpdateDateTime(ts1);
		emsIngateRestrictionDto.setUpdateUserId("test");
		emsIngateRestrictionDto.setUpdateExtensionSchema("NGTNMR");
		days.add(DayOfWeek.SUN);
		days.add(DayOfWeek.MON);
		days.add(DayOfWeek.TUE);
		days.add(DayOfWeek.WED);
		days.add(DayOfWeek.THU);
		days.add(DayOfWeek.FRI);
		days.add(DayOfWeek.SAT);
		emsIngateRestrictionDto.setRestrictedDays(days);
		emsIngateRestrictionDto.setReeferIndicator(null);
		
		station = new Station();
		station.setTermId(11668022820698L);
		station.setStationName("LOGISTIK");
		station.setState("SL");
		station.setSplc("919426000");
		station.setRule260Station("CNTRA");
		station.setRoadNumber("0978");
		station.setRoadName("KCSM");
		station.setOperationStation("92457");
		station.setIntermodalIndicator("O");
		station.setFSAC("092457");
		station.setExpiredDate(null);
		station.setExpirationDate(null);
		station.setDivision("74");
		station.setChar8Spell("HUMPREPA");
		station.setChar5Spell("GREGC");
		station.setChar5Alias(null);
		station.setBottomPick("Y");
		station.setTopPick("Y");
		station.setBillingInd(null);
		station.setBillAtFsac("071619");
		
		trafficType.add("LOCAL");
		trafficType.add("STEEL_WHEEL");
		trafficType.add("RUBBER_TIRE");
		local = "T";
		steelWheel = "T";
		rubberTire = "T";
		emsIngateRestrictionDto.setReeferIndicator(null);
		emsIngateRestrictionDto.setTemporaryIndicator("T");
		lengthRestriction.add(EMSEquipmentLengthRestriction.RESTRICT_ALL);
		emsIngateRestrictionDto.setEmsEquipmentLengthRestrictions(lengthRestriction);
		emsIngateRestrictionDtoList.add(emsIngateRestrictionDto);
		header = new HashMap<String,String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");
	}

	@AfterEach
	void tearDown() throws Exception {
		emsIngateRestrictionList = null;
		emsIngateRestrictionDtoList = null;
		emsIngateRestrictionDto = null;
		emsIngateRestriction = null;
	}
	@Test
	void testGetAllRestrictions() {
		when(emsIngateRestrictionRepository.findAll()).thenReturn(emsIngateRestrictionList);
		List<EMSIngateRestriction> allEMSRestrictions = emsIngateRestrictionService.getAllRestrictions();
		assertEquals(allEMSRestrictions,emsIngateRestrictionList);
	}

	@Test
	void testGetAllRestrictionsNoRecordsFoundException() {
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> emsIngateRestrictionService.getAllRestrictions());
		assertEquals("No Records are found for EMS Ingate Restrictions", exception.getMessage());
	}
	
	@Test
	void testInsertRestriction() {
		emsIngateRestriction.setOnlineOriginStation(station);
		emsIngateRestriction.setOnlineDestinationStation(station);
		emsIngateRestriction.setCorporateCustomerId((Long) 123456L);
		eqTp.add(EquipmentType.CONTAINER);
		emsIngateRestriction.setEquipmentTypes(eqTp);
		lengthRestriction.add(EMSEquipmentLengthRestriction.RESTRICT_ALL);
		emsIngateRestriction.setEmsEquipmentLengthRestrictions(lengthRestriction);

		assertNotNull(emsIngateRestriction.getCreateExtensionSchema());
		assertNotNull(emsIngateRestriction.getIngateTerminalId());
		when(terminalRepository.existsByTerminalIdAndExpiredDateIsNull(Mockito.any())).thenReturn(true);
		
		assertNotEquals(emsIngateRestriction.getIngateTerminalId(), CommonConstants.NON_TERMINAL_SPECIFIC);
		assertNotNull(emsIngateRestriction.getOnlineOriginStation());

		assertNotNull(emsIngateRestriction.getOnlineDestinationStation());
		when(stationRepository.existsByTermIdAndExpiredDateIsNull(emsIngateRestriction.getOnlineDestinationStation().getTermId())).thenReturn(true);

		when(corporateCustomerRepository.existsById(emsIngateRestriction.getCorporateCustomerId())).thenReturn(true);

		assertNull(emsIngateRestriction.getOfflineDestinationStation());
		assertNotNull(emsIngateRestriction.getCorporateCustomerId());
		assertNotNull(emsIngateRestriction.getWayBillRoute());
		assertNotNull(emsIngateRestriction.getEmsEquipmentLengthRestrictions());

		when(emsIngateRestrictionRepository.findByRestrictId(Mockito.any())).thenReturn(emsIngateRestriction);
		when(emsIngateRestrictionRepository.save(Mockito.any())).thenReturn(emsIngateRestriction);
		when(terminalRepository.getStationXrfId(Mockito.any())).thenReturn(11668022820698L);
		when(stationRepository.findById(Mockito.any())).thenReturn(Optional.of(station));
		EMSIngateRestriction restrictionAdded = emsIngateRestrictionService.insertRestriction(emsIngateRestriction, header);
		assertNotNull(restrictionAdded);
	}

	@Test
	void testUpdateAllocationTermIdNoRecordsFoundException() {
		emsIngateRestriction.setRestrictId(null);
		assertNull(emsIngateRestriction.getRestrictId());
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> emsIngateRestrictionService.updateRestriction(emsIngateRestriction, header));
		assertEquals("Restrict Id is mandatory Field", exception.getMessage());
	}

	@Test
	void testUpdateAllocationTermIdNullPointerException() {
		header.put("extensionschema", null);
		assertNull(header.get(CommonConstants.EXTENSION_SCHEMA));
		NullPointerException exception = assertThrows(NullPointerException.class,
				() -> emsIngateRestrictionService.insertRestriction(emsIngateRestriction, header));
		assertEquals("Extension Schema should not be null, empty or blank", exception.getMessage());
	}

	@Test
	void testUpdateAllocationIngateRecordNotAddedException() {
		assertNotNull(emsIngateRestriction.getCreateExtensionSchema());
		emsIngateRestriction.setOnlineOriginStation(null);
		emsIngateRestriction.setOnlineDestinationStation(null);
		emsIngateRestriction.setCorporateCustomerId((Long) 123456L);
		eqTp.add(EquipmentType.CONTAINER);
		emsIngateRestriction.setEquipmentTypes(eqTp);
		lengthRestriction.add(EMSEquipmentLengthRestriction.RESTRICT_ALL);
		emsIngateRestriction.setEmsEquipmentLengthRestrictions(lengthRestriction);
		assertNotNull(emsIngateRestriction.getCreateExtensionSchema());
		assertNotNull(emsIngateRestriction.getIngateTerminalId());
		when(terminalRepository.existsByTerminalIdAndExpiredDateIsNull(Mockito.any())).thenReturn(true);
		assertNotEquals(emsIngateRestriction.getIngateTerminalId(), CommonConstants.NON_TERMINAL_SPECIFIC);
		assertNull(emsIngateRestriction.getOnlineOriginStation());

		assertNull(emsIngateRestriction.getOnlineDestinationStation());
		when(stationRepository.existsByTermIdAndExpiredDateIsNull(Mockito.any())).thenReturn(true);

		when(corporateCustomerRepository.existsById(emsIngateRestriction.getCorporateCustomerId())).thenReturn(true);

		assertNull(emsIngateRestriction.getOfflineDestinationStation());
		assertNotNull(emsIngateRestriction.getCorporateCustomerId());
		assertNotNull(emsIngateRestriction.getWayBillRoute());
		assertNotNull(emsIngateRestriction.getEmsEquipmentLengthRestrictions());

		emsIngateRestriction.setStartTime(null);
		assertNull(emsIngateRestriction.getStartTime());
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> emsIngateRestrictionService.insertRestriction(emsIngateRestriction, header));
		assertEquals("'Ingate Terminal, Start Date, End Date, Equipment Type & Equipment Length' values should be present!", exception.getMessage());
	}



	@Test
	void testUpdateAllocationRestrictIdNoRecordsFoundException() {
		when(emsIngateRestrictionRepository.existsByRestrictId(Mockito.any())).thenReturn(false);
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> emsIngateRestrictionService.updateRestriction(emsIngateRestriction, header));
		assertEquals("Record Not Found!", exception.getMessage());
	}


	@Test
	void testUpdateRestriction() {
		assertNotNull(emsIngateRestriction.getRestrictId());
		when(emsIngateRestrictionRepository.existsByRestrictId(Mockito.any())).thenReturn(true);
		emsIngateRestriction.setOnlineOriginStation(station);
		emsIngateRestriction.setOnlineDestinationStation(station);
		emsIngateRestriction.setCorporateCustomerId((Long) 123456L);
		eqTp.add(EquipmentType.CONTAINER);
		emsIngateRestriction.setEquipmentTypes(eqTp);
		lengthRestriction.add(EMSEquipmentLengthRestriction.RESTRICT_ALL);
		emsIngateRestriction.setEmsEquipmentLengthRestrictions(lengthRestriction);

		assertNotNull(emsIngateRestriction.getCreateExtensionSchema());
		assertNotNull(emsIngateRestriction.getIngateTerminalId());
		when(terminalRepository.existsByTerminalIdAndExpiredDateIsNull(Mockito.any())).thenReturn(true);
		assertNotEquals(emsIngateRestriction.getIngateTerminalId(), CommonConstants.NON_TERMINAL_SPECIFIC);
		assertNotNull(emsIngateRestriction.getOnlineOriginStation());
		when(corporateCustomerRepository.existsById(emsIngateRestriction.getCorporateCustomerId())).thenReturn(true);

		assertNull(emsIngateRestriction.getOfflineDestinationStation());
		assertNotNull(emsIngateRestriction.getCorporateCustomerId());
		assertNotNull(emsIngateRestriction.getWayBillRoute());
		assertNotNull(emsIngateRestriction.getEmsEquipmentLengthRestrictions());

		when(emsIngateRestrictionRepository.findByRestrictId(Mockito.any())).thenReturn(emsIngateRestriction);
		when(terminalRepository.getStationXrfId(Mockito.any())).thenReturn(11668022820698L);
		when(stationRepository.findById(Mockito.any())).thenReturn(Optional.of(station));
		when(stationRepository.existsByTermIdAndExpiredDateIsNull(Mockito.any())).thenReturn(true);
		when(emsIngateRestrictionRepository.save(Mockito.any())).thenReturn(emsIngateRestriction);
		EMSIngateRestriction restrictionAdded = emsIngateRestrictionService.updateRestriction(emsIngateRestriction, header);
		assertNotNull(restrictionAdded);
	}

	@Test
	void testInsertAllocationTermIdNullPointerException() {
		header.put("extensionschema", null);
		assertNull(header.get(CommonConstants.EXTENSION_SCHEMA));
		NullPointerException exception = assertThrows(NullPointerException.class,
				() -> emsIngateRestrictionService.updateRestriction(emsIngateRestriction, header));
		assertEquals("Extension Schema should not be null, empty or blank", exception.getMessage());
	}

	@Test
	void testInsertAllocationIngateRecordNotAddedException() {
		assertNotNull(emsIngateRestriction.getRestrictId());
		when(emsIngateRestrictionRepository.existsByRestrictId(Mockito.any())).thenReturn(true);
		emsIngateRestriction.setOnlineOriginStation(null);
		emsIngateRestriction.setOnlineDestinationStation(null);
		emsIngateRestriction.setCorporateCustomerId((Long) 123456L);
		emsIngateRestriction.setIngateTerminalId(ingateTerminalId);
		eqTp.add(EquipmentType.CONTAINER);
		emsIngateRestriction.setEquipmentTypes(eqTp);
		lengthRestriction.add(EMSEquipmentLengthRestriction.RESTRICT_ALL);
		emsIngateRestriction.setEmsEquipmentLengthRestrictions(lengthRestriction);

		assertNotNull(emsIngateRestriction.getCreateExtensionSchema());
		assertNotNull(emsIngateRestriction.getIngateTerminalId());
		when(terminalRepository.existsByTerminalIdAndExpiredDateIsNull(Mockito.any())).thenReturn(true);
		assertNotEquals(emsIngateRestriction.getIngateTerminalId(), CommonConstants.NON_TERMINAL_SPECIFIC);
		assertNull(emsIngateRestriction.getOnlineOriginStation());
		when(corporateCustomerRepository.existsById(emsIngateRestriction.getCorporateCustomerId())).thenReturn(true);

		assertNull(emsIngateRestriction.getOfflineDestinationStation());
		assertNotNull(emsIngateRestriction.getCorporateCustomerId());
		assertNotNull(emsIngateRestriction.getWayBillRoute());
		assertNotNull(emsIngateRestriction.getEmsEquipmentLengthRestrictions());

		when(stationRepository.findById(Mockito.any())).thenReturn(Optional.of(station));
		when(emsIngateRestrictionRepository.findByRestrictId(Mockito.any())).thenReturn(emsIngateRestriction);

		emsIngateRestriction.setStartTime(null);
		assertNull(emsIngateRestriction.getStartTime());

		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> emsIngateRestrictionService.updateRestriction(emsIngateRestriction, header));
		assertEquals("'Ingate Terminal, Start Date, End Date, Equipment Type & Equipment Length' values should be present!", exception.getMessage());
	}

	@Test
	void testInsertAllocationIngateGateNotFound() {
		assertNotNull(emsIngateRestriction.getCreateExtensionSchema());
		when(terminalRepository.existsByTerminalIdAndExpiredDateIsNull(emsIngateRestriction.getIngateTerminalId())).thenReturn(false);
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> emsIngateRestrictionService.insertRestriction(emsIngateRestriction, header));
		assertEquals("Ingate Terminal with Id 16535306729700 not found!", exception.getMessage());
	}
	@Test
	void testInsertAllocationIngateGateConstant() {
		emsIngateRestriction.setIngateTerminalId(10000L);
		emsIngateRestriction.setOnlineOriginStation(station);
		assertNotNull(emsIngateRestriction.getCreateExtensionSchema());
		when(terminalRepository.getStationXrfId(Mockito.any())).thenReturn(10L);
		when(terminalRepository.existsByTerminalIdAndExpiredDateIsNull(Mockito.any())).thenReturn(true);
		when(stationRepository.findById(Mockito.any())).thenReturn(Optional.of(station));
//		assertNotEquals(emsIngateRestriction.getOnlineOriginStation(), terminalRepository.getStationXrfId(emsIngateRestriction.getIngateTerminalId()));
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> emsIngateRestrictionService.insertRestriction(emsIngateRestriction, header));
		assertEquals("Online Origin station Id 11668022820698 is either not valid or not found in 'Terminal' table!", exception.getMessage());
	}

	@Test
	void testInsertAllocationIngateGateNotConstant() {
		emsIngateRestriction.setIngateTerminalId(CommonConstants.NON_TERMINAL_SPECIFIC);
		emsIngateRestriction.setOnlineOriginStation(station);

		assertNotNull(emsIngateRestriction.getCreateExtensionSchema());
		when(terminalRepository.existsByTerminalIdAndExpiredDateIsNull(emsIngateRestriction.getIngateTerminalId())).thenReturn(true);
		when(stationRepository.findById(Mockito.any())).thenReturn(Optional.of(station));
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> emsIngateRestrictionService.insertRestriction(emsIngateRestriction, header));
		assertEquals("Online Origin Station with Id should be null for Non-Terminal specific EMS Ingate Restriction!", exception.getMessage());
		emsIngateRestriction.setIngateTerminalId(100L);
	}

	@Test
	void testInsertAllocationGetOnlineDestinationStationTermId() {
		emsIngateRestriction.setIngateTerminalId(100L);
		emsIngateRestriction.setOnlineDestinationStation(station);
		when(terminalRepository.existsByTerminalIdAndExpiredDateIsNull(emsIngateRestriction.getIngateTerminalId())).thenReturn(true);
		when(stationRepository.existsByTermIdAndExpiredDateIsNull(Mockito.any())).thenReturn(false);
		when(stationRepository.findById(Mockito.any())).thenReturn(Optional.of(station));
		NoRecordsFoundException exception1 = assertThrows(NoRecordsFoundException.class,
				() -> emsIngateRestrictionService.insertRestriction(emsIngateRestriction, header));
		assertEquals("Online Destination Station with Id 11668022820698 not found!", exception1.getMessage());
	}

	@Test
	void testInsertAllocationGetOfflineDestinationStationTermId() {
		emsIngateRestriction.setIngateTerminalId(100L);
		emsIngateRestriction.setOfflineDestinationStation(station);
		when(terminalRepository.existsByTerminalIdAndExpiredDateIsNull(emsIngateRestriction.getIngateTerminalId())).thenReturn(true);
		when(stationRepository.existsByTermIdAndExpiredDateIsNull(Mockito.any())).thenReturn(false);
		when(stationRepository.findById(Mockito.any())).thenReturn(Optional.of(station));

		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> emsIngateRestrictionService.insertRestriction(emsIngateRestriction, header));
		assertEquals("Offline Destination Station with Id 11668022820698 not found!", exception.getMessage());
	}

	@Test
	void testInsertAllocationGetEquipmentInit() {
		emsIngateRestriction.setOnlineOriginStation(null);
		emsIngateRestriction.setEquipmentInit("123");

		assertNotNull(emsIngateRestriction.getCreateExtensionSchema());
		when(terminalRepository.existsByTerminalIdAndExpiredDateIsNull(emsIngateRestriction.getIngateTerminalId())).thenReturn(true);
		assertNotEquals(emsIngateRestriction.getEquipmentInit(),"^[a-zA-Z]*$");
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> emsIngateRestrictionService.insertRestriction(emsIngateRestriction, header));
		assertEquals("Equipment Init should have only character values", exception.getMessage());
	}

	@Test
	void testInsertAllocationGetCorpCust() {
//		emsIngateRestriction.setOnlineDestinationStation(station);
		emsIngateRestriction.setOnlineOriginStation(null);
		emsIngateRestriction.setEquipmentInit(null);
		emsIngateRestriction.setCorporateCustomerId((Long) 123456L);

		assertNotNull(emsIngateRestriction.getCreateExtensionSchema());
		when(terminalRepository.existsByTerminalIdAndExpiredDateIsNull(emsIngateRestriction.getIngateTerminalId())).thenReturn(true);
		assertNotNull(emsIngateRestriction.getCorporateCustomerId());
		when(corporateCustomerRepository.existsById(emsIngateRestriction.getCorporateCustomerId())).thenReturn(false);
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> emsIngateRestrictionService.insertRestriction(emsIngateRestriction,header));
		assertEquals("Corporate Customer Id : 123456 not found!", exception.getMessage());

	}

	@Test
	void testInsertAllocationGetCorpShipper() {
		emsIngateRestriction.setOnlineOriginStation(null);
		emsIngateRestriction.setEquipmentInit(null);

		assertNotNull(emsIngateRestriction.getCreateExtensionSchema());
		when(terminalRepository.existsByTerminalIdAndExpiredDateIsNull(emsIngateRestriction.getIngateTerminalId())).thenReturn(true);
		assertTrue(emsIngateRestriction.getLineOfBusinesses().isEmpty());
		when(corporateCustomerRepository.existsById(emsIngateRestriction.getCorporateCustomerId())).thenReturn(false);
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> emsIngateRestrictionService.insertRestriction(emsIngateRestriction,header));
		assertEquals("A 'Corporate Shipper(Customer)' or a 'Primary Line Of Business' value is required!", exception.getMessage());

	}

	@Test
	void testInsertAllocationGetWayBillRoute() {
		emsIngateRestriction.setOnlineOriginStation(null);
		emsIngateRestriction.setEquipmentInit(null);
		emsIngateRestriction.setWayBillRoute(null);
		emsIngateRestriction.setCorporateCustomerId(1234L);

		assertNotNull(emsIngateRestriction.getCreateExtensionSchema());
		when(terminalRepository.existsByTerminalIdAndExpiredDateIsNull(emsIngateRestriction.getIngateTerminalId())).thenReturn(true);
		when(terminalRepository.getStationXrfId(Mockito.any())).thenReturn(123000L);
		when(corporateCustomerRepository.existsById(emsIngateRestriction.getCorporateCustomerId())).thenReturn(true);
//		when(stationRepository.findById(Mockito.any())).thenReturn(Optional.of(station));

		assertNull(emsIngateRestriction.getWayBillRoute());
		assertTrue(emsIngateRestriction.getTrafficTypes().isEmpty());
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> emsIngateRestrictionService.insertRestriction(emsIngateRestriction,header));
		assertEquals("A 'Way Bill Route' or a 'Traffic Type' value is required!", exception.getMessage());
	}

	@Test
	void testInsertAllocationGetActive() {
		emsIngateRestriction.setOnlineOriginStation(null);
		emsIngateRestriction.setEquipmentInit(null);
		emsIngateRestriction.setCorporateCustomerId(1234L);

		emsIngateRestriction.setActive("M");

		assertNotNull(emsIngateRestriction.getCreateExtensionSchema());
		when(terminalRepository.existsByTerminalIdAndExpiredDateIsNull(emsIngateRestriction.getIngateTerminalId())).thenReturn(true);
		when(corporateCustomerRepository.existsById(emsIngateRestriction.getCorporateCustomerId())).thenReturn(true);

		assertNotEquals(emsIngateRestriction.getActive(), "T");
		assertNotEquals(emsIngateRestriction.getActive(), "F");
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> emsIngateRestrictionService.insertRestriction(emsIngateRestriction,header));
		assertEquals("'active' value must be either 'T' or 'F'", exception.getMessage());
	}

	@Test
	void testInsertAllocationGetTempInd() {
//		emsIngateRestriction.setOnlineDestinationStation(station);
		emsIngateRestriction.setOnlineOriginStation(null);
		emsIngateRestriction.setEquipmentInit(null);
		emsIngateRestriction.setTemporaryIndicator("M");
		emsIngateRestriction.setCorporateCustomerId(1234L);


		assertNotNull(emsIngateRestriction.getCreateExtensionSchema());
		when(terminalRepository.existsByTerminalIdAndExpiredDateIsNull(emsIngateRestriction.getIngateTerminalId())).thenReturn(true);
		when(corporateCustomerRepository.existsById(emsIngateRestriction.getCorporateCustomerId())).thenReturn(true);

		assertNotEquals(emsIngateRestriction.getTemporaryIndicator(), "T");
		assertNotEquals(emsIngateRestriction.getTemporaryIndicator(), "F");
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> emsIngateRestrictionService.insertRestriction(emsIngateRestriction,header));
		assertEquals("'temporaryIndicator' value must be either 'T' or 'F'", exception.getMessage());
	}

	@Test
	void testInsertAllocationGetLoadCode() {
		emsIngateRestriction.setOnlineOriginStation(null);
		emsIngateRestriction.setEquipmentInit(null);
		emsIngateRestriction.setLoadEmptyCode("M");
		emsIngateRestriction.setCorporateCustomerId(1234L);


		assertNotNull(emsIngateRestriction.getCreateExtensionSchema());
		when(terminalRepository.existsByTerminalIdAndExpiredDateIsNull(emsIngateRestriction.getIngateTerminalId())).thenReturn(true);
		when(corporateCustomerRepository.existsById(emsIngateRestriction.getCorporateCustomerId())).thenReturn(true);

		assertNotEquals(emsIngateRestriction.getLoadEmptyCode(), "L");
		assertNotEquals(emsIngateRestriction.getLoadEmptyCode(), "E");
		assertNotEquals(emsIngateRestriction.getLoadEmptyCode(), "B");
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> emsIngateRestrictionService.insertRestriction(emsIngateRestriction,header));
		assertEquals("'loadEmptyCode' value must be 'L', 'E' or 'B'", exception.getMessage());
	}

	@Test
	void testInsertAllocationGetLengthRestrict() {
		emsIngateRestriction.setOnlineOriginStation(null);
		emsIngateRestriction.setEquipmentInit(null);
		emsIngateRestriction.setEquipmentLength(100);
		emsIngateRestriction.setCorporateCustomerId(1234L);

		assertNotNull(emsIngateRestriction.getCreateExtensionSchema());
		when(terminalRepository.existsByTerminalIdAndExpiredDateIsNull(emsIngateRestriction.getIngateTerminalId())).thenReturn(true);
		when(corporateCustomerRepository.existsById(emsIngateRestriction.getCorporateCustomerId())).thenReturn(true);

		assertNotEquals(emsIngateRestriction.getEquipmentLength(), 240);
		assertNotEquals(emsIngateRestriction.getEquipmentLength(), 336);
		assertNotEquals(emsIngateRestriction.getEquipmentLength(), 480);
		assertNotEquals(emsIngateRestriction.getEquipmentLength(), 540);
		assertNotEquals(emsIngateRestriction.getEquipmentLength(), 576);
		assertNotEquals(emsIngateRestriction.getEquipmentLength(), 636);
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> emsIngateRestrictionService.insertRestriction(emsIngateRestriction,header));
		assertEquals("'equipmentLength' value must be '240', '336', '480', '540', '576', '636' or null", exception.getMessage());
	}

	@Test
	void testInsertAllocationGetEqTp() {
		emsIngateRestriction.setOnlineOriginStation(null);
		emsIngateRestriction.setEquipmentInit(null);
		emsIngateRestriction.setCorporateCustomerId(1234L);
		eqTp = new ArrayList<>();
		eqTp.add(null);
		emsIngateRestriction.setEquipmentTypes(eqTp);

		assertNotNull(emsIngateRestriction.getCreateExtensionSchema());
		when(terminalRepository.existsByTerminalIdAndExpiredDateIsNull(emsIngateRestriction.getIngateTerminalId())).thenReturn(true);
		when(corporateCustomerRepository.existsById(emsIngateRestriction.getCorporateCustomerId())).thenReturn(true);

		assertTrue(emsIngateRestriction.getEquipmentTypes().isEmpty());
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> emsIngateRestrictionService.insertRestriction(emsIngateRestriction,header));
		assertEquals("Any One Equipment Type value should be provided!", exception.getMessage());
	}

	@Test
	void testInsertAllocationGetEqTpNull() {
		emsIngateRestriction.setOnlineOriginStation(null);
		emsIngateRestriction.setEquipmentInit(null);
		emsIngateRestriction.setCorporateCustomerId(1234L);

		eqTp.add(EquipmentType.CHASSIS);
		emsIngateRestriction.setEquipmentTypes(eqTp);
		lengthRestriction = new ArrayList<>();
		lengthRestriction.add(null);
		emsIngateRestriction.setEmsEquipmentLengthRestrictions(lengthRestriction);

		assertNotNull(emsIngateRestriction.getCreateExtensionSchema());
		when(terminalRepository.existsByTerminalIdAndExpiredDateIsNull(emsIngateRestriction.getIngateTerminalId())).thenReturn(true);
		when(corporateCustomerRepository.existsById(emsIngateRestriction.getCorporateCustomerId())).thenReturn(true);

		assertTrue(emsIngateRestriction.getEmsEquipmentLengthRestrictions().isEmpty());
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> emsIngateRestrictionService.insertRestriction(emsIngateRestriction,header));
		assertEquals("Any One Equipment Length value should be provided!", exception.getMessage());
	}

	@Test
	void testInsertAllocationGetLengthRestrictAll() {
		emsIngateRestriction.setOnlineOriginStation(null);
		emsIngateRestriction.setEquipmentInit(null);
		emsIngateRestriction.setCorporateCustomerId(1234L);

		eqTp.add(EquipmentType.CHASSIS);
		emsIngateRestriction.setEquipmentTypes(eqTp);
		lengthRestriction.add(EMSEquipmentLengthRestriction.RESTRICT_20_FT);
		lengthRestriction.add(EMSEquipmentLengthRestriction.RESTRICT_ALL);
		emsIngateRestriction.setEmsEquipmentLengthRestrictions(lengthRestriction);

		assertNotNull(emsIngateRestriction.getCreateExtensionSchema());
		when(terminalRepository.existsByTerminalIdAndExpiredDateIsNull(emsIngateRestriction.getIngateTerminalId())).thenReturn(true);
		when(corporateCustomerRepository.existsById(emsIngateRestriction.getCorporateCustomerId())).thenReturn(true);

		assertTrue(emsIngateRestriction.getEmsEquipmentLengthRestrictions().size()>1);
		assertTrue(emsIngateRestriction.getEmsEquipmentLengthRestrictions().contains(EMSEquipmentLengthRestriction.RESTRICT_ALL));
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> emsIngateRestrictionService.insertRestriction(emsIngateRestriction,header));
		assertEquals("EMS Length Restrictions field requires either 'RESTRICT_ALL' or Size", exception.getMessage());
	}
}
