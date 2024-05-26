package com.nscorp.obis.services;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.common.SpecificationGenerator;
import com.nscorp.obis.common.UserId;
import com.nscorp.obis.domain.Commodity;
import com.nscorp.obis.domain.SortFilter;
import com.nscorp.obis.dto.CommodityDTO;
import com.nscorp.obis.dto.mapper.CommodityMapper;
import com.nscorp.obis.exception.InvalidDataException;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.repository.CommodityRepository;
import com.nscorp.obis.response.data.PaginatedResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class CommodityServiceImpl implements CommodityService {

    @Autowired
    CommodityRepository commodityRepository;

    @Autowired
    SpecificationGenerator specificationGenerator;


    @Override
    public PaginatedResponse<CommodityDTO> fetchCommodity(String longName, String hazardIndicator, Integer commodityCode5,
                                                          Integer commodityCode2, Integer commoditySubCode, Integer pageSize, Integer pageNumber, String[] sort) {
        log.info("CommodityServiceImpl : fetchCommodity : Method Starts");

        Page<Commodity> page = commodityRepository.findAll(
                specificationGenerator.commoditySpecification(longName,
                        hazardIndicator, commodityCode5, commodityCode2, commoditySubCode),
                PageRequest.of(pageNumber, pageSize, Sort.by(SortFilter.sortOrder(sort))));

        if (page.isEmpty()) {
            throw new NoRecordsFoundException("No records found for given combination");
        }

        List<CommodityDTO> commodityDTOs = new ArrayList<CommodityDTO>();
        page.stream().forEach((temp) -> {
            commodityDTOs.add(CommodityMapper.INSTANCE.CommodityToCommodityDTO(temp));
        });
        PaginatedResponse<CommodityDTO> paginatedResponse = PaginatedResponse.of(commodityDTOs, page);
        log.info("CommodityServiceImpl : fetchCommodity : Method Ends");
        return paginatedResponse;
    }

    @Override
    public CommodityDTO addCommodity(@Valid CommodityDTO commodityDTO, Map<String, String> headers) throws SQLException {
        log.info("addCommodity : Method Starts");
        UserId.headerUserID(headers);

        Commodity commodity = new Commodity();
        log.info("Entity :" + commodity);
        log.info("DTO :" + commodityDTO);

        if (commodityRepository.existsByCommodityCode5AndCommodityCode2AndCommoditySubCode
                (commodityDTO.getCommodityCode5(), commodityDTO.getCommodityCode2(), commodityDTO.getCommoditySubCode())) {
            throw new InvalidDataException("Record already exist with given Commodity Code STCC-5, STCC-2 and Subcode");
        }
        if (commodityDTO.getExpiredDateTime() != null) {
            throw new InvalidDataException("Expiry date cant be added");
        }
        if (headers.get(CommonConstants.EXTENSION_SCHEMA) != null) {
            commodity.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
        } else {
            commodity.setUpdateExtensionSchema(headers.get("IMS02623"));
        }
        commodity.setUversion("!");
        String userId = headers.get(CommonConstants.USER_ID);
        commodity.setCreateUserId(userId.toUpperCase());
        commodity.setUpdateUserId(userId.toUpperCase());
        commodity.setCommodityCode5(commodityDTO.getCommodityCode5());
        commodity.setCommodityCode2(commodityDTO.getCommodityCode2());
        commodity.setCommoditySubCode(commodityDTO.getCommoditySubCode());
        commodity.setCommodityCodeLongName(commodityDTO.getCommodityCodeLongName());
        commodity.setCommodityCodeShortName(commodityDTO.getCommodityCodeShortName());
        commodity.setHazardIndicator(commodityDTO.getHazardIndicator());
        commodity.setPrimeIndicator(commodityDTO.getPrimeIndicator());
        commodity = commodityRepository.save(commodity);
        commodityDTO = CommodityMapper.INSTANCE
                .CommodityToCommodityDTO(commodity);
        log.info("addCommodity : Method Ends");
        return commodityDTO;
    }

    @Override
    public CommodityDTO
    updateCommodity(CommodityDTO commodityDTO, Map<String, String> headers) {

        Commodity commodity;
        Optional<Commodity> commodityOptional = commodityRepository.findByCommodityCode5AndCommodityCode2AndCommoditySubCode
                (commodityDTO.getCommodityCode5(), commodityDTO.getCommodityCode2(), commodityDTO.getCommoditySubCode());
        if (!commodityOptional.isPresent())
            throw new NoRecordsFoundException("No Record Found For Given Commodity's code and sub code");

        commodity = commodityOptional.get();

        if (commodity.getExpiredDateTime() != null) {
            throw new InvalidDataException(
                    "Record has been marked for deletion, no updates allowed unless record is restored.");
        }

        if (commodityDTO.getExpiredDateTime() != null) {
            throw new InvalidDataException("Expiry date cant be edited");
        }

        UserId.headerUserID(headers);
        String userId = headers.get(CommonConstants.USER_ID);
        commodity.setUpdateUserId(userId.toUpperCase());
        commodity.setCommodityCodeLongName(commodityDTO.getCommodityCodeLongName());
        commodity.setCommodityCodeShortName(commodityDTO.getCommodityCodeShortName());
        commodity.setHazardIndicator(commodityDTO.getHazardIndicator());
        commodity.setPrimeIndicator(commodityDTO.getPrimeIndicator());
        commodity.setUversion(Character.toString((char) ((((int) commodity.getUversion().charAt(0) - 32) % 94) + 33)));
        if (headers.get("extensionschema") != null)
            commodity.setUpdateExtensionSchema(headers.get("extensionschema"));

        commodity = commodityRepository.save(commodity);
        commodityDTO = CommodityMapper.INSTANCE.CommodityToCommodityDTO(commodity);
        return commodityDTO;
    }

    @Override
    public Commodity deleteCommodity(Commodity commodity, Map<String, String> headers) {
        Commodity existingCommodity = getExistingCommodity(commodity, headers);
        if (existingCommodity.getExpiredDateTime() != null) {
            throw new InvalidDataException(
                    "Record is already marked for deletion. See Expired date.");
        }
        existingCommodity.setExpiredDateTime(new Timestamp(System.currentTimeMillis()));
        existingCommodity.setUversion(Character.toString((char) ((((int) existingCommodity.getUversion().charAt(0) - 32) % 94) + 33)));
        existingCommodity = commodityRepository.save(existingCommodity);
        return existingCommodity;
    }

    @Override
    public Commodity restoreCommodity(Commodity commodity, Map<String, String> headers) {
        Commodity existingCommodity = getExistingCommodity(commodity, headers);

        if (existingCommodity.getExpiredDateTime() == null) {
            throw new InvalidDataException(
                    "Record is not currently marked for deletion.");
        }
        existingCommodity.setExpiredDateTime(null);
        existingCommodity.setUversion(Character.toString((char) ((((int) existingCommodity.getUversion().charAt(0) - 32) % 94) + 33)));
        existingCommodity = commodityRepository.save(existingCommodity);
        return existingCommodity;
    }

    private Commodity getExistingCommodity(Commodity commodity, Map<String, String> headers) {
        Commodity existingCommodity;
        Optional<Commodity> commodityOptional = commodityRepository.findByCommodityCode5AndCommodityCode2AndCommoditySubCode
                (commodity.getCommodityCode5(), commodity.getCommodityCode2(), commodity.getCommoditySubCode());

        if (!commodityOptional.isPresent())
            throw new NoRecordsFoundException("No Record Found For Given Commodity's code and sub code");

        existingCommodity = commodityOptional.get();
        /* Audit fields */
        UserId.headerUserID(headers);
        String userId = headers.get(CommonConstants.USER_ID);
        existingCommodity.setUpdateUserId(userId.toUpperCase());
        existingCommodity.setUpdateExtensionSchema(headers.get(CommonConstants.EXTENSION_SCHEMA));
        if (StringUtils.isNotEmpty(existingCommodity.getUversion())) {
            existingCommodity.setUversion(
                    Character.toString((char) ((((int) existingCommodity.getUversion().charAt(0) - 32) % 94) + 33)));
        }
        return existingCommodity;
    }
}
