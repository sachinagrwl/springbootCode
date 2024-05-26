package com.nscorp.obis.dto.mapper.health;

import com.nscorp.obis.domain.health.Component;
import com.nscorp.obis.dto.health.ComponentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ComponentMapper {

    ComponentMapper INSTANCE = Mappers.getMapper(ComponentMapper.class);
    ComponentDTO ApplicationToApplicationDTO(Component component);
    Component ApplicationDTOToApplication(ComponentDTO componentDto);
}
