package com.nscorp.obis.dto.mapper;

import com.nscorp.obis.domain.TruckerGroup;
import com.nscorp.obis.dto.TruckerGroupDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TruckerGroupMapper {

    TruckerGroupMapper INSTANCE = Mappers.getMapper(TruckerGroupMapper.class);
    TruckerGroupDTO truckerGroupToTruckerGroupDTO(TruckerGroup truckerGroup);
    TruckerGroup truckerGroupDTOToTruckerGroup(TruckerGroupDTO truckerGroupDTO);
}
