package com.nscorp.obis.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.nscorp.obis.domain.DrayageScac;
import com.nscorp.obis.dto.DrayageScacDTO;

@Mapper(componentModel = "spring")
public interface DrayageScacMapper {
	DrayageScacMapper INSTANCE = Mappers.getMapper(DrayageScacMapper.class);

	DrayageScac drayageScacDtoToDrayageScac(DrayageScacDTO drayageScacDto);

	DrayageScacDTO drayageScacToDrayageDto(DrayageScac drayageScac);

}
