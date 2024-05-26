package com.nscorp.obis.dto.mapper;

import com.nscorp.obis.domain.DamageCompLoc;
import com.nscorp.obis.dto.DamageCompLocDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DamageCompLocMapper {

    DamageCompLocMapper INSTANCE = Mappers.getMapper(DamageCompLocMapper.class);
    DamageCompLocDTO damageCompLocToDamageCompLocDTO(DamageCompLoc damageComponent);
    DamageCompLoc damageCompLocDTOToDamageCompLoc(DamageCompLocDTO damageComponentDTO);
}
