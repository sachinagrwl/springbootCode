package com.nscorp.obis.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.nscorp.obis.domain.AARHitch;
import com.nscorp.obis.dto.AARHitchDTO;

@Mapper(componentModel = "spring")
public interface AARHitchMapper {
	
	AARHitchMapper INSTANCE = Mappers.getMapper(AARHitchMapper.class);
	AARHitchDTO AARHitchToAARHitchDTO(AARHitch aarHitch);
	AARHitch AARHitchDTOToAARHitch(AARHitchDTO aarHitchDto);

}
