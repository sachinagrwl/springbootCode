package com.nscorp.obis.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.nscorp.obis.domain.EquipmentEmbargo;
import com.nscorp.obis.dto.EquipmentEmbargoDTO;

@Mapper(componentModel = "spring")
public interface EquipmentEmbargoMapper {
	
	EquipmentEmbargoMapper INSTANCE = Mappers.getMapper(EquipmentEmbargoMapper.class);
	EquipmentEmbargoDTO EquipmentEmbargoToEquipmentEmbargoDTO(EquipmentEmbargo eqEmbargo);
	EquipmentEmbargo  EquipmentEmbargoDTOToEquipmentEmbargo(EquipmentEmbargoDTO equipmentEmbargoDto);
	
}
