package com.nscorp.obis.dto.mapper;

import com.nscorp.obis.domain.Commodity;
import com.nscorp.obis.dto.CommodityDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CommodityMapper {

    CommodityMapper INSTANCE = Mappers.getMapper(CommodityMapper.class);

    CommodityDTO CommodityToCommodityDTO(Commodity commodity);

    Commodity CommodityDTOToCommodity(CommodityDTO commodityDTO);

}
