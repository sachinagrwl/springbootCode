package com.nscorp.obis.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.SizeExceedException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.nscorp.obis.domain.EquipmentCar;
import com.nscorp.obis.dto.EquipmentCarDTO;
import com.nscorp.obis.dto.mapper.EquipmentCarMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.EquipmentCarService;

class EquipmentCarControllerTest {

	@InjectMocks
	EquipmentCarController carController;

	@Mock
	EquipmentCarMapper carMapper;

	@Mock
	EquipmentCarService carService;

	EquipmentCar equipmentCar;
	EquipmentCarDTO equipmentCarDTO;
	List<EquipmentCar> equipmentCarList;
	List<EquipmentCarDTO> equipmentCarDTOList;

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

		equipmentCarDTO = new EquipmentCarDTO();
		equipmentCarDTO.setCarInit("ABCD");
		equipmentCarDTO.setCarNbr(new BigDecimal(4000));
		equipmentCarDTO.setCarEquipType("F");
		equipmentCarDTO.setAarType("ABCD");
		equipmentCarDTO.setCarLgth(300);
		equipmentCarDTO.setCarTareWgt(300);
		equipmentCarDTO.setDamageInd("N");
		equipmentCarDTO.setBadOrderInd("N");
		equipmentCarDTO.setCarSa(1000L);
		equipmentCarDTO.setPrevStcc(10);
		equipmentCarDTO.setPlatformHeight_inches(25);
		equipmentCarDTO.setArticulate("N");
		equipmentCarDTO.setNrOfAxles(10);
		equipmentCarDTO.setCarLoadLimit(100);

		equipmentCarList = new ArrayList<>();
		equipmentCarList.add(equipmentCar);

		equipmentCarDTOList = new ArrayList<>();
		equipmentCarDTOList.add(equipmentCarDTO);

		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");
	}

	@AfterEach
	void tearDown() throws Exception {
		header = null;
		equipmentCar = null;
		equipmentCarDTO = null;
		equipmentCarList = null;
		equipmentCarDTOList = null;
	}

	@Test
	void testGetEquipmentCar() {
		when(carService.getEquipmentCar(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(equipmentCar);
		ResponseEntity<APIResponse<List<EquipmentCarDTO>>> getEquipmentCar = carController.getEquipmentCar(carInit,
				carNbr, carEquipType);
		assertEquals(getEquipmentCar.getStatusCodeValue(), 200);
	}

	@Test
	void testAddEquipmentCar() {
		when(carMapper.EquipmentCarDTOToEquipmentCar(Mockito.any())).thenReturn(equipmentCar);
		when(carService.addEquipmentCar(Mockito.any(), Mockito.any())).thenReturn(equipmentCar);
		when(carMapper.EquipmentCarToEquipmentCarDTO(Mockito.any())).thenReturn(equipmentCarDTO);
		ResponseEntity<APIResponse<EquipmentCarDTO>> addedEquipCar = carController.addEquipmentCar(equipmentCarDTO,
				header);
		assertNotNull(addedEquipCar.getBody());
	}

	@ParameterizedTest
	@ValueSource(strings = { "1", "0", "37", "", "A" })
	void testUpdateEquipmentCar(String input) {
		equipmentCar.setArticulate(input);
		equipmentCarDTO.setArticulate(input);
		when(carService.updateEquipmentCar(Mockito.any(), Mockito.any())).thenReturn(equipmentCar);
		ResponseEntity<APIResponse<EquipmentCarDTO>> updateEquipmentCar = carController
				.updateEquipmentCar(equipmentCarDTO, header);
		assertEquals(updateEquipmentCar.getStatusCodeValue(), 200);
	}

	@Test
	void testNoRecordsFoundException() {
		when(carService.getEquipmentCar(Mockito.any(), Mockito.any(), Mockito.any()))
				.thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<List<EquipmentCarDTO>>> getEquipmentCar = carController.getEquipmentCar(carInit,
				carNbr, carEquipType);
		assertEquals(getEquipmentCar.getStatusCodeValue(), 404);

		when(carService.addEquipmentCar(Mockito.any(), Mockito.any())).thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<EquipmentCarDTO>> addEquipmentCar = carController.addEquipmentCar(equipmentCarDTO,
				header);
		assertEquals(addEquipmentCar.getStatusCodeValue(), 404);

		when(carService.updateEquipmentCar(Mockito.any(), Mockito.any())).thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<EquipmentCarDTO>> updateEquipmentCar = carController
				.updateEquipmentCar(equipmentCarDTO, header);
		assertEquals(updateEquipmentCar.getStatusCodeValue(), 404);
	}

	@Test
	void testRecordNotAddedException() {
		when(carService.updateEquipmentCar(Mockito.any(), Mockito.any())).thenThrow(new RecordNotAddedException());
		ResponseEntity<APIResponse<EquipmentCarDTO>> updateEquipmentCar = carController
				.updateEquipmentCar(equipmentCarDTO, header);
		assertEquals(updateEquipmentCar.getStatusCodeValue(), 406);

		when(carService.addEquipmentCar(Mockito.any(), Mockito.any())).thenThrow(new RecordNotAddedException());
		ResponseEntity<APIResponse<EquipmentCarDTO>> addedCar = carController.addEquipmentCar(equipmentCarDTO, header);
		assertEquals(addedCar.getStatusCodeValue(), 406);
	}

	@Test
	void testRoadsException() {
		when(carService.getEquipmentCar(Mockito.any(), Mockito.any(), Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<List<EquipmentCarDTO>>> getEquipmentCar = carController.getEquipmentCar(carInit,
				carNbr, carEquipType);
		assertEquals(getEquipmentCar.getStatusCodeValue(), 500);

		when(carService.addEquipmentCar(Mockito.any(), Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<EquipmentCarDTO>> addedEquipCar = carController.addEquipmentCar(equipmentCarDTO,
				header);
		assertEquals(addedEquipCar.getStatusCodeValue(), 500);

		when(carService.updateEquipmentCar(Mockito.any(), Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<EquipmentCarDTO>> updateEquipmentCar = carController
				.updateEquipmentCar(equipmentCarDTO, header);
		assertEquals(updateEquipmentCar.getStatusCodeValue(), 500);
	}

	@Test
	void testSizeExceedException() {
		when(carService.addEquipmentCar(Mockito.any(), Mockito.any())).thenThrow(new SizeExceedException());
		ResponseEntity<APIResponse<EquipmentCarDTO>> addedCar = carController.addEquipmentCar(equipmentCarDTO, header);
		assertEquals(addedCar.getStatusCodeValue(), 411);
	}

	@Test
	void testAddRecordAlreadyExistsException() {
		when(carService.addEquipmentCar(Mockito.any(), Mockito.any())).thenThrow(new RecordAlreadyExistsException());
		ResponseEntity<APIResponse<EquipmentCarDTO>> addedCar = carController.addEquipmentCar(equipmentCarDTO, header);
		assertEquals(addedCar.getStatusCodeValue(), 208);
	}

	@Test
	void testNullPointerException() {
		when(carService.addEquipmentCar(Mockito.any(), Mockito.any())).thenThrow(new NullPointerException());
		ResponseEntity<APIResponse<EquipmentCarDTO>> addedCar = carController.addEquipmentCar(equipmentCarDTO, header);
		assertEquals(addedCar.getStatusCodeValue(), 400);
	}
}