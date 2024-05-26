package com.nscorp.obis.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.domain.UmlerStackCar;
import com.nscorp.obis.dto.UmlerStackCarDTO;

@Mapper(componentModel = CommonConstants.MAPPER_COMPONENT_MODEL)
public interface UmlerStackCarMapper {
	UmlerStackCarMapper INSTANCE = Mappers.getMapper(UmlerStackCarMapper.class);

	UmlerStackCarDTO umlerStackCarToUmlerStackCarDTO(UmlerStackCar umlerStackCar);

	UmlerStackCar umlerStackCarDTOToUmlerStackCar(UmlerStackCarDTO umlerStackCarDTO);
}
