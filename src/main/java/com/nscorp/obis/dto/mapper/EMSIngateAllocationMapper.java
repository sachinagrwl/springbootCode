package com.nscorp.obis.dto.mapper;

import com.nscorp.obis.domain.EMSIngateAllocation;
import com.nscorp.obis.dto.EMSIngateAllocationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EMSIngateAllocationMapper {
	EMSIngateAllocationMapper INSTANCE = Mappers.getMapper(EMSIngateAllocationMapper.class);

	EMSIngateAllocationDTO emsIngateAllocationToEMSIngateAllocationDTO(EMSIngateAllocation emsIngateAllocation);

	EMSIngateAllocation emsIngateAllocationDTOToEMSIngateAllocation(EMSIngateAllocationDTO emsIngateAllocationDTO);
}
