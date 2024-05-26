package com.nscorp.obis.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.nscorp.obis.domain.AARWhyMadeCodes;
import com.nscorp.obis.dto.AARWhyMadeCodesDTO;

@Mapper(componentModel = "spring")
public interface AARWhyMadeCodesMapper {

	AARWhyMadeCodesMapper INSTANCE = Mappers.getMapper(AARWhyMadeCodesMapper.class);

	AARWhyMadeCodesDTO aarWhyMadeCodesToAARWhyMadeCodesDTO(AARWhyMadeCodes aarWhyMadeCodes);

	AARWhyMadeCodes aarWhyMadeCodesDTOToAARWhyMadeCodes(AARWhyMadeCodesDTO aarWhyMadeCodesDTO);
}
