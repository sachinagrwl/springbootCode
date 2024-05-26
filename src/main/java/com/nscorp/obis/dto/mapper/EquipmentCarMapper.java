package com.nscorp.obis.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.nscorp.obis.domain.EquipmentCar;
import com.nscorp.obis.dto.EquipmentCarDTO;

@Mapper(componentModel = "spring")
public interface EquipmentCarMapper {

	EquipmentCarMapper INSTANCE = Mappers.getMapper(EquipmentCarMapper.class);
	EquipmentCarDTO EquipmentCarToEquipmentCarDTO(EquipmentCar equipmentCar);
	EquipmentCar EquipmentCarDTOToEquipmentCar(EquipmentCarDTO equipmentCarDTO);
}
