package com.nscorp.obis.dto.mapper;

import com.nscorp.obis.domain.CarEmbargo;
import com.nscorp.obis.dto.CarEmbargoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CarEmbargoMapper {

    CarEmbargoMapper INSTANCE = Mappers.getMapper(CarEmbargoMapper.class);
    CarEmbargoDTO carEmbargoToCarEmbargoDTO(CarEmbargo carEmbargo);
    CarEmbargo carEmbargoDTOToCarEmbargo(CarEmbargoDTO carEmbargoDTO);
}
