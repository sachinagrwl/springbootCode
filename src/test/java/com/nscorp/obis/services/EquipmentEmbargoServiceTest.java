package com.nscorp.obis.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.domain.EquipmentEmbargo;
import com.nscorp.obis.domain.RestrictedWells;
import com.nscorp.obis.domain.Station;
import com.nscorp.obis.dto.EquipmentEmbargoDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.repository.EquipmentEmbargoRepository;
import com.nscorp.obis.repository.StationRepository;

class EquipmentEmbargoServiceTest {
	
	@InjectMocks
	EquipmentEmbargoServiceImpl eqEmbargoService;
	
	@Mock
	EquipmentEmbargoRepository eqEmbargoRepo;
	
	@Mock
	StationRepository stationRepo;
	
	EquipmentEmbargo eqEmbargo;
	EquipmentEmbargoDTO eqEmbargoDto;
	List<EquipmentEmbargo> eqEmbargoList;
	List<EquipmentEmbargoDTO> eqEmbargoDtoList;
	Station station;
	
	Map<String, String> header;
	
	BigDecimal eqNrLow = new BigDecimal(1000);
	BigDecimal eqNrHigh = new BigDecimal(1000);
	BigDecimal carNrLow = new BigDecimal(1000);
	BigDecimal carNrHigh = new BigDecimal(1000);
	BigDecimal eqNrLow1 = new BigDecimal(50);
	BigDecimal eqNrHigh1 = new BigDecimal(40);
	BigDecimal carNrLow1 = new BigDecimal(50);
	BigDecimal carNrHigh1 = new BigDecimal(40);
	List<RestrictedWells> restrictWells = new ArrayList<>();

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		eqEmbargo = new EquipmentEmbargo();
		eqEmbargo.setCarAarType("TEST");
		eqEmbargo.setCarEquipmentType("C");
		eqEmbargo.setCarInit("QWER");
		eqEmbargo.setCarNumberHigh(carNrHigh);
		eqEmbargo.setCarNumberLow(carNrLow);
		eqEmbargo.setDescription("DO NOT LOAD 20'. NOT 20' CAPABLE");
		eqEmbargo.setEmbargoId(123000L);
		eqEmbargo.setEquipmentAarType("SERT");
		eqEmbargo.setEquipmentInit("HIGH");
		eqEmbargo.setEquipmentLength(20);
		eqEmbargo.setEquipmentNumberHigh(eqNrHigh);
		eqEmbargo.setEquipmentNumberLow(eqNrLow);
		eqEmbargo.setEquipmentType("C");
		eqEmbargo.setOriginTerminal(null);
		eqEmbargo.setRestrictedWells(restrictWells);
		eqEmbargo.setRestriction("E");
		eqEmbargo.setRoadName("AMDX");
		eqEmbargo.setTofcCofcIndicator("T");
		
		eqEmbargoList = new ArrayList<>();
		eqEmbargoList.add(eqEmbargo);
		
		eqEmbargoDto = new EquipmentEmbargoDTO();
		eqEmbargoDto.setCarAarType("TEST");
		eqEmbargoDto.setCarEquipmentType("C");
		eqEmbargoDto.setCarInit("QWER");
		eqEmbargoDto.setCarNumberHigh(carNrHigh);
		eqEmbargoDto.setCarNumberLow(carNrLow);
		eqEmbargoDto.setDescription("DO NOT LOAD 20'. NOT 20' CAPABLE");
		eqEmbargoDto.setEmbargoId(123000L);
		eqEmbargoDto.setDestinationTerminal(null);
		eqEmbargoDto.setEquipmentAarType("SERT");
		eqEmbargoDto.setEquipmentInit("HIGH");
		eqEmbargoDto.setEquipmentLength(20);
		eqEmbargoDto.setEquipmentNumberHigh(eqNrHigh);
		eqEmbargoDto.setEquipmentNumberLow(eqNrLow);
		eqEmbargoDto.setEquipmentType("C");
		eqEmbargoDto.setOriginTerminal(null);
		eqEmbargoDto.setRestrictedWells(restrictWells);
		eqEmbargoDto.setRestriction("Y");
		eqEmbargoDto.setRoadName("AMDX");
		eqEmbargoDto.setTofcCofcIndicator("T");
		
		eqEmbargoDtoList = new ArrayList<>();
		eqEmbargoDtoList.add(eqEmbargoDto);
		
		station = new Station();
		station.setTermId(18245946233393L);
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
	}

	@AfterEach
	void tearDown() throws Exception {
		eqEmbargo = null;
		eqEmbargoList = null;
		eqEmbargoDto = null;
		eqEmbargoDtoList = null;
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"TEST",""," "})
	void testGetAllEmbargo(String roadName) {
		station.setStationName(roadName);
		station.setRoadName(roadName);
		when(eqEmbargoRepo.findAllByOrderByUpdateDateTimeDesc()).thenReturn(eqEmbargoList);
		when(stationRepo.existsById(Mockito.any())).thenReturn(true);
		when(stationRepo.findById(Mockito.any())).thenReturn(Optional.of(station));
		List<EquipmentEmbargo> getEmbargo = eqEmbargoService.getAllEmbargo();
		assertEquals(getEmbargo, eqEmbargoList);
	}
	
	@Test
	void testGetAllEmbargoNegativeFlow() {
		EquipmentEmbargo equipmentEmbargo = eqEmbargoList.get(0);
		equipmentEmbargo.setRoadName(null);
		equipmentEmbargo.setDestinationTerminal(null);
		equipmentEmbargo.setOriginTerminal(null);
		when(stationRepo.existsById(Mockito.any())).thenReturn(false);
		eqEmbargoList.add(equipmentEmbargo);
		when(eqEmbargoRepo.findAllByOrderByUpdateDateTimeDesc()).thenReturn(eqEmbargoList);
		List<EquipmentEmbargo> getEmbargo = eqEmbargoService.getAllEmbargo();
		assertEquals(getEmbargo, eqEmbargoList);
	}
	
	@Test
	void testGetAllEmbargoException() {
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(eqEmbargoService.getAllEmbargo()));
		assertEquals("No Record Found under this search!", exception.getMessage());
	}

	@Test
	void testAddEquipEmbargo() {
		
		when(eqEmbargoRepo.findById(Mockito.any())).thenReturn(Optional.of(eqEmbargo));
		when(eqEmbargoRepo.save(Mockito.any())).thenReturn(eqEmbargo);
		when(stationRepo.findById(Mockito.any())).thenReturn(Optional.of(station));
		EquipmentEmbargo addEqEmbargo = eqEmbargoService.addEquipEmbargo(eqEmbargo, header);
		assertNotNull(addEqEmbargo);
		
		Station originTerminal = new Station();
		originTerminal.setTermId(123000L);
		originTerminal.setFSAC("FSAC");
		originTerminal.setState("AZ");
		originTerminal.setRoadName("NS");
		originTerminal.setBillAtFsac("FSACBill");
		originTerminal.setIntermodalIndicator("Y");
		originTerminal.setRoadNumber("ABCD");
		originTerminal.setBottomPick("Y");
		originTerminal.setChar5Alias("ABCSA");
		originTerminal.setChar8Spell("ASASASAS");
		originTerminal.setOperationStation("ATLANTA");
		eqEmbargo.setOriginTerminal(originTerminal);


		when(stationRepo.existsByTermIdAndExpiredDateIsNull(Mockito.any())).thenReturn(false);
		NoRecordsFoundException exception15 = assertThrows(NoRecordsFoundException.class,
				() -> when(eqEmbargoService.addEquipEmbargo(eqEmbargo, header)));
		assertEquals("Origin Terminal with Id 18245946233393 not found!", exception15.getMessage());

		Station destinationTerminal = new Station();
		destinationTerminal.setTermId(123000L);
		destinationTerminal.setFSAC("FSAC");
		destinationTerminal.setState("AZ");
		destinationTerminal.setRoadName("NS");
		destinationTerminal.setBillAtFsac("FSACBill");
		destinationTerminal.setIntermodalIndicator("Y");
		destinationTerminal.setRoadNumber("ABCD");
		destinationTerminal.setBottomPick("Y");
		destinationTerminal.setChar5Alias("ABCSA");
		destinationTerminal.setChar8Spell("ASASASAS");
		destinationTerminal.setOperationStation("ATLANTA");
		eqEmbargo.setDestinationTerminal(destinationTerminal);

		when(stationRepo.existsByTermIdAndExpiredDateIsNull(Mockito.any())).thenReturn(false);
		NoRecordsFoundException exception151 = assertThrows(NoRecordsFoundException.class,
				() -> when(eqEmbargoService.addEquipEmbargo(eqEmbargo, header)));
		assertEquals("Destination Terminal with Id 18245946233393 not found!", exception151.getMessage());
		
	}
	
	@Test
	void testAddEquipEmbargoException() {

		EquipmentEmbargo obj1 = new EquipmentEmbargo();
		obj1.setCarAarType("TEST");
		obj1.setCarEquipmentType("C");
		obj1.setCarInit("QWER");
		obj1.setCarNumberHigh(carNrHigh);
		obj1.setCarNumberLow(carNrLow);
		obj1.setDescription("DO NOT LOAD 20'. NOT 20' CAPABLE");
		obj1.setEmbargoId(123000L);
		obj1.setDestinationTerminal(null);
		obj1.setEquipmentAarType("SERT");
		obj1.setEquipmentInit(null);
		obj1.setEquipmentLength(null);
		obj1.setEquipmentNumberHigh(eqNrHigh);
		obj1.setEquipmentNumberLow(eqNrLow);
		obj1.setEquipmentType("C");
		obj1.setOriginTerminal(null);
		obj1.setRestrictedWells(restrictWells);
		obj1.setRestriction("Y");
		obj1.setRoadName("AMDX");
		obj1.setTofcCofcIndicator("T");
		RecordNotAddedException exception1 = assertThrows(RecordNotAddedException.class,
				() -> when(eqEmbargoService.addEquipEmbargo(obj1, header)));
		assertEquals("Either 'EQ INIT' or 'EQ LENGTH' should be provided", exception1.getMessage());
	
		EquipmentEmbargo obj12 = new EquipmentEmbargo();
		obj12.setCarAarType("TEST");
		obj12.setCarEquipmentType("C");
		obj12.setCarInit("QWER");
		obj12.setCarNumberHigh(carNrHigh);
		obj12.setCarNumberLow(carNrLow);
		obj12.setDescription("DO NOT LOAD 20'. NOT 20' CAPABLE");
		obj12.setEmbargoId(123000L);
		obj12.setDestinationTerminal(null);
		obj12.setEquipmentAarType("SERT");
		obj12.setEquipmentInit(null);
		obj12.setEquipmentLength(20);
		obj12.setEquipmentNumberHigh(eqNrHigh);
		obj12.setEquipmentNumberLow(eqNrLow);
		obj12.setEquipmentType("C");
		obj12.setOriginTerminal(null);
		obj12.setRestrictedWells(restrictWells);
		obj12.setRestriction("Y");
		obj12.setRoadName("AMDX");
		obj12.setTofcCofcIndicator("T");
		RecordNotAddedException exception12 = assertThrows(RecordNotAddedException.class,
				() -> when(eqEmbargoService.addEquipEmbargo(obj12, header)));
		assertEquals("'EQ INIT' value should be provided for Nr values", exception12.getMessage());
		
		EquipmentEmbargo obj2 = new EquipmentEmbargo();
		obj2.setCarAarType("TEST");
		obj2.setCarEquipmentType("C");
		obj2.setCarInit("QWER");
		obj2.setCarNumberHigh(carNrHigh);
		obj2.setCarNumberLow(carNrLow);
		obj2.setDescription("DO NOT LOAD 20'. NOT 20' CAPABLE");
		obj2.setEmbargoId(123000L);
		obj2.setDestinationTerminal(null);
		obj2.setEquipmentAarType("SERT");
		obj2.setEquipmentInit("QWER");
		obj2.setEquipmentLength(20);
		obj2.setEquipmentNumberHigh(eqNrHigh1);
		obj2.setEquipmentNumberLow(eqNrLow1);
		obj2.setEquipmentType("C");
		obj2.setOriginTerminal(null);
		obj2.setRestrictedWells(restrictWells);
		obj2.setRestriction("Y");
		obj2.setRoadName("AMDX");
		obj2.setTofcCofcIndicator("T");
		RecordNotAddedException exception2 = assertThrows(RecordNotAddedException.class,
				() -> when(eqEmbargoService.addEquipEmbargo(obj2, header)));
		assertEquals("'EQUIP HIGH NR' should be greater than the 'EQUIP LOW NR'", exception2.getMessage());
		
		EquipmentEmbargo obj3 = new EquipmentEmbargo();
		obj3.setCarAarType("TEST");
		obj3.setCarEquipmentType("C");
		obj3.setCarInit("QWER");
		obj3.setCarNumberHigh(carNrHigh);
		obj3.setCarNumberLow(carNrLow);
		obj3.setDescription("DO NOT LOAD 20'. NOT 20' CAPABLE");
		obj3.setEmbargoId(123000L);
		obj3.setDestinationTerminal(null);
		obj3.setEquipmentAarType("SERT");
		obj3.setEquipmentInit("QWER");
		obj3.setEquipmentNumberHigh(eqNrHigh);
		obj3.setEquipmentNumberLow(eqNrLow);
        obj3.setEquipmentType("C");
        obj3.setEquipmentLength(80);
        obj3.setOriginTerminal(null);
		obj3.setRestrictedWells(restrictWells);
		obj3.setRestriction("Y");
		obj3.setRoadName("AMDX");
		obj3.setTofcCofcIndicator("T");
		
        RecordNotAddedException exception3 = assertThrows(RecordNotAddedException.class,
                () -> when(eqEmbargoService
                        .addEquipEmbargo(obj3, header)));
        assertEquals("Equipment Length: 80 is not valid for Equipment Type: C", exception3.getMessage());
        
        EquipmentEmbargo obj31 = new EquipmentEmbargo();
		obj31.setCarAarType("TEST");
		obj31.setCarEquipmentType("C");
		obj31.setCarInit("QWER");
		obj31.setCarNumberHigh(carNrHigh);
		obj31.setCarNumberLow(carNrLow);
		obj31.setDescription("DO NOT LOAD 20'. NOT 20' CAPABLE");
		obj31.setEmbargoId(123000L);
		obj31.setDestinationTerminal(null);
		obj31.setEquipmentAarType("SERT");
		obj31.setEquipmentInit("QWER");
		obj31.setEquipmentNumberHigh(eqNrHigh);
		obj31.setEquipmentNumberLow(eqNrLow);
        obj31.setEquipmentType(null);
        obj31.setEquipmentLength(90);
        obj31.setOriginTerminal(null);
		obj31.setRestrictedWells(restrictWells);
		obj31.setRestriction("Y");
		obj31.setRoadName("AMDX");
		obj31.setTofcCofcIndicator("T");
		
        RecordNotAddedException exception31 = assertThrows(RecordNotAddedException.class,
                () -> when(eqEmbargoService
                        .addEquipEmbargo(obj31, header)));
        assertEquals("'equipmentLength' 90 is an Invalid Value", exception31.getMessage());

        EquipmentEmbargo obj4 = new EquipmentEmbargo();
		obj4.setCarAarType("TEST");
		obj4.setCarEquipmentType("C");
		obj4.setCarInit("QWER");
		obj4.setCarNumberHigh(carNrHigh);
		obj4.setCarNumberLow(carNrLow);
		obj4.setDescription("DO NOT LOAD 20'. NOT 20' CAPABLE");
		obj4.setEmbargoId(123000L);
		obj4.setDestinationTerminal(null);
		obj4.setEquipmentAarType("SERT");
		obj4.setEquipmentInit("QWER");
		obj4.setEquipmentNumberHigh(eqNrHigh);
		obj4.setEquipmentNumberLow(eqNrLow);
		obj4.setOriginTerminal(null);
		obj4.setRestrictedWells(restrictWells);
		obj4.setRestriction("Y");
		obj4.setRoadName("AMDX");
		obj4.setTofcCofcIndicator("T");
        obj4.setEquipmentType("T");
        obj4.setEquipmentLength(80);
        RecordNotAddedException exception4 = assertThrows(RecordNotAddedException.class,
                () -> when(eqEmbargoService
                        .addEquipEmbargo(obj4, header)));
        assertEquals("Equipment Length: 80 is not valid for Equipment Type: T", exception4.getMessage());

        EquipmentEmbargo obj5 = new EquipmentEmbargo();
        obj5.setCarAarType("TEST");
        obj5.setCarEquipmentType("C");
        obj5.setCarInit("QWER");
        obj5.setCarNumberHigh(carNrHigh);
        obj5.setCarNumberLow(carNrLow);
        obj5.setDescription("DO NOT LOAD 20'. NOT 20' CAPABLE");
		obj5.setEmbargoId(123000L);
		obj5.setDestinationTerminal(null);
		obj5.setEquipmentAarType("SERT");
		obj5.setEquipmentInit("QWER");
		obj5.setEquipmentNumberHigh(eqNrHigh);
		obj5.setEquipmentNumberLow(eqNrLow);
		obj5.setOriginTerminal(null);
		obj5.setRestrictedWells(restrictWells);
		obj5.setRestriction("Y");
		obj5.setRoadName("AMDX");
		obj5.setTofcCofcIndicator("T");
        obj5.setEquipmentType("Z");
        obj5.setEquipmentLength(80);
        RecordNotAddedException exception5 = assertThrows(RecordNotAddedException.class,
                () -> when(eqEmbargoService
                        .addEquipEmbargo(obj5, header)));
        assertEquals("Equipment Length: 80 is not valid for Equipment Type: Z", exception5.getMessage());
              
        EquipmentEmbargo obj6 = new EquipmentEmbargo();
        obj6.setCarAarType(null);
        obj6.setCarEquipmentType("C");
        obj6.setCarInit(null);
        obj6.setCarNumberHigh(eqNrHigh);
        obj6.setCarNumberLow(null);
        obj6.setDescription("DO NOT LOAD 20'. NOT 20' CAPABLE");
		obj6.setEmbargoId(123000L);
		obj6.setDestinationTerminal(null);
		obj6.setEquipmentAarType("SERT");
		obj6.setEquipmentInit("QWER");
		obj6.setEquipmentNumberHigh(eqNrHigh);
		obj6.setEquipmentNumberLow(eqNrLow);
		obj6.setOriginTerminal(null);
		obj6.setRestrictedWells(restrictWells);
		obj6.setRestriction("E");
		obj6.setRoadName("AMDX");
		obj6.setTofcCofcIndicator("T");
		obj6.setEquipmentType("Z");
		obj6.setEquipmentLength(20);
        RecordNotAddedException exception6 = assertThrows(RecordNotAddedException.class,
                () -> when(eqEmbargoService
                        .addEquipEmbargo(obj6, header)));
        assertEquals("'CAR LOW NR' value should be provided", exception6.getMessage());
        
        EquipmentEmbargo obj61 = new EquipmentEmbargo();
        obj61.setCarAarType(null);
        obj61.setCarEquipmentType("C");
        obj61.setCarInit(null);
        obj61.setCarNumberHigh(null);
        obj61.setCarNumberLow(carNrLow);
        obj61.setDescription("DO NOT LOAD 20'. NOT 20' CAPABLE");
		obj61.setEmbargoId(123000L);
		obj61.setDestinationTerminal(null);
		obj61.setEquipmentAarType("SERT");
		obj61.setEquipmentInit("QWER");
		obj61.setEquipmentNumberHigh(eqNrHigh);
		obj61.setEquipmentNumberLow(eqNrLow);
		obj61.setDestinationTerminal(null);
		obj61.setRestrictedWells(restrictWells);
		obj61.setRestriction("E");
		obj61.setRoadName("AMDX");
		obj61.setTofcCofcIndicator("T");
		obj61.setEquipmentType("Z");
		obj61.setEquipmentLength(20);
        RecordNotAddedException exception61 = assertThrows(RecordNotAddedException.class,
                () -> when(eqEmbargoService
                        .addEquipEmbargo(obj61, header)));
        assertEquals("'CAR HIGH NR' value should be provided", exception61.getMessage());
        
        EquipmentEmbargo obj15 = new EquipmentEmbargo();
        obj15.setCarAarType("ABCD");
        obj15.setCarEquipmentType("C");
        obj15.setCarInit("QWER");
        obj15.setCarNumberHigh(carNrHigh);
        obj15.setCarNumberLow(eqNrLow);
        obj15.setDescription("DO NOT LOAD 20'. NOT 20' CAPABLE");
		obj15.setEmbargoId(123000L);
		obj15.setDestinationTerminal(null);
		obj15.setEquipmentAarType("SERT");
		obj15.setEquipmentInit("QWER");
		obj15.setEquipmentNumberHigh(null);
		obj15.setEquipmentNumberLow(eqNrLow);
		obj15.setOriginTerminal(null);
		obj15.setRestrictedWells(restrictWells);
		obj15.setRestriction("E");
		obj15.setRoadName("AMDX");
		obj15.setTofcCofcIndicator("T");
		obj15.setEquipmentType("Z");
		obj15.setEquipmentLength(20);
        RecordNotAddedException exception16= assertThrows(RecordNotAddedException.class,
                () -> when(eqEmbargoService
                        .addEquipEmbargo(obj15, header)));
        assertEquals("'EQ NR HIGH' value should be provided", exception16.getMessage());
        
        EquipmentEmbargo obj151 = new EquipmentEmbargo();
        obj151.setCarAarType("ABCD");
        obj151.setCarEquipmentType("C");
        obj151.setCarInit("QWER");
        obj151.setCarNumberHigh(carNrHigh);
        obj151.setCarNumberLow(eqNrLow);
        obj151.setDescription("DO NOT LOAD 20'. NOT 20' CAPABLE");
		obj151.setEmbargoId(123000L);
		obj151.setDestinationTerminal(null);
		obj151.setEquipmentAarType("SERT");
		obj151.setEquipmentInit("QWER");
		obj151.setEquipmentNumberHigh(eqNrHigh);
		obj151.setEquipmentNumberLow(null);
		obj151.setOriginTerminal(null);
		obj151.setRestrictedWells(restrictWells);
		obj151.setRestriction("E");
		obj151.setRoadName("AMDX");
		obj151.setTofcCofcIndicator("T");
		obj151.setEquipmentType("Z");
		obj151.setEquipmentLength(20);
        RecordNotAddedException exception161= assertThrows(RecordNotAddedException.class,
                () -> when(eqEmbargoService
                        .addEquipEmbargo(obj151, header)));
        assertEquals("'EQ NR LOW' value should be provided", exception161.getMessage());
        
        EquipmentEmbargo obj7 = new EquipmentEmbargo();
        obj7.setCarAarType("ABCD");
        obj7.setCarEquipmentType("C");
        obj7.setCarInit(null);
        obj7.setCarNumberHigh(carNrHigh);
        obj7.setCarNumberLow(eqNrLow);
        obj7.setDescription("DO NOT LOAD 20'. NOT 20' CAPABLE");
		obj7.setEmbargoId(123000L);
		obj7.setDestinationTerminal(null);
		obj7.setEquipmentAarType("SERT");
		obj7.setEquipmentInit("QWER");
		obj7.setEquipmentNumberHigh(eqNrHigh);
		obj7.setEquipmentNumberLow(eqNrLow);
		obj7.setOriginTerminal(null);
		obj7.setRestrictedWells(restrictWells);
		obj7.setRestriction("E");
		obj7.setRoadName("AMDX");
		obj7.setTofcCofcIndicator("T");
		obj7.setEquipmentType("Z");
		obj7.setEquipmentLength(20);
        RecordNotAddedException exception7= assertThrows(RecordNotAddedException.class,
                () -> when(eqEmbargoService
                        .addEquipEmbargo(obj7, header)));
        assertEquals("'CAR INIT' value should be provided for Nr values", exception7.getMessage());
        
        EquipmentEmbargo obj8 = new EquipmentEmbargo();
        obj8.setCarAarType("ABCD");
        obj8.setCarEquipmentType("C");
        obj8.setCarInit("ABCD");
        obj8.setCarNumberHigh(carNrHigh1);
        obj8.setCarNumberLow(carNrLow1);
        obj8.setDescription("DO NOT LOAD 20'. NOT 20' CAPABLE");
		obj8.setEmbargoId(123000L);
		obj8.setDestinationTerminal(null);
		obj8.setEquipmentAarType("SERT");
		obj8.setEquipmentInit("QWER");
		obj8.setEquipmentNumberHigh(eqNrHigh);
		obj8.setEquipmentNumberLow(eqNrLow);
		obj8.setOriginTerminal(null);
		obj8.setRestrictedWells(restrictWells);
		obj8.setRestriction("E");
		obj8.setRoadName("AMDX");
		obj8.setTofcCofcIndicator("T");
		obj8.setEquipmentType("Z");
		obj8.setEquipmentLength(20);
		RecordNotAddedException exception8 = assertThrows(RecordNotAddedException.class,
				() -> when(eqEmbargoService.addEquipEmbargo(obj8, header)));
		assertEquals("'CAR HIGH NR' should be greater than the 'CAR LOW NR'", exception8.getMessage());
		
		EquipmentEmbargo obj9 = new EquipmentEmbargo();
        obj9.setCarAarType(null);
        obj9.setCarEquipmentType("C");
        obj9.setCarInit("ABCD");
        obj9.setCarNumberHigh(carNrHigh);
        obj9.setCarNumberLow(carNrLow);
        obj9.setDescription("DO NOT LOAD 20'. NOT 20' CAPABLE");
		obj9.setEmbargoId(123000L);
		obj9.setDestinationTerminal(null);
		obj9.setEquipmentAarType("SERT");
		obj9.setEquipmentInit("QWER");
		obj9.setEquipmentNumberHigh(eqNrHigh);
		obj9.setEquipmentNumberLow(eqNrLow);
		obj9.setOriginTerminal(null);
		obj9.setRestrictedWells(restrictWells);
		obj9.setRestriction("Y");
		obj9.setRoadName("AMDX");
		obj9.setTofcCofcIndicator("T");
		obj9.setEquipmentType("Z");
		obj9.setEquipmentLength(20);
		RecordNotAddedException exception9 = assertThrows(RecordNotAddedException.class,
				() -> when(eqEmbargoService.addEquipEmbargo(obj9, header)));
		assertEquals("'RESTRICTED WELLS' should be provided", exception9.getMessage());
		
		EquipmentEmbargo obj10 = new EquipmentEmbargo();
        obj10.setCarAarType(null);
        obj10.setCarEquipmentType("C");
        obj10.setCarInit(null);
        obj10.setCarNumberHigh(null);
        obj10.setCarNumberLow(null);
        obj10.setDescription("DO NOT LOAD 20'. NOT 20' CAPABLE");
		obj10.setEmbargoId(123000L);
		obj10.setDestinationTerminal(null);
		obj10.setEquipmentAarType("SERT");
		obj10.setEquipmentInit("QWER");
		obj10.setEquipmentNumberHigh(eqNrHigh);
		obj10.setEquipmentNumberLow(eqNrLow);
		obj10.setOriginTerminal(null);
		obj10.setRestrictedWells(restrictWells);
		obj10.setRestriction("E");
		obj10.setRoadName("AMDX");
		obj10.setTofcCofcIndicator("T");
		obj10.setEquipmentType("Z");
		obj10.setEquipmentLength(20);
		RecordNotAddedException exception10 = assertThrows(RecordNotAddedException.class,
				() -> when(eqEmbargoService.addEquipEmbargo(obj10, header)));
		assertEquals("Either 'CAR INIT' or 'CAR AAR TYPE' should be provided", exception10.getMessage());
		
		EquipmentEmbargo obj11 = new EquipmentEmbargo();
        obj11.setCarAarType("AVDF");
        obj11.setCarEquipmentType("C");
        obj11.setCarInit("ABCD");
        obj11.setCarNumberHigh(null);
        obj11.setCarNumberLow(null);
        obj11.setDescription("DO NOT LOAD 20'. NOT 20' CAPABLE");
		obj11.setEmbargoId(123000L);
		obj11.setDestinationTerminal(null);
		obj11.setEquipmentAarType("SERT");
		obj11.setEquipmentInit("QWER");
		obj11.setEquipmentNumberHigh(eqNrHigh);
		obj11.setEquipmentNumberLow(eqNrLow);
		obj11.setOriginTerminal(null);
		obj11.setRestrictedWells(restrictWells);
		obj11.setRestriction("E");
		obj11.setRoadName("AMDX");
		obj11.setTofcCofcIndicator(null);
		obj11.setEquipmentType("Z");
		obj11.setEquipmentLength(20);
		RecordNotAddedException exception11 = assertThrows(RecordNotAddedException.class,
				() -> when(eqEmbargoService.addEquipEmbargo(obj11, header)));
		assertEquals("'TOFC/COFC Ind' should be provided", exception11.getMessage());
		
	}
	
	@Test
	void testAddTermIdNullPointerException() {
		header.put("extensionschema", null);
		assertNull(header.get(CommonConstants.EXTENSION_SCHEMA));
		NullPointerException exception = assertThrows(NullPointerException.class,
				() -> eqEmbargoService.addEquipEmbargo(eqEmbargo, header));
		assertEquals("Extension Schema should not be null, empty or blank", exception.getMessage());
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"!",""," "})
	void testUpdateEquipEmbargo(String uVersion) {
		when(eqEmbargoRepo.existsByEmbargoIdAndUversion(Mockito.any(),Mockito.any())).thenReturn(true);
		eqEmbargo.setUversion(uVersion);
		when(eqEmbargoRepo.findById(Mockito.any())).thenReturn(Optional.of(eqEmbargo));
		when(eqEmbargoRepo.save(Mockito.any())).thenReturn(eqEmbargo);
		when(stationRepo.findById(Mockito.any())).thenReturn(Optional.of(station));
		EquipmentEmbargo updateEmbargo = eqEmbargoService.updateEquipmentEmbargo(eqEmbargo, header);
		assertNotNull(updateEmbargo);
	}
	
	@Test
	void testUpdateEquipEmbargoException() {
		when(eqEmbargoRepo.existsByEmbargoIdAndUversion(Mockito.any(),Mockito.any())).thenReturn(true);
		when(eqEmbargoRepo.findById(Mockito.any())).thenReturn(Optional.of(eqEmbargo));
		when(stationRepo.findById(Mockito.any())).thenReturn(Optional.of(station));
		when(eqEmbargoRepo.save(Mockito.any())).thenReturn(eqEmbargo);
		header.put("extensionschema", null);
		assertNull(header.get(CommonConstants.EXTENSION_SCHEMA));
		NullPointerException exception = assertThrows(NullPointerException.class,
				() -> eqEmbargoService.updateEquipmentEmbargo(eqEmbargo, header));
		assertEquals("Extension Schema should not be null, empty or blank", exception.getMessage());
	}
	
	@Test
	void testDeleteEquipEmbargo() {
		when(eqEmbargoRepo.existsByEmbargoIdAndUversion(Mockito.any(),Mockito.any())).thenReturn(true);
		when(eqEmbargoRepo.findById(Mockito.any())).thenReturn(Optional.of(eqEmbargo));
		EquipmentEmbargo deleteEmbargo = eqEmbargoService.deleteEquipmentEmbargo(eqEmbargo);
		assertNotNull(deleteEmbargo);
	}
	
	@Test
	void testUpdateAndDeleteEqEmbargoNoRecordsFoundException() {
		when(eqEmbargoRepo.existsByEmbargoIdAndUversion(Mockito.any(),Mockito.any())).thenReturn(false);
		assertThrows(NoRecordsFoundException.class,
				() -> when(eqEmbargoService
						.updateEquipmentEmbargo(eqEmbargo, header)));
		assertThrows(NoRecordsFoundException.class,
				() -> when(eqEmbargoService
						.deleteEquipmentEmbargo(eqEmbargo)));
	}
}
