package com.nscorp.obis.dto.mapper;

import com.nscorp.obis.domain.EquipmentDefaultTareWeightMaintenance;
import com.nscorp.obis.dto.EquipmentDefaultTareWeightMaintenanceDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface EquipmentDefaultTareWeightMaintenanceMapper {
	
	EquipmentDefaultTareWeightMaintenanceMapper INSTANCE = Mappers.getMapper(EquipmentDefaultTareWeightMaintenanceMapper.class);

	EquipmentDefaultTareWeightMaintenanceDTO equipmentDefaultTareWeightMaintenanceToEquipmentDefaultTareWeightMaintenanceDTO(EquipmentDefaultTareWeightMaintenance equipmentDefaultTareWeight);

	EquipmentDefaultTareWeightMaintenance equipmentDefaultTareWeightMaintenanceDTOToEquipmentDefaultTareWeightMaintenance(EquipmentDefaultTareWeightMaintenanceDTO equipmentDefaultTareWeightDTO);
}
