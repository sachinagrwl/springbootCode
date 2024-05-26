package com.nscorp.obis.dto.mapper;

import com.nscorp.obis.domain.GenericCodeUpdate;
import com.nscorp.obis.dto.GenericCodeUpdateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface GenericCodeUpdateMapper {

	
	GenericCodeUpdateMapper INSTANCE = Mappers.getMapper(GenericCodeUpdateMapper.class);
	GenericCodeUpdateDTO GenericCodeUpdateToGenericCodeUpdateDTO(GenericCodeUpdate genericCodeUpdate);
	GenericCodeUpdate GenericCodeUpdateDTOToGenericCodeUpdate(GenericCodeUpdateDTO genericCodeUpdateDTO);
}
