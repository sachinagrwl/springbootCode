package com.nscorp.obis.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.nscorp.obis.domain.HazRestrPermit;
import com.nscorp.obis.dto.HazRestrPermitDTO;

@Mapper(componentModel = "spring")
public interface HazRestrPermitMapper {

	HazRestrPermitMapper INSTANCE = Mappers.getMapper(HazRestrPermitMapper.class);

	HazRestrPermitDTO hazRestrPermitToHazRestrPermitDTO(HazRestrPermit hazRestrPermit);

	HazRestrPermit hazRestrPermitDtoToHazRestrPermit(HazRestrPermitDTO hazRestrPermitDto);
}
