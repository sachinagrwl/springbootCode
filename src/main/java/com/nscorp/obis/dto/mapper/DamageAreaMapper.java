package com.nscorp.obis.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.nscorp.obis.domain.DamageArea;
import com.nscorp.obis.dto.DamageAreaDTO;

@Mapper(componentModel = "spring")
public interface DamageAreaMapper {
	
	DamageAreaMapper INSTANCE = Mappers.getMapper(DamageAreaMapper.class);
	
	DamageArea DamageAreaDTOToDamageArea(DamageAreaDTO damageAreaDTO);
	DamageAreaDTO DamageAreaToDamageAreaDTO(DamageArea damageArea);
}
