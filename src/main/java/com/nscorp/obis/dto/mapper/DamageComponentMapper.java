package com.nscorp.obis.dto.mapper;

import com.nscorp.obis.domain.DamageComponent;
import com.nscorp.obis.dto.DamageComponentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DamageComponentMapper {

    DamageComponentMapper INSTANCE = Mappers.getMapper(DamageComponentMapper.class);
    DamageComponentDTO damageComponentToDamageComponentDTO(DamageComponent damageComponent);
    DamageComponent damageComponentDTOToDamageComponent(DamageComponentDTO damageComponentDTO);
}
