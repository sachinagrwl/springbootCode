package com.nscorp.obis.services;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.nscorp.obis.domain.LowProfileEquipmentWidth;
import com.nscorp.obis.dto.LowProfileEquipmentWidthDTO;
import com.nscorp.obis.exception.RecordNotAddedException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.nscorp.obis.domain.UmlerLowProfileCar;
import com.nscorp.obis.dto.UmlerLowProfileCarDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.repository.LowProfileEquipmentWidthRepository;
import com.nscorp.obis.repository.UmlerLowProfileCarRepository;

class UmlerLowProfileCarServiceTest {

	@InjectMocks
	UmlerLowProfileCarServiceImpl umlerLowProfileCarService;

	@Mock
	UmlerLowProfileCarRepository umlerLowProfileCarRepository;
	
	@Mock
	LowProfileEquipmentWidthRepository lowProfileEqWidthRepository;

	UmlerLowProfileCarDTO umlerLowProfileCarDTO;
	UmlerLowProfileCar umlerLowProfileCar;

	List<UmlerLowProfileCarDTO> umlerLowProfileCarDTOList;

	List<UmlerLowProfileCar> umlerLowProfileCarList;

	LowProfileEquipmentWidthDTO lowProfileEquipmentWidthDTO;
	LowProfileEquipmentWidth lowProfileEquipmentWidth;

	List<LowProfileEquipmentWidthDTO> lowProfileEquipmentWidthDTOList;

	List<LowProfileEquipmentWidth> lowProfileEquipmentWidthList;
	Map<String, String> httpHeaders;
	Map<String, String> header;
	String carInit;

	String aarType;

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		umlerLowProfileCarDTO = new UmlerLowProfileCarDTO();
		umlerLowProfileCar = new UmlerLowProfileCar();
		umlerLowProfileCarDTOList = new ArrayList<>();
		umlerLowProfileCarList = new ArrayList<>();
		lowProfileEquipmentWidthDTO = new LowProfileEquipmentWidthDTO();
		lowProfileEquipmentWidthDTO.setUmlerId(100L);
		lowProfileEquipmentWidthDTO.setAar1stNr("1");
		lowProfileEquipmentWidthDTO.setMinEqWidth(100);
		lowProfileEquipmentWidthDTO.setMaxEqWidth(200);
		lowProfileEquipmentWidth = new LowProfileEquipmentWidth();
		lowProfileEquipmentWidth.setUmlerId(100L);
		lowProfileEquipmentWidth.setAar1stNr("1");
		lowProfileEquipmentWidth.setMinEqWidth(100);
		lowProfileEquipmentWidth.setMaxEqWidth(200);
		lowProfileEquipmentWidthDTOList = new ArrayList<>();
		lowProfileEquipmentWidthList = new ArrayList<>();
		lowProfileEquipmentWidthDTOList.add(lowProfileEquipmentWidthDTO);
		lowProfileEquipmentWidthList.add(lowProfileEquipmentWidth);
		httpHeaders = new HashMap<>();
		umlerLowProfileCarDTOList.add(umlerLowProfileCarDTO);
		umlerLowProfileCarList.add(umlerLowProfileCar);
		httpHeaders.put("userid", "TEST");
		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");
	}

	@AfterEach
	void tearDown() throws Exception {
		umlerLowProfileCarDTO = null;
		umlerLowProfileCar = null;
		umlerLowProfileCarDTOList = null;
		umlerLowProfileCarList = null;
		httpHeaders = null;
	}

	@ParameterizedTest
	@ValueSource(strings = { "test", "" })
	void testGetAllGuaranteeCustCrossRef(String reqParams) {
		when(umlerLowProfileCarRepository.findAllByAarTypeAndCarInit(Mockito.any(), Mockito.any()))
				.thenReturn(umlerLowProfileCarList);
		List<UmlerLowProfileCar> getResponse = umlerLowProfileCarService.getUmlerLowProfileCars(reqParams, reqParams);
		assertFalse(getResponse.isEmpty());
	}

	@SuppressWarnings("unchecked")
	@Test
	void testNoRecordFoundException() {
		when(umlerLowProfileCarRepository.findAllByAarTypeAndCarInit(Mockito.any(), Mockito.any()))
				.thenReturn(Collections.EMPTY_LIST);
		NoRecordsFoundException getException = assertThrows(NoRecordsFoundException.class,
				() -> when(umlerLowProfileCarService.getUmlerLowProfileCars(aarType, carInit)));
		assertEquals("No Record Found under this search!", getException.getMessage());
		
		when(umlerLowProfileCarRepository.existsByUmlerIdAndUversion(Mockito.any(), Mockito.any())).thenReturn(false);
		NoRecordsFoundException deleteException = assertThrows(NoRecordsFoundException.class,
				() -> when(umlerLowProfileCarService.deleteProfileLoad(umlerLowProfileCar)));
		assertEquals("No record Found to delete Under this Umler Id: "+umlerLowProfileCar.getUmlerId()+" and U_Version: "+umlerLowProfileCar.getUversion(), deleteException.getMessage());
	

	}
	
	
	@Test
	void testDeleteProfileCar() {
		when(umlerLowProfileCarRepository.existsByUmlerIdAndUversion(Mockito.any(), Mockito.any()))
			.thenReturn(true);
		when(umlerLowProfileCarRepository.findById(Mockito.any()))
			.thenReturn(Optional.ofNullable(umlerLowProfileCar));
		when(lowProfileEqWidthRepository.existsByUmlerId(Mockito.any()))
			.thenReturn(true);
		UmlerLowProfileCar deleteResponse = umlerLowProfileCarService.deleteProfileLoad(umlerLowProfileCar);
		assertEquals(deleteResponse, umlerLowProfileCar);
	}

	@Test
	void testUpdateConventionalCar() {
		umlerLowProfileCar.setUversion("!");
		umlerLowProfileCar.setLowProfileEquipmentWidth(lowProfileEquipmentWidthList);
		lowProfileEquipmentWidth.setUversion("!");
		when(umlerLowProfileCarRepository.existsByUmlerIdAndUversion(Mockito.any(), Mockito.any())).thenReturn(true);
		when(umlerLowProfileCarRepository.findById(Mockito.any())).thenReturn(Optional.ofNullable(umlerLowProfileCar));
		when(lowProfileEqWidthRepository.existsByUmlerIdAndAar1stNr(Mockito.any(), Mockito.any())).thenReturn(true);
		when(lowProfileEqWidthRepository.findByUmlerIdAndAar1stNr(Mockito.any(), Mockito.any())).thenReturn(lowProfileEquipmentWidth);
		UmlerLowProfileCar updatedUmlerLowProfileCar = umlerLowProfileCarService.updateUmlerLowProfileCars(umlerLowProfileCar, header);
		assertNotNull(updatedUmlerLowProfileCar);
	}

	@Test
	void testUpdateExtensionSchemaNullPointerException() {
		when(umlerLowProfileCarRepository.existsByUmlerIdAndUversion(Mockito.any(), Mockito.any())).thenReturn(true);
		header.put("extensionschema", " ");
		NullPointerException exception = assertThrows(NullPointerException.class,
				() -> when(umlerLowProfileCarService.updateUmlerLowProfileCars(umlerLowProfileCar, header)));
		assertEquals("Extension Schema should not be null, empty or blank", exception.getMessage());
	}

	@Test
	void testUpdateExtensionSchemaNoRecordsFoundException() {
		umlerLowProfileCar.setUmlerId(1234L);
		when(umlerLowProfileCarRepository.existsByUmlerIdAndUversion(Mockito.any(), Mockito.any())).thenReturn(false);
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(umlerLowProfileCarService.updateUmlerLowProfileCars(umlerLowProfileCar, header)));
		assertEquals("Record with Umler Id: 1234 not found!", exception.getMessage());
	}

	@Test
	void testUpdateNoRecordsFoundException() {
		umlerLowProfileCar.setLowProfileEquipmentWidth(lowProfileEquipmentWidthList);
		when(umlerLowProfileCarRepository.existsByUmlerIdAndUversion(Mockito.any(), Mockito.any())).thenReturn(true);
		when(umlerLowProfileCarRepository.findById(Mockito.any())).thenReturn(Optional.ofNullable(umlerLowProfileCar));
		when(lowProfileEqWidthRepository.existsByUmlerIdAndAar1stNr(Mockito.any(), Mockito.any())).thenReturn(false);
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(umlerLowProfileCarService.updateUmlerLowProfileCars(umlerLowProfileCar, header)));
		assertEquals("Record with Umler Id: "+ lowProfileEquipmentWidth.getUmlerId()+" and AAR 1st NR: "+lowProfileEquipmentWidth.getAar1stNr()+" not found!", exception.getMessage());
	}

	@Test
	void testUpdateMinMAxWidthRecordNotAddedException() {
		lowProfileEquipmentWidth.setMinEqWidth(45);
		lowProfileEquipmentWidth.setMaxEqWidth(40);
		umlerLowProfileCar.setLowProfileEquipmentWidth(lowProfileEquipmentWidthList);
		when(umlerLowProfileCarRepository.existsByUmlerIdAndUversion(Mockito.any(), Mockito.any())).thenReturn(true);
		when(umlerLowProfileCarRepository.findById(Mockito.any())).thenReturn(Optional.ofNullable(umlerLowProfileCar));
		when(lowProfileEqWidthRepository.existsByUmlerIdAndAar1stNr(Mockito.any(), Mockito.any())).thenReturn(true);
		RecordNotAddedException exception = assertThrows(RecordNotAddedException.class,
				() -> when(umlerLowProfileCarService.updateUmlerLowProfileCars(umlerLowProfileCar, header)));
		assertEquals("Min EQ Width: " + lowProfileEquipmentWidth.getMinEqWidth()
				+ " should be less than or equal to Max EQ Width: "
				+ lowProfileEquipmentWidth.getMaxEqWidth(), exception.getMessage());
	}
}
