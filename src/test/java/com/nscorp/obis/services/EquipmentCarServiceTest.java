package com.nscorp.obis.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.nscorp.obis.domain.EquipmentCar;
import com.nscorp.obis.domain.EquipmentCustomerRange;
import com.nscorp.obis.domain.EquipmentHitch;
import com.nscorp.obis.domain.EquipmentLocation;
import com.nscorp.obis.domain.HoldOrders;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.repository.AARTypeRepository;
import com.nscorp.obis.repository.EquipmentCarRepository;
import com.nscorp.obis.repository.EquipmentChassisRepository;
import com.nscorp.obis.repository.EquipmentContRepository;
import com.nscorp.obis.repository.EquipmentCustomerRangeRepository;
import com.nscorp.obis.repository.EquipmentHitchRepository;
import com.nscorp.obis.repository.EquipmentLocationRepository;
import com.nscorp.obis.repository.EquipmentTrailorRepository;
import com.nscorp.obis.repository.HoldOrdersRepository;

class EquipmentCarServiceTest {

	@InjectMocks
	EquipmentCarServiceImpl equipmentCarService;

	@Mock
	EquipmentCarRepository equipmentCarRepository;

	@Mock
	EquipmentHitchRepository equipmentHitchRepository;

	@Mock
	EquipmentLocationRepository equipmentLocationRepository;

	@Mock
	HoldOrdersRepository holdOrdersRepository;

	@Mock
	AARTypeRepository aarTypeRepository;

	@Mock
	EquipmentChassisRepository equipmentChassisRepository;

	@Mock
	EquipmentContRepository equipmentContRepository;

	@Mock
	EquipmentTrailorRepository equipmentTrailorRepository;

	@Mock
	EquipmentCustomerRangeRepository equipmentCustomerRangeRepository;

	EquipmentCar equipmentCar;
	List<EquipmentCar> equipmentCarList;

	List<EquipmentLocation> equipmentLocationList;
	List<EquipmentHitch> equipmentHitchList;
	List<HoldOrders> holdOrdersList;

	Map<String, String> header;

	String carInit;
	BigDecimal carNbr;
	String carEquipType;

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {

		MockitoAnnotations.initMocks(this);
		carInit = "ABCD";
		carNbr = new BigDecimal(100);
		carEquipType = "F";

		equipmentCar = new EquipmentCar();
		equipmentCar.setCarInit("ABCD");
		equipmentCar.setCarNbr(new BigDecimal(4000));
		equipmentCar.setCarEquipType("F");
		equipmentCar.setAarType("ABCD");
		equipmentCar.setCarLgth(300);
		equipmentCar.setCarTareWgt(300);
		equipmentCar.setDamageInd("N");
		equipmentCar.setBadOrderInd("N");
		equipmentCar.setCarSa(1000L);
		equipmentCar.setPrevStcc(10);
		equipmentCar.setPlatformHeight_inches(25);
		equipmentCar.setArticulate("N");
		equipmentCar.setNrOfAxles(10);
		equipmentCar.setCarLoadLimit(100);

		equipmentCarList = new ArrayList<>();
		equipmentCarList.add(equipmentCar);

		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");
	}

	@AfterEach
	void tearDown() throws Exception {
		header = null;
		equipmentCar = null;
		equipmentCarList = null;
	}

	@Test
	void testGetEquipmentCar() {
		when(equipmentCarRepository.findByCarInitAndCarNbrAndCarEquipType(Mockito.any(), Mockito.any(), Mockito.any()))
				.thenReturn(equipmentCar);
		when(equipmentHitchRepository.findByCarInitAndCarNbrAndCarEquipType(Mockito.any(), Mockito.any(),
				Mockito.any())).thenReturn(equipmentHitchList);
		when(equipmentLocationRepository.findByEquipInitAndEquipNbrAndEquipTp(Mockito.any(), Mockito.any(),
				Mockito.any())).thenReturn(equipmentLocationList);
		when(holdOrdersRepository.findByEquipmentInitAndEquipmentNbrAndEquipmentType(Mockito.any(), Mockito.any(),
				Mockito.any())).thenReturn(holdOrdersList);
		EquipmentCar equipCar = equipmentCarService.getEquipmentCar(carInit, carNbr, carEquipType);
		assertEquals(equipCar, equipmentCar);
	}

	@ParameterizedTest
	@ValueSource(strings = { "!", "", " " })
	void testUpdateEquipmentCar(String uVersion) {
		when(equipmentCarRepository.existsByCarInitAndCarNbrAndCarEquipTypeAndUversion(Mockito.any(), Mockito.any(),
				Mockito.any(), Mockito.any())).thenReturn(true);
		equipmentCar.setUversion(uVersion);
		when(equipmentCarRepository.findByCarInitAndCarNbrAndCarEquipType(Mockito.any(), Mockito.any(), Mockito.any()))
				.thenReturn(equipmentCar);
		when(equipmentCarRepository.save(Mockito.any())).thenReturn(equipmentCar);
		when(aarTypeRepository.existsByAarType(Mockito.any())).thenReturn(true);
		EquipmentCar equipCar = equipmentCarService.updateEquipmentCar(equipmentCar, header);
		assertNotNull(equipCar);
	}

	@Test
	void testEquipmentCarNoRecordsFoundException() {
		when(equipmentCarRepository.findByCarInitAndCarNbrAndCarEquipType(Mockito.any(), Mockito.any(), Mockito.any()))
				.thenReturn(null);
		NoRecordsFoundException exception = assertThrows(NoRecordsFoundException.class,
				() -> when(equipmentCarService.getEquipmentCar(carInit, carNbr, carEquipType)));
		assertEquals("No Record Found under this search!", exception.getMessage());

		when(equipmentCarRepository.existsByCarInitAndCarNbrAndCarEquipTypeAndUversion(Mockito.any(), Mockito.any(),
				Mockito.any(), Mockito.any())).thenReturn(false);
		NoRecordsFoundException updateException = assertThrows(NoRecordsFoundException.class,
				() -> when(equipmentCarService.updateEquipmentCar(equipmentCar, header)));
		assertEquals(
				"No record Found to Update Under this car Init: " + equipmentCar.getCarInit() + ", car Nbr: "
						+ equipmentCar.getCarNbr() + " and U_Version: " + equipmentCar.getUversion(),
				updateException.getMessage());
	}

	@Test
	void testEquipmentCarRecordNotAddedException() {
		when(equipmentCarRepository.existsByCarInitAndCarNbrAndCarEquipTypeAndUversion(Mockito.any(), Mockito.any(),
				Mockito.any(), Mockito.any())).thenReturn(true);
		when(equipmentCarRepository.findByCarInitAndCarNbrAndCarEquipType(Mockito.any(), Mockito.any(), Mockito.any()))
				.thenReturn(equipmentCar);
		when(aarTypeRepository.existsByAarType(Mockito.any())).thenReturn(false);
		RecordNotAddedException updateException = assertThrows(RecordNotAddedException.class,
				() -> when(equipmentCarService.updateEquipmentCar(equipmentCar, header)));
		assertEquals("AAR Type is not Valid !", updateException.getMessage());
	}

	@Test
	void testEquipmentCarNullPointerException() {
		when(equipmentCarRepository.existsByCarInitAndCarNbrAndCarEquipTypeAndUversion(Mockito.any(), Mockito.any(),
				Mockito.any(), Mockito.any())).thenReturn(true);
		when(equipmentCarRepository.findByCarInitAndCarNbrAndCarEquipType(Mockito.any(), Mockito.any(), Mockito.any()))
				.thenReturn(equipmentCar);
		when(aarTypeRepository.existsByAarType(Mockito.any())).thenReturn(true);
		header.put("extensionschema", null);
		NullPointerException updateException = assertThrows(NullPointerException.class,
				() -> when(equipmentCarService.updateEquipmentCar(equipmentCar, header)));
		assertEquals("Extension Schema should not be null, empty or blank", updateException.getMessage());
	}

	@Test
	void testAddEquipmentCar() {
		when(equipmentContRepository.existsByContainerInitAndContainerNbr(Mockito.any(), Mockito.any()))
				.thenReturn(false);
		when(equipmentTrailorRepository.existsByTrailorInitAndTrailorNumber(Mockito.any(), Mockito.any()))
				.thenReturn(false);
		when(equipmentChassisRepository.existsByChasInitAndChasNbr(Mockito.any(), Mockito.any())).thenReturn(false);
		when(equipmentCustomerRangeRepository.existsByEquipmentInit(Mockito.any())).thenReturn(false);
		when(equipmentCarRepository.existsByCarInitAndCarNbrAndCarEquipType(Mockito.any(), Mockito.any(),
				Mockito.any())).thenReturn(false);
		when(equipmentCarRepository.save(Mockito.any())).thenReturn(equipmentCar);
		when(aarTypeRepository.existsByAarType(Mockito.any())).thenReturn(true);
		EquipmentCar equipCar = equipmentCarService.addEquipmentCar(equipmentCar, header);
		assertNotNull(equipCar);
	}

	@Test
	void testAddRecordNotAddedExceptionValdiations() {
		when(equipmentContRepository.existsByContainerInitAndContainerNbr(Mockito.any(), Mockito.any()))
				.thenReturn(true);
		RecordNotAddedException addException1 = assertThrows(RecordNotAddedException.class,
				() -> when(equipmentCarService.addEquipmentCar(equipmentCar, header)));
		assertEquals("Car Init & Car Number found in Equipment Type - Container", addException1.getMessage());

		when(equipmentContRepository.existsByContainerInitAndContainerNbr(Mockito.any(), Mockito.any()))
				.thenReturn(false);
		when(equipmentTrailorRepository.existsByTrailorInitAndTrailorNumber(Mockito.any(), Mockito.any()))
				.thenReturn(true);
		RecordNotAddedException addException2 = assertThrows(RecordNotAddedException.class,
				() -> when(equipmentCarService.addEquipmentCar(equipmentCar, header)));
		assertEquals("Car Init & Car Number found in Equipment Type - Trailor", addException2.getMessage());

		when(equipmentContRepository.existsByContainerInitAndContainerNbr(Mockito.any(), Mockito.any()))
				.thenReturn(false);
		when(equipmentTrailorRepository.existsByTrailorInitAndTrailorNumber(Mockito.any(), Mockito.any()))
				.thenReturn(false);
		when(equipmentChassisRepository.existsByChasInitAndChasNbr(Mockito.any(), Mockito.any())).thenReturn(true);
		RecordNotAddedException addException3 = assertThrows(RecordNotAddedException.class,
				() -> when(equipmentCarService.addEquipmentCar(equipmentCar, header)));
		assertEquals("Car Init & Car Number found in Equipment Type - Chassis", addException3.getMessage());

		when(equipmentContRepository.existsByContainerInitAndContainerNbr(Mockito.any(), Mockito.any()))
				.thenReturn(false);
		when(equipmentTrailorRepository.existsByTrailorInitAndTrailorNumber(Mockito.any(), Mockito.any()))
				.thenReturn(false);
		when(equipmentChassisRepository.existsByChasInitAndChasNbr(Mockito.any(), Mockito.any())).thenReturn(false);
		when(equipmentCustomerRangeRepository.existsByEquipmentInit(Mockito.any())).thenReturn(true);
		when(equipmentCustomerRangeRepository.getEquipmentCustomerRange(Mockito.any(), Mockito.any()))
				.thenReturn(new EquipmentCustomerRange());
		RecordNotAddedException addException4 = assertThrows(RecordNotAddedException.class,
				() -> when(equipmentCarService.addEquipmentCar(equipmentCar, header)));
		assertEquals("Car Init & Car Number found in Equipment Type - null", addException4.getMessage());

		when(equipmentContRepository.existsByContainerInitAndContainerNbr(Mockito.any(), Mockito.any()))
				.thenReturn(false);
		when(equipmentTrailorRepository.existsByTrailorInitAndTrailorNumber(Mockito.any(), Mockito.any()))
				.thenReturn(false);
		when(equipmentChassisRepository.existsByChasInitAndChasNbr(Mockito.any(), Mockito.any())).thenReturn(false);
		when(equipmentCustomerRangeRepository.existsByEquipmentInit(Mockito.any())).thenReturn(false);
		when(equipmentCarRepository.existsByCarInitAndCarNbrAndCarEquipType(Mockito.any(), Mockito.any(),
				Mockito.any())).thenReturn(true);
		RecordNotAddedException addException5 = assertThrows(RecordNotAddedException.class,
				() -> when(equipmentCarService.addEquipmentCar(equipmentCar, header)));
		assertEquals("Record Already Exists!!", addException5.getMessage());

		when(equipmentContRepository.existsByContainerInitAndContainerNbr(Mockito.any(), Mockito.any()))
				.thenReturn(false);
		when(equipmentTrailorRepository.existsByTrailorInitAndTrailorNumber(Mockito.any(), Mockito.any()))
				.thenReturn(false);
		when(equipmentChassisRepository.existsByChasInitAndChasNbr(Mockito.any(), Mockito.any())).thenReturn(false);
		when(equipmentCustomerRangeRepository.existsByEquipmentInit(Mockito.any())).thenReturn(false);
		when(equipmentCarRepository.existsByCarInitAndCarNbrAndCarEquipType(Mockito.any(), Mockito.any(),
				Mockito.any())).thenReturn(false);
		when(aarTypeRepository.existsByAarType(Mockito.any())).thenReturn(false);
		RecordNotAddedException addException6 = assertThrows(RecordNotAddedException.class,
				() -> when(equipmentCarService.addEquipmentCar(equipmentCar, header)));
		assertEquals("AAR Type is not Valid !", addException6.getMessage());

	}
}