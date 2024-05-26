package com.nscorp.obis.dto.mapper;

import com.nscorp.obis.domain.CityState;
import com.nscorp.obis.dto.CityStateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CityStateMapper {

	CityStateMapper INSTANCE = Mappers.getMapper(CityStateMapper.class);

	CityStateDTO cityStateToCityStateDTO(CityState cityState);

	CityState cityStateDtoToCityState(CityStateDTO cityStateDTO);
	
}
