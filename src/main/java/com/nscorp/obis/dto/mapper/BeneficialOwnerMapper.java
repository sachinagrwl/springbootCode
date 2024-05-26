package com.nscorp.obis.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.nscorp.obis.domain.BeneficialOwner;
import com.nscorp.obis.dto.BeneficialOwnerDTO;

@Mapper(componentModel = "spring")
public interface BeneficialOwnerMapper {
    
    BeneficialOwnerMapper INSTANCE = Mappers.getMapper(BeneficialOwnerMapper.class);
	BeneficialOwnerDTO beneficialOwnerToBeneficialOwnerDTO(BeneficialOwner beneficialOwner);
	BeneficialOwner beneficialOwnerDTOToBeneficialOwner(BeneficialOwnerDTO beneficialOwnerDTO);

}
