package com.nscorp.obis.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.nscorp.obis.domain.EquipmentEmbargo;
import com.nscorp.obis.domain.RestrictedWells;
import com.nscorp.obis.domain.Station;
import com.nscorp.obis.dto.EquipmentEmbargoDTO;
import com.nscorp.obis.dto.mapper.EquipmentEmbargoMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.exception.SizeExceedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.EquipmentEmbargoService;

class EquipmentEmbargoControllerTest {
	
	@InjectMocks
	EquipmentEmbargoController eqEmbargoController;
	
	@Mock
	EquipmentEmbargoMapper eqEmbargoMapper;
	
	@Mock
	EquipmentEmbargoService eqEmbargoService;
	
	EquipmentEmbargo eqEmbargo;

	Station destinationTerminal;

	Station originTerminal;
	EquipmentEmbargoDTO eqEmbargoDto;
	List<EquipmentEmbargo> eqEmbargoList;
	List<EquipmentEmbargoDTO> eqEmbargoDtoList;
	
	Map<String, String> header;
	
	BigDecimal eqNrLow = new BigDecimal(1000);
	BigDecimal eqNrHigh = new BigDecimal(1000);
	BigDecimal carNrLow = new BigDecimal(1000);
	BigDecimal carNrHigh = new BigDecimal(1000);
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

//		eqEmbargo.setDestinationTerminalId(18245946233394L);
		destinationTerminal = new Station();
		eqEmbargo.setDestinationTerminal(destinationTerminal);
		eqEmbargo.getDestinationTerminal().setTermId(18245946233394L);
		eqEmbargo.getDestinationTerminal().setFSAC("FSAC");
		eqEmbargo.getDestinationTerminal().setState("AZ");
		eqEmbargo.getDestinationTerminal().setRoadName("NS");
		eqEmbargo.getDestinationTerminal().setBillAtFsac("FSACBill");
		eqEmbargo.getDestinationTerminal().setIntermodalIndicator("Y");
		eqEmbargo.getDestinationTerminal().setRoadNumber("ABCD");
		eqEmbargo.getDestinationTerminal().setBottomPick("Y");
		eqEmbargo.getDestinationTerminal().setChar5Alias("ABCSA");
		eqEmbargo.getDestinationTerminal().setChar8Spell("ASASASAS");
		eqEmbargo.getDestinationTerminal().setOperationStation("ATLANTA");
		eqEmbargo.setDestinationTerminal(destinationTerminal);

		eqEmbargo.setEquipmentAarType("SERT");
		eqEmbargo.setEquipmentInit("HIGH");
		eqEmbargo.setEquipmentLength(20);
		eqEmbargo.setEquipmentNumberHigh(eqNrHigh);
		eqEmbargo.setEquipmentNumberLow(eqNrLow);
		eqEmbargo.setEquipmentType("C");

//		eqEmbargo.setOriginTerminalId(18245946233393L);
		originTerminal = new Station();
		eqEmbargo.setOriginTerminal(originTerminal);
		eqEmbargo.getOriginTerminal().setTermId(18245946233393L);
		eqEmbargo.getOriginTerminal().setFSAC("FSAC");
		eqEmbargo.getOriginTerminal().setState("NV");
		eqEmbargo.getOriginTerminal().setRoadName("NS");
		eqEmbargo.getOriginTerminal().setBillAtFsac("FSACBill");
		eqEmbargo.getOriginTerminal().setIntermodalIndicator("Y");
		eqEmbargo.getOriginTerminal().setRoadNumber("ABCD");
		eqEmbargo.getOriginTerminal().setBottomPick("Y");
		eqEmbargo.getOriginTerminal().setChar5Alias("ABCSA");
		eqEmbargo.getOriginTerminal().setChar8Spell("ASASASAS");
		eqEmbargo.getOriginTerminal().setOperationStation("ATLANTA");
		eqEmbargo.setOriginTerminal(originTerminal);

		eqEmbargo.setRestrictedWells(restrictWells);
		eqEmbargo.setRestriction("Y");
		eqEmbargo.setRoadName("AMDX");
		eqEmbargo.setTofcCofcIndicator("T");
		
		eqEmbargoList = new ArrayList<>();
		eqEmbargoList.add(eqEmbargo);

//		eqEmbargoDto.setEmbargoTerminalId(18245946233393L);
		eqEmbargo.setDestinationTerminal(destinationTerminal);


		eqEmbargoDto = new EquipmentEmbargoDTO();
		eqEmbargoDto.setCarAarType("TEST");
		eqEmbargoDto.setCarEquipmentType("C");
		eqEmbargoDto.setCarInit("QWER");
		eqEmbargoDto.setCarNumberHigh(carNrHigh);
		eqEmbargoDto.setCarNumberLow(carNrLow);
		eqEmbargoDto.setDescription("DO NOT LOAD 20'. NOT 20' CAPABLE");
		eqEmbargoDto.setEmbargoId(123000L);

		eqEmbargoDto.setEquipmentAarType("SERT");
		eqEmbargoDto.setEquipmentInit("C");
		eqEmbargoDto.setEquipmentLength(20);
		eqEmbargoDto.setEquipmentNumberHigh(eqNrHigh);
		eqEmbargoDto.setEquipmentNumberLow(eqNrLow);
		eqEmbargoDto.setEquipmentType("C");

//		eqEmbargoDto.setOriginTerminalId(18245946233393L);
		eqEmbargo.setOriginTerminal(originTerminal);


		eqEmbargoDto.setRestrictedWells(restrictWells);
		eqEmbargoDto.setRestriction("Y");
		eqEmbargoDto.setRoadName("AMDX");
		eqEmbargoDto.setTofcCofcIndicator("T");
		
		eqEmbargoDtoList = new ArrayList<>();
		eqEmbargoDtoList.add(eqEmbargoDto);
		
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

	@Test
	void testGetAllEqEmbargo() {
		when(eqEmbargoService.getAllEmbargo()).thenReturn(eqEmbargoList);
		ResponseEntity<APIResponse<List<EquipmentEmbargoDTO>>> getEmbargo = eqEmbargoController.getAllEqEmbargo();
		assertEquals(getEmbargo.getStatusCodeValue(), 200);
	}
	
	@Test
	void testGetAllEqEmbargoNoRecordsFoundException() {
		when(eqEmbargoService.getAllEmbargo()).thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<List<EquipmentEmbargoDTO>>> getEmbargo = eqEmbargoController.getAllEqEmbargo();
		assertEquals(getEmbargo.getStatusCodeValue(), 404);
	}
	
	@Test
	void testGetAllEqEmbargoException() {
		when(eqEmbargoService.getAllEmbargo()).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<List<EquipmentEmbargoDTO>>> getEmbargo = eqEmbargoController.getAllEqEmbargo();
		assertEquals(getEmbargo.getStatusCodeValue(), 500);
	}

	@Test
	void testAddEqEmbargo() {
		when(eqEmbargoMapper.EquipmentEmbargoDTOToEquipmentEmbargo(Mockito.any())).thenReturn(eqEmbargo);
		when(eqEmbargoService.addEquipEmbargo(Mockito.any(), Mockito.any())).thenReturn(eqEmbargo);
		when(eqEmbargoMapper.EquipmentEmbargoToEquipmentEmbargoDTO(Mockito.any())).thenReturn(eqEmbargoDto);
		ResponseEntity<APIResponse<EquipmentEmbargoDTO>> addedEmbargo = eqEmbargoController.addEqEmbargo(eqEmbargoDto, header);
		assertNotNull(addedEmbargo.getBody());
	}

	@Test
	void testEmbargoNoRecordsFoundException() {
		when(eqEmbargoService.addEquipEmbargo(Mockito.any(), Mockito.any())).thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<EquipmentEmbargoDTO>> addedEmbargo = eqEmbargoController.addEqEmbargo(eqEmbargoDto, header);
		assertEquals(addedEmbargo.getStatusCodeValue(),404);
		
		when(eqEmbargoService.updateEquipmentEmbargo(Mockito.any(), Mockito.any())).thenThrow(new NoRecordsFoundException());
		ResponseEntity<APIResponse<EquipmentEmbargoDTO>> updateEmbargo = eqEmbargoController.updateEquipmentEmbargo(eqEmbargoDto, header);
		assertEquals(updateEmbargo.getStatusCodeValue(),404);
		
		when(eqEmbargoService.deleteEquipmentEmbargo(Mockito.any())).thenThrow(new NoRecordsFoundException());
		ResponseEntity<List<APIResponse<EquipmentEmbargoDTO>>> deleteEmbargo = eqEmbargoController.deleteEquipmentEmbargo(eqEmbargoDtoList);
		assertEquals(deleteEmbargo.getStatusCodeValue(),500);
	}

	@Test
	void testEmbargoSizeExceedException() {
		when(eqEmbargoService.addEquipEmbargo(Mockito.any(), Mockito.any())).thenThrow(new SizeExceedException());
		ResponseEntity<APIResponse<EquipmentEmbargoDTO>> addedEmbargo = eqEmbargoController.addEqEmbargo(eqEmbargoDto, header);
		assertEquals(addedEmbargo.getStatusCodeValue(),411);
	}

	@Test
	void testEquipmentEmbargoRecordAlreadyExistsException() {
		when(eqEmbargoService.addEquipEmbargo(Mockito.any(), Mockito.any())).thenThrow(new RecordAlreadyExistsException());
		ResponseEntity<APIResponse<EquipmentEmbargoDTO>> addedEmbargo = eqEmbargoController.addEqEmbargo(eqEmbargoDto, header);
		assertEquals(addedEmbargo.getStatusCodeValue(),208);
	}

	@Test
	void testEquipmentEmbargoRecordNotAddedException() {
		when(eqEmbargoService.addEquipEmbargo(Mockito.any(), Mockito.any())).thenThrow(new RecordNotAddedException());
		ResponseEntity<APIResponse<EquipmentEmbargoDTO>> addedEmbargo = eqEmbargoController.addEqEmbargo(eqEmbargoDto, header);
		assertEquals(addedEmbargo.getStatusCodeValue(),406);
		
		when(eqEmbargoService.updateEquipmentEmbargo(Mockito.any(),Mockito.any())).thenThrow(new RecordNotAddedException());
		ResponseEntity<APIResponse<EquipmentEmbargoDTO>> updateEmbargo = eqEmbargoController.updateEquipmentEmbargo(eqEmbargoDto, header);
		assertEquals(updateEmbargo.getStatusCodeValue(),208);
	}

	@Test
	void testEquipmentEmbargoNullPointerException() {
		when(eqEmbargoService.addEquipEmbargo(Mockito.any(), Mockito.any())).thenThrow(new NullPointerException());
		ResponseEntity<APIResponse<EquipmentEmbargoDTO>> addedEmbargo = eqEmbargoController.addEqEmbargo(eqEmbargoDto, header);
		assertEquals(addedEmbargo.getStatusCodeValue(),400);

	}

	@Test
	void testEquipmentEmbargoException() {
		when(eqEmbargoService.addEquipEmbargo(Mockito.any(), Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<EquipmentEmbargoDTO>> addedEmbargo = eqEmbargoController.addEqEmbargo(eqEmbargoDto, header);
		assertEquals(addedEmbargo.getStatusCodeValue(), 500);
		
		when(eqEmbargoService.updateEquipmentEmbargo(Mockito.any(), Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<APIResponse<EquipmentEmbargoDTO>> updateEmbargo = eqEmbargoController.updateEquipmentEmbargo(eqEmbargoDto, header);
		assertEquals(updateEmbargo.getStatusCodeValue(), 500);
		
		when(eqEmbargoService.deleteEquipmentEmbargo(Mockito.any())).thenThrow(new RuntimeException());
		ResponseEntity<List<APIResponse<EquipmentEmbargoDTO>>> deleteEmbargo = eqEmbargoController.deleteEquipmentEmbargo(eqEmbargoDtoList);
		assertEquals(deleteEmbargo.getStatusCodeValue(), 500);
	}
	
	@Test
	void testUpdateEquipmentEmbargo() {
		when(eqEmbargoService.updateEquipmentEmbargo(Mockito.any(), Mockito.any())).thenReturn(eqEmbargo);
		ResponseEntity<APIResponse<EquipmentEmbargoDTO>> updateTareWeight = eqEmbargoController.updateEquipmentEmbargo(eqEmbargoDto,
				header);
		assertEquals(updateTareWeight.getStatusCodeValue(), 200);
	}
	
	@Test
	void testDeleteEquipmentEmbargo() {
		when(eqEmbargoService.deleteEquipmentEmbargo(any())).thenReturn(eqEmbargo);
		ResponseEntity<List<APIResponse<EquipmentEmbargoDTO>>> responseEntity = eqEmbargoController.deleteEquipmentEmbargo(eqEmbargoDtoList);
		assertEquals(responseEntity.getStatusCodeValue(),200);
	}

}
