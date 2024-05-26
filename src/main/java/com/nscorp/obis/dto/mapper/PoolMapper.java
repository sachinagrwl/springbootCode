package com.nscorp.obis.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import com.nscorp.obis.domain.Pool;
import com.nscorp.obis.dto.PoolDTO;

@Mapper(componentModel = "spring")
public interface PoolMapper {

	PoolMapper INSTANCE = Mappers.getMapper(PoolMapper.class);

	@Mapping(target = "terminals" , ignore = true)
	@Mapping(target = "controllers" , ignore = true)
	@Mapping(target = "equipmentRanges" , ignore = true)
	@Mapping(target = "truckerGroup" , ignore = true)
	@Mapping(target = "agreementRequired" , ignore = true)
	@Mapping(target = "checkTrucker" , ignore = true)
	@Mapping(target = "customers" , ignore = true)
	PoolDTO fetchAllPoolToPoolDto(Pool pool);

	PoolDTO poolToPoolDto(Pool pool);

	Pool poolDtoToPool(PoolDTO poolDTO);
}
