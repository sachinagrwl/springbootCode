package com.nscorp.obis.controller;

import com.nscorp.obis.domain.DayOfWeek;
import com.nscorp.obis.domain.EMSEquipmentLengthRestriction;
import com.nscorp.obis.domain.EMSIngateRestriction;
import com.nscorp.obis.domain.EquipmentType;
import com.nscorp.obis.domain.Station;
import com.nscorp.obis.dto.EMSIngateRestrictionDTO;
import com.nscorp.obis.dto.mapper.EMSIngateRestrictionMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.SizeExceedException;
import com.nscorp.obis.repository.EMSIngateRestrictionRepository;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.EMSIngateRestrictionService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import javax.persistence.OptimisticLockException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

class EMSIngateRestrictionControllerTest {

	@Mock
	EMSIngateRestrictionService emsIngateRestrictionService;

	@Mock
	EMSIngateRestrictionMapper emsIngateRestrictionMapper;

	@InjectMocks
    EMSIngateRestrictionController emsIngateRestrictionController;
	
	@Mock
	EMSIngateRestrictionRepository emsIngateRestrictionRepository;
	
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
	List<EMSEquipmentLengthRestriction> lengthRestriction = new ArrayList<>(EnumSet.allOf(EMSEquipmentLengthRestriction.class));
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

		emsIngateRestriction.setRestrictId(1327341890048L);
		emsIngateRestriction.setIngateTerminalId(ingateTerminalId);
		emsIngateRestriction.setUversion("8");
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
		eqTp.add(EquipmentType.CONTAINER);
		emsIngateRestriction.setEquipmentTypes(eqTp);
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
		lengthRestriction.add(EMSEquipmentLengthRestriction.RESTRICT_ALL);
		emsIngateRestriction.setEmsEquipmentLengthRestrictions(lengthRestriction);
		emsIngateRestrictionList = new ArrayList<>();
		emsIngateRestrictionList.add(emsIngateRestriction);

		emsIngateRestrictionDtoList = new ArrayList<>();
		emsIngateRestrictionDto = new EMSIngateRestrictionDTO();
		emsIngateRestrictionDto.setRestrictId(1327341890048L);
		emsIngateRestrictionDto.setIngateTerminalId(ingateTerminalId);
		emsIngateRestrictionDto.setUversion("8");
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
		when(emsIngateRestrictionMapper.emsIngateRestrictionDTOToEMSIngateRestriction(Mockito.any())).thenReturn(emsIngateRestriction);
		when(emsIngateRestrictionService.getAllRestrictions()).thenReturn(emsIngateRestrictionList);
		ResponseEntity<APIResponse<List<EMSIngateRestrictionDTO>>> emsIngateRestrictionList = emsIngateRestrictionController.getAllRestrictions();
		assertEquals(emsIngateRestrictionList.getStatusCodeValue(),200);
	}

	@Test
	void testGetAllRestrictionsNoRecordsFoundException() {
		when(emsIngateRestrictionService.getAllRestrictions()).thenThrow(new NoRecordsFoundException());
		when(emsIngateRestrictionService.insertRestriction(Mockito.any(),Mockito.any())).thenThrow(new NoRecordsFoundException());
		when(emsIngateRestrictionService.updateRestriction(Mockito.any(),Mockito.any())).thenThrow(new NoRecordsFoundException());

		ResponseEntity<APIResponse<List<EMSIngateRestrictionDTO>>> emsResponseGet = emsIngateRestrictionController.getAllRestrictions();
		ResponseEntity<APIResponse<EMSIngateRestrictionDTO>> emsResponseAdd = emsIngateRestrictionController.addRestriction(emsIngateRestrictionDto,header);
		ResponseEntity<APIResponse<EMSIngateRestrictionDTO>> emsResponseUpdate =  emsIngateRestrictionController.updateRestriction(emsIngateRestrictionDto,header);

		Assertions.assertEquals(emsResponseGet.getStatusCodeValue(), 404);
		Assertions.assertEquals(emsResponseAdd.getStatusCodeValue(),404);
		Assertions.assertEquals(emsResponseUpdate.getStatusCodeValue(),404);
	}

	@Test
	void testGetAllRestrictionsRuntimeException() {
		when(emsIngateRestrictionService.getAllRestrictions()).thenThrow(new RuntimeException());
		when(emsIngateRestrictionService.insertRestriction(Mockito.any(),Mockito.any())).thenThrow(new RuntimeException());
		when(emsIngateRestrictionService.updateRestriction(Mockito.any(),Mockito.any())).thenThrow(new RuntimeException());

		ResponseEntity<APIResponse<List<EMSIngateRestrictionDTO>>> emsResponseGet = emsIngateRestrictionController.getAllRestrictions();
		ResponseEntity<APIResponse<EMSIngateRestrictionDTO>> emsResponseAdd = emsIngateRestrictionController.addRestriction(emsIngateRestrictionDto,header);
		ResponseEntity<APIResponse<EMSIngateRestrictionDTO>> emsResponseUpdate =  emsIngateRestrictionController.updateRestriction(emsIngateRestrictionDto,header);

		Assertions.assertEquals(emsResponseGet.getStatusCodeValue(), 500);
		Assertions.assertEquals(emsResponseAdd.getStatusCodeValue(),500);
		Assertions.assertEquals(emsResponseUpdate.getStatusCodeValue(),500);
	}

	@Test
	void testUpdateRestrictionsOptimisticLockException() {
		when(emsIngateRestrictionService.updateRestriction(Mockito.any(),Mockito.any())).thenThrow(new RuntimeException("Test", new OptimisticLockException("Test")));
		ResponseEntity<APIResponse<EMSIngateRestrictionDTO>> emsResponseUpdate = emsIngateRestrictionController.updateRestriction(Mockito.any(),Mockito.any());
		Assertions.assertEquals(emsResponseUpdate.getStatusCodeValue(), 409);
	}

	@Test
	void testGetAllRestrictionsSizeExceedException() {
		when(emsIngateRestrictionService.insertRestriction(Mockito.any(),Mockito.any())).thenThrow(new SizeExceedException());
		when(emsIngateRestrictionService.updateRestriction(Mockito.any(),Mockito.any())).thenThrow(new SizeExceedException());

		ResponseEntity<APIResponse<EMSIngateRestrictionDTO>> emsResponseAdd = emsIngateRestrictionController.addRestriction(emsIngateRestrictionDto,header);
		ResponseEntity<APIResponse<EMSIngateRestrictionDTO>> emsResponseUpdate =  emsIngateRestrictionController.updateRestriction(emsIngateRestrictionDto,header);

		Assertions.assertEquals(emsResponseAdd.getStatusCodeValue(),411);
		Assertions.assertEquals(emsResponseUpdate.getStatusCodeValue(),411);
	}

	@Test
	void testGetAllRestrictionsRecordNotAddedException() {
		when(emsIngateRestrictionService.insertRestriction(Mockito.any(),Mockito.any())).thenThrow(new RecordNotAddedException());
		when(emsIngateRestrictionService.updateRestriction(Mockito.any(),Mockito.any())).thenThrow(new RecordNotAddedException());

		ResponseEntity<APIResponse<EMSIngateRestrictionDTO>> emsResponseAdd = emsIngateRestrictionController.addRestriction(emsIngateRestrictionDto,header);
		ResponseEntity<APIResponse<EMSIngateRestrictionDTO>> emsResponseUpdate =  emsIngateRestrictionController.updateRestriction(emsIngateRestrictionDto,header);

		Assertions.assertEquals(emsResponseAdd.getStatusCodeValue(),406);
		Assertions.assertEquals(emsResponseUpdate.getStatusCodeValue(),406);
	}

	@Test
	void testGetAllRestrictionsRecordAlreadyExistsException() {
		when(emsIngateRestrictionService.insertRestriction(Mockito.any(),Mockito.any())).thenThrow(new RecordAlreadyExistsException());
		when(emsIngateRestrictionService.updateRestriction(Mockito.any(),Mockito.any())).thenThrow(new RecordAlreadyExistsException());

		ResponseEntity<APIResponse<EMSIngateRestrictionDTO>> emsResponseAdd = emsIngateRestrictionController.addRestriction(emsIngateRestrictionDto,header);
		ResponseEntity<APIResponse<EMSIngateRestrictionDTO>> emsResponseUpdate =  emsIngateRestrictionController.updateRestriction(emsIngateRestrictionDto,header);

		Assertions.assertEquals(emsResponseAdd.getStatusCodeValue(),208);
		Assertions.assertEquals(emsResponseUpdate.getStatusCodeValue(),208);
	}

	@Test
	void testGetAllRestrictionsNullPointerException() {
		when(emsIngateRestrictionService.getAllRestrictions()).thenThrow(new NullPointerException());
		when(emsIngateRestrictionService.insertRestriction(Mockito.any(),Mockito.any())).thenThrow(new NullPointerException());
		when(emsIngateRestrictionService.updateRestriction(Mockito.any(),Mockito.any())).thenThrow(new NullPointerException());

		ResponseEntity<APIResponse<List<EMSIngateRestrictionDTO>>> emsResponseGet = emsIngateRestrictionController.getAllRestrictions();
		ResponseEntity<APIResponse<EMSIngateRestrictionDTO>> emsResponseAdd = emsIngateRestrictionController.addRestriction(emsIngateRestrictionDto,header);
		ResponseEntity<APIResponse<EMSIngateRestrictionDTO>> emsResponseUpdate =  emsIngateRestrictionController.updateRestriction(emsIngateRestrictionDto,header);

		Assertions.assertEquals(emsResponseGet.getStatusCodeValue(), 400);
		Assertions.assertEquals(emsResponseAdd.getStatusCodeValue(),400);
		Assertions.assertEquals(emsResponseUpdate.getStatusCodeValue(),400);
	}
	
	@Test
	void testAddRestriction() {
		when(emsIngateRestrictionMapper.emsIngateRestrictionDTOToEMSIngateRestriction(emsIngateRestrictionDto)).thenReturn(emsIngateRestriction);
		when(emsIngateRestrictionService.insertRestriction(Mockito.any(), Mockito.any())).thenReturn(emsIngateRestriction);
		when(emsIngateRestrictionMapper.emsIngateRestrictionToEMSIngateRestrictionDTO(emsIngateRestriction)).thenReturn(emsIngateRestrictionDto);
		ResponseEntity<APIResponse<EMSIngateRestrictionDTO>> addedData = emsIngateRestrictionController.addRestriction(emsIngateRestrictionDto, header);
		assertEquals(addedData.getStatusCodeValue(),200);
	}
	
	@Test
	void testUpdateRestriction() {
		when(emsIngateRestrictionMapper.emsIngateRestrictionDTOToEMSIngateRestriction(Mockito.any())).thenReturn(emsIngateRestriction);
		when(emsIngateRestrictionService.updateRestriction(Mockito.any(),Mockito.any())).thenReturn(emsIngateRestriction);
		when(emsIngateRestrictionMapper.emsIngateRestrictionToEMSIngateRestrictionDTO(Mockito.any())).thenReturn(emsIngateRestrictionDto);
		ResponseEntity<APIResponse<EMSIngateRestrictionDTO>> restrictionUpdated = emsIngateRestrictionController.updateRestriction(emsIngateRestrictionDto, header);
		assertEquals(restrictionUpdated.getStatusCodeValue(),200);
	}

}
