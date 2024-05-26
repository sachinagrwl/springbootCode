package com.nscorp.obis.dto.mapper;

import com.nscorp.obis.domain.PoolEquipmentConflictRange;
import com.nscorp.obis.dto.PoolEquipmentConflictRangeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PoolEquipmentConflictRangeMapper {
    PoolEquipmentConflictRangeMapper INSTANCE = Mappers.getMapper(PoolEquipmentConflictRangeMapper.class);
    PoolEquipmentConflictRangeDTO PoolEquipmentConflictRangeToPoolEquipmentConflictRangeDTO(PoolEquipmentConflictRange poolConflict);
    PoolEquipmentConflictRange PoolEquipmentConflictRangeDTOToPoolEquipmentConflictRange(PoolEquipmentConflictRangeDTO poolConflictDto);
}
