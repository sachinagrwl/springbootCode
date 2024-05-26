package com.nscorp.obis.dto.mapper;

import com.nscorp.obis.domain.MergeStationTermHandle;
import com.nscorp.obis.dto.MergeStationTermHandleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MergeStationTermHandleMapper {

	MergeStationTermHandleMapper INSTANCE = Mappers.getMapper(MergeStationTermHandleMapper.class);

	MergeStationTermHandleDTO mergeStationTermHandleToMergeStationTermHandleDTO(MergeStationTermHandle mergeStationTermHandle);

	MergeStationTermHandle mergeStationTermHandleDTOToMergeStationTermHandle(MergeStationTermHandleDTO mergeStationTermHandleDTO);

}
