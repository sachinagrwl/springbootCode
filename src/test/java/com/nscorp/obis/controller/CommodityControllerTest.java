package com.nscorp.obis.controller;

import com.nscorp.obis.domain.Commodity;
import com.nscorp.obis.dto.CommodityDTO;
import com.nscorp.obis.dto.mapper.CommodityMapper;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.PaginatedResponse;
import com.nscorp.obis.services.CommodityService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CommodityControllerTest {

    @Mock
    CommodityService commodityService;

    @Mock
    CommodityMapper commodityMapper;

    @InjectMocks
    CommodityController commodityController;

    CommodityDTO commodityDTO;

    Commodity commodity;

    List<CommodityDTO> commodityDTOs;

    PaginatedResponse<CommodityDTO> paginatedResponse;

    String[] sort = {"commodityCodeLongName", "asc"};


    String longName;
    String hazardIndicator;
    Integer commodityCode5;
    Integer commodityCode2;
    Integer commoditySubCode;
    Integer pageNumber;
    Integer pageSize;

    Map<String, String> header;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        commodityDTO = new CommodityDTO();
        commodityDTOs = new ArrayList<CommodityDTO>();
        commodityDTO.setCommodityCode5(49210);
        commodityDTO.setCommodityCode2(7);
        commodityDTO.setCommoditySubCode(00);
        commodityDTO.setHazardIndicator("Y");
        commodityDTO.setPrimeIndicator("*");
        commodityDTO.setCommodityCodeLongName("TEST");
        commodityDTO.setCommodityCodeShortName("TEST");
        commodityDTO.setUversion("!");
        commodityDTOs.add(commodityDTO);

        commodity = new Commodity();

        paginatedResponse = new PaginatedResponse<>();
        longName = "TOXIC BY INHALATION LIQUID, WATER-REACTIVE, N.O.S. CLASS 6.1";
        hazardIndicator = "D";
        commodityCode5 = 49210;
        commodityCode2 = 7;
        commoditySubCode = 00;
        pageNumber = 0;
        pageSize = 20;

        header = new HashMap<>();
        header.put("userid", "test");
        header.put("extensionschema", "test");
    }

    @AfterEach
    void tearDown() {
        commodityDTO = null;
        commodityDTOs = null;
        commodity = null;
        paginatedResponse = null;

    }

    @Test
    void testFetchCommodity() {
        when(commodityService.fetchCommodity(Mockito.anyString(), Mockito.anyString(), Mockito.anyInt(),
                Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(), Mockito.any())).thenReturn(paginatedResponse);
        ResponseEntity<APIResponse<PaginatedResponse<CommodityDTO>>> responseEntity = commodityController
                .searchCommodity(longName, hazardIndicator, commodityCode5, commodityCode2,
                        commoditySubCode, pageNumber, pageSize, sort);
        Assertions.assertEquals(responseEntity.getStatusCodeValue(), 200);
    }

    @Test
    void testAddCommodity() throws SQLException {
        commodityDTO.setCommodityCode5(49210);
        commodityDTO.setCommodityCode2(7);
        commodityDTO.setCommoditySubCode(00);
        commodityDTO.setHazardIndicator("Y");
        commodityDTO.setPrimeIndicator("*");
        commodityDTO.setCommodityCodeLongName("TEST");
        commodityDTO.setCommodityCodeShortName("TEST");
        commodityDTO.setUversion("!");
        when(commodityService.addCommodity(commodityDTO, header))
                .thenReturn(commodityDTO);
        ResponseEntity<APIResponse<CommodityDTO>> response = commodityController
                .addCommodity(commodityDTO, header);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void testUpdateCommodity() throws SQLException {
        commodityDTO.setCommodityCode5(49210);
        commodityDTO.setCommodityCode2(7);
        commodityDTO.setCommoditySubCode(00);
        commodityDTO.setHazardIndicator("Y");
        commodityDTO.setPrimeIndicator("*");
        commodityDTO.setCommodityCodeLongName("TEST");
        commodityDTO.setCommodityCodeShortName("TEST");
        commodityDTO.setUversion("!");
        when(commodityService.updateCommodity(commodityDTO, header))
                .thenReturn(commodityDTO);
        ResponseEntity<APIResponse<CommodityDTO>> response = commodityController
                .updateCommodity(commodityDTO, header);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void testInvalidDataExceptionForAdd() throws SQLException {
        when(commodityService.addCommodity(commodityDTO, header))
                .thenThrow(InvalidDataException.class);
        ResponseEntity<APIResponse<CommodityDTO>> exception = commodityController
                .addCommodity(commodityDTO, header);
        Assertions.assertEquals(exception.getStatusCodeValue(), 400);
    }
    @Test
    void testInvalidDataExceptionForUpdate() throws SQLException {
        when(commodityService.updateCommodity(commodityDTO, header))
                .thenThrow(InvalidDataException.class);
        ResponseEntity<APIResponse<CommodityDTO>> exception = commodityController
                .updateCommodity(commodityDTO, header);
        Assertions.assertEquals(exception.getStatusCodeValue(), 400);
    }
    @Test
    void testNoRecordsExceptionForUpdate() throws SQLException {
        when(commodityService.updateCommodity(commodityDTO, header))
                .thenThrow(NoRecordsFoundException.class);
        ResponseEntity<APIResponse<CommodityDTO>> exception = commodityController
                .updateCommodity(commodityDTO, header);
        Assertions.assertEquals(exception.getStatusCodeValue(), 404);
    }


    @Test
    void testNoRecordsException() {
        when(commodityService.fetchCommodity(Mockito.anyString(), Mockito.anyString(), Mockito.anyInt(),
                Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(), Mockito.any())).thenThrow(NoRecordsFoundException.class);
        ResponseEntity<APIResponse<PaginatedResponse<CommodityDTO>>> exception = commodityController
                .searchCommodity(longName, hazardIndicator, commodityCode5, commodityCode2,
                        commoditySubCode, pageNumber, pageSize, sort);
        Assertions.assertEquals(exception.getStatusCodeValue(), 404);
    }

    @Test
    void testInvalidDataException() {
        when(commodityService.fetchCommodity(Mockito.anyString(), Mockito.anyString(), Mockito.anyInt(),
                Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(), Mockito.any())).thenThrow(InvalidDataException.class);
        ResponseEntity<APIResponse<PaginatedResponse<CommodityDTO>>> exception = commodityController
                .searchCommodity(longName, hazardIndicator, commodityCode5, commodityCode2,
                        commoditySubCode, pageNumber, pageSize, sort);
        assertEquals(exception.getStatusCodeValue(), 400);
    }


    @Test
    void testFetchCustomersParamsError() {
        longName = null;
        hazardIndicator = null;
        commodityCode5 = null;
        commodityCode2 = null;
        commoditySubCode = null;
        when(commodityService.fetchCommodity(Mockito.anyString(), Mockito.anyString(), Mockito.anyInt(),
                Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(), Mockito.any()))
                .thenReturn(paginatedResponse);
        ResponseEntity<APIResponse<PaginatedResponse<CommodityDTO>>> response = commodityController.
                searchCommodity(longName, hazardIndicator, commodityCode5, commodityCode2,
                        commoditySubCode, pageNumber, pageSize, sort);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }


    @Test
    void testFetchCommodityException() {
        when(commodityService.fetchCommodity(Mockito.anyString(), Mockito.anyString(), Mockito.anyInt(),
                Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(), Mockito.any())).thenThrow(RuntimeException.class);
        ResponseEntity<APIResponse<PaginatedResponse<CommodityDTO>>> exception2 = commodityController
                .searchCommodity(longName, hazardIndicator, commodityCode5, commodityCode2,
                        commoditySubCode, pageNumber, pageSize, sort);
        Assertions.assertEquals(exception2.getStatusCodeValue(), 500);
    }

    @Test
    void testAddCommodityException() throws SQLException {
        when(commodityService.addCommodity(commodityDTO,header)).thenThrow(RuntimeException.class);
        ResponseEntity<APIResponse<CommodityDTO>> exception = commodityController
                .addCommodity(commodityDTO, header);
        Assertions.assertEquals(exception.getStatusCodeValue(), 500);
    }

    @Test
    void testUpdateCommodityException() throws SQLException {
        when(commodityService.updateCommodity(commodityDTO,header)).thenThrow(RuntimeException.class);
        ResponseEntity<APIResponse<CommodityDTO>> exception = commodityController
                .updateCommodity(commodityDTO, header);
        Assertions.assertEquals(exception.getStatusCodeValue(), 500);
    }

    @Test
    void testDeleteCommodity() {
        when(commodityMapper.CommodityDTOToCommodity(Mockito.any())).thenReturn(commodity);
        when(commodityService.deleteCommodity(Mockito.any(),Mockito.any())).thenReturn(commodity);
        when(commodityMapper.CommodityToCommodityDTO(Mockito.any())).thenReturn(commodityDTO);
        ResponseEntity<List<APIResponse<CommodityDTO>>> responseEntity = commodityController.deleteCommodity(commodityDTOs,header);
        assertEquals(responseEntity.getStatusCodeValue(),200);
    }

    @Test
    void testRestoreCommodity() {
        commodity.setExpiredDateTime(new Timestamp(System.currentTimeMillis()));
        commodityDTO.setExpiredDateTime(new Timestamp(System.currentTimeMillis()));
        when(commodityMapper.CommodityDTOToCommodity(Mockito.any())).thenReturn(commodity);
        when(commodityService.restoreCommodity(Mockito.any(),Mockito.any())).thenReturn(commodity);
        when(commodityMapper.CommodityToCommodityDTO(Mockito.any())).thenReturn(commodityDTO);
        ResponseEntity<List<APIResponse<CommodityDTO>>> responseEntity = commodityController.restoreCommodity(commodityDTOs,header);
        assertEquals(responseEntity.getStatusCodeValue(),200);
    }
    @Test
    @SuppressWarnings("unchecked")
    void testDeleteCommodityDTOList(){
        ResponseEntity<List<APIResponse<CommodityDTO>>> responseEntity = commodityController.deleteCommodity(Collections.EMPTY_LIST,header);
        assertEquals(responseEntity.getStatusCodeValue(),500);
    }

    @Test
    @SuppressWarnings("unchecked")
    void testRestoreCommodityDTOList(){
        ResponseEntity<List<APIResponse<CommodityDTO>>> responseEntity = commodityController.restoreCommodity(Collections.EMPTY_LIST,header);
        assertEquals(responseEntity.getStatusCodeValue(),500);
    }
    @Test
    void testDeleteCommodityException(){
        when(commodityService.deleteCommodity(Mockito.any(),Mockito.any())).thenThrow(new RuntimeException());
        ResponseEntity<List<APIResponse<CommodityDTO>>> responseEntity = commodityController.deleteCommodity(commodityDTOs,header);
        assertEquals(responseEntity.getStatusCodeValue(),500);
    }

    @Test
    void testDeleteCommodityNoRecordFoundException(){
        when(commodityService.deleteCommodity(Mockito.any(),Mockito.any())).thenThrow(new NoRecordsFoundException());
        ResponseEntity<List<APIResponse<CommodityDTO>>> responseEntity = commodityController.deleteCommodity(commodityDTOs,header);
        assertEquals(responseEntity.getStatusCodeValue(),500);
    }

    @Test
    void testDeleteCommodityInvalidDataException(){
        when(commodityService.deleteCommodity(Mockito.any(),Mockito.any())).thenThrow(new InvalidDataException());
        ResponseEntity<List<APIResponse<CommodityDTO>>> responseEntity = commodityController.deleteCommodity(commodityDTOs,header);
        assertEquals(responseEntity.getStatusCodeValue(),500);
    }

    @Test
    void testRestoreCommodityException(){
        when(commodityService.restoreCommodity(Mockito.any(),Mockito.any())).thenThrow(new RuntimeException());
        ResponseEntity<List<APIResponse<CommodityDTO>>> responseEntity = commodityController.restoreCommodity(commodityDTOs,header);
        assertEquals(responseEntity.getStatusCodeValue(),500);
    }

    @Test
    void testRestoreCommodityNoRecordFoundException(){
        when(commodityService.restoreCommodity(Mockito.any(),Mockito.any())).thenThrow(new NoRecordsFoundException());
        ResponseEntity<List<APIResponse<CommodityDTO>>> responseEntity = commodityController.restoreCommodity(commodityDTOs,header);
        assertEquals(responseEntity.getStatusCodeValue(),500);
    }

    @Test
    void testRestoreCommodityInvalidDataException(){
        when(commodityService.restoreCommodity(Mockito.any(),Mockito.any())).thenThrow(new InvalidDataException());
        ResponseEntity<List<APIResponse<CommodityDTO>>> responseEntity = commodityController.restoreCommodity(commodityDTOs,header);
        assertEquals(responseEntity.getStatusCodeValue(),500);
    }


}
