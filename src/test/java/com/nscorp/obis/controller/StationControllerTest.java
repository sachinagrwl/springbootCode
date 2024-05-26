package com.nscorp.obis.controller;

import com.nscorp.obis.domain.Station;
import com.nscorp.obis.dto.StationDTO;
import com.nscorp.obis.dto.mapper.StationMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.SizeExceedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.PaginationWrapper;
import com.nscorp.obis.services.StationService;
import org.hibernate.QueryParameterException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import java.sql.Date;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class StationControllerTest {
	
	@Mock
	StationService stationService;

	@Mock
	StationMapper stationMapper;

	@InjectMocks
	StationController stationController;

	StationDTO stationDto;
	Station station;
	List<Station> stationList;
	Page<Station> stationPageList;
	List<StationDTO> stationDtoList;

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
	Map<String, String> header;

	int pageNumber;
	int pageSize;
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
		
		stationDto = new StationDTO();
		stationDto.setTermId((long) 1.8245946233393E13);
		stationDto.setStationName("LOGISTIK");
		stationDto.setState("SL");
		stationDto.setSplc("919426000");
		stationDto.setRule260Station("CNTRA");
		stationDto.setRoadNumber("0978");
		stationDto.setRoadName("KCSM");
		stationDto.setOperationStation("92457");
		stationDto.setIntermodalIndicator("O");
		stationDto.setFSAC("092457");
		stationDto.setExpiredDate(null);
		stationDto.setExpirationDate(null);
		stationDto.setDivision("74");
		stationDto.setChar8Spell("HUMPREPA");
		stationDto.setChar5Spell("GREGC");
		stationDto.setChar5Alias(null);
		stationDto.setBottomPick("Y");
		stationDto.setTopPick("Y");
		stationDto.setBillingInd(null);
		stationDto.setBillAtFsac("071619");
		
		stationDtoList = new ArrayList<>();
		stationDtoList.add(stationDto);

		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");
	
		pageNumber = 1;
		pageSize = 10;
		sort = new String[]{"state,asc"};
	}

	@AfterEach
	void tearDown() throws Exception {
		
		station = null;
		stationDto = null;
		stationList = null;
		stationDtoList = null;
		
	}

	@Test
	void testStationSizeExceedException() {
		when(stationService.searchStations(Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any()
				,Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any()
				,Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any())).thenThrow(new SizeExceedException());
		when(stationService.updateStation(Mockito.any(),Mockito.any())).thenThrow(new SizeExceedException());
		ResponseEntity<APIResponse<PaginationWrapper>> stationSearch = stationController.searchStations(stationName,state,roadNumber,FSAC,billAtFsac,roadName,operationStation,splc,rule260Station,intermodalIndicator
				,char5Spell,char5Alias,char8Spell,division,expirationDate,pageNumber, pageSize, sort);
		ResponseEntity<APIResponse<StationDTO>> updateStation = stationController.updateStation(Mockito.any(),Mockito.any());
		assertEquals(stationSearch.getStatusCodeValue(),411);
		assertEquals(updateStation.getStatusCodeValue(),411);
	}

	@Test
	void testStationRecordNotAddedException() {
		when(stationService.updateStation(Mockito.any(),Mockito.any())).thenThrow(new RecordNotAddedException());
		ResponseEntity<APIResponse<StationDTO>> updateStation = stationController.updateStation(Mockito.any(),Mockito.any());
		assertEquals(updateStation.getStatusCodeValue(),406);
	}

	@Test
	void testStationNullPointerException() {
		when(stationService.updateStation(Mockito.any(),Mockito.any())).thenThrow(new NullPointerException());
		ResponseEntity<APIResponse<StationDTO>> updateStation = stationController.updateStation(Mockito.any(),Mockito.any());
		assertEquals(updateStation.getStatusCodeValue(),400);
	}

	@Test
	void testStationNoRecordsFoundException() {
		when(stationService.searchStations(Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any()
				,Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any()
				,Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any())).thenThrow(new NoRecordsFoundException());
		when(stationService.updateStation(Mockito.any(),Mockito.any())).thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<PaginationWrapper>> stationSearch = stationController.searchStations(stationName,state,roadNumber,FSAC,billAtFsac,roadName,operationStation,splc,rule260Station,intermodalIndicator
				,char5Spell,char5Alias,char8Spell,division,expirationDate,pageNumber, pageSize, sort);
		ResponseEntity<APIResponse<StationDTO>> updateStation = stationController.updateStation(stationDto,header);
		assertEquals(stationSearch.getStatusCodeValue(),404);
		assertEquals(updateStation.getStatusCodeValue(),404);
	}

	@Test
	void testStationException() {
		when(stationService.searchStations(Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any()
				,Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any()
				,Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any())).thenThrow(new RuntimeException());
		when(stationService.updateStation(Mockito.any(),Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<PaginationWrapper>> stationSearch = stationController.searchStations(stationName,state,roadNumber,FSAC,billAtFsac,roadName,operationStation,splc,rule260Station,intermodalIndicator
				,char5Spell,char5Alias,char8Spell,division,expirationDate,pageNumber, pageSize, sort);
		ResponseEntity<APIResponse<StationDTO>> updateStation = stationController.updateStation(stationDto,header);
		assertEquals(updateStation.getStatusCodeValue(),500);
		assertEquals(stationSearch.getStatusCodeValue(),500);
	}

	@Test
	void testStationQueryParameterException() {
		when(stationService.searchStations(Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any()
				,Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any()
				,Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any())).thenThrow(new QueryParameterException("Incorrect Param"));
		ResponseEntity<APIResponse<PaginationWrapper>> stationSearch = stationController.searchStations(stationName,state,roadNumber,FSAC,billAtFsac,roadName,operationStation,splc,rule260Station,intermodalIndicator
				,char5Spell,char5Alias,char8Spell,division,expirationDate,pageNumber, pageSize, sort);
		assertEquals(stationSearch.getStatusCodeValue(),400);
	}

	@Test
	void searchStations() {
		stationPageList = new PageImpl<>(Arrays.asList(station),PageRequest.of(1, 10), 100L);
		when(stationService.searchStations(Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any()
				,Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any()
				,Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(stationPageList);
		ResponseEntity<APIResponse<PaginationWrapper>> allStations = stationController.searchStations(stationName,state,roadNumber,FSAC,billAtFsac,roadName,operationStation,splc,rule260Station,intermodalIndicator
				,char5Spell,char5Alias,char8Spell,division,expirationDate,pageNumber, pageSize, sort);
		assertEquals(allStations.getStatusCodeValue(), 200);
	}

	@Test
	void updateStation() {
		when(stationMapper.stationDTOToStation(Mockito.any())).thenReturn(station);
		when(stationService.updateStation(Mockito.any(), Mockito.any())).thenReturn(station);
		when(stationMapper.stationToStationDTO(Mockito.any())).thenReturn(stationDto);
		ResponseEntity<APIResponse<StationDTO>> stationUpdated = stationController.updateStation(stationDto, header);
		assertNotNull(stationUpdated.getBody());
	}

}
