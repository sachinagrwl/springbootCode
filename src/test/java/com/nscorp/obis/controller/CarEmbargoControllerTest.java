package com.nscorp.obis.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
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

import com.nscorp.obis.domain.CarEmbargo;
import com.nscorp.obis.domain.Station;
import com.nscorp.obis.dto.CarEmbargoDTO;
import com.nscorp.obis.dto.mapper.CarEmbargoMapper;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordAlreadyExistsException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.services.CarEmbargoService;

class CarEmbargoControllerTest {

    @Mock
    CarEmbargoService carEmbargoService;

    @Mock
    CarEmbargoMapper carEmbargoMapper;

    @InjectMocks
    CarEmbargoController carEmbargoController;

    CarEmbargo carEmbargo;
    CarEmbargoDTO carEmbargoDTO;
    List<CarEmbargo> carEmbargoList;
    List<CarEmbargoDTO> carEmbargoDTOList;
    Station station;
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
    void getAllCarEmbargos() {
        when(carEmbargoService.getAllCarEmbargo()).thenReturn(carEmbargoList);
        ResponseEntity<APIResponse<List<CarEmbargoDTO>>> getCarEmbargo = carEmbargoController.getAllCarEmbargos();
        assertEquals(getCarEmbargo.getStatusCodeValue(),200);
    }

    @Test
    void getAllCarEmbargosException() {
        when(carEmbargoService.getAllCarEmbargo()).thenThrow(new RuntimeException());
        when(carEmbargoService.insertCarEmbargo(Mockito.any(),Mockito.any())).thenThrow(new RuntimeException());
        when(carEmbargoService.updateCarEmbargo(Mockito.any(),Mockito.any())).thenThrow(new RuntimeException());
        when(carEmbargoService.deleteCarEmbargo(Mockito.any())).thenThrow(new RuntimeException());

        ResponseEntity<APIResponse<List<CarEmbargoDTO>>> getCarEmbargo = carEmbargoController.getAllCarEmbargos();
        ResponseEntity<APIResponse<CarEmbargoDTO>> addCarEmbargo = carEmbargoController.insertCarEmbargos(Mockito.any(),Mockito.any());
        ResponseEntity<APIResponse<CarEmbargoDTO>> updateCarEmbargo = carEmbargoController.updateCarEmbargo(Mockito.any(),Mockito.any());
        ResponseEntity<List<APIResponse<CarEmbargoDTO>>> deleteCarEmbargo = carEmbargoController.deleteCarEmbargo(carEmbargoDTOList);

        assertEquals(getCarEmbargo.getStatusCodeValue(),500);
        assertEquals(addCarEmbargo.getStatusCodeValue(),500);
        assertEquals(updateCarEmbargo.getStatusCodeValue(),500);
        assertEquals(deleteCarEmbargo.getStatusCodeValue(),500);
    }

    @Test
    void getAllCarEmbargosNoRecordsFoundException() {
        when(carEmbargoService.getAllCarEmbargo()).thenThrow(new NoRecordsFoundException());
        when(carEmbargoService.insertCarEmbargo(Mockito.any(),Mockito.any())).thenThrow(new NoRecordsFoundException());
        when(carEmbargoService.updateCarEmbargo(Mockito.any(),Mockito.any())).thenThrow(new NoRecordsFoundException());

        ResponseEntity<APIResponse<List<CarEmbargoDTO>>> getCarEmbargo = carEmbargoController.getAllCarEmbargos();
        ResponseEntity<APIResponse<CarEmbargoDTO>> addCarEmbargo = carEmbargoController.insertCarEmbargos(Mockito.any(),Mockito.any());
        ResponseEntity<APIResponse<CarEmbargoDTO>> updateCarEmbargo = carEmbargoController.updateCarEmbargo(Mockito.any(),Mockito.any());

        assertEquals(getCarEmbargo.getStatusCodeValue(),404);
        assertEquals(addCarEmbargo.getStatusCodeValue(),404);
        assertEquals(updateCarEmbargo.getStatusCodeValue(),404);
    }

    @Test
    void getAllCarEmbargosRecordAlreadyExistsException() {
        when(carEmbargoService.insertCarEmbargo(Mockito.any(),Mockito.any())).thenThrow(new RecordAlreadyExistsException());
        ResponseEntity<APIResponse<CarEmbargoDTO>> addCarEmbargo = carEmbargoController.insertCarEmbargos(Mockito.any(),Mockito.any());
        assertEquals(addCarEmbargo.getStatusCodeValue(),208);
    }

    @Test
    void CarEmbargoInvalidDataException() {
        when(carEmbargoService.updateCarEmbargo(Mockito.any(),Mockito.any())).thenThrow(new InvalidDataException());
        ResponseEntity<APIResponse<CarEmbargoDTO>> updateCarEmbargo = carEmbargoController.updateCarEmbargo(Mockito.any(),Mockito.any());
        assertEquals(updateCarEmbargo.getStatusCodeValue(),406);
    }

    @Test
    void CarEmbargoRecordNotAddedException() {
        when(carEmbargoService.updateCarEmbargo(Mockito.any(),Mockito.any())).thenThrow(new RecordNotAddedException());
        ResponseEntity<APIResponse<CarEmbargoDTO>> updateCarEmbargo = carEmbargoController.updateCarEmbargo(Mockito.any(),Mockito.any());
        assertEquals(updateCarEmbargo.getStatusCodeValue(),406);
    }

    @Test
    void CarEmbargoNullPointerException() {
        when(carEmbargoService.updateCarEmbargo(Mockito.any(),Mockito.any())).thenThrow(new NullPointerException());
        ResponseEntity<APIResponse<CarEmbargoDTO>> updateCarEmbargo = carEmbargoController.updateCarEmbargo(Mockito.any(),Mockito.any());
        assertEquals(updateCarEmbargo.getStatusCodeValue(),400);
    }

    @Test
    void insertCarEmbargos() {
        when(carEmbargoMapper.carEmbargoDTOToCarEmbargo(Mockito.any())).thenReturn(carEmbargo);
        when(carEmbargoService.insertCarEmbargo(Mockito.any(),Mockito.any())).thenReturn(carEmbargo);
        when(carEmbargoMapper.carEmbargoToCarEmbargoDTO(Mockito.any())).thenReturn(carEmbargoDTO);
        ResponseEntity<APIResponse<CarEmbargoDTO>> addCarEmbargo = carEmbargoController.insertCarEmbargos(Mockito.any(),Mockito.any());
        assertNotNull(addCarEmbargo.getBody());
    }

    @Test
    void updateCarEmbargos() {
        when(carEmbargoMapper.carEmbargoDTOToCarEmbargo(Mockito.any())).thenReturn(carEmbargo);
        when(carEmbargoService.updateCarEmbargo(Mockito.any(),Mockito.any())).thenReturn(carEmbargo);
        when(carEmbargoMapper.carEmbargoToCarEmbargoDTO(Mockito.any())).thenReturn(carEmbargoDTO);
        ResponseEntity<APIResponse<CarEmbargoDTO>> updateCarEmbargo = carEmbargoController.updateCarEmbargo(Mockito.any(),Mockito.any());
        assertNotNull(updateCarEmbargo.getBody());
    }

    @Test
    void deleteCarEmbargos() {
        when(carEmbargoMapper.carEmbargoDTOToCarEmbargo(Mockito.any())).thenReturn(carEmbargo);
        carEmbargoService.deleteCarEmbargo(Mockito.any());
        when(carEmbargoMapper.carEmbargoToCarEmbargoDTO(Mockito.any())).thenReturn(carEmbargoDTO);
        ResponseEntity<List<APIResponse<CarEmbargoDTO>>> deleteList = carEmbargoController.deleteCarEmbargo(carEmbargoDTOList);
        assertEquals(deleteList.getStatusCodeValue(),200);
    }

    @SuppressWarnings("unchecked")
	@Test
    void testDeleteCarEmbargoEmptyDTOList(){
        ResponseEntity<List<APIResponse<CarEmbargoDTO>>> responseEntity = carEmbargoController.deleteCarEmbargo(Collections.EMPTY_LIST);
        assertEquals(responseEntity.getStatusCodeValue(),500);
    }
}