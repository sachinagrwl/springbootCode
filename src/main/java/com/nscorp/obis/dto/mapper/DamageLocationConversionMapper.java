package com.nscorp.obis.dto.mapper;

import com.nscorp.obis.domain.DamageLocationConversion;
import com.nscorp.obis.dto.DamageLocationConversionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DamageLocationConversionMapper {
    DamageLocationConversionMapper INSTANCE = Mappers.getMapper(DamageLocationConversionMapper.class);
    DamageLocationConversion damageLocationConversionDtoToDamageLocationConversion(DamageLocationConversionDTO damageLocationConversionDTO);
    DamageLocationConversionDTO damageLocationConversionToDamageLocationConversionDTO(DamageLocationConversion damageLocationConversion);
}
