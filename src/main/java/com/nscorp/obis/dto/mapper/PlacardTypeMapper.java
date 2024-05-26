package com.nscorp.obis.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.nscorp.obis.domain.PlacardType;
import com.nscorp.obis.dto.PlacardTypeDTO;

@Mapper(componentModel = "spring")
public interface PlacardTypeMapper {
	
	PlacardTypeMapper INSTANCE = Mappers.getMapper(PlacardTypeMapper.class);

	PlacardTypeDTO placardTypeToPlacardTypeDTO(PlacardType placardType);

	PlacardType placardTypeDtoToPlacardType(PlacardTypeDTO placardTypeDto);

}
