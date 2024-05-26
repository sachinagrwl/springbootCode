package com.nscorp.obis.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.domain.PoolCarStorageExempt;
import com.nscorp.obis.dto.PoolCarStorageExemptDTO;

@Mapper(componentModel = CommonConstants.MAPPER_COMPONENT_MODEL)
public interface PoolCarStorageExemptMapper {

    PoolCarStorageExemptMapper INSTANCE = Mappers.getMapper(PoolCarStorageExemptMapper.class);

    @Mapping(target = "pool.controllers" , ignore = true)
    @Mapping(target = "pool.equipmentRanges" , ignore = true)
    @Mapping(target = "pool.terminals" , ignore = true)
    @Mapping(target = "pool.customers" , ignore = true)
    @Mapping(target = "pool.truckerGroup" , ignore = true)
    PoolCarStorageExemptDTO PoolCarStorageExemptToPoolCarStorageExemptDTO(PoolCarStorageExempt poolCarStorageExempt);

    PoolCarStorageExempt PoolCarStorageExemptDTOToPoolCarStorageExempt(PoolCarStorageExemptDTO poolCarStorageExemptDTO);
}
