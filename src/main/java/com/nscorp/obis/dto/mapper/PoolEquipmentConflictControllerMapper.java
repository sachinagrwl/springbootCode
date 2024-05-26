package com.nscorp.obis.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.nscorp.obis.domain.PoolEquipmentConflictController;
import com.nscorp.obis.dto.PoolEquipmentConflictControllerDTO;

@Mapper(componentModel = "spring")
public interface PoolEquipmentConflictControllerMapper {
	
	PoolEquipmentConflictControllerMapper INSTANCE = Mappers.getMapper(PoolEquipmentConflictControllerMapper.class);
	PoolEquipmentConflictControllerDTO PoolEquipmentConflictControllerToPoolEquipmentConflictControllerDTO(PoolEquipmentConflictController poolConflict);
	PoolEquipmentConflictController  PoolEquipmentConflictControllerDTOToPoolEquipmentConflictController(PoolEquipmentConflictControllerDTO poolConflictDto);

}
