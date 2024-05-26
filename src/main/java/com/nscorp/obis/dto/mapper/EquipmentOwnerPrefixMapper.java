package com.nscorp.obis.dto.mapper;

import org.mapstruct.Mapper;

import org.mapstruct.factory.Mappers;

import com.nscorp.obis.domain.EquipmentOwnerPrefix;
import com.nscorp.obis.dto.EquipmentOwnerPrefixDTO;

@Mapper(componentModel = "spring")
public interface EquipmentOwnerPrefixMapper {

	EquipmentOwnerPrefixMapper INSTANCE = Mappers.getMapper(EquipmentOwnerPrefixMapper.class);
	EquipmentOwnerPrefixDTO equipmentOwnerPrefixToequipmentOwnerPrefixDTO(EquipmentOwnerPrefix equipmentOwnerPrefix);
	EquipmentOwnerPrefix equipmentOwnerPrefixDTOToequipmentOwnerPrefix(EquipmentOwnerPrefixDTO equipmentOwnerPrefixDTO);
}

