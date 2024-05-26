package com.nscorp.obis.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.nscorp.obis.domain.EndorsementCode;
import com.nscorp.obis.dto.EndorsementCodeDTO;

@Mapper(componentModel = "spring")
public interface EndorsementCodeMapper {

	
	EndorsementCodeMapper INSTANCE = Mappers.getMapper(EndorsementCodeMapper.class);
	EndorsementCodeDTO endorsementCodeToEndorsementCodeDTO(EndorsementCode endorsementCode);
	EndorsementCode endorsementCodeDTOToEndorsementCode(EndorsementCodeDTO endorsementCodeDTO);
}
