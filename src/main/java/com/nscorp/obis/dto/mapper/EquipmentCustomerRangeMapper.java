package com.nscorp.obis.dto.mapper;

import com.nscorp.obis.domain.EquipmentCustomerRange;
import com.nscorp.obis.dto.EquipmentCustomerRangeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EquipmentCustomerRangeMapper {

    EquipmentCustomerRangeMapper INSTANCE = Mappers.getMapper(EquipmentCustomerRangeMapper.class);
    EquipmentCustomerRangeDTO EquipmentCustomerRangeToEquipmentCustomerRangeDTO(EquipmentCustomerRange equipmentCustomerRange);
    EquipmentCustomerRange EquipmentCustomerRangeDTOToEquipmentCustomerRange(EquipmentCustomerRangeDTO equipmentCustomerRangeDTO);
}
