package com.nscorp.obis.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.nscorp.obis.domain.DamageCodeConversion;
import com.nscorp.obis.dto.DamageCodeConversionDTO;

@Mapper(componentModel = "spring")
public interface DamageCodeConversionMapper {

	DamageCodeConversionMapper INSTANCE = Mappers.getMapper(DamageCodeConversionMapper.class);

	DamageCodeConversionDTO damageCodeConversionToDamageCodeConversionDTO(DamageCodeConversion damageCodeConversion);

	DamageCodeConversion damageCodeConversionDtoToDamageCodeConversion(DamageCodeConversionDTO damageCodeConversionDto);
}
