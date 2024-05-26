package com.nscorp.obis.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.domain.UmlerConventionalCar;
import com.nscorp.obis.dto.UmlerConventionalCarDTO;

@Mapper(componentModel = CommonConstants.MAPPER_COMPONENT_MODEL)
public interface UmlerConventionalCarMapper {
	
	UmlerConventionalCarMapper INSTANCE = Mappers.getMapper(UmlerConventionalCarMapper.class);
	UmlerConventionalCarDTO UmlerConventionalCarToUmlerConventionalCarDTO(UmlerConventionalCar umbConvLoad);
	UmlerConventionalCar  UmlerConventionalCarDTOToUmlerConventionalCar(UmlerConventionalCarDTO umbConvLoadDto);

}
