package com.nscorp.obis.dto.mapper;

import com.nscorp.obis.domain.PoolEquipmentRange;
import com.nscorp.obis.dto.PoolEquipmentRangeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PoolEquipmentRangeMapper {
    PoolEquipmentRangeMapper INSTANCE = Mappers.getMapper(PoolEquipmentRangeMapper.class);
    PoolEquipmentRangeDTO poolEquipmentRangeToPoolEquipmentRangeDto(PoolEquipmentRange poolEquipmentRange);
    PoolEquipmentRange poolEquipmentRangeDtoToPoolEquipmentRange(PoolEquipmentRangeDTO poolEquipmentRangeDTO);
}
