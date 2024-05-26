package com.nscorp.obis.dto.mapper;


import com.nscorp.obis.domain.HazRestriction;
import com.nscorp.obis.dto.HazRestrictionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface HazRestrictionMapper {
	
	HazRestrictionMapper INSTANCE = Mappers.getMapper(HazRestrictionMapper.class);
	HazRestrictionDTO hazRestrictionToHazRestrictionDTO(HazRestriction hazRestriction);
	HazRestriction hazRestrictionDTOToHazRestriction(HazRestrictionDTO hazRestrictionDto);

}
