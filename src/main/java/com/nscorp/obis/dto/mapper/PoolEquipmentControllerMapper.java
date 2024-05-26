package com.nscorp.obis.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.nscorp.obis.domain.PoolEquipmentController;
import com.nscorp.obis.dto.PoolEquipmentControllerDTO;

@Mapper(componentModel = "spring")
public interface PoolEquipmentControllerMapper {
	
	PoolEquipmentControllerMapper INSTANCE = Mappers.getMapper(PoolEquipmentControllerMapper.class);
	PoolEquipmentControllerDTO poolEquipmentControllerToPoolEquipmentControllerDTO(PoolEquipmentController poolEquipmentController);
	PoolEquipmentController poolEquipmentControllerDTOToPoolEquipmentController(PoolEquipmentControllerDTO poolEquipmentControllerDTO);

}
