package com.nscorp.obis.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.nscorp.obis.domain.Equipment;
import com.nscorp.obis.dto.EquipmentDTO;

@Mapper(componentModel = "spring")
public interface EquipmentMapper {
	
	EquipmentMapper INSTANCE = Mappers.getMapper(EquipmentMapper.class);
	EquipmentDTO equipmentToEquipmentDTO(Equipment equipment);
	Equipment equipmentDTOToEquipment(EquipmentDTO equipmentDTO);

}
