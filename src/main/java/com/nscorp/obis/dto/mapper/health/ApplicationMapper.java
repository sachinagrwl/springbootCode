package com.nscorp.obis.dto.mapper.health;

import com.nscorp.obis.domain.health.Application;
import com.nscorp.obis.dto.health.ApplicationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ApplicationMapper {

    ApplicationMapper INSTANCE = Mappers.getMapper(ApplicationMapper.class);
    ApplicationDTO ApplicationToApplicationDTO(Application application);
    Application ApplicationDTOToApplication(ApplicationDTO applicationDto);
}
