package com.nscorp.obis.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.nscorp.obis.domain.BeneficialOwnerDetail;
import com.nscorp.obis.dto.BeneficialOwnerDetailDTO;

@Mapper(componentModel = "spring")
public interface BeneficialOwnerDetailMapper {

	BeneficialOwnerDetailMapper INSTANCE = Mappers.getMapper(BeneficialOwnerDetailMapper.class);

	BeneficialOwnerDetailDTO beneficialOwnerDetailToBeneficialOwnerDetailDTO(BeneficialOwnerDetail beneficialOwnerDetail);

	BeneficialOwnerDetail beneficialOwnerDetailDTOToBeneficialOwnerDetail(BeneficialOwnerDetailDTO beneficialOwneDetailDTO);
}
