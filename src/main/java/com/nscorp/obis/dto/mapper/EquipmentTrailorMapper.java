package com.nscorp.obis.dto.mapper;

import com.nscorp.obis.domain.EquipmentTrailor;
import com.nscorp.obis.dto.EquipmentTrailorDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EquipmentTrailorMapper {

    EquipmentTrailorMapper INSTANCE = Mappers.getMapper(EquipmentTrailorMapper.class);
    EquipmentTrailorDTO EquipmentTrailorToEquipmentTrailorDTO(EquipmentTrailor equipmentTrailor);
    EquipmentTrailor EquipmentTrailorDTOToEquipmentTrailor(EquipmentTrailorDTO equipmentTrailorDTO);
}
