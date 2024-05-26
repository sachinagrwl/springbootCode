package com.nscorp.obis.dto.mapper;

import com.nscorp.obis.domain.EMSIngateRestriction;
import com.nscorp.obis.dto.EMSIngateRestrictionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EMSIngateRestrictionMapper {
	EMSIngateRestrictionMapper INSTANCE = Mappers.getMapper(EMSIngateRestrictionMapper.class);

	EMSIngateRestrictionDTO emsIngateRestrictionToEMSIngateRestrictionDTO(EMSIngateRestriction emsIngateRestriction);
		
	EMSIngateRestriction emsIngateRestrictionDTOToEMSIngateRestriction(EMSIngateRestrictionDTO emsIngateRestrictionDTO);
}
