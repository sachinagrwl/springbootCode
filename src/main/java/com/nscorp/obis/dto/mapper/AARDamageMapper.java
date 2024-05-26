package com.nscorp.obis.dto.mapper;


import com.nscorp.obis.domain.AARDamage;
import com.nscorp.obis.dto.AARDamageDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AARDamageMapper {

    AARDamageMapper INSTANCE = Mappers.getMapper(AARDamageMapper.class);
    AARDamageDTO AARDamageToAARDamageDTO(AARDamage aarDamage);
}
