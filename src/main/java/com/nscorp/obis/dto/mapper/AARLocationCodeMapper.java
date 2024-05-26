package com.nscorp.obis.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.nscorp.obis.domain.AARLocationCode;
import com.nscorp.obis.dto.AARLocationCodeDTO;

@Mapper(componentModel = "spring")
public interface AARLocationCodeMapper {

	AARLocationCodeMapper INSTANCE = Mappers.getMapper(AARLocationCodeMapper.class);

	AARLocationCodeDTO aarLocationCodeToAARLocationCodeDTO(AARLocationCode aarLocationCode);

	AARLocationCode aarLocationCodeDTOToAARLocationCode(AARLocationCodeDTO aarLocationCodeDTO);
}
