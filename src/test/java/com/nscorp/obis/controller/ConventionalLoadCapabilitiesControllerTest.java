package com.nscorp.obis.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.*;

import com.nscorp.obis.domain.ConventionalEquipmentWidth;
import com.nscorp.obis.dto.CodeTableSelectionDTO;
import com.nscorp.obis.dto.ConventionalEquipmentWidthDTO;
import com.nscorp.obis.dto.mapper.UmlerConventionalCarMapper;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.SizeExceedException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.nscorp.obis.domain.UmlerConventionalCar;
import com.nscorp.obis.dto.UmlerConventionalCarDTO;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.ConventionalLoadCapabilitiesService;

class ConventionalLoadCapabilitiesControllerTest {
	
	@InjectMocks
	ConventionalLoadCapabilitiesController conventionalCarController;

	@Mock
	UmlerConventionalCarMapper umlerConventionalCarMapper;

	@Mock
	ConventionalLoadCapabilitiesService conventionalCarService;

	UmlerConventionalCarDTO umlerConvCarDto;
	UmlerConventionalCar umlerConvCar;

	List<UmlerConventionalCarDTO> umlerConvCarDtoList;

	List<UmlerConventionalCar> umlerConvCarList;

	ConventionalEquipmentWidth convEqWidth;
	ConventionalEquipmentWidthDTO convEqWidthDto;
	List<ConventionalEquipmentWidthDTO> convEqWidthDtoList;
	List<ConventionalEquipmentWidth> convEqWidthList;

	Map<String, String> httpHeaders;

	String carInit;

	String aarType;
	Map<String, String> header;

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
		umlerConvCar.setMinEqLength(20);
		umlerConvCar.setMaxEqLength(30);
		umlerConvCar.setMinTrailerLength(10);
		umlerConvCar.setMaxTrailerLength(20);
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

		httpHeaders = new HashMap<>();
		umlerConvCarDtoList.add(umlerConvCarDto);
		umlerConvCarList.add(umlerConvCar);
		httpHeaders.put("userid", "TEST");

		header = new HashMap<String, String>();
		header.put("userid", "Test");
		header.put("extensionschema", "Test");
	}

	@AfterEach
	void tearDown() throws Exception {
		umlerConvCarDto = null;
		umlerConvCar = null;
		umlerConvCarDtoList = null;
		umlerConvCarList = null;
		httpHeaders = null;
	}

	@Test
	void testGetAllConvLoad() {
		when(conventionalCarService.getConvLoad(Mockito.any(), Mockito.any())).thenReturn(umlerConvCarList);
		ResponseEntity<APIResponse<List<UmlerConventionalCarDTO>>> getResponse = conventionalCarController
				.getAllConvLoad(aarType, carInit);
		assertEquals(HttpStatus.OK, getResponse.getStatusCode());
	}

	@Test
	void testAddConventionalCar() {
		when(umlerConventionalCarMapper.UmlerConventionalCarDTOToUmlerConventionalCar(Mockito.any())).thenReturn(umlerConvCar);
		when(conventionalCarService.addConventionalCar(Mockito.any(), Mockito.any())).thenReturn(umlerConvCar);
		when(umlerConventionalCarMapper.UmlerConventionalCarToUmlerConventionalCarDTO(Mockito.any())).thenReturn(umlerConvCarDto);
		ResponseEntity<APIResponse<UmlerConventionalCarDTO>> addedConvCar = conventionalCarController.addConventionalCar(umlerConvCarDto,
				header);
		assertNotNull(addedConvCar.getBody());
	}


	@Test
	void testUpdateConventionalCar() {
		when(umlerConventionalCarMapper.UmlerConventionalCarDTOToUmlerConventionalCar(Mockito.any())).thenReturn(umlerConvCar);
		when(conventionalCarService.updateConventionalCar(Mockito.any(), Mockito.any())).thenReturn(umlerConvCar);
		when(umlerConventionalCarMapper.UmlerConventionalCarToUmlerConventionalCarDTO(Mockito.any())).thenReturn(umlerConvCarDto);
		ResponseEntity<APIResponse<UmlerConventionalCarDTO>> tableUpdated = conventionalCarController.updateConventionalCar(umlerConvCarDto, header);
		assertNotNull(tableUpdated.getBody());
	}

	@Test
	void testNoRecordFoundException() {
		when(conventionalCarService.getConvLoad(Mockito.any(), Mockito.any()))
				.thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<List<UmlerConventionalCarDTO>>> getResponse = conventionalCarController
				.getAllConvLoad(aarType, carInit);
		assertEquals(HttpStatus.NOT_FOUND, getResponse.getStatusCode());

		when(conventionalCarService.addConventionalCar(Mockito.any(),Mockito.any())).thenThrow(new NoRecordsFoundException());
		when(conventionalCarService.updateConventionalCar(Mockito.any(),Mockito.any())).thenThrow(new NoRecordsFoundException());

		ResponseEntity<APIResponse<UmlerConventionalCarDTO>> addConvCar = conventionalCarController.addConventionalCar(Mockito.any(),Mockito.any());
		ResponseEntity<APIResponse<UmlerConventionalCarDTO>> updateConvCar =  conventionalCarController.updateConventionalCar(Mockito.any(),Mockito.any());

		assertEquals(addConvCar.getStatusCodeValue(),404);
		assertEquals(updateConvCar.getStatusCodeValue(),404);

		when(conventionalCarService.deleteConvLoad(Mockito.any()))
				.thenThrow(new NoRecordsFoundException());
		ResponseEntity<List<APIResponse<UmlerConventionalCarDTO>>> deleteResponse = conventionalCarController
				.deleteConventionalCar(umlerConvCarDtoList);
		assertEquals(deleteResponse.getStatusCodeValue(), 500);
	}
	
	@Test
	void TestException() {
		when(conventionalCarService.getConvLoad(Mockito.any(), Mockito.any()))
				.thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<List<UmlerConventionalCarDTO>>> getResponse = conventionalCarController
				.getAllConvLoad(aarType, carInit);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, getResponse.getStatusCode());

		when(conventionalCarService.addConventionalCar(Mockito.any(),Mockito.any())).thenThrow(new RuntimeException());
		when(conventionalCarService.updateConventionalCar(Mockito.any(),Mockito.any())).thenThrow(new RuntimeException());

		ResponseEntity<APIResponse<UmlerConventionalCarDTO>> addConvCar = conventionalCarController.addConventionalCar(Mockito.any(),Mockito.any());
		ResponseEntity<APIResponse<UmlerConventionalCarDTO>> updateConvCar =  conventionalCarController.updateConventionalCar(Mockito.any(),Mockito.any());

		assertEquals(addConvCar.getStatusCodeValue(),500);
		assertEquals(updateConvCar.getStatusCodeValue(),500);

		when(conventionalCarService.deleteConvLoad(Mockito.any()))
				.thenThrow(new RuntimeException());
		ResponseEntity<List<APIResponse<UmlerConventionalCarDTO>>> deleteResponse = conventionalCarController
				.deleteConventionalCar(umlerConvCarDtoList);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, deleteResponse.getStatusCode());
	}

	@Test
	void testAddUpdateSizeExceedException() {
		when(conventionalCarService.addConventionalCar(Mockito.any(),Mockito.any())).thenThrow(new SizeExceedException());
		when(conventionalCarService.updateConventionalCar(Mockito.any(),Mockito.any())).thenThrow(new SizeExceedException());

		ResponseEntity<APIResponse<UmlerConventionalCarDTO>> addConvCar = conventionalCarController.addConventionalCar(Mockito.any(),Mockito.any());
		ResponseEntity<APIResponse<UmlerConventionalCarDTO>> updateConvCar =  conventionalCarController.updateConventionalCar(Mockito.any(),Mockito.any());

		assertEquals(addConvCar.getStatusCodeValue(),411);
		assertEquals(updateConvCar.getStatusCodeValue(),411);
	}

	@Test
	void testAddRecordAlreadyExistsException() {
		when(conventionalCarService.addConventionalCar(Mockito.any(),Mockito.any())).thenThrow(new RecordAlreadyExistsException());
		ResponseEntity<APIResponse<UmlerConventionalCarDTO>> addConvCar = conventionalCarController.addConventionalCar(Mockito.any(),Mockito.any());
		assertEquals(addConvCar.getStatusCodeValue(),208);
	}

	@Test
	void testAddUpdateRecordNotAddedException() {
		when(conventionalCarService.addConventionalCar(Mockito.any(),Mockito.any())).thenThrow(new RecordNotAddedException());
		ResponseEntity<APIResponse<UmlerConventionalCarDTO>> addConvCar = conventionalCarController.addConventionalCar(Mockito.any(),Mockito.any());
		assertEquals(addConvCar.getStatusCodeValue(),406);

		when(conventionalCarService.updateConventionalCar(Mockito.any(),Mockito.any())).thenThrow(new RecordNotAddedException());
		ResponseEntity<APIResponse<UmlerConventionalCarDTO>> updateConvCar = conventionalCarController.updateConventionalCar(Mockito.any(),Mockito.any());
		assertEquals(updateConvCar.getStatusCodeValue(),406);
	}

	@Test
	void testAddUpdateNullPointerException() {
		when(conventionalCarService.addConventionalCar(Mockito.any(),Mockito.any())).thenThrow(new NullPointerException());
		when(conventionalCarService.updateConventionalCar(Mockito.any(),Mockito.any())).thenThrow(new NullPointerException());

		ResponseEntity<APIResponse<UmlerConventionalCarDTO>> addConvCar = conventionalCarController.addConventionalCar(Mockito.any(),Mockito.any());
		ResponseEntity<APIResponse<UmlerConventionalCarDTO>> updateConvCar =  conventionalCarController.updateConventionalCar(Mockito.any(),Mockito.any());

		assertEquals(addConvCar.getStatusCodeValue(),400);
		assertEquals(updateConvCar.getStatusCodeValue(),400);
	}

	@Test
	void testDeleteCoventionalCar() {
		when(conventionalCarService.deleteConvLoad(Mockito.any())).thenReturn(umlerConvCar);
		ResponseEntity<List<APIResponse<UmlerConventionalCarDTO>>> deleteResponse = conventionalCarController
				.deleteConventionalCar(umlerConvCarDtoList);
		assertEquals(deleteResponse.getStatusCodeValue(), 200);
	}

	@Test
	void testDeleteEmptyList(){
		when(conventionalCarService.deleteConvLoad(Mockito.any())).thenReturn(umlerConvCar);
		ResponseEntity<List<APIResponse<UmlerConventionalCarDTO>>> deleteCarExempt = conventionalCarController
				.deleteConventionalCar(Collections.emptyList());
		assertEquals(deleteCarExempt.getStatusCodeValue(), 500);
	}

}
