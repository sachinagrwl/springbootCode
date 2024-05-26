package com.nscorp.obis.services;

import com.nscorp.obis.common.SpecificationGenerator;
import com.nscorp.obis.domain.Commodity;
import com.nscorp.obis.dto.CommodityDTO;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.repository.CommodityRepository;
import com.nscorp.obis.response.data.PaginatedResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.domain.Specification;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class CommodityServiceTest {

    @Mock
    CommodityRepository commodityRepository;

    @Mock
    SpecificationGenerator specificationGenerator;

    @InjectMocks
    CommodityServiceImpl commodityService;

    CommodityDTO commodityDTO;

    Commodity commodity;

    List<CommodityDTO> commodityDtos;

    List<Commodity> commodities;

    Optional<Commodity> optional;


    Page<Commodity> page;

    String[] sort = {"commodityCodeLongName", "asc"};

    String longName;
    String hazardIndicator;
    Integer commodityCode5;
    Integer commodityCode2;
    Integer commoditySubCode;
    Integer pageNumber;
    Integer pageSize;

    Specification<Commodity> specification;

    Map<String, String> headers;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        commodityDTO = new CommodityDTO();
        commodityDtos = new ArrayList<CommodityDTO>();
        commodity = new Commodity();
        commodities = new ArrayList<>();
        commodityDTO.setCommodityCode5(49210);
        commodityDTO.setCommodityCode2(7);
        commodityDTO.setCommoditySubCode(00);
        commodityDTO.setHazardIndicator("Y");
        commodityDTO.setPrimeIndicator("*");
//        commodityDTO.setExpiredDateTime(new Timestamp(01-02-2008));
        commodityDTO.setCommodityCodeLongName("TEST");
        commodityDTO.setCommodityCodeShortName("TEST");
        commodityDTO.setUversion("!");
        commodityDtos.add(commodityDTO);
        commodity.setCommodityCode5(49210);
        commodity.setCommodityCode2(7);
        commodity.setCommoditySubCode(00);
        commodity.setHazardIndicator("Y");
        commodity.setPrimeIndicator("*");
//        commodity.setExpiredDateTime(new Timestamp(01-02-2008));
        commodity.setCommodityCodeLongName("TEST");
        commodity.setCommodityCodeShortName("TEST");
        commodity.setUversion("!");

        commodities.add(commodity);
        page = new PageImpl(commodities);
        longName = "TOXIC BY INHALATION LIQUID, WATER-REACTIVE, N.O.S. CLASS 6.1";
        hazardIndicator = "D";
        commodityCode5 = 49210;
        commodityCode2 = 7;
        commoditySubCode = 00;
        pageNumber = 0;
        pageSize = 20;
        specification = new SpecificationGenerator().commoditySpecification(longName, hazardIndicator, commodityCode5, commodityCode2,
                commoditySubCode);
        headers = new HashMap<>();
        headers.put("userid", "test");
        headers.put("extensionschema", "test");
        optional = Optional.of(commodity);

    }

    @AfterEach
    void tearDown() {
        commodityDTO = null;
        commodityDtos = null;
        specification = null;
        page = null;
    }

    @Test
    void testFetchCommodity() {
        when(specificationGenerator.commoditySpecification(longName, hazardIndicator, commodityCode5, commodityCode2,
                commoditySubCode)).thenReturn(specification);
        when(commodityRepository.findAll(Mockito.any(Specification.class), Mockito.any())).thenReturn(page);
        PaginatedResponse<CommodityDTO> response = commodityService.fetchCommodity(longName, hazardIndicator, commodityCode5, commodityCode2,
                commoditySubCode, pageSize, pageNumber, sort);
        assertNotNull(response);
        commodities = new ArrayList<>();
        page = new PageImpl(commodities);
        when(specificationGenerator.commoditySpecification(longName, hazardIndicator, commodityCode5, commodityCode2,
                commoditySubCode)).thenReturn(specification);
        when(commodityRepository.findAll(Mockito.any(Specification.class), Mockito.any())).thenReturn(page);
        assertThrows(NoRecordsFoundException.class, () -> commodityService.fetchCommodity(longName, hazardIndicator, commodityCode5, commodityCode2,
                commoditySubCode, pageSize, pageNumber, sort));
    }

    @Test
    void testAddCommodity() throws SQLException {
        commodityDTO.setExpiredDateTime(null);
        commodity.setExpiredDateTime(null);
        when(commodityRepository.existsByCommodityCode5AndCommodityCode2AndCommoditySubCode(
                Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(false);
        when(commodityRepository.save(Mockito.any())).thenReturn(commodity);
        CommodityDTO added = commodityService.addCommodity(commodityDTO, headers);
        assertEquals(commodity.getCommodityCode5(), added.getCommodityCode5());

        when(commodityRepository.existsByCommodityCode5AndCommodityCode2AndCommoditySubCode(
                Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(true);
        headers.put("extensionschema", null);
        assertThrows(InvalidDataException.class, () -> commodityService.addCommodity(commodityDTO, headers));

        when(commodityRepository.existsByCommodityCode5AndCommodityCode2AndCommoditySubCode(
                Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(false);
        commodityDTO.setExpiredDateTime(new Timestamp(01 - 02 - 2008));
        commodity.setExpiredDateTime(new Timestamp(01 - 02 - 2008));
        assertThrows(InvalidDataException.class, () -> commodityService.addCommodity(commodityDTO, headers));

    }

    @Test
    void testUpdateCommodity() {
        when(commodityRepository.findByCommodityCode5AndCommodityCode2AndCommoditySubCode(Mockito.anyInt(),
                Mockito.anyInt(), Mockito.anyInt())).thenReturn(optional);
        when(commodityRepository.save(Mockito.any())).thenReturn(commodity);
        CommodityDTO response = commodityService.updateCommodity(commodityDTO, headers);
        assertEquals(response.getCommodityCode5(), commodityDTO.getCommodityCode5());
    }

    @Test
    void testUpdateException() {
        NoRecordsFoundException exception1 = assertThrows(NoRecordsFoundException.class,
                () -> when(commodityService.updateCommodity(commodityDTO, headers)));
        assertEquals("No Record Found For Given Commodity's code and sub code", exception1.getMessage());

        when(commodityRepository.findByCommodityCode5AndCommodityCode2AndCommoditySubCode(Mockito.anyInt(),
                Mockito.anyInt(), Mockito.anyInt())).thenReturn(optional);
        commodity = optional.get();
        commodity.setExpiredDateTime(new Timestamp(01 - 02 - 2008));
        InvalidDataException exception2 = assertThrows(InvalidDataException.class,
                () -> when(commodityService.updateCommodity(commodityDTO, headers)));
        assertEquals("Record has been marked for deletion, no updates allowed unless record is restored.",exception2.getMessage());

        commodity.setExpiredDateTime(null);
        commodityDTO.setExpiredDateTime(new Timestamp(01 - 02 - 2008));
        InvalidDataException exception3 = assertThrows(InvalidDataException.class,
                () -> when(commodityService.updateCommodity(commodityDTO, headers)));
        assertEquals("Expiry date cant be edited",exception3.getMessage());



    }

    @Test
    void testDeleteCommodity() {
        when(commodityRepository.findByCommodityCode5AndCommodityCode2AndCommoditySubCode(Mockito.anyInt(),Mockito.anyInt(),Mockito.anyInt())).thenReturn(optional);
        commodityService.deleteCommodity(commodity,headers);
        Assertions.assertEquals(commodityDTO.getCommodityCode5(),commodity.getCommodityCode5());
    }

    @Test
    void testDeleteCommodityNoRecordsFoundException() {
        when(commodityRepository.findByCommodityCode5AndCommodityCode2AndCommoditySubCode(Mockito.anyInt(),Mockito.anyInt(),Mockito.anyInt())).thenReturn(Optional.empty());
        assertThrows(NoRecordsFoundException.class, () -> commodityService.deleteCommodity(commodity,headers));
    }

    @Test
    void testDeleteCommodityInvalidDataException() {
        when(commodityRepository.findByCommodityCode5AndCommodityCode2AndCommoditySubCode(Mockito.anyInt(),Mockito.anyInt(),Mockito.anyInt())).thenReturn(optional);
        commodity.setExpiredDateTime(new Timestamp(System.currentTimeMillis()));
        assertThrows(InvalidDataException.class, () -> commodityService.deleteCommodity(commodity,headers));
    }
    @Test
    void testRestoreCommodity() {
        when(commodityRepository.findByCommodityCode5AndCommodityCode2AndCommoditySubCode(Mockito.anyInt(),Mockito.anyInt(),Mockito.anyInt())).thenReturn(optional);
        commodity.setExpiredDateTime(new Timestamp(System.currentTimeMillis()));
        commodityService.restoreCommodity(commodity,headers);
        Assertions.assertEquals(commodityDTO.getCommodityCode5(),commodity.getCommodityCode5());
    }

    @Test
    void testRestoreCommodityNoRecordsFoundException() {
        when(commodityRepository.findByCommodityCode5AndCommodityCode2AndCommoditySubCode(Mockito.anyInt(),Mockito.anyInt(),Mockito.anyInt())).thenReturn(Optional.empty());
        assertThrows(NoRecordsFoundException.class, () -> commodityService.restoreCommodity(commodity,headers));
    }

    @Test
    void testRestoreCommodityInvalidDataException() {
        when(commodityRepository.findByCommodityCode5AndCommodityCode2AndCommoditySubCode(Mockito.anyInt(),Mockito.anyInt(),Mockito.anyInt())).thenReturn(optional);
        commodity.setExpiredDateTime(null);
        assertThrows(InvalidDataException.class, () -> commodityService.restoreCommodity(commodity,headers));
    }


}


