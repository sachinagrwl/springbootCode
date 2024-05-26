package com.nscorp.obis.dto.mapper;

import com.nscorp.obis.domain.ResourceList;
import com.nscorp.obis.dto.ResourceListDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ResourceListMapper {

	ResourceListMapper INSTANCE = Mappers.getMapper(ResourceListMapper.class);
	
	ResourceListDTO resourceListToResourceListDTO(ResourceList resourceList);

	ResourceList resourceListDTOToResourceList(ResourceListDTO resourceListDTO);
}
