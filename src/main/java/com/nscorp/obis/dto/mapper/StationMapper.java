package com.nscorp.obis.dto.mapper;

import com.nscorp.obis.domain.Station;
import com.nscorp.obis.dto.StationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface StationMapper {

    StationMapper INSTANCE = Mappers.getMapper(StationMapper.class);

    StationDTO stationToStationDTO(Station station);

    Station stationDTOToStation(StationDTO stationDTO);
}
