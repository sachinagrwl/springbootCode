package com.nscorp.obis.services;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.domain.*;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.repository.CorporateCustomerRepository;
import com.nscorp.obis.repository.EMSIngateAllocationRepository;
import com.nscorp.obis.repository.StationRepository;
import com.nscorp.obis.repository.TerminalRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class EMSIngateAllocationServiceTest {

	@InjectMocks
	EMSIngateAllocationServiceImpl emsIngateAllocationService;

	@Mock
	EMSIngateAllocationRepository emsIngateAllocationRepository;

	@Mock
	TerminalRepository terminalRepository;

	@Mock
	StationRepository stationRepository;

	@Mock
	CorporateCustomerRepository corporateCustomerRepository;

	EMSIngateAllocation emsIngateAllocation;
	List<EMSIngateAllocation> emsIngateAllocationList;
	Station station;
	Page<EMSIngateAllocation> page;
	String domestic = null;
	String international = null;
	String premium = null;
	List<String> lob;
	List<String> trafficType;
	Long ingateTerminalId = 42103633022158L;

	List<EquipmentType> eqTp;
	List<DayOfWeek> days;
	List<LineOfBusiness> lineOfBusinesses;
	List<TrafficType> trafficTypes;
	String local = null;
	String steelWheel = null;
	String rubberTire = null;

	List<EMSEquipmentLengthRestriction> lengthRestriction;
//	Date startDate = new Date(2020-02-17);
//	Date endDate = new Date(2020-04-05);
	LocalDate startDate = new Date(2020-02-17).toLocalDate();
	LocalDate endDate = new Date(2020-04-05).toLocalDate();
	LocalDate currentDate = null;
	LocalTime startTime = LocalTime.parse("00:00:00");
	LocalTime endTime = LocalTime.parse("23:59:59");
	Timestamp ts = Timestamp.valueOf(String.format("%04d-%02d-%02d 00:00:00", 2022, 07, 01));
	Timestamp ts1 = Timestamp.valueOf(String.format("%04d-%02d-%02d 00:00:00", 2022, 05, 01));
	Boolean includeInactive;
	Boolean includeExpired;
	Boolean includePermanent;

	String userid = "Test";
	String extensionschema =  "Test";

	Map<String, String> header;
	int pageNumber;
	int pageSize;
	Pageable pageable ;
	String[] sort;

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		emsIngateAllocation = new EMSIngateAllocation();
		emsIngateAllocationList = new ArrayList<>();
		lob = new ArrayList<>();
		trafficType = new ArrayList<>();
		lineOfBusinesses = new ArrayList<>();
		trafficTypes = new ArrayList<>();
		eqTp = new ArrayList<>();
		days = new ArrayList<>();
		lengthRestriction = new ArrayList<>();

		emsIngateAllocation.setTimsId(1327341890048L);
		emsIngateAllocation.setIngateTerminalId(ingateTerminalId);
		emsIngateAllocation.setUversion("8");
		emsIngateAllocation.setOnlineOriginStation(null);
		emsIngateAllocation.setOnlineDestinationStation(null);
		emsIngateAllocation.setOfflineDestinationStation(null);
		emsIngateAllocation.setEquipmentInit("EMHU");
		emsIngateAllocation.setEquipmentLowestNumber(null);
		emsIngateAllocation.setEquipmentHighestNumber(null);
		emsIngateAllocation.setCorporateCustomerId(null);
		emsIngateAllocation.setLoadEmptyCode("E");
		emsIngateAllocation.setEquipmentLength(null);
		emsIngateAllocation.setGrossWeight(null);
		lob.add("DOMESTIC");
		lob.add("INTERNATIONAL");
		lob.add("PREMIUM");
		lineOfBusinesses.add(LineOfBusiness.DOMESTIC);
		domestic = "T";
		international = "T";
		premium = "T";
		emsIngateAllocation.setWayBillRoute("N");
		emsIngateAllocation.setHazardousIndicator(null);
		emsIngateAllocation.setWeightQualifier(null);
		emsIngateAllocation.setTotalIngatesAllowed(99999999);
		emsIngateAllocation.setNumberIngated(null);
		emsIngateAllocation.setActive("T");
		emsIngateAllocation.setAllotmentTypeCode("D");
		emsIngateAllocation.setStartDate(startDate);
		emsIngateAllocation.setStartTime(startTime);
		emsIngateAllocation.setEndDate(endDate);
		emsIngateAllocation.setEndTime(endTime);
		emsIngateAllocation.setCreateDateTime(ts);
		emsIngateAllocation.setCreateUserId("test");
		emsIngateAllocation.setCreateExtensionSchema("IMS08120");
		emsIngateAllocation.setUpdateDateTime(ts1);
		emsIngateAllocation.setUpdateUserId("test");
		emsIngateAllocation.setUpdateExtensionSchema("NGTNMR");
		days.add(DayOfWeek.SUN);
		days.add(DayOfWeek.MON);
		days.add(DayOfWeek.TUE);
		days.add(DayOfWeek.WED);
		days.add(DayOfWeek.THU);
		days.add(DayOfWeek.FRI);
		days.add(DayOfWeek.SAT);
		emsIngateAllocation.setRestrictedDays(days);
		emsIngateAllocation.setReeferIndicator(null);
		trafficType.add("LOCAL");
		trafficType.add("STEEL_WHEEL");
		trafficType.add("RUBBER_TIRE");
		trafficTypes.add(TrafficType.LOCAL);
		local = "T";
		steelWheel = "T";
		rubberTire = "T";
		emsIngateAllocation.setReeferIndicator(null);
		emsIngateAllocation.setTemporaryIndicator("T");

		emsIngateAllocationList.add(emsIngateAllocation);
		
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

		includeInactive = true;
		includePermanent = true;
		includeExpired = true;

		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");

		pageNumber = 1;
		pageSize = 10;
		sort = new String[]{"ingateTerminalId,asc"};
		pageable = PageRequest.of(pageNumber - 1, pageSize,Sort.by(sort));
	}

	@AfterEach
	void tearDown() throws Exception {
		emsIngateAllocationList = null;
		emsIngateAllocation = null;
	}

	@Test
	void testSearchIngateAllocation() {
		page = new PageImpl<>(emsIngateAllocationList, PageRequest.of(0, 10), 100L);
		when(emsIngateAllocationRepository.searchAllIngateAllocations(Mockito.any(), Mockito.any(), Mockito.any(),
				Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),
				Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),
				Mockito.any(), Mockito.any())).thenReturn(page);
		Page<EMSIngateAllocation> allIngateAllocations = emsIngateAllocationService.searchIngateAllocation(
				emsIngateAllocation.getIngateTerminalId(), emsIngateAllocation.getOnlineOriginStation(),
				emsIngateAllocation.getOnlineDestinationStation(),
				emsIngateAllocation.getOfflineDestinationStation(), emsIngateAllocation.getCorporateCustomerId(),
				lob, emsIngateAllocation.getWayBillRoute(), trafficType, emsIngateAllocation.getStartDate(),
				emsIngateAllocation.getEndDate(), currentDate, includeInactive, includeExpired, includePermanent,
				pageable);
		assertEquals(allIngateAllocations,page);
	}

	@Test
	void testSearchIngateAllocationInactive() {
		page = new PageImpl<>(emsIngateAllocationList, PageRequest.of(0, 10), 100L);
		pageable = PageRequest.of(0, 10,null);
		includeInactive = true;
		includePermanent = null;
		includeExpired = null;
		when(emsIngateAllocationRepository.searchAllIngateAllocations(Mockito.any(), Mockito.any(), Mockito.any(),
				Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),
				Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),
				Mockito.any(), Mockito.any())).thenReturn(page);
		Page<EMSIngateAllocation> allIngateAllocations = emsIngateAllocationService.searchIngateAllocation(
				emsIngateAllocation.getIngateTerminalId(), emsIngateAllocation.getOnlineOriginStation(),
				emsIngateAllocation.getOnlineDestinationStation(),
				emsIngateAllocation.getOfflineDestinationStation(), emsIngateAllocation.getCorporateCustomerId(),
				lob, emsIngateAllocation.getWayBillRoute(), trafficType, emsIngateAllocation.getStartDate(),
				emsIngateAllocation.getEndDate(), currentDate, includeInactive, includeExpired, includePermanent,
				pageable);
		assertEquals(allIngateAllocations.getContent(),page.getContent());
	}

	@Test
	void testSearchIngateAllocationPermanent() {
		page = new PageImpl<>(emsIngateAllocationList, PageRequest.of(0, 10), 100L);
		pageable = PageRequest.of(0, 10,null);
		includeInactive = null;
		includePermanent = true;
		includeExpired = null;
		when(emsIngateAllocationRepository.searchAllIngateAllocations(Mockito.any(), Mockito.any(), Mockito.any(),
				Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),
				Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),
				Mockito.any(), Mockito.any())).thenReturn(page);
		Page<EMSIngateAllocation> allIngateAllocations = emsIngateAllocationService.searchIngateAllocation(
				emsIngateAllocation.getIngateTerminalId(), emsIngateAllocation.getOnlineOriginStation(),
				emsIngateAllocation.getOnlineDestinationStation(),
				emsIngateAllocation.getOfflineDestinationStation(), emsIngateAllocation.getCorporateCustomerId(),
				lob, emsIngateAllocation.getWayBillRoute(), trafficType, emsIngateAllocation.getStartDate(),
				emsIngateAllocation.getEndDate(), currentDate, includeInactive, includeExpired, includePermanent,
				pageable);
		assertEquals(allIngateAllocations.getContent(),page.getContent());
	}

	@Test
	void testSearchIngateAllocationExpired() {
		page = new PageImpl<>(emsIngateAllocationList, PageRequest.of(0, 10), 100L);
		pageable = PageRequest.of(0, 10,null);
		includeInactive = null;
		includePermanent = null;
		includeExpired = true;
		when(emsIngateAllocationRepository.searchAllIngateAllocations(Mockito.any(), Mockito.any(), Mockito.any(),
				Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),
				Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),
				Mockito.any(), Mockito.any())).thenReturn(page);
		Page<EMSIngateAllocation> allIngateAllocations = emsIngateAllocationService.searchIngateAllocation(
				emsIngateAllocation.getIngateTerminalId(), emsIngateAllocation.getOnlineOriginStation(),
				emsIngateAllocation.getOnlineDestinationStation(),
				emsIngateAllocation.getOfflineDestinationStation(), emsIngateAllocation.getCorporateCustomerId(),
				lob, emsIngateAllocation.getWayBillRoute(), trafficType, emsIngateAllocation.getStartDate(),
				emsIngateAllocation.getEndDate(), currentDate, includeInactive, includeExpired, includePermanent,
				pageable);
		assertEquals(allIngateAllocations.getContent(),page.getContent());
	}

	@Test
	void testSearchIngateAllocationInactivePermanent() {
		page = new PageImpl<>(emsIngateAllocationList, PageRequest.of(0, 10), 100L);
		pageable = PageRequest.of(0, 10,null);
		includeInactive = true;
		includePermanent = true;
		includeExpired = null;
		when(emsIngateAllocationRepository.searchAllIngateAllocations(Mockito.any(), Mockito.any(), Mockito.any(),
				Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),
				Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),
				Mockito.any(), Mockito.any())).thenReturn(page);
		Page<EMSIngateAllocation> allIngateAllocations = emsIngateAllocationService.searchIngateAllocation(
				emsIngateAllocation.getIngateTerminalId(), emsIngateAllocation.getOnlineOriginStation(),
				emsIngateAllocation.getOnlineDestinationStation(),
				emsIngateAllocation.getOfflineDestinationStation(), emsIngateAllocation.getCorporateCustomerId(),
				lob, emsIngateAllocation.getWayBillRoute(), trafficType, emsIngateAllocation.getStartDate(),
				emsIngateAllocation.getEndDate(), currentDate, includeInactive, includeExpired, includePermanent,
				pageable);
		assertEquals(allIngateAllocations.getContent(),page.getContent());
	}

	@Test
	void testSearchIngateAllocationInactiveExpired() {
		page = new PageImpl<>(emsIngateAllocationList, PageRequest.of(0, 10), 100L);
		pageable = PageRequest.of(0, 10,null);
		includeInactive = true;
		includePermanent = null;
		includeExpired = true;
		when(emsIngateAllocationRepository.searchAllIngateAllocations(Mockito.any(), Mockito.any(), Mockito.any(),
				Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),
				Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),
				Mockito.any(), Mockito.any())).thenReturn(page);
		Page<EMSIngateAllocation> allIngateAllocations = emsIngateAllocationService.searchIngateAllocation(
				emsIngateAllocation.getIngateTerminalId(), emsIngateAllocation.getOnlineOriginStation(),
				emsIngateAllocation.getOnlineDestinationStation(),
				emsIngateAllocation.getOfflineDestinationStation(), emsIngateAllocation.getCorporateCustomerId(),
				lob, emsIngateAllocation.getWayBillRoute(), trafficType, emsIngateAllocation.getStartDate(),
				emsIngateAllocation.getEndDate(), currentDate, includeInactive, includeExpired, includePermanent,
				pageable);
		assertEquals(allIngateAllocations.getContent(),page.getContent());
	}

	@Test
	void testSearchIngateAllocationPermanentExpired() {
		page = new PageImpl<>(emsIngateAllocationList, PageRequest.of(0, 10), 100L);
		pageable = PageRequest.of(0, 10,null);
		includeInactive = null;
		includePermanent = true;
		includeExpired = true;
		when(emsIngateAllocationRepository.searchAllIngateAllocations(Mockito.any(), Mockito.any(), Mockito.any(),
				Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),
				Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),
				Mockito.any(), Mockito.any())).thenReturn(page);
		Page<EMSIngateAllocation> allIngateAllocations = emsIngateAllocationService.searchIngateAllocation(
				emsIngateAllocation.getIngateTerminalId(), emsIngateAllocation.getOnlineOriginStation(),
				emsIngateAllocation.getOnlineDestinationStation(),
				emsIngateAllocation.getOfflineDestinationStation(), emsIngateAllocation.getCorporateCustomerId(),
				lob, emsIngateAllocation.getWayBillRoute(), trafficType, emsIngateAllocation.getStartDate(),
				emsIngateAllocation.getEndDate(), currentDate, includeInactive, includeExpired, includePermanent,
				pageable);
		assertEquals(allIngateAllocations.getContent(),page.getContent());
	}

	@Test
	void testAddAllocation() {
		emsIngateAllocation.setOnlineOriginStation(station);
		emsIngateAllocation.setOnlineDestinationStation(station);
		emsIngateAllocation.setCorporateCustomerId((Long) 123456L);
		eqTp.add(EquipmentType.CONTAINER);
		emsIngateAllocation.setEquipmentTypes(eqTp);
		lengthRestriction.add(EMSEquipmentLengthRestriction.RESTRICT_ALL);
		emsIngateAllocation.setEmsEquipmentLengthRestrictions(lengthRestriction);

		assertNotNull(emsIngateAllocation.getCreateExtensionSchema());
		assertNotNull(emsIngateAllocation.getIngateTerminalId());
		when(terminalRepository.existsByTerminalIdAndExpiredDateIsNull(Mockito.any())).thenReturn(true);
		
		assertNotEquals(emsIngateAllocation.getIngateTerminalId(), CommonConstants.NON_TERMINAL_SPECIFIC);
		assertNotNull(emsIngateAllocation.getOnlineOriginStation());

		assertNotNull(emsIngateAllocation.getOnlineDestinationStation());
		when(stationRepository.existsByTermIdAndExpiredDateIsNull(emsIngateAllocation.getOnlineDestinationStation().getTermId())).thenReturn(true);

		when(corporateCustomerRepository.existsById(emsIngateAllocation.getCorporateCustomerId())).thenReturn(true);

		assertNull(emsIngateAllocation.getOfflineDestinationStation());
		assertNotNull(emsIngateAllocation.getCorporateCustomerId());
		assertNotNull(emsIngateAllocation.getWayBillRoute());
		assertNotNull(emsIngateAllocation.getEmsEquipmentLengthRestrictions());

		when(emsIngateAllocationRepository.findByTimsId(Mockito.any())).thenReturn(emsIngateAllocation);
		when(emsIngateAllocationRepository.save(Mockito.any())).thenReturn(emsIngateAllocation);
		when(terminalRepository.getStationXrfId(Mockito.any())).thenReturn(11668022820698L);
		when(stationRepository.findById(Mockito.any())).thenReturn(Optional.of(station));
		EMSIngateAllocation allocationAdded = emsIngateAllocationService.insertAllocation(emsIngateAllocation, header);
		assertNotNull(allocationAdded);
	}

	@Test
	void testUpdateAllocation() {
		assertNotNull(emsIngateAllocation.getTimsId());
		when(emsIngateAllocationRepository.existsByTimsId(Mockito.any())).thenReturn(true);
		emsIngateAllocation.setOnlineOriginStation(station);
		emsIngateAllocation.setOnlineDestinationStation(station);
		emsIngateAllocation.setCorporateCustomerId((Long) 123456L);
		eqTp.add(EquipmentType.CONTAINER);
		emsIngateAllocation.setEquipmentTypes(eqTp);
		lengthRestriction.add(EMSEquipmentLengthRestriction.RESTRICT_ALL);
		emsIngateAllocation.setEmsEquipmentLengthRestrictions(lengthRestriction);

		assertNotNull(emsIngateAllocation.getCreateExtensionSchema());
		assertNotNull(emsIngateAllocation.getIngateTerminalId());
		when(terminalRepository.existsByTerminalIdAndExpiredDateIsNull(Mockito.any())).thenReturn(true);
		assertNotEquals(emsIngateAllocation.getIngateTerminalId(), CommonConstants.NON_TERMINAL_SPECIFIC);
		assertNotNull(emsIngateAllocation.getOnlineOriginStation());
		when(corporateCustomerRepository.existsById(emsIngateAllocation.getCorporateCustomerId())).thenReturn(true);

		assertNull(emsIngateAllocation.getOfflineDestinationStation());
		assertNotNull(emsIngateAllocation.getCorporateCustomerId());
		assertNotNull(emsIngateAllocation.getWayBillRoute());
		assertNotNull(emsIngateAllocation.getEmsEquipmentLengthRestrictions());

		when(emsIngateAllocationRepository.findByTimsId(Mockito.any())).thenReturn(emsIngateAllocation);
		when(terminalRepository.getStationXrfId(Mockito.any())).thenReturn(11668022820698L);
		when(stationRepository.findById(Mockito.any())).thenReturn(Optional.of(station));
		when(stationRepository.existsByTermIdAndExpiredDateIsNull(Mockito.any())).thenReturn(true);
		when(emsIngateAllocationRepository.save(Mockito.any())).thenReturn(emsIngateAllocation);
		EMSIngateAllocation allocationAdded = emsIngateAllocationService.updateAllocation(emsIngateAllocation, header);
		assertNotNull(allocationAdded);
		
	}
	
	@Test
	void testInsertExtensionSchema() {
		header.put("extensionschema", null);
		assertNull(header.get(CommonConstants.EXTENSION_SCHEMA));
		NullPointerException exception = assertThrows(NullPointerException.class,
				() -> emsIngateAllocationService.insertAllocation(emsIngateAllocation, header));
		assertEquals("Extension Schema should not be null, empty or blank", exception.getMessage());
	}
	
	@Test
	void testUpdateStartDateRecordNotAddedException() {
		emsIngateAllocation.setAllotmentType(EMSAllotmentType.FIXED);
		emsIngateAllocation.setStartDate(null);
		emsIngateAllocation.setOnlineOriginStation(null);
		when(emsIngateAllocationRepository.existsByTimsId(Mockito.any())).thenReturn(true);
		assertNotNull(emsIngateAllocation.getIngateTerminalId());
		when(terminalRepository.existsByTerminalIdAndExpiredDateIsNull(Mockito.any())).thenReturn(true);
		assertNotNull(emsIngateAllocation.getIngateTerminalId());
		assertNotEquals(emsIngateAllocation.getIngateTerminalId(), (Long)99999999999999L);
		assertNotEquals(emsIngateAllocation.getOnlineOriginStation(), terminalRepository.getStationXrfId(emsIngateAllocation.getIngateTerminalId()));
		when(stationRepository.existsByTermIdAndExpiredDateIsNull(Mockito.any())).thenReturn(true);
		
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> emsIngateAllocationService.updateAllocation(emsIngateAllocation,header));
		assertEquals("For AllotmentType 'FIXED', Both 'StartDate' & 'EndDate' are required", exception.getMessage());
	
	}
	
	@Test
	void testUpdateEndDateRecordNotAddedException() {
		emsIngateAllocation.setAllotmentType(EMSAllotmentType.FIXED);
		emsIngateAllocation.setEndDate(null);
		emsIngateAllocation.setOnlineOriginStation(null);
		when(emsIngateAllocationRepository.existsByTimsId(Mockito.any())).thenReturn(true);
		assertNotNull(emsIngateAllocation.getIngateTerminalId());
		when(terminalRepository.existsByTerminalIdAndExpiredDateIsNull(Mockito.any())).thenReturn(true);
		assertNotNull(emsIngateAllocation.getIngateTerminalId());
		assertNotEquals(emsIngateAllocation.getIngateTerminalId(), (Long)99999999999999L);
		assertNotEquals(emsIngateAllocation.getOnlineOriginStation(), terminalRepository.getStationXrfId(emsIngateAllocation.getIngateTerminalId()));
		when(stationRepository.existsByTermIdAndExpiredDateIsNull(Mockito.any())).thenReturn(true);
		
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> emsIngateAllocationService.updateAllocation(emsIngateAllocation,header));
		assertEquals("For AllotmentType 'FIXED', Both 'StartDate' & 'EndDate' are required", exception.getMessage());
	
	}
	
	@Test
	void testInsertEquipmentInitRecordNotAddedException() {
		emsIngateAllocation.setOnlineOriginStation(null);
		emsIngateAllocation.setEquipmentInit("123");

		assertNotNull(emsIngateAllocation.getCreateExtensionSchema());
		when(terminalRepository.existsByTerminalIdAndExpiredDateIsNull(emsIngateAllocation.getIngateTerminalId())).thenReturn(true);
		assertNotEquals(emsIngateAllocation.getEquipmentInit(),"^[a-zA-Z]*$");
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> emsIngateAllocationService.insertAllocation(emsIngateAllocation, header));
		assertEquals("Equipment Init should have only character values", exception.getMessage());
	}

	// Check Ingate terminal is valid or not
	@Test
	void testUpdateAllocationTermIdNoRecordsFoundException() {
		when(emsIngateAllocationRepository.existsByTimsId(Mockito.any())).thenReturn(true);
		assertNotNull(emsIngateAllocation.getIngateTerminalId());
		when(terminalRepository.existsByTerminalIdAndExpiredDateIsNull(Mockito.any())).thenReturn(false);
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> emsIngateAllocationService.updateAllocation(emsIngateAllocation,header));
		assertEquals("Ingate Terminal with Id 42103633022158 not found!", exception.getMessage());
	}

	//Auto populate online origin using STN_XRF ID
	@Test
	void testUpdateAllocationTerminalRecordNotAddedException() {
		emsIngateAllocation.setIngateTerminalId(10000L);
		emsIngateAllocation.setOnlineOriginStation(station);
		assertNotNull(emsIngateAllocation.getCreateExtensionSchema());
		when(terminalRepository.getStationXrfId(Mockito.any())).thenReturn(10L);
		when(terminalRepository.existsByTerminalIdAndExpiredDateIsNull(Mockito.any())).thenReturn(true);
		when(stationRepository.findById(Mockito.any())).thenReturn(Optional.of(station));
		
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> emsIngateAllocationService.insertAllocation(emsIngateAllocation, header));
		assertEquals("Online Origin station with Id 11668022820698 is either not valid or not found in 'Terminal' table!", exception.getMessage());
	}

	//Check for Non-terminal
	@Test
	void testUpdateAllocationNonTerminalRecordNotAddedException() {
		emsIngateAllocation.setIngateTerminalId(CommonConstants.NON_TERMINAL_SPECIFIC);
		emsIngateAllocation.setOnlineOriginStation(station);

		assertNotNull(emsIngateAllocation.getCreateExtensionSchema());
		when(terminalRepository.existsByTerminalIdAndExpiredDateIsNull(emsIngateAllocation.getIngateTerminalId())).thenReturn(true);
		when(stationRepository.findById(Mockito.any())).thenReturn(Optional.of(station));
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> emsIngateAllocationService.insertAllocation(emsIngateAllocation, header));
		assertEquals("Online Origin Station Id should be null for Non-Terminal specific EMS Ingate Restriction!", exception.getMessage());
		emsIngateAllocation.setIngateTerminalId(100L);
	}

	@Test
	void testInsertAllocationDestinationNoRecordsFoundException() {
		emsIngateAllocation.setIngateTerminalId(100L);
		emsIngateAllocation.setOnlineDestinationStation(station);
		when(terminalRepository.existsByTerminalIdAndExpiredDateIsNull(emsIngateAllocation.getIngateTerminalId())).thenReturn(true);
		when(stationRepository.existsByTermIdAndExpiredDateIsNull(Mockito.any())).thenReturn(false);
		when(stationRepository.findById(Mockito.any())).thenReturn(Optional.of(station));
		NoRecordsFoundException exception1 = assertThrows(NoRecordsFoundException.class,
				() -> emsIngateAllocationService.insertAllocation(emsIngateAllocation, header));
		assertEquals("Online Destination Station with Id 11668022820698 not found!", exception1.getMessage());
	}
	
	@Test
	void testInsertAllocationOffDestinationNoRecordsFoundException() {
		emsIngateAllocation.setIngateTerminalId(100L);
		emsIngateAllocation.setOfflineDestinationStation(station);
		when(terminalRepository.existsByTerminalIdAndExpiredDateIsNull(emsIngateAllocation.getIngateTerminalId())).thenReturn(true);
		when(stationRepository.existsByTermIdAndExpiredDateIsNull(Mockito.any())).thenReturn(false);
		when(stationRepository.findById(Mockito.any())).thenReturn(Optional.of(station));

		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> emsIngateAllocationService.insertAllocation(emsIngateAllocation, header));
		assertEquals("Offline Destination Station with Id 11668022820698 not found!", exception.getMessage());
	}

	//Check Corporate customerId is valid or not
	@Test
	void testUpdateAllocationCorpCustNoRecordsFoundException() {
		emsIngateAllocation.setOnlineOriginStation(null);
		emsIngateAllocation.setEquipmentInit(null);
		emsIngateAllocation.setCorporateCustomerId((Long) 123456L);

		assertNotNull(emsIngateAllocation.getCreateExtensionSchema());
		when(terminalRepository.existsByTerminalIdAndExpiredDateIsNull(emsIngateAllocation.getIngateTerminalId())).thenReturn(true);
		assertNotNull(emsIngateAllocation.getCorporateCustomerId());
		when(corporateCustomerRepository.existsById(emsIngateAllocation.getCorporateCustomerId())).thenReturn(false);
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> emsIngateAllocationService.insertAllocation(emsIngateAllocation,header));
		assertEquals("Corporate Customer Id : 123456 not found!", exception.getMessage());
	}

	@Test
	void testInsertAllocationLOBRecordNotAddedException() {
		emsIngateAllocation.setOnlineOriginStation(null);
		emsIngateAllocation.setEquipmentInit(null);

		assertNotNull(emsIngateAllocation.getCreateExtensionSchema());
		when(terminalRepository.existsByTerminalIdAndExpiredDateIsNull(emsIngateAllocation.getIngateTerminalId())).thenReturn(true);
		assertTrue(emsIngateAllocation.getLineOfBusinesses().isEmpty());
		when(corporateCustomerRepository.existsById(emsIngateAllocation.getCorporateCustomerId())).thenReturn(false);
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> emsIngateAllocationService.insertAllocation(emsIngateAllocation,header));
		assertEquals("A 'Corporate Shipper(Customer)' or a 'Primary Line Of Business' value is required!", exception.getMessage());
	}

	@Test
	void testInsertAllocationWBRRecordNotAddedException() {
		emsIngateAllocation.setOnlineOriginStation(null);
		emsIngateAllocation.setEquipmentInit(null);
		emsIngateAllocation.setWayBillRoute(null);
		emsIngateAllocation.setCorporateCustomerId(1234L);

		assertNotNull(emsIngateAllocation.getCreateExtensionSchema());
		when(terminalRepository.existsByTerminalIdAndExpiredDateIsNull(emsIngateAllocation.getIngateTerminalId())).thenReturn(true);
		when(terminalRepository.getStationXrfId(Mockito.any())).thenReturn(123000L);
		when(corporateCustomerRepository.existsById(emsIngateAllocation.getCorporateCustomerId())).thenReturn(true);

		assertNull(emsIngateAllocation.getWayBillRoute());
		assertTrue(emsIngateAllocation.getTrafficTypes().isEmpty());
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> emsIngateAllocationService.insertAllocation(emsIngateAllocation,header));
		assertEquals("A 'Way Bill Route' or a 'Traffic Type' value is required!", exception.getMessage());
	}

	@Test
	void testInsertAllocationActiveRecordNotAddedException() {
		emsIngateAllocation.setOnlineOriginStation(null);
		emsIngateAllocation.setEquipmentInit(null);
		emsIngateAllocation.setCorporateCustomerId(1234L);

		emsIngateAllocation.setActive("M");

		assertNotNull(emsIngateAllocation.getCreateExtensionSchema());
		when(terminalRepository.existsByTerminalIdAndExpiredDateIsNull(emsIngateAllocation.getIngateTerminalId())).thenReturn(true);
		when(corporateCustomerRepository.existsById(emsIngateAllocation.getCorporateCustomerId())).thenReturn(true);

		assertNotEquals(emsIngateAllocation.getActive(), "T");
		assertNotEquals(emsIngateAllocation.getActive(), "F");
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> emsIngateAllocationService.insertAllocation(emsIngateAllocation,header));
		assertEquals("'active' value must be either 'T' or 'F'", exception.getMessage());
	}

	@Test
	void testUpdateAllocationTempIndRecordNotAddedException() {
		emsIngateAllocation.setOnlineOriginStation(null);
		emsIngateAllocation.setEquipmentInit(null);
		emsIngateAllocation.setTemporaryIndicator("M");
		emsIngateAllocation.setCorporateCustomerId(1234L);


		assertNotNull(emsIngateAllocation.getCreateExtensionSchema());
		when(terminalRepository.existsByTerminalIdAndExpiredDateIsNull(emsIngateAllocation.getIngateTerminalId())).thenReturn(true);
		when(corporateCustomerRepository.existsById(emsIngateAllocation.getCorporateCustomerId())).thenReturn(true);

		assertNotEquals(emsIngateAllocation.getTemporaryIndicator(), "T");
		assertNotEquals(emsIngateAllocation.getTemporaryIndicator(), "F");
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> emsIngateAllocationService.insertAllocation(emsIngateAllocation,header));
		assertEquals("'temporaryIndicator' value must be either 'T' or 'F'", exception.getMessage());
	}

	@Test
	void testUpdateAllocationLoadRecordNotAddedException() {
		emsIngateAllocation.setIngateTerminalId((Long)99999999999999L);
		emsIngateAllocation.setCorporateCustomerId((Long) 123456L);
		emsIngateAllocation.setOnlineOriginStation(null);
		emsIngateAllocation.setLoadEmptyCode("M");
		when(emsIngateAllocationRepository.existsByTimsId(Mockito.any())).thenReturn(true);
		assertNotNull(emsIngateAllocation.getIngateTerminalId());
		when(terminalRepository.existsByTerminalIdAndExpiredDateIsNull(Mockito.any())).thenReturn(true);
		assertNotNull(emsIngateAllocation.getIngateTerminalId());
		assertEquals(emsIngateAllocation.getIngateTerminalId(), (Long) CommonConstants.NON_TERMINAL_SPECIFIC);
		assertNull(emsIngateAllocation.getOnlineOriginStation());
		when(corporateCustomerRepository.existsById(emsIngateAllocation.getCorporateCustomerId())).thenReturn(true);

		assertNotEquals(emsIngateAllocation.getLoadEmptyCode(), "L");
		assertNotEquals(emsIngateAllocation.getLoadEmptyCode(), "E");
		assertNotEquals(emsIngateAllocation.getLoadEmptyCode(), "B");
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> emsIngateAllocationService.updateAllocation(emsIngateAllocation,header));
		assertEquals("'loadEmptyCode' value must be 'L', 'E' or 'B'", exception.getMessage());
	}

	@Test
	void testUpdateAllocationEqLengthRecordNotAddedException() {
		emsIngateAllocation.setIngateTerminalId((Long)99999999999999L);
		emsIngateAllocation.setCorporateCustomerId((Long) 123456L);
		emsIngateAllocation.setOnlineOriginStation(null);
		emsIngateAllocation.setEquipmentLength(100);
		when(emsIngateAllocationRepository.existsByTimsId(Mockito.any())).thenReturn(true);
		assertNotNull(emsIngateAllocation.getIngateTerminalId());
		when(terminalRepository.existsByTerminalIdAndExpiredDateIsNull(Mockito.any())).thenReturn(true);
		assertNotNull(emsIngateAllocation.getIngateTerminalId());
		assertEquals(emsIngateAllocation.getIngateTerminalId(), (Long) CommonConstants.NON_TERMINAL_SPECIFIC);
		assertNull(emsIngateAllocation.getOnlineOriginStation());
		when(corporateCustomerRepository.existsById(emsIngateAllocation.getCorporateCustomerId())).thenReturn(true);

		assertNotEquals(emsIngateAllocation.getEquipmentLength(), 240);
		assertNotEquals(emsIngateAllocation.getEquipmentLength(), 336);
		assertNotEquals(emsIngateAllocation.getEquipmentLength(), 480);
		assertNotEquals(emsIngateAllocation.getEquipmentLength(), 540);
		assertNotEquals(emsIngateAllocation.getEquipmentLength(), 576);
		assertNotEquals(emsIngateAllocation.getEquipmentLength(), 636);
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> emsIngateAllocationService.updateAllocation(emsIngateAllocation,header));
		assertEquals("'equipmentLength' value must be '240', '336', '480', '540', '576', '636' or null", exception.getMessage());
	}

	@Test
	void testUpdateAllocationEqTypeRecordNotAddedException() {
		emsIngateAllocation.setIngateTerminalId((Long)99999999999999L);
		emsIngateAllocation.setCorporateCustomerId((Long) 123456L);
		emsIngateAllocation.setOnlineOriginStation(null);
		eqTp.add(null);
		emsIngateAllocation.setEquipmentTypes(eqTp);
		when(emsIngateAllocationRepository.existsByTimsId(Mockito.any())).thenReturn(true);
		assertNotNull(emsIngateAllocation.getIngateTerminalId());
		when(terminalRepository.existsByTerminalIdAndExpiredDateIsNull(Mockito.any())).thenReturn(true);
		assertNotNull(emsIngateAllocation.getIngateTerminalId());
		assertEquals(emsIngateAllocation.getIngateTerminalId(), (Long) CommonConstants.NON_TERMINAL_SPECIFIC);
		assertNull(emsIngateAllocation.getOnlineOriginStation());
		when(corporateCustomerRepository.existsById(emsIngateAllocation.getCorporateCustomerId())).thenReturn(true);

		assertTrue(emsIngateAllocation.getEquipmentTypes().isEmpty());
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> emsIngateAllocationService.updateAllocation(emsIngateAllocation,header));
		assertEquals("Any One Equipment Type value should be provided!", exception.getMessage());
	}

	@Test
	void testUpdateAllocationEqLengthRestrictRecordNotAddedException() {
		emsIngateAllocation.setIngateTerminalId((Long)99999999999999L);
		emsIngateAllocation.setCorporateCustomerId((Long) 123456L);
		emsIngateAllocation.setOnlineOriginStation(null);
		eqTp.add(EquipmentType.CHASSIS);
		emsIngateAllocation.setEquipmentTypes(eqTp);
		lengthRestriction.add(null);
		emsIngateAllocation.setEmsEquipmentLengthRestrictions(lengthRestriction);
		when(emsIngateAllocationRepository.existsByTimsId(Mockito.any())).thenReturn(true);
		assertNotNull(emsIngateAllocation.getIngateTerminalId());
		when(terminalRepository.existsByTerminalIdAndExpiredDateIsNull(Mockito.any())).thenReturn(true);
		assertNotNull(emsIngateAllocation.getIngateTerminalId());
		assertEquals(emsIngateAllocation.getIngateTerminalId(), (Long) CommonConstants.NON_TERMINAL_SPECIFIC);
		assertNull(emsIngateAllocation.getOnlineOriginStation());
		when(corporateCustomerRepository.existsById(emsIngateAllocation.getCorporateCustomerId())).thenReturn(true);

		assertTrue(emsIngateAllocation.getEmsEquipmentLengthRestrictions().isEmpty());
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> emsIngateAllocationService.updateAllocation(emsIngateAllocation,header));
		assertEquals("Any One Equipment Length value should be provided!", exception.getMessage());
	}

	@Test
	void testUpdateAllocationEqLengthAllRecordNotAddedException() {
		emsIngateAllocation.setIngateTerminalId((Long)99999999999999L);
		emsIngateAllocation.setCorporateCustomerId((Long) 123456L);
		emsIngateAllocation.setOnlineOriginStation(null);
		eqTp.add(EquipmentType.CHASSIS);
		emsIngateAllocation.setEquipmentTypes(eqTp);
		lengthRestriction.add(EMSEquipmentLengthRestriction.RESTRICT_20_FT);
		lengthRestriction.add(EMSEquipmentLengthRestriction.RESTRICT_ALL);
		emsIngateAllocation.setEmsEquipmentLengthRestrictions(lengthRestriction);
		when(emsIngateAllocationRepository.existsByTimsId(Mockito.any())).thenReturn(true);
		assertNotNull(emsIngateAllocation.getIngateTerminalId());
		when(terminalRepository.existsByTerminalIdAndExpiredDateIsNull(Mockito.any())).thenReturn(true);
		assertNotNull(emsIngateAllocation.getIngateTerminalId());
		assertEquals(emsIngateAllocation.getIngateTerminalId(), (Long) CommonConstants.NON_TERMINAL_SPECIFIC);
		assertNull(emsIngateAllocation.getOnlineOriginStation());
		when(corporateCustomerRepository.existsById(emsIngateAllocation.getCorporateCustomerId())).thenReturn(true);

		assertTrue(emsIngateAllocation.getEmsEquipmentLengthRestrictions().size()>1);
		assertTrue(emsIngateAllocation.getEmsEquipmentLengthRestrictions().contains(EMSEquipmentLengthRestriction.RESTRICT_ALL));
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> emsIngateAllocationService.updateAllocation(emsIngateAllocation,header));
		assertEquals("EMS Length Restrictions field requires either 'RESTRICT_ALL' or Size", exception.getMessage());
	}


	@Test
	void testUpdateAllocationNoRecordsFoundException() {
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> emsIngateAllocationService.updateAllocation(emsIngateAllocation,header));
		assertEquals("Record Not Found!", exception.getMessage());
	}

	@Test
	void testUpdateAllocationMissingRecordNotAddedException() {
		emsIngateAllocation.setStartTime(null);
		emsIngateAllocation.setOnlineOriginStation(null);
		emsIngateAllocation.setOnlineDestinationStation(null);
		emsIngateAllocation.setCorporateCustomerId((Long) 123456L);
		emsIngateAllocation.setNumberIngated(10);
		eqTp.add(EquipmentType.CONTAINER);
		emsIngateAllocation.setEquipmentTypes(eqTp);
		lengthRestriction.add(EMSEquipmentLengthRestriction.RESTRICT_ALL);
		emsIngateAllocation.setEmsEquipmentLengthRestrictions(lengthRestriction);
		when(emsIngateAllocationRepository.existsByTimsId(Mockito.any())).thenReturn(true);
		when(terminalRepository.existsByTerminalIdAndExpiredDateIsNull(Mockito.any())).thenReturn(true);
		when(stationRepository.existsByTermIdAndExpiredDateIsNull(Mockito.any())).thenReturn(true);
		when(corporateCustomerRepository.existsById(emsIngateAllocation.getCorporateCustomerId())).thenReturn(true);
		when(emsIngateAllocationRepository.findByTimsId(Mockito.any())).thenReturn(emsIngateAllocation);
		when(emsIngateAllocationRepository.save(Mockito.any())).thenReturn(emsIngateAllocation);
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> emsIngateAllocationService.updateAllocation(emsIngateAllocation,header));
		assertEquals("'Ingate Terminal, Start Date, End Date, Equipment Type & Equipment Length' "
				+ "values should be present!", exception.getMessage());
	}

	@Test
	void testUpdateAllocationNoRecordsFoundExceptionUnderTimsId() {
		emsIngateAllocation.setTimsId(null);
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> emsIngateAllocationService.updateAllocation(emsIngateAllocation,header));
		assertEquals("Tims Id is mandatory field", exception.getMessage());
	}
}
