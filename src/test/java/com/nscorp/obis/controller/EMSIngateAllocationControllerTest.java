package com.nscorp.obis.controller;

import com.nscorp.obis.domain.DayOfWeek;
import com.nscorp.obis.domain.EMSEquipmentLengthRestriction;
import com.nscorp.obis.domain.EMSIngateAllocation;
import com.nscorp.obis.domain.EquipmentType;
import com.nscorp.obis.domain.Station;
import com.nscorp.obis.dto.EMSIngateAllocationDTO;
import com.nscorp.obis.dto.mapper.EMSIngateAllocationMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.SizeExceedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.PaginationWrapper;
import com.nscorp.obis.services.EMSIngateAllocationService;
import org.hibernate.QueryParameterException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;

import javax.persistence.OptimisticLockException;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static com.nscorp.obis.domain.EMSAllotmentType.DAILY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class EMSIngateAllocationControllerTest {

	@Mock
	EMSIngateAllocationService emsIngateAllocationService;
	@Mock
	EMSIngateAllocationMapper emsIngateAllocationMapper;
	@InjectMocks
    EMSIngateAllocationController emsIngateAllocationController;

	EMSIngateAllocationDTO emsIngateAllocationDto;
	EMSIngateAllocation emsIngateAllocation;

	List<EMSIngateAllocation> emsIngateAllocationList;

	List<EMSIngateAllocationDTO> emsIngateAllocationDtoList;
	Station station;
	Page<EMSIngateAllocation> page;
	Map<String, String> header;
	String domestic = null;
	String international = null;
	String premium = null;
	List<String> lob = new ArrayList<String>() ;
	List<String> trafficType = new ArrayList<String>();
	Long ingateTerminalId = 42103633022158L;
	List<EquipmentType> eqTp = new ArrayList<>(EnumSet.allOf(EquipmentType.class));
	List<DayOfWeek> days = new ArrayList<>(EnumSet.allOf(DayOfWeek.class));
	String local = null;
	String steelWheel = null;
	String rubberTire = null;
	List<EMSEquipmentLengthRestriction> lengthRestriction = new ArrayList<>(
			EnumSet.allOf(EMSEquipmentLengthRestriction.class));
	LocalDate startDate = new Date(2020-02-17).toLocalDate();
	LocalDate endDate = new Date(2020-04-05).toLocalDate();
	Date st_date;
	Date end_date;
	Date currentDate = null;
	LocalTime startTime = LocalTime.parse("00:00:00");
	LocalTime endTime = LocalTime.parse("23:59:59");
	Timestamp ts = Timestamp.valueOf(String.format("%04d-%02d-%02d 00:00:00", 2022, 07, 01));
	Timestamp ts1 = Timestamp.valueOf(String.format("%04d-%02d-%02d 00:00:00", 2022, 05, 01));
	Boolean includeInactive;
	Boolean includeExpired;
	Boolean includePermanent;
	Pageable pageable;
	int pageNumber;
	int pageSize;
	String[] sort;

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		emsIngateAllocation = new EMSIngateAllocation();

		emsIngateAllocation.setTimsId(1327341890048L);
		emsIngateAllocation.setIngateTerminalId(ingateTerminalId);
		emsIngateAllocation.setUversion("8");
		emsIngateAllocation.setOnlineOriginStation(station);
		emsIngateAllocation.setOnlineDestinationStation(null);
		emsIngateAllocation.setOfflineDestinationStation(null);
		emsIngateAllocation.setEquipmentInit("EMHU");
		emsIngateAllocation.setEquipmentLowestNumber(null);
		emsIngateAllocation.setEquipmentHighestNumber(null);
		emsIngateAllocation.setCorporateCustomerId(null);
		emsIngateAllocation.setLoadEmptyCode("E");
		emsIngateAllocation.setEquipmentLength(null);
		emsIngateAllocation.setGrossWeight(null);
		eqTp.add(EquipmentType.CONTAINER);
		emsIngateAllocation.setEquipmentTypes(eqTp);
		lob.add("DOMESTIC");
		lob.add("INTERNATIONAL");
		lob.add("PREMIUM");
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
		local = "T";
		steelWheel = "T";
		rubberTire = "T";
		emsIngateAllocation.setReeferIndicator(null);
		emsIngateAllocation.setTemporaryIndicator("T");
		lengthRestriction.add(EMSEquipmentLengthRestriction.RESTRICT_ALL);
		emsIngateAllocation.setEmsEquipmentLengthRestrictions(lengthRestriction);
		emsIngateAllocationList = new ArrayList<>();
		emsIngateAllocationList.add(emsIngateAllocation);

		emsIngateAllocationDto = new EMSIngateAllocationDTO();
		emsIngateAllocationDto.setTimsId(1327341890048L);
		emsIngateAllocationDto.setIngateTerminalId(ingateTerminalId);
		emsIngateAllocationDto.setUversion("8");
		emsIngateAllocationDto.setOnlineOriginStation(station);
		emsIngateAllocationDto.setOnlineDestinationStation(null);
		emsIngateAllocationDto.setOfflineDestinationStation(null);
		emsIngateAllocationDto.setEquipmentInit("EMHU");
		emsIngateAllocationDto.setEquipmentLowestNumber(null);
		emsIngateAllocationDto.setEquipmentHighestNumber(null);
		emsIngateAllocationDto.setCorporateCustomerId(null);
		emsIngateAllocationDto.setLoadEmptyCode("E");
		emsIngateAllocationDto.setEquipmentLength(null);
		emsIngateAllocationDto.setGrossWeight(null);
		emsIngateAllocationDto.setEquipmentTypes(eqTp);
		lob.add("DOMESTIC");
		lob.add("INTERNATIONAL");
		lob.add("PREMIUM");
		domestic = "T";
		international = "T";
		premium = "T";
		emsIngateAllocationDto.setWayBillRoute("N");
		emsIngateAllocationDto.setHazardousIndicator(null);
		emsIngateAllocationDto.setWeightQualifier(null);
		emsIngateAllocationDto.setTotalIngatesAllowed(99999999);
		emsIngateAllocationDto.setNumberIngated(null);
		emsIngateAllocationDto.setActive("T");
		emsIngateAllocationDto.setAllotmentType(DAILY);
		emsIngateAllocationDto.setStartDate(startDate);
		emsIngateAllocationDto.setStartTime(startTime);
		emsIngateAllocationDto.setEndDate(endDate);
		emsIngateAllocationDto.setEndTime(endTime);
		emsIngateAllocationDto.setWayBillRoute("TEST");
		emsIngateAllocationDto.setCreateDateTime(ts);
		emsIngateAllocationDto.setCreateUserId("test");
		emsIngateAllocationDto.setCreateExtensionSchema("IMS08120");
		emsIngateAllocationDto.setUpdateDateTime(ts1);
		emsIngateAllocationDto.setUpdateUserId("test");
		emsIngateAllocationDto.setUpdateExtensionSchema("NGTNMR");
		emsIngateAllocationDto.setRestrictedDays(days);
		emsIngateAllocationDto.setReeferIndicator(null);
		trafficType.add("LOCAL");
		trafficType.add("STEEL_WHEEL");
		trafficType.add("RUBBER_TIRE");
		local = "T";
		steelWheel = "T";
		rubberTire = "T";
		emsIngateAllocationDto.setReeferIndicator(null);
		emsIngateAllocationDto.setTemporaryIndicator("T");
		lengthRestriction.add(EMSEquipmentLengthRestriction.RESTRICT_ALL);
		emsIngateAllocationDto.setEmsEquipmentLengthRestrictions(lengthRestriction);
		emsIngateAllocationDtoList = new ArrayList<>();
		emsIngateAllocationDtoList.add(emsIngateAllocationDto);
		
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

		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");

		includeInactive = true;
		includePermanent = true;
		includeExpired = true;

		pageNumber = 1;
		pageSize = 10;
		sort = new String[]{"ingateTerminalId,asc"};
		pageable = PageRequest.of(pageNumber - 1, pageSize,Sort.by(sort));
	}

	@AfterEach
	void tearDown() throws Exception {
		emsIngateAllocationList = null;
		emsIngateAllocationDtoList = null;
		emsIngateAllocationDto = null;
		emsIngateAllocation = null;
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"onlineOriginStationTermId", "ingateTerminalId", "onlineDestinationStationTermId", "offlineDestinationStationTermId", "corporateCustomerId",
							"lineOfBusinesses", "trafficTypes"})
	void testSearchAllocationsListAsc(String input) {
		String [] sortAsc = {input, "asc"};
		page = new PageImpl<>(emsIngateAllocationList, PageRequest.of(0, 10), 100L);
		when(emsIngateAllocationService.searchIngateAllocation(Mockito.any(), Mockito.any(),
				Mockito.any(),Mockito.any(),
				Mockito.any(),Mockito.any(),Mockito.any(), Mockito.any(),
				Mockito.any(), Mockito.any(),Mockito.any(),Mockito.any(),
				Mockito.any(), Mockito.any(),Mockito.any())).thenReturn(page);
		ResponseEntity<APIResponse<PaginationWrapper>> emsIngateAllocationList = emsIngateAllocationController.searchAllocations(emsIngateAllocation.getIngateTerminalId(),emsIngateAllocation.getOnlineOriginStation(),
				emsIngateAllocation.getOnlineDestinationStation(),emsIngateAllocation.getOfflineDestinationStation(),
				emsIngateAllocation.getCorporateCustomerId(),lob,emsIngateAllocation.getWayBillRoute(), trafficType,
				st_date, end_date ,includeInactive,
				includeExpired, includePermanent, pageNumber, pageSize, sortAsc);
		assertEquals(emsIngateAllocationList.getBody().getStatus(),"SUCCESS");
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"onlineOriginStationTermId", "ingateTerminalId", "onlineDestinationStationTermId", "offlineDestinationStationTermId", "corporateCustomerId",
							"lineOfBusinesses", "trafficTypes"})
	void testSearchAllocationsListDesc(String input) {
		String [] sortDesc = {input, "desc"};
		page = new PageImpl<>(emsIngateAllocationList, PageRequest.of(0, 10), 100L);
		when(emsIngateAllocationService.searchIngateAllocation(Mockito.any(), Mockito.any(),
				Mockito.any(),Mockito.any(),
				Mockito.any(),Mockito.any(),Mockito.any(), Mockito.any(),
				Mockito.any(), Mockito.any(),Mockito.any(),Mockito.any(),
				Mockito.any(), Mockito.any(),Mockito.any())).thenReturn(page);
		ResponseEntity<APIResponse<PaginationWrapper>> emsIngateAllocationList = emsIngateAllocationController.searchAllocations(emsIngateAllocation.getIngateTerminalId(),emsIngateAllocation.getOnlineOriginStation(),
				emsIngateAllocation.getOnlineDestinationStation(),emsIngateAllocation.getOfflineDestinationStation(),
				emsIngateAllocation.getCorporateCustomerId(),lob,emsIngateAllocation.getWayBillRoute(), trafficType,
				st_date, end_date,includeInactive,
				includeExpired, includePermanent, pageNumber, pageSize, sortDesc);
		assertEquals(emsIngateAllocationList.getBody().getStatus(),"SUCCESS");
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"onlineOriginStationTermId", "ingateTerminalId", "onlineDestinationStationTermId", "offlineDestinationStationTermId", "corporateCustomerId",
							"lineOfBusinesses", "trafficTypes"})
	void testSearchAllocationsListEmpty(String input) {
		String [] sortFail = {input, "fail"};
		page = new PageImpl<>(emsIngateAllocationList, PageRequest.of(0, 10), 100L);
		when(emsIngateAllocationService.searchIngateAllocation(Mockito.any(), Mockito.any(),
				Mockito.any(),Mockito.any(),
				Mockito.any(),Mockito.any(),Mockito.any(), Mockito.any(),
				Mockito.any(), Mockito.any(),Mockito.any(),Mockito.any(),
				Mockito.any(), Mockito.any(),Mockito.any())).thenReturn(page);
		ResponseEntity<APIResponse<PaginationWrapper>> emsIngateAllocationList = emsIngateAllocationController.searchAllocations(emsIngateAllocation.getIngateTerminalId(),emsIngateAllocation.getOnlineOriginStation(),
				emsIngateAllocation.getOnlineDestinationStation(),emsIngateAllocation.getOfflineDestinationStation(),
				emsIngateAllocation.getCorporateCustomerId(),lob,emsIngateAllocation.getWayBillRoute(), trafficType,
				st_date, end_date,includeInactive,
				includeExpired, includePermanent, pageNumber, pageSize, sortFail);
		assertEquals(emsIngateAllocationList.getBody().getStatus(),"SUCCESS");
	}

	@Test
	void testSearchAllocationsNoRecordsFoundException() {
		page = new PageImpl<>(emsIngateAllocationList, PageRequest.of(0, 10), 100L);
		when(emsIngateAllocationService.searchIngateAllocation(Mockito.any(), Mockito.any(),
				Mockito.any(),Mockito.any(),
				Mockito.any(),Mockito.any(),Mockito.any(), Mockito.any(),
				Mockito.any(), Mockito.any(),Mockito.any(),Mockito.any(),
				Mockito.any(), Mockito.any(),Mockito.any())).thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<PaginationWrapper>> emsIngateAllocationList = emsIngateAllocationController.searchAllocations(emsIngateAllocation.getIngateTerminalId(),emsIngateAllocation.getOnlineOriginStation(),
				emsIngateAllocation.getOnlineDestinationStation(),emsIngateAllocation.getOfflineDestinationStation(),
				emsIngateAllocation.getCorporateCustomerId(),lob,emsIngateAllocation.getWayBillRoute(), trafficType,
				st_date, end_date,includeInactive,
				includeExpired, includePermanent, pageNumber, pageSize, sort);
		assertEquals(emsIngateAllocationList.getBody().getStatusCode(),110);
	}

	@Test
	void testSearchAllocationsSizeExceedException() {
		page = new PageImpl<>(emsIngateAllocationList, PageRequest.of(0, 10), 100L);
		when(emsIngateAllocationService.searchIngateAllocation(Mockito.any(), Mockito.any(),
				Mockito.any(),Mockito.any(),
				Mockito.any(),Mockito.any(),Mockito.any(), Mockito.any(),
				Mockito.any(), Mockito.any(),Mockito.any(),Mockito.any(),
				Mockito.any(), Mockito.any(),Mockito.any())).thenThrow(new SizeExceedException());
		ResponseEntity<APIResponse<PaginationWrapper>> emsIngateAllocationList = emsIngateAllocationController.searchAllocations(emsIngateAllocation.getIngateTerminalId(),emsIngateAllocation.getOnlineOriginStation(),
				emsIngateAllocation.getOnlineDestinationStation(),emsIngateAllocation.getOfflineDestinationStation(),
				emsIngateAllocation.getCorporateCustomerId(),lob,emsIngateAllocation.getWayBillRoute(), trafficType,
				st_date, end_date,includeInactive,
				includeExpired, includePermanent, pageNumber, pageSize, sort);
		assertEquals(emsIngateAllocationList.getBody().getStatusCode(),120);
	}

	@Test
	void testSearchAllocationsQueryParameterException() {
		page = new PageImpl<>(emsIngateAllocationList, PageRequest.of(0, 10), 100L);
		when(emsIngateAllocationService.searchIngateAllocation(Mockito.any(), Mockito.any(),
				Mockito.any(),Mockito.any(),
				Mockito.any(),Mockito.any(),Mockito.any(), Mockito.any(),
				Mockito.any(), Mockito.any(),Mockito.any(),Mockito.any(),
				Mockito.any(), Mockito.any(),Mockito.any())).thenThrow(new QueryParameterException("Request Parameter is incorrect!"));
		ResponseEntity<APIResponse<PaginationWrapper>> emsIngateAllocationList = emsIngateAllocationController.searchAllocations(emsIngateAllocation.getIngateTerminalId(),emsIngateAllocation.getOnlineOriginStation(),
				emsIngateAllocation.getOnlineDestinationStation(),emsIngateAllocation.getOfflineDestinationStation(),
				emsIngateAllocation.getCorporateCustomerId(),lob,emsIngateAllocation.getWayBillRoute(), trafficType,
				st_date, end_date,includeInactive,
				includeExpired, includePermanent, pageNumber, pageSize, sort);
		assertEquals(emsIngateAllocationList.getBody().getStatusCode(),120);
	}

	@Test
	void testUpdateAllocations() {
		when(emsIngateAllocationMapper.emsIngateAllocationDTOToEMSIngateAllocation(Mockito.any())).thenReturn(emsIngateAllocation);
		when(emsIngateAllocationService.updateAllocation(Mockito.any(), Mockito.any())).thenReturn(emsIngateAllocation);
		when(emsIngateAllocationMapper.emsIngateAllocationToEMSIngateAllocationDTO(Mockito.any())).thenReturn(emsIngateAllocationDto);
		ResponseEntity<APIResponse<EMSIngateAllocationDTO>> allocationsUpdated = emsIngateAllocationController.updateRestriction(emsIngateAllocationDto,header);
		assertNotNull(allocationsUpdated.getBody());
	}

	@Test
	void testAddAllocations() {
		when(emsIngateAllocationMapper.emsIngateAllocationDTOToEMSIngateAllocation(Mockito.any())).thenReturn(emsIngateAllocation);
		when(emsIngateAllocationService.insertAllocation(Mockito.any(), Mockito.any())).thenReturn(emsIngateAllocation);
		when(emsIngateAllocationMapper.emsIngateAllocationToEMSIngateAllocationDTO(Mockito.any())).thenReturn(emsIngateAllocationDto);
		ResponseEntity<APIResponse<EMSIngateAllocationDTO>> allocationsUpdated = emsIngateAllocationController.addAllocation(emsIngateAllocationDto,header);
		assertNotNull(allocationsUpdated);
	}

	@Test
	void testAllocationRecordNotAddedException() {
		when(emsIngateAllocationService.updateAllocation(Mockito.any(),Mockito.any())).thenThrow(new RecordNotAddedException());
		when(emsIngateAllocationService.insertAllocation(Mockito.any(),Mockito.any())).thenThrow(new RecordNotAddedException());

		ResponseEntity<APIResponse<EMSIngateAllocationDTO>> insertAllocation = emsIngateAllocationController.addAllocation(Mockito.any(),Mockito.any());
		ResponseEntity<APIResponse<EMSIngateAllocationDTO>> updateAllocation = emsIngateAllocationController.updateRestriction(Mockito.any(),Mockito.any());

		assertEquals(insertAllocation.getStatusCodeValue(),406);
		assertEquals(updateAllocation.getStatusCodeValue(),406);
	}

	@Test
	void testAllocationNullPointerException() {
		when(emsIngateAllocationService.updateAllocation(Mockito.any(),Mockito.any())).thenThrow(new NullPointerException());
		when(emsIngateAllocationService.insertAllocation(Mockito.any(),Mockito.any())).thenThrow(new NullPointerException());

		ResponseEntity<APIResponse<EMSIngateAllocationDTO>> insertAllocation = emsIngateAllocationController.addAllocation(Mockito.any(),Mockito.any());
		ResponseEntity<APIResponse<EMSIngateAllocationDTO>> updateAllocation = emsIngateAllocationController.updateRestriction(Mockito.any(),Mockito.any());

		assertEquals(insertAllocation.getStatusCodeValue(),400);
		assertEquals(updateAllocation.getStatusCodeValue(),400);
	}

	@Test
	void testSearchAllocationsException() {
		when(emsIngateAllocationService.searchIngateAllocation(Mockito.any(), Mockito.any(),
				Mockito.any(),Mockito.any(),
				Mockito.any(),Mockito.any(),Mockito.any(), Mockito.any(),
				Mockito.any(), Mockito.any(),Mockito.any(),Mockito.any(),
				Mockito.any(), Mockito.any(),Mockito.any())).thenThrow(new RuntimeException());
		when(emsIngateAllocationService.updateAllocation(Mockito.any(),Mockito.any())).thenThrow(new RuntimeException());
		when(emsIngateAllocationService.insertAllocation(Mockito.any(),Mockito.any())).thenThrow(new RuntimeException());

		ResponseEntity<APIResponse<PaginationWrapper>> emsIngateAllocationList = emsIngateAllocationController.searchAllocations(emsIngateAllocation.getIngateTerminalId(),emsIngateAllocation.getOnlineOriginStation(),
				emsIngateAllocation.getOnlineDestinationStation(),emsIngateAllocation.getOfflineDestinationStation(),
				emsIngateAllocation.getCorporateCustomerId(),lob,emsIngateAllocation.getWayBillRoute(), trafficType,
				st_date, end_date,includeInactive,
				includeExpired, includePermanent, pageNumber, pageSize, sort);
		ResponseEntity<APIResponse<EMSIngateAllocationDTO>> insertAllocation = emsIngateAllocationController.addAllocation(Mockito.any(),Mockito.any());
		ResponseEntity<APIResponse<EMSIngateAllocationDTO>> updateAllocation = emsIngateAllocationController.updateRestriction(Mockito.any(),Mockito.any());

		assertEquals(insertAllocation.getStatusCodeValue(),500);
		assertEquals(updateAllocation.getStatusCodeValue(),500);
		assertEquals(emsIngateAllocationList.getStatusCodeValue(),500);
	}

	@Test
	void testUpdateAllocationsOptimisticLockException() {
		when(emsIngateAllocationService.updateAllocation(Mockito.any(),Mockito.any())).thenThrow(new RuntimeException("Test", new OptimisticLockException("Test")));
		ResponseEntity<APIResponse<EMSIngateAllocationDTO>> updateAllocation = emsIngateAllocationController.updateRestriction(Mockito.any(),Mockito.any());
		assertEquals(updateAllocation.getStatusCodeValue(), 409);
	}

	@Test
	void testAllocationSizeExceedException() {
		when(emsIngateAllocationService.updateAllocation(Mockito.any(),Mockito.any())).thenThrow(new SizeExceedException());
		when(emsIngateAllocationService.insertAllocation(Mockito.any(),Mockito.any())).thenThrow(new SizeExceedException());

		ResponseEntity<APIResponse<EMSIngateAllocationDTO>> insertAllocation = emsIngateAllocationController.addAllocation(Mockito.any(),Mockito.any());
		ResponseEntity<APIResponse<EMSIngateAllocationDTO>> updateAllocation = emsIngateAllocationController.updateRestriction(Mockito.any(),Mockito.any());

		assertEquals(insertAllocation.getStatusCodeValue(),411);
		assertEquals(updateAllocation.getStatusCodeValue(),411);
	}

	@Test
	void testAllocationRecordAlreadyExistsException() {
		when(emsIngateAllocationService.updateAllocation(Mockito.any(),Mockito.any())).thenThrow(new RecordAlreadyExistsException());
		when(emsIngateAllocationService.insertAllocation(Mockito.any(),Mockito.any())).thenThrow(new RecordAlreadyExistsException());

		ResponseEntity<APIResponse<EMSIngateAllocationDTO>> insertAllocation = emsIngateAllocationController.addAllocation(Mockito.any(),Mockito.any());
		ResponseEntity<APIResponse<EMSIngateAllocationDTO>> updateAllocation = emsIngateAllocationController.updateRestriction(Mockito.any(),Mockito.any());

		assertEquals(insertAllocation.getStatusCodeValue(),208);
		assertEquals(updateAllocation.getStatusCodeValue(),208);
	}

	@Test
	void testAllocationNoRecordsFoundException() {
		when(emsIngateAllocationService.updateAllocation(Mockito.any(),Mockito.any())).thenThrow(new NoRecordsFoundException());
		when(emsIngateAllocationService.insertAllocation(Mockito.any(),Mockito.any())).thenThrow(new NoRecordsFoundException());

		ResponseEntity<APIResponse<EMSIngateAllocationDTO>> insertAllocation = emsIngateAllocationController.addAllocation(Mockito.any(),Mockito.any());
		ResponseEntity<APIResponse<EMSIngateAllocationDTO>> updateAllocation = emsIngateAllocationController.updateRestriction(Mockito.any(),Mockito.any());

		assertEquals(insertAllocation.getStatusCodeValue(),404);
		assertEquals(updateAllocation.getStatusCodeValue(),404);
	}

}
