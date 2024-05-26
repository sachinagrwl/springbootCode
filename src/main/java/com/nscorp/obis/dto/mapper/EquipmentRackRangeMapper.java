package com.nscorp.obis.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.nscorp.obis.domain.EquipmentRackRange;
import com.nscorp.obis.domain.TempEVT;
import com.nscorp.obis.dto.EquipmentRackRangeDTO;
import com.nscorp.obis.dto.TempEVTDTO;

@Mapper(componentModel = "spring")
public interface EquipmentRackRangeMapper {
	
	EquipmentRackRangeMapper INSTANCE = Mappers.getMapper(EquipmentRackRangeMapper.class);
	EquipmentRackRangeDTO equipmentRackRangeToEquipmentRackRangeDTO(EquipmentRackRange equipmentRackRange);
	EquipmentRackRange equipmentRackRangeDTOToEquipmentRackRange(EquipmentRackRangeDTO equipmentRackRangeDTO);
	

}
