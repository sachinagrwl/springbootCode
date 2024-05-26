package com.nscorp.obis.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.nscorp.obis.domain.DamageReason;
import com.nscorp.obis.dto.DamageReasonDTO;

@Mapper(componentModel = "spring")
public interface DamageReasonMapper {

	DamageReasonMapper INSTANCE = Mappers.getMapper(DamageReasonMapper.class);

	DamageReasonDTO damageReasonToDamageReasonDTO(DamageReason damageReason);

	DamageReason damageReasonDTOToDamageReason(DamageReasonDTO damageReasonDTO);

}
