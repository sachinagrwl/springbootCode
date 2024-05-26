package com.nscorp.obis.services;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
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
import com.nscorp.obis.domain.StackEquipmentWidth;
import com.nscorp.obis.domain.StackWellLength;
import com.nscorp.obis.domain.UmlerStackCar;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.repository.StackEquipmentWidthRepository;
import com.nscorp.obis.repository.StackWellLengthRepository;
import com.nscorp.obis.repository.UmlerStackCarRepository;

class UmlerStackCarServiceTest {

	@InjectMocks
	UmlerStackCarServiceImpl umlerStackCarServiceImpl;

	@Mock
	UmlerStackCarRepository umlerStackCarRepository;

	@Mock
	StackEquipmentWidthRepository stackEquipmentWidthRepository;

	@Mock
	StackWellLengthRepository stackWellLengthRepository;

	UmlerStackCar umlerStackCar;

	List<UmlerStackCar> umlerStackCarsList;
	StackWellLength stackWellLength;
	List<StackWellLength> stackWellLengthList;
	StackEquipmentWidth stackEqWidth;
	List<StackEquipmentWidth> stackEqWidthList;

	Map<String, String> httpHeaders;

	String carInit;

	String aarType;

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		umlerStackCar = new UmlerStackCar();
		stackWellLength = new StackWellLength();
		stackWellLengthList = new ArrayList<>();
		stackEqWidth = new StackEquipmentWidth();
		stackEqWidthList = new ArrayList<>();
		umlerStackCarsList = new ArrayList<>();
		
		stackWellLength.setUmlerId(100L);
		stackWellLength.setAar1stNr("TEST");
		stackWellLength.setEndWellLength(25);
		stackWellLength.setMedWellLength(35);
		
		stackWellLengthList.add(stackWellLength);
		
		stackEqWidth.setUmlerId(100L);
		stackEqWidth.setMaximumEquipmentLength(20);
		stackEqWidth.setMaximumEquipmentWidth(20);
		stackEqWidth.setMinimumEquipmentLength(10);
		stackEqWidth.setMinimumEquipmentWidth(10);
		
		stackEqWidthList.add(stackEqWidth);

		umlerStackCar.setAar1stNoHigh("20");
		umlerStackCar.setAar1stNoLow("10");
		umlerStackCar.setAar2ndNoHigh("20");
		umlerStackCar.setAar2ndNoLow("10");
		umlerStackCar.setAar3rdNo("20");
		umlerStackCar.setAarCd("S");
		umlerStackCar.setAarType("S330");
		umlerStackCar.setAcceptChasBumper("S");
		umlerStackCar.setAcceptCntPairsOnTop(null);
		umlerStackCar.setAcceptNoseMountedReefer("Y");
		umlerStackCar.setAcceptTrlrPairsInd("N");
		umlerStackCar.setC20MaxWeight(12345);
		umlerStackCar.setCarHighNr(BigDecimal.valueOf(35));
		umlerStackCar.setCarInit("TEST");
		umlerStackCar.setCarLowNr(BigDecimal.valueOf(30));
		umlerStackCar.setCarOwner("TEST");
		umlerStackCar.setCondMaxEqLength(12);
		umlerStackCar.setEndWellLength(20);
		umlerStackCar.setEqPairsMaxLength(null);
		umlerStackCar.setEqPairsMinLength(null);
		umlerStackCar.setEqPairsWellInd(null);
		umlerStackCar.setLengthDeterminedWidthRestrictionsInd("Y");
		umlerStackCar.setMaxEqLength(20);
		umlerStackCar.setMaxEqWidth(20);
		umlerStackCar.setMaxLoadWeight(34567);
		umlerStackCar.setMaxTrlrLength(53);
		umlerStackCar.setMed2MaxEqLength(20);
		umlerStackCar.setMed2MinEqLength(10);
		umlerStackCar.setMed2TopMaxEqLength(20);
		umlerStackCar.setMed2TopMinEqLength(10);
		umlerStackCar.setMedMaxEqLength(20);
		umlerStackCar.setMedMinEqLength(10);
		umlerStackCar.setMedTopMaxEqLength(20);
		umlerStackCar.setMedTopMinEqLength(10);
		umlerStackCar.setMedWellLength(10);
		umlerStackCar.setMinEqLength(20);
		umlerStackCar.setMinEqWidth(10);
		umlerStackCar.setMinTrlrLength(45);
		umlerStackCar.setNoMedLengthOnTopInd("Y");
		umlerStackCar.setNumberOfPlatform("TEST");
		umlerStackCar.setSingleMultipleAarInd("S");
		umlerStackCar.setStackEquipmentWidthList(stackEqWidthList);
		umlerStackCar.setStackWellLengthList(stackWellLengthList);
		umlerStackCar.setTofcCofcInd("T");
		umlerStackCar.setTopCntPairsMaxLength(null);
		umlerStackCar.setTopCntPairsMinLength(null);
		umlerStackCar.setTopMaxEqLength(20);
		umlerStackCar.setTopMinEqLength(10);
		umlerStackCar.setTrlrPairsMaxLength(20);
		umlerStackCar.setTrlrPairsMinLength(10);
		umlerStackCar.setUmlerId(100L);
		
		umlerStackCarsList.add(umlerStackCar);
		
		httpHeaders = new HashMap<>();
		umlerStackCarsList.add(umlerStackCar);
		httpHeaders.put("userid", "TEST");
		httpHeaders.put("extensionschema", "Test");

	}

	@AfterEach
	void tearDown() throws Exception {
		umlerStackCar = null;
		umlerStackCarsList = null;
		httpHeaders = null;
	}

	@ParameterizedTest
	@ValueSource(strings = { "test", "" })
	void testGetAllGuaranteeCustCrossRef(String reqParams) {
		when(umlerStackCarRepository.findAllByAarTypeAndCarInit(Mockito.any(), Mockito.any()))
				.thenReturn(umlerStackCarsList);
		List<UmlerStackCar> getResponse = umlerStackCarServiceImpl.getUmlerStackCars(reqParams, reqParams);
		assertFalse(getResponse.isEmpty());
	}

	@SuppressWarnings("unchecked")
	@Test
	void testNoRecordFoundException() {
		when(umlerStackCarRepository.findAllByAarTypeAndCarInit(Mockito.any(), Mockito.any()))
				.thenReturn(Collections.EMPTY_LIST);
		NoRecordsFoundException getException = assertThrows(NoRecordsFoundException.class,
				() -> when(umlerStackCarServiceImpl.getUmlerStackCars(aarType, carInit)));
		assertEquals("No Record Found under this search!", getException.getMessage());

		when(umlerStackCarRepository.existsByUmlerIdAndUversion(Mockito.any(), Mockito.any())).thenReturn(false);
		NoRecordsFoundException deleteException = assertThrows(NoRecordsFoundException.class,
				() -> when(umlerStackCarServiceImpl.deleteUmlerStackCar(umlerStackCar)));
		assertEquals("No record Found to delete Under this Umler Id: 100 and U_Version: null",
				deleteException.getMessage());

	}

	@Test
	void testAddStackCar() {
		when(umlerStackCarRepository.existsByAarType(Mockito.any())).thenReturn(false);
		when(umlerStackCarRepository.existsByAarCdAndAar1stNoLowAndAar1stNoHighAndAar2ndNoLowAndAar2ndNoHigh(
				Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(false);
		when(umlerStackCarRepository.existsByCarInitAndCarLowNrAndCarHighNr(Mockito.any(), Mockito.any(),
				Mockito.any())).thenReturn(false);
		when(stackEquipmentWidthRepository.findByUmlerId(Mockito.any())).thenReturn(stackEqWidthList);
		when(stackWellLengthRepository.save(Mockito.any())).thenReturn(stackWellLength);
		when(stackEquipmentWidthRepository.save(Mockito.any())).thenReturn(stackEqWidth);
		when(umlerStackCarRepository.save(Mockito.any())).thenReturn(umlerStackCar);
		UmlerStackCar addStackCar = umlerStackCarServiceImpl.insertStackCar(umlerStackCar, httpHeaders);
		assertNotNull(addStackCar);
	}

	@Test
	void testAddExtensionStackCar() {
		httpHeaders.put("extensionschema", null);
		assertNull(httpHeaders.get(CommonConstants.EXTENSION_SCHEMA));
		NullPointerException addNullException = assertThrows(NullPointerException.class,
				() -> when(umlerStackCarServiceImpl.insertStackCar(umlerStackCar, httpHeaders)));
		assertEquals("Extension Schema should not be null, empty or blank", addNullException.getMessage());
	}
	
	@Test
	void testAddMinEqLengthNotValidRecordNotAddedException() {
		umlerStackCar.setMinEqLength(12);
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> when(umlerStackCarServiceImpl.insertStackCar(umlerStackCar, httpHeaders)));
		assertEquals("'minEqLength' should be 20, 40, 45, 48 & 53", exception.getMessage());
	}
	
	@Test
	void testAddMaxEqLengthNotValidRecordNotAddedException() {
		umlerStackCar.setMaxEqLength(12);
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> when(umlerStackCarServiceImpl.insertStackCar(umlerStackCar, httpHeaders)));
		assertEquals("'maxEqLength' should be 20, 40, 45, 48 & 53", exception.getMessage());
	}
	
	@Test
	void testAddEqLengthRecordNotAddedException(){
		umlerStackCar.setMinEqLength(40);;
		umlerStackCar.setMaxEqLength(20);
		
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> when(umlerStackCarServiceImpl.insertStackCar(umlerStackCar, httpHeaders)));
		assertEquals("Min Eq Length: 40 should be less than or equal to Max Eq Length: 20", exception.getMessage());
	}
	
	@Test
	void testAddMinTrlrLengthNotValidRecordNotAddedException() {
		umlerStackCar.setMinTrlrLength(12);
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> when(umlerStackCarServiceImpl.insertStackCar(umlerStackCar, httpHeaders)));
		assertEquals("'minTrlrLength' should be 28, 40, 45, 48 & 53", exception.getMessage());
	}
	
	@Test
	void testAddMaxTrlrLengthNotValidRecordNotAddedException() {
		umlerStackCar.setMaxTrlrLength(12);
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> when(umlerStackCarServiceImpl.insertStackCar(umlerStackCar, httpHeaders)));
		assertEquals("'maxTrlrLength' should be 28, 40, 45, 48 & 53", exception.getMessage());
	}
	
	@Test
	void testAddTrlrLengthRecordNotAddedException(){
		umlerStackCar.setMinTrlrLength(40);;
		umlerStackCar.setMaxTrlrLength(28);
		
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> when(umlerStackCarServiceImpl.insertStackCar(umlerStackCar, httpHeaders)));
		assertEquals("Min Trlr Length: 40 should be less than or equal to Max Trlr Length: 28", exception.getMessage());
	}
	
	@Test
	void testAddMultipleAARTypeAlreadyExistsException(){
		when(umlerStackCarRepository.existsByAarCdAndAar1stNoLowAndAar1stNoHighAndAar2ndNoLowAndAar2ndNoHigh(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(true);
		
		RecordAlreadyExistsException exception = assertThrows(RecordAlreadyExistsException.class,
				() -> when(umlerStackCarServiceImpl.insertStackCar(umlerStackCar, httpHeaders)));
		assertEquals("Multiple AAR Type already exists!", exception.getMessage());
	}
	
	@Test
	void testAddAarTypeRecordNotAddedException(){
		umlerStackCar.setAarType("Q");
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> when(umlerStackCarServiceImpl.insertStackCar(umlerStackCar, httpHeaders)));
		assertEquals("AAR Type For Stack Cars should start only with 'S'", exception.getMessage());
	}
	
	@Test
	void testAddAarCdRecordNotAddedException(){
		umlerStackCar.setAarCd("Q");
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> when(umlerStackCarServiceImpl.insertStackCar(umlerStackCar, httpHeaders)));
		assertEquals("AAR Cd For Stack Cars AAR Type should start only with 'S'", exception.getMessage());
	}
	
	@Test
	void testAddAarTypeAlreadyExistsException(){
		when(umlerStackCarRepository.existsByAarType(Mockito.any())).thenReturn(true);
		RecordAlreadyExistsException exception = assertThrows(RecordAlreadyExistsException.class,
				() -> when(umlerStackCarServiceImpl.insertStackCar(umlerStackCar, httpHeaders)));
		assertEquals("AAR Type: "+umlerStackCar.getAarType()+" already exists!", exception.getMessage());
	}
	
	@Test
	void testAddCrLowNrHighNrRecordNotAddedException(){
		umlerStackCar.setCarLowNr(BigDecimal.valueOf(35));
		umlerStackCar.setCarHighNr(BigDecimal.valueOf(30));
		
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> when(umlerStackCarServiceImpl.insertStackCar(umlerStackCar, httpHeaders)));
		assertEquals("Car Low Nr: 35 should be less than or equal to Car High Nr: 30", exception.getMessage());
	}
	
	@Test
	void testAddCarInitAlreadyExistsException(){
		when(umlerStackCarRepository.existsByCarInitAndCarLowNrAndCarHighNr(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(true);
		
		RecordAlreadyExistsException exception = assertThrows(RecordAlreadyExistsException.class,
				() -> when(umlerStackCarServiceImpl.insertStackCar(umlerStackCar, httpHeaders)));
		assertEquals("Car Low Nr: 30 and Car High Nr: 35 already exists for Car Init: TEST", exception.getMessage());
	}
	
	@Test
	void testAddEqWidthRecordNotAddedException(){
		umlerStackCar.setMinEqWidth(30);;
		umlerStackCar.setMaxEqLength(10);
		
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> when(umlerStackCarServiceImpl.insertStackCar(umlerStackCar, httpHeaders)));
		assertEquals("Min EQ Width: 30 should be less than or equal to Max EQ Width: 20", exception.getMessage());
	}
	

	@Test
	void testDeleteStackCar() {
		when(umlerStackCarRepository.existsByUmlerIdAndUversion(Mockito.any(), Mockito.any())).thenReturn(true);
		when(umlerStackCarRepository.findById(Mockito.any())).thenReturn(Optional.ofNullable(umlerStackCar));
		when(stackEquipmentWidthRepository.existsByUmlerId(Mockito.any())).thenReturn(true);
		when(stackWellLengthRepository.existsByUmlerId(Mockito.any())).thenReturn(true);
		UmlerStackCar deleteResponse = umlerStackCarServiceImpl.deleteUmlerStackCar(umlerStackCar);
		assertEquals(deleteResponse, umlerStackCar);

		when(stackEquipmentWidthRepository.existsByUmlerId(Mockito.any())).thenReturn(false);
		when(stackWellLengthRepository.existsByUmlerId(Mockito.any())).thenReturn(false);
		UmlerStackCar deleteResponse2 = umlerStackCarServiceImpl.deleteUmlerStackCar(umlerStackCar);
		assertEquals(deleteResponse2, umlerStackCar);
	}

	@Test
	void testUpdateStackCar() {
		umlerStackCar.setUversion("!");
		stackWellLength.setUversion("!");
		umlerStackCar.setStackWellLengthList(stackWellLengthList);
		when(umlerStackCarRepository.existsByUmlerIdAndUversion(Mockito.any(), Mockito.any())).thenReturn(true);
		when(umlerStackCarRepository.findById(Mockito.any())).thenReturn(Optional.ofNullable(umlerStackCar));
		when(stackWellLengthRepository.existsByUmlerIdAndAar1stNr(Mockito.any(), Mockito.any())).thenReturn(true);
		when(stackWellLengthRepository.findByUmlerIdAndAar1stNr(Mockito.any(), Mockito.any())).thenReturn(stackWellLength);
		UmlerStackCar updatedUmlerStackCar = umlerStackCarServiceImpl.updateStackCar(umlerStackCar, httpHeaders);
		assertNotNull(updatedUmlerStackCar);
	}

	@Test
	void testUpdateUmlerIdNoRecordsFoundException() {
		umlerStackCar.setUmlerId(1234L);
		when(umlerStackCarRepository.existsByUmlerIdAndUversion(Mockito.any(), Mockito.any())).thenReturn(false);
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(umlerStackCarServiceImpl.updateStackCar(umlerStackCar, httpHeaders)));
		assertEquals("Record with Umler Id: 1234 not found!", exception.getMessage());
	}

	@Test
	void testUpdateNoRecordsFoundException() {
		umlerStackCar.setStackWellLengthList(stackWellLengthList);
		when(umlerStackCarRepository.existsByUmlerIdAndUversion(Mockito.any(), Mockito.any())).thenReturn(true);
		when(umlerStackCarRepository.findById(Mockito.any())).thenReturn(Optional.ofNullable(umlerStackCar));
		when(stackWellLengthRepository.existsByUmlerIdAndAar1stNr(Mockito.any(), Mockito.any())).thenReturn(false);
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(umlerStackCarServiceImpl.updateStackCar(umlerStackCar, httpHeaders)));
		assertEquals("Record with Umler Id: "+ stackWellLength.getUmlerId()+" and AAR 1st NR: "+stackWellLength.getAar1stNr()+" not found!", exception.getMessage());
	}
}
