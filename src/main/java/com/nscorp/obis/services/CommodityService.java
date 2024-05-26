package com.nscorp.obis.services;


import com.nscorp.obis.domain.Commodity;
import com.nscorp.obis.dto.CommodityDTO;
import com.nscorp.obis.response.data.PaginatedResponse;


import javax.validation.Valid;
import java.sql.SQLException;
import java.util.Map;

public interface CommodityService {

    PaginatedResponse<CommodityDTO> fetchCommodity(String longName, String hazardIndicator,
                                                   Integer commodityCode5, Integer commodityCode2, Integer commoditySubCode, Integer pageSize, Integer pageNumber, String[] sort);

    CommodityDTO addCommodity(
            @Valid CommodityDTO commodityDTO, Map<String, String> headers) throws SQLException;

    CommodityDTO updateCommodity(@Valid CommodityDTO commodityDTO, Map<String, String> headers);

    Commodity deleteCommodity(@Valid Commodity commodity,Map<String, String> headers);

    Commodity restoreCommodity(@Valid Commodity commodity,Map<String, String> headers);

}
