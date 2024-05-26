package com.nscorp.obis.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.nscorp.obis.domain.DamageLocation;
import com.nscorp.obis.dto.DamageLocationDTO;

@Mapper(componentModel = "spring")
public interface DamageLocationMapper {
    
    DamageLocationMapper INSTANCE = Mappers.getMapper(DamageLocationMapper.class);

    DamageLocationDTO damageLocationToDamageLocationDTO(DamageLocation damageLocation);

    DamageLocation damageLocationDTOToDamageLocation(DamageLocationDTO damageLocationDTO);

}
