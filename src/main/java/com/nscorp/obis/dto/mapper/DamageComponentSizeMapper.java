package com.nscorp.obis.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.nscorp.obis.domain.DamageComponentSize;
import com.nscorp.obis.dto.DamageComponentSizeDTO;

@Mapper(componentModel = "spring")
public interface DamageComponentSizeMapper {
    
    DamageComponentSizeMapper INSTANCE = Mappers.getMapper(DamageComponentSizeMapper.class);

	DamageComponentSizeDTO damageComponentSizeToDamageComponentSizeDTO(
            DamageComponentSize damageComponentSize);

    DamageComponentSize damageComponentSizeDTOToDamageComponentSize(
            DamageComponentSizeDTO damageComponentSizeDTO);
}
