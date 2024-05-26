package com.nscorp.obis.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.nscorp.obis.domain.StationTermHandle;
import com.nscorp.obis.dto.StationTermHandleDTO;

@Mapper(componentModel = "spring")
public interface StationTermHandleMapper {

	StationTermHandleMapper INSTANCE = Mappers.getMapper(StationTermHandleMapper.class);

	StationTermHandleDTO stationTermHandleToStationTermHandleDTO(StationTermHandle stationTermHandle);

	StationTermHandle stationTermHandleDTOToStationTermHandle(StationTermHandleDTO stationTermHandleDTO);

}
