package com.nscorp.obis.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.domain.GuaranteeCustCrossRef;
import com.nscorp.obis.dto.GuaranteeCustCrossRefDTO;

@Mapper(componentModel = CommonConstants.MAPPER_COMPONENT_MODEL)
public interface GuaranteeCustCrossRefMapper {

	GuaranteeCustCrossRefMapper INSTANCE = Mappers.getMapper(GuaranteeCustCrossRefMapper.class);

	GuaranteeCustCrossRefDTO GuaranteeCustCrossRefToGuaranteeCustCrossRefDTO(GuaranteeCustCrossRef guaranteeCustCrossRef);

	GuaranteeCustCrossRef GuaranteeCustCrossRefDTOToGuaranteeCustCrossRef(
			GuaranteeCustCrossRefDTO guaranteeCustCrossRefDTO);

}
