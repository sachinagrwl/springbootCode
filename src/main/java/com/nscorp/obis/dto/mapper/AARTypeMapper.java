package com.nscorp.obis.dto.mapper;

import com.nscorp.obis.domain.AARType;
import com.nscorp.obis.dto.AARTypeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AARTypeMapper {

	AARTypeMapper INSTANCE = Mappers.getMapper(AARTypeMapper.class);

	AARTypeDTO aarTypeToAARTypeDTO(AARType aarType);

	AARType aarTypeDTOToAARType(AARTypeDTO aarTypeDTO);
	
}
