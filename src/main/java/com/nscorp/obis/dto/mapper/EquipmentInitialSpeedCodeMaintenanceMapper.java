package com.nscorp.obis.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.nscorp.obis.domain.EquipmentInitialSpeedCodeMaintenance;
import com.nscorp.obis.dto.EquipmentInitialSpeedCodeMaintenanceDTO;

@Mapper(componentModel = "spring")
public interface EquipmentInitialSpeedCodeMaintenanceMapper {

	EquipmentInitialSpeedCodeMaintenanceMapper INSTANCE = Mappers.getMapper(EquipmentInitialSpeedCodeMaintenanceMapper.class);
	EquipmentInitialSpeedCodeMaintenanceDTO EquipmentInitialSpeedCodeMaintenanceToEquipmentInitialSpeedCodeMaintenanceDTO(EquipmentInitialSpeedCodeMaintenance eqInitSpeedCode); 
	EquipmentInitialSpeedCodeMaintenance EquipmentInitialSpeedCodeMaintenanceDTOToEquipmentInitialSpeedCodeMaintenance(EquipmentInitialSpeedCodeMaintenanceDTO eqInitSpeedCodeDto);

}