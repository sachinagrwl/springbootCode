package com.nscorp.obis.dto.mapper.health;

import com.nscorp.obis.domain.health.Information;
import com.nscorp.obis.dto.health.InformationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface InformationMapper {

    InformationMapper INSTANCE = Mappers.getMapper(InformationMapper.class);
    InformationDTO ApplicationToApplicationDTO(Information component);
    Information ApplicationDTOToApplication(InformationDTO componentDto);
}
