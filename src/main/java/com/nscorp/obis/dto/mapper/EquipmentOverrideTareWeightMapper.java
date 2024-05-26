package com.nscorp.obis.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.nscorp.obis.domain.EquipmentOverrideTareWeight;
import com.nscorp.obis.dto.EquipmentOverrideTareWeightDTO;

@Mapper(componentModel = "spring")
public interface EquipmentOverrideTareWeightMapper {
	
	EquipmentOverrideTareWeightMapper INSTANCE = Mappers.getMapper(EquipmentOverrideTareWeightMapper.class);
	EquipmentOverrideTareWeightDTO EquipmentOverrideTareWeightToEquipmentOverrideTareWeightDTO(EquipmentOverrideTareWeight eqOverrideTareWgt);
	EquipmentOverrideTareWeight EquipmentOverrideTareWeightDTOToEquipmentOverrideTareWeight(EquipmentOverrideTareWeightDTO eqOverrideTareWgtDto);

}
