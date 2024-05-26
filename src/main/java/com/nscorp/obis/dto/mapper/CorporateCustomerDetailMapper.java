package com.nscorp.obis.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.nscorp.obis.domain.CorporateCustomerDetail;
import com.nscorp.obis.dto.CorporateCustomerDetailDTO;

@Mapper(componentModel = "spring")
public interface CorporateCustomerDetailMapper {

	
	CorporateCustomerDetailMapper INSTANCE = Mappers.getMapper(CorporateCustomerDetailMapper.class);

	CorporateCustomerDetailDTO corporateCustomerDetailToCorporateCustomerDetailDTO(CorporateCustomerDetail corporateCustomerDetail);

	CorporateCustomerDetail corporateCustomerDetailDTOToCorporateCustomerDetail(CorporateCustomerDetailDTO corporateCustomerDetailDTO);

}
