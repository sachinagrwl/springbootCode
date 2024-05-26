package com.nscorp.obis.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.nscorp.obis.domain.DamageComponentReason;
import com.nscorp.obis.dto.DamageComponentReasonDTO;

@Mapper(componentModel = "spring")
public interface DamageComponentReasonMapper {

	DamageComponentReasonMapper INSTANCE = Mappers.getMapper(DamageComponentReasonMapper.class);

	DamageComponentReasonDTO damageComponentReasonToDamageComponentReasonDTO(
			DamageComponentReason damageComponentReason);

	DamageComponentReason damageComponentReasonDTOToDamageComponentReason(
			DamageComponentReasonDTO damageComponentReasonDTO);

}
