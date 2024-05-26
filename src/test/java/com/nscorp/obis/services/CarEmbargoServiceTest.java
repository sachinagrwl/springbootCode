package com.nscorp.obis.services;

import com.nscorp.obis.domain.CarEmbargo;
import com.nscorp.obis.domain.Station;
import com.nscorp.obis.dto.CarEmbargoDTO;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.RecordNotDeletedException;
import com.nscorp.obis.repository.CarEmbargoRepository;
import com.nscorp.obis.repository.StationRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CarEmbargoServiceTest {

    @InjectMocks
    CarEmbargoServiceImpl carEmbargoService;

    @Mock
    CarEmbargoRepository carEmbargoRepository;

    @Mock
    StationRepository stationRepository;

    CarEmbargo carEmbargo;
    CarEmbargoDTO carEmbargoDTO;
    List<CarEmbargo> carEmbargoList;
    List<CarEmbargoDTO> carEmbargoDTOList;
    Station station;
    CarEmbargo addedCarEmbargo;
    Map<String,String> header;

    @SuppressWarnings("deprecation")
    @BeforeEach
    void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);
        carEmbargo = new CarEmbargo();
        carEmbargo.setEmbargoId(Long.valueOf(123));
        carEmbargo.setAarType("ABC");
        carEmbargo.setRoadName("Road");
        carEmbargo.setRestriction("N");
        carEmbargo.setDescription("Description");
        carEmbargo.setTerminal(station);

        carEmbargoList = new ArrayList<>();

        carEmbargoDTO = new CarEmbargoDTO();
        carEmbargoDTO.setEmbargoId(Long.valueOf(123));
        carEmbargoDTO.setAarType("ABC");
        carEmbargoDTO.setRoadName("Road");
        carEmbargoDTO.setRestriction("N");
        carEmbargoDTO.setDescription("Description");
        carEmbargoDTO.setTerminal(station);

        carEmbargoDTOList = new ArrayList<>();

        carEmbargoList.add(carEmbargo);
        carEmbargoDTOList.add(carEmbargoDTO);
        
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
    }

    @AfterEach
    void tearDown() throws Exception{
        carEmbargoDTOList = null;
        carEmbargoList = null;
        carEmbargo = null;
        carEmbargoDTO = null;
    }

    @Test
    void getAllCarEmbargo() {
        when(carEmbargoRepository.findAllByOrderByAarType()).thenReturn(carEmbargoList);
        List<CarEmbargo> carEmbargos = carEmbargoService.getAllCarEmbargo();
        assertEquals(carEmbargos,carEmbargoList);
    }

    @Test
    void testGetAllCarEmbargoException() {
        NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
                () -> when(carEmbargoService.getAllCarEmbargo()));
        Assertions.assertEquals("No CarEmbargo Found!", exception.getMessage());
    }

    @Test
    void insertCarEmbargo() {
        when(stationRepository.existsByTermId(Long.valueOf(123))).thenReturn(true);
        when(stationRepository.findById(Mockito.any())).thenReturn(Optional.of(station));
        when(carEmbargoRepository.save(Mockito.any())).thenReturn(carEmbargo);
        addedCarEmbargo = carEmbargoService.insertCarEmbargo(carEmbargo,header);
        assertNotNull(addedCarEmbargo);
        
        Station terminal = new Station();
	    terminal.setTermId(123000L);
	    terminal.setFSAC("FSAC");
	    terminal.setState("AZ");
	    terminal.setRoadName("NS");
		terminal.setBillAtFsac("FSACBill");
		terminal.setIntermodalIndicator("Y");
		terminal.setRoadNumber("ABCD");
		terminal.setBottomPick("Y");
		terminal.setChar5Alias("ABCSA");
		terminal.setChar8Spell("ASASASAS");
		terminal.setOperationStation("ATLANTA");
		carEmbargo.setTerminal(terminal);


		when(stationRepository.existsByTermIdAndExpiredDateIsNull(Mockito.any())).thenReturn(false);
		NoRecordsFoundException exception15 = assertThrows(NoRecordsFoundException.class,
				() -> when(carEmbargoService.insertCarEmbargo(carEmbargo, header)));
		assertEquals("Terminal with Id 11668022820698 not found!", exception15.getMessage());
    }

    @Test
    void insertCarEmbargoStationException() {
        when(stationRepository.existsByTermId(Long.valueOf(123))).thenReturn(false);
        when(stationRepository.findById(Mockito.any())).thenReturn(Optional.of(station));
        when(carEmbargoRepository.save(Mockito.any())).thenReturn(carEmbargo);
        addedCarEmbargo = carEmbargoService.insertCarEmbargo(carEmbargo,header);
        assertNotNull(addedCarEmbargo);
    }

    @Test
    void insertCarEmbargoInvalidInit() {
        carEmbargo.setEquipmentInit("INIT");
        InvalidDataException exception = assertThrows(InvalidDataException.class,
                () -> when(carEmbargoService.insertCarEmbargo(carEmbargo,header)));
        assertEquals("Please remove AarType or Equipment Init!",exception.getMessage());
    }

    @Test
    void insertCarEmbargoInvalidAarType() {
        carEmbargo.setAarType(null);
        InvalidDataException exception = assertThrows(InvalidDataException.class,
                () -> when(carEmbargoService.insertCarEmbargo(carEmbargo,header)));
        assertEquals("Please Enter AarType or Equipment Init!",exception.getMessage());
    }

    @Test
    void insertCarEmbargoInvalidNrValidation() {
    	carEmbargo.setEquipmentNumberHigh(null);
    	carEmbargo.setEquipmentNumberLow(BigDecimal.valueOf(400));
    	RecordNotAddedException exception1= assertThrows(RecordNotAddedException.class,
                () -> when(carEmbargoService
                        .insertCarEmbargo(carEmbargo, header)));
        assertEquals("'EQUIP HIGH NR' value should be provided", exception1.getMessage());
        
        carEmbargo.setEquipmentNumberHigh(BigDecimal.valueOf(400));
    	carEmbargo.setEquipmentNumberLow(null);
    	RecordNotAddedException exception2= assertThrows(RecordNotAddedException.class,
                () -> when(carEmbargoService
                        .insertCarEmbargo(carEmbargo, header)));
        assertEquals("'EQUIP LOW NR' value should be provided", exception2.getMessage());
    }
    
    @Test
    void insertCarEmbargoInvalidLowNr() {
        carEmbargo.setEquipmentNumberLow(BigDecimal.valueOf(100));
        carEmbargo.setEquipmentNumberHigh(BigDecimal.valueOf(200));
        InvalidDataException exception = assertThrows(InvalidDataException.class,
                () -> when(carEmbargoService.insertCarEmbargo(carEmbargo,header)));
        assertEquals("'EQUIP INIT' value should be provided for Nr values",exception.getMessage());
    }

    @Test
    void insertCarEmbargoInvalidLowNrHighNr() {
        carEmbargo.setAarType(null);
        carEmbargo.setEquipmentInit("ABC");
        carEmbargo.setEquipmentNumberLow(BigDecimal.valueOf(400));
        carEmbargo.setEquipmentNumberHigh(BigDecimal.valueOf(200));

        RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
                () -> when(carEmbargoService.insertCarEmbargo(carEmbargo,header)));
        assertEquals("'EQUIP HIGH NR' should be greater than the 'EQUIP LOW NR'",exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"!",""," "})
    void testUpdateRoad(String uVersion) {
        carEmbargo.setUversion(uVersion);
        when(carEmbargoRepository.existsByEmbargoIdAndUversion(Mockito.any(), Mockito.any())).thenReturn(true);
        when(stationRepository.findById(Mockito.any())).thenReturn(Optional.of(station));
        when(carEmbargoRepository.findByEmbargoId(Mockito.any())).thenReturn(carEmbargo);
        when(carEmbargoRepository.save(Mockito.any())).thenReturn(carEmbargo);
        CarEmbargo updateCarEmbargo = carEmbargoService.updateCarEmbargo(carEmbargo, header);
        assertNotNull(updateCarEmbargo);
    }

    @Test
    void testCarEmbargoNoRecordsFoundException() {

        when(carEmbargoRepository.existsByEmbargoIdAndUversion(Mockito.any(), Mockito.any())).thenReturn(false);
        when(stationRepository.findById(Mockito.any())).thenReturn(Optional.of(station));
        NoRecordsFoundException exception1 = assertThrows(NoRecordsFoundException.class,
                () -> when(carEmbargoService.updateCarEmbargo(carEmbargo,header)));
        assertEquals("No record Found Under this Embargo Id:"+carEmbargo.getEmbargoId()
                + " and Uversion:" + carEmbargo.getUversion(), exception1.getMessage());
    }

    @Test
    void testCarEmbargoNullPointerException() {
        header.put("extensionschema", null);
        when(carEmbargoRepository.existsByEmbargoIdAndUversion(Mockito.any(), Mockito.any())).thenReturn(true);
        when(stationRepository.findById(Mockito.any())).thenReturn(Optional.of(station));
        NullPointerException exception1 = assertThrows(NullPointerException.class,
                () -> when(carEmbargoService.updateCarEmbargo(carEmbargo, header)));
        assertEquals("Extension Schema should not be null, empty or blank.", exception1.getMessage());
    }
    @Test
    void testDeleteCarEmbargo() {
        when(carEmbargoRepository.existsByEmbargoIdAndUversion(Mockito.any(), Mockito.any())).thenReturn(true);
        when(carEmbargoRepository.findByEmbargoId(Mockito.any())).thenReturn(carEmbargo);
        CarEmbargo deleteCarEmbargo = carEmbargoService.deleteCarEmbargo(carEmbargo);
        assertNotNull(deleteCarEmbargo);
    }

    @Test
    void testDeleteCarEmbargoRecordNotDeleted() {
        when(carEmbargoRepository.existsByEmbargoIdAndUversion(Mockito.any(), Mockito.any())).thenReturn(false);
        assertThrows(RecordNotDeletedException.class,
                () -> carEmbargoService.deleteCarEmbargo(carEmbargo));
    }
}