package com.nscorp.obis.dto.mapper;
import com.nscorp.obis.domain.TiaTrucker;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.nscorp.obis.domain.Trucker;
import com.nscorp.obis.dto.TruckerDTO;

@Mapper(componentModel = "spring")
public interface TruckerMapper {
	TruckerMapper INSTANCE = Mappers.getMapper(TruckerMapper.class);
	TruckerDTO TruckerToTruckerDTO(Trucker Trucker);
	Trucker TruckerDTOToTrucker(TruckerDTO truckerDto);
}
