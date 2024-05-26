package com.nscorp.obis.services;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.*;

import com.nscorp.obis.domain.ConventionalEquipmentWidth;
import com.nscorp.obis.dto.ConventionalEquipmentWidthDTO;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.repository.ConventionalEquipmentWidthRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.nscorp.obis.domain.UmlerConventionalCar;
import com.nscorp.obis.dto.UmlerConventionalCarDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.repository.UmlerConventionalCarRepository;

class ConventionalLoadCapabilitiesServiceTest {

	@InjectMocks
	ConventionalLoadCapabilitiesServiceImpl conventionalCarService;

	@Mock
	UmlerConventionalCarRepository conventionalCarRepository;

	@Mock
	ConventionalEquipmentWidthRepository convEqRepo;

	UmlerConventionalCarDTO umlerConvCarDto;
	UmlerConventionalCar umlerConvCar;

	UmlerConventionalCar umlerConvCarNegativeFlow;

	List<UmlerConventionalCarDTO> umlerConvCarDtoList;

	List<UmlerConventionalCar> umlerConvCarList;
	ConventionalEquipmentWidth convEqWidth;
	ConventionalEquipmentWidthDTO convEqWidthDto;
	List<ConventionalEquipmentWidth> convEqWidthList;
	List<ConventionalEquipmentWidthDTO> convEqWidthDtoList;
	Map<String, String> httpHeaders;
	Map<String, String> header;

	String carInit;

	String aarType;

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		convEqWidth = new ConventionalEquipmentWidth();
		convEqWidth.setUmlerId(100L);
		convEqWidth.setAar1stNr("1");
		convEqWidth.setMinEqWidth(100);
		convEqWidth.setMaxEqWidth(200);

		convEqWidthList = new ArrayList<>();
		convEqWidthList.add(convEqWidth);

		convEqWidthDto = new ConventionalEquipmentWidthDTO();
		convEqWidthDto.setUmlerId(100L);
		convEqWidthDto.setAar1stNr("1");
		convEqWidthDto.setMinEqWidth(100);
		convEqWidthDto.setMaxEqWidth(200);

		convEqWidthDtoList = new ArrayList<>();
		convEqWidthDtoList.add(convEqWidthDto);

		umlerConvCarDto = new UmlerConventionalCarDTO();
		umlerConvCarDto.setUmlerId(100L);
		umlerConvCarDto.setSingleMultipleAarInd("S");
		umlerConvCarDto.setAarType("P110");
		umlerConvCarDto.setAarCd("P");
		umlerConvCarDto.setAar1stNoLow("1");
		umlerConvCarDto.setAar1stNoHigh("2");
		umlerConvCarDto.setAar2ndNoLow("1");
		umlerConvCarDto.setAar2ndNoHigh("2");
		umlerConvCarDto.setAar3rdNo("3");
		umlerConvCarDto.setCarInit("Test");
		umlerConvCarDto.setCarLowNr(BigDecimal.valueOf(30));
		umlerConvCarDto.setCarHighNr(BigDecimal.valueOf(35));
		umlerConvCarDto.setCarOwner("Test");
		umlerConvCarDto.setTofcCofcInd("T");
		umlerConvCarDto.setMinEqWidth(100);
		umlerConvCarDto.setMaxEqWidth(200);
		umlerConvCarDto.setConventionalEquipmentWidth(convEqWidthDtoList);
		umlerConvCarDto.setSingleDoubleWellInd("S");
		umlerConvCarDto.setMinEqLength(20);
		umlerConvCarDto.setMaxEqLength(30);
		umlerConvCarDto.setMinTrailerLength(10);
		umlerConvCarDto.setMaxTrailerLength(20);
		umlerConvCarDto.setAggregateLength(100);
		umlerConvCarDto.setTotCofcLength(100);
		umlerConvCarDto.setAccept2c20Ind("Y");
		umlerConvCarDto.setAccept3t28Ind("Y");
		umlerConvCarDto.setAcceptNoseMountedReefer("Y");
		umlerConvCarDto.setReeferAwellInd("Y");
		umlerConvCarDto.setReeferTofcInd("Y");
		umlerConvCarDto.setNoReeferT40Ind("Y");
		umlerConvCarDto.setMaxLoadWeight(100);
		umlerConvCarDto.setC20MaxWeight(100);

		umlerConvCarDtoList = new ArrayList<>();
		umlerConvCarDtoList.add(umlerConvCarDto);

		umlerConvCar = new UmlerConventionalCar();
		umlerConvCar.setUmlerId(100L);
		umlerConvCar.setSingleMultipleAarInd("S");
		umlerConvCar.setAarType("P110");
		umlerConvCar.setAarCd("P");
		umlerConvCar.setAar1stNoLow("1");
		umlerConvCar.setAar1stNoHigh("2");
		umlerConvCar.setAar2ndNoLow("1");
		umlerConvCar.setAar2ndNoHigh("2");
		umlerConvCar.setAar3rdNo("3");
		umlerConvCar.setCarInit("Test");
		umlerConvCar.setCarLowNr(BigDecimal.valueOf(30));
		umlerConvCar.setCarHighNr(BigDecimal.valueOf(35));
		umlerConvCar.setCarOwner("Test");
		umlerConvCar.setTofcCofcInd("T");
		umlerConvCar.setMinEqWidth(100);
		umlerConvCar.setMaxEqWidth(200);
		umlerConvCar.setConventionalEquipmentWidth(convEqWidthList);
		umlerConvCar.setSingleDoubleWellInd("S");
		umlerConvCar.setMinEqLength(40);
		umlerConvCar.setMaxEqLength(45);
		umlerConvCar.setMinTrailerLength(40);
		umlerConvCar.setMaxTrailerLength(45);
		umlerConvCar.setAggregateLength(100);
		umlerConvCar.setTotCofcLength(100);
		umlerConvCar.setAccept2c20Ind("Y");
		umlerConvCar.setAccept3t28Ind("Y");
		umlerConvCar.setAcceptNoseMountedReefer("Y");
		umlerConvCar.setReeferAwellInd("Y");
		umlerConvCar.setReeferTofcInd("Y");
		umlerConvCar.setNoReeferT40Ind("Y");
		umlerConvCar.setMaxLoadWeight(100);
		umlerConvCar.setC20MaxWeight(100);

		umlerConvCarList = new ArrayList<>();
		umlerConvCarList.add(umlerConvCar);

		umlerConvCarNegativeFlow = new UmlerConventionalCar();
		umlerConvCarNegativeFlow.setUmlerId(100L);
		umlerConvCarNegativeFlow.setSingleMultipleAarInd("S");
		umlerConvCarNegativeFlow.setAarType("P110");
		umlerConvCarNegativeFlow.setAarCd("P");
		umlerConvCarNegativeFlow.setAar1stNoLow("1");
		umlerConvCarNegativeFlow.setAar1stNoHigh("2");
		umlerConvCarNegativeFlow.setAar2ndNoLow("1");
		umlerConvCarNegativeFlow.setAar2ndNoHigh("2");
		umlerConvCarNegativeFlow.setAar3rdNo("3");
		umlerConvCarNegativeFlow.setCarInit("Test");
		umlerConvCarNegativeFlow.setCarLowNr(BigDecimal.valueOf(30));
		umlerConvCarNegativeFlow.setCarHighNr(BigDecimal.valueOf(35));
		umlerConvCarNegativeFlow.setCarOwner("Test");
		umlerConvCarNegativeFlow.setTofcCofcInd("T");
		umlerConvCarNegativeFlow.setMinEqWidth(100);
		umlerConvCarNegativeFlow.setMaxEqWidth(200);
		umlerConvCarNegativeFlow.setConventionalEquipmentWidth(convEqWidthList);
		umlerConvCarNegativeFlow.setSingleDoubleWellInd("S");
		umlerConvCarNegativeFlow.setMinEqLength(40);
		umlerConvCarNegativeFlow.setMaxEqLength(45);
		umlerConvCarNegativeFlow.setMinTrailerLength(40);
		umlerConvCarNegativeFlow.setMaxTrailerLength(45);
		umlerConvCarNegativeFlow.setAggregateLength(100);
		umlerConvCarNegativeFlow.setTotCofcLength(100);
		umlerConvCarNegativeFlow.setAccept2c20Ind("Y");
		umlerConvCarNegativeFlow.setAccept3t28Ind("Y");
		umlerConvCarNegativeFlow.setAcceptNoseMountedReefer("Y");
		umlerConvCarNegativeFlow.setReeferAwellInd("Y");
		umlerConvCarNegativeFlow.setReeferTofcInd("Y");
		umlerConvCarNegativeFlow.setNoReeferT40Ind("Y");
		umlerConvCarNegativeFlow.setMaxLoadWeight(100);
		umlerConvCarNegativeFlow.setC20MaxWeight(100);

		httpHeaders = new HashMap<>();
		umlerConvCarDtoList.add(umlerConvCarDto);
		umlerConvCarList.add(umlerConvCar);
		httpHeaders.put("userid", "TEST");

		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");

		when(conventionalCarRepository.saveAndFlush(Mockito.any())).thenReturn(umlerConvCar);
	}

	@AfterEach
	void tearDown() throws Exception {
		umlerConvCarDto = null;
		umlerConvCar = null;
		umlerConvCarDtoList = null;
		umlerConvCarList = null;
		httpHeaders = null;
	}

	@ParameterizedTest
	@ValueSource(strings = { "test", "" })
	void testGetConvLoad() {
		when(conventionalCarRepository.findAllByAarTypeAndCarInit(Mockito.any(), Mockito.any()))
				.thenReturn(umlerConvCarList);
		List<UmlerConventionalCar> getResponse = conventionalCarService.getConvLoad(aarType, carInit);
		assertFalse(getResponse.isEmpty());
	}

	@SuppressWarnings("unchecked")
	@Test
	void testNoRecordFoundException() {
		when(conventionalCarRepository.findAllByAarTypeAndCarInit(Mockito.any(), Mockito.any()))
				.thenReturn(Collections.EMPTY_LIST);
		NoRecordsFoundException getException = assertThrows(NoRecordsFoundException.class,
				() -> when(conventionalCarService.getConvLoad(aarType, carInit)));
		assertEquals("No Record Found under this search!", getException.getMessage());

		when(conventionalCarRepository.existsByUmlerIdAndUversion(Mockito.any(), Mockito.any())).thenReturn(false);
		NoRecordsFoundException deleteException = assertThrows(NoRecordsFoundException.class,
				() -> when(conventionalCarService.deleteConvLoad(umlerConvCar)));
		assertEquals("No record Found to delete Under this Umler Id: " + umlerConvCar.getUmlerId() + " and U_Version: "
				+ umlerConvCar.getUversion(), deleteException.getMessage());
	}

	@Test
	void testAddConventionalCar() {
		when(conventionalCarRepository.existsByAarType(umlerConvCar.getAarType())).thenReturn(false);
		when(conventionalCarRepository.existsByAarCdAndAar1stNoLowAndAar1stNoHighAndAar2ndNoLowAndAar2ndNoHigh(
				Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(false);
		when(conventionalCarRepository.existsByCarInitAndCarLowNrAndCarHighNr(Mockito.any(), Mockito.any(),
				Mockito.any())).thenReturn(false);
		when(conventionalCarRepository.save(Mockito.any())).thenReturn(umlerConvCar);
		UmlerConventionalCar addedUmlerConvCar = conventionalCarService.addConventionalCar(umlerConvCar, header);
		assertNotNull(addedUmlerConvCar);
	}

	@Test
	void testAddAarTypeRecordNotAddedException() {
		umlerConvCar.setAarType("Q");
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> when(conventionalCarService.addConventionalCar(umlerConvCar, header)));
		assertEquals("AAR Type For Conventional Cars should start only with 'P'", exception.getMessage());
	}

	@Test
	void testAddAarTypeAlreadyExistsRecordNotAddedException() {
		when(conventionalCarRepository.existsByAarType(Mockito.any())).thenReturn(true);
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> when(conventionalCarService.addConventionalCar(umlerConvCar, header)));
		assertEquals("AAR Type: " + umlerConvCar.getAarType() + " already exists!", exception.getMessage());
	}

	@Test
	void testAddMultipleAarTypeAlreadyExistsRecordNotAddedException() {
		when(conventionalCarRepository.existsByAarType(Mockito.any())).thenReturn(false);
		when(conventionalCarRepository.existsByAarCdAndAar1stNoLowAndAar1stNoHighAndAar2ndNoLowAndAar2ndNoHigh(
				Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(true);
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> when(conventionalCarService.addConventionalCar(umlerConvCar, header)));
		assertEquals("Multiple AAR Type already exists!", exception.getMessage());
	}

	@Test
	void testAddCarInitLowNrHighNrAlreadyExistsRecordNotAddedException() {
		when(conventionalCarRepository.existsByAarType(Mockito.any())).thenReturn(false);
		when(conventionalCarRepository.existsByAarCdAndAar1stNoLowAndAar1stNoHighAndAar2ndNoLowAndAar2ndNoHigh(
				Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(false);
		when(conventionalCarRepository.existsByCarInitAndCarLowNrAndCarHighNr(Mockito.any(), Mockito.any(),
				Mockito.any())).thenReturn(true);
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> when(conventionalCarService.addConventionalCar(umlerConvCar, header)));
		assertEquals("Car Low Nr: " + umlerConvCar.getCarLowNr() + " and Car High Nr: " + umlerConvCar.getCarHighNr()
				+ " already exists for Car Init: " + umlerConvCar.getCarInit(), exception.getMessage());
	}

	@Test
	void testAddMinEqWidthHigherThanMaxEqWidthRecordNotAddedException() {
		convEqWidth.setMinEqWidth(45);
		convEqWidth.setMaxEqWidth(40);
		when(conventionalCarRepository.existsByAarType(Mockito.any())).thenReturn(false);
		when(conventionalCarRepository.existsByAarCdAndAar1stNoLowAndAar1stNoHighAndAar2ndNoLowAndAar2ndNoHigh(
				Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(false);
		when(conventionalCarRepository.existsByCarInitAndCarLowNrAndCarHighNr(Mockito.any(), Mockito.any(),
				Mockito.any())).thenReturn(false);
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> when(conventionalCarService.addConventionalCar(umlerConvCar, header)));
		assertEquals("Min EQ Width: " + convEqWidth.getMinEqWidth() + " should be less than or equal to Max EQ Width: "
				+ convEqWidth.getMaxEqWidth(), exception.getMessage());
	}

	@Test
	void testUpdateConventionalCar() {
		umlerConvCar.setUversion("!");
		convEqWidth.setUversion("!");
		when(conventionalCarRepository.existsByUmlerId(Mockito.any())).thenReturn(true);
		when(conventionalCarRepository.findByUmlerId(Mockito.any())).thenReturn(umlerConvCar);
		when(convEqRepo.existsByUmlerIdAndAar1stNr(Mockito.any(), Mockito.any())).thenReturn(true);
		when(convEqRepo.findByUmlerIdAndAar1stNr(Mockito.any(), Mockito.any())).thenReturn(convEqWidth);
		when(conventionalCarRepository.save(Mockito.any())).thenReturn(umlerConvCar);
		UmlerConventionalCar updatedUmlerConvCar = conventionalCarService.updateConventionalCar(umlerConvCar, header);
		assertNotNull(updatedUmlerConvCar);
	}

	@Test
	void testUpdateAARTypeRecordNotAddedException() {
		umlerConvCar.setAarType("Q");
		when(conventionalCarRepository.existsByUmlerId(Mockito.any())).thenReturn(true);

		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> when(conventionalCarService.updateConventionalCar(umlerConvCar, header)));
		assertEquals("AAR Type For Conventional Cars should start only with 'P'", exception.getMessage());
	}

	@Test
	void testUpdateAARCodeRecordNotAddedException() {
		umlerConvCar.setAarCd("Q");
		when(conventionalCarRepository.existsByUmlerId(Mockito.any())).thenReturn(true);

		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> when(conventionalCarService.updateConventionalCar(umlerConvCar, header)));
		assertEquals("AAR Cd For Conventional Cars AAR Type should start only with 'P'", exception.getMessage());
	}

	@Test
	void testUpdateCarLowNrHigherThanCarHighNrRecordNotAddedException() {
		umlerConvCar.setCarLowNr(BigDecimal.valueOf(45));
		umlerConvCar.setCarHighNr(BigDecimal.valueOf(40));
		when(conventionalCarRepository.existsByUmlerId(Mockito.any())).thenReturn(true);

		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> when(conventionalCarService.updateConventionalCar(umlerConvCar, header)));
		assertEquals("Car Low Nr: " + umlerConvCar.getCarLowNr() + " should be less than or equal to Car High Nr: "
				+ umlerConvCar.getCarHighNr(), exception.getMessage());
	}

	@Test
	void testUpdateMinEqWidthHigherThanMaxEqWidthRecordNotAddedException() {
		umlerConvCar.setMinEqWidth(45);
		umlerConvCar.setMaxEqWidth(40);
		when(conventionalCarRepository.existsByUmlerId(Mockito.any())).thenReturn(true);

		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> when(conventionalCarService.updateConventionalCar(umlerConvCar, header)));
		assertEquals("Min EQ Width: " + umlerConvCar.getMinEqWidth() + " should be less than or equal to Max EQ Width: "
				+ umlerConvCar.getMaxEqWidth(), exception.getMessage());
	}

	@Test
	void testUpdateMinEqLengthNotValidRecordNotAddedException() {
		umlerConvCar.setMinEqLength(21);
		when(conventionalCarRepository.existsByUmlerId(Mockito.any())).thenReturn(true);

		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> when(conventionalCarService.updateConventionalCar(umlerConvCar, header)));
		assertEquals("'minEqLength' should be 20, 40, 45, 48 & 53", exception.getMessage());
	}

	@Test
	void testUpdateMaxEqLengthNotValidRecordNotAddedException() {
		umlerConvCar.setMaxEqLength(21);
		when(conventionalCarRepository.existsByUmlerId(Mockito.any())).thenReturn(true);

		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> when(conventionalCarService.updateConventionalCar(umlerConvCar, header)));
		assertEquals("'maxEqLength' should be 20, 40, 45, 48 & 53", exception.getMessage());
	}

	@Test
	void testUpdateMinEqLengthHigherThanMaxEqLengthRecordNotAddedException() {
		umlerConvCar.setMinEqLength(48);
		umlerConvCar.setMaxEqLength(45);
		when(conventionalCarRepository.existsByUmlerId(Mockito.any())).thenReturn(true);

		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> when(conventionalCarService.updateConventionalCar(umlerConvCar, header)));
		assertEquals(
				"Min Cont Length: " + umlerConvCar.getMinEqLength()
						+ " should be less than or equal to Max Cont Length: " + umlerConvCar.getMaxEqLength(),
				exception.getMessage());
	}

	@Test
	void testUpdateMinTrailerLengthNotValidRecordNotAddedException() {
		umlerConvCar.setMinTrailerLength(21);
		when(conventionalCarRepository.existsByUmlerId(Mockito.any())).thenReturn(true);

		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> when(conventionalCarService.updateConventionalCar(umlerConvCar, header)));
		assertEquals("'minTrailerLength' should be 28, 40, 45, 48 & 53", exception.getMessage());
	}

	@Test
	void testUpdateMaxTrailerLengthNotValidRecordNotAddedException() {
		umlerConvCar.setMaxTrailerLength(21);
		when(conventionalCarRepository.existsByUmlerId(Mockito.any())).thenReturn(true);

		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> when(conventionalCarService.updateConventionalCar(umlerConvCar, header)));
		assertEquals("'maxTrailerLength' should be 28, 40, 45, 48 & 53", exception.getMessage());
	}

	@Test
	void testUpdateMinTrailerLengthHigherThanMaxTrailerLengthRecordNotAddedException() {
		umlerConvCar.setMinTrailerLength(48);
		umlerConvCar.setMaxTrailerLength(45);
		when(conventionalCarRepository.existsByUmlerId(Mockito.any())).thenReturn(true);

		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> when(conventionalCarService.updateConventionalCar(umlerConvCar, header)));
		assertEquals(
				"Min Trlr Length: " + umlerConvCar.getMinTrailerLength()
						+ " should be less than or equal to Max Trlr Length: " + umlerConvCar.getMaxTrailerLength(),
				exception.getMessage());
	}

	@Test
	void testUpdateSingleWellInd() {
		umlerConvCar.setSingleDoubleWellInd("D");
		when(conventionalCarRepository.existsByUmlerId(Mockito.any())).thenReturn(true);
		when(conventionalCarRepository.findByUmlerId(Mockito.any())).thenReturn(umlerConvCar);
		when(convEqRepo.existsByUmlerIdAndAar1stNr(Mockito.any(), Mockito.any())).thenReturn(true);
		when(convEqRepo.findByUmlerIdAndAar1stNr(Mockito.any(), Mockito.any())).thenReturn(convEqWidth);
		when(conventionalCarRepository.save(Mockito.any())).thenReturn(umlerConvCar);
		UmlerConventionalCar updatedUmlerConvCar = conventionalCarService.updateConventionalCar(umlerConvCar, header);
		assertNotNull(updatedUmlerConvCar);
	}

	@Test
	void testUpdateTofcCofcIndContainer() {
		umlerConvCar.setTofcCofcInd("C");
		when(conventionalCarRepository.existsByUmlerId(Mockito.any())).thenReturn(true);
		when(conventionalCarRepository.findByUmlerId(Mockito.any())).thenReturn(umlerConvCar);
		when(convEqRepo.existsByUmlerIdAndAar1stNr(Mockito.any(), Mockito.any())).thenReturn(true);
		when(convEqRepo.findByUmlerIdAndAar1stNr(Mockito.any(), Mockito.any())).thenReturn(convEqWidth);
		when(conventionalCarRepository.save(Mockito.any())).thenReturn(umlerConvCar);
		UmlerConventionalCar updatedUmlerConvCar = conventionalCarService.updateConventionalCar(umlerConvCar, header);
		assertNotNull(updatedUmlerConvCar);
	}

	@Test
	void testUpdateTofcCofcIndContainerAndDoubleWellInd() {
		umlerConvCar.setSingleDoubleWellInd("D");
		umlerConvCar.setTofcCofcInd("C");
		when(conventionalCarRepository.existsByUmlerId(Mockito.any())).thenReturn(true);
		when(conventionalCarRepository.findByUmlerId(Mockito.any())).thenReturn(umlerConvCar);
		when(convEqRepo.existsByUmlerIdAndAar1stNr(Mockito.any(), Mockito.any())).thenReturn(true);
		when(convEqRepo.findByUmlerIdAndAar1stNr(Mockito.any(), Mockito.any())).thenReturn(convEqWidth);
		when(conventionalCarRepository.save(Mockito.any())).thenReturn(umlerConvCar);
		UmlerConventionalCar updatedUmlerConvCar = conventionalCarService.updateConventionalCar(umlerConvCar, header);
		assertNotNull(updatedUmlerConvCar);
	}

	@Test
	void testUpdateTofcCofcIndBoth() {
		umlerConvCar.setTofcCofcInd("B");
		when(conventionalCarRepository.existsByUmlerId(Mockito.any())).thenReturn(true);
		when(conventionalCarRepository.findByUmlerId(Mockito.any())).thenReturn(umlerConvCar);
		when(convEqRepo.existsByUmlerIdAndAar1stNr(Mockito.any(), Mockito.any())).thenReturn(true);
		when(convEqRepo.findByUmlerIdAndAar1stNr(Mockito.any(), Mockito.any())).thenReturn(convEqWidth);
		when(conventionalCarRepository.save(Mockito.any())).thenReturn(umlerConvCar);
		UmlerConventionalCar updatedUmlerConvCar = conventionalCarService.updateConventionalCar(umlerConvCar, header);
		assertNotNull(updatedUmlerConvCar);
	}

	@Test
	void testUpdateTofcCofcIndNull() {
		umlerConvCar.setTofcCofcInd(null);
		when(conventionalCarRepository.existsByUmlerId(Mockito.any())).thenReturn(true);
		when(conventionalCarRepository.findByUmlerId(Mockito.any())).thenReturn(umlerConvCar);
		when(convEqRepo.existsByUmlerIdAndAar1stNr(Mockito.any(), Mockito.any())).thenReturn(true);
		when(convEqRepo.findByUmlerIdAndAar1stNr(Mockito.any(), Mockito.any())).thenReturn(convEqWidth);
		when(conventionalCarRepository.save(Mockito.any())).thenReturn(umlerConvCar);
		UmlerConventionalCar updatedUmlerConvCar = conventionalCarService.updateConventionalCar(umlerConvCar, header);
		assertNotNull(updatedUmlerConvCar);
	}

	@Test
	void testUpdateAcceptNoseMountedReeferNo() {
		umlerConvCar.setAcceptNoseMountedReefer("N");
		when(conventionalCarRepository.existsByUmlerId(Mockito.any())).thenReturn(true);
		when(conventionalCarRepository.findByUmlerId(Mockito.any())).thenReturn(umlerConvCar);
		when(convEqRepo.existsByUmlerIdAndAar1stNr(Mockito.any(), Mockito.any())).thenReturn(true);
		when(convEqRepo.findByUmlerIdAndAar1stNr(Mockito.any(), Mockito.any())).thenReturn(convEqWidth);
		when(conventionalCarRepository.save(Mockito.any())).thenReturn(umlerConvCar);
		UmlerConventionalCar updatedUmlerConvCar = conventionalCarService.updateConventionalCar(umlerConvCar, header);
		assertNotNull(updatedUmlerConvCar);
	}

//	@Test
//	void testUpdateExtensionSchemaNullPointerException() {
//		when(conventionalCarRepository.existsByUmlerId(umlerConvCar.getUmlerId())).thenReturn(true);
//		header.put("extensionschema", " ");
//		NullPointerException exception = assertThrows(NullPointerException.class,
//				() -> when(conventionalCarService.updateConventionalCar(umlerConvCar, header)));
//		assertEquals("Extension Schema should not be null, empty or blank", exception.getMessage());
//	}

	@Test
	void testUpdateConvEqWidthRecordNotAddedException() {
		umlerConvCar.setUversion("!");
		convEqWidth.setUversion("!");
		convEqWidth.setMinEqWidth(45);
		convEqWidth.setMaxEqWidth(40);
		when(conventionalCarRepository.existsByUmlerId(Mockito.any())).thenReturn(true);
		when(conventionalCarRepository.findByUmlerId(Mockito.any())).thenReturn(umlerConvCar);
		when(convEqRepo.existsByUmlerIdAndAar1stNr(Mockito.any(), Mockito.any())).thenReturn(true);
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> when(conventionalCarService.updateConventionalCar(umlerConvCar, header)));
		assertEquals("Min EQ Width: " + convEqWidth.getMinEqWidth() + " should be less than or equal to Max EQ Width: "
				+ convEqWidth.getMaxEqWidth(), exception.getMessage());
	}

	/*
	 * @Test void testUpdateConvEqWidthNoRecordsFoundException() {
	 * when(conventionalCarRepository.existsByUmlerId(Mockito.any())).thenReturn(
	 * true);
	 * when(conventionalCarRepository.findByUmlerId(Mockito.any())).thenReturn(
	 * umlerConvCar); when(convEqRepo.existsByUmlerIdAndAar1stNr(Mockito.any(),
	 * Mockito.any())).thenReturn(false); NoRecordsFoundException exception =
	 * assertThrows(NoRecordsFoundException.class, () ->
	 * when(conventionalCarService.updateConventionalCar(umlerConvCar, header)));
	 * assertEquals("Record with Umler Id: "+
	 * convEqWidth.getUmlerId()+" and AAR 1st NR: "+convEqWidth.getAar1stNr()
	 * +" not found!", exception.getMessage()); }
	 */

	@Test
	void testUpdateConvCarNoRecordsFoundException() {
		when(conventionalCarRepository.existsByUmlerId(Mockito.any())).thenReturn(false);
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(conventionalCarService.updateConventionalCar(umlerConvCar, header)));
		assertEquals("Record with Umler Id: " + umlerConvCar.getUmlerId() + " not found!", exception.getMessage());
	}

	@Test
	void testUpdateConvCarRecordNotAdded() {
		umlerConvCar.setUversion("!");
		convEqWidth.setUversion("!");
		when(conventionalCarRepository.existsByUmlerId(Mockito.any())).thenReturn(true);
		umlerConvCarNegativeFlow.setAarType("PTST");
		when(conventionalCarRepository.findByUmlerId(Mockito.any())).thenReturn(umlerConvCarNegativeFlow);
		when(convEqRepo.existsByUmlerIdAndAar1stNr(Mockito.any(), Mockito.any())).thenReturn(true);
		when(convEqRepo.findByUmlerIdAndAar1stNr(Mockito.any(), Mockito.any())).thenReturn(convEqWidth);
		when(conventionalCarRepository.save(Mockito.any())).thenReturn(umlerConvCar);
		when(conventionalCarRepository.existsByAarType(Mockito.any())).thenReturn(true);
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> when(conventionalCarService.updateConventionalCar(umlerConvCar, header)));
		assertEquals("AAR Type: " + umlerConvCar.getAarType() + " already exists!", exception.getMessage());

	}

	@Test
	void testDeleteConvLoad() {
		when(conventionalCarRepository.existsByUmlerIdAndUversion(Mockito.any(), Mockito.any())).thenReturn(true);
		when(conventionalCarRepository.findById(Mockito.any())).thenReturn(Optional.ofNullable(umlerConvCar));
		when(convEqRepo.existsByUmlerId(Mockito.any())).thenReturn(true);
		UmlerConventionalCar deleteResponse = conventionalCarService.deleteConvLoad(umlerConvCar);
		assertEquals(deleteResponse, umlerConvCar);
	}

	@Test
	void testUpdateConvCarRecordNotAddedMultipleAAR() {
		umlerConvCar.setUversion("!");
		convEqWidth.setUversion("!");
		umlerConvCarNegativeFlow.setAarCd("TEST");
		umlerConvCarNegativeFlow.setAar1stNoLow("6");
		umlerConvCarNegativeFlow.setAar1stNoHigh("7");
		umlerConvCarNegativeFlow.setAar2ndNoLow("8");
		umlerConvCarNegativeFlow.setAar2ndNoHigh("9");
		when(conventionalCarRepository.existsByUmlerId(Mockito.any())).thenReturn(true);
		when(conventionalCarRepository.findByUmlerId(Mockito.any())).thenReturn(umlerConvCarNegativeFlow);
		when(convEqRepo.existsByUmlerIdAndAar1stNr(Mockito.any(), Mockito.any())).thenReturn(true);
		when(convEqRepo.findByUmlerIdAndAar1stNr(Mockito.any(), Mockito.any())).thenReturn(convEqWidth);
		when(conventionalCarRepository.save(Mockito.any())).thenReturn(umlerConvCar);
		when(conventionalCarRepository.existsByAarType(Mockito.any())).thenReturn(false);
		when(conventionalCarRepository.existsByAarCdAndAar1stNoLowAndAar1stNoHighAndAar2ndNoLowAndAar2ndNoHigh(
				Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(true);

		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> when(conventionalCarService.updateConventionalCar(umlerConvCar, header)));
		assertEquals("Multiple AAR Type already exists!", exception.getMessage());

	}

	@Test
	void testUpdateConvCarRecordNotAddedCarNr() {
		umlerConvCar.setUversion("!");
		convEqWidth.setUversion("!");
		umlerConvCarNegativeFlow.setUmlerId(11111L);
		umlerConvCarList.add(umlerConvCarNegativeFlow);

		when(conventionalCarRepository.existsByUmlerId(Mockito.any())).thenReturn(true);
		when(conventionalCarRepository.findByUmlerId(Mockito.any())).thenReturn(umlerConvCarNegativeFlow);
		when(convEqRepo.existsByUmlerIdAndAar1stNr(Mockito.any(), Mockito.any())).thenReturn(true);
		when(convEqRepo.findByUmlerIdAndAar1stNr(Mockito.any(), Mockito.any())).thenReturn(convEqWidth);
		when(conventionalCarRepository.saveAndFlush(Mockito.any())).thenReturn(umlerConvCar);
		when(conventionalCarRepository.existsByAarType(Mockito.any())).thenReturn(false);
		when(conventionalCarRepository.existsByAarCdAndAar1stNoLowAndAar1stNoHighAndAar2ndNoLowAndAar2ndNoHigh(
				Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(false);

		when(conventionalCarRepository.existsByCarInitAndCarLowNrAndCarHighNr(Mockito.any(), Mockito.any(),
				Mockito.any())).thenReturn(true);
		when(conventionalCarRepository.findByCarInitAndCarLowNrAndCarHighNr(Mockito.any(), Mockito.any(),
				Mockito.any())).thenReturn(umlerConvCarList);

		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> when(conventionalCarService.updateConventionalCar(umlerConvCar, header)));
		assertEquals("Car Low Nr: 30 and Car High Nr: 35 already exists for Car Init: TEST", exception.getMessage());

	}

	@Test
	void testUpdateConventionalCarInsertNewAAR() {
		umlerConvCar.setUversion("!");
		convEqWidth.setUversion("!");
		when(conventionalCarRepository.existsByUmlerId(Mockito.any())).thenReturn(true);
		when(conventionalCarRepository.findByUmlerId(Mockito.any())).thenReturn(umlerConvCar);
		when(convEqRepo.existsByUmlerIdAndAar1stNr(Mockito.any(), Mockito.any())).thenReturn(false);
		when(convEqRepo.findByUmlerIdAndAar1stNr(Mockito.any(), Mockito.any())).thenReturn(convEqWidth);
		when(conventionalCarRepository.saveAndFlush(Mockito.any())).thenReturn(umlerConvCar);
		UmlerConventionalCar updatedUmlerConvCar = conventionalCarService.updateConventionalCar(umlerConvCar, header);
		assertNotNull(updatedUmlerConvCar);
	}
}
