package com.nscorp.obis.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.nscorp.obis.domain.TempEVT;
import com.nscorp.obis.dto.TempEVTDTO;

@Mapper(componentModel = "spring")
public interface TempEVTMapper {

	
	TempEVTMapper INSTANCE = Mappers.getMapper(TempEVTMapper.class);
	TempEVTDTO tempEVTToTempEVTDTO(TempEVT tempEVT);
	TempEVT tempEVTDTOToTempEVT(TempEVTDTO tempEVTDTO);

}
