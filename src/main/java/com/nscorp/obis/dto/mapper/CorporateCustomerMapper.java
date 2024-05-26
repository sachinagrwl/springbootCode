package com.nscorp.obis.dto.mapper;

import com.nscorp.obis.domain.CorporateCustomer;
import com.nscorp.obis.dto.CorporateCustomerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CorporateCustomerMapper {
	
	CorporateCustomerMapper INSTANCE = Mappers.getMapper(CorporateCustomerMapper.class);

	CorporateCustomerDTO corporateCustomerToCorporateCustomerDTO(CorporateCustomer corporateCustomer);

	CorporateCustomer corporateCustomerDTOToCorporateCustomer(CorporateCustomerDTO corporateCustomerDTO);

}
