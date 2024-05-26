package com.nscorp.obis.dto.mapper;

import com.nscorp.obis.domain.StationRestriction;
import com.nscorp.obis.dto.StationRestrictionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface StationRestrictionMapper {
	
	StationRestrictionMapper INSTANCE = Mappers.getMapper(StationRestrictionMapper.class);

	StationRestrictionDTO stationRestrictionToStationRestrictionDTO(StationRestriction stationRestriction);

	StationRestriction stationRestrictionDTOToStationRestriction(StationRestrictionDTO stationRestrictionDTO);
}
