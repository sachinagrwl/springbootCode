package com.nscorp.obis.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.nscorp.obis.domain.EquipmentCustomerLesseeRange;
import com.nscorp.obis.dto.EquipmentCustomerLesseeRangeDTO;

@Mapper(componentModel = "spring")
public interface EquipmentCustomerLesseeRangeMapper {

	EquipmentCustomerLesseeRangeMapper INSTANCE = Mappers.getMapper(EquipmentCustomerLesseeRangeMapper.class);
	EquipmentCustomerLesseeRangeDTO EquipmentCustomerLesseeRangeToEquipmentCustomerLesseeRangeDTO(EquipmentCustomerLesseeRange equipmentCustomerLesseeRange);
	EquipmentCustomerLesseeRange EquipmentCustomerLesseeRangeDTOToEquipmentCustomerLesseeRange(EquipmentCustomerLesseeRangeDTO equipmentCustomerLesseeRangeDto);
}
