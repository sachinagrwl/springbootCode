package com.nscorp.obis.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.nscorp.obis.domain.Road;
import com.nscorp.obis.dto.RoadDTO;

@Mapper(componentModel = "spring")
public interface RoadMapper {

	RoadMapper INSTANCE = Mappers.getMapper(RoadMapper.class);
	RoadDTO RoadToRoadDTO(Road road);
	Road RoadDTOToRoad(RoadDTO roadDTO);
}
