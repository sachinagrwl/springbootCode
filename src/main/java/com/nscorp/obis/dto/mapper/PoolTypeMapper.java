package com.nscorp.obis.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.nscorp.obis.common.CommonConstants;
import com.nscorp.obis.domain.PoolType;
import com.nscorp.obis.dto.PoolTypeDTO;

@Mapper(componentModel = CommonConstants.MAPPER_COMPONENT_MODEL)
public interface PoolTypeMapper {
	
	PoolTypeMapper INSTANCE = Mappers.getMapper(PoolTypeMapper.class);
	PoolTypeDTO poolTypeToPoolTypeDTO(PoolType poolType);
	PoolType poolTypeDtoToPoolType(PoolTypeDTO poolTypeDto);

}
