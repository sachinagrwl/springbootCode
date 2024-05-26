package com.nscorp.obis.services;


import com.nscorp.obis.domain.Station;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.repository.StationRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;

import java.sql.Date;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.matches;
import static org.mockito.Mockito.when;

class StationServiceTest {
	
	@InjectMocks
	StationServiceImpl stationService;

	@Mock
	StationRepository stationRepository;
	
	Station station;
	List<Station> stationList;
	Page<Station> stationPageList;
	Station stationUpdated;
	
	@Mock
	Pageable pageable;
	
	int pageNumber;
	int pageSize;
	
	String userid = "Test";
	String extensionschema =  "Test";
	
	Map<String, String> header;

	String stationName = "LOGISTIK";
	String state = null;
	String roadNumber = null;
	String FSAC= null;
	String billAtFsac = null;
	String roadName = null;
	String operationStation = null;
	String splc = null;
	String rule260Station = null;
	String intermodalIndicator = null;
	String char5Spell = null;
	String char5Alias = null;
	String char8Spell = null;
	String division = null;
	Date expirationDate = null;
	String[] sort;

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		
		MockitoAnnotations.initMocks(this);
		station = new Station();
		station.setTermId((long) 1.8245946233393E13);
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
		
		stationList = new ArrayList<>();
		stationList.add(station);

		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");
	
		pageNumber = 1;
		pageSize = 10;
		sort = new String[]{"stationName,asc"};
		pageable = PageRequest.of(pageNumber-1,pageSize,Sort.by(sort));
	}

	@AfterEach
	void tearDown() throws Exception {
		stationList = null;
		station = null;
	}

	@Test
	void testSearchStations() {
		stationPageList = new PageImpl<>(Arrays.asList(station),PageRequest.of(1, 10), 100L);
		when(stationRepository.searchAll(Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any()
					,Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any()
					,Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(stationPageList);
			Page<Station> allStations = stationService.searchStations(stationName,state,roadNumber,FSAC,billAtFsac,roadName,operationStation,splc,rule260Station,intermodalIndicator
					,char5Spell,char5Alias,char8Spell,division,expirationDate,pageable);
			assertEquals(allStations, stationPageList);
	}

	@Test
	void testUpdateStationNoRecordsFoundException() {
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(stationService.updateStation(station, header)));
		assertEquals("Record with TermId 18245946233393 Not Found!", exception.getMessage());
	}

	@Test
	void testUpdateStationBillAtFsacRecordNotAddedException() {
		Station station1 = new Station();
		station1.setTermId(Long.valueOf("12357"));
		station1.setBillAtFsac("ABCD");
		when(stationRepository.existsByTermId(Mockito.any())).thenReturn(true);
		assertNotNull(station1.getBillAtFsac());
		assertNotEquals(station1.getBillAtFsac(),matches("[0-9]+"));
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> when(stationService.updateStation(station1, header)));
		assertEquals("'billAtFsac' should be in numeric", exception.getMessage());
	}

	@Test
	void testUpdateStationStateRecordNotAddedException() {
		Station station1 = new Station();
		station1.setTermId(Long.valueOf("12357"));
		station1.setState("ab12345");
		when(stationRepository.existsByTermId(Mockito.any())).thenReturn(true);
		when(stationRepository.getByTermId(Mockito.any())).thenReturn(station1);
		assertNotNull(station1.getState());
		assertNotEquals(station1.getState(),matches("[a-zA-Z]+"));
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> when(stationService.updateStation(station1, header)));
		assertEquals("'State' should have only alphabets", exception.getMessage());
	}

	@Test
	void testUpdateStationIntermodalIndicatorRecordNotAddedException() {
		Station station1 = new Station();
		station1.setTermId(Long.valueOf("12357"));
		station1.setIntermodalIndicator("A");
		when(stationRepository.existsByTermId(Mockito.any())).thenReturn(true);
		when(stationRepository.getByTermId(Mockito.any())).thenReturn(station1);
		assertNotNull(station1.getIntermodalIndicator());
		assertNotEquals(station1.getIntermodalIndicator(),matches("O"));
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> when(stationService.updateStation(station1, header)));
		assertEquals("IntermodalIndicator value should be 'O','S' or null", exception.getMessage());
	}

	@Test
	void testUpdateStationRoadNumberRecordNotAddedException() {
		Station station1 = new Station();
		station1.setTermId(Long.valueOf("12357"));
		station1.setRoadNumber("ABCD");
		when(stationRepository.existsByTermId(Mockito.any())).thenReturn(true);
		when(stationRepository.getByTermId(Mockito.any())).thenReturn(station1);
		assertNotNull(station1.getRoadNumber());
		assertNotEquals(station1.getRoadNumber(),matches("[0-9]+"));
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> when(stationService.updateStation(station1,header)));
		assertEquals("'roadNumber' should be in numeric!", exception.getMessage());
	}

	@Test
	void testUpdateStationFSACRecordNotAddedException() {
		Station station1 = new Station();
		station1.setTermId(Long.valueOf("12357"));
		station1.setFSAC("ABCD");
		when(stationRepository.existsByTermId(Mockito.any())).thenReturn(true);
		when(stationRepository.getByTermId(Mockito.any())).thenReturn(station1);
		assertNotNull(station1.getFSAC());
		assertNotEquals(station1.getFSAC(),matches("[0-9]+"));
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> when(stationService.updateStation(station1, header)));
		assertEquals("'fsac' should be in numeric!", exception.getMessage());
	}

	@Test
	void testUpdateStationFSACNullRecordNotAddedException() {
		Station station1 = new Station();
		station1.setTermId(Long.valueOf("12357"));
		when(stationRepository.existsByTermId(Mockito.any())).thenReturn(true);
		when(stationRepository.getByTermId(Mockito.any())).thenReturn(station1);
		assertNull(station1.getFSAC());
		station1.setFSAC("1234");
	}

	@Test
	void  testUpdateStation(){
		when(stationRepository.existsByTermId(Mockito.any())).thenReturn(true);
		when(stationRepository.getByTermId(Mockito.any())).thenReturn(station);
		when(stationRepository.save(Mockito.any())).thenReturn(station);
		stationUpdated = stationService.updateStation(station,header);
		assertEquals(stationUpdated,station);
	}
}
