package com.nscorp.obis.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.domain.UmlerLowProfileCar;
import com.nscorp.obis.dto.UmlerLowProfileCarDTO;

@Mapper(componentModel = CommonConstants.MAPPER_COMPONENT_MODEL)
public interface UmlerLowProfileCarMapper {
	UmlerLowProfileCarMapper INSTANCE = Mappers.getMapper(UmlerLowProfileCarMapper.class);

	UmlerLowProfileCarDTO UmlerLowProfileCarToUmlerLowProfileCarDTO(UmlerLowProfileCar umlerLowProfile);

	UmlerLowProfileCar UmlerLowProfileCarDTOToUmlerLowProfileCar(UmlerLowProfileCarDTO umlerLowProfileDTO);
}
